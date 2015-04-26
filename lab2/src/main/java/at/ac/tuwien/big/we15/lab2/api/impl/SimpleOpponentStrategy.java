package at.ac.tuwien.big.we15.lab2.api.impl;

import java.util.Collections;
import java.util.List;

import at.ac.tuwien.big.we15.lab2.api.GameStatus;
import at.ac.tuwien.big.we15.lab2.api.OpponentStrategy;
import at.ac.tuwien.big.we15.lab2.api.Question;
import at.ac.tuwien.big.we15.lab2.api.QuestionCatalog;

public class SimpleOpponentStrategy implements OpponentStrategy {

	@Override
	public Question nextQuestion(QuestionCatalog catalog, GameStatus status) {
		List<Question> unselectedQuestions = catalog.getUnselectedQuestions();
		Collections.shuffle(unselectedQuestions);
		return unselectedQuestions.get(0);
	}

	@Override
	public boolean answerQuestion(Question question) {
		float result = 1f/question.getValue();
		double random = Math.random();
		return result <= random;
	}
	
	

}
