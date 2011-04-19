package com.example.testsurf;

import android.content.Context;

public class ZoneStartButton extends Zone{

	public ZoneStartButton(int left, int top, int right, int bottom, Context gamecontext) {
		super(left, top, right, bottom, gamecontext);
		ID = 0;
		background = context.getResources().getDrawable(R.drawable.startbutton);
	}

}
