package com.example.testsurf;
/**
 * This is a simple creep used for testing purposes
 * 
 * @author Sean
 *
 */
public class CreepSimple extends Creep{
	/**
	 * Constructor for Creep
	 * 
	 * @param xloc the starting location of the creep
	 * @param yloc the starting location of the creep
	 * @param view the gameview that created the creep
	 */
	public CreepSimple(float xloc, float yloc, GameView view) {
		super(xloc, yloc, view);
		health = 100;
		health2 = health;
		speed = 25;
		size = 1;
		xscale = (float) (xsize/800.0 * size);
		yscale = (float) (ysize/480.0 * size);
		creepicon = context.getResources().getDrawable(R.drawable.icon);
		moneyval = 100;
	}

}
