package com.dsw.dispenserdata;

public class Sublocations {
	String sublocationName;
	String districtName;
	
	public Sublocations(){
		
	}
	
	public Sublocations(String name, String district){
		this.sublocationName = name;
		this.districtName = district;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getSublocationName() {
		return sublocationName;
	}

	public void setSublocationName(String sublocationName) {
		this.sublocationName = sublocationName;
	}
	
}
