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
import com.dsw.dispenserdata.Districts;
import com.dsw.dispenserdata.LazyAdapter;

public class DistrictsActivity extends Activity{
	public static final String KEY_DISTRICT_NAME = "district_name";
	private static final String TAG_DISTRICT_NAME = "district_name";
	
	ListView listView;
	LazyAdapter lazyAdapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);
		setContentView(R.layout.districts);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		DbHandler db = new DbHandler(this);
		ArrayList<HashMap<String, String>> districts = new ArrayList<HashMap<String, String>>();
		List<Districts> mDistricts = db.getDistricts();
		
		for(Districts district: mDistricts){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(KEY_DISTRICT_NAME, district.getDistrictName());
			districts.add(map);
		}
		
		listView = (ListView) findViewById(R.id.listDistricts);
		lazyAdapter = new LazyAdapter(this, districts, "districts");
		listView.setAdapter(lazyAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String distName = ((TextView)view.findViewById(R.id.districtName)).getText().toString();
				
				Intent in = new Intent(getApplicationContext(), SublocationsActivity.class);
				in.putExtra(TAG_DISTRICT_NAME, distName);
				startActivity(in);
			}
		});
	}
}
