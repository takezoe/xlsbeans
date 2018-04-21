package com.github.takezoe.xlsbeans;

import java.awt.Point;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.takezoe.xlsbeans.annotation.Column;
import com.github.takezoe.xlsbeans.annotation.MapColumns;
import com.github.takezoe.xlsbeans.xml.AnnotationReader;
import com.github.takezoe.xlsbeans.xssfconverter.WCell;
import com.github.takezoe.xlsbeans.xssfconverter.WSheet;

/**
 *
 * @author Naoki Takezoe
 * @author Mitsuyoshi Hasegawa
 */
public class Utils {

	protected static Method[] getMapColumnMethods(Object obj, AnnotationReader reader) throws Exception {

		List<Method> result = new ArrayList<Method>();
		Method[] methods = obj.getClass().getMethods();
		for(int i = 0; i < methods.length; i++){
			if(methods[i].getName().startsWith("set") && methods[i].getParameterTypes().length == 1){
				Annotation[] anns = reader.getAnnotations(obj.getClass(), methods[i]);

				for(int j = 0; j < anns.length; j++){
					if(anns[j] instanceof MapColumns){
						result.add(methods[i]);
					}
				}
			}
		}
		return result.toArray(new Method[result.size()]);
	}

	protected static Field[] getMapColumnFields(Object obj, AnnotationReader reader) throws Exception {

		List<Field> result = new ArrayList<Field>();
		Field[] fields = obj.getClass().getFields();
		for(int i = 0; i < fields.length; i++){
			Annotation[] anns = reader.getAnnotations(obj.getClass(), fields[i]);

			for(int j = 0; j < anns.length; j++){
				if(anns[j] instanceof MapColumns){
					result.add(fields[i]);
				}
			}
		}
		return result.toArray(new Field[result.size()]);
	}

	public static List<Object> getMapColumnProperties(Object obj, AnnotationReader reader) throws Exception {

		List<Object> list = new ArrayList<Object>();

		for(Method method: getMapColumnMethods(obj, reader)){
			list.add(method);
		}

		for(Field field: getMapColumnFields(obj, reader)){
			list.add(field);
		}

		return list;

	}

	protected static Method[] getColumnMethods(Object obj, String name, AnnotationReader reader, XLSBeansConfig config) throws Exception {
		List<Method> result = new ArrayList<Method>();
		Method[] methods = obj.getClass().getMethods();
		for(int i = 0; i < methods.length;i ++){
			if(methods[i].getName().startsWith("set") && methods[i].getParameterTypes().length == 1){
				Annotation[] anns = reader.getAnnotations(obj.getClass(), methods[i]);
				for(int j = 0; j < anns.length; j++){
					if(anns[j] instanceof Column){
						Column column = (Column) anns[j];
						String columnName = column.columnName();
						if(name == null){
							result.add(methods[i]);
						} else if(Utils.matches(columnName, name, config)){
							result.add(methods[i]);
						}
					}
				}
			}
		}
		return result.toArray(new Method[result.size()]);
	}

	protected static Field[] getColumnFields(Object obj, String name, AnnotationReader reader, XLSBeansConfig config) throws Exception {
		List<Field> result = new ArrayList<Field>();
		Field[] fields = obj.getClass().getFields();
		for(int i = 0; i < fields.length; i++){
			Annotation[] anns = reader.getAnnotations(obj.getClass(), fields[i]);
			for(int j = 0; j < anns.length; j++){
				if(anns[j] instanceof Column){
					Column column = (Column)anns[j];
					String columnName = column.columnName();
					if(name == null){
						result.add(fields[i]);
					} else if(Utils.matches(columnName, name, config)){
						result.add(fields[i]);
					}
				}
			}
		}
		return result.toArray(new Field[result.size()]);
	}

	/**
	 * Returns a list of column methods and fields of a given object.
	 *
	 * @param obj target object
	 * @param name property name
	 * @param reader annotation reader
	 * @return list of column methods and fields
	 * @throws Exception
	 */
	public static List<Object> getColumnProperties(Object obj, String name, AnnotationReader reader, XLSBeansConfig config) throws Exception {

		List<Object> list = new ArrayList<Object>();

		for(Method method: getColumnMethods(obj, name, reader, config)){
			list.add(method);
		}

		for(Field field: getColumnFields(obj, name, reader, config)){
			list.add(field);
		}

		return list;
	}

	public static WCell getCell(WSheet wSheet, String label, int from, XLSBeansConfig config) throws XLSBeansException {
		// If target Cell is not found, throws XLSBeansException.
		return Utils.getCell(wSheet, label, from, true, config);
	}

