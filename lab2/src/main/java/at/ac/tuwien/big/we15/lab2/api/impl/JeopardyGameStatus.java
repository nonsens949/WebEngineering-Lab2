package at.ac.tuwien.big.we15.lab2.api.impl;

import at.ac.tuwien.big.we15.lab2.api.GameStatus;

public class JeopardyGameStatus implements GameStatus {

	private int player1Score;
	private int player2Score;
	private int round;
	private String player1Category;
	private String player2Category;
	private boolean player1Answer;
	private boolean player2Answer;
	private int player1Value;
	private int player2Value;

	public JeopardyGameStatus() {
		player1Score = 0;
		player2Score = 0;
		round = 1;
		player1Category = "noch nichts";
		player2Category = "noch nichts";
		player1Answer = true;
		player2Answer = true;
		player1Value = 0;
		player2Value = 0;
	}
	
	
	@Override
	public int getPlayer1Score() {
		return player1Score;
	}

	@Override
	public int getPlayer2Score() {
		return player2Score;
	}

	@Override
	public void setPlayer1Score(int score) {
		player1Score = score;
	}

	@Override
	public void setPlayer2Score(int score) {
		player2Score = score;
	}

	@Override
	public int getRound() {
		return round;
	}

	@Override
	public void setRound(int round) {
		this.round = round;
	}
	
	@Override
	public void incrementRound() {
		round++;
	}

	@Override
	public String getPlayer1Category() {
		return player1Category;
	}

	@Override
	public String getPlayer2Category() {
		return player2Category;
	}

	@Override
	public boolean getPlayer1Answer() {
		return player1Answer;
	}

	@Override
	public boolean getPlayer2Answer() {
		return player2Answer;
	}

	@Override
	public int getPlayer1Value() {
		return player1Value;
	}

	@Override
	public int getPlayer2Value() {
		return player2Value;
	}

	@Override
	public void setPlayer1Category(String category) {
		player1Category = category;
	}

	@Override
	public void setPlayer2Category(String category) {
		player2Category = category;
	}

	@Override
	public void setPlayer1Answer(boolean answerCorrect) {
		player1Answer = answerCorrect;
	}

	@Override
	public void setPlayer2Answer(boolean answerCorrect) {
		player2Answer = answerCorrect;
	}

	@Override
	public void setPlayer1Value(int value) {
		player1Value = value;
	}

	@Override
	public void setPlayer2Value(int value) {
		player2Value = value;
	}

	
}
