package com.pieropan.julien.bouncingball.activities;

import java.util.ArrayList;
import java.util.List;

import com.pieropan.julien.bouncingball.blocks.Player;
import com.pieropan.julien.bouncingball.database.HighScore;
import com.pieropan.julien.bouncingball.database.HighScoreDAO;
import com.pieropan.julien.bouncingball.helpers.MapSelector;
import com.pieropan.julien.bouncingball.helpers.MyIntent;
import com.pieropan.julien.bouncingball.listview.MapRow;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class GameMaps extends Activity {

	// FIELDS

	private Typeface font = null;
	
	private MyIntent extras = null;
	
	private ListView mapsLV = null;
	private Button btnReturn = null;
	private TextView mapsSelTV = null;
	
	private List<String> mapList = null;
	
	private Intent mIntent = null;
	
	private HighScoreDAO highScoreDAO = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_maps);
		
		// Instantation de la classe dao pour la db
		this.highScoreDAO = new HighScoreDAO(this);
		this.highScoreDAO.open();
		
		loadIntents();
		
		font = Typeface.createFromAsset(getAssets(), "font/Churli_cute5.ttf");
		
		btnReturn = (Button) findViewById(R.id.returnMapsBTN);
		mapsLV = (ListView) findViewById(R.id.mapsLV);
		mapsSelTV = (TextView) findViewById(R.id.textView1);
		
		this.btnReturn.setTypeface(font);
		this.mapsSelTV.setTypeface(font);
		
		mapList = MapSelector.getInstance().getMaps(extras.getWorld());
		
		mapsLV.setAdapter(new MapRowAdaptater(this, loadListRow()));
		
		createListeners();
	}
	
	private List<MapRow> loadListRow()
	{
		Integer timer = null;
		List<MapRow> items = new ArrayList<MapRow>();
		List<HighScore> highScores = this.highScoreDAO.getHighScores(this.extras.getWorld());
		
		for (String m : mapList)
		{
			timer = null;
			for (HighScore h : highScores)
			{
				if (h.getMap().equals(m) && h.getWorld().equals(extras.getWorld()))
					timer = h.getTimer();
			}
			if (timer == null)
				items.add(new MapRow(m, null));
			else
				items.add(new MapRow(m, timer.toString()));
		}
		
		return items;
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
		btnReturn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mIntent = new Intent(GameMaps.this, GameWorlds.class);
				mIntent.putExtra(GameMenu.INTENT_NAME, extras);
				startActivity(mIntent);
				finish();
			}
		});
		
		mapsLV.setOnItemClickListener(new OnItemClickListener() {
			  @Override
			  public void onItemClick(AdapterView<?> parent, View view,
			    int position, long id) {
			    
				  extras.setMap(MapSelector.getInstance().getCarac(extras.getWorld(), mapList.get(position)));
				  extras.setLifes(Player.PLAYER_LIFES);
				  
				  mIntent = new Intent(GameMaps.this, GameMain.class);
				  mIntent.putExtra(GameMenu.INTENT_NAME, extras);
				  startActivity(mIntent);
				  finish();
				  
			  }
		});
	}
}
