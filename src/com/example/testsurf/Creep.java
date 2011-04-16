package com.example.testsurf;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class Creep {
	protected double pos_x;
	protected double pos_y;
	protected int xsize;
	protected int ysize;
	protected double xscale;
	protected double yscale;
	protected int direction;
	protected double speed;
	protected long old_time;
	protected double size;
	protected Context context;
	protected Drawable creepicon;
	protected User user;
	protected boolean alive = true;
	protected int health;
	protected GameView gameview;
	protected int moneyval;
	public Creep(float xloc, float yloc,User gameuser, GameView view){
		gameview= view;
		context = view.getContext();
		xsize = view.getWidth();
		ysize = view.getHeight();
		direction = 1;
		alive = true;
		pos_x = xloc;
		pos_y = yloc;
		user = gameuser;
		old_time = System.currentTimeMillis();
	}
	public void move(long current_time){
		if(alive){
			long diff = old_time-current_time;
			switch(direction){
			case 1: pos_x-=speed*diff/1000;break;
			case 2: pos_y-=speed*diff/1000;break;
			case 3: pos_x+=speed*diff/1000;break;
			case 4: pos_y+=speed*diff/1000;break;
			}
			old_time = current_time;
		}
	}
	public void drawSelf(Canvas canvas){
		if(alive){
			int top = (int) (pos_y-10*yscale);
			int bottom = (int) (pos_y+10*yscale);
			int right = (int) (pos_x+10*xscale);
			int left = (int) (pos_x-10*xscale);
			if(left > xsize){
				alive = false;
				user.decLives(1);
				return;
			}
			creepicon.setBounds(left, top, right, bottom);
			creepicon.draw(canvas);
		}
	}
	public void decHealth(int value){
		health -= value;
		if(health < 0){
			alive = false;
			user.incMoney(moneyval);
			user.incScore(1);
		}
	}
	public double getPosX(){
		return pos_x;
	}
	public double getPosY(){
		return pos_y;
	}
	public boolean getAlive(){
		return alive;
	}
}
