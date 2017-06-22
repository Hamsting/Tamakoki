package com.sunrin.peropero.tamakoki;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by Hamsting on 2017-06-05.
 * 게임의 루프를 담당해주는 쓰레드이다.
 */

public class MainThread extends Thread
{
	private final float TARGET_FPS = 60.0f;

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

	public boolean getRunning()
	{
		return running;
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
				if (SceneManager.instance.changeSceneNum != 0)
				{
					scene = SceneManager.instance.changeScene();
					surfaceHolder = scene.getHolder();
					Log.e("MainThread_0", "Changed : " + scene.toString());
				}
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
							if (GameActivity.SHOW_FPS)
								showFps(c);
						}
						catch (Exception ex)
						{
							Log.e("MainThread_2", ex.toString());
							ex.printStackTrace();
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
			Log.e("MainThread_1", e.toString());
		}
	}

	private void showFps(Canvas _canvas)
	{
		String fps = Float.toString(1.0f / elapsedTime);
		ScreenConfig screenConfig = scene.screenConfig;
		Paint paint = scene.paint;
		paint.setTextSize(10);
		paint.setColor(Color.rgb(0, 0, 0));
		_canvas.drawRect(screenConfig.getX(0), screenConfig.getY(0), screenConfig.getX(720 / 4), screenConfig.getY(10), paint);
		paint.setColor(Color.rgb(255, 255, 255));
		_canvas.drawText("FPS : " + fps + ", Elapsed : " + elapsedTime, screenConfig.getX(0), screenConfig.getY(10), paint);
	}
}





































