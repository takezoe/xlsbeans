package net.java.amateras.xlsbeans;

import java.util.Map;

import net.java.amateras.xlsbeans.annotation.Column;
import net.java.amateras.xlsbeans.annotation.MapColumns;

public class LanguageIDE {
	
	private String name;
	private Map<String, String> attributes;
	
	public Map<String, String> getAttributes() {
		return attributes;
	}
	
	@MapColumns(previousColumnName="Name")
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	
	public String getName() {
		return name;
	}
	
	@Column(columnName="Name")
	public void setName(String name) {
		this.name = name;
	}
	
}
