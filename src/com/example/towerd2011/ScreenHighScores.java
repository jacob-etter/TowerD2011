/**
 * Displays the High Scores
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
package com.example.towerd2011;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ScreenHighScores extends Activity{
	SharedPreferences prefs;
	/** Called when the activity is first created. */
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		/**force landscape*/
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		/**Remove title bar*/
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		/**Remove notification bar*/
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		/** set the layout style */
		setContentView(R.layout.highscores);
		/** get the high score information */
		prefs = getSharedPreferences("HighScores", Context.MODE_PRIVATE);
		int num_scores = prefs.getInt("NumOfScores", 0);
		String[] names;
		/** if no high scores then say no score yet */
		if(num_scores == 0){
			names = new String[1];
			names[0] = "No Scores Yet";
		}
		/** else create a string array to hold the scores */
		else{
			names = new String[num_scores];
		}
		/** put the scores in the string */
		for(int i=0; i<num_scores;++i){
			String key = "ScoreString"+Integer.toString(i);
			String score = prefs.getString(key, "00");
			key = "NameString"+Integer.toString(i);
			String name = prefs.getString(key, "ErrorScreenHighScore");
			names[i] = Integer.toString(i+1)+"\nUsername: "+name+"\n"+"Score: "+score;
		} 
		/** put the score in the list */
		ListView lv = (ListView) findViewById(R.id.ListViewScores);
		lv.setAdapter(new ArrayAdapter<String>(this, R.layout.rowhighscores,R.id.ScoreString, names));
	}
}
