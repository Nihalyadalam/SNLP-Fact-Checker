package de.upb.snlp.scm;

import java.util.ArrayList;
import java.util.List;

import de.upb.snlp.scm.core.Network;
import de.upb.snlp.scm.core.Parser;
import de.upb.snlp.scm.core.Relation;
import de.upb.snlp.scm.model.Input;
import de.upb.snlp.scm.model.Triplet;
import de.upb.snlp.scm.util.Config;
import de.upb.snlp.scm.util.FileUtil;
import de.upb.snlp.scm.util.ListUtil;

/**
 * 
 * @author Kadiray Karakaya
 *
 */
public class Main {

	public static void main(String args[]) {

		// read input file
		List<Input> inputs = FileUtil.readTSV(Config.INPUT_FILE);

		// find truth score and build output file
		StringBuilder ouput = new StringBuilder();
		for (Input i : inputs) {
			double value = 0;
			try {
				value = findTruthScore(i.getSentence());
			} catch (Exception e) {
				i.getSentence();
				e.printStackTrace();
			}
			ouput.append(FileUtil.createTTLLine(String.valueOf(i.getId()), value));
			if ("true".equalsIgnoreCase(Config.DEBUG)) {
				System.out.print(i.getSentence() + "\t");
				System.out.println(value);
			}

		}

		// write to output file
		FileUtil.writeToFile(Config.OUTPUT_FILE, ouput.toString());

	}

	/**
	 * Finds truth score for the given sentence. If the relation is found for a
	 * subject from wikipedia and the object of this relation is the same as the
	 * object of input relation returns 1.0. If the object of this relation is
	 * not the same as the object of the input returns -1.0. If the relation is
	 * not found or the subject's article is not found returns 0.0
	 * 
	 * @param input
	 *            is the sentence to be checked for truth value
	 * @return returns a correctness value of either 1.0, -1.0, or 0.0
	 */
	private static double findTruthScore(String input) {
		Triplet inputRelations = Relation.findRelation(input);

		// if input relations could not be found score is 0
		if (inputRelations == null) {
			return 0.0;
		}

		// article is found by replacing spaces with underscore
		String article = inputRelations.getSubject().replaceAll(" ", "_");

		// get wikipedia article html document
		Network.getCorpus(article);

		// extract triples from wikipedia article html doc
		List<Triplet> triplets = Parser.getInfobox(inputRelations.getSubject(), Network.doc);

		// find triples whose relation is the same as the input relation
		List<Triplet> equalRelations = new ArrayList<>();
		if (ListUtil.isNotEmpty(triplets)) {
			for (Triplet t : triplets) {
				if (t.getPredicate().equals(inputRelations.getPredicate())) {
					equalRelations.add(t);
				}
			}
		}

		// check for correctness
		double value = 0;
		boolean right = false;
		if (ListUtil.isNotEmpty(equalRelations)) {
			for (Triplet t : equalRelations) {
				if (t.getObject().contains(inputRelations.getObject())
						|| inputRelations.getObject().contains(t.getObject())) {
					value = 1.0;
					right = true;
					break;
				}
				if (right == false) {
					value = -1.0;
				}
			}
		} else {
			value = 0.0;
		}

		return value;
	}

}
