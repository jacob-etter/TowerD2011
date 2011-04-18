package com.example.testsurf;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

/**
 * The game thread that will update the state of objects and draw them
 * @author Sean
 *
 */
class GameThread extends Thread {
    private SurfaceHolder _surfaceHolder;
    private GameView _view;
    private boolean _run = false;
    private boolean paused = false;
    private int xpress;
    private int ypress;
    public long click_time;
    private long creep_timer=0;
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
    	for(int i =0; i<_view.getCreeplist().size();++i){
    		_view.getCreeplist().get(i).move(current_time);
    	}
    	for(int i=0;i<_view.getBulletlist().size();++i){
    		_view.getBulletlist().get(i).move(current_time);
    	}
    	for(int i=0;i<_view.towerlist.size();++i){
    		_view.getTowerlist().get(i).fire(_view);
    	}
    	if((_view.getUser().getScore()<25)&&(current_time - creep_timer)>5000){
    		if(_view.getPathlist().size()>0){
	    		int x = _view.getPathlist().get(0).getSides()[0];
	    		int y = (_view.getPathlist().get(0).getSides()[1]+_view.getPathlist().get(0).getSides()[3])/2;
				_view.getCreeplist().add(new CreepSimple(x, y,_view));
				creep_timer = current_time; 
    		}
    	}
    	else if((_view.getUser().getScore()>=25)&&(current_time - creep_timer)>1000){
    		if(_view.getPathlist().size()>0){
	    		int x = _view.getPathlist().get(0).getSides()[0];
	    		int y = (_view.getPathlist().get(0).getSides()[1]+_view.getPathlist().get(0).getSides()[3])/2;
				_view.getCreeplist().add(new CreepSimple(x, y,_view));
				creep_timer = current_time; 
    		}
    	}
    }
    /**
     * Execute a touch event
     * @param event
     * @return
     */
    public boolean doTouchEvent(MotionEvent event) {
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
    			}
    		}
    		return true;
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
}
