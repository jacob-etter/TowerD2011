/**
 * Generates the grid used to store towers, Empty Zones, and Path Zones
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
package com.example.towerd2011;

public class Grid {
	private Zone[][] grid;
	public Grid(int xnum,int ynum,int xsize, int ysize,GameView gameview){
		/** loop and generate empty zones for the size of the grid */
		grid = new Zone[xnum][ynum];
		for(double i = 0; i < xnum;++i){
			for(double j = 0; j < ynum; ++j){
				int left = (int)((i*xsize)/xnum);
				if(left < 0)
					left = 0;
				int top = (int)(((j)*ysize)/ynum);
				if (top > ysize)
					top = ysize;
				int right = (int) ((i+1)*xsize/xnum);
				if(right > xsize)
					right = xsize;
				int bottom = (int) (((j+1)*ysize)/ynum);
				if(bottom < 0)
					bottom = 0;
				grid[(int)i][(int)j] = new ZoneEmpty(left,top,right,bottom,gameview);
			}
		}
		return;
	}
	/**
	 * set the grid zone
	 * @param x
	 * @param y
	 * @return
	 */
	public Zone getGridZone(int x, int y){
		return grid[x][y];
	}
	/**
	 * get the grid zone
	 * @param x
	 * @param y
	 * @param object
	 */
	public void setGridZone(int x, int y, Zone object){
		grid[x][y] = object;
	}

}
