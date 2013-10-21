package com.higley.innovativealarmclock;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SimplePuzzle extends Activity implements OnClickListener{
	
	private Button buttonC1;
	private Button buttonC2;
	private Button buttonC3;
	private Button buttonC4;
	private TextView questionText;
	
	private int correctIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// If 4-multiple choice
		setContentView(R.layout.view_puzzle_4choice);
		buttonC1 = (Button) findViewById(R.id.puzzle_buttonChoice1);
		buttonC2 = (Button) findViewById(R.id.puzzle_buttonChoice2);
		buttonC3 = (Button) findViewById(R.id.puzzle_buttonChoice3);
		buttonC4 = (Button) findViewById(R.id.puzzle_buttonChoice4);
		questionText = (TextView)findViewById(R.id.textViewQuestion);
		buttonC1.setOnClickListener(this);
		buttonC2.setOnClickListener(this);
		buttonC3.setOnClickListener(this);
		buttonC4.setOnClickListener(this);
		loadArithmeticMultipleChoice();
	}
	
	private void loadArithmeticMultipleChoice()
	{
		QuestionMC question = MathGenerator.generateArithmeticQuestion(4);
		
		questionText.setText(question.getQuestion());
		ArrayList<String> options = question.getOptions();
		buttonC1.setText(options.get(0));
		buttonC2.setText(options.get(1));
		buttonC3.setText(options.get(2));
		buttonC4.setText(options.get(3));
		correctIndex = question.getCorrectIndex();
	}
	
	private void processResponse(int index)
	{
		if (correctIndex == index)
		{
			questionText.setText("CORRECT");
		}
		else
		{
			questionText.setText("INCORRECT");
		}
	}

	public void onClick(View v)
	{
		if (v.getId() == buttonC1.getId())
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
		}
	}
}
