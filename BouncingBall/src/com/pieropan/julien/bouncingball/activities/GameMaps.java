package com.pieropan.julien.bouncingball.activities;

import java.util.List;

import com.pieropan.julien.bouncingball.blocks.Player;
import com.pieropan.julien.bouncingball.helpers.MapSelector;
import com.pieropan.julien.bouncingball.helpers.MyIntent;

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

public class GameMaps extends Activity {

	// FIELDS

	private Typeface font = null;
	
	private MyIntent extras = null;
	
	private ListView mapsLV = null;
	private Button btnReturn = null;
	
	private List<String> mapList = null;
	
	private Intent mIntent = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_maps);
		
		loadIntents();
		
		font = Typeface.createFromAsset(getAssets(), "font/Churli_cute5.ttf");
		
		btnReturn = (Button) findViewById(R.id.returnMapsBTN);
		mapsLV = (ListView) findViewById(R.id.mapsLV);
		
		this.btnReturn.setTypeface(font);
		
		mapList = MapSelector.getInstance().getMaps(extras.getWorld());
		
		mapsLV.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mapList));
		
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
