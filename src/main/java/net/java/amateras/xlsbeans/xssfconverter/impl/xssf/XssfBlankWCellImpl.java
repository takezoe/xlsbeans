package net.java.amateras.xlsbeans.xssfconverter.impl.xssf;

import net.java.amateras.xlsbeans.xssfconverter.WCell;
import net.java.amateras.xlsbeans.xssfconverter.WCellFormat;

/**
 * blank cell implementation.
 * (HSSF/XSSF return null, if Excel cell doesn't exist.)
 * 
 * @author Mitsuyoshi Hasegawa
 *
 */
public class XssfBlankWCellImpl implements WCell {

	private int column = 0;
	private int row = 0;

	public XssfBlankWCellImpl(int column, int row) {
		this.column = column;
		this.row = row;
	}


	public WCellFormat getCellFormat() {
		return new XssfWCellFormatImpl(null);
	}

	public int getColumn() {
		return column;
	}

	public String getContents() {
		return "";
	}

	public int getRow() {
		return row;
	}

}
