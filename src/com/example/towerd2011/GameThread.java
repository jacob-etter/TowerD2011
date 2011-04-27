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
import android.view.MotionEvent;
import android.view.SurfaceHolder;


class GameThread extends Thread {
	protected SurfaceHolder _surfaceHolder;
	protected GameView _view;
	protected boolean _run = false;
	protected boolean paused = true;
	protected boolean in_round = false;
	protected int xpress;
	protected int ypress;
	protected long click_time;
	protected long creep_timer;
	protected long pause_time;
	protected int spawn_count;
	protected int old_spawn_count;
	protected long spawn_timer;
	protected int wave;
	protected double difficulty;
	protected boolean gameover = false;
	/**
	 * Constructor for GameThread
	 * 
	 * @param surfaceHolder
	 * @param panel
	 */
	public GameThread(SurfaceHolder surfaceHolder, GameView panel) {
		_surfaceHolder = surfaceHolder;
		_view = panel;
		int dif = _view.getDifficulty();
		switch(dif){
		case 1:difficulty = 1;break;
		case 2:difficulty = 1.5;break;
		case 3:difficulty = 2;break;
		}
		SharedPreferences prefs = _view.getContext().getSharedPreferences("DiffAdjust", Context.MODE_PRIVATE);
		spawn_timer = prefs.getInt("SpawnTimer", 3000);
		old_spawn_count = prefs.getInt("SpawnCount", 5);
		difficulty += prefs.getInt("DifficultyOffset", 0);
		wave = 0;
		creep_timer = 0;
		spawn_count = 0;
	}
	/**
	 * Start the game
	 * @param run
	 */
	public void setRunning(boolean run) {
		_run = run;
		paused = false;
	}
	/**
	 * Get the surfaceholder
	 * @return
	 */
	public SurfaceHolder getSurfaceHolder() {
		return _surfaceHolder;
	}
	/**
	 * Update the state of the game
	 */
	public void updateGame(){
		long current_time = System.currentTimeMillis();
		if((spawn_count < 1)&&(_view.getCreeplist().size()<1)){
			in_round = false;
		}
		for(int i =0; i<_view.getCreeplist().size();++i){
			_view.getCreeplist().get(i).move(current_time);
		}
		for(int i=0;i<_view.getBulletlist().size();++i){
			_view.getBulletlist().get(i).move(current_time);
		}
		for(int i=0;i<_view.towerlist.size();++i){
			_view.getTowerlist().get(i).fire(_view);
		}
		spawnCreeps(current_time);
		if(_view.getUser().getLives() <= 0){
			_run = false;
			SharedPreferences prefs = _view.getContext().getSharedPreferences("HighScores", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putInt("Score", _view.getUser().getScore());
			editor.putInt("RoundsCompleted", wave);
			editor.commit();
			Intent myIntent = new Intent(_view.getContext(), ScreenGameOver.class);
			_view.getContext().startActivity(myIntent);
		}
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
			xpress = xpress/(_view.getWidth()/_view.xsize);
			ypress = ypress/(_view.getHeight()/_view.ysize);
			_view.tiles.getGridZone(xpress,ypress).setHighlight();
			click_time = System.currentTimeMillis();
			handled = true;
		} 
		else if (event.getAction() == MotionEvent.ACTION_MOVE){
			old_xpress = xpress;
			old_ypress = ypress;
			_view.tiles.getGridZone(xpress,ypress).removeHighlight();
			ypress  = (int) event.getY();
			xpress = (int) event.getX();
			xpress = xpress/(_view.getWidth()/_view.xsize);
			ypress = ypress/(_view.getHeight()/_view.ysize);
			_view.tiles.getGridZone(xpress,ypress).setHighlight();
			if((old_xpress != xpress)&&(old_ypress!=ypress)){
				click_time = System.currentTimeMillis();
			}
			handled = true;
		}
		else if (event.getAction() == MotionEvent.ACTION_UP){
			_view.tiles.getGridZone(xpress,ypress).removeHighlight();
			ypress  = (int) event.getY();
			xpress = (int) event.getX();
			xpress = xpress/(_view.getWidth()/_view.xsize);
			ypress = ypress/(_view.getHeight()/_view.ysize);
			if((System.currentTimeMillis()-click_time )> 250){
				if(_view.tiles.getGridZone(xpress,ypress).getID()==2){
					gamePause();
					DialogBuyTower customDialog = new DialogBuyTower(_view,xpress, ypress);
					customDialog.show();
				}
				else if(_view.tiles.getGridZone(xpress,ypress).getID()>4){
					gamePause();
					DialogSellTower dialogselltower = new DialogSellTower(_view,xpress, ypress);
					dialogselltower.show();
				}
				else if((_view.tiles.getGridZone(xpress,ypress).getID()==0)&&(in_round == false)){
					startround();
				}
				else if(_view.tiles.getGridZone(xpress,ypress).getID()==4){
					gamePause();
					DialogCreepList dialogcreeplist = new DialogCreepList(_view,xpress, ypress);
					dialogcreeplist.show();
				}
			}
			if(_view.tiles.getGridZone(xpress,ypress).getID()==1){
				gamePause();
				DialogCreepZone dialogcreeps = new DialogCreepZone(_view,xpress, ypress);
				dialogcreeps.show();
			}
			handled = true;
		}
		return handled;
		//}
	}
	/**
	 * Main run program
	 */
	@Override
	public void run() {
		Canvas c;
		while (_run) {
			c = null;
			try {
				c = _surfaceHolder.lockCanvas(null);
				synchronized (_surfaceHolder) {
					if(paused==false){
						updateGame();
					}
					_view.onDraw(c);
				}
			} finally {
				// do this in a finally so that if an exception is thrown
				// during the above, we don't leave the Surface in an
				// inconsistent state
				if (c != null) {
					_surfaceHolder.unlockCanvasAndPost(c);
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
		spawn_count = old_spawn_count;
		old_spawn_count = old_spawn_count+2;
		spawn_timer = (long) (spawn_timer*0.95);
		difficulty = difficulty*1.1;
		wave += 1;
	}
	protected void spawnCreeps(long current_time){
		if((current_time - creep_timer)>spawn_timer){
			if((_view.getPathlist().size()>0)&&(spawn_count > 0)){
				int x = _view.getPathlist().get(0).getSides()[0];
				int y = (_view.getPathlist().get(0).getSides()[1]+_view.getPathlist().get(0).getSides()[3])/2;
				int round = wave % 4;
				switch(round){
				case 0: roundZero(x,y); break;
				case 1: roundOne(x,y); break;
				case 2: roundTwo(x,y); break;
				case 3: roundThree(x,y); break;
				}
				creep_timer = current_time; 
				spawn_count -= 1;
			}
		}
	}
	protected void roundZero(int x, int y){
		_view.getCreeplist().add(new CreepSimple(x, y,_view, difficulty));
	}
	protected void roundOne(int x, int y){
		_view.getCreeplist().add(new CreepQuick(x, y,_view,difficulty));
	}
	protected void roundTwo(int x, int y){
		_view.getCreeplist().add(new CreepTough(x, y,_view,difficulty));
	}
	protected void roundThree(int x, int y){
		switch(spawn_count%3){
		case 0: _view.getCreeplist().add(new CreepSimple(x, y,_view,difficulty)); break;
		case 1: _view.getCreeplist().add(new CreepTough(x, y,_view,difficulty)); break;
		case 2: _view.getCreeplist().add(new CreepQuick(x, y,_view,difficulty)); break;
		}
	}
	public void gamePause(){
		if(paused == false){
			paused = true;
			pause_time = System.currentTimeMillis();
		}
	}
	public void gameResume(){
		if(paused == true){
			long diff = System.currentTimeMillis() - pause_time;
			for(int i=0;i<_view.getCreeplist().size();++i){
				Creep creep = _view.getCreeplist().get(i);
				creep.incOldTime(diff);
			}
			for(int i=0;i<_view.getTowerlist().size();++i){
				Tower tower = _view.getTowerlist().get(i);
				tower.incLastFire(diff);
			}
			for(int i=0;i<_view.getBulletlist().size();++i){
				Bullet bullet = _view.getBulletlist().get(i);
				bullet.incOldTime(diff);
			}
			creep_timer += diff;
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
