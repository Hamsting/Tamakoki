package com.sunrin.peropero.tamakoki;

/**
 * Created by Hamsting on 2017-06-09.
 */

public class SceneManager
{
	public static SceneManager instance;

	public IScene currentScene;



	public SceneManager()
	{
		if (instance == null)
			instance = this;
	}

	public void init()
	{

	}

	public void startScene(IScene _scene)
	{
		currentScene = _scene;
		_scene.init();
	}

	public void changeScene(IScene _scene)
	{
		AppManager.instance.mainThread.changeScene(_scene);
	}
}
