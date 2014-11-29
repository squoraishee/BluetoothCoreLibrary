package com.mobilonix.bluetooth;

import android.app.*;
import android.os.*;

public class WifiInfoActivity extends Activity
{
	CustomWifiManager customWifiManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.wifi_reference_activity);
		
		customWifiManager = new CustomWifiManager(this);
	    customWifiManager.init();
	}
}
