/**
 * Displays the Select Level Screen
 * @author Marina Nunamaker and Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Marina Nunamaker and Sean Wiese
 */
package com.example.towerd2011;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ScreenOptions extends Activity {
	/** preferences that can be changed in this screen */
	private SharedPreferences prefsOptions;
	private SharedPreferences prefsScores;
	/** preferences for difficulty */
	private int whatdiffpref;
	/** preference for sound */
	private int whatsoundpref;
	/** preference for music */
	private int whatmusicpref;
	/** default values for preferences */
	int difficultyInt = 1; 
	int musicInt = 1;
	int soundInt = 1;
	public void onCreate(Bundle savedInstanceState) {
		/**force landscape*/
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		/**Remove title bar*/
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		/**Remove notification bar*/
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);
		/** Retrieve and set preferences */
		prefsOptions = getSharedPreferences("Options", Context.MODE_PRIVATE);
		prefsScores = getSharedPreferences("HighScores", Context.MODE_PRIVATE);
		retrievePreferences();

		/**Define preference objects */
		RadioGroup difficulty = (RadioGroup) findViewById(R.id.difficultyRadioGroup);
		RadioGroup music = (RadioGroup) findViewById(R.id.musicRadioGroup);
		RadioGroup sound = (RadioGroup) findViewById(R.id.soundRadioGroup);

		/**Save difficulty preferences */
		difficulty.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.easyRadio:difficultyInt=1;break;
				case R.id.mediumRadio:difficultyInt=2;break;
				case R.id.hardRadio:difficultyInt=3;break;
				}
				Editor editor = prefsOptions.edit();
				editor.putInt("Difficulty", difficultyInt);
				editor.commit();
			}
		});

		/**Save sound preferences */
		sound.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.soundonRadio:
					soundInt=1;
					break;
				case R.id.soundoffRadio:
					soundInt=0;
					break;
				}
				Editor editor = prefsOptions.edit();
				editor.putInt("Sound", soundInt);
				editor.commit();
			}
		});

		/** Save music preferences */
		music.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.musiconRadio:
					musicInt=1;
					break;
				case R.id.musicoffRadio:
					musicInt=0;
					break;
				}
				Editor editor = prefsOptions.edit();
				editor.putInt("Music", musicInt);
				editor.commit();
			}
		});
		/** button to go to high scores screen */
		Button highScores = (Button) findViewById(R.id.ButtonHighScores);
		highScores.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(), ScreenHighScores.class);
				startActivityForResult(myIntent, 0);
			}
		});
		/** button to clear all options and set them to default values */
		Button clearoptions = (Button) findViewById(R.id.ButtonClearOptions);
		clearoptions.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				alertDialog1();
			}
		});
		/** but to clear high scores */
		Button clearscores = (Button) findViewById(R.id.ButtonClearScores);
		clearscores.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				alertDialog2();
			}
		});
		Button devoptions = (Button) findViewById(R.id.ButtonDevOptions);
		devoptions.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(), ScreenDificultyAdjust.class);
				startActivityForResult(myIntent, 0);
			}
		});

	}
	/** and alert dialog to warn user before clearing options */
	protected void alertDialog1(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to clear options?")
		.setCancelable(false)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Editor editor = prefsOptions.edit();
				editor.clear();
				editor.commit();
				retrievePreferences();
				dialog.dismiss();
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	/** an alert dialog to warn the user before clearing high scores */
	protected void alertDialog2(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to clear high scores?")
		.setCancelable(false)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Editor editor = prefsScores.edit();
				editor.clear();
				editor.commit();
				retrievePreferences();
				dialog.dismiss();
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	/** Retrieve Default/Previous Preferences */
	protected void retrievePreferences()
	{    	
		/** Difficulty Radio Buttons */
		RadioButton difficultyEasy = (RadioButton)findViewById(R.id.easyRadio) ;
		RadioButton difficultyMedium = (RadioButton)findViewById(R.id.mediumRadio) ;
		RadioButton difficultyHard = (RadioButton)findViewById(R.id.hardRadio);
		/** Sound Radio Buttons */
		RadioButton soundOn = (RadioButton)findViewById(R.id.soundonRadio);
		RadioButton soundOff = (RadioButton)findViewById(R.id.soundoffRadio);
		/** Music Radio Buttons */
		RadioButton musicOn = (RadioButton)findViewById(R.id.musiconRadio);
		RadioButton musicOff = (RadioButton)findViewById(R.id.musicoffRadio);

		whatdiffpref=prefsOptions.getInt("Difficulty",1);
		whatsoundpref=prefsOptions.getInt("Sound",1);
		whatmusicpref=prefsOptions.getInt("Music",1);

		/** Retrieve Difficulty Preferences **/
		switch(whatdiffpref){
		case 1: difficultyEasy.setChecked(true);break;
		case 2: difficultyMedium.setChecked(true);break;   	
		case 3:difficultyHard.setChecked(true);break;
		}

		/** Retrieve Sound Preferences **/
		switch(whatsoundpref){
		case 0: soundOff.setChecked(true);break;
		case 1:soundOn.setChecked(true);break;
		}
		/** Retrieve Music Preferences **/
		switch(whatmusicpref){
		case 0: musicOff.setChecked(true);break; 
		case 1: musicOn.setChecked(true);break;  
		}

	}

}