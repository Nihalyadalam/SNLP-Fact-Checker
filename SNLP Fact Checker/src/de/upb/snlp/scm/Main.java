package de.upb.snlp.scm;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import de.upb.snlp.scm.core.NLP;
import de.upb.snlp.scm.model.Triplet;
import de.upb.snlp.scm.util.Config;
import de.upb.snlp.scm.util.Network;
import de.upb.snlp.scm.util.Similarity;
import de.upb.snlp.scm.util.TripletUtil;
import edu.stanford.nlp.util.Pair;

/**
 * 
 * @author Kadiray Karakaya
 *
 */
public class Main {

	public static void main(String args[]) {

		String classifierPath = Config.NER_3_CLASSIFIER;

		String input = "Nobel Peace Prize is Henry Dunant's honour.";

		System.out.println(NLP.findNamedEntities(input, classifierPath));

		Map<String, LinkedHashSet<String>> EntityMap = NLP.findNamedEntities(input, classifierPath);

		LinkedHashSet<String> personSet = EntityMap.get("PERSON");

		String articleTitle = "";
		for (String s : personSet) {
			articleTitle += s + "_";
		}
		if (articleTitle.endsWith("_")) {
			articleTitle = articleTitle.substring(0, articleTitle.length() - 1);
		}

		String corpus = Network.getCorpus(articleTitle);

		List<Triplet> corpusRelations = NLP.findRelations(corpus);

		List<Triplet> corpusList = new ArrayList<>();
		for (Triplet t : corpusRelations) {
			corpusList.add(t);
		}

		List<Triplet> inputRelations = NLP.findRelations(input);

		List<Triplet> inputList = new ArrayList<>();

		for (Triplet t : inputRelations) {
			inputList.add(t);
		}

		List<Pair<Triplet, Triplet>> pairs = TripletUtil.findCandidatesForComparison(inputList, corpusList);

		Similarity similarity = new Similarity(Similarity.WU_PALMER);

		for (Pair<Triplet, Triplet> pair : pairs) {
			System.out.println("initial: " + pair.first.toString() + " : " + pair.second.toString());
			pair = TripletUtil.alignTriples(pair.first, pair.second);
			System.out.println("aligned: " + pair.first.toString() + " : " + pair.second.toString());
			double sim = similarity.findSimilarity(pair.first, pair.second);
			System.out.println("sim: " + sim);

		}

		// Triplet triplet1 = new Triplet("Henry Dumont", "receive", "Nobel
		// Peace Prize");
		// Triplet triplet2 = new Triplet("Nobel Peace Prize", "be", "Henry
		// Dumont's Honour");
		//
		// if (!TripletUtil.isTripletsWorthComparison(triplet1, triplet2)) {
		// return;
		// }

		// Pair<Triplet, Triplet> tripletPair =
		// TripletUtil.alignTriples(triplet1, triplet2);

		// System.out.println(corpus);

		/*
		 * String corpus = NetworkUtil.getCorpus("Albert Einstein");
		 * 
		 * 
		 * int index = 0; while (corpus.indexOf("\n", index)!=-1) { int line =
		 * corpus.indexOf("\n", index); String sentence =
		 * corpus.substring(index, line); index = line + 1;
		 * System.out.println(NERUtil.identifyNER(sentence,
		 * classifierPath).toString()); }
		 */
	}

}
