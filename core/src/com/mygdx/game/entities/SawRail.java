package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Assets;

public class SawRail {
	float x;
	float y;
	float width;
	float height;
	
	TextureRegion sawRailRegion = new TextureRegion();
	Sprite sawRailSprite;
	
	public SawRail(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		init();
	}
	
	private void init(){
		sawRailRegion.setRegion(Assets.instance.assetSaw.sawRail);
		sawRailSprite = new Sprite(sawRailRegion);
		sawRailSprite.setBounds(x, y, width, height);
	}
		
	//Shouldn't need to update
	
	public void draw(SpriteBatch batch, OrthographicCamera camera){
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		sawRailSprite.draw(batch);
		batch.end();
	}

}
