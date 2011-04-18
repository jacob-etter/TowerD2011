package com.example.testsurf;


public class BulletSimple extends Bullet{

	public BulletSimple(int x, int y, Creep creep_target, GameView view, int dmg) {
		super(x, y, creep_target, view, dmg);
		speed = 100;
		size = 5;
		bullet = context.getResources().getDrawable(R.drawable.red2);
		xscale = (float) (xsize/800.0*size);
		yscale = (float) (ysize/480.0*size);
	}

}
