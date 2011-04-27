/**
 * This is the superclass of dialog 
 * 
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
package com.example.towerd2011;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.Window;

public class DialogCustom extends Dialog{
	protected GameView view;
	protected int x_pos;
	protected int y_pos;
	protected User user;
	protected ArrayList<Tower> towerlist;
	protected Context context;
	/**
	 * 
	 * @param gameview
	 * @param xloc
	 * @param yloc
	 */
	public DialogCustom(GameView gameview, int xloc, int yloc){
		super(gameview.getContext());
		context = gameview.getContext();
		towerlist = gameview.getTowerlist();
		user = gameview.getUser();
		x_pos = xloc;
		y_pos = yloc;
		view = gameview;
		/** 'Window.FEATURE_NO_TITLE' - Used to hide the title */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/** Design the dialog in main.xml file */
	}
	protected void exitDialog(){
		view.getThread().gameResume();
		dismiss();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        view.getThread().gameResume();
	    }
	    return super.onKeyDown(keyCode, event);
	}
}

