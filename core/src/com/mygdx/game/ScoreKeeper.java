package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;

public class ScoreKeeper { //handles game point math and the drawing of them to the screen
	
	private int score = 0;
	private int lives;
	private float x, y;
	private BitmapFont font;
	FreeTypeFontGenerator generator;
	String scoreStr;
	String extraLivesStr;
	
	TextureRegion extraLifeRegion;
	Sprite extraLifeSprite;
	
	public ScoreKeeper(float x, float y) { //TODO set these too x and y of the camera to move everything along with the viewport, making it more like a hud
		this.x = x;
		this.y = y;
		init();
	}
	
	public void init() {
		//font = new BitmapFont(Gdx.files.internal("assets-raw/font.fnt"), Gdx.files.internal("assets-raw/font.png"), false);
		generator = new FreeTypeFontGenerator(Gdx.files.internal("assets-raw/kenvector_future.ttf"));
		
		font = generator.generateFont(48); //TODO - find a better alternative the above comment might be a start.
		font.setScale(0.02f);
		
		extraLifeRegion = Assets.instance.assetLog.log;
		extraLifeSprite = new Sprite(extraLifeRegion);
		extraLifeSprite.setBounds(3, 7.5f, 0.5f, 2);
	}
	
	public void updateScore(int logsCut, int lives) {
		//score = score + pointsAdded;
		this.score = logsCut;
		this.lives = lives / 3;
		
	}
	
	public void update(float deltaTime) {
		//Gdx.app.log("POINTS", "SCORE == " + score + ", " + scoreStr);
	}
	
	public void draw(SpriteBatch batch, OrthographicCamera camera) {
		batch.setProjectionMatrix(camera.combined);

		scoreStr = Integer.toString(score);
		extraLivesStr = Integer.toString(lives);
		
		batch.begin();
			font.draw(batch, scoreStr, x, y); //draws the score
			font.draw(batch, " :" + extraLivesStr, 3.5f, 9);
			extraLifeSprite.draw(batch);
		batch.end();
	}

}
