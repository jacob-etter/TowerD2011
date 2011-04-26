package com.example.testsurf;

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
