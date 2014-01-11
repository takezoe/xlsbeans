package net.java.amateras.xlsbeans;

import java.awt.Point;
import java.util.List;

import junit.framework.TestCase;
import net.java.amateras.xlsbeans.xssfconverter.WorkbookFinder;

/**
 * 
 * @author Naoki Takezoe
 */
public class XLSBeansTest extends TestCase {

	public void test1() throws Exception {

		// System.out.println("*************************************");
		// System.out.println("test1");
		// System.out.println("*************************************");

		UserList userList = new XLSBeans().load(
				XLSBeansTest.class.getResourceAsStream("example_1.xls"),
				XLSBeansTest.class.getResourceAsStream("example_1.xml"),
				UserList.class);

		// DumpXLSBeans.showDump(userList);

		assertEquals("User list2", userList.getTitle());
		assertEquals("06/04/10", userList.getLastUpdate());

		assertEquals(new Point(2, 23), userList.getUnitUsers().get(0)
				.getUnitUsers().get(0).getNamePosition());
		assertEquals(new Point(2, 24), userList.getUnitUsers().get(0)
				.getUnitUsers().get(1).getNamePosition());

		assertEquals(new Point(2, 29), userList.getUnitUsers().get(1)
				.getUnitUsers().get(0).getNamePosition());
		assertEquals(new Point(2, 30), userList.getUnitUsers().get(1)
				.getUnitUsers().get(1).getNamePosition());

		// ////////////////////////////////////////////////////////
		List<User> hUsers = userList.getHorizontalUsers();
		assertEquals(4, hUsers.size());

		assertEquals(1, hUsers.get(0).getId());
		assertEquals("male", hUsers.get(0).getGender());
		assertEquals("Ken", hUsers.get(0).getName());
		assertEquals("Edgar", hUsers.get(0).getFamilyName());

		assertEquals(2, hUsers.get(1).getId());
		assertEquals("male", hUsers.get(1).getGender());
		assertEquals("John", hUsers.get(1).getName());
		assertEquals("Woodgate", hUsers.get(1).getFamilyName());

		assertEquals(3, hUsers.get(2).getId());
		assertEquals("male", hUsers.get(2).getGender());
		assertEquals("Mat", hUsers.get(2).getName());
		assertEquals("Brown", hUsers.get(2).getFamilyName());

		assertEquals(4, hUsers.get(3).getId());
		assertEquals("female", hUsers.get(3).getGender());
		assertEquals("Nancy", hUsers.get(3).getName());
		assertEquals("Davis", hUsers.get(3).getFamilyName());

		// ////////////////////////////////////////////////////////
		List<User> vUsers = userList.getVerticalUsers();
		assertEquals(4, vUsers.size());

		assertEquals(1, vUsers.get(0).getId());
		assertEquals("male", vUsers.get(0).getGender());
		assertEquals("Ken", vUsers.get(0).getName());
		assertEquals("Edgar", vUsers.get(0).getFamilyName());

		assertEquals(2, vUsers.get(1).getId());
		assertEquals("", vUsers.get(1).getGender());
		assertEquals("John", vUsers.get(1).getName());
		assertEquals("Woodgate", vUsers.get(1).getFamilyName());

		assertEquals(3, vUsers.get(2).getId());
		assertEquals("", vUsers.get(2).getGender());
		assertEquals("Mat", vUsers.get(2).getName());
		assertEquals("Brown", vUsers.get(2).getFamilyName());

		assertEquals(4, vUsers.get(3).getId());
		assertEquals("female", vUsers.get(3).getGender());
		assertEquals("Nancy", vUsers.get(3).getName());
		assertEquals("Davis", vUsers.get(3).getFamilyName());

		// ////////////////////////////////////////////////////////
		assertEquals("sample.SampleAction", userList.getActionClassName());
		assertEquals("sample.SampleForm", userList.getFormClassName());
	}

