package com.iac.innovativealarmclock;

import java.util.ArrayList;

public class QuestionMC {
	
	private String question;
	private ArrayList<String> options = null;
	private int correctIndex;
	private int correctAnswer;
	
	public void setQuestion(String aQuestion)
	{
		question = aQuestion;
	}
	
	public void setOptions(ArrayList<String> aOptions)
	{
		options = aOptions;
	}
	
	public void setCorrectIndex(int index)
	{
		correctIndex = index;
	}
	
	public void addOption(String aOption)
	{
		if (options == null)
		{
			options = new ArrayList<String>();
		}
		options.add(aOption);
	}
	
	public String getQuestion()
	{
		return question;
	}
	
	public ArrayList<String> getOptions()
	{
		return options;
	}
	
	public int getCorrectIndex()
	{
		return correctIndex;
	}
	public void setCorrectAnswer(int answer) {
		correctAnswer = answer;
	}
	public int getCorrectAnswer() {
		return correctAnswer;
	}

}
