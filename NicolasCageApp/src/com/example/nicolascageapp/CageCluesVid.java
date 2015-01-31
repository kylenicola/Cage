package com.example.nicolascageapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.VideoView;


public class CageCluesVid extends Activity
{
	private final String TAG = "CageCluesVid";
	private final String CAGE_CLUES_VIDEO_TIME = "CAGE_CLUES_VIDEO_TIME";

	// Video info
	private VideoView cageCluesVideo;
	private int videoTime;
	private boolean userPaused;

	// count
	private int cageCluesCount; 
	
	// for descriptions
	public String[] descriptions;
	public int descriptionsLen;
	
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		Log.d(TAG, "Starts CageCluesVid");

		setContentView(R.layout.cage_clues);
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		
		descriptions = getResources().getStringArray(R.array.cageclues_description);
		descriptionsLen = descriptions.length;


		
		cageCluesVideo = (VideoView) findViewById(R.id.cageclues_video);
		cageCluesVideo.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.i_lost_my_hand2));

		// for count & loop
		cageCluesCount = 0;
		cageCluesVideo.setOnCompletionListener(new OnCompletionListener() {


			@Override
			public void onCompletion(MediaPlayer mp) {

				Log.d(TAG, "Completes loop");

				// Updates count
				CageCluesVid.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {

						// If someone watches this 2^31-1 times...which i mean, just wow.
						if(CageCluesVid.this.cageCluesCount + 1 < Integer.MAX_VALUE)
						{
							CageCluesVid.this.cageCluesCount += 1;
						}
						int cageCluesCount = CageCluesVid.this.cageCluesCount;
						
						
						// update count
						TextView cageCluesCountView = (TextView) findViewById(R.id.cageclues_count);
						cageCluesCountView.setText(String.valueOf(cageCluesCount));
						
						// update description
						if(cageCluesCount - 1 < CageCluesVid.this.descriptionsLen)
						{
							TextView cageCluesDescription = (TextView) findViewById(R.id.cageclues_description);
							cageCluesDescription.setText(CageCluesVid.this.descriptions[cageCluesCount-1]);
						}
					}

				});

				// Loop (though really looping would be best)
				mp.seekTo(0);
				mp.start();

			}

		});
		
		userPaused = false;
		// If the video is clicked will play/pause
		cageCluesVideo.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					Log.d(TAG, "touched");
					VideoView vid = (VideoView) v;
					if(vid.isPlaying())
					{
						vid.pause();
						userPaused = true;
					}
					else
					{
						vid.start();
						userPaused = false;
					}
				}
				return false;
			}
			
		});


		if(savedInstanceState != null)
		{
			videoTime = savedInstanceState.getInt(CAGE_CLUES_VIDEO_TIME, 0);
		}
		else
		{
			videoTime = 0;
		}
	}
	
	public void continueToQuiz(View view)
	{
		// update prefs
		updatePrefs();
		
		// set text yellow
		TextView cont = (TextView) view;
		cont.setTextColor(android.graphics.Color.YELLOW);
		
		// move to quiz
		Intent intent = new Intent(this, CageClues.class);
		startActivity(intent);
		
	}
	
	@Override
	public void onBackPressed() 
	{
		Intent intent = new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
//		if(!userPaused)
//		{
//			cageCluesVideo.seekTo(videoTime);
//		}
//		Log.d(TAG, "onstart userpause: " + String.valueOf(userPaused));
//		Log.d(TAG, "onstart videotime: " + String.valueOf(cageCluesVideo.getCurrentPosition()));
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		if(!userPaused)
		{
			videoTime = 0;
		}
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		cageCluesVideo.pause();
		videoTime = cageCluesVideo.getCurrentPosition();
		Log.d(TAG, "pause userpause: " + String.valueOf(userPaused));
		Log.d(TAG, "pause videotime: " + String.valueOf(cageCluesVideo.getCurrentPosition()));
	}

	@Override 
	protected void onResume()
	{
		super.onResume();
		cageCluesVideo.seekTo(videoTime);
		if(!userPaused)
		{
			cageCluesVideo.start();
		}
		
		Log.d(TAG, "resume userpause: " + String.valueOf(userPaused));
		Log.d(TAG, "resume videotime: " + String.valueOf(cageCluesVideo.getCurrentPosition()));
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putInt(CAGE_CLUES_VIDEO_TIME, cageCluesVideo.getCurrentPosition());

		super.onSaveInstanceState(savedInstanceState);
	}
	
	private void updatePrefs()
	{
		SharedPreferences prefs = this.getSharedPreferences("mPrefs", 0);
		int curTimesWatched = prefs.getInt(Stats.CAGECLUES_TIMES_WATCH, 0);
		Editor ed = prefs.edit();
		if(curTimesWatched < cageCluesCount)
		{
			ed.putInt(Stats.CAGECLUES_TIMES_WATCH, cageCluesCount);
			ed.putInt(Stats.RESULT, Stats.BETTER);
		}
		else if(curTimesWatched == cageCluesCount)
		{
			ed.putInt(Stats.RESULT, Stats.TIE);
		}
		else
		{
			ed.putInt(Stats.RESULT, Stats.WORSE);
		}
		ed.commit();
	}

}
