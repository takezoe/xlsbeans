package com.github.takezoe.xlsbeans.converter;

import com.github.takezoe.xlsbeans.XLSBeansConfig;

public class StringTypeConverter implements TypeConverter<String> {

    public String convert(String text, XLSBeansConfig config){
        if(text != null && config.isTrimText()){
            return text.trim();
        }
        return text;
    }

}
