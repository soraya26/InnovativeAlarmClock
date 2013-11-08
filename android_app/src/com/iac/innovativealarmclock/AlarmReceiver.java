package com.iac.innovativealarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "Ring ring ring.", Toast.LENGTH_LONG).show();
		
		// Load simple pluzzle
		Intent puzzleIntent = new Intent();
		puzzleIntent.setClassName("com.higley.innovativealarmclock", "com.higley.innovativealarmclock.SimplePuzzle");
		puzzleIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(puzzleIntent);
	}

}
