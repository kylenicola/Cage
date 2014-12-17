package com.example.nicolascageapp;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
	   
	   private long startTime = 0;
	   private long differenceTime = 0;
	   
	   private Map<Integer, Integer> letterTimes = new HashMap<Integer, Integer>();
	   private Map<Integer, Boolean> letterClicked = new HashMap<Integer, Boolean>();
	   
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
		   int letter = v.getId();
		   long current = System.currentTimeMillis();
		   int score = 0;
		   differenceTime = Math.abs((current - startTime) - letterTimes.get(letter));

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
		   

		   Log.d("abcs", "Start time: " + String.valueOf(startTime));
		   Log.d("abcs", "Current time: " + String.valueOf(current));
		   Log.d("abcs", "Letter time: " + String.valueOf(letterTimes.get(letter)));
		   Log.d("abcs", "letter A: " + String.valueOf(R.id.ButtonA));
		   Log.d("abcs", "letter pressed: " + String.valueOf(letter));
		   Log.d("abcs", "abc_times TimeA: " + String.valueOf(R.integer.TimeA));
		   Log.d("abcs", "times[0] time: " + String.valueOf(times[0]));
		   // update player score
		   playerScore += score;
		   
		   // change a text to show score
	   }
	 
	   /** Called when the activity is first created. */
	   @Override
	   public void onCreate(Bundle savedInstanceState) 
	   {
	       super.onCreate(savedInstanceState);
	      
	       setContentView(R.layout.activity_abcs_with_nic);
	       getActionBar().setDisplayHomeAsUpEnabled(true);
	       
	       final SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
	       
	       myVideoView = (VideoView)findViewById(R.id.abcs_videoView);
	       myVideoView.setMediaController(new MediaController(this));
	       myVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.abcs_video));
	       myVideoView.start();
	       
	       startTime = System.currentTimeMillis();
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
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
	   }
}

