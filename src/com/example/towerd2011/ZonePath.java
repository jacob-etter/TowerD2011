/**
 * A zone that creeps follow and cannot be built on
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
package com.example.towerd2011;

public class ZonePath extends Zone{
	/** the path before this one */
	protected ZonePath previous;
	/** the next path in the list */
	protected ZonePath next;
	/** the location of the connection to the next path
	 * this is used by creeps for pathing
	 */
	protected double d_next[] = {-1,-1};
	/**
	 * Custructor for zone path
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @param gameview
	 */
	public ZonePath(int left, int top, int right, int bottom, GameView gameview) {
		super(left, top, right, bottom, gameview);
		zone_id = 1;
		if(theme == 1){
			background = context.getResources().getDrawable(R.drawable.dirt);
		}
		else if(theme == 2){
			background = context.getResources().getDrawable(R.drawable.pathzone);
		}
	}
	/**
	 * 
	 * @param path
	 */
	public void setPrev(ZonePath path){
		previous = path;
	}
	/**
	 * 
	 * @param path
	 */
	public void setNext(ZonePath path){
		next = path;
	}
	/**
	 * 
	 * @return
	 */
	public ZonePath getPrev(){
		return previous;
	}
	/**
	 * 
	 * @return
	 */
	public ZonePath getNext(){
		return next;
	}
	/**
	 * Finds the location of the connection to the next path to set d_next
	 * @return
	 */
	public double[] getDirecNext(){
		/** if we already calulated d_next return that value */
		if(d_next[0] != -1)
			return d_next;
		/** if there is no text set the d_next to the right side middle */
		if(next == null){
			d_next[0] = sides[2];
			d_next[1] = (sides[1]+sides[3])/2;
		}
		/** right side middle */
		else if(sides[2] == next.getSides()[0]){
			d_next[0] = sides[2];
			d_next[1] = (sides[1]+sides[3])/2;
		}
		/** top middle */
		else if(sides[1] == next.getSides()[3]){
			d_next[0] = (sides[0]+sides[2])/2;
			d_next[1] = sides[1];
		}
		/** left middle */
		else if(sides[0] == next.getSides()[2]){
			d_next[0] = sides[0];
			d_next[1] = (sides[1]+sides[3])/2;
		}
		/** bottom middle */
		else if(sides[3] == next.getSides()[1]){
			d_next[0] = (sides[0]+sides[2])/2;
			d_next[1] = sides[3];
		}
		return d_next;
	}
}
