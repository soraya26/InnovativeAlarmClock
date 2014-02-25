package com.iac.innovativealarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

	public void onReceive(Context context, Intent intent) {
		//TODO: Prepare for NFC recognition
		
		//----- Dummy alarm code -----
		Toast.makeText(context, "Ring ring ring.", Toast.LENGTH_LONG).show();
		
		// Load simple puzzle
		Intent puzzleIntent = new Intent();
		puzzleIntent.setClassName("com.iac.innovativealarmclock", "com.iac.innovativealarmclock.SimplePuzzle");
		puzzleIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(puzzleIntent);
		//----------------------------
	}

}
