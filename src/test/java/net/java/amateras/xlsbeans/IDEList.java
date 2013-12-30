package net.java.amateras.xlsbeans;

import java.util.List;

import net.java.amateras.xlsbeans.annotation.HorizontalRecords;
import net.java.amateras.xlsbeans.annotation.Sheet;

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
