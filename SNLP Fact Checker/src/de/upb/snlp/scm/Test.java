package de.upb.snlp.scm;

import java.util.Collection;
import java.util.Properties;

import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.util.CoreMap;

public class Test {

	public static void main(String[] args) {
		// Create the Stanford CoreNLP pipeline
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize,ssplit,pos,lemma,depparse,natlog,openie");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		// Annotate an example document.
		Annotation doc = new Annotation("Albert Einstein was born in Ulm, in the Kingdom of Württemberg in the German Empire, on 14 March 1879.");
		pipeline.annotate(doc);

		// Loop over sentences in the document
		for (CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)) {
			// Get the OpenIE triples for the sentence
			Collection<RelationTriple> triples = sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
			// Print the triples
			for (RelationTriple triple : triples) {
				System.out.println(triple.confidence + "\t" + triple.subjectLemmaGloss() + "\t"
						+ triple.relationLemmaGloss() + "\t" + triple.objectLemmaGloss());
			}
		}

		// Create a CoreNLP document
		// Document sdoc = new Document("Albert Einstein was born in Ulm, in the
		// Kingdom of Württemberg in the German Empire, on 14 March 1879.");
		//
		// // Iterate over the sentences in the document
		// for (Sentence sent : sdoc.sentences()) {
		// // Iterate over the triples in the sentence
		// for (RelationTriple triple : sent.openieTriples()) {
		// // Print the triple
		// System.out.println(triple.confidence + "\t" +
		// triple.subjectLemmaGloss() + "\t" +
		// triple.relationLemmaGloss() + "\t" +
		// triple.objectLemmaGloss());
		// }
		// }

	}

}
