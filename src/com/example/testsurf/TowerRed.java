package com.example.testsurf;

/**
 * A Test tower
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
public class TowerRed extends Tower{

	public TowerRed(int left, int top, int right, int bottom, GameView gameview) {
		super(left, top, right, bottom, gameview);
		price = context.getResources().getInteger(R.integer.pricetowerred);
		base = context.getResources().getDrawable(R.drawable.red2);
		base.setBounds(sides[0], sides[1], sides[2], sides[3]);
		saleprice = (int) (price*.6);
	}
}
