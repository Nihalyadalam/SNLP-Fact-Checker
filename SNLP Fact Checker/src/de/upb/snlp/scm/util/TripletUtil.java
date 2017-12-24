package de.upb.snlp.scm.util;

import java.util.ArrayList;
import java.util.List;

import de.upb.snlp.scm.model.Triplet;
import edu.stanford.nlp.util.Pair;

/**
 * 
 * @author Kadiray Karakaya
 *
 */
public class TripletUtil {

	public static boolean isTripletsWorthComparison(Triplet triplet1, Triplet triplet2) {
		Similarity similarity = new Similarity(Similarity.WU_PALMER);
		double sim = similarity.findSimilarity(triplet1.getPredicate(), triplet2.getPredicate());
		return sim > 0.3;
	}

	public static Pair<Triplet, Triplet> preprocessTriples(Triplet triplet1, Triplet triplet2) {
		Pair<Triplet, Triplet> pair = alignTriples(triplet1, triplet2);
		pair = processPronouns(pair);
		return pair;
		// return simplifyTriples(pair);
	}

	private static Pair<Triplet, Triplet> alignTriples(Triplet triplet1, Triplet triplet2) {
		Pair<Triplet, Triplet> pair = new Pair<>();
		// align subject and objects
		if (triplet1.getSubject().contains(triplet2.getObject())) {
			String subject = triplet1.getSubject();
			String object = triplet1.getObject();
			triplet1.setObject(subject);
			triplet1.setSubject(object);
		} else if (triplet2.getSubject().contains(triplet1.getObject())) {
			String subject = triplet2.getSubject();
			String object = triplet2.getObject();
			triplet2.setObject(subject);
			triplet2.setSubject(object);
		} else if (triplet1.getObject().contains(triplet2.getSubject())) {
			String subject = triplet1.getSubject();
			String object = triplet1.getObject();
			triplet1.setObject(subject);
			triplet1.setSubject(object);
		} else if (triplet2.getObject().contains(triplet1.getSubject())) {
			String subject = triplet2.getSubject();
			String object = triplet2.getObject();
			triplet2.setObject(subject);
			triplet2.setSubject(object);
		}
		pair.setFirst(triplet1);
		pair.setSecond(triplet2);
		return pair;
	}

	private static Pair<Triplet, Triplet> processPronouns(Pair<Triplet, Triplet> pair) {
		Triplet triplet1 = pair.first;
		Triplet triplet2 = pair.second;
		// simplify subjects
		if (triplet1.getSubject().equals("he") || triplet1.getSubject().equals("she")) {
			triplet1.setSubject(triplet2.getSubject());
		} else if (triplet2.getSubject().equals("he") || triplet2.getSubject().equals("she")) {
			triplet2.setSubject(triplet1.getSubject());
		}

		// simplify objects;
		if (triplet1.getObject().equals("he") || triplet1.getSubject().equals("she")) {
			triplet1.setObject(triplet2.getObject());
		} else if (triplet2.getObject().equals("he") || triplet1.getSubject().equals("she")) {
			triplet2.setObject(triplet1.getObject());
		}

		return pair;
	}

	private static Pair<Triplet, Triplet> simplifyTriples(Pair<Triplet, Triplet> pair) {
		Triplet triplet1 = pair.first;
		Triplet triplet2 = pair.second;
		// simplify subjects
		if (triplet1.getSubject().contains(triplet2.getSubject())) {
			triplet1.setSubject(triplet2.getSubject());
		} else if (triplet2.getSubject().contains(triplet1.getSubject())) {
			triplet2.setSubject(triplet1.getSubject());
		}

		// simplify objects;
		if (triplet1.getObject().contains(triplet2.getObject())) {
			triplet1.setObject(triplet2.getObject());
		} else if (triplet2.getObject().contains(triplet1.getObject())) {
			triplet2.setObject(triplet1.getObject());
		}

		return pair;
	}

	public static List<Pair<Triplet, Triplet>> findCandidatesForComparison(List<Triplet> inputTriplets,
			List<Triplet> corpusTriplets) {
		List<Pair<Triplet, Triplet>> candidates = new ArrayList<>();
		for (Triplet input : inputTriplets) {
			for (Triplet corpus : corpusTriplets) {
				if (input.getObject().contains(corpus.getObject()) || input.getSubject().contains(corpus.getSubject())
						|| corpus.getObject().contains(input.getObject())
						|| corpus.getSubject().contains(input.getSubject())
						|| corpus.getObject().contains(input.getSubject())
						|| corpus.getSubject().contains(input.getObject())
						|| input.getObject().contains(corpus.getSubject())
						|| input.getSubject().contains(corpus.getObject())) {
					Pair<Triplet, Triplet> pair = new Pair<>(input, corpus);
					candidates.add(pair);
				}
			}
		}
		return candidates;
	}

}
