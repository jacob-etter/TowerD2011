package com.example.testsurf;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Displays the Game Over Sreen
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
public class ScreenGameOver extends Activity {
	/** Called when the activity is first created. */
	SharedPreferences prefs;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		//force landscape
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.gameover);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		int score = prefs.getInt("Score",1); 
		int wave = prefs.getInt("RoundsCompleted",1); 
		String final_score = Integer.toString(score);
		String rounds = Integer.toString(wave);
		TextView txscore = (TextView) findViewById(R.id.TextViewScore);
		TextView txrounds = (TextView) findViewById(R.id.TextViewRComp);
		txscore.setText(final_score);
		txrounds.setText(rounds);
		Button mainmenu = (Button) findViewById(R.id.ButtonMainMenu);
		mainmenu.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(), ScreenMainMenu.class);
				myIntent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
				startActivity(myIntent);
			}
		});
	}
	public void updateHighScore(){
		
	}
}
