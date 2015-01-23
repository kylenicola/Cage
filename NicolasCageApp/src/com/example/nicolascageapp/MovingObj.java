package com.example.nicolascageapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.view.View;

public class MovingObj extends View
{
	// positions
	protected float x = 0;
	protected float y = 0;
	
	// velocities
	protected float xVelocity = 0;
	protected float yVelocity = 0;
	
	// boundaries
	protected int xBoundary;
	protected int yBoundary;
	public static final int NO_BOUNDARY = 0;
	public static final int FIXED_BOUNDARY = 1;
	public static final int THROUGH_BOUNDARY = 2;
	protected int boundaryType = NO_BOUNDARY;
	
	// img + properties
	protected Bitmap img;
	protected int imgHeight;
	protected int imgWidth;
	
	// for onDraw
	protected Handler handler;
	protected int frameRate = 30;
	protected Paint paint;
	protected Runnable r = new Runnable()
	{
		@Override
		public void run() {
			invalidate();
		}
	};
	
	
	public MovingObj(Context context) {
		super(context);
		handler = new Handler(context.getMainLooper());
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public void setX(float x)
	{		
		this.x = x;
	}
	
	public void setY(float y)
	{		
		this.y = y;
	}
	
	public float getXVelocity()
	{
		return xVelocity;
	}
	
	public float getYVelocity()
	{
		return yVelocity;
	}
	
	public void setXVelocity(float xVelocity)
	{		
		this.xVelocity = xVelocity;
	}
	
	public void setYVelocity(float yVelocity)
	{		
		this.yVelocity = yVelocity;
	}
	
	public void setImg(int imgResource)
	{
		img = BitmapFactory.decodeResource(getResources(), imgResource);
		imgHeight = img.getHeight();
		imgWidth = img.getWidth();
	}
	
	public int getImgHeight()
	{
		return imgHeight;
	}
	
	public int getImgWidth()
	{
		return imgWidth;
	}
	
	public void setBoundaries(int xBoundary, int yBoundary)
	{
		this.xBoundary 	=	xBoundary;
		this.yBoundary 	=	yBoundary;
	}
	
	public void setBoundaryType(int boundaryType)
	{
		this.boundaryType = boundaryType;
	}
	
	public void setFrameRate(int frameRate)
	{
		this.frameRate = frameRate;
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		x += xVelocity;
		y += yVelocity;
		
		if(boundaryType == FIXED_BOUNDARY)
		{
			if(x < 0)
			{
				x = 1;
				xVelocity = Math.abs(xVelocity);
			}
			else if(x + imgWidth > xBoundary)
			{
				x = xBoundary - imgWidth;
				xVelocity = -Math.abs(xVelocity);
			}
			if(y < 0)
			{
				y = 1;
				yVelocity = Math.abs(yVelocity);
			}
			else if(y + imgHeight > yBoundary)
			{
				y = yBoundary - imgHeight;
				yVelocity = -Math.abs(yVelocity);
			}
		}
		else if(boundaryType == THROUGH_BOUNDARY)
		{
			if(x < -imgWidth)
			{
				x = xBoundary;
			}
			else if(x > xBoundary)
			{
				x = -imgWidth;
			}
			if(y < -imgHeight)
			{
				y = yBoundary;
			}
			else if(y > yBoundary)
			{
				y = -imgHeight;
			}
		}
		
		canvas.drawBitmap(img, x, y, paint);
		
		handler.postDelayed(r, frameRate);
	}

}
