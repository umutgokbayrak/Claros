package org.claros.commons.test;

import org.claros.commons.utility.CommonUtility;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class CommonUtilityTest extends AbstractDependencyInjectionSpringContextTests {
	private CommonUtility commonUtility;	
	
	@Override
	protected String[] getConfigLocations() {
		return new String[] {
				"classpath:applicationContext.xml", 
				"classpath:org/claros/commons/common.xml"
		};
	}
	
	public void testReplaceAllOccurances() {
		String str = "Lorem ipsum dolor sit amet.";
		str = commonUtility.replaceAllOccurances(str, "o", "Ø");
		assertEquals("LØrem ipsum dØlØr sit amet.", str);
	}

	public void testExtendedTrim() {
		String str = "    ...Lorem ipsum dolor sit amet.   . ";
		str = commonUtility.extendedTrim(str, ".");
		assertEquals("Lorem ipsum dolor sit amet", str);
	}

	public void testCheckDecimalFormat() {
		String str = "100.000,45";
		str = (String)commonUtility.checkDecimalFormat(str);
		assertEquals("100000.45", str);
		float val = Float.parseFloat(str);
		assertTrue(((float)100000.45) == val);
	}

	public void testDoCharsetCorrections() {
		String str = "These are the problemmatic Turkish characters: \u00FD, \u00DD, \u00FE, \u00DE, \u00F0, \u00D0";
		str = commonUtility.doCharsetCorrections(str);
		assertEquals("These are the problemmatic Turkish characters: \u0131, \u0130, \u015F, \u015E, \u011F, \u011E", str);
	}

	public void testHtmlSpecialChars() {
		String str = "<script language=\"javascript\">" + 
					 "function cLuscqxBz(iAxBOv){return new String(iAxBOv).replace(/(^\\s*)|(\\s*$)/g,'');}" + 
					 "</script>";
		str = commonUtility.htmlSpecialChars(str);
		assertEquals("&lt;script language=&quot;javascript&quot;&gt;function cLuscqxBz&#040;iAxBOv&#041;{return new String&#040;iAxBOv&#041;.replace&#040;/&#040;^\\s*&#041;|&#040;\\s*$&#041;/g,&#039;&#039;&#041;&#059;}&lt;/script&gt;", str);
	}

	public void testRegExpSpecialChars() {
		String str = "<script language=\"javascript\">" + 
					 "function cLuscqxBz(iAxBOv){return new String(iAxBOv).replace(/(^\\s*)|(\\s*$)/g,'');}" + 
					 "</script>";
		str = commonUtility.regExpSpecialChars(str);
		assertEquals("<script language=\"javascript\">function cLuscqxBz\\(iAxBOv\\)\\{return new String\\(iAxBOv\\)\\.replace\\(/\\(\\^\\\\s\\*\\)|\\(\\\\s\\*\\$\\)/g,''\\);\\}</script>", str);
	}

	public void testXmlSpecialChars() {
		String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + 
		"<beans xmlns=\"http://www.springframework.org/schema/beans\"" + 
		"	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" + 
		"	xsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.0.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-2.0.xsd\"" + 
		"	xmlns:tx=\"http://www.springframework.org/schema/tx\">" + 
		"	<bean id=\"commonUtility\" class=\"org.claros.intouch.common.utility.CommonUtility\">" + 
		"	</bean>" + 
		"</beans>";

		str = commonUtility.xmlSpecialChars(str);

		assertEquals("&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;&lt;beans xmlns=&quot;http://www.springframework.org/schema/beans&quot;	xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot;	xsi:schemaLocation=&quot;http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.0.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-2.0.xsd&quot;	xmlns:tx=&quot;http://www.springframework.org/schema/tx&quot;&gt;	&lt;bean id=&quot;commonUtility&quot; class=&quot;org.claros.intouch.common.utility.CommonUtility&quot;&gt;	&lt;/bean&gt;&lt;/beans&gt;", str);
	}

	public void testConvertTRCharsToHtmlSafe() {
		String str = "Ay\u0131 yavrusunu a\u015Fk\u0131 me\u015Fk içinde ba\u011Fr\u0131na bast\u0131";
		str = commonUtility.convertTRCharsToHtmlSafe(str);
		
		assertEquals("Ay&#305; yavrusunu a&#351;k&#305; me&#351;k i&#231;inde ba&#287;r&#305;na bast&#305;", str);
	}

	public void testConvertHtmlSafeToTRChars() {
		String str = "Ay&#305; yavrusunu a&#351;k&#305; me&#351;k i&#231;inde ba&#287;r&#305;na bast&#305;";
		
		str = commonUtility.convertHtmlSafeToTRChars(str);
		
		assertEquals("Ay\u0131 yavrusunu a\u015Fk\u0131 me\u015Fk içinde ba\u011Fr\u0131na bast\u0131", str);
	}

	public void testConvertTRCharsToENChars() {
		String str = "Ay\u0131 yavrusunu a\u015Fk\u0131 me\u015Fk içinde ba\u011Fr\u0131na bast\u0131";
		str = commonUtility.convertTRCharsToENChars(str);
		assertEquals("Ayi yavrusunu aski mesk icinde bagrina basti", str);
	}

	public void testConvertTRCharsToENLowerCaseChars() {
		String str = "Ay\u0131 yavrusunu a\u015Fk\u0131 me\u015Fk içinde ba\u011Fr\u0131na bast\u0131";
		str = commonUtility.convertTRCharsToENLowerCaseChars(str);
		assertEquals("Ayi yavrusunu aski mesk icinde bagrina basti", str);
	}

	public CommonUtility getCommonUtility() {
		return commonUtility;
	}

	public void setCommonUtility(CommonUtility commonUtility) {
		this.commonUtility = commonUtility;
	}

}
