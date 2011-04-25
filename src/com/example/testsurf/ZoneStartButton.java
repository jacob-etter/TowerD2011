package com.example.testsurf;
/**
 * 
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */

public class ZoneStartButton extends Zone{

	public ZoneStartButton(int left, int top, int right, int bottom, GameView gameview) {
		super(left, top, right, bottom, gameview);
		ID = 0;
		if(theme == 1){
			background = context.getResources().getDrawable(R.drawable.startbuttonclassic);
		}
		else if(theme == 2){
			background = context.getResources().getDrawable(R.drawable.startbutton);
		}
	}

}
