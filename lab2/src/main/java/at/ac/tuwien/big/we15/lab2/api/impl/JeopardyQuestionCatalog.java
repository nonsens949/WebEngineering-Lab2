package at.ac.tuwien.big.we15.lab2.api.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import at.ac.tuwien.big.we15.lab2.api.Category;
import at.ac.tuwien.big.we15.lab2.api.QuestionCatalog;

public class JeopardyQuestionCatalog implements QuestionCatalog {

	private List<Category> categories;
	private HashSet<Integer> selectedQuestions;
	
	public JeopardyQuestionCatalog() {
		
		categories = new ArrayList<Category>();
		selectedQuestions = new HashSet<Integer>();
	}
	
	public JeopardyQuestionCatalog(List<Category> categories, HashSet<Integer> selectedQuestions) {
		
		this.setCategories(categories);
		this.setSelectedQuestions(selectedQuestions);
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public HashSet<Integer> getSelectedQuestions() {
		return selectedQuestions;
	}

	public void setSelectedQuestions(HashSet<Integer> selectedQuestions) {
		this.selectedQuestions = selectedQuestions;
	}
	
	public void selectQuestion(int questionId) {
		selectedQuestions.add(questionId);
	}
	
	public boolean questionSelected(int questionId) {
		return selectedQuestions.contains(questionId);
	}
}
