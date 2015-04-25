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

	@Override
	public List<Category> getCategories() {
		return categories;
	}

	@Override
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	@Override
	public HashSet<Integer> getSelectedQuestions() {
		return selectedQuestions;
	}

	@Override
	public void setSelectedQuestions(HashSet<Integer> selectedQuestions) {
		this.selectedQuestions = selectedQuestions;
	}
	
	@Override
	public void selectQuestion(int questionId) {
		selectedQuestions.add(questionId);
	}
	
	@Override
	public boolean questionSelected(int questionId) {
		return selectedQuestions.contains(questionId);
	}
}
