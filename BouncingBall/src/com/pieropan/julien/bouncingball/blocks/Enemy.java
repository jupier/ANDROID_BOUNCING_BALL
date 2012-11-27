package com.pieropan.julien.bouncingball.blocks;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Enemy extends AnimatedSprite {

	// CONSTANTS
	
	public static final String ENEMIES_NAME = "enemies_move";
	public static final short CATEGORYBIT_ENEMIES = 16;
	public static final short MASKBITS_ENEMIES = EnemyBox.CATEGORYBIT_BOX_ENEMIES + Player.CATEGORYBIT_PLAYER + Box.CATEGORYBIT_BOX;
	public static final FixtureDef ENEMIES_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0, 0, 
																					false, 
																					Enemy.CATEGORYBIT_ENEMIES, 
																					Enemy.MASKBITS_ENEMIES, 
																					(short)0);
	
	// METHODS
	
    public Enemy(final float pX, final float pY, final TiledTextureRegion pTiledTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
    }
	
}
