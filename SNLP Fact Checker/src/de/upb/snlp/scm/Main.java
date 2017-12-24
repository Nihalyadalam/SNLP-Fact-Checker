package de.upb.snlp.scm;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import de.upb.snlp.scm.core.NLP;
import de.upb.snlp.scm.model.Triplet;
import de.upb.snlp.scm.util.Config;
import de.upb.snlp.scm.util.Network;

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

		for (Triplet t : corpusRelations) {
			System.out.println(t.toString());
		}

		List<Triplet> inputRelations = NLP.findRelations(input);

		for (Triplet t : inputRelations) {
			System.out.println("input: ");
			System.out.println(t.toString());
		}

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
