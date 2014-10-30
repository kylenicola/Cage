package com.example.nicolascageapp;


import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.os.Build;

import java.util.Timer;

public class MainActivity extends Activity {

	//final ImageButton rattleTheCageButton = (ImageButton) findViewById(R.id.imageButton1);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		
		/*rattleTheCageButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				rattleTheCageButton.setPressed(true);
				Handler handler = new Handler();
			    handler.postDelayed(new Runnable() {
			    	@Override
			        public void run() {
			    		Intent intent = new Intent(getBaseContext(), RattleTheCageActivity.class);
			    		startActivity(intent);
			        }
			    }, 1000);
			    return true;
			}
		});*/
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onClickRattleCage(View view) {
		//rattleTheCageButton.setPressed(true);
		Handler handler = new Handler();
	    handler.postDelayed(new Runnable() {
	    	@Override
	        public void run() {
	    		Intent intent = new Intent(getBaseContext(), RattleTheCage.class);
	    		startActivity(intent);
	        }
	    }, 1000);
	}
}		


