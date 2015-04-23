package at.ac.tuwien.big.we15.lab2.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
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
import at.ac.tuwien.big.we15.lab2.api.Category;
import at.ac.tuwien.big.we15.lab2.api.GameState;
import at.ac.tuwien.big.we15.lab2.api.Question;
import at.ac.tuwien.big.we15.lab2.api.QuestionDataProvider;
import at.ac.tuwien.big.we15.lab2.api.impl.GameStateImpl;
import at.ac.tuwien.big.we15.lab2.api.impl.ServletJeopardyFactory;

public class BigJeopardyServlet extends HttpServlet implements Servlet {

	@Override
	public void init() throws ServletException {
		//Initializations
		super.init();
		System.out.println("init servlet");
		String p = getInitParameter("initpara1");
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//called by server (via service method) to allow the servlet to handle a GET request
		System.out.println("doGet in servlet");
		Enumeration<String> enumNames = req.getParameterNames();
		while(enumNames.hasMoreElements()){
			System.out.println(enumNames.nextElement());
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//TODO
		System.out.println("doPost in servlet");
		Enumeration<String> enumNames = req.getParameterNames();
		ArrayList<String> parameterNameList = new ArrayList<String>();
		while(enumNames.hasMoreElements()){
			parameterNameList.add(enumNames.nextElement());
		}
		for(String s : parameterNameList){
			System.out.println("parameternamelist : " + s);
		}
		if(parameterNameList.contains("login")){
			System.out.println("input name= login ");
			//INPUT BEHANDLUNG BEI LOGIN BEI DEM BEISIPEL NOCH NICHT BENOETIGT
			HttpSession s = req.getSession();
			s.setAttribute("gameState", new GameStateImpl());
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jeopardy.jsp");
			dispatcher.forward(req, resp);
		}
		//TODO in doGet() verschieben was in doGet() gehoert
		if(parameterNameList.contains("question_submit")){
			System.out.println("parameterNameList contains question_submit");
			HttpSession s = req.getSession();
			int questionId = Integer.parseInt(req.getParameter("question_selection"));
			// ServletContext coming from javax.servlet.GenericServlet or subclass
			ServletContext servletContext = getServletContext();
			ServletJeopardyFactory factory = new ServletJeopardyFactory(servletContext);
			QuestionDataProvider provider = factory.createQuestionDataProvider();
			List<Category> categories = provider.getCategoryData();
			// category has name and holds questions
			// questions have attributes and answers
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
			
			
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/question.jsp");
			dispatcher.forward(req, resp);
		}
		//TODO auswertung von gegebener antwort und gutrechnung auf punktekonto?
		if(parameterNameList.contains("answer_submit")){
			HttpSession s = req.getSession();
			System.out.println("parameternamelist contains answer_submit");
			
			String[] answers = req.getParameterValues("answers");
			ArrayList<Integer> answerList = new ArrayList<Integer>();
			for(String st : answers){
				answerList.add(Integer.parseInt(st));
			}
					
			//TODO geht vielleicht schoener?
			Question currentQuestion = (Question)s.getAttribute("simpleQuestion");
			System.out.println("currentQuestionId = " + currentQuestion.getId());
			System.out.println("currentQuestionText = " + currentQuestion.getText());
			
			List<Answer> correctAnswers = currentQuestion.getCorrectAnswers();
			List<Answer> wrongAnswers = currentQuestion.getAllAnswers();
			wrongAnswers.removeAll(correctAnswers);
			boolean answerUser = (answers.length == correctAnswers.size());
			for(Answer a : correctAnswers){
				answerUser = (answerUser && answerList.contains(a.getId()));
			}
			GameState gs = (GameState)s.getAttribute("gameState");
			
			if(answerUser){
				gs.raiseScorePlayer1(currentQuestion.getValue());
			} 
			else {
				gs.reduceScorePlayer1(currentQuestion.getValue());
			}
			gs.incrementRoundCounter();
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jeopardy.jsp");
			dispatcher.forward(req, resp);
		}
		
	}
	
}
