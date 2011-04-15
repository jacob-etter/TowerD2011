package com.example.testsurf;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

/*This is the customDialog that will pop up when 
 * a user clicks on a zone. This dialog will then ask
 * for a tower selection and call the view to update
 */
public class CustomDialog extends Dialog implements OnClickListener{
	Button ButtonRed;
	Button ButtonBlue;
	Button ButtonGreen;
	Button ButtonCancel;
	private GameView view;
	private int x;
	private int y;
	private User user;
	private Context context;
	public CustomDialog(Context gamecontext, GameView g, int xloc, int yloc, User gameuser){
		super(gamecontext);
		context = gamecontext;
		user = gameuser;
		x = xloc;
		y = yloc;
		view = g;
		user = gameuser;
		/** 'Window.FEATURE_NO_TITLE' - Used to hide the title */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/** Design the dialog in main.xml file */
		setContentView(R.layout.customdialog);
		ButtonRed = (Button) findViewById(R.id.ButtonRed);
		ButtonRed.setOnClickListener(this);
		ButtonBlue = (Button) findViewById(R.id.ButtonBlue);
		ButtonBlue.setOnClickListener(this);
		ButtonGreen = (Button) findViewById(R.id.ButtonGreen);
		ButtonGreen.setOnClickListener(this);
		ButtonCancel = (Button) findViewById(R.id.ButtonCancel);
		ButtonCancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int [] sides = view.tiles.getGridZone(x,y).getSides();
		int usermoney = user.getMoney();
		Resources res = context.getResources();
		Zone tower;
		/** When OK Button is clicked, dismiss the dialog */
		if ((v == ButtonRed)&&(usermoney >= res.getInteger(R.integer.pricetowerred))){
			user.decMoney(res.getInteger(R.integer.pricetowerred));
			tower = new TowerRifle(sides[0],sides[1],sides[2],sides[3],context);
			view.tiles.setGridZone(x,y,tower);
			dismiss();
		}
		else if ((v == ButtonBlue)&&(usermoney >= res.getInteger(R.integer.pricetowerblue))){
			user.decMoney(res.getInteger(R.integer.pricetowerblue));
			tower = new TowerBlue(sides[0],sides[1],sides[2],sides[3],context);
			view.tiles.setGridZone(x,y,tower);
			dismiss();
		}
		else if ((v == ButtonGreen)&&(usermoney >= res.getInteger(R.integer.pricetowergreen))){
			user.decMoney(res.getInteger(R.integer.pricetowergreen));
			tower = new TowerGreen(sides[0],sides[1],sides[2],sides[3],context);
			view.tiles.setGridZone(x,y,tower);
			dismiss();
		}
		else if (v == ButtonCancel){
			dismiss();
		}
	}
}