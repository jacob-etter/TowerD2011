package com.example.testsurf;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Displays the Game Over Sreen
 * @author Sean
 *
 */
public class ScreenGameOver extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		//force landscape
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onCreate(savedInstanceState);
		//need to implement shared preferences
		setContentView(R.layout.gameover);
		String score = this.getString(R.string.FinalScore);
		String rounds = this.getString(R.string.RoundsComp);
		TextView txscore = (TextView) findViewById(R.id.TextViewScore);
		TextView txrounds = (TextView) findViewById(R.id.TextViewRComp);
		txscore.setText(score);
		txrounds.setText(rounds);
		Button mainmenu = (Button) findViewById(R.id.ButtonMainMenu);
		mainmenu.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				//add to high scores list needs be done marina
				Intent myIntent = new Intent(view.getContext(), Menu.class);
				myIntent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
				startActivity(myIntent);
			}
		});
	}
}
