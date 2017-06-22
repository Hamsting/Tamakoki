package com.sunrin.peropero.tamakoki;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

/**
 * Created by Hamsting on 2017-06-22.
 * 인게임에서 사용되는 버튼이다.
 * 콜백 함수를 외부에서 정의하여 사용한다.
 * 안드로이드 내장 Button 클래스와 이름이 겹쳐 자동완성 등이 불편할 수 있어 TamaButton으로 이름을 지었다.
 */

public class TamaButton extends IObject
{
	public static final int STATE_NONE = 0;
	public static final int STATE_PRESS = 1;
	public static final int STATE_RELEASE = 2;

	public Point pos;
	public PointF pivot;

	private Bitmap bmpBtn;
	private Bitmap bmpBtnPress;
	private Point btnSize;
	private Point btnPressSize;
	private Callback callback;
	private int state;
	private boolean hasPressState;


	public TamaButton(int _resId, Point _pos)
	{
		super();
		bmpBtn = BitmapFactory.decodeResource(res, _resId, bmpOptions);
		btnSize = new Point(bmpBtn.getWidth(), bmpBtn.getHeight());
		pos = _pos;
		pivot = new PointF(0.0f, 0.0f);
		callback = null;
		state = STATE_NONE;
		hasPressState = false;
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
		if (state == STATE_RELEASE)
			state = STATE_NONE;
	}

	@Override
	public void draw(Canvas _canvas)
	{
		super.draw(_canvas);
		ScreenConfig s = SceneManager.instance.currentScene.screenConfig;

		if (hasPressState && state == STATE_PRESS)
		{
			Rect rec = new Rect(s.getX(pos.x - (int) (btnPressSize.x * pivot.x)), s.getY(pos.y - (int) (btnPressSize.y * pivot.y)),
					s.getX(pos.x + (int) (btnPressSize.x * (1.0f - pivot.x))), s.getY(pos.y + (int) (btnPressSize.y * (1.0f - pivot.y))));
			_canvas.drawBitmap(bmpBtnPress, null, rec, null);
		}
		else
		{
			Rect rec = new Rect(s.getX(pos.x - (int) (btnSize.x * pivot.x)), s.getY(pos.y - (int) (btnSize.y * pivot.y)),
					s.getX(pos.x + (int) (btnSize.x * (1.0f - pivot.x))), s.getY(pos.y + (int) (btnSize.y * (1.0f - pivot.y))));
			_canvas.drawBitmap(bmpBtn, null, rec, null);
		}
	}

	public void setPressBitmap(int _resId)
	{
		hasPressState = true;
		bmpBtnPress = BitmapFactory.decodeResource(res, _resId, bmpOptions);
		btnPressSize = new Point(bmpBtnPress.getWidth(), bmpBtnPress.getHeight());
	}

	public int getState()
	{
		return state;
	}

	public void setStateNone()
	{
		state = STATE_NONE;
	}

	public void OnPress()
	{
		state = STATE_PRESS;
	}

	public void OnRelease()
	{
		if (state != STATE_PRESS)
			return;

		state = STATE_RELEASE;
		callback.callbackMethod();
	}

	public boolean hitTest(Point _t)
	{
		Rect rec;
		ScreenConfig s = SceneManager.instance.currentScene.screenConfig;
		if (hasPressState && state == STATE_PRESS)
			rec = new Rect(s.getX(pos.x - (int) (btnPressSize.x * pivot.x)), s.getY(pos.y - (int) (btnPressSize.y * pivot.y)),
					s.getX(pos.x + (int) (btnPressSize.x * (1.0f - pivot.x))), s.getY(pos.y + (int) (btnPressSize.y * (1.0f - pivot.y))));
		else
			rec = new Rect(s.getX(pos.x - (int) (btnSize.x * pivot.x)), s.getY(pos.y - (int) (btnSize.y * pivot.y)),
					s.getX(pos.x + (int) (btnSize.x * (1.0f - pivot.x))), s.getY(pos.y + (int) (btnSize.y * (1.0f - pivot.y))));
		if (rec.contains(_t.x, _t.y))
			return true;
		return false;
	}

	interface Callback
	{
		void callbackMethod();
	}

	public void setCallback(Callback _callback)
	{
		callback = _callback;
	}
}
