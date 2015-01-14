package com.example.nicolascageapp;

import android.hardware.SensorEventListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.util.Log;

public class ShakeEventListener implements SensorEventListener {

	private static final int MIN_FORCE = 10;
	
	private long mFirstDirectionChangeTime = 0;
	
	private long mLastDirectionChangeTime = 0;
	
	public long mTotalDuration = 0;
	
	private float lastX = 0;
	
	private float lastY = 0;
	
	private float lastZ = 0;
	
	private OnShakeListener mShakeListener;
	
	public interface OnShakeListener {
		void onShake(long totalDuration, boolean hasStopped, float xSpd, float ySpd);
	}
	
	public void setOnShakeListener(OnShakeListener listener)
	{
		mShakeListener = listener;
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		
		float x = event.values[SensorManager.DATA_X];
		float y = event.values[SensorManager.DATA_Y];
		float z = event.values[SensorManager.DATA_Z];
		
		float x_tilt = -event.values[0];
		float y_tilt = event.values[1];
		
		float totalMovement = Math.abs(x + y + z - lastX - lastY - lastZ);
		
		if (totalMovement > MIN_FORCE)
		{
			long now = System.currentTimeMillis();
			
			if(mFirstDirectionChangeTime == 0)
			{
				mFirstDirectionChangeTime = now;
				mLastDirectionChangeTime = now;
			}
			
			long lastChangeWasAgo = now - mLastDirectionChangeTime;
			
			//if (lastChangeWasAgo < MAX_PAUSE_BETWEEN_DIRECTION_CHANGE)
			//{
				mLastDirectionChangeTime = now;
				
				lastX = x;
				lastY = y;
				lastZ = z;
				
				mTotalDuration = now - mFirstDirectionChangeTime;

				mShakeListener.onShake(mTotalDuration, false, x_tilt, y_tilt);
			//}
		} 
		else 
		{
			long now = System.currentTimeMillis();
			long wait_time = 2;
			
			if(mFirstDirectionChangeTime == 0)
			{
				mFirstDirectionChangeTime = now;
				mLastDirectionChangeTime = now;
			}
			
			if((now - mLastDirectionChangeTime) > wait_time * 1000)
			{
				mLastDirectionChangeTime = now;
				mFirstDirectionChangeTime = mFirstDirectionChangeTime + wait_time * 2000 < now ? mFirstDirectionChangeTime + wait_time * 2000 : now;
				mTotalDuration = now - mFirstDirectionChangeTime;
				mShakeListener.onShake(mTotalDuration, true, x_tilt, y_tilt);
			}
//			resetShakeParameters();
//			mShakeListener.onShake(mDirectionChangeCount, true);
		}
		
	}
	
	public void resetShakeParameters()
	{
		mFirstDirectionChangeTime = 0;
		mLastDirectionChangeTime = 0;
		mTotalDuration = 0;
		lastX = 0;
		lastY = 0;
		lastZ = 0;
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

}
