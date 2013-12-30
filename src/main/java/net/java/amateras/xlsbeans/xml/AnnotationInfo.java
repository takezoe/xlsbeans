package net.java.amateras.xlsbeans.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Naoki Takezoe
 */
public class AnnotationInfo {
	
	private String annotationClass;
	private Map<String, String> annotationAttributes = new HashMap<String, String>();
	
	public void setAnnotationClass(String annotationClass){
		this.annotationClass = annotationClass;
	}
	
	public String getAnnotationClass(){
		return this.annotationClass;
	}
	
	public void addAnnotationAttribute(String name, String value){
		this.annotationAttributes.put(name, value);
	}
	
	public String getAnnotationAttribute(String name){
		return this.annotationAttributes.get(name);
	}
	
	public String[] getAnnotationAttributeKeys(){
		return this.annotationAttributes.keySet().toArray(new String[0]);
	}
	
}
