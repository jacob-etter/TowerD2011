package com.example.testsurf;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/*This is the customDialog that will pop up when 
 * a user clicks on a zone. This dialog will then ask
 * for a tower selection and call the view to update
 */
public class DialogSellTower extends Dialog implements OnClickListener{
	Button ButtonYes;
	Button ButtonNo;
	private GameView view;
	private int x;
	private int y;
	User user;
	TextView tx;
	Context context;
	public DialogSellTower(Context gamecontext, GameView g, int xloc, int yloc, User gameuser) {
		super(gamecontext);
		context = gamecontext;
		x = xloc;
		y = yloc;
		view = g;
		user = gameuser;
		/** 'Window.FEATURE_NO_TITLE' - Used to hide the title */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/** Design the dialog in main.xml file */
		setContentView(R.layout.dialogselltower);
		ButtonYes = (Button) findViewById(R.id.ButtonSellYes);
		ButtonYes.setOnClickListener(this);
		ButtonNo = (Button) findViewById(R.id.ButtonSellNo);
		ButtonNo.setOnClickListener(this);
		tx = (TextView) findViewById(R.id.saleprice);
		int saleprice = view.tiles.getGridZone(x,y).getSalePrice();
		String text = Integer.toString(saleprice);
		tx.setText(text);
	}

	@Override
	public void onClick(View v) {
		int [] sides = view.tiles.getGridZone(x,y).getSides();
		Zone empty;
		int saleprice;
		if (v == ButtonYes){
			empty = new ZoneEmpty(sides[0],sides[1],sides[2],sides[3],context);
			saleprice = view.tiles.getGridZone(x,y).getSalePrice();
			user.incMoney(saleprice);
			view.tiles.setGridZone(x,y,empty);
			dismiss();
		}
		else if (v == ButtonNo){
			dismiss();
		}
	}
}