package com.github.takezoe.xlsbeans.xssfconverter.impl.xssf;

import com.github.takezoe.xlsbeans.xssfconverter.WBorderLineStyle;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * border line resolver.
 * (Java Excel API - HSSF/XSSF)
 * 
 * @author Mitsuyoshi Hasegawa
 *
 */
public class XssfBorderLineStyleResolver {

	public static WBorderLineStyle resolve(short cellStyle) {
		WBorderLineStyle style = null;
		switch(cellStyle) {
			case CellStyle.BORDER_NONE: style = WBorderLineStyle.NONE; break;
			case CellStyle.BORDER_THIN: style = WBorderLineStyle.THIN; break;
			case CellStyle.BORDER_MEDIUM: style = WBorderLineStyle.MEDIUM; break;
			case CellStyle.BORDER_DASHED: style = WBorderLineStyle.DASHED; break;
			case CellStyle.BORDER_DOTTED: style = WBorderLineStyle.DOTTED; break;
			case CellStyle.BORDER_THICK: style = WBorderLineStyle.THICK; break;
			case CellStyle.BORDER_DOUBLE: style = WBorderLineStyle.DOUBLE; break;
			case CellStyle.BORDER_HAIR: style = WBorderLineStyle.HAIR; break;
			case CellStyle.BORDER_MEDIUM_DASHED: style = WBorderLineStyle.MEDIUM_DASHED; break;
			case CellStyle.BORDER_DASH_DOT: style = WBorderLineStyle.DASH_DOT; break;
			case CellStyle.BORDER_MEDIUM_DASH_DOT: style = WBorderLineStyle.MEDIUM_DASH_DOT; break;
			case CellStyle.BORDER_MEDIUM_DASH_DOT_DOT: style = WBorderLineStyle.MEDIUM_DASH_DOT_DOT; break;
			case CellStyle.BORDER_SLANTED_DASH_DOT: style = WBorderLineStyle.SLANTED_DASH_DOT; break;
			default:  style = WBorderLineStyle.NONE; break;
		}
		return style;
	}
}
