package com.dsw.dispenserdata;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dsw.dispenserapp.DistrictsActivity;
import com.dsw.dispenserapp.R;
import com.dsw.dispenserapp.ResolveIssue;
import com.dsw.dispenserapp.SublocationsActivity;
import com.dsw.dispenserapp.VillagesActivity;
import com.dsw.dispenserapp.Waterpoint;

public class LazyAdapter extends BaseAdapter{
	private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;
    private String which_list = "";
    
    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d, String list) {
        activity = a;
        data = d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        which_list = list;
    }
    
    @Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		
		if(which_list == "resolve_issues"){
			if(convertView == null)
				vi = inflater.inflate(R.layout.resolve_issue_row, null);
			TextView issueID = (TextView) vi.findViewById(R.id.issueId);
			TextView waterpointID = (TextView) vi.findViewById(R.id.waterpointID);
			TextView issueTypeID = (TextView) vi.findViewById(R.id.issueTypeId);
			TextView waterpointName = (TextView) vi.findViewById(R.id.waterpointName);
			TextView issueType = (TextView) vi.findViewById(R.id.issueType);
			TextView dateCreated = (TextView) vi.findViewById(R.id.dateCreated);
			TextView issueStatus = (TextView) vi.findViewById(R.id.issueResolved);
			
			HashMap<String, String> issues = new HashMap<String, String>();
			issues = data.get(position);
			issueID.setText(issues.get(ResolveIssue.KEY_ISSUE_ID));
			waterpointID.setText(issues.get(ResolveIssue.KEY_ISSUE_WATERPOINT_ID));
			issueTypeID.setText(issues.get(ResolveIssue.KEY_ISSUE_ISSUE_TYPE));
			if(ResolveIssue.KEY_WATERPOINT_NAME.isEmpty()){
				waterpointName.setText(issues.get(ResolveIssue.KEY_WATERPOINT_NAME));
			}
			else{
				waterpointName.setText(issues.get(ResolveIssue.KEY_ISSUE_WATERPOINT_ID));
			}
			//waterpointName.setText(issues.get(ResolveIssue.KEY_WATERPOINT_NAME + "(" + ResolveIssue.KEY_ISSUE_WATERPOINT_ID +")"));
			issueType.setText(issues.get(ResolveIssue.KEY_ISSUE_TYPE));
			dateCreated.setText(issues.get(ResolveIssue.KEY_ISSUE_DATE_CREATED));
			issueStatus.setText(issues.get(ResolveIssue.KEY_ISSUE_RESOLVED));
		}
		else if(which_list == "waterpoints"){
			if(convertView == null)
				vi = inflater.inflate(R.layout.waterpoints_row, null);
			TextView waterpointId = (TextView) vi.findViewById(R.id.waterpointsID);
			TextView waterpointName = (TextView) vi.findViewById(R.id.waterpointsName);
			
			HashMap<String, String> waterpoints = new HashMap<String, String>();
			waterpoints = data.get(position);
			waterpointId.setText(waterpoints.get(Waterpoint.KEY_WATERPOINT_ID));
			waterpointName.setText(waterpoints.get(Waterpoint.KEY_WATERPOINT_NAME));
		}
		else if(which_list == "districts"){
			if(convertView == null)
				vi = inflater.inflate(R.layout.district_row, null);
			TextView districtName = (TextView) vi.findViewById(R.id.districtName);
			
			HashMap<String, String> districts = new HashMap<String, String>();
			districts = data.get(position);
			districtName.setText(districts.get(DistrictsActivity.KEY_DISTRICT_NAME)); 
		}
		else if(which_list == "sublocations"){
			if(convertView == null)
				vi = inflater.inflate(R.layout.sublocation_row, null);
			TextView sublocationName = (TextView) vi.findViewById(R.id.sublocationName);
			
			HashMap<String, String> sublocations = new HashMap<String, String>();
			sublocations = data.get(position);
			sublocationName.setText(sublocations.get(SublocationsActivity.KEY_SUBLOCATION_NAME));
		}
		else if(which_list == "villages"){
			if(convertView == null)
				vi = inflater.inflate(R.layout.village_row, null);
			TextView villageName = (TextView) vi.findViewById(R.id.villageName);
			
			HashMap<String, String> villages = new HashMap<String, String>();
			villages = data.get(position);
			villageName.setText(villages.get(VillagesActivity.KEY_VILLAGE_NAME));
		}
		return vi;
	}
}
