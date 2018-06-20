package hr.tvz.oop.model;

/**
 * Pojedinaèan odgovor korisnika.
 */
public class Anwser {

	int resultsId;
	int imageId;
	boolean isCorrect;

	public int getResultsId() {
		return resultsId;
	}

	public void setResultsId(int resultsId) {
		this.resultsId = resultsId;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public boolean isCorrect() {
		return isCorrect;
	}

	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

}
