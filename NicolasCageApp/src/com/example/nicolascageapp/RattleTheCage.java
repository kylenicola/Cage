package com.example.nicolascageapp;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;

public class RattleTheCage extends Activity {
	
	private SensorManager mSensorManager;
	private ShakeEventListener mSensorListener;
	private SensorEventListener mSensorListener2;
	private SensorEventListener mTouchSensorListener;
	
	// for all the sounds  we play
	private SoundPool mSounds;
	private HashMap<Integer, Integer> mSoundIDMap;
	

	// image going to be drawing
	private MovingCage movingCage;
	
	// to place cage
	private int mSrcWidth;
	private int mSrcHeight;
	
	// to track finger movements
	private VelocityTracker velocity;
	
	// to track total time of finger movements
	private Timer rattleTimer;
	
	@Override 
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rattle_main);
		
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		//get screen dimensions
		Display display = getWindowManager().getDefaultDisplay();  
		mSrcWidth = display.getWidth(); 
		mSrcHeight = display.getHeight();
		
		// make the cage view and place it
		movingCage = new MovingCage(getBaseContext());
		movingCage.setX((mSrcWidth/2) - (movingCage.getImageWidth()/2));
		movingCage.setY((mSrcHeight/2) - (movingCage.getImageHeight()/2));
		
		// get the velocity tracker
		velocity = VelocityTracker.obtain();
		
		// grab the view to setup on touch listener
		final LinearLayout mainView = 
				(android.widget.LinearLayout)findViewById(R.id.rattle_main_view);
		
		mainView.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// get the x,y.  adjust for image height
				float x = event.getAxisValue(MotionEvent.AXIS_X) - (movingCage.getImageWidth() / 2);
				float y = event.getAxisValue(MotionEvent.AXIS_Y) - (movingCage.getImageHeight() / 2);
				long now = System.currentTimeMillis();
				
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					movingCage.onTouch = true;
				}
				else if(event.getAction() == MotionEvent.ACTION_MOVE)
				{
					velocity.addMovement(event);
					velocity.computeCurrentVelocity(10);
					movingCage.setXVelocity(velocity.getXVelocity());
					movingCage.setYVelocity(velocity.getYVelocity());

				}
				else if(event.getAction() == MotionEvent.ACTION_UP)
				{
					movingCage.onTouch = false;
				}
				movingCage.setX(x);
				movingCage.setY(y);

				return true;
			}
			
		});
		
		mainView.addView(movingCage);
		movingCage.invalidate();
		
		rattleTimer = new Timer();
		
		
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		
		
//		mSensorListener2 = new SensorEventListener(){
//
//			
//			@Override
//			public void onSensorChanged(SensorEvent event) {
//				movingCage.setX(movingCage.getX() + (-event.values[0])*2);
//				movingCage.setY(movingCage.getY() + (event.values[1])*2);
//				movingCage.invalidate();
//			}
//
//			@Override
//			public void onAccuracyChanged(Sensor sensor, int accuracy) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		};
	

