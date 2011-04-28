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
	/** the game view for the dialog */
	protected GameView view;
	/** the position clicked in the grid */
	protected int x_index;
	protected int y_index;
	/** the current user */
	protected User user;
	/** the towerlist to be used */
	protected ArrayList<Tower> towerlist;
	/** the context of the gameview */
	protected Context context;
	/**
	 * 
	 * @param gameview
	 * @param xloc
	 * @param yloc
	 */
	public DialogCustom(GameView gameview, int xloc, int yloc){
		/** send the context and our custom button theme to Dialog constructor */
		super(gameview.getContext(), com.example.towerd2011.R.style.CustomDialog);
		context = gameview.getContext();
		towerlist = gameview.getTowerlist();
		user = gameview.getUser();
		x_index = xloc;
		y_index = yloc;
		view = gameview;
		/** 'Window.FEATURE_NO_TITLE' - Used to hide the title */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/** Design the dialog in main.xml file */
	}
	/**
	 * used to replace dismiss so we resume the game before we dismiss
	 */
	protected void exitDialog(){
		view.getThread().gameResume();
		dismiss();
	}
	/**
	 * Overrides the back button so that we resume the game before we leave the dialog
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        view.getThread().gameResume();
	    }
	    return super.onKeyDown(keyCode, event);
	}
}

