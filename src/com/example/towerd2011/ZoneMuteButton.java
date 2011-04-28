package com.example.towerd2011;

public class ZoneMuteButton extends Zone{
	public ZoneMuteButton(int left, int top, int right, int bottom, GameView gameview) {
		super(left, top, right, bottom, gameview);
		ID = 5;
		if(theme == 1){
			background = context.getResources().getDrawable(R.drawable.soundclassic);
		}
		else if(theme == 2){
			background = context.getResources().getDrawable(R.drawable.soundmodern);
		}
	}

}