	public void test1_hssf() throws Exception {

		// System.out.println("*************************************");
		// System.out.println("test1_hssf");
		// System.out.println("*************************************");

		UserList userList = new XLSBeans().load(
				XLSBeansTest.class.getResourceAsStream("example_1.xls"),
				XLSBeansTest.class.getResourceAsStream("example_1.xml"),
				UserList.class, WorkbookFinder.TYPE_HSSF);

		// DumpXLSBeans.showDump(userList);

		assertEquals("User list2", userList.getTitle());
		assertEquals("06/04/10", userList.getLastUpdate());

		assertEquals(new Point(2, 23), userList.getUnitUsers().get(0)
				.getUnitUsers().get(0).getNamePosition());
		assertEquals(new Point(2, 24), userList.getUnitUsers().get(0)
				.getUnitUsers().get(1).getNamePosition());

		assertEquals(new Point(2, 29), userList.getUnitUsers().get(1)
				.getUnitUsers().get(0).getNamePosition());
		assertEquals(new Point(2, 30), userList.getUnitUsers().get(1)
				.getUnitUsers().get(1).getNamePosition());

		// ////////////////////////////////////////////////////////
		List<User> hUsers = userList.getHorizontalUsers();
		assertEquals(4, hUsers.size());

		assertEquals(1, hUsers.get(0).getId());
		assertEquals("male", hUsers.get(0).getGender());
		assertEquals("Ken", hUsers.get(0).getName());
		assertEquals("Edgar", hUsers.get(0).getFamilyName());

		assertEquals(2, hUsers.get(1).getId());
		assertEquals("male", hUsers.get(1).getGender());
		assertEquals("John", hUsers.get(1).getName());
		assertEquals("Woodgate", hUsers.get(1).getFamilyName());

		assertEquals(3, hUsers.get(2).getId());
		assertEquals("male", hUsers.get(2).getGender());
		assertEquals("Mat", hUsers.get(2).getName());
		assertEquals("Brown", hUsers.get(2).getFamilyName());

		assertEquals(4, hUsers.get(3).getId());
		assertEquals("female", hUsers.get(3).getGender());
		assertEquals("Nancy", hUsers.get(3).getName());
		assertEquals("Davis", hUsers.get(3).getFamilyName());

		// ////////////////////////////////////////////////////////
		List<User> vUsers = userList.getVerticalUsers();
		assertEquals(4, vUsers.size());

		assertEquals(1, vUsers.get(0).getId());
		assertEquals("male", vUsers.get(0).getGender());
		assertEquals("Ken", vUsers.get(0).getName());
		assertEquals("Edgar", vUsers.get(0).getFamilyName());

		assertEquals(2, vUsers.get(1).getId());
		assertEquals("", vUsers.get(1).getGender());
		assertEquals("John", vUsers.get(1).getName());
		assertEquals("Woodgate", vUsers.get(1).getFamilyName());

		assertEquals(3, vUsers.get(2).getId());
		assertEquals("", vUsers.get(2).getGender());
		assertEquals("Mat", vUsers.get(2).getName());
		assertEquals("Brown", vUsers.get(2).getFamilyName());

		assertEquals(4, vUsers.get(3).getId());
		assertEquals("female", vUsers.get(3).getGender());
		assertEquals("Nancy", vUsers.get(3).getName());
		assertEquals("Davis", vUsers.get(3).getFamilyName());

		// ////////////////////////////////////////////////////////
		assertEquals("sample.SampleAction", userList.getActionClassName());
		assertEquals("sample.SampleForm", userList.getFormClassName());
	}

