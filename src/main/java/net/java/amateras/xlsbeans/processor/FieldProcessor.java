package net.java.amateras.xlsbeans.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import net.java.amateras.xlsbeans.NeedPostProcess;
import net.java.amateras.xlsbeans.XLSBeans;
import net.java.amateras.xlsbeans.XLSBeansConfig;
import net.java.amateras.xlsbeans.xml.AnnotationReader;
import net.java.amateras.xlsbeans.xssfconverter.WSheet;

/**
 *
 * @author Naoki Takezoe
 */
public interface FieldProcessor {

    public void doProcess(WSheet wSheet, Object obj, Method setter, Annotation ann, AnnotationReader reader,
                          XLSBeansConfig config, List<NeedPostProcess> needPostProcess) throws Exception ;

    public void doProcess(WSheet wSheet, Object obj, Field field, Annotation ann, AnnotationReader reader,
                          XLSBeansConfig config, List<NeedPostProcess> needPostProcess) throws Exception ;

}
