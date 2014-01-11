package net.java.amateras.xlsbeans.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import junit.framework.TestCase;
import net.java.amateras.xlsbeans.NeedPostProcess;
import net.java.amateras.xlsbeans.XLSBeansConfig;
import net.java.amateras.xlsbeans.xml.AnnotationInfo;
import net.java.amateras.xlsbeans.xml.AnnotationReader;
import net.java.amateras.xlsbeans.xml.DynamicAnnotationBuilder;
import net.java.amateras.xlsbeans.xssfconverter.WSheet;

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
