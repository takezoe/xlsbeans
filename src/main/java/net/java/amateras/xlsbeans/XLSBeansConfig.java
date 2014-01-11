package net.java.amateras.xlsbeans;

import net.java.amateras.xlsbeans.converter.*;

import java.util.HashMap;
import java.util.Map;

public class XLSBeansConfig {

    private boolean ignoreSheetNotFound = false;

    private boolean normalizeLabelText = false;

    private boolean trimText = false;

    private Map<Class<?>, TypeConverter> converters = new HashMap<Class<?>, TypeConverter>();

    public XLSBeansConfig(){
        converters.put(Integer.class, new IntegerTypeConverter());
        converters.put(Integer.TYPE, new IntegerTypeConverter());
        converters.put(Long.class, new LongTypeConverter());
        converters.put(Long.TYPE, new LongTypeConverter());
        converters.put(Double.class, new DoubleTypeConverter());
        converters.put(Double.TYPE, new DoubleTypeConverter());
        converters.put(Float.class, new FloatTypeConverter());
        converters.put(Float.TYPE, new FloatTypeConverter());
        converters.put(Boolean.class, new BooleanTypeConverter());
        converters.put(Boolean.TYPE, new BooleanTypeConverter());
        converters.put(Character.class, new CharacterTypeConverter());
        converters.put(Character.TYPE, new CharacterTypeConverter());
        converters.put(String.class, new StringTypeConverter());
    }

    public boolean isIgnoreSheetNotFound() {
        return ignoreSheetNotFound;
    }

    public void setIgnoreSheetNotFound(boolean ignoreSheetNotFound) {
        this.ignoreSheetNotFound = ignoreSheetNotFound;
    }

    public boolean isNormalizeLabelText() {
        return normalizeLabelText;
    }

    public void setNormalizeLabelText(boolean normalizeLabelText) {
        this.normalizeLabelText = normalizeLabelText;
    }

    public boolean isTrimText() {
        return trimText;
    }

    public void setTrimText(boolean trimText) {
        this.trimText = trimText;
    }

    public <T> void addTypeConverter(Class<T> clazz, TypeConverter<T> converter){
        converters.put(clazz, converter);
    }

    public <T> TypeConverter<T> getConverter(Class<T> clazz){
        TypeConverter<T> converter = converters.get(clazz);
        if(converter != null){
            return converter;
        }
        return converters.get(String.class);
    }

}
