package com.example.nicolascageapp;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.os.Build;

public class CageCluesQuiz extends Activity
{

	private RadioGroup rg = null;

	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.cage_clues_quiz);
		rg = (RadioGroup) findViewById(R.id.gameResponse);
		rg.clearCheck();
	}
	
	public void onRadioButtonClicked(View v)
	{
		updatePrefs();
		Intent intent = new Intent(getBaseContext(), Stats.class);
		intent.putExtra(Stats.WHERE_FROM, Stats.CAGECLUES);
		startActivity(intent);
	}
	
	private void updatePrefs()
	{
		SharedPreferences prefs = this.getSharedPreferences("mPrefs", 0);
		Editor ed = prefs.edit();
		ed.putBoolean(Stats.CAGECLUES_MYSTERY_SOLVED, true);
		ed.commit();
	}

	@Override
	public void onBackPressed() 
	{
		Intent intent = new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}