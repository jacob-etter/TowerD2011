package com.example.testsurf;

public class BulletTesla extends Bullet{

	public BulletTesla(int x, int y, Creep creep_target, GameView view, int dmg) {
		super(x, y, creep_target, view, dmg);
		speed = 200;
		size = 8;
		bullet = context.getResources().getDrawable(R.drawable.bullettesla);
		xscale = (float) (xsize/800.0*size);
		yscale = (float) (ysize/480.0*size);
	}

}
