/*  Copyright (c) 2006-2007, Vladimir Nikic
    All rights reserved.
	
    Redistribution and use of this software in source and binary forms, 
    with or without modification, are permitted provided that the following 
    conditions are met:
	
    * Redistributions of source code must retain the above
      copyright notice, this list of conditions and the
      following disclaimer.
	
    * Redistributions in binary form must reproduce the above
      copyright notice, this list of conditions and the
      following disclaimer in the documentation and/or other
      materials provided with the distribution.
	
    * The name of HtmlCleaner may not be used to endorse or promote 
      products derived from this software without specific prior
      written permission.

    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
    AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
    IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
    ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
    LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
    CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
    SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
    INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
    CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
    ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
    POSSIBILITY OF SUCH DAMAGE.
	
    You can contact Vladimir Nikic by sending e-mail to
    nikic_vladimir@yahoo.com. Please include the word "HtmlCleaner" in the
    subject line.
*/

package org.htmlcleaner;

import java.io.*;
import java.util.*;

/**
 * Main HTML tokenizer.
 * <p>Its taks is to parse HTML and produce list of valid tokens:
 * open tag tokens, end tag tokens, contents (text) and comments.
 * As soon as new item is added to token list, cleaner is invoked
 * to clean current list at the end.</p>
 *
 * Created by: Vladimir Nikic.<br>
 * Date: November, 2006

 */
public class HtmlTokenizer {
	
	private final static int WORKING_BUFFER_SIZE = 1024;
	
	private final static String trChString = "\u0131\u0130\u015F\u015E\u011F\u011E\u00fd\u00dd\u00fe\u00de\u00f0\u00d0\u00E7\u00C7\u00FC\u00DC\u00F6\u00D6";
	private final static String unicode[] = {"&#305;", "&#304;", "&#351;", "&#350;", "&#287;", "&#286;", 
											"&#305;", "&#304;", "&#351;", "&#350;", "&#287;", "&#286;", 
											"&#231;", "&#199;", "&#252;", "&#220;", "&#246;", "&#214;"};
	
	private final static String dirtyChString = "\n\"&<>";
	private final static String cleanhtmlEntities[] = {"<br/>", "&quot;", "&amp;", "&lt;", "&gt;"};

	private final static String clickAttributes = "onblur$onchange$onclick$ondblclick$onfocus$onkeydown$onkeypress$onkeyup$onLoad$onmousedown$onmouseout$onmouseover$onmousemove$onmouseup$onselect$onunload";

    private BufferedReader _reader;
    private char[] _working = new char[WORKING_BUFFER_SIZE];
    
    private transient int _pos = 0;
    private transient int _len = -1;

    private transient StringBuffer _saved = new StringBuffer(512);

    private transient boolean _isLateForDoctype = false;
    private transient TagToken _currentTagToken = null;
    private transient List _tokenList = new ArrayList();

    private boolean _asExpected = true;

    private boolean _isScriptContext = false;
    private boolean _isStyleContext = false;
    
    private boolean _isTextPlain = false;

    private HtmlCleaner cleaner;

    /**
     * Constructor - cretes instance of the parser with specified content.
     * @param cleaner
     * @throws IOException
     */
    public HtmlTokenizer(HtmlCleaner cleaner) throws IOException {
        this._reader = new BufferedReader( cleaner.getReader() );
        this.cleaner = cleaner;
    }

    private void addToken(BaseToken token) {
        _tokenList.add(token);
        cleaner.makeTree( _tokenList, _tokenList.listIterator(_tokenList.size() - 1) );
    }

    private void readIfNeeded(int neededChars) throws IOException {
        if (_len == -1 && _pos + neededChars >= WORKING_BUFFER_SIZE) {
            int numToCopy = WORKING_BUFFER_SIZE - _pos;
            System.arraycopy(_working, _pos, _working, 0, numToCopy);
    		_pos = 0;
    		int size = _reader.read(_working, numToCopy, WORKING_BUFFER_SIZE - numToCopy);

            if (size < WORKING_BUFFER_SIZE - numToCopy) {
    			_len = (size == -1 && numToCopy == 0) ? 0 : size + numToCopy;
    		} else if (size == -1) {
    			_len = numToCopy;
            }
        }
    }

