package com.example.testsurf;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class Tower extends Zone{
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
	protected Drawable background;
	public Tower(int left, int top, int right, int bottom, Context gamecontext) {
		super(left, top, right, bottom, gamecontext);
		ID = 3; //tell gameview that this zone is a tower
		angle = 0;
		pos_x = (sides[0] + sides[2])/2;
		pos_y = (sides[1] + sides[3])/2;
		cur_target = null;
		background = context.getResources().getDrawable(R.drawable.emptyzone);
		background.setBounds(sides[0], sides[1], sides[2], sides[3]);
		barrel = null;
	}
	@Override
	public void drawSelf(Canvas canvas,Context context){
		background.draw(canvas);
		base.draw(canvas);
		if(barrel != null){
			if(cur_target == null)
				angle = 0;
			else
				angle = Math.pow(Math.tan(Math.abs(pos_y - cur_target.pos_y)/Math.abs(pos_x - cur_target.pos_x)), -1.0);
			barrel.draw(canvas);
		}
		else{

		}
		if(highlighted == true){
			canvas.drawRect(sides[0],sides[1],sides[2],sides[3],shade);
		}
	}
	@Override
	public int getSalePrice(){
		return saleprice; 
	}
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
			creep_x = creep.getPosX();
			creep_y = creep.getPosY();
			dx = Math.abs(pos_x - creep_x);
			dy = Math.abs(pos_y - creep_y);
			dist = Math.sqrt( (dx*dx) + (dy*dy) );
			if (dist < rng) 
			{
				cur_target = creep;
				break;
			}
		}
	} 
	public void fire(ArrayList<Creep> creeplist) 
	{
		long currenttime=System.currentTimeMillis();
		if(cur_target == null){
			findTarget(creeplist);
		}
		if((cur_target != null)&&(cur_target.getAlive())){
			if(((currenttime-last_fire)/1000)>=cooldown){
				cur_target.decHealth(dmg);
				last_fire = currenttime;
			}
		}
		else if((cur_target != null)&&(cur_target.getAlive() == false)){
			cur_target = null;
		}
	}
}
