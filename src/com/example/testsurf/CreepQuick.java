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
	public CreepQuick(float xloc, float yloc, GameView view,double difficulty) {
		super(xloc, yloc, view, difficulty);
		health = (int) (50*difficulty);
		health2 = health;
		speed = 45;
		size = 1.8;
		xscale = (float) (xsize/800.0 * size);
		yscale = (float) (ysize/480.0 * size);
		creepicon = context.getResources().getDrawable(R.drawable.creep_eye);
		moneyval = (int) (.25*health);
	}

}
