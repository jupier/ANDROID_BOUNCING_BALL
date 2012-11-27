package com.pieropan.julien.bouncingball.blocks;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Box extends AnimatedSprite {

	// CONSTANTS

	public static final String BOX_NAME = "wall";
	public static final String BOX_VERTICAL_NAME = "wall_vertical";
	public static final short CATEGORYBIT_BOX = 1;
	public static final short MASKBITS_BOX = Box.CATEGORYBIT_BOX + Player.CATEGORYBIT_PLAYER + Enemy.CATEGORYBIT_ENEMIES;
	public static final FixtureDef BOX_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0f, 0.5f, 0.5f, 
																					false, 
																					Box.CATEGORYBIT_BOX, 
																					Box.MASKBITS_BOX, 
																					(short)0);
	
	// METHODS
	
    public Box(final float pX, final float pY, final TiledTextureRegion pTiledTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
    }

}
