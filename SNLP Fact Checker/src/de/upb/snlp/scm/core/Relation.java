package de.upb.snlp.scm.core;

import de.upb.snlp.scm.model.Triplet;

/**
 * 
 * @author Kadiray Karakaya
 *
 */
public class Relation {

	// known relationship labels
	public static final String AWARD = "award";
	public static final String BORN_IN = "born in";
	public static final String DIE_IN = "die in";
	public static final String SPOUSE = "spouse";
	public static final String LEADER = "leader";
	public static final String TEAM = "team";
	public static final String FOUND = "found";
	public static final String STARS = "stars";
	public static final String AUTHOR = "author";
	public static final String SUBSIDIARY = "subsidiary";
	public static final String UNKNOWN = "unknown";

	public static Triplet findRelation(String input) {
		String relation = null;
		Triplet triplet = null;
		if (input.contains("award is")) {
			return Parser.parseRegular(input, "award is", AWARD);
		} else if (input.contains("honour")) {
			return Parser.parseInverted(input, "honour", AWARD);
		} else if (input.contains("nascence place")) {
			return Parser.parseInverted(input, "nascence place", BORN_IN);
		} else if (input.contains("birth place is")) {
			return Parser.parseRegular(input, "birth place is", BORN_IN);
		} else if (input.contains("death place is")) {
			return Parser.parseRegular(input, "death place is", DIE_IN);
		} else if (input.contains("last place")) {
			return Parser.parseInverted(input, "last place", DIE_IN);
		} else if (input.contains("spouse is")) {
			return Parser.parseRegular(input, "spouse is", SPOUSE);
		} else if (input.contains("better half")) {
			return Parser.parseInverted(input, "better half", SPOUSE);
		} else if (input.contains(" role.")) {
			return Parser.parseInverted(input, "role", LEADER);
		} else if (input.contains("office is")) {
			return Parser.parseRegular(input, "office is", LEADER);
		} else if (input.contains("team is")) {
			return Parser.parseRegular(input, "team is", TEAM);
		} else if (input.contains(" squad.")) {
			return Parser.parseInverted(input, "squad", TEAM);
		} else if (input.contains("foundation place is")) {
			return Parser.parseRegular(input, "foundation place is", FOUND);
		} else if (input.contains("innovation place")) {
			return Parser.parseInverted(input, "innovation place", FOUND);
		} else if (input.startsWith("Stars")) {
			String s = "Stars Jonah Hill has been clerked Clerks.";
			String subject = input.substring("Stars ".length(), input.indexOf(" has been"));
			String object = input.substring(input.lastIndexOf(' ') + 1);
			object = Parser.normalize(object);
			return new Triplet(subject, STARS, object);
		} else if (input.contains("stars")) {
			int predIndex = input.indexOf("stars");
			String object = input.substring(0, predIndex);
			String subject = input.substring(predIndex + "stars".length() + 1, input.length());
			object = Parser.normalize(object);
			subject = Parser.normalize(subject);
			return new Triplet(subject, STARS, object);
		} else if (input.contains("author is")) {
			return TripletUtil.swap(Parser.parseRegular(input, "author is", AUTHOR));
		} else if (input.contains(" generator.")) {
			return TripletUtil.swap(Parser.parseInverted(input, "generator", AUTHOR));
		} else if (input.contains("subordinate")) {
			return TripletUtil.swap(Parser.parseInverted(input, "subordinate", SUBSIDIARY));
		} else if (input.contains("subsidiary")) {
			return TripletUtil.swap(Parser.parseRegular(input, "subsidiary is", SUBSIDIARY));
		} else {
			relation = UNKNOWN;
		}
		return triplet;
	}

}
