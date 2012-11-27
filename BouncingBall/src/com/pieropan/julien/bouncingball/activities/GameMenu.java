package com.pieropan.julien.bouncingball.activities;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;

public class GameMenu extends Activity {

	// CONSTANTS
	
	public static final String INTENT_MAP_NAME = "MAP_NAME";
	public static final String INTENT_MAP_LIFE = "MAP_LIFE";
	public static final String INTENT_SETTING_MUSIC = "SETTING_MUSIC";
	public static final String INTENT_SETTING_SOUNDS = "SETTING_SOUNDS";
	
	// FIELDS
	private Typeface font = null;
	
	private boolean isMusic = true;
	private boolean isSounds = true;
	
	private Button btnPlay = null;
	private Button btnSettings = null;
	private Button btnAbout = null;
	
	private Intent mIntent = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_menu);
		
		// Chargement de la police de caractère
		font = Typeface.createFromAsset(getAssets(), "font/Churli_cute5.ttf");
		
		// Chargement des boutons
		btnPlay = (Button) findViewById(R.id.playBtn);
		btnSettings = (Button) findViewById(R.id.settingsBtn);
		btnAbout = (Button) findViewById(R.id.aboutBtn);
		
		btnPlay.setTypeface(font);
		btnSettings.setTypeface(font);
		btnAbout.setTypeface(font);
		
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
		btnPlay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mIntent = new Intent(GameMenu.this, GameMaps.class);
				mIntent.putExtra(GameMenu.INTENT_SETTING_MUSIC, isMusic);
				mIntent.putExtra(GameMenu.INTENT_SETTING_SOUNDS, isSounds);
				startActivity(mIntent);
				finish();
			}
		});
		
		btnSettings.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mIntent = new Intent(GameMenu.this, GameSettings.class);
				mIntent.putExtra(GameMenu.INTENT_SETTING_MUSIC, isMusic);
				mIntent.putExtra(GameMenu.INTENT_SETTING_SOUNDS, isSounds);
				startActivity(mIntent);
				finish();
			}
		});
		
	}

}
