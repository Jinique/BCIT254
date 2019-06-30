package com.example.eyeballmaze.model;

import java.util.Arrays;

public class Model {

    private LevelHandler levelHandler = new LevelHandler();


    private int numberOfMoves;
    private int numberOfGoals;
    private int goalX;
    private int goalY;
    private boolean gameWin;
    private boolean gameLost;

    private String[][] currentLevel;
    private int mapX;
    private int mapY;

    private String playerDirection;
    private int playerX;
    private int playerY;
    private String playerColour;
    private String playerShape;

    private int targetX;
    private int targetY;
    private String targetColour;
    private String targetShape;

    private String lastPlayerDirection;
    private int lastPlayerX;
    private int lastPlayerY;

    //////////////////       set level       //////////////////
    public void setLevel(String levelNumber) {
        currentLevel = levelHandler.getLevel(levelNumber);
        setMapSize(currentLevel);
        setMoveCount(0);
        setGoalCount(countGoalFromMap());
        findPlayerFromMap();
        setWinState(false);
        setLoseState(false);
    }

    public String[][] getMap() {
        return currentLevel;
    }

    private void setMapSize(String[][] map) {
        mapY = map.length;
        String[] temp = map[0][0].split("");
        mapX = temp.length - 1;
    }

    //////////////////       map size       //////////////////
    public int getMapX() {
        return mapX;
    }

    public int getMapY() {
        return mapY;
    }


    //////////////////       move count       //////////////////
    private void setMoveCount(int i) {
        numberOfMoves = i;
    }

    public int getMoveCount() {
        return numberOfMoves;
    }

    public void changeMoveCount(int i) {
        numberOfMoves = numberOfMoves + i;
        setMoveCount(numberOfMoves);
        if (numberOfMoves > 14) {
            setLoseState(true);
        }
    }

    //////////////////       goal count       //////////////////
    private int countGoalFromMap() {
        int value = 0;
        for (int i = 0; i < mapY; i++) {
            for (int j = 0; j < mapX; j++) {
                String[] temp = currentLevel[i][j].split("");
                if (temp[4].equals("G")) {
                    setGoalX(j);
                    setGoalY(i);
                    value++;
                }
            }
        }
        return value;
    }

    private void setGoalCount(int i) {
        numberOfGoals = i;
    }

    public int getGoalCount() {
        return numberOfGoals;
    }

    private void changeGoalCount(int i) {
        numberOfGoals = numberOfGoals + i;
        setGoalCount(numberOfGoals);
        if (numberOfGoals == 0) {
            setWinState(true);
        }
    }

    public void setWinState(boolean i) {
        gameWin = i;
    }

    public boolean getWinState() {
        return gameWin;
    }

    public void setLoseState(boolean b) {
        gameLost = b;
    }

    public boolean getLoseState() {
        return gameLost;
    }

    private void setGoalX(int x) {
        goalX = x;
    }

    public int getGoalX() {
        return goalX;
    }

    private void setGoalY(int y) {
        goalY = y;
    }

    public int getGoalY() {
        return goalY;
    }

    //////////////////       player       //////////////////
    private void findPlayerFromMap() {
        for (int i = 0; i < mapY; i++) {
            for (int j = 0; j < mapX; j++) {
                String[] temp = currentLevel[i][j].split("");
                if (temp[3].equals("U") || temp[2].equals("D") || temp[2].equals("L") || temp[2].equals("R")) {
                    setPlayerX(j);
                    setPlayerY(i);
                    setPlayerShape(temp[1]);
                    setPlayerColour(temp[2]);
                    setPlayerDirection(temp[3]);
                }
            }
        }
    }

    private void setPlayerX(int x) {
        playerX = x;
    }

    public int getPlayerX() {
        return playerX;
    }

    private void setPlayerY(int y) {
        playerY = y;
    }

    public int getPlayerY() {
        return playerY;
    }

    private void setPlayerDirection(String s) {
        playerDirection = s;
    }

    public String getPlayerDirection() {
        return playerDirection;
    }

    public String calcPlayerDirection() {
        if (playerX == targetX) {
            if (playerY > targetY) {
                setPlayerDirection("U");
                return "U";
            } else {
                setPlayerDirection("D");
                return "D";
            }
        } else {
            if (playerX > targetX) {
                setPlayerDirection("L");
                return "L";
            } else {
                setPlayerDirection("R");
                return "R";
            }
        }
    }

    public void updatePlayer() {
        setPlayerX(targetX);
        setPlayerY(targetY);
        setPlayerShape(findShape(playerX, playerY));
        setPlayerColour(findColour(playerX, playerY));
    }

    public void setPlayer(int x, int y, String direction) {
        playerX = x;
        playerY = y;
        playerDirection = direction;
        playerShape = findShape(playerX, playerY);
        playerColour = findColour(playerX, playerY);
    }

    private void setPlayerColour(String playerColour) {
        this.playerColour = playerColour;
    }

    private void setPlayerShape(String playerShape) {
        this.playerShape = playerShape;
    }

    //////////////////       target       //////////////////
    public void updateTarget(String target) {
        setTargetX(Character.digit(target.charAt(0), 10));
        setTargetY(Character.digit(target.charAt(1), 10));
        setTargetShape(findShape(targetX, targetY));
        setTargetColour(findColour(targetX, targetY));
    }

    private void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    private void setTargetY(int targetY) {
        this.targetY = targetY;
    }

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    private void setTargetShape(String targetShape) {
        this.targetShape = targetShape;
    }

    public String getTargetShape() {
        return targetShape;
    }

    private void setTargetColour(String targetColour) {
        this.targetColour = targetColour;
    }

    public String getTargetColour() {
        return targetColour;
    }

    //////////////////       checker       //////////////////
    private String findShape(int x, int y) {
        String value;
        String[] temp = currentLevel[y][x].split("");
        value = temp[1];
        return value;
    }

    private String findColour(int x, int y) {
        String value;
        String[] temp = currentLevel[y][x].split("");
        value = temp[2];
        return value;
    }

    public int checkAll() {
        int result = 0;
        if (checkCoOrd()) {
            if (!checkShape() && !checkColour()) {
                result = 2;
            }
        } else {
            result = 1;
        }
        return result;
    }

    private boolean checkShape() {
        boolean result = false;
        if (playerShape.equals(targetShape)) result = true;
        return result;
    }

    private boolean checkColour() {
        boolean result = false;
        if (playerColour.equals(targetColour)) result = true;
        return result;
    }

    private boolean checkCoOrd() {
        boolean result = false;
        switch (playerDirection) {
            case "U":
                if (((playerY > targetY) && (playerX == targetX)) ||
                        ((playerY == targetY) && (playerX != targetX))) {
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
                        ((playerX == targetX) && (playerY != targetY))) {
                    result = true;
                }
                break;
        }
        return result;
    }

    public void checkPlayerVsGoal() {
        if ((playerX == goalX) && (playerY == goalY)) changeGoalCount(-1);
    }

    //////////////////       checker       //////////////////
    public void saveUndo() {
        lastPlayerDirection = playerDirection;
        lastPlayerX = playerX;
        lastPlayerY = playerY;
    }

    public int getLastPlayerX() {
        return lastPlayerX;
    }

    public int getLastPlayerY() {
        return lastPlayerY;
    }

    public String getLastPlayerDirection() {
        return lastPlayerDirection;
    }
}
