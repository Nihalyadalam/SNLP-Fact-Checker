package de.upb.snlp.scm;

import java.util.ArrayList;
import java.util.List;

import de.upb.snlp.scm.model.Sentence;

/**
 * 
 * @author Kadiray Karakaya
 *
 */
public class Filter {

	public static void main(String[] args) {
		String base = "Martina Navratilova's birth place is Prague.";

		List<Sentence> sentences = new ArrayList<>();

		String[] data = { "Tom Hanks' birth place is New York City.", "George Bernard Shaw's birth place is Dublin.",
				"Abraham Lincoln's birth place is Hodgenville, Kentucky.",
				"Jimmy Carter's birth place is Plains, Georgia.", "Stephen King's birth place is Portland, Maine." };

		for (String s : data) {
			sentences.add(new Sentence(s));
		}

		int[] sequence = new int[20];

		String[] baseWords = base.split(" ");

		for (Sentence s : sentences) {
			String[] dataWords = s.getWords();
			for (int i = 0; i < dataWords.length; i++) {
				for (String b : baseWords) {
					if (dataWords[i].equals(b)) {
						s.up(i);
					}
				}
			}
		}

		System.out.println(sentences);

		for (Sentence s : sentences) {
			System.out.println(findStructure(s));
		}

	}

	private static String findStructure(Sentence s) {
		StringBuilder structure = new StringBuilder();
		int i = 0;
		if (s.getSequence()[0] != 1) {
			structure.append("{ } ");
		}
		for (String w : s.getWords()) {
			if (s.getSequence()[i] == 1) {
				structure.append(w).append(" ");
			}
			i++;
		}
		if (s.getSequence()[s.getSequence().length - 1] != 1) {
			structure.append("{}");
		}

		return structure.toString();
	}

}
