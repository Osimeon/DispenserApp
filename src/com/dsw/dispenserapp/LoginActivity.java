package com.dsw.dispenserapp;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dsw.dispenserdata.DbHandler;

@SuppressLint("NewApi")
public class LoginActivity extends Activity {
    Button btnLogin;
    EditText inputJobId, inputPassword;
    TextView loginErrorMsg;
    ProgressDialog dialog; 
    DbHandler db = new DbHandler(this);
    UserFunctions funcs = new UserFunctions();    
    ConnectionDetector cd;
	AlertDialogManager alert = new AlertDialogManager();
	Sync sync = new Sync(this);
	public static final String KEY_USER_ID = "staff_number";
	public static final String KEY_USER_EMAIL = "email";
	
	
    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    String staffId;
    String password;
    String responseMsg = "";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
 
        // Importing all assets like buttons, text fields
        inputJobId = (EditText) findViewById(R.id.loginNumber);
        inputPassword = (EditText) findViewById(R.id.loginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);
 
        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
            	if((inputJobId.getText().toString().isEmpty()) || (inputPassword.getText().toString().isEmpty())){
            		Toast.makeText(getApplicationContext(), "Please Fill All Details Before Proceeding!", Toast.LENGTH_SHORT).show();
            	}
            	else{
            		beforeSync();
            	}
            }
        });
    }
    
    public void beforeSync(){
    	cd = new ConnectionDetector(getApplicationContext());
		if (!cd.isConnectingToInternet()) {
			alert.showAlertDialog(LoginActivity.this,"Internet Connection Error","Please connect to a working Internet connection", false);
			return;
		}
		new MyNewTask().execute();
    }
    
    public void serverError(){
    	alert.showAlertDialog(LoginActivity.this,"Network Error","Could Not Establish Connection To Server, Check Network", false);
		return;
    }
    
    private class MyNewTask extends AsyncTask<String,String,String>{
    	String flag = null;
    	
    	@Override
        protected void onPreExecute(){
			dialog = new ProgressDialog(LoginActivity.this);
			dialog.setIndeterminate(true);
			dialog.setTitle("Log In");
			dialog.setMessage("Processing...");
			dialog.setCancelable(true);
			dialog.show();			
		}
    	
    	@Override
		protected String doInBackground(String... params) {
    		staffId = inputJobId.getText().toString();
            password = inputPassword.getText().toString();     
            JSONObject json = funcs.loginUser(staffId, password);   
            if(json != null){
	            try {
	            	if (json.getString(KEY_SUCCESS) != null) {
	                    String res = json.getString(KEY_SUCCESS); 
	                    flag = res;
	                    if(Integer.parseInt(res) == 1){
	                    	funcs.logoutUser(getApplicationContext());
	                    	sync.syncIssues(db,staffId);
	                    	sync.syncIssueTypes(db);
	                    	sync.syncUserDetails(db,staffId);
	                    	sync.syncWaterpoints(db, staffId);                    	
	                    }
	                    else{                        
	                        dialog.dismiss();
	                    }
	                }
	            }
	            catch (JSONException e) {
	            	 e.printStackTrace();
	            }
            }
            else{
            	//Toast.makeText(getApplicationContext(), "Server Response Error", Toast.LENGTH_LONG).show();
            	dialog.dismiss();            	
            }
    		return flag;   		
    	}
    
    	@Override
        protected void onPostExecute(String flag){
    		super.onPostExecute(flag);    		
			dialog.dismiss();
			try{
				int indicator = Integer.parseInt(flag);			
				if(indicator == 1){
					Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
		        	startActivity(i);
		        	finish();
		        	Toast.makeText(getApplicationContext(), "Authentication Successful", Toast.LENGTH_LONG).show();
				}
				else{
					Toast.makeText(getApplicationContext(), "Authentication Failed", Toast.LENGTH_LONG).show();
				}
			}
			catch(Exception e){
				serverError();
			}
    	}
    }
}