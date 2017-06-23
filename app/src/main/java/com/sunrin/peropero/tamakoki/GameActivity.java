package com.sunrin.peropero.tamakoki;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Hamsting on 2017-06-09.
 * SurfaceView를 그리기 위한 기본 액티비티.
 */

public class GameActivity extends AppCompatActivity
{
	public static final boolean SHOW_FPS = true;
	public SplashScene splashScene;
	public MainScene mainScene;
	public SkillScene skillScene;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		AppManager appManager = new AppManager();

        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);

		splashScene = (SplashScene)findViewById(R.id.start_scene);
		mainScene = (MainScene)findViewById(R.id.main_scene);
		skillScene = (SkillScene)findViewById(R.id.skill_scene);
		appManager.init(this, splashScene);
    }
}
