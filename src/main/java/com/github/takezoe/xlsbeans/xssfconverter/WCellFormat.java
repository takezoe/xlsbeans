package com.github.takezoe.xlsbeans.xssfconverter;

/**
 * Cell Format Wrapper.
 * (Java Excel API Wrapper Interface.)
 *
 * @author Mitsuyoshi Hasegawa
 */
public interface WCellFormat {

  /**
   * get BorderLineStyle wrapper-object.
   *
   * @param border border wrapper-object
   * @return BorderLineStyle wrapper-object
   */
  WBorderLineStyle getBorder(WBorder border);
}
