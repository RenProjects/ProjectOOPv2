package hr.tvz.oop.model;

/**
 * Enumeracija kategorija slika.
 */
public enum Category {

	POSITIVE(1), NEGATIVE(0);
	
	private int value;
	
	private Category(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
