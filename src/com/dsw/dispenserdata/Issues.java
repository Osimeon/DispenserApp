package com.dsw.dispenserdata;

public class Issues {
	int issueId;
	int issueWaterpointId;
	String issueDateCreated;
	String issueStatus;
	int issueIssueType;
	
	public Issues(){
		
	}
	
	public Issues(int _id, int _wpId, String _date, String _status, int _type){
		this.issueId = _id;
		this.issueWaterpointId = _wpId;
		this.issueDateCreated = _date;
		this.issueStatus = _status;
		this.issueIssueType = _type;
	}

	public int getIssueId() {
		return issueId;
	}

	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}

	public int getIssueWaterpointId() {
		return issueWaterpointId;
	}

	public void setIssueWaterpointId(int issueWaterpointId) {
		this.issueWaterpointId = issueWaterpointId;
	}

	public String getIssueDateCreated() {
		return issueDateCreated;
	}

	public void setIssueDateCreated(String issueDateCreated) {
		this.issueDateCreated = issueDateCreated;
	}

	public String getIssueStatus() {
		return issueStatus;
	}

	public void setIssueStatus(String issueStatus) {
		this.issueStatus = issueStatus;
	}

	public int getIssueIssueType() {
		return issueIssueType;
	}

	public void setIssueIssueType(int issueIssueType) {
		this.issueIssueType = issueIssueType;
	}
}
