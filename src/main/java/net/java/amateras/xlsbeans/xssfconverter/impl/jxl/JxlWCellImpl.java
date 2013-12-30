package net.java.amateras.xlsbeans.xssfconverter.impl.jxl;

import jxl.Cell;
import jxl.format.CellFormat;
import net.java.amateras.xlsbeans.xssfconverter.WCell;
import net.java.amateras.xlsbeans.xssfconverter.WCellFormat;

/**
 * Cell wrapper for Java Excel API.
 * @author Mitsuyoshi Hasegawa
 *
 */
public class JxlWCellImpl implements WCell {
	private Cell cell = null;

	public JxlWCellImpl(Cell cell) {
		this.cell = cell;
	}

	public WCellFormat getCellFormat() {
		CellFormat org = cell.getCellFormat();
		return new JxlWCellFormatImpl(org);
	}

	public int getColumn() {
		return cell.getColumn();
	}

	public String getContents() {
		return cell.getContents();
	}

	public int getRow() {
		return cell.getRow();
	}

}
