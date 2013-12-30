package net.java.amateras.xlsbeans;

import junit.framework.Test;
import junit.framework.TestSuite;
import net.java.amateras.xlsbeans.processor.FieldProcessorFactoryTest;
import net.java.amateras.xlsbeans.processor.HorizontalRecordsProcessorTest;
import net.java.amateras.xlsbeans.publicfield.PublicFieldTest;
import net.java.amateras.xlsbeans.xml.DynamicAnnotationBuilderTest;
import net.java.amateras.xlsbeans.xml.XMLLoaderTest;

/**
 * Runs all tests of XLSBeans.
 * 
 * @author Naoki Takezoe
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for net.java.amateras.xlsbeans");
		suite.addTestSuite(XLSBeansTest.class);
		suite.addTestSuite(PublicFieldTest.class);
		suite.addTestSuite(FieldProcessorFactoryTest.class);
		suite.addTestSuite(DynamicAnnotationBuilderTest.class);
		suite.addTestSuite(XMLLoaderTest.class);
		suite.addTestSuite(UtilsTest.class);
		suite.addTestSuite(HorizontalRecordsProcessorTest.class);
		return suite;
	}

}
