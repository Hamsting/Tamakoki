package com.sunrin.peropero.tamakoki;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.transition.Scene;

/**
 * Created by ww on 2017-06-09.
 */

public class Tama extends IObject
{
	private Bitmap bmpTama;



	public Tama()
	{
		super();
		bmpTama = BitmapFactory.decodeResource(res, R.drawable.img_knobs, bmpOptions);
	}

	@Override
	public void tick(float _eTime)
	{
		super.tick(_eTime);
	}

	@Override
	public void draw(Canvas _canvas)
	{
		super.draw(_canvas);
		ScreenConfig s = SceneManager.instance.currentScene.screenConfig;
		Rect rec = new Rect(s.getX(0), s.getY(50), s.getX(1080), s.getY(1920));
		_canvas.drawBitmap(bmpTama, null, rec, null);
	}
}
