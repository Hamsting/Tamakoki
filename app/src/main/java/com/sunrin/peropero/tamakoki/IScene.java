package com.sunrin.peropero.tamakoki;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by Hamsting on 2017-06-09.
 */

public class IScene extends SurfaceView implements SurfaceHolder.Callback
{
	public ArrayList<IObject> node;

	protected Resources res;
	protected MainThread mainThread;
	protected Context mainContext;
	protected ScreenConfig screenConfig;
	protected boolean enableDraw = true;
	protected Paint paint;



	public IScene(Context r, AttributeSet a)
	{
		super(r, a);
		getHolder().addCallback(this);
		getHolder().setFormat(0x00000004);
		getHolder().setFixedSize(720, 1280);
		setFocusable(true);
		mainContext = r;
		res = getResources();
	}

	public void init(int _sw, int _sh)
	{
		mainThread = AppManager.instance.mainThread;
		screenConfig = new ScreenConfig(_sw, _sh);
		screenConfig.setSize(720, 1280);
		paint = new Paint();
		node = new ArrayList<>();
	}

	public void tick(float _eTime)
	{
		for (IObject o : node)
			o.tick(_eTime);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		float eTime = mainThread.elapsedTime;
		tick(eTime);

		if (!enableDraw)
			return;

		clearCanvas(canvas);
	}

	private void clearCanvas(Canvas _canvas)
	{
		paint.setColor(Color.rgb(0, 0, 0));
		_canvas.drawRect(0, 0, screenConfig.screenWidth, screenConfig.screenHeight, paint);
	}

	public void addNode(IObject _node)
	{
		if (node == null)
			return;
		node.add(_node);
	}

	public void removeNode(IObject _node)
	{
		if (node == null)
			return;
		node.remove(_node);
	}

	public void removeNode(int _index)
	{
		node.remove(_index);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return true;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		AppManager.instance.onSurfaceCreated(this, holder);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
		AppManager.instance.onSurfaceChanged(this, holder, format, width, height);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		AppManager.instance.onSurfaceDestroyed(this, holder);
	}
}
