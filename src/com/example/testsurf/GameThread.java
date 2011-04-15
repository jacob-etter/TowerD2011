package com.example.testsurf;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

class GameThread extends Thread {
    private SurfaceHolder _surfaceHolder;
    private GameView _view;
    private boolean _run = false;
    private boolean paused = false;

    public GameThread(SurfaceHolder surfaceHolder, GameView panel) {
        _surfaceHolder = surfaceHolder;
        _view = panel;
    }

    public void setRunning(boolean run) {
        _run = run;
    }

    public SurfaceHolder getSurfaceHolder() {
        return _surfaceHolder;
    }
    public void updateGame(){
    	long current_time = System.currentTimeMillis();
    	ArrayList<Creep> creeps = _view.creeplist;
    	for(int i =0; i<creeps.size();++i){
    		_view.creeplist.get(i).move(current_time);
    	}
    }

    @Override
    public void run() {
        Canvas c;
        while (_run) {
            c = null;
            try {
                c = _surfaceHolder.lockCanvas(null);
                synchronized (_surfaceHolder) {
                	if(paused==false){
//                		updateGame();
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
