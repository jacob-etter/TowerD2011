/**
 * A super class for Towers, Paths, and Empty zones
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
package com.example.towerd2011;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class Zone {
	/** the sizes of the zone left top right bottom */
	protected int[] sides;
	/** the id for the zone */
	protected int zone_id;
	/** the highlight color */
	protected Paint paint_highlighed = new Paint();
	/** is the zone highlighted */
	protected boolean highlighted;
	/** the context of the zone */
	protected Context context;
	/** the background of the zone */
	protected Drawable background;
	/** the game view */
	protected GameView view;
	/** the current theme */
	protected int theme;
	/**
	 * zone cunstructor 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @param gameview
	 */
	public Zone(int left,int top,int right,int bottom,GameView gameview){
		sides = new int[4];
		highlighted = false;
		sides[0] = left;
		sides[1] = top;
		sides[2] = right;
		sides[3] = bottom;
		paint_highlighed.setARGB(100, 0, 0, 0);
		zone_id = 0;
		view = gameview;
		context = gameview.getContext();
		theme = view.getTheme();
	}
	/**
	 * 
	 * @return
	 */
	public int[] getSides(){
		return sides;
	}
	/**
	 * 
	 */
	public void setHighlight(){
		highlighted = true;
	}
	/**
	 * 
	 * @param canvas
	 */
	public void drawSelf(Canvas canvas){
		background.setBounds(sides[0], sides[1], sides[2], sides[3]);
		background.draw(canvas);
		if(highlighted == true){
			canvas.drawRect(sides[0],sides[1],sides[2],sides[3],paint_highlighed); 
		}
	}
	/**
	 * 
	 */
	public void removeHighlight(){
		highlighted = false;
	}
	/**
	 * 
	 * @return
	 */
	public int getID(){
		return zone_id;
	}
}
