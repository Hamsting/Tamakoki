package com.sunrin.peropero.tamakoki;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Hamsting on 2017-06-09.
 */

public class Tama extends IObject
{
	private static final int TAMA_X = 720 / 2;
	private static final int TAMA_Y = 1280 - 300;

	private Bitmap bmpTama;
	private Point tamaSize;



	public Tama()
	{
		super();
		bmpTama = BitmapFactory.decodeResource(res, R.drawable.img_tama, bmpOptions);
		tamaSize = new Point(bmpTama.getWidth(), bmpTama.getHeight());
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

		Rect rec = new Rect(s.getX(TAMA_X - tamaSize.x / 2), s.getY(TAMA_Y - tamaSize.y),
							s.getX(TAMA_X + tamaSize.x / 2), s.getY(TAMA_Y));
		_canvas.drawBitmap(bmpTama, null, rec, null);
	}
}
