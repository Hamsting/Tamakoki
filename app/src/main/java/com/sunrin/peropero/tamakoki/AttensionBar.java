package com.sunrin.peropero.tamakoki;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Sunrin on 2017-06-12.
 */

public class AttensionBar extends IObject
{
	private int max = 100;
	private int current = 0;
	private float percent;
	private Bitmap bmpBg;
	private Bitmap bmpBar;



	public AttensionBar()
	{
		super();
		bmpBg = BitmapFactory.decodeResource(res, R.drawable.img_barbg, bmpOptions);
		bmpBar = BitmapFactory.decodeResource(res, R.drawable.img_bar, bmpOptions);
	}

	@Override
	public void tick(float _eTime)
	{
		super.tick(_eTime);
		percent = (float)current / max;
	}

	@Override
	public void draw(Canvas _canvas)
	{
		super.draw(_canvas);
		ScreenConfig s = SceneManager.instance.currentScene.screenConfig;

		Rect rec = new Rect(s.getX(0), s.getY(50), s.getX(720), s.getY(1280));
		// _canvas.drawBitmap(bmpTama, null, rec, null);
	}

	public void setMax(int _max)
	{
		max = _max;
	}

	public void setCurrent(int _current)
	{
		current = _current;
	}
}
