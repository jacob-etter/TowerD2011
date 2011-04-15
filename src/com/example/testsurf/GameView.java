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
    private int xpress;
    private int ypress;
    private int xsize = 16;//size of grid in x
    private int ysize= 10;//size of grid in y
    public Grid tiles; //grid of zones
    private int initiate=0;
	private long click_time;
	private User user;
	public ArrayList<Creep> creeplist = new ArrayList<Creep>();

    public GameView(Context context) {
        super(context); 
        getHolder().addCallback(this);
        _thread = new GameThread(getHolder(), this);
        setFocusable(true);
		user = new User(2000, 0, 10, "Sean");
		text = new Paint();
		text.setARGB(255, 0, 0, 0);
		text.setTextSize(40);
		text.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int old_xpress;
        int old_ypress;
    	synchronized (_thread.getSurfaceHolder()) {
    		if (event.getAction() == MotionEvent.ACTION_DOWN){
    			ypress  = (int) event.getY();
    			xpress = (int) event.getX();
    			xpress = xpress/(getWidth()/xsize);
    			ypress = ypress/(getHeight()/ysize);
    			tiles.getGridZone(xpress,ypress).setHighlight();
    			click_time = System.currentTimeMillis();
    		} 
    		else if (event.getAction() == MotionEvent.ACTION_MOVE){
    			old_xpress = xpress;
    			old_ypress = ypress;
    			tiles.getGridZone(xpress,ypress).removeHighlight();
    			ypress  = (int) event.getY();
    			xpress = (int) event.getX();
    			xpress = xpress/(getWidth()/xsize);
    			ypress = ypress/(getHeight()/ysize);
    			tiles.getGridZone(xpress,ypress).setHighlight();
    			if((old_xpress != xpress)&&(old_ypress!=ypress)){
    				click_time = System.currentTimeMillis();
    			}
    		}
        	else if (event.getAction() == MotionEvent.ACTION_UP){
    			tiles.getGridZone(xpress,ypress).removeHighlight();
    			if((System.currentTimeMillis()-click_time )> 250){
    				ypress  = (int) event.getY();
    				xpress = (int) event.getX();
    				xpress = xpress/(getWidth()/xsize);
    				ypress = ypress/(getHeight()/ysize);
    				if(tiles.getGridZone(xpress,ypress).getID()==2){
    					CustomDialog customDialog = new CustomDialog(getContext(), this, xpress, ypress,user);
    					customDialog.show();
    				}
    				else if(tiles.getGridZone(xpress,ypress).getID()>2){
    					DialogSellTower dialogselltower = new DialogSellTower(getContext(), this,xpress,ypress,user);
    					dialogselltower.show();
    				}
    			}
    		}
    		return true;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
		if(initiate == 0){
			tiles = new Grid(xsize,ysize,getWidth(),getHeight(),getContext());
			initiate = 1;
			creeplist.add(new Creep(50, 50,user, getContext()));
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
    	for(int i =0; i<creeplist.size();++i){
    		creeplist.get(i).drawSelf(canvas);
    	}
	}
	protected void drawUserInfo(Canvas canvas){
		String userscore = Integer.toString(user.getScore());
		String money = Integer.toString(user.getMoney());
		canvas.drawText("Score = " + userscore + " Money = " + money,0, getHeight()/ysize-15,text);
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

