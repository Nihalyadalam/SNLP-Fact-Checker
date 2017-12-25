package de.upb.snlp.scm.core;

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
	public static RelatednessCalculator WU_PALMER = new WuPalmer(db);

	private RelatednessCalculator calculator;

	// { new HirstStOnge(db), new LeacockChodorow(db), new Lesk(db),
	// new WuPalmer(db), new Resnik(db), new JiangConrath(db), new Lin(db), new
	// Path(db) };
	public Similarity(RelatednessCalculator calculator) {
		this.calculator = calculator;
	}

	public double findSimilarity(Triplet triplet1, Triplet triplet2) {
		WS4JConfiguration.getInstance().setMFS(true);
		double[][] simMatrix = calculator.getNormalizedSimilarityMatrix(triplet1.toArray(), triplet2.toArray());
		double score = 0;
		for (int i = 0; i < simMatrix.length; i++) {
			// simMatrix is always 3x3
			score += simMatrix[i][i];
		}
		return score / simMatrix.length;
	}

	public double findSimilarity(String word1, String word2) {
		WS4JConfiguration.getInstance().setMFS(true);
		String[] s1 = { word1 };
		String[] s2 = { word2 };
		double[][] simMatrix = calculator.getNormalizedSimilarityMatrix(s1, s2);
		return simMatrix[0][0];
	}

}
