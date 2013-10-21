package com.higley.innovativealarmclock;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Home extends Activity {
	
	Button button_simplePuzzle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_home);
		
		// Set up event listeners
		button_simplePuzzle = (Button)findViewById(R.id.home_buttonSimplePuzzle);
		button_simplePuzzle.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v) {
				// Launch activity
				Intent simplePuzzleIntent = new Intent(Home.this, SimplePuzzle.class);
				startActivity(simplePuzzleIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
