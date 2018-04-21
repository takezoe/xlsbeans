package com.github.takezoe.xlsbeans;

import com.github.takezoe.xlsbeans.annotation.Column;

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
