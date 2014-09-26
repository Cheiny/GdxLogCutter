package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.mygdx.game.entities.CautionLine;
import com.mygdx.game.entities.Log;
import com.mygdx.game.entities.LogSplit;
import com.mygdx.game.entities.SawBlade;
import com.mygdx.game.entities.SawRail;

public class WorldController extends InputAdapter {
	
	public static final String TAG = WorldController.class.getName();
	
	public CameraHelper cameraHelper;
	
	float genTargetY = 0;
	
	public ScoreKeeper scoreKeeper;
	public Log log;
	public SawBlade sawBlade;
	public SawRail sawRail;
	public CautionLine cautionLine;
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
		
		scoreKeeper = new ScoreKeeper(0, 10); // TODO these values may be used for score display... if not get rid of them
		log = new Log(0, 9, 1, 5);
		sawBlade = new SawBlade(1, 3.5f, 1, 1);
		sawRail = new SawRail(-1.25f, 3.3f, 3.5f, 0.4f);
		cautionLine = new CautionLine(-1.5f, 0, 0.5f, 10);
	}


	public void update(float deltaTime) {
		upDateGameObjects(deltaTime);
		handleGameInput(deltaTime);
		scoreKeeper.update(deltaTime);
		cameraHelper.update(deltaTime);
	}

	private void handleGameInput(float deltaTime) {
		if(Gdx.input.justTouched()) {
			sawBlade.slide();
			if(log.getFalling() == true) {
				log.setFalling(false);
				log.split();
				scoreKeeper.updateScore(log.getCutPoints());
				if(log.getCutPoints() == 10) {
					log.target.split();
				}
			} else {
				//log.setFalling(true);
			}
		}
		
	}

	private void upDateGameObjects(float deltaTime) { //TODO!!! account for deltaTime for everything that needs updating
		log.update(deltaTime);
		sawBlade.update(deltaTime);
		//target.update(deltaTime);
		
		if(log.isCut()) {
			for(int i=0; i<log.getNumberOfLogs(); i++) {
				log.logSplit[i].setFalling(log.getFalling());
				log.logSplit[i].update(deltaTime);
			}
		}
	}	
}
