package com.github.takezoe.xlsbeans.processor;

import com.github.takezoe.xlsbeans.NeedPostProcess;
import com.github.takezoe.xlsbeans.XLSBeansConfig;
import com.github.takezoe.xlsbeans.xml.AnnotationReader;
import com.github.takezoe.xlsbeans.xssfconverter.WSheet;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Naoki Takezoe
 */
public interface FieldProcessor {

  public void doProcess(WSheet wSheet, Object obj, Method setter, Annotation ann, AnnotationReader reader,
                        XLSBeansConfig config, List<NeedPostProcess> needPostProcess) throws Exception;

  public void doProcess(WSheet wSheet, Object obj, Field field, Annotation ann, AnnotationReader reader,
                        XLSBeansConfig config, List<NeedPostProcess> needPostProcess) throws Exception;

}
