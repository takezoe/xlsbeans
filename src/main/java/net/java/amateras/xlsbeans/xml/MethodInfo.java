package net.java.amateras.xlsbeans.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Naoki Takezoe
 */
public class MethodInfo {
	
	private String methodName;
	private Map<String, AnnotationInfo> annotationInfos = new HashMap<String, AnnotationInfo>();
	
	public void setMethodName(String methodName){
		this.methodName = methodName;
	}
	public String getMethodName(){
		return this.methodName;
	}
	
	public void addAnnotationInfo(AnnotationInfo info){
		this.annotationInfos.put(info.getAnnotationClass(), info);
	}
	
	public AnnotationInfo getAnnotationInfo(String annotationClass){
		return this.annotationInfos.get(annotationClass);
	}
	
	public AnnotationInfo[] getAnnotationInfos(){
		return annotationInfos.values().toArray(new AnnotationInfo[0]);
	}
	
}
