/**
 * The game thread that will update the state of objects and draw them
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
package com.example.towerd2011;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;


class GameThread extends Thread {
	/** this is the surface holder for this thread */
	protected SurfaceHolder surfaceHolder;
	/** the game view that created this thread */
	protected GameView view;
	/** a boolean to tell if the game is running */
	protected boolean game_run = false;
	/** a boolean to tell if update game is paused */
	protected boolean paused = true;
	/** a boolean to tell if a round is in progress */
	protected boolean in_round = false;
	/** the location of a user's touch */
	protected int xpress;
	protected int ypress;
	/** the amount of time a user has clicked on the same zone */
	protected long click_time;
	/** the time that a creep last spawned */
	protected long last_creep_spawn;
	/** the time between creep spawns */
	protected long spawn_timer;
	/** how long the game has been paused */
	protected long pause_time;
	/** the amount of creeps to spawn for this round */
	protected int spawn_count;
	/** the amount of creeps to spawn for a round but this value 
	 * is not decremented after each spawn like spawn count is
	 */
	protected int old_spawn_count;
	/** the current wave that the user is on */
	protected int wave;
	/** the current difficulty multiplier */
	protected double difficulty;
	/** to tell if the game is over */
	protected boolean gameover = false;
	/** the creep factory used for spawning creeps */
	protected CreepFactory factory;
	/** our media player */
	protected MediaPlayer mp;
	/**
	 * Constructor for GameThread
	 * 
	 * @param surfaceHolder
	 * @param gameview
	 */
	public GameThread(SurfaceHolder surfaceHolder, GameView gameview) {
		/** save the surface holder and gamve view*/
		this.surfaceHolder = surfaceHolder;
		view = gameview;
		/** get the game difficulty setting easy, medium, hard*/
		int dif = view.getDifficulty();
		switch(dif){
		case 1:difficulty = 1;break;
		case 2:difficulty = 1.5;break;
		case 3:difficulty = 2;break;
		}
		/** load the default settings for some variables */
		SharedPreferences prefs = view.getContext().getSharedPreferences("DiffAdjust", Context.MODE_PRIVATE);
		spawn_timer = prefs.getInt("SpawnTimer", 3000);
		old_spawn_count = prefs.getInt("SpawnCount", 5);
		difficulty += prefs.getInt("DifficultyOffset", 0);
		wave = 0;
		last_creep_spawn = 0;
		spawn_count = 0;
		factory = new CreepFactory(view);
		int music = gameview.getMusic();
		mp = MediaPlayer.create(gameview.getContext(), R.raw.music);
		mp.setLooping(true);
		mp.setVolume((float)0.3, (float)0.3);
		/** start playing music */
		if(music == 1){
			mp.start();
		}
	}
	/**
	 * Start the game
	 * @param run
	 */
	public void setRunning(boolean run) {
		game_run = run;
		paused = false;
	}
	/**
	 * Get the surfaceholder
	 * @return
	 */
	public SurfaceHolder getSurfaceHolder() {
		return surfaceHolder;
	}
	/**
	 * Update the state of the game
	 */
	public void updateGame(){
		long current_time = System.currentTimeMillis();
		/** end the round if there are no more creeps to spawn and all current
		 * creeps are dead
		 */
		if((spawn_count < 1)&&(view.getCreeplist().size()<1)){
			in_round = false;
		}
		/** call the move function for all creeps */
		for(int i =0; i<view.getCreeplist().size();++i){
			view.getCreeplist().get(i).move(current_time);
		}
		/** call the move function for all bullets */
		for(int i=0;i<view.getBulletlist().size();++i){
			view.getBulletlist().get(i).move(current_time);
		}
		/** call the fire function for all towers */
		for(int i=0;i<view.towerlist.size();++i){
			view.getTowerlist().get(i).fire(view);
		}
		/** call the spawn creeps function */
		spawnCreeps(current_time);
		/** check to see if the game is over */
		if(view.getUser().getLives() <= 0){
			gameOver();
		}
	}
	/**
	 * if the game is over go to the scores screen
	 */
	public void gameOver(){
		/** end the thread loop */
		game_run = false;
		/** put the score and wave info into a sharedpreferences */
		SharedPreferences prefs = view.getContext().getSharedPreferences("HighScores", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("Score", view.getUser().getScore());
		editor.putInt("RoundsCompleted", wave);
		editor.commit();
		Intent myIntent = new Intent(view.getContext(), ScreenGameOver.class);
		view.getContext().startActivity(myIntent);
	}
	/**
	 * Execute a touch event
	 * @param event
	 * @return
	 */
	public boolean doTouchEvent(MotionEvent event) {
		boolean handled = false;
		int old_xpress;
		int old_ypress;
		//synchronized (_surfaceHolder) {
		if (event.getAction() == MotionEvent.ACTION_DOWN){
			ypress  = (int) event.getY();
			xpress = (int) event.getX();
			xpress = xpress/(view.getWidth()/view.x_grid_size);
			ypress = ypress/(view.getHeight()/view.y_grid_Size);
			/** set the current clicked zone to highlighted */
			view.tiles.getGridZone(xpress,ypress).setHighlight();
			click_time = System.currentTimeMillis();
			handled = true;
		} 
		else if (event.getAction() == MotionEvent.ACTION_MOVE){
			old_xpress = xpress;
			old_ypress = ypress;
			view.tiles.getGridZone(xpress,ypress).removeHighlight();
			ypress  = (int) event.getY();
			xpress = (int) event.getX();
			xpress = xpress/(view.getWidth()/view.x_grid_size);
			ypress = ypress/(view.getHeight()/view.y_grid_Size);
			view.tiles.getGridZone(xpress,ypress).setHighlight();
			if((old_xpress != xpress)&&(old_ypress!=ypress)){
				click_time = System.currentTimeMillis();
			}
			handled = true;
		}
		else if (event.getAction() == MotionEvent.ACTION_UP){
			view.tiles.getGridZone(xpress,ypress).removeHighlight();
			ypress  = (int) event.getY();
			xpress = (int) event.getX();
			xpress = xpress/(view.getWidth()/view.x_grid_size);
			ypress = ypress/(view.getHeight()/view.y_grid_Size);
			/** make sure the user has selected the same zone for at least 1/4th a second*/
			if((System.currentTimeMillis()-click_time )> 250){
				/** launch a buy tower dialog */
				if(view.tiles.getGridZone(xpress,ypress).getID()==2){
					gamePause();
					DialogBuyTower dialogbuytower = new DialogBuyTower(view,xpress, ypress);
					dialogbuytower.show();
				}
				/** launch a dialog sell tower */
				else if(view.tiles.getGridZone(xpress,ypress).getID()>5){
					gamePause();
					DialogSellTower dialogselltower = new DialogSellTower(view,xpress, ypress);
					dialogselltower.show();
				}
				/** star the round */
				else if((view.tiles.getGridZone(xpress,ypress).getID()==0)&&(in_round == false)){
					startround();
				}
				/** launch a creeplist dialog */
				else if(view.tiles.getGridZone(xpress,ypress).getID()==4){
					gamePause();
					DialogCreepList dialogcreeplist = new DialogCreepList(view,xpress, ypress);
					dialogcreeplist.show();
				}
				/** toggle the music settings */
				else if(view.tiles.getGridZone(xpress,ypress).getID()==5){
					SharedPreferences prefs = view.getContext().getSharedPreferences("Options", Context.MODE_PRIVATE);
					int music = prefs.getInt("Music",0);
					int sound = prefs.getInt("Sound", 0);
					music = 1-music;
					sound = 1-sound;
					SharedPreferences.Editor editor = prefs.edit();
					editor.putInt("Music", music);
					editor.putInt("Sound", sound);
					editor.commit();
					view.setSound(sound);
					view.setMusic(music);
					for(int i=0;i<view.getTowerlist().size();++i){
						Tower tower = view.getTowerlist().get(i);
						tower.setSound(sound);
					}
					if(music == 0){
						mp.pause();
					}
					else if(music == 1){
						mp.seekTo(0);
						mp.start();
					}
				}
			}
			/** show creeps in current zone */
			if(view.tiles.getGridZone(xpress,ypress).getID()==1){
				gamePause();
				DialogCreepZone dialogcreeps = new DialogCreepZone(view,xpress, ypress);
				dialogcreeps.show();
			}
			handled = true;
		}
		return handled;
	}
	/**
	 * Main run program
	 */
	@Override
	public void run() {
		Canvas c;
		/** loop while the game is set to game_run = true */
		while (game_run) {
			c = null;
			try {
				c = surfaceHolder.lockCanvas(null);
				synchronized (surfaceHolder) {
					/** if not paused update the game */
					if(paused==false){
						updateGame();
					}
					/** update the canvas */
					view.onDraw(c);
				}
			} finally {
				// do this in a finally so that if an exception is thrown
				// during the above, we don't leave the Surface in an
				// inconsistent state
				if (c != null) {
					surfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
	}
	/**
	 * This function is responsible for starting each 
	 * round after the user pressed the start round button
	 */
	protected void startround(){
		in_round = true;
		/** set the spawn count */
		spawn_count = old_spawn_count;
		/** increment the spawn count for next round */
		old_spawn_count = old_spawn_count+2;
		/** set the spawn timer and decrement it */
		spawn_timer = (long) (spawn_timer*0.95);
		difficulty = difficulty*1.1;
		/** increase the wave count */
		wave += 1;
	}
	/**
	 * spawns the creeps 
	 * @param current_time
	 */
	protected void spawnCreeps(long current_time){
		/** check if its time to spawn a wave of creeps */
		if((current_time - last_creep_spawn)>spawn_timer){
			/** make sure that there are more creeps to spawn */
			if((view.getPathlist().size()>0)&&(spawn_count > 0)){
				/** get the position to spawn the creep */
				int x = view.getPathlist().get(0).getSides()[0];
				int y = (view.getPathlist().get(0).getSides()[1]+view.getPathlist().get(0).getSides()[3])/2;
				/** mod the round counter to see which type of creep to spawn */
				int round = wave % 4;
				switch(round){
				/** spawn a simple creep*/
				case 1:factory.addCreep(0, (float)x, (float)y, difficulty);break;
				/** spawn an tough creep */
				case 2:factory.addCreep(1, (float)x, (float)y, difficulty);break;
				/** spawn a fast creep */
				case 3:factory.addCreep(2, (float)x, (float)y, difficulty);break;
				/** spawn one of the three different creeps depending on the spawn count */
				case 0:
					switch(spawn_count%3){
					case 0:factory.addCreep(0, (float)x, (float)y, difficulty);break;
					case 1:factory.addCreep(1, (float)x, (float)y, difficulty);break;
					case 2:factory.addCreep(2, (float)x, (float)y, difficulty);break;
					}break;
				}
				last_creep_spawn = current_time; 
				spawn_count -= 1;
			}
		}
	}
	/**
	 * pause the game 
	 */
	public void gamePause(){
		if(paused == false){
			paused = true;
			pause_time = System.currentTimeMillis();
		}
	}
	/** 
	 * resume the game 
	 */
	public void gameResume(){
		if(paused == true){
			/** update the timers of the creeps, bullets, towers, and creep_spawn */
			long diff = System.currentTimeMillis() - pause_time;
			for(int i=0;i<view.getCreeplist().size();++i){
				Creep creep = view.getCreeplist().get(i);
				creep.incOldTime(diff);
			}
			for(int i=0;i<view.getTowerlist().size();++i){
				Tower tower = view.getTowerlist().get(i);
				tower.incLastFire(diff);
			}
			for(int i=0;i<view.getBulletlist().size();++i){
				Bullet bullet = view.getBulletlist().get(i);
				bullet.incOldTime(diff);
			}
			last_creep_spawn += diff;
			paused = false;
		}
	}
	/**
	 * return the current wave that game is on
	 * @return
	 */
	public int getWave(){
		return wave;
	}
}
