package org.claros.commons;

import org.claros.commons.test.CommonUtilityTest;
import org.claros.commons.test.FormatterTest;

import junit.framework.Test;
import junit.framework.TestSuite;

public class CommonTestSuite {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.claros.intouch.common");
		//$JUnit-BEGIN$
		suite.addTestSuite(FormatterTest.class);
		suite.addTestSuite(CommonUtilityTest.class);
		//$JUnit-END$
		return suite;
	}

}
