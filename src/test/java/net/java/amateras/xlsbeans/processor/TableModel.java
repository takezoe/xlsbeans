package net.java.amateras.xlsbeans.processor;

import java.util.List;

import net.java.amateras.xlsbeans.annotation.HorizontalRecords;
import net.java.amateras.xlsbeans.annotation.Sheet;

@Sheet(name="テーブル定義")
public class TableModel {

	private List<ColumnModel> columns;

	public List<ColumnModel> getColumns() {
		return columns;
	}

	@HorizontalRecords(tableLabel="カラム一覧", recordClass=ColumnModel.class, range=3)
	public void setColumns(List<ColumnModel> columns) {
		this.columns = columns;
	}



}
