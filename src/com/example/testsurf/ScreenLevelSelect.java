package com.example.testsurf;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * Displays the Select Level Screen
 * @author Sean
 *
 */
public class ScreenLevelSelect extends Activity {
	/** Called when the activity is first created. */
	protected int theme = 0;
	protected int level = 0;
	public SharedPreferences prefs;
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
		setContentView(R.layout.levelselect);
		prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		RadioGroup RadioGroupLevel = (RadioGroup) findViewById(R.id.RadioGroupLevel);
		RadioGroupLevel.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.RadioOne:
					level = 1;
					break;
				case R.id.RadioTwo:
					level = 2;
					break;
				case R.id.RadioThree:
					level = 3;
					break;
				}
			}
		});
		RadioGroup RadioGroupTheme = (RadioGroup) findViewById(R.id.RadioGroupTheme);
		RadioGroupTheme.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.RadioClassic:
					theme = 1;
					break;
				case R.id.RadioModern:
					theme = 2;
					break;
				}
			}

		});	
		Button mainmenu = (Button) findViewById(R.id.ButtonStart);
		mainmenu.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if((theme!=0)&&(level!=0)){
					SharedPreferences.Editor editor = prefs.edit();
					editor.putInt("theme", theme);
					editor.putInt("level", level);
					editor.commit();
					Intent myIntent = new Intent(view.getContext(), Main.class);
					startActivity(myIntent);
				}
			}
		});
	}
}
