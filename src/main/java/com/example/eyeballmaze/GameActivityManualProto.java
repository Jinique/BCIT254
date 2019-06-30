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

import com.example.eyeballmaze.model.SplashScreen;
import com.example.eyeballmaze.model.SFX;

public class GameActivityManualProto extends AppCompatActivity {

    SFX sfx = new SFX();
    Switch soundSwitch;

    private int mapX;
    private int mapY;

    ImageView[][] imageViews = new ImageView[4][6];
    int[][] imageSrc = new int[4][6];

    boolean gameOver = false;

    private int numberOfGoals;
    private int goalX;
    private int goalY;

    private int numberOfMoves;

    private String playerDirection;
    private int playerX;
    private int playerY;
    private String playerColour;
    private String playerShape;

    private String lastPlayerDirection;
    private int lastPlayerX;
    private int lastPlayerY;

    private int targetX;
    private int targetY;
    private String targetColour;
    private String targetShape;

    private TextView goal_counter;
    private TextView move_counter;
    private TextView completeMessage;

    private Button btnReset;
    private Button btnUndo;
    private Button btnSave;
    private Button btnExit;

    private ConstraintLayout completeSplash;
    private ConstraintLayout botBar;

    private SplashScreen splashScreen;

    final Handler handler = new Handler();

    public static String[][] map =
            {
                    {"    ", "    ", "FR G", "    "}, // 00 10 20 30
                    {"CC  ", "FY  ", "DY  ", "CG  "}, // 01 11 21 31
                    {"FG  ", "SR  ", "SG  ", "DY  "}, // 02 12 22 32
                    {"FR  ", "FC  ", "SR  ", "FG  "}, // 03 13 23 33
                    {"SC  ", "DR  ", "FC  ", "DC  "}, // 04 14 24 34
                    {"    ", "DCU ", "    ", "    "}  // 05 15 25 35
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_manual);

        btnReset = findViewById(R.id.btn_reset);
        btnSave = findViewById(R.id.btn_save);
        btnUndo = findViewById(R.id.btn_undo);
        btnExit = findViewById(R.id.btn_exit);

        completeSplash = findViewById(R.id.completeSplash);
        botBar = findViewById(R.id.botBar);

        goal_counter = findViewById(R.id.goal_counter2);
        move_counter = findViewById(R.id.move_counter2);

        completeMessage = findViewById(R.id.completeMessage2);

        makeMap();

        imageViews[0][0] = findViewById(R.id.gameTile00);
        imageViews[1][0] = findViewById(R.id.gameTile10);
        imageViews[2][0] = findViewById(R.id.gameTile20);
        imageViews[3][0] = findViewById(R.id.gameTile30);

        imageViews[0][1] = findViewById(R.id.gameTile01);
        imageViews[1][1] = findViewById(R.id.gameTile11);
        imageViews[2][1] = findViewById(R.id.gameTile21);
        imageViews[3][1] = findViewById(R.id.gameTile31);

        imageViews[0][2] = findViewById(R.id.gameTile02);
        imageViews[1][2] = findViewById(R.id.gameTile12);
        imageViews[2][2] = findViewById(R.id.gameTile22);
        imageViews[3][2] = findViewById(R.id.gameTile32);

        imageViews[0][3] = findViewById(R.id.gameTile03);
        imageViews[1][3] = findViewById(R.id.gameTile13);
        imageViews[2][3] = findViewById(R.id.gameTile23);
        imageViews[3][3] = findViewById(R.id.gameTile33);

        imageViews[0][4] = findViewById(R.id.gameTile04);
        imageViews[1][4] = findViewById(R.id.gameTile14);
        imageViews[2][4] = findViewById(R.id.gameTile24);
        imageViews[3][4] = findViewById(R.id.gameTile34);

        imageViews[0][5] = findViewById(R.id.gameTile05);
        imageViews[1][5] = findViewById(R.id.gameTile15);
        imageViews[2][5] = findViewById(R.id.gameTile25);
        imageViews[3][5] = findViewById(R.id.gameTile35);

        imageSrc[0][0] = R.drawable.empty_block;
        imageSrc[1][0] = R.drawable.empty_block;
        imageSrc[2][0] = R.drawable.red_flower;
        imageSrc[3][0] = R.drawable.empty_block;

        imageSrc[0][1] = R.drawable.cyan_cross;
        imageSrc[1][1] = R.drawable.yellow_flower;
        imageSrc[2][1] = R.drawable.yellow_diamond;
        imageSrc[3][1] = R.drawable.green_cross;

        imageSrc[0][2] = R.drawable.green_flower;
        imageSrc[1][2] = R.drawable.red_star;
        imageSrc[2][2] = R.drawable.green_star;
        imageSrc[3][2] = R.drawable.yellow_diamond;

        imageSrc[0][3] = R.drawable.red_flower;
        imageSrc[1][3] = R.drawable.cyan_flower;
        imageSrc[2][3] = R.drawable.red_star;
        imageSrc[3][3] = R.drawable.green_flower;