	public void test1_xssf() throws Exception {

		// System.out.println("*************************************");
		// System.out.println("test1_xssf");
		// System.out.println("*************************************");

		UserList userList = new XLSBeans().load(
				XLSBeansTest.class.getResourceAsStream("example_1.xlsx"),
				XLSBeansTest.class.getResourceAsStream("example_1.xml"),
				UserList.class, WorkbookFinder.TYPE_XSSF);

		// DumpXLSBeans.showDump(userList);

		assertEquals("User list2", userList.getTitle());
		assertEquals("06/04/10", userList.getLastUpdate());

		assertEquals(new Point(2, 23), userList.getUnitUsers().get(0)
				.getUnitUsers().get(0).getNamePosition());
		assertEquals(new Point(2, 24), userList.getUnitUsers().get(0)
				.getUnitUsers().get(1).getNamePosition());

		assertEquals(new Point(2, 29), userList.getUnitUsers().get(1)
				.getUnitUsers().get(0).getNamePosition());
		assertEquals(new Point(2, 30), userList.getUnitUsers().get(1)
				.getUnitUsers().get(1).getNamePosition());

		// ////////////////////////////////////////////////////////
		List<User> hUsers = userList.getHorizontalUsers();
		assertEquals(4, hUsers.size());

		assertEquals(1, hUsers.get(0).getId());
		assertEquals("male", hUsers.get(0).getGender());
		assertEquals("Ken", hUsers.get(0).getName());
		assertEquals("Edgar", hUsers.get(0).getFamilyName());

		assertEquals(2, hUsers.get(1).getId());
		assertEquals("male", hUsers.get(1).getGender());
		assertEquals("John", hUsers.get(1).getName());
		assertEquals("Woodgate", hUsers.get(1).getFamilyName());

		assertEquals(3, hUsers.get(2).getId());
		assertEquals("male", hUsers.get(2).getGender());
		assertEquals("Mat", hUsers.get(2).getName());
		assertEquals("Brown", hUsers.get(2).getFamilyName());

		assertEquals(4, hUsers.get(3).getId());
		assertEquals("female", hUsers.get(3).getGender());
		assertEquals("Nancy", hUsers.get(3).getName());
		assertEquals("Davis", hUsers.get(3).getFamilyName());

		// ////////////////////////////////////////////////////////
		List<User> vUsers = userList.getVerticalUsers();
		assertEquals(4, vUsers.size());

		assertEquals(1, vUsers.get(0).getId());
		assertEquals("male", vUsers.get(0).getGender());
		assertEquals("Ken", vUsers.get(0).getName());
		assertEquals("Edgar", vUsers.get(0).getFamilyName());

		assertEquals(2, vUsers.get(1).getId());
		assertEquals("", vUsers.get(1).getGender());
		assertEquals("John", vUsers.get(1).getName());
		assertEquals("Woodgate", vUsers.get(1).getFamilyName());

		assertEquals(3, vUsers.get(2).getId());
		assertEquals("", vUsers.get(2).getGender());
		assertEquals("Mat", vUsers.get(2).getName());
		assertEquals("Brown", vUsers.get(2).getFamilyName());

		assertEquals(4, vUsers.get(3).getId());
		assertEquals("female", vUsers.get(3).getGender());
		assertEquals("Nancy", vUsers.get(3).getName());
		assertEquals("Davis", vUsers.get(3).getFamilyName());

		// ////////////////////////////////////////////////////////
		assertEquals("sample.SampleAction", userList.getActionClassName());
		assertEquals("sample.SampleForm", userList.getFormClassName());
	}

	public void test2() throws Exception {
		UserList[] userLists = new XLSBeans().loadMultiple(
				XLSBeansTest.class.getResourceAsStream("example_1.xls"),
				XLSBeansTest.class.getResourceAsStream("example_1.xml"),
				UserList.class);

		// System.out.println("*************************************");
		// System.out.println("test2");
		// System.out.println("*************************************");
		//
		// System.out.println(userLists.length);

		assertEquals(1, userLists.length);

		UserList userList = userLists[0];

		assertEquals("User list2", userList.getTitle());
		assertEquals("06/04/10", userList.getLastUpdate());
	}

	public void test2_hssf() throws Exception {
		UserList[] userLists = new XLSBeans().loadMultiple(
				XLSBeansTest.class.getResourceAsStream("example_1.xls"),
				XLSBeansTest.class.getResourceAsStream("example_1.xml"),
				UserList.class, WorkbookFinder.TYPE_HSSF);

		// System.out.println("*************************************");
		// System.out.println("test2_hssf");
		// System.out.println("*************************************");

		// for(UserList userList : userLists){
		// DumpXLSBeans.showDump(userList);
		// }

		assertEquals(1, userLists.length);

		UserList userList = userLists[0];

		assertEquals("User list2", userList.getTitle());
		assertEquals("06/04/10", userList.getLastUpdate());
	}

