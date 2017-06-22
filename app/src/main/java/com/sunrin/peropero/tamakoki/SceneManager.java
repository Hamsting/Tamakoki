package com.sunrin.peropero.tamakoki;

import android.util.Log;
import android.view.View;

/**
 * Created by Hamsting on 2017-06-09.
 * 게임 씬들의 시작, 이동 등을 관리한다.
 */

public class SceneManager
{
	public static SceneManager instance;
	public static final int SN_SPLASHSCENE = 1;
	public static final int SN_MAINSCENE = 2;

	public IScene currentScene;
	public IScene nextScene;
	public int changeSceneNum;



	public SceneManager()
	{
		if (instance == null)
			instance = this;
	}

	public void init()
	{
		changeSceneNum = 0;
	}

	public void startScene(IScene _scene)
	{
		currentScene = _scene;
		currentScene.setVisibility(View.VISIBLE);
		_scene.init();
	}

	public IScene changeScene()
	{
		AppManager a = AppManager.instance;
		switch (changeSceneNum)
		{
			case SN_MAINSCENE :
				nextScene = a.gameActivity.mainScene;
				break;
		}
		changeSceneNum = 0;
		Log.e("SceneManager", "NextScene : " + nextScene.toString());
		if (nextScene == null)
			return null;

		a.gameActivity.runOnUiThread(new Runnable(){
			@Override
			public void run()
			{
				currentScene.setVisibility(View.VISIBLE);
				nextScene.setVisibility(View.INVISIBLE);
				nextScene = null;
			}
		});
		IScene temp = currentScene;
		currentScene = nextScene;
		nextScene = temp;
		currentScene.init();
		return currentScene;
	}

	public void loadMainScene()
	{
		changeSceneNum = SN_MAINSCENE;
	}
}
