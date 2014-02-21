package com.dsw.dispenserdata;

public class Waterpoints {
	int waterpointId;
	String waterpointName;
	String village;
	
	public Waterpoints(){
		
	}
	
	public Waterpoints(int _id, String _name, String _village){
		this.waterpointId = _id;
		this.waterpointName = _name;
		this.village = _village;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public int getWaterpointId() {
		return waterpointId;
	}

	public void setWaterpointId(int waterpointId) {
		this.waterpointId = waterpointId;
	}

	public String getWaterpointName() {
		return waterpointName;
	}

	public void setWaterpointName(String waterpointName) {
		this.waterpointName = waterpointName;
	}
	
	@Override
    public String toString () {
        return waterpointName;
    }
}
