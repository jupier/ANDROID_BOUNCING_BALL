package com.pieropan.julien.bouncingball.activities;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class GameSettings extends Activity {

	// FIELDS
	
	private Typeface font = null;

	private boolean isMusic = true;
	private boolean isSounds = true;
	
	private CheckBox cbMusic = null;
	private CheckBox cbSounds = null;
	private Button btnOk = null;
	
	private Intent mIntent = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_settings);
		
		// Chargement de la police de caractère
		font = Typeface.createFromAsset(getAssets(), "font/Churli_cute5.ttf");
						
		// Chargement des boutons
		cbMusic = (CheckBox) findViewById(R.id.cbMusic);
		cbSounds = (CheckBox) findViewById(R.id.cbSounds);
		btnOk = (Button) findViewById(R.id.btnOk);
		
		cbMusic.setTypeface(font);
		cbSounds.setTypeface(font);
		btnOk.setTypeface(font);
		
		loadIntents();
		
		cbMusic.setChecked(isMusic);
		cbSounds.setChecked(isSounds);
		
		createListeners();
	}
	
	private void loadIntents()
	{
		Bundle extras = this.getIntent().getExtras();
		
		this.isMusic = extras.getBoolean(GameMenu.INTENT_SETTING_MUSIC);
		this.isSounds = extras.getBoolean(GameMenu.INTENT_SETTING_SOUNDS);
	}

	private void createListeners()
	{
		btnOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mIntent = new Intent(GameSettings.this, GameMenu.class);
				mIntent.putExtra(GameMenu.INTENT_SETTING_MUSIC, cbMusic.isChecked());
				mIntent.putExtra(GameMenu.INTENT_SETTING_SOUNDS, cbSounds.isChecked());
				startActivity(mIntent);
				finish();
			}
		});
	}

}
