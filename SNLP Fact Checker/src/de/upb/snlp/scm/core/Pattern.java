package de.upb.snlp.scm.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.upb.snlp.scm.model.Word;

/**
 * 
 * @author Kadiray Karakaya
 *
 */
public class Pattern {

	static Map<String, Word[]> sievedWords = new HashMap<>();

	public static void matchStructures(List<String> sentences) {
		List<String[]> sieve = new ArrayList<>();
		for (String sentence : sentences) {
			String[] words = sentence.split(" ");
			sieve.add(words);
		}
	}

	private static void findPattern(String[] words) {
		for (String w : words) {
			for (String key : sievedWords.keySet()) {
				if (w.equals(sievedWords.get(key))) {

				}
			}
		}
	}

}