    List getTokenList() {
    	return this._tokenList;
    }

    private void go() throws IOException {
    	_pos++;
    	readIfNeeded(0);
    }

    private void go(int step) throws IOException {
    	_pos += step;
    	readIfNeeded(step - 1);
    }

    /**
     * Checks if content starts with specified value at the current position.
     * @param value
     * @return true if starts with specified value, false otherwise.
     * @throws IOException
     */
    private boolean startsWith(String value) throws IOException {
        int valueLen = value.length();
        readIfNeeded(valueLen);
        if (_len >= 0 && _pos + valueLen  > _len) {
            return false;
        }

        for (int i = 0; i < valueLen; i++) {
        	char ch1 = Character.toLowerCase( value.charAt(i) );
        	char ch2 = Character.toLowerCase( _working[_pos + i] );
        	if (ch1 != ch2) {
        		return false;
        	}
        }

        return true;
    }

    /**
     * Checks if character at specified position is whitespace.
     * @param position
     * @return true is whitespace, false otherwise.
     */
    private boolean isWhitespace(int position) {
    	if (_len >= 0 && position >= _len) {
            return false;
        }

        return Character.isWhitespace( _working[position] );
    }

    /**
     * Checks if character at current runtime position is whitespace.
     * @return true is whitespace, false otherwise.
     */
    private boolean isWhitespace() {
        return isWhitespace(_pos);
    }

    /**
     * Checks if character at specified position is equal to specified char.
     * @param position
     * @param ch
     * @return true is equals, false otherwise.
     */
    private boolean isChar(int position, char ch) {
    	if (_len >= 0 && position >= _len) {
            return false;
        }

        return Character.toLowerCase(ch) == Character.toLowerCase(_working[position]);
    }

    /**
     * Checks if character at current runtime position is equal to specified char.
     * @param ch
     * @return true is equal, false otherwise.
     */
    private boolean isChar(char ch) {
        return isChar(_pos, ch);
    }

    /**
     * Checks if character at specified position can be identifier start.
     * @param position
     * @return true is may be identifier start, false otherwise.
     */
    private boolean isIdentifierStartChar(int position) {
    	if (_len >= 0 && position >= _len) {
            return false;
        }

        char ch = _working[position];
        return Character.isUnicodeIdentifierStart(ch) || (':' == ch);
    }

    /**
     * Checks if character at current runtime position can be identifier start.
     * @return true is may be identifier start, false otherwise.
     */
    private boolean isIdentifierStartChar() {
        return isIdentifierStartChar(_pos);
    }

    /**
     * Checks if character at current runtime position can be identifier part.
     * @return true is may be identifier part, false otherwise.
     */
    private boolean isIdentifierChar() {
    	if (_len >= 0 && _pos >= _len) {
            return false;
        }

        char ch = _working[_pos];
        return Character.isUnicodeIdentifierStart(ch) || Character.isDigit(ch) || (':' == ch) || ('.' == ch) || ('-' == ch);
    }

    /**
     * Checks if end of the content is reached.
     */
    private boolean isAllRead() {
        return _len >= 0 && _pos >= _len;
    }

    /**
     * Saves specified character to the temporary buffer.
     * @param ch
     */
    private void save(char ch) {
    	int chPos;
    	if(_isTextPlain)
    	{
    		chPos = dirtyChString.indexOf(ch);
        	if(chPos > -1) {
        		_saved.append(cleanhtmlEntities[chPos]);
        		return;
        	}
    	}
    	chPos = trChString.indexOf(ch);
    	if(chPos > -1)
    		_saved.append(unicode[chPos]);
    	else
    		_saved.append(ch);
    }

    /**
     * Saves character at current runtime position to the temporary buffer.
     */
    private void saveCurrent() {
        if (!isAllRead()) {
            save( _working[_pos] );
        }
    }

