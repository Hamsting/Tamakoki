package com.sunrin.peropero.tamakoki;

import android.content.IntentFilter;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by Sunrin on 2017-06-05.
 */

public class MainThread extends Thread
{
	private final float TARGET_FPS = 30.0f;

	private SurfaceHolder surfaceHolder;
	private IScene scene;
	private boolean running = false;
	private long lastRealTime;
	private long fixedElapsedTimeLong;

	public float fixedElapsedTime;
	public float elapsedTime;



	public MainThread(SurfaceHolder _surfaceHolder, IScene _scene)
	{
		surfaceHolder = _surfaceHolder;
		scene = _scene;
		setPriority(MIN_PRIORITY);

		lastRealTime = SystemClock.elapsedRealtime();
		fixedElapsedTime = 1.0f / TARGET_FPS;
		fixedElapsedTimeLong = (long)(fixedElapsedTime * 1000.0f);
		elapsedTime = 0.0f;
	}

	public SurfaceHolder getSurfaceHolder()
	{
		return surfaceHolder;
	}

	public void setRunning(boolean _running)
	{
		running = _running;
	}

	@Override
	public void run()
	{
		try
		{
			Canvas c;
			while (running)
			{
				c = null;
				try
				{
					c = surfaceHolder.lockCanvas(null);
					synchronized (surfaceHolder)
					{
						try
						{
							long currentRealTime = SystemClock.elapsedRealtime();
							long elapsedTimeLong = currentRealTime - lastRealTime;
							elapsedTime = elapsedTimeLong / 1000.0f;
							lastRealTime = currentRealTime;
							scene.onDraw(c);
						}
						catch (Exception ex)
						{
							Log.e("MainThread", ex.toString());
						}
					}
				}
				finally
				{
					if (c != null)
						surfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
		catch (Exception e)
		{
			Log.e("MainThread", e.toString());
		}
	}
}





































