package com.mobilonix.bluetooth;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class WifiInfoActivity extends Activity
{
	CustomWifiManager customWifiManager;
	ArrayAdapter adapter;
	
	ListView scanList;
	Button scanButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.wifi_reference_activity);
		
		customWifiManager = new CustomWifiManager(this);
	    customWifiManager.init();
		
		initUI();
		initUIListeners();
		
	}
	
	public void initUI() {
		scanButton = (Button)findViewById(R.id.wifi_scan_button);
	    scanList = (ListView)findViewById(R.id.scan_list);
	}
	
	public void initUIListeners() {
		scanButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View p1)
			{
				customWifiManager.scan(scanList);
		    }

		});
	}
}
