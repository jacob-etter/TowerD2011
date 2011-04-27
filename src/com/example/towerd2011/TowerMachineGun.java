/**
 * A simple tower that has  high rate of fire but small damage
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
package com.example.towerd2011;

public class TowerMachineGun extends Tower 
{
	public TowerMachineGun(int left, int top, int right, int bottom, GameView gameview) 
	{
		super(left, top, right, bottom, gameview);
		rng = context.getResources().getInteger(R.integer.towermachinerange);
		cooldown = context.getResources().getInteger(R.integer.towermachinecooldown)/1000.0;
		dmg = context.getResources().getInteger(R.integer.towermachinedamage); 
		base = context.getResources().getDrawable(R.drawable.machinegunbase);
		base.setBounds(sides[0], sides[1], sides[2], sides[3]);
		barrel = context.getResources().getDrawable(R.drawable.machinegunbarrel);
		barrel.setBounds(sides[0], sides[1], sides[2], sides[3]);
		price = context.getResources().getInteger(R.integer.pricetowermachinegun);
		saleprice = (int) (price*.6);
	}
	@Override
	protected Bullet newBullet(int pox_x,int pox_y,Creep cur_target,GameView view,int dmg){
		return new BulletSimple(pos_x, pos_y, cur_target,view, dmg);
	}
}
