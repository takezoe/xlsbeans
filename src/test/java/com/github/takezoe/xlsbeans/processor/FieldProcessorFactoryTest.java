package com.github.takezoe.xlsbeans.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import com.github.takezoe.xlsbeans.NeedPostProcess;
import com.github.takezoe.xlsbeans.XLSBeansConfig;
import com.github.takezoe.xlsbeans.xml.AnnotationInfo;
import com.github.takezoe.xlsbeans.xml.AnnotationReader;
import com.github.takezoe.xlsbeans.xml.DynamicAnnotationBuilder;
import com.github.takezoe.xlsbeans.xssfconverter.WSheet;
import junit.framework.TestCase;

public class FieldProcessorFactoryTest extends TestCase {

	public void testRegisterProcessor() throws Exception {
		SampleFieldProcessor processor = new SampleFieldProcessor();
		DynamicAnnotationBuilder.setClassLoader(getClass().getClassLoader());
		FieldProcessorFactory.registerProcessor(SampleAnnotation.class, processor);

		Annotation annotation = DynamicAnnotationBuilder.buildAnnotation(
				SampleAnnotation.class, new AnnotationInfo());

		assertSame(processor, FieldProcessorFactory.getProcessor(annotation));
	}

	private @interface SampleAnnotation {
	}

	private class SampleFieldProcessor implements FieldProcessor {
		public void doProcess(WSheet wSheet, Object obj, Method setter, Annotation ann, AnnotationReader reader,
													XLSBeansConfig config, List<NeedPostProcess> needPostProcess) throws Exception {
		}

		public void doProcess(WSheet wSheet, Object obj, Field field, Annotation ann, AnnotationReader reader,
				XLSBeansConfig config, List<NeedPostProcess> needPostProcess) throws Exception {
		}
	}
}
