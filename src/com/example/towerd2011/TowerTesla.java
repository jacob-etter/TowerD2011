/**
 * A tower that can shoot at 3 different targets
 * @author Jacob
 * 
 */
package com.example.towerd2011;

import java.util.ArrayList;

import android.media.MediaPlayer;

public class TowerTesla extends Tower 
{
	public ArrayList<Creep> targets;
	/**
	 * contructor for tesla tower
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @param view
	 */
	public TowerTesla(int left, int top, int right, int bottom,	GameView view) 
	{
		super(left, top, right, bottom, view);
		/** get tower specs */
		range = context.getResources().getInteger(R.integer.towerteslarange);
		cooldown = context.getResources().getInteger(R.integer.towerteslacooldown)/1000.0;
		damage = context.getResources().getInteger(R.integer.towertesladamage); 
		/** get the towers images */
		base = context.getResources().getDrawable(R.drawable.towerteslabase);
		base.setBounds(sides[0], sides[1], sides[2], sides[3]);
		barrel = context.getResources().getDrawable(R.drawable.towerteslabarrel); 
		barrel.setBounds(sides[0], sides[1], sides[2], sides[3]);
		price = context.getResources().getInteger(R.integer.pricetowertesla);
		saleprice = (int) (price*.6);
		targets = new ArrayList<Creep>();
		targets.ensureCapacity(3);
		mp = MediaPlayer.create(context, R.raw.zzap);
		mp.setVolume((float)2.0, (float)2.0);
		sound = view.getSound();
	}

	/**
	 * Finds the next target to fire at if target was null
	 * @param creeplist
	 * @Override
	 */
	protected void findTarget(ArrayList<Creep> creeplist, int index) 
	{
		// Internal Vars
		Creep creep;
		double dist = 0.0;
		double creep_x = 0;
		double creep_y = 0;
		double dx = 0;
		double dy = 0;
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
					if(!targets.contains(creep))
					{
						targets.add(creep);

						break;
					}
				}
			}
		}
	} 
	/** 
	 * Executes the firing of the tower
	 * @param creeplist
	 * @param bulletlist
	 * @param view
	 * @Override
	 */
	public void fire(GameView view) 
	{
		ArrayList<Creep> creeplist = view.getCreeplist();
		ArrayList<Bullet> bulletlist = view.getBulletlist();
		long currenttime=System.currentTimeMillis();
		targets.clear();
		for(int i = 0; i < 3; i++)
		{
			findTarget(creeplist, i);
		}
		if((currenttime-last_fire) >= cooldown*1000)
		{
			for(int i = 0; i < targets.size(); i++)
			{
				if(targets.get(i) != null && targets.get(i).getAlive2())
				{
					bulletlist.add(new BulletTesla(pos_x, pos_y, targets.get(i), view, damage));
					targets.get(i).decHealth2(damage);
					if(sound == 1) {
						mp.start();
					}
				}
				if(targets.get(i) != null && !targets.get(i).getAlive2())
				{
					targets.set(i, null);
				}
			}

			last_fire = currenttime;
		}
	}
}
