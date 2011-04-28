/**
 * Displays the Game Over Sreen
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
import android.text.InputFilter;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ScreenGameOver extends Activity {
	/** Create some varriables */
	SharedPreferences prefs;
	int score;
	int wave;
	int num_scores;
	EditText name;
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
		setContentView(R.layout.gameover);
		/** load the high score prefs */
		prefs = getSharedPreferences("HighScores", Context.MODE_PRIVATE);
		score = prefs.getInt("Score",1);
		num_scores = prefs.getInt("NumOfScores", 0);
		wave = prefs.getInt("RoundsCompleted",1); 
		/** load the save name field for putting a user name to match the score */
		name = (EditText) findViewById(R.id.EditTextName);
		/** make sure the name can not be too long to show properly in the highscores list */
		InputFilter[] FilterArray = new InputFilter[1];
		FilterArray[0] = new InputFilter.LengthFilter(18);
		name.setFilters(FilterArray);
		/** set the score and wave textview */
		String final_score = Integer.toString(score);
		String rounds = Integer.toString(wave);
		TextView txscore = (TextView) findViewById(R.id.TextViewScore);
		TextView txrounds = (TextView) findViewById(R.id.TextViewRComp);
		txscore.setText(final_score);
		txrounds.setText(rounds);
		/** setup the mainmenu button */
		Button mainmenu = (Button) findViewById(R.id.ButtonMainMenu);
		mainmenu.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				/** on click we need to save the score and user name to the 
				 * high scores list
				 */
				num_scores += 1;
				SharedPreferences.Editor editor = prefs.edit();
				editor.putInt("NumOfScores", num_scores);
				editor.commit();
				String username = name.getText().toString();
				/** see where the score goes in the list */
				for(int i = 0; i<num_scores;++i){
					String temp_score = prefs.getString("ScoreString"+Integer.toString(i), "000");
					if(Integer.parseInt(temp_score)<= score){
						/** insert the score to this row */
						insertScore(i,Integer.toString(score),username);
						break;
					}
				}
				/** go to the main menu and clear the gameview activity and this activity */
				Intent myIntent = new Intent(view.getContext(), ScreenMainMenu.class);
				myIntent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
				startActivity(myIntent);
			}
		});
	}
	/**
	 * This function puts the new score in the proper place in the high scores list
	 * @param index
	 * @param score_string
	 * @param username
	 */
	public void insertScore(int index, String score_string, String username){
		SharedPreferences.Editor editor = prefs.edit();
		String score_string2;
		String score_string1 = score_string;
		String name_string2;
		String name_string1 = username;
		for(int i=index;i<num_scores;++i){
			name_string2 = name_string1;
			score_string2 = score_string1;
			name_string1 = prefs.getString("NameString"+Integer.toString(i), "ErrorScreenGameOver");
			score_string1 = prefs.getString("ScoreString"+Integer.toString(i), "000000");
			editor.putString("ScoreString"+Integer.toString(i), score_string2);
			editor.putString("NameString"+Integer.toString(i), name_string2);
			editor.commit();
		}
	}
}
