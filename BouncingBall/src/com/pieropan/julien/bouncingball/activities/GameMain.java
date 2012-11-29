package com.pieropan.julien.bouncingball.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXObject;
import org.andengine.extension.tmx.TMXObjectGroup;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ScrollDetector;
import org.andengine.input.touch.detector.SurfaceScrollDetector;
import org.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.debug.Debug;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.pieropan.julien.bouncingball.blocks.Box;
import com.pieropan.julien.bouncingball.blocks.Enemy;
import com.pieropan.julien.bouncingball.blocks.EnemyBox;
import com.pieropan.julien.bouncingball.blocks.Player;
import com.pieropan.julien.bouncingball.helpers.MyUserData;

import android.graphics.Color;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.content.Intent;

public class GameMain extends SimpleBaseGameActivity implements IAccelerationListener, IScrollDetectorListener {

	// CONSTANTS
	
	private Integer enemyId = 0;
	
	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;
	
	private static final String FONT_RES = "Churli_cute5.ttf";
	private static final Integer FONT_SIZE = 38;
	
	private static final String CONTROL_RES = "onscreen_control_knob.png";
	
	private static final String PLAYER_RES = "face_circle_tiled.png";
	private static final String ENEMY_RES = "face_box_tiled.png";
	private static final String BONUS_RES = "face_hexagon_tiled.png";
	
	private static final String SOUND_JUMP_RES = "jump.ogg";
	private static final String SOUND_DEAD_RES = "dead.ogg";
	private static final String SOUND_BONUS_RES = "bonus.ogg";
	private static final String MUSIC_RES = "Kirby_Theme.ogg";

	private static final Float ENEMIES_VELOCITY = 2.0f;

	// FIELDS
	
	private Intent mIntent = null;
	private String mapNameIntent = null;
	private Integer playerLifesIntent = null;
	private Integer mapTimerIntent;
	private boolean isMusic = true;
	private boolean isSounds = true;
	
	private SmoothCamera mCamera = null;
	private SurfaceScrollDetector mScrollDetector = null;
	private EngineOptions mEngineOptions = null;
	
	private Scene mScene = null;
	private PhysicsWorld mPhysicsWorld = null;
	
	private List<AnimatedSprite> enemiesList = new ArrayList<AnimatedSprite>();
	private List<AnimatedSprite> bonusList = new ArrayList<AnimatedSprite>();
	
	private Integer currentTime = null;
	private TimerHandler mTimerHandler = null;
	
		// TEXT
	private ITexture mFontTextureAtlas = null;
	private Font mFont = null;
	
		// CONTROL
	private BitmapTextureAtlas mControlTextureAtlas = null;
	private ITextureRegion mControlTextureRegion = null;
	
		// PLAYER
	private boolean isLanded = false;
	private boolean isDead = false;
	private boolean isEnemiesWall = false;
	private boolean isAgainstWall = false;
	private Player player = null;
	private Body playerBody = null;
	private BitmapTextureAtlas mPlayerTextureAtlas = null;
	private TiledTextureRegion mPlayerTextureRegion = null;
	
		// ENEMIES
	private BitmapTextureAtlas mEnemyTextureAtlas = null;
	private TiledTextureRegion mEnemyTextureRegion = null;

		// BONUS
	private BitmapTextureAtlas mBonusTextureAtlas = null;
	private TiledTextureRegion mBonusTextureRegion = null;
	
		// SOUND
	private Music mMusic = null;
	private Sound mJumpSound = null;
	private Sound mDeadSound = null;
	private Sound mBonusSound = null;
	
		// MAP
	private TMXLoader mTmxLoader = null;
	private TMXTiledMap mTMXTiledMap = null;
	private RepeatingSpriteBackground mRepeatingSpriteBackground;
	
		// HUD
	private HUD mHUD = null;
	private Text mLifesChangeableText = null;
	private Text mTimerChangeableText = null;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		
		enemyId = 0;
		
