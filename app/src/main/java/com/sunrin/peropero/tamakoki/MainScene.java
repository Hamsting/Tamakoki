package com.sunrin.peropero.tamakoki;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Hamsting on 2017-06-09.
 * 게임의 진행을 담당하는 메인 씬이다.
 * 인게임의 관리, 렌더링을 한다.
 */

public class MainScene extends IScene
{
	private static final int KAWAII_X = 20;
	private static final int KAWAII_Y = 20;
	private static final int BTNSKILL_X = 720 - 50;
	private static final int BTNSKILL_Y = 150;

	public int attantionDamage;
	public float bonusPercent;
	public int bonusPoint;
	public Tama tama;

	private AttentionBar abar;
	private Bitmap bmpBg;
	private Bitmap bmpKawaii;
	private Point kawaiiSize;
	private ArrayList<Heart> hearts;
	private TamaButton btnGoSkill;
	private float bonusAttentionTimer;
	private float bonusPointTimer;



	public MainScene(Context r, AttributeSet a)
	{
		super(r, a);
	}

	@Override
	public void init()
	{
		super.init();
		tama = new Tama();
		addNode(tama);
		abar = new AttentionBar();
		addNode(abar);
		hearts = new ArrayList<>();

		BitmapFactory.Options ops = AppManager.instance.bmpOptions;
		bmpBg = BitmapFactory.decodeResource(res, R.drawable.img_bg, ops);
		bmpKawaii = BitmapFactory.decodeResource(res, R.drawable.img_kawaii, ops);
		kawaiiSize = new Point(bmpKawaii.getWidth(), bmpKawaii.getHeight());

		btnGoSkill = new TamaButton(R.drawable.img_goskillbtn, new Point(BTNSKILL_X, BTNSKILL_Y), new PointF(1.0f, 0.0f));
		TamaButton.Callback cb = new TamaButton.Callback() {
			@Override
			public void callbackMethod() {
				SceneManager.instance.loadSkillScene();
			}
		};
		btnGoSkill.setCallback(cb);
		btnGoSkill.setPressBitmap(R.drawable.img_goskillbtn_press);
		addNode(btnGoSkill);

		GlobalData g = GlobalData.instance;
		g.loadData();
		abar.level = g.level;
		abar.point = g.point;
		abar.currentAttention = g.cur;
		abar.maxAttention = g.max;
		tama.upgradeTama(g.grade);

		attantionDamage = 10 + g.calculateSkillPower(2, g.skill[2]);
		bonusPercent = 0.02f + (float)g.calculateSkillPower(3, g.skill[3]) * 0.01f;
		bonusPoint = 2;
		bonusAttentionTimer = g.aTimer;
		bonusPointTimer = g.bTimer;
	}

	@Override
	public void tick(float _eTime)
	{
		super.tick(_eTime);
		GlobalData g = GlobalData.instance;

		if (g.skill[0] > 0)
		{
			bonusAttentionTimer += _eTime;
			if (bonusAttentionTimer >= 5.0f) {
				abar.addAttention(g.calculateSkillPower(0, g.skill[0]));
				createRandomHeart(false);
				bonusAttentionTimer -= 5.0f;
			}
		}
		if (g.skill[1] > 0)
		{
			bonusPointTimer += _eTime;
			if (bonusPointTimer >= 20.0f) {
				abar.point += g.calculateSkillPower(1, g.skill[1]);
				createRandomHeart(true);
				bonusPointTimer -= 20.0f;
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		ScreenConfig s = screenConfig;

		Rect rect = new Rect(s.getX(0), s.getY(0), s.getX(720), s.getY(1280));
		canvas.drawBitmap(bmpBg, null, rect, null);

		tama.draw(canvas);

		rect = new Rect(s.getX(KAWAII_X), s.getY(KAWAII_Y),
				s.getX(KAWAII_X + kawaiiSize.x), s.getY(KAWAII_Y + kawaiiSize.y));
		canvas.drawBitmap(bmpKawaii, null, rect, null);

		abar.draw(canvas);

		btnGoSkill.draw(canvas);

		Iterator<Heart> iter = hearts.iterator();
		while (iter.hasNext())
		{
			Heart h = iter.next();
			if (h == null)
				continue;
			h.draw(canvas);
			if (h.remove)
				iter.remove();
			if (!iter.hasNext())
				break;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (!super.onTouchEvent(event))
			return false;

		ScreenConfig s = screenConfig;
		Point pos = new Point(s.screenToVirtualX((int)event.getX()),
				s.screenToVirtualY((int)event.getY()));
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			Rect area = new Rect(s.getX(100), s.getY(1280 - 760),
					s.getX(720 - 100), s.getY(1280 - 130));
			if (area.contains(pos.x, pos.y))
			{
				boolean bonus = abar.addAttention(attantionDamage);
				createRandomHeart(bonus);
			}
			else if (btnGoSkill.hitTest(pos))
				btnGoSkill.onPress();
		}
		else if (event.getAction() == MotionEvent.ACTION_UP)
		{
			if (btnGoSkill.hitTest(pos))
				btnGoSkill.onRelease();
			else
				btnGoSkill.setStateNone();
		}
		return true;
	}

	private void createRandomHeart(boolean _bonus)
	{
		int posx = (int)(Math.random() * 520) + 100;
		int posy = 1080 - (int)(Math.random() * 650);
		Heart h = new Heart(posx, posy, _bonus);
		addNode(h);
		hearts.add(h);
	}

	public void removeHeart(Heart _h)
	{
		hearts.remove(_h);
		_h.setRemove();
	}

	public void saveData()
	{
		GlobalData g = GlobalData.instance;
		g.level = abar.level;
		g.point = abar.point;
		g.grade = tama.grade;
		g.cur = abar.currentAttention;
		g.max = abar.maxAttention;
		g.aTimer = bonusAttentionTimer;
		g.bTimer = bonusPointTimer;
		g.saveData();
	}
}