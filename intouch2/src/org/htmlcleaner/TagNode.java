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
import java.util.*;

/**
 * <p>XML node node tag - it is produced during cleaning process when all start and end
 * tokens are removed and replaced by instances of TagNode.</p>
 *
 * Created by: Vladimir Nikic<br/>
 * Date: November, 2006.
 */
public class TagNode extends TagToken {

    private TagNode parent = null; 
    private Map attributes = new TreeMap();
    private List children = new ArrayList();
    private List itemsToMove = null;
    
    private transient boolean isFormed = false;
    
    public TagNode() {
	}
    
    public TagNode(String name) {
    	super(name.toLowerCase());
    }

	public Map getAttributes() {
		return attributes;
	}

	public List getChildren() {
		return children;
	}

	public TagNode getParent() {
		return parent;
	}

    public void addAttribute(String attName, String attValue) {
        if ( attName != null && !"".equals(attName.trim()) ) {
            attributes.put( attName.toLowerCase(), attValue == null ? "" : attValue );
        }
    }
    
    public void addChild(Object child) {
    	children.add(child);
    	if (child instanceof TagNode) {
            TagNode childTagNode = (TagNode)child;
            childTagNode.parent = this;
        }
    }
    
    public void addChildren(List children) {
    	if (children != null) {
    		Iterator it = children.iterator();
    		while (it.hasNext()) {
    			Object child = it.next();
    			addChild(child);
    		}
    	}
    }
    
    public void addItemForMoving(Object item) {
    	if (itemsToMove == null) {
    		itemsToMove = new ArrayList();
    	}
    	
    	itemsToMove.add(item);
    }
    
	public List getItemsToMove() {
		return itemsToMove;
	}

    public void setItemsToMove(List itemsToMove) {
        this.itemsToMove = itemsToMove;
    }

	public boolean isFormed() {
		return isFormed;
	}

	public void setFormed() {
		this.isFormed = true;
	}
	
    public void serialize(XmlSerializer xmlSerializer) throws IOException {
    	xmlSerializer.serialize(this);
    }
    
    public TagNode makeCopy() {
    	TagNode copy = new TagNode(this.name);
    	copy.attributes = this.attributes;
    	
    	return copy;
    }

}