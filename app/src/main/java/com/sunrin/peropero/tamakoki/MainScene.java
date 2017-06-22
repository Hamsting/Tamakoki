package com.sunrin.peropero.tamakoki;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TouchDelegate;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Hamsting on 2017-06-09.
 */

public class MainScene extends IScene
{
	private static final int KAWAII_X = 20;
	private static final int KAWAII_Y = 20;

	public int attantionDamage;
	public float bonusPercent;
	public int bonusPoint;

	private Tama tama;
	private AttentionBar abar;
	private Bitmap bmpBg;
	private Bitmap bmpKawaii;
	private Point kawaiiSize;
	private ArrayList<Heart> hearts;



	public MainScene(Context r, AttributeSet a)
	{
		super(r, a);
	}

	@Override
	public void init()
	{
		super.init();
		tama = new Tama();
		addNode(tama);
		abar = new AttentionBar();
		addNode(abar);
		hearts = new ArrayList<>();

		BitmapFactory.Options ops = AppManager.instance.bmpOptions;
		bmpBg = BitmapFactory.decodeResource(res, R.drawable.img_bg, ops);
		bmpKawaii = BitmapFactory.decodeResource(res, R.drawable.img_kawaii, ops);
		kawaiiSize = new Point(bmpKawaii.getWidth(), bmpKawaii.getHeight());

		attantionDamage = 10;
		bonusPercent = 0.02f;
		bonusPoint = 2;

		int[] data = AppManager.instance.loadData();
		abar.level = data[0];
		abar.point = data[1];
		abar.currentAttention = data[2];
		abar.maxAttention = data[3];
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

		Iterator<Heart> iter = hearts.iterator();
		while (iter.hasNext())
		{
			Heart h = iter.next();
			if (h == null)
				continue;
			h.draw(canvas);
			if (h.remove)
				iter.remove();
			if (!iter.hasNext())
				break;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			ScreenConfig s = screenConfig;
			Point pos = new Point(s.screenToVirtualX((int)event.getX()),
								  s.screenToVirtualY((int)event.getY()));
			Rect area = new Rect(s.getX(100), s.getY(1280 - 760),
					s.getX(720 - 100), s.getY(1280 - 130));
			if (area.contains(pos.x, pos.y))
			{
				boolean bonus = abar.addAttention(attantionDamage);
				createRandomHeart(bonus);
			}
		}
		return super.onTouchEvent(event);
	}

	private void createRandomHeart(boolean _bonus)
	{
		int posx = (int)(Math.random() * 520) + 100;
		int posy = 1080 - (int)(Math.random() * 650);
		Heart h = new Heart(posx, posy, _bonus);
		addNode(h);
		hearts.add(h);
	}

	public void removeHeart(Heart _h)
	{
		hearts.remove(_h);
		_h.setRemove();
		_h = null;
	}

	public void saveData()
	{
		AppManager.instance.saveData(abar.level, abar.point, abar.currentAttention, abar.maxAttention);
	}
}
