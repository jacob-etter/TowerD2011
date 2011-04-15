package com.example.testsurf;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class Tower extends Zone{
	protected Drawable base; //base png of the tower
	protected Drawable barrel; //barrel png of the tower
	protected double angle; //angle of the barrel
	/* Tower stats */
	protected double rng; //range of the tower
	protected double rof; //rate of fire
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
	void findTarget(Creep[] creeps) 
	{
		// Internal Vars
		double dist = 0.0;
		float creep_x = 0;
		float creep_y = 0;
		float dx = 0;
		float dy = 0;
		// If we already have a target, return that
		if(cur_target == null) 
		{
			for (Creep creep : creeps) 
			{
				creep_x = creep.getPosX();
				creep_y = creep.getPosY();
				dx = Math.abs(pos_x - creep_x);
				dy = Math.abs(pos_y - creep_y);
				dist = Math.sqrt( (dx*dx) + (dy*dy) );
				if ( dist < rng ) 
				{
					cur_target = creep;
				}
			}
		}
		return;
	} 
	void fire(Creep[] creeps, long currenttime) 
	{
		float cooldown = (last_fire-currenttime)/1000;
		if(cooldown >= rof) {
			findTarget(creeps);
			cur_target.decHealth(dmg);
		}
		return;
	}
}
