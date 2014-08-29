package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SmallConveyorBelt {

	TextureAtlas spriteSheet = new TextureAtlas("SmallConveyorBeltTextureAtlas.txt");

	TextureRegion[] conveyorBeltFrames;
	float  stateTime = 0;
	SpriteBatch batch = new SpriteBatch();
	TextureRegion currentFrame;
	double x;
	double y;
	
	SmallConveyorBelt(double x, double y) {
		this.x = x;
		this.y = y;
		conveyorBeltFrames = new TextureRegion[10];
	}
	
	public void draw(SpriteBatch batch2) {
		
		stateTime += Gdx.graphics.getDeltaTime();
		for (int i=0; i<8; i++) {
			
			
			conveyorBeltFrames[i].setRegion(spriteSheet.findRegion("smallConveyorBeltF" + i+1));
		}
			Animation smallConveyorBeltAnim = new Animation((float) 0.1, conveyorBeltFrames);
			
			
			currentFrame.setRegion(smallConveyorBeltAnim.getKeyFrame(stateTime, true));
			currentFrame.getRegionWidth();
			
			batch.begin();
			
			batch.draw(currentFrame, (float) 250, (float) 250);
			batch.end();
		}
	
	
	
}
