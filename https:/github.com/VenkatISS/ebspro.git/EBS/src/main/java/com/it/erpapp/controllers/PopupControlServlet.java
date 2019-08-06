package com.it.erpapp.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.it.erpapp.framework.model.vos.AgencyVO;
import com.it.erpapp.processor.MasterDataRequestResponseProcessorBean;
import com.it.erpapp.processor.ProcessorBean;

/**
 * Servlet implementation class ControlServlet
 */
@WebServlet("/PopupControlServlet")
public class PopupControlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PopupControlServlet() {
		super();
		// TODO Auto-generated constructor stub
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

		String jspPage = "jsp/pages/app.jsp";
		HttpSession session = request.getSession(true);
		AgencyVO voObj = (AgencyVO) session.getAttribute("agencyVO");
		int st = Integer.parseInt(request.getParameter("sitype"));
		if ((voObj != null) && (voObj.getAgency_code() > 0)) {
			jspPage = request.getParameter("page");
			String actionId = request.getParameter("actionId") != null ? request.getParameter("actionId") : "";
			if (actionId.length() > 0) {
				ProcessorBean processBean = new MasterDataRequestResponseProcessorBean();
				processBean.process(request, response);

				switch (st) {
				case 1:
					jspPage = "jsp/pages/erp/popups/dom_sale.jsp";
					break;
				case 2:
					jspPage = "jsp/pages/erp/popups/com_sale.jsp";
					break;
				case 3:
					jspPage = "jsp/pages/erp/popups/arb_sales_popup.jsp";
					break;
				case 4:
					jspPage = "jsp/pages/erp/popups/quotations_popup.jsp";
					break;
				case 5:
					jspPage = "jsp/pages/erp/popups/delivery_challen_popup.jsp";
					break;
				case 6:
					jspPage = "jsp/pages/erp/popups/purchases_return_popup.jsp";
					break;
				case 7:
					jspPage = "jsp/pages/erp/popups/ncdbc_popup.jsp";
					break;
				case 8:
					jspPage = "jsp/pages/erp/popups/rc_popup.jsp";
					break;
				case 9:
					jspPage = "jsp/pages/erp/popups/sales_return_popup.jsp";
					break;
				case 10:
					jspPage = "jsp/pages/erp/popups/credit_note_popup.jsp";
					break;
				case 11:
					jspPage = "jsp/pages/erp/popups/debit_note_popup.jsp";
					break;
				case 12:
					jspPage = "jsp/pages/erp/popups/tv_popup.jsp";
					break;
				case 13:
					jspPage = "jsp/pages/erp/popups/payments_popup.jsp";
					break;
				case 14:
					jspPage = "jsp/pages/erp/popups/receipts_popup.jsp";
					break;
				default:
					jspPage = "jsp/pages/app.jsp";
					break;
				}

			}
		}
		RequestDispatcher rd = request.getRequestDispatcher(jspPage);
		rd.forward(request, response);
	}

}
