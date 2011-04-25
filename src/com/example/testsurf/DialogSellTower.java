package com.example.testsurf;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * This is a dialog used to sell a tower
 * 
 * @author Sean
 *
 */
public class DialogSellTower extends DialogTower implements OnClickListener{
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
		setContentView(R.layout.dialogselltower);
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
		tower = (Tower) view.tiles.getGridZone(x_pos,y_pos);
		int sale_price = tower.getSalePrice();
		int cd_price = tower.getCooldownPrice();
		int dmg_price = tower.getDamagePrice();
		int rng_price = tower.getRangePrice();
		String s_price = Integer.toString(sale_price);
		String c_price = Integer.toString(cd_price);
		String d_price = Integer.toString(dmg_price);
		String r_price = Integer.toString(rng_price);
		sell_value = (TextView) findViewById(R.id.saleprice);
		up_range = (TextView) findViewById(R.id.RngCost);
		up_dmg = (TextView) findViewById(R.id.DmgCost);
		up_cooldown = (TextView) findViewById(R.id.CoolCost);
		sell_value.setText(s_price);
		up_range.setText(r_price);
		up_dmg.setText(d_price);
		up_cooldown.setText(c_price);
	}
	@Override
	public void onClick(View v) {
		int [] sides = view.tiles.getGridZone(x_pos,y_pos).getSides();
		Zone empty;
		int saleprice;
		int usermoney = user.getMoney();
		if (v == ButtonSell){
			empty = new ZoneEmpty(sides[0],sides[1],sides[2],sides[3],view);
			saleprice = tower.getSalePrice();
			user.incMoney(saleprice);
			view.tiles.setGridZone(x_pos,y_pos,empty);
			dismiss();
			for(int i=0;i<towerlist.size();++i){
				if(sides == towerlist.get(i).getSides()){
					towerlist.remove(i);
				}
			}
		}
		else if ((v == ButtonUpDmg)&&(usermoney >= tower.getDamagePrice())){
			user.decMoney(tower.getDamagePrice());
			tower.upDamage();
			dismiss();
		}
		else if ((v == ButtonUpRange)&&(usermoney >= tower.getRangePrice())){
			user.decMoney(tower.getRangePrice());
			tower.upRange();
			dismiss();
		}
		else if ((v == ButtonUpCooldown)&&(usermoney >= tower.getCooldownPrice())){
			user.decMoney(tower.getCooldownPrice());
			tower.upCooldown();
			dismiss();
		}
		else if (v == ButtonCancel){
			dismiss();
		}
	}
}