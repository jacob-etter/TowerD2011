/**
 * This is the bullet superclass
 * The bullet will be fired from a tower and target a creep to do damage
 * 
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
package com.example.towerd2011;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class Bullet { 
	/** This the target for the bullet*/
	protected Creep target;
	/** This is a boolean to see if the target is alive*/
	/**The positon of the bullet */
	protected double pos_x;
	protected double pos_y;
	/**The speed that the bullet moves at */
	protected double speed;
	/** This is the last time move was called. used to calculate
	 * how much the bullet should move
	 */
	protected long old_time;
	/**
	 * keeps track if the bullet is alive. If it is dead it will get removed
	 * from the bullet list
	 */
	protected boolean alive;
	/** Image used to draw the bullet*/
	protected Drawable bullet;
	/** the game view used for draing*/
	protected GameView view;
	protected double size;
	/** A scaling factor used for different size screens*/
	protected double xscale;
	protected double yscale;
	/** The size of the game view screen used in the scaling factor*/
	protected int xsize;
	protected int ysize;
	/** Used to tell if a bullet has hit its target */
	protected boolean x_side = false;
	protected boolean y_side = false;
	protected boolean hit_x = false;
	protected boolean hit_y = false;
	/** damage the bullet should deal to the target*/
	protected int damage;
	/**
	 * Constructor for Bullet
	 * 
	 * @param x the starting location of the bullet
	 * @param y the starting location of the bullet
	 * @param creep_target the target for the bullet
	 * @param view the gamview for the bullet
	 * @param dmg the damage to be dealt to the target
	 */
	public Bullet(int x, int y, Creep creep_target,GameView view,int dmg){
		this.view = view;
		damage = dmg;
		xsize = view.getWidth();
		ysize = view.getHeight();
		pos_x = x;
		pos_y = y;
		target = creep_target;
		old_time = System.currentTimeMillis();
		/** set the inital settings used to see if a bullet has reached its target*/
		if(pos_x>target.getPosX())
			x_side = true;
		if(pos_y>target.getPosY())
			y_side = true;
		alive = true;
	}
	/**
	 * When called updates the position of the bullet
	 * 
	 * @param currenttime
	 */
	public void move(long currenttime){ 
		if(alive){
			double tar_x;
			double tar_y;
			long diff = currenttime-old_time;
			tar_x = target.getPosX();
			tar_y = target.getPosY();
			/** Update the bullets position */
			tar_x = (tar_x-pos_x);
			tar_y = (tar_y-pos_y);
			pos_x += speed*diff/1000*(tar_x/(Math.abs(tar_y)+Math.abs(tar_x)));
			pos_y += speed*diff/1000*(tar_y/(Math.abs(tar_y)+Math.abs(tar_x)));
			old_time = currenttime; 
		}
	}
	/**
	 * Draws the bullet on the canvas
	 * 
	 * @param canvas
	 */
	public void drawSelf(Canvas canvas){
		if(alive){
			double tar_x;
			double tar_y;
			tar_x = target.getPosX();
			tar_y = target.getPosY();
			/** check to see if the bullet has hit the target */
			if((pos_x<tar_x)==x_side)
				hit_x = true;
			if((pos_y<tar_y)==y_side)
				hit_y = true;
			if((hit_x)&&(hit_y)){
				alive = false;
				target.decHealth(damage);
				return;
			}
			/** draw the bullet*/
			int top = (int) (pos_y-yscale);
			int bottom = (int) (pos_y+yscale);
			int right = (int) (pos_x+xscale);
			int left = (int) (pos_x-xscale);
			bullet.setBounds(left, top, right, bottom);
			bullet.draw(canvas);
		}
	}
	/**
	 * returns the alive status of the bullet
	 * 
	 * @return
	 */
	public boolean getAlive(){
		return alive;
	}
	/**
	 * changes the old time value used in pausing and unpausing the game
	 * so that the time spent paused is accounted for
	 * @param value
	 */
	public void incOldTime(long value){
		old_time += value;
	}

}
