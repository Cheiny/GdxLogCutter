package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.entities.EventNotification;
import com.mygdx.game.entities.Message;

public class WorldRenderer implements Disposable {
	
	public static final String TAG = WorldRenderer.class.getName();
	
	private OrthographicCamera camera;
	private Viewport viewPort;
	
	private SpriteBatch batch;
	private WorldController worldController;
	
	Message message = Message.NONE;


	public WorldRenderer(WorldController worldController) {
		this.worldController = worldController;
		init();
	}
	
	private void init() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		viewPort = new FitViewport(640, 480, camera);
		camera.position.set(Constants.VIEWPORT_WIDTH / 2, Constants.VIEWPORT_HEIGHT / 2, 0);
		camera.update();
	}
	
	public void render() {
		worldController.cameraHelper.applyTo(camera);
		
		renderGameObjects();
	}
	
	private void renderGameObjects() {
		//worldController.cameraHelper.applyTo(camera);
		worldController.cautionLine.draw(batch);
		worldController.sawRail.draw(batch, camera);
		worldController.log.draw(batch, camera);
		
		if(worldController.log.isCut() == false) {
			if(worldController.log.isPerfectCut() == false) {
				worldController.log.target.draw(batch, camera);
			} 
		}
	
		worldController.sawBlade.draw(batch, camera);
		
		//Draws the logSplit array if the main log is cut
		if(worldController.log.isCut()) { //TODO - put a statement here that will create different eventNotifications based on how closely the log was cut.
			for(int i = worldController.log.getNumberOfLogs()-1; i>=0; i--) { 
				worldController.log.logSplit[i].draw(batch, camera);
			}
			if(worldController.log.isPerfectCut() == false) {
				worldController.log.target.draw(batch, camera);
			}
		}
		worldController.scoreKeeper.draw(batch, camera);
		
		drawMessage(); //draws any messages that may be needed.
	}

	private void drawMessage() {
		if(message == Message.NONE) {
			switch(worldController.log.getMessage()) {
			case BAD:
				message = Message.BAD;
				worldController.eventNotification = new EventNotification(Message.BAD);
				break;
			case GOOD:
				message = message.GOOD;
				worldController.eventNotification = new EventNotification(Message.GOOD);
				break;
			case MISS:
				message = message.MISS;
				worldController.eventNotification = new EventNotification(Message.MISS);
				break;
			case PERFECT:
				message = message.PERFECT;
				worldController.eventNotification = new EventNotification(Message.PERFECT);
				break;
			case START:
				break;
			default:
				break;
			}
		} else {
			if(worldController.eventNotification.getAlpha() >= 0) {
				worldController.eventNotification.draw(batch, camera);
			} else {
				message = Message.NONE;
				worldController.log.setMessage(Message.NONE);
			}
		}
	}

	public void resize(int width, int height) {
		//camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		viewPort.update(width, height);
		camera.update();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}
}
