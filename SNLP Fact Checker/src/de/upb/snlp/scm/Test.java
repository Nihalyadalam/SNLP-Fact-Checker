package de.upb.snlp.scm;

import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.impl.HirstStOnge;
import edu.cmu.lti.ws4j.impl.JiangConrath;
import edu.cmu.lti.ws4j.impl.LeacockChodorow;
import edu.cmu.lti.ws4j.impl.Lesk;
import edu.cmu.lti.ws4j.impl.Lin;
import edu.cmu.lti.ws4j.impl.Path;
import edu.cmu.lti.ws4j.impl.Resnik;
import edu.cmu.lti.ws4j.impl.WuPalmer;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;

public class Test {
	private static ILexicalDatabase db = new NictWordNet();
	private static RelatednessCalculator[] rcs = { new HirstStOnge(db), new LeacockChodorow(db), new Lesk(db),
			new WuPalmer(db), new Resnik(db), new JiangConrath(db), new Lin(db), new Path(db) };

	private static void run(String word1, String word2) {
		WS4JConfiguration.getInstance().setMFS(true);
		double average = 0;
		for (RelatednessCalculator rc : rcs) {
			double s = rc.calcRelatednessOfWords(word1, word2);
			average += s;
			average = Double.isInfinite(average) ? Double.MAX_VALUE : average;
		}
		average /= rcs.length;
		average = Double.isInfinite(average) ? 1.0 : average / Double.MAX_VALUE;
		System.out.println("average sim: " + average);
	}

	private static void runs(String[] word1, String[] word2) {
		WS4JConfiguration.getInstance().setMFS(true);
		double average = 0;
		for (RelatednessCalculator rc : rcs) {
			System.out.println(rc.getClass().getSimpleName());
			double[][] nmatrix = rc.getNormalizedSimilarityMatrix(word1, word2);
			for (double[] col : nmatrix) {
				for (double d : col) {
					System.out.print(d + " - ");
				}
				System.out.println(" ");
			}
			// average += s;
			// average = Double.isInfinite(average) ? Double.MAX_VALUE :
			// average;
		}
		// average /= rcs.length;
		// average = Double.isInfinite(average) ? 1.0 : average /
		// Double.MAX_VALUE;
		// System.out.println("average sim: " + average);
	}

	public static void main(String[] args) {
		long t0 = System.currentTimeMillis();
		String[] words1 = { "make", "do", "together", "potato" };
		String[] words2 = { "make", "do", "never", "tomato" };
		// run("make", "do");
		runs(words1, words2);
		long t1 = System.currentTimeMillis();
		System.out.println("Done in " + (t1 - t0) + " msec.");
	}
}