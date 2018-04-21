package com.github.takezoe.xlsbeans.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import com.github.takezoe.xlsbeans.NeedPostProcess;
import com.github.takezoe.xlsbeans.Utils;
import com.github.takezoe.xlsbeans.XLSBeansConfig;
import com.github.takezoe.xlsbeans.annotation.SheetName;
import com.github.takezoe.xlsbeans.xml.AnnotationReader;
import com.github.takezoe.xlsbeans.xssfconverter.WSheet;

/**
 * @author Naoki Takezoe
 * @see SheetName
 */
public class SheetNameProcessor implements FieldProcessor {

  public void doProcess(WSheet wSheet, Object obj, Method setter, Annotation cell, AnnotationReader reader,
                        XLSBeansConfig config, List<NeedPostProcess> needPostProcess) throws Exception {
    Utils.invokeSetter(setter, obj, wSheet.getName(), config);
  }

  public void doProcess(WSheet wSheet, Object obj, Field field, Annotation ann, AnnotationReader reader,
                        XLSBeansConfig config, List<NeedPostProcess> needPostProcess) throws Exception {
    Utils.setField(field, obj, wSheet.getName(), config);
  }

}
