package net.java.amateras.xlsbeans.xml;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import ognl.Ognl;
import ognl.OgnlContext;

/**
 * Creates {@link java.lang.annotation.Annotation} instances
 * dynamically using Javassist.
 *
 * @author Naoki Takezoe
 */
public class DynamicAnnotationBuilder {

    private static ClassLoader loader;

    private static OgnlContext ognlContext = null;

    /**
     * Sets class loader that uses Annotation defined by XLSBeans.
     *
     * @param loader ClassLoader used to find Annotation.
     */
    public static void setClassLoader(ClassLoader loader){
        DynamicAnnotationBuilder.loader = loader;
        DynamicAnnotationBuilder.ognlContext = null;
    }

    /**
     * Set class loader that uses Annotation defined by XLSBeans,
     * and that uses JavaBeans type in defined dynamic-annotation by users.
     *
     * @param loader ClassLoader used to find Annotation.
     * @param propertyLoaders used to find JavaBeans type.
     */
    public static void setClassLoader(ClassLoader loader,
            ClassLoader[] propertyLoaders) {
        DynamicAnnotationBuilder.loader = loader;
        if (propertyLoaders != null && propertyLoaders.length != 0) {
            Map<Integer, ClassLoader> loaderMap = new HashMap<Integer, ClassLoader>();
            for (ClassLoader propertyLoader : propertyLoaders) {
                loaderMap.put(propertyLoader.hashCode(), propertyLoader);
            }
            DynamicAnnotationBuilder.ognlContext = new OgnlContext(loaderMap);
            DynamicAnnotationBuilder.ognlContext.setClassResolver(new MultipleLoaderClassResolver());
        } else {
            DynamicAnnotationBuilder.ognlContext = null;
        }
    }

    /**
     * Creates the annotation instance dynamically using Javaassist.
     */
    public static Annotation buildAnnotation(final Class<?> ann, AnnotationInfo info) throws Exception {

        final Map<String, Object> defaultValues = new HashMap<String, Object>();
        for(Method method : ann.getMethods()){
            Object defaultValue = method.getDefaultValue();
            if(defaultValue!=null){
                defaultValues.put(method.getName(), defaultValue);
            }
        }

        final Map<String, Object> xmlValues = new HashMap<String, Object>();
        for(String key : info.getAnnotationAttributeKeys()){
            Object value = null;
            if (ognlContext == null) {
                value = Ognl.getValue(info.getAnnotationAttribute(key),new Object());
            } else {
                value = Ognl.getValue(info.getAnnotationAttribute(key), ognlContext, new Object());
            }
            xmlValues.put(key, value);
        }

        ClassLoader loader = DynamicAnnotationBuilder.loader;
        if(loader == null){
            loader = Thread.currentThread().getContextClassLoader();
        }
        
        Object obj = Proxy.newProxyInstance(loader, new Class[]{ann},
                new InvocationHandler(){
                    public Object invoke(Object proxy, Method method,
                            Object[] args) throws Throwable {
                        String name = method.getName();
                        if (name.equals("annotationType")) {
                            return ann;
                        } else if(xmlValues.containsKey(name)){
                            return xmlValues.get(name);
                        } else {
                            return defaultValues.get(name);
                        }
                    }
        });

        return (Annotation)obj;
    }

}
