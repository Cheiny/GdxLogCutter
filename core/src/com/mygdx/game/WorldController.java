package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.mygdx.game.entities.Log;
import com.mygdx.game.entities.LogSplit;
import com.mygdx.game.entities.SawBlade;
import com.mygdx.game.entities.SawRail;
import com.mygdx.game.entities.Target;

public class WorldController extends InputAdapter {
	
	public static final String TAG = WorldController.class.getName();
	
	
	//TODO - Shouldn't need these anymore
	public Sprite[] testSprites;
	public Sprite testLine; 
	public int selectedSprite;
	//----------------------------------
	
	public CameraHelper cameraHelper;
	
	float genTargetY = 0;
	
	
	public Log log;
	public SawBlade sawBlade;
	public SawRail sawRail;
	public Target target; //Target meant for user to try and cut.
	public static LogSplit[] logSplit = new LogSplit[1];
	
	public WorldController() {
		init();
	}
	
	public void init() {
		initGameObjects();
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		
	}
	
	private void initGameObjects() {
		log = new Log(0, 9, 1, 5);
		sawBlade = new SawBlade(1, 3.5f, 1, 1);
		sawRail = new SawRail(-1.25f, 3.3f, 3.5f, 0.4f);
		
		genTargetY = MathUtils.random(0.5f, 4.5f);
		
		target = new Target(log.getX(), log.getY() + genTargetY, 1, 0.3f, log);
		
	}


	public void update(float deltaTime) {
		upDateGameObjects(deltaTime);
		handleGameInput(deltaTime);
		handleDebugInput(deltaTime);
		cameraHelper.update(deltaTime);
	}

	private void handleGameInput(float deltaTime) {
		if(Gdx.input.justTouched()) {
			sawBlade.slide();
			if(log.getFalling() == true) {
				log.setFalling(false);
				log.split();
			} else {
				log.setFalling(true);
			}
		}
		
	}

	private void upDateGameObjects(float deltaTime) { //TODO!!! account for deltaTime for everything that needs updating
		log.update(deltaTime);
		sawBlade.update(deltaTime);
		target.update(deltaTime);
		
		if(log.isCut()) {
			for(int i=0; i<log.getNumberOfLogs(); i++) {
				log.logSplit[i].setFalling(log.getFalling());
				log.logSplit[i].update();
			}
		}
	}

	private void handleDebugInput(float deltaTime) {
		if(Gdx.app.getType() != ApplicationType.Desktop) {
			return;
		}
		
		// Selected Sprite Controls
		float sprMoveSpeed = 5 * deltaTime;
		if (Gdx.input.isKeyPressed(Keys.A)) moveSelectedSprite(-sprMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.D)) moveSelectedSprite(sprMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.W)) moveSelectedSprite(0, sprMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.S)) moveSelectedSprite(0, -sprMoveSpeed);
		
		
		// Camera Controls (move)
		float camMoveSpeed = 5 * deltaTime;
		float camMoveSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camMoveSpeed *= camMoveSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.LEFT)) moveCamera(-camMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) moveCamera(camMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.UP)) moveCamera(0, camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.DOWN)) moveCamera(0, -camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.BACKSPACE)) 
		cameraHelper.setPosition(0, 0);
		// Camera Controls (zoom)
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camZoomSpeed *= camZoomSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.COMMA)) 
		cameraHelper.addZoom(camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.PERIOD)) cameraHelper.addZoom(-camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.SLASH)) cameraHelper.setZoom(1);
		}
	
	private void moveCamera (float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
		}
		
	
	@Override
	public boolean keyUp (int keycode) {
	// Reset game world
	if (keycode == Keys.R) {
		init();
		Gdx.app.debug(TAG, "Game world resetted");
	}
	// Select next sprite
	else if (keycode == Keys.SPACE) {
		selectedSprite = (selectedSprite + 1) % testSprites.length;
		Gdx.app.debug(TAG, "Sprite #" + selectedSprite + " selected");
	
	// Update camera's target to follow the currently
	// selected sprite
		if (cameraHelper.hasTarget()) {
			cameraHelper.setTarget(testSprites[selectedSprite]);
		}
	
	}
	
	// Toggle camera follow
	else if (keycode == Keys.ENTER) {
		cameraHelper.setTarget(cameraHelper.hasTarget() ? null : testSprites[selectedSprite]);
		Gdx.app.debug(TAG, "Camera follow enabled: " + cameraHelper.hasTarget());
	}
	return false;
	}
		
	private void moveSelectedSprite(float x, float y) {
		testSprites[selectedSprite].translate(x, y);		
	}
}
