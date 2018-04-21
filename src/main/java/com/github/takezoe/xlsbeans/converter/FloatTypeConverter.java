package com.github.takezoe.xlsbeans.converter;

import com.github.takezoe.xlsbeans.XLSBeansConfig;

public class FloatTypeConverter implements TypeConverter<Float> {

  public Float convert(String text, XLSBeansConfig config) {
    if (text == null || text.length() == 0) {
      text = "0";
    }
    return new Float(text.trim());
  }

}
