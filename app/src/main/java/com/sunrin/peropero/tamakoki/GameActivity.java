package com.sunrin.peropero.tamakoki;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity
{
	MainView mainView;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

		int scnW = getWindowManager().getDefaultDisplay().getWidth();
		int scnH = getWindowManager().getDefaultDisplay().getHeight();
		mainView = (MainView)findViewById(R.id.main_view);
		mainView.init(scnW, scnH, this);
    }
}
