package com.example.testsurf;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
/**
 * This is the superclass of dialog 
 * 
 * @author Sean
 *
 */
public class DialogTower extends Dialog{
	protected GameView view;
	protected int x_pos;
	protected int y_pos;
	protected User user;
	protected Context context;
	protected ArrayList<Tower> towerlist;
	/**
	 * 
	 * @param gameview
	 * @param xloc
	 * @param yloc
	 */
	public DialogTower(GameView gameview, int xloc, int yloc){
		super(gameview.getContext());
		towerlist = gameview.getTowerlist();
		context = gameview.getContext();
		user = gameview.getUser();
		x_pos = xloc;
		y_pos = yloc;
		view = gameview;
		/** 'Window.FEATURE_NO_TITLE' - Used to hide the title */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/** Design the dialog in main.xml file */
	}
}

