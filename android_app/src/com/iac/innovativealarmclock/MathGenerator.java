package com.iac.innovativealarmclock;

import java.util.Random;
import android.util.Log;

public class MathGenerator {
	
	private static final int OPERATOR_ADDITION = 0;
	private static final int OPERATOR_SUBTRACTION = 1;
	private static final int OPERATOR_MULTIPLICATION = 2;
	
	private static final int STDDEVIATION_FACTOR = 5;
	static int answer = 0;
	
	public static QuestionMC generateArithmeticQuestion(int optionCount)
	{
		QuestionMC question = new QuestionMC();
		
		// Generate question
		Random random = new Random();
		int operator = random.nextInt(3);
		int num1, num2;
		String questionText = new String();
		switch (operator)
		{
		case OPERATOR_ADDITION:
			num1 = random.nextInt(99) + 1;
			num2 = random.nextInt(99) + 1;
			answer = num1 + num2;
			Log.v("Debug", num1+" and "+num2+" were used to create the answer "+answer);
			question.setCorrectAnswer(answer);
			questionText = num1 + " + " + num2  + " =";
			break;
		case OPERATOR_SUBTRACTION:
			num1 = random.nextInt(99) + 1;
			num2 = random.nextInt(num1 - 1) + 1;
			answer = num1 - num2;
			question.setCorrectAnswer(answer);
			Log.v("Debug", num1+" and "+num2+" were used to create the answer "+answer);
			questionText = num1 + " - " + num2 + " =";
			break;
		case OPERATOR_MULTIPLICATION:
			num1 = random.nextInt(12) + 1;
			num2 = random.nextInt(12) + 1;
			answer = num1 * num2;
			question.setCorrectAnswer(answer);
			Log.v("Debug", num1+" and "+num2+" were used to create the answer "+answer);
			questionText = num1 + " x " + num2 + " =";
			break;
		default:
			// Won't happen
			answer = 0;
		}
		question.setQuestion(questionText);
		
		// Generate options
		int answerPosition = random.nextInt(optionCount);
		question.setCorrectIndex(answerPosition);
		for (int i = 0; i < optionCount; i++)
		{
			if (i == answerPosition)
			{
				question.addOption(Integer.toString(answer));
			}
			else
			{
				question.addOption(Integer.toString(MathGenerator.generateWrongAnswer(answer, random)));
			}
		}
		
		return question;
	}
	
	private static int generateWrongAnswer(int answer, Random random)
	{
		int integerOff;
		while (true) {
			double off = random.nextGaussian();
			integerOff = (int) (off * answer / STDDEVIATION_FACTOR);
			if (integerOff != 0)
			{
				break;
			}
		}
		
		return (integerOff + answer);
	}
	public int getAnswer() {
		Log.v("Returning", "Returning answer, which is "+answer);
		return answer;
		
	}

}
