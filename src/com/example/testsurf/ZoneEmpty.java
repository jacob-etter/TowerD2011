package com.example.testsurf;


/**
 * A zone that can be turned into a tower
 * @author Sean
 *
 */
public class ZoneEmpty extends Zone{
	public ZoneEmpty(int left, int top, int right, int bottom, GameView gameview) {
		super(left, top, right, bottom, gameview);
		ID = 2;
		if(view.getTheme() == 1){
			background = context.getResources().getDrawable(R.drawable.grass);
		}
		else if(view.getTheme() == 2){
			background = context.getResources().getDrawable(R.drawable.emptyzone);
		}
	}
}
