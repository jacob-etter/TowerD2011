/**
 * This this the ScreenDificulytAdjust used for debugging
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
package com.example.towerd2011;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class ScreenDificultyAdjust extends Activity {
	/** create the variables to store the buttons, prefs, and edittext fields */
	SharedPreferences prefs;
	Button ButtonTimer;
	Button ButtonDifficulty;
	Button ButtonCount;
	Button ButtonClear;
	Button ButtonMoney;
	Button ButtonLives;
	EditText timer;
	EditText Difficulty;
	EditText count;
	EditText money;
	EditText lives;
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
		setContentView(R.layout.difficultyadjustment);
		/** load the prefs */
		prefs = getSharedPreferences("DiffAdjust", Context.MODE_PRIVATE);
		/** load the edittext fields and set their values */
		timer = (EditText)findViewById(R.id.EditTextTimer);
		timer.setText(Integer.toString(prefs.getInt("SpawnTimer", 3000)));
		Difficulty = (EditText)findViewById(R.id.EditTextDifficulty);
		Difficulty.setText(Integer.toString(prefs.getInt("DifficultyOffset", 0)));
		count = (EditText)findViewById(R.id.EditTextCount);
		count.setText(Integer.toString(prefs.getInt("SpawnCount", 5)));
		money = (EditText)findViewById(R.id.EditTextMoney);
		money.setText(Integer.toString(prefs.getInt("Money", 500)));
		lives = (EditText)findViewById(R.id.EditTextLives);
		lives.setText(Integer.toString(prefs.getInt("Lives", 10)));
		/** sets the timer field */
		ButtonTimer = (Button) findViewById(R.id.ButtonTimer);
		ButtonTimer.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String timer_string = timer.getText().toString();
				Editor editor = prefs.edit();
				editor.putInt("SpawnTimer",Integer.parseInt(timer_string));
				editor.commit();
			}
		});
		/** sets the difficulty field */
		ButtonDifficulty = (Button) findViewById(R.id.ButtonDifficulty);
		ButtonDifficulty.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String Difficulty_String = Difficulty.getText().toString();
				Editor editor = prefs.edit();
				editor.putInt("DifficultyOffset",Integer.parseInt(Difficulty_String));
				editor.commit();
			}
		});
		/** sets the count field */
		ButtonCount = (Button) findViewById(R.id.ButtonCount);
		ButtonCount.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String count_string = count.getText().toString();
				Editor editor = prefs.edit();
				editor.putInt("SpawnCount",Integer.parseInt(count_string));
				editor.commit();
			}
		});
		/** sets the money field */
		ButtonMoney = (Button) findViewById(R.id.ButtonMoney);
		ButtonMoney.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String money_string = money.getText().toString();
				Editor editor = prefs.edit();
				editor.putInt("Money",Integer.parseInt(money_string));
				editor.commit();
			}
		});
		/** sets the lives field */
		ButtonLives = (Button) findViewById(R.id.ButtonLives);
		ButtonLives.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String lives_string = lives.getText().toString();
				Editor editor = prefs.edit();
				editor.putInt("Lives",Integer.parseInt(lives_string));
				editor.commit();
			}
		});
		/** clears the data for DiffAdjust */
		ButtonClear = (Button) findViewById(R.id.ButtonClear);
		ButtonClear.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				alertDialog();
			}
		});
	}
	/**
	 * an alert dialog to make sure you want to clear this data
	 */
	protected void alertDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to clear options?")
		.setCancelable(false)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Editor editor = prefs.edit();
				editor.clear();
				editor.commit();
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
}
