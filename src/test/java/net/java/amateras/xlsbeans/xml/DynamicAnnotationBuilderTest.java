package net.java.amateras.xlsbeans.xml;

import java.lang.annotation.Annotation;

import junit.framework.TestCase;
import net.java.amateras.xlsbeans.annotation.Cell;
import net.java.amateras.xlsbeans.annotation.HorizontalRecords;
import net.java.amateras.xlsbeans.annotation.RecordTerminal;

public class DynamicAnnotationBuilderTest extends TestCase {

	public void testBuildAnnotation1() throws Exception {
		AnnotationInfo info = new AnnotationInfo();
		info.addAnnotationAttribute("row", "10");
		info.addAnnotationAttribute("column", "99");
		
		Annotation ann = DynamicAnnotationBuilder.buildAnnotation(
				Cell.class, info);
		
		assertSame(Cell.class, ann.annotationType());
		
		Cell cell = (Cell) ann;
		assertEquals(10, cell.row());
		assertEquals(99, cell.column());
	}
	
	public void testBuildAnnotation2() throws Exception {
		Annotation ann = DynamicAnnotationBuilder.buildAnnotation(
				HorizontalRecords.class, new AnnotationInfo());
		
		assertSame(HorizontalRecords.class, ann.annotationType());
		
		HorizontalRecords records = (HorizontalRecords) ann;
		assertFalse(records.optional());
		assertEquals("", records.tableLabel());
		assertEquals("", records.terminateLabel());
		assertEquals(-1, records.headerRow());
		assertEquals(-1, records.headerColumn());
		assertNull(records.recordClass());
		assertSame(RecordTerminal.Empty, records.terminal());
		assertEquals(1, records.range());
		assertEquals(1, records.bottom());
	}

}
