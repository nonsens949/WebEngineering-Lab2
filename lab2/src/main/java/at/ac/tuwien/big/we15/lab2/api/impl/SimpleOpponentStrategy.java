package at.ac.tuwien.big.we15.lab2.api.impl;

import at.ac.tuwien.big.we15.lab2.api.OpponentStrategy;
import at.ac.tuwien.big.we15.lab2.api.Question;
import at.ac.tuwien.big.we15.lab2.api.QuestionCatalog;

public class SimpleOpponentStrategy implements OpponentStrategy {

	@Override
	public Question nextQuestion(QuestionCatalog catalog, int userScore, int opponentScore, int round) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean answerQuestion(Question question) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
