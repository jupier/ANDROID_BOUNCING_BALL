package com.pieropan.julien.bouncingball.activities;

import com.pieropan.julien.bouncingball.blocks.Player;
import com.pieropan.julien.bouncingball.helpers.MyIntent;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GameMenu extends Activity {

	// CONSTANTS
	
	public static final String INTENT_NAME = "BOUNCING_BALL_INTENT";
	
	/*public static final String INTENT_MAP_NAME = "MAP_NAME";
	public static final String INTENT_MAP_LIFE = "MAP_LIFE";
	public static final String INTENT_MAP_TIMER = "MAP_TIMER";
	public static final String INTENT_SETTING_MUSIC = "SETTING_MUSIC";
	public static final String INTENT_SETTING_SOUNDS = "SETTING_SOUNDS";*/
	
	// FIELDS
	private Typeface font = null;
	
	private MyIntent extras = new MyIntent(null, null, Player.PLAYER_LIFES, null, true, true);
	
	//private boolean isMusic = true;
	//private boolean isSounds = true;
	
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
		Bundle extra = this.getIntent().getExtras();
		
		if (extra != null)
		{
			this.extras = (MyIntent) extra.getSerializable(GameMenu.INTENT_NAME);
		}
	}
	
	private void createListeners()
	{
		btnPlay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mIntent = new Intent(GameMenu.this, GameWorlds.class);
				mIntent.putExtra(GameMenu.INTENT_NAME, extras);
				startActivity(mIntent);
				finish();
			}
		});
		
		btnSettings.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mIntent = new Intent(GameMenu.this, GameSettings.class);
				mIntent.putExtra(GameMenu.INTENT_NAME, extras);
				startActivity(mIntent);
				finish();
			}
		});
		
		btnAbout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Context context = getApplicationContext();
				CharSequence text = " :D Boloss";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
				
			}
		});
	}
	

}
