package net.java.amateras.xlsbeans.processor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import net.java.amateras.xlsbeans.Utils;
import net.java.amateras.xlsbeans.XLSBeansConfig;
import net.java.amateras.xlsbeans.XLSBeansException;
import net.java.amateras.xlsbeans.annotation.Column;
import net.java.amateras.xlsbeans.xml.AnnotationReader;

/**
 * Provides generic utility methods for {@link HorizontalRecordsProcessor} and 
 * {@link VerticalRecordsProcessor}.
 * 
 * @author Naoki Takezoe
 */
public class RecordsProcessorUtil {
	
	public static void checkColumns(Class<?> recordClass, List<HeaderInfo> headers,
                                    AnnotationReader reader, XLSBeansConfig config) throws Exception {
		
		for(Object property: Utils.getColumnProperties(recordClass.newInstance(), null, reader, config)){
			Column column = null;
			if(property instanceof Method){
				column = reader.getAnnotation(recordClass, (Method) property, Column.class);
			} else if(property instanceof Field){
				column = reader.getAnnotation(recordClass, (Field) property, Column.class);
			}
			
			if(!column.optional()){
				String columnName = column.columnName();
				boolean find = false;
				for(HeaderInfo info: headers){
					if(info.getHeaderLabel().equals(Utils.normalize(columnName, config))){
						find = true;
						break;
					}
				}
				if(!find){
					throw new XLSBeansException("Column '" + columnName + "' doesn't exist.");
				}
			}
		}
	}
	
}
