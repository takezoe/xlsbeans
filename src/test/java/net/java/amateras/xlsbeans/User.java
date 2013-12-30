package net.java.amateras.xlsbeans;

import net.java.amateras.xlsbeans.annotation.Column;

/**
 * 
 * @author Naoki Takezoe
 */
public class User {
	
	private int id;
	private String name;
	private String familyName;
	private String gender;
	
	public int getId() {
		return id;
	}
	
	@Column(columnName="ID")
	public void setId(int id) {
		this.id = id;
	}
	
	public String getGender(){
		return gender;
	}
	
	@Column(columnName="Gender", merged=true)
	public void setGender(String gender){
		this.gender = gender;
	}
	
	public String getName() {
		return name;
	}
	
	@Column(columnName="Name")
	public void setName(String name) {
		this.name = name;
	}

	public String getFamilyName() {
		return familyName;
	}

	@Column(columnName="Name", headerMerged=1)
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	
}
