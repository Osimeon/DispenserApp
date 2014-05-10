package com.dsw.dispenserapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.dsw.dispenserdata.DbHandler;
import com.dsw.dispenserdata.LazyAdapter;
import com.dsw.dispenserdata.Villages;

public class PrototypeVillagesActivity extends Activity{
	//Nodes
	public static final String KEY_VILLAGE_NAME = "village_name";
	
	public static final String TAG_VILLAGE_NAME = "village_name";
	
	public static final String TAG_SUBLOCATION_NAME = "sublocation_name";
	
	ListView listView;
	LazyAdapter lazyAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);
		setContentView(R.layout.prototype_villages);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		Intent in = getIntent();
		final String sublocation = in.getStringExtra(TAG_SUBLOCATION_NAME);
		
		DbHandler db = new DbHandler(this);
		ArrayList<HashMap<String, String>> villages = new ArrayList<HashMap<String, String>>();
		
		List<Villages> mVillages = db.getVillages(sublocation);
		
		for(Villages village : mVillages){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(KEY_VILLAGE_NAME, village.getVillageName());
			villages.add(map);
		}
		
		TextView viewHeader = (TextView) findViewById(R.id.txtAllVillagesPrototype);
		viewHeader.setText(sublocation + ": Villages");
		
		listView = (ListView) findViewById(R.id.listVillagesPrototype);
		lazyAdapter = new LazyAdapter(this, villages, "villages");
		listView.setAdapter(lazyAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String vilName = ((TextView)view.findViewById(R.id.villageName)).getText().toString();
				Intent in = new Intent(getApplicationContext(), PrototypeWaterpointsActivity.class);
				in.putExtra(TAG_VILLAGE_NAME, vilName);
				startActivity(in);
			}
		});
	}
}
