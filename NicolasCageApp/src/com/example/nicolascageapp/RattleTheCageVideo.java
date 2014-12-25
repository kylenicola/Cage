package com.example.nicolascageapp;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.VideoView;


public class RattleTheCageVideo extends Activity
{
	static final int RATTLE_SCORE = 50;

	private final String TAG = "NicolasCageApp RattleTheCageVideo";
	private final String PREFS = "NicolageCageAppPrefs";
	private final String BEES_AUDIO_TIME = "Bees Audio Time";
	private final String BEES_VIDEO_TIME = "Bees Video Time";

	private Timer backgroundTimer;
	private boolean continueTask;

	private int beesVideoTime;
	private int beesAudioTime;

	private MediaPlayer beesAudioPlayer;
	private VideoView beesVideoView;



	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.rattle_end_video);

		beesAudioPlayer = MediaPlayer.create(this, R.raw.flightofbumblebees);

		int video = R.raw.not_the_bees;
		continueTask = true;

		beesVideoView = (VideoView)findViewById(R.id.RattleTheCageVideo);
		beesVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + video));
		beesVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {


			@Override
			public void onCompletion(MediaPlayer mp) {

				SharedPreferences prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
				Editor editor = prefs.edit();
				editor.putInt("rattle_most_recent_score", RATTLE_SCORE);
				editor.commit();


				Intent intent = new Intent(RattleTheCageVideo.this, RattleTheCageScore.class);
				intent.putExtra("score", RATTLE_SCORE);
				startActivity(intent);
				RattleTheCageVideo.this.finish();

			}
		});

		if(savedInstanceState != null)
		{
			beesAudioTime = savedInstanceState.getInt(BEES_AUDIO_TIME);
			beesVideoTime = savedInstanceState.getInt(BEES_VIDEO_TIME);
		}
		else
		{
			beesAudioTime = 0;
			beesVideoTime = 0;
		}

	}





	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onStop()
	{
		beesAudioTime = 0;
		beesVideoTime = 0;
		continueTask = false;
		backgroundTimer.cancel();
		backgroundTimer.purge();

		super.onStop();
	}

	@Override 
	protected void onStart()
	{
		beesVideoView.seekTo(beesVideoTime);
		beesAudioPlayer.seekTo(beesAudioTime);
		continueTask = true;
		backgroundTimer = new Timer();
		backgroundTimer.scheduleAtFixedRate(new TimerTask() {


			@Override
			public void run()
			{
				RattleTheCageVideo.this.runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						if(RattleTheCageVideo.this.continueTask)
						{
							Log.d(TAG, "switching background image");
							LinearLayout bee_layout = (LinearLayout) findViewById(R.id.bees_layout);
							BitmapDrawable bg = (BitmapDrawable) bee_layout.getBackground();
							BitmapDrawable bees_1_bg = (BitmapDrawable) getResources().getDrawable(R.drawable.bees_1_repeat);


							if(bg.getConstantState().equals(bees_1_bg.getConstantState()))
							{
								bee_layout.setBackgroundResource(R.drawable.bees_2_repeat);
							}
							else
							{
								bee_layout.setBackgroundResource(R.drawable.bees_1_repeat);
							}
						}
					}
				});



			}
		}
		, 0, 500);

		beesAudioPlayer.start();
		beesVideoView.start();

		super.onStart();
	}

	@Override
	protected void onPause()
	{
		super.onPause();

		continueTask = false;
		beesVideoView.pause();
		beesAudioPlayer.pause();
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		continueTask = true;
		beesVideoView.start();
		beesAudioPlayer.start();
		
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putInt(BEES_VIDEO_TIME, beesVideoView.getCurrentPosition());
		savedInstanceState.putInt(BEES_AUDIO_TIME, beesAudioPlayer.getCurrentPosition());

		super.onSaveInstanceState(savedInstanceState);
	}




}
