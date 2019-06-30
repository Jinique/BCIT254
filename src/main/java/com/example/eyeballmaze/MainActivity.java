package com.example.eyeballmaze;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.eyeballmaze.model.SFX;

public class MainActivity extends AppCompatActivity {

    SFX sfx = new SFX();
    Switch soundSwitch;
    private String selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button exitButton = findViewById(R.id.btn_exit);

        exitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                exitButtonGenerator();
            }
        });

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

        sfx.bgm(this, R.raw.main, true);
    }

    public void onNewGameOtherButtonClick(View v){
        final String[] listItems = this.getResources().getStringArray(R.array.select_lvl_array);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        builder.setTitle(R.string.select_lvl);
        builder.setSingleChoiceItems(R.array.select_lvl_array, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selection = listItems[which];
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Selected Level : " + selection, Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(getBaseContext(),   GameActivityOther.class);
                myIntent.putExtra("GAME_LEVEL", selection);
                startActivity(myIntent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onNewGameManualButtonClick(View v){
        final String[] listItems = this.getResources().getStringArray(R.array.select_lvl_array);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        builder.setTitle(R.string.select_lvl);
        builder.setSingleChoiceItems(R.array.select_lvl_array, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selection = listItems[which];
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Selected Level : " + selection, Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(getBaseContext(),   GameActivityManual.class);
                myIntent.putExtra("GAME_LEVEL", selection);
                startActivity(myIntent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    public  void onNewGameProgrammaticButtonClick(View v){
        final String[] listItems = this.getResources().getStringArray(R.array.select_lvl_array);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        builder.setTitle(R.string.select_lvl);
        builder.setSingleChoiceItems(R.array.select_lvl_array, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selection = listItems[which];
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Selected Level : " + selection, Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(getBaseContext(),   GameActivityProgrammatic.class);
                myIntent.putExtra("GAME_LEVEL", selection);
                startActivity(myIntent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onNewGameManualProtoButtonClick(View v){
        Intent myIntent = new Intent(getBaseContext(),   GameActivityManualProto.class);
        startActivity(myIntent);
    }


    public void onTutorialButtonClick(View v){
        Intent myIntent = new Intent(getBaseContext(),   TutorialActivity.class);
        startActivity(myIntent);
    }

    public void exitButtonGenerator(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are you sure?");
        builder.setTitle("Quit game")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Quit game");
        alert.show();
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