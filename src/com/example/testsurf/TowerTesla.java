package com.example.testsurf;

import java.util.ArrayList;

import android.content.Context;

public class TowerTesla extends Tower 
{
	public ArrayList<Creep> targets;

	public TowerTesla(int left, int top, int right, int bottom,	Context gamecontext) 
	{
		super(left, top, right, bottom, gamecontext);
		rng = 150;
		cooldown = 2.0;
		dmg = 60;
		angle = 0;
		base = gamecontext.getResources().getDrawable(R.drawable.teslabase);
		base.setBounds(sides[0], sides[1], sides[2], sides[3]);
		barrel = gamecontext.getResources().getDrawable(R.drawable.teslabarrel);
		barrel.setBounds(sides[0], sides[1], sides[2], sides[3]);
		price = context.getResources().getInteger(R.integer.pricetowertesla);
		saleprice = (int) (price*.6);
		targets = new ArrayList<Creep>();
		targets.ensureCapacity(3);
	}
	
	/**
	 * Finds the next target to fire at if target was null
	 * @param creeplist
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
				if (dist < rng) 
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
		/*if(targets.isEmpty() == false)
		{
			for(int i = 0; i < targets.size(); i++)
			{
				targets.set(i, inRange(targets.get(i)));
			}
		}
		if(targets.size() < 3)
		{
			for(int i = 2; i > targets.size(); i--)
			{
				findTarget(creeplist, i);
			}
		}
		*/
		if((currenttime-last_fire) >= cooldown*1000)
		{
			/*if(target1 != null && target1.getAlive2())
			{
				bulletlist.add(new BulletSimple(pos_x, pos_y, target1,view, dmg));
				target1.decHealth2(dmg);
			}
			if(target2 != null && target2.getAlive2())
			{
				bulletlist.add(new BulletSimple(pos_x, pos_y, target2,view, dmg));
				target2.decHealth2(dmg);
			}
			if(target3 != null && target3.getAlive2())
			{
				bulletlist.add(new BulletSimple(pos_x, pos_y, target3,view, dmg));
				target3.decHealth2(dmg);
			}*/
			for(int i = 0; i < targets.size(); i++)
			{
				if(targets.get(i) != null && targets.get(i).getAlive2())
				{
					bulletlist.add(new BulletSimple(pos_x, pos_y, targets.get(i), view, dmg));
					targets.get(i).decHealth2(dmg);
				}
				if(targets.get(i) != null && !targets.get(i).getAlive2())
				{
					targets.set(i, null);
				}
			}
			
			last_fire = currenttime;
		}
	}

	private Creep inRange(Creep target) 
	{
		if(target != null)
		{
			double dist = 0.0;
			double creep_x = 0;
			double creep_y = 0;
			double dx = 0;
			double dy = 0;
			creep_x = target.getPosX();
			creep_y = target.getPosY();
			dx = (pos_x - creep_x);
			dy = (pos_y - creep_y);
			dist = Math.sqrt( (dx*dx) + (dy*dy) );
			if (dist > rng) 
			{
				target = null;
			}
		}
		return target;
	}

}