		mCamera = new SmoothCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, 200, 200, 1.0f);
		
		mEngineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
		mEngineOptions.getAudioOptions().setNeedsSound(true).setNeedsMusic(true);
		
		return mEngineOptions;
	}
	
	@Override
	public Engine onCreateEngine(final EngineOptions pEngineOptions) {
         return new LimitedFPSEngine(pEngineOptions, 120);
	}
	
	@Override
	protected void onCreateResources() {
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		FontFactory.setAssetBasePath("font/");
		SoundFactory.setAssetBasePath("mfx/");
		MusicFactory.setAssetBasePath("mfx/");
		
		loadIntents();
		
		loadTextures();
		loadFont();
		loadController();
		loadSounds();
		loadMap();
		
	}

	private void loadTextures()
	{
		this.mPlayerTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		this.mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mPlayerTextureAtlas, this, GameMain.PLAYER_RES, 0, 32, 2, 1);
		this.mPlayerTextureAtlas.load();
		
		this.mEnemyTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		this.mEnemyTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mEnemyTextureAtlas, this, GameMain.ENEMY_RES, 0, 32, 2, 1);
		this.mEnemyTextureAtlas.load();
		
		this.mBonusTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		this.mBonusTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBonusTextureAtlas, this, GameMain.BONUS_RES, 0, 32, 2, 1);
		this.mBonusTextureAtlas.load();
		
	}
	
	private void loadFont()
	{
		this.mFontTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		this.mFont = FontFactory.createFromAsset(this.getFontManager(), this.mFontTextureAtlas, this.getAssets(), GameMain.FONT_RES, GameMain.FONT_SIZE, true, Color.WHITE);

		this.mEngine.getTextureManager().loadTexture(this.mFontTextureAtlas);
		this.mEngine.getFontManager().loadFont(this.mFont);
	}
	
	private void loadController()
	{
		this.mControlTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.BILINEAR);
		this.mControlTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mControlTextureAtlas, this, GameMain.CONTROL_RES, 0, 0);
		this.mControlTextureAtlas.load();
	}
	
	private void loadSounds()
	{
		try {
			this.mMusic = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, GameMain.MUSIC_RES);
			this.mMusic.setLooping(true);
			
			this.mDeadSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, GameMain.SOUND_DEAD_RES);
			this.mJumpSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, GameMain.SOUND_JUMP_RES);
			this.mBonusSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, GameMain.SOUND_BONUS_RES);
		} catch (final IOException e) {
			Debug.e(e);
		}
	}
	
	private void loadMap()
	{
		this.mRepeatingSpriteBackground = new RepeatingSpriteBackground(CAMERA_WIDTH, CAMERA_HEIGHT, this.getTextureManager(), AssetBitmapTextureAtlasSource.create(this.getAssets(), "gfx/" + this.mapNameIntent + ".png"), this.getVertexBufferObjectManager());
		
		this.mTmxLoader = new TMXLoader(this.getAssets(), this.mEngine.getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, this.getVertexBufferObjectManager());
		try {
			this.mTMXTiledMap = this.mTmxLoader.loadFromAsset("tmx/" + this.mapNameIntent + ".tmx");
		} catch (TMXLoadException e) {
			e.printStackTrace();
		}
	}
	
	private void loadObjectsFromMap(TMXTiledMap map) {
		for (final TMXObjectGroup group : map.getTMXObjectGroups())
		{
			if (group.getName().equals(Box.BOX_NAME))
				makeRectanglesFromObjects(group, Box.BOX_NAME);
			if (group.getName().equals(Box.BOX_VERTICAL_NAME))
				makeRectanglesFromObjects(group, Box.BOX_VERTICAL_NAME);
			if (group.getName().equals(EnemyBox.ENEMIES_BOX_NAME))
				makeRectanglesFromObjects(group, EnemyBox.ENEMIES_BOX_NAME);
			if (group.getName().equals("enemies"))
				makeRectanglesFromObjects(group, "enemies");
			if (group.getName().equals("bonus_jump"))
				this.spawnBonus(group);
			if (group.getName().equals(Enemy.ENEMIES_NAME))
				spawnMovingEnemies(group);
			if (group.getName().equals(Player.PLAYER_NAME))
				spawnPlayer(group);
		}
		this.mScene.attachChild(this.mTMXTiledMap.getTMXLayers().get(0));
	}
	
	private void makeRectanglesFromObjects(TMXObjectGroup group, String name)
	{
		Rectangle rect = null;
		Body rectBody = null;
		
		for (final TMXObject object : group.getTMXObjects()) {

			rect = new Rectangle(object.getX(), object.getY(), object.getWidth(), object.getHeight(),this.getVertexBufferObjectManager());
			rect.setVisible(false);
			rect.setUserData(new MyUserData(name));
			
			if (name.equals(Box.BOX_NAME) || name.equals(Box.BOX_VERTICAL_NAME) || name.equals("enemies"))
				rectBody = PhysicsFactory.createBoxBody(this.mPhysicsWorld, rect, BodyType.StaticBody, Box.BOX_FIXTURE_DEF);
			else if (name.equals(EnemyBox.ENEMIES_BOX_NAME))
				rectBody = PhysicsFactory.createBoxBody(this.mPhysicsWorld, rect, BodyType.StaticBody, EnemyBox.BOX_ENEMIES_FIXTURE_DEF);
			rectBody.setUserData(new MyUserData(name));

			mScene.attachChild(rect);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(rect, rectBody, true, true));
		}
	}
	
	private void spawnPlayer(TMXObjectGroup group)
	{
		TMXObject object = group.getTMXObjects().get(0);
		
		this.player = new Player(object.getX(), object.getY(), this.mPlayerTextureRegion, this.getVertexBufferObjectManager());
		this.player.setLifes(this.playerLifesIntent);
		
		this.playerBody = PhysicsFactory.createCircleBody(this.mPhysicsWorld, this.player, BodyType.DynamicBody, Player.PLAYER_FIXTURE_DEF);
		this.playerBody.setUserData(new MyUserData(Player.PLAYER_NAME));
		this.playerBody.setLinearDamping(1.5f);
		
		this.mScene.attachChild(this.player);
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this.player, this.playerBody, true, true));
		
		this.mCamera.setChaseEntity(this.player);
	}
	
	private void spawnMovingEnemies(TMXObjectGroup group)
	{
		Box enemy = null;
		Integer time = null;
		Path path = null;

		for (final TMXObject object : group.getTMXObjects()) {
		
			enemy = new Box(object.getX(), object.getY(), this.mPlayerTextureRegion, this.getVertexBufferObjectManager());
			enemy.setColor(0, 1, 0);

			if (object.getWidth() > 40)
			{
				path = new Path(3).to(object.getX(), object.getY())
									.to(object.getX() + object.getWidth(), object.getY())
									.to(object.getX(), object.getY());
				time = object.getWidth() / 32;
			}
			else if (object.getHeight() > 40)
			{
				path = new Path(3).to(object.getX(), object.getY())
						.to(object.getX(), object.getY() + object.getHeight())
						.to(object.getX(), object.getY());
				time = object.getHeight() / 32;
			}
			else
				path = null;
			
			if (path != null)
			{
				enemy.registerEntityModifier(new LoopEntityModifier(new PathModifier(time, path, null, new IPathModifierListener() {
					@Override
					public void onPathStarted(final PathModifier pPathModifier, final IEntity pEntity) {
					}
					@Override
					public void onPathWaypointStarted(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) {
					}
					@Override
					public void onPathWaypointFinished(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) {
					}
					@Override
					public void onPathFinished(final PathModifier pPathModifier, final IEntity pEntity) {
					}
				})));
				this.mScene.attachChild(enemy);
				this.enemiesList.add(enemy);
			}
		}
	}
	
	private void spawnBonus(TMXObjectGroup group)
	{
		TMXObject object = group.getTMXObjects().get(0);
		
		AnimatedSprite bonus = new AnimatedSprite(object.getX(), object.getY(), this.mBonusTextureRegion, this.getVertexBufferObjectManager());
		bonus.setUserData("bonus_jump");
		bonus.setColor(1, 1, 1);
		
		this.mScene.attachChild(bonus);
		
		this.bonusList.add(bonus);
	}
	
	private void loadIntents()
	{
		Bundle extras = this.getIntent().getExtras();
		
		this.playerLifesIntent = extras.getInt(GameMenu.INTENT_MAP_LIFE);
		this.mapNameIntent = extras.getString(GameMenu.INTENT_MAP_NAME);
		this.isMusic = extras.getBoolean(GameMenu.INTENT_SETTING_MUSIC);
		this.isSounds = extras.getBoolean(GameMenu.INTENT_SETTING_SOUNDS);
	}
	
	@Override
	protected Scene onCreateScene() {
		
		this.mapTimerIntent = 30000;
		
		this.mScene = new Scene();
		this.mScene.setBackground(this.mRepeatingSpriteBackground);
		
		this.mScrollDetector = new SurfaceScrollDetector(this);
		this.mPhysicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_JUPITER), false);
		
		loadHud();
		loadObjectsFromMap(this.mTMXTiledMap);
		
		createCollisionListener();
		createGameUpdateHandler();
		createGameTimerHandler();
		
		currentTime = this.mapTimerIntent;
		
		this.mScene.registerUpdateHandler(this.mPhysicsWorld);
		
		if (this.isMusic)
			this.mMusic.play();
		
		return this.mScene;
	}

	private void loadHud()
	{
		this.mHUD = new HUD();
		
		Rectangle rect = new Rectangle(0, 0, GameMain.CAMERA_WIDTH, GameMain.CAMERA_HEIGHT / 12,this.getVertexBufferObjectManager());
		rect.setColor(0, 0, 0);
		rect.setAlpha(0.5f);
		this.mHUD.attachChild(rect);
		
		rect = new Rectangle(GameMain.CAMERA_WIDTH / 7f * 6f, 0, GameMain.CAMERA_WIDTH / 7f, GameMain.CAMERA_HEIGHT / 12, this.getVertexBufferObjectManager());
		rect.setColor(0, 0, 0);
		rect.setAlpha(0.8f);
		this.mHUD.attachChild(rect);
		
		Line line = new Line(0, GameMain.CAMERA_HEIGHT / 12, GameMain.CAMERA_WIDTH, GameMain.CAMERA_HEIGHT / 12, this.getVertexBufferObjectManager());
		line.setColor(0, 0, 0);
		this.mHUD.attachChild(line);
		
		Text mMenuText = new Text(GameMain.CAMERA_WIDTH / 7 * 6 + 8, 0, mFont, "pause", 5, this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pEvent, float pX, float pY) {
				if (pEvent.isActionDown()) {
					mIntent = new Intent(GameMain.this, GameMenu.class);
					mIntent.putExtra(GameMenu.INTENT_SETTING_MUSIC, isMusic);
					mIntent.putExtra(GameMenu.INTENT_SETTING_SOUNDS, isSounds);
					startActivity(mIntent);
					finish();
					return true;
				}
				return false;
			}
		};
		mMenuText.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		mHUD.registerTouchArea(mMenuText);
		mHUD.attachChild(mMenuText);
		
		this.mLifesChangeableText = new Text(5, 0, mFont, "lives : " + this.playerLifesIntent, "score : XXXX".length(), this.getVertexBufferObjectManager());
		this.mLifesChangeableText.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mHUD.attachChild(this.mLifesChangeableText);
		
		this.mTimerChangeableText = new Text(this.CAMERA_WIDTH / 7 * 2, 0, mFont, "time : " + this.mapTimerIntent, "time : XXXXX".length(), this.getVertexBufferObjectManager());
		this.mTimerChangeableText.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mHUD.attachChild(this.mTimerChangeableText);
		
		Sprite jump = new Sprite(GameMain.CAMERA_WIDTH / 10 * 9, GameMain.CAMERA_HEIGHT / 7 * 6, this.mControlTextureRegion, this.getVertexBufferObjectManager()) {

			@Override
			public boolean onAreaTouched(TouchEvent pEvent, float pX, float pY) {
				if (pEvent.isActionDown()) {
					if (isSounds)
						mJumpSound.play();
					playerBody.applyLinearImpulse(0, player.getJumpVelocity(), playerBody.getPosition().x, playerBody.getPosition().y);
					return true;
				}
				return false;
			}

		};
		jump.setScale(1.5f);
		mHUD.registerTouchArea(jump);
		mHUD.attachChild(jump);
		
		this.mCamera.setHUD(mHUD);
	}
	
	private void createCollisionListener() {
		this.mPhysicsWorld.setContactListener(new ContactListener() {
			
			public void beginContact(Contact contact) {
				
				MyUserData fix1_object, fix2_object;
				String fix1_name, fix2_name;

				if (contact.getFixtureA().getBody().getUserData() != null && contact.getFixtureA().getBody().getUserData() != null) {

					fix1_object = (MyUserData) contact.getFixtureA().getBody().getUserData();
					fix2_object = (MyUserData) contact.getFixtureB().getBody().getUserData();
					fix1_name = fix1_object.getName();
					fix2_name = fix2_object.getName();

				} else {
					fix1_name = "";
					fix2_name = "";
				}

				if ((fix1_name.equals("player") && fix2_name.equals("wall")) || 
						(fix2_name.equals("player") && fix1_name.equals("wall"))) {
					isLanded = true;
				}
				
				if ((fix1_name.equals("player") && fix2_name.equals("enemies")) || 
						(fix2_name.equals("player") && fix1_name.equals("enemies"))) {
					isDead = true;
				}


			}

			public void endContact(Contact contact) {
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				
				if (isDead)
				{
					isDead = false;
					if (isSounds)
						mDeadSound.play();
					player.setLifes(player.getLifes() - 1);
					mIntent = new Intent(GameMain.this, GameHit.class);
					mIntent.putExtra(GameMenu.INTENT_MAP_NAME, mapNameIntent);
					mIntent.putExtra(GameMenu.INTENT_MAP_LIFE, player.getLifes());
					mIntent.putExtra(GameMenu.INTENT_SETTING_MUSIC, isMusic);
					mIntent.putExtra(GameMenu.INTENT_SETTING_SOUNDS, isSounds);
					startActivity(mIntent);
					finish();
				}
				
			}
		});
	}
	
	public void createGameUpdateHandler() {

		this.mScene.registerUpdateHandler(new IUpdateHandler() {
			public void onUpdate(float pSecondsElapsed) {
				
				for (AnimatedSprite sp : enemiesList)
				{
					if (sp.collidesWith(player))
					{
						endGame();
					}
				}
				
				for (AnimatedSprite sp : bonusList)
				{
					if (sp.collidesWith(player))
					{
						if (player.getJumpVelocity() < Player.PLAYER_JUMP_VELOCITY * 1.5f)
						{
							if (isSounds)
								mBonusSound.play();
							player.setJumpVelocity();
						}
						mScene.detachChild(sp);
					}
				}
				
			}

			@Override
			public void reset() {
			}
		});
	}
	
	private void endGame()
	{
		if (isSounds)
			mDeadSound.play();
		mIntent = new Intent(GameMain.this, GameHit.class);
		mIntent.putExtra(GameMenu.INTENT_MAP_NAME, mapNameIntent);
		mIntent.putExtra(GameMenu.INTENT_MAP_LIFE, player.getLifes());
		mIntent.putExtra(GameMenu.INTENT_SETTING_MUSIC, isMusic);
		mIntent.putExtra(GameMenu.INTENT_SETTING_SOUNDS, isSounds);
		startActivity(mIntent);
		finish();
	}
	
	private void createGameTimerHandler() {
		this.mTimerHandler = new TimerHandler(1.0f / 10.0f, true, new ITimerCallback() {
	        @Override
	        public void onTimePassed(final TimerHandler pTimerHandler) {
	        	currentTime = currentTime - 10;
	        	
	        	if (currentTime < 1000)
	        		mTimerChangeableText.setColor(1, 0, 0);
	        	else
	        		mTimerChangeableText.setColor(1, 1, 1);
	        	
	        	mTimerChangeableText.setText("time : " + currentTime);
	        }
		});

		this.mScene.registerUpdateHandler(this.mTimerHandler);
	}
	
	@Override
	public void onResumeGame() {
		super.onResumeGame();
		this.enableAccelerationSensor(this);
		
		if (this.isMusic)
			this.mMusic.resume();
	}

	@Override
	public void onPauseGame() {
		super.onPauseGame();
		this.disableAccelerationSensor();
		
		if (this.isMusic)
			this.mMusic.pause();
	}
	
	// IAccelerationListener
	
	@Override
	public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAccelerationChanged(AccelerationData pAccelerationData) {
		
		Vector2 gravity = new Vector2(pAccelerationData.getX(), this.mPhysicsWorld.getGravity().y);
		this.mPhysicsWorld.setGravity(gravity);
		
		this.playerBody.setLinearVelocity(pAccelerationData.getX(), this.playerBody.getLinearVelocity().y);
	}
	
	// IScrollDetectorListener
	
	@Override
	public void onScrollStarted(ScrollDetector pScollDetector, int pPointerID,
			float pDistanceX, float pDistanceY) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onScroll(ScrollDetector pScollDetector, int pPointerID,
			float pDistanceX, float pDistanceY) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onScrollFinished(ScrollDetector pScollDetector, int pPointerID,
			float pDistanceX, float pDistanceY) {
		// TODO Auto-generated method stub
		
	}
}