package com.sunrin.peropero.tamakoki;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Hamsting on 2017-06-22.
 * 인게임에 쓰이게 될 변수들을 담은 클래스이다.
 */

public class GlobalData
{
	public static GlobalData instance;

	public int level;
	public int point;
	public int grade;
	public int cur;
	public int max;
	public float aTimer;
	public float bTimer;
	public int[] skill;



	public GlobalData()
	{
		if (instance == null)
			instance = this;

		loadData();
	}

	public void saveData()
	{
		SharedPreferences sharedPreference =
				AppManager.instance.gameActivity.getSharedPreferences("SavedData", Context.MODE_MULTI_PROCESS | Context.MODE_WORLD_READABLE);
		SharedPreferences.Editor editor = sharedPreference.edit();
		editor.putInt("Level", level);
		editor.putInt("Point", point);
		editor.putInt("Grade", grade);
		editor.putInt("Current", cur);
		editor.putInt("Max", max);
		editor.putFloat("AttentionTimer", aTimer);
		editor.putFloat("BonusPointTimer", bTimer);

		int m = SkillScene.MAX_SKILL_NUM;
		for (int i = 0; i < m; ++i)
			editor.putInt("Skill_" + Integer.toString(i), skill[i]);
		editor.commit();
	}

	public void loadData()
	{
		SharedPreferences pref =
				AppManager.instance.gameActivity.getSharedPreferences("SavedData", Context.MODE_MULTI_PROCESS | Context.MODE_WORLD_READABLE);
		level = pref.getInt("Level", 1);
		point = pref.getInt("Point", 0);
		grade = pref.getInt("Grade", 0);
		cur = pref.getInt("Current", 0);
		max = pref.getInt("Max", 50);
		aTimer = pref.getFloat("AttentionTimer", 0.0f);
		bTimer = pref.getFloat("BonusPointTimer", 0.0f);

		int m = SkillScene.MAX_SKILL_NUM;
		skill = new int[m];
		for (int i = 0; i < m; ++i)
			skill[i] = pref.getInt("Skill_" + Integer.toString(i), 0);
	}

	public void resetData()
	{
		level = 1;
		point = 0;
		grade = 0;
		cur = 0;
		max = 50;
		aTimer = 0.0f;
		bTimer = 0.0f;

		int m = SkillScene.MAX_SKILL_NUM;
		skill = new int[m];
		for (int i = 0; i < m; ++i)
			skill[i] = 0;

		saveData();
	}

	public int calculateSkillPower(int _i, int _lv)
	{
		if (_lv == 0)
			return 0;

		switch (_i)
		{
			case 0 :
				return 150 + 100 * (_lv - 1);
			case 1 :
				return 1 + (_lv - 1);
			case 2 :
				return 4 + 3 * (_lv - 1);
			case 3 :
				return 1 + (_lv - 1);
		}
		return 0;
	}
}
