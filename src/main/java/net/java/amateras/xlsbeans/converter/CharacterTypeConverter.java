package net.java.amateras.xlsbeans.converter;

import net.java.amateras.xlsbeans.XLSBeansConfig;

public class CharacterTypeConverter implements TypeConverter<Character> {

    public Character convert(String text, XLSBeansConfig config) {
        if(text == null || text.length() == 0){
            text = " ";
        }
        return new Character(text.charAt(0));
    }
}
