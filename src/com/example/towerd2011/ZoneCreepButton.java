package com.example.towerd2011;

public class ZoneCreepButton extends Zone{
	public ZoneCreepButton(int left, int top, int right, int bottom, GameView gameview) {
		super(left, top, right, bottom, gameview);
		ID = 4;
		if(theme == 1){
			background = context.getResources().getDrawable(R.drawable.listiconclassic);
		}
		else if(theme == 2){
			background = context.getResources().getDrawable(R.drawable.listiconmodern);
		}
	}

}
