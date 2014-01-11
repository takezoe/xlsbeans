package net.java.amateras.xlsbeans.converter;

import net.java.amateras.xlsbeans.XLSBeansConfig;

/**
 *
 * @param <T> the property type
 */
public interface TypeConverter<T> {

    /**
     * Convert the cell value for the Bean property.
     *
     * @param text the cell value
     * @param config the XLSBeans configuration
     * @return the converted value
     */
    public T convert(String text, XLSBeansConfig config);

}
