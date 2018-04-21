package com.github.takezoe.xlsbeans;

public class SheetNotFoundException extends XLSBeansException {

	private static final long serialVersionUID = 3766157546047611833L;

	public SheetNotFoundException(String sheetName) {
		super("Can't find a sheet (name='" + sheetName + "').");
	}
	
	public SheetNotFoundException(int number) {
		super("Can't find a sheet (number=" + number + ").");
	}
	
}
