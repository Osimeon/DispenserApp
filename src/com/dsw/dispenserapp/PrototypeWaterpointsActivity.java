package com.dsw.dispenserapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.dsw.dispenserdata.DbHandler;
import com.dsw.dispenserdata.LazyAdapter;
import com.dsw.dispenserdata.Waterpoints;

public class PrototypeWaterpointsActivity extends Activity{
	public static final String KEY_WATERPOINT_ID = "waterpoint_id";
	public static final String KEY_WATERPOINT_NAME = "waterpoint_name";
	
	public static final String TAG_VILLAGE_NAME = "village_name";
	public static final String TAG_WATERPOINT_ID = "waterpoint_id";
	public static final String TAG_WATERPOINT_NAME = "waterpoint_name";
	
	ListView listView;
	LazyAdapter lazyAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);
		setContentView(R.layout.prototype_waterpoint);		
	}
		
	
	@Override
	protected void onResume(){
		super.onResume();
		DbHandler db = new DbHandler(this);
		
		Intent in = getIntent();
		final String vilName = in.getStringExtra(TAG_VILLAGE_NAME);
		
		TextView viewHeader = (TextView) findViewById(R.id.txtAllWaterpointsPrototype);
				
		
		ArrayList<HashMap<String, String>> waterpoints = new ArrayList<HashMap<String, String>>();
		List<Waterpoints> allWaterpoints = db.getWaterpoints(vilName);
				
		for(Waterpoints waterpoint: allWaterpoints){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(KEY_WATERPOINT_ID, String.valueOf(waterpoint.getWaterpointId()));
			map.put(KEY_WATERPOINT_NAME, waterpoint.getWaterpointName());
			waterpoints.add(map);
		}
		
		viewHeader.setText(vilName + ": Waterpoints");
		viewHeader.setTextColor(Color.RED);
		
		listView = (ListView) findViewById(R.id.listWaterpointsPrototype);
		lazyAdapter = new LazyAdapter(this, waterpoints, "waterpoints");
		listView.setAdapter(lazyAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String waterpointsName = ((TextView)view.findViewById(R.id.waterpointsName)).getText().toString();
				String waterpointId = ((TextView)view.findViewById(R.id.waterpointsID)).getText().toString();
				
				Intent in = new Intent(getApplicationContext(), PrototypeCreateIssueActivity.class);
				in.putExtra(TAG_WATERPOINT_ID, waterpointId);
				in.putExtra(TAG_WATERPOINT_NAME, waterpointsName);
				startActivity(in);
			}
		});
	}
}
