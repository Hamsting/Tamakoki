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
	public static final int SN_SKILLSCENE = 3;

	public IScene currentScene;
	public IScene nextScene;
	public int changeSceneNum;

	private boolean changeCompleted;



	public SceneManager()
	{
		if (instance == null)
			instance = this;
	}

	public void init()
	{
		changeSceneNum = 0;
		changeCompleted = true;
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
			case SN_SKILLSCENE :
				nextScene = a.gameActivity.skillScene;
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
				if (nextScene.getClass() == SplashScene.class)
					nextScene.setVisibility(View.GONE);
				nextScene = null;
				changeCompleted = true;
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
		if (!changeCompleted)
			return;
		changeSceneNum = SN_MAINSCENE;
		changeCompleted = false;
	}
	public void loadSkillScene()
	{
		if (!changeCompleted)
			return;
		changeSceneNum = SN_SKILLSCENE;
		changeCompleted = false;
	}
}
