package com.example.testsurf;

import android.content.Context;

public class ZoneNull extends Zone{

	public ZoneNull(int left, int top, int right, int bottom,Context gamecontext) {
		super(left, top, right, bottom, gamecontext);
		ID = 3;
		background = context.getResources().getDrawable(R.drawable.emptyzone);
	}

}
