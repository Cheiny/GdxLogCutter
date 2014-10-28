package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Assets;

public class Log {
	
	float splitTexy; //
	float splitTexh; //TODO localize these variables
	
	float cutAccuracy;// the distance a cut is made from the target line.
	
	
	//Object bounds variables
	private float x;
	private float y;
	private float baseY; //this one doesn't move around so much and is used to calculate target position
	private float width;
	private float height;
	
	private float fallSpeed = 10;
		
	//Texture Variables
	float logBotTexHeight = 24; //The height of the logBottom section of the total log texture
	float logTopPatchTexHeight = 12;//The height of the logTopPatch section of the total log texture

	float bodyTexHeight;  //variable Texture height for the log body
	float totalTexHeight; //entire log Texture Height - constant
		
	//Sprite Bounds variables
	float totalHeight;    //original log height used for calculations.
		
	float splity;
	float logBoty;
		
	float logSplitx = 0;
		
	float splitHeight;
	float logBottomSpriteScreenHeight;
	float logTopPatchScreenHeight;
	float mainLogHeight;
	
	float targetYAdj;
		
	public static float gapSize = 0.25f; //the space between the two logs when cut TODO - make this work
		
	int logNumber = 0;
	//int pointValue = 0; //value of points earned for a cut
	
	int pointValue = 0;
	int counter = 0; // Counts how many logs have been cut for the scoreKeeper.
	int lives = 0;
	
	TextureRegion logBodyRegion = new TextureRegion();
	TextureRegion logBottomRegion = new TextureRegion();
	
	private Sprite logBodySprite;
	private Sprite logBottomSprite;

	public LogSplit[] logSplit = new LogSplit[5];
	public Target target;
	private Message message;
	
	private boolean falling = true;
	private boolean logCut = false;
	private boolean perfectCut;
	
	

	public Log(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.y = baseY;
		this.width = width;
		this.height = height;
		this.totalHeight = height;
		this.mainLogHeight = height;
		//this.mainLogy = y;
		init();
	}
	
	public void init() {
		assembleLog();
		targetYAdj = MathUtils.random(0.5f, 4.5f);
		//target = new Target(x, y + targetYAdj, 1, 0.2f);
		target = new Target(x, y, targetYAdj, 1, 0.2f);
		message = Message.NONE;
		logNumber = 0;
	}
	
	
	private void assembleLog() {
		
		logBodyRegion.setRegion(Assets.instance.assetLog.log); //	Sets logMiddleRegion to get initial measurements
		logBottomRegion.setRegion(Assets.instance.assetLog.logBottom); //Shouldn't need measurements... Hopefully
		
		//Measurements
		totalTexHeight = logBodyRegion.getRegionHeight(); //Constant
		bodyTexHeight = logBodyRegion.getRegionHeight();	//This one changes 
		
		logBottomSpriteScreenHeight = (logBotTexHeight*height)/totalTexHeight;
		logTopPatchScreenHeight = (logTopPatchTexHeight*height)/totalTexHeight;
		logBoty = y - logBottomSpriteScreenHeight/2.6f; // changes if the log is cut to look normal
		
		//Sprites
		logBodySprite = new Sprite(logBodyRegion);
		logBottomSprite = new Sprite(logBottomRegion);
		
		logBodySprite.setBounds((float) x, (float) y, (float) width, (float) height);
		logBottomSprite.setBounds((float) x, (float) logBoty, (float) width, (float) logBottomSpriteScreenHeight);
		
	}

	public void update(float deltaTime) {
		
		Gdx.app.log("Log", "LogY = " + baseY + ", TargetY = " + (target.getY() + targetYAdj) + ", MouseY = " + (baseY + Gdx.input.getY()));

		
		//makes the log fall
		if(falling) {
			if(onScreen()) {
			y = (y - fallSpeed*deltaTime);
			baseY = (baseY - fallSpeed*deltaTime);
			target.update(baseY, deltaTime);
			logBoty = y-logBottomSpriteScreenHeight/2.6f;
			//splity = splity-fallSpeed;
			} else { 
				respawn();
			}
		}
		
		//updates the log's body position and size to be drawn to the screen
		logBodySprite.setBounds((float) x, (float) y, (float) width, (float) mainLogHeight);
		logBottomSprite.setBounds((float) x, (float) logBoty, (float) width, (float) logBottomSpriteScreenHeight);
	}
	
	private void respawn() { //moves the log back to the top of the screen and resets it's size.
		y = 9;
		baseY = 9;
		mainLogHeight = 5;
		height = 5;
		logCut = false;
		perfectCut = false;
		init();
	}

	private boolean onScreen() {
		if(y < 0 - height){
			return false;
		} else {
			return true;
		}
	}

	public void draw(SpriteBatch batch, OrthographicCamera camera){
		batch.setProjectionMatrix(camera.combined); //TODO - Maybe put this inside batch.begin/end block?
		
		batch.begin();
		logBodySprite.draw(batch);
		if(logCut) {
			logBottomSprite.draw(batch); 
		}
		batch.end();
	}

