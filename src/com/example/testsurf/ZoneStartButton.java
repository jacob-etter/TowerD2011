package com.example.testsurf;


public class ZoneStartButton extends Zone{

	public ZoneStartButton(int left, int top, int right, int bottom, GameView gameview) {
		super(left, top, right, bottom, gameview);
		ID = 0;
		if(view.getTheme() == 1){
			background = context.getResources().getDrawable(R.drawable.startbuttonclassic);
		}
		else if(view.getTheme() == 2){
			background = context.getResources().getDrawable(R.drawable.startbutton);
		}
	}

}
