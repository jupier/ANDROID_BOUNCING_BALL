package com.pieropan.julien.bouncingball.activities;

import com.pieropan.julien.bouncingball.helpers.MyIntent;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class GameSettings extends Activity {

	// FIELDS
	
	private Typeface font = null;

	private MyIntent extras = null;
	
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
		
		cbMusic.setChecked(this.extras.isMusic());
		cbSounds.setChecked(this.extras.isSounds());
		
		createListeners();
	}
	
	private void loadIntents()
	{
		Bundle extras = this.getIntent().getExtras();
		
		this.extras = (MyIntent) extras.getSerializable(GameMenu.INTENT_NAME);
	}

	private void createListeners()
	{
		btnOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				extras.setMusic(cbMusic.isChecked());
				extras.setSounds(cbSounds.isChecked());
				
				mIntent = new Intent(GameSettings.this, GameMenu.class);
				mIntent.putExtra(GameMenu.INTENT_NAME, extras);
				startActivity(mIntent);
				finish();
			}
		});
	}

}
