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
import com.it.erpapp.framework.PPMasterDataFactory;
import com.it.erpapp.framework.TransactionsDataFactory;
import com.it.erpapp.framework.model.vos.AgencyVO;
import com.it.erpapp.response.MessageObject;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class TransactionsDataServiceBean {

	Logger logger = LoggerFactory.getLogger(TransactionsDataServiceBean.class);
	long agencyId;
	long itemId;
	long bankId;

	// Purchase Invoices
	public void fetchPurchaseInvoices(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(5101, "FETCH PURCHASE INVOICES DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHPURCHASEINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("bank_data", (new Gson().toJson(mdf.getAgencyBankData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code()))));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(ppmdf.getAgencyEquipmentData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("pi_data", (new Gson().toJson(factory.getAgencyPurchaseInvoices(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));

			request.setAttribute("arb_pi_data", (new Gson().toJson(factory.getAgencyARBPurchaseInvoices(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));

			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHPURCHASEINVOICES.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

			HttpSession session = request.getSession(true);
			AgencyFactory af = new AgencyFactory();
			session.setAttribute("agencyVO", (af.fetchAgencyVO(agencyId)));

			request.setAttribute("urlValue", "2");

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.FETCHPURCHASEINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void savePurchaseInvoices(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(5102, "SAVE PURCHASE INVOICE DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.SAVEPURCHASEINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("bank_data", (new Gson().toJson(mdf.getAgencyBankData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(ppmdf.getAgencyEquipmentData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			Map<String, String[]> requestParams = request.getParameterMap();
			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("pi_data",
					(new Gson().toJson(factory.saveAgencyPurchaseInvoice(requestParams, agencyId))));
			request.setAttribute("arb_pi_data", (new Gson().toJson(factory.getAgencyARBPurchaseInvoices(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));

			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.SAVEPURCHASEINVOICES.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

			HttpSession session = request.getSession(true);
			AgencyFactory af = new AgencyFactory();
			session.setAttribute("agencyVO", (af.fetchAgencyVO(agencyId)));
		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SAVEPURCHASEINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deletePurchaseInvoices(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(5103, "DELETE PURCHASE INVOICE DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {

			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter("dataId"));
			bankId = Long.parseLong(request.getParameter("bankId"));
			logger.info(ApplicationConstants.LogMessageKeys.DELETEPURCHASEINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.ITEMID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.BANKID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, itemId, bankId);

			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("bank_data", (new Gson().toJson(mdf.getAgencyBankData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(ppmdf.getAgencyEquipmentData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("pi_data",
					(new Gson().toJson(factory.deleteAgencyPurchaseInvoice(itemId, agencyId, bankId))));

			request.setAttribute("arb_pi_data", (new Gson().toJson(factory.getAgencyARBPurchaseInvoices(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));

			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.DELETEPURCHASEINVOICES.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.ITEMID.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.BANKID.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, itemId, bankId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			logger.info(ApplicationConstants.LogMessageKeys.DELETEPURCHASEINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.ITEMID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.BANKID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, itemId, bankId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	// ARB Purchase Invoices
	public void fetchARBPurchaseInvoices(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(5101, "FETCH PURCHASE INVOICES DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHARBPURCHASEINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("arb_types_list", (new Gson().toJson((new CacheFactory()).getARBTypesData())));
			request.setAttribute("cvo_data",
					(new Gson().toJson((new MasterDataFactory()).getAgencyAllCVOData(agencyId))));
			request.setAttribute("arb_data",
					(new Gson().toJson((new PPMasterDataFactory()).getAgencyAllARBData(agencyId))));
			request.setAttribute("arb_prices_data",
					(new Gson().toJson((new PPMasterDataFactory()).getAgencyARBPriceData(((AgencyVO) request
							.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
									.getAgency_code()))));

			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("arb_pi_data", (new Gson().toJson(factory.getAgencyARBPurchaseInvoices(agencyId))));

			request.setAttribute("pi_data", (new Gson().toJson(factory.getAgencyPurchaseInvoices(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));

			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHARBPURCHASEINVOICES.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

			HttpSession session = request.getSession(true);
			AgencyFactory af = new AgencyFactory();
			session.setAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue(), (af.fetchAgencyVO(agencyId)));
		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.FETCHARBPURCHASEINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void saveARBPurchaseInvoices(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(5102, "SAVE PURCHASE INVOICE DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.SAVEARBPURCHASEINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("arb_types_list", (new Gson().toJson((new CacheFactory()).getARBTypesData())));
			request.setAttribute("cvo_data",
					(new Gson().toJson((new MasterDataFactory()).getAgencyAllCVOData(agencyId))));
			Map<String, String[]> requestParams = request.getParameterMap();
			request.setAttribute("arb_data",
					(new Gson().toJson((new PPMasterDataFactory()).getAgencyAllARBData(agencyId))));
			request.setAttribute("arb_prices_data",
					(new Gson().toJson((new PPMasterDataFactory()).getAgencyARBPriceData(((AgencyVO) request
							.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
									.getAgency_code()))));
			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("arb_pi_data",
					(new Gson().toJson(factory.saveAgencyARBPurchaseInvoice(requestParams, agencyId))));

			request.setAttribute("pi_data", (new Gson().toJson(factory.getAgencyPurchaseInvoices(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));

			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.SAVEARBPURCHASEINVOICES.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

			HttpSession session = request.getSession(true);
			AgencyFactory af = new AgencyFactory();
			session.setAttribute("agencyVO", (af.fetchAgencyVO(agencyId)));
		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SAVEARBPURCHASEINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deleteARBPurchaseInvoices(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(5103, "DELETE PURCHASE INVOICE DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter("dataId"));

			logger.info(ApplicationConstants.LogMessageKeys.DELETEARBPURCHASEINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.ITEMID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, itemId);

			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("arb_types_list", (new Gson().toJson((new CacheFactory()).getARBTypesData())));
			request.setAttribute("cvo_data",
					(new Gson().toJson((new MasterDataFactory()).getAgencyAllCVOData(agencyId))));
			request.setAttribute("arb_data",
					(new Gson().toJson((new PPMasterDataFactory()).getAgencyAllARBData(agencyId))));
			request.setAttribute("arb_prices_data",
					(new Gson().toJson((new PPMasterDataFactory()).getAgencyARBPriceData(((AgencyVO) request
							.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
									.getAgency_code()))));

			request.setAttribute("arb_pi_data",
					(new Gson().toJson(factory.deleteAgencyARBPurchaseInvoice(itemId, agencyId))));

			request.setAttribute("pi_data", (new Gson().toJson(factory.getAgencyPurchaseInvoices(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));

			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.DELETEARBPURCHASEINVOICES.getValue()
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

			logger.info(ApplicationConstants.LogMessageKeys.DELETEARBPURCHASEINVOICES.getValue()
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

	// Other Purchase Invoices
	public void fetchOTHERPurchaseInvoices(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(5116, "FETCH OTHER PURCHASE INVOICES DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHOTHERPURCHASEINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("cvo_data",
					(new Gson().toJson((new MasterDataFactory()).getAgencyAllCVOData(agencyId))));
			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("oth_pi_data", (new Gson().toJson(factory.getAgencyOTHERPurchaseInvoices(agencyId))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHOTHERPURCHASEINVOICES.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

			HttpSession session = request.getSession(true);
			AgencyFactory af = new AgencyFactory();
			session.setAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue(), (af.fetchAgencyVO(agencyId)));

			request.setAttribute("urlValue", "2");

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.FETCHOTHERPURCHASEINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void saveOTHERPurchaseInvoices(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(5117, "SAVE OTHER PURCHASE INVOICE DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.SAVEOTHERPURCHASEINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("cvo_data",
					(new Gson().toJson((new MasterDataFactory()).getAgencyAllCVOData(agencyId))));
			Map<String, String[]> requestParams = request.getParameterMap();
			TransactionsDataFactory factory = new TransactionsDataFactory();
			factory.saveAgencyOTHERPurchaseInvoice(requestParams, agencyId);
			fetchOTHERPurchaseInvoices(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.SAVEOTHERPURCHASEINVOICES.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SAVEOTHERPURCHASEINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deleteOTHERPurchaseInvoices(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(5118, "DELETE PURCHASE INVOICE DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter("dataId"));

			logger.info(ApplicationConstants.LogMessageKeys.DELETEOTHERPURCHASEINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.ITEMID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, itemId);

			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("arb_types_list", (new Gson().toJson((new CacheFactory()).getARBTypesData())));
			request.setAttribute("cvo_data",
					(new Gson().toJson((new MasterDataFactory()).getAgencyAllCVOData(agencyId))));
			request.setAttribute("oth_pi_data",
					(new Gson().toJson(factory.deleteAgencyOTHERPurchaseInvoice(itemId, agencyId))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.DELETEOTHERPURCHASEINVOICES.getValue()
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

			logger.info(ApplicationConstants.LogMessageKeys.DELETEOTHERPURCHASEINVOICES.getValue()
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

	// DOM CYL Sales Invoices
	public void fetchDomesticCylinderSalesInvoices(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(5121, "FETCH DOMESTIC CYLINDER INVOICES DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHDOMESTICCYLINDERSALESINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.ITEMID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, itemId);

			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("bank_data", (new Gson().toJson(mdf.getAgencyAllBankData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(agencyId))));
			request.setAttribute("staff_data", (new Gson().toJson(mdf.getAgencyAllStaffData(agencyId))));
			request.setAttribute("cl_data", (new Gson().toJson(mdf.getAgencyCreditLimitsData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(ppmdf.getAgencyEquipmentData(agencyId))));
			request.setAttribute("refill_prices_data", (new Gson().toJson(ppmdf.getAgencyRefillPriceData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("area_codes_data", (new Gson().toJson(ppmdf.getAgencyAllAreaCodeData(agencyId))));
			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("drs_data", (new Gson().toJson(factory.getAgencyDOMSalesInvoices(agencyId))));
			request.setAttribute("dequipment_data", (new Gson().toJson(ppmdf.getAgencyDEquipmentData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));

			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHDOMESTICCYLINDERSALESINVOICES.getValue()
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

			HttpSession session = request.getSession(true);
			AgencyFactory af = new AgencyFactory();
			session.setAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue(), (af.fetchAgencyVO(agencyId)));
		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			logger.info(ApplicationConstants.LogMessageKeys.FETCHDOMESTICCYLINDERSALESINVOICES.getValue()
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

	public void saveDomesticCylinderSalesInvoices(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(5122, "SAVE DOMESTIC CYLINDER INVOICE DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.SAVEDOMESTICCYLINDERSALESINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.ITEMID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, itemId);

			Map<String, String[]> requestParams = request.getParameterMap();
			TransactionsDataFactory factory = new TransactionsDataFactory();
			factory.saveAgencyDOMSalesInvoice(requestParams, agencyId);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			fetchDomesticCylinderSalesInvoices(request, response);
			logger.info(
					ApplicationConstants.LogMessageKeys.SAVEDOMESTICCYLINDERSALESINVOICES.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SAVEDOMESTICCYLINDERSALESINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deleteDomesticCylinderSalesInvoices(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(5123, "DELETE DOMESTIC CYLINDER INVOICE DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter("dataId"));

			logger.info(ApplicationConstants.LogMessageKeys.DELETEDOMESTICCYLINDERSALESINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.ITEMID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, itemId);

			TransactionsDataFactory factory = new TransactionsDataFactory();
			factory.deleteAgencyDOMSalesInvoice(itemId, agencyId);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			fetchDomesticCylinderSalesInvoices(request, response);

			logger.info(
					ApplicationConstants.LogMessageKeys.DELETEDOMESTICCYLINDERSALESINVOICES.getValue()
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

			logger.info(ApplicationConstants.LogMessageKeys.DELETEDOMESTICCYLINDERSALESINVOICES.getValue()
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

	// Commercial Sales Invoices
	public void fetchCommercialSalesInvoices(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(5126, "FETCH COMMERCIAL SALES INVOICES DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHCOMMERCIALSALESINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("bank_data", (new Gson().toJson(mdf.getAgencyAllBankData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(agencyId))));
			request.setAttribute("staff_data", (new Gson().toJson(mdf.getAgencyAllStaffData(agencyId))));
			request.setAttribute("cl_data", (new Gson().toJson(mdf.getAgencyCreditLimitsData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));

			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(ppmdf.getAgencyEquipmentData(agencyId))));
			request.setAttribute("refill_prices_data", (new Gson().toJson(ppmdf.getAgencyRefillPriceData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("area_codes_data", (new Gson().toJson(ppmdf.getAgencyAllAreaCodeData(agencyId))));
			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("crs_data", (new Gson().toJson(factory.getAgencyCOMSalesInvoices(agencyId))));
			request.setAttribute("dequipment_data", (new Gson().toJson(ppmdf.getAgencyDEquipmentData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHCOMMERCIALSALESINVOICES.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

			HttpSession session = request.getSession(true);
			AgencyFactory af = new AgencyFactory();
			session.setAttribute("agencyVO", (af.fetchAgencyVO(agencyId)));
		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.FETCHCOMMERCIALSALESINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void saveCommercialSalesInvoices(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(5127, "SAVE COMMERCIAL SALES INVOICE DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.SAVECOMMERCIALSALESINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			TransactionsDataFactory factory = new TransactionsDataFactory();
			factory.saveAgencyCOMSalesInvoice(requestParams, agencyId);
			fetchCommercialSalesInvoices(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.SAVECOMMERCIALSALESINVOICES.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SAVECOMMERCIALSALESINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deleteCommercialSalesInvoices(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(5128, "DELETE COMMERCIAL SALES INVOICE DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter("dataId"));

			logger.info(ApplicationConstants.LogMessageKeys.DELETECOMMERCIALSALESINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.ITEMID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, itemId);

			TransactionsDataFactory factory = new TransactionsDataFactory();
			factory.deleteAgencyCOMSalesInvoice(itemId, agencyId);
			fetchCommercialSalesInvoices(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.DELETECOMMERCIALSALESINVOICES.getValue()
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

			logger.info(ApplicationConstants.LogMessageKeys.DELETECOMMERCIALSALESINVOICES.getValue()
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

	// ARB Sales Invoices
	public void fetchARBSalesInvoices(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(5131, "FETCH ARB SALES INVOICES DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHARBSALESINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("arb_types_list", (new Gson().toJson((new CacheFactory()).getARBTypesData())));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("bank_data", (new Gson().toJson(mdf.getAgencyAllBankData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(agencyId))));
			request.setAttribute("staff_data", (new Gson().toJson(mdf.getAgencyAllStaffData(agencyId))));
			request.setAttribute("cl_data", (new Gson().toJson(mdf.getAgencyCreditLimitsData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));

			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("arb_data", (new Gson().toJson(ppmdf.getAgencyAllARBData(agencyId))));
			request.setAttribute("arb_prices_data", (new Gson().toJson(ppmdf.getAgencyARBPriceData(agencyId))));
			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("arbs_data", (new Gson().toJson(factory.getAgencyARBSalesInvoices(agencyId))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHARBSALESINVOICES.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

			HttpSession session = request.getSession(true);
			AgencyFactory af = new AgencyFactory();
			session.setAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue(), (af.fetchAgencyVO(agencyId)));
		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.FETCHARBSALESINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void saveARBSalesInvoices(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(5132, "SAVE ARB SALES INVOICE DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.SAVEARBSALESINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			TransactionsDataFactory factory = new TransactionsDataFactory();
			factory.saveAgencyARBSalesInvoice(requestParams, agencyId);
			fetchARBSalesInvoices(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.SAVEARBSALESINVOICES.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SAVEARBSALESINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deleteARBSalesInvoices(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(5133, "DELETE ARB SALES INVOICE DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter("dataId"));

			logger.info(ApplicationConstants.LogMessageKeys.DELETEARBSALESINVOICES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.ITEMID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, itemId);

			TransactionsDataFactory factory = new TransactionsDataFactory();
			factory.deleteAgencyARBSalesInvoice(itemId, agencyId);
			fetchARBSalesInvoices(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.DELETEARBSALESINVOICES.getValue()
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

			logger.info(ApplicationConstants.LogMessageKeys.DELETEARBSALESINVOICES.getValue()
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

	// Quotations
	public void fetchQuotations(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(5141, "FETCH QUOTATIONS DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHQUOTATIONS.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			request.setAttribute("arb_types_list", (new Gson().toJson((new CacheFactory()).getARBTypesData())));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(agencyId))));
			request.setAttribute("staff_data", (new Gson().toJson(mdf.getAgencyAllStaffData(agencyId))));
			request.setAttribute("cl_data", (new Gson().toJson(mdf.getAgencyCreditLimitsData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(ppmdf.getAgencyEquipmentData(agencyId))));
			request.setAttribute("dequipment_data", (new Gson().toJson(ppmdf.getAgencyEquipmentData(agencyId))));
			request.setAttribute("arb_data", (new Gson().toJson(ppmdf.getAgencyAllARBData(agencyId))));
			request.setAttribute("refill_prices_data", (new Gson().toJson(ppmdf.getAgencyRefillPriceData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("arb_prices_data", (new Gson().toJson(ppmdf.getAgencyARBPriceData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("prod_types_list", (new Gson().toJson(new CacheFactory().getProductCatogoryData())));
			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("qtn_data", (new Gson().toJson(factory.getAgencyQuotations(agencyId))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHQUOTATIONS.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

			HttpSession session = request.getSession(true);
			AgencyFactory af = new AgencyFactory();
			session.setAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue(), (af.fetchAgencyVO(agencyId)));
		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.FETCHQUOTATIONS.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void saveQuotation(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(5142, "SAVE QUOTATIONS DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.SAVEQUOTATION.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			TransactionsDataFactory factory = new TransactionsDataFactory();
			factory.saveAgencyQuotation(requestParams, agencyId);
			fetchQuotations(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SAVEQUOTATION.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SAVEQUOTATION.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deleteQuotation(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(5143, "DELETE QUOTATIONS DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter("dataId"));

			logger.info(ApplicationConstants.LogMessageKeys.DELETEQUOTATION.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.ITEMID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, itemId);

			TransactionsDataFactory factory = new TransactionsDataFactory();
			factory.deleteAgencyQuotation(itemId, agencyId);
			fetchQuotations(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.DELETEQUOTATION.getValue()
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

			logger.info(ApplicationConstants.LogMessageKeys.DELETEQUOTATION.getValue()
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

	// Delivery Challans
	public void fetchDeliveryChallan(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(5151, "FETCH DELIVERY CHALLAN DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHDELIVERYCHALLAN.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			request.setAttribute("arb_types_list", (new Gson().toJson((new CacheFactory()).getARBTypesData())));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(agencyId))));
			request.setAttribute("fleet_data", (new Gson().toJson(mdf.getAgencyAllFleetData(agencyId))));
			request.setAttribute("staff_data", (new Gson().toJson(mdf.getAgencyAllStaffData(agencyId))));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(ppmdf.getAgencyEquipmentData(agencyId))));
			request.setAttribute("dequipment_data", (new Gson().toJson(ppmdf.getAgencyEquipmentData(agencyId))));
			request.setAttribute("arb_data", (new Gson().toJson(ppmdf.getAgencyAllARBData(agencyId))));
			request.setAttribute("refill_prices_data", (new Gson().toJson(ppmdf.getAgencyRefillPriceData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("arb_prices_data", (new Gson().toJson(ppmdf.getAgencyARBPriceData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("prod_types_list", (new Gson().toJson(new CacheFactory().getProductCatogoryData())));
			TransactionsDataFactory factory = new TransactionsDataFactory();
			request.setAttribute("dc_data", (new Gson().toJson(factory.getAgencyDeliveryChallan(agencyId))));
			request.setAttribute("drs_data", (new Gson().toJson(factory.getAgencyDOMSalesInvoices(agencyId))));
			request.setAttribute("crs_data", (new Gson().toJson(factory.getAgencyCOMSalesInvoices(agencyId))));
			request.setAttribute("cyldp_data", (new Gson().toJson(factory.getAgencyPurchaseInvoices(agencyId))));

			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHDELIVERYCHALLAN.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

			HttpSession session = request.getSession(true);
			AgencyFactory af = new AgencyFactory();
			session.setAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue(), (af.fetchAgencyVO(agencyId)));
		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.FETCHDELIVERYCHALLAN.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void saveDeliveryChallan(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(5152, "SAVE DELIVERY CHALLAN DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.SAVEDELIVERYCHALLAN.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			TransactionsDataFactory factory = new TransactionsDataFactory();
			factory.saveAgencyDeliveryChallan(requestParams, agencyId);
			fetchDeliveryChallan(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SAVEDELIVERYCHALLAN.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SAVEDELIVERYCHALLAN.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deleteDeliveryChallan(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(5153, "DELETE DELIVERY CHALLAN DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter("dataId"));

			logger.info(ApplicationConstants.LogMessageKeys.DELETEDELIVERYCHALLAN.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.ITEMID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, itemId);

			TransactionsDataFactory factory = new TransactionsDataFactory();
			factory.deleteAgencyDeliveryChallan(itemId, agencyId);
			fetchDeliveryChallan(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.DELETEDELIVERYCHALLAN.getValue()
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

			logger.info(ApplicationConstants.LogMessageKeys.DELETEDELIVERYCHALLAN.getValue()
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
	
	
	/*--------------new multi popup 29032019 start-------------------*/
	
	
    //Save & Fetch Popup Data



public void saveAndFetchPopUpData(HttpServletRequest request, HttpServletResponse response){
	
	MessageObject msgObj = new MessageObject(9999, "SAVE & FETCH POPUP DATA", ApplicationConstants.ERROR_STATUS_STRING);
	String logMessageKeys=ApplicationConstants.LogMessageKeys.SAVEANDFETCHPOPUPDATA.getValue();

	try {
	//	agencyId = Long.parseLong(request.getParameter("agencyId"));
		agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
		String effdate = request.getParameter("effDateInFTL");
		int popupPageId=Integer.parseInt(request.getParameter("popupPageId"));
		int popupId=Integer.parseInt(request.getParameter("popupId"));
		
		
		Map<String, String[]> requestParams = request.getParameterMap();
		PPMasterDataFactory pmdf = new PPMasterDataFactory();
		MasterDataFactory mdf = new MasterDataFactory();
		OTransactionsDataServiceBean otbean=new OTransactionsDataServiceBean();

		if(popupId==1){
		logMessageKeys=ApplicationConstants.LogMessageKeys.SAVECVODATA.getValue();

		logger.info(logMessageKeys
				+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),agencyId);

		request.getSession().setAttribute("cvo_data", (new Gson().toJson(mdf.saveAgencyCVOData(requestParams, agencyId, effdate))));
		
		}else if(popupId==2)
		{
			logMessageKeys=ApplicationConstants.LogMessageKeys.SAVEAGENCYEQUIPMENTDATA.getValue();
			
			logger.info(logMessageKeys
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("cylinder_types_list", (new Gson().toJson(new CacheFactory().getCylinderTypesData())));
			request.setAttribute("equipment_data",
					(new Gson().toJson(pmdf.saveAgencyEquipmentData(requestParams, agencyId))));
			request.setAttribute("dequipment_data", (new Gson().toJson(pmdf.getAgencyDEquipmentData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
		}else if(popupId==3)
		{
			logMessageKeys=ApplicationConstants.LogMessageKeys.SAVEAGENCYREFILLPRICEDATA.getValue();
			
			logger.info(logMessageKeys
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("cylinder_types_list", (new Gson().toJson(new CacheFactory().getCylinderTypesData())));
			request.setAttribute("equipment_data", (new Gson().toJson(pmdf.getAgencyEquipmentData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("refill_prices_data",
					(new Gson().toJson(pmdf.saveAgencyRefillPriceData(requestParams, agencyId))));

		}else if(popupId==4)
		{
			
			logMessageKeys=ApplicationConstants.LogMessageKeys.SAVEBANKDATA.getValue();
			logger.info(logMessageKeys
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("bank_data", (new Gson().toJson(mdf.saveAgencyBankData(requestParams, agencyId))));
		
		}else if(popupId==5)
		{
			logMessageKeys=ApplicationConstants.LogMessageKeys.SAVESTAFFDATA.getValue();
			logger.info(logMessageKeys
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute(ApplicationConstants.paramKeys.STAFF_DATA.getValue(),
					(new Gson().toJson(mdf.saveAgencyStaffData(requestParams, agencyId))));
		
		}else if(popupId==6)
		{
			logMessageKeys=ApplicationConstants.LogMessageKeys.SAVEAGENCYAREACODEDATA.getValue();

			logger.info(logMessageKeys
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);
			request.setAttribute("area_codes_data",
					(new Gson().toJson(pmdf.saveAgencyAreaCodeData(requestParams, agencyId))));

		}else if(popupId==7)
		{
			logMessageKeys=ApplicationConstants.LogMessageKeys.SAVEAGENCYARBDATA.getValue();

			logger.info(logMessageKeys
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("arb_types_list", (new Gson().toJson(new CacheFactory().getARBTypesData())));
			request.setAttribute("arb_data", (new Gson().toJson(pmdf.saveAgencyARBData(requestParams, agencyId))));
		}else if(popupId==8)
		{
			
			logMessageKeys=ApplicationConstants.LogMessageKeys.SAVEAGENCYARBPRICEDATA.getValue();

			logger.info(logMessageKeys
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("arb_types_list", (new Gson().toJson(new CacheFactory().getARBTypesData())));
			request.setAttribute("arb_data", (new Gson().toJson(pmdf.getAgencyARBData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("arb_prices_data",
					(new Gson().toJson(pmdf.saveAgencyARBPriceData(requestParams, agencyId))));
		}
		
		if(popupPageId==1){
		fetchDomesticCylinderSalesInvoices(request,response);
		}else if(popupPageId==2){
			fetchCommercialSalesInvoices(request, response);
		}else if(popupPageId==3){
			fetchARBSalesInvoices(request, response);
		}else if(popupPageId==4){
			fetchPurchaseInvoices(request, response);
		}else if(popupPageId==5){
			fetchARBPurchaseInvoices(request, response);
		}else if(popupPageId==6){
			fetchOTHERPurchaseInvoices(request, response);
			}else if(popupPageId==7){
				otbean.fetchReceiptsData(request, response);
			}else if(popupPageId==8){
				otbean.fetchPaymentsData(request, response);
			}else if(popupPageId==9){
				otbean.fetchBankTranxsData(request, response);
			}else if(popupPageId==10){
				otbean.fetchNCData(request, response);
			}else if(popupPageId==11){
				otbean.fetchRCData(request, response);
			}else if(popupPageId==12){
				otbean.fetchTVData(request, response);;
			}else if(popupPageId==13){
				fetchQuotations(request, response);
			}else if(popupPageId==14){
				fetchDeliveryChallan(request, response);
			}



		
		msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
		logger.info(logMessageKeys
				+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
				+ApplicationConstants.actionStatusKeys.STATUS.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
				agencyId,ApplicationConstants.SUCCESS_STATUS_STRING);

	}catch(BusinessException be){
		msgObj.setMessageText(be.getExceptionMessage());
		
		logger.info(logMessageKeys
				+ ApplicationConstants.paramKeys.PARAM.getValue()+ApplicationConstants.paramKeys.AGENCYID.getValue()
				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue() +ApplicationConstants.paramKeys.SEPERATOR.getValue()
				+ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()+ApplicationConstants.LogKeys.LOG_PARAM.getValue(),  
				agencyId,be);

	}
	request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
}


	

}
