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

import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * <p>Compact XML serializer - creates resulting XML by stripping whitespaces.</p>
 *
 * Created by: Vladimir Nikic<br/>
 * Date: November, 2006.
 */
public class CompactXmlSerializer extends XmlSerializer {

	protected CompactXmlSerializer(Writer writer, HtmlCleaner htmlCleaner) {
		super(writer, htmlCleaner);
	}

    private void serialize(List nodes, TagNode tagNode) throws IOException {
        if ( nodes != null && !nodes.isEmpty() ) {
            ListIterator childrenIt = nodes.listIterator();
            while ( childrenIt.hasNext() ) {
                Object item = childrenIt.next();
                if (item != null) {
                	if (item instanceof List) {
                    	serialize((List)item, tagNode);
                    } else if ( item instanceof ContentToken ) {
                    	ContentToken contentToken = (ContentToken) item;
                    	String content = contentToken.getContent().toString();
                    	boolean isFirstChSpace = Character.isWhitespace(content.charAt(0));
                    	boolean isLastChSpace = Character.isWhitespace(content.charAt(content.length()-1));
                    	content = content.trim();
                    	if(isFirstChSpace) content = " " + content;
                    	if(isLastChSpace) content += " ";
                    	//if ( !dontEscape(tagNode) ) {
                    	//	content = escapeXml(content).toString();
                    	//} else {
                    		content = content.replaceAll("]]>", "]]&amp;");
                    	//}
                        writer.write(" " + content);

                        if (childrenIt.hasNext()) {
                            if ( isEmptyContentToken(childrenIt.next()) ) {
                                writer.write("\n");
                            }
                            childrenIt.previous();
                        }
                    } else if (item instanceof CommentToken) {
                    	String content = ((CommentToken) item).getCommentedContent().toString().trim();
                    	writer.write(content);
                    } else {
                    	((BaseToken)item).serialize(this);
                    }
                }
            }
        }
    }

    /**
     * Checks if specified object is empty content token - it's string
     * representation is made of spaces or is empty string. 
     * @param token
     */
    private boolean isEmptyContentToken(Object token) {
        if (token instanceof ContentToken) {
            ContentToken contentToken = (ContentToken) token;
            if ( !"".equals(contentToken.getContent().toString().trim()) ) {
                return true;
            }
        }

        return false;
    }

    protected void serialize(TagNode tagNode) throws IOException {
        serializeOpenTag(tagNode, false);

        List tagChildren = tagNode.getChildren();
        if ( !tagChildren.isEmpty() ) {
            serialize(tagChildren, tagNode);
            serializeEndTag(tagNode, false);
        }
	}

}