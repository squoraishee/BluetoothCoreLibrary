package com.mobilonix.bluetooth;

import android.app.*;
import android.os.*;
import android.widget.*;
import java.util.*;

public class BluetoothCoreBaseActivity extends Activity 
{  
   
    //ListViews
	ListView bluetoothList;

    //Buttons
	Button connectToDevice;
	Button disconnectDevice;
	Button sendToDevice;
	
	//TextViews
	TextView incomingData;

	//Misc
    CustomBluetoothManager btmanager;
	ArrayAdapter adapter;
	ArrayList<String> currentDeviceList;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		initUI();
		init();
		
    }
	
	void initUI() {
	
		bluetoothList = (ListView)findViewById(R.id.device_list_view);
        initUIListeners();
	}
	
	void initUIListeners() {
		
	}
	
	void init() {
		
		//first initialize the lib)findViewByIdrary
		btmanager = new CustomBluetoothManager(this);
		btmanager.init();
		
		//get the device list
		currentDeviceList = btmanager.scan();
		currentDeviceList.add("blank");
	    adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,currentDeviceList);
	    bluetoothList.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
	
}
