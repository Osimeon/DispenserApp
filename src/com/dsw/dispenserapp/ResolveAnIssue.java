package com.dsw.dispenserapp;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dsw.dispenserdata.DbHandler;

public class ResolveAnIssue extends Activity{
	String issueId;
	String typeId;
	String wpId;
	String issueType;
	String waterpointName;
	String dateCreated;
	UserFunctions funcs = new UserFunctions();
	ProgressDialog dialog; 
	ConnectionDetector cd;
	AlertDialogManager alert = new AlertDialogManager();
	Context _context;
	String localIssueId = null;
	private static String KEY_SUCCESS = "success";
	DbHandler db = new DbHandler(this);
	
	@Override
	protected void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);
		setContentView(R.layout.resolve_my_issue);
		
		_context = this.getApplicationContext();
		
		Intent in = getIntent();
		issueId = in.getStringExtra("issueId");
		typeId = in.getStringExtra("typeId");
		wpId = in.getStringExtra("wpId");
		issueType = in.getStringExtra("issueType");
		waterpointName = in.getStringExtra("wpName");
		dateCreated = in.getStringExtra("dateCreated");
		
		TextView viewIssueId = (TextView) findViewById(R.id.resolveMyIssueID);
		TextView viewWaterpointId = (TextView) findViewById(R.id.resolveMyIssueWpID);
		TextView viewIssueTypeId = (TextView) findViewById(R.id.resolveMyIssueTypeID);
		EditText viewIssueType = (EditText) findViewById(R.id.txtResolveMyIssueTypeName);
		EditText viewWaterpointName = (EditText) findViewById(R.id.txtResolveMyIssueWpName);
		EditText viewDateCreated = (EditText) findViewById(R.id.txtResolveMyIssueDate);
		
		viewIssueId.setText(issueId);
		localIssueId = issueId;
		viewWaterpointId.setText(wpId);
		viewIssueTypeId.setText(issueId);
		viewIssueType.setText(issueType);
		viewWaterpointName.setText(waterpointName);
		viewDateCreated.setText(dateCreated);
		
		Button resolve = (Button) findViewById(R.id.btnResolveIssue);
		
				
		resolve.setOnClickListener(new View.OnClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				beforeSync(); 
			}
		});
	}
	
	public void beforeSync(){
    	cd = new ConnectionDetector(getApplicationContext());
		if (!cd.isConnectingToInternet()) {
			alert.showAlertDialog(ResolveAnIssue.this,"Internet Connection Error","Please connect to a working Internet connection", false);
			return;
		}
		new MyNewTask().execute();
    }
	
	public void serverError(){
    	alert.showAlertDialog(ResolveAnIssue.this,"Network Error","Could Not Establish Connection To Server, Check Network", false);
		return;
    }
	
	private class MyNewTask extends AsyncTask<String,String,String>{
		String indicator = null;
		@Override
        protected void onPreExecute(){
			dialog = new ProgressDialog(ResolveAnIssue.this);
			dialog.setIndeterminate(true);
			dialog.setTitle("Resolve Issue");
			dialog.setMessage("Processing...");
			dialog.setCancelable(true);
			dialog.show();
		}
		
		@Override
		protected String doInBackground(String... params) {			
			JSONObject json = funcs.resolveIssue(localIssueId, _context);
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
				if(Integer.parseInt(flag) == 1){
					db.deleteIssue(Integer.parseInt(localIssueId));				
					Intent i = new Intent(_context, DashboardActivity.class);
					startActivity(i);
					finish();
					Toast.makeText(_context, "Issue Resolved", Toast.LENGTH_LONG).show();
				}
				else{
					Intent i = new Intent(_context, DashboardActivity.class);
					startActivity(i);
					finish();
					Toast.makeText(_context, "Could Not Resolve Issue Try Again", Toast.LENGTH_LONG).show();
				}
			}
			catch(Exception e){
				serverError();
			}
		}
	}
}
