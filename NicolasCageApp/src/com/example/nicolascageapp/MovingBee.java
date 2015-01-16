package com.example.nicolascageapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
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
	private final float TOTAL_VELOCITY = 50;
	
	// Bee image setup
	private int beeImgID_1 = R.drawable.moving_bee_1;
	private int beeImgID_2 = R.drawable.moving_bee_2;
	private Bitmap beeImg;
	
	// Bee image info
	private int beeWidth;
	private int beeHeight;
	
	// screen info
	private int screenWidth;
	private int screenHeight;
	
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
		//beeImg = BitmapFactory.decodeResource(getResources(), x_velocity > 0 ? beeImgID_2 : beeImgID_1);
		beeImg = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);
		beeWidth = beeImg.getWidth();
		beeHeight = beeImg.getHeight();
		
		//setPosition();
		
		x = 50;
		y = 50;
		
		// screen info
		screenWidth = this.getWidth();
		screenHeight = this.getHeight();
		

		
		handler = new Handler();
		
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
		y_velocity = (float) (Math.sqrt(Math.pow(TOTAL_VELOCITY, 2) - Math.pow(x, 2)));
		y_velocity *= Math.random() > .5 ? 1 : -1;
	}
	
	private void setPosition()
	{
		x = -beeHeight;
		y = (float) (Math.random() * screenHeight);
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
		//x += x_velocity;
		
		//y += y_velocity;
		
		if(x < -beeWidth)
		{
			x = screenWidth;
		}
		else if(x > screenWidth + beeWidth)
		{
			x = -beeWidth;
		}
		else if(y < -beeHeight)
		{
			y = screenHeight;
		}
		else if(y > screenHeight + beeHeight)
		{
			y = -beeHeight;
		}
		
		//canvas.drawBitmap(beeImg, x, y, paint);
		canvas.drawCircle(x, y, 50, paint);
		
		Log.d("bee drawing", "x: " + String.valueOf(x));
		Log.d("bee drawing", "y: " + String.valueOf(y));
		
		handler.postDelayed(r, FRAME_RATE);
	}

}
