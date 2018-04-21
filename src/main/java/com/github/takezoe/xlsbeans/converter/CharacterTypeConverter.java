package com.github.takezoe.xlsbeans.converter;

import com.github.takezoe.xlsbeans.XLSBeansConfig;

public class CharacterTypeConverter implements TypeConverter<Character> {

  public Character convert(String text, XLSBeansConfig config) {
    if (text == null || text.length() == 0) {
      text = " ";
    }
    return new Character(text.charAt(0));
  }
}
