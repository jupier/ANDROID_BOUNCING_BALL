package com.pieropan.julien.bouncingball.activities;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameHit extends Activity {

	// FIELDS
	
	private Integer playerLifesIntent = null;
	private String mapNameIntent = null;
	private boolean isMusic = true;
	private boolean isSounds = true;
	
	private Typeface font = null;
	
	private Button btnContinue = null;
	private Button btnQuit = null;
	private TextView tvLifes = null;
	
	private Intent mIntent = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_hit);
		
		// Chargement des données à partir de l'intent
		loadIntents();
		
		// Chargement de la police de caractère
		font = Typeface.createFromAsset(getAssets(), "font/Churli_cute5.ttf");
				
		// Chargement des boutons
		btnContinue = (Button) findViewById(R.id.continueBtn);
		btnQuit = (Button) findViewById(R.id.quitBtn);
		tvLifes = (TextView) findViewById(R.id.tvLifes);
		
		btnContinue.setTypeface(font);
		btnQuit.setTypeface(font);
		tvLifes.setTypeface(font);
		
		if (this.playerLifesIntent == 0)
		{	
			tvLifes.setText("game over");
			btnContinue.setEnabled(false);
		}
		else
		{
			tvLifes.setText("lifes : " + playerLifesIntent);
			btnContinue.setEnabled(true);
		}
		
		createListeners();
	}
	
	private void loadIntents()
	{
		Bundle extras = this.getIntent().getExtras();
		
		this.playerLifesIntent = extras.getInt(GameMenu.INTENT_MAP_LIFE) - 1;
		this.mapNameIntent = extras.getString(GameMenu.INTENT_MAP_NAME);
		this.isMusic = extras.getBoolean(GameMenu.INTENT_SETTING_MUSIC);
		this.isSounds = extras.getBoolean(GameMenu.INTENT_SETTING_SOUNDS);
	}
	
	private void createListeners()
	{
		btnContinue.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mIntent = new Intent(GameHit.this, GameMain.class);
				mIntent.putExtra(GameMenu.INTENT_MAP_NAME, mapNameIntent);
				mIntent.putExtra(GameMenu.INTENT_MAP_LIFE, playerLifesIntent);
				mIntent.putExtra(GameMenu.INTENT_SETTING_MUSIC, isMusic);
				mIntent.putExtra(GameMenu.INTENT_SETTING_SOUNDS, isSounds);
				startActivity(mIntent);
				finish();
			}
		});
		
		btnQuit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mIntent = new Intent(GameHit.this, GameMenu.class);
				mIntent.putExtra(GameMenu.INTENT_SETTING_MUSIC, isMusic);
				mIntent.putExtra(GameMenu.INTENT_SETTING_SOUNDS, isSounds);
				startActivity(mIntent);
				finish();
			}
		});
	}

}
