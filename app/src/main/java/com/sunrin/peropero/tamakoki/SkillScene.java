package com.sunrin.peropero.tamakoki;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * Created by Hamsting on 2017-06-22.
 * 스킬의 구입, 강화를 할 수 있는 씬.
 */

public class SkillScene extends IScene
{
	public static final int MAX_SKILL_NUM = 4;

	private static final int BTNMAIN_X = 720 / 2;
	private static final int BTNMAIN_Y = 1280 - 80;
	private static final int POINTTEXT_X = 435;
	private static final int POINTTEXT_Y = 70;
	private static final String[] SKILLNAME = {
			"라그아비", "타이레놀", "관심종자", "아낌없이 퍼주는 달걀"
	};
	private static final int[] SKILLCOST = {
			15, 20, 30, 40
	};
	private static final String[] SKILLPOSTDESC = {
			"5초마다 관심도 +",
			"20초마다 포인트 +",
			"추가 관심도 +",
			"보너스 포인트 확률 +"
	};
	private static final int SKILLDESC_X = 240;
	private static final int[] SKILLDESC_Y = {
			265, 535, 805, 1075
	};

	private Bitmap bmpBg;
	private Bitmap[] bmpSkill;
	private Rect[] skillRect;
	private TamaButton btnGoMain;
	private Typeface fontNXFG;



	public SkillScene(Context r, AttributeSet a)
	{
		super(r, a);
	}

	@Override
	public void init()
	{
		super.init();
		ScreenConfig s = screenConfig;

		BitmapFactory.Options ops = AppManager.instance.bmpOptions;
		fontNXFG = Typeface.createFromAsset(AppManager.instance.gameActivity.getAssets(), "font/NEXONFG.ttf");
		bmpBg = BitmapFactory.decodeResource(res, R.drawable.img_skill_bg, ops);
		bmpSkill = new Bitmap[4];
		bmpSkill[0] = BitmapFactory.decodeResource(res, R.drawable.img_skill_raguabi, ops);
		bmpSkill[1] = BitmapFactory.decodeResource(res, R.drawable.img_skill_tylenol, ops);
		bmpSkill[2] = BitmapFactory.decodeResource(res, R.drawable.img_skill_gwansim, ops);
		bmpSkill[3] = BitmapFactory.decodeResource(res, R.drawable.img_skill_namu, ops);
		skillRect = new Rect[4];
		for (int i = 0; i < 4; ++i)
			skillRect[i] = new Rect(s.getX(70), s.getY(150 + 270 * i), s.getX(70 + 128), s.getY(150 + 270 * i + 128));

		btnGoMain = new TamaButton(R.drawable.img_gomainbtn, new Point(BTNMAIN_X, BTNMAIN_Y), new PointF(0.5f, 0.5f));
		TamaButton.Callback cb = new TamaButton.Callback() {
			@Override
			public void callbackMethod() {
				SceneManager.instance.loadMainScene();
			}
		};
		btnGoMain.setCallback(cb);
		btnGoMain.setPressBitmap(R.drawable.img_gomainbtn_press);
		addNode(btnGoMain);
	}

	@Override
	public void tick(float _eTime)
	{
		super.tick(_eTime);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		ScreenConfig s = screenConfig;
		GlobalData g = GlobalData.instance;

		Rect rect = new Rect(s.getX(0), s.getY(0), s.getX(720), s.getY(1280));
		canvas.drawBitmap(bmpBg, null, rect, null);

		paint.setTypeface(Typeface.MONOSPACE);;
		paint.setTextSize(24);
		paint.setColor(Color.rgb(109, 109, 109));
		String desc;
		for (int i = 0; i < 4; ++i)
		{
			canvas.drawBitmap(bmpSkill[i], null, skillRect[i], null);
			desc = SKILLPOSTDESC[i] + Integer.toString(g.calculateSkillPower(i, g.skill[i])) +
					" -> 강화 후 +" + Integer.toString(g.calculateSkillPower(i, g.skill[i] + 1));
			canvas.drawText(desc, s.getX(SKILLDESC_X), s.getY(SKILLDESC_Y[i]), paint);
		}

		paint.setTypeface(fontNXFG);
		paint.setTextSize(30);
		paint.setColor(Color.rgb(22, 165, 226));
		canvas.drawText(Integer.toString(g.point), s.getX(POINTTEXT_X), s.getY(POINTTEXT_Y), paint);
		paint.setTypeface(Typeface.DEFAULT);

		btnGoMain.draw(canvas);
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
			for (int i = 0; i < 4; ++i)
			{
				if (skillRect[i].contains(pos.x, pos.y))
				{
					showDialog(i);
				}
			}

			if (btnGoMain.hitTest(pos))
				btnGoMain.onPress();
		}
		else if (event.getAction() == MotionEvent.ACTION_UP)
		{
			if (btnGoMain.hitTest(pos))
				btnGoMain.onRelease();
			else
				btnGoMain.setStateNone();
		}
		return true;
	}

	private void showDialog(final int _i)
	{
		String type = "강화";
		if (GlobalData.instance.skill[_i] == 0)
			type = "구입";

		if (GlobalData.instance.point < SKILLCOST[_i])
		{
			AlertDialog.Builder alt = new AlertDialog.Builder(AppManager.instance.gameActivity);
			alt.setMessage("포인트가 부족합니다.").setCancelable(true);
			alt.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id)
				{
					dialog.cancel();
				}
			});
			AlertDialog alert = alt.create();
			alert.setTitle("스킬 " + type);
			alert.show();
			return;
		}

		String msg = SKILLNAME[_i] + "을(를) " + type + "하시겠습니까?\n" +
					 Integer.toString(SKILLCOST[_i]) + " 포인트를 소모합니다.";
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(AppManager.instance.gameActivity);
		alt_bld.setMessage(msg).setCancelable(false);
		alt_bld.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id)
			{
				onBuySkill(_i);
			}
		});
		alt_bld.setNegativeButton("No",	new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id)
			{
				dialog.cancel();
			}
		});
		AlertDialog alert = alt_bld.create();
		alert.setTitle("스킬 " + type);
		alert.show();
	}

	private void onBuySkill(int _i)
	{
		GlobalData g = GlobalData.instance;
		g.skill[_i] += 1;
		g.point -= SKILLCOST[_i];
		g.saveData();
	}
}
