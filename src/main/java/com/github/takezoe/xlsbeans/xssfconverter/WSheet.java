package com.github.takezoe.xlsbeans.xssfconverter;

/**
 * Sheet wrapper.
 * (Java Excel API Wrapper Interface.)
 *
 * @author Mitsuyoshi Hasegawa.
 */
public interface WSheet {
  String getName();

  int getColumns();

  WCell[] getColumn(int i);

  WCell getCell(int column, int row);

  int getRows();
}
