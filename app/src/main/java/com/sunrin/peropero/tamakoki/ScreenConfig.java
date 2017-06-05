package com.sunrin.peropero.tamakoki;

/**
 * Created by Hamsting on 2017-06-05.
 */

public class ScreenConfig
{
	public int screenWidth;
	public int screenHeight;
	public int virtualWidth;
	public int virtualHeight;



	public ScreenConfig(int _screenWidth, int _screenHeight)
	{
		screenWidth = _screenWidth;
		screenHeight = _screenHeight;
	}

	public void setSize(int _w, int _h)
	{
		virtualWidth = _w;
		virtualHeight = _h;
	}

	public int getX(int _x)
	{
		return _x * screenWidth/virtualWidth;
	}

	public int getY(int _y)
	{
		return _y * screenHeight/virtualHeight;
	}
}
