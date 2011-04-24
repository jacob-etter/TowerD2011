package com.example.testsurf;

import android.content.Intent;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

/**
 * The game thread that will update the state of objects and draw them
 * @author Sean
 *
 */
class GameThread extends Thread {
	protected SurfaceHolder _surfaceHolder;
	protected GameView _view;
	protected boolean _run = false;
	protected boolean paused = true;
	protected boolean in_round = false;
	protected int xpress;
	protected int ypress;
	protected long click_time;
	protected long creep_timer=0;
	protected int spawn_count = 0;
	protected int old_spawn_count = 5;
	protected long spawn_timer = 10000;
	protected int wave = 0;
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
		if((current_time - creep_timer)>spawn_timer){
			if((_view.getPathlist().size()>0)&&(spawn_count > 0)){
				int x = _view.getPathlist().get(0).getSides()[0];
				int y = (_view.getPathlist().get(0).getSides()[1]+_view.getPathlist().get(0).getSides()[3])/2;
				_view.getCreeplist().add(new CreepSimple(x, y,_view));
				creep_timer = current_time; 
				spawn_count -= 1;
			}
		}
		if(_view.getUser().getLives() <= 0){
			_run = false;
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
		synchronized (_surfaceHolder) {
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
				if((System.currentTimeMillis()-click_time )> 250){
					ypress  = (int) event.getY();
					xpress = (int) event.getX();
					xpress = xpress/(_view.getWidth()/_view.xsize);
					ypress = ypress/(_view.getHeight()/_view.ysize);
					if(_view.tiles.getGridZone(xpress,ypress).getID()==2){
						DialogBuyTower customDialog = new DialogBuyTower(_view,xpress, ypress);
						customDialog.show();
					}
					else if(_view.tiles.getGridZone(xpress,ypress).getID()>2){
						DialogSellTower dialogselltower = new DialogSellTower(_view,xpress, ypress);
						dialogselltower.show();
					}
					else if((_view.tiles.getGridZone(xpress,ypress).getID()==0)&&(in_round == false)){
						startround();
					}
				}
				handled = true;
			}
			return handled;
		}
	}
	/**
	 * run the game
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
	protected void startround(){
		in_round = true;
		spawn_count = old_spawn_count;
		old_spawn_count = 2*old_spawn_count;
		spawn_timer = spawn_timer/2;
		wave += 1;
	}
	public int getWave(){
		return wave;
	}
}
