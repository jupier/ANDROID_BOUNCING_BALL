package com.pieropan.julien.bouncingball.blocks;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.FixtureDef;

public class EnemyBox extends AnimatedSprite {

	// CONSTANTS
	
	public static final String ENEMIES_BOX_NAME = "enemies_wall";
	public static final short CATEGORYBIT_BOX_ENEMIES = 8;
	public static final short MASKBITS_BOX_ENEMIES = EnemyBox.CATEGORYBIT_BOX_ENEMIES + Enemy.CATEGORYBIT_ENEMIES;
	public static final FixtureDef BOX_ENEMIES_FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f, 
																					false, 
																					EnemyBox.CATEGORYBIT_BOX_ENEMIES, 
																					EnemyBox.MASKBITS_BOX_ENEMIES, 
																					(short)0);
	
	// METHODS
	
    public EnemyBox(final float pX, final float pY, final TiledTextureRegion pTiledTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
    }
	
}
