/**
 * 
 */
package com.example.testsurf;

/**
 * @author Cody
 *
 */
public class CreepTough extends Creep {

	/**
	 * @param xloc
	 * @param yloc
	 * @param view
	 */
	public CreepTough(float xloc, float yloc, GameView view) {
		super(xloc, yloc, view);
		health = 250;
		health2 = health;
		speed = 15;
		size = 2;
		xscale = (float) (xsize/800.0 * size);
		yscale = (float) (ysize/480.0 * size);
		creepicon = context.getResources().getDrawable(R.drawable.creep_box);
		moneyval = 175;
	}

}