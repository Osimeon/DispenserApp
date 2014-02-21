package com.dsw.dispenserapp;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dsw.dispenserdata.DbHandler;
import com.dsw.dispenserdata.IssueTypes;
import com.dsw.dispenserdata.Users;

@SuppressLint("NewApi")
public class CreateIssueActivity extends Activity{
	
	public static final String TAG_WATERPOINT_ID = "waterpoint_id";
	public static final String TAG_WATERPOINT_NAME = "waterpoint_name";
	
	private static String KEY_SUCCESS = "success";
	UserFunctions funcs = new UserFunctions();
	ProgressDialog dialog; 
	ConnectionDetector cd;
	AlertDialogManager alert = new AlertDialogManager();

	String waterId = null; 
	String isTypeId = null;
	String userComments = null;
	String dispStatus = null;
	String userAssigned = null;
	String resolved = null;

	Spinner spinnerIssueTypes;	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_issue_activity);
		
		Intent in = getIntent();
		waterId = in.getStringExtra(TAG_WATERPOINT_ID);
		String waterpointName = in.getStringExtra(TAG_WATERPOINT_NAME);

		spinnerIssueTypes = (Spinner) findViewById(R.id.spinnerIssueType);
		
		Button createIssue = (Button) findViewById(R.id.btnCreateIssue);
		TextView wpName = (TextView) findViewById(R.id.txtCreateIssue);
		
		wpName.setText("Waterpoint Name: "+waterpointName);
		
		createIssue.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				invalidate();
			}
		});
		
		/*
		 * Handle Spinner Click Events
		 */
		spinnerIssueTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
				int typeId =  ((IssueTypes) spinnerIssueTypes.getSelectedItem ()).getIssueTypeId() ;
				isTypeId = String.valueOf(typeId);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		
		loadSpinnerData();
	}
	
	/*
	 * Get Current App User
	 */
	public String user(){
		DbHandler db = new DbHandler(this);
		Users currUser = db.getUser();
		String staffNumber = String.valueOf(currUser.getStaffNumber());
		return staffNumber;
	}
	
	/*
	 * Validate variables 
	 * Proceed to beforeSync() when true
	 * Make toast if false
	 */
	public void invalidate(){
		EditText comments = (EditText) findViewById(R.id.txtUserComments);
		RadioGroup dispFunc = (RadioGroup) findViewById(R.id.funcGroup);
		RadioButton radiovalue =  (RadioButton)this.findViewById(dispFunc.getCheckedRadioButtonId());
		
		String userComments = comments.getText().toString();
		String status = radiovalue.getText().toString();
		
		if((waterId.isEmpty())||(isTypeId.isEmpty())||status.isEmpty()){
			Toast.makeText(CreateIssueActivity.this, "Please Fill All Fields Before Proceeding", Toast.LENGTH_LONG).show();
		}
		
		else{			
			this.userComments = userComments;
			this.dispStatus = status;
			beforeSync();
		}
	}
	
	/*
	 * Check for internet connection
	 * Ask user if they are ready to resolve issue
	 * If wants to resolve issue add @param userAssigned, @param resolved
	 * Post issue
	 */
	public void beforeSync(){
    	cd = new ConnectionDetector(getApplicationContext());
		if (!cd.isConnectingToInternet()) {
			alert.showAlertDialog(CreateIssueActivity.this,"Internet Connection Error","Please connect to a working Internet connection", false);
			return;
		}
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(CreateIssueActivity.this);
        alertDialog.setTitle("Resolve Issue");
        alertDialog.setMessage("Do You Want To Resolve This Issue?");
        alertDialog.setIcon(R.drawable.success);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
            	userAssigned = user();
            	resolved = "Yes";
            	new MyResolveCreateTask().execute();
            }
        });
 
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {            
            	new MyNewTask().execute();            
            	dialog.cancel();
            }
        });
        alertDialog.show();		
    }
	
	/*
	 * Message to display in-case server is not responding
	 * User will be notified
	 */
	public void serverError(){
    	alert.showAlertDialog(CreateIssueActivity.this,"Network Error","Could Not Establish Connection To Server, Check Network", false);
		return;
    }
	
	/*
	 * Get issue type spinner data
	 */
	private void loadSpinnerData() {
		DbHandler db = new DbHandler(getApplicationContext());
		List<IssueTypes> issueTypesLabel = db.getIssueTypesName();
		
		ArrayAdapter<IssueTypes> typeAdapter =  new ArrayAdapter<IssueTypes>(this, android.R.layout.simple_spinner_item, issueTypesLabel);		
		typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerIssueTypes.setAdapter(typeAdapter);
	}
	
	/*
	 * Run On UiThread And Post Issue
	 * Inform user the status of the issue
	 */
	private class MyNewTask extends AsyncTask<String,String,String>{		
		String indicator = null;
		
		@Override
        protected void onPreExecute(){
			dialog = new ProgressDialog(CreateIssueActivity.this);
			dialog.setIndeterminate(true);
			dialog.setTitle("Create Issue");
			dialog.setMessage("Processing...");
			dialog.setCancelable(true);
			dialog.show();
		}
		
		@Override
		protected String doInBackground(String... params) {
			JSONObject json = funcs.createIssue(waterId, isTypeId, userComments, dispStatus, userAssigned, resolved, getApplicationContext());
			if(json != null){
				try {
					indicator = json.getString(KEY_SUCCESS);				
				}
				catch (JSONException e) {
					e.printStackTrace();
				}
			}
			else{
            	dialog.dismiss();            	
            }			
			return indicator;
		}
		
		@Override
        protected void onPostExecute(String flag){
			super.onPostExecute(flag); 
			dialog.dismiss();
			try{
				int indicator = Integer.parseInt(flag);	
				if(indicator == 1){
					Toast.makeText(getApplicationContext(), "Issue Successfully Created", Toast.LENGTH_LONG).show();
					Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
					startActivity(i);
					finish();
				}
				else{
					Toast.makeText(getApplicationContext(), "Could Not Create Issue Try Again", Toast.LENGTH_LONG).show();
					Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
					startActivity(i);
					finish();
				}
			}
			catch(Exception e){
				serverError();
			}
		}
	}
	
	/*
	 * Run On UiThread And Post Issue
	 * Inform user the status of the issue
	 */
	private class MyResolveCreateTask extends AsyncTask<String,String,String>{		
		String indicator = null;
		
		@Override
        protected void onPreExecute(){
			dialog = new ProgressDialog(CreateIssueActivity.this);
			dialog.setIndeterminate(true);
			dialog.setTitle("Create Issue");
			dialog.setMessage("Processing...");
			dialog.setCancelable(true);
			dialog.show();
		}
		
		@Override
		protected String doInBackground(String... params) {
			JSONObject json = funcs.createIssueResolve(waterId, isTypeId, userComments, dispStatus, userAssigned, resolved, getApplicationContext());
			if(json != null){
				try {
					indicator = json.getString(KEY_SUCCESS);				
				}
				catch (JSONException e) {
					e.printStackTrace();
				}
			}
			else{
            	dialog.dismiss();            	
            }			
			return indicator;
		}
		
		@Override
        protected void onPostExecute(String flag){
			super.onPostExecute(flag); 
			dialog.dismiss();
			try{
				int indicator = Integer.parseInt(flag);	
				if(indicator == 1){
					Toast.makeText(getApplicationContext(), "Issue Successfully Created", Toast.LENGTH_LONG).show();
					Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
					startActivity(i);
					finish();
				}
				else{
					Toast.makeText(getApplicationContext(), "Could Not Create Issue Try Again", Toast.LENGTH_LONG).show();
					Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
					startActivity(i);
					finish();
				}
			}
			catch(Exception e){
				serverError();
			}
		}
	}
}
