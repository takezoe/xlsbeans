package com.github.takezoe.xlsbeans.converter;

import com.github.takezoe.xlsbeans.XLSBeansConfig;

public class BooleanTypeConverter implements TypeConverter<Boolean> {

    public Boolean convert(String text, XLSBeansConfig config) {
        if(text == null || text.length() == 0){
            text = "false";
        }
        return new Boolean(text);
    }
}
