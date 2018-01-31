package de.upb.snlp.scm.core;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;

import de.upb.snlp.scm.model.Triplet;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.SentenceUtils;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.util.CoreMap;

/**
 * Stanford Core Open IE utilities
 * 
 * @author Kadiray Karakaya
 *
 */
public class NLP {
	public static LinkedHashMap<String, LinkedHashSet<String>> findNamedEntities(String text, String classifierPath) {
		LinkedHashMap<String, LinkedHashSet<String>> map = new <String, LinkedHashSet<String>>LinkedHashMap();
		CRFClassifier<CoreLabel> classifier = CRFClassifier.getClassifierNoExceptions(classifierPath);
		List<List<CoreLabel>> classify = classifier.classify(text);
		for (List<CoreLabel> coreLabels : classify) {
			for (CoreLabel coreLabel : coreLabels) {

				String word = coreLabel.word();
				String category = coreLabel.get(CoreAnnotations.AnswerAnnotation.class);
				if (!"O".equals(category)) {
					if (map.containsKey(category)) {
						map.get(category).add(word);
					} else {
						LinkedHashSet<String> temp = new LinkedHashSet<String>();
						temp.add(word);
						map.put(category, temp);
					}

				}
			}

		}
		return map;
	}

	public static List<Triplet> findRelations(List<String> sentences) {
		List<Triplet> relations = new ArrayList<>();
		for (String s : sentences) {
			relations.addAll(findRelations(s));
		}

		return relations;
	}

	public static List<Triplet> findRelations(String plainText) {
		List<Triplet> relations = new ArrayList<>();
		// Create the Stanford CoreNLP pipeline
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize,ssplit,pos,lemma,depparse,natlog,openie");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		// Annotate an example document.
		Annotation doc = new Annotation(plainText);
		pipeline.annotate(doc);

		// Loop over sentences in the document
		for (CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)) {
			// Get the OpenIE triples for the sentence
			Collection<RelationTriple> triples = sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
			// Print the triples
			for (RelationTriple triple : triples) {
				if (triple.confidence > 0.8) {
					Triplet triplet = new Triplet(triple.subjectLemmaGloss(), triple.relationLemmaGloss(),
							triple.objectLemmaGloss());
					relations.add(triplet);
				}
			}
		}
		return relations;
	}

	public static List<String> extractSentences(String corpus) {
		Reader reader = new StringReader(corpus);
		DocumentPreprocessor dp = new DocumentPreprocessor(reader);
		List<String> sentenceList = new ArrayList<String>();

		for (List<HasWord> sentence : dp) {
			// SentenceUtils not Sentence
			String sentenceString = SentenceUtils.listToString(sentence);
			sentenceList.add(sentenceString);
		}
		return sentenceList;
	}

	public static List<String> findCandidateSentences(String corpus, List<String> inputObjects) {
		List<String> candidateSentences = new ArrayList<>();

		List<String> allSentences = extractSentences(corpus);

		for (String s : allSentences) {
			for (String o : inputObjects) {
				if (s.contains(o)) {
					candidateSentences.add(s);
				}
			}
		}

		return candidateSentences;
	}

}