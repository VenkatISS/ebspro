package com.it.erpapp.appservices;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.it.erpapp.commons.ApplicationConstants;
import com.it.erpapp.framework.AgencyFactory;
import com.it.erpapp.framework.CacheFactory;
import com.it.erpapp.framework.MasterDataFactory;
import com.it.erpapp.framework.PPMasterDataFactory;
import com.it.erpapp.framework.TransactionsDataFactory;
import com.it.erpapp.framework.model.vos.AgencyVO;
import com.it.erpapp.response.MessageObject;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class ShowPagesServiceBean {

	Logger logger = LoggerFactory.getLogger(ShowPagesServiceBean.class);
	long agencyId;
	long invId;

	public void showHome(HttpServletRequest request, HttpServletResponse response) {

	}

	public void showMasterData(HttpServletRequest request, HttpServletResponse response) {

	}

	public void showTransactions(HttpServletRequest request, HttpServletResponse response) {

	}

	public void showReports(HttpServletRequest request, HttpServletResponse response) {

	}

	public void showProfile(HttpServletRequest request, HttpServletResponse response) {

	}

	public void showDOMSalesInvoicePopup(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(999, "FETCH DOMESTIC SALES INVOICES DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			invId = Long.parseLong(request.getParameter("sid"));
			Long monthYYYY = Long.parseLong(request.getParameter("si_date"));
			Date date = new Date(monthYYYY);
			SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy");
			String dateText = sdf.format(date);

			request.setAttribute("dateyear", dateText);
			logger.info(ApplicationConstants.LogMessageKeys.SHOWDOMSALESINVOICEPOPUP.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.INVOICEID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, invId);

			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("bank_data", (new Gson().toJson(mdf.getAgencyBankData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(agencyId))));
			request.setAttribute("staff_data", (new Gson().toJson(mdf.getAgencyStaffData(agencyId))));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(ppmdf.getAgencyAllEquipmentData(agencyId))));
			request.setAttribute("refill_prices_data", (new Gson().toJson(ppmdf.getAgencyRefillPriceData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("area_codes_data", (new Gson().toJson(ppmdf.getAgencyAreaCodeData(agencyId))));
			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("drs_data", (new Gson().toJson(factory.getAgencyDOMSalesInvoicesById(invId))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWDOMSALESINVOICEPOPUP.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.INVOICEID.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, invId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SHOWDOMSALESINVOICEPOPUP.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.INVOICEID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, invId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void showCOMSalesInvoicePopup(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(998, "FETCH COMMERCIAL SALES INVOICES DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			invId = Long.parseLong(request.getParameter("sid"));
			Long monthYYYY = Long.parseLong(request.getParameter("si_date"));
			Date date = new Date(monthYYYY);
			SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy");
			String dateText = sdf.format(date);

			request.setAttribute("dateyear", dateText);
			logger.info(ApplicationConstants.LogMessageKeys.SHOWCOMSALESINVOICEPOPUP.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.INVOICEID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, invId);

			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("bank_data", (new Gson().toJson(mdf.getAgencyBankData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(agencyId))));
			request.setAttribute("staff_data", (new Gson().toJson(mdf.getAgencyStaffData(agencyId))));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(ppmdf.getAgencyAllEquipmentData(agencyId))));
			request.setAttribute("refill_prices_data", (new Gson().toJson(ppmdf.getAgencyRefillPriceData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("area_codes_data", (new Gson().toJson(ppmdf.getAgencyAreaCodeData(agencyId))));
			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("crs_data", (new Gson().toJson(factory.getAgencyCOMSalesInvoicesById(invId))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWCOMSALESINVOICEPOPUP.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.INVOICEID.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, invId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SHOWCOMSALESINVOICEPOPUP.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.INVOICEID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, invId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void showARBSalesInvoicePopup(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(998, "FETCH COMMERCIAL SALES INVOICES DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			invId = Long.parseLong(request.getParameter("sid"));
			Long monthYYYY = Long.parseLong(request.getParameter("si_date"));
			Date date = new Date(monthYYYY);
			SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy");
			String dateText = sdf.format(date);

			request.setAttribute("dateyear", dateText);

			logger.info(ApplicationConstants.LogMessageKeys.SHOWARBSALESINVOICEPOPUP.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.INVOICEID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, invId);

			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			request.setAttribute("arb_data",
					(new Gson().toJson((new PPMasterDataFactory()).getAgencyAllARBData(agencyId))));
			request.setAttribute("arb_types_list", (new Gson().toJson((new CacheFactory()).getARBTypesData())));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("bank_data", (new Gson().toJson(mdf.getAgencyBankData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(agencyId))));
			request.setAttribute("staff_data", (new Gson().toJson(mdf.getAgencyStaffData(agencyId))));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(ppmdf.getAgencyEquipmentData(agencyId))));
			request.setAttribute("refill_prices_data", (new Gson().toJson(ppmdf.getAgencyRefillPriceData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("area_codes_data", (new Gson().toJson(ppmdf.getAgencyAllAreaCodeData(agencyId))));
			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("arbs_data", (new Gson().toJson(factory.getAgencyARBSalesInvoicesById(invId))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWARBSALESINVOICEPOPUP.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.INVOICEID.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, invId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SHOWARBSALESINVOICEPOPUP.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.INVOICEID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, invId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void showQuotationsPopup(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(998, "FETCH COMMERCIAL SALES INVOICES DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			invId = Long.parseLong(request.getParameter("sid"));
			Long monthYYYY = Long.parseLong(request.getParameter("si_date"));
			Date date = new Date(monthYYYY);
			SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy");
			String dateText = sdf.format(date);

			request.setAttribute("dateyear", dateText);

			logger.info(ApplicationConstants.LogMessageKeys.SHOWQUOTATIONSPOPUP.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.INVOICEID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, invId);

			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("bank_data", (new Gson().toJson(mdf.getAgencyBankData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(agencyId))));
			request.setAttribute("staff_data", (new Gson().toJson(mdf.getAgencyStaffData(agencyId))));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(ppmdf.getAgencyAllEquipmentData(agencyId))));
			request.setAttribute("refill_prices_data", (new Gson().toJson(ppmdf.getAgencyRefillPriceData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("area_codes_data", (new Gson().toJson(ppmdf.getAgencyAllAreaCodeData(agencyId))));
			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("qts_data", (new Gson().toJson(factory.getQuotationsById(invId))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWQUOTATIONSPOPUP.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.INVOICEID.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, invId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SHOWQUOTATIONSPOPUP.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.INVOICEID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, invId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void showDeliveryChallenPopup(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(998, "FETCH COMMERCIAL SALES INVOICES DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			invId = Long.parseLong(request.getParameter("sid"));
			Long monthYYYY = Long.parseLong(request.getParameter("si_date"));
			Date date = new Date(monthYYYY);
			SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy");
			String dateText = sdf.format(date);

			request.setAttribute("dateyear", dateText);

			logger.info(ApplicationConstants.LogMessageKeys.SHOWDELIVERYCHALLENPOPUP.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.INVOICEID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, invId);

			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("bank_data", (new Gson().toJson(mdf.getAgencyBankData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(agencyId))));
			request.setAttribute("staff_data", (new Gson().toJson(mdf.getAgencyStaffData(agencyId))));
			request.setAttribute("fleet_data", (new Gson().toJson(mdf.getAgencyAllFleetData(agencyId))));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(ppmdf.getAgencyAllEquipmentData(agencyId))));
			request.setAttribute("refill_prices_data", (new Gson().toJson(ppmdf.getAgencyRefillPriceData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("area_codes_data", (new Gson().toJson(ppmdf.getAgencyAllAreaCodeData(agencyId))));
			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("dcs_data", (new Gson().toJson(factory.getDeliveryChallenById(invId))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWDELIVERYCHALLENPOPUP.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.INVOICEID.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, invId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SHOWDELIVERYCHALLENPOPUP.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.INVOICEID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, invId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void showPurchaseReturnPopup(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(998, "FETCH COMMERCIAL SALES INVOICES DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			invId = Long.parseLong(request.getParameter("sid"));
			Long monthYYYY = Long.parseLong(request.getParameter("si_date"));
			Date date = new Date(monthYYYY);
			SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy");
			String dateText = sdf.format(date);

			request.setAttribute("dateyear", dateText);

			logger.info(ApplicationConstants.LogMessageKeys.SHOWPURCHASERETURNPOPUP.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.INVOICEID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, invId);

			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("bank_data", (new Gson().toJson(mdf.getAgencyBankData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(agencyId))));
			request.setAttribute("staff_data", (new Gson().toJson(mdf.getAgencyStaffData(agencyId))));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(ppmdf.getAgencyAllEquipmentData(agencyId))));
			request.setAttribute("arb_data", (new Gson().toJson(ppmdf.getAgencyAllARBData(agencyId))));
			request.setAttribute("refill_prices_data", (new Gson().toJson(ppmdf.getAgencyRefillPriceData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("area_codes_data", (new Gson().toJson(ppmdf.getAgencyAllAreaCodeData(agencyId))));
			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("prs_data", (new Gson().toJson(factory.getAgencyPurchaseReturnById(invId))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWPURCHASERETURNPOPUP.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.INVOICEID.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, invId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SHOWPURCHASERETURNPOPUP.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.INVOICEID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, invId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void showNCDBCPopup(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(998, "FETCH COMMERCIAL SALES INVOICES DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession().getAttribute("agencyVO")).getAgency_code();
			invId = Long.parseLong(request.getParameter("sid"));
			Long monthYYYY = Long.parseLong(request.getParameter("si_date"));
			Date date = new Date(monthYYYY);
			SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy");
			String dateText = sdf.format(date);

			request.setAttribute("dateyear", dateText);

			logger.info(ApplicationConstants.LogMessageKeys.SHOWNCDBCPOPUP.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.INVOICEID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, invId);

			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			request.setAttribute("prod_types_list", (new Gson().toJson(new CacheFactory().getProductCatogoryData())));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("bank_data", (new Gson().toJson(mdf.getAgencyBankData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(agencyId))));
			request.setAttribute("staff_data", (new Gson().toJson(mdf.getAgencyStaffData(agencyId))));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(ppmdf.getAgencyAllEquipmentData(agencyId))));
			request.setAttribute("arb_data", (new Gson().toJson(ppmdf.getAgencyAllARBData(agencyId))));
			request.setAttribute("services_data", (new Gson().toJson(ppmdf.getAgencyServicesData(agencyId))));
			request.setAttribute("refill_prices_data", (new Gson().toJson(ppmdf.getAgencyRefillPriceData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("area_codes_data", (new Gson().toJson(ppmdf.getAgencyAllAreaCodeData(agencyId))));
			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("ncdbcs_data", (new Gson().toJson(factory.getAgencyNCDBCById(invId))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWNCDBCPOPUP.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.INVOICEID.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, invId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SHOWNCDBCPOPUP.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.INVOICEID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, invId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void showRCPopup(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(998, "FETCH COMMERCIAL SALES INVOICES DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession().getAttribute("agencyVO")).getAgency_code();
			invId = Long.parseLong(request.getParameter("sid"));
			Long monthYYYY = Long.parseLong(request.getParameter("si_date"));
			Date date = new Date(monthYYYY);
			SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy");
			String dateText = sdf.format(date);

			request.setAttribute("dateyear", dateText);

			logger.info(ApplicationConstants.LogMessageKeys.SHOWRCPOPUP.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.INVOICEID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, invId);

			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			request.setAttribute("prod_types_list", (new Gson().toJson(new CacheFactory().getProductCatogoryData())));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("bank_data", (new Gson().toJson(mdf
					.getAgencyBankData(((AgencyVO) request.getSession().getAttribute("agencyVO")).getAgency_code()))));
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(agencyId))));
			request.setAttribute("staff_data", (new Gson().toJson(mdf.getAgencyStaffData(agencyId))));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(ppmdf.getAgencyAllEquipmentData(agencyId))));
			request.setAttribute("arb_data", (new Gson().toJson(ppmdf.getAgencyAllARBData(agencyId))));
			request.setAttribute("refill_prices_data", (new Gson().toJson(ppmdf.getAgencyRefillPriceData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("area_codes_data", (new Gson().toJson(ppmdf.getAgencyAllAreaCodeData(agencyId))));
			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("rcs_data", (new Gson().toJson(factory.getAgencyRCById(invId))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWRCPOPUP.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.INVOICEID.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, invId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SHOWRCPOPUP.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.INVOICEID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, invId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	// showTVPopup

	public void showTVPopup(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(998, "FETCH COMMERCIAL SALES INVOICES DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			invId = Long.parseLong(request.getParameter("sid"));
			Long monthYYYY = Long.parseLong(request.getParameter("si_date"));
			Date date = new Date(monthYYYY);
			SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy");
			String dateText = sdf.format(date);

			request.setAttribute("dateyear", dateText);

			logger.info(ApplicationConstants.LogMessageKeys.SHOWTVPOPUP.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.INVOICEID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, invId);

			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			request.setAttribute("prod_types_list", (new Gson().toJson(new CacheFactory().getProductCatogoryData())));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("bank_data", (new Gson().toJson(mdf.getAgencyBankData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(agencyId))));
			request.setAttribute("staff_data", (new Gson().toJson(mdf.getAgencyStaffData(agencyId))));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(ppmdf.getAgencyAllEquipmentData(agencyId))));
			request.setAttribute("arb_data", (new Gson().toJson(ppmdf.getAgencyAllARBData(agencyId))));
			request.setAttribute("refill_prices_data", (new Gson().toJson(ppmdf.getAgencyRefillPriceData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("area_codes_data", (new Gson().toJson(ppmdf.getAgencyAllAreaCodeData(agencyId))));
			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("tvs_data", (new Gson().toJson(factory.getAgencyTVById(invId))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWTVPOPUP.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.INVOICEID.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, invId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SHOWTVPOPUP.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.INVOICEID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, invId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void showCreditNotePopup(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(998, "FETCH COMMERCIAL SALES INVOICES DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			invId = Long.parseLong(request.getParameter("sid"));
			Long monthYYYY = Long.parseLong(request.getParameter("si_date"));
			Date date = new Date(monthYYYY);
			SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy");
			String dateText = sdf.format(date);

			request.setAttribute("dateyear", dateText);

			logger.info(ApplicationConstants.LogMessageKeys.SHOWCREDITNOTEPOPUP.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.INVOICEID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, invId);

			request.setAttribute("prod_types_list", (new Gson().toJson(new CacheFactory().getProductCatogoryData())));
			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("bank_data", (new Gson().toJson(mdf.getAgencyBankData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(agencyId))));
			request.setAttribute("staff_data", (new Gson().toJson(mdf.getAgencyStaffData(agencyId))));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(ppmdf.getAgencyAllEquipmentData(agencyId))));
			request.setAttribute("arb_data", (new Gson().toJson(ppmdf.getAgencyAllARBData(agencyId))));
			request.setAttribute("refill_prices_data", (new Gson().toJson(ppmdf.getAgencyRefillPriceData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("area_codes_data", (new Gson().toJson(ppmdf.getAgencyAllAreaCodeData(agencyId))));
			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("cnp_data", (new Gson().toJson(factory.getAgencyCreditNoteById(invId))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWCREDITNOTEPOPUP.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.INVOICEID.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, invId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SHOWCREDITNOTEPOPUP.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.INVOICEID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, invId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void showDebitNotePopup(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(998, "FETCH COMMERCIAL SALES INVOICES DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			invId = Long.parseLong(request.getParameter("sid"));
			Long monthYYYY = Long.parseLong(request.getParameter("si_date"));
			Date date = new Date(monthYYYY);
			SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy");
			String dateText = sdf.format(date);

			request.setAttribute("dateyear", dateText);

			logger.info(ApplicationConstants.LogMessageKeys.SHOWDEBITNOTEPOPUP.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.INVOICEID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, invId);

			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			request.setAttribute("prod_types_list", (new Gson().toJson(new CacheFactory().getProductCatogoryData())));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("bank_data", (new Gson().toJson(mdf.getAgencyBankData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(agencyId))));
			request.setAttribute("staff_data", (new Gson().toJson(mdf.getAgencyStaffData(agencyId))));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(ppmdf.getAgencyAllEquipmentData(agencyId))));
			request.setAttribute("arb_data", (new Gson().toJson(ppmdf.getAgencyAllARBData(agencyId))));
			request.setAttribute("refill_prices_data", (new Gson().toJson(ppmdf.getAgencyRefillPriceData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("area_codes_data", (new Gson().toJson(ppmdf.getAgencyAllAreaCodeData(agencyId))));
			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("dnp_data", (new Gson().toJson(factory.getAgencyDebitNoteById(invId))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWDEBITNOTEPOPUP.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.INVOICEID.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, invId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SHOWDEBITNOTEPOPUP.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.INVOICEID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, invId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}
	
	//payment
	public void showPaymentsInvoicePopup(HttpServletRequest request,
			HttpServletResponse response){
		MessageObject msgObj = new MessageObject(5511, "FETCH PAYMENTS DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO)request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			
			invId = Long.parseLong(request.getParameter("sid"));
			Long monthYYYY = Long.parseLong(request.getParameter("si_date"));
			Date date = new Date(monthYYYY);
			SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy");
			String dateText = sdf.format(date);
			request.setAttribute("dateyear", dateText);

			logger.info(ApplicationConstants.LogMessageKeys.FETCHPAYMENTSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId);

			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("staff_data", new Gson().toJson(mdf.getAgencyAllStaffData(agencyId)));
			request.setAttribute("bank_data", new Gson().toJson(mdf.getAgencyAllBankData(agencyId)));
			request.setAttribute("cvo_data", new Gson().toJson(mdf.getAgencyAllCVOData(agencyId)));
			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("payments_data", (new Gson().toJson(factory.getAgencyPaymentsDataById(invId))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.FETCHPAYMENTSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.STATUS.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,ApplicationConstants.SUCCESS_STATUS_STRING);

			HttpSession session = request.getSession(true);
            AgencyFactory af = new AgencyFactory();
            session.setAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue(),af.fetchAgencyVO(agencyId));
		}catch(BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.FETCHPAYMENTSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,be);
		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

//receipts
	
	
	
	public void showReceiptsInvoicePopup(HttpServletRequest request,
			HttpServletResponse response){
		MessageObject msgObj = new MessageObject(5511, "FETCH RECEIPTS DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO)request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			
			invId = Long.parseLong(request.getParameter("sid"));
			Long monthYYYY = Long.parseLong(request.getParameter("si_date"));
			Date date = new Date(monthYYYY);
			SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy");
			String dateText = sdf.format(date);
			request.setAttribute("dateyear", dateText);

			logger.info(ApplicationConstants.LogMessageKeys.FETCHPAYMENTSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId);

			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("staff_data", new Gson().toJson(mdf.getAgencyAllStaffData(agencyId)));
			request.setAttribute("bank_data", new Gson().toJson(mdf.getAgencyAllBankData(agencyId)));
			request.setAttribute("cvo_data", new Gson().toJson(mdf.getAgencyAllCVOData(agencyId)));
			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("receipts_data", (new Gson().toJson(factory.getAgencyReceiptsDataById(invId))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.FETCHPAYMENTSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.STATUS.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,ApplicationConstants.SUCCESS_STATUS_STRING);

			HttpSession session = request.getSession(true);
            AgencyFactory af = new AgencyFactory();
            session.setAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue(),af.fetchAgencyVO(agencyId));
		}catch(BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.FETCHPAYMENTSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,be);
		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}
}
