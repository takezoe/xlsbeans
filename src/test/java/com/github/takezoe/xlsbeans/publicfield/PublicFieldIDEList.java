package com.github.takezoe.xlsbeans.publicfield;

import com.github.takezoe.xlsbeans.annotation.HorizontalRecords;
import com.github.takezoe.xlsbeans.annotation.Sheet;

import java.util.List;

@Sheet(name="IDE")
public class PublicFieldIDEList {
	
	@HorizontalRecords(tableLabel="Java IDEs", recordClass=PublicFieldIDE.class)
	public List<PublicFieldIDE> ides;
	
}
