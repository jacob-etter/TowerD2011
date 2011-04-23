/**
 * 
 */
package com.example.testsurf;

/**
 * @author Cody
 *
 */
public class CreepQuick extends Creep {

	/**
	 * @param xloc
	 * @param yloc
	 * @param view
	 */
	public CreepQuick(float xloc, float yloc, GameView view) {
		super(xloc, yloc, view);
		health = 85;
		health2 = health;
		speed = 35;
		size = 1.8;
		xscale = (float) (xsize/800.0 * size);
		yscale = (float) (ysize/480.0 * size);
		creepicon = context.getResources().getDrawable(R.drawable.creep_eye);
		moneyval = 130;
	}

}
