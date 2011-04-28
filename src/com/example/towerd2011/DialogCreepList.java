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
		setContentView(R.layout.dialogcreeps);
		ListView lv = (ListView) findViewById(R.id.ListView);
		ArrayList<Creep> creeplist = view.getCreeplist();
		String[] names = new String[creeplist.size()+1];
		if(creeplist.size() == 0){
			names[0] = "No Creeps In this Zone";
		}
		else{
			names[0] = "\nCreeps\n";
		}
		for(int i=1;i<(creeplist.size()+1);i++){
			Creep creep = creeplist.get(i-1);
			double health = creep.getHealth();
			double health_original = creep.getHealthOriginal();
			int percent = (int) (health/health_original*100);
			String health_percent = Integer.toString(percent)+"%";
			String type = creep.getType();
			names[i] = Integer.toString(i)+"\nType = "+type+"\nHealth = "+health_percent;
		}
		lv.setAdapter(new ArrayAdapter<String>(view.getContext(), R.layout.dialoglistrow,R.id.ScoreString, names));
		Close = (Button) findViewById(R.id.ButtonClose);
		Close.setOnClickListener(this);
	}
	public void onClick(View v) {
		if (v == Close){
			exitDialog();
		}
	}
}