package com.sunrin.peropero.tamakoki;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Hamsting on 2017-06-09.
 * 인게임의 캐릭터 타마를 관리하고 렌더링해준다.
 */

public class Tama extends IObject
{
	public static final int[] UPGRADE_LEVEL = {
			10, 30, 60, 99999999
	};

	private static final int TAMA_X = 720 / 2;
	private static final int TAMA_Y = 1280 - 210;

	public int grade;

	private Bitmap[] bmpTama;
	private Point tamaSize;



	public Tama()
	{
		super();
		grade = 0;
		bmpTama = new Bitmap[4];
		bmpTama[0] = BitmapFactory.decodeResource(res, R.drawable.img_tama_0, bmpOptions);
		bmpTama[1] = BitmapFactory.decodeResource(res, R.drawable.img_tama_1, bmpOptions);
		bmpTama[2] = BitmapFactory.decodeResource(res, R.drawable.img_tama_2, bmpOptions);
		bmpTama[3] = BitmapFactory.decodeResource(res, R.drawable.img_tama_3, bmpOptions);
		tamaSize = new Point(bmpTama[0].getWidth(), bmpTama[0].getHeight());
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

		Rect rec = new Rect(s.getX(TAMA_X - tamaSize.x / 2), s.getY(TAMA_Y - tamaSize.y),
							s.getX(TAMA_X + tamaSize.x / 2), s.getY(TAMA_Y));
		_canvas.drawBitmap(bmpTama[grade], null, rec, null);
	}

	public void upgradeTama()
	{
		++grade;
		tamaSize = new Point(bmpTama[grade].getWidth(), bmpTama[grade].getHeight());
	}

	public void upgradeTama(int _grade)
	{
		grade = _grade;
		tamaSize = new Point(bmpTama[grade].getWidth(), bmpTama[grade].getHeight());
	}
}
