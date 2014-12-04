package com.example.nicolascageapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ScoreMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score_menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.score_menu, menu);
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
	
	public void onClickRattleTheCageScore(View view) {
		Intent intent = new Intent(this, RattleTheCageScore.class);
		startActivity(intent);
	}
	
	public void onClickABCsScore(View view) {
		Intent intent = new Intent(this, ABCsScore.class);
		startActivity(intent);
	}
	
	public void onClickCageCluesScore(View view) {
		Intent intent = new Intent(this, CageCluesScore.class);
		startActivity(intent);
	}		
}