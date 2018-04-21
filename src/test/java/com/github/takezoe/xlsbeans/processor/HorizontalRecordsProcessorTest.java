package com.github.takezoe.xlsbeans.processor;

import com.github.takezoe.xlsbeans.XLSBeans;
import junit.framework.TestCase;

public class HorizontalRecordsProcessorTest extends TestCase {

  public void testHorizontalRecordsProcessor() throws Exception {
    TableModel tables =
        new XLSBeans().load(HorizontalRecordsProcessorTest.class.getResourceAsStream(
            "HorizontalRecordsProcessorTest.xls"), TableModel.class);

    assertEquals(2, tables.getColumns().size());

    ColumnModel column1 = tables.getColumns().get(0);
    assertEquals("ユーザID", column1.getLogicalName());
    assertEquals("USER_ID", column1.getPhysicalName());
    assertEquals("VARCHAR", column1.getType());
    assertEquals("100", column1.getSize());
    assertEquals("○", column1.getNotNull());

    ColumnModel column2 = tables.getColumns().get(1);
    assertEquals("パスワード", column2.getLogicalName());
    assertEquals("PASSWORD", column2.getPhysicalName());
    assertEquals("VARCHAR", column2.getType());
    assertEquals("100", column2.getSize());
    assertEquals("", column2.getNotNull());
  }

}
