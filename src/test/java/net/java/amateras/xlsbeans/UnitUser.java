package net.java.amateras.xlsbeans;

import java.awt.Point;

import net.java.amateras.xlsbeans.annotation.Column;

public class UnitUser {

	private int id;
	private String name;
	private Point namePosition;

	public int getId() {
		return id;
	}
	@Column(columnName="ID")
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	@Column(columnName="名前")
	public void setName(String name) {
		this.name = name;
	}
	public Point getNamePosition() {
		return namePosition;
	}
	public void setNamePosition(Point namePosition) {
		this.namePosition = namePosition;
	}
}