	public void test2_xssf() throws Exception {
		UserList[] userLists = new XLSBeans().loadMultiple(
				XLSBeansTest.class.getResourceAsStream("example_1.xlsx"),
				XLSBeansTest.class.getResourceAsStream("example_1.xml"),
				UserList.class, WorkbookFinder.TYPE_XSSF);

		// System.out.println("*************************************");
		// System.out.println("test2_xssf");
		// System.out.println("*************************************");

		// for(UserList userList : userLists){
		// DumpXLSBeans.showDump(userList);
		// }

		assertEquals(1, userLists.length);

		UserList userList = userLists[0];

		assertEquals("User list2", userList.getTitle());
		assertEquals("06/04/10", userList.getLastUpdate());
	}

	public void test3() throws Exception {
		Object[] objList = new XLSBeans().loadMultiple(
				XLSBeansTest.class.getResourceAsStream("example_2.xls"), null,
				new Class<?>[] { LanguageList.class, IDEList.class });

		// System.out.println("*************************************");
		// System.out.println("test3");
		// System.out.println("*************************************");

		assertEquals(2, objList.length);

		LanguageList langList = (LanguageList) objList[0];

		assertEquals(4, langList.getLangs().size());
		assertEquals("Java", langList.getLangs().get(0).getName());
		assertEquals("Perl", langList.getLangs().get(1).getName());
		assertEquals("Ruby", langList.getLangs().get(2).getName());
		assertEquals("Python", langList.getLangs().get(3).getName());

		assertEquals(2, langList.getIDEs().size());

		assertEquals("Java", langList.getIDEs().get(0).getName());
		assertEquals("Eclipse",
				langList.getIDEs().get(0).getAttributes().get("Custom1"));
		assertEquals("IBM",
				langList.getIDEs().get(0).getAttributes().get("Custom2"));

		assertEquals("C#", langList.getIDEs().get(1).getName());
		assertEquals("VisualStudio", langList.getIDEs().get(1).getAttributes()
				.get("Custom1"));
		assertEquals("Microsoft", langList.getIDEs().get(1).getAttributes()
				.get("Custom2"));

		IDEList ideList = (IDEList) objList[1];
		assertEquals(5, ideList.getIDEs().size());
		assertEquals("Eclipse", ideList.getIDEs().get(0).getName());
		assertEquals(new Point(0, 2), ideList.getIDEs().get(0)
				.getNamePosition());
		assertEquals("NetBeans", ideList.getIDEs().get(1).getName());
		assertEquals(new Point(0, 3), ideList.getIDEs().get(1)
				.getNamePosition());
		assertEquals("JDeveloper", ideList.getIDEs().get(2).getName());
		assertEquals(new Point(0, 4), ideList.getIDEs().get(2)
				.getNamePosition());
		assertEquals("JBuilder", ideList.getIDEs().get(3).getName());
		assertEquals(new Point(0, 5), ideList.getIDEs().get(3)
				.getNamePosition());
		assertEquals("IDEA", ideList.getIDEs().get(4).getName());
		assertEquals(new Point(0, 6), ideList.getIDEs().get(4)
				.getNamePosition());
	}