	public static WCell getCell(WSheet wSheet, String label, int from, boolean throwableWhenNotFound, XLSBeansConfig config) throws XLSBeansException {
		int rows = wSheet.getColumns();
		for(int i = 0;i < rows; i++){
			WCell[] columns = wSheet.getColumn(i);
			for(int j = from; j < columns.length; j++){
                if(Utils.matches(columns[j].getContents(), label, config)){
                    return columns[j];
                }
			}
		}
		if (throwableWhenNotFound) {
			throw new XLSBeansException("Cell '" + label + "' doesn't exist.");
		}
		return null;
	}

	/**
	 * Invokes the setter method using reflection.
	 *
	 * @param setter the setter method
	 * @param obj the target object
	 * @param value the value which is String, primitive types or wrapper types
	 * @throws Exception
	 */
	public static void invokeSetter(Method setter, Object obj, String value, XLSBeansConfig config) throws Exception {
		Class<?>[] types = setter.getParameterTypes();
		if(types.length != 1){
			return;
		}
		Object valueObject = config.getConverter(types[0]).convert(value, config);
		if(valueObject != null){
			setter.invoke(obj, new Object[]{ valueObject });
		}
	}

	/**
	 * Set field value using reflection.
	 *
	 * @param field the field
	 * @param obj the target object
	 * @param value the value which is String, primitive types or wrapper types
	 * @throws Exception
	 */
	public static void setField(Field field, Object obj, String value, XLSBeansConfig config) throws Exception {
		Class<?> type = field.getType();
		Object valueObject = config.getConverter(type).convert(value, config);
		if(valueObject != null){
			field.set(obj, valueObject);
		}
	}

	/**
     * Return cell object by using first argument sheet.
     * This cell will be found by label name in Excel sheet.
     *
     * NOTICE: When the cell object is specified for the third argument,
     * a lower right cell is scanned from the cell.
	 *
	 * @param wSheet the sheet object.
	 * @param label the target cell label.
     * @param after A lower right cell is scanned from this cell object.
     *
     * @return Target JExcel Api cell object.
     * @throws XLSBeansException This occures when the cell is not found.
	 */
	public static WCell getCell(WSheet wSheet, String label, WCell after, XLSBeansConfig config) throws XLSBeansException {
		return getCell(wSheet, label, after, false, config);
	}

    /**
     * Return cell object by using first argument sheet.
     * This cell will be found by label name in Excel sheet.
     *
     * NOTICE: When the cell object is specified for the third argument,
     * a lower right cell is scanned from the cell.
     *
	 * @param wSheet the sheet object.
	 * @param label the target cell label.
     * @param after A lower right cell is scanned from the cell object.
     * @param includeAfter Is the third argument cell object scanned?
     *
     * @return Target JExcel Api cell object.
     * @throws XLSBeansException This occures when the cell is not found.
     */
    public static WCell getCell(WSheet wSheet, String label, WCell after, boolean includeAfter, XLSBeansConfig config) throws XLSBeansException {
        return getCell(wSheet, label, after, includeAfter, true, config);
    }

	/**
     * Return cell object by using first argument sheet.
     * This cell will be found by label name in Excel sheet.
     *
     * NOTICE: When the cell object is specified for the third argument,
     * a lower right cell is scanned from the cell.
	 *
	 * @param wSheet the sheet object.
	 * @param label the target cell label.
	 * @param after A lower right cell is scanned from the cell object.
	 * @param includeAfter Is the third argument cell object scanned?
     * @param throwableWhenNotFound If this argument is true, throws XLSBeansException when
     * we can't find target cell.
     *
	 * @return Target JExcel Api cell object.
	 * @throws XLSBeansException This occures when the cell is not found.
	 */
	public static WCell getCell(WSheet wSheet, String label, WCell after,
			boolean includeAfter, boolean throwableWhenNotFound, XLSBeansConfig config) throws XLSBeansException {

		if (after == null) {
			// Call XLSBeans#getCell() - method if third argument is null.
			return Utils.getCell(wSheet, label, 0, throwableWhenNotFound, config);
		}

		int columnStart = after.getColumn();
		int rowStart = after.getRow();

		for (int col = columnStart; col < wSheet.getColumns(); col++) {
			// getting cells each columns
			WCell[] columns = wSheet.getColumn(col);
			for (int row = rowStart; row < columns.length; row++) {
				// except first cell if "includeAfter" is false.
				if (col == columnStart && row == rowStart && !includeAfter) {
					continue;
				}
				if (columns[row].getContents().equals(label)) {
					return columns[row];
				}
			}
		}
        if (throwableWhenNotFound) {
            // can't find cell
            throw new XLSBeansException("Cell '" + label + "' doesn't exist.");
        } else {
            return null;
        }
	}

