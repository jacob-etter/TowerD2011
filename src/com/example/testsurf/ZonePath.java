package com.example.testsurf;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class ZonePath extends Zone{

	public ZonePath(int left, int top, int right, int bottom, Context gamecontext) {
		super(left, top, right, bottom, gamecontext);
		ID = 1;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void drawSelf(Canvas canvas,Context context){
		Drawable graphic;
		graphic = context.getResources().getDrawable(R.drawable.pathzone);
		graphic.setBounds(sides[0], sides[1], sides[2], sides[3]);
		graphic.draw(canvas);
		if(highlighted == true){
			canvas.drawRect(sides[0],sides[1],sides[2],sides[3],shade);
		}
	}
}
