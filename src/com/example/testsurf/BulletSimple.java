package com.example.testsurf;

/**
 * This is a simple bullet used for testing purposes
 * 
 * @author Sean
 *
 */
public class BulletSimple extends Bullet{
	/**
	 * Constructor for Bullet
	 * 
	 * @param x the starting location of the bullet
	 * @param y the starting location of the bullet
	 * @param creep_target the target for the bullet
	 * @param view the gamview for the bullet
	 * @param dmg the damage to be dealt to the target
	 */
	public BulletSimple(int x, int y, Creep creep_target, GameView view, int dmg) {
		super(x, y, creep_target, view, dmg);
		speed = 200;
		size = 2;
		bullet = context.getResources().getDrawable(R.drawable.green2);
		xscale = (float) (xsize/800.0*size);
		yscale = (float) (ysize/480.0*size);
	}

}
