package net.java.amateras.xlsbeans;

import java.lang.reflect.Field;

import junit.framework.TestCase;

public abstract class XLSBeansTestCase extends TestCase {

	protected Object getField(Object obj, String fieldName) throws Exception {
		Field field = obj.getClass().getField(fieldName);
		field.setAccessible(true);
		return field.get(obj);
	}

}
