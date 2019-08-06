package com.it.erpapp.appservices;

import java.util.Map;

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
import com.it.erpapp.framework.OTransactionsDataFactory;
import com.it.erpapp.framework.PPMasterDataFactory;
import com.it.erpapp.framework.TransactionsDataFactory;
import com.it.erpapp.framework.model.vos.AgencyVO;
import com.it.erpapp.response.MessageObject;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class OTransactionsDataServiceBean {
	Logger logger=LoggerFactory.getLogger(OTransactionsDataServiceBean.class);

	long agencyId;
	long itemId;
	long bankId;

	// Receipts Data
	public void fetchReceiptsData(HttpServletRequest request,
			HttpServletResponse response){
		MessageObject msgObj = new MessageObject(5501, "FETCH RECEIPTS DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO)request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			
			logger.info(ApplicationConstants.LogMessageKeys.FETCHRECEIPTSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId);

			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("staff_data", new Gson().toJson(mdf.getAgencyAllStaffData(agencyId)));
			request.setAttribute("bank_data", new Gson().toJson(mdf.getAgencyAllBankData(agencyId)));
			request.setAttribute("cvo_data", new Gson().toJson(mdf.getAgencyAllCVOData(agencyId)));
			OTransactionsDataFactory otdf = new OTransactionsDataFactory();
			request.setAttribute("receipts_data", new Gson().toJson(otdf.getAgencyReceiptsData(agencyId)));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.FETCHRECEIPTSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.STATUS.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,ApplicationConstants.SUCCESS_STATUS_STRING);

			HttpSession session = request.getSession(true);
            AgencyFactory af = new AgencyFactory();
            session.setAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue(),(af.fetchAgencyVO(agencyId)));
		}catch(BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.FETCHRECEIPTSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void saveReceiptsData(HttpServletRequest request, HttpServletResponse response){
		
		MessageObject msgObj = new MessageObject(5502, "SAVE RECEIPTS DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			
			logger.info(ApplicationConstants.LogMessageKeys.SAVERECEIPTSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			OTransactionsDataFactory otdf = new OTransactionsDataFactory();
			otdf.saveAgencyReceiptsData(requestParams, agencyId);
			fetchReceiptsData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.SAVERECEIPTSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.STATUS.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,ApplicationConstants.SUCCESS_STATUS_STRING);

		}catch(BusinessException be){
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.SAVERECEIPTSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,be);

		}
	
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deleteReceiptsData(HttpServletRequest request, HttpServletResponse response){
		
		MessageObject msgObj = new MessageObject(5503, "DELETE RECEIPTS DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter("itemId"));
			
			logger.info(ApplicationConstants.LogMessageKeys.DELETERECEIPTSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId,itemId);

			OTransactionsDataFactory otdf = new OTransactionsDataFactory();
			otdf.deleteAgencyReceiptsData(itemId, agencyId);
			fetchReceiptsData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.DELETERECEIPTSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()  +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ApplicationConstants.paramKeys.SEPERATOR.getValue()+ApplicationConstants.actionStatusKeys.STATUS.toString()
					+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId,itemId,
					ApplicationConstants.SUCCESS_STATUS_STRING);

		}catch(BusinessException be){
			msgObj.setMessageText(be.getExceptionMessage());
			logger.info(ApplicationConstants.LogMessageKeys.DELETERECEIPTSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()  +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ApplicationConstants.paramKeys.SEPERATOR.getValue()+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId,itemId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	// Payments Data
	public void fetchPaymentsData(HttpServletRequest request,
			HttpServletResponse response){
		MessageObject msgObj = new MessageObject(5511, "FETCH PAYMENTS DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO)request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			
			logger.info(ApplicationConstants.LogMessageKeys.FETCHPAYMENTSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId);

			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("staff_data", new Gson().toJson(mdf.getAgencyAllStaffData(agencyId)));
			request.setAttribute("bank_data", new Gson().toJson(mdf.getAgencyAllBankData(agencyId)));
			request.setAttribute("cvo_data", new Gson().toJson(mdf.getAgencyAllCVOData(agencyId)));
			OTransactionsDataFactory otdf = new OTransactionsDataFactory();
			request.setAttribute("payments_data", (new Gson().toJson(otdf.getAgencyPaymentsData(agencyId))));
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


	
	
	public void savePaymentsData(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(5502, "SAVE RECEIPTS DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			String mon = request.getParameter("month");
			String yr = request.getParameter("year");

			logger.info(ApplicationConstants.LogMessageKeys.SAVEPAYMENTSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			OTransactionsDataFactory otdf = new OTransactionsDataFactory();
			otdf.saveAgencyPaymentsData(requestParams, agencyId, mon, yr);
			fetchPaymentsData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SAVEPAYMENTSDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SAVEPAYMENTSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);
		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deletePaymentsData(HttpServletRequest request, HttpServletResponse response){
		
		MessageObject msgObj = new MessageObject(5503, "DELETE RECEIPTS DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.ITEM_ID.getValue()));
			
			logger.info(ApplicationConstants.LogMessageKeys.DELETEPAYMENTSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId,itemId);

			OTransactionsDataFactory otdf = new OTransactionsDataFactory();
			otdf.deleteAgencyPaymentsData(itemId, agencyId);
			fetchPaymentsData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.DELETEPAYMENTSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()  +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ApplicationConstants.paramKeys.SEPERATOR.getValue()+ApplicationConstants.actionStatusKeys.STATUS.toString()
					+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId,itemId,
					ApplicationConstants.SUCCESS_STATUS_STRING);

		}catch(BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			logger.info(ApplicationConstants.LogMessageKeys.DELETEPAYMENTSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()  +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ApplicationConstants.paramKeys.SEPERATOR.getValue()+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId,itemId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	// Bank Transactions Data
	public void fetchBankTranxsData(HttpServletRequest request,
			HttpServletResponse response){
		MessageObject msgObj = new MessageObject(5521, "FETCH BANK TRANSACTIONS DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO)request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			
			logger.info(ApplicationConstants.LogMessageKeys.FETCHBANKTRANXSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId);

			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("bank_data", new Gson().toJson(mdf.getAgencyAllBankData(agencyId)));
			OTransactionsDataFactory otdf = new OTransactionsDataFactory();
			request.setAttribute("bt_data", new Gson().toJson(otdf.getAgencyBankTranxsData(agencyId)));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
		
		
			logger.info(ApplicationConstants.LogMessageKeys.FETCHBANKTRANXSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.STATUS.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,ApplicationConstants.SUCCESS_STATUS_STRING);

			HttpSession session = request.getSession(true);
            AgencyFactory af = new AgencyFactory();
            session.setAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue(),(af.fetchAgencyVO(agencyId)));
		}catch(BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.FETCHBANKTRANXSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void saveBankTranxsData(HttpServletRequest request, HttpServletResponse response){
		
		MessageObject msgObj = new MessageObject(5522, "SAVE BANK TRANSACTIONS DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			
			logger.info(ApplicationConstants.LogMessageKeys.SAVEBANKTRANXSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			OTransactionsDataFactory otdf = new OTransactionsDataFactory();
			otdf.saveAgencyBankTranxsData(requestParams, agencyId);
			fetchBankTranxsData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.SAVEBANKTRANXSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.STATUS.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,ApplicationConstants.SUCCESS_STATUS_STRING);

		}catch(BusinessException be){
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.SAVEBANKTRANXSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deleteBankTranxsData(HttpServletRequest request, HttpServletResponse response){
		
		MessageObject msgObj = new MessageObject(5523, "DELETE BANK TRANSACTIONS DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.ITEM_ID.getValue()));
			
			logger.info(ApplicationConstants.LogMessageKeys.DELETEBANKTRANXSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId,itemId);

			OTransactionsDataFactory otdf = new OTransactionsDataFactory();
			otdf.deleteAgencyBankTranxsData(itemId, agencyId);
			fetchBankTranxsData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.DELETEBANKTRANXSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()  +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ApplicationConstants.paramKeys.SEPERATOR.getValue()+ApplicationConstants.actionStatusKeys.STATUS.toString()
					+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId,itemId,
					ApplicationConstants.SUCCESS_STATUS_STRING);

		}catch(BusinessException be){
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.DELETEBANKTRANXSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()  +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ApplicationConstants.paramKeys.SEPERATOR.getValue()+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId,itemId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	// Credit Notes Data
	public void fetchCreditNotesData(HttpServletRequest request, HttpServletResponse response){
		MessageObject msgObj = new MessageObject(5531, "FETCH CREDIT NOTES DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO)request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			
			
			logger.info(ApplicationConstants.LogMessageKeys.FETCHCREDITNOTESDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId);

			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("bank_data", new Gson().toJson(mdf.getAgencyAllBankData(agencyId)));
			request.setAttribute("cvo_data", new Gson().toJson(mdf.getAgencyAllCVOData(agencyId)));
			request.setAttribute("cylinder_types_list", new Gson().toJson((new CacheFactory()).getCylinderTypesData()));
			request.setAttribute("arb_types_list", new Gson().toJson((new CacheFactory()).getARBTypesData()));
			request.setAttribute("prods_list", new Gson().toJson((new CacheFactory()).getProductCatogoryData()));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", new Gson().toJson(ppmdf.getAgencyEquipmentData(agencyId)));
			request.setAttribute("arb_data", new Gson().toJson(ppmdf.getAgencyAllARBData(agencyId)));
			OTransactionsDataFactory otdf = new OTransactionsDataFactory();
			request.setAttribute("cn_data", new Gson().toJson(otdf.getAgencyCreditNotesData(agencyId)));
			request.setAttribute("sr_data", new Gson().toJson(otdf.getAgencySaleReturnData(agencyId)));
			
      //			new popup start for multipopup 03052019
			
				request.setAttribute("staff_data", new Gson().toJson(mdf.getAgencyAllStaffData(agencyId)));
				request.setAttribute("area_codes_data", new Gson().toJson(ppmdf.getAgencyAreaCodeData(agencyId)));

	    //		end popup for multipopup 03052019
			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("pi_data", new Gson().toJson(
					factory.getAgencyPurchaseInvoices(((AgencyVO)request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code())));
			request.setAttribute("arb_pi_data", new Gson().toJson(factory.getAgencyARBPurchaseInvoices(agencyId)));
			request.setAttribute("drs_data", new Gson().toJson(factory.getAgencyDOMSalesInvoices(agencyId)));
			request.setAttribute("crs_data", new Gson().toJson(factory.getAgencyCOMSalesInvoices(agencyId)));
			request.setAttribute("arbs_data", new Gson().toJson(factory.getAgencyARBSalesInvoices(agencyId)));
			request.setAttribute("pr_data", new Gson().toJson(otdf.getAgencyPurchaseReturnData(agencyId)));			

			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.FETCHCREDITNOTESDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.STATUS.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,ApplicationConstants.SUCCESS_STATUS_STRING);

			HttpSession session = request.getSession(true);
            AgencyFactory af = new AgencyFactory();
            session.setAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue(),(af.fetchAgencyVO(agencyId)));
		}catch(BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.FETCHCREDITNOTESDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void saveCreditNotesData(HttpServletRequest request, HttpServletResponse response){
		
		MessageObject msgObj = new MessageObject(5532, "SAVE CREDIT NOTES DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			
			logger.info(ApplicationConstants.LogMessageKeys.SAVECREDITNOTESDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			OTransactionsDataFactory otdf = new OTransactionsDataFactory();
			otdf.saveAgencyCreditNotesData(requestParams, agencyId);
			fetchCreditNotesData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
		
			logger.info(ApplicationConstants.LogMessageKeys.SAVECREDITNOTESDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.STATUS.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,ApplicationConstants.SUCCESS_STATUS_STRING);

		}catch(BusinessException be){
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.SAVECREDITNOTESDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deleteCreditNotesData(HttpServletRequest request, HttpServletResponse response){
		
		MessageObject msgObj = new MessageObject(5533, "DELETE CREDIT NOTES DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.ITEM_ID.getValue()));
			
			logger.info(ApplicationConstants.LogMessageKeys.DELETECREDITNOTESDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId,itemId);

			OTransactionsDataFactory otdf = new OTransactionsDataFactory();
			otdf.deleteAgencyCreditNotesData(itemId, agencyId);
			fetchCreditNotesData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.DELETECREDITNOTESDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()  +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ApplicationConstants.paramKeys.SEPERATOR.getValue()+ApplicationConstants.actionStatusKeys.STATUS.toString()
					+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId,itemId,
					ApplicationConstants.SUCCESS_STATUS_STRING);

		}catch(BusinessException be){
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.DELETECREDITNOTESDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()  +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ApplicationConstants.paramKeys.SEPERATOR.getValue()+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId,itemId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	// Debit Notes Data
	public void fetchDebitNotesData(HttpServletRequest request,
			HttpServletResponse response){
		MessageObject msgObj = new MessageObject(5541, "FETCH DEBIT NOTES DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO)request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			
			logger.info(ApplicationConstants.LogMessageKeys.FETCHDEBITNOTESDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId);

			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("bank_data", new Gson().toJson(mdf.getAgencyAllBankData(agencyId)));
			request.setAttribute("cvo_data", new Gson().toJson(mdf.getAgencyAllCVOData(agencyId)));
			request.setAttribute("cylinder_types_list", new Gson().toJson((new CacheFactory()).getCylinderTypesData()));
			request.setAttribute("arb_types_list", new Gson().toJson((new CacheFactory()).getARBTypesData()));
			request.setAttribute("prods_list", new Gson().toJson((new CacheFactory()).getProductCatogoryData()));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", new Gson().toJson(ppmdf.getAgencyEquipmentData(agencyId)));
			request.setAttribute("arb_data", new Gson().toJson(ppmdf.getAgencyAllARBData(agencyId)));
			OTransactionsDataFactory otdf = new OTransactionsDataFactory();
			request.setAttribute("dn_data", new Gson().toJson(otdf.getAgencyDebitNotesData(agencyId)));
			request.setAttribute("sr_data", new Gson().toJson(otdf.getAgencySaleReturnData(agencyId)));

			 //			new popup start for multipopup 03052019
			
			request.setAttribute("staff_data", new Gson().toJson(mdf.getAgencyAllStaffData(agencyId)));
			request.setAttribute("area_codes_data", new Gson().toJson(ppmdf.getAgencyAreaCodeData(agencyId)));

    //		end popup for multipopup 03052019
			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("pi_data", new Gson().toJson(
					factory.getAgencyPurchaseInvoices(((AgencyVO)request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code())));
			request.setAttribute("arb_pi_data", new Gson().toJson(factory.getAgencyARBPurchaseInvoices(agencyId)));
			request.setAttribute("drs_data", new Gson().toJson(factory.getAgencyDOMSalesInvoices(agencyId)));
			request.setAttribute("crs_data", new Gson().toJson(factory.getAgencyCOMSalesInvoices(agencyId)));
			request.setAttribute("arbs_data", new Gson().toJson(factory.getAgencyARBSalesInvoices(agencyId)));
			request.setAttribute("pr_data", new Gson().toJson(otdf.getAgencyPurchaseReturnData(agencyId)));
			
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.FETCHDEBITNOTESDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.STATUS.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,ApplicationConstants.SUCCESS_STATUS_STRING);

			HttpSession session = request.getSession(true);
            AgencyFactory af = new AgencyFactory();
            session.setAttribute("agencyVO",af.fetchAgencyVO(agencyId));
            
		}catch(BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.FETCHDEBITNOTESDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void saveDebitNotesData(HttpServletRequest request, HttpServletResponse response){
		
		MessageObject msgObj = new MessageObject(5542, "SAVE DEBIT NOTES DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			
			logger.info(ApplicationConstants.LogMessageKeys.SAVEDEBITNOTESDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			OTransactionsDataFactory otdf = new OTransactionsDataFactory();
			otdf.saveAgencyDebitNotesData(requestParams, agencyId);
			fetchDebitNotesData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.SAVEDEBITNOTESDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.STATUS.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,ApplicationConstants.SUCCESS_STATUS_STRING);


		}catch(BusinessException be){
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.SAVEDEBITNOTESDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deleteDebitNotesData(HttpServletRequest request, HttpServletResponse response){
		
		MessageObject msgObj = new MessageObject(5543, "DELETE DEBIT NOTES DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.ITEM_ID.getValue()));
			
			logger.info(ApplicationConstants.LogMessageKeys.DELETEDEBITNOTESDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId,itemId);

			OTransactionsDataFactory otdf = new OTransactionsDataFactory();
			otdf.deleteAgencyDebitNotesData(itemId, agencyId);
			fetchDebitNotesData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.DELETEDEBITNOTESDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()  +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ApplicationConstants.paramKeys.SEPERATOR.getValue()+ApplicationConstants.actionStatusKeys.STATUS.toString()
					+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId,itemId,
					ApplicationConstants.SUCCESS_STATUS_STRING);

		}catch(BusinessException be){
			msgObj.setMessageText(be.getExceptionMessage());
		
			logger.info(ApplicationConstants.LogMessageKeys.DELETEDEBITNOTESDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()  +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ApplicationConstants.paramKeys.SEPERATOR.getValue()+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId,itemId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	// Purchase Return Data
	public void fetchPurchaseReturnData(HttpServletRequest request,
			HttpServletResponse response){
		MessageObject msgObj = new MessageObject(5571, "FETCH PURCHASE RETURN DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO)request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			
			logger.info(ApplicationConstants.LogMessageKeys.FETCHPURCHASERETURNDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId);

			request.setAttribute("cylinder_types_list", new Gson().toJson((new CacheFactory()).getCylinderTypesData()));
			request.setAttribute("arb_types_list", new Gson().toJson((new CacheFactory()).getARBTypesData()));
			request.setAttribute("prods_list", new Gson().toJson((new CacheFactory()).getProductCatogoryData()));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", new Gson().toJson(ppmdf.getAgencyEquipmentData(agencyId)));
			request.setAttribute("arb_data", new Gson().toJson(ppmdf.getAgencyAllARBData(agencyId)));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("bank_data", new Gson().toJson(mdf.getAgencyAllBankData(agencyId)));
			request.setAttribute("cvo_data", new Gson().toJson(mdf.getAgencyAllCVOData(agencyId)));
			request.setAttribute("refill_prices_data", new Gson().toJson(
					ppmdf.getAgencyRefillPriceData(((AgencyVO)request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code())));
			request.setAttribute("equipment_data", new Gson().toJson(ppmdf.getAgencyEquipmentData(agencyId)));
			OTransactionsDataFactory otdf = new OTransactionsDataFactory();
			request.setAttribute("pr_data", new Gson().toJson(otdf.getAgencyPurchaseReturnData(agencyId)));

			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("pi_data", new Gson().toJson(
					factory.getAgencyPurchaseInvoices(((AgencyVO)request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code())));
			request.setAttribute("arb_pi_data", new Gson().toJson(factory.getAgencyARBPurchaseInvoices(agencyId)));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.FETCHPURCHASERETURNDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.STATUS.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,ApplicationConstants.SUCCESS_STATUS_STRING);

			HttpSession session = request.getSession(true);
            AgencyFactory af = new AgencyFactory();
            session.setAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue(),af.fetchAgencyVO(agencyId));
		}catch(BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.FETCHPURCHASERETURNDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void savePurchaseReturnData(HttpServletRequest request, HttpServletResponse response){
		
		MessageObject msgObj = new MessageObject(5572, "SAVE PURCHASE RETURN DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			
			logger.info(ApplicationConstants.LogMessageKeys.SAVEPURCHASERETURNDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			OTransactionsDataFactory otdf = new OTransactionsDataFactory();
			otdf.saveAgencyPurchaseReturnData(requestParams, agencyId);
			fetchPurchaseReturnData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.SAVEPURCHASERETURNDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.STATUS.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,ApplicationConstants.SUCCESS_STATUS_STRING);

		}catch(BusinessException be){
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.SAVEPURCHASERETURNDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deletePurchaseReturnData(HttpServletRequest request, HttpServletResponse response){
		
		MessageObject msgObj = new MessageObject(5573, "DELETE PURCHASE RETURN DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter("dataId"));
			
			logger.info(ApplicationConstants.LogMessageKeys.DELETEPURCHASERETURNDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId,itemId);

			OTransactionsDataFactory otdf = new OTransactionsDataFactory();
			otdf.deleteAgencyPurchaseReturnData(itemId, agencyId);
			fetchPurchaseReturnData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.DELETEPURCHASERETURNDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()  +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ApplicationConstants.paramKeys.SEPERATOR.getValue()+ApplicationConstants.actionStatusKeys.STATUS.toString()
					+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId,itemId,
					ApplicationConstants.SUCCESS_STATUS_STRING);

		}catch(BusinessException be){
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.DELETEPURCHASERETURNDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()  +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ApplicationConstants.paramKeys.SEPERATOR.getValue()+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId,itemId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	// Sales Return Data
	public void fetchSalesReturnData(HttpServletRequest request,
			HttpServletResponse response){
		MessageObject msgObj = new MessageObject(5551, "FETCH SALES RETURN DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO)request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			
			logger.info(ApplicationConstants.LogMessageKeys.FETCHSALESRETURNDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId);

			request.setAttribute("cylinder_types_list", new Gson().toJson((new CacheFactory()).getCylinderTypesData()));
			request.setAttribute("arb_types_list", new Gson().toJson((new CacheFactory()).getARBTypesData()));
			request.setAttribute("prod_types_list", new Gson().toJson(new CacheFactory().getProductCatogoryData()));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("bank_data", new Gson().toJson(mdf.getAgencyAllBankData(agencyId)));
			request.setAttribute("cvo_data", new Gson().toJson(mdf.getAgencyAllCVOData(agencyId)));
			request.setAttribute("staff_data", (new Gson().toJson(mdf.getAgencyAllStaffData(agencyId))));

			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", new Gson().toJson(ppmdf.getAgencyEquipmentData(agencyId)));
			request.setAttribute("arb_data", new Gson().toJson(ppmdf.getAgencyAllARBData(agencyId)));
			request.setAttribute("arb_prices_data", new Gson().toJson(ppmdf.getAgencyARBPriceData(agencyId)));
			request.setAttribute("refill_prices_data", new Gson().toJson(
					ppmdf.getAgencyRefillPriceData(((AgencyVO)request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code())));
			OTransactionsDataFactory otdf = new OTransactionsDataFactory();
			request.setAttribute("sr_data", new Gson().toJson(otdf.getAgencySaleReturnData(agencyId)));
			
			TransactionsDataFactory factory = new TransactionsDataFactory();
            request.setAttribute("drs_data", (new Gson().toJson(factory.getAgencyDOMSalesInvoices(agencyId))));
            request.setAttribute("crs_data", (new Gson().toJson(factory.getAgencyCOMSalesInvoices(agencyId))));
            request.setAttribute("arbs_data", (new Gson().toJson(factory.getAgencyARBSalesInvoices(agencyId))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.FETCHSALESRETURNDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.STATUS.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,ApplicationConstants.SUCCESS_STATUS_STRING);

			HttpSession session = request.getSession(true);
            AgencyFactory af = new AgencyFactory();
            session.setAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue(),(af.fetchAgencyVO(agencyId)));
		}catch(BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.FETCHSALESRETURNDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,be);
		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void saveSalesReturnData(HttpServletRequest request, HttpServletResponse response){
		
		MessageObject msgObj = new MessageObject(5552, "SAVE SALES RETURN DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			
			logger.info(ApplicationConstants.LogMessageKeys.SAVESALESRETURNDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			OTransactionsDataFactory otdf = new OTransactionsDataFactory();
			otdf.saveAgencySaleReturnData(requestParams, agencyId);
			fetchSalesReturnData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.SAVESALESRETURNDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.STATUS.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,ApplicationConstants.SUCCESS_STATUS_STRING);

		}catch(BusinessException be){
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.SAVESALESRETURNDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deleteSalesReturnData(HttpServletRequest request, HttpServletResponse response){
		
		MessageObject msgObj = new MessageObject(5553, "DELETE SALES RETURN DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter("dataId"));
			
			logger.info(ApplicationConstants.LogMessageKeys.DELETESALESRETURNDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId,itemId);

			OTransactionsDataFactory otdf = new OTransactionsDataFactory();
			otdf.deleteAgencySaleReturnData(itemId, agencyId);
			fetchSalesReturnData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
	
			logger.info(ApplicationConstants.LogMessageKeys.DELETESALESRETURNDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()  +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ApplicationConstants.paramKeys.SEPERATOR.getValue()+ApplicationConstants.actionStatusKeys.STATUS.toString()
					+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId,itemId,
					ApplicationConstants.SUCCESS_STATUS_STRING);

		}catch(BusinessException be){
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.DELETESALESRETURNDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()  +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ApplicationConstants.paramKeys.SEPERATOR.getValue()+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId,itemId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	// Assets Data
	public void fetchAssetsData(HttpServletRequest request,
			HttpServletResponse response){
		MessageObject msgObj = new MessageObject(5561, "FETCH ASSET DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO)request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			
			logger.info(ApplicationConstants.LogMessageKeys.FETCHASSETSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId);

			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("bank_data", new Gson().toJson(mdf.getAgencyAllBankData(agencyId)));
			request.setAttribute("staff_data", new Gson().toJson(mdf.getAgencyAllStaffData(agencyId)));
			OTransactionsDataFactory otdf = new OTransactionsDataFactory();
			request.setAttribute("am_data", new Gson().toJson(otdf.getAgencyAssetsData(agencyId)));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.FETCHASSETSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.STATUS.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,ApplicationConstants.SUCCESS_STATUS_STRING);

			HttpSession session = request.getSession(true);
            AgencyFactory af = new AgencyFactory();
            session.setAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue(),(af.fetchAgencyVO(agencyId)));
		}catch(BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.FETCHASSETSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void saveAssetsData(HttpServletRequest request, HttpServletResponse response){
		
		MessageObject msgObj = new MessageObject(5562, "SAVE ASSET DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			
			logger.info(ApplicationConstants.LogMessageKeys.SAVEASSETSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			OTransactionsDataFactory otdf = new OTransactionsDataFactory();
			otdf.saveAgencyAssetsData(requestParams, agencyId);
			fetchAssetsData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.SAVEASSETSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.STATUS.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId,ApplicationConstants.SUCCESS_STATUS_STRING);

		}catch(BusinessException be){
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.SAVEASSETSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deleteAssetData(HttpServletRequest request, HttpServletResponse response){
		
		MessageObject msgObj = new MessageObject(5563, "DELETE ASSET DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter("dataId"));
			
			logger.info(ApplicationConstants.LogMessageKeys.DELETEASSETDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId,itemId);

		    OTransactionsDataFactory otdf = new OTransactionsDataFactory();
			otdf.deleteAgencyAssetData(itemId, agencyId);
			fetchAssetsData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.DELETEASSETDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()  +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ApplicationConstants.paramKeys.SEPERATOR.getValue()+ApplicationConstants.actionStatusKeys.STATUS.toString()
					+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId,itemId,
					ApplicationConstants.SUCCESS_STATUS_STRING);

		}catch(BusinessException be){
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.DELETEASSETDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()  +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ApplicationConstants.paramKeys.SEPERATOR.getValue()+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId,itemId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}


	// TVs
	public void fetchTVData(HttpServletRequest request, HttpServletResponse response){
		MessageObject msgObj = new MessageObject(5701, "FETCH TVS DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO)request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			logger.info(ApplicationConstants.LogMessageKeys.FETCHTVDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId);

			request.setAttribute("cylinder_types_list", new Gson().toJson((new CacheFactory()).getCylinderTypesData()));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("staff_data", new Gson().toJson(mdf.getAgencyAllStaffData(agencyId)));
			request.setAttribute("bank_data", new Gson().toJson(
					mdf.getAgencyAllBankData(((AgencyVO)request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code())));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", new Gson().toJson(ppmdf.getAgencyEquipmentData(agencyId)));
			request.setAttribute("services_data", new Gson().toJson(ppmdf.getAgencyServicesData(agencyId)));
			request.setAttribute("refill_prices_data", new Gson().toJson(
					ppmdf.getAgencyRefillPriceData(((AgencyVO)request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code())));
			request.setAttribute("prod_types_list", new Gson().toJson(new CacheFactory().getProductCatogoryData()));
			OTransactionsDataFactory factory = new OTransactionsDataFactory();
			request.setAttribute("tvs_data", new Gson().toJson(
					factory.getAgencyTVData(agencyId)));
			request.setAttribute("dequipment_data", (new Gson().toJson(ppmdf.getAgencyDEquipmentData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));

			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.FETCHTVDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.STATUS.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,ApplicationConstants.SUCCESS_STATUS_STRING);

			HttpSession session = request.getSession(true);
            AgencyFactory af = new AgencyFactory();
            session.setAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue(),(af.fetchAgencyVO(agencyId)));
		}catch(BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.FETCHTVDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void saveTVData(HttpServletRequest request, HttpServletResponse response){
		
		MessageObject msgObj = new MessageObject(5702, "SAVE TV DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			
			logger.info(ApplicationConstants.LogMessageKeys.SAVETVDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId);

		    Map<String, String[]> requestParams = request.getParameterMap();
			OTransactionsDataFactory factory = new OTransactionsDataFactory();
			factory.saveAgencyTVData(requestParams, agencyId);
			fetchTVData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.SAVETVDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.STATUS.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,ApplicationConstants.SUCCESS_STATUS_STRING);

		}catch(BusinessException be){
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.SAVETVDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deleteTVData(HttpServletRequest request, HttpServletResponse response){
		
		MessageObject msgObj = new MessageObject(5703, "DELETE TV DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter("dataId"));
			bankId = Long.parseLong(request.getParameter("bankId"));
			
			logger.info(ApplicationConstants.LogMessageKeys.DELETETVDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					 +ApplicationConstants.paramKeys.SEPERATOR.getValue()+ApplicationConstants.paramKeys.BANKID.toString()
						+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId,itemId,bankId);

			OTransactionsDataFactory factory = new OTransactionsDataFactory();
			factory.deleteAgencyTVData(itemId, agencyId,bankId);
			fetchTVData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.DELETETVDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()  +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ApplicationConstants.paramKeys.SEPERATOR.getValue()+ApplicationConstants.paramKeys.BANKID.toString()
					+ApplicationConstants.LogKeys.LOG_PARAM.getValue()+ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.STATUS.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId,itemId,bankId,ApplicationConstants.SUCCESS_STATUS_STRING);


		}catch(BusinessException be){
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.DELETETVDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()  +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ApplicationConstants.paramKeys.SEPERATOR.getValue()+ApplicationConstants.paramKeys.BANKID.toString()
					+ApplicationConstants.LogKeys.LOG_PARAM.getValue()+ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId,itemId,bankId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	// RCs
	public void fetchRCData(HttpServletRequest request,
			HttpServletResponse response){
		MessageObject msgObj = new MessageObject(5711, "FETCH RCS DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO)request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			
			logger.info(ApplicationConstants.LogMessageKeys.FETCHRCDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId);

		    request.setAttribute("cylinder_types_list", new Gson().toJson((new CacheFactory()).getCylinderTypesData()));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("staff_data", new Gson().toJson(mdf.getAgencyAllStaffData(agencyId)));
			request.setAttribute("bank_data", new Gson().toJson(
					mdf.getAgencyAllBankData(((AgencyVO)request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code())));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", new Gson().toJson(ppmdf.getAgencyEquipmentData(agencyId)));
			request.setAttribute("services_data", new Gson().toJson(ppmdf.getAgencyServicesData(agencyId)));
			request.setAttribute("refill_prices_data", new Gson().toJson(
					ppmdf.getAgencyRefillPriceData(((AgencyVO)request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code())));
			request.setAttribute("prod_types_list", new Gson().toJson(new CacheFactory().getProductCatogoryData()));
			OTransactionsDataFactory factory = new OTransactionsDataFactory();
			request.setAttribute("rcs_data", new Gson().toJson(
					factory.getAgencyRCData(agencyId)));
			request.setAttribute("dequipment_data", (new Gson().toJson(ppmdf.getAgencyDEquipmentData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));

			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.FETCHRCDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.STATUS.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,ApplicationConstants.SUCCESS_STATUS_STRING);

			HttpSession session = request.getSession(true);
            AgencyFactory af = new AgencyFactory();
            session.setAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue(),af.fetchAgencyVO(agencyId));
		}catch(BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.FETCHRCDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void saveRCData(HttpServletRequest request, HttpServletResponse response){
		
		MessageObject msgObj = new MessageObject(5712, "SAVE RC DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			
			logger.info(ApplicationConstants.LogMessageKeys.SAVERCDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			OTransactionsDataFactory factory = new OTransactionsDataFactory();
			factory.saveAgencyRCData(requestParams, agencyId);
			fetchRCData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.SAVERCDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.STATUS.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,ApplicationConstants.SUCCESS_STATUS_STRING);

		}catch(BusinessException be){
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.SAVERCDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deleteRCData(HttpServletRequest request, HttpServletResponse response){
		
		MessageObject msgObj = new MessageObject(5703, "DELETE RC DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter("dataId"));
			bankId = Long.parseLong(request.getParameter("bankId"));
			
			logger.info(ApplicationConstants.LogMessageKeys.DELETERCDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					 +ApplicationConstants.paramKeys.SEPERATOR.getValue()+ApplicationConstants.paramKeys.BANKID.toString()
						+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId,itemId,bankId);

			
			OTransactionsDataFactory factory = new OTransactionsDataFactory();
			factory.deleteAgencyRCData(itemId, agencyId,bankId);
			fetchRCData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.DELETERCDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()  +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ApplicationConstants.paramKeys.SEPERATOR.getValue()+ApplicationConstants.paramKeys.BANKID.toString()
					+ApplicationConstants.LogKeys.LOG_PARAM.getValue()+ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.STATUS.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId,itemId,bankId,ApplicationConstants.SUCCESS_STATUS_STRING);

		}catch(BusinessException be){
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.DELETERCDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()  +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ApplicationConstants.paramKeys.SEPERATOR.getValue()+ApplicationConstants.paramKeys.BANKID.toString()
					+ApplicationConstants.LogKeys.LOG_PARAM.getValue()+ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId,itemId,bankId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	// NCs
	public void fetchNCData(HttpServletRequest request,
			HttpServletResponse response){
		MessageObject msgObj = new MessageObject(5711, "FETCH NC/DBCS DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO)request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			
			logger.info(ApplicationConstants.LogMessageKeys.FETCHNCDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId);

			request.setAttribute("cylinder_types_list", new Gson().toJson((new CacheFactory()).getCylinderTypesData()));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("staff_data", new Gson().toJson(mdf.getAgencyAllStaffData(agencyId)));
			request.setAttribute("bank_data", new Gson().toJson(
					mdf.getAgencyAllBankData(((AgencyVO)request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code())));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", new Gson().toJson(ppmdf.getAgencyEquipmentData(agencyId)));
			request.setAttribute("services_data", new Gson().toJson(ppmdf.getAgencyServicesData(agencyId)));
			request.setAttribute("arb_data", new Gson().toJson(ppmdf.getAgencyAllARBData(agencyId)));
			request.setAttribute("refill_prices_data", new Gson().toJson(
					ppmdf.getAgencyRefillPriceData(((AgencyVO)request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code())));
			request.setAttribute("arb_prices_data", new Gson().toJson(
					ppmdf.getAgencyARBPriceData(((AgencyVO)request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code())));
			request.setAttribute("prod_types_list", new Gson().toJson(new CacheFactory().getProductCatogoryData()));
			request.setAttribute("bom_data", new Gson().toJson(
					ppmdf.getAgencyBOMData(((AgencyVO)request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code())));
			OTransactionsDataFactory factory = new OTransactionsDataFactory();
			request.setAttribute("page_data", new Gson().toJson(
					factory.getAgencyNCDBCData(agencyId)));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.FETCHNCDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.STATUS.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,ApplicationConstants.SUCCESS_STATUS_STRING);

			HttpSession session = request.getSession(true);
            AgencyFactory af = new AgencyFactory();
            session.setAttribute(ApplicationConstants.paramKeys.AGENCYVO.toString(),af.fetchAgencyVO(agencyId));
		}catch(BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.FETCHNCDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void saveNCData(HttpServletRequest request, HttpServletResponse response){
		
		MessageObject msgObj = new MessageObject(5722, "SAVE NC DBC RC TV DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			
			logger.info(ApplicationConstants.LogMessageKeys.SAVENCDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			OTransactionsDataFactory factory = new OTransactionsDataFactory();
			factory.saveAgencyNCDBCData(requestParams, agencyId);
			fetchNCData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.SAVENCDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.STATUS.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,ApplicationConstants.SUCCESS_STATUS_STRING);

		}catch(BusinessException be){
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.SAVENCDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
					agencyId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deleteNCData(HttpServletRequest request, HttpServletResponse response){		
		MessageObject msgObj = new MessageObject(5723, "DELETE NC DBC DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {			
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter("dataId"));
			long  starId = Long.parseLong(request.getParameter("bankId"));
			logger.info("Received request for /OTransactionsDataServiceBean/deleteNCDataOne  --->  AGENCY_ID : "
    				+ " - {}" + ", ITEM ID :"+" - {}",agencyId,itemId);

			
			OTransactionsDataFactory factory = new OTransactionsDataFactory();
			factory.deleteAgencyNCDBCData(itemId, agencyId,starId);
			fetchNCData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
		
			logger.info(ApplicationConstants.LogMessageKeys.DELETENCDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()  +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ApplicationConstants.paramKeys.SEPERATOR.getValue()+ApplicationConstants.actionStatusKeys.STATUS.toString()
					+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId,itemId,
					ApplicationConstants.SUCCESS_STATUS_STRING);

		}catch(BusinessException be){
			msgObj.setMessageText(be.getExceptionMessage());
			
			logger.info(ApplicationConstants.LogMessageKeys.DELETENCDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
    				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()  +ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ApplicationConstants.paramKeys.ITEMID.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ApplicationConstants.paramKeys.SEPERATOR.getValue()+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId,itemId,be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

}
