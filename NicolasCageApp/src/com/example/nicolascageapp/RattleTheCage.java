package com.example.nicolascageapp;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Vibrator;

public class RattleTheCage extends Activity {
	
	private SensorManager mSensorManager;
	private ShakeEventListener mSensorListener;	
	
	// for all the sounds  we play
	private SoundPool mSounds;
	private HashMap<Integer, Integer> mSoundIDMap;
	
	
	
	@Override 
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rattle_main);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensorListener = new ShakeEventListener();
		ImageView myImageView = (ImageView) findViewById(R.id.rattleImage);
		myImageView.setImageResource(R.drawable.shakeme_first);
		myImageView.setTag(R.drawable.shakeme_first);
		
		mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {
			
			@Override
			public void onShake(long totalDuration, boolean hasStopped) {
				ImageView myImageView = (ImageView) findViewById(R.id.rattleImage);
				TextView myTextView = (TextView) findViewById(R.id.rattleText);
				Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				
				long sixth_time = 13;
				long fifth_time = 10;
				long fourth_time = 7;
				long third_time = 4;
				long second_time = 2;
				
				

				if(totalDuration > (sixth_time * 1000))
				{
					
					Intent intent = new Intent(RattleTheCage.this, RattleTheCageVideo.class);
					startActivity(intent);
				}
				else if(totalDuration > (fifth_time * 1000))
				{
					if(!myImageView.getTag().equals(R.drawable.shakeme_fifth))
					{
						if(!hasStopped)
						{
							// put sound in here
							mSounds.play(mSoundIDMap.get(R.raw.yell_four), 1, 1, 1, 0, 1);
						}
						myTextView.setText(R.string.rattle_text_pos_5);
						long[] patterns = {0, 400, 100, 400, 100, 400, 100, 400, 100, 400, 100, 400, 100};
						myImageView.setImageResource(R.drawable.shakeme_fifth);
						v.vibrate(patterns, -1);
						myImageView.setTag(R.drawable.shakeme_fifth);
					}
				}
				else if(totalDuration > (fourth_time * 1000))
				{
					if(!myImageView.getTag().equals(R.drawable.shakeme_fourth))
					{
						if(!hasStopped)
						{
							// put sound in here
							mSounds.play(mSoundIDMap.get(R.raw.yell_three), 1, 1, 1, 0, 1);
							myTextView.setText(R.string.rattle_text_pos_4);
						}
						else
						{
							myTextView.setText(R.string.rattle_text_neg_4);
						}
						
						long[] patterns = {0, 300, 100, 350, 250, 300, 100, 350, 250, 300, 100, 350, 250};
						myImageView.setImageResource(R.drawable.shakeme_fourth);
						v.vibrate(patterns, -1);
						myImageView.setTag(R.drawable.shakeme_fourth);
					}
				}
				else if(totalDuration > (third_time * 1000))
				{
					if(!myImageView.getTag().equals(R.drawable.shakeme_third))
					{
						if(!hasStopped)
						{
							// put sound in here
							mSounds.play(mSoundIDMap.get(R.raw.yell_two), 1, 1, 1, 0, 1);
							myTextView.setText(R.string.rattle_text_pos_3);
						}
						else
						{
							myTextView.setText(R.string.rattle_text_neg_2);
						}
						
						
						long[] patterns = {0, 220, 100, 230, 500, 220, 100, 230, 500, 220, 100, 230, 350};
						myImageView.setImageResource(R.drawable.shakeme_third);
						v.vibrate(patterns, -1);
						myImageView.setTag(R.drawable.shakeme_third);
					}
				}
				else if(totalDuration > (second_time * 1000))
				{
					if(!myImageView.getTag().equals(R.drawable.shakeme_second))
					{
						if(!hasStopped)
						{
							// put sound in here
							mSounds.play(mSoundIDMap.get(R.raw.yell_one), 1, 1, 1, 0, 1);
							myTextView.setText(R.string.rattle_text_pos_2);
						}
						else
						{
							myTextView.setText(R.string.rattle_text_neg_2);
						}
						
						long[] patterns = {0, 200, 100, 200, 1500};
						myImageView.setImageResource(R.drawable.shakeme_second);
						v.vibrate(patterns, -1);
						myImageView.setTag(R.drawable.shakeme_second);
					}
				}
				else
				{
					if(!myImageView.getTag().equals(R.drawable.shakeme_first))
					{
						myTextView.setText(R.string.rattle_text_pos_1);
						myImageView.setImageResource(R.drawable.shakeme_first);
						myImageView.setTag(R.drawable.shakeme_first);
					}
				}

			}
		});
		
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
		mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
		mSensorListener.resetShakeParameters();
	}
	
	@Override
	protected void onPause() 
	{
		mSensorManager.unregisterListener(mSensorListener);
		
		if(mSounds != null) {
			mSounds.release();
			mSounds = null;
		}
		super.onPause();
	}
	
	@Override
	protected void onStop()
	{
		mSensorManager.unregisterListener(mSensorListener);
		
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
		mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
		mSensorListener.resetShakeParameters();
		super.onStart();
	}
	


}
