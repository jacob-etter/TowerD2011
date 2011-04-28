/**
 * This is a dialog that is used to buy a tower
 * 
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
package com.example.towerd2011;

import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
		ImageView towerrifle = (ImageView) findViewById(R.id.ImageViewRifle);
		ImageView towermachine = (ImageView) findViewById(R.id.ImageViewMachine);
		ImageView towertesla = (ImageView) findViewById(R.id.ImageViewTesla);
		if(view.getTheme() == 2){
			towermachine.setImageDrawable(context.getResources().getDrawable(R.drawable.machinegunfull));
			towerrifle.setImageDrawable(context.getResources().getDrawable(R.drawable.riflefull));
			towertesla.setImageDrawable(context.getResources().getDrawable(R.drawable.teslafull));
		}
		else{
			towerrifle.setImageDrawable(context.getResources().getDrawable(R.drawable.cannon));
			towermachine.setImageDrawable(context.getResources().getDrawable(R.drawable.classicmachinegun));
			towertesla.setImageDrawable(context.getResources().getDrawable(R.drawable.teslafull));
		}
		TextView damage_rifle = (TextView) findViewById(R.id.TextRifleDamage);
		damage_rifle.setText("damage = "+Integer.toString(context.getResources().getInteger(R.integer.towerrifledamage)));
		TextView damage_machine = (TextView) findViewById(R.id.TextMachineDamage);
		damage_machine.setText("damage = "+Integer.toString(context.getResources().getInteger(R.integer.towermachinedamage)));
		TextView damage_tesla = (TextView) findViewById(R.id.TextTeslaDamage);
		damage_tesla.setText("damage = 3x"+Integer.toString(context.getResources().getInteger(R.integer.towertesladamage)));
		TextView range_rifle = (TextView) findViewById(R.id.TextRifleRange);
		range_rifle.setText("range = "+Integer.toString(context.getResources().getInteger(R.integer.towerriflerange)));
		TextView range_machine = (TextView) findViewById(R.id.TextMachineRange);
		range_machine.setText("range = "+Integer.toString(context.getResources().getInteger(R.integer.towermachinerange)));
		TextView range_tesla = (TextView) findViewById(R.id.TextTeslaRange);
		range_tesla.setText("range = "+Integer.toString(context.getResources().getInteger(R.integer.towerteslarange)));
		TextView cooldown_rifle = (TextView) findViewById(R.id.TextRifleCooldown);
		cooldown_rifle.setText("rate = "+Float.toString((float) (context.getResources().getInteger(R.integer.towerriflecooldown)/1000.0)));
		TextView cooldown_machine = (TextView) findViewById(R.id.TextMachineCooldown);
		cooldown_machine.setText("rate = "+Float.toString((float) (context.getResources().getInteger(R.integer.towermachinecooldown)/1000.0)));
		TextView cooldown_tesla = (TextView) findViewById(R.id.TextTeslaCooldown);
		cooldown_tesla.setText("rate = "+Float.toString((float) (context.getResources().getInteger(R.integer.towerteslacooldown)/1000.0)));
		TextView cost_rifle = (TextView) findViewById(R.id.TextRifleCost);
		cost_rifle.setText("cost = "+Integer.toString(context.getResources().getInteger(R.integer.pricetowerrifle)));
		TextView cost_machine = (TextView) findViewById(R.id.TextMachineCost);
		cost_machine.setText("cost = "+Integer.toString(context.getResources().getInteger(R.integer.pricetowermachinegun)));
		TextView cost_tesla = (TextView) findViewById(R.id.TextTeslaCost);
		cost_tesla.setText("cost = "+Integer.toString(context.getResources().getInteger(R.integer.pricetowertesla)));
	}

	public void onClick(View v) {
		int [] sides = view.tiles.getGridZone(x_pos,y_pos).getSides();
		int usermoney = user.getMoney();
		Resources res = context.getResources();
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
