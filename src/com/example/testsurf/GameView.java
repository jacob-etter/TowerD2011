package com.example.testsurf;


import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class GameView extends SurfaceView implements SurfaceHolder.Callback {
	private GameThread _thread;
	private Paint text;
//    private int xpress;
//    private int ypress;
    public int xsize = 16;//size of grid in x
    public int ysize= 10;//size of grid in y
    public Grid tiles; //grid of zones
    private int initiate=0;
//	private long click_time;
	public User user;
	public ArrayList<Creep> creeplist = new ArrayList<Creep>();
	public ArrayList<Tower> towerlist = new ArrayList<Tower>();
	public ArrayList<ZonePath> pathlist = new ArrayList<ZonePath>();

    public GameView(Context context) {
        super(context); 
        getHolder().addCallback(this);
        _thread = new GameThread(getHolder(), this);
        setFocusable(true);
		user = new User(5000, 0, 10, "Sean");
		text = new Paint();
		text.setARGB(255, 0, 0, 0);
		text.setTextSize(40);
		text.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	return _thread.doTouchEvent(event);
    }

    @Override
    public void onDraw(Canvas canvas) {
		if(initiate == 0){
			tiles = new Grid(xsize,ysize,getWidth(),getHeight(),getContext());
			for(int i=0;i<xsize;++i){
				int[] sides = tiles.getGridZone(i, 5).getSides();
				ZonePath path = new ZonePath(sides[0],sides[1],sides[2],sides[3],getContext());
				tiles.setGridZone(i, 5, path);
				pathlist.add(path);
			}
			initiate = 1;
		}
		Drawable background;
		background = getContext().getResources().getDrawable(R.drawable.simplebackground);
		background.setBounds(0, 0, getWidth(), getHeight());
		background.draw(canvas);
        drawZones(canvas);
        drawCreeps(canvas);
        drawUserInfo(canvas);
    }
	protected void drawZones(Canvas canvas){

		for(int i = 0;i<xsize;++i){ 
			for(int j=0;j<ysize;++j){
				tiles.getGridZone(i,j).drawSelf(canvas,getContext());
			}
		}

	}
	protected void drawCreeps(Canvas canvas){
//		Creep test = new Creep(400,200,user,this);
//		test.drawSelf(canvas);
    	for(int i =0; i<creeplist.size();++i){
    		creeplist.get(i).drawSelf(canvas);
    	}
    	for(int i = 0;i<creeplist.size();++i){
    		if(creeplist.get(i).getAlive()==false){
    			creeplist.remove(i);
    		}
    	}
	}
	protected void drawUserInfo(Canvas canvas){
		String userscore = Integer.toString(user.getScore());
		String money = Integer.toString(user.getMoney());
		String lives = Integer.toString(user.getLives());
		canvas.drawText("  Score = " + userscore + " Money = " + money+" Lives = "+lives,0, getHeight()/ysize-15,text);
	}
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        _thread.setRunning(true);
        _thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // simply copied from sample application LunarLander:
        // we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
        boolean retry = true;
        _thread.setRunning(false);
        while (retry) {
            try {
                _thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // we will try it again and again...
            }
        }
    }
}

