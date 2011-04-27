package com.example.testsurf;
/**
 * This is a simple creep used for testing purposes
 * 
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
public class CreepSimple extends Creep{
	/**
	 * Constructor for Creep
	 * 
	 * @param xloc the starting location of the creep
	 * @param yloc the starting location of the creep
	 * @param view the gameview that created the creep
	 */
	public CreepSimple(float xloc, float yloc, GameView view,double difficulty) {
		super(xloc, yloc, view, difficulty);
		health = (int) (75*difficulty);
		health2 = health;
		original_health = health;
		speed = 30;
		size = 2;
		xscale = (float) (xsize/800.0 * size);
		yscale = (float) (ysize/480.0 * size);
		creepicon = context.getResources().getDrawable(R.drawable.creep_ghost);
		moneyval = (int) (.25*health);
		type = "Simple";
	}

}
