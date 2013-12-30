package net.java.amateras.xlsbeans;

import java.awt.Point;

import net.java.amateras.xlsbeans.annotation.Column;

public class IDE {
	
	private String name;
	private Point namePosition;

	public String getName() {
		return name;
	}
	
	@Column(columnName="IDE")
	public void setName(String name) {
		this.name = name;
	}
	
	public Point getNamePosition() {
		return namePosition;
	}

	public void setNamePosition(Point namePosition) {
		this.namePosition = namePosition;
	}

	public String toString(){
		return "name=" + getName();
	}
	
}
