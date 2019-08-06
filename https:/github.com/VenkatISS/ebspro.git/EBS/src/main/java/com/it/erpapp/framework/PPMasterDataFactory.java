package com.it.erpapp.framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.it.erpapp.framework.bos.AgencyStockDataBO;
import com.it.erpapp.framework.bos.pps.ARBDataBO;
import com.it.erpapp.framework.bos.pps.ARBPriceDataBO;
import com.it.erpapp.framework.bos.pps.AgencyBOMBO;
import com.it.erpapp.framework.bos.pps.AreaCodeDataBO;
import com.it.erpapp.framework.bos.pps.EquipmentDataBO;
import com.it.erpapp.framework.bos.pps.RefillPriceDataBO;
import com.it.erpapp.framework.bos.pps.ServicesDataBO;
import com.it.erpapp.framework.managers.PPMasterDataPersistenceManager;
import com.it.erpapp.framework.model.AgencyStockDataDO;
import com.it.erpapp.framework.model.pps.ARBDataDO;
import com.it.erpapp.framework.model.pps.ARBPriceDataDO;
import com.it.erpapp.framework.model.pps.AreaCodeDataDO;
import com.it.erpapp.framework.model.pps.EquipmentDataDO;
import com.it.erpapp.framework.model.pps.RefillPriceDataDO;
import com.it.erpapp.framework.model.pps.ServicesDataDO;
import com.it.erpapp.framework.model.pps.ServicesGSTData;
import com.it.erpapp.framework.model.transactions.AgencyBOMDO;
import com.it.erpapp.framework.model.transactions.BOMItemsDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class PPMasterDataFactory {

	private PPMasterDataPersistenceManager getPPMasterDataPersistenceManager() {
		return new PPMasterDataPersistenceManager();
	}

	// Equipment Data
	public List<EquipmentDataDO> getAgencyEquipmentData(long agencyId) throws BusinessException {
		return getPPMasterDataPersistenceManager().getAgencyEquipmentData(agencyId);
	}

	// Equipment Data
	public List<EquipmentDataDO> getAgencyAllEquipmentData(long agencyId) throws BusinessException {
		return getPPMasterDataPersistenceManager().getAgencyAllEquipmentData(agencyId);
	}

	// Equipment Data
	public List<EquipmentDataDO> getAgencyDEquipmentData(long agencyId) throws BusinessException {
		return getPPMasterDataPersistenceManager().getAgencyDEquipmentData(agencyId);
	}
	
	// STOCK Data
	public List<AgencyStockDataDO> getAgencyStockData(long agencyId) throws BusinessException {
		return getPPMasterDataPersistenceManager().getAgencyStockData(agencyId);
	}

	public List<EquipmentDataDO> saveAgencyEquipmentData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException {
		List<EquipmentDataDO> doList = new ArrayList<>();
		List<AgencyStockDataDO> asdoList = new ArrayList<>();

		String[] epids = requestParams.get("eqepid");
		String[] euss = requestParams.get("eus");
		String[] egsts = requestParams.get("egst");
		String[] ecstehs = requestParams.get("ecsteh");
		String[] esds = requestParams.get("esd");
		String[] efs = requestParams.get("ef");
		String[] ees = requestParams.get("ee");
		String[] eds = requestParams.get("ed");
		EquipmentDataBO boObj = new EquipmentDataBO();
		AgencyStockDataBO asbo = new AgencyStockDataBO();
		for (int i = 0; i < epids.length; i++) {
			doList.add(boObj.createDO(Integer.parseInt(epids[i]), Integer.parseInt(euss[i]), egsts[i], ecstehs[i],
					esds[i], Integer.parseInt(efs[i]), Integer.parseInt(ees[i]), eds[i], agencyId));

			asdoList.add(asbo.createDO(0, 0, "NA", eds[i], 0, Integer.parseInt(epids[i]), 0, Integer.parseInt(efs[i]),
					Integer.parseInt(ees[i]), Integer.parseInt(efs[i]), Integer.parseInt(ees[i]), 0, "0.00", agencyId));
		}
		getPPMasterDataPersistenceManager().saveAgencyEquipmentData(doList, asdoList);
		return getPPMasterDataPersistenceManager().getAgencyEquipmentData(agencyId);
	}

	public List<EquipmentDataDO> deleteAgencyEquipmentData(long itemId, long agencyId) throws BusinessException {
		getPPMasterDataPersistenceManager().deleteAgencyEquipmentData(itemId);
		return getPPMasterDataPersistenceManager().getAgencyEquipmentData(agencyId);
	}

	// ARB Data
	public List<ARBDataDO> getAgencyARBData(long agencyId) throws BusinessException {
		return getPPMasterDataPersistenceManager().getAgencyARBData(agencyId);
	}

	// ARB Data
	public List<ARBDataDO> getAgencyAllARBData(long agencyId) throws BusinessException {
		return getPPMasterDataPersistenceManager().getAgencyAllARBData(agencyId);
	}

	public List<ARBDataDO> saveAgencyARBData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException {
		List<ARBDataDO> doList = new ArrayList<>();
		List<AgencyStockDataDO> asdoList = new ArrayList<>();
		String[] pids = requestParams.get("pid");
		String[] pbrands = requestParams.get("pbrand");
		String[] pnames = requestParams.get("pname");
		String[] uss = requestParams.get("pus");
		String[] gstps = requestParams.get("gstp");
		String[] cstehvs = requestParams.get("cstehv");
		String[] mrps = requestParams.get("mrp");
		String[] eds = requestParams.get("ed");
		String[] oss = requestParams.get("os");
		String[] uniqRandomValForProd = requestParams.get("uniqRandomValForProd");
		
		List<String> stockNum = Arrays.asList(uniqRandomValForProd);
		List<String> arbprodNum = Arrays.asList(uniqRandomValForProd);
		
		ARBDataBO boObj = new ARBDataBO();
		AgencyStockDataBO asboObj = new AgencyStockDataBO();
		for (int i = 0; i < pids.length; i++) {
			doList.add(boObj.createDO(Integer.parseInt(pids[i]), pbrands[i], pnames[i], Integer.parseInt(uss[i]),
					gstps[i], cstehvs[i], mrps[i], Integer.parseInt(oss[i]), eds[i], agencyId));
			asdoList.add(asboObj.createDO(0, 0, "NA", eds[i], 0, Integer.parseInt(pids[i]), 0, Integer.parseInt(oss[i]),
					0, Integer.parseInt(oss[i]), 0, 0, "0.00", agencyId));

		}
		getPPMasterDataPersistenceManager().saveAgencyARBData(doList, asdoList, stockNum, arbprodNum);
		return getPPMasterDataPersistenceManager().getAgencyAllARBData(agencyId);
	}

	public List<ARBDataDO> deleteAgencyARBData(long itemId, long agencyId) throws BusinessException {
		getPPMasterDataPersistenceManager().deleteAgencyARBData(itemId);
		return getPPMasterDataPersistenceManager().getAgencyAllARBData(agencyId);
	}

	// Services Data
	public List<ServicesDataDO> getAgencyServicesData(long agencyId) throws BusinessException {
		return getPPMasterDataPersistenceManager().getAgencyServicesData(agencyId);
	}
	
/*------------------SERVICES GST  14052019--------------------------------*/

	
	public void saveAgencyServicesGST(String agencyId)
			throws BusinessException {
		long agencyid=Long.parseLong(agencyId);
		 getPPMasterDataPersistenceManager().saveAgencyServicesGST(agencyid);

	}


   public List<ServicesGSTData> getAgencyServicesGTData(long agencyId) throws BusinessException {
		return getPPMasterDataPersistenceManager().getAgencyServicesGSTData(agencyId);
	}
	
/*------------------SERVICES GST END  14052019--------------------------------*/


	public List<ServicesDataDO> saveAgencyServicesData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException {
		List<ServicesDataDO> doList = new ArrayList<>();
		String[] scodes = requestParams.get("scode");
		String[] scharges = requestParams.get("scharge");
		String[] gstamts = requestParams.get("gstamt");
		String[] saccodes = requestParams.get("saccode");
		String[] seds = requestParams.get("sed");
		ServicesDataBO boObj = new ServicesDataBO();
		for (int i = 0; i < scodes.length; i++) {
			doList.add(boObj.createDO(Integer.parseInt(scodes[i]), scharges[i], seds[i], gstamts[i], saccodes[i],
					agencyId));
		}
		getPPMasterDataPersistenceManager().saveAgencyServicesData(doList);
		return getPPMasterDataPersistenceManager().getAgencyServicesData(agencyId);
	}

	public List<ServicesDataDO> deleteAgencyServicesData(long itemId, long agencyId) throws BusinessException {
		getPPMasterDataPersistenceManager().deleteAgencyServicesData(itemId);
		return getPPMasterDataPersistenceManager().getAgencyServicesData(agencyId);
	}

	// update Gst Applicable percentage Services Data
	public List<ServicesDataDO> updateServiceGSTPercentage(long agencyId,String gstp) throws BusinessException {
		return getPPMasterDataPersistenceManager().updateServiceGSTPercentage(agencyId,gstp);
	}
			
	// Area Code Data
	public List<AreaCodeDataDO> getAgencyAreaCodeData(long agencyId) throws BusinessException {
		return getPPMasterDataPersistenceManager().getAgencyAreaCodeData(agencyId);
	}

	// Area Code Data
	public List<AreaCodeDataDO> getAgencyAllAreaCodeData(long agencyId) throws BusinessException {
		return getPPMasterDataPersistenceManager().getAgencyAllAreaCodeData(agencyId);
	}

	public List<AreaCodeDataDO> saveAgencyAreaCodeData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException {
		List<AreaCodeDataDO> doList = new ArrayList<>();
		String[] acs = requestParams.get("ac");
		String[] ans = requestParams.get("an");
		String[] owds = requestParams.get("owd");
		String[] tcs = requestParams.get("tc");
		String[] eds = requestParams.get("ed");
		AreaCodeDataBO boObj = new AreaCodeDataBO();
		for (int i = 0; i < acs.length; i++) {
			doList.add(boObj.createDO(acs[i], ans[i], Integer.parseInt(owds[i]), tcs[i], eds[i], agencyId));
		}
		getPPMasterDataPersistenceManager().saveAgencyAreaCodeData(doList);
		return getPPMasterDataPersistenceManager().getAgencyAreaCodeData(agencyId);
	}

	public List<AreaCodeDataDO> deleteAgencyAreaCodeData(long itemId, long agencyId) throws BusinessException {
		getPPMasterDataPersistenceManager().deleteAgencyAreaCodeData(itemId);
		return getPPMasterDataPersistenceManager().getAgencyAreaCodeData(agencyId);
	}

	// Refill Price Data
	public List<RefillPriceDataDO> getAgencyRefillPriceData(long agencyId) throws BusinessException {
		return getPPMasterDataPersistenceManager().getAgencyRefillPriceData(agencyId);
	}

	public List<RefillPriceDataDO> saveAgencyRefillPriceData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException {
		List<RefillPriceDataDO> doList = new ArrayList<>();
		String[] pids = requestParams.get("pid");
		String[] bps = requestParams.get("bp");
		String[] sgsts = requestParams.get("sgst");
		String[] cgsts = requestParams.get("cgst");
		String[] rsps = requestParams.get("rsp");
		// String[] ops = requestParams.get("op");
		String[] ofps = requestParams.get("ofp");
		String[] ofpBps = requestParams.get("ofpBp");
		String[] mons = requestParams.get("mon");
		String[] yrs = requestParams.get("yr");
		RefillPriceDataBO boObj = new RefillPriceDataBO();
		for (int i = 0; i < pids.length; i++) {
			doList.add(boObj.createDO(Integer.parseInt(pids[i]), bps[i], sgsts[i], cgsts[i], rsps[i], ofps[i], ofpBps[i],
					Integer.parseInt(mons[i]), Integer.parseInt(yrs[i]), agencyId));
		}
		getPPMasterDataPersistenceManager().saveAgencyRefillPriceData(doList);
		return getPPMasterDataPersistenceManager().getAgencyRefillPriceData(agencyId);
	}

	public List<RefillPriceDataDO> deleteAgencyRefillPriceData(long itemId, long agencyId) throws BusinessException {
		getPPMasterDataPersistenceManager().deleteAgencyRefillPriceData(itemId);
		return getPPMasterDataPersistenceManager().getAgencyRefillPriceData(agencyId);
	}

	// ARB Price Data
	public List<ARBPriceDataDO> getAgencyARBPriceData(long agencyId) throws BusinessException {
		return getPPMasterDataPersistenceManager().getAgencyARBPriceData(agencyId);
	}

	public List<ARBPriceDataDO> saveAgencyARBPriceData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException {
		List<ARBPriceDataDO> doList = new ArrayList<>();
		String[] pids = requestParams.get("pid");
		String[] bps = requestParams.get("bp");
		String[] sgstps = requestParams.get("sgst");
		String[] cgstps = requestParams.get("cgst");
		String[] rsps = requestParams.get("rsp");
		String[] ofps = requestParams.get("ofp");
		String[] ofpBps = requestParams.get("ofpBp");
		String[] mons = requestParams.get("mon");
		String[] yrs = requestParams.get("yr");
		ARBPriceDataBO boObj = new ARBPriceDataBO();
		for (int i = 0; i < pids.length; i++) {
			doList.add(boObj.createDO(Long.parseLong(pids[i]), bps[i], sgstps[i], cgstps[i], rsps[i], ofps[i] ,
					ofpBps[i] , Integer.parseInt(mons[i]), Integer.parseInt(yrs[i]), agencyId));
		}
		getPPMasterDataPersistenceManager().saveAgencyARBPriceData(doList);
		return getPPMasterDataPersistenceManager().getAgencyARBPriceData(agencyId);
	}

	public List<ARBPriceDataDO> deleteAgencyARBPriceData(long itemId, long agencyId) throws BusinessException {
		getPPMasterDataPersistenceManager().deleteAgencyARBPriceData(itemId);
		return getPPMasterDataPersistenceManager().getAgencyARBPriceData(agencyId);
	}

	// BOM Data
	public List<AgencyBOMDO> getAgencyBOMData(long agencyId) throws BusinessException {
		return getPPMasterDataPersistenceManager().getAgencyBOMData(agencyId);
	}

	public List<AgencyBOMDO> saveAgencyBOMData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException {
		String[] bomname = requestParams.get("bom_name");
		String[] bomtype = requestParams.get("ctype");
		String[] pids = requestParams.get("spid");
		String[] qtys = requestParams.get("qty");
		String[] drns = requestParams.get("drn");
		List<BOMItemsDO> doList = new ArrayList<>();
		AgencyBOMBO boObj = new AgencyBOMBO();
		for (int i = 0; i < pids.length; i++) {
			doList.add(boObj.createBOMItemDO(pids[i], qtys[i], drns[i]));
		}
		getPPMasterDataPersistenceManager()
				.saveAgencyBOMData(boObj.createDO(bomname[0], Integer.parseInt(bomtype[0]), agencyId), doList);
		return getPPMasterDataPersistenceManager().getAgencyBOMData(agencyId);
	}

	public List<AgencyBOMDO> deleteAgencyBOMData(long itemId, long agencyId) throws BusinessException {
		getPPMasterDataPersistenceManager().deleteAgencyBOMData(itemId);
		return getPPMasterDataPersistenceManager().getAgencyBOMData(agencyId);
	}

}