    /**
     * Saves specified number of characters at current runtime position to the temporary buffer.
     * @throws IOException
     */
    private void saveCurrent(int size) throws IOException {
    	readIfNeeded(size);
        int pos = _pos;
        while ( !isAllRead() && (size > 0) ) {
            save( _working[pos] );
            pos++;
            size--;
        }
    }

    /**
     * Skips whitespaces at current position and moves foreward until
     * non-whitespace character is found or the end of content is reached.
     * @throws IOException
     */
    private void skipWhitespaces() throws IOException {
        while ( !isAllRead() && isWhitespace() ) {
            saveCurrent();
            go();
        }
    }

    private void addSavedAsContent() {
        if (_saved.length() > 0) {
        	if (!_isScriptContext)
        		addToken( new ContentToken(_saved.toString()) );
            _saved.delete(0, _saved.length());
        }
    }

    /**
     * Starts parsing HTML.
     * @throws IOException
     */
    void start(boolean isTextPlain) throws IOException {
    	// initialize runtime values
    	_isTextPlain = isTextPlain;
        _currentTagToken = null;
        _tokenList.clear();
        _asExpected = true;
        _isScriptContext = false;
        _isStyleContext = false;
        _isLateForDoctype = false;

        this._pos = WORKING_BUFFER_SIZE;
        readIfNeeded(0);

        while ( !isAllRead() ) {
            // resets all the runtime values
            _saved.delete(0, _saved.length());
            _currentTagToken = null;
            _asExpected = true;

            // this is enough for making decision
            readIfNeeded(10);

            if (isTextPlain) {
                if ( startsWith("http://") || startsWith("https://") || startsWith("www.") ) {
                	tagAnchorStart();
                } else {
                    content();
                }
            }
            else if (_isScriptContext) {
                if ( startsWith("</script") && (isWhitespace(_pos + 8) || isChar(_pos + 8, '>')) ) {
                    tagEnd();
                } else {
                    content();
                }
            } else if (_isStyleContext) {
                if ( startsWith("</style") && (isWhitespace(_pos + 7) || isChar(_pos + 7, '>')) ) {
                    tagEnd();
                } else {
                    content();
                }
            } else {
                if ( startsWith("<!doctype") ) {
                	if ( !_isLateForDoctype ) {
                		doctype();
                		_isLateForDoctype = true;
                	} else {
                		ignore();
                	}
                } else if ( startsWith("</") && isIdentifierStartChar(_pos + 2) ) {
                	_isLateForDoctype = true;
                    tagEnd();
                } else if ( startsWith("<!--") ) {
                    comment();
                } else if ( startsWith("<") && isIdentifierStartChar(_pos + 1) ) {
                	_isLateForDoctype = true;
                    tagStart();
                } else {
                    content();
                }
            }
        }

        _reader.close();
    }

    /**
     * Parses start of the tag.
     * It expects that current position is at the "<" after which
     * the tag's name follows.
     * @throws IOException
     */
    private void tagStart() throws IOException {
        saveCurrent();
        go();

        if ( isAllRead() ) {
            return;
        }

        String tagName = identifier();
        _currentTagToken = new TagNode(tagName);

        if (_asExpected) {
            skipWhitespaces();
            tagAttributes();

            String originalSource = _saved.toString();
            if ( isChar('>') ) {
            	go();
                if ( "script".equalsIgnoreCase(tagName) ) {
                    _isScriptContext = true;
                } else if ( "style".equalsIgnoreCase(tagName) ) {
                    _isStyleContext = true;
                } else if ( "a".equalsIgnoreCase(tagName) || "area".equalsIgnoreCase(tagName) ) {
                    int pos = originalSource.toLowerCase(new Locale("en", "US")).indexOf("mailto:");
                    if(pos > 0) {
                    	if(originalSource.length() > pos+7) {
	                    	char ch = originalSource.charAt(pos-1);
	                    	if(ch == '\'' || ch == '\"') {
	                    		int posEnd = originalSource.indexOf(ch, pos+7);
	                    		if(posEnd > pos+7)
	                    			_currentTagToken.addAttribute("href", 
	                    					"javascript:parent.compose.fastEmail('" 
	                    					+ originalSource.substring(pos+7, posEnd) 
	                    				+ "');");
	                    	}
                    	}
                    }
                    else
                    	_currentTagToken.addAttribute("target", "_blank");
                }
                originalSource += ">";
            } else if ( startsWith("/>") ) {
            	go(2);
                addToken( new EndTagToken(tagName) );
                originalSource += "/>";
            }
            
            if(!_isScriptContext) {
                addToken(_currentTagToken);
                _currentTagToken.setOriginalSource(originalSource);            	
            }

            _currentTagToken = null;
        } else {
        	addSavedAsContent();
        }
    }

