package com.sunrin.peropero.tamakoki;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.TouchDelegate;

/**
 * Created by Hamsting on 2017-06-09.
 */

public class MainScene extends IScene
{
	private static final int KAWAII_X = 20;
	private static final int KAWAII_Y = 20;

	private Tama tama;
	private AttentionBar abar;
	private Bitmap bmpBg;
	private Bitmap bmpKawaii;
	private Point kawaiiSize;



	public MainScene(Context r, AttributeSet a)
	{
		super(r, a);
	}

	@Override
	public void init(int _sw, int _sh)
	{
		super.init(_sw, _sh);
		tama = new Tama();
		addNode(tama);
		abar = new AttentionBar();
		addNode(abar);

		BitmapFactory.Options ops = AppManager.instance.bmpOptions;
		bmpBg = BitmapFactory.decodeResource(res, R.drawable.img_bg, ops);
		bmpKawaii = BitmapFactory.decodeResource(res, R.drawable.img_kawaii, ops);
		kawaiiSize = new Point(bmpKawaii.getWidth(), bmpKawaii.getHeight());
	}

	@Override
	public void tick(float _eTime)
	{
		super.tick(_eTime);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		ScreenConfig s = screenConfig;

		Rect rect = new Rect(s.getX(0), s.getY(0), s.getX(720), s.getY(1280));
		canvas.drawBitmap(bmpBg, null, rect, null);

		tama.draw(canvas);

		rect = new Rect(s.getX(KAWAII_X), s.getY(KAWAII_Y),
				s.getX(KAWAII_X + kawaiiSize.x), s.getY(KAWAII_Y + kawaiiSize.y));
		canvas.drawBitmap(bmpKawaii, null, rect, null);

		abar.draw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			ScreenConfig s = screenConfig;
			Point pos = new Point((int) event.getX(), (int) event.getY());
			Rect area = new Rect(s.getX(200), s.getY(1280 - 700),
					s.getX(720 - 200), s.getY(1280 - 300));
			if (area.contains(pos.x, pos.y))
				abar.addCurrent(10);
		}
		return super.onTouchEvent(event);
	}
}
