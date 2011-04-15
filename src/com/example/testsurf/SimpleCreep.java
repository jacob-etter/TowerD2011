package com.example.testsurf;

import android.content.Context;

public class SimpleCreep extends Creep{
	public SimpleCreep(float xloc, float yloc, User gameuser,Context gamecontext) {
		super(xloc, yloc, gameuser, gamecontext);
		health = 100;
		speed = 1;
		size = 1;
		creepicon = context.getResources().getDrawable(R.drawable.icon);
	}

}
