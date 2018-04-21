package com.github.takezoe.xlsbeans.xssfconverter.impl.jxl;

import com.github.takezoe.xlsbeans.xssfconverter.WCell;
import com.github.takezoe.xlsbeans.xssfconverter.WSheet;
import jxl.Cell;
import jxl.Sheet;

/**
 * Sheet wrapper for Java Excel API.
 * 
 * @author Mitsuyoshi Hasegawa
 *
 */
public class JxlWSheetImpl implements WSheet {

	private Sheet sheet = null;

	public JxlWSheetImpl(Sheet sheet) {
		this.sheet = sheet;
	}

	public WCell getCell(int column, int row) {
		return new JxlWCellImpl(sheet.getCell(column, row));
	}

	public WCell[] getColumn(int i) {
		Cell[] cells = sheet.getColumn(i);
		if (cells == null) {
			return null;
		}
		WCell[] retCells = new WCell[cells.length];
		for (int j = 0 ; j < cells.length; j++) {
			retCells[j] = new JxlWCellImpl(cells[j]);
		}
		return retCells;
	}

	public int getColumns() {
		return sheet.getColumns();
	}

	public String getName() {
		return sheet.getName();
	}

	public int getRows() {
		return sheet.getRows();
	}
}
