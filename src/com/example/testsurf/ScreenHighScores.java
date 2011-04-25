package com.example.testsurf;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ScreenHighScores extends ListActivity{
	SharedPreferences prefs;
	/** Called when the activity is first created. */
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String[] names = new String[10];
		for(int i=0; i<10;++i){
			String key = "ScoreString"+Integer.toBinaryString(i);
			String score = prefs.getString(key, "0");
			key = "NameString"+Integer.toBinaryString(i);
			String name = prefs.getString(key, "None");
			names[i] = "Username: "+name+"\t"+"Score: "+score;
		} 
		// Create an array of Strings, that will be put to our ListActivity
//		String[] names = new String[] { "Linux", "Windows7", "Eclipse", "Suse",
//				"Ubuntu", "Solaris", "Android", "iPhone" };
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
