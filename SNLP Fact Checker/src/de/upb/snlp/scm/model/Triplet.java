package de.upb.snlp.scm.model;

/**
 * 
 * @author Kadiray Karakaya
 *
 */
public class Triplet {

	private String subject;
	private String predicate;
	private String object;

	public Triplet(String subject, String predicate, String object) {
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPredicate() {
		return predicate;
	}

	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	@Override
	public String toString() {
		return "_" + subject + "_\t\t_" + predicate + "_\t\t_" + object + "_";
	}

}