    /**
     * Parses start of the tag.
     * It expects that current position is at the "http://" or "www." after which
     * the tag's name follows.
     * @throws IOException
     */
    private void tagAnchorStart() throws IOException {
        while ( !isAllRead() && !isWhitespace() ) {
            saveCurrent();
            go();
        }
        
        String href = _saved.toString();
        if(href.startsWith("www."))
        	href = "http://" + href;

        TagNode anchorNode = new TagNode("A");
        anchorNode.addAttribute("href", href);
        anchorNode.addAttribute("target", "_blank");

        addToken(anchorNode);
        addSavedAsContent();
        addToken( new EndTagToken("A") );
        
        anchorNode = null;
        
        skipWhitespaces();
        addSavedAsContent();
    }
    
    /**
     * Parses start of the tag.
     * It expects that current position is at the "@" after which
     * the tag's name follows.
     * @throws IOException
     */
    private void tagMailStart() throws IOException {
        while ( !isAllRead() && !isWhitespace() && !isChar('>')) {
            saveCurrent();
            go();
        }

        TagNode anchorNode = new TagNode("A");
        anchorNode.addAttribute("href", "javascript:parent.fastEmail('" + _saved.toString() + "');");

        addToken(anchorNode);
        addSavedAsContent();
        addToken( new EndTagToken("A") );
        
        anchorNode = null;
        
        skipWhitespaces();
        addSavedAsContent();
    }
    
    /**
     * Parses end of the tag.
     * It expects that current position is at the "<" after which
     * "/" and the tag's name follows.
     * @throws IOException
     */
    private void tagEnd() throws IOException {
        saveCurrent(2);
        go(2);

        if ( isAllRead() ) {
            return;
        }

        String tagName = identifier();
        _currentTagToken = new EndTagToken(tagName);

        if (_asExpected) {
            skipWhitespaces();
            tagAttributes();

            String originalSource = _saved.toString();
            addToken(_currentTagToken);

            if ( isChar('>') ) {
            	go();
                originalSource += ">";
            }

            if ( "script".equalsIgnoreCase(tagName) ) {
                _isScriptContext = false;
            } else if ( "style".equalsIgnoreCase(tagName) ) {
                _isStyleContext = false;
            }

            _currentTagToken.setOriginalSource(originalSource);
            _currentTagToken = null;
        } else {
            addSavedAsContent();
        }
    }

    /**
     * Parses an identifier from the current position.
     * @throws IOException
     */
    private String identifier() throws IOException {
        _asExpected = true;

        if ( !isIdentifierStartChar() ) {
            _asExpected = false;
            return null;
        }

        StringBuffer tagName = new StringBuffer(16);

        while ( !isAllRead() && isIdentifierChar() ) {
            saveCurrent();
            tagName.append( _working[_pos] );
            go();
        }

        return tagName.toString();
    }

