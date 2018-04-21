package com.github.takezoe.xlsbeans.xssfconverter;

/**
 * border line style.
 * (Java Excel API Wrapper.)
 *
 * @author Mitsuyoshi Hasegawa
 */
public class WBorderLineStyle {
  private int value;
  private String string;
  private static WBorderLineStyle styles[] = new WBorderLineStyle[0];
  public static final WBorderLineStyle NONE = new WBorderLineStyle(0, "none");
  public static final WBorderLineStyle THIN = new WBorderLineStyle(1, "thin");
  public static final WBorderLineStyle MEDIUM = new WBorderLineStyle(2,
      "medium");
  public static final WBorderLineStyle DASHED = new WBorderLineStyle(3,
      "dashed");
  public static final WBorderLineStyle DOTTED = new WBorderLineStyle(4,
      "dotted");
  public static final WBorderLineStyle THICK = new WBorderLineStyle(5,
      "thick");
  public static final WBorderLineStyle DOUBLE = new WBorderLineStyle(6,
      "double");
  public static final WBorderLineStyle HAIR = new WBorderLineStyle(7, "hair");
  public static final WBorderLineStyle MEDIUM_DASHED = new WBorderLineStyle(
      8, "medium dashed");
  public static final WBorderLineStyle DASH_DOT = new WBorderLineStyle(9,
      "dash dot");
  public static final WBorderLineStyle MEDIUM_DASH_DOT = new WBorderLineStyle(
      10, "medium dash dot");
  public static final WBorderLineStyle DASH_DOT_DOT = new WBorderLineStyle(
      11, "Dash dot dot");
  public static final WBorderLineStyle MEDIUM_DASH_DOT_DOT = new WBorderLineStyle(
      12, "Medium dash dot dot");
  public static final WBorderLineStyle SLANTED_DASH_DOT = new WBorderLineStyle(
      13, "Slanted dash dot");

  public WBorderLineStyle(int value, String string) {
    this.value = value;
    this.string = string;

    WBorderLineStyle oldstyles[] = styles;
    styles = new WBorderLineStyle[oldstyles.length + 1];
    System.arraycopy(oldstyles, 0, styles, 0, oldstyles.length);
    styles[oldstyles.length] = this;
  }

  public int getValue() {
    return value;
  }

  public String getDescription() {
    return string;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof WBorderLineStyle) {
      WBorderLineStyle target = (WBorderLineStyle) obj;
      if (this.getValue() == target.getValue() && this.getDescription().equals(target.getDescription())) {
        return true;
      }
    }
    return false;
  }
}
