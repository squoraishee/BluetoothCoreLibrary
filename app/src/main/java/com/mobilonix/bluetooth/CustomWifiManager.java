package com.mobilonix.bluetooth;

import android.content.*;
import android.net.wifi.*;
import android.util.*;
import java.util.*;
import android.widget.*;

public class CustomWifiManager
{
	
	Context context;
	WifiManager wifiManager;
	WifiScanReceiver scanReceiver;
	
	//the wifi manager lets you access all the wifi i fo of the andorid system it acts at a very low lev
	public CustomWifiManager(Context context) {
		this.context = context;
		
		//All system services are enumerated as part of the wifi class
		wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
      
	}
	
	public void init() {
		
		scanReceiver = new WifiScanReceiver(wifiManager);  //Default BCR doesn't need an arrached context
		
		//register receiver will ick up scan results.  what about scan results from different activities?
	    //when registering the receiver it's imperitvice to realize that the second argument is the intent filter which can be created on the fly
		context.registerReceiver(scanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
	}
	
	public void scan(ListView list) {
		
		if(list != null) {
			scanReceiver.setList(list);
		}
		
		wifiManager.startScan();
		//scanning for wifi requires hitting an activity which has an intent for scanning
	}
	
	class WifiScanReceiver extends BroadcastReceiver
	{

		WifiManager wifiManager;
		ListView list;
		
		List<ScanResult> lastWifiList;
		
		public WifiScanReceiver(WifiManager wifiManager){
			this.wifiManager = wifiManager;
		}
		
		public void setList(ListView list) {
			this.list = list;
		}
		
		@Override
		public void onReceive(Context p1, Intent p2)
		{
			lastWifiList = wifiManager.getScanResults();
		    Utils.showToast(context,"Got wifi scan results...");
			
			ArrayAdapter adapter = new ArrayAdapter(context,android.R.layout.simple_list_item_1,lastWifiList);
			list.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			Log.v("","Get scan results");
		}
		
	}
	
}
