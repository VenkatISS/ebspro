package com.it.erpapp.appservices;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.it.erpapp.commons.ApplicationConstants;
import com.it.erpapp.framework.CacheFactory;
import com.it.erpapp.framework.PPMasterDataFactory;
import com.it.erpapp.framework.model.vos.AgencyVO;
import com.it.erpapp.response.MessageObject;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class PPMasterDataServiceBean {

	Logger logger = LoggerFactory.getLogger(PPMasterDataServiceBean.class);

	// Equipment Data
	long agencyId;
	long itemId;

	// Equipment Data
	public void fetchAgencyEquipmentData(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(3101, "FETCH EQUIPMENT DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHAGENCYEQUIPMENTDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("cylinder_types_list", (new Gson().toJson(new CacheFactory().getCylinderTypesData())));
			PPMasterDataFactory mdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(mdf.getAgencyEquipmentData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("dequipment_data", (new Gson().toJson(mdf.getAgencyDEquipmentData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));

			request.setAttribute("astock_data", (new Gson().toJson(mdf.getAgencyStockData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHAGENCYEQUIPMENTDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			logger.info(ApplicationConstants.LogMessageKeys.FETCHAGENCYEQUIPMENTDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);
		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void saveAgencyEquipmentData(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(3102, "SAVE EQUIPMENT DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.SAVEAGENCYEQUIPMENTDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("cylinder_types_list", (new Gson().toJson(new CacheFactory().getCylinderTypesData())));
			Map<String, String[]> requestParams = request.getParameterMap();
			PPMasterDataFactory mdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data",
					(new Gson().toJson(mdf.saveAgencyEquipmentData(requestParams, agencyId))));
			request.setAttribute("dequipment_data", (new Gson().toJson(mdf.getAgencyDEquipmentData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			
			request.setAttribute("astock_data", (new Gson().toJson(mdf.getAgencyStockData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SAVEAGENCYEQUIPMENTDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SAVEAGENCYEQUIPMENTDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deleteAgencyEquipmentData(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(3103, "DELETE EQUIPMENT DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.ITEM_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.DELETEAGENCYEQUIPMENTDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.ITEMID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, itemId);

			request.setAttribute("cylinder_types_list", (new Gson().toJson(new CacheFactory().getCylinderTypesData())));
			PPMasterDataFactory mdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data",
					(new Gson().toJson(mdf.deleteAgencyEquipmentData(itemId, agencyId))));
			request.setAttribute("dequipment_data", (new Gson().toJson(mdf.getAgencyDEquipmentData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("astock_data", (new Gson().toJson(mdf.getAgencyStockData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.DELETEAGENCYEQUIPMENTDATA.getValue()
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

			logger.info(ApplicationConstants.LogMessageKeys.DELETEAGENCYEQUIPMENTDATA.getValue()
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

	// Fetch Stock Details
	public void fetchAgencyEquipmentStockData(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(3101, "FETCH EQUIPMENT DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHAGENCYEQUIPMENTDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("cylinder_types_list", (new Gson().toJson(new CacheFactory().getCylinderTypesData())));
			PPMasterDataFactory mdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(mdf.getAgencyEquipmentData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("dequipment_data", (new Gson().toJson(mdf.getAgencyDEquipmentData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));

			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHAGENCYEQUIPMENTDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			logger.info(ApplicationConstants.LogMessageKeys.FETCHAGENCYEQUIPMENTDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);
		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	// ARB Data
	public void fetchAgencyARBData(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(3111, "FETCH ARB DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHAGENCYARBDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			PPMasterDataFactory mdf = new PPMasterDataFactory();
			request.setAttribute("arb_types_list", (new Gson().toJson(new CacheFactory().getARBTypesData())));
			request.setAttribute("arb_data", (new Gson().toJson(mdf.getAgencyAllARBData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			
			request.setAttribute("astock_data", (new Gson().toJson(mdf.getAgencyStockData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHAGENCYARBDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			logger.info(ApplicationConstants.LogMessageKeys.FETCHAGENCYARBDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void saveAgencyARBData(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(3112, "SAVE ARB DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.SAVEAGENCYARBDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("arb_types_list", (new Gson().toJson(new CacheFactory().getARBTypesData())));
			Map<String, String[]> requestParams = request.getParameterMap();
			PPMasterDataFactory mdf = new PPMasterDataFactory();
			request.setAttribute("arb_data", (new Gson().toJson(mdf.saveAgencyARBData(requestParams, agencyId))));
			
			request.setAttribute("astock_data", (new Gson().toJson(mdf.getAgencyStockData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.SAVEAGENCYARBDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			logger.info(ApplicationConstants.LogMessageKeys.SAVEAGENCYARBDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deleteAgencyARBData(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(3113, "DELETE ARB DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.ITEM_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.DELETEAGENCYARBDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.ITEMID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, itemId);

			PPMasterDataFactory mdf = new PPMasterDataFactory();
			request.setAttribute("arb_types_list", (new Gson().toJson(new CacheFactory().getARBTypesData())));
			request.setAttribute("arb_data", (new Gson().toJson(mdf.deleteAgencyARBData(itemId, agencyId))));
			
			request.setAttribute("astock_data", (new Gson().toJson(mdf.getAgencyStockData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.DELETEAGENCYARBDATA.getValue()
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

			logger.info(ApplicationConstants.LogMessageKeys.DELETEAGENCYARBDATA.getValue()
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

	// Services Data
	public void fetchAgencyServicesData(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(3121, "FETCH SERVICES DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {

			agencyId = ((AgencyVO) request.getSession().getAttribute("agencyVO")).getAgency_code();
			logger.info(ApplicationConstants.LogMessageKeys.FETCHAGENCYSERVICESDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			PPMasterDataFactory mdf = new PPMasterDataFactory();
			request.setAttribute("service_types_list", (new Gson().toJson(new CacheFactory().getServiceTypesData())));
			request.setAttribute("services_data", (new Gson().toJson(mdf.getAgencyServicesData(
					((AgencyVO) request.getSession().getAttribute("agencyVO")).getAgency_code()))));
			request.setAttribute("services_gst", (new Gson().toJson(mdf.getAgencyServicesGTData(
					((AgencyVO) request.getSession().getAttribute("agencyVO")).getAgency_code()))));

			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHAGENCYSERVICESDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.FETCHAGENCYSERVICESDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	// update GST APPLICABLE PERCENTAGE Services Data
	
		public void updateServiceGSTApplicablePercentage(HttpServletRequest request, HttpServletResponse response) {

			MessageObject msgObj = new MessageObject(3124, "FETCH SERVICES DATA", ApplicationConstants.ERROR_STATUS_STRING);
			try {

				agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
				String gstper=request.getParameter("gstp");
				logger.info(ApplicationConstants.LogMessageKeys.FETCHAGENCYSERVICESDATA.getValue()
						+ ApplicationConstants.paramKeys.PARAM.getValue()
						+ ApplicationConstants.paramKeys.AGENCYID.getValue()
						+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

				PPMasterDataFactory mdf = new PPMasterDataFactory();
				request.setAttribute("save_services_data",
						(new Gson().toJson(mdf.updateServiceGSTPercentage(agencyId,gstper))));
				request.setAttribute("service_types_list", (new Gson().toJson(new CacheFactory().getServiceTypesData())));
				request.setAttribute("services_data", (new Gson().toJson(mdf.getAgencyServicesData(
						((AgencyVO) request.getSession().getAttribute("agencyVO")).getAgency_code()))));
				request.setAttribute("services_gst", (new Gson().toJson(mdf.getAgencyServicesGTData(
						((AgencyVO) request.getSession().getAttribute("agencyVO")).getAgency_code()))));
				
				msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
				logger.info(
						ApplicationConstants.LogMessageKeys.FETCHAGENCYSERVICESDATA.getValue()
								+ ApplicationConstants.paramKeys.PARAM.getValue()
								+ ApplicationConstants.paramKeys.AGENCYID.getValue()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
								+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
								+ ApplicationConstants.actionStatusKeys.STATUS.toString()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
						agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

			} catch (BusinessException be) {
				msgObj.setMessageText(be.getExceptionMessage());

				logger.info(ApplicationConstants.LogMessageKeys.FETCHAGENCYSERVICESDATA.getValue()
						+ ApplicationConstants.paramKeys.PARAM.getValue()
						+ ApplicationConstants.paramKeys.AGENCYID.getValue()
						+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
						+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
						+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
						+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

			}
			request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
		}
	// Area Code Data
	public void fetchAgencyAreaCodeData(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(3131, "FETCH AREA CODE DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {

			agencyId = ((AgencyVO) request.getSession().getAttribute("agencyVO")).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHAGENCYAREACODEDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			PPMasterDataFactory mdf = new PPMasterDataFactory();
			request.setAttribute("area_codes_data", (new Gson().toJson(mdf.getAgencyAreaCodeData(
					((AgencyVO) request.getSession().getAttribute("agencyVO")).getAgency_code()))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHAGENCYAREACODEDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			logger.info(ApplicationConstants.LogMessageKeys.FETCHAGENCYAREACODEDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void saveAgencyAreaCodeData(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(3132, "SAVE AREA CODE DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.SAVEAGENCYAREACODEDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			PPMasterDataFactory mdf = new PPMasterDataFactory();
			request.setAttribute("area_codes_data",
					(new Gson().toJson(mdf.saveAgencyAreaCodeData(requestParams, agencyId))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.SAVEAGENCYAREACODEDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SAVEAGENCYAREACODEDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deleteAgencyAreaCodeData(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(3133, "DELETE AREA CODE DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {

			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.ITEM_ID.getValue()));
			logger.info(ApplicationConstants.LogMessageKeys.DELETEAGENCYAREACODEDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.ITEMID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, itemId);

			PPMasterDataFactory mdf = new PPMasterDataFactory();
			request.setAttribute("area_codes_data",
					(new Gson().toJson(mdf.deleteAgencyAreaCodeData(itemId, agencyId))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.DELETEAGENCYAREACODEDATA.getValue()
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

			logger.info(ApplicationConstants.LogMessageKeys.DELETEAGENCYAREACODEDATA.getValue()
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

	// Refill Price Data
	public void fetchAgencyRefillPriceData(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(3201, "FETCH REFILL PRICE DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {

			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHAGENCYREFILLPRICEDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("cylinder_types_list", (new Gson().toJson(new CacheFactory().getCylinderTypesData())));
			PPMasterDataFactory mdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(mdf.getAgencyEquipmentData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("refill_prices_data", (new Gson().toJson(mdf.getAgencyRefillPriceData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHAGENCYREFILLPRICEDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			logger.info(ApplicationConstants.LogMessageKeys.FETCHAGENCYREFILLPRICEDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void saveAgencyRefillPriceData(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(3202, "SAVE REFILL PRICE DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			request.setAttribute("checkDisplay", "1");

			agencyId = Long.parseLong(request.getParameter("agencyId"));
			logger.info(ApplicationConstants.LogMessageKeys.SAVEAGENCYREFILLPRICEDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("cylinder_types_list", (new Gson().toJson(new CacheFactory().getCylinderTypesData())));
			PPMasterDataFactory mdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(mdf.getAgencyEquipmentData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			Map<String, String[]> requestParams = request.getParameterMap();
			request.setAttribute("refill_prices_data",
					(new Gson().toJson(mdf.saveAgencyRefillPriceData(requestParams, agencyId))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.SAVEAGENCYREFILLPRICEDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SAVEAGENCYREFILLPRICEDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deleteAgencyRefillPriceData(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(3203, "DELETE REFILL PRICE DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			request.setAttribute("checkDisplay", "1");

			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.ITEM_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.DELETEAGENCYREFILLPRICEDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.ITEMID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, itemId);

			request.setAttribute("cylinder_types_list", (new Gson().toJson(new CacheFactory().getCylinderTypesData())));
			PPMasterDataFactory mdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(mdf.getAgencyEquipmentData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("refill_prices_data",
					(new Gson().toJson(mdf.deleteAgencyRefillPriceData(itemId, agencyId))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.DELETEAGENCYREFILLPRICEDATA.getValue()
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

			logger.info(ApplicationConstants.LogMessageKeys.DELETEAGENCYREFILLPRICEDATA.getValue()
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

	// ARB Price Data
	public void fetchAgencyARBPriceData(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(3211, "FETCH ARB PRICES DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {

			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHAGENCYARBPRICEDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			PPMasterDataFactory mdf = new PPMasterDataFactory();
			request.setAttribute("arb_types_list", (new Gson().toJson(new CacheFactory().getARBTypesData())));
			request.setAttribute("arb_data", (new Gson().toJson(mdf.getAgencyARBData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("arb_prices_data", (new Gson().toJson(mdf.getAgencyARBPriceData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHAGENCYARBPRICEDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.FETCHAGENCYARBPRICEDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void saveAgencyARBPriceData(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(3212, "SAVE ARB PRICES DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			request.setAttribute("checkDisplay", "1");

			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.SAVEAGENCYARBPRICEDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);
			Map<String, String[]> requestParams = request.getParameterMap();
			PPMasterDataFactory mdf = new PPMasterDataFactory();
			request.setAttribute("arb_types_list", (new Gson().toJson(new CacheFactory().getARBTypesData())));
			request.setAttribute("arb_data", (new Gson().toJson(mdf.getAgencyARBData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("arb_prices_data",
					(new Gson().toJson(mdf.saveAgencyARBPriceData(requestParams, agencyId))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.SAVEAGENCYARBPRICEDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SAVEAGENCYARBPRICEDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deleteAgencyARBPriceData(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(3213, "DELETE ARB PRICES DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			request.setAttribute("checkDisplay", "1");

			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.ITEM_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.DELETEAGENCYARBPRICEDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.ITEMID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, itemId);

			PPMasterDataFactory mdf = new PPMasterDataFactory();
			request.setAttribute("arb_types_list", (new Gson().toJson(new CacheFactory().getARBTypesData())));
			request.setAttribute("arb_data", (new Gson().toJson(mdf.getAgencyARBData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("arb_prices_data",
					(new Gson().toJson(mdf.deleteAgencyARBPriceData(itemId, agencyId))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.DELETEAGENCYARBPRICEDATA.getValue()
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

			logger.info(ApplicationConstants.LogMessageKeys.DELETEAGENCYARBPRICEDATA.getValue()
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

	// BOM Data
	public void fetchAgencyBOMData(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(3141, "FETCH BOM DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {

			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHAGENCYBOMDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("cylinder_types_list", (new Gson().toJson(new CacheFactory().getCylinderTypesData())));
			request.setAttribute("service_types_list", (new Gson().toJson(new CacheFactory().getServiceTypesData())));
			request.setAttribute("arb_types_list", (new Gson().toJson(new CacheFactory().getARBTypesData())));
			request.setAttribute("prod_types_list", (new Gson().toJson(new CacheFactory().getProductCatogoryData())));
			PPMasterDataFactory mdf = new PPMasterDataFactory();
			request.setAttribute("arb_data", (new Gson().toJson(mdf.getAgencyAllARBData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("bom_data", (new Gson().toJson(mdf.getAgencyBOMData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHAGENCYBOMDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.FETCHAGENCYBOMDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void saveAgencyBOMData(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(3142, "SAVE BOM DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			logger.info(ApplicationConstants.LogMessageKeys.SAVEAGENCYBOMDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();
			PPMasterDataFactory mdf = new PPMasterDataFactory();
			mdf.saveAgencyBOMData(requestParams, agencyId);
			fetchAgencyBOMData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.SAVEAGENCYBOMDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			logger.info(ApplicationConstants.LogMessageKeys.SAVEAGENCYBOMDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void deleteAgencyBOMData(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(3143, "DELETE BOM DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {

			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			itemId = Long.parseLong(request.getParameter("dataId"));
			logger.info(ApplicationConstants.LogMessageKeys.DELETEAGENCYBOMDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.ITEMID.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, itemId);

			PPMasterDataFactory mdf = new PPMasterDataFactory();
			mdf.deleteAgencyBOMData(itemId, agencyId);
			fetchAgencyBOMData(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.DELETEAGENCYBOMDATA.getValue()
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

			logger.info(ApplicationConstants.LogMessageKeys.DELETEAGENCYBOMDATA.getValue()
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

}
