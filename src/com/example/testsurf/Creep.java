package com.example.testsurf;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class Creep {
	protected float pos_x;
	protected float pos_y;
	protected int xsize;
	protected int ysize;
	protected float xscale;
	protected float yscale;
	protected int direction=1;
	protected float speed = 1;
	protected long old_time;
	protected float size;
	protected Context context;
	protected Drawable creepicon;
	protected User user;
	protected boolean alive = true;
	protected int health;
	public Creep(float xloc, float yloc,User gameuser, Context gamecontext){
		context = gamecontext;
		pos_x = xloc;
		pos_y = yloc;
		user = gameuser;
		creepicon = context.getResources().getDrawable(R.drawable.icon);
		old_time = System.currentTimeMillis();
		size = 1;
	}
	public void move(long current_time){
		if(alive){
			long diff = old_time-current_time;
			switch(direction){
			case 1: pos_x+=speed*diff/1000;break;
			case 2: pos_y-=speed*diff/1000;break;
			case 3: pos_x-=speed*diff/1000;break;
			case 4: pos_y+=speed*diff/1000;break;
			}
		}
	}
	public void drawSelf(Canvas canvas){
		if(true){
			xscale = (float) (xsize/800.0 * size);
			yscale = (float) (ysize/480.0 * size);
			int top = (int) (pos_y+10*yscale);
			int bottom = (int) (pos_y-10*yscale);
			int right = (int) (pos_x+10*xscale);
			int left = (int) (pos_x-10*xscale);
			if(right > xsize){
				user.decLives(1);
				alive = false;
			}
			creepicon.setBounds(left, top, right, bottom);
			creepicon.draw(canvas);
		}
	}
	public void decHealth(int value){
		health -= value;
		if(health < 0)
			alive = false;
	}
	public float getPosX(){
		return pos_x;
	}
	public float getPosY(){
		return pos_y;
	}
}
