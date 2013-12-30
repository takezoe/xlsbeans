package net.java.amateras.xlsbeans.publicfield;

import java.util.List;

import net.java.amateras.xlsbeans.annotation.HorizontalRecords;
import net.java.amateras.xlsbeans.annotation.Sheet;

@Sheet(name="Language")
public class PublicFieldLanguageList {
	
	@HorizontalRecords(tableLabel="Programming Languages", recordClass=PublicFieldLanguage.class)
	public List <PublicFieldLanguage> langs;
	
	@HorizontalRecords(tableLabel="IDE for Languages", recordClass=PublicFieldLanguageIDE.class)
	public List <PublicFieldLanguageIDE> ides;

}
