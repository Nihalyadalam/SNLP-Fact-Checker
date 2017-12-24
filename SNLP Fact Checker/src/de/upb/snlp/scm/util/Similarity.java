package de.upb.snlp.scm.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import de.upb.snlp.scm.model.Triplet;
import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.impl.WuPalmer;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;

/**
 * 
 * @author Kadiray Karakaya
 *
 */
public class Similarity {

	private static ILexicalDatabase db = new NictWordNet();
	public static RelatednessCalculator wuPalmer = new WuPalmer(db);

	private RelatednessCalculator calculator;

	// { new HirstStOnge(db), new LeacockChodorow(db), new Lesk(db),
	// new WuPalmer(db), new Resnik(db), new JiangConrath(db), new Lin(db), new
	// Path(db) };
	public Similarity(RelatednessCalculator calculator) {
		this.calculator = calculator;
	}

	public double findSimilarity(Triplet triplet1, Triplet triplet2) {
		WS4JConfiguration.getInstance().setMFS(true);
		double[][] nmatrix = calculator.getNormalizedSimilarityMatrix(triplet1.toArray(), triplet2.toArray());
		System.out.println(triplet1.toString());
		System.out.println(triplet2.toString());
		double score = 0;
		int size = 0;
		NumberFormat formatter = new DecimalFormat("#0.00");
		for (double[] col : nmatrix) {
			System.out.println(" ");
			for (double d : col) {
				System.out.print(formatter.format(d) + " _ ");
				score += d;
			}

			size++;
		}
		score /= size;
		return score;
	}

	public double findSimilarity(String word1, String word2) {
		WS4JConfiguration.getInstance().setMFS(true);
		String[] s1 = { word1 };
		String[] s2 = { word2 };
		double[][] nmatrix = calculator.getNormalizedSimilarityMatrix(s1, s2);
		double score = 0;
		int size = 0;
		NumberFormat formatter = new DecimalFormat("#0.00");
		for (double[] col : nmatrix) {
			for (double d : col) {
				score += d;
			}

			size++;
		}
		score /= size;
		return score;
	}

}
