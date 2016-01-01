package org.claros.commons.test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.claros.commons.utility.Formatter;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class FormatterTest extends AbstractDependencyInjectionSpringContextTests {
	private Formatter formatter;

	@Override
	protected String[] getConfigLocations() {
		return new String[] {
				"classpath:applicationContext.xml", 
				"classpath:org/claros/commons/common.xml"
		};
	}

	public void testFormatDecimal() {
		BigDecimal num = new BigDecimal(82736238.332332);
		String str = formatter.formatDecimal(num, true);
		assertEquals("82,736,238.33", str);
	}

	public void testFormatInteger() {
		BigDecimal num = new BigDecimal(82736238.332332);
		String str = formatter.formatInteger(num, true);
		assertEquals("82,736,238", str);
	}

	public void testFormatSensitive() {
		BigDecimal num = new BigDecimal(82736238.332332);
		String str = formatter.formatSensitive(num, true, 5);
		assertEquals("82,736,238.33233", str);
	}

	public void testFormatNumberWithPattern() {
		BigDecimal num = new BigDecimal(82736238.332);
		String str = formatter.formatNumberWithPattern(num, "#,##0.00000");
		assertEquals("82,736,238.33200", str);
	}

	public void testFormatDateWithPattern() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 25);
		cal.set(Calendar.MONTH, 10);
		cal.set(Calendar.YEAR, 2001);
		Date dt = cal.getTime();
		String str = formatter.formatDateWithPattern(dt, "dd.MM.yyyy");
		assertEquals("25.11.2001", str);
	}

	public void testGetDouble() {
		BigDecimal num = new BigDecimal(82736238.332);
		double dbl = formatter.getDouble(num);
		assertTrue(dbl == 82736238.332);
	}

	public Formatter getFormatter() {
		return formatter;
	}

	public void setFormatter(Formatter formatter) {
		this.formatter = formatter;
	}
}
