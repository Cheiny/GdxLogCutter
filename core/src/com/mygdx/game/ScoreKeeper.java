package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScoreKeeper { //handles game point math and the drawing of them to the screen
	
	private static int score = 0;
	private float x, y, width, height;
	private BitmapFont scoreDisplay;
	
	public ScoreKeeper(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		init();
	}
	
	public void init() {
		scoreDisplay = new BitmapFont();
		scoreDisplay.setScale(0.1f);
	}
	
	public void updateScore(int pointsAdded) {
		score = score + pointsAdded;
	}
	
	public void update(float deltaTime) {
		Gdx.app.log("POINTS", "SCORE == " + score);
	}
	
	public void draw(SpriteBatch batch) {
		String scoreStr = Integer.toString(score);
		
		batch.begin();
			scoreDisplay.draw(batch, scoreStr, x, y);
		batch.end();
	}

}
