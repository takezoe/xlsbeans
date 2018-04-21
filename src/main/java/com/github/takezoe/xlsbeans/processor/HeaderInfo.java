package com.github.takezoe.xlsbeans.processor;

public class HeaderInfo {
	
	private String headerLabel;
	private int headerRange;
	
	public HeaderInfo(String headerLabel, int headerRange){
		this.headerLabel = headerLabel;
		this.headerRange = headerRange;
	}
	
	public String getHeaderLabel() {
		return headerLabel;
	}

	public int getHeaderRange() {
		return headerRange;
	}
}
