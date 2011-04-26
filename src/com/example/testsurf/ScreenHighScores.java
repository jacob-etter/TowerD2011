package com.example.testsurf;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
/**
 * Displays the High Scores
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
public class ScreenHighScores extends ListActivity{
	SharedPreferences prefs;
	/** Called when the activity is first created. */
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		//force landscape
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		int num_scores = prefs.getInt("NumOfScores", 0);
		String[] names = new String[10];
		if(num_scores == 0){
			names = new String[1];
			names[0] = "No Scores Yet";
		}
		else{
			names = new String[num_scores+1];
			names[0] = "\nHIGH SCORES";
		}
		for(int i=1; i<num_scores+1;++i){
			String key = "ScoreString"+Integer.toString(i-1);
			String score = prefs.getString(key, "00");
			key = "NameString"+Integer.toString(i-1);
			String name = prefs.getString(key, "ErrorScreenHighScore");
			names[i] = Integer.toString(i)+"\nUsername: "+name+"\n"+"Score: "+score;
		} 
		// Use your own layout and point the adapter to the UI elements which
		// contains the label
		this.setListAdapter(new ArrayAdapter<String>(this, R.layout.rowlayout,
				R.id.ScoreString, names));
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// Get the item that was clicked
		Object o = this.getListAdapter().getItem(position);
		String keyword = o.toString();
		Toast.makeText(this, "You selected: " + keyword, Toast.LENGTH_LONG)
				.show();

	}
}
