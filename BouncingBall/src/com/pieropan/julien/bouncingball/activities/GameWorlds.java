package com.pieropan.julien.bouncingball.activities;

import java.util.List;

import com.pieropan.julien.bouncingball.helpers.MapSelector;
import com.pieropan.julien.bouncingball.helpers.MyIntent;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class GameWorlds extends Activity {

	// FIELDS
	
	private Typeface font = null;
	
	private ListView worldLV = null;
	private Button returnBTN = null;
	private TextView worldSelTV = null;
	
	private MyIntent extras = null;
	
	private List<String> worldList = null;
	
	private Intent mIntent = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_worlds);
		
		loadIntents();
		
		font = Typeface.createFromAsset(getAssets(), "font/Churli_cute5.ttf");
		
		worldLV = (ListView) findViewById(R.id.worldLIST);
		returnBTN = (Button) findViewById(R.id.returnWorldBTN);
		worldSelTV = (TextView) findViewById(R.id.textView2);
		
		returnBTN.setTypeface(font);
		worldSelTV.setTypeface(font);
		
		worldList = MapSelector.getInstance().getWorlds();
		
		worldLV.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, worldList));
		
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
		returnBTN.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mIntent = new Intent(GameWorlds.this, GameMenu.class);
				mIntent.putExtra(GameMenu.INTENT_NAME, extras);
				startActivity(mIntent);
				finish();
			}
		});
		
		worldLV.setOnItemClickListener(new OnItemClickListener() {
			  @Override
			  public void onItemClick(AdapterView<?> parent, View view,
			    int position, long id) {
			    
				  extras.setWorld(worldList.get(position));
				  
				  mIntent = new Intent(GameWorlds.this, GameMaps.class);
				  mIntent.putExtra(GameMenu.INTENT_NAME, extras);
				  startActivity(mIntent);
				  finish();
				  
			  }
		});
	}

}
