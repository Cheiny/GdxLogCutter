package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Assets;

public class BackDrop {
	
	float x, y, width, height;
	Sprite cautionLineSpriteL;
	Sprite cautionLineSpriteR;
	
	Sprite lightConcreteSprite;

	TextureRegion lightConcreteRegion;
	TextureRegion cautionLineRegion;
	
	public BackDrop(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		init();
	}
	
	private void init() {
		cautionLineRegion = new TextureRegion(Assets.instance.assetLevelDecoration.assetCautionLine);
		lightConcreteRegion = new TextureRegion(Assets.instance.assetLevelDecoration.assetLightConcrete);
		
		cautionLineSpriteL = new Sprite(cautionLineRegion);
		cautionLineSpriteR = new Sprite(cautionLineRegion);

		cautionLineSpriteL.setBounds(x, y, 0.5f, height);
		cautionLineSpriteR.setBounds(x + 3.5f, y, 0.5f, height);
		
		lightConcreteSprite = new Sprite(lightConcreteRegion);
		lightConcreteSprite.setBounds(x, y, width, height);
	}

	public void draw(SpriteBatch batch) {
		batch.begin();
		lightConcreteSprite.draw(batch);
		cautionLineSpriteL.draw(batch);
		cautionLineSpriteR.draw(batch);
		batch.end();
	}
	
}
