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
import android.widget.EditText;
import android.widget.TextView;

/**
 * Displays the Game Over Sreen
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
public class ScreenGameOver extends Activity {
	/** Called when the activity is first created. */
	SharedPreferences prefs;
	int score;
	int wave;
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
		score = prefs.getInt("Score",1); 
		wave = prefs.getInt("RoundsCompleted",1); 
		String final_score = Integer.toString(score);
		String rounds = Integer.toString(wave);
		TextView txscore = (TextView) findViewById(R.id.TextViewScore);
		TextView txrounds = (TextView) findViewById(R.id.TextViewRComp);
		txscore.setText(final_score);
		txrounds.setText(rounds);
		Button mainmenu = (Button) findViewById(R.id.ButtonMainMenu);
		mainmenu.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				EditText name = (EditText) findViewById(R.id.EditTextName);
				String username = name.getText().toString();
				for(int i = 0; i<10;++i){
					String temp_score = prefs.getString("ScoreString"+Integer.toBinaryString(i), "0");
					if(Integer.parseInt(temp_score)>= score){
						insertScore(i,temp_score,username);
						break;
					}
				}
				Intent myIntent = new Intent(view.getContext(), ScreenMainMenu.class);
				myIntent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
				startActivity(myIntent);
			}
		});
	}
	public void insertScore(int index, String score_string, String username){
		SharedPreferences.Editor editor = prefs.edit();
		String score_string2;
		String score_string1 = score_string;
		String name_string2;
		String name_string1 = username;
		for(int i=index;i<10;++i){
			name_string2 = name_string1;
			score_string2 = score_string1;
			name_string1 = prefs.getString("NameString"+Integer.toBinaryString(i), "None");
			score_string1 = prefs.getString("ScoreString"+Integer.toBinaryString(i), "0");
			editor.putString("ScoreString"+Integer.toBinaryString(i), score_string2);
			editor.putString("NameString"+Integer.toBinaryString(i), name_string2);
			editor.commit();
		}
	}
}
