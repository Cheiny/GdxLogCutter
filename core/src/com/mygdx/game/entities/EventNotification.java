package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Assets;
import com.mygdx.game.Constants;

public class EventNotification {

	Message message;
	TextureRegion region;
	Sprite sprite;
	
	int x, y, width, height;
	
	float alpha;
	
	public EventNotification(Message message) {
		this.message = message;
		init();
	}
	
	private void init() {
		switch(message) {
		case BAD:
		region = new TextureRegion(Assets.instance.assetLevelDecoration.badSign);
		break;
		case GOOD:
		region = new TextureRegion(Assets.instance.assetLevelDecoration.goodSign);
		break;
		case PERFECT:
		region = new TextureRegion(Assets.instance.assetLevelDecoration.perfectSign);
		break;
		case MISS:
		region = new TextureRegion(Assets.instance.assetLevelDecoration.missSign);
		break;
		case NONE:
		region = new TextureRegion(Assets.instance.assetLevelDecoration.perfectSign); //shouldn't draw?
		break;
		case START:
			break;
		default:
			break;
		}
		
		alpha = 1.0f;
		x = (int) (Constants.VIEWPORT_WIDTH/2);
		y = (int) (Constants.VIEWPORT_HEIGHT/2);
		
		width = 3;
		height = 1;
		
		sprite = new Sprite(region);
		sprite.setBounds(x, y, width, height);
	}
	
	public void update(float delta) {
		alpha = alpha-delta; //gets to 0 in 1 second?
	}
	
	public void draw(SpriteBatch batch, OrthographicCamera camera) {
		//alpha = alpha-0.1f;
		sprite.setAlpha(alpha);
		sprite.setBounds(camera.position.x - width/2, camera.position.y, width, height);
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		sprite.draw(batch);
		batch.end();
	}

	public double getAlpha() {
		return alpha;
	}

}
