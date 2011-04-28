/**
 * The main view for the game
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
package com.example.towerd2011;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class GameView extends SurfaceView implements SurfaceHolder.Callback {
	/** stores the current theme type */
	protected int theme;
	/** stores the current level type */
	protected int level;
	/** stores options for the game */
	protected int sound;
	protected int difficulty;
	protected int music;
	/** stores the gamethread that updates the game */
	protected GameThread thread;
	/** the text background */
	protected Paint text_background;
	protected Paint text;
	/** the diffent paths fore each level */
	protected int[][] paths;
	protected int[][] paths_level_1= {{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},{5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5}};
	protected int[][] paths_level_2= {{0,1,2,3,3,3,4,5,5,5,6,7,7,7,7,7,8,9,9,9,9,9,9,9,10,11,12,12,12,12,12,12,12,13,14,15}
	,{5,5,5,5,6,7,7,7,6,5,5,5,4,3,2,2,2,2,3,4,5,6,7,8,8,8,8,7,6,5,4,3,2,2,2,2}};
	protected int[][] paths_level_3= {{0,1,2,3,4,5,6,7,8,9,10,10,10,9,8,7,6,5,4,3,2,1,0,0,0,1,2,3,4,5,6,7,8,9,10,11,12,12,
		12,11,10,9,8,7,6,5,4,3,2,1,1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,14,14,14,14,14,14,13,12,12,12,13,14,15}
	,{9,9,9,9,9,9,9,9,9,9,9,8,7,7,7,7,7,7,7,7,7,7,7,6,5,5,5,5,5,5,5,5,5,5,5,5,5,4,3,3,3,3,3,3,3,3,3,3,3,3,2,1,1,1,1,1,1,
		1,1,1,1,1,1,1,1,2,3,4,5,6,7,7,7,8,9,9,9,9}};
	/** the size of the grid used to store the zones */
	protected int x_grid_size = 16;
	protected int y_grid_Size= 10;
	/** the grid used to store the zones */
	protected Grid tiles;
	/** keeps track if we need to initialize the grid for the first draw */
	private int initiate=0;
	/** stores the current user */
	protected User user;
	/** stores lists for the important objects */
	protected ArrayList<Creep> creeplist = new ArrayList<Creep>();
	protected ArrayList<Tower> towerlist = new ArrayList<Tower>();
	protected ArrayList<ZonePath> pathlist = new ArrayList<ZonePath>();
	protected ArrayList<Bullet> bulletlist = new ArrayList<Bullet>();
	/**
	 * Constructor for the gameview 
	 * 
	 * @param context
	 */
	public GameView(Context context) {
		super(context); 
		getHolder().addCallback(this);
		setFocusable(true);
		/** get the dev preferences */
		SharedPreferences prefsdiff = context.getSharedPreferences("DiffAdjust", Context.MODE_PRIVATE);
		/** create the user with the deve preferences or default values */
		user = new User(prefsdiff.getInt("Money", 500), 0, prefsdiff.getInt("Lives", 10), "Sean");
		/** set the text and text background settings */
		text = new Paint();
		text.setARGB(255, 0, 0, 0);
		text.setTextSize(30);
		text.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		text_background = new Paint();
		text_background.setARGB(100, 0, 0, 0);
		text.setARGB(255, 255, 255, 255);
		/** load the options for the user */
		SharedPreferences prefs = context.getSharedPreferences("Options", Context.MODE_PRIVATE);
		theme = prefs.getInt("theme",1); 
		level = prefs.getInt("level",1); 
		sound = prefs.getInt("Sound",1);
		music = prefs.getInt("Music",1);
		difficulty = prefs.getInt("Difficulty",1);
		if(level == 1)
			paths = paths_level_1;
		else if(level == 2)
			paths = paths_level_2;
		else if(level == 3)
			paths = paths_level_3;
		/** create the new game thread */
		thread = new GameThread(getHolder(), this);
	}
	/**
	 * on a touch event call the gamethread touch event handler
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean touch = false;
		touch = thread.doTouchEvent(event);
		return touch;
	}
	/**
	 * drawn the canvas 
	 */
	@Override
	public void onDraw(Canvas canvas) {
		/** if this is the first drawn initiate certain values */
		if(initiate == 0){
			initilize();
		}
		/**draw the background */
		Drawable background;
		background = getContext().getResources().getDrawable(R.drawable.simplebackground);
		background.setBounds(0, 0, getWidth(), getHeight());
		background.draw(canvas);
		/** draw the other elements */
		drawZones(canvas);
		drawBullets(canvas);
		drawCreeps(canvas);
		drawUserInfo(canvas);
	}
	/**
	 * on the first draw we setup our grid
	 */
	protected void initilize(){
		tiles = new Grid(x_grid_size,y_grid_Size,getWidth(),getHeight(),this);
		int[] sides;
		for(int i=0;i<paths[0].length;++i){
			sides = tiles.getGridZone(paths[0][i], paths[1][i]).getSides();
			ZonePath path = new ZonePath(sides[0],sides[1],sides[2],sides[3],this);
			tiles.setGridZone(paths[0][i], paths[1][i], path);
			pathlist.add(path);
		}
		for(int i = 0; i<pathlist.size();++i){
			if(i == 0){
				pathlist.get(i).setPrev(null);
				pathlist.get(i).setNext(pathlist.get(i+1));
			}
			else if (i==pathlist.size()-1){
				pathlist.get(i).setPrev(pathlist.get(i-1));
				pathlist.get(i).setNext(null);
			}
			else{
				pathlist.get(i).setPrev(pathlist.get(i-1));
				pathlist.get(i).setNext(pathlist.get(i+1));
			}
		}

		for(int i = 0; i<x_grid_size; ++i){
			sides = tiles.getGridZone(i,0).getSides();
			tiles.setGridZone(i, 0, new ZoneNull(sides[0],sides[1],sides[2],sides[3],this));
		}
		sides = tiles.getGridZone(0,1).getSides();
		tiles.setGridZone(0, 1, new ZoneStartButton(sides[0],sides[1],sides[2],sides[3],this));
		sides = tiles.getGridZone(x_grid_size-1,1).getSides();
		tiles.setGridZone(x_grid_size-1, 1, new ZoneCreepButton(sides[0],sides[1],sides[2],sides[3],this));
		sides = tiles.getGridZone(x_grid_size-1,2).getSides();
		tiles.setGridZone(x_grid_size-1, 2, new ZoneMuteButton(sides[0],sides[1],sides[2],sides[3],this));
		initiate = 1;
	}
	/**
	 * Will draw all zones which include Towers, ZoneEmpties, and ZonePaths
	 * @param canvas
	 */
	protected void drawZones(Canvas canvas){
		for(int i = 0;i<x_grid_size;++i){ 
			for(int j=0;j<y_grid_Size;++j){
				tiles.getGridZone(i,j).drawSelf(canvas);
			}
		}

	}
	/**
	 * Will draw all of the creeps and delete them if they are dead
	 * @param canvas
	 */
	protected void drawCreeps(Canvas canvas){
		for(int i =0; i<creeplist.size();++i){
			creeplist.get(i).drawSelf(canvas);
		}
		/** remove dead creeps */
		for(int i = 0;i<creeplist.size();++i){
			if(creeplist.get(i).getAlive()==false){
				creeplist.remove(i);
			}
		}
	}
	/**
	 * Will draw all of the bullets in play and will delete them if they are dead
	 * @param canvas
	 */
	protected void drawBullets(Canvas canvas){
		for(int i=0; i<bulletlist.size();++i){
			bulletlist.get(i).drawSelf(canvas);
		}
		/** remove dead bullets */
		for(int i=0; i<bulletlist.size();++i){
			if(bulletlist.get(i).getAlive()==false){
				bulletlist.remove(i);
			}
		}
	}
	/**
	 * Will draw the user stats at the top of the view
	 * @param canvas
	 */
	protected void drawUserInfo(Canvas canvas){
		String userscore = Integer.toString(user.getScore());
		String money = Integer.toString(user.getMoney());
		String lives = Integer.toString(user.getLives());
		String wave = Integer.toString(thread.getWave());
		canvas.drawRect(0,0,getWidth(),getHeight()/y_grid_Size, text_background);
		canvas.drawText("  Wave = "+wave+" Score = "+userscore+" Money = "+money+" Lives = "+lives,0, getHeight()/y_grid_Size-15,text);
	}
	/**
	 * @see android.view.SurfaceHolder.Callback#surfaceChanged(android.view.SurfaceHolder, int, int, int)
	 */
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
	}
	/**
	 * @see android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder)
	 */
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();
	}
	/**
	 * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.SurfaceHolder)
	 */
	public void surfaceDestroyed(SurfaceHolder holder) {
		// simply copied from sample application LunarLander:
		// we have to tell thread to shut down & wait for it to finish, or else
		// it might touch the Surface after we return and explode
		/** stop the mussic */
		if(thread.mp.isPlaying()) {
			thread.mp.stop();
		}
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// we will try it again and again...
			}
		}
	}
	/**
	 * return game thread
	 * @return
	 */
	public GameThread getThread(){
		return thread;
	}
	/**
	 * return the grid
	 * @return
	 */
	public Grid getGrid(){
		return tiles;
	}
	/**
	 * return the grid sizes 
	 * @return
	 */
	public int[] getGridSize(){
		int[] size = {x_grid_size,y_grid_Size};
		return size;
	}
	/** 
	 * return the user
	 * @return
	 */
	public User getUser(){
		return user;
	}
	/**
	 * return the tower list
	 * @return
	 */
	public ArrayList<Tower> getTowerlist(){
		return towerlist;
	}
	/**
	 * return the bullet list
	 * @return
	 */
	public ArrayList<Bullet> getBulletlist(){
		return bulletlist;
	}
	/** 
	 * return the creep list
	 * @return
	 */
	public ArrayList<Creep> getCreeplist(){
		return creeplist;
	}
	/**
	 * return the path list
	 * @return
	 */
	public ArrayList<ZonePath> getPathlist(){
		return pathlist;
	}
	/**
	 * return the theme
	 * @return
	 */
	public int getTheme(){
		return theme;
	}
	/**
	 * return the sound
	 * @return
	 */
	public int getSound(){
		return sound;
	}
	/**
	 * return the music
	 * @return
	 */
	public int getMusic(){
		return music;
	}
	/**
	 * return the difficulty
	 * @return
	 */
	public int getDifficulty(){
		return difficulty;
	}
	/** 
	 * set the sound
	 * @param value
	 */
	public void setSound(int value){
		sound = value;
	}
	/**
	 * set the music varriable
	 * @param value
	 */
	public void setMusic(int value){
		music = value;
	}
}

