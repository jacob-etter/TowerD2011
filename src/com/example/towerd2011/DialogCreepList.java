/**
 * This is the superclass of dialog 
 * 
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
package com.example.towerd2011;

import java.util.ArrayList;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class DialogCreepList extends DialogCustom implements OnClickListener{
	Button Close;
	/**
	 * 
	 * @param gameview
	 * @param xloc
	 * @param yloc
	 */
	public DialogCreepList(GameView gameview, int xloc, int yloc){
		super(gameview,xloc,yloc);
		/** set the layout of the dialog */
		setContentView(R.layout.dialogcreeps);
		ArrayList<Creep> creeplist = view.getCreeplist();
		/** create a list to hold the creeps that is one larger
		 * that the number of the creep so that we can have a 
		 * title row to save space in the dialog
		 */
		String[] names = new String[creeplist.size()+1];
		/** if no creeps set the title row to be this*/
		if(creeplist.size() == 0){
			names[0] = "No Creeps on the map";
		}
		/** else set the title row to be this */
		else{
			names[0] = "\nCreeps\n";
		}
		/** for loop to get the info for each creep and put it in the string array */
		for(int i=1;i<(creeplist.size()+1);i++){
			Creep creep = creeplist.get(i-1);
			double health = creep.getHealth();
			double health_original = creep.getHealthOriginal();
			int percent = (int) (health/health_original*100);
			String health_percent = Integer.toString(percent)+"%";
			String type = creep.getType();
			names[i] = Integer.toString(i)+"\nType = "+type+"\nHealth = "+health_percent;
		}
		/** grab the list view */
		ListView lv = (ListView) findViewById(R.id.ListView);
		/** put our names string list into the list view */
		lv.setAdapter(new ArrayAdapter<String>(view.getContext(), R.layout.dialoglistrow,R.id.ScoreString, names));
		/** setup the close button */
		Close = (Button) findViewById(R.id.ButtonClose);
		Close.setOnClickListener(this);
	}
	public void onClick(View v) {
		if (v == Close){
			exitDialog();
		}
	}
}