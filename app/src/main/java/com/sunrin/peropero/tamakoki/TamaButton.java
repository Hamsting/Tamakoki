package com.sunrin.peropero.tamakoki;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

/**
 * Created by Hamsting on 2017-06-22.
 */

public class TamaButton extends IObject
{
	public Point pos;
	public PointF pivot;

	private Bitmap bmpBtn;
	private Point btnSize;



	public TamaButton(int _resId, Point _pos)
	{
		super();
		bmpBtn = BitmapFactory.decodeResource(res, _resId, bmpOptions);
		btnSize = new Point(bmpBtn.getWidth(), bmpBtn.getHeight());
		pos = _pos;
		pivot = new PointF(0.0f, 0.0f);
	}

	public TamaButton(int _resId, Point _pos, PointF _pivot)
	{
		this(_resId, _pos);
		pivot = _pivot;
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

		Rect rec = new Rect(s.getX(pos.x - (int)(btnSize.x * pivot.x)), s.getY(pos.y - (int)(btnSize.y * pivot.y)),
							s.getX(pos.x + (int)(btnSize.x * (1.0f - pivot.x))), s.getY(pos.y + (int)(btnSize.y * (1.0f - pivot.y))));
		_canvas.drawBitmap(bmpBtn, null, rec, null);
	}

	public void OnPress()
	{

	}

	public void OnRelease()
	{

	}
}
