package com.github.takezoe.xlsbeans.xssfconverter;

/**
 * Sheet wrapper that indicates no sheet found.
 *
 * @author Seiji Sogabe
 */
public class NullWSheetImpl implements WSheet {

  public static final NullWSheetImpl INSTANCE = new NullWSheetImpl();

  public String getName() {
    return null;
  }

  public WCell[] getColumn(int i) {
    return null;
  }

  public WCell getCell(int column, int row) {
    return null;
  }

  public int getColumns() {
    return 0;
  }

  public int getRows() {
    return 0;
  }

}
