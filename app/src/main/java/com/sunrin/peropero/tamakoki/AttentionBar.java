package com.sunrin.peropero.tamakoki;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Hamsting on 2017-06-12.
 */

public class AttentionBar extends IObject
{
	private static final int BARBG_X = 120;
	private static final int BARBG_Y = 20;

	private int max = 10000;
	private int current = 0;
	private float percent;
	private Bitmap bmpBg;
	private Bitmap bmpBar;
	private Point bgSize;
	private Point barSize;
	private Point barPadding;



	public AttentionBar()
	{
		super();
		bmpBg = BitmapFactory.decodeResource(res, R.drawable.img_barbg, bmpOptions);
		bmpBar = BitmapFactory.decodeResource(res, R.drawable.img_bar, bmpOptions);
		bgSize = new Point(bmpBg.getWidth(), bmpBg.getHeight());
		barSize = new Point(bmpBar.getWidth(), bmpBar.getHeight());
		barPadding = new Point((bgSize.x - barSize.x) / 2, (bgSize.y - barSize.y) / 2);
	}

	@Override
	public void tick(float _eTime)
	{
		super.tick(_eTime);
		percent = (float)current / max;
		if (current < max)
			++current;
	}

	@Override
	public void draw(Canvas _canvas)
	{
		super.draw(_canvas);
		ScreenConfig s = SceneManager.instance.currentScene.screenConfig;

		Rect rec = new Rect(s.getX(BARBG_X), s.getY(BARBG_Y),
							s.getX(BARBG_X + bgSize.x), s.getY(BARBG_Y + bgSize.y));
		_canvas.drawBitmap(bmpBg, null, rec, null);

		rec.left += barPadding.x;
		rec.top += barPadding.y;
		rec.right -= barPadding.x + (1.0f - percent) * barSize.x;
		rec.bottom -= barPadding.y;
		_canvas.drawBitmap(bmpBar, null, rec, null);
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
