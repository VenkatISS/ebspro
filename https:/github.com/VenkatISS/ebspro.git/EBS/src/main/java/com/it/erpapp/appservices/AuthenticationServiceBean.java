package com.it.erpapp.appservices;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.it.erpapp.cache.CacheManager;
import com.it.erpapp.commons.ApplicationConstants;
import com.it.erpapp.framework.AgencyFactory;
import com.it.erpapp.framework.CacheFactory;
import com.it.erpapp.framework.model.vos.AgencyVO;
import com.it.erpapp.response.MessageObject;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class AuthenticationServiceBean {

	Logger logger = LoggerFactory.getLogger(AuthenticationServiceBean.class);

	long agencyId;
	String password;
	String emaildForFPWD;

	public void validateLogin(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(1001, "AGENCY LOGIN", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter("ai"));
			password = request.getParameter("pwd");

			logger.info(ApplicationConstants.LogMessageKeys.VALIDATE_DEALER_LOGIN.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.PASSWORD.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, password);

			// Set Agency Details in Session
			AgencyFactory af = new AgencyFactory();
			HttpSession session = request.getSession(true);
			AgencyVO agencyVO = af.validateLogin(request, response);
			if (agencyVO.getIs_ftl() == 0) {
				msgObj.setMessageStatus("FIRST LOGIN");
				request.setAttribute("showFirstloginDiv", "firstloginDiv");

				logger.info(
						ApplicationConstants.LogMessageKeys.VALIDATE_DEALER_LOGIN.getValue()
								+ ApplicationConstants.paramKeys.PARAM.getValue()
								+ ApplicationConstants.paramKeys.AGENCYID.getValue()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
								+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
								+ ApplicationConstants.actionStatusKeys.STATUS.toString()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
						agencyId, ApplicationConstants.actionStatusKeys.FIRSTLOGIN.toString());

			} else {
				msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

				logger.info(
						ApplicationConstants.LogMessageKeys.VALIDATE_DEALER_LOGIN.getValue()
								+ ApplicationConstants.paramKeys.PARAM.getValue()
								+ ApplicationConstants.paramKeys.AGENCYID.getValue()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
								+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
								+ ApplicationConstants.actionStatusKeys.STATUS.toString()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
						agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

			}
			session.setAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue(), agencyVO);
			if (!CacheManager.isCacheLoaded) {
				// Call Cache For Data
				CacheFactory cf = new CacheFactory();
				cf.getStatutoryItemsData();
				cf.getProductCatogoryData();
			}
			// call HomePageServiceBean for alerts data
			HomePageServiceBean hpsb = new HomePageServiceBean();
			hpsb.fetchAlertsStatutoryData(request, response);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.VALIDATE_DEALER_LOGIN.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	// forgot password
	public void sendResetPasswordMail(HttpServletRequest request,HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(1005, "AGENCY FORGOT PASSWORD",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			emaildForFPWD = request.getParameter("femailId");

			logger.info(ApplicationConstants.LogMessageKeys.SENDRESETPASSWORDMAIL.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue() + ApplicationConstants.paramKeys.EMAIL.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), emaildForFPWD);

			// Set Agency Details in Session
			AgencyFactory af = new AgencyFactory();
			af.sendResetPasswordMail(request,response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			request.setAttribute("showDIV1", "successDiv1");

			logger.info(ApplicationConstants.LogMessageKeys.SENDRESETPASSWORDMAIL.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue() + ApplicationConstants.paramKeys.EMAIL.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.STATUS.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), emaildForFPWD,
					ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SENDRESETPASSWORDMAIL.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue() + ApplicationConstants.paramKeys.EMAIL.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), emaildForFPWD, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	// update password
	public void updatePassword(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession(true);

		MessageObject msgObj = new MessageObject(5011, ApplicationConstants.paramKeys.ERROR.toString(),
				"UNABLE TO PROCESS REQUEST");
		String agencycode = (String) session.getAttribute(ApplicationConstants.paramKeys.AGENCY_ID.getValue());
		String activationCode = (String) session.getAttribute("activationCode");
		String newPassword = request.getParameter("fnpwd");
		long agencyCode = Long.parseLong(agencycode);

		logger.info(ApplicationConstants.LogMessageKeys.UPDATEPASSWORD.getValue()
				+ ApplicationConstants.paramKeys.PARAM.getValue() + ApplicationConstants.paramKeys.AGENCYID.getValue()
				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
				+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
				+ ApplicationConstants.paramKeys.ACTIVATIONCODE.toString()
				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
				+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
				+ ApplicationConstants.paramKeys.NEWPASSWORD.toString()
				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencycode, activationCode, newPassword);

		if (!(StringUtils.isEmpty(agencycode) && StringUtils.isEmpty(activationCode))) {
			try {
				AgencyFactory af = new AgencyFactory();
				af.updatePassword(agencyCode, activationCode, newPassword);
				msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
				msgObj.setMessageStatus("PASSWORD UPDATED - PLEASE LOGIN");

				logger.info(
						ApplicationConstants.LogMessageKeys.UPDATEPASSWORD.getValue()
								+ ApplicationConstants.paramKeys.PARAM.getValue()
								+ ApplicationConstants.paramKeys.AGENCYID.getValue()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
								+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
								+ ApplicationConstants.paramKeys.ACTIVATIONCODE.toString()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
								+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
								+ ApplicationConstants.paramKeys.NEWPASSWORD.toString()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
								+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
								+ ApplicationConstants.actionStatusKeys.STATUS.toString()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
						agencycode, activationCode, newPassword, ApplicationConstants.SUCCESS_STATUS_STRING);

			} catch (Exception e) {
				e.printStackTrace();

				logger.info(
						ApplicationConstants.LogMessageKeys.UPDATEPASSWORD.getValue()
								+ ApplicationConstants.paramKeys.PARAM.getValue()
								+ ApplicationConstants.paramKeys.AGENCYID.getValue()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
								+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
								+ ApplicationConstants.paramKeys.ACTIVATIONCODE.toString()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
								+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
								+ ApplicationConstants.paramKeys.NEWPASSWORD.toString()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
								+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
								+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
						agencycode, activationCode, newPassword, e);

			}
		} else {
			msgObj.setMessageStatus(ApplicationConstants.paramKeys.ERROR.toString());
			msgObj.setMessageStatus("PASSWORD UPDATION FAILED - TRY AGAIN");

			logger.info(
					ApplicationConstants.LogMessageKeys.UPDATEPASSWORD.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.ACTIVATIONCODE.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.NEWPASSWORD.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencycode, activationCode, newPassword, ApplicationConstants.FAILURE_STATUS_STRING);

		}
		request.setAttribute("msg_obj", msgObj);
	}

	// Change Password
	public void changePassword(HttpServletRequest request,HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(1007, "INVALID OLD PASSWORD.PLEASE CHECK IT ONCE AND TRY AGAIN",
				"ERROR");
		String agencycode = request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue());

		String newPassword = request.getParameter("cnpwd");
		String oldPassword = request.getParameter("copwd");

		long agencyCode = Long.parseLong(agencycode);

		logger.info(ApplicationConstants.LogMessageKeys.CHANGEPASSWORD.getValue()
				+ ApplicationConstants.paramKeys.PARAM.getValue() + ApplicationConstants.paramKeys.AGENCYID.getValue()
				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
				+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
				+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
				+ ApplicationConstants.paramKeys.NEWPASSWORD.toString()
				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencycode, newPassword);

		if (!(StringUtils.isEmpty(agencycode))) {
			try {
				AgencyFactory af = new AgencyFactory();
				af.changePassword(agencyCode, newPassword, oldPassword);
				msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
				msgObj.setMessageText("PASSWORD CHANGED SUCCESSFULLY");
				request.setAttribute("showDIV", "successDiv");

				logger.info(
						ApplicationConstants.LogMessageKeys.CHANGEPASSWORD.getValue()
								+ ApplicationConstants.paramKeys.PARAM.getValue()
								+ ApplicationConstants.paramKeys.AGENCYID.getValue()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
								+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
								+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
								+ ApplicationConstants.paramKeys.NEWPASSWORD.toString()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
								+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
								+ ApplicationConstants.actionStatusKeys.STATUS.toString()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
						agencycode, newPassword, ApplicationConstants.SUCCESS_STATUS_STRING);

			} catch (Exception e) {
				e.printStackTrace();

				logger.info(ApplicationConstants.LogMessageKeys.CHANGEPASSWORD.getValue()
						+ ApplicationConstants.paramKeys.PARAM.getValue()
						+ ApplicationConstants.paramKeys.AGENCYID.getValue()
						+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
						+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
						+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
						+ ApplicationConstants.paramKeys.NEWPASSWORD.toString()
						+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
						+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
						+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
						+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencycode, newPassword, e);

			}
		} else {
			msgObj.setMessageStatus(ApplicationConstants.paramKeys.ERROR.toString());
			msgObj.setMessageStatus("PASSWORD UPDATION FAILED - TRY AGAIN");

			logger.info(
					ApplicationConstants.LogMessageKeys.UPDATEPASSWORD.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.NEWPASSWORD.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencycode, newPassword, ApplicationConstants.FAILURE_STATUS_STRING);

		}
		request.setAttribute("msg_obj", msgObj);
	}

	public void logoutUser(HttpServletRequest request,HttpServletResponse response) {

		HttpSession session = request.getSession(true);

		String agencyid = (String) session.getAttribute(ApplicationConstants.paramKeys.AGENCY_ID.getValue());
		session.invalidate();
		MessageObject msgObj = new MessageObject(1000, "SUCCESSFULLY LOGGED OUT",
				ApplicationConstants.SUCCESS_STATUS_STRING);
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);

		logger.info(ApplicationConstants.LogMessageKeys.LOGOUTUSER.getValue()
				+ ApplicationConstants.paramKeys.PARAM.getValue() + ApplicationConstants.paramKeys.AGENCYID.getValue()
				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
				+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
				+ ApplicationConstants.paramKeys.ACTIVATIONCODE.toString()
				+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyid,
				ApplicationConstants.LogMessageKeys.SUCCESSFULLYLOGGEDOUT.getValue());

	}
}