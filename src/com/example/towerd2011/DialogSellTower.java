/**
 * This is a dialog used to sell a tower
 * 
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
package com.example.towerd2011;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DialogSellTower extends DialogCustom implements OnClickListener{
	/** get the buttons and text views */
	private Button ButtonSell;
	private Button ButtonCancel;
	private Button ButtonUpDmg ;
	private Button ButtonUpCooldown;
	private Button ButtonUpRange;
	private TextView sell_value;
	private TextView up_range;
	private TextView up_dmg;
	private TextView up_cooldown;
	Tower tower;
	/**
	 * This is the constructor for DialogSellTower
	 * @param gameview
	 * @param xloc
	 * @param yloc
	 */
	public DialogSellTower(GameView gameview, int xloc, int yloc){
		super(gameview,xloc,yloc);
		/** set the layout */
		setContentView(R.layout.dialogselltower);
		/** load all the buttons and set their onclicklisteners */
		ButtonSell = (Button) findViewById(R.id.ButtonSell);
		ButtonSell.setOnClickListener(this);
		ButtonCancel = (Button) findViewById(R.id.ButtonCancel);
		ButtonCancel.setOnClickListener(this);
		ButtonUpDmg = (Button) findViewById(R.id.ButtonUpDmg);
		ButtonUpDmg.setOnClickListener(this);
		ButtonUpCooldown = (Button) findViewById(R.id.ButtonUpCooldown);
		ButtonUpCooldown.setOnClickListener(this);
		ButtonUpRange = (Button) findViewById(R.id.ButtonUpRng);
		ButtonUpRange.setOnClickListener(this);
		/** find the tower that was selected and get its stats */
		tower = (Tower) view.tiles.getGridZone(x_index,y_index);
		int sale_price = tower.getSalePrice();
		int cd_price = tower.getCooldownPrice();
		int dmg_price = tower.getDamagePrice();
		int rng_price = tower.getRangePrice();
		/** put the prices into strings to be displayed on the dialog */
		String s_price = Integer.toString(sale_price);
		String c_price = Integer.toString(cd_price);
		String d_price = Integer.toString(dmg_price);
		String r_price = Integer.toString(rng_price);
		/** get all the text views and set the prices for upgrades and saleprice */
		sell_value = (TextView) findViewById(R.id.saleprice);
		up_range = (TextView) findViewById(R.id.RngCost);
		up_dmg = (TextView) findViewById(R.id.DmgCost);
		up_cooldown = (TextView) findViewById(R.id.CoolCost);
		sell_value.setText(s_price);
		up_range.setText(r_price);
		up_dmg.setText(d_price);
		up_cooldown.setText(c_price);
	}
	public void onClick(View v) {
		int [] sides = view.tiles.getGridZone(x_index,y_index).getSides();
		Zone empty;
		int saleprice;
		int usermoney = user.getMoney();
		/** sell the tower */
		if (v == ButtonSell){
			empty = new ZoneEmpty(sides[0],sides[1],sides[2],sides[3],view);
			saleprice = tower.getSalePrice();
			user.incMoney(saleprice);
			view.tiles.setGridZone(x_index,y_index,empty);
			exitDialog();
			/** remove the tower from the tower list */
			for(int i=0;i<towerlist.size();++i){
				if(sides == towerlist.get(i).getSides()){
					towerlist.remove(i);
				}
			}
		}
		/** upgrade the tower damage */
		else if ((v == ButtonUpDmg)&&(usermoney >= tower.getDamagePrice())){
			user.decMoney(tower.getDamagePrice());
			tower.upDamage();
			exitDialog();
		}
		/** upgrade the towers range */
		else if ((v == ButtonUpRange)&&(usermoney >= tower.getRangePrice())){
			user.decMoney(tower.getRangePrice());
			tower.upRange();
			exitDialog();
		}
		/** upgrade the towers cooldown */
		else if ((v == ButtonUpCooldown)&&(usermoney >= tower.getCooldownPrice())){
			user.decMoney(tower.getCooldownPrice());
			tower.upCooldown();
			exitDialog();
		}
		/** close the screen */
		else if (v == ButtonCancel){
			exitDialog();
		}
	}
}