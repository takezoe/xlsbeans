package net.java.amateras.xlsbeans.converter;

import net.java.amateras.xlsbeans.XLSBeansConfig;

public class DoubleTypeConverter implements TypeConverter<Double> {

    public Double convert(String text, XLSBeansConfig config){
        if(text == null || text.length() == 0){
            text = "0";
        }
        return new Double(text.trim());
    }

}
