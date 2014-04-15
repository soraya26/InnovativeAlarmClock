package com.iac.innovativealarmclock;

import com.iac.innovativealarmclock.R;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Stats extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_stats);
	
		
	}
	
	@SuppressLint("NewApi") //FIXME
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.stats, menu);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
			
		case R.id.action_alarms:
			Intent alarmsIntent = new Intent(Stats.this, AlarmsActivity.class);
			Stats.this.startActivity(alarmsIntent);
			Stats.this.overridePendingTransition(0, 0);
			return true;

		case R.id.action_settings:
			Intent settingsIntent = new Intent(Stats.this, Settings.class);
			Stats.this.startActivity(settingsIntent);
			Stats.this.overridePendingTransition(0, 0);
			return true;
		}
		
		return false;
	}
}