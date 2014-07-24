package net.java.amateras.xlsbeans.xssfconverter.impl.jxl;

import jxl.Sheet;
import jxl.Workbook;
import net.java.amateras.xlsbeans.xssfconverter.NullWSheetImpl;
import net.java.amateras.xlsbeans.xssfconverter.WSheet;
import net.java.amateras.xlsbeans.xssfconverter.WWorkbook;

/**
 * Workbook wrapper for Java Excel API.
 * 
 * @author Mitsuyoshi Hasegawa
 *
 */
public class JxlWWorkbookImpl implements WWorkbook {

	private Workbook workbook = null;

	public JxlWWorkbookImpl(Workbook workbook) {
		this.workbook = workbook;
	}

	public WSheet getSheet(int i) {
		Sheet sheet;
		try {
		    sheet = workbook.getSheet(i);
		} catch (IndexOutOfBoundsException e) {
		    sheet = null;
		}
		return (sheet != null) ? new JxlWSheetImpl(workbook.getSheet(i)) : NullWSheetImpl.INSTANCE;
	}

	public WSheet getSheet(String name) {
		Sheet sheet = workbook.getSheet(name);
		return (sheet != null) ? new JxlWSheetImpl(workbook.getSheet(name)) : NullWSheetImpl.INSTANCE;
	}

	public WSheet[] getSheets() {
		Sheet[] sheets = workbook.getSheets();
		if (sheets == null) {
			return null;
		}
		WSheet[] retSheets = new WSheet[sheets.length];
		for (int i = 0; i < sheets.length; i++) {
			retSheets[i] = new JxlWSheetImpl(sheets[i]);
		}
		return retSheets;
	}
}
