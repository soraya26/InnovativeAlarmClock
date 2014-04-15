package com.iac.innovativealarmclock;

import java.util.ArrayList;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;
import android.util.Log;

public class AlarmController {

	private SQLiteDatabase db;
	private DBManager dbManager;
	
	private static Alarm activeAlarm = null;
	private static AlarmManager alarmManager;
	
	// Constructors
	
	public AlarmController(Context context)
	{
		dbManager = new DBManager(context);
	}
	
	// Getters and setters
	
	public boolean alarmIsSet()
	{
		return (activeAlarm != null);
	}
	
	public Alarm getActiveAlarm()
	{
		return activeAlarm;
	}
	
	// Methods
	
	public void open()
	{
		db = dbManager.getWritableDatabase();
	}
	
	public void close()
	{
		dbManager.close();
	}
	
	public Alarm insertAlarm(Alarm alarm)
	{
		ContentValues values = new ContentValues();
		values.put(DBManager.TB_ALARMS_COL_NAME, alarm.getName());
		values.put(DBManager.TB_ALARMS_COL_NEXT, alarm.getNextOccurance());
		values.put(DBManager.TB_ALARMS_COL_ISSET, alarm.getIsSet());
		values.put(DBManager.TB_ALARMS_COL_REPEATS, alarm.getRepeats());
		values.put(DBManager.TB_ALARMS_COL_RINGTONE, alarm.getRingtone());
		values.put(DBManager.TB_ALARMS_COL_VIBRATE, alarm.getVibrate());
		
		long ID = db.insert(DBManager.TB_ALARMS_NAME, null, values);
		alarm.setID(ID);
		
		return alarm;
	}
	
	public void updateAlarm(Alarm alarm)
	{
		ContentValues values = new ContentValues();
		values.put(DBManager.TB_ALARMS_COL_NAME, alarm.getName());
		values.put(DBManager.TB_ALARMS_COL_NEXT, alarm.getNextOccurance());
		values.put(DBManager.TB_ALARMS_COL_ISSET, alarm.getIsSet());
		values.put(DBManager.TB_ALARMS_COL_REPEATS, alarm.getRepeats());
		values.put(DBManager.TB_ALARMS_COL_RINGTONE, alarm.getRingtone());
		values.put(DBManager.TB_ALARMS_COL_VIBRATE, alarm.getVibrate());
		
		db.update(DBManager.TB_ALARMS_NAME, values, DBManager.TB_ALARMS_COL_ID + "=?", new String[] { Long.toString(alarm.getID()) });
	}
	
	public void deleteAlarm(Alarm alarm)
	{
		db.delete(DBManager.TB_ALARMS_NAME, DBManager.TB_ALARMS_COL_ID + "=?", new String[] { Long.toString(alarm.getID()) });
	}
	
	public List<Alarm> getAllAlarms()
	{
		List<Alarm> allAlarms = new ArrayList<Alarm>();
		
		Cursor cursor = db.query(DBManager.TB_ALARMS_NAME, DBManager.TB_ALARMS_ALLCOLS,
				null, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			Alarm alarm = cursorToAlarm(cursor);
			
			// Fix alarm
			alarm.setToNextTimeOccurance();
			updateAlarm(alarm);
			
			allAlarms.add(alarm);
			cursor.moveToNext();
		}
		cursor.close();
		
		return allAlarms;
	}
	
	public static void setNextAlarm(List<Alarm> alarms, Context context)
	{
		Alarm min = new Alarm();
		min.setNextOccurance(Long.MAX_VALUE);
		for (Alarm alarm : alarms)
		{
			if (alarm.getIsSet())
			{
				if (alarm.getNextOccurance() < min.getNextOccurance())
				{
					min = alarm;
				}
			}
		}
		if (min.getNextOccurance() == Long.MAX_VALUE)
		{
			// No alarm
			activeAlarm = null;
		}
		else if (min != activeAlarm)
		{
			activeAlarm = min;
			alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
			Intent alarmIntent = new Intent(context, AlarmReceiver.class);
			PendingIntent pendingAlarmIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
			alarmManager.set(AlarmManager.RTC_WAKEUP, activeAlarm.getNextOccurance(), pendingAlarmIntent);
			Time t = new Time();
			t.set(activeAlarm.getNextOccurance());
			Log.v("IAC", "set for " + t.toString());
		}
	}
	
	private Alarm cursorToAlarm(Cursor cursor)
	{
		Alarm alarm = new Alarm();
		
		alarm.setID(cursor.getLong(0));
		alarm.setName(cursor.getString(1));
		alarm.setNextOccurance(cursor.getLong(2));
		alarm.set(cursor.getInt(3) == 1);
		//TODO: repetition information
		
		return alarm;
	}
	
}
