package com.iac.innovativealarmclock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBManager extends SQLiteOpenHelper {
	
	private static final String TAG = "DBUpgrade";
	
	public static final String DB_ALARMS_NAME = "alarms.db";
	
	public static final String TB_ALARMS_NAME = "alarms";
	public static final String TB_ALARMS_COL_ID = "id";
	public static final String TB_ALARMS_COL_NAME = "name";
	public static final String TB_ALARMS_COL_NEXT = "next";
	public static final String TB_ALARMS_COL_ISSET = "isset";
	public static final String TB_ALARMS_COL_REPEATS = "repeats";
	public static final String TB_ALARMS_COL_RINGTONE = "ringtone";
	public static final String TB_ALARMS_COL_VIBRATE = "vibrate";
	public static String[] TB_ALARMS_ALLCOLS = {TB_ALARMS_COL_ID, TB_ALARMS_COL_NAME,
		TB_ALARMS_COL_NEXT, TB_ALARMS_COL_ISSET, TB_ALARMS_COL_REPEATS, TB_ALARMS_COL_RINGTONE, TB_ALARMS_COL_VIBRATE};
	
	
	private static final int DB_ALARMS_VERSION = 4;
	
	private static final String DB_ALARMS_CREATE =
			"CREATE TABLE " + TB_ALARMS_NAME + "("
			+ TB_ALARMS_COL_ID + " integer primary key autoincrement, "
			+ TB_ALARMS_COL_NAME + " text, "
			+ TB_ALARMS_COL_NEXT + " integer, "
			+ TB_ALARMS_COL_ISSET + " integer, "
			+ TB_ALARMS_COL_REPEATS + " integer, "
			+ TB_ALARMS_COL_RINGTONE + " integer, "
			+ TB_ALARMS_COL_VIBRATE + " boolean "
			+ ");";

	public DBManager(Context context)
	{
		super(context, DB_ALARMS_NAME, null, DB_ALARMS_VERSION);
	}

	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(DB_ALARMS_CREATE);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		 Log.d(TAG, "Upgrading database from version " + oldVersion 
                 + " to "
                 + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS alarms");
		onCreate(db);
	}

}
