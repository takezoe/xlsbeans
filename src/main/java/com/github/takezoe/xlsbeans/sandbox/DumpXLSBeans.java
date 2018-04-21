package com.github.takezoe.xlsbeans.sandbox;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.takezoe.xlsbeans.annotation.*;

/**
 * Dump POJO formed by XLSBeans
 * @author Mitsuyoshi Hasegawa
 */
public class DumpXLSBeans {

    // current-element
    private StringBuilder indent = new StringBuilder();
    
    private String getIndent() {
        return this.indent.toString();
    }
    
    /**
     * Dump XLSbeans.
     * 
     * @param bean
     * @throws Exception
     */
    public static void showDump(Object bean) throws Exception {
        DumpXLSBeans dumpXlsBeans = new DumpXLSBeans();
        dumpXlsBeans.dump(bean, dumpXlsBeans.getIndent());
    }
    
    private void dump(Object bean, String indent) throws Exception {
        this.indent.append(indent);
        this.indent.append("    ");
        
        if (bean == null) {
            System.out.println(getIndent() + "bean is null");
            return;
        }
        
        
        // find Getter
        Method[] getters = getGetter(bean);
        if (getters.length == 0) {
            return;
        }
        
        // display bean-name 
        System.out.println(getIndent() + "***************** <<" + bean.getClass().getName() + ">>");
        
        for (Method getter : getters) {
            if (!isArrayOrListReturnValue(getter)) {
                // single item
                Method setter = getSetterInBean(bean, getter);
                String label = getAnnotationLabel(setter);
                if(label == null){
                	label = setter.getName().replaceFirst("^set", "");
                }
                Object obj = getter.invoke(bean, new Object[]{});
                System.out.println(getIndent() + label + "[" + obj + "]");
            } else {
                if (getter.getReturnType().isAssignableFrom(List.class)) {
                    // listed item
                	@SuppressWarnings("unchecked")
                    List list = (List) getter.invoke(bean, new Object[]{});
                    if (list == null) {
                        System.out.println(getIndent() + "################ " + getter.getName() + "method return value(List) is null.");
                        continue;
                    }
                    for (Object obj : list) {
                        (new DumpXLSBeans()).dump(obj, getIndent());
                    }
                } else if (getter.getReturnType().isArray()) {
                    // array
                    Object array = getter.invoke(bean, new Object[]{});
                    if (array == null) {
                        System.out.println(getIndent() + "################ " +  getter.getName() + "method return value(Array) is null.");
                        continue;
                    }
                    Object[] arrays = (Object[]) array;
                    for (Object obj : arrays) {
                        (new DumpXLSBeans()).dump(obj, getIndent());
                    }
                } else if (getter.getReturnType().isAssignableFrom(Map.class)) {
                    // map
                	@SuppressWarnings("unchecked")
                    Map map = (Map) getter.invoke(bean, new Object[]{});
                    if (map == null) {
                        System.out.println(getIndent() + "*****************" +  getter.getName() + " return value(Map) is null.");
                        continue;
                    }
                    System.out.print(getIndent() + "{");
                    for (Object obj : map.keySet()) {
                        System.out.print("[" + obj + ", " + map.get(obj) + "]");
                    }
                    System.out.println("}");
                    continue;
                } else {
                    System.err.println(getIndent() + "*****************" +  "getter-method is invalid, may be.");
                }
            }
        }
    }
    
    private Method getSetterInBean(Object bean, Method getter) throws Exception {
        String getterName = getter.getName();
        String guessSetterName = getterName.replace("get", "set");
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().equals(guessSetterName)) {
                return method;
            }
        }
        return null;
    }
    
    private String getAnnotationLabel(Method setter) {
        if (setter == null) {
            return "";
        }
        if (setter.isAnnotationPresent(Column.class)) {
            Column column = setter.getAnnotation(Column.class);
            return column.columnName();
        } else if (setter.isAnnotationPresent(HorizontalRecords.class)) {
            HorizontalRecords records = setter.getAnnotation(HorizontalRecords.class);
            return records.tableLabel();
        } else if (setter.isAnnotationPresent(LabelledCell.class)) {
            LabelledCell cell = setter.getAnnotation(LabelledCell.class);
            return cell.label();
        } else if (setter.isAnnotationPresent(MapColumns.class)) {
            MapColumns mapColumns = setter.getAnnotation(MapColumns.class);
            return mapColumns.previousColumnName();
        } else if (setter.isAnnotationPresent(VerticalRecords.class)) {
            VerticalRecords records = setter.getAnnotation(VerticalRecords.class);
            return records.tableLabel();
        } else if (setter.isAnnotationPresent(IterateTables.class)) {
            IterateTables tables = setter.getAnnotation(IterateTables.class);
            return tables.tableLabel();
        }
        return null;
    }
    
    private boolean isArrayOrListReturnValue(Method getter) {
        if (getter == null) {
            return false;
        }
        Class<?> type = getter.getReturnType();
        if (Void.class.isAssignableFrom(type)) {
            return false;
        }
        
        if (List.class.isAssignableFrom(type)) {
            return true;
        }
        if (Map.class.isAssignableFrom(type)) {
            return true;
        }
        if (type.isArray()) {
            return true;
        }
        // neither array nor listed item.
        return false;
        
    }
    
    private Method[] getGetter(Object bean) throws Exception {
        Method[] methods = bean.getClass().getMethods();
        if (methods == null || methods.length == 0) {
            return new Method[]{};
        }
        
        if (bean instanceof String) {
            return new Method[]{};
        }
        
        List<Method> list = new ArrayList<Method>();
        for (Method msd : methods) {
            String methodName = msd.getName();
            if (methodName.startsWith("get") && !"getClass".equals(methodName)
                    && !"getBytes".equals(methodName)) {
                list.add(msd);
            }
        }
        return list.toArray(new Method[]{});
    }
    
}
