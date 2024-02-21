package in.shamit.nlp.wordvec.demo.services.vo;

public class Word {
	private String word;
	private float[]vector;
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public float[] getVector() {
		return vector;
	}
	public void setVector(float[] vector) {
		this.vector = vector;
	}
}
