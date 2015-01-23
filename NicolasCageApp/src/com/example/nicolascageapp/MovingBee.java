package com.example.nicolascageapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

public class MovingBee extends MovingObj {
	
	// How fast the bee should be flying
	private final float TOTAL_VELOCITY = 42;
	
	// Bee image setup
	private int beeImgID_1 = R.drawable.moving_bee_1;
	private int beeImgID_2 = R.drawable.moving_bee_2;
	
	// frame rate yo
	private final int FRAME_RATE = 30;
	
	public MovingBee(Context context) {
		super(context);
		
		paint = new Paint();
		
		initVelocities();
		
		// sets bee image based upon x_velocity (don't want a bee flying backwards...maybe)
		// also get stats
		img = BitmapFactory.decodeResource(getResources(), xVelocity > 0 ? beeImgID_2 : beeImgID_1);

		imgWidth = img.getWidth();
		imgHeight = img.getHeight();
		
		initPositions();
		
		handler = new Handler(context.getMainLooper());
	}
	
	// responsible for settings x, y velocities taking into account
	// the total velocity.
	private void initVelocities()
	{
		// gets random float from range -50 to 50 and set it to x_velocity
		xVelocity = (float) ((Math.random()*(TOTAL_VELOCITY*2)) - TOTAL_VELOCITY);
		
		// Extremely unlikely case that it equals 0, just set it to 15.  
		if(xVelocity == 0)
		{
			xVelocity = 15;
		}
		// Calculates y velocity c^2 - b^2 = a^2
		yVelocity = (float) (Math.sqrt(Math.pow(TOTAL_VELOCITY, 2) - Math.pow(xVelocity, 2)));
		Log.d("setting vel", "1. y_vel: " + String.valueOf(yVelocity) );
		yVelocity *= Math.random() > .5 ? 1 : -1;
		Log.d("setting vel", "1. y_vel: " + String.valueOf(yVelocity) );
	}
	
	private void initPositions()
	{
		this.x = -imgHeight;
		this.y = (float) (Math.random() * this.getHeight());
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		x += xVelocity;
		y += yVelocity;
		
		if(x < -imgWidth)
		{
			x = this.getWidth();
		}
		else if(x > this.getWidth() + imgWidth)
		{
			x = -imgWidth;
		}
		else if(y < -imgHeight)
		{
			y = this.getHeight();
		}
		else if(y > this.getHeight() + imgHeight)
		{
			y = -imgHeight;
		}
		
		canvas.drawBitmap(img, x, y, paint);
		
		
		handler.postDelayed(r, FRAME_RATE);
	}

}
