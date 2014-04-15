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

public class Settings extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_settings);
	
		
	}
	
	@SuppressLint("NewApi") //FIX
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.settings, menu);
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
			Intent alarmsIntent = new Intent(Settings.this, AlarmsActivity.class);
			Settings.this.startActivity(alarmsIntent);
			Settings.this.overridePendingTransition(0, 0);
			return true;
		
		case R.id.action_stats:
			Intent statsIntent = new Intent(Settings.this, Stats.class);
			Settings.this.startActivity(statsIntent);
			Settings.this.overridePendingTransition(0, 0);
			return true;
		}
	return false;
	}
}

