package net.java.amateras.xlsbeans.xssfconverter.impl.xssf;

import org.apache.poi.ss.usermodel.CellStyle;

import net.java.amateras.xlsbeans.xssfconverter.WBorder;
import net.java.amateras.xlsbeans.xssfconverter.WBorderLineStyle;
import net.java.amateras.xlsbeans.xssfconverter.WCellFormat;

/**
 * CellFormat wrapper for HSSF/XSSF.
 * 
 * @author Mitsuyoshi Hasegawa
 *
 */
public class XssfWCellFormatImpl implements WCellFormat {
	private CellStyle cellStyle = null;

	public XssfWCellFormatImpl(CellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}

	public WBorderLineStyle getBorder(WBorder border) {
		if (cellStyle == null) {
			return WBorderLineStyle.NONE;
		}

		WBorderLineStyle style = null;
		if (WBorder.BOTTOM.equals(border)) {
			style = XssfBorderLineStyleResolver.resolve(cellStyle.getBorderBottom());
		} else if (WBorder.LEFT.equals(border)) {
			style = XssfBorderLineStyleResolver.resolve(cellStyle.getBorderLeft());
		} else if (WBorder.RIGHT.equals(border)) {
			style = XssfBorderLineStyleResolver.resolve(cellStyle.getBorderRight());
		} else if (WBorder.TOP.equals(border)) {
			style = XssfBorderLineStyleResolver.resolve(cellStyle.getBorderTop());
		} else {
			// FIXME Correct strictly? XLSBeans don't use this style, except TOP, LEFT.
			style = WBorderLineStyle.NONE;
		}
		return style;
	}

}
