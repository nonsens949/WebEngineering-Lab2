package at.ac.tuwien.big.we15.lab2.api;

public interface GameState {

	/**
	 * sets the score from player1 to param score
	 * @param score
	 * 			score to be set
	 */
	public void setScorePlayer1(int score);
	
	/**
	 * increases the score from player1 by the param score
	 * @param score
	 * 			amount the score will increased by
	 */
	public void raiseScorePlayer1(int score);
	
	/**
	 * reduces the score from player1 by the param score
	 * @param score
	 * 			amount the score will be reduced by
	 */
	public void reduceScorePlayer1(int score);
	
	/**
	 * returns the current score of player1
	 * @return
	 * 			current score of player 1
	 */
	public int getScorePlayer1();
	
	/**
	 * sets the score from player2 to param score
	 * @param score
	 * 			score to be set
	 */
	public void setScorePlayer2(int score);
	
	/**
	 * increases the score from player2 by the param score
	 * @param score
	 * 			amount the score will increased by
	 */
	public void raiseScorePlayer2(int score);
	
	/**
	 * reduces the score from player2 by the param score
	 * @param score
	 * 			amount the score will be reduced by
	 */
	public void reduceScorePlayer2(int score);
	
	/**
	 * returns the current score of player1
	 * @return
	 * 			current score of player 1
	 */
	public int getScorePlayer2();
	
	/**
	 * increases the roundCounter by 1
	 */
	public void incrementRoundCounter();
	
	/**
	 * returns the roundCounter which holds the information which round is currently played	
	 * @return
	 * 			current counter of the rounds
	 */
	public int getRoundCounter();
	
}
