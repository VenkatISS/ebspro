package com.it.erpapp.framework;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.it.erpapp.framework.bos.AgencyCVOBalanceDataBO;
import com.it.erpapp.framework.bos.BankDataBO;
import com.it.erpapp.framework.bos.CVODataBO;
import com.it.erpapp.framework.bos.CreditLimitsDataBO;
import com.it.erpapp.framework.bos.ExpenditureDataBO;
import com.it.erpapp.framework.bos.FleetDataBO;
import com.it.erpapp.framework.bos.StaffDataBO;
import com.it.erpapp.framework.bos.StatutoryDataBO;
import com.it.erpapp.framework.managers.MasterDataPersistenceManager;
import com.it.erpapp.framework.model.AgencyCVOBalanceDataDO;
import com.it.erpapp.framework.model.AgencyCashDataDO;
import com.it.erpapp.framework.model.BankDataDO;
import com.it.erpapp.framework.model.CVODataDO;
import com.it.erpapp.framework.model.CreditLimitsDataDO;
import com.it.erpapp.framework.model.ExpenditureDataDO;
import com.it.erpapp.framework.model.FleetDataDO;
import com.it.erpapp.framework.model.StaffDataDO;
import com.it.erpapp.framework.model.StatutoryDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class MasterDataFactory {

	public List<StatutoryDataDO> getAgencyStatutoryData(long agencyId) throws BusinessException {
		return getMasterDataPersistenceManager().getAgencyStatutoryData(agencyId);
	}

	public List<StatutoryDataDO> saveAgencyStatutoryData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException {
		String[] itemTypes = requestParams.get("item");
		String[] refNumbers = requestParams.get("refno");
		String[] validityDates = requestParams.get("validupto");
		String[] remindBefore = requestParams.get("reminder");
		String[] remarks = requestParams.get("remarks");
		List<StatutoryDataDO> doList = new ArrayList<>();
		StatutoryDataBO sdbo = new StatutoryDataBO();
		for (int i = 0; i < refNumbers.length; i++) {
			doList.add(sdbo.createDO(Integer.parseInt(itemTypes[i]), refNumbers[i], validityDates[i],
					Integer.parseInt(remindBefore[i]), remarks[i], agencyId));
		}
		getMasterDataPersistenceManager().saveAgencyStatutoryData(doList);
		return getMasterDataPersistenceManager().getAgencyStatutoryData(agencyId);
	}

	public List<StatutoryDataDO> deleteAgencyStatutoryData(long itemId, long agencyId) throws BusinessException {
		getMasterDataPersistenceManager().deleteAgencyStatutoryData(itemId);
		return getMasterDataPersistenceManager().getAgencyStatutoryData(agencyId);
	}

	// Staff Data
	public List<StaffDataDO> getAgencyStaffData(long agencyId) throws BusinessException {
		return getMasterDataPersistenceManager().getAgencyStaffData(agencyId);
	}

	// Staff Data
	public List<StaffDataDO> getAgencyAllStaffData(long agencyId) throws BusinessException {
		return getMasterDataPersistenceManager().getAgencyAllStaffData(agencyId);
	}

	public List<StaffDataDO> saveAgencyStaffData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException {
		String[] empCodes = requestParams.get("s_code");
		String[] empNames = requestParams.get("s_name");
		String[] dobs = requestParams.get("s_dob");
		String[] designations = requestParams.get("s_designation");
		String[] roles = requestParams.get("s_role");
		List<StaffDataDO> doList = new ArrayList<>();
		StaffDataBO sdbo = new StaffDataBO();
		for (int i = 0; i < empCodes.length; i++) {
			doList.add(sdbo.createDO(empCodes[i], empNames[i], dobs[i], designations[i], Integer.parseInt(roles[i]),
					agencyId));
		}
		getMasterDataPersistenceManager().saveAgencyStaffData(doList);
		return getMasterDataPersistenceManager().getAgencyStaffData(agencyId);
	}

	public List<StaffDataDO> deleteAgencyStaffData(long itemId, long agencyId) throws BusinessException {
		getMasterDataPersistenceManager().deleteAgencyStaffData(itemId);
		return getMasterDataPersistenceManager().getAgencyStaffData(agencyId);
	}

	// Fleet Data
	public List<FleetDataDO> getAgencyFleetData(long agencyId) throws BusinessException {
		return getMasterDataPersistenceManager().getAgencyFleetData(agencyId);
	}

	// Fleet Data
	public List<FleetDataDO> getAgencyAllFleetData(long agencyId) throws BusinessException {
		return getMasterDataPersistenceManager().getAgencyAllFleetData(agencyId);
	}

	public List<FleetDataDO> saveAgencyFleetData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException {
		String[] vnos = requestParams.get("vh_no");
		String[] vmakes = requestParams.get("vh_make");
		String[] vtypes = requestParams.get("vh_type");
		String[] vusages = requestParams.get("vh_usage");
		List<FleetDataDO> doList = new ArrayList<>();
		FleetDataBO fdbo = new FleetDataBO();
		for (int i = 0; i < vnos.length; i++) {
			doList.add(fdbo.createDO(vnos[i], vmakes[i], Integer.parseInt(vtypes[i]), Integer.parseInt(vusages[i]),
					agencyId));
		}
		getMasterDataPersistenceManager().saveAgencyFleetData(doList);
		return getMasterDataPersistenceManager().getAgencyFleetData(agencyId);
	}

	//Bank Report Opening Balance Data

			public String getOpeningBalanceByBank(Map<String,  String[]> requestParams, long agencyId,long bId) throws BusinessException, ParseException{
				
				String[] fromdate = requestParams.get("fd");

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				long fdl = (sdf.parse(fromdate[0])).getTime();

				return  getMasterDataPersistenceManager().getOpeningBalanceByBank(agencyId,fdl,bId);
			}


	public List<FleetDataDO> deleteAgencyFleetData(long fleetId, long agencyId) throws BusinessException {
		getMasterDataPersistenceManager().deleteAgencyFleetData(fleetId);
		return getMasterDataPersistenceManager().getAgencyFleetData(agencyId);
	}

	// Bank Data
	public List<BankDataDO> getAgencyBankData(long agencyId) throws BusinessException {
		return getMasterDataPersistenceManager().getAgencyBankData(agencyId);
	}

	// Bank Data
	public List<BankDataDO> getAgencyAllBankData(long agencyId) throws BusinessException {
		return getMasterDataPersistenceManager().getAgencyAllBankData(agencyId);
	}

	public List<BankDataDO> saveAgencyBankData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException {
		String[] codes = requestParams.get("bank_code");
		String[] names = requestParams.get("bank_name");
		String[] accnos = requestParams.get("bank_accno");
		String[] branches = requestParams.get("bank_branch");
		String[] ifsccodes = requestParams.get("bank_ifsc");
		String[] balances = requestParams.get("bank_ob");
		String[] addresses = requestParams.get("bank_addr");
		String[] dlBal = requestParams.get("DLbal");

		List<BankDataDO> doList = new ArrayList<>();
		BankDataBO bdbo = new BankDataBO();
		for (int i = 0; i < codes.length; i++) {
			doList.add(bdbo.createDO(codes[i], names[i], accnos[i], branches[i], ifsccodes[i], balances[i],
					addresses[i], dlBal[i], agencyId));
		}
		getMasterDataPersistenceManager().saveAgencyBankData(doList);
		return getMasterDataPersistenceManager().getAgencyAllBankData(agencyId);
	}

	public List<BankDataDO> deleteAgencyBankData(long bankDataId, long agencyId) throws BusinessException {
		getMasterDataPersistenceManager().deleteAgencyBankData(bankDataId);
		return getMasterDataPersistenceManager().getAgencyAllBankData(agencyId);
	}
	//updateAgencyBankData
    public List<BankDataDO> updateAgencyBankData(Map<String, String[]> requestParams, long agencyId)
                    throws BusinessException {
            String[] codes = requestParams.get("accountType");
            String[] branch = requestParams.get("branch");
            String[] ifsc = requestParams.get("ifsc");
            String[] addr = requestParams.get("addr");
            String[] bankId = requestParams.get("bankId");

            List<BankDataDO> doList = new ArrayList<>();
            BankDataBO bdbo = new BankDataBO();
            for (int i = 0; i < codes.length; i++) {
                    doList.add(bdbo.updateDO(codes[i], branch[i], ifsc[i], addr[i],bankId[i], agencyId));
            }
            getMasterDataPersistenceManager().updateAgencyBankData(doList);
            return getMasterDataPersistenceManager().getAgencyAllBankData(agencyId);
    }

	// CVO Data
	public List<CVODataDO> getAgencyCVOData(long agencyId) throws BusinessException {
		return getMasterDataPersistenceManager().getAgencyCVOData(agencyId);
	}

	// CVO Data
	public List<CVODataDO> getAgencyAllCVOData(long agencyId) throws BusinessException {
		return getMasterDataPersistenceManager().getAgencyAllCVOData(agencyId);
	}

	public List<CVODataDO> saveAgencyCVOData(Map<String, String[]> requestParams, long agencyId, String effdate)
			throws BusinessException {

		String[] names = requestParams.get("cvo_name");
		String[] caddrs = requestParams.get("cvo_addr");
		String[] categorys = requestParams.get("cvo_cat");
		String[] contacts = requestParams.get("cvo_contact");
		String[] gstyns = requestParams.get("gstyn");
		String[] tins = requestParams.get("cvo_tin");
		String[] emails = requestParams.get("cvo_email");
		String[] pans = requestParams.get("cvo_pan");
		String[] balances = requestParams.get("cvo_ob");

		List<CVODataDO> doList = new ArrayList<>();
		CVODataBO cvobo = new CVODataBO();

		List<AgencyCVOBalanceDataDO> acvoDOList = new ArrayList<>();
		AgencyCVOBalanceDataBO acvoBalBO = new AgencyCVOBalanceDataBO();

		for (int i = 0; i < names.length; i++) {
			doList.add(cvobo.createDO(names[i], contacts[i], caddrs[i], Integer.parseInt(gstyns[i]), tins[i], emails[i],
					pans[i], balances[i], Integer.parseInt(categorys[i]), agencyId));

			acvoDOList.add(acvoBalBO.createDO(balances[i], Integer.parseInt(categorys[i]), agencyId, 0, "NA", 0, 0, "0",
					"NA"));
		}
		getMasterDataPersistenceManager().saveAgencyCVOData(doList, acvoDOList, effdate);
		return getMasterDataPersistenceManager().getAgencyCVOData(agencyId);
	}

	public List<CVODataDO> deleteAgencyCVOData(long cvoDataId, long agencyId) throws BusinessException {
		getMasterDataPersistenceManager().deleteAgencyCVOData(cvoDataId);
		return getMasterDataPersistenceManager().getAgencyCVOData(agencyId);
	}

	//update cvo
    public List<CVODataDO> updateAgencyCVOData(Map<String, String[]> requestParams, long agencyId, String effdate)
                    throws BusinessException {

            String[] names = requestParams.get("partyName");
            String[] caddrs = requestParams.get("addr");
            String[] categorys = requestParams.get("partyType");
            String[] contacts = requestParams.get("mobl");
            String[] gstyns = requestParams.get("dgstyn");
            String[] tins = requestParams.get("gstin");
            String[] emails = requestParams.get("email");
            String[] pans = requestParams.get("pan");
            String[] balances = requestParams.get("obal");
            String[] cbalances = requestParams.get("cvocbal");
            String[] cvoId = requestParams.get("cvoId");



            List<CVODataDO> doList = new ArrayList<>();
            CVODataBO cvobo = new CVODataBO();

            List<AgencyCVOBalanceDataDO> acvoDOList = new ArrayList<>();
            AgencyCVOBalanceDataBO acvoBalBO = new AgencyCVOBalanceDataBO();

            for (int i = 0; i < names.length; i++) {
                    doList.add(cvobo.updateDO(names[i], contacts[i], caddrs[i], gstyns[i], tins[i], emails[i],
                                    pans[i], balances[i],cbalances[i], categorys[i],cvoId[i],agencyId));

                    acvoDOList.add(acvoBalBO.updateDO(balances[i], cbalances[i],categorys[i], agencyId, 0, "NA", 0, 0, "0",
                                    "NA"));
            }
            getMasterDataPersistenceManager().updateAgencyCVOData(doList, acvoDOList, effdate);
            return getMasterDataPersistenceManager().getAgencyCVOData(agencyId);
    }
	// Expenditure Data
	public List<ExpenditureDataDO> getAgencyExpenditureData(long agencyId) throws BusinessException {
		return getMasterDataPersistenceManager().getAgencyExpenditureData(agencyId);
	}

	public List<ExpenditureDataDO> saveAgencyExpenditureData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException {
		List<ExpenditureDataDO> doList = new ArrayList<>();
		ExpenditureDataBO ebo = new ExpenditureDataBO();
		String[] item_names = requestParams.get("item_name");
		String[] shs = requestParams.get("esh");
		String[] mhs = requestParams.get("emh");
		for (int i = 0; i < item_names.length; i++) {
			doList.add(ebo.createDO(item_names[i], Integer.parseInt(shs[i]), Integer.parseInt(mhs[i]), agencyId));
		}
		getMasterDataPersistenceManager().saveAgencyExpenditureData(doList);
		return getMasterDataPersistenceManager().getAgencyExpenditureData(agencyId);
	}

	public List<ExpenditureDataDO> deleteAgencyExpenditureData(long expDataId, long agencyId) throws BusinessException {
		getMasterDataPersistenceManager().deleteAgencyExpenditureData(expDataId);
		return getMasterDataPersistenceManager().getAgencyExpenditureData(agencyId);
	}

	private MasterDataPersistenceManager getMasterDataPersistenceManager() {
		return new MasterDataPersistenceManager();
	}

	// Credit Limits Data
	public List<CreditLimitsDataDO> getAgencyCreditLimitsData(long agencyId) throws BusinessException {
		return getMasterDataPersistenceManager().getAgencyCreditLimitsData(agencyId);
	}

	public void saveAgencyCreditLimitsData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException {
		List<CreditLimitsDataDO> doList = new ArrayList<>();
		CreditLimitsDataBO dataBO = new CreditLimitsDataBO();
		String[] cust_ids = requestParams.get("cid");
		String[] cls = requestParams.get("cl");
		String[] cds = requestParams.get("cd");

		String[] discountCcyl1 = requestParams.get("ccyl1");
		String[] discountCcyl2 = requestParams.get("ccyl2");
		String[] discountCcyl3 = requestParams.get("ccyl3");
		String[] discountCcyl4 = requestParams.get("ccyl4");
		String[] discountCcyl5 = requestParams.get("ccyl5");

		String[] ccs = requestParams.get("cc");
		for (int i = 0; i < cust_ids.length; i++) {
			doList.add(dataBO.createDO(Long.parseLong(cust_ids[i]), cls[i], Integer.parseInt(cds[i]), discountCcyl1[i],
					discountCcyl2[i], discountCcyl3[i], discountCcyl4[i], discountCcyl5[i], Integer.parseInt(ccs[i]),
					agencyId));
		}
		getMasterDataPersistenceManager().saveAgencyCreditLimitsData(doList);

	}

	public void deleteAgencyCreditLimitsData(long clDataId, long agencyId) throws BusinessException {
		getMasterDataPersistenceManager().deleteAgencyCreditLimitsData(clDataId);
	}

	public void updateCreditlimitsData(Map<String, String> requestParams, long agencyId) throws BusinessException {

		getMasterDataPersistenceManager().updateCreditlimitsData(requestParams, agencyId);

	}

	
/*------------------ Start Opening Balance Method 06052019 -----------------------------------------*/
	
    //fetch opening balance data
	
	public List<BankDataDO> getAgencyBankOpeningBalanceData(long agencyId) throws BusinessException {
		return getMasterDataPersistenceManager().getAgencyBankOpeningBalanceData(agencyId);
	}
	
	public List<AgencyCashDataDO> getAgencyCashOpeningBalanceData(long agencyId) throws BusinessException {
		return getMasterDataPersistenceManager().getAgencyCashOpeningBalanceData(agencyId);
	}
	
	public List<CVODataDO> getAgencyUjwalaOpeningBalanceData(long agencyId) throws BusinessException {
		return getMasterDataPersistenceManager().getAgencyUjwalaOpeningBalanceData(agencyId);
	}
	
	//update opening balance data
	
	
	public void updateOpeningBalanceData(Map<String, String> requestParams, long agencyId) throws BusinessException {
		getMasterDataPersistenceManager().updateOpeningBalanceData(requestParams, agencyId);

	}

	// End Opening Balance Method 06052019

}
