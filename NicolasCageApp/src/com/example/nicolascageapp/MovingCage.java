package com.example.nicolascageapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MovingCage extends View {
	
	// x, y pos
	private float x;
	private float y;
	
	// x, y velocities
	private float x_velocity;
	private float y_velocity;
	
	// minimum velocity to increase time
	private final float MIN_VELOCITY = 50;
	
	// velocity decrease
	private final double VEL_DECREASE = -.9;
	
	// times used while moving
	private long mStartTime;
	private long mLastVelocityOkayTime;
	private long mTotalDuration;
	private final long WAIT_TIME = 1500;
	
	// times used to calculate entire duration, not just duration moving
	private long mEntireStartTime;
	private long mEntireTotalTime;
	
	// If cage is slowing his roll
	public boolean isSlowing;
	
	// Handler
	private Handler h;
	
	// Frame rate
	private int FRAME_RATE = 30;
	
	public boolean onTouch;
	
	// paint
	private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	// drawable id
	private int drawable_id = R.drawable.shakeme_first;
	private Bitmap drawable_bitmap;
	
	// Vibrator for when it hits a wall
	private Vibrator vibrator;
	

	public MovingCage(Context context) {
		super(context);
		
		this.drawable_bitmap = BitmapFactory.decodeResource(getResources(), drawable_id);
		this.x = (this.getWidth()/2) - (this.drawable_bitmap.getWidth() / 2);
		this.y = (this.getHeight()/2) - (this.drawable_bitmap.getHeight() / 2);
		this.x_velocity = 0;
		this.y_velocity = 0;
		this.onTouch = false;
		mPaint.setColor(0xFF00FF00);
		this.h = new Handler();
		this.mStartTime = 0;
		this.mTotalDuration = 2;
		this.isSlowing = false;
		vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
		
		mEntireStartTime = -1;
		mEntireTotalTime = 0;
		
	}
	
	public void setXVelocity(float x_velocity)
	{
		this.x_velocity = x_velocity;
	}
	
	public void setYVelocity(float y_velocity)
	{
		this.y_velocity = y_velocity;
	}
	
	// used to calculate if Nic is moving fast enough.
	public float getTotalVelocity()
	{
		return (float) Math.sqrt(Math.pow(x_velocity, 2) + Math.pow(y_velocity, 2));
	}
	
	
	// This is for the runnable to know when to change images, play sounds, and
	// move to next intent
	public long getTotalTimeMoving()
	{
		return mTotalDuration;
	}
	
	public long getEntireTotalTime()
	{
		return mEntireTotalTime;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	
	public void setY(float y)
	{
		this.y = y;
	}
	
	public float getX()
	{
		return this.x;
	}
	
	public float getY()
	{
		return this.y;
	}
	
	public float getImageWidth()
	{
		return this.drawable_bitmap.getWidth();
	}
	
	public float getImageHeight()
	{
		return this.drawable_bitmap.getHeight();
	}
	
	// sets new drawable and converts to bitmap
	public void setDrawable(int new_drawable_id)
	{
		this.drawable_id = new_drawable_id;
		this.drawable_bitmap = BitmapFactory.decodeResource(getResources(), this.drawable_id);
	}
	
	// gets drawable id
	public int getDrawableID()
	{
		return this.drawable_id;
	}
	
	private Runnable r = new Runnable()
	{

		@Override
		public void run() {
			invalidate();
			
		}
		
	};
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		long now = System.currentTimeMillis();
		if(mStartTime == 0)
		{
			mStartTime = now;
		}
		if(mEntireStartTime < 0)
		{
			mEntireStartTime = now;
		}
		
		if(!onTouch)
		{
			x += x_velocity;
			y += y_velocity;
		}
		
		if(x < 0 || x + (getImageWidth()) > this.getWidth() || y < 0 || y  + (getImageHeight()) > this.getHeight())
		{
			vibrator.vibrate(20);
		}
		
		if(x < 0)
		{
			x = 1;
			x_velocity *= VEL_DECREASE;
		}
		if(x + (getImageWidth()) > this.getWidth())
		{
			x = this.getWidth() - getImageWidth();
			x_velocity *= VEL_DECREASE;
		}
		if(y < 0)
		{
			y = 1;
			y_velocity *= VEL_DECREASE;
		}
		if(y  + (getImageHeight()) > this.getHeight())
		{
			y = this.getHeight() - getImageHeight();
			y_velocity *= VEL_DECREASE;
		}
		
		if(getTotalVelocity() > MIN_VELOCITY)
		{
			isSlowing = false;
			mLastVelocityOkayTime = now;
			mTotalDuration = now - mStartTime;
			mEntireTotalTime = now - mEntireStartTime;
		}
		else
		{
			if((now - mLastVelocityOkayTime) > WAIT_TIME)
			{
				isSlowing = true;
				mLastVelocityOkayTime = now;
				mStartTime = mStartTime + (WAIT_TIME * 2) < now ? mStartTime + (WAIT_TIME * 2) : now;
				mTotalDuration = now - mStartTime;
			}
		}

		canvas.drawBitmap(drawable_bitmap, x, y, mPaint);
		
		h.postDelayed(r, FRAME_RATE);
	}
	
}
