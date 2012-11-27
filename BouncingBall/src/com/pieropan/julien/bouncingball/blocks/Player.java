package com.pieropan.julien.bouncingball.blocks;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Player extends AnimatedSprite {

	// CONSTANTS
	
	public static final Integer PLAYER_LIFES = 3;
	public static final String PLAYER_NAME = "player";
	public static final short CATEGORYBIT_PLAYER = 4;
	public static final short MASKBITS_PLAYER = Player.CATEGORYBIT_PLAYER + Box.CATEGORYBIT_BOX + Enemy.CATEGORYBIT_ENEMIES;
	public static final FixtureDef PLAYER_FIXTURE_DEF = PhysicsFactory.createFixtureDef(1.0f, 0.0f, 1.0f, 
																						false, 
																						Player.CATEGORYBIT_PLAYER, 
																						Player.MASKBITS_PLAYER, 
																						(short)0);
	
	
	// FIELDS
	
	private Integer lifes;
	
	// METHODS
	
    public Player(final float pX, final float pY, final TiledTextureRegion pTiledTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
    }

	public Integer getLifes() {
		return lifes;
	}

	public void setLifes(Integer lifes) {
		this.lifes = lifes;
	}

}
