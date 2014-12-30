package com.example.nicolascageapp;


import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.media.audiofx.BassBoost.Settings;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Timer;

public class MainActivity extends Activity {
	
	static final int DIALOG_ABOUT_ID = 0;
	static final int DIALOG_HELP_ID = 1;
	static final int DIALOG_QUIT_ID = 2;
	static final int DIALOG_SETTINGS_ID = 3;
	
	public static final String KEY_PREF_SOUND = "pref_sound";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
		
		//getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
		
		final ImageButton rattleTheCageButton = (ImageButton) findViewById(R.id.imageButton1);
		rattleTheCageButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				rattleTheCageButton.setPressed(true);
				Handler handler = new Handler();
			    handler.postDelayed(new Runnable() {
			    	@Override
			        public void run() {
			    		Intent intent = new Intent(getBaseContext(), RattleTheCage.class);
			    		startActivity(intent);
			        }
			    }, 1000);
			    return true;
			}
		});
		
		final ImageButton abcButton = (ImageButton) findViewById(R.id.imageButton2);
		abcButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				abcButton.setPressed(true);
				Handler handler = new Handler();
			    handler.postDelayed(new Runnable() {
			    	@Override
			        public void run() {
			    		Intent intent = new Intent(getBaseContext(), AbcsWithNic.class);
			    		startActivity(intent);
			        }
			    }, 1000);
			    return true;
			}
		});
		
		final ImageButton cluesButton = (ImageButton) findViewById(R.id.imageButton3);
		cluesButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				cluesButton.setPressed(true);
				Handler handler = new Handler();
			    handler.postDelayed(new Runnable() {
			    	@Override
			        public void run() {
			    		Intent intent = new Intent(getBaseContext(), CageCluesVid.class);
			    		startActivity(intent);
			        }
			    }, 1000);
			    return true;
			}
		});
	}
	
//	public void onClickSettings(View view) {
//		Intent intent = new Intent(this, Settings.class);
//		startActivity(intent);
//	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return super.onCreateOptionsMenu(menu);
	}
	
	private Dialog createAboutDialog(Builder builder) {
		Context context = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.main_action_about, null); 		
		builder.setView(layout);
		builder.setPositiveButton("OK", null);	
		return builder.create();
	}
	
	private Dialog createHelpDialog(Builder builder) {
		Context context = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.main_action_help, null); 		
		builder.setView(layout);
		builder.setPositiveButton("OK", null);	
		return builder.create();
	}
	
	private Dialog createQuitDialog(Builder builder) {
		builder.setMessage(R.string.quit_question).setCancelable(false)
		.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				MainActivity.this.finish();
			}
		})
		.setNegativeButton(R.string.no, null);   
		return builder.create();
	}
	
	private Dialog createSettingsDialog(Builder builder) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
		boolean soundOn = prefs.getBoolean(KEY_PREF_SOUND, true);
		Log.d("main activity", "soundOn: " + String.valueOf(soundOn));
		boolean[] settingsDefault = {soundOn, false};
		
		builder.setPositiveButton("Ok", null).setMultiChoiceItems(R.array.Settings, settingsDefault, new DialogInterface.OnMultiChoiceClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {

				switch(which)
				{
				case 0:
					Log.d("main activity clicked", "isChecked: " + String.valueOf(isChecked));
					SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
					Editor ed = prefs.edit();
					ed.putBoolean(KEY_PREF_SOUND, isChecked);
					ed.commit();
					break;
				case 1:
					AlertDialog.Builder nopeBuilder = new AlertDialog.Builder(MainActivity.this);
					Dialog nopeDialog = null;
					if (isChecked)
					{
						nopeBuilder.setMessage("NICOLAS CAGE SLOWS FOR NO MAN").setPositiveButton("my bad", null);
						nopeDialog = nopeBuilder.create();
						nopeDialog.show();
					}
					break;
				}
				
			}
		});
		return builder.create();
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		switch(id)
		{
		case DIALOG_ABOUT_ID:
			dialog = createAboutDialog(builder);
			break;
		case DIALOG_HELP_ID:
			dialog = createHelpDialog(builder);
			break;
		case DIALOG_QUIT_ID:
			dialog = createQuitDialog(builder);
			break;
		case DIALOG_SETTINGS_ID:
			//
			Intent intent = new Intent(this, Settings.class);
			startActivity(intent);
			//dialog = createSettingsDialog(builder);
			break;
		
		}
		return dialog;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Log.d("butthole", "settings clicked");
			Intent intent = new Intent(MainActivity.this, SettingsPage.class);
			startActivity(intent);
			//showDialog(DIALOG_SETTINGS_ID);
			return true;
		}
		else if(id == R.id.action_about)
		{
			showDialog(DIALOG_ABOUT_ID);
			return true;
		}
		else if(id == R.id.action_help)
		{
			Log.d("butthole", "help clicked");
			showDialog(DIALOG_HELP_ID);
			return true;
		}
		else if(id == R.id.action_exit)
		{
			showDialog(DIALOG_QUIT_ID);
			return true;
		}
		else if(id == R.id.action_scores)
		{
			Intent intent = new Intent(this, ScoreMenu.class);
			startActivity(intent);
			// Create intent to go to scores menu
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}		


