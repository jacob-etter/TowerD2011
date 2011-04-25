package com.example.testsurf;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
/**
 * Displays the options menu
 * @author Marina
 *
 */
public class ScreenOptions extends ScreenMainMenu {

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		//force landscape
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);
		Button highscores = (Button) findViewById(R.id.ButtonHighScores);
		highscores.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(), ScreenHighScores.class);
				startActivity(myIntent);
			}
		});
	}
}