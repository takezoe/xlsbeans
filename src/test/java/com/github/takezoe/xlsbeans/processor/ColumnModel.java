package com.github.takezoe.xlsbeans.processor;

import com.github.takezoe.xlsbeans.annotation.Column;

public class ColumnModel {
  private String physicalName;
  private String logicalName;
  private String type;
  private String size;
  private String notNull;

  public String getPhysicalName() {
    return physicalName;
  }

  @Column(columnName = "物理名")
  public void setPhysicalName(String physicalName) {
    this.physicalName = physicalName;
  }

  public String getLogicalName() {
    return logicalName;
  }

  @Column(columnName = "論理名")
  public void setLogicalName(String logicalName) {
    this.logicalName = logicalName;
  }

  public String getType() {
    return type;
  }

  @Column(columnName = "型")
  public void setType(String type) {
    this.type = type;
  }

  public String getSize() {
    return size;
  }

  @Column(columnName = "型", headerMerged = 1)
  public void setSize(String size) {
    this.size = size;
  }

  public String getNotNull() {
    return notNull;
  }

  @Column(columnName = "NOT NULL")
  public void setNotNull(String notNull) {
    this.notNull = notNull;
  }
}
