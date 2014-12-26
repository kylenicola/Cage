package com.example.nicolascageapp;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;


public class AbcsWithNic extends Activity
{
	VideoView myVideoView;
	public int playerScore;

	private int currentPosition = 0;
	private int differenceTime = 0;

	private Map<Integer, Integer> letterTimes = new HashMap<Integer, Integer>();
	private Map<Integer, Boolean> letterClicked = new HashMap<Integer, Boolean>();

	private Vibrator vibrator;
	private long buttonVibrateTime = 100;

	private final String TAG = "NicolasCageApp AbcsWithNic";

	private int[] letters = {R.id.ButtonA, R.id.ButtonB, R.id.ButtonC,
			R.id.ButtonD, R.id.ButtonE, R.id.ButtonF,
			R.id.ButtonG, R.id.ButtonH, R.id.ButtonI,
			R.id.ButtonJ, R.id.ButtonK, R.id.ButtonL,
			R.id.ButtonM, R.id.ButtonN, R.id.ButtonO,
			R.id.ButtonP, R.id.ButtonQ, R.id.ButtonR,
			R.id.ButtonS, R.id.ButtonT, R.id.ButtonU,
			R.id.ButtonV, R.id.ButtonW, R.id.ButtonX,
			R.id.ButtonY, R.id.ButtonZ};
	private int[] times = {R.integer.TimeA, R.integer.TimeB, R.integer.TimeC, 
			R.integer.TimeD, R.integer.TimeE, R.integer.TimeF,
			R.integer.TimeG, R.integer.TimeH, R.integer.TimeI,
			R.integer.TimeJ, R.integer.TimeK, R.integer.TimeL,
			R.integer.TimeM, R.integer.TimeN, R.integer.TimeO,
			R.integer.TimeP, R.integer.TimeQ, R.integer.TimeR,
			R.integer.TimeS, R.integer.TimeT, R.integer.TimeU,
			R.integer.TimeV, R.integer.TimeW, R.integer.TimeX,
			R.integer.TimeY, R.integer.TimeZ};

	private final int NUM_LETTERS = 26;

	private void fillLetterTimesAndClicked()
	{		   		   
		Resources res = getResources();
		for(int i = 0; i < NUM_LETTERS; i++)
		{
			letterTimes.put(letters[i], res.getInteger(times[i]));
			letterClicked.put(letters[i], false);
		}		   
	}

	public void onLetterClick(View v)
	{
		// Vibrate for touch
		vibrator.vibrate(buttonVibrateTime);

				int letter = v.getId();
				int score = 0;

				currentPosition = myVideoView.getCurrentPosition();
				differenceTime = Math.abs(currentPosition - letterTimes.get(letter));

				if(differenceTime < 500)
				{
					if(!letterClicked.get(letter))
					{
						score = 100;
						letterClicked.put(letter, true);
					}
				}
				else if(differenceTime < 1000)
				{
					if(!letterClicked.get(letter))
					{
						score = 50;
						letterClicked.put(letter, true);
					}
				}
				else
				{
					score = -25;
				}


				Log.d(TAG, "Difference time: " + String.valueOf(differenceTime));
				Log.d(TAG, "Letter time: " + String.valueOf(letterTimes.get(letter)));
				Log.d(TAG, "Progress: " + String.valueOf(myVideoView.getCurrentPosition()));

				// update player score
				playerScore += score;

				// change a text to show score
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

		setContentView(R.layout.activity_abcs_with_nic);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		final SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);

		myVideoView = (VideoView)findViewById(R.id.abcs_videoView);
		myVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.abcs_video));
		myVideoView.start();


		fillLetterTimesAndClicked();



		myVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() 
		{
			@Override
			public void onCompletion(MediaPlayer mp) 
			{

				Handler handler = new Handler();
				handler.postDelayed(new Runnable() 
				{
					@Override
					public void run() 
					{
						Editor editor = prefs.edit();
						editor.putInt("abc_most_recent_score", getPlayerScore());
						editor.commit();
						String endToast = "Your score was: " + getPlayerScore();
						Toast.makeText(AbcsWithNic.this, endToast, 
								Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(getBaseContext(), ABCsScore.class);
						intent.putExtra("playerScore", playerScore);
						startActivity(intent);
					}

				}, 500);
			}
		});

	}

	public int getPlayerScore()
	{
		return playerScore;
	}

	@Override
	public void onBackPressed() 
	{
		Intent intent = new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}

