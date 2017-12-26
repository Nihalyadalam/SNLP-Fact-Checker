package de.upb.snlp.scm.model;

/**
 * 
 * @author Kadiray Karakaya
 *
 */
public class Input {
	private long id;
	private String sentence;
	private double value;

	public Input(long id, String sentence, double value) {
		this.id = id;
		this.sentence = sentence;
		this.value = value;
	}

	public long getId() {
		return id;
	}

	public String getSentence() {
		return sentence;
	}

	public double getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Input [id=" + id + ", sentence=" + sentence + ", value=" + value + "]";
	}

}
