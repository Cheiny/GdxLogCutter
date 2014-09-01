package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Assets;

public class Target {
	float x;
	float y;
	float width;
	float height;
	
	Log log;
	
	TextureRegion targetLineRegion = new TextureRegion();
	Sprite targetLineSprite;
	
	public Target(float x, float y, float width, float height, Log log) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.log = log;
		init();
	}
	
	private void init() {
		targetLineRegion.setRegion(Assets.instance.assetTarget.targetLine);
		targetLineSprite = new Sprite(targetLineRegion);
		
		targetLineSprite.setBounds(x, y, width, height);
	}
	
	public void update(float delta){
		if(log.getFalling()) {
			y = y- log.getFallSpeed()*delta;
		}
		targetLineSprite.setBounds(x, y, width, height);
	}
	
	public void draw(SpriteBatch batch, OrthographicCamera camera){
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		targetLineSprite.draw(batch);
		batch.end();
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}

	public float getY() {
		return y;
	}
	}
