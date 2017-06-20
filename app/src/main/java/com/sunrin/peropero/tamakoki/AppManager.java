package com.sunrin.peropero.tamakoki;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by Hamsting on 2017-06-09.
 */

public class AppManager
{
	public static AppManager instance;

	public MainThread mainThread;
	public GameActivity gameActivity;
	public BitmapFactory.Options bmpOptions;
	public Resources res;
	public Context context;
	public AttributeSet attributeSet;



	public AppManager()
	{
		if (instance == null)
			instance = this;
	}

	public void init(GameActivity _gameActivity, IScene _startScene)
	{
		gameActivity = _gameActivity;

		res = _gameActivity.getResources();

		bmpOptions = new BitmapFactory.Options();
		bmpOptions.inScaled = false;
		bmpOptions.inPreferredConfig = Bitmap.Config.RGB_565;

		mainThread = new MainThread(_startScene.getHolder(), _startScene);

		SceneManager sceneManager = new SceneManager();
		sceneManager.init();
		sceneManager.startScene(_startScene);
	}

	public void onSurfaceCreated(IScene _scene, SurfaceHolder _holder)
	{
		mainThread.setRunning(true);
		try
		{
			if (mainThread.getState() == Thread.State.TERMINATED)
			{
				mainThread = new MainThread(_scene.getHolder(), _scene);
				mainThread.setRunning(true);
				_scene.setFocusable(true);
				mainThread.start();
			}
			else
				mainThread.start();
		}
		catch (Exception ex)
		{
			Log.i(_scene.toString(), "surfaceCreated -> Exception : " + ex.toString());
		}
	}

	public void onSurfaceChanged(IScene _scene, SurfaceHolder holder, int format, int width, int height)
	{
	}

	public void onSurfaceDestroyed(IScene _scene, SurfaceHolder holder)
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
				Log.i(_scene.toString(), "surfaceDestroyed -> Exception : " + ex.toString());
			}
		}
	}
}
