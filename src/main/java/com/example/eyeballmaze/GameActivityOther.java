package com.example.eyeballmaze;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.eyeballmaze.model.SFX;


public class GameActivityOther extends AppCompatActivity {
    SFX sfx = new SFX();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_other);

    }
}
