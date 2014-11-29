package com.mobilonix.bluetooth;

import android.app.*;
import android.bluetooth.*;
import android.content.*;
import java.util.*;
import java.io.*;
import android.util.*;
import java.util.logging.*;

public class CustomBluetoothManager
{
	
	BluetoothAdapter blueToothAdapter;
	Context context;
	int REQUEST_BT = 1;
	int REQUEST_DEVICE_DISCOVERABLE = 1;
	Set<BluetoothDevice> pairedDevices;
	ArrayList<BluetoothDevice> currentDeviceArray;
	
	//time related
	Timer timer;
	TimerTask shutDownBTTask;
	
    //Broadcast Related
	BroadcastReceiver receiverBt;
	BroadcastReceiver receiverBTDiscovery;
	
	public CustomBluetoothManager(Context context) {
		this.context = context;
		timer = new Timer();
		initShutDownBTTask();
        blueToothAdapter = BluetoothAdapter.getDefaultAdapter();  //hopefully this isn't baked into the singleton
	    currentDeviceArray = new ArrayList<BluetoothDevice>();
	}

	void init() {
		//Utils.Logger()
		Activity a = (Activity)context;
		if(!blueToothAdapter.isEnabled()) {
			Intent intent = new Intent(blueToothAdapter.ACTION_REQUEST_ENABLE);
			a.startActivityForResult(intent,REQUEST_BT);
	        Utils.showToast(context, "Bluetooth not enabled. Redircting...");
		} else {
			Utils.showToast(context, "Bluetooth is currently enabled...");
		}
		
	    initReceiver();
	}
	
