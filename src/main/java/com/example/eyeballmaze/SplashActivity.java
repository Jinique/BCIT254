package com.example.eyeballmaze;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.eyeballmaze.model.SFX;
import com.example.eyeballmaze.model.SplashScreen;

public class SplashActivity extends AppCompatActivity {
    SplashScreen splashScreen;
    SFX sfx = new SFX();
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        splashScreen = new SplashScreen(this);
        setContentView(splashScreen);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3*1000);

        sfx.bgm(this, R.raw.splash, true);
    }

    public void goMainMenu(View view) {
        Intent intentStart = new Intent(this, MainActivity.class);
        startActivity(intentStart);
        finish();
    }

    public void onDestroy()
    {
        sfx.bgm_stop();
        super.onDestroy();
    }
    public void onUserLeaveHint()
    {
        sfx.bgm_pause();
        super.onUserLeaveHint();
    }

    public void onResume()
    {
        sfx.bgm_start();
        super.onResume();
    }

    public void onBackPressed()
    {
        sfx.bgm_stop();
        super.onBackPressed();
    }

}