package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.entities.Message;

public class ScoreKeeper { //handles game point math and the drawing of them to the screen TODO - remake this into a "hud" class
	
	private int score = 0;
	private int lives = 0;	
	private int lifePoints; //Points that buildup to form lives
	private int oneLife = 3; //the amount of lifePoints that == a life
	
	
	private int meterFill;
	private int meterRegionHeight;
	
	boolean drawOneUpAnimation;
	boolean animationDone;
	
	private float x, y;
	private float meterX, meterY, meterWidth, meterHeight; //Screen variables for the life meter
	float alpha;
	
	private BitmapFont font;
	FreeTypeFontGenerator generator;
	String scoreStr;
	String extraLivesStr;
	
	TextureRegion logMeterOutlineRegion;
	TextureRegion extraLifeRegion;
	TextureRegion logBottomRegion; //patches the bottom of the meter fill
	
	Sprite logMeterOutlineSprite;
	Sprite extraLifeSprite;
	Sprite logBottomSprite;
	
	public ScoreKeeper(float x, float y) { //TODO set these to x and y of the camera to move everything along with the viewport, making it more like a hud
		this.x = x;
		this.y = y;
		init();
	}
	
	public void init() {
		//font = new BitmapFont(Gdx.files.internal("assets-raw/font.fnt"), Gdx.files.internal("assets-raw/font.png"), false);
		generator = new FreeTypeFontGenerator(Gdx.files.internal("assets-raw/kenvector_future.ttf"));
		
		meterX = 3;
		meterY = 7.5f;
		meterWidth = 0.5f;
		meterHeight = 2;
		
		meterFill = 0;
		alpha = 1.0f;
		
		font = generator.generateFont(48); //TODO - find a better alternative - the above comment might be a start.
		font.setScale(0.02f);
		
		extraLifeRegion = Assets.instance.assetLog.log;
		logMeterOutlineRegion = Assets.instance.assetLog.logOutline;
		logBottomRegion = Assets.instance.assetLog.logBottom;
		
		logMeterOutlineSprite = new Sprite(logMeterOutlineRegion);
		extraLifeSprite = new Sprite(extraLifeRegion);
		logBottomSprite = new Sprite(logBottomRegion);
		
		extraLifeSprite.setBounds(meterX, meterY, meterWidth, meterHeight);
		logMeterOutlineSprite.setBounds(meterX, meterY, meterWidth, meterHeight);
	}
	
	public void updateScore(int logsCut, int lives) {
		//score = score + pointsAdded;
		
		
		this.score = logsCut;
		this.lifePoints = lives; //used to check whether we need to update score
		this.lives = lives / 3;
		this.meterFill = (lives) % 3;
		this.lives = lives / 3;
		meterFill++;
		
		if(meterFill == 3) {
			drawOneUpAnimation = true;
		} else {
			drawOneUpAnimation = false;
		}
		Gdx.app.log("Scorekeeper", "meterFill = " + meterFill);

	}
	
	


	public void update(float deltaTime) {
		float meterFillHeight;
		
		meterFillHeight = (meterHeight * (meterFill/3.0f));
		
		
		meterRegionHeight = (int) ((meterFill/3.0f) * Assets.instance.assetLog.log.getRegionHeight());

		extraLifeSprite.setRegionHeight(meterRegionHeight); //WHOA HOLY SHIT THIS IS SO MUCH EASIER THAN WHAT I DID IN Log.Split() TODO
		extraLifeSprite.setBounds(meterX,  meterY + (meterHeight - meterFillHeight), meterWidth, meterFillHeight); //TODO change this
		extraLifeSprite.setAlpha(alpha);
		
		logBottomSprite.setBounds(meterX, meterY + (meterHeight - meterFillHeight) - 0.1f, 0.5f, 0.25f); //may need to be adjusted
		
		if(drawOneUpAnimation == true) {
			meterX = meterX + deltaTime;
			meterY = meterY + deltaTime/2;
			meterHeight = meterHeight - deltaTime/2;
			alpha -= deltaTime;
			extraLifeSprite.setAlpha(alpha);
			extraLifeSprite.setBounds(meterX, meterY, meterWidth, meterHeight);
			
			if(alpha <= .02) {
				this.lifePoints++;
				this.lives++;
				drawOneUpAnimation = false;
				animationDone = true;
				resetMeter();
				
			}
		}
	}
	
	private void resetMeter() {
		meterX = 3;
		meterY = 7.5f;
		meterWidth = 0.5f;
		meterHeight = 2;
		alpha = 1.0f;
		meterFill = 0;
	}

	public void draw(SpriteBatch batch, OrthographicCamera camera) {
		batch.setProjectionMatrix(camera.combined);

		scoreStr = Integer.toString(score);
		extraLivesStr = Integer.toString(lives);
		
		batch.begin();
			font.draw(batch, scoreStr, x, y); //draws the score
			font.draw(batch, " :" + extraLivesStr, 3.5f, 9);
			
			extraLifeSprite.draw(batch);
			
			logMeterOutlineSprite.draw(batch);
			if(meterFill != 0 && meterFill != 3) {
				logBottomSprite.draw(batch);
			}
			
		batch.end();
		
	}
	
	public int getLives() {
		return lifePoints;
	}
	
	public int getLogsCut() {
		return lifePoints;
	}

	public void updateScore(Message message) {
		switch(message) {
		case BAD:
			score++;
			break;
		case GOOD:
			score++;
			break;
		case MISS:
			if(lifePoints > 0) {
				lifePoints = lifePoints - oneLife;
				lifePoints = lifePoints - ((lifePoints % oneLife));
			}
			break;
		case PERFECT:
			score++;
			if(meterFill == 2) {
				drawOneUpAnimation = true;
			} else {
				lifePoints++;
			}
			break;
			
			
		case START:
		case NONE:
		default:
			break;
		
		}
		
		lives = lifePoints / 3;
		meterFill = lifePoints % 3;
		
		//---------handle Animation---------------
		if(drawOneUpAnimation == true) {
			meterFill = oneLife;
		}
		//-------------done----------------------
		
		Gdx.app.log("Scorekeeper", "lives " + lives +"meterFill = " + meterFill);

	}

}
