package com.mygdx.game;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;

public class MyGdxGameMain implements ApplicationListener {
	
	private static final String TAG = MyGdxGameMain.class.getName();
	
	private WorldController worldController;
	private WorldRenderer worldRenderer;
	private boolean paused;
	
	@Override
	public void create () {
		// Set Libgdx log level to DEBUG
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		// load Assets
		Assets.instance.init(new AssetManager());
		// Initialize controller and renderer
		worldController = new WorldController();
		worldRenderer = new WorldRenderer(worldController);
		paused = false;
		
	}

	@Override
	public void render () {
		
		// Update game world by the time that has passed
		// since last rendered frame.
		if(!paused) {
		worldController.update(Gdx.graphics.getDeltaTime());
		}
		// Sets the clear screen color to: Cornflower Blue
		Gdx.gl.glClearColor(0x64/255.0f, 0x95/255.0f, 0xed/255.0f, 0xff/255.0f);
		// Clears the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Render game world to screen
		worldRenderer.render();
	
	}

	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);
		
	}

	@Override
	public void pause() {
		paused = true;
	}

	@Override
	public void resume() {
		paused = false;
		Assets.instance.init(new AssetManager());

	}

	@Override
	public void dispose() {
		worldRenderer.dispose();
		Assets.instance.dispose();
	}
}
