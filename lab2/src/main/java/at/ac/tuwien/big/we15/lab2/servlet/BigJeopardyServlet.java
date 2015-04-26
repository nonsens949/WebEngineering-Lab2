package at.ac.tuwien.big.we15.lab2.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import at.ac.tuwien.big.we15.lab2.api.*;
import at.ac.tuwien.big.we15.lab2.api.impl.*;

public class BigJeopardyServlet extends HttpServlet implements Servlet {

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		ArrayList<String> parameterNameList = Collections.list(req.getParameterNames());
		
		if(parameterNameList.contains("login")){
			HttpSession s = req.getSession();
			User user = new UserImpl(req.getParameter("username"), Avatar.getRandomAvatar());
			Avatar op = Avatar.getRandomAvatar();
			User opponent = new UserImpl(op.getName(), op);
			
			s.setAttribute("user", user);
			s.setAttribute("opponent", opponent);
			
			GameStatus status = new JeopardyGameStatus();
			s.setAttribute("gameStatus", status);
			OpponentStrategy strategy = new SimpleOpponentStrategy();
			s.setAttribute("strategy", strategy);
			
			ServletContext servletContext = getServletContext();
			ServletJeopardyFactory factory = new ServletJeopardyFactory(servletContext);
			QuestionDataProvider provider = factory.createQuestionDataProvider();
			List<Category> categories = provider.getCategoryData();
			HashSet<Integer> selectedQuestions = new HashSet<Integer>();
			
			s.setAttribute("questionCatalog", new JeopardyQuestionCatalog(categories, selectedQuestions));
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jeopardy.jsp");
			dispatcher.forward(req, resp);
		}
		
		if(parameterNameList.contains("question_submit")){

			HttpSession s = req.getSession();
			int questionId = Integer.parseInt(req.getParameter("question_selection"));
			
			QuestionCatalog catalog = (QuestionCatalog) s.getAttribute("questionCatalog");
			
			List<Category> categories = catalog.getCategories();

			Question question = null;
			for(Category c : categories){
				List<Question> questionList = c.getQuestions();
				for(Question q : questionList){
					if(q.getId() == questionId){
						question = q;
					}
				}
			}
			s.setAttribute("simpleQuestion", question);
			
			catalog.selectQuestion(questionId);
			
			OpponentStrategy strategy = (OpponentStrategy) s.getAttribute("strategy");
			GameStatus status = (GameStatus) s.getAttribute("gameStatus");
			
			if (catalog.getSelectedQuestions().size()%2 == 1) {
				
				Question kiQuestion = strategy.nextQuestion(catalog, status);
				
				catalog.selectQuestion(kiQuestion.getId());
				status.setPlayer2Value(kiQuestion.getValue());
				status.setPlayer2Category(kiQuestion.getCategory().getName());
				boolean answerKi = strategy.answerQuestion(kiQuestion);
				
				status.setPlayer2Answer(answerKi);
				
				if (answerKi) {
					status.setPlayer2Score(status.getPlayer2Score() + kiQuestion.getValue());
					
				} else {
					status.setPlayer2Score(status.getPlayer2Score() - kiQuestion.getValue());
				}
			}
			
			if (status.getPlayer1Score() > status.getPlayer2Score()) {
				
				Question kiQuestion = strategy.nextQuestion(catalog, status);
				
				catalog.selectQuestion(kiQuestion.getId());
				status.setPlayer2Value(kiQuestion.getValue());
				status.setPlayer2Category(kiQuestion.getCategory().getName());
				boolean answerKi = strategy.answerQuestion(kiQuestion);
				
				status.setPlayer2Answer(answerKi);
				
				if (answerKi) {
					
					status.setPlayer2Score(status.getPlayer2Score() + kiQuestion.getValue());
					
				} else {
					
					status.setPlayer2Score(status.getPlayer2Score() - kiQuestion.getValue());
				}
			}
				
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/question.jsp");
			dispatcher.forward(req, resp);
		}
		
		if(parameterNameList.contains("answers")||parameterNameList.isEmpty()){
			
			HttpSession session = req.getSession();
			
			GameStatus status = (GameStatus) session.getAttribute("gameStatus");
			
			String [] paramVals = req.getParameterValues("answers");
			ArrayList<String> answerList = new ArrayList<String>();
			
			if (paramVals != null) {
				
				answerList = new ArrayList<String>(Arrays.asList(paramVals));
			}
			
			Question currentQuestion = (Question)session.getAttribute("simpleQuestion");
			
			List<Answer> correctAnswers = currentQuestion.getCorrectAnswers();	

			boolean answerUser = answerList.size() == correctAnswers.size();
			for(Answer a : correctAnswers){
				answerUser = (answerUser && answerList.contains(((Integer)a.getId()).toString()));
			}
			
			status.incrementRound();
			status.setPlayer1Value(currentQuestion.getValue());
			status.setPlayer1Answer(answerUser);
			status.setPlayer1Category(currentQuestion.getCategory().getName());
			
			if (answerUser) {
				
				status.setPlayer1Score(status.getPlayer1Score() + currentQuestion.getValue());
				
			} else {
				
				status.setPlayer1Score(status.getPlayer1Score() - currentQuestion.getValue());
			}
			
			if(status.getRound() <= 10 ){
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jeopardy.jsp");
				dispatcher.forward(req, resp);
			}
			else{
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/winner.jsp");
				dispatcher.forward(req, resp);
			}
			
		}
		
		if(parameterNameList.contains("logout")){
			HttpSession s = req.getSession();
			s.setAttribute("user", null);
			s.setAttribute("gameState", null);
			s.setAttribute("simpleQuestion", null);
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
			dispatcher.forward(req, resp);
		}
		
		if(parameterNameList.contains("restart")){
			HttpSession s = req.getSession();
			s.setAttribute("simpleQuestion", null);
			
			GameStatus status = new JeopardyGameStatus();
			s.setAttribute("gameStatus", status);
			QuestionCatalog catalog = (QuestionCatalog) s.getAttribute("questionCatalog");
			catalog.getSelectedQuestions().clear();
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jeopardy.jsp");
			dispatcher.forward(req, resp);
		}
	}
	
}
