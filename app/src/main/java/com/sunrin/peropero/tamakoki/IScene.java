package com.sunrin.peropero.tamakoki;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Hamsting on 2017-06-09.
 * SurfaceView를 재정의하여 만든 씬의 기본적인 형태.
 * 이 클래스를 상속한 클래스에서 생성자와 tick()과 draw() 등을 재정의해서 사용한다.
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
	protected boolean initialized;



	public IScene(Context r, AttributeSet a)
	{
		super(r, a);
		setVisibility(INVISIBLE);
		getHolder().addCallback(this);
		getHolder().setFormat(0x00000004);
		getHolder().setFixedSize(720, 1280);
		setFocusable(true);
		mainContext = r;
		res = getResources();
		initialized = false;
		Log.e("IScene", "IScene : " + this.getClass().toString());
	}

	public void init()
	{
		mainThread = AppManager.instance.mainThread;
		screenConfig = new ScreenConfig();
		screenConfig.setSize(720, 1280);
		paint = new Paint();
		node = new ArrayList<>();
		initialized = true;
	}

	public void tick(float _eTime)
	{
		if (!initialized)
			return;

		Iterator<IObject> iter = node.iterator();
		while (iter.hasNext())
		{
			IObject o = iter.next();
			if (o == null)
				continue;
			o.tick(_eTime);
			if (o.remove)
				iter.remove();
			if (!iter.hasNext())
				break;
		}
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		if (!initialized)
			return;

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
		if (!initialized)
			return false;
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