        imageSrc[0][4] = R.drawable.cyan_star;
        imageSrc[1][4] = R.drawable.red_diamond;
        imageSrc[2][4] = R.drawable.cyan_flower;
        imageSrc[3][4] = R.drawable.cyan_diamond;

        imageSrc[0][5] = R.drawable.empty_block;
        imageSrc[1][5] = R.drawable.cyan_diamond;
        imageSrc[2][5] = R.drawable.empty_block;
        imageSrc[3][5] = R.drawable.empty_block;

        sfx.bgm(this, R.raw.game, false);

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

        run();
    }

    public void onClickUndo(View v){
        imageViews[playerX][playerY].setImageBitmap(BitmapFactory.decodeResource(getResources(), imageSrc[playerX][playerY]));
        playerDirection = lastPlayerDirection;
        playerX = lastPlayerX;
        playerY = lastPlayerY;
        changeMoveCount(-1);
        setDoubleImageInMaze(playerX, playerY, convertDirectionStringToInt(playerDirection));
        updatePlayer(playerX, playerY, playerDirection);
        setUndo(false);
    }

    public int convertDirectionStringToInt(String playerDirection){
        int direction = 0;
        switch (playerDirection){
            case "U":
                direction = R.drawable.eyeball_up;
                break;
            case "D":
                direction = R.drawable.eyeball_down;
                break;
            case "L":
                direction = R.drawable.eyeball_left;
                break;
            case "R":
                direction = R.drawable.eyeball_right;
                break;
        }
        return direction;
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

    public void makeMap() {
        setMapSize(map);
        // setImageViews();
    }

    public void setMapSize(String[][] map) {
        mapY = map.length;
        String[] temp = map[0][0].split("");
        mapX = temp.length;
    }

    /*
    // auto gen of maps to its resource NOT WORKING ATM
    public void setImageViews() {
        for (int i = 0; i > mapY; i++) {
            for (int j = 0; j > mapX; i++) {
                String viewId = "R.id.gameTile" + j + i;
                imageViews[j][i] = findViewById(Integer.parseInt(viewId));
            }
        }
    }
    */

    public void run() {
        showCompleteSplash(false);
        resetStage(4, 6);
    }

    public void resetStage(int x, int y) {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                imageViews[j][i].setImageResource(imageSrc[j][i]);
            }
        }

        setMoveCount(0);
        setGoalCount(1);
        goalX = 2;
        goalY = 0;
        updatePlayer(1, 5, "U");

        setDoubleImageInMaze(2, 0, R.drawable.goal);
        setDoubleImageInMaze(1, 5, R.drawable.eyeball_up);
        //setPlayerX(1);
        //setPlayerY(5);
        //setPlayerDirection("U");
        // playerColour = findColour(playerX, playerY);
        setUndo(false);
    }

    public void setStageOne(){
        for (int i = 0; i < mapY; i++) {
            for (int j = 0; j < mapX; j++) {
                imageViews[j][i].setImageResource(imageSrc[j][i]);
            }
        }
        setMoveCount(0);
        setGoalCount(1);

        goalX = 2;
        goalY = 0;
        updatePlayer(1, 5, "U");

        setDoubleImageInMaze(goalX, goalY, R.drawable.goal);
        setDoubleImageInMaze(playerX, playerY, R.drawable.eyeball_up);
    }

    public void onClickReset(View v){
        run();
    }

    public void onClickExit(View v){
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

    public void onClickSave(View v){
        alert("save feature is not available at the moment. \n" +
                "Thank you for your support.");
    }

    public void checkPlayerVsGoal(){
        if ((playerX == goalX) && (playerY == goalY)) changeGoalCount(-1);
    }

    public void setMoveCount(int i) {
        numberOfMoves = i;
        move_counter.setText("" + numberOfMoves);
    }

    public void changeMoveCount(int i) {
        numberOfMoves = numberOfMoves + i;
        setMoveCount(numberOfMoves);
    }

    public void setGoalCount(int i) {
        numberOfGoals = i;
        goal_counter.setText("" + numberOfGoals);
    }

    public void changeGoalCount(int i) {
        numberOfGoals = numberOfGoals + i;
        setGoalCount(numberOfGoals);
        if (numberOfGoals == 0) {
            setGameOver(true);
            stageClear();
        }
    }



    public void setGameOver(boolean i) {
        gameOver = i;
    }
    public boolean getGameOver() {
        return gameOver;
    }

    public void stageClear() {
        sfx.bgm_stop();
        //SFX sfxTwo = new SFX();
        showCompleteSplash(true);

        completeMessage.setText(numberOfMoves + " moves");
        sfx.bgm(this, R.raw.win, false);
    }

    public void showCompleteSplash(boolean b){
        if (b){
            botBar.setVisibility(View.INVISIBLE);
            completeSplash.setVisibility(View.VISIBLE);
        } else {
            botBar.setVisibility(View.VISIBLE);
            completeSplash.setVisibility(View.INVISIBLE);
        }
    }

    public void onClickToMove(View v) {
        if (!getGameOver()) {
            ImageView nextImageView = (ImageView) v;
            updateTarget(nextImageView);
            if (checkAll()) {
                saveUndo();
                makeMove();
                checkPlayerVsGoal();
            }
        } else {
            alert("Stage Clear\n" +
                    "going to Main Menu after 5 seconds");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 5000);
        }

    }

    public void makeMove() {
        imageViews[playerX][playerY].setImageBitmap(BitmapFactory.decodeResource(getResources(), imageSrc[playerX][playerY]));
        setDoubleImageInMaze(targetX, targetY, calcPlayerDirection());
        updatePlayer(targetX, targetY, playerDirection);
        changeMoveCount(1);
    }

    public void updateTarget(ImageView nextImageView) {
        String targetPosition = String.valueOf(getLocationImageView(nextImageView));
        targetX = Character.digit(targetPosition.charAt(0), 10);
        targetY = Character.digit(targetPosition.charAt(1), 10);
        targetColour = findColour(targetX, targetY);
        targetShape = findShape(targetX, targetY);
    }

    public void updatePlayer(int x, int y, String direction) {
        playerX = x;
        playerY = y;
        playerDirection = direction;
        playerShape = findShape(playerX, playerY);
        playerColour = findColour(playerX, playerY);
    }

    public boolean checkAll() {
        int errorCount = 0;
        boolean result = false;

        if (checkCoOrd()) {
            if (checkShape() || checkColour()) {
                result = true;
            } else {
                if (!checkShape() || !checkColour()) errorCount = 2;
            }
        } else {
            errorCount = 1;
        }

        if (errorCount != 0) displayError(errorCount);
        return result;
    }

    public void displayError(int errorCount) {
        switch (errorCount) {
            case 1:
                alert("You can only move front, right or left. no diagonal!");
                break;
            case 2:
                alert("Have to be SAME SHAPE or COLOUR");
                break;
            default: }
    }

    public boolean checkShape() {
        boolean result = false;
        if (playerShape.equals(targetShape)) result = true;
        return result;
    }

    public boolean checkColour() {
        boolean result = false;
        if (playerColour.equals(targetColour)) result = true;
        return result;
    }

    public boolean checkCoOrd() {
        //String direction = playerDirection;
        boolean result = false;
        switch (playerDirection) {
            case "U":
                if (((playerY > targetY) && (playerX == targetX)) ||
                        ((playerY == targetY) && (playerX != targetX))){
                    result = true;
                }
                break;
            case "D":
                if (((playerY < targetY) && (playerX == targetX)) ||
                        ((playerY == targetY) && (playerX != targetX))) {
                    result = true;
                }
                break;
            case "L":
                if (((playerX > targetX) && (playerY == targetY)) ||
                        ((playerX == targetX) && (playerY != targetY))) {
                    result = true;
                }
                break;
            case "R":
                if (((playerX < targetX) && (playerY == targetY)) ||
                        ((playerX == targetX) && (playerY != targetY))){
                    result = true;
                }
                break;
        }
        if (!result) displayError(4);
        return result;
    }



    public String findShape(int x, int y) {
        String value;
        String[] temp = map[y][x].split("");
        value = temp[1];
        //alert("shape is " + value);
        return value;
    }

    public String findColour(int x, int y) {
        String value;
        String[] temp = map[y][x].split("");
        value = temp[2];
        //alert("colour is " + value);
        return value;
    }

    public int calcPlayerDirection() {
        if (playerX == targetX) {
            if (playerY > targetY) {
                setPlayerDirection("U");
                return R.drawable.eyeball_up;
            } else {
                setPlayerDirection("D");
                return R.drawable.eyeball_down;
            }
        } else {
            if (playerX > targetX) {
                setPlayerDirection("L");
                return R.drawable.eyeball_left;
            } else {
                setPlayerDirection("R");
                return R.drawable.eyeball_right;
            }
        }
    }

    public String getLocationImageView(ImageView imageView) {
        // https://stackoverflow.com/questions/10137692/how-to-get-resource-name-from-resource-id
        String name = getResources().getResourceEntryName(imageView.getId());
        name = name.replace("gameTile", "");
        //return Integer.parseInt(name);
        return name;
    }

    public void setDoubleImageInMaze(int col, int row, int src) {
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

    public void setPlayerDirection(String s) {
        playerDirection = s;
    }

    private void alert(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(GameActivityManualProto.this).create();
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

    public void saveUndo(){
        lastPlayerDirection = playerDirection;
        lastPlayerX = playerX;
        lastPlayerY = playerY;
        setUndo(true);
    }

    public void setUndo(boolean state){
        //enableUndoBtn = state;
        btnUndo.setEnabled(state);
    }

}