	//Splits the log in half - kind've messy
	public void split() {
		int splitTexHeight;
		
		//Check to see if log is over the cutting line at y=4
		if(y<4 && (y+mainLogHeight)>4) {
			
			scoreCut();
				
			Gdx.app.log("Scorer", "CutAccuracy == " + cutAccuracy);
			
			//Sprite heights TODO - Make another variable to take over here instead of changing y directly
			mainLogHeight = (y+mainLogHeight) - 4;
			splitHeight = 4 - y - gapSize; //should just be needed for measurements
			height = height - splitHeight;
			
			//Texture heights
			bodyTexHeight = (mainLogHeight*totalTexHeight)/totalHeight;
			splitTexHeight = (int) ((splitHeight*totalTexHeight)/totalHeight);

			
			//TODO - Make another variable to take over here instead of changing y directly
			
			splity = y;
			y = 4;
			logBoty = y - logBottomSpriteScreenHeight/2.6f; // changes if the log is cut to look normal

			
			//logSplitx = MathUtils.random(-0.5f, 0.5f); // random
			logSplitx = x;
		
			//Textures: chop regions
			logBodyRegion.setRegion(Assets.instance.assetLog.log, 0, 0, logBodyRegion.getRegionWidth(), (int) bodyTexHeight);
		
			//Sprites: add chopped regions
			logBodySprite.setRegion(logBodyRegion);
		
			//Sprites: change positions
			logBodySprite.setBounds( x, y, width, mainLogHeight);
			logBottomSprite.setBounds( x, logBoty, width, logBottomSpriteScreenHeight);

			
			if(!logCut) {
				
				logSplit[logNumber] = new LogSplit( logSplitx, splity, width, splitHeight, splitTexHeight, totalTexHeight - splitTexHeight, logTopPatchScreenHeight, logBottomSpriteScreenHeight, fallSpeed, false, perfectCut); //hopefully this last variable is right
				logCut = true;
					
				
			} else {
				
				splitTexh = 0;
				splitTexy = 0;
				float gapSpace = (float) (gapSize*logNumber); //total amount of space added from all the cuts.
				for (int i =0; i<logNumber; i++) { //TODO this could be kept for some cool features?
					splitTexh = splitTexh + logSplit[i].getTextureHeight(); //texture height from bottom of log
				}
				splitTexy = ((totalTexHeight - splitTexh) - splitTexHeight - gapSpace); // texture y value for the split log TODO - account for gap between logs
				logSplit[logNumber] = new LogSplit( logSplitx, splity, width, (splitHeight), splitTexHeight, splitTexy, logTopPatchScreenHeight, logBottomSpriteScreenHeight, fallSpeed, true, perfectCut); //hopefully this last variable is right
			}
			
			logNumber++; //TODO add this back if multiple log splits are wanted/needed.
			
			logCut = true;
		}
		falling = true;
	}

	
	
	private void scoreCut() {
		cutAccuracy = target.getY() - 4;
		calculatePointValue(cutAccuracy);
		
		if(calculatePointValue(cutAccuracy) == 10) {
			logBottomRegion.setRegion(Assets.instance.assetLog.logBottomGreen);
			logBottomSprite.setRegion(logBottomRegion);
			perfectCut = true;
			
		} 
		if(message == Message.PERFECT) {
			lives++;
		}
		if(message != Message.MISS) {
			counter++;
		}
	}

	private int calculatePointValue(float cutAccuracy) {
		pointValue = 0;
		
		if(Math.abs(cutAccuracy) < 0.25f) {
			pointValue = 10; // "PERFECT"
			message = Message.PERFECT;
		}
		if(Math.abs(cutAccuracy) >= 0.25f && Math.abs(cutAccuracy) < 0.5f) {
			pointValue = 5; // "GREAT"
			message = Message.GOOD; //TODO add great
		}
		if(Math.abs(cutAccuracy) >= 0.5f && Math.abs(cutAccuracy) < 1) {
			pointValue = 2; // "GOOD"
			message = Message.GOOD;
		}
		if(Math.abs(cutAccuracy) >= 1) {
			pointValue = 0; // "BAD"
			message = Message.BAD;
		}
		
		return pointValue;
	}

	//Getters and setters
	public boolean isCut() {
		return logCut;
	}

	public int getNumberOfLogs() {
		return logNumber;
	}
	
	public boolean getFalling() {
		return falling;
	}
	
	public void setFalling(boolean b) {
		falling = b;
	}

	public float getY() {
		return this.y;
	}
	
	public float getX() {
		return this.x;
	}

	public float getFallSpeed() {
		return this.fallSpeed;
	}
	
	public boolean isPerfectCut() {
		return perfectCut;
	}
	public int getCutPoints() { 
		return this.pointValue;
	}
	
	public int getCounter() { // returns # of logs cut
		return counter;
	}

	public  Message getMessage() {
		return message;
	}

	public int getLives() {
		return lives;
	}
}
