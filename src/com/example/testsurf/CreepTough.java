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
	public CreepTough(float xloc, float yloc, GameView view,double difficulty) {
		super(xloc, yloc, view,difficulty);
		health = (int) (125*difficulty);
		health2 = health;
		original_health = health;
		speed = 20;
		size = 2;
		xscale = (float) (xsize/800.0 * size);
		yscale = (float) (ysize/480.0 * size);
		creepicon = context.getResources().getDrawable(R.drawable.creep_box);
		moneyval = (int) (.25*health);
		type = "Simple";
	}

}
