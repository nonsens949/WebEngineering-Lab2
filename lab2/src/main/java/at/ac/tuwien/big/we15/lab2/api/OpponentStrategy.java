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
	 * @param userScore
	 * @param opponentScore
	 * @param round
	 * @return the question chosen by the opponent
	 */
	public Question nextQuestion(QuestionCatalog catalog, int userScore, int opponentScore, int round);
	
	/**
	 * 
	 * @param question
	 * @return true if the question was answered correctly, otherwise returns false
	 */
	public boolean answerQuestion(Question question);
}
