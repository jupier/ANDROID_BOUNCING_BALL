package com.pieropan.julien.bouncingball.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class HighScoreDAO {

	private SQLiteDatabase database;
	private SqlLiteHelper dbHelper;
	private String[] allColumns = { "ID", "WORLD", "MAP", "TIMER" };
	
	public HighScoreDAO(Context context)
	{
		dbHelper = SqlLiteHelper.getInstance(context, null, null, 1);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}

	public boolean exist(String world, String map)
	{
		Cursor cursor = database.query(SqlLiteHelper.TAB_MAPS, allColumns
        , "MAP LIKE ? AND WORLD LIKE ? ", new String[]{map, world}, null, null, null);
		
		if (cursor != null && cursor.getCount() == 0)
			return false;
		return true;
	}
	
	public void createHighScore(String world, String map, Integer timer) {
	    ContentValues values = new ContentValues();
	    values.put(SqlLiteHelper.COL_WORLD, world);
	    values.put(SqlLiteHelper.COL_MAP, map);
	    values.put(SqlLiteHelper.COL_TIMER, timer);
	    database.insert(SqlLiteHelper.TAB_MAPS, null, values);
	  }
	
	public void updateHighScore(String world, String map, Integer timer)
	{
		ContentValues values = new ContentValues();
		values.put(SqlLiteHelper.COL_TIMER, timer);
		database.update(SqlLiteHelper.TAB_MAPS, values, 
		" WORLD LIKE ? AND MAP LIKE ? ", new String[] {world, map});
	}
	
	public List<HighScore> getHighScores(String world)
	{
	    List<HighScore> shops = new ArrayList<HighScore>();

	    Cursor cursor = database.query(SqlLiteHelper.TAB_MAPS,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      HighScore comment = cursorToHighScore(cursor);
	      shops.add(comment);
	      cursor.moveToNext();
	    }

	    cursor.close();
	    return shops;
	}
	
	private HighScore cursorToHighScore(Cursor cursor)
	{
		HighScore fav = new HighScore();
		
		fav.setMap(cursor.getString(2));
		fav.setTimer(cursor.getInt(3));
		fav.setWorld(cursor.getString(1));
		return fav;
	}
	
}
