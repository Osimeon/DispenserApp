package com.dsw.dispenserdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class DbHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "issues_db";
 
    // Login table name
    public  static final String TABLE_USERS = "login";
    public  static final String TABLE_WATERPOINTS = "waterpoints";
    public  static final String TABLE_ISSUE_TYPES = "issue_types";
    public  static final String TABLE_ISSUES = "issues";
    public  static final String TABLE_DISTRICTS = "districts";
    public  static final String TABLE_SUBLOCATIONS = "sublocations";
    public  static final String TABLE_VILLAGES = "villages";
    
    //District Table Columns names
    private static final String KEY_DISTRICT_NAME = "district_name";
    
    //Sublocation Table Columns names
    private static final String KEY_SUBLOCATION_NAME = "sublocation_name";
    private static final String KEY_SUBLOCATION_DISTRICT = "district";
    
    //Villages Table Columns names
    private static final String KEY_VILLAGE_NAME = "village_name";
    private static final String KEY_VILLAGE_SUBLOCATION = "sublocation";
    
    // Login Table Columns names
    private static final String KEY_ID = "emp_no";
    private static final String KEY_EMAIL = "email";
    
    //Waterpoints Table Columns names
    private static final String KEY_WATERPOINT_ID = "waterpoint_id";
    private static final String KEY_WATERPOINT_NAME = "waterpoint_name";
    private static final String KEY_WATERPOINT_VILLAGE = "village";
    
    //Issue Types Table Columns names
    private static final String KEY_ISSUE_TYPE_ID = "issuetype_id";
    private static final String KEY_ISSUE_TYPE = "issue_type";
    private static final String KEY_ISSUE_TYPE_NAME = "issue_type_name";
    
    //Issues Table Columns names
    private static final String KEY_ISSUE_ID = "issue_id";
    private static final String KEY_ISSUE_WATERPOINT_ID = "issue_waterpoint_id";
    private static final String KEY_ISSUE_DATE_CREATED = "date_cretead";
    private static final String KEY_ISSUE_RESOLVED = "resolved";
    private static final String KEY_ISSUE_ISSUE_TYPE = "issue_type_id";
    
    Context _context;
    
    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        _context = context;
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    	String CREATE_DISTRICT_TABLE = "CREATE TABLE " + TABLE_DISTRICTS + "("
    			+ KEY_DISTRICT_NAME + " TEXT " + ")";
    	
    	String CREATE_SUBLOCATION_TABLE = "CREATE TABLE " + TABLE_SUBLOCATIONS + "("
    			+ KEY_SUBLOCATION_NAME + " TEXT, " 
    			+ KEY_SUBLOCATION_DISTRICT + " TEXT " + ")"; 
    	
    	String CREATE_VILLAGES_TABLE = "CREATE TABLE " + TABLE_VILLAGES + "("
    			+ KEY_VILLAGE_NAME + " TEXT, " 
    			+ KEY_VILLAGE_SUBLOCATION + " TEXT " + ")";
    	
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER ,"
                + KEY_EMAIL + " TEXT " + ")";
        
        String CREATE_TABLE_WATERPOINTS = "CREATE TABLE " + TABLE_WATERPOINTS + "("
        		+ KEY_WATERPOINT_ID + " INTEGER , "
        		+ KEY_WATERPOINT_NAME + " TEXT, " 
        		+ KEY_WATERPOINT_VILLAGE + " TEXT "+ ")";
        
        String CREATE_TABLE_ISSUE_TYPES = "CREATE TABLE " + TABLE_ISSUE_TYPES + "("
        		+ KEY_ISSUE_TYPE_ID + " INTEGER , " 
        		+ KEY_ISSUE_TYPE + " TEXT, "
        		+ KEY_ISSUE_TYPE_NAME + " TEXT " + ")";
        
        String CREATE_TABLE_ISSUES = "CREATE TABLE " + TABLE_ISSUES + "("
        		+ KEY_ISSUE_ID + " INTEGER, " 
        		+ KEY_ISSUE_WATERPOINT_ID + " INTEGER, "
        		+ KEY_ISSUE_DATE_CREATED + " TEXT, "
        		+ KEY_ISSUE_RESOLVED + " TEXT, "
        		+ KEY_ISSUE_ISSUE_TYPE + " INTEGER " + ")";
        db.execSQL(CREATE_TABLE_WATERPOINTS);
        db.execSQL(CREATE_TABLE_ISSUE_TYPES);
        db.execSQL(CREATE_TABLE_ISSUES);
        db.execSQL(CREATE_LOGIN_TABLE);   
        db.execSQL(CREATE_DISTRICT_TABLE);
        db.execSQL(CREATE_SUBLOCATION_TABLE);
        db.execSQL(CREATE_VILLAGES_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
    	if(newVersion > oldVersion)
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WATERPOINTS);
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ISSUE_TYPES);
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ISSUES);
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISTRICTS);
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBLOCATIONS);
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VILLAGES);
        
        // Create tables again
        onCreate(db);
    }
    
    //Add A District To DB
    public void addDistrict(Districts district){
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(KEY_DISTRICT_NAME, district.getDistrictName());
    	db.insert(TABLE_DISTRICTS, null, values);
    	db.close();
    }
    
    //Add A Sublocation To DB
    public void addSublocation(Sublocations sublocation){
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(KEY_SUBLOCATION_NAME, sublocation.getSublocationName());
    	values.put(KEY_SUBLOCATION_DISTRICT, sublocation.getDistrictName());
    	db.insert(TABLE_SUBLOCATIONS, null, values);
    	db.close();
    }
    
    //Add A Village To DB
    public void addVillage(Villages village){
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(KEY_VILLAGE_NAME, village.getVillageName());
    	values.put(KEY_VILLAGE_SUBLOCATION, village.getSublocation());
    	db.insert(TABLE_VILLAGES, null, values);
    	db.close();
    }
    
    //Add Current User To DB
    public void addStaff(Users user){
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(KEY_ID, user.getStaffNumber());
    	values.put(KEY_EMAIL, user.getStaffEmail());
    	db.insert(TABLE_USERS, null, values);
        db.close(); 
    }
    
    //Add An Issue Type To DB
    public void addIssueType(IssueType type){
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(KEY_ISSUE_TYPE_ID, type.getIssueTypeId());
    	values.put(KEY_ISSUE_TYPE, type.getIssueType());
    	values.put(KEY_ISSUE_TYPE_NAME, type.getIssueTypeName());
    	db.insert(TABLE_ISSUE_TYPES, null, values);
    	db.close();
    }
    
    //Add An Issue To DB
    public void addIssue(Issues issue){
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(KEY_ISSUE_ID, issue.getIssueId());
    	values.put(KEY_ISSUE_WATERPOINT_ID, issue.getIssueWaterpointId());
    	values.put(KEY_ISSUE_DATE_CREATED, issue.getIssueDateCreated());
    	values.put(KEY_ISSUE_ISSUE_TYPE, issue.getIssueIssueType());
    	values.put(KEY_ISSUE_RESOLVED, issue.getIssueStatus());
    	db.insert(TABLE_ISSUES, null, values);
    	db.close();
    }
    
    //Delete An Issue From Que
    public void deleteIssue(int id){
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete(TABLE_ISSUES, KEY_ISSUE_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }
    
    //All Districts
    public List<Districts> getDistricts(){
    	List<Districts> allDistricts = new ArrayList<Districts>();
    	String selectQuery = "SELECT DISTINCT district_name FROM " + TABLE_DISTRICTS + " ORDER BY district_name ASC";
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	
    	if(cursor.moveToFirst()){
    		do{
    			Districts district = new Districts();
    			district.setDistrictName(cursor.getString(0));
    			allDistricts.add(district);
    		} while(cursor.moveToNext());    		
    	}
    	cursor.close();
		db.close();
		return allDistricts;
    }
    
    //All Sublocations    
    public List<Sublocations> getSublocations(String district){
    	List<Sublocations> sublocations = new ArrayList<Sublocations>();
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.rawQuery("SELECT DISTINCT sublocation_name FROM " + TABLE_SUBLOCATIONS + " WHERE district = ?" + "ORDER BY sublocation_name ASC", new String [] {district});    	
    	if(cursor.moveToFirst()){
    		do{
    			Sublocations sublocation = new Sublocations();
    			sublocation.setSublocationName(cursor.getString(0));
    			sublocations.add(sublocation);
    		} while(cursor.moveToNext());    		
    	}
    	cursor.close();
		db.close();
		return sublocations;
    }
    
    //All Villages
    public List<Villages> getVillages(String sublocation){
    	List<Villages> villages = new ArrayList<Villages>();
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.rawQuery("SELECT DISTINCT village_name FROM " + TABLE_VILLAGES + " WHERE sublocation = ?" + "ORDER BY village_name ASC", new String [] {sublocation});
    	if(cursor.moveToFirst()){
    		do{
    			Villages village = new Villages();
    			village.setVillageName(cursor.getString(0));
    			villages.add(village);
    		} while(cursor.moveToNext());    		
    	}
    	cursor.close();
		db.close();
		return villages;
    }
    
    //All unresolved issues assigned to current user
    public List<Issues> getIssues(){
    	List<Issues> allIssues = new ArrayList<Issues>();
    	String issueQuery = "SELECT * FROM " + TABLE_ISSUES;
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.rawQuery(issueQuery, null);
    	try{
    		if(cursor.moveToFirst()){
        		do{
        			Issues issue = new Issues();
        			issue.setIssueId(cursor.getInt(0));
        			issue.setIssueWaterpointId(cursor.getInt(1));
        			issue.setIssueDateCreated(cursor.getString(2));
        			issue.setIssueStatus(cursor.getString(3));
        			issue.setIssueIssueType(cursor.getInt(4));
        			allIssues.add(issue);
        		}while(cursor.moveToNext());
        	}
        	
        	cursor.close();
    		db.close();
    	}
    	catch(Exception e){
    		//Toast.makeText(_context, "Waterpoints Do Not Belong To Your Region", Toast.LENGTH_LONG).show();
    	}
    	
		return allIssues;
    }
    
    //Getting a single issue
    public Issues getIssue(int issueId){
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.query(TABLE_ISSUES, new String[] { KEY_ISSUE_ID,  
				KEY_ISSUE_WATERPOINT_ID,
				KEY_ISSUE_DATE_CREATED,
				KEY_ISSUE_RESOLVED,
				KEY_ISSUE_ISSUE_TYPE
				}, KEY_ISSUE_ID + "=?",
				new String[] { String.valueOf(issueId) }, null, null, null,null);
    	if (cursor != null)
			cursor.moveToFirst();
    	Issues issue = new Issues(cursor.getInt(0), cursor.getInt(1),
    			cursor.getString(2),
    			cursor.getString(3),
    			cursor.getInt(4));
    	cursor.close();
		db.close();
    	return issue;
    }
    
    //Getting a single district
    public Districts getDistrict(String districtName){
    	SQLiteDatabase db = this.getReadableDatabase();
    	Districts district = null;
    	Cursor cursor = db.query(TABLE_DISTRICTS, new String[] {KEY_DISTRICT_NAME}, KEY_DISTRICT_NAME + "=?",
				new String[] { districtName }, null, null, null,null);    	
    	try{
    		if (cursor != null)
    			cursor.moveToFirst();
    		district = new Districts(cursor.getString(0));
    		cursor.close();
    		db.close();
    	}
    	catch(Exception e){
    		Log.d("Districts", "Error Getting District");
    	}
    	return district;
    }
    
    //Getting a single sublocation 
    public Sublocations getSublocation(String districtName){
    	SQLiteDatabase db = this.getReadableDatabase();
    	Sublocations sublocation = null;
    	Cursor cursor = db.query(TABLE_SUBLOCATIONS, new String[] {KEY_SUBLOCATION_NAME}, KEY_SUBLOCATION_DISTRICT + "=?",
				new String[] { districtName }, null, null, null,null);    	
    	try{
    		if (cursor != null)
    			cursor.moveToFirst();
    		sublocation = new Sublocations(cursor.getString(0), cursor.getString(1));
    		cursor.close();
    		db.close();
    	}
    	catch(Exception e){
    		Log.d("Districts", "Error Getting District");
    	}
    	return sublocation;
    }
    
    //Getting a single village
    public Villages getVillage(String sublocationName){
    	SQLiteDatabase db = this.getReadableDatabase();
    	Villages village = null;
    	Cursor cursor = db.query(TABLE_VILLAGES, new String[] {KEY_VILLAGE_NAME}, KEY_VILLAGE_SUBLOCATION + "=?",
				new String[] { sublocationName }, null, null, null,null);    	
    	try{
    		if (cursor != null)
    			cursor.moveToFirst();
    		village = new Villages(cursor.getString(0), cursor.getString(1));
    		cursor.close();
    		db.close();
    	}
    	catch(Exception e){
    		Log.d("Districts", "Error Getting District");
    	}
    	return village;
    }
    
    //Getting a list of waterpoints
    public List<Waterpoints> getWaterpoints(String village){
    	List<Waterpoints> allWaterpoints = new ArrayList<Waterpoints>();
    	SQLiteDatabase db = this.getReadableDatabase();    	    	
    	Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_WATERPOINTS + " WHERE village =?" + " ORDER BY waterpoint_name ASC ", new String [] {village});    	
    	if(cursor.moveToFirst()){
    		do{
    			Waterpoints waterpoint = new Waterpoints();
    			waterpoint.setWaterpointId(cursor.getInt(0));
    			waterpoint.setWaterpointName(cursor.getString(1));
    			waterpoint.setVillage(cursor.getString(2));
    			allWaterpoints.add(waterpoint);
    		}while(cursor.moveToNext());
    	}    	
    	cursor.close();
		db.close();
		return allWaterpoints;
    }
    
    
    //Getting a list of waterpoints
    public List<Waterpoints> getWaterpoints(){
    	List<Waterpoints> allWaterpoints = new ArrayList<Waterpoints>();
    	String issueQuery = "SELECT * FROM " + TABLE_WATERPOINTS;
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.rawQuery(issueQuery, null);
    	
    	if(cursor.moveToFirst()){
    		do{
    			Waterpoints waterpoint = new Waterpoints();
    			waterpoint.setWaterpointId(cursor.getInt(0));
    			waterpoint.setWaterpointName(cursor.getString(1));
    			waterpoint.setVillage(cursor.getString(2));
    			allWaterpoints.add(waterpoint);
    		}while(cursor.moveToNext());
    	}    	
    	cursor.close();
		db.close();
		return allWaterpoints;
    }
    
    public List<String> getAllIssueTypeLabels(){
    	List<String> labels = new ArrayList<String>();
    	
    	// Select All Query
        String selectQuery = "SELECT  issuetype_id, issue_type_name FROM " + TABLE_ISSUE_TYPES;
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();        
        return labels;
    }
    
    //Getting a single water point
    public Waterpoints getWaterpoint(int waterpointId){
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.query(TABLE_WATERPOINTS, new String[] { KEY_WATERPOINT_ID,  
				KEY_WATERPOINT_NAME
				}, KEY_WATERPOINT_ID + "=?",
				new String[] { String.valueOf(waterpointId) }, null, null, null,null);
    	if (cursor != null){
			cursor.moveToFirst();
	    	Waterpoints waterpoint = new Waterpoints(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
	    	cursor.close();
			db.close();
	    	return waterpoint;
    	}
    	return null;
    }
    
    //Getting all issue types
    public List<IssueType> getIssueTypes(){
    	List<IssueType> allTypes = new ArrayList<IssueType>();
    	String issueQuery = "SELECT * FROM " + TABLE_ISSUE_TYPES;
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.rawQuery(issueQuery, null);
    	
    	if(cursor.moveToFirst()){
    		do{
    			IssueType type = new IssueType();
    			type.setIssueTypeId(cursor.getInt(0));
    			type.setIssueType(cursor.getString(1));
    			type.setIssueTypeName(cursor.getString(2));
    			allTypes.add(type);
    		}while(cursor.moveToNext());
    	}    	
    	cursor.close();
		db.close();
		return allTypes;
    }
    
    //Getting all issue types
    public List<IssueTypes> getIssueTypesName(){
    	List<IssueTypes> allTypes = new ArrayList<IssueTypes>();
    	String issueQuery = "SELECT issuetype_id, issue_type_name FROM " + TABLE_ISSUE_TYPES;
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.rawQuery(issueQuery, null);
    	
    	if(cursor.moveToFirst()){
    		do{
    			IssueTypes type = new IssueTypes();
    			type.setIssueTypeId(cursor.getInt(0));
    			type.setIssueTypeName(cursor.getString(1));
    			allTypes.add(type);
    		}while(cursor.moveToNext());
    	}    	
    	cursor.close();
		db.close();
		return allTypes;
    }
    
    
    //Getting a single issue type
    public IssueType getIssueType(int typeId){
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.query(TABLE_ISSUE_TYPES, new String[] { KEY_ISSUE_TYPE_ID,  
				KEY_ISSUE_TYPE,
				KEY_ISSUE_TYPE_NAME
				}, KEY_ISSUE_TYPE_ID + "=?",
				new String[] { String.valueOf(typeId) }, null, null, null,null);
    	if (cursor != null)
			cursor.moveToFirst();
    	IssueType type= new IssueType(cursor.getInt(0), cursor.getString(1),
    			cursor.getString(2));
    	cursor.close();
		db.close();
    	return type;
    }
    
    public void addWaterpoint(Waterpoints waterpoint){
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(KEY_WATERPOINT_ID, waterpoint.getWaterpointId());
    	values.put(KEY_WATERPOINT_NAME, waterpoint.getWaterpointName());
    	values.put(KEY_WATERPOINT_VILLAGE, waterpoint.getVillage());
    	db.insert(TABLE_WATERPOINTS, null, values);
    	db.close();
    }
    
    //Getting the current user
    public Users getUser(){
    	String selectQuery = "SELECT  * FROM " + TABLE_USERS;
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
			cursor.moveToFirst();
        
        Users user = new Users(cursor.getInt(0),cursor.getString(1));
        cursor.close();
		db.close();
        return user;
    }
    
    
    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String,String> user = new HashMap<String,String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;
          
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.put("uid", cursor.getString(0));
            user.put("email", cursor.getString(1));
        }
        cursor.close();
        db.close();
        // return user
        return user;
    }
 
    /**
     * Getting user login status
     * return true if rows are there in table
     * */
    public int getRowCount(String tableName) {
        String countQuery = "SELECT  * FROM " + tableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();   
        return rowCount;
    }
     
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();    
        return rowCount;
    }
    
    public int getIssueTypeCount(){
    	String countQuery = "SELECT count(issuetype_id) as typeCount FROM " + TABLE_ISSUE_TYPES;
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.rawQuery(countQuery, null);
    	int rowCount = cursor.getCount();
    	db.close();
    	cursor.close();
    	return rowCount;
    }
    
    /**
     * Re crate database
     * Delete all tables and create them again
     * */
    public void resetTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, null, null);
        db.delete(TABLE_ISSUES, null, null);
        db.close();
    } 
    
    public void resetWaterpoints(){
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete(TABLE_WATERPOINTS, null, null);
    }
    
    public void deleteFromTable(String tableName){
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete(tableName, null, null);
    }
}	
