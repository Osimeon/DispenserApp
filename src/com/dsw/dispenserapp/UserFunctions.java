package com.dsw.dispenserapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.dsw.dispenserdata.DbHandler;
import com.dsw.dispenserdata.Users;
 
public class UserFunctions {
	
    private JSONParser jsonParser;
    //private static String universalURL = "http://62.24.108.175:81/ipa_mis/mobileapp/index.php";
    private static String universalURL = "http://192.168.3.76:81/ipa_mis/mobileapp/index.php";
     
    private static String login_tag = "login";
    private static String waterpoints_tag = "waterpoints";
    private static String issuetype_tag = "issuetypes";
    private static String issues_tag = "issues";
    private static String users_tag = "users";
    private static String resolve_tag = "resolve";
    private static String count_tag = "waterpointscount";
    private static String create_tag = "createissue";
    private static String type_count = "issuetypecount";
    
    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }
    
    //Get issue type count
    public JSONObject getIssueTypeCount(){
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", type_count));
        JSONObject json = jsonParser.getJSONFromUrl(universalURL, params);
        if(json != null){
        	return json;
        }
        else {
        	return null;
        }
    }
    
    //Get water point count
    public JSONObject getWaterpointCount(String staffId){
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", count_tag));
        params.add(new BasicNameValuePair("staffId", staffId));
        JSONObject json = jsonParser.getJSONFromUrl(universalURL, params);
        if(json != null){
        	return json;
        }
        else {
        	return null;
        }
    }
    
    //Create an issue
    public JSONObject createIssue(String wpId, String isType, String comments, String status, String userAssigned, String resolved, Context context){
    	DbHandler db = new DbHandler(context);
    	Users currentUser = db.getUser();
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair("tag", create_tag));
    	params.add(new BasicNameValuePair("wpId", wpId));
    	params.add(new BasicNameValuePair("typeId", isType));
    	params.add(new BasicNameValuePair("user", String.valueOf(currentUser.getStaffNumber())));
    	params.add(new BasicNameValuePair("comments", comments));
    	params.add(new BasicNameValuePair("status", status));
    	params.add(new BasicNameValuePair("user_assigned", userAssigned));
    	params.add(new BasicNameValuePair("resolved", resolved));
    	JSONObject json = jsonParser.getJSONFromUrl(universalURL, params);
    	if(json != null){
        	return json;
        }
        else {
        	return null;
        }
    }
    
  //Create an issue
    public JSONObject createIssueResolve(String wpId, String isType, String comments, String status, String userAssigned, String resolved, Context context){
    	DbHandler db = new DbHandler(context);
    	Users currentUser = db.getUser();
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair("tag", "createresolve"));
    	params.add(new BasicNameValuePair("wpId", wpId));
    	params.add(new BasicNameValuePair("typeId", isType));
    	params.add(new BasicNameValuePair("user", String.valueOf(currentUser.getStaffNumber())));
    	params.add(new BasicNameValuePair("comments", comments));
    	params.add(new BasicNameValuePair("status", status));
    	params.add(new BasicNameValuePair("user_assigned", userAssigned));
    	params.add(new BasicNameValuePair("resolved", resolved));
    	JSONObject json = jsonParser.getJSONFromUrl(universalURL, params);
    	if(json != null){
        	return json;
        }
        else {
        	return null;
        }
    }
    
    //Resolve an issue
    public JSONObject resolveIssue(String issueId, Context context){
    	DbHandler db = new DbHandler(context);
    	Users currentUser = db.getUser();
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair("tag", resolve_tag));
    	params.add(new BasicNameValuePair("issue_id", issueId));
    	params.add(new BasicNameValuePair("emp_no", String.valueOf(currentUser.getStaffNumber())));
    	JSONObject json = jsonParser.getJSONFromUrl(universalURL, params);
    	if(json != null){
        	return json;
        }
        else {
        	return null;
        }
    }
    
    /**
     * function make Login Request
     * @param email
     * @param password
     * */
    public JSONObject loginUser(String staffId, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("staffid", staffId));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(universalURL, params);
        
        if(json != null){
        	return json;
        }
        else {
        	return null;
        }
    }
    
    public JSONObject getUsers(String empId){
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair("tag", users_tag));
    	params.add(new BasicNameValuePair("staff_id", empId));
    	JSONObject json = jsonParser.getJSONFromUrl(universalURL, params);
    	if(json != null){
        	return json;
        }
        else {
        	return null;
        }
    }
    
    public JSONObject getWateropints(String staffId){    	
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair("tag", waterpoints_tag));
    	params.add(new BasicNameValuePair("staffId", staffId));
    	JSONObject json = jsonParser.getJSONFromUrl(universalURL, params);
    	if(json != null){
        	return json;
        }
        else {
        	return null;
        }
    }
    
    public JSONObject getIssues(String empNumber){
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair("tag", issues_tag));
    	params.add(new BasicNameValuePair("staff_number", empNumber));
    	JSONObject json = jsonParser.getJSONFromUrl(universalURL, params);
    	if(json != null){
        	return json;
        }
        else {
        	return null;
        }
    }
    
    public JSONObject getIssueTypes(){
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair("tag", issuetype_tag));
    	JSONObject json = jsonParser.getJSONFromUrl(universalURL, params);
    	if(json != null){
        	return json;
        }
        else {
        	return null;
        }
    }
    
    
    
    /**
     * Function get Login status
     * */
    public boolean isUserLoggedIn(Context context){
        DbHandler db = new DbHandler(context);
        int count = db.getRowCount();
        if(count > 0){
            return true;
        }
        return false;
    }
     
    /**
     * Function to logout user
     * Reset Database
     * */
    public boolean logoutUser(Context context){
        DbHandler db = new DbHandler(context);
        db.resetTables();
        Log.d("Log Out","Logging out user....");
        return true;
    }     
}