package com.example.testsurf;

import java.util.ArrayList;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
/**
 * The Superclass for towers
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
public class Tower extends Zone{
	/*price*/
	protected int saleprice;
	protected int price;
	protected Drawable base; //base png of the tower
	protected Drawable barrel; //barrel png of the tower
	protected double angle; //angle of the barrel
	/* Tower stats */
	protected double rng; //range of the tower
	protected double cooldown; //rate of fire
	protected long last_fire; //the time that the tower last fired
	protected int dmg; //damge done by the tower
	protected int pos_x; //x positon of the tower
	protected int pos_y; //y position of the tower
	/* Target */
	protected Creep cur_target; //The creep we are targeting
	/* Upgrades */
	protected int cd_price = 0;
	protected int rng_price = 0;
	protected int dmg_price = 0;
	/* Sound */
	protected MediaPlayer mp;
	
	public Tower(int left, int top, int right, int bottom, GameView gameview) {
		super(left, top, right, bottom, gameview);
		saleprice = 0;
		ID = 4; //tell gameview that this zone is a tower
		angle = 0;
		pos_x = (sides[0] + sides[2])/2;
		pos_y = (sides[1] + sides[3])/2;
		cur_target = null;
		if(theme == 1){
			background = context.getResources().getDrawable(R.drawable.grass);
		}
		else if(theme == 2){
			background = context.getResources().getDrawable(R.drawable.emptyzone);
		}
		background.setBounds(sides[0], sides[1], sides[2], sides[3]);
		barrel = null;
		mp = MediaPlayer.create(context, R.raw.pewpew);
		//mp.setLooping(false);
	}
	@Override
	public void drawSelf(Canvas canvas)
	{
		background.draw(canvas);
		base.draw(canvas);
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
		if(highlighted == true){
			canvas.drawRect(sides[0],sides[1],sides[2],sides[3],shade);
		}
	}
	/**
	 * Finds the next target to fire at if target was null
	 * @param creeplist
	 */
	protected void findTarget(ArrayList<Creep> creeplist) 
	{
		// Internal Vars
		Creep creep;
		double dist = 0.0;
		double creep_x = 0;
		double creep_y = 0;
		double dx = 0;
		double dy = 0;
		// If we already have a target, return that
		for(int i=0;i<creeplist.size();++i) 
		{
			creep = creeplist.get(i);
			if(creep.getAlive2()){
				creep_x = creep.getPosX();
				creep_y = creep.getPosY();
				dx = (pos_x - creep_x);
				dy = (pos_y - creep_y);
				dist = Math.sqrt( (dx*dx) + (dy*dy) );
				if (dist < rng) 
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
		ArrayList<Creep> creeplist = view.getCreeplist();
		ArrayList<Bullet> bulletlist = view.getBulletlist();
		long currenttime=System.currentTimeMillis();
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
			if (dist > rng) 
			{
				cur_target = null;
			}
		}
		if(cur_target == null){
			findTarget(creeplist);
		}
		if((cur_target != null)&&(cur_target.getAlive2())){
			if(((currenttime-last_fire))>=cooldown*1000){
				bulletlist.add(newBullet(pos_x, pos_y, cur_target,view, dmg));
				cur_target.decHealth2(dmg);
				last_fire = currenttime;
				//if(sound) {
					mp.start();
				//}
			}
		}
		else if((cur_target != null)&&(cur_target.getAlive2() == false)){
			cur_target = null;
		}
		
	}
	protected Bullet newBullet(int pox_x,int pox_y,Creep cur_target,GameView view,int dmg){
		return new BulletCannon(pos_x, pos_y, cur_target,view, dmg);
	}
	public int getSalePrice(){
		return saleprice;
	}
	public int getCooldownPrice(){
		if(cd_price == 0){
			cd_price = (int) (saleprice*.5);
		}
		return cd_price;
	}
	public int getDamagePrice(){
		if(dmg_price == 0){
			dmg_price = (int) (saleprice*.5);
		}
		return dmg_price;
	}
	public int getRangePrice(){
		if(rng_price == 0){
			rng_price = (int) (saleprice*.5);
		}
		return rng_price;
	}
	public void upCooldown(){
		cooldown *= .5; 
		saleprice += .6*cd_price;
		cd_price *= 1.5;
	}
	public void upDamage(){
		dmg *= 1.5;
		saleprice += .6*dmg_price;
		dmg_price *= 1.5;
	}
	public void upRange(){
		rng *= 1.5;
		saleprice += .6*rng_price;
		rng_price *= 1.5;
	}
}
