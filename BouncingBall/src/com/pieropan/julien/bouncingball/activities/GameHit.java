package com.pieropan.julien.bouncingball.activities;

import com.pieropan.julien.bouncingball.helpers.MyIntent;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameHit extends Activity {

	// FIELDS
	
	private Typeface font = null;
	
	private MyIntent extras = null;
	
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
		
		if (this.extras.getLifes() == 0)
		{	
			tvLifes.setText("game over");
			btnContinue.setEnabled(false);
		}
		else
		{
			tvLifes.setText("lifes : " + this.extras.getLifes());
			btnContinue.setEnabled(true);
		}
		
		createListeners();
	}
	
	private void loadIntents()
	{
		Bundle extra = this.getIntent().getExtras();
		
		if (extra != null)
		{
			this.extras = (MyIntent) extra.getSerializable(GameMenu.INTENT_NAME);
			this.extras.setLifes(this.extras.getLifes() - 1);
		}
	}
	
	private void createListeners()
	{
		btnContinue.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mIntent = new Intent(GameHit.this, GameMain.class);
				mIntent.putExtra(GameMenu.INTENT_NAME, extras);
				startActivity(mIntent);
				finish();
			}
		});
		
		btnQuit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mIntent = new Intent(GameHit.this, GameMenu.class);
				mIntent.putExtra(GameMenu.INTENT_NAME, extras);
				startActivity(mIntent);
				finish();
			}
		});
	}

}
