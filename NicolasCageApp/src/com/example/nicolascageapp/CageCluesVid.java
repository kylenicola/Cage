package com.example.nicolascageapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;
 

public class CageCluesVid extends Activity
{
	VideoView myVideoView;
	 
	   /** Called when the activity is first created. */
	   @Override
	   public void onCreate(Bundle savedInstanceState) 
	   {
	       super.onCreate(savedInstanceState);
	      
	       setContentView(R.layout.cage_clues);
	       getActionBar().setDisplayHomeAsUpEnabled(true);
	       
	       myVideoView = (VideoView) findViewById(R.id.cage_clues_video);
	       myVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.i_lost_my_hand2));
	       myVideoView.start();
	   }
	   
	   public void moveToQuiz(View view) {
			Intent intent = new Intent(this, CageClues.class);
			startActivity(intent);
	   }
}
