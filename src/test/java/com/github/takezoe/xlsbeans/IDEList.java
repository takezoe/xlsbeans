package com.github.takezoe.xlsbeans;

import com.github.takezoe.xlsbeans.annotation.HorizontalRecords;
import com.github.takezoe.xlsbeans.annotation.Sheet;

import java.util.List;

@Sheet(name="IDE")
public class IDEList {
	
	private List<IDE> ides;

	public List<IDE> getIDEs() {
		return ides;
	}

	@HorizontalRecords(tableLabel="Java IDEs", recordClass=IDE.class)
	public void setIDEs(List<IDE> ides) {
		this.ides = ides;
	}
	
}