//		mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {
//			final long sixth_time 	=	13;
//			final long fifth_time 	=	10;
//			final long fourth_time 	=	7;
//			final long third_time 	=	4;
//			final long second_time	=	2;
//			
//			Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//			
//			@Override
//			public void onShake(long totalDuration, boolean hasStopped, float xSpd, float ySpd) {
//				movingCage.setX(movingCage.x + xSpd*100);
//				movingCage.setY(movingCage.y + ySpd*100);
//				movingCage.invalidate();
//				
//				Log.d("is this", "being called?");
//				
////				if(totalDuration > (sixth_time * 1000))
////				{
////					
////					Intent intent = new Intent(RattleTheCage.this, RattleTheCageVideo.class);
////	startActivity(intent);
////				}
////				else if(totalDuration > (fifth_time * 1000))
////				{
////					if(!myImageView.getTag().equals(R.drawable.shakeme_fifth))
////					{
////						if(!hasStopped)
////						{
////							// put sound in here
////							mSounds.play(mSoundIDMap.get(R.raw.yell_four), 1, 1, 1, 0, 1);
////						}
////						myTextView.setText(R.string.rattle_text_pos_5);
////						long[] patterns = {0, 400, 100, 400, 100, 400, 100, 400, 100, 400, 100, 400, 100};
////						myImageView.setImageResource(R.drawable.shakeme_fifth);
////						v.vibrate(patterns, -1);
////						myImageView.setTag(R.drawable.shakeme_fifth);
////					}
////				}
////				else if(totalDuration > (fourth_time * 1000))
////				{
////					if(!myImageView.getTag().equals(R.drawable.shakeme_fourth))
////					{
////						if(!hasStopped)
////						{
////							// put sound in here
////							mSounds.play(mSoundIDMap.get(R.raw.yell_three), 1, 1, 1, 0, 1);
////							myTextView.setText(R.string.rattle_text_pos_4);
////						}
////						else
////						{
////							myTextView.setText(R.string.rattle_text_neg_4);
////						}
////						
////						long[] patterns = {0, 300, 100, 350, 250, 300, 100, 350, 250, 300, 100, 350, 250};
////						myImageView.setImageResource(R.drawable.shakeme_fourth);
////						v.vibrate(patterns, -1);
////						myImageView.setTag(R.drawable.shakeme_fourth);
////					}
////				}
////				else if(totalDuration > (third_time * 1000))
////				{
////					if(!myImageView.getTag().equals(R.drawable.shakeme_third))
////					{
////						if(!hasStopped)
////						{
////							// put sound in here
////							mSounds.play(mSoundIDMap.get(R.raw.yell_two), 1, 1, 1, 0, 1);
////							myTextView.setText(R.string.rattle_text_pos_3);
////						}
////						else
////						{
////							myTextView.setText(R.string.rattle_text_neg_2);
////						}
////						
////						
////						long[] patterns = {0, 220, 100, 230, 500, 220, 100, 230, 500, 220, 100, 230, 350};
////						myImageView.setImageResource(R.drawable.shakeme_third);
////						v.vibrate(patterns, -1);
////						myImageView.setTag(R.drawable.shakeme_third);
////					}
////				}
////				else if(totalDuration > (second_time * 1000))
////				{
////					if(!myImageView.getTag().equals(R.drawable.shakeme_second))
////					{
////						if(!hasStopped)
////						{
////							// put sound in here
////							mSounds.play(mSoundIDMap.get(R.raw.yell_one), 1, 1, 1, 0, 1);
////							myTextView.setText(R.string.rattle_text_pos_2);
////						}
////						else
////						{
////							myTextView.setText(R.string.rattle_text_neg_2);
////						}
////						
////						long[] patterns = {0, 200, 100, 200, 1500};
////						myImageView.setImageResource(R.drawable.shakeme_second);
////						v.vibrate(patterns, -1);
////						myImageView.setTag(R.drawable.shakeme_second);
////					}
////				}
////				else
////				{
////					if(!myImageView.getTag().equals(R.drawable.shakeme_first))
////					{
////						myTextView.setText(R.string.rattle_text_pos_1);
////						myImageView.setImageResource(R.drawable.shakeme_first);
////						myImageView.setTag(R.drawable.shakeme_first);
////					}
////				}
//
//			}
//		});
		
	}
	
	private void createSoundPool() {
		
		// place sound ids here
		int[] soundIds = {R.raw.yell_one, R.raw.yell_two, R.raw.yell_three, R.raw.yell_four};
		
		mSoundIDMap = new HashMap<Integer, Integer>();
		mSounds = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
		for(int id : soundIds) 
			mSoundIDMap.put(id, mSounds.load(this, id, 1));
	}
	
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		createSoundPool();
		//mSensorManager.registerListener(mSensorListener2, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
		//mSensorListener.resetShakeParameters();
	}
	
	@Override
	protected void onPause() 
	{
		//mSensorManager.unregisterListener(mSensorListener2);
		
		if(mSounds != null) {
			mSounds.release();
			mSounds = null;
		}
		super.onPause();
	}
	
	@Override
	protected void onStop()
	{
		//mSensorManager.unregisterListener(mSensorListener2);
		
		rattleTimer.cancel();
		rattleTimer.purge();
		
		if(mSounds != null) {
			mSounds.release();
			mSounds = null;
		}
		super.onStop();
	}
	
	@Override 
	protected void onStart()
	{
		createSoundPool();
		
		rattleTimer.schedule(new TimerTask(){

			@Override
			public void run() {
				//Log.d("is this", "running?");
				//Log.d("is this", "movingCage Velocity: " + String.valueOf(movingCage.getTotalVelocity()));
				//Log.d("is this", "total moving time: " + String.valueOf(movingCage.getTotalTimeMoving()));
				final long sixth_time 	=	13000;
				final long fifth_time 	=	10000;
				final long fourth_time 	=	7000;
				final long third_time 	=	4000;
				final long second_time	=	2000;
				
				long totalTimeMoving = movingCage.getTotalTimeMoving();
				
				Log.d("is this", "totalmoving: " + String.valueOf(totalTimeMoving));
				
				if(totalTimeMoving > sixth_time)
				{
					Intent intent = new Intent(RattleTheCage.this, RattleTheCageVideo.class);
					startActivity(intent);
				}
				else if(totalTimeMoving > fifth_time)
				{
					if(movingCage.getDrawableID() != R.drawable.shakeme_fifth)
					{
						movingCage.setDrawable(R.drawable.shakeme_fifth);
					}
				}
				else if(totalTimeMoving > fourth_time)
				{
					if(movingCage.getDrawableID() != R.drawable.shakeme_fourth)
					{
						movingCage.setDrawable(R.drawable.shakeme_fourth);
					}
				}
				else if(totalTimeMoving > third_time)
				{
					if(movingCage.getDrawableID() != R.drawable.shakeme_third)
					{
						movingCage.setDrawable(R.drawable.shakeme_third);
					}
				}
				else if(totalTimeMoving > second_time)
				{
					if(movingCage.getDrawableID() != R.drawable.shakeme_second)
					{
						movingCage.setDrawable(R.drawable.shakeme_second);
					}
				}				
				else
				{
					if(movingCage.getDrawableID() != R.drawable.shakeme_first)
					{
						movingCage.setDrawable(R.drawable.shakeme_first);
					}
				}
				
				
			}
			
		}, 0, 1000);
		//mSensorManager.registerListener(mSensorListener2, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
		//mSensorListener.resetShakeParameters();
		super.onStart();
	}
	


}
