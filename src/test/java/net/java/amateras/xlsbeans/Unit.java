package net.java.amateras.xlsbeans;

import java.awt.Point;
import java.util.List;

import net.java.amateras.xlsbeans.annotation.HorizontalRecords;
import net.java.amateras.xlsbeans.annotation.LabelledCell;
import net.java.amateras.xlsbeans.annotation.LabelledCellType;

public class Unit {

	private String unitName;
	private Point unitNamePosition;
	private List<UnitUser> unitUsers;

	public String getUnitName() {
		return unitName;
	}

	@LabelledCell(label="部門名", type=LabelledCellType.Right)
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Point getUnitNamePosition() {
		return unitNamePosition;
	}

	public void setUnitNamePosition(Point unitNamePosition) {
		this.unitNamePosition = unitNamePosition;
	}

	public List<UnitUser> getUnitUsers() {
		return unitUsers;
	}

	@HorizontalRecords(tableLabel="部門情報")
	public void setUnitUsers(List<UnitUser> unitUsers) {
		this.unitUsers = unitUsers;
	}
}
