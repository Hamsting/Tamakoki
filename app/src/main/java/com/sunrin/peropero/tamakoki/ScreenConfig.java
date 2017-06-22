package com.sunrin.peropero.tamakoki;

/**
 * Created by Hamsting on 2017-06-05.
 * 실제 화면과 게임 씬의 화면의 비율을 계산해주는 클래스이다.
 */

public class ScreenConfig
{
	private final int TARGET_WIDTH = 720;
	private final int TARGET_HEIGHT = 1280;

	public int screenWidth;
	public int screenHeight;
	public int virtualWidth;
	public int virtualHeight;



	public ScreenConfig()
	{
		GameActivity gameActivity = AppManager.instance.gameActivity;
		screenWidth = gameActivity.getWindowManager().getDefaultDisplay().getWidth();
		screenHeight = gameActivity.getWindowManager().getDefaultDisplay().getHeight();
	}

	public void setSize(int _vw, int _vh)
	{
		virtualWidth = _vw;
		virtualHeight = _vh;
	}

	public int getX(int _x)
	{
		return _x * TARGET_WIDTH/virtualWidth;
	}

	public int getY(int _y)
	{
		return _y * TARGET_HEIGHT/virtualHeight;
	}

	public int screenToVirtualX(int _x)
	{
		return _x * virtualWidth/screenWidth;
	}

	public int screenToVirtualY(int _y)
	{
		return _y * virtualHeight/screenHeight;
	}

	public int virtualToScreenX(int _x)
	{
		return _x * screenWidth/virtualWidth;
	}

	public int virtualToScreenY(int _y)
	{
		return _y * screenHeight/virtualHeight;
	}
}
