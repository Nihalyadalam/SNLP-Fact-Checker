package de.upb.snlp.scm.model;

import java.util.Arrays;

/**
 * 
 * @author Kadiray Karakaya
 *
 */
public class Sentence {

	private String[] words;
	private int[] sequence;

	public Sentence(String s) {
		words = s.split(" ");
		sequence = new int[words.length];
	}

	public String[] getWords() {
		return words;
	}

	public int[] getSequence() {
		return sequence;
	}

	public void up(int index) {
		sequence[index] = 1;
	}

	@Override
	public String toString() {
		return "Sentence [words=" + Arrays.toString(words) + ", sequence=" + Arrays.toString(sequence) + "]";
	}

}
