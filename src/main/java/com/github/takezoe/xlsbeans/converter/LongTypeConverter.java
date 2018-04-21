package com.github.takezoe.xlsbeans.converter;

import com.github.takezoe.xlsbeans.XLSBeansConfig;

public class LongTypeConverter implements TypeConverter<Long> {

    public Long convert(String text, XLSBeansConfig config){
        if(text == null || text.length() == 0){
            text = "0";
        }
        return new Long(text.trim());
    }

}
