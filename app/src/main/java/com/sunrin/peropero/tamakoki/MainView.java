package com.sunrin.peropero.tamakoki;
/*
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
*/
/**
 * Created by Hamsting on 2017-06-05.
 */

public class MainView // extends SurfaceView implements SurfaceHolder.Callback
{
	/*
	MainThread mainThread;
	Handler handler;
	Context mainContext;
	ScreenConfig screenConfig;
	boolean drawCls = false;

	private GameActivity gameActivity;
	private int xx = 0;
	private Paint paint;
	private Bitmap bmpHams;
	private Rect rectHams;



	public MainView(Context r, AttributeSet a)
	{
		super(r, a);
		getHolder().addCallback(this);
		getHolder().setFormat(0x00000004);

		mainThread = new MainThread(getHolder(), this);
		setFocusable(true);
		mainContext = r;
	}

	public void init(int _w, int _h, GameActivity _gameActivity)
	{
		gameActivity = _gameActivity;
		screenConfig = new ScreenConfig(_w, _h);
		screenConfig.setSize(1080, 1920);
		drawCls = true;
		paint = new Paint();


		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = false;
		options.inPreferredConfig = Bitmap.Config.RGB_565;

		bmpHams = BitmapFactory.decodeResource(getResources(), R.drawable.img_knobs, options);
		rectHams = new Rect(0, screenConfig.getY(50), screenConfig.getX(278), screenConfig.getY(337 + 50));
	}

	public void tick(float eTime)
	{
		xx += (int)(1920.0f * 0.2f * eTime);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		float eTime = mainThread.elapsedTime;
		tick(eTime);

		if (!drawCls)
			return;

		clearCanvas(canvas);

		canvas.drawBitmap(bmpHams, null, rectHams, null);
		canvas.drawBitmap(bmpHams, null, rectHams, null);
		canvas.drawBitmap(bmpHams, null, rectHams, null);
		canvas.drawBitmap(bmpHams, null, rectHams, null);
		canvas.drawBitmap(bmpHams, null, rectHams, null);
		canvas.drawBitmap(bmpHams, null, rectHams, null);
		canvas.drawBitmap(bmpHams, null, rectHams, null);
		canvas.drawBitmap(bmpHams, null, rectHams, null);
		canvas.drawBitmap(bmpHams, null, rectHams, null);
		canvas.drawBitmap(bmpHams, null, rectHams, null);
		canvas.drawBitmap(bmpHams, null, rectHams, null);

		String fps = Float.toString(1.0f / eTime);
		paint.setColor(Color.rgb(255, 255, 255));
		canvas.drawText("FPS : " + fps + ", Elapsed : " + eTime, screenConfig.getX(0), screenConfig.getY(50), paint);
		canvas.drawText("Bmp W : " + bmpHams.getWidth() + ", Bmp H : " + bmpHams.getHeight(), screenConfig.getX(0), screenConfig.getY(60), paint);

		super.onDraw(canvas);
	}

	private void clearCanvas(Canvas _canvas)
	{
		paint.setColor(Color.rgb(0, 0, 0));
		_canvas.drawRect(0, 0, screenConfig.screenWidth, screenConfig.screenHeight, paint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return true;
		// return super.onTouchEvent(event);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		mainThread.setRunning(true);
		try
		{
			if (mainThread.getState() == Thread.State.TERMINATED)
			{
				mainThread = new MainThread(getHolder(), this);
				mainThread.setRunning(true);
				setFocusable(true);
				mainThread.start();
			}
			else
				mainThread.start();
		}
		catch (Exception ex)
		{
			Log.i("Mainview", "surfaceCreated -> Exception : " + ex.toString());
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		boolean re = true;
		mainThread.setRunning(false);
		while (re)
		{
			try
			{
				mainThread.join();
				re = false;
			}
			catch (Exception ex)
			{
				Log.i("Mainview", "surfaceDestroyed -> Exception : " + ex.toString());
			}
		}
	}
	*/
}



































