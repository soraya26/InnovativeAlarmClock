package com.iac.innovativealarmclock;

import java.util.HashMap;
import java.util.List;

import com.higley.innovativealarmclock.R;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AlarmsActivity extends Activity {

	private static final int NEWALARM_REQUEST_CODE = 1855;

	private AlarmController alarmManager = null;
	private StableArrayAdapter adapter;
	private boolean inEdit;
	private Alarm editingAlarm;
	ListView alarmList;
	List<Alarm> alarms;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_allalarms);

		alarmList = (ListView) findViewById(R.id.allalarms_listview_all);

		// Load alarms
		if (alarmManager == null)
		{
			alarmManager = new AlarmController(this);
			alarmManager.open();
		}
		loadAlarmList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarms, menu);
		return true;
	}

	public void handleMenuClick(MenuItem item)
	{
		//TODO: clean this up
		String itemTitle = (String)item.getTitle();
		if (itemTitle.equals("Home"))
		{
			Intent alarmsIntent = new Intent(AlarmsActivity.this, Home.class);
			AlarmsActivity.this.startActivity(alarmsIntent);
			AlarmsActivity.this.overridePendingTransition(0, 0);
		}
		else if (itemTitle.equals("Add Alarm"))
		{
			inEdit = false;
			Intent newAlarmIntent = new Intent(AlarmsActivity.this, NewAlarmActivity.class);
			AlarmsActivity.this.startActivityForResult(newAlarmIntent, NEWALARM_REQUEST_CODE);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == NEWALARM_REQUEST_CODE)
		{
			updateAlarm();
		}
	}

	private void updateAlarm()
	{
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

		boolean alarm_status = prefs.getBoolean("preference_status", true);
		String alarm_name = prefs.getString("preference_name", "");
		String alarm_time_str = prefs.getString("preference_time", "0:00");
		if (!inEdit)
		{
			Alarm alarm = new Alarm();
			alarm.setName(alarm_name);
			alarm.set(alarm_status);
			alarm.setNextOccurance(alarm_time_str);
			alarm = alarmManager.insertAlarm(alarm);
		}
		else
		{
			editingAlarm.setName(alarm_name);
			editingAlarm.set(alarm_status);
			editingAlarm.setNextOccurance(alarm_time_str);
			alarmManager.updateAlarm(editingAlarm);
		}
		loadAlarmList();
	}

	private class StableArrayAdapter extends ArrayAdapter<Alarm> {

		HashMap<Alarm, Integer> mIdMap = new HashMap<Alarm, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
				List<Alarm> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			Alarm item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}

	private void loadAlarmList()
	{
		alarms = alarmManager.getAllAlarms();
		adapter = new StableArrayAdapter(this,
				android.R.layout.simple_list_item_1, alarms);
		AlarmController.setNextAlarm(alarms, this);
		alarmList.setAdapter(adapter);


		alarmList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.v("IAC", arg2 + " " + arg3);
				Alarm alarm = adapter.getItem(arg2);
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
				SharedPreferences.Editor editor = prefs.edit();
				editor.putBoolean("preference_status", alarm.getIsSet());
				editor.putString("preference_name", alarm.getName());
				editor.putString("preference_time", alarm.getTimeString());
				editor.apply();

				inEdit = true;
				editingAlarm = alarm;
				Intent newAlarmIntent = new Intent(AlarmsActivity.this, NewAlarmActivity.class);
				AlarmsActivity.this.startActivityForResult(newAlarmIntent, NEWALARM_REQUEST_CODE);
			}

		});
		
		alarmList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				//TODO: dialog
				Alarm alarm = adapter.getItem(arg2);
				alarmManager.deleteAlarm(alarm);
				loadAlarmList();
				
				return false;
			}
		});
	}

}
