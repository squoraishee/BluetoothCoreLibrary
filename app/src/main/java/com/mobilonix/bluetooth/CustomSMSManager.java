package com.mobilonix.bluetooth;

import android.content.*;
import android.telephony.gsm.*;
import java.text.*;
import android.os.*;

public class CustomSMSManager
{
	Context context;
	SMSReceiver receiver;
	SmsManager smsManager;
	
	public CustomSMSManager(Context context){
		
		this.context = context;
		receiver = new SMSReceiver();
		context.registerReceiver(receiver,new IntentFilter("android.provider.Telephony.SMS_RECEIVER"));
	    
		smsManager = SmsManager.getDefault();
	}
	
	void sendSms(String message) {
		smsManager.sendTextMessage("",null,"",null,null);
	}
	
	void sendSMSViaProgram() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		
	}
	
	public class SMSReceiver extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context p1, Intent p2)
		{
			// TODO: Implement this method
		}
		
	}
}
