package com.example.eyeballmaze;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eyeballmaze.model.Drawable;
import com.example.eyeballmaze.model.Model;
import com.example.eyeballmaze.model.SFX;

import java.util.Locale;


public class GameActivityTomV extends AppCompatActivity {

    // COMPOSITIONS
    SFX sfx = new SFX();
    Model model = new Model();
    Drawable drawable = new Drawable();
    private Handler handler = new Handler();

    // TOP BAR
    private TextView move_counter;
    private TextView goal_counter;
    Switch soundSwitch;

    // BOTTOM BAR
    private Button btnReset;
    private Button btnSolution;
    private Button btnUndo;
    private Button btnExit;

    // Map image layout
    ImageView[][] imageViews = new ImageView[4][6];
    int[][] imageSrc = new int[4][6];

    private ConstraintLayout completeSplash;
    private ConstraintLayout failedSplash;
    private ConstraintLayout botBar;
    private TextView completeMessage;
    private String selection;

    // game state
    private String currentLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_manual);

        move_counter = findViewById(R.id.move_counter2);
        goal_counter = findViewById(R.id.goal_counter2);
        soundSwitch = findViewById(R.id.soundSwitch);

        btnReset = findViewById(R.id.btn_reset);
        btnSolution = findViewById(R.id.btn_solution);
        btnUndo = findViewById(R.id.btn_undo);
        btnExit = findViewById(R.id.btn_exit);

        botBar = findViewById(R.id.botBar);
        completeSplash = findViewById(R.id.completeSplash);
        completeMessage = findViewById(R.id.completeMessage2);
        failedSplash = findViewById(R.id.failedSplash);

        run();
    }

    //////////////////       level selection       //////////////////
    private void run(){
        bgmSwitch();
        //sfx.bgm_stop();
        setLevel(getIntent().getStringExtra("GAME_LEVEL"));
        handler.removeCallbacksAndMessages(null);
    }

    //////////////////       BGM control       //////////////////
    private void bgmSwitch(){
        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), "Sound " + getString(R.string.on), Toast.LENGTH_SHORT).show();
                    sfx.bgm_start();
                } else {
                    Toast.makeText(getApplicationContext(), "Sound " + getString(R.string.off), Toast.LENGTH_SHORT).show();
                    sfx.bgm_pause();
                }
            }
        });
    }

    public void onDestroy() {
        sfx.bgm_stop();
        super.onDestroy();
    }
    public void onUserLeaveHint() {
        sfx.bgm_pause();
        super.onUserLeaveHint();
    }

    public void onResume() {
        sfx.bgm_start();
        super.onResume();
    }

    public void onBackPressed() {
        sfx.bgm_stop();
        super.onBackPressed();
    }

    //////////////////       set level       //////////////////
    private void setLevel(String levelNumber){
        sfx.bgm(this, R.raw.game, true);
        showCompleteSplash(false);
        showFailedSplash(false);
        setUndoBtn(false);
        model.setLevel(levelNumber);
        currentLevel = levelNumber;
        setMap();
        drawGoal();
        drawPlayer(model.getPlayerX(), model.getPlayerY());
        update();
        //countdown();
    }

    private void countdown(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                model.setLoseState(true);
                stageFailed();
            }
        },86*1000);
    }

    private void setMap(){
        for (int i = 0; i < model.getMapY(); i++){
            for (int j = 0; j < model.getMapX(); j++){
                String viewId = String.format(Locale.ENGLISH, "gameTile%d%d", j, i);
                int resId = getResources().getIdentifier(viewId, "id", getPackageName());
                imageViews[j][i] = findViewById(resId);
                imageViews[j][i].setClickable(true);
                imageSrc[j][i] = findImageSrc(j, i);
                imageViews[j][i].setImageResource(imageSrc[j][i]);
            }
        }
        model.setWinState(false);
    }

    private int findImageSrc(int x, int y){
        String value;
        String[] temp = model.getMap()[y][x].split("");
        value = temp[1]+temp[2];
        return drawable.getDrawable(value);
    }

    private void drawGoal(){
        for (int i = 0; i < model.getMapY(); i++) {
            for (int j = 0; j < model.getMapX(); j++) {
                String[] temp = model.getMap()[i][j].split("");
                if (temp[4].equals("G")) {
                    setDoubleImageInMaze(j, i, drawable.getDrawable("G"));
                }
            }
        }
        setDoubleImageInMaze(model.getGoalX(), model.getGoalY(), drawable.getDrawable("G"));
    }

    private void drawPlayer(int x, int y){
        if (model.getMoveCount() == 0){
            setDoubleImageInMaze(x, y, drawable.getDrawable(model.getPlayerDirection()));
        } else {
            setDoubleImageInMaze(x, y, drawable.getDrawable(model.calcPlayerDirection()));
        }
    }

    private void drawOriginalBlock (){
        imageViews[model.getPlayerX()][model.getPlayerY()].setImageBitmap(BitmapFactory.decodeResource(getResources(), imageSrc[model.getPlayerX()][model.getPlayerY()]));
    }

    private void setDoubleImageInMaze(int col, int row, int src) {
        Bitmap image1 = BitmapFactory.decodeResource(getResources(), imageSrc[col][row]);
        Bitmap image2 = BitmapFactory.decodeResource(getResources(), src);
        Bitmap mergedImages = createSingleImageFromMultipleImages(image1, image2);
        imageViews[col][row].setImageBitmap(mergedImages);
    }

    private Bitmap createSingleImageFromMultipleImages(Bitmap firstImage, Bitmap secondImage) {
        Bitmap result = Bitmap.createBitmap(secondImage.getWidth(), secondImage.getHeight(), secondImage.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(firstImage, 0, 0, null);
        canvas.drawBitmap(secondImage, 0, 0, null);
        return result;
    }

    private void update(){
        updateMoveCount();
        updateGoalCount();
    }

    private void updateMoveCount(){
        move_counter.setText(String.valueOf(model.getMoveCount()));
    }

    private void updateGoalCount(){
        goal_counter.setText(String.valueOf(model.getGoalCount()));
    }

    //////////////////       onClickMove       //////////////////
    public void onClickToMove(View v) {
        model.saveUndo();
        if (!model.getWinState() && !model.getLoseState()){
            ImageView nextImageView = (ImageView) v;
            updateTarget(nextImageView);
            if (checkAll()){
                makeMove();
            }
        } else if (!model.getLoseState()){
            endGameOptions();
        } else {
            failGameOptions();
        }
    }

    private boolean checkAll(){
        int errorCount;
        boolean result = false;
        errorCount = model.checkAll();
        if (errorCount != 0){
            displayError(errorCount);
        } else {
            result = true;
        }
        return result;
    }

    public void updateTarget(ImageView nextImageView) {
        String targetPosition = String.valueOf(getLocationImageView(nextImageView));
        model.updateTarget(targetPosition);
    }

    public String getLocationImageView(ImageView imageView) {
        String name = getResources().getResourceEntryName(imageView.getId());
        name = name.replace("gameTile", "");
        return name;
    }

    public void makeMove() {
        drawOriginalBlock();
        drawPlayer(model.getTargetX(), model.getTargetY());
        model.updatePlayer();
        model.changeMoveCount(1);
        model.checkPlayerVsGoal();
        setUndoBtn(true);
        update();
        if (model.getWinState()){
            stageClear();
        } else if (model.getLoseState()){
            model.setLoseState(true);
            stageFailed();
        }
    }

    private void stageClear() {
        sfx.bgm_stop();
        showCompleteSplash(true);
        completeMessage.setText(model.getMoveCount() + " moves");
        sfx.bgm(this, R.raw.win, false);
    }

    private void stageFailed(){
        sfx.bgm_stop();
        showFailedSplash(true);
        sfx.bgm(this, R.raw.lose, false);
    }

    public void onClickReset(View v){
        sfx.bgm_stop();
        setLevel(currentLevel);
    }

    public void onClickSolution(View v){
        solve();
    }

    public void onClickUndo(View v){
        drawOriginalBlock();
        model.setPlayer(model.getLastPlayerX(), model.getLastPlayerY(), model.getLastPlayerDirection());
        model.changeMoveCount(-1);
        setDoubleImageInMaze(model.getPlayerX(), model.getPlayerY(), drawable.getDrawable(model.getPlayerDirection()));
        update();
        setUndoBtn(false);
    }

    private void setUndoBtn(boolean b){
        btnUndo.setEnabled(b);
    }

    public void onClickExit(View v){
        exit();
    }

    private void exit(){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage("Are you sure?\n" +
                "all your game data will be lost!");
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
        android.support.v7.app.AlertDialog alert = builder.create();
        alert.setTitle("Quit game");
        alert.show();
    }


    //////////////////       checkers       //////////////////
    public void endGameOptions(){
        final String[] listItems = this.getResources().getStringArray(R.array.gameover_array);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        builder.setTitle(R.string.select_next);
        builder.setSingleChoiceItems(R.array.gameover_array, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selection = listItems[which];
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sfx.bgm_stop();
                if(selection.equals("Replay")){
                    setLevel(currentLevel);
                } else if(selection.equals("Next Level")){
                    setLevel(toNextLevel());
                } else {
                    exit();
                }

                Toast.makeText(GameActivityTomV.this, selection, Toast.LENGTH_SHORT).show();
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

    public void failGameOptions(){
        final String[] listItems = this.getResources().getStringArray(R.array.gamelose_array);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        builder.setTitle(R.string.select_next);
        builder.setSingleChoiceItems(R.array.gamelose_array, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selection = listItems[which];
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sfx.bgm_stop();
                if(selection.equals("Replay")){
                    setLevel(currentLevel);
                } else {
                    exit();
                }

                Toast.makeText(GameActivityTomV.this, selection, Toast.LENGTH_SHORT).show();
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

    private String toNextLevel(){
        final String[] items = getResources().getStringArray(R.array.select_lvl_array);
        String newLevel = "";
        for (int i = 0; i < items.length; i++){
            if (currentLevel.equals(items[i])){
                newLevel = items[i+1];
                alert(newLevel);
            }
        }
        return newLevel;
    }

    //////////////////       MISC       //////////////////
    private void showCompleteSplash(boolean b){
        if (b){
            botBar.setVisibility(View.INVISIBLE);
            completeSplash.setVisibility(View.VISIBLE);
        } else {
            botBar.setVisibility(View.VISIBLE);
            completeSplash.setVisibility(View.INVISIBLE);
        }
    }

    private void showFailedSplash(boolean b){
        if (b){
            botBar.setVisibility(View.INVISIBLE);
            failedSplash.setVisibility(View.VISIBLE);
        } else {
            botBar.setVisibility(View.VISIBLE);
            failedSplash.setVisibility(View.INVISIBLE);
        }
    }

    //////////////////       MISC       //////////////////
    public void displayError(int errorCount) {
        switch (errorCount) {
            case 1:
                alert("You can only move front, right or left. NO diagonal or backward movement!");
                break;
            case 2:
                alert("Have to be SAME SHAPE or COLOUR");
                break;
            default:
                alert("unknown");
        }
    }

    public void alert(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void solve(){
        sfx.bgm_stop();
        setLevel(currentLevel);
        switch (currentLevel){
            case "levelOne":
                solveOne();
                break;
            case "levelTwo":
                solveTwo();
                break;
            default:
                alert("no solution available");
                break;
        }
    }

    private void solveOne(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {imageViews[1][3].performClick();}},1000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {imageViews[3][3].performClick();}},2000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {imageViews[3][1].performClick();}},3000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {imageViews[0][1].performClick();}},4000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {imageViews[0][4].performClick();}},5000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {imageViews[2][4].performClick();}},6000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {imageViews[2][0].performClick();}},7000);
    }

    private void solveTwo(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {imageViews[1][4].performClick();}},1000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {imageViews[1][3].performClick();}},2000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {imageViews[2][3].performClick();}},3000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {imageViews[2][0].performClick();}},4000);
    }
}
