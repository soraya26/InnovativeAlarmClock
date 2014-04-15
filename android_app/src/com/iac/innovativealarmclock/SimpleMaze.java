package com.iac.innovativealarmclock;

import java.util.ArrayList;

import com.iac.innovativealarmclock.R;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Point;
import android.text.Editable;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.util.Log;

public class SimpleMaze extends Activity implements OnClickListener {
	
	private int mazeHeight; // height of maze in "blocks" (explained later)
	private int mazeWidth; // width of maze in "blocks" (explained later)
	private int mazeBlockEdgeSize; // size of one edge of the maze "block" (explained later)
	
	/**************************
	To generate the maze, the screen is divided up into "blocks", squares of pixels of size relative to the size
	of the screen. During maze generation, each square is given one of four values: start, path, wall or end. The
	start and end blocks are self-explanatory, and should be on opposite sides of the phone from each other. Path
	blocks are blocks that are able to be traversed by the user. Wall blocks are sides of the maze.
	**************************/
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		generateMazeSize();
		
	}
	private void generateMazeSize()
	{
		Display display = getWindowManager().getDefaultDisplay();
		int screenHeight = display.getHeight();
		int screenWidth = display.getWidth();
		if ((float)screenHeight / (float)screenWidth > 1.7) // aspect ratio is 16:9
		{
			mazeHeight = 16;
			mazeWidth = 9;
			mazeBlockEdgeSize = screenHeight/16;
		}
		else if ((float)screenHeight / (float)screenWidth == 1.6) // aspect ratio is 16:10
		{
			mazeHeight = 16;
			mazeWidth = 10;
			mazeBlockEdgeSize = screenHeight/16;
		}
		else if ((float)screenHeight / (float)screenWidth < 1.4) // aspect ratio is 4:3
		{
			mazeHeight = 16;
			mazeWidth = 12;
			mazeBlockEdgeSize = screenHeight/16;
		}
	}
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	
}