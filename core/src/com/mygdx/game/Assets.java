package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.Constants;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;


	public class Assets implements Disposable, AssetErrorListener {
		
		public static final String TAG = Assets.class.getName();
		public static final Assets instance = new Assets();
		private AssetManager assetManager;
		
		public AssetSaw assetSaw;
		public AssetLog assetLog;
		public AssetTestLine assetTestLine;
		public AssetLevelDecoration assetLevelDecoration;
		public AssetTarget assetTarget;
		
		
		
		// singleton: prevent instantiation from other classes
		private Assets () {}

		public void init (AssetManager assetManager) {
			this.assetManager = assetManager;
			// set asset manager error handler
			assetManager.setErrorListener(this);
			// load texture atlas
			assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
			// start loading assets and wait until finished
			assetManager.finishLoading();

			Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);

			TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
			
			for (String a : assetManager.getAssetNames()) Gdx.app.debug(TAG, "asset: " + a);
			
			for(Texture t : atlas.getTextures()) {
				t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			}
			
			assetLog = new AssetLog(atlas);
			assetSaw = new AssetSaw(atlas);
			assetTestLine = new AssetTestLine(atlas);
			assetLevelDecoration = new AssetLevelDecoration(atlas);
			assetTarget = new AssetTarget(atlas);
				
			}
		
		
			@Override
			public void dispose () {
				assetManager.dispose();
			}

			@Override
			public void error(AssetDescriptor asset, Throwable throwable) {
				
				Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", (Exception)throwable);
				
			}
			
			public class AssetSaw {
				public final AtlasRegion sawBladeF1;
				public final AtlasRegion sawBladeF2;
				public final AtlasRegion sawRail;
				public AssetSaw (TextureAtlas atlas) {
					sawBladeF1 = atlas.findRegion("sawBladeF1");
					sawBladeF2 = atlas.findRegion("sawBladeF2");
					sawRail = atlas.findRegion("sawRail");
				}
			}
			
			public class AssetTarget {
				public final AtlasRegion targetLine;
				public AssetTarget (TextureAtlas atlas) {
					targetLine = atlas.findRegion("targetLine");
				}
			}
			
			public class AssetLog {
				//TODO this needs cleaning
				public final AtlasRegion logBottom;
				public final AtlasRegion logMiddle;
				public final AtlasRegion logTopPatch;
				public final AtlasRegion log;
				public final AtlasRegion logSplit;
				
				public final TextureAtlas atlas;
				
				public AssetLog(TextureAtlas atlas) {
					logBottom = atlas.findRegion("logBottom");
					logMiddle = atlas.findRegion("logMiddle");
					logTopPatch = atlas.findRegion("logTopPatch");
					log = atlas.findRegion("log");
					logSplit = atlas.findRegion("logSplit");
					this.atlas = atlas;
				}

			}
			
			public class AssetTestLine {
				public final AtlasRegion testLine;
				public AssetTestLine(TextureAtlas atlas) {
					testLine = atlas.findRegion("testLine");
				}
			}
			
			public class AssetLevelDecoration {
				public final AtlasRegion smallMovingConveyorBelt;
				public final AtlasRegion longMovingConveyorBelt;
				public final AtlasRegion logHallway;
				
				public AssetLevelDecoration(TextureAtlas atlas) {
					smallMovingConveyorBelt = atlas.findRegion("smallConveyorBeltF1");
					longMovingConveyorBelt = atlas.findRegion("longMovingConveyorBeltF1");
					logHallway = atlas.findRegion("logHallway");
				}
			}
			
}
	

