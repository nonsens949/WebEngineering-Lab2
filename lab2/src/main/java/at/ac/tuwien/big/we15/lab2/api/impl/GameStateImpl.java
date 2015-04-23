package at.ac.tuwien.big.we15.lab2.api.impl;

import at.ac.tuwien.big.we15.lab2.api.GameState;

public class GameStateImpl implements GameState {
	
	int scorePlayer1;
	int scorePlayer2;
	int roundCounter;
	
	public void GameStateImpl(){
		this.scorePlayer1 = 0;
		this.scorePlayer2 = 0;
		this.roundCounter = 0;
	}

	@Override
	public void setScorePlayer1(int score) {
		this.scorePlayer1 = score;
		
	}

	@Override
	public void raiseScorePlayer1(int score) {
		this.scorePlayer1 += score;
		System.out.println("score raised player 1, score = " + this.scorePlayer1);
		
	}
	
	@Override
	public int getScorePlayer1() {
		return this.scorePlayer1;
	}

	@Override
	public void setScorePlayer2(int score) {
		this.scorePlayer2 = score;	
	}

	@Override
	public void raiseScorePlayer2(int score) {
		this.scorePlayer2 += score;
		System.out.println("score raised player 2, score = " + this.scorePlayer2);	
	}

	@Override
	public int getScorePlayer2() {
		return this.scorePlayer2;
	}

	@Override
	public void incrementRoundCounter() {
		this.roundCounter ++;
		
	}

	@Override
	public int getRoundCounter() {
		return this.roundCounter;
	}

	@Override
	public void reduceScorePlayer1(int score) {
		this.scorePlayer1 -= score;		
	}

	@Override
	public void reduceScorePlayer2(int score) {
		this.scorePlayer2 -= score;
	}

}
