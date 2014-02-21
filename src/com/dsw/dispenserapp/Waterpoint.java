package com.dsw.dispenserapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.dsw.dispenserdata.DbHandler;
import com.dsw.dispenserdata.LazyAdapter;
import com.dsw.dispenserdata.Waterpoints;

public class Waterpoint extends Activity{
	public static final String KEY_WATERPOINT_ID = "waterpoint_id";
	public static final String KEY_WATERPOINT_NAME = "waterpoint_name";
	
	ListView listView;
	LazyAdapter lazyAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);
		setContentView(R.layout.waterpoint);		
	}
	
	
	
	@Override
	protected void onResume(){
		super.onResume();
		DbHandler db = new DbHandler(this);
		ArrayList<HashMap<String, String>> waterpoints = new ArrayList<HashMap<String, String>>();
		List<Waterpoints> allWaterpoints = db.getWaterpoints();
		
		for(Waterpoints waterpoint: allWaterpoints){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(KEY_WATERPOINT_ID, String.valueOf(waterpoint.getWaterpointId()));
			map.put(KEY_WATERPOINT_NAME, waterpoint.getWaterpointName());
			waterpoints.add(map);
		}
		
		listView = (ListView) findViewById(R.id.listWaterpoints);
		lazyAdapter = new LazyAdapter(this, waterpoints, "waterpoints");
		listView.setAdapter(lazyAdapter);
	}
}
