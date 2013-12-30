package net.java.amateras.xlsbeans.xssfconverter.impl.xssf;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import net.java.amateras.xlsbeans.xssfconverter.WCell;
import net.java.amateras.xlsbeans.xssfconverter.WSheet;

/**
 * Sheet wrapper for HSSF/XSSF.
 * 
 * @author Mitsuyoshi Hasegawa
 *
 */
public class XssfWSheetImpl implements WSheet {
	private Sheet sheet = null;

	public XssfWSheetImpl(Sheet sheet) {
		this.sheet = sheet;
	}

	public WCell getCell(int column, int row) {
		Row xssfrow = sheet.getRow(row);
		if (xssfrow == null) {
			return new XssfBlankWCellImpl(column, row);
		}
		Cell cell = xssfrow.getCell(column);
		if (cell == null) {
			return new XssfBlankWCellImpl(column, row);
		}
		return new XssfWCellImpl(xssfrow.getCell(column));
	}

	public WCell[] getColumn(int i) {
		int maxRowIndex = sheet.getLastRowNum();
		WCell[] wcells = new WCell[maxRowIndex + 1];
		for (int j = 0; j <= maxRowIndex; j++) {
			wcells[j] = getCell(i, j);
		}
		return wcells;
	}

	public int getColumns() {
		int minRowIndex = sheet.getFirstRowNum();
		int maxRowIndex = sheet.getLastRowNum();
		int maxColumnsIndex = 0;
		for (int i = minRowIndex; i <= maxRowIndex; i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			int column = row.getLastCellNum();
			if (column > maxColumnsIndex) {
				maxColumnsIndex = column;
			}
		}
		return maxColumnsIndex;
	}

	public String getName() {
		return sheet.getSheetName();
	}

	public int getRows() {
		// JExcel API standard (plus one).
		return sheet.getLastRowNum() + 1;
	}

}
