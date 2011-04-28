/**
 * A zone that can be turned into a tower
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
package com.example.towerd2011;

public class ZoneEmpty extends Zone{
	/**
	 * constructor for the zone
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @param gameview
	 */
	public ZoneEmpty(int left, int top, int right, int bottom, GameView gameview) {
		super(left, top, right, bottom, gameview);
		zone_id = 2;
		/** set image pased on theme */
		if(theme == 1){
			background = context.getResources().getDrawable(R.drawable.grass);
		}
		else if(theme == 2){
			background = context.getResources().getDrawable(R.drawable.emptyzone);
		}
	}
}