	public void test3_hssf() throws Exception {
		Object[] objList = new XLSBeans().loadMultiple(
				XLSBeansTest.class.getResourceAsStream("example_2.xls"), null,
				new Class<?>[] { LanguageList.class, IDEList.class },
				WorkbookFinder.TYPE_HSSF);

		// System.out.println("*************************************");
		// System.out.println("test3_hssf");
		// System.out.println("*************************************");

		assertEquals(2, objList.length);

		LanguageList langList = (LanguageList) objList[0];

		assertEquals(4, langList.getLangs().size());
		assertEquals("Java", langList.getLangs().get(0).getName());
		assertEquals("Perl", langList.getLangs().get(1).getName());
		assertEquals("Ruby", langList.getLangs().get(2).getName());
		assertEquals("Python", langList.getLangs().get(3).getName());

		assertEquals(2, langList.getIDEs().size());

		assertEquals("Java", langList.getIDEs().get(0).getName());
		assertEquals("Eclipse",
				langList.getIDEs().get(0).getAttributes().get("Custom1"));
		assertEquals("IBM",
				langList.getIDEs().get(0).getAttributes().get("Custom2"));

		assertEquals("C#", langList.getIDEs().get(1).getName());
		assertEquals("VisualStudio", langList.getIDEs().get(1).getAttributes()
				.get("Custom1"));
		assertEquals("Microsoft", langList.getIDEs().get(1).getAttributes()
				.get("Custom2"));

		IDEList ideList = (IDEList) objList[1];
		assertEquals(5, ideList.getIDEs().size());
		assertEquals("Eclipse", ideList.getIDEs().get(0).getName());
		assertEquals(new Point(0, 2), ideList.getIDEs().get(0)
				.getNamePosition());
		assertEquals("NetBeans", ideList.getIDEs().get(1).getName());
		assertEquals(new Point(0, 3), ideList.getIDEs().get(1)
				.getNamePosition());
		assertEquals("JDeveloper", ideList.getIDEs().get(2).getName());
		assertEquals(new Point(0, 4), ideList.getIDEs().get(2)
				.getNamePosition());
		assertEquals("JBuilder", ideList.getIDEs().get(3).getName());
		assertEquals(new Point(0, 5), ideList.getIDEs().get(3)
				.getNamePosition());
		assertEquals("IDEA", ideList.getIDEs().get(4).getName());
		assertEquals(new Point(0, 6), ideList.getIDEs().get(4)
				.getNamePosition());
	}

	public void test3_xssf() throws Exception {
		Object[] objList = new XLSBeans().loadMultiple(
				XLSBeansTest.class.getResourceAsStream("example_2.xlsx"), null,
				new Class<?>[] { LanguageList.class, IDEList.class },
				WorkbookFinder.TYPE_XSSF);

		// System.out.println("*************************************");
		// System.out.println("test3_xssf");
		// System.out.println("*************************************");

		assertEquals(2, objList.length);

		LanguageList langList = (LanguageList) objList[0];

		assertEquals(4, langList.getLangs().size());
		assertEquals("Java", langList.getLangs().get(0).getName());
		assertEquals("Perl", langList.getLangs().get(1).getName());
		assertEquals("Ruby", langList.getLangs().get(2).getName());
		assertEquals("Python", langList.getLangs().get(3).getName());

		assertEquals(2, langList.getIDEs().size());

		assertEquals("Java", langList.getIDEs().get(0).getName());
		assertEquals("Eclipse",
				langList.getIDEs().get(0).getAttributes().get("Custom1"));
		assertEquals("IBM",
				langList.getIDEs().get(0).getAttributes().get("Custom2"));

		assertEquals("C#", langList.getIDEs().get(1).getName());
		assertEquals("VisualStudio", langList.getIDEs().get(1).getAttributes()
				.get("Custom1"));
		assertEquals("Microsoft", langList.getIDEs().get(1).getAttributes()
				.get("Custom2"));

		IDEList ideList = (IDEList) objList[1];
		assertEquals(5, ideList.getIDEs().size());
		assertEquals("Eclipse", ideList.getIDEs().get(0).getName());
		assertEquals(new Point(0, 2), ideList.getIDEs().get(0)
				.getNamePosition());
		assertEquals("NetBeans", ideList.getIDEs().get(1).getName());
		assertEquals(new Point(0, 3), ideList.getIDEs().get(1)
				.getNamePosition());
		assertEquals("JDeveloper", ideList.getIDEs().get(2).getName());
		assertEquals(new Point(0, 4), ideList.getIDEs().get(2)
				.getNamePosition());
		assertEquals("JBuilder", ideList.getIDEs().get(3).getName());
		assertEquals(new Point(0, 5), ideList.getIDEs().get(3)
				.getNamePosition());
		assertEquals("IDEA", ideList.getIDEs().get(4).getName());
		assertEquals(new Point(0, 6), ideList.getIDEs().get(4)
				.getNamePosition());
	}

