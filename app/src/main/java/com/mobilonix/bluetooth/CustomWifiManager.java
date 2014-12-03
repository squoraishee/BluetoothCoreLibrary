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
	
	//non core functionaiity
	WifiManager.WifiLock lock;
	
	//Signal monitoring
	boolean signalMonitorServiceEnabled = false;
	ArrayList<Double> signalData;
	
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
	
	
	void signalMonitorService(boolean state) {
		
		if(state) {
			signalMonitorServiceEnabled = true;
			signalData = new ArrayList<Double>();
		} else {
			signalMonitorServiceEnabled = false;
			return;
		}
		
		new Thread(new Runnable() {

				@Override
				public void run()
				{
				     while(signalMonitorServiceEnabled){
						 
						 try
						 {
							 Thread.sleep(1000);
						 }
						 catch (InterruptedException e)
						 {
							 
						 }

					 }
				}
				
		});
	}
	
	void enableWiFiLock(int lockType){
		//how long before a wifi connection goes idke. is ther anyway to hackwifi lock to make it work even when the settings are of of for enabling.?
		//what else does the wake lock permission effect.  is it toonly to prevent the device from faling aslep or all features f the device including wifi

		//so there are 3 separate types of wifi locks.  there is he standard ifi lock where they wifi is kep active, the high prformance lock how does thatbwork why is tha different and  the scan only lock all are obtained from the wifi manager service
        lock = wifiManager.createWifiLock(lockType,"LockTag");
		lock.acquire();
		
	}
	//so under what cases woukd a wifi lokc be needed if it isn't handle by aitplane mode or it isnt taken care of setwifi enabled?  it is super sceded.  when does the wii go out of idle state
    void releaseWifiLock() {
		lock.release();
	}
}
