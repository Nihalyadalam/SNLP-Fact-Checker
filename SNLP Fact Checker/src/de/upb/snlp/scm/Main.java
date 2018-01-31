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
		List<Input> inputs = FileUtil.readTSV("Data/test.tsv");

		StringBuilder ouput = new StringBuilder();

		for (Input i : inputs) {
			double value = 0;
			try {
				value = findExactInfo(i.getSentence());
			} catch (Exception e) {
				i.getSentence();
				e.printStackTrace();
			}
			ouput.append(createTTLLine(String.valueOf(i.getId()), value));
			if ("true".equalsIgnoreCase(Config.DEBUG)) {
				System.out.print(i.getSentence() + "\t");
				System.out.println(value);
			}

		}

		FileUtil.writeToFile("data/result.ttl", ouput.toString());

	}

	private static String createTTLLine(String id, double value) {
		StringBuilder str = new StringBuilder();
		str.append("<http://swc2017.aksw.org/task2/dataset/").append(id).append(">")
				.append("<http://swc2017.aksw.org/hasTruthValue>\"").append(value)
				.append("\"^^<http://www.w3.org/2001/XMLSchema#double> .").append("\n");

		return str.toString();
	}

	private static double findExactInfo(String input) {
		Triplet inputRelations = Relation.findRelation(input);

		if (inputRelations == null) {
			return 0.0;
		}

		String article = inputRelations.getSubject().replaceAll(" ", "_");

		String corpus = Network.getCorpus(article);

		List<Triplet> triplets = Parser.getInfobox(inputRelations.getSubject(), Network.doc);

		double value = 0;
		boolean right = false;

		List<Triplet> equalRelations = new ArrayList<>();

		if (ListUtil.isNotEmpty(triplets)) {
			for (Triplet t : triplets) {
				if (t.getPredicate().equals(inputRelations.getPredicate())) {
					equalRelations.add(t);
				}
			}
		}

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
