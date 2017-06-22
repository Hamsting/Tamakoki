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

/**
 * Created by Hamsting on 2017-06-20.
 */

public class SplashScene extends IScene
{
	private static final float ANIMTIMER_1 = 1.0f;
	private static final float ANIMTIMER_2 = 0.75f;
	private static final float ANIMTIMER_3 = 1.5f;
	private static final float ANIMTIMER_4 = 0.75f;
	private static final float ANIMTIMER_5 = 1.0f;

	private Bitmap bmpLogo;
	private Point logoSize;
	private Point logoHalfSize;
	private float timer;
	private int animState;
	private int logoAlpha;



	public SplashScene(Context r, AttributeSet a)
	{
		super(r, a);
	}

	@Override
	public void init()
	{
		super.init();

		BitmapFactory.Options ops = AppManager.instance.bmpOptions;
		bmpLogo = BitmapFactory.decodeResource(res, R.drawable.img_logo, ops);
		logoSize = new Point(bmpLogo.getWidth(), bmpLogo.getHeight());
		logoHalfSize = new Point(logoSize.x / 2, logoSize.y / 2);
		timer = 0.0f;
		logoAlpha = 0;
		animState = 0;
	}

	@Override
	public void tick(float _eTime)
	{
		super.tick(_eTime);
		timer += _eTime;

		animation();
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		ScreenConfig s = screenConfig;

		if (logoAlpha > 0)
		{
			paint.setARGB(logoAlpha, 255, 255, 255);
			Rect rect = new Rect(720 / 2 - logoHalfSize.x, 1280 / 2 - logoHalfSize.y,
								 720 / 2 + logoHalfSize.x, 1280 / 2 + logoHalfSize.y);
			canvas.drawBitmap(bmpLogo, null, rect, paint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return super.onTouchEvent(event);
	}

	private void animation()
	{
		if (animState == 0 && timer >= ANIMTIMER_1)
		{
			++animState;
			timer -= ANIMTIMER_1;
		}
		else if (animState == 1)
		{
			logoAlpha = clamp((int)(255.0f * timer / ANIMTIMER_2), 0, 255);
			if (timer >= ANIMTIMER_2)
			{
				++animState;
				timer -= ANIMTIMER_2;
			}
		}
		else if (animState == 2 && timer >= ANIMTIMER_3)
		{
			++animState;
			timer -= ANIMTIMER_3;
		}
		else if (animState == 3)
		{
			logoAlpha = clamp(255 - (int)(255.0f * timer / ANIMTIMER_4), 0, 255);
			if (timer >= ANIMTIMER_4)
			{
				++animState;
				timer -= ANIMTIMER_4;
			}
		}
		else if (animState == 4 && timer >= ANIMTIMER_5)
		{
			++animState;
			timer -= ANIMTIMER_5;
		}
		else if (animState == 5)
		{
			++animState;
			SceneManager.instance.loadMainScene();
		}
	}

	private int clamp(int _n, int _min, int _max)
	{
		if (_n < _min)
			return _min;
		else if (_n > _max)
			return _max;
		return _n;
	}
}
