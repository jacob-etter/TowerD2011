package com.example.testsurf;

import android.content.Context;

public class TowerMachineGun extends Tower 
{
	public TowerMachineGun(int left, int top, int right, int bottom, Context gamecontext) 
	{
		super(left, top, right, bottom, gamecontext);
		rng = 40;
		rof = 0.2; // This is the number of seconds between shots
		dmg = 5;
		base = gamecontext.getResources().getDrawable(R.drawable.riflebase);
		base.setBounds(sides[0], sides[1], sides[2], sides[3]);
		barrel = gamecontext.getResources().getDrawable(R.drawable.riflebarrel);
		barrel.setBounds(sides[0], sides[1], sides[2], sides[3]);
	}
}
