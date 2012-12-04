package com.pieropan.julien.bouncingball.activities;

import com.pieropan.julien.bouncingball.database.HighScore;
import com.pieropan.julien.bouncingball.database.HighScoreDAO;
import com.pieropan.julien.bouncingball.helpers.MapCaracteristic;
import com.pieropan.julien.bouncingball.helpers.MapSelector;
import com.pieropan.julien.bouncingball.helpers.MyIntent;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameWin extends Activity {

	// FIELDS
	
	private Typeface font = null;
	
	private MyIntent extras = null;
	
	private Button btnNext = null;
	private Button btnRetry = null;
	private Button btnQuit = null;
	private TextView tvLifes = null;
	private TextView tvTime = null;
	
	private Intent mIntent = null;
	
	private MapCaracteristic nextCarac = null;
	
	private HighScoreDAO highScoreDAO = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_win);

		// Instantation de la classe dao pour la db
		this.highScoreDAO = new HighScoreDAO(this);
		this.highScoreDAO.open();
		
		// Chargement des données à partir de l'intent
		loadIntents();
		
		HighScore h = this.highScoreDAO.getHighScores(this.extras.getWorld()).get(0);
		
		System.out.println(h.getWorld() + " " + h.getMap() + " " + h.getTimer());
		
		updateScore();
		
		h = this.highScoreDAO.getHighScores(this.extras.getWorld()).get(0);
		
		System.out.println(h.getWorld() + " " + h.getMap() + " " + h.getTimer());
		
		// Chargement de la police de caractère
		font = Typeface.createFromAsset(getAssets(), "font/Churli_cute5.ttf");
		
		// Chargement des boutons
		btnRetry = (Button) findViewById(R.id.continueBtn2);
		btnQuit = (Button) findViewById(R.id.quitBtn2);
		btnNext = (Button) findViewById(R.id.nextBtn);
		tvLifes = (TextView) findViewById(R.id.lifesTV);
		tvTime = (TextView) findViewById(R.id.timeTV);
		
		btnRetry.setTypeface(font);
		btnQuit.setTypeface(font);
		btnNext.setTypeface(font);
		tvLifes.setTypeface(font);
		tvTime.setTypeface(font);
		
		tvLifes.setText("life : " + this.extras.getLifes());
		tvTime.setText("time : " + (this.extras.getMap().getTimer() - this.extras.getTimer()));
		
		if (this.nextCarac == null)
			this.btnNext.setEnabled(false);
		else
			this.btnNext.setEnabled(true);
		
		this.createListeners();
	}
	
	private void loadIntents()
	{
		Bundle extra = this.getIntent().getExtras();

		if (extra != null)
		{
			this.extras = (MyIntent) extra.getSerializable(GameMenu.INTENT_NAME);
			this.nextCarac = MapSelector.getInstance().nextCarac(this.extras.getWorld(), this.extras.getMap().getName());
		}
	}
	
	private void updateScore()
	{
		if (this.highScoreDAO.exist(this.extras.getWorld(), this.extras.getMap().getName()))
		{
			System.out.println("update");
			
			this.highScoreDAO.updateHighScore(	this.extras.getWorld(), 
												this.extras.getMap().getName(),
												this.extras.getMap().getTimer() - this.extras.getTimer());
		}
		else
		{
			System.out.println("create");
			
			this.highScoreDAO.createHighScore(	this.extras.getWorld(), 
												this.extras.getMap().getName(),
												this.extras.getMap().getTimer() - this.extras.getTimer());
		}
	}
	
	private void createListeners()
	{
		btnRetry.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mIntent = new Intent(GameWin.this, GameMain.class);
				mIntent.putExtra(GameMenu.INTENT_NAME, extras);
				startActivity(mIntent);
				finish();
			}
		});
		
		btnQuit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mIntent = new Intent(GameWin.this, GameMenu.class);
				mIntent.putExtra(GameMenu.INTENT_NAME, extras);
				startActivity(mIntent);
				finish();
			}
		});
		
		btnNext.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				extras.setMap(nextCarac);
				
				mIntent = new Intent(GameWin.this, GameMain.class);
				mIntent.putExtra(GameMenu.INTENT_NAME, extras);
				startActivity(mIntent);
				finish();
			}
		});
	}

}
