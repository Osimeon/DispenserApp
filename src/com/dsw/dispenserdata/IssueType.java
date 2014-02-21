package com.dsw.dispenserdata;

public class IssueType {
	int issueTypeId;
	String issueType;
	String issueTypeName;
	
	public IssueType(){
		
	}
	
	public IssueType(int _id, String _type, String _name){
		this.issueTypeId = _id;
		this.issueType = _type;
		this.issueTypeName = _name;
	}

	public int getIssueTypeId() {
		return issueTypeId;
	}

	public void setIssueTypeId(int issueTypeId) {
		this.issueTypeId = issueTypeId;
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public String getIssueTypeName() {
		return issueTypeName;
	}

	public void setIssueTypeName(String issueTypeName) {
		this.issueTypeName = issueTypeName;
	}
}