	public void test4() throws Exception {
		UserList2 userList = new XLSBeans().load(
				XLSBeansTest.class.getResourceAsStream("example_3.xls"), null,
				UserList2.class);
		List<User> users = userList.getUserList();
		User user1 = users.get(0);

		assertEquals(1, user1.getId());
		assertEquals("false", user1.getGender()); // test for a boolean
													// formula
		assertEquals("name1", user1.getName()); // test for a string formula
		assertEquals("11", user1.getFamilyName()); // test for a numeric
													// formula

		User user2 = users.get(1);
		assertEquals(2, user2.getId()); // test for a numeric formula
		assertEquals("true", user2.getGender());
		assertEquals("name2", user2.getName());
		assertEquals("12", user2.getFamilyName());

		User user3 = users.get(2);
		assertEquals(3, user3.getId());
		assertEquals("false", user3.getGender());
		assertEquals("name3", user3.getName());
		assertEquals("13", user3.getFamilyName());

		User user4 = users.get(3);
		assertEquals(4, user4.getId());
		assertEquals("true", user4.getGender());
		assertEquals("name4", user4.getName());
		assertEquals("#NAME?", user4.getFamilyName()); // test for error
														// formula

	}

	public void test4_hssf() throws Exception {
		UserList2 userList = new XLSBeans().load(
				XLSBeansTest.class.getResourceAsStream("example_3.xls"), null,
				UserList2.class, WorkbookFinder.TYPE_HSSF);
		List<User> users = userList.getUserList();
		User user1 = users.get(0);

		assertEquals(1, user1.getId());
		assertEquals("false", user1.getGender()); // test for a boolean
													// formula
		assertEquals("name1", user1.getName()); // test for a string formula
		assertEquals("11", user1.getFamilyName()); // test for a numeric
													// formula

		User user2 = users.get(1);
		assertEquals(2, user2.getId()); // test for a numeric formula
		assertEquals("true", user2.getGender());
		assertEquals("name2", user2.getName());
		assertEquals("12", user2.getFamilyName());

		User user3 = users.get(2);
		assertEquals(3, user3.getId());
		assertEquals("false", user3.getGender());
		assertEquals("name3", user3.getName());
		assertEquals("13", user3.getFamilyName());

		User user4 = users.get(3);
		assertEquals(4, user4.getId());
		assertEquals("true", user4.getGender());
		assertEquals("name4", user4.getName());
		assertEquals("A6+a", user4.getFamilyName()); // test for error
														// formula (!! it's
														// different from
														// jxl's)
	}

	public void test4_xssf() throws Exception {
		UserList2 userList = new XLSBeans().load(
				XLSBeansTest.class.getResourceAsStream("example_3.xlsx"), null,
				UserList2.class, WorkbookFinder.TYPE_XSSF);
		List<User> users = userList.getUserList();
		User user1 = users.get(0);

		assertEquals(1, user1.getId());
		assertEquals("false", user1.getGender()); // test for a boolean
													// formula
		assertEquals("name1", user1.getName()); // test for a string formula
		assertEquals("11", user1.getFamilyName()); // test for a numeric
													// formula

		User user2 = users.get(1);
		assertEquals(2, user2.getId()); // test for a numeric formula
		assertEquals("true", user2.getGender());
		assertEquals("name2", user2.getName());
		assertEquals("12", user2.getFamilyName());

		User user3 = users.get(2);
		assertEquals(3, user3.getId());
		assertEquals("false", user3.getGender());
		assertEquals("name3", user3.getName());
		assertEquals("13", user3.getFamilyName());

		User user4 = users.get(3);
		assertEquals(4, user4.getId());
		assertEquals("true", user4.getGender());
		assertEquals("name4", user4.getName());
		assertEquals("A6+a", user4.getFamilyName()); // test for error
														// formula (!! it's
														// different from
														// jxl's)
	}

