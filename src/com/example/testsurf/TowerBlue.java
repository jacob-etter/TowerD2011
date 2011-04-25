package com.example.testsurf;

/**
 * A simple test tower
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
public class TowerBlue extends Tower{

	public TowerBlue(int left, int top, int right, int bottom, GameView gameview) {
		super(left, top, right, bottom, gameview);
		base = context.getResources().getDrawable(R.drawable.blue2);
		price = context.getResources().getInteger(R.integer.pricetowerblue);
		base.setBounds(sides[0], sides[1], sides[2], sides[3]);
		saleprice = (int) (price*.6);
	}
}
