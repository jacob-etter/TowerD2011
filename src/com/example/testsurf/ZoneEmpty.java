package com.example.testsurf;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class ZoneEmpty extends Zone{
	public ZoneEmpty(int left, int top, int right, int bottom, Context gamecontext) {
		super(left, top, right, bottom, gamecontext);
		ID = 2;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void drawSelf(Canvas canvas,Context context){
		Drawable graphic;
		graphic = context.getResources().getDrawable(R.drawable.emptyzone);
		graphic.setBounds(sides[0], sides[1], sides[2], sides[3]);
		graphic.draw(canvas);
		if(highlighted == true){
			canvas.drawRect(sides[0],sides[1],sides[2],sides[3],shade);
		}
	}

}
