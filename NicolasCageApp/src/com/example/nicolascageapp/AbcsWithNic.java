package com.example.nicolascageapp;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
	   
	   private void fillLetterTimes()
	   {		   		   
		   assert(times.length == letters.length && times.length == 26);
		   for(int i = 0; i < NUM_LETTERS; i++)
		   {
			   letterTimes.put(letters[i], times[i]);
		   }		   
	   }
	   
	   private void fillClicked()
	   {
		   for(int i = 0; i < NUM_LETTERS; i++)
		   {
			   letterClicked.put(letters[i], false);
		   }
	   }
	   
	   
	   public void onLetterClick(View v)
	   {
		   int letter = v.getId();
		   int score = 0;
		   differenceTime = Math.abs((System.currentTimeMillis() - startTime) - letterTimes.get(letter));

		   if(differenceTime < 500)
		   {
			   score = 100;
		   }
		   else if(differenceTime < 1000)
		   {
			   score = 50;
		   }
		   else
		   {
			   score = -25;
		   }

		   // do something with score
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

	       
	       
	       
	       final Button keyA = (Button) findViewById(R.id.ButtonA);
	         keyA.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long aTime = System.currentTimeMillis();
	                
	                if (startTime - aTime < 2000)
	                {
	                	playerScore += 100;
	                }
	                else if (startTime - aTime <= 1600)
	                {
	
	                	playerScore += 50;
	                }
	             }
	         });
	         final Button keyB = (Button) findViewById(R.id.ButtonB);
	         keyB.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long bTime = System.currentTimeMillis();
	                if (startTime - bTime <= 2200)
	                {
	                	playerScore += 100;
	                }
	                else if (startTime - bTime  <= 2300)
	                {
	                	playerScore += 50;
	                }
	             }
	         });
	         final Button keyC = (Button) findViewById(R.id.ButtonC);
	         keyC.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long cTime = System.currentTimeMillis();
	                if (startTime - cTime <= 2300)
	                {
	                	playerScore += 100;
	                	
	                }
	                else if (startTime - cTime <= 2400)
	                {
	                	playerScore += 50;
	                }
	             }
	         });
	         final Button keyD = (Button) findViewById(R.id.ButtonD);
	         keyD.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long dTime = System.currentTimeMillis();
	                if (startTime - dTime <= 2450)
	                {
	                	playerScore += 100;
	                }
	                else if (startTime - dTime <= 2550)
	                {
	                	playerScore += 50;
	                }
	             }
	         });
	         final Button keyE = (Button) findViewById(R.id.ButtonE);
	         keyE.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long eTime = System.currentTimeMillis();
	                if (startTime - eTime <= 2500)
	                {
	                	playerScore += 100;
	                }
	                else if (startTime - eTime <= 2600)
	                {
	                	playerScore += 50;
	                }
	             }
	         });
	         final Button keyF = (Button) findViewById(R.id.ButtonF);
	         keyF.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long fTime = System.currentTimeMillis();
	                if (startTime - fTime <= 3400)
	                {
	                	playerScore += 100;
	                }
	                else if (startTime - fTime <= 3500)
	                {
	                	playerScore += 50;
	                }
	             }
	         });
	         final Button keyG = (Button) findViewById(R.id.ButtonG);
	         keyG.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long gTime = System.currentTimeMillis();
	                if (startTime - gTime <= 3300)
	                {
	                	playerScore += 100;
	                }
	                else if (startTime - gTime <= 3400)
	                {
	                	playerScore += 50;
	                }
	             }
	         });
	         final Button keyH = (Button) findViewById(R.id.ButtonH);
	         keyH.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long hTime = System.currentTimeMillis();
	                if (startTime - hTime <= 3450)
	                {
	                	playerScore += 100;
	                }
	                else if (startTime - hTime <= 3550)
	                {
	                	playerScore += 50;
	                }
	             }
	         });
	         final Button keyI = (Button) findViewById(R.id.ButtonI);
	         keyI.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long iTime = System.currentTimeMillis();
	                if (startTime - iTime <= 3550)
	                {
	                	playerScore += 100;
	                }
	                else if (startTime - iTime <= 3650)
	                {
	                	playerScore += 50;
	                }
	             }
	         });
	         final Button keyJ = (Button) findViewById(R.id.ButtonJ);
	         keyJ.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long jTime = System.currentTimeMillis();
	                if (startTime - jTime <= 4200)
	                {
	                	playerScore += 100;
	                }
	                else if (startTime - jTime <= 4300)
	                {
	                	playerScore += 50;
	                }
	             }
	         });
	         final Button keyK = (Button) findViewById(R.id.ButtonK);
	         keyK.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long kTime = System.currentTimeMillis();
	                if (startTime - kTime <= 4350)
	                {
	                	playerScore += 100;
	                }
	                else if (startTime - kTime <= 4450)
	                {
	                	playerScore += 50;
	                }
	             }
	         });
	         final Button keyL = (Button) findViewById(R.id.ButtonL);
	         keyL.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long lTime = System.currentTimeMillis();
	                if (startTime - lTime <= 4400)
	                {
	                	playerScore += 100;
	                }
	                else if (startTime - lTime <= 4500)
	                {
	                	playerScore += 50;
	                }
	             }
	         });
	         final Button keyM = (Button) findViewById(R.id.ButtonM);
	         keyM.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long mTime = System.currentTimeMillis();
	                if (startTime - mTime <= 4450)
	                {
	                	playerScore += 100;
	                }
	                else if (startTime - mTime <= 4550)
	                {
	                	playerScore += 50;
	                }
	             }
	         });
	         final Button keyN = (Button) findViewById(R.id.ButtonN);
	         keyN.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long nTime = System.currentTimeMillis();
	                if (startTime - nTime <= 4500)
	                {
	                	playerScore += 100;
	                }
	                else if (startTime - nTime <= 4600)
	                {
	                	playerScore += 50;
	                }
	             }
	         });
	         final Button keyO = (Button) findViewById(R.id.ButtonO);
	         keyO.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long oTime = System.currentTimeMillis();
	                if (startTime - oTime <= 4750)
	                {
	                	playerScore += 100;
	                }
	                else if (startTime - oTime <= 4850)
	                {
	                	playerScore += 50;
	                }
	             }
	         });
	         final Button keyP = (Button) findViewById(R.id.ButtonP);
	         keyP.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long pTime = System.currentTimeMillis();
	                if (startTime - pTime <= 5200)
	                {
	                	playerScore += 100;
	                }
	                else if (startTime - pTime <= 5300)
	                {
	                	playerScore += 50;
	                }
	             }
	         });
	         final Button keyQ = (Button) findViewById(R.id.ButtonQ);
	         keyQ.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long qTime = System.currentTimeMillis();
	                if (startTime - qTime <= 5300)
	                {
	                	playerScore += 100;
	                }
	                else if (startTime - qTime <= 5400)
	                {
	                	playerScore += 50;
	                }
	             }
	         });
	         final Button keyR = (Button) findViewById(R.id.ButtonR);
	         keyR.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long rTime = System.currentTimeMillis();
	                if (startTime - rTime <= 5400)
	                {
	                	playerScore += 100;
	                }
	                else if (startTime - rTime <= 5500)
	                {
	                	playerScore += 50;
	                }
	             }
	         });
	         final Button keyS = (Button) findViewById(R.id.ButtonS);
	         keyS.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long sTime = System.currentTimeMillis();
	                if (startTime - sTime <= 5500)
	                {
	                	playerScore += 100;
	                }
	                else if (startTime - sTime <= 5600)
	                {
	                	playerScore += 50;
	                }
	             }
	         });
	         final Button keyT = (Button) findViewById(R.id.ButtonT);
	         keyT.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long tTime = System.currentTimeMillis();
	                if (startTime - tTime <= 6200)
	                {
	                	playerScore += 100;
	                }
	                else if (startTime - tTime <= 6300)
	                {
	                	playerScore += 50;
	                }
	             }
	         });
	         final Button keyU = (Button) findViewById(R.id.ButtonU);
	         keyU.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long uTime = System.currentTimeMillis();
	                if (startTime - uTime <= 6300)
	                {
	                	playerScore += 100;
	                }
	                else if (startTime - uTime <= 6400)
	                {
	                	playerScore += 50;
	                }
	             }
	         });
	         final Button keyV = (Button) findViewById(R.id.ButtonV);
	         keyV.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long vTime = System.currentTimeMillis();
	                if (startTime - vTime <= 6350)
	                {
	                	playerScore += 100;
	                }
	                else if (startTime - vTime <= 6450)
	                {
	                	playerScore += 50;
	                }
	             }
	         });
	         final Button keyW = (Button) findViewById(R.id.ButtonW);
	         keyW.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long wTime = System.currentTimeMillis();
	                if (startTime - wTime <= 6400)
	                {
	                	playerScore += 100;
	                }
	                else if (startTime - wTime <= 6500)
	                {
	                	playerScore += 50;
	                }
	             }
	         });
	         final Button keyX= (Button) findViewById(R.id.ButtonX);
	         keyX.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long xTime = System.currentTimeMillis();
	                if (startTime - xTime <= 6500)
	                {
	                	playerScore += 100;
	                }
	                else if (startTime - xTime <= 6600)
	                {
	                	playerScore += 50;
	                }
	             }
	         });
	         final Button keyY = (Button) findViewById(R.id.ButtonY);
	         keyY.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long yTime = System.currentTimeMillis();
	                if (startTime - yTime <= 7150)
	                {
	                	playerScore += 100;
	                }
	                else if (startTime - yTime <= 7350)
	                {
	                	playerScore += 50;
	                }
	             }
	         });
	         final Button keyZ = (Button) findViewById(R.id.ButtonZ);
	         keyZ.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long zTime = System.currentTimeMillis();
	                if (startTime - zTime <= 7200)
	                {
	                	playerScore += 100;
	                }
	                else if (startTime - zTime <= 7300)
	                {
	                	playerScore += 50;
	                }
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

