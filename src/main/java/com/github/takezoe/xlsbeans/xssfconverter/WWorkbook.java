package com.github.takezoe.xlsbeans.xssfconverter;

/**
 * Workbook wrapper.
 * (Java Excel API Wrapper Interface.)
 *
 * @author Mitsuyoshi Hasegawa
 */
public interface WWorkbook {
  WSheet getSheet(int i);

  WSheet getSheet(String name);

  WSheet[] getSheets();
}
