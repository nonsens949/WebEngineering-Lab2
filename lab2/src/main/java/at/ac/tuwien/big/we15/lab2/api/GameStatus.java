package at.ac.tuwien.big.we15.lab2.api;

/**
 * This class stores scores for two players and the number of played rounds
 * @author Emil Gruber
 *
 */
public interface GameStatus {

	public int getPlayer1Score();
	
	public int getPlayer2Score();
	
	public void setPlayer1Score(int score);
	
	public void setPlayer2Score(int score);
	
	public int getRound();
	
	public void setRound(int round);
	
	public void incrementRound();
	
	/**
	 * 
	 * @return the last category selected by player 1
	 */
	public String getPlayer1Category();
	
	/**
	 * 
	 * @return the last category selected by player 2
	 */
	public String getPlayer2Category();
	
	public void setPlayer1Category(String category);
	
	public void setPlayer2Category(String category);
	
	/**
	 * 
	 * @return true if the last answer was correct, false otherwise
	 */
	public boolean getPlayer1Answer();
	
	/**
	 * 
	 * @return true if the last answer was correct, false otherwise
	 */
	public boolean getPlayer2Answer();
	
	public void setPlayer1Answer(boolean answerCorrect);
	
	public void setPlayer2Answer(boolean answerCorrect);
	
	/**
	 * 
	 * @return the value of the last question
	 */
	public int getPlayer1Value();
	
	/**
	 * 
	 * @return the value of the last question
	 */
	public int getPlayer2Value();
	
	public void setPlayer1Value(int value);
	
	public void setPlayer2Value(int value);
}
