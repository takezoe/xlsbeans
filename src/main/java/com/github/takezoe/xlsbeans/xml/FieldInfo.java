package com.github.takezoe.xlsbeans.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Naoki Takezoe
 */
public class FieldInfo {

  private String fieldName;
  private Map<String, AnnotationInfo> annotationInfos = new HashMap<String, AnnotationInfo>();

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getFieldName() {
    return this.fieldName;
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
}
