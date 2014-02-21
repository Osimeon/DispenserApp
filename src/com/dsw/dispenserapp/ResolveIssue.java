package com.dsw.dispenserapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.dsw.dispenserdata.DbHandler;
import com.dsw.dispenserdata.IssueType;
import com.dsw.dispenserdata.Issues;
import com.dsw.dispenserdata.LazyAdapter;

public class ResolveIssue extends Activity{
	public static final String KEY_ISSUE_ID = "issue_id";
	public static final String KEY_ISSUE_WATERPOINT_ID = "issue_waterpoint_id";
	public static final String KEY_ISSUE_DATE_CREATED = "issue_date_created";
	public static final String KEY_ISSUE_RESOLVED = "issue_resolved";
	public static final String KEY_ISSUE_ISSUE_TYPE = "issue_issue_type";
	
	//Alias fields
	public static final String KEY_WATERPOINT_NAME = "waterpoint_name";
	public static final String KEY_ISSUE_TYPE = "issue_type";
	
	ListView listView;
	LazyAdapter lazyAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);
		setContentView(R.layout.resolve_issue);	
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		DbHandler db = new DbHandler(this);
		ArrayList<HashMap<String, String>> issues = new ArrayList<HashMap<String, String>>();		
		
		TextView issueLabel = (TextView) findViewById(R.id.txtIssuesAssigned);		
		List<Issues> userIssues = db.getIssues();
		if(userIssues.isEmpty()){
			issueLabel.setText("You Have No Issues To Resolve");
		}
		else{
			/*issueLabel.setText("Please Contact Your Area Coodinator To Re-assign Issues Properly");
			issueLabel.setTextColor(Color.RED);*/
		}
		
		for(Issues issue: userIssues){
			HashMap<String, String> map = new HashMap<String, String>();
			//Waterpoints waterpoint = null;
			/*try{
				waterpoint = db.getWaterpoint(issue.getIssueWaterpointId());
			}
			catch(Exception e){
				Toast.makeText(getApplicationContext(), "Waterpoints Not In Your Current Regional Office!", Toast.LENGTH_LONG).show();
			}*/
			//if(waterpoint != null){
				IssueType issueType = db.getIssueType(issue.getIssueIssueType());
				map.put(KEY_ISSUE_ID, String.valueOf(issue.getIssueId()));
				map.put(KEY_ISSUE_WATERPOINT_ID, String.valueOf(issue.getIssueWaterpointId()));
				//map.put(KEY_WATERPOINT_NAME, waterpoint.getWaterpointName());
				map.put(KEY_ISSUE_DATE_CREATED, issue.getIssueDateCreated());
				map.put(KEY_ISSUE_RESOLVED, issue.getIssueStatus());
				map.put(KEY_ISSUE_ISSUE_TYPE, String.valueOf(issue.getIssueStatus()));
				map.put(KEY_ISSUE_TYPE, issueType.getIssueTypeName());
				issues.add(map);
			//}
			//else{
				//Toast.makeText(getApplicationContext(), "Waterpoint Not Found In Your Regional Office", Toast.LENGTH_SHORT).show();
			//}
		}
		
		listView = (ListView) findViewById(R.id.listIssues);
		lazyAdapter = new LazyAdapter(this, issues,"resolve_issues");
		listView.setAdapter(lazyAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String waterpointId = ((TextView)view.findViewById(R.id.waterpointID)).getText().toString();
				String issueTypeId = ((TextView)view.findViewById(R.id.issueTypeId)).getText().toString();
				String issueId = ((TextView)view.findViewById(R.id.issueId)).getText().toString();
				String waterpointName = ((TextView)view.findViewById(R.id.waterpointName)).getText().toString();
				String issueType = ((TextView)view.findViewById(R.id.issueType)).getText().toString();
				String dateCreated = ((TextView)view.findViewById(R.id.dateCreated)).getText().toString();
				
				Intent i = new Intent(getApplicationContext(), ResolveAnIssue.class);
				i.putExtra("wpId", waterpointId);
				i.putExtra("typeId", issueTypeId);
				i.putExtra("issueId", issueId);
				i.putExtra("wpName", waterpointName);
				i.putExtra("issueType", issueType);
				i.putExtra("dateCreated", dateCreated);
				startActivity(i);
			}
		});
	}
}
