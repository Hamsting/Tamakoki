package com.sunrin.peropero.tamakoki;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Hamsting on 2017-06-19.
 * 관심도를 줬을 때의 효과이며, 하트의 애니메이션과 렌더링을 관리한다.
 */

public class Heart extends IObject
{
	private static final int DEST_X = 20 + 77 / 2;
	private static final int DEST_Y = 20 + 103 / 2;
	private static final float ANIMTIMER_1 = 0.2f;
	private static final float ANIMTIMER_2 = 0.25f;
	private static final float ANIMTIMER_3 = 0.75f;

	private static Bitmap bmpHeart;
	private static Bitmap bmpBonusHeart;

	private Point heartSize;
	private Point currentPos;
	private Point originalPos;
	private Point distance;
	private boolean bonus;

	private float timer;
	private int animState;
	private float scale;



	public Heart(int _x, int _y, boolean _bonus)
	{
		super();
		if (bmpHeart == null)
			bmpHeart = BitmapFactory.decodeResource(res, R.drawable.img_heart, bmpOptions);
		if (bmpBonusHeart == null)
			bmpBonusHeart = BitmapFactory.decodeResource(res, R.drawable.img_bonusheart, bmpOptions);
		heartSize = new Point(bmpHeart.getWidth(), bmpHeart.getHeight());
		originalPos = new Point(_x, _y);
		currentPos = new Point(_x, _y);
		distance = new Point(_x - DEST_X, _y - DEST_Y);
		timer = 0.0f;
		animState = 0;
		scale = 1.0f;
		bonus = _bonus;
	}

	@Override
	public void tick(float _eTime)
	{
		super.tick(_eTime);
		timer += _eTime;

		animation();
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
		if (bonus)
			_canvas.drawBitmap(bmpBonusHeart, null, rec, null);
		else
			_canvas.drawBitmap(bmpHeart, null, rec, null);
	}

	private void animation()
	{
		if (animState == 0)
		{
			scale = 1.0f + 1.0f * (timer / ANIMTIMER_1);
			if (timer >= ANIMTIMER_1)
			{
				++animState;
				timer -= ANIMTIMER_1;
			}
		}
		else if (animState == 1)
		{
			scale = 2.0f - 1.0f * (timer / ANIMTIMER_2);
			if (timer >= ANIMTIMER_2)
			{
				++animState;
				timer -= ANIMTIMER_2;
			}
		}
		else if (animState == 2)
		{
			double ease = 1.0f - Math.cos(90.0f * (timer / ANIMTIMER_3) * Math.PI / 180.f);
			int easedX = (int)(distance.x * ease);
			int easedY = (int)(distance.y * ease);
			currentPos.set(originalPos.x - easedX, originalPos.y - easedY);
			scale = 1.0f - 0.75f * (timer / ANIMTIMER_3);
			if (timer >= ANIMTIMER_3)
			{
				++animState;
				timer -= ANIMTIMER_3;
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
		MainScene scene = (MainScene)SceneManager.instance.currentScene;
		scene.removeHeart(this);
	}
}
