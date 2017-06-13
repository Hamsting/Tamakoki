package com.sunrin.peropero.tamakoki;

/**
 * Created by Hamsting on 2017-06-09.
 */

public class SceneManager
{
	public static SceneManager instance;

	public IScene currentScene;
	public int screenWidth;
	public int screenHeight;



	public SceneManager()
	{
		if (instance == null)
			instance = this;
	}

	public void init()
	{
		GameActivity gameActivity = AppManager.instance.gameActivity;
		screenWidth = gameActivity.getWindowManager().getDefaultDisplay().getWidth();
		screenHeight = gameActivity.getWindowManager().getDefaultDisplay().getHeight();
	}

	public void startScene(IScene _scene)
	{
		currentScene = _scene;
		_scene.init(720, 1280);
	}
}
