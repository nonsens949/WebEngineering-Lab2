package at.ac.tuwien.big.we15.lab2.api;

/**
 * Represents the strategy of the virtual opponent in a jeopardy-game
 * @author Emil Gruber
 *
 */
public interface OpponentStrategy {

	/**
	 * 
	 * @param catalog
	 * @param status
	 * @return
	 */
	public Question nextQuestion(QuestionCatalog catalog, GameStatus status);
	
	/**
	 * 
	 * @param question
	 * @return true if the question was answered correctly, otherwise returns false
	 */
	public boolean answerQuestion(Question question);
}
