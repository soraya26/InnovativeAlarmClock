package com.iac.innovativealarmclock;

import java.util.ArrayList;

import com.iac.innovativealarmclock.R;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
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

public class SimplePuzzle extends Activity implements OnClickListener{
	/*
	private Button buttonC1;
	private Button buttonC2;
	private Button buttonC3;
	private Button buttonC4; */ 
	private TextView questionText;
	private TextView counterText;
	private EditText answerBox;
	private int correctAnswer;
	//private int correctIndex;
	private int requiredProblems = 3;
	private int solved = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// If 4-multiple choice
		setContentView(R.layout.view_puzzle_4choice); /*
		buttonC1 = (Button) findViewById(R.id.fourmc_button_one);
		buttonC2 = (Button) findViewById(R.id.fourmc_button_two);
		buttonC3 = (Button) findViewById(R.id.fourmc_button_three);
		buttonC4 = (Button) findViewById(R.id.fourmc_button_four); */
		questionText = (TextView)findViewById(R.id.fourmc_textview_question);
		counterText = (TextView)findViewById(R.id.fourmc_textview_counter);
		answerBox = (EditText)findViewById(R.id.answer_box); /*
		buttonC1.setOnClickListener(this);
		buttonC2.setOnClickListener(this);
		buttonC3.setOnClickListener(this);
		buttonC4.setOnClickListener(this); */
		loadArithmeticMultipleChoice();
		answerBox.setOnKeyListener(new OnKeyListener() {
			
			public boolean onKey(View v, int keyCode, KeyEvent event)
			{
				Log.v("click", "clique clique clique");
				Log.v("Data", "keyCode = "+keyCode+", KeyEvent = "+event.getKeyCode());
				/*if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.FLAG_EDITOR_ACTION)
				{
					int x = Integer.parseInt(answerBox.getText().toString());
					Log.v("Answer", "Guess acknowledged as "+x);
					processResponse(x);
				}
				switch (keyCode)
				{
					case KeyEvent.FLAG_EDITOR_ACTION:
						int x = Integer.parseInt(answerBox.getText().toString());
						Log.v("Answer", "Guess acknowledged as "+x);
						processResponse(x);
						
					return true;
				}*/
				if (keyCode == 66)
				{
					int x = Integer.parseInt(answerBox.getText().toString());
					Log.v("Answer", "Guess acknowledged as "+x+", Answer acknowledged as "+correctAnswer);
					processResponse(x);
				}
				return false;
			}
			
		});
		updateCounter();
	}

	private void loadArithmeticMultipleChoice()
	{
		QuestionMC question = MathGenerator.generateArithmeticQuestion(4);
		
		questionText.setText(question.getQuestion()); /*
		ArrayList<String> options = question.getOptions();
		buttonC1.setText(options.get(0));
		buttonC2.setText(options.get(1));
		buttonC3.setText(options.get(2));
		buttonC4.setText(options.get(3));
		correctIndex = question.getCorrectIndex(); */
		correctAnswer = question.getCorrectAnswer();
		Log.v("Set", "Set private int correctAnswer to "+correctAnswer);
		
	}

	private void processResponse(int guess)
	{
		if (correctAnswer == guess)
		{
			Toast.makeText(this, "Correct", 1000).show();
			solved++;
		}
		else
		{
			Toast.makeText(this, "Incorrect", 1000).show();
		}
		updateCounter();
		if (solved == requiredProblems)
		{
			Toast.makeText(this, "Alarm disabled", 1000).show();
			finish();
		}
		else
		{
			loadArithmeticMultipleChoice();
		}
	}
	private void updateCounter()
	{
		String text = "(";
		text += solved;
		text += '/';
		text += requiredProblems;
		text += ')';

		counterText.setText(text);
	}

	public void onClick(View v)
	{
		/*if (v.getId() == buttonC1.getId())
		{
			processResponse(0);
		}
		if (v.getId() == buttonC2.getId())
		{
			processResponse(1);
		}
		if (v.getId() == buttonC3.getId())
		{
			processResponse(2);
		}
		if (v.getId() == buttonC4.getId())
		{
			processResponse(3);
		}*/
	}
	
}
