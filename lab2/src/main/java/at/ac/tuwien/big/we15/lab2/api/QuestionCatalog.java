package at.ac.tuwien.big.we15.lab2.api;

import java.util.HashSet;
import java.util.List;

/**
 * This class stores the available questions for the use in a jeopardy-game
 * @author Emil Gruber
 *
 */
public interface QuestionCatalog {

	public List<Category> getCategories();

	public void setCategories(List<Category> categories);

	public HashSet<Integer> getSelectedQuestions();

	public void setSelectedQuestions(HashSet<Integer> selectedQuestions);
	
	public void selectQuestion(int questionId);
	
	public boolean questionSelected(int questionId);
}
