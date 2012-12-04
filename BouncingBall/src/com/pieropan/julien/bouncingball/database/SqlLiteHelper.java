package com.pieropan.julien.bouncingball.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlLiteHelper extends SQLiteOpenHelper {
	
	private static SqlLiteHelper instance = null;
	
	private static final String DB_NAME = "BoucingBallApplicationDB.db";

	public static final String COL_ID = "ID";
	public static final String COL_WORLD = "WORLD";
	public static final String COL_MAP = "MAP";
	public static final String COL_TIMER = "TIMER";
	
	public static final String TAB_MAPS = "MAPS";
	
	private static final String DATABASE_CREATE_MAPS = "create table "
		      + TAB_MAPS + "("  + COL_ID + " integer primary key autoincrement, "
		      					+ COL_WORLD + " text not null, "
		      					+ COL_MAP + " text not null, "
		      					+ COL_TIMER + " integer )";
	
	
	private SqlLiteHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}
	
	public static SqlLiteHelper getInstance(Context context, String name, CursorFactory factory, int version)
	{
		if (instance == null)
		{
			instance = new SqlLiteHelper(context);
		}
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TAB_MAPS);
		db.execSQL(DATABASE_CREATE_MAPS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);
	}
	
}
