package com.github.takezoe.xlsbeans.publicfield;

import com.github.takezoe.xlsbeans.annotation.Column;
import com.github.takezoe.xlsbeans.annotation.MapColumns;

import java.util.Map;

public class PublicFieldLanguageIDE {

  @Column(columnName = "Name")
  public String name;

  @MapColumns(previousColumnName = "Name")
  public Map<String, String> attributes;

}
