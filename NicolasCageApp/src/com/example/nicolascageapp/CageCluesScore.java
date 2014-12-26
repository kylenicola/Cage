package com.example.nicolascageapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

public class CageCluesScore extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cage_clues_score);

		ViewFlipper mFlipper;
		mFlipper = ((ViewFlipper)findViewById(R.id.flipper));
		mFlipper.startFlipping();
		mFlipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in));
		mFlipper.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out));
	}

	public void onClickToMainMenu(View view) {
		Intent intent = new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}	

	@Override
	public void onBackPressed() 
	{
		Intent intent = new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

}
