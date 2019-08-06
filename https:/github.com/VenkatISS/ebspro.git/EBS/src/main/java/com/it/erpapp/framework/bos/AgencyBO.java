package com.it.erpapp.framework.bos;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.it.erpapp.framework.model.AccountActivationDO;
import com.it.erpapp.framework.model.AgencyDO;
import com.it.erpapp.framework.model.AgencyDetailsDO;
import com.it.erpapp.framework.model.AgencySerialNosDO;
import com.it.erpapp.framework.model.BankDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;
import com.it.erpapp.utils.PasswordUtil;
import com.it.erpapp.utils.RandomActivationCodeGeneratorUtil;

public class AgencyBO {

	//ACCOUNT_ACTIVATION

	public AccountActivationDO createAccountActvDO(String agencyCode) throws BusinessException {
		String activationCode = RandomActivationCodeGeneratorUtil.generateActivationCode();

		AccountActivationDO accountActvDO = new AccountActivationDO();
		accountActvDO.setAgencyCode(Long.parseLong(agencyCode));
		accountActvDO.setRequestType(1);
		accountActvDO.setActivationCode(activationCode);
		accountActvDO.setCreatedDate(System.currentTimeMillis());
		return accountActvDO;
	}
	public AgencyDO createDO(String agencyCode, String passCode, 
			String agencyEmail) throws BusinessException {
		AgencyDO agencyDO = new AgencyDO();
		agencyDO.setAgencyCode(Long.parseLong(agencyCode));
		try {
			agencyDO.setPassCode(PasswordUtil.encryptPasscode(passCode));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new BusinessException("UNABLE TO ENCRYPT PASSWORD");
		}
		agencyDO.setEmailId(agencyEmail);
		agencyDO.setStatus(0);
		agencyDO.setIsFirstTimeLogin(0);
		agencyDO.setCreatedBy(Long.parseLong(agencyCode));
		agencyDO.setCreatedDate(System.currentTimeMillis());
		return agencyDO;
	}
	
	public AgencyDetailsDO createAgencyDetailDO(String mobile, String name, 
			int state, int oc, String gstin,int pps) {
		
		AgencyDetailsDO agencyDetailsDO = new AgencyDetailsDO();
		agencyDetailsDO.setDealerMobile(mobile);
		agencyDetailsDO.setDealerName(name);
		agencyDetailsDO.setOilCompany(oc);
		agencyDetailsDO.setGstin(gstin);
		agencyDetailsDO.setState(state);
		agencyDetailsDO.setPpship(pps);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
//			long sded = (sdf.parse("2017-06-30").getTime());
			long sded = (sdf.parse("2017-12-31").getTime());
			agencyDetailsDO.setDayendDate(sded);
			agencyDetailsDO.setLastTrxnDate(sded);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		agencyDetailsDO.setSi_sno(0);
		agencyDetailsDO.setCs_sno(0);
		agencyDetailsDO.setOffer_price_acceptable(0);
		agencyDetailsDO.setCreatedDate(System.currentTimeMillis());
		agencyDetailsDO.setVersion(1);
		agencyDetailsDO.setDeleted(0);
		return agencyDetailsDO;
	}
	
	public List<BankDataDO> createDefaultAccounts(String agencyId){
		List<BankDataDO> doList = new ArrayList<>();
		//TAR Account
		BankDataDO tarDO = new BankDataDO();
		tarDO.setBank_code("TAR ACCOUNT");
		tarDO.setBank_name("50");
		tarDO.setBank_addr("SANDOZ");
		tarDO.setBank_acc_no(agencyId);
		tarDO.setBank_branch("SANDOZ");
		tarDO.setBank_ifsc_code("HDFC0006");
		tarDO.setAcc_ob("0.00");
		tarDO.setAcc_cb("0.00");
		tarDO.setOd_and_loan_acceptable_bal("NA");
		tarDO.setCreated_by(Long.parseLong(agencyId));
		tarDO.setCreated_date(System.currentTimeMillis());
		tarDO.setVersion(1);
		tarDO.setDeleted(0);
		doList.add(tarDO);

		//STAR Account
		BankDataDO starDO = new BankDataDO();
		starDO.setBank_code("STAR ACCOUNT");
		starDO.setBank_name("50");
		starDO.setBank_addr("SANDOZ");
		starDO.setBank_acc_no(agencyId);
		starDO.setBank_branch("SANDOZ");
		starDO.setBank_ifsc_code("HDFC0006");
		starDO.setAcc_ob("0.00");
		starDO.setAcc_cb("0.00");
		starDO.setOd_and_loan_acceptable_bal("NA");
		starDO.setCreated_by(Long.parseLong(agencyId));
		starDO.setCreated_date(System.currentTimeMillis());
		starDO.setVersion(1);
		starDO.setDeleted(0);
		doList.add(starDO);
		
		return doList;
	}
	
	public AgencySerialNosDO createAgencySerialNosDO(String agencyId,String btbInvCounter,String btcInvCounter){
		int cyear = Calendar.getInstance().get(Calendar.YEAR);
		String cfy = (Integer.toString(cyear)).substring(2);
		
		int cmonth = Calendar.getInstance().get(Calendar.MONTH);
		int cApril = Calendar.APRIL;
		
		AgencySerialNosDO dataDO = new AgencySerialNosDO();
		dataDO.setBtSno(001);
		dataDO.setCnSno(001);
		dataDO.setDnSno(001);
		dataDO.setDcSno(001);
		
		if(cmonth >= cApril)
			dataDO.setFy(Integer.parseInt(cfy));
		else
			dataDO.setFy((Integer.parseInt(cfy))-1);
		dataDO.setPmtsSno(001);
		dataDO.setPrSno(001);
		dataDO.setQtSno(001);
		dataDO.setRcptsSno(001);
		dataDO.setSiSno(Integer.parseInt(btbInvCounter));
		dataDO.setCsSno(Integer.parseInt(btcInvCounter));
		dataDO.setSrSno(001);
		dataDO.setCreatedBy(Long.parseLong(agencyId));
		return dataDO;
	}
	
}