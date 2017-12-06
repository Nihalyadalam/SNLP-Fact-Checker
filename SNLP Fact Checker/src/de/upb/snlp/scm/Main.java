package de.upb.snlp.scm;

import de.upb.snlp.scm.core.NERUtil;
import de.upb.snlp.scm.util.Config;
import de.upb.snlp.scm.util.FileUtil;

/**
 * 
 * @author Kadiray Karakaya
 *
 */
public class Main {

	public static void main(String args[]) {

		String content = FileUtil.readFile(Config.TEST_FILE);

		String classifierPath = Config.NER_3_CLASSIFIER;

		System.out.println(NERUtil.identifyNER(content, classifierPath).toString());
	}

}
