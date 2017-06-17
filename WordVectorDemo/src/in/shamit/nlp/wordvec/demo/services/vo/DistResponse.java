package in.shamit.nlp.wordvec.demo.services.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="DistResponse")
public class DistResponse {
	private float distance;
	private Word word;
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
	public Word getWord() {
		return word;
	}
	public void setWord(Word word) {
		this.word = word;
	}
}
