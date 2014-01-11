package net.java.amateras.xlsbeans.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import net.java.amateras.xlsbeans.NeedPostProcess;
import net.java.amateras.xlsbeans.Utils;
import net.java.amateras.xlsbeans.XLSBeansConfig;
import net.java.amateras.xlsbeans.xml.AnnotationReader;
import net.java.amateras.xlsbeans.xssfconverter.WSheet;

/**
 *
 * @author Naoki Takezoe
 * @see net.java.amateras.xlsbeans.annotation.SheetName
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
