package com.example.nicolascageapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Stats extends Activity implements 
StatsClearPrefsDialogFragment.StatsClearPrefsDialogFragmentListener{
	public static final String TAG = "Stats";

	// for sharedPrefs
	public static final String RATTLETHECAGE_BEST_TIME = "RattleTheCageBestTime";
	public static final String ABCSWITHNIC_BEST_SCORE = "AbcsWithNicBestScore";
	public static final String CAGECLUES_MYSTERY_SOLVED = "CageCluesMysterySolved";
	public static final String CAGECLUES_TIMES_WATCH = "CageCluesTimesWatched";
	public static final String RESULT = "Result";

	// in extras
	public static final String WHERE_FROM = "WhereFrom";

	// if their score/time/number of times watched is better
	public static final int TIE = 1;
	public static final int BETTER = 2;
	public static final int WORSE = 3;

	// Where user came from
	public static final int MENU = 1;
	public static final int RATTLETHECAGE = 2;
	public static final int ABCSWITHNIC = 3;
	public static final int CAGECLUES = 4;

	// stats
	private long bestTime;
	private int bestScore;
	private boolean mysterySolved;
	private int timesWatched;
	private int result;
	private String timeRattle;

	// views
	private TextView bestTimeView;
	private TextView bestScoreView;
	private TextView mysterySolvedView;
	private TextView timesWatchedView;

	// this is to replay whatever class it came from
	private Class c;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.stats);

		//getActionBar().setDisplayHomeAsUpEnabled(true);


	}

	private void getStats()
	{
		SharedPreferences prefs = this.getSharedPreferences("mPrefs", 0);

		bestTime = prefs.getLong(RATTLETHECAGE_BEST_TIME, -1);
		bestScore = prefs.getInt(ABCSWITHNIC_BEST_SCORE, -1);
		mysterySolved = prefs.getBoolean(CAGECLUES_MYSTERY_SOLVED, false);
		timesWatched = prefs.getInt(CAGECLUES_TIMES_WATCH, -1);
		result = prefs.getInt(RESULT, 0);
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
			timeRattle = String.format("%02d:%02d:%02d:%d", hour, minute, second, bestTime % 1000);
			bestTimeView.setText(timeRattle);
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
			timesWatchedView.setText("Not enough");
		}
	}
	



	public void setRefresh(Bundle extras)
	{
		int whereFrom;

		if(extras != null)
		{
			whereFrom = extras.getInt(WHERE_FROM, MENU);
		}
		else
		{
			whereFrom = MENU;
		}

		Log.d(TAG, "where from: " + String.valueOf(whereFrom));

		// views to use
		LinearLayout buttonOptionsMenu = (LinearLayout) findViewById(R.id.stats_button_options);
		LinearLayout replayLayout = (LinearLayout) findViewById(R.id.stats_button_options_refresh);
		ImageView replayImageView = (ImageView) findViewById(R.id.stats_refresh_image);
		TextView replayTextView = (TextView) findViewById(R.id.stats_refresh_text);
		String msg = "";
		Toast t;
		Resources res = getResources();
		switch(whereFrom)
		{
		case RATTLETHECAGE:
			c = RattleTheCage.class;
			switch(result)
			{
			case TIE:
				msg = res.getString(R.string.stats_toast_rattlethecage_tie);
				break;
			case WORSE:
				msg = res.getString(R.string.stats_toast_rattlethecage_worse);
				break;
			case BETTER:
				msg = res.getString(R.string.stats_toast_rattlethecage_better);
				break;
			}
			t = Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG);
			t.show();
			break;
		case ABCSWITHNIC:
			c = AbcsWithNic.class;
			switch(result)
			{
			case TIE:
				msg = res.getString(R.string.stats_toast_abcswithnic_tie);
				break;
			case WORSE:
				msg = res.getString(R.string.stats_toast_abcswithnic_worse);
				break;
			case BETTER:
				msg = res.getString(R.string.stats_toast_abcswithnic_better);
				break;
			}
			t = Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG);
			t.show();
			break;
		case CAGECLUES:
			c = CageClues.class;
			switch(result)
			{
			case TIE:
				msg = res.getString(R.string.stats_toast_cageclues_tie);
				break;
			case WORSE:
				msg = res.getString(R.string.stats_toast_cageclues_worse);
				break;
			case BETTER:
				msg = res.getString(R.string.stats_toast_cageclues_better);
				break;
			}
			t = Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG);
			t.show();
			break;
		default:
			buttonOptionsMenu.removeView(replayLayout);

		}
	}

	

	public void onBackPressed(View v)
	{
		onBackPressed();
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	// For reset stats
	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		Log.d(TAG, "gets to Postiive Click");
		resetStats();
	}
	
	private void resetStats()
	{
		Log.d(TAG, "gets to resetStats");
		getSharedPreferences("mPrefs", 0).edit().clear().commit();
		getStats();
		setStats();
	}
	
	public void onResetPressed(View v)
	{
		Log.d(TAG, "onResetPressed");
		DialogFragment newFragment = new StatsClearPrefsDialogFragment();
		newFragment.show(getFragmentManager(), "resetStatsFragment");
	}
	
	
	// Go back to originating game
	public void onRefreshPressed(View v)
	{
		Intent intent = new Intent(this, c).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
	@Override 
	public void onStart()
	{
		super.onStart();
		// get stats from shared prefs
		getStats();

		// set textViews using stats
		setStats();

		setRefresh(getIntent().getExtras());
	}


}
