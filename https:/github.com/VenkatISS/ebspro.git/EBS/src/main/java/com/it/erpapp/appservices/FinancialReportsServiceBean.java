package com.it.erpapp.appservices;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.it.erpapp.commons.ApplicationConstants;
import com.it.erpapp.framework.FinancialReportsFactory;
import com.it.erpapp.framework.MasterDataFactory;
import com.it.erpapp.framework.model.finreports.BalanceSheetDO;
import com.it.erpapp.framework.model.finreports.CapitalAccountReportDO;
import com.it.erpapp.framework.model.finreports.DepreciationDO;
import com.it.erpapp.framework.model.finreports.DepreciationDataDO;
import com.it.erpapp.framework.model.finreports.PAndLAccountDO;
import com.it.erpapp.framework.model.vos.AgencyVO;
import com.it.erpapp.response.MessageObject;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class FinancialReportsServiceBean {

	Logger logger = LoggerFactory.getLogger(FinancialReportsServiceBean.class);
	long agencyId;
	long itemId;
	String year;

	public void fetchPartnersData(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(8501, "FETCH PARTNERS DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {

			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHPARTNERSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			FinancialReportsFactory frf = new FinancialReportsFactory();
			request.setAttribute(ApplicationConstants.paramKeys.PARTNERS_DATA.getValue(),
					(new Gson().toJson(frf.getAgencyPartnersData(((AgencyVO) request.getSession()
							.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code()))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHPARTNERSDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.FETCHPARTNERSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}

		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void savePartnersData(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(8502, "SAVE PARTNERS DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.SAVEPARTNERSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			FinancialReportsFactory frf = new FinancialReportsFactory();
			frf.saveAgencyPartnersData(requestParams, agencyId);
			fetchPartnersData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SAVEPARTNERSDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SAVEPARTNERSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}

		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deletePartnersData(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(8503, "DELETE PARTNERS DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {

			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter("itemId"));

			logger.info(ApplicationConstants.LogMessageKeys.DELETEPARTNERSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.ITEMID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, itemId);

			FinancialReportsFactory frf = new FinancialReportsFactory();
			frf.deleteAgencyPartnersData(itemId, agencyId);
			fetchPartnersData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.DELETEPARTNERSDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.ITEMID.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, itemId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.DELETEPARTNERSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.ITEMID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, itemId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void fetchPartnersTranxsData(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(8511, "FETCH PARTNERS TRANSACTIONS DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHPARTNERSTRANXSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("bank_data", (new Gson().toJson(mdf.getAgencyBankData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			FinancialReportsFactory frf = new FinancialReportsFactory();
			request.setAttribute(ApplicationConstants.paramKeys.PARTNERS_DATA.getValue(),
					(new Gson().toJson(frf.getAgencyPartnersData(((AgencyVO) request.getSession()
							.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code()))));
			request.setAttribute("pt_data", (new Gson().toJson(frf.getAgencyPartnersTranxsData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHPARTNERSTRANXSDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.FETCHPARTNERSTRANXSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}

		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void savePartnersTranxsData(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(8512, "SAVE PARTNERS TRANSACTIONS DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.SAVEPARTNERSTRANXSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			FinancialReportsFactory frf = new FinancialReportsFactory();
			frf.saveAgencyPartnersTranxsData(requestParams, agencyId);
			fetchPartnersTranxsData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SAVEPARTNERSTRANXSDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SAVEPARTNERSTRANXSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}

		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deletePartnersTranxsData(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(8513, "DELETE PARTNERS TRANSACTIONS DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter("itemId"));

			logger.info(ApplicationConstants.LogMessageKeys.DELETEPARTNERSTRANXSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.ITEMID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, itemId);

			FinancialReportsFactory frf = new FinancialReportsFactory();
			frf.deleteAgencyPartnersTranxsData(itemId, agencyId);
			fetchPartnersTranxsData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.DELETEPARTNERSTRANXSDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.ITEMID.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, itemId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.DELETEPARTNERSTRANXSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.ITEMID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, itemId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void showCapitalAccountData(HttpServletRequest request,HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(8521, "SHOW CAPITAL ACCOUNT PAGE",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.SHOWCAPITALACCOUNTDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			FinancialReportsFactory frf = new FinancialReportsFactory();
			request.setAttribute(ApplicationConstants.paramKeys.PARTNERS_DATA.getValue(),
					(new Gson().toJson(frf.getAgencyPartnersData(((AgencyVO) request.getSession()
							.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code()))));
			request.setAttribute("spid", "0");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWCAPITALACCOUNTDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SHOWCAPITALACCOUNTDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}

		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void fetchCapitalAccountData(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(8522, "FETCH CAPITAL REPORT DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			year = request.getParameter("sfy");
			request.setAttribute("year", String.valueOf(year));
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.FETCHCAPITALACCOUNTDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, year);

			Map<String, String[]> requestParams = request.getParameterMap();
			FinancialReportsFactory frf = new FinancialReportsFactory();
			request.setAttribute("car_data",
					(new Gson().toJson(frf.fetchAgencyCapitalAccountReport(requestParams, agencyId))));
			request.setAttribute(ApplicationConstants.paramKeys.PARTNERS_DATA.getValue(),
					(new Gson().toJson(frf.getAgencyPartnersData(((AgencyVO) request.getSession()
							.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code()))));
			String[] pids = requestParams.get("pid");
			request.setAttribute("spid", pids[0]);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.FETCHCAPITALACCOUNTDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.toString() + ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.STATUS.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, year,
					ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.FETCHCAPITALACCOUNTDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.toString() + ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, year, be);

		} catch (ParseException e) {
			e.printStackTrace();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHCAPITALACCOUNTDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.toString() + ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, year, e);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void showDepreciationReport(HttpServletRequest request,HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(5566, "SHOW DEPRECIATION ACCOUNT PAGE",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.SHOWDEPRECIATIONREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("sfy", "0");
			request.setAttribute(ApplicationConstants.paramKeys.DEPR_DATA.getValue(), "");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWDEPRECIATIONREPORT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SHOWDEPRECIATIONREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}

		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void fetchDepreciationReport(HttpServletRequest request,HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(5567, "FETCH DEPRECIATION REPORT DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		Map<String, String[]> requestParams = request.getParameterMap();
		String[] sfys = requestParams.get("sfy");
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			int syfv = Integer.parseInt(sfys[0]);

			logger.info(ApplicationConstants.LogMessageKeys.FETCHDEPRECIATIONREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, sfys[0]);

			FinancialReportsFactory frf = new FinancialReportsFactory();
			request.setAttribute("sfy", sfys[0]);
			DepreciationDO dataDO = frf.fetchDepreciationReportForAgencyByFinancialYear(syfv, agencyId);
			request.setAttribute(ApplicationConstants.paramKeys.DEPR_DATA.getValue(), (new Gson().toJson(dataDO)));
			List<DepreciationDataDO> allDetailsList = frf
					.fetchDepreciationReportDetailsForAgencyByFinancialYear(dataDO.getId());
			request.setAttribute(ApplicationConstants.paramKeys.DEPR_DETAILS_DATA.getValue(),
					(new Gson().toJson(allDetailsList)));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHDEPRECIATIONREPORT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.ITEMID.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, sfys[0], ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.FETCHDEPRECIATIONREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.ITEMID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, sfys[0], be);

		} catch (ParseException e) {
			e.printStackTrace();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHDEPRECIATIONREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.toString() + ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, sfys[0], e);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void processDepreciationReport(HttpServletRequest request,HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(5568, "PROCESS DEPRECIATION ACCOUNT PAGE",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.PROCESSDEPRECIATIONREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			FinancialReportsFactory frf = new FinancialReportsFactory();
			DepreciationDO dataDO = frf.processDepreciationReportForAgencyByFinancialYear(requestParams, agencyId);
			request.setAttribute(ApplicationConstants.paramKeys.DEPR_DATA.getValue(), (new Gson().toJson(dataDO)));
			request.setAttribute("sfy", dataDO.getFyv() + "");
			List<DepreciationDataDO> allDetailsList = frf
					.fetchDepreciationReportDetailsForAgencyByFinancialYear(requestParams, agencyId);
			request.setAttribute(ApplicationConstants.paramKeys.DEPR_DETAILS_DATA.getValue(),
					(new Gson().toJson(allDetailsList)));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.PROCESSDEPRECIATIONREPORT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.PROCESSDEPRECIATIONREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		} catch (ParseException e) {
			e.printStackTrace();
			logger.info(ApplicationConstants.LogMessageKeys.PROCESSDEPRECIATIONREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, e);
		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void submitDepreciationReport(HttpServletRequest request,HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(5569, "SUBMIT DEPRECIATION ACCOUNT PAGE",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.SUBMITDEPRECIATIONREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			FinancialReportsFactory frf = new FinancialReportsFactory();
			DepreciationDO dataDO = frf.submitDepreciationReportForAgencyByFinancialYear(requestParams, agencyId);
			request.setAttribute(ApplicationConstants.paramKeys.DEPR_DATA.getValue(), (new Gson().toJson(dataDO)));
			request.setAttribute("sfy", dataDO.getFyv() + "");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SUBMITDEPRECIATIONREPORT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SUBMITDEPRECIATIONREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		} catch (ParseException e) {
			e.printStackTrace();
			logger.info(ApplicationConstants.LogMessageKeys.SUBMITDEPRECIATIONREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, e);

		}

		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void showCapitalAccountReport(HttpServletRequest request,HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(8531, "SHOW CAPITAL ACCOUNT REPORT PAGE",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.SHOWCAPITALACCOUNTREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("sfy", "0");
			request.setAttribute(ApplicationConstants.paramKeys.CAPAR_DATA.getValue(), "");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWCAPITALACCOUNTREPORT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SHOWCAPITALACCOUNTREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void fetchCapitalAccountReport(HttpServletRequest request,HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(8532, "FETCH CAPITAL ACCOUNT REPORT DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			year = request.getParameter("sfy");
			request.setAttribute("year", year);
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.FETCHCAPITALACCOUNTREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, year);

			Map<String, String[]> requestParams = request.getParameterMap();
			String[] sfys = requestParams.get("sfy");
			int syfv = Integer.parseInt(sfys[0]);
			FinancialReportsFactory frf = new FinancialReportsFactory();
			request.setAttribute("sfy", sfys[0]);
			request.setAttribute(ApplicationConstants.paramKeys.CAPAR_DATA.getValue(),
					(new Gson().toJson(frf.fetchCapitalAccountReportForAgencyByFinancialYear(syfv, agencyId))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.FETCHCAPITALACCOUNTREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.toString() + ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.STATUS.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, year,
					ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.FETCHCAPITALACCOUNTREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.toString() + ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, year, be);

		} catch (ParseException e) {
			e.printStackTrace();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHCAPITALACCOUNTREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.toString() + ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, year, e);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void processCapitalAccountReport(HttpServletRequest request,HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(8533, "PROCESS CAPITAL ACCOUNT REPORT PAGE",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.PROCESSCAPITALACCOUNTREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			FinancialReportsFactory frf = new FinancialReportsFactory();
			CapitalAccountReportDO dataDO = frf.processCapitalAccountReportForAgencyByFinancialYear(requestParams,
					agencyId);
			request.setAttribute(ApplicationConstants.paramKeys.CAPAR_DATA.getValue(), (new Gson().toJson(dataDO)));
			request.setAttribute("sfy", dataDO.getFyv() + "");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.PROCESSCAPITALACCOUNTREPORT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.PROCESSCAPITALACCOUNTREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		} catch (ParseException e) {
			e.printStackTrace();
			logger.info(ApplicationConstants.LogMessageKeys.PROCESSCAPITALACCOUNTREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, e);

		}

		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void submitCapitalAccountReport(HttpServletRequest request,HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(8534, "SUBMIT CAPITAL ACCOUNT REPORT PAGE",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.SUBMITCAPITALACCOUNTREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			FinancialReportsFactory frf = new FinancialReportsFactory();
			CapitalAccountReportDO dataDO = frf.submitCapitalAccountReportForAgencyByFinancialYear(requestParams,
					agencyId);
			request.setAttribute(ApplicationConstants.paramKeys.CAPAR_DATA.getValue(), (new Gson().toJson(dataDO)));
			request.setAttribute("sfy", dataDO.getFyv() + "");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SUBMITCAPITALACCOUNTREPORT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SUBMITCAPITALACCOUNTREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		} catch (ParseException e) {
			e.printStackTrace();
			logger.info(ApplicationConstants.LogMessageKeys.SUBMITCAPITALACCOUNTREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, e);

		}

		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void showPandLAccount(HttpServletRequest request,HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(8541, "SHOW P AND L ACCOUNT PAGE",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.SHOWPANDLACCOUNT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("sfy", "0");
			request.setAttribute(ApplicationConstants.paramKeys.PAL_DATA.getValue(), "");
			request.setAttribute(ApplicationConstants.paramKeys.PAL_DETAILS.getValue(), "");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWPANDLACCOUNT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SHOWPANDLACCOUNT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void fetchPandLAccount(HttpServletRequest request,HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(8542, "FETCH P AND L ACCOUNT DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		Map<String, String[]> requestParams = request.getParameterMap();
		String[] sfys = requestParams.get("sfy");
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			int syfv = Integer.parseInt(sfys[0]);

			logger.info(ApplicationConstants.LogMessageKeys.FETCHPANDLACCOUNT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, sfys[0]);

			FinancialReportsFactory frf = new FinancialReportsFactory();
			request.setAttribute("sfy", sfys[0]);
			PAndLAccountDO dataDO = frf.fetchPAndLAccountForAgencyByFinancialYear(syfv, agencyId);
			request.setAttribute(ApplicationConstants.paramKeys.PAL_DATA.getValue(), (new Gson().toJson(dataDO)));
			request.setAttribute(ApplicationConstants.paramKeys.PAL_DETAILS.getValue(),
					(new Gson().toJson(frf.fetchPAndLAccountDetailsForAgencyByFinancialYear(dataDO.getId()))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.FETCHPANDLACCOUNT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.toString() + ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.STATUS.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, sfys[0],
					ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.FETCHPANDLACCOUNT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.toString() + ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, sfys[0], be);

		} catch (ParseException e) {
			e.printStackTrace();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHPANDLACCOUNT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.toString() + ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, sfys[0], e);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void processPandLAccount(HttpServletRequest request,HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(8543, "PROCESS P AND L ACCOUNT PAGE",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.PROCESSPANDLACCOUNT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			FinancialReportsFactory frf = new FinancialReportsFactory();
			PAndLAccountDO dataDO = frf.processPAndLAccountForAgencyByFinancialYear(requestParams, agencyId);
			request.setAttribute(ApplicationConstants.paramKeys.PAL_DATA.getValue(), (new Gson().toJson(dataDO)));
			request.setAttribute(ApplicationConstants.paramKeys.PAL_DETAILS.getValue(),
					(new Gson().toJson(frf.fetchPAndLAccountDetailsForAgencyByFinancialYear(dataDO.getId()))));
			request.setAttribute("sfy", dataDO.getFyv() + "");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.PROCESSPANDLACCOUNT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.PROCESSPANDLACCOUNT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		} catch (ParseException e) {
			e.printStackTrace();
			logger.info(ApplicationConstants.LogMessageKeys.PROCESSPANDLACCOUNT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, e);

		}

		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void savePandLAccount(HttpServletRequest request,HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(8544, "SAVE P AND L ACCOUNT PAGE",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.SAVEPANDLACCOUNT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			FinancialReportsFactory frf = new FinancialReportsFactory();
			PAndLAccountDO dataDO = frf.savePAndLAccountForAgencyByFinancialYear(requestParams, agencyId);
			request.setAttribute(ApplicationConstants.paramKeys.PAL_DATA.getValue(), (new Gson().toJson(dataDO)));
			request.setAttribute("sfy", dataDO.getFyv() + "");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SAVEPANDLACCOUNT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SAVEPANDLACCOUNT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		} catch (ParseException e) {
			e.printStackTrace();
			logger.info(ApplicationConstants.LogMessageKeys.SAVEPANDLACCOUNT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, e);

		}

		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void submitPandLAccount(HttpServletRequest request,HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(8545, "SUBMIT P AND L ACCOUNT REPORT PAGE",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.SUBMITPANDLACCOUNT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			FinancialReportsFactory frf = new FinancialReportsFactory();
			PAndLAccountDO dataDO = frf.submitPAndLAccountForAgencyByFinancialYear(requestParams, agencyId);
			request.setAttribute(ApplicationConstants.paramKeys.PAL_DATA.getValue(), (new Gson().toJson(dataDO)));
			request.setAttribute(ApplicationConstants.paramKeys.PAL_DETAILS.getValue(),
					(new Gson().toJson(frf.fetchPAndLAccountDetailsForAgencyByFinancialYear(dataDO.getId()))));
			request.setAttribute("sfy", dataDO.getFyv() + "");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SUBMITPANDLACCOUNT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SUBMITPANDLACCOUNT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		} catch (ParseException e) {
			e.printStackTrace();
			logger.info(ApplicationConstants.LogMessageKeys.SUBMITPANDLACCOUNT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, e);

		}

		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void showBalanceSheet(HttpServletRequest request,HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(8551, "SHOW BALANCE SHEET PAGE",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {

			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.SHOWBALANCESHEET.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("sfy", "0");
			request.setAttribute(ApplicationConstants.paramKeys.BS_DATA.getValue(), "");
			request.setAttribute(ApplicationConstants.paramKeys.BS_DETAILS.getValue(), "");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWBALANCESHEET.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SHOWBALANCESHEET.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void fetchBalanceSheet(HttpServletRequest request,HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(8552, "FETCH BALANCE SHEET DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		Map<String, String[]> requestParams = request.getParameterMap();
		String[] sfys = requestParams.get("sfy");
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			int syfv = Integer.parseInt(sfys[0]);

			logger.info(ApplicationConstants.LogMessageKeys.FETCHBALANCESHEET.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, sfys[0]);

			FinancialReportsFactory frf = new FinancialReportsFactory();
			request.setAttribute("sfy", sfys[0]);
			BalanceSheetDO dataDO = frf.fetchBalanceSheetForAgencyByFinancialYear(syfv, agencyId);
			request.setAttribute(ApplicationConstants.paramKeys.BS_DATA.getValue(), (new Gson().toJson(dataDO)));
			request.setAttribute(ApplicationConstants.paramKeys.BS_DETAILS.getValue(),
					(new Gson().toJson(frf.fetchBalanceSheetDetailsForAgencyByFinancialYear(dataDO.getId()))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.FETCHBALANCESHEET.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.toString() + ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.STATUS.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, sfys[0],
					ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.FETCHBALANCESHEET.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.toString() + ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, sfys[0], be);

		} catch (ParseException e) {
			e.printStackTrace();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHBALANCESHEET.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.toString() + ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, sfys[0], e);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void processBalanceSheet(HttpServletRequest request,HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(8553, "PROCESS BALANCE SHEET PAGE",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.PROCESSBALANCESHEET.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			FinancialReportsFactory frf = new FinancialReportsFactory();
			BalanceSheetDO dataDO = frf.processBalanceSheetForAgencyByFinancialYear(requestParams, agencyId);
			request.setAttribute(ApplicationConstants.paramKeys.BS_DATA.getValue(), (new Gson().toJson(dataDO)));
			request.setAttribute(ApplicationConstants.paramKeys.BS_DETAILS.getValue(),
					(new Gson().toJson(frf.fetchBalanceSheetDetailsForAgencyByFinancialYear(dataDO.getId()))));
			request.setAttribute("sfy", dataDO.getFyv() + "");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.PROCESSBALANCESHEET.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.PROCESSBALANCESHEET.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		} catch (ParseException e) {
			e.printStackTrace();
			logger.info(ApplicationConstants.LogMessageKeys.PROCESSBALANCESHEET.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, e);

		}

		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void saveBalanceSheet(HttpServletRequest request,HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(8553, "PROCESS BALANCE SHEET PAGE",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.SAVEBALANCESHEET.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			FinancialReportsFactory frf = new FinancialReportsFactory();
			BalanceSheetDO dataDO = frf.saveBalanceSheetForAgencyByFinancialYear(requestParams, agencyId);
			request.setAttribute(ApplicationConstants.paramKeys.BS_DATA.getValue(), (new Gson().toJson(dataDO)));
			request.setAttribute(ApplicationConstants.paramKeys.BS_DETAILS.getValue(),
					(new Gson().toJson(frf.fetchBalanceSheetDetailsForAgencyByFinancialYear(dataDO.getId()))));
			request.setAttribute("sfy", dataDO.getFyv() + "");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SAVEBALANCESHEET.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SAVEBALANCESHEET.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		} catch (ParseException e) {
			e.printStackTrace();
			logger.info(ApplicationConstants.LogMessageKeys.SAVEBALANCESHEET.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, e);

		}

		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void submitBalanceSheet(HttpServletRequest request,HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(8545, "SUBMIT P AND L ACCOUNT REPORT PAGE",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.SUBMITBALANCESHEET.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			FinancialReportsFactory frf = new FinancialReportsFactory();
			BalanceSheetDO dataDO = frf.submitBalanceSheetForAgencyByFinancialYear(requestParams, agencyId);
			request.setAttribute(ApplicationConstants.paramKeys.BS_DATA.getValue(), (new Gson().toJson(dataDO)));
			request.setAttribute(ApplicationConstants.paramKeys.BS_DETAILS.getValue(),
					(new Gson().toJson(frf.fetchBalanceSheetDetailsForAgencyByFinancialYear(dataDO.getId()))));
			request.setAttribute("sfy", dataDO.getFyv() + "");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SUBMITBALANCESHEET.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SUBMITBALANCESHEET.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		} catch (ParseException e) {
			e.printStackTrace();
			logger.info(ApplicationConstants.LogMessageKeys.SUBMITBALANCESHEET.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, e);

		}

		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

}
