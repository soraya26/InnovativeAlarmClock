package com.iac.innovativealarmclock;

import com.iac.innovativealarmclock.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.util.Log;

public class Home extends Activity {
	
	TextView text_intro, text_time, text_name;
	
	private AlarmController alarmManager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_home);
		
		if (alarmManager == null)
		{
			alarmManager = new AlarmController(this);
			alarmManager.open();
			AlarmController.setNextAlarm(alarmManager.getAllAlarms(), this);
		}
		
		text_intro = (TextView) findViewById(R.id.home_textview_alarm_intro);
		text_time = (TextView) findViewById(R.id.home_textview_alarm_time);
		text_name = (TextView) findViewById(R.id.home_textview_alarm_name);
		
		updateText();
	}

	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.home, menu);
		
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.action_alarms:
			Intent alarmsIntent = new Intent(Home.this, AlarmsActivity.class);
			Home.this.startActivity(alarmsIntent);
			Home.this.overridePendingTransition(0, 0);
			return true;
		
		case R.id.action_stats:
			Intent statsIntent = new Intent(Home.this, Stats.class);
			Home.this.startActivity(statsIntent);
			Home.this.overridePendingTransition(0, 0);
			return true;
			
		case R.id.action_settings:
			Intent settingsIntent = new Intent(Home.this, Settings.class);
			Home.this.startActivity(settingsIntent);
			Home.this.overridePendingTransition(0, 0);
			return true;
			
		case R.id.action_test_puzzle: //s testing the test puzzle function, borrowed code from AlarmReciever.java
			Log.v("test message hit", "hope this works");
			Intent puzzleIntent = new Intent(this, SimplePuzzle.class);
			//puzzleIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Home.this.startActivity(puzzleIntent);;
			Home.this.overridePendingTransition(0, 0);
			return true;
		}
		
		return false;
	}
	
	private void updateText()
	{
		if (alarmManager.alarmIsSet())
		{
			Alarm activeAlarm = alarmManager.getActiveAlarm();
			text_time.setVisibility(TextView.VISIBLE);
			text_name.setVisibility(TextView.VISIBLE);
			text_intro.setText("Next alarm set for");
			text_time.setText(activeAlarm.getTimeString());
			text_name.setText(activeAlarm.getName());
		}
		else
		{
			text_time.setVisibility(TextView.INVISIBLE);
			text_name.setVisibility(TextView.INVISIBLE);
			text_intro.setText("No alarm is currently set");
		}
	}
	
	OnClickListener setoffListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(Home.this, SimplePuzzle.class);
			Home.this.startActivity(intent);
		}
	};

}
