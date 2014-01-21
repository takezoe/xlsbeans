package net.java.amateras.xlsbeans;

import java.lang.reflect.Method;

import junit.framework.TestCase;
import net.java.amateras.xlsbeans.xml.AnnotationReader;
import net.java.amateras.xlsbeans.xssfconverter.WCell;
import net.java.amateras.xlsbeans.xssfconverter.WSheet;
import net.java.amateras.xlsbeans.xssfconverter.WWorkbook;
import net.java.amateras.xlsbeans.xssfconverter.WorkbookFinder;

public class UtilsTest extends TestCase {

	public void testGetMapColumnMethod() {
		// TODO TestCase
	}

	public void testGetColumnMethod() throws Exception {
		IDE ide = new IDE();
		AnnotationReader reader = new AnnotationReader(null);
		Method[] methods = Utils.getColumnMethods(ide, "IDE", reader, new XLSBeansConfig());
		assertEquals(1, methods.length);
		assertEquals("setName", methods[0].getName());
	}

	public void testGetCellSheetStringInt1() throws Exception {
		WWorkbook w = WorkbookFinder.find(
				UtilsTest.class.getResourceAsStream("example_1.xls"),
				WorkbookFinder.TYPE_JXL);

		WCell cell = Utils.getCell(w.getSheet(0), "Title", 0, new XLSBeansConfig());
		assertEquals("Title", cell.getContents());
	}

	public void testGetCellSheetStringInt1_hssf() throws Exception {
		WWorkbook w = WorkbookFinder.find(
				UtilsTest.class.getResourceAsStream("example_1.xls"),
				WorkbookFinder.TYPE_HSSF);

		WCell cell = Utils.getCell(w.getSheet(0), "Title", 0, new XLSBeansConfig());
		assertEquals("Title", cell.getContents());
	}

	public void testGetCellSheetStringInt1_xssf() throws Exception {
		WWorkbook w = WorkbookFinder.find(
				UtilsTest.class.getResourceAsStream("example_1.xlsx"),
				WorkbookFinder.TYPE_XSSF);

		WCell cell = Utils.getCell(w.getSheet(0), "Title", 0, new XLSBeansConfig());
		assertEquals("Title", cell.getContents());
	}

	public void testGetCellSheetStringInt2() throws Exception {
		WWorkbook w = WorkbookFinder.find(
				UtilsTest.class.getResourceAsStream("example_1.xls"),
				WorkbookFinder.TYPE_JXL);
		try {
			Utils.getCell(w.getSheet(0), "Title", 1, new XLSBeansConfig());
			fail();
		} catch(XLSBeansException ex){
			assertEquals("Cell 'Title' doesn't exist.", ex.getMessage());
		}
	}

	public void testGetCellSheetStringInt2_hssf() throws Exception {
		WWorkbook w = WorkbookFinder.find(
				UtilsTest.class.getResourceAsStream("example_1.xls"),
				WorkbookFinder.TYPE_HSSF);
		try {
			Utils.getCell(w.getSheet(0), "Title", 1, new XLSBeansConfig());
			fail();
		} catch(XLSBeansException ex){
			assertEquals("Cell 'Title' doesn't exist.", ex.getMessage());
		}
	}

	public void testGetCellSheetStringInt2_xssf() throws Exception {
		WWorkbook w = WorkbookFinder.find(
				UtilsTest.class.getResourceAsStream("example_1.xlsx"),
				WorkbookFinder.TYPE_XSSF);
		try {
			Utils.getCell(w.getSheet(0), "Title", 1, new XLSBeansConfig());
			fail();
		} catch(XLSBeansException ex){
			assertEquals("Cell 'Title' doesn't exist.", ex.getMessage());
		}
	}

	public void testInvokeSetter1() throws Exception {
		IDE ide = new IDE();
		Utils.invokeSetter(IDE.class.getMethod("setName", String.class), ide, "Eclipse", new XLSBeansConfig());
		assertEquals("Eclipse", ide.getName());
	}

	public void testInvokeSetter2() throws Exception {
		IDE ide = new IDE();
		try {
			Utils.invokeSetter(IDE.class.getMethod("setVersion", String.class), ide, "1.0", new XLSBeansConfig());
			fail();
		} catch(NoSuchMethodException ex){
			assertEquals(
					"net.java.amateras.xlsbeans.IDE.setVersion(java.lang.String)",
					ex.getMessage());
		}
	}

	/**
	 * added 2008/12/14
	 *
	 * If "throwableWhenNotFound" attribute is "true",
	 * thrown XLSBeansException by non-existed Cell.
	 *
	 * @throws Exception
	 */
	public void testGetCellOptionalTrue() throws Exception {
		WWorkbook w = WorkbookFinder.find(
				UtilsTest.class.getResourceAsStream("example_1.xls"),
				WorkbookFinder.TYPE_JXL);

		WSheet s = w.getSheet("Users2");
		try {
			Utils.getCell(s, "non-existed-label", 0, true, new XLSBeansConfig());
		} catch (XLSBeansException e) {
			assertEquals("Cell 'non-existed-label' doesn't exist.", e.getMessage());
			// SUCCEED
			return;
		}
		fail();
	}

	public void testGetCellOptionalTrue_hssf() throws Exception {
		WWorkbook w = WorkbookFinder.find(
				UtilsTest.class.getResourceAsStream("example_1.xls"),
				WorkbookFinder.TYPE_HSSF);

		WSheet s = w.getSheet("Users2");
		try {
			Utils.getCell(s, "non-existed-label", 0, true, new XLSBeansConfig());
		} catch (XLSBeansException e) {
			assertEquals("Cell 'non-existed-label' doesn't exist.", e.getMessage());
			// SUCCEED
			return;
		}
		fail();
	}

