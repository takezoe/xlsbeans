package com.github.takezoe.xlsbeans.xssfconverter.impl.xssf;

import com.github.takezoe.xlsbeans.xssfconverter.NullWSheetImpl;
import com.github.takezoe.xlsbeans.xssfconverter.WSheet;
import com.github.takezoe.xlsbeans.xssfconverter.WWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Workbook wrapper for HSSF/XSSF.
 *
 * @author Mitsuyoshi Hasegawa
 */
public class XssfWWorkbookImpl implements WWorkbook {

  private Workbook workbook = null;

  public XssfWWorkbookImpl(Workbook workbook) {
    this.workbook = workbook;
  }

  public WSheet getSheet(int i) {
    Sheet sheet = workbook.getSheetAt(i);
    return (sheet != null) ? new XssfWSheetImpl(sheet) : NullWSheetImpl.INSTANCE;
  }

  public WSheet getSheet(String name) {
    Sheet sheet = workbook.getSheet(name);
    return (sheet != null) ? new XssfWSheetImpl(sheet) : NullWSheetImpl.INSTANCE;
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
