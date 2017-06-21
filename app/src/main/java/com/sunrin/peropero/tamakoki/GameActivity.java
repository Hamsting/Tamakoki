package com.sunrin.peropero.tamakoki;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Hamsting on 2017-06-09.
 */

public class GameActivity extends AppCompatActivity
{
	public static final boolean SHOW_FPS = true;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		AppManager appManager = new AppManager();

        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);

		SplashScene startScene = (SplashScene)findViewById(R.id.start_scene);
		appManager.init(this, startScene);
    }
}
