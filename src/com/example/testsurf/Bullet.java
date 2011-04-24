package com.example.testsurf;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
/**
 * This is the bullet superclass
 * The bullet will be fired from a tower and target a creep to do damage
 * 
 * @author Sean
 *
 */
public class Bullet {
	protected Creep target;
	protected boolean t_alive;
	protected double dead_x;
	protected double dead_y;
	protected double pos_x;
	protected double pos_y;
	protected double speed;
	protected long old_time;
	protected boolean alive;
	protected Drawable bullet;
	protected Context context;
	protected double size;
	protected double xscale;
	protected double yscale;
	protected int xsize;
	protected int ysize;
	protected boolean x_side = false;
	protected boolean y_side = false;
	protected int damage;
	protected boolean hit_x = false;
	protected boolean hit_y = false;
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
		context = view.getContext();
		damage = dmg;
		xsize = view.getWidth();
		ysize = view.getHeight();
		pos_x = x;
		pos_y = y;
		target = creep_target;
		old_time = System.currentTimeMillis();
		if(pos_x>target.getPosX())
			x_side = true;
		if(pos_y>target.getPosY())
			y_side = true;
		alive = true;
		t_alive = true;
	}
	/**
	 * When called updates the position of the bullet
	 * 
	 * @param currenttime
	 */
	public void move(long currenttime){ 
		if((alive)&&(t_alive)){
			double tar_x;
			double tar_y;
			long diff = currenttime-old_time;
			tar_x = target.getPosX();
			tar_y = target.getPosY();
			if((pos_x<tar_x)==x_side)
				hit_x = true;
			if((pos_y<tar_y)==y_side)
				hit_y = true;
			if((hit_x)&&(hit_y)){
				alive = false;
				target.decHealth(damage);
				return;
			}
			else if(target.getAlive()==false){
				dead_x = tar_x;
				dead_y = tar_y;
				t_alive = false;
				return;
			}
			tar_x = (tar_x-pos_x);
			tar_y = (tar_y-pos_y);
			pos_x += speed*diff/1000*(tar_x/(Math.abs(tar_y)+Math.abs(tar_x)));
			pos_y += speed*diff/1000*(tar_y/(Math.abs(tar_y)+Math.abs(tar_x)));
			old_time = currenttime; 
		}
		else if(alive){
			double tar_x;
			double tar_y;
			long diff = currenttime-old_time;
			tar_x = dead_x;
			tar_y = dead_y;
			if(((pos_x<tar_x)==x_side)||((pos_y<tar_y)==y_side)){
				alive = false;
				return;
			}
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
		if((alive)&&(t_alive)){
			double tar_x;
			double tar_y;
			tar_x = target.getPosX();
			tar_y = target.getPosY();
			if((pos_x<tar_x)==x_side)
				hit_x = true;
			if((pos_y<tar_y)==y_side)
				hit_y = true;
			if((hit_x)&&(hit_y)){
				alive = false;
				target.decHealth(damage);
				return;
			}
			int top = (int) (pos_y-yscale);
			int bottom = (int) (pos_y+yscale);
			int right = (int) (pos_x+xscale);
			int left = (int) (pos_x-xscale);
			bullet.setBounds(left, top, right, bottom);
			bullet.draw(canvas);
		}
		else if(alive){
			double tar_x;
			double tar_y;
			tar_x = dead_x;
			tar_y = dead_y;
			if(((pos_x<tar_x)==x_side)||((pos_y<tar_y)==y_side)){
				alive = false;
				return;
			}
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

}
