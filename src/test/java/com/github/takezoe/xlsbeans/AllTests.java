package com.github.takezoe.xlsbeans;

import com.github.takezoe.xlsbeans.processor.FieldProcessorFactoryTest;
import com.github.takezoe.xlsbeans.processor.HorizontalRecordsProcessorTest;
import com.github.takezoe.xlsbeans.publicfield.PublicFieldTest;
import com.github.takezoe.xlsbeans.xml.DynamicAnnotationBuilderTest;
import com.github.takezoe.xlsbeans.xml.XMLLoaderTest;
import junit.framework.Test;
import junit.framework.TestSuite;

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
