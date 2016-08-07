package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.*;
import java.lang.*;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 3;
    private static final int DEFAULT_WORD_LENGTH = 5;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
	private HashSet wordSet=new HashSet();
	private List<String> wordList=new ArrayList<String>();
	private Map<String,ArrayList<String>> letterToWord=new HashMap<String,ArrayList<String>>();
	private Map<Integer,ArrayList<String>> sizeToWord=new HashMap<Integer, ArrayList<String>>();
	private ArrayList<String> temp=new ArrayList<String>();
	private String keymap;
	private int wordLength;

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
		wordLength=DEFAULT_WORD_LENGTH;
        while((line = in.readLine()) != null) {
            String word = line.trim();
			wordSet.add(word);
			wordList.add(word);
			keymap=sortWord(word);
			if(letterToWord.get(keymap)!=null)
			{
				letterToWord.get(keymap).add(word);
			}
			else
			{
				letterToWord.put(keymap,new ArrayList<String>(Arrays.asList(word)));
			}
			if(sizeToWord.get(word.length())!=null)
			{
				sizeToWord.get(word.length()).add(word);
			}
			else
			{
				sizeToWord.put(word.length(), new ArrayList<String>(Arrays.asList(word)));
			}
        }
    }
	public String sortWord(String word)
	{
		char[] array = word.replaceAll("\\s+", "").toLowerCase().toCharArray();
		Arrays.sort(array);
		String keymap=new String(array);
		return keymap;
		
	}

    public boolean isGoodWord(String word, String base) {
        if(wordSet.contains(word))
		{
			if(!(word.contains(base)))
				return true;
		}
		return false;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
		String s;
		s=sortWord(word);
		result=letterToWord.get(s);
        return result;
    }

    public String pickGoodStarterWord() 
	{
        String temps,temps1;
		while(true)
		{
			temp=sizeToWord.get(wordLength);
			temps=temp.get(random.nextInt(temp.size()));
			temps1=sortWord(temps);
			if(letterToWord.get(temps1).size()<MIN_NUM_ANAGRAMS)
				continue;
			if(wordLength==MAX_WORD_LENGTH)
				wordLength=DEFAULT_WORD_LENGTH;
			else
				wordLength++;
			temp.clear();
			return temps;
		}
    }
}
