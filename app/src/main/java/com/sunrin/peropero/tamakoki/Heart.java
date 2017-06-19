package com.sunrin.peropero.tamakoki;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Hamsting on 2017-06-19.
 */

public class Heart extends IObject
{
	private static final int DEST_X = 70;
	private static final int DEST_Y = 45;

	private static Bitmap bmpHeart;

	private Point heartSize;
	private Point currentPos;
	private Point originalPos;
	private Point distance;

	private float timer;
	private int animState;
	private float scale;



	public Heart(int _x, int _y)
	{
		super();
		if (bmpHeart == null)
			bmpHeart = BitmapFactory.decodeResource(res, R.drawable.img_heart, bmpOptions);
		heartSize = new Point(bmpHeart.getWidth(), bmpHeart.getHeight());
		originalPos = new Point(_x, _y);
		currentPos = new Point(_x, _y);
		distance = new Point(_x - DEST_X, _y - DEST_Y);
		timer = 0.0f;
		animState = 0;
		scale = 1.0f;
	}

	@Override
	public void tick(float _eTime)
	{
		super.tick(_eTime);
		timer += _eTime;

		animate();
	}

	@Override
	public void draw(Canvas _canvas)
	{
		super.draw(_canvas);
		ScreenConfig s = SceneManager.instance.currentScene.screenConfig;

		int halfSizeX = (int)(heartSize.x * scale / 2.0f);
		int halfSizeY = (int)(heartSize.y * scale / 2.0f);
		Rect rec = new Rect(s.getX(currentPos.x - halfSizeX), s.getY(currentPos.y - halfSizeY),
				s.getX(currentPos.x + halfSizeX), s.getY(currentPos.y + halfSizeY));
		_canvas.drawBitmap(bmpHeart, null, rec, null);
	}

	private void animate()
	{
		if (animState == 0)
		{
			scale = 1.0f + 1.0f * (timer / 0.3f);
			if (timer >= 0.3f)
			{
				++animState;
				timer -= 0.3f;
			}
		}
		else if (animState == 1)
		{
			scale = 2.0f - 1.0f * (timer / 0.3f);
			if (timer >= 0.3f)
			{
				++animState;
				timer -= 0.3f;
			}
		}
		else if (animState == 2)
		{
			double ease = 1.0f - Math.cos((double)(90.0f * (timer / 1.0f)));
			int easedX = (int)(distance.x * ease);
			int easedY = (int)(distance.y * ease);
			currentPos.set(originalPos.x - easedX, originalPos.y - easedY);
			scale = 1.0f - 0.5f * (timer / 1.0f);
			if (timer >= 1.0f)
			{
				++animState;
				timer -= 1.0f;
			}
		}
		else if (animState == 3)
		{
			++animState;
			destroy();
		}
	}

	private void destroy()
	{
		parent.removeNode(this);
	}
}
