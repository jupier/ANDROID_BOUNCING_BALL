package com.pieropan.julien.bouncingball.activities;

import com.pieropan.julien.bouncingball.blocks.Player;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;

public class GameMaps extends Activity {

	// FIELDS
	
	private boolean isMusic = true;
	private boolean isSounds = true;
	
	private Typeface font = null;
	
	private Button btnMapIsland = null;
	private Button btnMapCave = null;
	private Button btnReturn = null;
	
	private Intent mIntent = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_maps);
		
		font = Typeface.createFromAsset(getAssets(), "font/Churli_cute5.ttf");
		
		btnMapIsland = (Button) findViewById(R.id.mapIslandBtn);
		btnMapCave = (Button) findViewById(R.id.mapCaveBtn);
		btnReturn = (Button) findViewById(R.id.btnReturn);
		
		this.btnMapIsland.setTypeface(font);
		this.btnMapCave.setTypeface(font);
		this.btnReturn.setTypeface(font);
		
		loadIntents();
		
		createListeners();
	}
	
	private void loadIntents()
	{
		Bundle extras = this.getIntent().getExtras();
		
		if (extras != null)
		{
			this.isMusic = extras.getBoolean(GameMenu.INTENT_SETTING_MUSIC);
			this.isSounds = extras.getBoolean(GameMenu.INTENT_SETTING_SOUNDS);
		}
	}
	
	private void createListeners()
	{
		btnMapIsland.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mIntent = new Intent(GameMaps.this, GameMain.class);
				mIntent.putExtra(GameMenu.INTENT_MAP_NAME, "map_island");
				mIntent.putExtra(GameMenu.INTENT_MAP_LIFE, Player.PLAYER_LIFES);
				mIntent.putExtra(GameMenu.INTENT_SETTING_MUSIC, isMusic);
				mIntent.putExtra(GameMenu.INTENT_SETTING_SOUNDS, isSounds);
				startActivity(mIntent);
				finish();
			}
		});
		
		btnMapCave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mIntent = new Intent(GameMaps.this, GameMain.class);
				mIntent.putExtra(GameMenu.INTENT_MAP_NAME, "map_cave");
				mIntent.putExtra(GameMenu.INTENT_MAP_LIFE, Player.PLAYER_LIFES);
				mIntent.putExtra(GameMenu.INTENT_SETTING_MUSIC, isMusic);
				mIntent.putExtra(GameMenu.INTENT_SETTING_SOUNDS, isSounds);
				startActivity(mIntent);
				finish();
			}
		});
		
		btnReturn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mIntent = new Intent(GameMaps.this, GameMenu.class);
				mIntent.putExtra(GameMenu.INTENT_SETTING_MUSIC, isMusic);
				mIntent.putExtra(GameMenu.INTENT_SETTING_SOUNDS, isSounds);
				startActivity(mIntent);
				finish();
			}
		});
	}
}
