package com.it.erpapp.appservices;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.it.erpapp.commons.ApplicationConstants;
import com.it.erpapp.framework.MyProfileDataFactory;
import com.it.erpapp.framework.model.vos.AgencyVO;
import com.it.erpapp.response.MessageObject;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class MyProfileServiceBean {

	Logger logger = LoggerFactory.getLogger(MyProfileServiceBean.class);
	long agencyId;
	String cpname;
	String cdmobile;
	String clnno;
	String cemailId;
	String cOffcAddr;
	String agencyoPin;
	String agencyPin;
	String agencynPin;
	String emailForpin;

	public void fetchProfilepageDetails(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(9001, "FETCH PROFILE PAGE DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHPROFILEPAGEDETAILS.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			HttpSession session = request.getSession(true);
			MyProfileDataFactory mpdf = new MyProfileDataFactory();

			AgencyVO agencyVO = mpdf.getProfileData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code());
			session.setAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue(), agencyVO);
			request.setAttribute("fl", "1");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHPROFILEPAGEDETAILS.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);
		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.FETCHPROFILEPAGEDETAILS.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);
		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void updateProfileDetails(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(9002, "UPDATE PROFILE PAGE DETAILS",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			cpname = request.getParameter("cpname");
			cdmobile = request.getParameter("cdmobile");
			clnno = request.getParameter("clnno");
//			cemailId = request.getParameter("cemailId");
			cOffcAddr = request.getParameter("cOffcAddr");

			logger.info(
					ApplicationConstants.LogMessageKeys.UPDATEPROFILEDETAILS.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.CONTACTPERSONNAME.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.CONTACTMOBILE.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.CONTACTLANDLINENO.toString()
//							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
//							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
//							+ ApplicationConstants.paramKeys.CONTACTEMAILID.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.CONTACTOFFCADDR.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
//					agencyId, cpname, cdmobile, clnno, cemailId, cOffcAddr);
					agencyId, cpname, cdmobile, clnno, cOffcAddr);		

			HttpSession session = request.getSession(true);
			MyProfileDataFactory mdf = new MyProfileDataFactory();

			Map<String, String> requestvals = new HashMap<>();
			requestvals.put("cpname", request.getParameter("cpname"));
			requestvals.put("cdmobile", request.getParameter("cdmobile"));
			requestvals.put("clnno", request.getParameter("clnno"));
//			requestvals.put("cemailId", request.getParameter("cemailId"));
			requestvals.put("cOffcAddr", request.getParameter("cOffcAddr"));

			AgencyVO agencyVO = mdf.updateProfileData(requestvals, agencyId);
			session.setAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue(), agencyVO);
			request.setAttribute("fl", "1");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.UPDATEPROFILEDETAILS.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.CONTACTPERSONNAME.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.CONTACTMOBILE.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.CONTACTLANDLINENO.toString()
//							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
//							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
//							+ ApplicationConstants.paramKeys.CONTACTEMAILID.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.CONTACTOFFCADDR.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
//					agencyId, cpname, cdmobile, clnno, cemailId, cOffcAddr, ApplicationConstants.SUCCESS_STATUS_STRING);
					agencyId, cpname, cdmobile, clnno, cOffcAddr, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(
					ApplicationConstants.LogMessageKeys.UPDATEPROFILEDETAILS.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.CONTACTPERSONNAME.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.CONTACTMOBILE.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.CONTACTLANDLINENO.toString()
//							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
//							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
//							+ ApplicationConstants.paramKeys.CONTACTEMAILID.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.CONTACTOFFCADDR.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
//					agencyId, cpname, cdmobile, clnno, cemailId, cOffcAddr, be);
							agencyId, cpname, cdmobile, clnno, cOffcAddr, be);
		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}
	
	// --------------------------SENDING CONFIRMATION MAIL TO USER'S THE NEW EMAIL ID AND UPDATING EMAILID -----------------------------
	public void sendingConfirmationMailAndUpdatingEmailId(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(9010, "SENDING CONFIRMATION MAIL TO USER'S THE NEW EMAIL ID AND UPDATING EMAILID ", ApplicationConstants.ERROR_STATUS_STRING);
		String oldEmailId;String newEmailId;
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			oldEmailId = request.getParameter("oldEmailId");
			newEmailId = request.getParameter("newEmailId");

			HttpSession session = request.getSession(true);
			MyProfileDataFactory mdf = new MyProfileDataFactory();

			Map<String, String> requestvals = new HashMap<>();
			requestvals.put("oldEmailId", request.getParameter("oldEmailId"));
			requestvals.put("newEmailId", request.getParameter("newEmailId"));

			AgencyVO agencyVO = mdf.sendMailAndUpdateRegisterdEmailId(requestvals, agencyId);
			session.setAttribute("agencyVO", agencyVO);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			msgObj.setMessageText("Confirmation Email Has been Sent To Your New Email Id.Please Confirm Your Id Through Your Mail.");
		
		} catch (BusinessException be) {			
			msgObj.setMessageText(be.getExceptionMessage());
			logger.info(ApplicationConstants.LogMessageKeys.FORGOTPINNUMBER.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);
			be.printStackTrace();
		} catch (Exception e) {			
			msgObj.setMessageText("UNABLE TO UPDATE EMAIL ID");
			logger.info(ApplicationConstants.LogMessageKeys.FORGOTPINNUMBER.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, e);
			e.printStackTrace();
		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void UpdateEmailId(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(9011, "UPDATING EMAILID ", ApplicationConstants.ERROR_STATUS_STRING);

		try {
			agencyId = Long.parseLong(request.getParameter("agencyId"));

			HttpSession session = request.getSession(true);
			MyProfileDataFactory mdf = new MyProfileDataFactory();

			Map<String, String> requestvals = new HashMap<>();
			requestvals.put("oldEmailId", request.getParameter("oldEmailId"));
			requestvals.put("newEmailId", request.getParameter("newEmailId"));

			AgencyVO agencyVO = mdf.updateRegisterdEmailId(requestvals, agencyId);
			session.setAttribute("agencyVO", agencyVO);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			msgObj.setMessageText("SUCCESSFULLY UPDATED EMAIL ID");
		
		} catch (Exception e) {
			msgObj.setMessageText("EMAIL ID HAS ALREADY BEEN UPDATED");
			logger.info(ApplicationConstants.LogMessageKeys.FORGOTPINNUMBER.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, e);
			e.printStackTrace();
		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}
	
	// save pin number
	public void savePinNumber(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(9002, "SAVE PIN NUMBER ", ApplicationConstants.ERROR_STATUS_STRING);
		try {

			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			HttpSession session = request.getSession(true);
			MyProfileDataFactory mdf = new MyProfileDataFactory();

			Map<String, String> requestvals = new HashMap<>();
			requestvals.put("agencyPin", request.getParameter("agencyPin"));

			AgencyVO agencyVO = mdf.savePinNumber(requestvals, agencyId);
			session.setAttribute("agencyVO", agencyVO);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			logger.info(ApplicationConstants.LogMessageKeys.SAVEPINNUMBER.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue(), agencyId, be);
		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	// changePinNumber
	public void changePinNumber(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(9002, "CHANGE PIN NUMBER ", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));

			HttpSession session = request.getSession(true);
			MyProfileDataFactory mdf = new MyProfileDataFactory();

			Map<String, String> requestvals = new HashMap<>();
			requestvals.put("agencyoPin", request.getParameter("agencyoPin"));
			requestvals.put("agencynPin", request.getParameter("agencynPin"));

			AgencyVO agencyVO = mdf.changePinNumber(requestvals, agencyId);
			session.setAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue(), agencyVO);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.CHANGEPINNUMBER.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue(), agencyId, be);
		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	// changePinNumber
	public void forgotPinNumber(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(9002, "CHANGE PIN NUMBER ", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			emailForpin = request.getParameter("emailIdforOtp");
			Random rnd = new Random();
			int generatedOtp = 100000 + rnd.nextInt(900000);
			request.setAttribute("generatedOtp", generatedOtp);

			HttpSession session = request.getSession(true);
			MyProfileDataFactory mdf = new MyProfileDataFactory();

			Map<String, String> requestvals = new HashMap<>();
			requestvals.put("emailIdforOtp", request.getParameter("emailIdforOtp"));

			AgencyVO agencyVO = mdf.forgotPinNumber(requestvals, agencyId, generatedOtp);
			session.setAttribute("agencyVO", agencyVO);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (Exception e) {

			logger.info(ApplicationConstants.LogMessageKeys.FORGOTPINNUMBER.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, e);
			e.printStackTrace();
		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	// uploadProfile
	public void uploadprofilepicToPath(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(9006, "UPLOAD PICTURE ", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			HttpSession session = request.getSession(true);
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			Part profilepic = request.getPart("datafile");
			if (profilepic != null) {
			}
			MyProfileDataFactory mdf = new MyProfileDataFactory();

			AgencyVO agencyVO = mdf.uploadpicTodb(agencyId, profilepic);
			session.setAttribute("agencyVO", agencyVO);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
		} catch (Exception e) {
			logger.info(ApplicationConstants.LogMessageKeys.UPLOADPROFILEPICTOPATH.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, e);
			e.printStackTrace();
		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	// removeProfilePic
	public void removeProfilePhoto(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(9001, "FETCH PROFILE PHOTO DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			logger.info(ApplicationConstants.LogMessageKeys.FETCHPROFILEPAGEDETAILS.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);
			HttpSession session = request.getSession(true);
			MyProfileDataFactory mpdf = new MyProfileDataFactory();
			AgencyVO agencyVO = mpdf.removeProfilePic(agencyId);
			session.setAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue(), agencyVO);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHPROFILEPAGEDETAILS.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.FETCHPROFILEPAGEDETAILS.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}
	
	//upload master data through excel

	public void uploadMasterDataXLtoDB(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(9008, "UPLOAD MASTER DATA EXCEL SHEET ", ApplicationConstants.ERROR_STATUS_STRING);
		BusinessException bexp=new BusinessException();
		try {
			HttpSession session = request.getSession(true);

			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			long effDate = ((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getEffective_date();
			
			Part uploaddatafile = request.getPart("xldatafile");
			Part filePart = request.getPart("xldatafile"); // Retrieves <input type="file" name="file">
			if (uploaddatafile != null) {
				MyProfileDataFactory mdf = new MyProfileDataFactory();
				mdf.uploadataTodbFromExcel(agencyId, filePart, effDate);
				//session.setAttribute("agencyVO", agencyVO);
				msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
				msgObj.setMessageText(bexp.getExceptionMessage());
				String data=msgObj.getMessageText();
				System.out.println(data);
				
			}
		}catch (BusinessException e) {
			msgObj.setMessageStatus(e.getMessage());
			msgObj.setMessageStatus("ERROR");
			msgObj.setMessageText(e.getExceptionMessage());
			String data=msgObj.getMessageText();
			System.out.println(data);
			logger.info(ApplicationConstants.LogMessageKeys.UPLOADPROFILEPICTOPATH.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, e);
			e.printStackTrace();
		}catch (Exception e) {
			msgObj.setMessageStatus(e.getMessage());
			logger.info(ApplicationConstants.LogMessageKeys.UPLOADPROFILEPICTOPATH.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, e);
			e.printStackTrace();
		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}
}