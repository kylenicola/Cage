package com.example.nicolascageapp;

import java.util.HashMap;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
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
import android.widget.RelativeLayout;

public class RattleTheCage extends Activity {
	
	private final static String TAG = "RattleTheCage";

	// for shake listener
	private SensorManager mSensorManager;
	private RattleTheCageShakeListener mShakeListener;
	private boolean mShakeOn;

	// for all the sounds  we play
	private SoundPool mSounds;
	private HashMap<Integer, Integer> mSoundIDMap;

	// image going to be drawing
	private MovingCage movingCage;

	// to place cage initially
	private int mSrcWidth;
	private int mSrcHeight;

	// to track finger movements
	private VelocityTracker velocity;
	
	// to track total time of finger movements
	private Timer rattleTimer;

	// for BEEEEEEEESSSS
	private Stack<MovingBee> beeStack;
	private int beeStackSize;
	
	public RelativeLayout mainView;

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
	
		// set up BeeStack
		beeStack = new Stack<MovingBee>();
		beeStackSize = 0;

		// grab the view to setup on touch listener
		mainView = 
				(android.widget.RelativeLayout)findViewById(R.id.rattle_main_view);

		// touch listener
		mainView.setOnTouchListener(new RattleTheCageTouchListener());
		
		// shake listener
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mShakeListener = new RattleTheCageShakeListener();
		mShakeOn = false;

		mainView.addView(movingCage);
		movingCage.invalidate();

		rattleTimer = new Timer();





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
		
		if(mShakeOn)
		{
			mSensorManager.registerListener(mShakeListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
		}
	}

	@Override
	protected void onPause() 
	{
		if(mShakeOn)
		{
			mSensorManager.unregisterListener(mShakeListener);
		}

		if(mSounds != null) {
			mSounds.release();
			mSounds = null;
		}
		super.onPause();
	}

