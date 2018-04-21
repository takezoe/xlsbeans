package com.github.takezoe.xlsbeans.publicfield;

import com.github.takezoe.xlsbeans.annotation.Column;

import java.awt.Point;

public class PublicFieldIDE {
	
	@Column(columnName="IDE")
	public String name;
	
	public Point namePosition;

	public String toString(){
		return "name=" + name;
	}
	
}
