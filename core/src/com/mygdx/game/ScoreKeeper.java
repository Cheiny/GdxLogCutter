package com.mygdx.game;

import com.badlogic.gdx.Gdx;

public class ScoreKeeper { //handles game point math and the drawing of them to the screen
	private static int score = 0;
	private float x, y, width, height;
	
	public ScoreKeeper(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		init();
	}
	
	public void init() {
	// TODO?
	}
	
	public void updateScore(int pointsAdded) {
		score = score + pointsAdded;
	}
	
	public void update(float deltaTime) {
		Gdx.app.log("POINTS", "SCORE == " + score);
	}

}
