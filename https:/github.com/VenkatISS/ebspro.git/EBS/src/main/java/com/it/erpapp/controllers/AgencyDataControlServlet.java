package com.it.erpapp.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.it.erpapp.framework.model.vos.AgencyVO;
import com.it.erpapp.processor.ProcessorBean;
import com.it.erpapp.processor.RequestResponseProcessorBean;

/**
 * Servlet implementation class AgencyDataControlServlet
 */
@WebServlet("/AgencyDataControlServlet")
@MultipartConfig
public class AgencyDataControlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AgencyDataControlServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String jspPage = "jsp/pages/app.jsp";
		String actionId = request.getParameter("actionId") != null ? request.getParameter("actionId") : "";
		if (actionId.length() > 0) {
			ProcessorBean processBean = new RequestResponseProcessorBean();
			processBean.process(request, response);
			
			if(actionId.equals("9011")){
				jspPage = "jsp/pages/updateEmailId.jsp";
			}else {
				jspPage = "jsp/pages/app.jsp";
			}
		}else {
			jspPage = "jsp/pages/app.jsp";
		}
		RequestDispatcher rd = request.getRequestDispatcher(jspPage);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String jspPage = "jsp/pages/app.jsp";
		HttpSession session = request.getSession(true);
		AgencyVO voObj = (AgencyVO) session.getAttribute("agencyVO");
		if ((voObj != null) && (voObj.getAgency_code() > 0)) {
			jspPage = request.getParameter("page");
			String actionId = request.getParameter("actionId") != null ? request.getParameter("actionId") : "";
			if (actionId.length() > 0) {
				ProcessorBean processBean = new RequestResponseProcessorBean();
				processBean.process(request, response);
				
				if(actionId.equals("9011")){
					jspPage = "jsp/pages/updateEmailId.jsp";
				}
			} else {
				jspPage = "jsp/pages/app.jsp";
			}
		}
		RequestDispatcher rd = request.getRequestDispatcher(jspPage);
		rd.forward(request, response);
	}

}
