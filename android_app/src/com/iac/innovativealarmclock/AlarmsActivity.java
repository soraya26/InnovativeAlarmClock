package com.iac.innovativealarmclock;

import java.util.HashMap;
import java.util.List;

import com.iac.innovativealarmclock.R;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AlarmsActivity extends Activity {

	private static final int NEWALARM_REQUEST_CODE = 1855;

	private AlarmController alarmManager = null;
	private AlarmArrayAdapter adapter;
	private boolean inEdit;
	private Alarm editingAlarm;
	ListView alarmList;
	List<Alarm> alarms;

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

	@SuppressLint("NewApi") //FIXME
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarms, menu);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		return true;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		// If this is a alarm settings activity being closed
		if (requestCode == NEWALARM_REQUEST_CODE)
		{
			updateAlarm();
		}
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId())
		{
		case R.id.action_add_alarm:
			inEdit = false;
			resetPreferences();
			
			Intent newAlarmIntent = new Intent(AlarmsActivity.this, NewAlarmActivity.class);
			AlarmsActivity.this.startActivityForResult(newAlarmIntent, NEWALARM_REQUEST_CODE);
			return true;
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
			
		case R.id.action_stats:
			Intent statsIntent = new Intent(AlarmsActivity.this, Stats.class);
			AlarmsActivity.this.startActivity(statsIntent);
			AlarmsActivity.this.overridePendingTransition(0, 0);
			return true;
			
		case R.id.action_settings:
			Intent settingsIntent = new Intent(AlarmsActivity.this, Settings.class);
			AlarmsActivity.this.startActivity(settingsIntent);
			AlarmsActivity.this.overridePendingTransition(0, 0);
		}
		
		return false;
	}

	private void updateAlarm()
	{
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

		boolean alarm_status = prefs.getBoolean("preference_status", true);
		String alarm_name = prefs.getString("preference_name", "");
		String alarm_time_str = prefs.getString("preference_time", "0:00");
		
		// Adding a new alarm
		if (!inEdit)
		{
			Alarm alarm = new Alarm();
			alarm.setName(alarm_name);
			alarm.set(alarm_status);
			alarm.setNextOccurance(alarm_time_str);
			alarm = alarmManager.insertAlarm(alarm);
		}
		// Modifying an existing alarm
		else
		{
			editingAlarm.setName(alarm_name);
			editingAlarm.set(alarm_status);
			editingAlarm.setNextOccurance(alarm_time_str);
			alarmManager.updateAlarm(editingAlarm);
		}
		
		loadAlarmList();
	}

	private void loadAlarmList()
	{
		alarms = alarmManager.getAllAlarms();
		adapter = new AlarmArrayAdapter(this, android.R.layout.simple_list_item_1, alarms);
		AlarmController.setNextAlarm(alarms, this);
		alarmList.setAdapter(adapter);


		alarmList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapterView, View view, int position,
					long id) {
				Alarm alarm = adapter.getItem(position);
				setPreferencesByAlarm(alarm);

				inEdit = true;
				editingAlarm = alarm;
				Intent newAlarmIntent = new Intent(AlarmsActivity.this, NewAlarmActivity.class);
				AlarmsActivity.this.startActivityForResult(newAlarmIntent, NEWALARM_REQUEST_CODE);
			}

		});

		alarmList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				editingAlarm = adapter.getItem(position);
				raiseAlarmDialog();
				
				return false;
			}
		});
	}
	
	private void setPreferencesByAlarm(Alarm alarm)
	{
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean("preference_status", alarm.getIsSet());
		editor.putString("preference_name", alarm.getName());
		editor.putString("preference_time", alarm.getTimeString());
		editor.apply();
	}
	
	private void resetPreferences()
	{
		Alarm alarm = new Alarm();
		alarm.setDefaultValues();
		setPreferencesByAlarm(alarm);
	}
	
	private void raiseAlarmDialog()
	{	
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle(getString(R.string.str_delete_confirm_title));
		dialogBuilder.setMessage(getString(R.string.str_delete_confirm_message));
		dialogBuilder.setCancelable(true);
		dialogBuilder.setPositiveButton(getString(R.string.str_delete_confirm_delete), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				deleteSelectedAlarm();
			}
		});
		dialogBuilder.setNegativeButton(getString(R.string.str_delete_confirm_cancel), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// Do nothing
			}
		});
		Dialog dialog = dialogBuilder.create();
		dialog.show();
	}
	
	private void deleteSelectedAlarm()
	{
		alarmManager.deleteAlarm(editingAlarm);
		loadAlarmList();
	}

	private class AlarmArrayAdapter extends ArrayAdapter<Alarm> {

		HashMap<Alarm, Integer> mIdMap = new HashMap<Alarm, Integer>();

		public AlarmArrayAdapter(Context context, int textViewResourceId, List<Alarm> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); i++) {
				mIdMap.put(objects.get(i), i);
			}
		}

		public long getItemId(int position) {
			Alarm item = getItem(position);
			return mIdMap.get(item);
		}

		public boolean hasStableIds() {
			return true;
		}

	}

}
