package com.example.testsurf;


public class ZoneNull extends Zone{

	public ZoneNull(int left, int top, int right, int bottom,GameView gameview) {
		super(left, top, right, bottom, gameview);
		ID = 3;
		if(view.getTheme() == 1){
			background = context.getResources().getDrawable(R.drawable.grass);
		}
		else if(view.getTheme() == 2){
			background = context.getResources().getDrawable(R.drawable.emptyzone);
		}
	}

}
