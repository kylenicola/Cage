package com.example.nicolascageapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class Stats extends Activity {
	// for sharedPrefs
	public static String RATTLETHECAGE_BEST_TIME = "RattleTheCageBestTime";
	public static String ABCSWITHNIC_BEST_SCORE = "AbcsWithNicBestScore";
	public static String CAGECLUES_MYSTERY_SOLVED = "CageCluesMysterySolved";
	public static String CAGECLUES_TIMES_WATCH = "CageCluesTimesWatched";
	
	// Where user came from
	public static int MENU = 1;
	public static int RATTLETHECAGE = 2;
	public static int ABCSWITHNIC = 3;
	public static int CAGECLUES = 4;
	
	// stats
	private long bestTime;
	private int bestScore;
	private boolean mysterySolved;
	private int timesWatched;
	
	// views
	private TextView bestTimeView;
	private TextView bestScoreView;
	private TextView mysterySolvedView;
	private TextView timesWatchedView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.stats);
		
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		
		// get stats from shared prefs
		getStats();
		
		// set textViews using stats
		setStats();
		
		
	}
	
	private void getStats()
	{
		SharedPreferences prefs = this.getSharedPreferences("mPrefs", 0);
		
		bestTime = prefs.getLong(RATTLETHECAGE_BEST_TIME, -1);
		bestScore = prefs.getInt(ABCSWITHNIC_BEST_SCORE, -1);
		mysterySolved = prefs.getBoolean(CAGECLUES_MYSTERY_SOLVED, false);
		timesWatched = prefs.getInt(CAGECLUES_TIMES_WATCH, 0);
	}
	
	private void setStats()
	{
		bestTimeView = (TextView) findViewById(R.id.stats_rattlethecage_best_time);
		bestScoreView = (TextView) findViewById(R.id.stats_abcswithnic_high_score);
		mysterySolvedView = (TextView) findViewById(R.id.stats_cageclues_mystery_solved);
		timesWatchedView = (TextView) findViewById(R.id.stats_cageclues_times_watched);
		
		// bestTime
		if(bestTime > 0)
		{
			long second = (bestTime / 1000) % 60;
			long minute = (bestTime / (1000 * 60)) % 60;
			long hour = (bestTime / (1000 * 60 * 60)) % 24;
			String time = String.format("%02d:%02d:%02d", hour, minute, second);
			bestTimeView.setText(time);
		}
		else
		{
			bestTimeView.setText("Haven\'t rattled");
		}
		
		// best score
		if(bestScore > 0)
		{
			bestScoreView.setText(String.valueOf(bestScore));
		}
		else
		{
			bestScoreView.setText("Haven\'t or can\'t spell");
		}
		
		if(mysterySolved)
		{
			mysterySolvedView.setText("YUSSSSSSSS");
		}
		else
		{
			mysterySolvedView.setText("Nope.  Dummy alert.");
		}
		
		if(timesWatched > 10)
		{
			timesWatchedView.setText(String.valueOf(timesWatched));
		}
		else
		{
			timesWatchedView.setText("NOT ENOUGH");
		}
	}
	
	//private void 

//	public void backPressed(View v)
//	{
//		TextView backView = (TextView) findViewById(R.id.stats_back);
//		backView.setTextColor(android.graphics.Color.YELLOW);
//		onBackPressed();
//	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
	
}
