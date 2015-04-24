package at.ac.tuwien.big.we15.lab2.api;

import at.ac.tuwien.big.we15.lab2.api.impl.Opponent;

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
	
	/**
	 * sets the non computer player to the param user
	 * @param user
	 * 			non computer player to be set
	 */
	public void setUser(User user);
	
	/**
	 * returns the non computer player
	 * @return
	 * 		User who is not the computer
	 */
	public User getUser();
	
	/**
	 * returns the last negative change as string
	 * @return
	 * 		String wich contains the last negative change
	 */
	public String getLastPositiveChange();
	
	/**
	 * returns the last positive change as string
	 * @return
	 * 		String wich contains the last positive change
	 */
	public String getLastNegativeChange();
	
	/**
	 * returns the last neutral change as string
	 * @return
	 * 		String wich contains the last neutral change
	 */
	public String getLastNeutralChange();
	
	/**
	 * sets the last positive change as string
	 * @param lastPositiveChange
	 * 			String to be set as last positive change
	 */
	public void setLastPositiveChange(String lastPositiveChange);
	
	/**
	 * sets the last neutral change as string
	 * @param lastNegativeChange
	 * 			String to be set as last negative change
	 */
	public void setLastNegativeChange(String lastNegativeChange);
	
	/**
	 * sets the last neutral change as string
	 * @param lastNeutralChange
	 * 			String to be set as last neutral change
	 */
	public void setLastNeutralChange(String lastNeutralChange);
	
	/**
	 * returns the instance of the opponent
	 * @return
	 * 		instance of the opponent
	 */
	public Opponent getOpponent();
	
}
