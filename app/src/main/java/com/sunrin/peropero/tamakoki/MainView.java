package com.sunrin.peropero.tamakoki;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Hamsting on 2017-06-05.
 */

public class MainView extends SurfaceView implements SurfaceHolder.Callback
{
	private GameActivity gameActivity;
	MainThread mainThread;
	Handler handler;
	Context mainContext;
	ScreenConfig screenConfig;
	boolean drawCls = false;
	private int xx = 0;



	public MainView(Context r, AttributeSet a)
	{
		super(r, a);
		getHolder().addCallback(this);
		mainThread = new MainThread(getHolder(), this);
		setFocusable(true);
		mainContext = r;
	}

	public void init(int _w, int _h, GameActivity _gameActivity)
	{
		gameActivity = _gameActivity;
		screenConfig = new ScreenConfig(_w, _h);
		screenConfig.setSize(1080, 1920);
		drawCls = true;
	}

	public void tick()
	{
		xx += 2;
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		float eTime = mainThread.elapsedTime;
		tick();

		if (!drawCls)
			return;

		Paint backPaint = new Paint();
		backPaint.setColor(Color.rgb(0, 0, 0));
		canvas.drawRect(0, 0,
				screenConfig.screenWidth, screenConfig.screenHeight, backPaint);

		backPaint.setColor(Color.rgb(255, 0, 0));
		canvas.drawRect(screenConfig.getX(0), screenConfig.getY(xx),
						screenConfig.getX(200), screenConfig.getY(200 + xx), backPaint);

		backPaint.setColor(Color.rgb(0, 255, 0));
		canvas.drawRect(screenConfig.getX(900), screenConfig.getY(400),
				screenConfig.getX(1100), screenConfig.getY(600), backPaint);

		backPaint.setColor(Color.rgb(0, 0, 255));
		canvas.drawRect(screenConfig.getX(1800), screenConfig.getY(800),
				screenConfig.getX(2000), screenConfig.getY(1000), backPaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return true;
		// return super.onTouchEvent(event);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		mainThread.setRunning(true);
		try
		{
			if (mainThread.getState() == Thread.State.TERMINATED)
			{
				mainThread = new MainThread(getHolder(), this);
				mainThread.setRunning(true);
				setFocusable(true);
				mainThread.start();
			}
			else
				mainThread.start();
		}
		catch (Exception ex)
		{
			Log.i("Mainview", "surfaceCreated -> Exception : " + ex.toString());
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		boolean re = true;
		mainThread.setRunning(false);
		while (re)
		{
			try
			{
				mainThread.join();
				re = false;
			}
			catch (Exception ex)
			{
				Log.i("Mainview", "surfaceDestroyed -> Exception : " + ex.toString());
			}
		}
	}
}



































