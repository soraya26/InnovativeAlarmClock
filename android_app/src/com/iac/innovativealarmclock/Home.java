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
