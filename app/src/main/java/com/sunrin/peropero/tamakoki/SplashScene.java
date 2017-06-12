package com.sunrin.peropero.tamakoki;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;

/**
 * Created by ww on 2017-06-09.
 */

public class SplashScene extends IScene
{
	private float timer = 0.0f;
	private Tama tama;
	private Bitmap bmpBg;
	private Bitmap bmpKawaii;



	public SplashScene(Context r, AttributeSet a)
	{
		super(r, a);
	}

	@Override
	public void init(int _sw, int _sh)
	{
		super.init(_sw, _sh);
		tama = new Tama();
		addNode(tama);
		BitmapFactory.Options ops = AppManager.instance.bmpOptions;

		bmpBg = BitmapFactory.decodeResource(res, R.drawable.img_bg, ops);
		bmpKawaii = BitmapFactory.decodeResource(res, R.drawable.img_kawaii, ops);
	}

	@Override
	public void tick(float _eTime)
	{
		super.tick(_eTime);
		timer += _eTime;
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		ScreenConfig s = screenConfig;

		Rect rect = new Rect(s.getX(0), s.getY(0), s.getX(720), s.getY(1280));
		canvas.drawBitmap(bmpBg, null, rect, null);

		rect = new Rect(s.getX(40), s.getY(30),
					    s.getX(40 + bmpKawaii.getWidth()), s.getY(30 + bmpKawaii.getHeight()));
		canvas.drawBitmap(bmpKawaii, null, rect, null);

		tama.draw(canvas);

		float eTime = mainThread.elapsedTime;
		String fps = Float.toString(1.0f / eTime);
		paint.setColor(Color.rgb(0, 0, 0));
		canvas.drawText("FPS : " + fps + ", Elapsed : " + eTime, screenConfig.getX(0), screenConfig.getY(50), paint);

	}
}
