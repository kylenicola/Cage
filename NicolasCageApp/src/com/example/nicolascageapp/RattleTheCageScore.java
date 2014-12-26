package com.example.nicolascageapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class RattleTheCageScore extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rattle_the_cage_score);

		SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
		Editor editor = prefs.edit();

		TextView mostRecentScore = (TextView) findViewById(R.id.mostRecentScoreRattle);
		int most_recent_score = prefs.getInt("rattle_most_recent_score", 0);

		int hi_score_two = prefs.getInt("rattle_hi_score", 0);
		TextView hiScoreTwo = (TextView)findViewById(R.id.hiScoreRattle);
		hiScoreTwo.setText(Integer.toString(hi_score_two));

		mostRecentScore.setText(Integer.toString(most_recent_score));

		if(prefs.getInt("rattle_hi_score", 0) < most_recent_score) {
			editor.putInt("rattle_hi_score", most_recent_score);
			editor.commit();
			int hi_score = prefs.getInt("rattle_hi_score", 0);
			TextView hiScore = (TextView)findViewById(R.id.hiScoreRattle);
			hiScore.setText(Integer.toString(hi_score));
		}
	}


	public void onClickToMainMenu(View view) {
		Intent intent = new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}	

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

}
