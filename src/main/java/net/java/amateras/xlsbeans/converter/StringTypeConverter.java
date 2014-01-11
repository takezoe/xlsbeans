package net.java.amateras.xlsbeans.converter;

import net.java.amateras.xlsbeans.XLSBeansConfig;

public class StringTypeConverter implements TypeConverter<String> {

    public String convert(String text, XLSBeansConfig config){
        if(text != null && config.isTrimText()){
            return text.trim();
        }
        return text;
    }

}
