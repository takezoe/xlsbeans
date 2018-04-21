package com.github.takezoe.xlsbeans.processor;

import com.github.takezoe.xlsbeans.annotation.HorizontalRecords;
import com.github.takezoe.xlsbeans.annotation.Sheet;

import java.util.List;

@Sheet(name = "テーブル定義")
public class TableModel {

  private List<ColumnModel> columns;

  public List<ColumnModel> getColumns() {
    return columns;
  }

  @HorizontalRecords(tableLabel = "カラム一覧", recordClass = ColumnModel.class, range = 3)
  public void setColumns(List<ColumnModel> columns) {
    this.columns = columns;
  }


}
