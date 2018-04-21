package com.github.takezoe.xlsbeans.publicfield;

import java.awt.Point;

import com.github.takezoe.xlsbeans.XLSBeans;
import com.github.takezoe.xlsbeans.XLSBeansTest;
import junit.framework.TestCase;

/**
 * A testcase for public field support.
 *
 * @author Naoki Takezoe
 */
public class PublicFieldTest extends TestCase {

  public void testPublicField() throws Exception {
    Object[] objList = new XLSBeans().loadMultiple(
        XLSBeansTest.class.getResourceAsStream("example_2.xls"),
        null,
        new Class<?>[]{PublicFieldLanguageList.class, PublicFieldIDEList.class});

    assertEquals(2, objList.length);

    PublicFieldLanguageList langList = (PublicFieldLanguageList) objList[0];

    assertEquals(4, langList.langs.size());
    assertEquals("Java", langList.langs.get(0).name);
    assertEquals("Perl", langList.langs.get(1).name);
    assertEquals("Ruby", langList.langs.get(2).name);
    assertEquals("Python", langList.langs.get(3).name);

    assertEquals(2, langList.ides.size());

    assertEquals("Java", langList.ides.get(0).name);
//		assertEquals("Eclipse", langList.ides.get(0).attributes.get("Custom1"));
//		assertEquals("IBM", langList.ides.get(0).attributes.get("Custom2"));

    assertEquals("C#", langList.ides.get(1).name);
//		assertEquals("VisualStudio", langList.ides.get(1).attributes.get("Custom1"));
//		assertEquals("Microsoft", langList.ides.get(1).attributes.get("Custom2"));


    PublicFieldIDEList ideList = (PublicFieldIDEList) objList[1];
    assertEquals(5, ideList.ides.size());
    assertEquals("Eclipse", ideList.ides.get(0).name);
    assertEquals(new Point(0, 2), ideList.ides.get(0).namePosition);
    assertEquals("NetBeans", ideList.ides.get(1).name);
    assertEquals(new Point(0, 3), ideList.ides.get(1).namePosition);
    assertEquals("JDeveloper", ideList.ides.get(2).name);
    assertEquals(new Point(0, 4), ideList.ides.get(2).namePosition);
    assertEquals("JBuilder", ideList.ides.get(3).name);
    assertEquals(new Point(0, 5), ideList.ides.get(3).namePosition);
    assertEquals("IDEA", ideList.ides.get(4).name);
    assertEquals(new Point(0, 6), ideList.ides.get(4).namePosition);
  }

}
