package de.upb.snlp.scm;

import java.util.ArrayList;
import java.util.List;

import de.upb.snlp.scm.core.Network;
import de.upb.snlp.scm.core.Parser;
import de.upb.snlp.scm.core.Relation;
import de.upb.snlp.scm.model.Input;
import de.upb.snlp.scm.model.Triplet;
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

		int trueCount = 0;
		int falseCount = 0;

		StringBuilder ouput = new StringBuilder();

		for (Input i : inputs) {
			double value = 0;
			try {
				value = findExactInfo(i.getSentence());
			} catch (Exception e) {
				i.getSentence();
				e.printStackTrace();
			}
			System.out.print(i.getSentence() + "\t");
			ouput.append(createTTLLine(String.valueOf(i.getId()), value));
			// if (i.getValue() == value) {
			// System.out.print("True" + "\t");
			// ouput.append(createTTLLine(String.valueOf(i.getId()), value));
			// trueCount++;
			// } else {
			// System.out.print("False" + "\t");
			// ouput.append(createTTLLine(String.valueOf(i.getId()), 0.0));
			// falseCount++;
			// }
			System.out.println(value);

		}

		FileUtil.writeToFile("data/result.ttl", ouput.toString());

		System.out.println("True: " + trueCount);
		System.out.println("False: " + falseCount);

	}

	private static String createTTLLine(String id, double value) {
		StringBuilder str = new StringBuilder();
		str.append("<http://swc2017.aksw.org/task2/dataset/").append(id).append(">")
				.append("<http://swc2017.aksw.org/hasTruthValue>\"").append(value)
				.append("\"^^<http://www.w3.org/2001/XMLSchema#double> .").append("\n");

		return str.toString();
	}

	private static double findExactInfo(String input) {

		/**
		 * First approach
		 */
		// Map<String, LinkedHashSet<String>> EntityMap =
		// NLP.findNamedEntities(input, Config.NER_3_CLASSIFIER);

		// System.out.println(EntityMap);

		// LinkedHashSet<String> personSet = EntityMap.get("PERSON");
		//
		// if (personSet != null) {
		// // award
		// // birth
		// // death
		// //
		// return;
		// }

		// List<Triplet> inputRelations = NLP.findRelations(input);

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

//		if (ListUtil.isNotEmpty(triplets)) {
//			for (Triplet t : triplets) {
//				if (t.getPredicate().equals(inputRelations.getPredicate())) {
//					if (t.getObject().contains(inputRelations.getObject())
//							|| inputRelations.getObject().contains(t.getObject())) {
//						value = 1.0;
//						right = true;
//						break;
//					}
//					if (right == false) {
//						value = -1.0;
//					}
//				} else {
//					value = 0.0;
//				}
//			}
//		} else {
//			value = 0.0;
//		}

		return value;

		/**
		 * First approach
		 */
		// List<Triplet> inputList = new ArrayList<>();
		//
		// List<String> inputObjects = new ArrayList<>();
		//
		// for (Triplet t : inputRelations) {
		// inputList.add(t);
		// System.out.println(t.toString());
		// inputObjects.add(t.getObject());
		// }
		//
		// String articleTitle = "";
		// for (String s : personSet) {
		// articleTitle += s + "_";
		// }
		// if (articleTitle.endsWith("_")) {
		// articleTitle = articleTitle.substring(0, articleTitle.length() - 1);
		// }
		//
		// String corpus = Network.getCorpus(articleTitle);
		//
		// List<String> candidateSentences = NLP.findCandidateSentences(corpus,
		// inputObjects);
		//
		// List<Triplet> corpusRelations =
		// NLP.findRelations(candidateSentences);
		//
		// corpusRelations.addAll(Parser.getInfobox(articleTitle.replace("_", "
		// "), Network.doc));
		//
		// // List<Triplet> corpusRelations = NLP.findRelations(corpus);
		//
		// List<Triplet> corpusList = new ArrayList<>();
		// for (Triplet t : corpusRelations) {
		// corpusList.add(t);
		// }
		//
		// List<Pair<Triplet, Triplet>> pairs =
		// TripletUtil.findCandidatesForComparison(inputList, corpusList);
		//
		// Similarity similarity = new Similarity(Similarity.WU_PALMER);
		//
		// Map<Pair<Triplet, Triplet>, Double> similarities = new HashMap<>();
		//
		// int prefix = 0;
		// for (Pair<Triplet, Triplet> pair : pairs) {
		// pair = TripletUtil.preprocessTriples(pair.first, pair.second);
		// double sim = similarity.findSimilarity(pair.first, pair.second);
		// if (similarities.containsKey(pair)) {
		// prefix++;
		// pair.first.setSubject(prefix + pair.first.getSubject());
		// }
		// similarities.put(pair, sim);
		// }
		//
		// Map<Pair<Triplet, Triplet>, Double> sortedSimilarities =
		// MapUtil.sortByValue(similarities);
		//
		// for (Map.Entry<Pair<Triplet, Triplet>, Double> e :
		// sortedSimilarities.entrySet()) {
		// // System.out.println("sim: " + e.getValue() + " | " +
		// // e.getKey().first.toString() + " | "
		// // + e.getKey().second.toString());
		// for (String o : inputObjects) {
		// e.getKey().second.getObject().contains(o);
		// System.out.println("sim: " + e.getValue() + " | " +
		// e.getKey().first.toString() + " | "
		// + e.getKey().second.toString());
		// }
		// break;
		// }
	}

}
