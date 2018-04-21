package com.github.takezoe.xlsbeans.xssfconverter.impl.xssf;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.takezoe.xlsbeans.xssfconverter.WCell;
import com.github.takezoe.xlsbeans.xssfconverter.WCellFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

/**
 * Cell wrapper for HSSF/XSSF.
 * 
 * @author Mitsuyoshi Hasegawa
 * 
 */
public class XssfWCellImpl implements WCell {

	private Cell cell = null;

	public XssfWCellImpl(Cell cell) {
		this.cell = cell;
	}

	public WCellFormat getCellFormat() {
		return new XssfWCellFormatImpl(cell.getCellStyle());
	}

	public int getColumn() {
		return cell.getColumnIndex();
	}

	public String getContents() {
		String contents = null;
		// IllegalStateException occurs , if illegal type defined...
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			contents = "";
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			contents = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_ERROR:
			contents = String.valueOf(cell.getCellFormula());
			break;
		case Cell.CELL_TYPE_FORMULA:
			contents = getCellFormulaContents(cell);
			break;
		case Cell.CELL_TYPE_NUMERIC:
			contents = getNumericContents(cell);
			break;
		case Cell.CELL_TYPE_STRING:
			contents = String.valueOf(cell.getStringCellValue());
			break;
		default:
			contents = "";
			break;
		}
		return contents;
	}

	public int getRow() {
		return cell.getRowIndex();
	}

	private String convertDoubleValue(double d) {
		BigDecimal bd = new BigDecimal(d);
		String convertValue = null;
		try {
			convertValue = String.valueOf(bd.intValueExact());
		} catch (ArithmeticException e) {
			convertValue = String.valueOf(d);
		}
		return convertValue;
	}

	private String getNumericContents(Cell cell) {
		String contents = null;
		if (DateUtil.isCellDateFormatted(cell)) {
			Date date = cell.getDateCellValue();
			// FIXME format string...in JExcel API standard.
			SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd");
			contents = formatter.format(date);
		} else {
			contents = String.valueOf(convertDoubleValue(cell
					.getNumericCellValue()));
		}
		return contents;
	}

	private String getCellFormulaContents(Cell cell) {
		String contents = null;
		switch (cell.getCachedFormulaResultType()) {
		case Cell.CELL_TYPE_NUMERIC:
			contents = getNumericContents(cell);
			break;
		case Cell.CELL_TYPE_STRING:
			contents = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			contents = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_ERROR:
			contents = String.valueOf(cell.getCellFormula());
			break;
		default:
			contents = "";
		}
		return contents;
	}

}
