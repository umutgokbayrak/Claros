package org.htmlcleaner;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.List;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>DOM serializer - creates xml DOM.</p>
 *
 * Created by: Vladimir Nikic<br/>
 * Date: April, 2007.
 */
public class DomSerializer {

    public Document createDOM(TagNode rootNode) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        Document document = factory.newDocumentBuilder().newDocument();
        Element rootElement = document.createElement(rootNode.getName());
        document.appendChild(rootElement);

        createSubnodes(document, rootElement, rootNode.getChildren());

        return document;
    }

    private void createSubnodes(Document document, Element element, List tagChildren) {
        if (tagChildren != null) {
            Iterator it = tagChildren.iterator();
            while (it.hasNext()) {
                Object item = it.next();
                if (item instanceof CommentToken) {
                    CommentToken commentToken = (CommentToken) item;
                    Comment comment = document.createComment( commentToken.getContent() );
                    element.appendChild(comment);
                } else if (item instanceof ContentToken) {
                    ContentToken contentToken = (ContentToken) item;
                    Text text = document.createTextNode( contentToken.getContent() );
                    element.appendChild(text);
                } else if (item instanceof TagNode) {
                    TagNode subTagNode = (TagNode) item;
                    Element subelement = document.createElement( subTagNode.getName() );
                    Map attributes =  subTagNode.getAttributes();
                    Iterator entryIterator = attributes.entrySet().iterator();
                    while (entryIterator.hasNext()) {
                        Map.Entry entry = (Map.Entry) entryIterator.next();
                        String attrName = (String) entry.getKey();
                        String attrValue = (String) entry.getValue();
                        subelement.setAttribute(attrName, attrValue);
                    }

                    // recursively create subnodes
                    createSubnodes(document, subelement, subTagNode.getChildren());

                    element.appendChild(subelement);
                } else if (item instanceof List) {
                    List sublist = (List) item;
                    createSubnodes(document, element, sublist);
                }
            }
        }
    }

}