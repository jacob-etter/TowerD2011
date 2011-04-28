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

public class DialogCreepZone extends DialogCustom implements OnClickListener{
	Button Close;
	/**
	 * 
	 * @param gameview
	 * @param xloc
	 * @param yloc
	 */
	public DialogCreepZone(GameView gameview, int xloc, int yloc){
		super(gameview,xloc,yloc);
		/** set the layout */
		setContentView(R.layout.dialogcreeps);
		/** grab the creeplist and create our own creeplist for creeps found in this zone*/
		ArrayList<Creep> creeplist = view.getCreeplist();
		ArrayList<Creep> creepsfound = new ArrayList<Creep>();
		int[] sides = view.getGrid().getGridZone(x_index,y_index).getSides();
		/** search for creeps in the zone and add them to creeps found list */
		for(int i=0;i < creeplist.size();++i){
			Creep creep = creeplist.get(i);
			int x = (int) creep.getPosX();
			int y = (int) creep.getPosY();
			if((x>sides[0])&&(x<sides[2])&&(y>sides[1])&&(y<sides[3])){
				creepsfound.add(creep);
			}
		}
		/** initialize our creep list */
		String[] names = new String[creepsfound.size()+1];
		if(creepsfound.size() == 0){
			names[0] = "No Creeps in this zone";
		}
		else{
			names[0] = "\nCreeps\n";
		}
		/** grab all the info for creeps found in the zone */
		for(int i=1;i<(creepsfound.size()+1);i++){
			Creep creep = creepsfound.get(i-1);
			double health = creep.getHealth();
			double health_original = creep.getHealthOriginal();
			int percent = (int) (health/health_original*100);
			String health_percent = Integer.toString(percent)+"%";
			String type = creep.getType();
			names[i] = Integer.toString(i)+"\nType = "+type+"\nHealth = "+health_percent;
		}
		/** find the list view and put our string array in the list */
		ListView lv = (ListView) findViewById(R.id.ListView);
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