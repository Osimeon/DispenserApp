package com.dsw.dispenserapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.dsw.dispenserdata.DbHandler;
import com.dsw.dispenserdata.Districts;
import com.dsw.dispenserdata.IssueType;
import com.dsw.dispenserdata.Issues;
import com.dsw.dispenserdata.Sublocations;
import com.dsw.dispenserdata.Users;
import com.dsw.dispenserdata.Villages;
import com.dsw.dispenserdata.Waterpoints;

public class Sync extends Activity{
	Context _context;
	AlertDialogManager alert = new AlertDialogManager();	
	
	public Sync(Context context){
		this._context = context;
	}
	
	@Override
	protected void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);
	}
	
	//Staff Nodes
  	private static final String KEY_USER_ID = "emp_no";
  	private static final String KEY_USER_EMAIL = "email";
  	
  	//Water points Nodes
  	private static final String KEY_WATERPOINT_ID = "waterpoint_id";
  	private static final String KEY_WATERPOINT_NAME = "waterpoint_name";
  	
  	//District Nodes
  	private static final String KEY_DISTRICT_NAME = "district_name";
  	
  	//Sublocation Nodes
  	private static final String KEY_SUBLOCATION_NAME = "sublocation";
  	
  	//Village Nodes
  	private static final String KEY_VILLAGE_NAME = "village";
  	
  	//Issue type Nodes
  	private static final String KEY_ISSUE_TYPE_ID = "issuetype_id";
  	private static final String KEY_ISSUE_TYPE = "issue_type";
  	private static final String KEY_ISSUE_NAME = "issue_name";
  	
  	//Issue Nodes
  	private static final String KEY_ISSUE_ID = "issueid";
  	private static final String KEY_ISSUE_WATERPOINT_ID = "waterpoint_id";
  	private static final String KEY_ISSUE_DATE_CREATED = "date_created";
  	private static final String KEY_ISSUE_RESOLVED = "resolved";
  	private static final String KEY_ISSUE_ISSUE_TYPE_ID = "issuetype_id";
  	
  	//Water points Count
  	private static final String KEY_WATERPOINTS_COUNT = "count";
  	private static final String KEY_ISSUE_TYPE_COUNT = "count";	
  	  	
  	JSONArray waterpointList;
    JSONArray issueTypeList;
    JSONArray issuesList;
    JSONArray userDetails;
    JSONArray waterpointCount;
    JSONArray issueTypeCount;
    
    UserFunctions userFunction = new UserFunctions();
    
    //Error to show user that is returned
    //From server in-case can't service request
    public void serverError(){
    	alert.showAlertDialog(_context,"Server Error","Could Not Establish Connection To Server", false);
		return;
    }
    
    //Get Local Store Waterpoint count
    public int appWaterpointsCount(DbHandler db){
    	return db.getRowCount(DbHandler.TABLE_WATERPOINTS);    	
    }
    
    //Get Local Store issue type count
    public int appIssueTypeCount(DbHandler db){
    	return db.getRowCount(DbHandler.TABLE_ISSUE_TYPES);
    }
    
    //Get Server issue type count
    public int getServerIssueTypeCount(){
    	int count = 0;
    	JSONObject typeCount = userFunction.getIssueTypeCount();
    	if(typeCount != null){
    		try{
        		issueTypeCount = typeCount.getJSONArray("issueTypeCount"); 
        		for(int i = 0; i < issueTypeCount.length(); i++){
        			JSONObject type = issueTypeCount.getJSONObject(i);
        			count = type.getInt(KEY_ISSUE_TYPE_COUNT);
        		}
        	}
        	catch (JSONException e) {
        		e.printStackTrace();
        	}
    	}
    	return count;
    }
    
    //Get Server waterpoint count
    public int getServerWaterpointsCount(String staffId){
    	int count = 0;
    	JSONObject wpCount = userFunction.getWaterpointCount(staffId);
    	if(wpCount != null){
    		try{
        		waterpointCount = wpCount.getJSONArray("waterpointCount");
        		for(int i = 0; i < waterpointCount.length(); i++){
        			JSONObject wp = waterpointCount.getJSONObject(i);
        			count = wp.getInt(KEY_WATERPOINTS_COUNT);
        		}
        	}
        	catch (JSONException e) {
           	 	e.printStackTrace();                       
            }
    	}
    	return count;
    }
    
    //Synchronizing Waterpoints 
    public void syncWaterpoints(DbHandler db, String staffId){
    	if((getServerWaterpointsCount(staffId) > appWaterpointsCount(db)) || (appWaterpointsCount(db) > getServerWaterpointsCount(staffId))){ 
    		String appIssue = String.valueOf(appWaterpointsCount(db));
    		Log.d("App Waterpoint Count", appIssue);
    		db.resetWaterpoints();
    		db.deleteFromTable(DbHandler.TABLE_DISTRICTS);
    		db.deleteFromTable(DbHandler.TABLE_SUBLOCATIONS);
    		db.deleteFromTable(DbHandler.TABLE_VILLAGES);
	    	JSONObject waterpoints = userFunction.getWateropints(staffId);
	    	if(waterpoints != null){
		    	try{
		    		waterpointList = waterpoints.getJSONArray("waterpoints");
		    		Log.d("Total Waterpoints Returned ", String.valueOf(waterpointList.length()));
		    		
		    		for(int i = 0; i < waterpointList.length(); i++){
		    			JSONObject wp = waterpointList.getJSONObject(i);
		        		String waterpintId = wp.getString(KEY_WATERPOINT_ID);
		        		String waterpointName = wp.getString(KEY_WATERPOINT_NAME);
		        		String districtName = wp.getString(KEY_DISTRICT_NAME);
		        		String sublocationName = wp.getString(KEY_SUBLOCATION_NAME);
		        		String villageName = wp.getString(KEY_VILLAGE_NAME);
		        		Waterpoints waterpoint = new Waterpoints(Integer.parseInt(waterpintId),waterpointName, villageName);
		        		Districts district = new Districts(districtName);
		        		Sublocations sublocation = new Sublocations(sublocationName, districtName);
		        		Villages village = new Villages(villageName, sublocationName);
		        		db.addDistrict(district);
		        		db.addSublocation(sublocation);
		        		db.addVillage(village);
		        		db.addWaterpoint(waterpoint);
		        		Log.d("Insert: ", " Inserting District...");
		        		Log.d("Insert: ", " Inserting Sublocation...");
		        		Log.d("Insert: ", " Inserting Village...");
		        		Log.d("Insert: ", " Inserting Waterpoint...");
		    		}
		    	}
		    	catch (JSONException e) {
		       	 	e.printStackTrace();                       
		        }
	    	}
    	}
    }
    
    public void syncIssueTypes(DbHandler db){
    	if((getServerIssueTypeCount() > appIssueTypeCount(db)) || (appIssueTypeCount(db) > getServerIssueTypeCount())){
    		String appIssue = String.valueOf(appIssueTypeCount(db));
    		Log.d("App Issue Type Count", appIssue);
	    	JSONObject issueTypes = userFunction.getIssueTypes();
	    	db.deleteFromTable(DbHandler.TABLE_ISSUE_TYPES);
	    	if(issueTypes != null){
		    	try{
		    		issueTypeList = issueTypes.getJSONArray("issuetypes");
		    		for(int i = 0; i < issueTypeList.length(); i++){
		    			JSONObject it = issueTypeList.getJSONObject(i);
		        		String issueTypeId = it.getString(KEY_ISSUE_TYPE_ID);
		        		String issueType = it.getString(KEY_ISSUE_TYPE);
		        		String issueName = it.getString(KEY_ISSUE_NAME);
		        		IssueType type = new IssueType(Integer.parseInt(issueTypeId),issueType, issueName);
		        		db.addIssueType(type);
		        		Log.d("Insert: ", " Inserting Issue Type...");
		    		}
		    	}
		    	catch (JSONException e) {
		       	 	e.printStackTrace();                       
		        }
	    	}
    	}
    }
    
    public void syncIssues(DbHandler db, String staffID){
    	JSONObject issues = userFunction.getIssues(staffID);
    	if(issues != null){
	    	try{
	    		issuesList = issues.getJSONArray("issues");
	    		for(int i = 0; i < issuesList.length(); i++){
	    			JSONObject is = issuesList.getJSONObject(i);
	        		int issueId = is.getInt(KEY_ISSUE_ID);
	        		int waterpointId = is.getInt(KEY_ISSUE_WATERPOINT_ID);
	        		String dateCreated = is.getString(KEY_ISSUE_DATE_CREATED);
	        		String resolved = is.getString(KEY_ISSUE_RESOLVED);
	        		int issueTypeId = is.getInt(KEY_ISSUE_ISSUE_TYPE_ID);
	        		Issues issue = new Issues(issueId,waterpointId,dateCreated,resolved,issueTypeId);
	        		db.addIssue(issue);
	        		Log.d("Insert: ", " Inserting Issue...");
	    		}
	    	}
	    	catch (JSONException e) {
	       	 	e.printStackTrace();                       
	        }
    	}
    }
    
    public void syncUserDetails(DbHandler db, String staffId){
    	JSONObject user = userFunction.getUsers(staffId);
    	if(user != null){
	    	try{
	    		userDetails = user.getJSONArray("users");
	    		for(int i = 0; i < userDetails.length(); i++){
	    			JSONObject ud = userDetails.getJSONObject(i);
	    			int userId = ud.getInt(KEY_USER_ID);
	    			String userEmail = ud.getString(KEY_USER_EMAIL);
	    			Users currUser = new Users(userId, userEmail);
	    			db.addStaff(currUser);
	    			Log.d("Insert: ", " Inserting User...");
	    		}
	    	}
	    	catch (JSONException e) {
	       	 	e.printStackTrace();                       
	        }
    	}
    }
}
