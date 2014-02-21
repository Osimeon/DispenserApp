package com.dsw.dispenserapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dsw.dispenserdata.DbHandler;
import com.dsw.dispenserdata.Users;

public class DashboardActivity extends Activity{
	DbHandler db = new DbHandler(this);
	
	protected void onResume(){
		super.onResume();
		Button btnResolve = (Button) findViewById(R.id.btn_resolve);
		int issuesCount = db.getRowCount(DbHandler.TABLE_ISSUES);
		if(issuesCount > 0){
			btnResolve.setText("Resolve Issues(" + issuesCount +")");
			btnResolve.setTextColor(Color.RED);
		}
	}
	
	protected void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);
		setContentView(R.layout.home_screen);	
		Users currentUser = db.getUser();
		
		
		String userEmail = currentUser.getStaffEmail();
		
		Button btnCreate = (Button) findViewById(R.id.btn_create);
		Button btnResolve = (Button) findViewById(R.id.btn_resolve);
		Button btnWaterpoint = (Button) findViewById(R.id.btn_waterpoint);
		Button btnMisc = (Button) findViewById(R.id.btn_misc);
		
		TextView logoutId = (TextView) findViewById(R.id.logoutId);
		RelativeLayout logout = (RelativeLayout) findViewById(R.id.relativeLogout);
		
		logoutId.setText("Logout ("+userEmail+")");		
		
		logout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(i);
				finish();
			}
		});
		
		btnMisc.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				//Intent i = new Intent(DashboardActivity.this, DistrictsActivity.class);
				//startActivity(i);
			}
		});
		
		btnCreate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Intent i = new Intent(DashboardActivity.this, DistrictsActivity.class);
				startActivity(i);
			}
		});
		
		btnResolve.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Intent i = new Intent(DashboardActivity.this, ResolveIssue.class);
				startActivity(i);
			}
		});
		
		btnWaterpoint.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Intent i = new Intent(DashboardActivity.this, Waterpoint.class);
				startActivity(i);
			}
		});
	}
}	
