package net.java.amateras.xlsbeans;

import net.java.amateras.xlsbeans.annotation.Column;

public class Language {
	
	private String name;

	public String getName() {
		return name;
	}
	
	@Column(columnName="Language")
	public void setName(String name) {
		this.name = name;
	}
	
}
