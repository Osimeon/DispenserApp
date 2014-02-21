package com.dsw.dispenserdata;

/**
 * @author osimeon
 *
 */
public class Users {
	int staffNumber; 
	String staffEmail;
	
	public Users(){
		
	}
	
	public Users(int _empNo, String _email){
		this.staffNumber = _empNo;
		this.staffEmail = _email;
	}

	public int getStaffNumber() {
		return staffNumber;
	}

	public void setStaffNumber(int staffNumber) {
		this.staffNumber = staffNumber;
	}

	public String getStaffEmail() {
		return staffEmail;
	}

	public void setStaffEmail(String staffEmail) {
		this.staffEmail = staffEmail;
	}
}
