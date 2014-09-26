package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScoreKeeper { //handles game point math and the drawing of them to the screen
	
	private static int score = 0;
	private float x, y;
	private BitmapFont scoreDisplay;
	String scoreStr;
	
	public ScoreKeeper(float x, float y) {
		this.x = x;
		this.y = y;
		init();
	}
	
	public void init() {
		scoreDisplay = new BitmapFont(); //TODO make a custom font
		scoreDisplay.setScale(0.1f);
	}
	
	public void updateScore(int pointsAdded) {
		score = score + pointsAdded;
	}
	
	public void update(float deltaTime) {
		Gdx.app.log("POINTS", "SCORE == " + score + ", " + scoreStr);
	}
	
	public void draw(SpriteBatch batch, OrthographicCamera camera) {
		batch.setProjectionMatrix(camera.combined);

		scoreStr = Integer.toString(score);
		
		batch.begin();
			scoreDisplay.draw(batch, scoreStr, x, y);
		batch.end();
	}

}
