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
		return subject + " " + predicate + " " + object;
	}

	public String[] toArray() {
		String[] arr = new String[3];
		arr[0] = subject;
		arr[1] = predicate;
		arr[2] = object;
		return arr;
	}

}
