package com.example.testsurf;

/**
 * A simple tower that has  high rate of fire but small damage
 * @author Sean
 *
 */
public class TowerMachineGun extends Tower 
{
	public TowerMachineGun(int left, int top, int right, int bottom, GameView gameview) 
	{
		super(left, top, right, bottom, gameview);
		rng = 150;
		cooldown = 0.2; // This is the number of seconds between shots
		dmg = 5;
		base = context.getResources().getDrawable(R.drawable.machinegunbase);
		base.setBounds(sides[0], sides[1], sides[2], sides[3]);
		barrel = context.getResources().getDrawable(R.drawable.machinegunbarrel);
		barrel.setBounds(sides[0], sides[1], sides[2], sides[3]);
		price = context.getResources().getInteger(R.integer.pricetowermachinegun);
		saleprice = (int) (price*.6);
	}
}
