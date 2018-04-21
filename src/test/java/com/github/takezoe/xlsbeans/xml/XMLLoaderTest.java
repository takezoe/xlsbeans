package com.github.takezoe.xlsbeans.xml;

import junit.framework.TestCase;

public class XMLLoaderTest extends TestCase {
	
	public void testLoad() throws Exception {
		XMLInfo info = XMLLoader.load(
				XMLLoaderTest.class.getResourceAsStream("XMLLoaderTest_testLoad.xml"));
		
		ClassInfo classInfo = info.getClassInfo(
				"net.java.amateras.xlsbeans.example.UserList");
		assertEquals(1, classInfo.getAnnotationInfos().length);
		
		AnnotationInfo annInfo = classInfo.getAnnotationInfo(
				"net.java.amateras.xlsbeans.annotation.LabelledCell");
		
		assertEquals(2, annInfo.getAnnotationAttributeKeys().length);
		assertEquals("'Title'", annInfo.getAnnotationAttribute("label"));
		assertEquals("@net.java.amateras.xlsbeans.annotation.LabelledCellType@Right", 
				annInfo.getAnnotationAttribute("type"));
	}
	
}
