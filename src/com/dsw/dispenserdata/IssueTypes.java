package com.dsw.dispenserdata;

public class IssueTypes {
	int issueTypeId;
	String issueTypeName;
	
	public IssueTypes(){
		
	}
	
	public IssueTypes(int _id, String _name){
		this.issueTypeId = _id;
		this.issueTypeName = _name;
	}

	public int getIssueTypeId() {
		return issueTypeId;
	}

	public void setIssueTypeId(int issueTypeId) {
		this.issueTypeId = issueTypeId;
	}

	public String getIssueTypeName() {
		return issueTypeName;
	}

	public void setIssueTypeName(String issueTypeName) {
		this.issueTypeName = issueTypeName;
	}
	
	@Override
    public String toString () {
        return issueTypeName;
    }
}
