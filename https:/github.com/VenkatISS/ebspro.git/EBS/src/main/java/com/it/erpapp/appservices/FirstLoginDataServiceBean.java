package com.it.erpapp.appservices;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.it.erpapp.commons.ApplicationConstants;
import com.it.erpapp.framework.AgencyFactory;
import com.it.erpapp.framework.MyProfileDataFactory;
import com.it.erpapp.framework.PPMasterDataFactory;
import com.it.erpapp.framework.model.vos.AgencyVO;
import com.it.erpapp.response.MessageObject;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class FirstLoginDataServiceBean {
	Logger logger = LoggerFactory.getLogger(FirstLoginDataServiceBean.class);

	public void saveAgencyFirstLoginData(HttpServletRequest request,HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(1003, "SAVE AGENCY FIRST LOGIN DATA",
				ApplicationConstants.ERROR_STATUS_STRING);

		String agencyId = request.getParameter("agencyId");
		String fYear = request.getParameter("fy");
		String tarOBalance = request.getParameter("tarob");
		String starOBalance = request.getParameter("starob");
		String cashBalance = request.getParameter("cashb");
		String effectiveDate = request.getParameter("ed");
		String btbinvc = request.getParameter("btbInvCounter");
		String btcinvc = request.getParameter("btcInvCounter");
		String ujwob = request.getParameter("ujw");

		try {
			logger.info(
					ApplicationConstants.LogMessageKeys.SAVEAGENCYFIRSTLOGINDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.FYEAR.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.TAROBALANCE.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.STAROBALANCE.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.CASHBALANCE.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.EFFECTIVEDATE.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue() + "B2B INVOICE COUNTER :"
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue() + "B2C INVOICE COUNTER :"
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, fYear, tarOBalance, starOBalance, cashBalance, effectiveDate, btbinvc, btcinvc, ujwob);

			HttpSession session = request.getSession(true);
			AgencyFactory af = new AgencyFactory();
			af.saveAgencyFirstLoginData(request,response);
               
			//Services Data 10/05/2019			
			PPMasterDataFactory mdf = new PPMasterDataFactory();
			mdf.saveAgencyServicesGST(agencyId);
			//Services Data 10/05/2019
			MyProfileDataFactory mpdf = new MyProfileDataFactory();
			AgencyVO agencyVO = mpdf.getProfileData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code());
			session.setAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue(), agencyVO);
			request.setAttribute("fl", "0");
			Map<String, Object> homepagedetails = af.fetchHomePageDetails(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code());
			session.setAttribute("details", homepagedetails);

			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.SAVEAGENCYFIRSTLOGINDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SAVEAGENCYFIRSTLOGINDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}

		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

}
