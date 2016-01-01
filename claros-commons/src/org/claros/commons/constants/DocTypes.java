package org.claros.commons.constants;

/**
 * 
 * @author Umut Gokbayrak
 * 
 */
public class DocTypes {
	
	/**
	 * <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
	 * 
	 * This declares the document to be HTML 4.01 Strict. HTML 4.01 Strict is a trimmed down version 
	 * of HTML 4.01 that emphasizes structure over presentation. Deprecated elements and attributes 
	 * (including most presentational attributes), frames, and link targets are not allowed in HTML 
	 * 4 Strict. By writing to HTML 4 Strict, authors can achieve accessible, structurally rich documents 
	 * that easily adapt to style sheets and different browsing situations. However, HTML 4 Strict 
	 * documents may look bland on very old browsers that lack support for style sheets.
	 * 
	 * Newer browsers such as Internet Explorer 5 for Mac, Netscape 6, and Mozilla use a 
	 * standards-compliant rendering for HTML 4 Strict documents. These browsers use a "quirks" mode 
	 * for most other document types to emulate rendering bugs in older browsers.
	 */
	public final static int html4Strict = 1;

	/**
	 * <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	 * 
	 * This declares the document to be HTML 4.01 Transitional. HTML 4 Transitional includes all elements 
	 * and attributes of HTML 4 Strict but adds presentational attributes, deprecated elements, and link 
	 * targets.
	 * 
	 * Newer browsers such as Internet Explorer 5 for Mac, Netscape 6, and Mozilla use a 
	 * standards-compliant rendering for HTML 4.01 Transitional documents that include the URI of the 
	 * DTD in the DOCTYPE. These browsers use a "quirks" mode to emulate rendering bugs in older 
	 * browsers if the URI is omitted: 
	 * 
	 * <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
	 * 
	 */
	public final static int html4Loose = 2;
	
	
	/**
	 * <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
	 * 
	 * This declares the document to be HTML 4.01 Frameset. HTML 4 Frameset is a variant of HTML 4 Transitional 
	 * for documents that use frames.
	 */
	public final static int html4Frameset = 3;
	
	/**
	 * <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
	 * 
	 * This declares the document to be XHTML 1.0 Strict. XHTML 1.0 Strict is an XML version of HTML 4 Strict.
	 */
	public final static int xhtml1Strict = 4;

	/**
	 * <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	 * 
	 * This declares the document to be XHTML 1.0 Transitional. XHTML 1.0 Transitional is an XML version of 
	 * HTML 4 Transitional.
	 */
	public final static int xhtml1Transitional = 5;

	/**
	 * <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
	 * 
	 * This declares the document to be XHTML 1.0 Frameset. XHTML 1.0 Frameset is an XML version of HTML 4 Frameset.
	 */
	public final static int xhtml1Frameset = 6;
	
	
	/**
	 * <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
	 * 
	 * This declares the document to be HTML 3.2. HTML 3.2 is well supported by most browsers in use. 
	 * However, HTML 3.2 has limited support for style sheets and no support for HTML 4 features such as 
	 * frames and internationalization.
	 */
	public final static int html32 = 7;
	
	/**
	 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML//EN">
	 * 
	 * This declares the document to be HTML 2.0. HTML 2.0 is widely supported by browsers but lacks 
	 * support for tables, frames, and internationalization, as well as many commonly used presentational elements and 
	 * attributes.
	 */
	public final static int html20 = 8;
	
	public static String getDocType(final int docType) {
		String str = "";
		switch (docType) {
		case html20:
			str = "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML//EN\">";
			break;
		case html32:
			str = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">";
			break;
		case html4Frameset:
			str = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Frameset//EN\" \"http://www.w3.org/TR/html4/frameset.dtd\">";
			break;
		case html4Loose:
			str = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">";
			break;
		case html4Strict:
			str = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">";
			break;
		case xhtml1Frameset:
			str = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Frameset//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd\">";
			break;
		case xhtml1Strict:
			str = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">";
			break;
		case xhtml1Transitional:
			str = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";
			break;
		default:
			str = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">";
			break;
		}
		return str;
	}
}
