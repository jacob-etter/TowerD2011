/**
 * This is the Zone that is at the top of the screen where we display scores
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
package com.example.towerd2011;

public class ZoneNull extends Zone{
	/** 
	 * Constructor for zone null
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @param gameview
	 */
	public ZoneNull(int left, int top, int right, int bottom,GameView gameview) {
		super(left, top, right, bottom, gameview);
		zone_id = 3;
		/** images for background based on them */
		if(theme == 1){
			background = context.getResources().getDrawable(R.drawable.grass);
		}
		else if(theme == 2){
			background = context.getResources().getDrawable(R.drawable.emptyzone); 
		}
	}
}
