package com.github.takezoe.xlsbeans.processor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import com.github.takezoe.xlsbeans.Utils;
import com.github.takezoe.xlsbeans.XLSBeansConfig;
import com.github.takezoe.xlsbeans.XLSBeansException;
import com.github.takezoe.xlsbeans.annotation.Column;
import com.github.takezoe.xlsbeans.xml.AnnotationReader;

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
                    if(Utils.matches(info.getHeaderLabel(), columnName, config)){
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
