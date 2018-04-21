package com.github.takezoe.xlsbeans.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Naoki Takezoe
 */
public class ClassInfo {

  private String className;
  private Map<String, AnnotationInfo> annotationInfos = new HashMap<String, AnnotationInfo>();
  private Map<String, MethodInfo> methodInfos = new HashMap<String, MethodInfo>();
  private Map<String, FieldInfo> fieldInfos = new HashMap<String, FieldInfo>();

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public void addAnnotationInfo(AnnotationInfo info) {
    this.annotationInfos.put(info.getAnnotationClass(), info);
  }

  public AnnotationInfo getAnnotationInfo(String annotationClass) {
    return this.annotationInfos.get(annotationClass);
  }

  public AnnotationInfo[] getAnnotationInfos() {
    return annotationInfos.values().toArray(new AnnotationInfo[0]);
  }

  public void addMethodInfo(MethodInfo info) {
    this.methodInfos.put(info.getMethodName(), info);
  }

  public MethodInfo getMethodInfo(String methodName) {
    return this.methodInfos.get(methodName);
  }

  public void addFieldInfo(FieldInfo info) {
    this.fieldInfos.put(info.getFieldName(), info);
  }

  public FieldInfo getFieldInfo(String fieldName) {
    return this.fieldInfos.get(fieldName);
  }

}
