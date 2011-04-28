/**
 * This is the creep superclass
 * The creep will follow a path until is reaches the end or is killed by a tower
 * 
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
package com.example.towerd2011;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class Creep {
	/** the current postion of the creep */
	protected double pos_x;
	protected double pos_y;
	/** the size of the surface view */
	protected int xsize;
	protected int ysize;
	/** a scaling factor used in the game */
	protected double xscale;
	protected double yscale;
	/** speed of the creep */
	protected double speed;
	/** the last time move was called. Used for calcuating movement */
	protected long old_time;
	/** Size of the creep */
	protected double size;
	/** The gameview */
	protected GameView view;
	/** main creep icon */
	protected Drawable creepicon;
	/** 
	 * a boolean to keep track if the creep is alive
	 * The second alive is the anticipated state of the creep
	 * after all bullets that are targeting the creep have hit it 
	 */
	protected boolean alive = true;
	protected boolean alive2 = true;
	/**
	 * Similar to health with two health where one is the 
	 * anticipated health after a bullet hits the creep
	 */
	protected int health;
	protected int health2;
	/** 
	 * the starting health of the creep used in the creeplist and creep dialog
	 * to show a percent health
	 */
	protected int original_health;
	/** the value of the creep to the player if it is killed */
	protected int moneyval;
	/** the type of creep */
	protected String type;
	/**
	 * Constructor for Creep
	 * 
	 * @param xloc the starting location of the creep
	 * @param yloc the starting location of the creep
	 * @param view the gameview that created the creep
	 */
	public Creep(float xloc, float yloc, GameView view, double difficulty){
		this.view = view;
		xsize = view.getWidth();
		ysize = view.getHeight();
		alive = true;
		pos_x = xloc;
		pos_y = yloc;
		old_time = System.currentTimeMillis();
		type = "Creep";
	}
	/**
	 * updates the position of the creep
	 * 
	 * @param current_time
	 */
	public void move(long current_time){
		if(alive){
			int x= (int) (pos_x/(xsize/view.getGridSize()[0]));
			int y = (int) (pos_y/(ysize/view.getGridSize()[1]));
			if(x > view.x_grid_size){
				alive = false;
				if(alive2 )
					view.getUser().decLives(1);
				return;
			}
			ZonePath path = (ZonePath) view.getGrid().getGridZone(x, y);
			if(path.getID()!=1){
				alive = false;
				return;
			}
			double[] tar = path.getDirecNext();
			long diff = current_time - old_time;
			double tar_x = (tar[0]-pos_x);
			double tar_y = (tar[1]-pos_y);
			double pos_x1 = speed*diff/1000*(tar_x/(Math.abs(tar_y)+Math.abs(tar_x)))*(xsize/800.0);
			double pos_y1 = speed*diff/1000*(tar_y/(Math.abs(tar_y)+Math.abs(tar_x)))*(ysize/480.0);
			pos_x += pos_x1;
			pos_y += pos_y1;
			if(pos_x > xsize){
				alive = false;
				view.getUser().decLives(1);
				return;
			}
			old_time = current_time;
		}
	}
	/**
	 * draws the creep on the canvas
	 * 
	 * @param canvas
	 */
	public void drawSelf(Canvas canvas){
		if(alive){
			int top = (int) (pos_y-10*yscale);
			int bottom = (int) (pos_y+10*yscale);
			int right = (int) (pos_x+10*xscale);
			int left = (int) (pos_x-10*xscale);
			if(left > xsize){
				alive = false;
				if(alive2)
					view.getUser().decLives(1);
				return;
			}
			creepicon.setBounds(left, top, right, bottom);
			creepicon.draw(canvas);
		}
	}
	/**
	 * Decreases the health of the creep
	 * 
	 * @param value amount to decrement the health of the creep by
	 */
	public void decHealth(int value){
		health -= value;
		if((health <= 0)&&(alive)){
			alive = false;
			view.getUser().incMoney(moneyval);
			view.getUser().incScore(1);
		}
	}
	public void decHealth2(int value){
		health2 -= value;
		if((health2 <= 0)&&(alive2)){
			alive2 = false;
		}
	}
	/**
	 * gets the current x postion of the creep
	 * 
	 * @return
	 */
	public double getPosX(){
		return pos_x;
	}
	/**
	 * gets the current y position of the creep
	 * 
	 * @return
	 */
	public double getPosY(){
		return pos_y;
	}
	/**
	 * gets the current alive state of the creep
	 * 
	 * @return
	 */
	public boolean getAlive(){
		return alive;
	}
	protected boolean getAlive2(){
		return alive2;
	}
	protected String getType(){
		return type;
	}
	protected int getHealth(){
		return health;
	}
	protected int getHealthOriginal(){
		return original_health;
	}
	public void incOldTime(long value){
		old_time += value;
	}
}
