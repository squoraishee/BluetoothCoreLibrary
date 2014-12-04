package com.mobilonix.bluetooth;

import android.app.*;
import android.os.*;
import android.widget.*;
import java.util.*;

public class MainGameActivity extends Activity
{
	
	static final String PRIMARY_MENU = "PRIMARY_MENU";
	static final String EDUCATE_MENU = "EDUCATE_MENU";
	static final String PLAY_MENU = "PLAY_MENU";
	static final String CLEAN_MENU = "CLEAN_MENU";
	
	ArrayList<MenuOption> primaryMenuOptions;
	ArrayList<MenuOption> educateMenuOptions;
	ArrayList<MenuOption> educateResultMenuOptions;
	
	ArrayList<MenuOption> playMenuOptions;
	ArrayList<MenuOption> playResultMenuOptions;
	
	ArrayList<MenuOption> cleanMenuOptions;
	ArrayList<MenuOption> cleanResultMenuOptions;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
	}
	
	public void displayMenuOptions() {
		
	}
	
	class MenuOption {
		
		ImageView optionImage;
		String optionText;
		String headingText;
		
		public MenuOption(String optionText, String headingText, ImageView optionImage) {
			this.optionText = optionText;
			this.optionImage = optionImage;
		    this.headingText = headingText;
	    }
		 
		public void setImageOption(int image){
			optionImage.setImageDrawable(image);
		}
		
		public void setTextStyle() {
			
		}
		
	}
}
