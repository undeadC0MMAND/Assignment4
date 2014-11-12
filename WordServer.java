import java.util.*;
import java.io.*;


public class WordServer
{
	private String[] words;
	private int wordsAvailable;
	
	public void loadWords(Scanner S) throws NullPointerException
	{
		int arraySize = Integer.parseInt(S.nextLine());
		wordsAvailable = arraySize;
		words = new String[arraySize];
		
		String word;
		
		for(int i = 0; i < (words.length); i++)
		{
			word = S.nextLine();
			words[i] = word;
		}
	
	}
	public String getNextWord()
	{
		Random randomNum = new Random();
		String returnWord = null;
		if(wordsAvailable>0)
		{
			int randomValue = randomNum.nextInt(wordsAvailable);
			returnWord = words[randomValue];
			
			System.arraycopy(words, randomValue + 1, words, randomValue, words.length - 1 - randomValue);
			words[words.length - 1] = returnWord;
			
			wordsAvailable--;
		}
		else
		{
			returnWord = "";
		}
		
		return returnWord;
	}
}
