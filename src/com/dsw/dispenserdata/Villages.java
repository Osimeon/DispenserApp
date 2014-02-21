package com.dsw.dispenserdata;

public class Villages {
	String villageName;
	String sublocation;
	
	public Villages(){
		
	}
	
	public Villages(String name, String sublocation){
		this.villageName = name;
		this.sublocation = sublocation;
	}

	public String getSublocation() {
		return sublocation;
	}

	public void setSublocation(String sublocation) {
		this.sublocation = sublocation;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}
	
}
