package net.java.amateras.xlsbeans.publicfield;

import java.awt.Point;

import net.java.amateras.xlsbeans.annotation.Column;

public class PublicFieldIDE {
	
	@Column(columnName="IDE")
	public String name;
	
	public Point namePosition;

	public String toString(){
		return "name=" + name;
	}
	
}
