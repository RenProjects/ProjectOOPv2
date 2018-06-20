package hr.tvz.oop.dao;

public interface ResultsDao {

	/**
	 * Inicijalizacija kviza.
	 * 
	 * @param userId
	 * @return id sesije
	 */
	int initializeQuiz(int userId);

	/**
	 * Pohrana pojedinog rezultata korinika.
	 * 
	 * @param imageId
	 * @param resultsId
	 * @param isCorrect
	 */
	void saveResult(int imageId, int resultsId, boolean isCorrect);
	
	/**
	 * Završetak kviza.
	 * 
	 * @param userId
	 * @param quizId
	 */
	void finishQuiz(int userId, int quizId);

}