	public void testNormalize() throws Exception {
        XLSBeans xlsBeans = new XLSBeans();

        XLSBeansConfig config = new XLSBeansConfig();
        config.setNormalizeLabelText(true);
        xlsBeans.setConfig(config);

		UserList userList = xlsBeans.load(
                XLSBeansTest.class.getResourceAsStream("example_4.xls"),
                XLSBeansTest.class.getResourceAsStream("example_1.xml"),
                UserList.class);

		// DumpXLSBeans.showDump(userList);

		assertEquals("User list2", userList.getTitle());
		assertEquals("06/04/10", userList.getLastUpdate());

		assertEquals(new Point(2, 23), userList.getUnitUsers().get(0)
				.getUnitUsers().get(0).getNamePosition());
		assertEquals(new Point(2, 24), userList.getUnitUsers().get(0)
				.getUnitUsers().get(1).getNamePosition());

		assertEquals(new Point(2, 29), userList.getUnitUsers().get(1)
				.getUnitUsers().get(0).getNamePosition());
		assertEquals(new Point(2, 30), userList.getUnitUsers().get(1)
				.getUnitUsers().get(1).getNamePosition());

		// ////////////////////////////////////////////////////////
		List<User> hUsers = userList.getHorizontalUsers();
		assertEquals(4, hUsers.size());

		assertEquals(1, hUsers.get(0).getId());
		assertEquals("male", hUsers.get(0).getGender());
		assertEquals("Ken", hUsers.get(0).getName());
		assertEquals("Edgar", hUsers.get(0).getFamilyName());

		assertEquals(2, hUsers.get(1).getId());
		assertEquals("male", hUsers.get(1).getGender());
		assertEquals("John", hUsers.get(1).getName());
		assertEquals("Woodgate", hUsers.get(1).getFamilyName());

		assertEquals(3, hUsers.get(2).getId());
		assertEquals("male", hUsers.get(2).getGender());
		assertEquals("Mat", hUsers.get(2).getName());
		assertEquals("Brown", hUsers.get(2).getFamilyName());

		assertEquals(4, hUsers.get(3).getId());
		assertEquals("female", hUsers.get(3).getGender());
		assertEquals("Nancy", hUsers.get(3).getName());
		assertEquals("Davis", hUsers.get(3).getFamilyName());

		// ////////////////////////////////////////////////////////
		List<User> vUsers = userList.getVerticalUsers();
		assertEquals(4, vUsers.size());

		assertEquals(1, vUsers.get(0).getId());
		assertEquals("male", vUsers.get(0).getGender());
		assertEquals("Ken", vUsers.get(0).getName());
		assertEquals("Edgar", vUsers.get(0).getFamilyName());

		assertEquals(2, vUsers.get(1).getId());
		assertEquals("", vUsers.get(1).getGender());
		assertEquals("John", vUsers.get(1).getName());
		assertEquals("Woodgate", vUsers.get(1).getFamilyName());

		assertEquals(3, vUsers.get(2).getId());
		assertEquals("", vUsers.get(2).getGender());
		assertEquals("Mat", vUsers.get(2).getName());
		assertEquals("Brown", vUsers.get(2).getFamilyName());

		assertEquals(4, vUsers.get(3).getId());
		assertEquals("female", vUsers.get(3).getGender());
		assertEquals("Nancy", vUsers.get(3).getName());
		assertEquals("Davis", vUsers.get(3).getFamilyName());

		// ////////////////////////////////////////////////////////
		assertEquals("sample.SampleAction", userList.getActionClassName());
		assertEquals("sample.SampleForm", userList.getFormClassName());
	}

	public static void main(String[] args) throws Exception {
		XLSBeansTest example = new XLSBeansTest();
		example.test1();
		example.test2();
		example.test3();
	}

}
