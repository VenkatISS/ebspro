package com.it.erpapp.framework;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.it.erpapp.framework.bos.AgencyBO;
import com.it.erpapp.framework.managers.AgencyPersistenceManager;
import com.it.erpapp.framework.model.AccountActivationDO;
import com.it.erpapp.framework.model.vos.AgencyVO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class AgencyFactory {

	public void registerAgency(HttpServletRequest request,HttpServletResponse response) throws BusinessException {
		AgencyBO agencyBO = new AgencyBO();
		getAgencyPersistenceManager().createAgency(
				agencyBO.createDO(request.getParameter("agencycode"), request.getParameter("pwd"),
						request.getParameter("emailId")),
				agencyBO.createAgencyDetailDO(request.getParameter("dmobile"), request.getParameter("dname"),
						Integer.parseInt(request.getParameter("storut")), Integer.parseInt(request.getParameter("oc")),
						request.getParameter("gstin"), Integer.parseInt(request.getParameter("ft"))),
				agencyBO.createDefaultAccounts(request.getParameter("agencycode")),
				agencyBO.createAccountActvDO(request.getParameter("agencycode")));
	}

	// Dealer Email Activation
	public AccountActivationDO updateAgency(long agencyId, String activationCode, int requestType) {
		return getAgencyPersistenceManager().activateDealerAccount(agencyId, activationCode, requestType);
	}

	public AgencyVO validateLogin(HttpServletRequest request, HttpServletResponse response) throws BusinessException {
		return getAgencyPersistenceManager().validateLogin(Long.parseLong(request.getParameter("ai")),
				request.getParameter("pwd"));
	}

	public AgencyVO saveAgencyFirstLoginData(HttpServletRequest request,HttpServletResponse response) throws BusinessException {
		AgencyBO agencyBO = new AgencyBO();
		return getAgencyPersistenceManager().createAgencyData(
				agencyBO.createAgencySerialNosDO(request.getParameter("agencyId"), request.getParameter("btbInvCounter"), request.getParameter("btcInvCounter")),
				request.getParameter("fy"), request.getParameter("tarob"), request.getParameter("starob"), request.getParameter("cashb"),
				request.getParameter("ed"), request.getParameter("ujwob"));
	}

	/*---------FORGOT PWD----------*/
	// sendResetPasswordMail
	public void sendResetPasswordMail(HttpServletRequest request,HttpServletResponse response) throws BusinessException {
		getAgencyPersistenceManager().sendResetPasswordMail(request.getParameter("femailId"));
	}

	// update password
	public AgencyVO updatePassword(long agencyId, String activationCode, String newPassword) {
		return getAgencyPersistenceManager().updatePassword(agencyId, activationCode, newPassword);
	}

	// change password
	public AgencyVO changePassword(long agencyId, String newPassword, String oldPassword) {
		return getAgencyPersistenceManager().changePassword(agencyId, newPassword, oldPassword);
	}

	public AgencyVO fetchAgencyVO(long agencyId) {
		return getAgencyPersistenceManager().fetchAgencyDetails(agencyId);
	}

	public Map<String, Object> fetchHomePageDetails(long agencyId) throws BusinessException {
		return getAgencyPersistenceManager().fetchHomePageDetails(agencyId);
	}

	private AgencyPersistenceManager getAgencyPersistenceManager() {
		return new AgencyPersistenceManager();
	}
}