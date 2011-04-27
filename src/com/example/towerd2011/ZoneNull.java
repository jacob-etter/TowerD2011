/**
 * This is the Zone that is at the top of the screen where we display scores
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
package com.example.towerd2011;

public class ZoneNull extends Zone{

	public ZoneNull(int left, int top, int right, int bottom,GameView gameview) {
		super(left, top, right, bottom, gameview);
		ID = 3;
		if(theme == 1){
			background = context.getResources().getDrawable(R.drawable.grass);
		}
		else if(theme == 2){
			background = context.getResources().getDrawable(R.drawable.emptyzone);
		}
	}
}