	void initReceiver() {
		
		//the receiver registers based on the intent filter
		IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
		IntentFilter deviceFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		
		//so the getaction comand gets the data from the filter that you want the listener to parse.  if i want another action i need to look at another different value of getaction from the intent. implicit inte t
		receiverBt = new BroadcastReceiver() {

			@Override
			public void onReceive(Context p1, Intent intent)
			{
				String action = intent.getAction();
				//for the intent we first get the action and then we get the valye of the specific action
				if(action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)){


					int btState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,0);
					switch(btState) {
						case BluetoothAdapter.STATE_OFF : {
								Utils.showToast(context, "Bluetooth off");
								break;
							}
						case BluetoothAdapter.STATE_ON : {
								Utils.showToast(context, "Bluetooth on");
								break;
							}
						case BluetoothAdapter.STATE_TURNING_OFF :  {
								Utils.showToast(context, "Bluetooth turning off");
								break;
							}
						case BluetoothAdapter.STATE_TURNING_ON : {
								Utils.showToast(context, "Bluetooth turning on");
								break;
							}
					}

				}
			}
		};
         
		//check from extra data from the intent
		receiverBTDiscovery = new BroadcastReceiver() {

			@Override
			public void onReceive(Context p1, Intent intent)
			{
			    String action = intent.getAction();
				if(action.equals(BluetoothDevice.ACTION_FOUND)) {
					
					//what is a parcellable extra?  why cant we just use an obje t extra?
					//also an intent is a passive data structure.  what makes it passive and useful as the glue between acrivities.  does it have agreater use than that?
					//how does broadcastintent differ from broadcast send message?  or should i only braodcat intents 
					BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				    currentDeviceArray.add(device);
				}
				
			}
			
		};
		//when you regster the broadcast receiver, you make sure theres an intent filter associated with it
		context.getApplicationContext().registerReceiver(receiverBt,filter);
		context.getApplicationContext().registerReceiver(receiverBTDiscovery,deviceFilter);
	
	}
	
	void connect() {
		//here we would open up a bluetooth server socket if we wanted to make this app a server.  how do we decide whih app to make a server  do we need a server running to create nluetooth socket?
		//why would you need a uuid.  are all uuids 128 bit? is the uuid the same in android as it is in ios?
		//is te service disocery protocl intimately connected with the bluetoh service how does the sdp number how long does it stay o  the system how manycan you have?
		//so which service do we pick to listen to connectsion rfcomm or something else or service soke lso f the accept connection is blocking call how do w do it in teh abckgrund.  doe sit have to  e a blcokig ca?
		// so all the work for acceptin connecfions shoulf be done in a separate thread
		//how do younkanoe thag all the methods for bluetooth interwctiviy are thread safe are they all sycnhronzed ie that the si llest thing you to do o ake an operarii threadsafe?

	}

	void setDiscoverable(boolean d, int discoverableTime){
		if(d){
			Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		    discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, discoverableTime);
			
			//so absicaly you need to enable discoverablity of your device not for simple connections to toher devices but only if your device acts like a server acceping icoming connections.  but put it in discoveranle mode oes that cause some sort of ping to hapoen?
			
			Activity a = (Activity)context;
			a.startActivityForResult(discoverableIntent,REQUEST_DEVICE_DISCOVERABLE);
	
			//question is whether to register another broadcast receiver t determine the nuemberbof desin changes
		    //do we want to register a device change
		    //so in dicidg how to managse bluetooth connections one device has to act as the sevrwr and only cyou have to device how to manage theserver aockets. theres multiple methods but one is that both devices intended to connect can manage a bluetoth socket anf nogtioate, he other way is when one device opens a connecin another devie and a socket is created on demand or rather lacxy loaded
		}
	}
	
	public ArrayList<String> scan() {
		//so bluetooth pairin vs connected has two different meanings.  pairing means that two dvices are aware of each others existance and share some data but theycant transmit data between eacher until an RfCom connection is established between them
	    ArrayList<String> currentDeviceList = new ArrayList<String>();
		pairedDevices = blueToothAdapter.getBondedDevices();
		
		for(BluetoothDevice d : pairedDevices) {
			currentDeviceList.add(d.getAddress());
		}
		
		//the goal for tommorrow is to be able to connect to a bluetooth device and to be able to send a message t that other device and get one back possible using the same library created here
        //also kust oearn about bluetooth profiles bluetooth profiles are basically wirless devi es proiles that allow for the comminciation between devices, for example the hands free profile.  why are profiles necessry? cant i just do device discovery? how many different tpes ofnprofiles arer there?
        // so to whati you have alist of the paired devices why donyu a separate discovert stage throughbstartbdiscovery?
	    
		return currentDeviceList;
	}
	
	public void shutOff(int timeTillShutdown) {
		
		if(timeTillShutdown > 0) {
			timer.schedule(shutDownBTTask,4000);
		} else {
			blueToothAdapter.disable();
		}
		
	}
	
	public void destroy() {
		context.getApplicationContext().unregisterReceiver(receiverBt);
        shutOff(0);
    }
	
	void initShutDownBTTask() {
		shutDownBTTask = new TimerTask() {

			@Override
			public void run()
			{    
			     //Shutting down bluetooth connection
                 blueToothAdapter.disable();
          	}
		};
	}
	
	class BluetoothConnectionAcceptThread extends Thread {
		
		BluetoothServerSocket serverSocket; //produces bluetooth sockets
	 
		//pass a bluetooth adapter to this thread and keep it non coupled to the outer class
		public BluetoothConnectionAcceptThread(BluetoothAdapter a, String name, UUID uuid) {
			try {
				serverSocket = a.listenUsingRfcommWithServiceRecord(name, uuid);
			} catch(IOException e){
				Log.e(this.getName(), "Failed to establish RFCOMM connection");
			}
		}
		
		@Override
		public void run(){
		    BluetoothSocket socket = null;
			while(true) {
				
				try
				{
					//Question should we put in its own try catch
					socket = serverSocket.accept();
				}
				catch (IOException e)
				{
					Log.d(this.getName(),"Couldn't acccept socket connection");
				}

				if(socket != null) {
					
					//we want to close the server socket because once a connecion is accpted we dont need to produce anymore new sockets
					try
					{
						serverSocket.close();
					}
					catch (IOException e)
					{
						Log.d("","Failed to close the Server Socket");
					}
					break;
				
				}
			}
		
		}
	}
	
	class BluetoothConnectionThread {
		
		BluetoothAdapter adapter;
		BluetoothSocket socket;
		BluetoothDevice device;
		boolean cancelDiscovery;
		
		String TAG;
		
		public BluetoothConnectionThread(BluetoothAdapter adapter, BluetoothDevice device,UUID uuid,boolean cancelDiscovery) {
			
			TAG = this.getClass().getName();
			
			//is this the device to connect to or a dvice thag has a conecion laready?
		    //we definitely want to save the device we're connecting to
			this.device = device;
			this.adapter = adapter;
			this.cancelDiscovery = cancelDiscovery;
			
			try
			{
				socket = device.createRfcommSocketToServiceRecord(uuid);
			}
			catch (IOException e)
			{
				Log.e(TAG,"Error establishing bluetooth connection");
			}
			
		}
		 
		@Override
		public void run()
		{
			
			if(cancelDiscovery){
				
				blueToothAdapter.cancelDiscovery();
			}
			
			try
			{
				socket.connect();
			}
			catch (IOException connectException)
			{
				cancel();
			}

		}
		
		public void cancel() {
			try
			{
				socket.close();
			}
			catch (IOException closeException)
			{

			}
		}
	}
	
    public class TranceiverThread extends Thread {
		
		InputStream in;
		OutputStream out;
		BluetoothSocket socket;
		
		public TranceiverThread(BluetoothSocket socket) {
			this.socket = socket;
			
			try
			{
				in = socket.getInputStream();
				out = socket.getOutputStream();
			}
			catch (IOException e)
			{
				
			}
			
		}
		
		public void read() {
			
			byte[] buffer = null;
			int bytes;
			
			while(true){
				
				try
				{
					bytes = in.read(buffer);
				}
				catch (IOException e)
				{
					break;
				}

			}
			
		}
		
		public void write(byte[] buffer) {
			try
			{
				out.write(buffer);
			}
			catch (IOException e)
			{}
		}
		
		public void cancel() {
			try
			{
				socket.close();
			}
			catch (IOException closeException)
			{

			}
		}
	}
	
	//here we will handle the blue tooth apis.  what do the blu toth apis provide for he android os
	//so thigs the bluetooth api can do is query the local blue tooth adapter for cnnected devces.  what info can you get about these connected deviced and whst yudbfhsy xinfbconne 
	//what is an rfcomm chanel and why woud you want tk establsh itt. so bluetooth suprt for clasic blueetooth was aleays thefe but
	//but andorid api 18 had support for le bluetooth
	//al the apis are in the android.bluetooth package so badilsly if a drvice isnt paired cn you still locate it? how does the mac addfess come i to play and what does to instatniate a device mean
	//s the idea is to find otu which devices are connected via the bldtooth adaoter and then which fe ies  can younestablsihg a bluetooth service tock
	//so bluetooth interactions go over input anfput dttesm objevy soudn sks pel enoigj and you can receive the state of the devu enusing the blue th so the bleu tooth and bluetooth adm psotions let you discoevt other devi es o bdlud tooh comenucisifonsoccur over fdzame rsdji 

}
