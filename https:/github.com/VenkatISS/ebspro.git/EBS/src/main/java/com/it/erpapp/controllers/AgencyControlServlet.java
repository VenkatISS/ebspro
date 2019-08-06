package com.it.erpapp.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.it.erpapp.processor.ProcessorBean;
import com.it.erpapp.processor.RequestResponseProcessorBean;
import com.it.erpapp.response.MessageObject;

/**
 * Servlet implementation class ControlServlet
 */
@WebServlet("/AgencyControlServlet")
public class AgencyControlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AgencyControlServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("jsp/pages/app.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String jspPage = "jsp/pages/" + request.getParameter("page");
		String actionId = request.getParameter("actionId") != null ? request.getParameter("actionId") : "";

		if (actionId.length() > 0) {
			ProcessorBean processorBean = new RequestResponseProcessorBean();
			processorBean.process(request, response);

			if ((actionId.equals("1003"))
					&& (((MessageObject) request.getAttribute("msg_obj")).getMessageStatus().equals("ERROR"))) {
				jspPage = "jsp/pages/app";
			}
			request.setAttribute("NEXTJSP", "/" + jspPage + ".jsp");
		} else {
			jspPage = "jsp/pages/app";
			request.setAttribute("NEXTJSP", "/" + jspPage + ".jsp");
		}

		RequestDispatcher rd = request.getRequestDispatcher((String) request.getAttribute("NEXTJSP"));
		rd.forward(request, response);
	}

}
