package com.example.eyeballmaze.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.example.eyeballmaze.R;

public class SplashScreen extends View
{
    private Bitmap backgroundImage;
    // private Bitmap titleImage;
    private Paint scorePaint1 = new Paint();
    private Paint scorePaint2 = new Paint();


    public SplashScreen(Context context) {
        super(context);

        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.splash);
        //titleImage = BitmapFactory.decodeResource(getResources(), R.drawable.title);

        scorePaint1.setColor(Color.WHITE);
        scorePaint1.setTextSize(120);
        scorePaint1.setAntiAlias(true);

        scorePaint2.setColor(Color.WHITE);
        scorePaint2.setTextSize(70);
        scorePaint2.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        canvas.drawBitmap(backgroundImage, 0, 0, null);

        //canvas.drawBitmap(titleImage, 0, 0, scorePaint);
        canvas.drawText(getResources().getString(R.string.app_name), 100, 250, scorePaint1);
        canvas.drawText(getResources().getString(R.string.loading), 150, 500, scorePaint2);
    }
}
