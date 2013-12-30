package net.java.amateras.xlsbeans.publicfield;

import java.util.Map;

import net.java.amateras.xlsbeans.annotation.Column;
import net.java.amateras.xlsbeans.annotation.MapColumns;

public class PublicFieldLanguageIDE {
	
	@Column(columnName="Name")
	public String name;
	
	@MapColumns(previousColumnName="Name")
	public Map<String, String> attributes;
	
}
