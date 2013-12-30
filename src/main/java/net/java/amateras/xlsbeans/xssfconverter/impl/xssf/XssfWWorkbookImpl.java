package net.java.amateras.xlsbeans.xssfconverter.impl.xssf;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import net.java.amateras.xlsbeans.xssfconverter.WSheet;
import net.java.amateras.xlsbeans.xssfconverter.WWorkbook;

/**
 * Workbook wrapper for HSSF/XSSF.
 * 
 * @author Mitsuyoshi Hasegawa
 *
 */
public class XssfWWorkbookImpl implements WWorkbook {

	private Workbook workbook = null;

	public XssfWWorkbookImpl(Workbook workbook) {
		this.workbook = workbook;
	}

	public WSheet getSheet(int i) {
		Sheet sheet = workbook.getSheetAt(i);
		return new XssfWSheetImpl(sheet);
	}

	public WSheet getSheet(String name) {
		Sheet sheet = workbook.getSheet(name);
		return new XssfWSheetImpl(sheet);
	}

	public WSheet[] getSheets() {
		int sheetNum = workbook.getNumberOfSheets();
		WSheet[] retSheets = new WSheet[sheetNum];
		for (int i = 0; i < sheetNum; i++) {
			retSheets[i] = new XssfWSheetImpl(workbook.getSheetAt(i));
		}
		return retSheets;
	}

}
