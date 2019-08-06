package com.it.erpapp.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.it.erpapp.commons.ApplicationConstants;

/**
 * Servlet implementation class AppControlServlet
 */
@WebServlet("/AppControlServlet")
public class AppControlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AppControlServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String nextpage = ApplicationConstants.JSPFiles.HOME_PAGE.getValue();

		HttpSession session = request.getSession(true);
		session.setAttribute("response", null);

		if (session.getAttribute("response") == null) {
			nextpage = ApplicationConstants.JSPFiles.SESSIONINVALID_MESSAGE_PAGE.getValue();
		}

		RequestDispatcher rd = request.getRequestDispatcher(nextpage);
		rd.forward(request, response);
	}

}
