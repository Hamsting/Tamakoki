package com.sunrin.peropero.tamakoki;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by Hamsting on 2017-06-09.
 * 어플리케이션의 시작과 쓰레드를 관리.
 */

public class AppManager
{
	public static AppManager instance;

	public MainThread mainThread;
	public GameActivity gameActivity;
	public BitmapFactory.Options bmpOptions;
	public Resources res;



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

		GlobalData globalData = new GlobalData();

		SceneManager sceneManager = new SceneManager();
		sceneManager.init();
		sceneManager.startScene(_startScene);
	}

	public void onSurfaceCreated(IScene _scene, SurfaceHolder _holder)
	{
		Log.e("AppManager_0", "Surface Created : " + _scene.toString());
		if (mainThread != null && mainThread.getRunning())
			return;

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
			ex.printStackTrace();
		}
		Log.e("Nope", Boolean.toString(mainThread.getRunning()));
	}

	public void onSurfaceChanged(IScene _scene, SurfaceHolder holder, int format, int width, int height)
	{
		Log.e("AppManager_1", "Surface Changed.");
	}

	public void onSurfaceDestroyed(IScene _scene, SurfaceHolder holder)
	{
		Log.e("AppManager_2", "Surface Destroyed.");
		if (_scene.getClass() == MainScene.class)
			((MainScene)_scene).saveData();

		if (SceneManager.instance.currentScene != null && SceneManager.instance.currentScene != _scene)
			return;

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
		AppManager.instance.gameActivity.finish();
	}
}
