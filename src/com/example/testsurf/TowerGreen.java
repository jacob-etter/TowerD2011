package com.example.testsurf;

/**
 * A Simple test tower
 * @author Sean
 *
 */
public class TowerGreen extends Tower{

	public TowerGreen(int left, int top, int right, int bottom, GameView gameview) {
		super(left, top, right, bottom, gameview);
		base = context.getResources().getDrawable(R.drawable.green2);
		base.setBounds(sides[0], sides[1], sides[2], sides[3]);
		price = context.getResources().getInteger(R.integer.pricetowergreen);
		saleprice = (int) (price*.6);
	}
}
