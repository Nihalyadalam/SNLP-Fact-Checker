package de.upb.snlp.scm;

import de.upb.snlp.scm.core.NERUtil;
import de.upb.snlp.scm.util.Config;
import de.upb.snlp.scm.util.NetworkUtil;

/**
 * 
 * @author Kadiray Karakaya
 *
 */
public class Main {

	public static void main(String args[]) {

		String corpus = NetworkUtil.getCorpus("Albert Einstein");

		String classifierPath = Config.NER_3_CLASSIFIER;

		int index = 0;
		while (corpus.indexOf("\n", index)!=-1) {
			int line = corpus.indexOf("\n", index);
			String sentence = corpus.substring(index, line);
			index = line + 1;
			System.out.println(NERUtil.identifyNER(sentence, classifierPath).toString());
		}

	}

}
