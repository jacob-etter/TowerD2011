/**
 * This is the button that toggles music and sound settings
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
package com.example.towerd2011;

public class ZoneMuteButton extends Zone{
	/**
	 * constructor for the music button 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @param gameview
	 */
	public ZoneMuteButton(int left, int top, int right, int bottom, GameView gameview) {
		super(left, top, right, bottom, gameview);
		zone_id = 5;
		/** set image based on theme */
		if(theme == 1){
			background = context.getResources().getDrawable(R.drawable.soundclassic);
		}
		else if(theme == 2){
			background = context.getResources().getDrawable(R.drawable.soundmodern);
		}
	}

}