	@Override
	protected void onStop()
	{
		if(mShakeOn)
		{
			mSensorManager.unregisterListener(mShakeListener);
		}
		
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

		rattleTimer = new Timer();
		rattleTimer.schedule(new ChangeCageTask(), 0, 1000);
		
		
		// turn on shake listener
		if(mShakeOn)
		{
			mSensorManager.registerListener(mShakeListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
		}
		
		// turn on touch listener
		super.onStart();
	}

	// If the amount of time MovingCage has been moving at minimum velocity has crossed
	// a threshold this updates MovingCage's drawable, vibrates, and plays a sound.
	public void updateMovingCage(int drawable, int soundID, long[] patterns, int beeAmount)
	{
		if(movingCage.getDrawableID() != drawable)
		{
			if(!movingCage.isSlowing && soundID != -1)
			{
				mSounds.play(mSoundIDMap.get(soundID), 1, 1, 1, 0, 1);
			}
			movingCage.setDrawable(drawable);
			if(beeStackSize != beeAmount)
			{

				int count = beeAmount - beeStackSize;
				if(count > 0)
				{
					for(int x = 0; x < count; x++)
					{
						final MovingBee movingBee = new MovingBee(getBaseContext());
						RattleTheCage.this.runOnUiThread(new Runnable(){

							@Override
							public void run() {
								mainView.addView(movingBee);
							}
							
						});
						
						beeStack.push(movingBee);
						beeStackSize++;
					}
				}
				else
				{
					for(int x = count; x < 0; x++)
					{
						RattleTheCage.this.runOnUiThread(new Runnable(){

							@Override
							public void run() {
								mainView.removeView(beeStack.pop());							
							}
						});
						beeStackSize--;
					}
				}
			}
		}
	}


	// Responsible for checking the total time moving
	// and then doing the calling updateMovingCage with the correct 
	// parameters
	private class ChangeCageTask extends TimerTask
	{
		// Times for changes in milliseconds
		final long sixth_time 	=	1000	*	13;
		final long fifth_time 	=	1000	*	10;
		final long fourth_time 	=	1000	*	7;
		final long third_time 	=	1000	*	4;
		final long second_time	=	1000	*	2;
		
		// Number of bees
		final int bee_count_first	=	0;
		final int bee_count_second 	=	1;
		final int bee_count_third 	=	5;
		final int bee_count_fourth	=	12;
		final int bee_count_fifth 	=	40;
				

		@Override
		public void run() {
			long totalTimeMoving = movingCage.getTotalTimeMoving();

			if(totalTimeMoving > sixth_time)
			{
				Log.d(TAG, "sixth time called.");
				
				// to store time
				updatePrefs();
				
				// move to next activity
				Intent intent = new Intent(RattleTheCage.this, RattleTheCageVideo.class);
				startActivity(intent);
			}
			else if(totalTimeMoving > fifth_time)
			{
				long[] patterns = {0, 400, 100, 400, 100, 400, 100, 400, 100, 400, 100, 400, 100};
				updateMovingCage(R.drawable.shakeme_fifth, R.raw.yell_four, patterns, bee_count_fifth);
			}
			else if(totalTimeMoving > fourth_time)
			{
				long[] patterns = {0, 300, 100, 350, 250, 300, 100, 350, 250, 300, 100, 350, 250};
				updateMovingCage(R.drawable.shakeme_fourth, R.raw.yell_three, patterns, bee_count_fourth);
			}
			else if(totalTimeMoving > third_time)
			{
				long[] patterns = {0, 220, 100, 230, 500, 220, 100, 230, 500, 220, 100, 230, 350};
				updateMovingCage(R.drawable.shakeme_third, R.raw.yell_two, patterns, bee_count_third);
			}
			else if(totalTimeMoving > second_time)
			{
				long[] patterns = {0, 200, 100, 200, 1500};
				updateMovingCage(R.drawable.shakeme_second, R.raw.yell_one, patterns, bee_count_second);
			}				
			else
			{
				updateMovingCage(R.drawable.shakeme_first, -1, null, bee_count_first);
			}
		}
	}
	
	private class RattleTheCageShakeListener implements SensorEventListener
	{
		private float lastX = 0;
		private float lastY = 0;
		private float lastZ = 0;
		float lastXVelocity = 0;
		float lastYVelocity = 0;
		
		final float VELOCITY_MULTIPLIER = 20;
		
		private long lastTime = -1;
		
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			float x = event.values[SensorManager.DATA_X];
			float y = event.values[SensorManager.DATA_Y];
			float z = event.values[SensorManager.DATA_Z];
			
			
			
			long now = System.currentTimeMillis();
			
			if(lastTime != -1)
			{
				long diffTime = now - lastTime;
				float xVelocity = (x-lastX)	*	VELOCITY_MULTIPLIER;
				float yVelocity = (y-lastY)	*	VELOCITY_MULTIPLIER * (float) 1.5;
				float zVelocity = (z-lastZ)	*	VELOCITY_MULTIPLIER;
				
			
				
				//Log.d("test", "x vel: " + String.valueOf(xVelocity));
				Log.d("test", "y vel: " + String.valueOf(yVelocity));
				//Log.d("test", "z vel: " + String.valueOf(zVelocity));
				if(Math.abs(xVelocity) > 20)
				{
					if((xVelocity > 0 && lastXVelocity > 0) || (xVelocity < 0 && lastXVelocity < 0))
					{
						if(Math.abs(xVelocity) > Math.abs(lastXVelocity))
						{
							movingCage.setXVelocity(xVelocity);
							lastXVelocity = xVelocity;
						}
					}
					else
					{
						movingCage.setXVelocity(xVelocity);
						lastXVelocity = xVelocity;
					}
				}
				if(Math.abs(yVelocity) > 30)
				{
					if((yVelocity > 0 && lastYVelocity > 0) || (yVelocity < 0 && lastYVelocity < 0))
					{
						if(Math.abs(yVelocity) > Math.abs(lastYVelocity))
						{
							movingCage.setYVelocity(yVelocity);
							lastYVelocity = yVelocity;
						}
					}
					else
					{
						movingCage.setYVelocity(yVelocity);
						lastYVelocity = yVelocity;
					}
				}
			}
			lastX = x;
			lastY = y;
			lastZ = z;
			
			lastTime = now;
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class RattleTheCageTouchListener implements OnTouchListener
	{
		@Override
		public boolean onTouch(View v, MotionEvent event) {

			// get the x,y.  adjust for image height
			float x = event.getAxisValue(MotionEvent.AXIS_X) - (movingCage.getImageWidth() / 2);
			float y = event.getAxisValue(MotionEvent.AXIS_Y) - (movingCage.getImageHeight() / 2);
			
			Log.d("touch listener going", "for realz");

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
	}

	private void updatePrefs()
	{
		Log.d(TAG, "prefs called");
		SharedPreferences prefs = this.getSharedPreferences("mPrefs", 0);
		long curBestTime = prefs.getLong(Stats.RATTLETHECAGE_BEST_TIME, 0);
		long time = movingCage.getEntireTotalTime();
		Editor ed = prefs.edit();
		Log.d(TAG, "time: " + String.valueOf(time));
		Log.d(TAG, "Current best time: " + String.valueOf(curBestTime));
		if(curBestTime > time || curBestTime == 0)
		{
			ed.putLong(Stats.RATTLETHECAGE_BEST_TIME, time);
			ed.putInt(Stats.RESULT, Stats.BETTER);
			
		}
		else if(curBestTime == time)
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
