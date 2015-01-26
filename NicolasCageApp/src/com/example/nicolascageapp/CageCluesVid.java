package com.example.nicolascageapp;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;


public class CageCluesVid extends Activity
{
	private final String TAG = "NicolasCageApp CageCluesVid";
	private final String CAGE_CLUES_VIDEO_TIME = "CAGE_CLUES_VIDEO_TIME";

	private VideoView cageCluesVideo;
	private int videoTime;

	private int cageCluesCount; 
	
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

		if(savedInstanceState != null)
		{
			videoTime = savedInstanceState.getInt(CAGE_CLUES_VIDEO_TIME, 0);
		}
		else
		{
			videoTime = 0;
		}
	}

	public void moveToQuiz(View view) {
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
		cageCluesVideo.seekTo(videoTime);
		cageCluesVideo.start();
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		videoTime = 0;
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		cageCluesVideo.pause();
	}

	@Override 
	protected void onResume()
	{
		super.onResume();
		cageCluesVideo.start();
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putInt(CAGE_CLUES_VIDEO_TIME, cageCluesVideo.getCurrentPosition());

		super.onSaveInstanceState(savedInstanceState);
	}

}
