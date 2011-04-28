/**
 * A tower that has a long range and damage but long cooldown
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
package com.example.towerd2011;

public class TowerRifle extends Tower 
{
	public TowerRifle(int left, int top, int right, int bottom,	GameView gameview) 
	{
		super(left, top, right, bottom, gameview);
		rng = context.getResources().getInteger(R.integer.towerriflerange);
		cooldown = context.getResources().getInteger(R.integer.towerriflecooldown)/1000.0;
		dmg = context.getResources().getInteger(R.integer.towerrifledamage); 
		if(gameview.getTheme() == 1)
		{
			base = context.getResources().getDrawable(R.drawable.cannonbase);
			barrel = context.getResources().getDrawable(R.drawable.cannonhead);
		}
		else
		{
			base = context.getResources().getDrawable(R.drawable.machinegunbase);
			barrel = context.getResources().getDrawable(R.drawable.machinegunbarrel);
		}		
		base.setBounds(sides[0], sides[1], sides[2], sides[3]);
		barrel.setBounds(sides[0], sides[1], sides[2], sides[3]);
		price = context.getResources().getInteger(R.integer.pricetowerrifle);
		saleprice = (int) (price*.6);
	}
}
