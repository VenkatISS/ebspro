package com.it.erpapp.framework;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.it.erpapp.framework.bos.PartnerInfoBO;
import com.it.erpapp.framework.bos.PartnerTranxsBO;
import com.it.erpapp.framework.bos.transactions.BankTransxDataBO;
import com.it.erpapp.framework.managers.FinancialReportsPersistenceManager;
import com.it.erpapp.framework.model.PartnerInfoDO;
import com.it.erpapp.framework.model.PartnerTranxDO;
import com.it.erpapp.framework.model.finreports.BalanceSheetDO;
import com.it.erpapp.framework.model.finreports.BalanceSheetDetailsDO;
import com.it.erpapp.framework.model.finreports.CapitalAccountReportDO;
import com.it.erpapp.framework.model.finreports.DepreciationDO;
import com.it.erpapp.framework.model.finreports.DepreciationDataDO;
import com.it.erpapp.framework.model.finreports.PAndLAccountDO;
import com.it.erpapp.framework.model.finreports.PAndLAccountDetailsDO;
import com.it.erpapp.framework.model.transactions.others.BankTranxsDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class FinancialReportsFactory {

	public List<PartnerInfoDO> getAgencyPartnersData(long agencyId) throws BusinessException {
		return getFinancialReportsPersistenceManager().getAgencyPartnersData(agencyId);
	}

	public void saveAgencyPartnersData(Map<String, String[]> requestParams, long agencyId) throws BusinessException {
		String[] names = requestParams.get("pname");
		String[] pans = requestParams.get("pan");
		String[] sps = requestParams.get("sp");
		String[] obs = requestParams.get("ob");
		List<PartnerInfoDO> doList = new ArrayList<>();
		PartnerInfoBO bo = new PartnerInfoBO();
		for (int i = 0; i < names.length; i++) {
			doList.add(bo.createDO(names[i], pans[i], sps[i], obs[i], agencyId));
		}
		getFinancialReportsPersistenceManager().saveAgencyPartnersData(doList);
	}

	public void deleteAgencyPartnersData(long itemId, long agencyId) throws BusinessException {
		getFinancialReportsPersistenceManager().deleteAgencyPartnersData(itemId);
	}

	public List<PartnerTranxDO> getAgencyPartnersTranxsData(long agencyId) throws BusinessException {
		return getFinancialReportsPersistenceManager().getAgencyPartnersTranxsData(agencyId);
	}

	public void saveAgencyPartnersTranxsData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException {
		String[] tds = requestParams.get("td");
		String[] pns = requestParams.get("pn");
		String[] tts = requestParams.get("tt");
		String[] tas = requestParams.get("ta");
		String[] bids = requestParams.get("bid");
		String[] rms = requestParams.get("rm");
		List<PartnerTranxDO> doList = new ArrayList<>();
		List<BankTranxsDataDO> bankDOList = new ArrayList<>();
		PartnerTranxsBO bo = new PartnerTranxsBO();
		BankTransxDataBO bankTranxBO = new BankTransxDataBO();

		for (int i = 0; i < tds.length; i++) {
			doList.add(bo.createDO(tds[i], Long.parseLong(pns[i]), Integer.parseInt(tts[i]), tas[i],
					Long.parseLong(bids[i]), rms[i], agencyId));
			if (!tts[i].equalsIgnoreCase("3")) {
				int pr = 2;
				if (tts[i].equalsIgnoreCase("2"))
					pr = 1;
				bankDOList.add(bankTranxBO.createDO(tds[i], Long.parseLong(bids[i]), tas[i], "0.00", pr, 3, "NA", 7, "",
						agencyId));
			}
		}
		getFinancialReportsPersistenceManager().saveAgencyPartnersTranxsData(doList, bankDOList);
	}

	public void deleteAgencyPartnersTranxsData(long itemId, long agencyId) throws BusinessException {
		getFinancialReportsPersistenceManager().deleteAgencyPartnersTranxsData(itemId);
	}

	private FinancialReportsPersistenceManager getFinancialReportsPersistenceManager() {
		return new FinancialReportsPersistenceManager();
	}

	public List<PartnerTranxDO> fetchAgencyCapitalAccountReport(Map<String, String[]> requestParams, long agencyId)
			throws ParseException {

		String[] sfys = requestParams.get("sfy");
		String[] pids = requestParams.get("pid");

		String[] yrs = sfys[0].split("-");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fromdate = yrs[0] + "-04-01";
		String todate = yrs[1] + "-03-31";

		long fdl = (sdf.parse(fromdate)).getTime();
		long tdl = (sdf.parse(todate)).getTime();

		return getFinancialReportsPersistenceManager().getAgencyPartnersTranxsDataByPartnerIdAndYear(fdl, tdl,
				Long.parseLong(pids[0]), agencyId);
	}

	public DepreciationDO fetchDepreciationReportForAgencyByFinancialYear(int syfv, long agencyId)
			throws ParseException {
		String fromdate = syfv + "-04-01";
		String todate = (syfv + 1) + "-03-31";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate)).getTime();
		long tdl = (sdf.parse(todate)).getTime();
		return getFinancialReportsPersistenceManager().fetchDepreciationReportForAgencyByFinancialYear(fdl, tdl, syfv,
				agencyId);
	}

	public DepreciationDO processDepreciationReportForAgencyByFinancialYear(Map<String, String[]> requestParams,
			long agencyId) throws ParseException {
		int rs = Integer.parseInt(requestParams.get("statusId")[0]);
		long rid = Long.parseLong(requestParams.get("recId")[0]);
		return getFinancialReportsPersistenceManager().processDepreciationReportForAgencyByFinancialYear(rid, rs,
				agencyId);
	}

	public List<DepreciationDataDO> fetchDepreciationReportDetailsForAgencyByFinancialYear(
			Map<String, String[]> requestParams, long agencyId) {
		long rid = Long.parseLong(requestParams.get("recId")[0]);
		return getFinancialReportsPersistenceManager().getDepreciationDataDOList(rid);
	}

	public List<DepreciationDataDO> fetchDepreciationReportDetailsForAgencyByFinancialYear(long rid) {
		return getFinancialReportsPersistenceManager().getDepreciationDataDOList(rid);
	}

	public DepreciationDO submitDepreciationReportForAgencyByFinancialYear(Map<String, String[]> requestParams,
			long agencyId) throws ParseException {
		long rid = Long.parseLong(requestParams.get("recId")[0]);
		return getFinancialReportsPersistenceManager().submitDepreciationReportForAgencyByFinancialYear(rid, agencyId);
	}

	public CapitalAccountReportDO fetchCapitalAccountReportForAgencyByFinancialYear(int syfv, long agencyId)
			throws ParseException {
		String fromdate = syfv + "-04-01";
		String todate = (syfv + 1) + "-03-31";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate)).getTime();
		long tdl = (sdf.parse(todate)).getTime();
		return getFinancialReportsPersistenceManager().fetchCapitalAccountReportForAgencyByFinancialYear(fdl, tdl, syfv,
				agencyId);
	}

	public CapitalAccountReportDO processCapitalAccountReportForAgencyByFinancialYear(
			Map<String, String[]> requestParams, long agencyId) throws ParseException {
		int rs = Integer.parseInt(requestParams.get("statusId")[0]);
		long rid = Long.parseLong(requestParams.get("recId")[0]);
		return getFinancialReportsPersistenceManager().processCapitalAccountReportForAgencyByFinancialYear(rid, rs,
				agencyId);
	}

	public CapitalAccountReportDO submitCapitalAccountReportForAgencyByFinancialYear(
			Map<String, String[]> requestParams, long agencyId) throws ParseException {
		long rid = Long.parseLong(requestParams.get("recId")[0]);
		return getFinancialReportsPersistenceManager().submitCapitalAccountReportForAgencyByFinancialYear(rid,
				agencyId);
	}

	public PAndLAccountDO fetchPAndLAccountForAgencyByFinancialYear(int syfv, long agencyId) throws ParseException {
		return getFinancialReportsPersistenceManager().fetchPAndLAccountForAgencyByFinancialYear(syfv, agencyId);
	}

	public PAndLAccountDetailsDO fetchPAndLAccountDetailsForAgencyByFinancialYear(long recordId) throws ParseException {
		return getFinancialReportsPersistenceManager().fetchPAndLAccountDetailsForAgencyByFinancialYear(recordId);
	}

	public PAndLAccountDO processPAndLAccountForAgencyByFinancialYear(Map<String, String[]> requestParams,
			long agencyId) throws ParseException {
		long rid = Long.parseLong(requestParams.get("recId")[0]);
		return getFinancialReportsPersistenceManager().processPAndLAccountForAgencyByFinancialYear(rid, agencyId);
	}

	public PAndLAccountDO savePAndLAccountForAgencyByFinancialYear(Map<String, String[]> requestParams, long agencyId)
			throws ParseException {
		long rid = Long.parseLong(requestParams.get("recId")[0]);
		String osamt = requestParams.get("os")[0];
		String csamt = requestParams.get("cs")[0];
		String pvamt = requestParams.get("ps")[0];
		String svamt = requestParams.get("ss")[0];
		String oiamt = requestParams.get("ots")[0];

		return getFinancialReportsPersistenceManager().savePAndLAccountForAgencyByFinancialYear(osamt, csamt, pvamt,
				svamt, oiamt, rid, agencyId);
	}

	public PAndLAccountDO submitPAndLAccountForAgencyByFinancialYear(Map<String, String[]> requestParams, long agencyId)
			throws ParseException {
		long rid = Long.parseLong(requestParams.get("recId")[0]);
		String gpamt = requestParams.get("gp")[0];
		String npamt = requestParams.get("np")[0];

		return getFinancialReportsPersistenceManager().submitPAndLAccountForAgencyByFinancialYear(gpamt, npamt, rid,
				agencyId);
	}

	public BalanceSheetDO fetchBalanceSheetForAgencyByFinancialYear(int syfv, long agencyId) throws ParseException {
		return getFinancialReportsPersistenceManager().fetchBalanceSheetForAgencyByFinancialYear(syfv, agencyId);
	}

	public BalanceSheetDetailsDO fetchBalanceSheetDetailsForAgencyByFinancialYear(long recordId) throws ParseException {
		return getFinancialReportsPersistenceManager().fetchBalanceSheetDetailsForAgencyByFinancialYear(recordId);
	}

	public BalanceSheetDO processBalanceSheetForAgencyByFinancialYear(Map<String, String[]> requestParams,
			long agencyId) throws ParseException {
		long rid = Long.parseLong(requestParams.get("recId")[0]);
		return getFinancialReportsPersistenceManager().processBalanceSheetForAgencyByFinancialYear(rid, agencyId);
	}

	public BalanceSheetDO saveBalanceSheetForAgencyByFinancialYear(Map<String, String[]> requestParams, long agencyId)
			throws ParseException {
		long rid = Long.parseLong(requestParams.get("recId")[0]);
		String scamt = requestParams.get("clsc")[0];
		String framt = requestParams.get("ulfr")[0];
		String pramt = requestParams.get("prov")[0];
		String sdamt = requestParams.get("casd")[0];
		String laamt = requestParams.get("cala")[0];
		String chamt = requestParams.get("cach")[0];
		String dpamt = requestParams.get("cadp")[0];
		String rsamt = requestParams.get("cars")[0];

		return getFinancialReportsPersistenceManager().saveBalanceSheetForAgencyByFinancialYear(scamt, framt, pramt,
				sdamt, laamt, chamt, dpamt, rsamt, rid, agencyId);
	}

	public BalanceSheetDO submitBalanceSheetForAgencyByFinancialYear(Map<String, String[]> requestParams, long agencyId)
			throws ParseException {
		long rid = Long.parseLong(requestParams.get("recId")[0]);
		String tlamt = requestParams.get("fytl")[0];
		String taamt = requestParams.get("fyta")[0];

		return getFinancialReportsPersistenceManager().submitBalanceSheetForAgencyByFinancialYear(tlamt, taamt, rid,
				agencyId);
	}

}
