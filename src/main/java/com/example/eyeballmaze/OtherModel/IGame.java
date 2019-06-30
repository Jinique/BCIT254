package com.example.eyeballmaze.OtherModel;

public interface IGame {
	Integer getRowCount();
	Integer getcolumnCount();
	void restartMaze();
	String makeMove(String move, int space);
	Object[] whatsAt(int x, int y);
	void updateMove();
	void updateMaze();
	void showNextMove();
	//void start();
	void clearConsole();
	boolean isComplete();
	String getItem(int x, int y);
	Integer[] getPlayerLocation();
    String getGoalCount();
	void setGoalCount(String goalCount);
    String getMoveCount();
	void setMoveCount(String moveCount);
	void setMovesLeft(String moveCount);
    void setMazeCharacter(int x, int y, String item);
	String[][] getGameMap();
	Integer getMovesLeft();
    String getPlayerDirection();
}