package net.java.amateras.xlsbeans.converter;

import net.java.amateras.xlsbeans.XLSBeansConfig;

public class FloatTypeConverter implements TypeConverter<Float> {

    public Float convert(String text, XLSBeansConfig config){
        if(text == null || text.length() == 0){
            text = "0";
        }
        return new Float(text.trim());
    }

}
