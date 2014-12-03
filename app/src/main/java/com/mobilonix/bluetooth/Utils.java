package com.mobilonix.bluetooth;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.mobilonix.bluetooth.*;

public class Utils
{   /*
	static LocationObject getLocationObject(Context c){
		if(LocationObject.getObjectCount() > 1) {
			return null;
		} else {
			return new LocationObject(c);
		}
	}
	*/

	static void showToast(Context ctx, String message) {
		Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
	}

	public class CustomDialogFragment extends DialogFragment {


		public CustomDialogFragment newInstance() {

			return null;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View v = null;
			//View v = inflater.inflate(R.layout.add_personality_dialog,container);
			getDialog().setTitle("Manage Personality Profiles");
			return v;
		}

	}
	
	void getFile(String remoteLocation,String localLocation) {
		
		
		
	}
}
