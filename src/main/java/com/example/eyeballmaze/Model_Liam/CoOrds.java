package com.example.eyeballmaze.Model_Liam;

public class CoOrds {
	public int x, y;
	public PlayerDirection looking;
	
	public CoOrds(int p1, int p2, PlayerDirection dir){
		x = p1;
		y = p2;
		looking = dir;
	}
}
