package at.ac.tuwien.big.we15.lab2.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
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

import at.ac.tuwien.big.we15.lab2.api.Answer;
import at.ac.tuwien.big.we15.lab2.api.Avatar;
import at.ac.tuwien.big.we15.lab2.api.Category;
import at.ac.tuwien.big.we15.lab2.api.GameStatus;
import at.ac.tuwien.big.we15.lab2.api.Question;
import at.ac.tuwien.big.we15.lab2.api.QuestionCatalog;
import at.ac.tuwien.big.we15.lab2.api.QuestionDataProvider;
import at.ac.tuwien.big.we15.lab2.api.User;
import at.ac.tuwien.big.we15.lab2.api.impl.GameStateImpl;
import at.ac.tuwien.big.we15.lab2.api.impl.JeopardyGameStatus;
import at.ac.tuwien.big.we15.lab2.api.impl.JeopardyQuestionCatalog;
import at.ac.tuwien.big.we15.lab2.api.impl.ServletJeopardyFactory;
import at.ac.tuwien.big.we15.lab2.api.impl.UserImpl;

public class BigJeopardyServlet extends HttpServlet implements Servlet {

	@Override
	public void init() throws ServletException {
		super.init();
		String p = getInitParameter("initpara1");
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//called by server (via service method) to allow the servlet to handle a GET request
		Enumeration<String> enumNames = req.getParameterNames();
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		ArrayList<String> parameterNameList = Collections.list(req.getParameterNames());

		//in login.jsp wurde auf login geklickt
		if(parameterNameList.contains("login")){
			HttpSession s = req.getSession();
			User user = new UserImpl(req.getParameter("username"), Avatar.getRandomAvatar());
			Avatar op = Avatar.getRandomAvatar();
			User opponent = new UserImpl(op.getName(), op);
			//GameState gs = new GameStateImpl();
			//gs.setUser(user);
			//s.setAttribute("gameState", gs);
			s.setAttribute("user", user);
			s.setAttribute("opponent", opponent);
			
			GameStatus status = new JeopardyGameStatus();
			s.setAttribute("gameStatus", status);
			
			//Inserted by emil
			ServletContext servletContext = getServletContext();
			ServletJeopardyFactory factory = new ServletJeopardyFactory(servletContext);
			QuestionDataProvider provider = factory.createQuestionDataProvider();
			List<Category> categories = provider.getCategoryData();
			HashSet<Integer> selectedQuestions = new HashSet<Integer>();
			
			s.setAttribute("questionCatalog", new JeopardyQuestionCatalog(categories, selectedQuestions));
			//emil ende
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jeopardy.jsp");
			dispatcher.forward(req, resp);
		}
		//in jeopardy.jsp wurde eine frage ausgewaehlt
		if(parameterNameList.contains("question_submit")){

			HttpSession s = req.getSession();
			int questionId = Integer.parseInt(req.getParameter("question_selection"));
			
			QuestionCatalog catalog = (QuestionCatalog) s.getAttribute("questionCatalog");
			
			List<Category> categories = catalog.getCategories();

			//setzt question auf die frage mit der aktuellen id
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
				
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/question.jsp");
			dispatcher.forward(req, resp);
		}
		//in question.jsp wurde eine antwort gegeben
		if(parameterNameList.contains("answer_submit")){
			HttpSession session = req.getSession();
			
			ArrayList<String> answerList = new ArrayList<String>(Arrays.asList(req.getParameterValues("answers")));

			Question currentQuestion = (Question)session.getAttribute("simpleQuestion");
			
			List<Answer> correctAnswers = currentQuestion.getCorrectAnswers();	

			//wertet die vom user gegebene antwort aus		
			boolean answerUser = answerList.size() == correctAnswers.size();
			for(Answer a : correctAnswers){
				answerUser = (answerUser && answerList.contains(((Integer)a.getId()).toString()));
			}
			/*
			GameState gs = (GameState)session.getAttribute("gameState");
			gs.incrementRoundCounter();
			gs.setLastNeutralChange(gs.getUser().getUsername() + "hat " + currentQuestion.getCategory().getName() + " f�r " + currentQuestion.getValue() + " gew�hlt.");	
			
			if(answerUser){
				gs.raiseScorePlayer1(currentQuestion.getValue());
				gs.setLastPositiveChange("Du hast richtig geantwortet: + " + currentQuestion.getValue() + ".");
			} 
			else {
				gs.reduceScorePlayer1(currentQuestion.getValue());
				gs.setLastNegativeChange("Du hast falsch geantwortet: - " + currentQuestion.getValue() + ".");
			}
			
			if(gs.getRoundCounter() <= 10 ){
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jeopardy.jsp");
				dispatcher.forward(req, resp);
			}
			else{
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/winner.jsp");
				dispatcher.forward(req, resp);
			}*/
			
			GameStatus status = (GameStatus) session.getAttribute("gameStatus");
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
		//in einem jsp wurde auf logout geklickt
		if(parameterNameList.contains("logout")){
			HttpSession s = req.getSession();
			s.setAttribute("user", null);
			s.setAttribute("gameState", null);
			s.setAttribute("simpleQuestion", null);
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
			dispatcher.forward(req, resp);
		}
		//in winner.jsp wurde auf neues spiel geklickt
		if(parameterNameList.contains("restart")){
			HttpSession s = req.getSession();
			s.setAttribute("gameState", new GameStateImpl());
			s.setAttribute("simpleQuestion", null);
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jeopardy.jsp");
			dispatcher.forward(req, resp);
		}
	}
	
}
