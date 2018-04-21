package com.github.takezoe.xlsbeans.publicfield;

import com.github.takezoe.xlsbeans.annotation.HorizontalRecords;
import com.github.takezoe.xlsbeans.annotation.Sheet;

import java.util.List;

@Sheet(name="Language")
public class PublicFieldLanguageList {
	
	@HorizontalRecords(tableLabel="Programming Languages", recordClass=PublicFieldLanguage.class)
	public List <PublicFieldLanguage> langs;
	
	@HorizontalRecords(tableLabel="IDE for Languages", recordClass=PublicFieldLanguageIDE.class)
	public List <PublicFieldLanguageIDE> ides;

}
