package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Assets;

public class LogSplit{

	float x;
	float y;
	float width;
	float height;
	
	float splitTexHeight;
	float texWidth;
	float splitTexy;
	
	float logBottomSpriteScreenHeight;
	float logTopPatchScreenHeight;
	float logBotPatchScreenHeight;
	
	float fallSpeed;
	
	TextureRegion logSplitRegion = new TextureRegion();
	TextureRegion logTopPatchRegion = new TextureRegion();
	TextureRegion logBotPatchRegion = new TextureRegion();
	
	Sprite logSplitSprite;
	Sprite logTopPatchSprite;
	Sprite logBotPatchSprite;
	
	boolean falling = false;
	boolean drawBotPatch = false;
	
	public LogSplit(float logSplitx, float splity,
					float width, float splitHeight,
					float splitTexHeight, float splitTexy,				   //	all calculated in Log class 
					float logTopPatchScreenHeight,						   //	- probs not the best way to do it. 
					float logBotPatchScreenHeight, float fallSpeed,			//	TODO - try and get rid of some of these and do more
					boolean drawBotPatch) { 								//	do more calculations inside this class
		
		this.x = logSplitx;
		this.y = splity;
		this.width = width;
		this.height = splitHeight;
		this.splitTexHeight = splitTexHeight;
		this.logTopPatchScreenHeight = logTopPatchScreenHeight;
		this.logBotPatchScreenHeight = logBotPatchScreenHeight;
		this.splitTexy = splitTexy;
		this.drawBotPatch = drawBotPatch;
		this.fallSpeed = fallSpeed;
		init();
	}

	private void init() {
		
		texWidth = Assets.instance.assetLog.logSplit.getRegionWidth();
		
		logSplitRegion.setRegion(Assets.instance.assetLog.log, (int) 0, (int) splitTexy, (int) texWidth, (int) splitTexHeight);
		logTopPatchRegion.setRegion(Assets.instance.assetLog.logTopPatch);
		
		
		logSplitSprite = new Sprite(logSplitRegion);
		logTopPatchSprite = new Sprite(logTopPatchRegion);
		
		
		logSplitSprite.setBounds(x, y, width, height);
		logTopPatchSprite.setBounds( x, y+height-(logTopPatchScreenHeight/5), width, logTopPatchScreenHeight);
		
		if(drawBotPatch) {
		logBotPatchRegion.setRegion(Assets.instance.assetLog.logBottom);
		logBotPatchSprite = new Sprite(logBotPatchRegion);
		logBotPatchSprite.setBounds( x, y-(logBotPatchScreenHeight/2.6f), width, logBotPatchScreenHeight);
		
		}
		
	}

	public void update(float delta) {
		
		 if(falling) { 
		//	if(onScreen()) { TODO should probably implement this eventually
			y = (y-fallSpeed*delta);
		}

		logSplitSprite.setBounds((float)x, (float)y, (float)width, (float)height);
		logTopPatchSprite.setBounds((float) x, (float) (y+height-(logTopPatchScreenHeight/5)), (float) width, (float) logTopPatchScreenHeight);
		if(drawBotPatch) logBotPatchSprite.setBounds((float) x, (float) ((float) y-(logBotPatchScreenHeight/3)), (float) width, (float) logBotPatchScreenHeight);

	}

	public void draw(SpriteBatch batch, OrthographicCamera camera) {
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		logSplitSprite.draw(batch);
		logTopPatchSprite.draw(batch);
		if(drawBotPatch) logBotPatchSprite.draw(batch);
		batch.end();
	}


	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	public float getTextureHeight() {
		return (float) splitTexHeight;
	}

	public float getY() {
		return y;
	}
	

}
