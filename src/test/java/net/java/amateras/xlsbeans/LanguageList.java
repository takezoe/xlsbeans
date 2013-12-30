package net.java.amateras.xlsbeans;

import java.util.List;

import net.java.amateras.xlsbeans.annotation.HorizontalRecords;
import net.java.amateras.xlsbeans.annotation.Sheet;

@Sheet(name="Language")
public class LanguageList {
	
	private List <Language> langs;
	private List <LanguageIDE> ides;

	public List<LanguageIDE> getIDEs() {
		return ides;
	}
	
	@HorizontalRecords(tableLabel="IDE for Languages", recordClass=LanguageIDE.class)
	public void setIDEs(List<LanguageIDE> ides) {
		this.ides = ides;
	}

	public List<Language> getLangs() {
		return langs;
	}

	@HorizontalRecords(tableLabel="Programming Languages", recordClass=Language.class)
	public void setLangs(List<Language> langs) {
		this.langs = langs;
	}

}
