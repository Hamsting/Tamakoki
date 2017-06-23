package com.sunrin.peropero.tamakoki;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;

/**
 * Created by Hamsting on 2017-06-12.
 * 타마의 관심도와 레벨, 포인트를 관리하고 관심도를 렌더링함.
 */

public class AttentionBar extends IObject
{
	private static final int BARBG_X = 120;
	private static final int BARBG_Y = 20;
	private static final int TEXT_MARGIN = 5;
	private static final int LV_TEXTSIZE = 45;
	private static final int POINT_TEXTSIZE = 45;
	private static final int LEVELUP_BONUSPOINT = 4;
	private static final float MAXUP_PREFIX = 60.0f;

	public int level;
	public int point;
	public int maxAttention;
	public int currentAttention;

	private MainScene mainScene;
	private float percent;
	private Bitmap bmpBg;
	private Bitmap bmpBar;
	private Point bgSize;
	private Point barSize;
	private Point barPadding;
	private Typeface fontNXFG;



	public AttentionBar()
	{
		super();
		mainScene = (MainScene)SceneManager.instance.currentScene;
		fontNXFG = Typeface.createFromAsset(AppManager.instance.gameActivity.getAssets(), "font/NEXONFG.ttf");
		bmpBg = BitmapFactory.decodeResource(res, R.drawable.img_barbg, bmpOptions);
		bmpBar = BitmapFactory.decodeResource(res, R.drawable.img_bar, bmpOptions);
		bgSize = new Point(bmpBg.getWidth(), bmpBg.getHeight());
		barSize = new Point(bmpBar.getWidth(), bmpBar.getHeight());
		barPadding = new Point((bgSize.x - barSize.x) / 2, (bgSize.y - barSize.y) / 2);
		maxAttention = 50;
		currentAttention = 0;
		level = 1;
		point = 0;
	}

	@Override
	public void tick(float _eTime)
	{
		super.tick(_eTime);
		percent = (float)currentAttention / maxAttention;
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

		Paint paint = mainScene.paint;

		paint.setTypeface(fontNXFG);
		paint.setColor(Color.rgb(238, 252, 83));
		paint.setTextSize(LV_TEXTSIZE);
		_canvas.drawText("Lv. ", s.getX(BARBG_X), s.getY(BARBG_Y + bgSize.y + LV_TEXTSIZE + TEXT_MARGIN), paint);
		paint.setColor(Color.rgb(242, 297, 16));
		_canvas.drawText(Integer.toString(level), s.getX(BARBG_X + (int)(LV_TEXTSIZE * 1.8f)), s.getY(BARBG_Y + bgSize.y + LV_TEXTSIZE + TEXT_MARGIN), paint);

		paint.setColor(Color.rgb(16, 215, 242));
		paint.setTextSize(POINT_TEXTSIZE);
		paint.setTextAlign(Paint.Align.RIGHT);
		String pointStr = Integer.toString(point);
		_canvas.drawText("Point : ", s.getX(BARBG_X + bgSize.x - (int)(POINT_TEXTSIZE * 0.6f * pointStr.length())),
									 s.getY(BARBG_Y + bgSize.y + POINT_TEXTSIZE + TEXT_MARGIN), paint);
		paint.setColor(Color.rgb(22, 165, 226));
		_canvas.drawText(pointStr, s.getX(BARBG_X + bgSize.x), s.getY(BARBG_Y + bgSize.y + POINT_TEXTSIZE + TEXT_MARGIN), paint);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setTypeface(Typeface.DEFAULT);
	}

	public boolean addAttention(int _add)
	{
		currentAttention += _add;
		while (currentAttention >= maxAttention)
		{
			currentAttention -= maxAttention;
			++level;
			float maxUp = clamp(1.225f - 0.225f * ((float)level / MAXUP_PREFIX), 1.0f, 1.225f);
			maxAttention  = (int)(maxAttention * maxUp);
			point += LEVELUP_BONUSPOINT;
			if (level == Tama.UPGRADE_LEVEL[mainScene.tama.grade])
				mainScene.tama.upgradeTama();
		}

		float rand = (float)Math.random();
		if (rand <= mainScene.bonusPercent)
		{
			point += mainScene.bonusPoint;
			return true;
		}
		return false;
	}

	private float clamp(float _n, float _min, float _max)
	{
		if (_n < _min)
			return _min;
		else if (_n > _max)
			return _max;
		return _n;
	}
}
