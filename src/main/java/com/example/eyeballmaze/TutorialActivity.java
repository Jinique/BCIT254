package com.example.eyeballmaze;

import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.MediaController;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.eyeballmaze.model.SFX;

public class TutorialActivity extends AppCompatActivity {
    SFX sfx = new SFX();
    Switch soundSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        // Button buttonPlayTutorial = findViewById(R.id.videoButton);

        getWindow().setFormat(PixelFormat.UNKNOWN);

        VideoView myVideoView = findViewById(R.id.videoTutorial);
        String uriPath = "android.resource://com.example.eyeballmaze/" + R.raw.tutorial_video;
        Uri uri = Uri.parse(uriPath);
        myVideoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        myVideoView.setMediaController(mediaController);
        mediaController.setAnchorView(myVideoView);
        // myVideoView.requestFocus();
        myVideoView.start();

        soundSwitch = findViewById(R.id.soundSwitch);
        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // https://stackoverflow.com/questions/13388493/how-can-i-convert-the-android-resources-int-to-a-string-eg-android-r-string-c
                if (isChecked) {
                    // The toggle is enabled
                    Toast.makeText(getApplicationContext(), "Sound " + getString(R.string.on), Toast.LENGTH_SHORT).show();
                    sfx.bgm_start();
                } else {
                    // The toggle is disabled
                    Toast.makeText(getApplicationContext(), "Sound " + getString(R.string.off), Toast.LENGTH_SHORT).show();
                    sfx.bgm_pause();
                }
            }
        });
        sfx.bgm(this, R.raw.tutorial, true);

        /*
        buttonPlayTutorial.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoView myVideoView = findViewById(R.id.videoTutorial);
                String uriPath = "android.resource://com.example.eyeballmaze" + R.raw.tutorial;
                Uri uri = Uri.parse(uriPath);
                myVideoView.setVideoURI(uri);
                myVideoView.requestFocus();
                myVideoView.start();
            }
        });
        */
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
