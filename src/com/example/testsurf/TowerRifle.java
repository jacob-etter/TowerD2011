package com.example.testsurf;

/**
 * A tower that has a long range and damage but long cooldown
 * @author Sean
 *
 */
public class TowerRifle extends Tower 
{
	public TowerRifle(int left, int top, int right, int bottom,	GameView gameview) 
	{
		super(left, top, right, bottom, gameview);
		rng = 300;
		cooldown = 1.5;
		dmg = 50; 
		base = context.getResources().getDrawable(R.drawable.riflebase);
		base.setBounds(sides[0], sides[1], sides[2], sides[3]);
		barrel = context.getResources().getDrawable(R.drawable.riflebarrel);
		barrel.setBounds(sides[0], sides[1], sides[2], sides[3]);
		price = context.getResources().getInteger(R.integer.pricetowerrifle);
		saleprice = (int) (price*.6);
	}
}
