package com.github.takezoe.xlsbeans;

import com.github.takezoe.xlsbeans.annotation.Column;
import com.github.takezoe.xlsbeans.annotation.MapColumns;

import java.util.Map;

public class LanguageIDE {

  private String name;
  private Map<String, String> attributes;

  public Map<String, String> getAttributes() {
    return attributes;
  }

  @MapColumns(previousColumnName = "Name")
  public void setAttributes(Map<String, String> attributes) {
    this.attributes = attributes;
  }

  public String getName() {
    return name;
  }

  @Column(columnName = "Name")
  public void setName(String name) {
    this.name = name;
  }

}
