package com.github.takezoe.xlsbeans;

import com.github.takezoe.xlsbeans.annotation.HorizontalRecords;
import com.github.takezoe.xlsbeans.annotation.Sheet;

import java.util.List;

@Sheet(name = "Language")
public class LanguageList {

  private List<Language> langs;
  private List<LanguageIDE> ides;

  public List<LanguageIDE> getIDEs() {
    return ides;
  }

  @HorizontalRecords(tableLabel = "IDE for Languages", recordClass = LanguageIDE.class)
  public void setIDEs(List<LanguageIDE> ides) {
    this.ides = ides;
  }

  public List<Language> getLangs() {
    return langs;
  }

  @HorizontalRecords(tableLabel = "Programming Languages", recordClass = Language.class)
  public void setLangs(List<Language> langs) {
    this.langs = langs;
  }

}
