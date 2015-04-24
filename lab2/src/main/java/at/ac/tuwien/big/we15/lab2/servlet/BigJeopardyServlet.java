package at.ac.tuwien.big.we15.lab2.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import at.ac.tuwien.big.we15.lab2.api.Avatar;
import at.ac.tuwien.big.we15.lab2.api.Category;
import at.ac.tuwien.big.we15.lab2.api.GameState;
import at.ac.tuwien.big.we15.lab2.api.Question;
import at.ac.tuwien.big.we15.lab2.api.QuestionDataProvider;
import at.ac.tuwien.big.we15.lab2.api.User;
import at.ac.tuwien.big.we15.lab2.api.impl.GameStateImpl;
import at.ac.tuwien.big.we15.lab2.api.impl.ServletJeopardyFactory;
import at.ac.tuwien.big.we15.lab2.api.impl.UserImpl;

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
			User user = new UserImpl(req.getParameter("username"), Avatar.getRandomAvatar());
			System.out.println(user.getAvatar().getImageHead());
			s.setAttribute("user", user);
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
			HttpSession session = req.getSession();
			System.out.println("parameternamelist contains answer_submit");
			
			String[] answers = req.getParameterValues("answers");
			ArrayList<Integer> answerList = new ArrayList<Integer>();
			
			ArrayList<String> answerList2 = new ArrayList<String>(Arrays.asList(req.getParameterValues("answers")));
			
			for(String st : answers){
				answerList.add(Integer.parseInt(st));
			}
					
			//TODO geht vielleicht schoener?
			Question currentQuestion = (Question)session.getAttribute("simpleQuestion");
			System.out.println("currentQuestionId = " + currentQuestion.getId());
			System.out.println("currentQuestionText = " + currentQuestion.getText());
			
			List<Answer> correctAnswers = currentQuestion.getCorrectAnswers();
			List<Answer> wrongAnswers = currentQuestion.getAllAnswers();
			wrongAnswers.removeAll(correctAnswers);
			
			//nur fuer debug zwecke!
			for(Answer a : correctAnswers){
				System.out.println("correctanswer = " + a.getText() + " id " + a.getId());
			}
			for(Answer a : wrongAnswers){
				System.out.println("wronganswer = " + a.getText() + " id " + a.getId());
			}
			for(String st : answerList2){
				System.out.println("givenAnswerList2 = " + st);
			}
			for(String st : answers){
				System.out.println("givenAnwswerList = " + st);
			}
			

			boolean answerUser = (answers.length == correctAnswers.size());
			for(Answer a : correctAnswers){
				answerUser = (answerUser && answerList.contains(a.getId()));
			}
			GameState gs = (GameState)session.getAttribute("gameState");
			gs.incrementRoundCounter();
			
			if(answerUser){
				gs.raiseScorePlayer1(currentQuestion.getValue());
			} 
			else {
				gs.reduceScorePlayer1(currentQuestion.getValue());
			}
			
			if(gs.getRoundCounter() <= 10 ){
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
			s.setAttribute("gameState", new GameStateImpl());
			s.setAttribute("simpleQuestion", null);
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jeopardy.jsp");
			dispatcher.forward(req, resp);
		}
	}
	
}
