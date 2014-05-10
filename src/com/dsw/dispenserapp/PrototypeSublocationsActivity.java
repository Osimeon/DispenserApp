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
import com.dsw.dispenserdata.Sublocations;

public class PrototypeSublocationsActivity extends Activity{
	
	//Nodes
	public static final String KEY_SUBLOCATION_NAME = "sublocation_name";
	public static final String TAG_SUBLOCATION_NAME = "sublocation_name";
	
	private static final String TAG_DISTRICT = "district_name";
	
	ListView listView;
	LazyAdapter lazyAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);
		setContentView(R.layout.prototype_sublocations);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		Intent in = getIntent();
		final String dist = in.getStringExtra(TAG_DISTRICT);
		 
		DbHandler db = new DbHandler(this);
		ArrayList<HashMap<String, String>> sublocations = new ArrayList<HashMap<String, String>>();
		List<Sublocations> mSublocations = db.getSublocations(dist);
		
		for(Sublocations sublocation : mSublocations){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(KEY_SUBLOCATION_NAME, sublocation.getSublocationName());
			sublocations.add(map);
		}
		
		TextView viewHeader = (TextView) findViewById(R.id.txtAllSublocationsPrototype);
		viewHeader.setText(dist + ": Sublocations");
		
		listView = (ListView) findViewById(R.id.listSublocationsPrototype);
		lazyAdapter = new LazyAdapter(this, sublocations, "sublocations");
		listView.setAdapter(lazyAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String distName = ((TextView)view.findViewById(R.id.sublocationName)).getText().toString();
				
				Intent in = new Intent(getApplicationContext(), PrototypeVillagesActivity.class);
				in.putExtra(TAG_SUBLOCATION_NAME, distName);
				startActivity(in);
			}
		});
	}	
}