	public void testGetCellOptionalTrue_xssf() throws Exception {
		WWorkbook w = WorkbookFinder.find(
				UtilsTest.class.getResourceAsStream("example_1.xlsx"),
				WorkbookFinder.TYPE_XSSF);

		WSheet s = w.getSheet("Users2");
		try {
			Utils.getCell(s, "non-existed-label", 0, true, new XLSBeansConfig());
		} catch (XLSBeansException e) {
			assertEquals("Cell 'non-existed-label' doesn't exist.", e.getMessage());
			// SUCCEED
			return;
		}
		fail();
	}

	/**
	 * added 2008/12/14
	 *
	 * If "throwableWhenNotFound" attribute is default(=true),
	 * thrown XLSBeansException by non-existed Cell.
	 *
	 * @throws Exception
	 */
	public void testGetCellOptionalDefault() throws Exception {
		WWorkbook w = WorkbookFinder.find(
				UtilsTest.class.getResourceAsStream("example_1.xls"),
				WorkbookFinder.TYPE_JXL);
		WSheet s = w.getSheet("Users2");
		try {
			Utils.getCell(s, "non-existed-label", 0, new XLSBeansConfig());
		} catch (XLSBeansException e) {
			assertEquals("Cell 'non-existed-label' doesn't exist.", e.getMessage());
			// SUCCEED
			return;
		}
		fail();
	}

	public void testGetCellOptionalDefault_hssf() throws Exception {
		WWorkbook w = WorkbookFinder.find(
				UtilsTest.class.getResourceAsStream("example_1.xls"),
				WorkbookFinder.TYPE_HSSF);
		WSheet s = w.getSheet("Users2");
		try {
			Utils.getCell(s, "non-existed-label", 0, new XLSBeansConfig());
		} catch (XLSBeansException e) {
			assertEquals("Cell 'non-existed-label' doesn't exist.", e.getMessage());
			// SUCCEED
			return;
		}
		fail();
	}

	public void testGetCellOptionalDefault_xssf() throws Exception {
		WWorkbook w = WorkbookFinder.find(
				UtilsTest.class.getResourceAsStream("example_1.xlsx"),
				WorkbookFinder.TYPE_XSSF);
		WSheet s = w.getSheet("Users2");
		try {
			Utils.getCell(s, "non-existed-label", 0, new XLSBeansConfig());
		} catch (XLSBeansException e) {
			assertEquals("Cell 'non-existed-label' doesn't exist.", e.getMessage());
			// SUCCEED
			return;
		}
		fail();
	}

	/**
	 * added 2008/12/14
	 *
	 * If "throwableWhenNotFound" attribute is "false",
	 * NEVER thrown XLSBeansException by non-existed Cell.
	 *
	 * @throws Exception
	 */
	public void testGetCellOptionalFalse() throws Exception {
		WWorkbook w = WorkbookFinder.find(
				UtilsTest.class.getResourceAsStream("example_1.xls"),
				WorkbookFinder.TYPE_JXL);
		WSheet s = w.getSheet("Users2");
		WCell c = Utils.getCell(s, "non-existed-label", 0, false, new XLSBeansConfig());
		assertNull(c);
		// SUCCEEDED
	}

	public void testGetCellOptionalFalse_hssf() throws Exception {
		WWorkbook w = WorkbookFinder.find(
				UtilsTest.class.getResourceAsStream("example_1.xls"),
				WorkbookFinder.TYPE_HSSF);
		WSheet s = w.getSheet("Users2");
		WCell c = Utils.getCell(s, "non-existed-label", 0, false, new XLSBeansConfig());
		assertNull(c);
		// SUCCEEDED
	}

	public void testGetCellOptionalFalse_xssf() throws Exception {
		WWorkbook w = WorkbookFinder.find(
				UtilsTest.class.getResourceAsStream("example_1.xlsx"),
				WorkbookFinder.TYPE_XSSF);
		WSheet s = w.getSheet("Users2");
		WCell c = Utils.getCell(s, "non-existed-label", 0, false, new XLSBeansConfig());
		assertNull(c);
		// SUCCEEDED
	}


	public void testGetCellSheetStringCell() {
		// TODO TestCase
	}

	public void testGetCellSheetStringCellBoolean() {
		// TODO TestCase
	}

	public void testGetCellSheetStringCellBooleanBoolean() {
		// TODO TestCase
	}

	public void testGetMethodWithAnnotation() {
		// TODO TestCase
	}

    public void testMatches_normalize(){
        String rawText        = "a bc　\t  de\nfg   h  ";
        String normalizedText = "a bc defg h";

        XLSBeansConfig config = new XLSBeansConfig();
        config.setNormalizeLabelText(false);
        assertFalse(Utils.matches(rawText, normalizedText, config));

        config.setNormalizeLabelText(true);
        assertTrue(Utils.matches(rawText, normalizedText, config));
    }

    public void testMatches_regex(){
        XLSBeansConfig config = new XLSBeansConfig();

        config.setNormalizeLabelText(false);
        config.setRegexLabelText(true);
        assertTrue(Utils.matches("test_data", "test.*", config));
        assertFalse(Utils.matches("test_data", "nottest.*", config));
    }
}
