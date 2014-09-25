package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class WorldRenderer implements Disposable {
	
	public static final String TAG = WorldRenderer.class.getName();
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private WorldController worldController;


	public WorldRenderer(WorldController worldController) {
		this.worldController = worldController;
		init();
	}
	
	private void init() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.position.set(Constants.VIEWPORT_WIDTH / 2, Constants.VIEWPORT_HEIGHT / 2, 0);
		camera.update();
	}
	
	public void render() {
		renderGameObjects();
	}
	
	private void renderGameObjects() {
		//worldController.cameraHelper.applyTo(camera);
		worldController.sawRail.draw(batch, camera);
		worldController.log.draw(batch, camera);
		if(worldController.log.isCut() == false) {
			worldController.log.target.draw(batch, camera);
		}
	
		worldController.sawBlade.draw(batch, camera);
		
		if(worldController.log.isCut()) {
			for(int i = worldController.log.getNumberOfLogs()-1; i>=0; i--) { 
				worldController.log.logSplit[i].draw(batch, camera);
			}
			worldController.log.target.draw(batch, camera);
		}
		worldController.scoreKeeper.draw(batch);
	}

	public void resize(int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		camera.update();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}
}
