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
	protected int[] sides;//left top right bottom
	protected int ID;
	protected Paint red = new Paint();
	protected Paint blue = new Paint();
	protected Paint green = new Paint();
	protected Paint black = new Paint();
	protected Paint white = new Paint();
	protected Paint shade = new Paint();
	protected boolean highlighted;
	protected Context context;
	protected Drawable background;
	protected GameView view;
	protected int theme;

	public Zone(int left,int top,int right,int bottom,GameView gameview){
		sides = new int[4];
		highlighted = false;
		sides[0] = left;
		sides[1] = top;
		sides[2] = right;
		sides[3] = bottom;
		red.setARGB(255, 255, 0, 0);
		blue.setARGB(255, 0, 0, 255);
		green.setARGB(255, 0, 255, 0);
		black.setARGB(255, 0, 0, 0);
		white.setARGB(255, 255, 255, 255);
		shade.setARGB(100, 0, 0, 0);
		ID = 0;
		view = gameview;
		context = gameview.getContext();
		theme = view.getTheme();
	}
	public int[] getSides(){
		return sides;
	}
	public void setHighlight(){
		highlighted = true;
	}
	public void drawSelf(Canvas canvas){
		background.setBounds(sides[0], sides[1], sides[2], sides[3]);
		background.draw(canvas);
		if(highlighted == true){
			canvas.drawRect(sides[0],sides[1],sides[2],sides[3],shade);
		}
	}
	public void removeHighlight(){
		highlighted = false;
	}
	public int getID(){
		return ID;
	}
}