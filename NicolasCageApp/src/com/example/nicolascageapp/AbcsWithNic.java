package com.example.nicolascageapp;
import java.io.File;

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
	       final long startTime = System.nanoTime();
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
	                
	                if (aTime - startTime <= 2000)
	                {
	                	playerScore += 100;
	                }
	                else if (aTime-startTime <= 1600)
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
	                if (bTime - startTime  <= 2200)
	                {
	                	playerScore += 100;
	                }
	                else if (bTime - startTime  <= 2300)
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
	                if (cTime - startTime <= 2300)
	                {
	                	playerScore += 100;
	                	
	                }
	                else if (cTime - startTime <= 2400)
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
	                if (dTime - startTime <= 2450)
	                {
	                	playerScore += 100;
	                }
	                else if (dTime - startTime <= 2550)
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
	                if (eTime - startTime <= 2500)
	                {
	                	playerScore += 100;
	                }
	                else if (eTime - startTime <= 2600)
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
	                if (fTime - startTime <= 3400)
	                {
	                	playerScore += 100;
	                }
	                else if (fTime - startTime <= 3500)
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
	                if (gTime - startTime <= 3300)
	                {
	                	playerScore += 100;
	                }
	                else if (gTime - startTime <= 3400)
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
	                if (hTime - startTime <= 3450)
	                {
	                	playerScore += 100;
	                }
	                else if (hTime - startTime <= 3550)
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
	                if (iTime - startTime <= 3550)
	                {
	                	playerScore += 100;
	                }
	                else if (iTime - startTime <= 3650)
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
	                if (jTime - startTime <= 4200)
	                {
	                	playerScore += 100;
	                }
	                else if (jTime - startTime <= 4300)
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
	                if (kTime - startTime <= 4350)
	                {
	                	playerScore += 100;
	                }
	                else if (kTime - startTime <= 4450)
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
	                if (lTime - startTime <= 4400)
	                {
	                	playerScore += 100;
	                }
	                else if (lTime - startTime <= 4500)
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
	                if (mTime - startTime <= 4450)
	                {
	                	playerScore += 100;
	                }
	                else if (mTime - startTime <= 4550)
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
	                if (nTime - startTime <= 4500)
	                {
	                	playerScore += 100;
	                }
	                else if (nTime - startTime <= 4600)
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
	                if (oTime - startTime <= 4750)
	                {
	                	playerScore += 100;
	                }
	                else if (oTime - startTime <= 4850)
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
	                if (pTime - startTime <= 5200)
	                {
	                	playerScore += 100;
	                }
	                else if (pTime - startTime <= 5300)
	                {
	                	playerScore += 50;
	                }
	             }
	         });
	         final Button keyQ = (Button) findViewById(R.id.buttonQ);
	         keyQ.setOnClickListener(new View.OnClickListener() 
	         {
	             public void onClick(View v) 
	             {
	                long qTime = System.currentTimeMillis();
	                if (qTime - startTime <= 5300)
	                {
	                	playerScore += 100;
	                }
	                else if (qTime - startTime <= 5400)
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
	                if (rTime - startTime <= 5400)
	                {
	                	playerScore += 100;
	                }
	                else if (rTime - startTime <= 5500)
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
	                if (sTime - startTime <= 5500)
	                {
	                	playerScore += 100;
	                }
	                else if (sTime - startTime <= 5600)
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
	                if (tTime - startTime <= 6200)
	                {
	                	playerScore += 100;
	                }
	                else if (tTime - startTime <= 6300)
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
	                if (uTime - startTime <= 6300)
	                {
	                	playerScore += 100;
	                }
	                else if (uTime - startTime <= 6400)
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
	                if (vTime - startTime <= 6350)
	                {
	                	playerScore += 100;
	                }
	                else if (vTime - startTime <= 6450)
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
	                if (wTime - startTime <= 6400)
	                {
	                	playerScore += 100;
	                }
	                else if (wTime - startTime <= 6500)
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
	                if (xTime - startTime <= 6500)
	                {
	                	playerScore += 100;
	                }
	                else if (xTime - startTime <= 6600)
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
	                if (yTime - startTime <= 7150)
	                {
	                	playerScore += 100;
	                }
	                else if (yTime - startTime <= 7350)
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
	                if (zTime - startTime <= 7200)
	                {
	                	playerScore += 100;
	                }
	                else if (zTime - startTime <= 7300)
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
	   
	   public void backToMain(View view) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
	   }
	   
}



