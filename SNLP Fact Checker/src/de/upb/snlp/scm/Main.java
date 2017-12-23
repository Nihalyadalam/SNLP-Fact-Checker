package de.upb.snlp.scm;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import de.upb.snlp.scm.core.NERUtil;
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
		
		System.out.println(NERUtil.identifyNER(input, classifierPath));
		
		Map<String, LinkedHashSet<String>> EntityMap = NERUtil.identifyNER(input, classifierPath);
		
		LinkedHashSet<String> personSet = EntityMap.get("PERSON");
		
		String articleTitle = "";
		for(String s: personSet) {
			articleTitle += s + " ";
		}
		articleTitle = articleTitle.trim();
		
		String corpus = Network.getCorpus(articleTitle);
		
		System.out.println(corpus);
		
		
		/*
		String corpus = NetworkUtil.getCorpus("Albert Einstein");


		int index = 0;
		while (corpus.indexOf("\n", index)!=-1) {
			int line = corpus.indexOf("\n", index);
			String sentence = corpus.substring(index, line);
			index = line + 1;
			System.out.println(NERUtil.identifyNER(sentence, classifierPath).toString());
		}
*/
	}

}