	public static List<Object> getPropertiesWithAnnotation(Object tableObj, AnnotationReader reader, Class<?> clazz) throws Exception {

		List<Object> properties = new ArrayList<Object>();

		for(Method method: getMethodsWithAnnotation(tableObj, reader, clazz)){
			properties.add(method);
		}
		for(Field field: getFieldsWithAnnotation(tableObj, reader, clazz)){
			properties.add(field);
		}

		return properties;
	}

	/**
	 * Returns methods in object with annotation.
	 *
	 * @param tableObj target JavaBeans
	 * @param reader AnnotationReader object
	 * @param clazz target annotation type class.
	 * @return setter methods in argument object with annotation.
	 * @throws Exception unexpected exception.
	 */
	protected static Method[] getMethodsWithAnnotation(Object tableObj, AnnotationReader reader, Class<?> clazz) throws Exception {
		List<Method> result = new ArrayList<Method>();
		Method[] methods = tableObj.getClass().getMethods();

		for (Method method : methods) {
			// setter
			if (method.getName().startsWith("set") && method.getParameterTypes().length == 1) {
				// find annotation
				Annotation[] ans = reader.getAnnotations(tableObj.getClass(), method);
				for (Annotation an : ans) {
					if (an.annotationType().isAssignableFrom(clazz)) {
						result.add(method);
						break;
					}
				}
			}
		}
		return result.toArray(new Method[result.size()]);
	}

	/**
	 * Returns fields in object with annotation.
	 *
	 * @param tableObj target JavaBeans
	 * @param reader AnnotationReader object
	 * @param clazz target annotation type class.
	 * @return public fields in argument object with annotation.
	 * @throws Exception unexpected exception.
	 */
	protected static Field[] getFieldsWithAnnotation(Object tableObj, AnnotationReader reader, Class<?> clazz) throws Exception {
		List<Field> result = new ArrayList<Field>();
		Field[] fields = tableObj.getClass().getFields();

		for (Field field : fields) {
			// find annotation
			Annotation[] ans = reader.getAnnotations(tableObj.getClass(), field);
			for (Annotation an : ans) {
				if (an.annotationType().isAssignableFrom(clazz)) {
					result.add(field);
					break;
				}
			}
		}
		return result.toArray(new Field[result.size()]);
	}

	public static void setPosition(int x, int y, Object obj, String propertyName) throws Exception {
		try {
			Field field = obj.getClass().getField("positions");
            if(field.getType().isAssignableFrom(Map.class)){
                field.setAccessible(true);
                Object fieldValue = field.get(obj);
                if(fieldValue == null){
                    fieldValue = new HashMap<String, Point>();
                    field.set(obj, fieldValue);
                }
                ((Map)fieldValue).put(propertyName, new Point(x, y));
                return;
            }
		} catch(NoSuchFieldException ex){
		}

		String positionMethodName = toSetterName(propertyName) + "Position";
		try {
			Method positionMethod = obj.getClass().getMethod(positionMethodName, Integer.TYPE, Integer.TYPE);
			positionMethod.invoke(obj, x, y);
			return;
		} catch(NoSuchMethodException ex){
		}
		try {
			Method positionMethod = obj.getClass().getMethod(positionMethodName, Point.class);
			positionMethod.invoke(obj, new Point(x, y));
			return;
		} catch(NoSuchMethodException ex){
		}

		String positionFieldName = propertyName + "Position";
		try {
			Field field = obj.getClass().getField(positionFieldName);
			field.set(obj, new Point(x, y));
		} catch(NoSuchFieldException ex){
		}
	}

	public static String toSetterName(String propertyName){
		return "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
	}

	public static String toPropertyName(String setterName){
		setterName = setterName.replaceAll("^set", "");
		return setterName.substring(0, 1).toLowerCase() + setterName.substring(1);
	}

    public static boolean matches(String text1, String text2, XLSBeansConfig config){
        if(config.isRegexLabelText() && text2.startsWith("/") && text2.endsWith("/")){
            return normalize(text1, config).matches(text2.substring(1, text2.length() - 1));
        } else {
            return normalize(text1, config).equals(normalize(text2, config));
        }
    }

    private static String normalize(String text, XLSBeansConfig config){
        if(text != null && config.isNormalizeLabelText()){
            return text.trim().replaceAll("[\n\r]", "").replaceAll("[\t 　]+", " ");
        }
        return text;
    }

}
