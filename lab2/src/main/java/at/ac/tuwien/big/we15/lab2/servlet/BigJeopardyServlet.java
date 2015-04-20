package at.ac.tuwien.big.we15.lab2.servlet;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BigJeopardyServlet extends HttpServlet implements Servlet {

	@Override
	public void init() throws ServletException {
		//Initializations
		String p = getInitParameter("initpara1");
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//called by server (via service method) to allow the servlet to handle a GET request
	}
}
