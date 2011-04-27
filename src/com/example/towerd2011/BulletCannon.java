/**
 * This is a cannon bullet used as the default bullet
 * 
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
package com.example.towerd2011;

public class BulletCannon extends Bullet{

	public BulletCannon(int x, int y, Creep creep_target, GameView view, int dmg) {
		super(x, y, creep_target, view, dmg);
		speed = 200;
		size = 4;
		bullet = context.getResources().getDrawable(R.drawable.bulletcannon);
		xscale = (float) (xsize/800.0*size);
		yscale = (float) (ysize/480.0*size);
	}

}
