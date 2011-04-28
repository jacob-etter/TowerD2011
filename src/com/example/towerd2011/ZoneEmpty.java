/**
 * A zone that can be turned into a tower
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
package com.example.towerd2011;

public class ZoneEmpty extends Zone{
	public ZoneEmpty(int left, int top, int right, int bottom, GameView gameview) {
		super(left, top, right, bottom, gameview);
		ID = 2;
		if(theme == 1){
			background = context.getResources().getDrawable(R.drawable.grass);
		}
		else if(theme == 2){
			background = context.getResources().getDrawable(R.drawable.emptyzone);
		}
	}
}
