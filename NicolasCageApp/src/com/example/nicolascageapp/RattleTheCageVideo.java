package com.example.nicolascageapp;
import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;


public class RattleTheCageVideo extends Activity
{
	//String SrcPath = "http://youtu.be/diQhM7HLNG8";
	VideoView myVideoView;
	
	static final int RATTLE_SCORE = 50;
	
	private boolean finished = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.rattle_end_video);
		//LinearLayout layout = (LinearLayout) findViewById(R.id.rattle_vid_layout);
		//AnimationDrawable progressAnimation = (AnimationDrawable) layout.getBackground();
		//progressAnimation.start();
		
		//AnimationDrawable progressAnimation = (AnimationDrawable) getBackground();
		//progressAnimation.start();

		final SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
		// all videos
		int[] videos = {R.raw.not_the_bees, R.raw.im_a_vampire, R.raw.love_me, R.raw.america_going_down_the_drain};

		// grab random video
		int video = videos[(int)(Math.random()*videos.length)];
		video = R.raw.not_the_bees;
		
		
		myVideoView = (VideoView)findViewById(R.id.RattleTheCageVideo);
		myVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + video));
		myVideoView.start();
		myVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

	
			@Override
			public void onCompletion(MediaPlayer mp) {

				// TODO Auto-generated method stub
				// need code to switch activities

				Editor editor = prefs.edit();
				editor.putInt("rattle_most_recent_score", RATTLE_SCORE);
				editor.commit();
				finished = true;
				

				Intent intent = new Intent(RattleTheCageVideo.this, RattleTheCageScore.class);
				intent.putExtra("score", RATTLE_SCORE);
				startActivity(intent);

			}
		});
		
		

		/*new Handler().postDelayed(new Runnable() 
	       {
	            @Override
	            public void run() {
	                setContentView(R.layout.cage_clues_main); 
	                }
	            }, 9250);*/
	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

}
