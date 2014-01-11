package net.java.amateras.xlsbeans.converter;

import net.java.amateras.xlsbeans.XLSBeansConfig;

public class IntegerTypeConverter implements TypeConverter<Integer> {

    public Integer convert(String text, XLSBeansConfig config){
        if(text == null || text.length() == 0){
            text = "0";
        }
        return new Integer(text.trim());
    }

}