    /**
     * Parses list tag attributes from the current position.
     * @throws IOException
     */
    private void tagAttributes() throws IOException {
        while( !isAllRead() && _asExpected && !isChar('>') && !startsWith("/>") ) {
            skipWhitespaces();
            String attName = identifier();

            if (!_asExpected) {
                if ( !isChar('<') && !isChar('>') && !startsWith("/>") ) {
                    saveCurrent();
                    go();
                }

                if (!isChar('<')) {
                    _asExpected = true;
                }

                continue;
            }

            String attValue = attName;

            skipWhitespaces();
            if ( isChar('=') ) {
                saveCurrent();
                go();
                attValue = attributeValue();
            }

            if(-1 != clickAttributes.indexOf(attName.toLowerCase(new Locale("en", "US"))))
            	attValue = "return false;";
            if (_asExpected) {
                _currentTagToken.addAttribute(attName, attValue);
            }
        }
    }

    /**
     * Parses a single tag attribute - it is expected to be in one of the forms:
     * 		name=value
     * 		name="value"
     * 		name='value'
     * 		name
     * @throws IOException
     */
    private String attributeValue() throws IOException {
        skipWhitespaces();
        
        if ( isChar('<') || isChar('>') || startsWith("/>") ) {
        	return "";
        }

        boolean isQuoteMode = false;
        boolean isAposMode = false;

        StringBuffer result = new StringBuffer();

        if ( isChar('\'') ) {
            isAposMode = true;
            saveCurrent();
            go();
        } else if ( isChar('\"') ) {
            isQuoteMode = true;
            saveCurrent();
            go();
        }

        while ( !isAllRead() &&
                ( (isAposMode && !isChar('\'')) ||
                  (isQuoteMode && !isChar('\"')) ||
                  (!isAposMode && !isQuoteMode && !isWhitespace() && !isChar('>') && !startsWith("/>"))
                )
              ) {
            result.append( _working[_pos] );
            saveCurrent();
            go();
        }

        if ( isChar('\'') && isAposMode ) {
            saveCurrent();
            go();
        } else if ( isChar('\"') && isQuoteMode ) {
            saveCurrent();
            go();
        }


        return result.toString();
    }

    private void content() throws IOException {
        while ( !isAllRead() ) {
            saveCurrent();
            go();

            if ( _isTextPlain ) {
            	if ( isWhitespace() ) {
            		skipWhitespaces();
            		break;
            	}
            	else if ( isChar('<') ){
                    saveCurrent();
                    go();
                    break;
            	}
            	else if(_working[_pos] == '@') {
            		tagMailStart();
            		return;
            	}
            }
            else {
            	if ( isChar('<') )
            		break;
            }
        }

        addSavedAsContent();
    }

    private void ignore() throws IOException {
        while ( !isAllRead() ) {
        	go();
            if ( isChar('<') ) {
                break;
            }
        }
    }

    private void comment() throws IOException {
    	go(4);
        while ( !isAllRead() && !startsWith("-->") ) {
            saveCurrent();
            go();
        }

        if (startsWith("-->")) {
        	go(3);
        }

        if (_saved.length() > 0) {
            if ( !cleaner.isOmitComments() ) {
                String hyphenRepl = cleaner.getHyphenReplacementInComment();
                String comment = _saved.toString().replaceAll("--", hyphenRepl + hyphenRepl);

        		if ( comment.length() > 0 && comment.charAt(0) == '-' ) {
        			comment = hyphenRepl + comment.substring(1);
        		}
        		int len = comment.length();
        		if ( len > 0 && comment.charAt(len - 1) == '-' ) {
        			comment = comment.substring(0, len - 1) + hyphenRepl;
        		}

        		addToken( new CommentToken(comment) );
        	}
            _saved.delete(0, _saved.length());
        }
    }
    
    private void doctype() throws IOException {
    	go(9);

    	skipWhitespaces();
    	String part1 = identifier();
	    skipWhitespaces();
	    String part2 = identifier();
	    skipWhitespaces();
	    String part3 = attributeValue();
	    skipWhitespaces();
	    String part4 = attributeValue();
	    
	    ignore();
	    
	    DoctypeToken _docType = new DoctypeToken(part1, part2, part3, part4);
	    
	    if ( _docType.isValid() ) {
	    	cleaner.setDoctype(_docType);
	    }
    }

}