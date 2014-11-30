package com.mobilonix.bluetooth;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class TechnologySelectorActivity extends Activity
{
	Context context;
	Button selectBluetoothReferenceButton;
	Button selectWifiReferenceButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.technology_selector_activity);
	
		context = this;
		
		//All basic initializations of the activity
		initUI();
		initUIListeners();
		
	}
	public void initUI(){
		selectBluetoothReferenceButton = (Button)findViewById(R.id.select_bluetooth_button);
		selectWifiReferenceButton = (Button)findViewById(R.id.select_wifi_button);
	}
	
	public void initUIListeners() {
		
		selectBluetoothReferenceButton.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					Intent intent = new Intent(context, BluetoothCoreBaseActivity.class);
					startActivity(intent);
				}
			
		});
		
		selectWifiReferenceButton.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					Intent intent = new Intent(context, WifiInfoActivity.class);
					startActivity(intent);
				}

		});
	}
}
