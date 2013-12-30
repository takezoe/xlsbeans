package net.java.amateras.xlsbeans.publicfield;

import java.util.List;

import net.java.amateras.xlsbeans.annotation.HorizontalRecords;
import net.java.amateras.xlsbeans.annotation.Sheet;

@Sheet(name="IDE")
public class PublicFieldIDEList {
	
	@HorizontalRecords(tableLabel="Java IDEs", recordClass=PublicFieldIDE.class)
	public List<PublicFieldIDE> ides;
	
}
