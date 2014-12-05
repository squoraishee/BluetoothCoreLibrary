package com.mobilonix.bluetooth;

import android.app.*;
import android.graphics.drawable.Drawable;
import android.os.*;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import java.util.*;

public class MainGameActivity extends Activity
{
    //view constants
    static final String PRIMARY_MENU = "PRIMARY_MENU";
    static final String TEACH_MENU = "TEACH_MENU";
    static final String PLAY_MENU = "PLAY_MENU";
    static final String CLEAN_MENU = "CLEAN_MENU";

    enum TransitionType {
        SNAP_IN,
        SNAP_OUT,
        FADE_IN,
        FADE_OUT,
        SLIDE_RIGHT,
        SLIDE_LEFT,
        SLIDE_UP,
        SLIDE_DOWN,
        FADE_LEFT,
        FADE_RIGHT
    }

    //video sequence
    enum VideoSequence {
        INTRO_VIDEO,
        AFTER_FEED_CLICK,
        FEEDING_FAILED,
        FEED_SUCCESSFUL,
        AFTER_TEACH_CLICK,
        TEACHING_CLICK,
        TEACH_SUCCESSFUL,
        TEACH_FAILED,
        AFTER_CLEAN_CLICK,
        CLEANING_VIDEO,
        CLEAN_SUCCESSFUL,
        CLEAN_FAILED,
        AFTER_PLAY_CLICK,
        PLAYING_VIDEO,
        PLAY_SUCCESSFUL,
        PLAY_FAILED
    }

    VideoSequence videoSequence;

    //boundary views
    View menuRightBoundary;
    View menuBottomBoundary;

	ArrayList<MenuOption> primaryMenuOptions;
	ArrayList<MenuOption> teachMenuOptions;
	ArrayList<MenuOption> teachResultMenuOptions;
	
	ArrayList<MenuOption> playMenuOptions;
	ArrayList<MenuOption> playResultMenuOptions;
	
	ArrayList<MenuOption> cleanMenuOptions;
	ArrayList<MenuOption> cleanResultMenuOptions;

    ArrayList<MenuOption> feedMenuOptions;
    ArrayList<MenuOption> feedResultMenuOptions;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.owner_activity);

        initMenuOptions();

	}

    public void initMenuOptions() {

        primaryMenuOptions = new ArrayList<MenuOption>();

        ImageView teachImage = (ImageView)findViewById(R.id.teach_image);
        ImageView feedImage = (ImageView)findViewById(R.id.feed_image);
        ImageView cleanImage = (ImageView)findViewById(R.id.clean_image);
        ImageView playImage = (ImageView)findViewById(R.id.play_image);

        primaryMenuOptions.add(new MenuOption("","",teachImage));
        primaryMenuOptions.add(new MenuOption("","",feedImage));
        primaryMenuOptions.add(new MenuOption("","",cleanImage));
        primaryMenuOptions.add(new MenuOption("","",playImage));

        for (MenuOption e : primaryMenuOptions) {

        }

    }

    public void positionImageView(ImageView v,int x,int y) {
        v.setX(x);
        v.setY(y);
    }

	public void resetMenuOptions() {
		for (MenuOption e : primaryMenuOptions) {

        }
	}

    public void displayMenuOptions(int start, int end) {
        for (MenuOption e : primaryMenuOptions) {

        }
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
			optionImage.setImageResource(image);
		}
		
		public void setTextStyle() {
			
		}
		
	}
}
