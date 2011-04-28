/**
 * The Superclass for towers
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
package com.example.towerd2011;

import java.util.ArrayList;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;

public class Tower extends Zone{
	/** sale price of tower and the price of the tower */
	protected int saleprice;
	protected int price;
	/** the images for the tower */
	protected Drawable base; 
	protected Drawable barrel; 
	/** the angle of the barrel */
	protected double angle; 
	/** range of the tower */
	protected double range; 
	/** the tower cooldown */
	protected double cooldown;
	/** the last time the tower fired */
	protected long last_fire;
	/** the damage of the tower */
	protected int damage;
	/** the postion of the tower */
	protected int pos_x;
	protected int pos_y;
	/** target to the tower */
	protected Creep cur_target;
	/** cooldown upgrade price */
	protected int cooldown_price;
	/** range upgrad price */
	protected int range_price;
	/** damage upgrade price */
	protected int damage_price;
	/** media player for sound */
	protected MediaPlayer mp;
	/** the sound value */
	protected int sound;

	public Tower(int left, int top, int right, int bottom, GameView gameview) {
		super(left, top, right, bottom, gameview);
		/** set the zone id for the tower */
		zone_id = 6;
		/** default angle of barrel */
		angle = 0;
		/** Position of the tower */
		pos_x = (sides[0] + sides[2])/2;
		pos_y = (sides[1] + sides[3])/2;
		/** current target needs be initialized to null */
		cur_target = null;
		/** set the background based on the theme in use */
		if(theme == 1){
			background = context.getResources().getDrawable(R.drawable.grass);
		}
		else if(theme == 2){
			background = context.getResources().getDrawable(R.drawable.emptyzone);
		}
		background.setBounds(sides[0], sides[1], sides[2], sides[3]);
		barrel = null;
		/** sound settings */
		mp = MediaPlayer.create(context, R.raw.pewpew);
		mp.setVolume((float) 1.3, (float) 1.3);
		sound = gameview.getSound();
	}
	/**
	 * drawns the tower
	 */
	@Override
	public void drawSelf(Canvas canvas)
	{
		/** drawns the background and base */
		background.draw(canvas);
		base.draw(canvas);
		/** draws the barrel at the right angle */
		if(barrel != null){
			if(cur_target != null)
			{
				angle = Math.atan2((pos_y - cur_target.pos_y),(pos_x - cur_target.pos_x));
				angle = angle * 180/Math.PI;
				angle -= 90;
			}
			canvas.save();
			canvas.rotate((float)(angle), (float)pos_x, (float)pos_y);
			barrel.draw(canvas);
			canvas.restore();
		}
		/** highlights the zone if it is selected by the user */
		if(highlighted == true){
			canvas.drawRect(sides[0],sides[1],sides[2],sides[3],paint_highlighed);
		}
	}
	/**
	 * Finds the next target to fire at if target was null
	 * @param creeplist
	 */
	protected void findTarget(ArrayList<Creep> creeplist) 
	{
		/** Internal Vars */
		Creep creep;
		double dist = 0.0;
		double creep_x = 0;
		double creep_y = 0;
		double dx = 0;
		double dy = 0;
		/** Search for a target */
		for(int i=0;i<creeplist.size();++i) 
		{
			creep = creeplist.get(i);
			if(creep.getAlive2()){
				creep_x = creep.getPosX();
				creep_y = creep.getPosY();
				dx = (pos_x - creep_x);
				dy = (pos_y - creep_y);
				dist = Math.sqrt( (dx*dx) + (dy*dy) );
				if (dist < range) 
				{
					cur_target = creep;
					break;
				}
			}
		}
	} 
	/** 
	 * Executes the firing of the tower
	 * @param creeplist
	 * @param bulletlist
	 * @param view
	 */
	public void fire(GameView view) 
	{
		/** loads creeplist and bullet list */
		ArrayList<Creep> creeplist = view.getCreeplist();
		ArrayList<Bullet> bulletlist = view.getBulletlist();
		long currenttime=System.currentTimeMillis();
		/** if its time to fire then fire */
		if(((currenttime-last_fire))>=cooldown*1000){
			/** if are target isn't null make sure its still in range */
			if(cur_target != null){
				double dist = 0.0;
				double creep_x = 0;
				double creep_y = 0;
				double dx = 0;
				double dy = 0;
				creep_x = cur_target.getPosX();
				creep_y = cur_target.getPosY();
				dx = (pos_x - creep_x);
				dy = (pos_y - creep_y);
				dist = Math.sqrt( (dx*dx) + (dy*dy) );
				if (dist > range) 
				{
					cur_target = null;
				}
			}
			/** if the target is null get a new target */
			if(cur_target == null){
				findTarget(creeplist);
			}
			/** if we have a target and our target is alive then fire */
			if((cur_target != null)&&(cur_target.getAlive2())){
				bulletlist.add(newBullet(pos_x, pos_y, cur_target,view, damage));
				cur_target.decHealth2(damage);
				last_fire = currenttime;
				if(sound == 1) {
					mp.start();
				}
			}
			/** if our target is not alieve and not not null then get a new target */
			else if((cur_target != null)&&(cur_target.getAlive2() == false)){
				cur_target = null;
			}
		}
	}
	/**
	 * make a bullet this is so that subtowers can override just the
	 * new bullet class and not all of fire if they need a different bullet type
	 * @param pox_x
	 * @param pox_y
	 * @param cur_target
	 * @param view
	 * @param dmg
	 * @return
	 */
	protected Bullet newBullet(int pox_x,int pox_y,Creep cur_target,GameView view,int dmg){
		return new BulletCannon(pos_x, pos_y, cur_target,view, dmg);
	}
	/**
	 * 
	 * @return
	 */
	public int getSalePrice(){
		return saleprice;
	}
	/**
	 * 
	 * @return
	 */
	public int getCooldownPrice(){
		if(cooldown_price == 0){
			cooldown_price = (int) (saleprice/.6*.5);
		}
		return cooldown_price;
	}
	/**
	 * 
	 * @return
	 */
	public int getDamagePrice(){
		if(damage_price == 0){
			damage_price = (int) (saleprice/.6*.5);
		}
		return damage_price;
	}
	/**
	 * 
	 * @return
	 */
	public int getRangePrice(){
		if(range_price == 0){
			range_price = (int) (saleprice/.6*.5);
		}
		return range_price;
	}
	/**
	 * 
	 */
	public void upCooldown(){
		cooldown *= .5; 
		saleprice += .6*cooldown_price;
		cooldown_price *= 1.5;
	}
	/**
	 * 
	 */
	public void upDamage(){
		damage *= 1.5;
		saleprice += .6*damage_price;
		damage_price *= 1.5;
	}
	/**
	 * 
	 */
	public void upRange(){
		range *= 1.5;
		saleprice += .6*range_price;
		range_price *= 1.5;
	}
	/**
	 * 
	 * @param value
	 */
	public void incLastFire(long value){
		last_fire += value;
	}
	/**
	 * 
	 * @param value
	 */
	public void setSound(int value){
		sound = value;
	}
}
