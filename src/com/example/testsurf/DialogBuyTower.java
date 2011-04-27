package com.example.testsurf;

import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * This is a dialog that is used to buy a tower
 * 
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
public class DialogBuyTower extends DialogCustom implements OnClickListener{
	private Button ButtonRed;
	private Button ButtonBlue;
	private Button ButtonGreen;
	private Button ButtonCancel;
	/**
	 * Constructor for the DialogBuyTower
	 * 
	 * @param gameview
	 * @param xloc
	 * @param yloc
	 */
	public DialogBuyTower(GameView gameview, int xloc, int yloc){
		super(gameview,xloc,yloc);
		/** Design the dialog in main.xml file */
		setContentView(R.layout.dialogbuytower);
		ButtonRed = (Button) findViewById(R.id.ButtonRed);
		ButtonRed.setOnClickListener(this);
		ButtonBlue = (Button) findViewById(R.id.ButtonBlue);
		ButtonBlue.setOnClickListener(this);
		ButtonGreen = (Button) findViewById(R.id.ButtonGreen);
		ButtonGreen.setOnClickListener(this);
		ButtonCancel = (Button) findViewById(R.id.ButtonCancel);
		ButtonCancel.setOnClickListener(this);
	}

	public void onClick(View v) {
		int [] sides = view.tiles.getGridZone(x_pos,y_pos).getSides();
		int usermoney = user.getMoney();
		Resources res = view.getContext().getResources();
		Tower tower;
		/** When OK Button is clicked, dismiss the dialog */
		if ((v == ButtonRed)&&(usermoney >= res.getInteger(R.integer.pricetowerrifle))){
			user.decMoney(res.getInteger(R.integer.pricetowerrifle));
			tower = new TowerRifle(sides[0],sides[1],sides[2],sides[3],view);
			towerlist.add((Tower) tower);
			view.tiles.setGridZone(x_pos,y_pos,tower);
			exitDialog();
		}
		else if ((v == ButtonBlue)&&(usermoney >= res.getInteger(R.integer.pricetowermachinegun))){
			user.decMoney(res.getInteger(R.integer.pricetowermachinegun));
			tower = new TowerMachineGun(sides[0],sides[1],sides[2],sides[3],view);
			towerlist.add((Tower) tower);
			view.tiles.setGridZone(x_pos,y_pos,tower);
			exitDialog();
		}
		else if ((v == ButtonGreen)&&(usermoney >= res.getInteger(R.integer.pricetowertesla))){
			user.decMoney(res.getInteger(R.integer.pricetowertesla));
			tower = new TowerTesla(sides[0],sides[1],sides[2],sides[3],view);
			towerlist.add((Tower) tower);
			view.tiles.setGridZone(x_pos,y_pos,tower);
			exitDialog();
		}
		else if (v == ButtonCancel){
			exitDialog();
		}
	}
}
