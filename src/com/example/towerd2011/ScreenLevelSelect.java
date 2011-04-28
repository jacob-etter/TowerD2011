/**
 * Displays the Select Level Screen
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
package com.example.towerd2011;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ScreenLevelSelect extends Activity {
	/** Called when the activity is first created. */
	/** the theme that we will use */
	protected int theme = 0;
	/** the level to use */
	protected int level = 0;
	public SharedPreferences prefs;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		/**force landscape*/
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		/**Remove title bar*/
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		/**Remove notification bar*/
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		/** set the layout */
		setContentView(R.layout.levelselect);
		/** get the options shared preferences */
		prefs = getSharedPreferences("Options", Context.MODE_PRIVATE);
		/** set up the radio group for level select */
		RadioGroup RadioGroupLevel = (RadioGroup) findViewById(R.id.RadioGroupLevel);
		RadioGroupLevel.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.RadioOne:level = 1;break;
				case R.id.RadioTwo:level = 2;break;
				case R.id.RadioThree:level = 3;break;
				}
			}
		});
		/** setup the radio group for theme */
		RadioGroup RadioGroupTheme = (RadioGroup) findViewById(R.id.RadioGroupTheme);
		RadioGroupTheme.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.RadioClassic:theme = 1;break;
				case R.id.RadioModern:theme = 2;break;
				}
			}

		});	
		/** setup the main menu button */
		Button mainmenu = (Button) findViewById(R.id.ButtonStart);
		mainmenu.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if((theme!=0)&&(level!=0)){
					SharedPreferences.Editor editor = prefs.edit();
					editor.putInt("theme", theme);
					editor.putInt("level", level);
					editor.commit();
					Intent myIntent = new Intent(view.getContext(), Main.class);
					startActivity(myIntent);
				}
			}
		});
	}
}
