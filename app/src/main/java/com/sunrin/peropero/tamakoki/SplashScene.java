package com.sunrin.peropero.tamakoki;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

/**
 * Created by ww on 2017-06-09.
 */

public class SplashScene extends IScene
{
	private float timer = 0.0f;
	private Tama tama;



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
	}
}
