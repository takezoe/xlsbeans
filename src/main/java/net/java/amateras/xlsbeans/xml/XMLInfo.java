package net.java.amateras.xlsbeans.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Naoki Takezoe
 */
public class XMLInfo {
	
	private Map<String, ClassInfo> classInfos = new HashMap<String, ClassInfo>();
	
	public void addClassInfo(ClassInfo classInfo){
		this.classInfos.put(classInfo.getClassName(), classInfo);
	}
	
	public ClassInfo getClassInfo(String clazz){
		return this.classInfos.get(clazz);
	}
}
