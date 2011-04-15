package com.example.testsurf;

import android.content.Context;

public class TowerRifle extends Tower 
{
	public TowerRifle(int left, int top, int right, int bottom,	Context gamecontext) 
	{
		super(left, top, right, bottom, gamecontext);
		rng = 50;
		rof = 1; // This is the number of seconds between shots
		dmg = 10; 
		base = gamecontext.getResources().getDrawable(R.drawable.riflebase);
		base.setBounds(sides[0], sides[1], sides[2], sides[3]);
		barrel = gamecontext.getResources().getDrawable(R.drawable.riflebarrel);
		barrel.setBounds(sides[0], sides[1], sides[2], sides[3]);
	}
}
