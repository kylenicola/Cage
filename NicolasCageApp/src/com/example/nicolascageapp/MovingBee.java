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

public class MovingBee extends View {
	
	// positions
	private float x;
	private float y;
	
	// velocities
	private float x_velocity;
	private float y_velocity;
	
	// How fast the bee should be flying
	private final float TOTAL_VELOCITY = 55;
	
	// Bee image setup
	private int beeImgID_1 = R.drawable.moving_bee_1;
	private int beeImgID_2 = R.drawable.moving_bee_2;
	private Bitmap beeImg;
	
	// Bee image info
	private int beeWidth;
	private int beeHeight;
	
	// Handler to constantly be drawin'
	Handler handler;
	
	// frame rate yo
	private final int FRAME_RATE = 30;
	
	private Paint paint;
	
		
	public MovingBee(Context context) {
		super(context);
		
		paint = new Paint();
		paint.setColor(android.graphics.Color.RED);
		
		setVelocities();
		
		// sets bee image based upon x_velocity (don't want a bee flying backwards...maybe)
		// also get stats
		beeImg = BitmapFactory.decodeResource(getResources(), x_velocity > 0 ? beeImgID_2 : beeImgID_1);

		beeWidth = beeImg.getWidth();
		beeHeight = beeImg.getHeight();
		
		setPosition();
		
		handler = new Handler(context.getMainLooper());
	}
	
	// responsible for settings x, y velocities taking into account
	// the total velocity.
	private void setVelocities()
	{
		// gets random float from range -50 to 50 and set it to x_velocity
		x_velocity = (float) ((Math.random()*(TOTAL_VELOCITY*2)) - TOTAL_VELOCITY);
		
		// Extremely unlikely case that it equals 0, just set it to 15.  
		if(x_velocity == 0)
		{
			x_velocity = 15;
		}
		// Calculates y velocity c^2 - b^2 = a^2
		y_velocity = (float) (Math.sqrt(Math.pow(TOTAL_VELOCITY, 2) - Math.pow(x_velocity, 2)));
		Log.d("setting vel", "1. y_vel: " + String.valueOf(y_velocity) );
		y_velocity *= Math.random() > .5 ? 1 : -1;
		Log.d("setting vel", "1. y_vel: " + String.valueOf(y_velocity) );
	}
	
	private void setPosition()
	{
		x = -beeHeight;
		y = (float) (Math.random() * this.getHeight());
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

		x += x_velocity;
		y += y_velocity;
		
		if(x < -beeWidth)
		{
			x = this.getWidth();
		}
		else if(x > this.getWidth() + beeWidth)
		{
			x = -beeWidth;
		}
		else if(y < -beeHeight)
		{
			y = this.getHeight();
		}
		else if(y > this.getHeight() + beeHeight)
		{
			y = -beeHeight;
		}
		
		canvas.drawBitmap(beeImg, x, y, paint);
		
		
		handler.postDelayed(r, FRAME_RATE);
	}

}
