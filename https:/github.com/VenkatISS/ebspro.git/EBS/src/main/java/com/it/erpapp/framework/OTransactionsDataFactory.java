package com.it.erpapp.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.it.erpapp.framework.bos.AgencyCVOBalanceDataBO;
import com.it.erpapp.framework.bos.AgencyCashDataBO;
import com.it.erpapp.framework.bos.AgencyStockDataBO;
import com.it.erpapp.framework.bos.GSTPaymentDetailsBO;
import com.it.erpapp.framework.bos.transactions.AssetDataBO;
import com.it.erpapp.framework.bos.transactions.BankTransxDataBO;
import com.it.erpapp.framework.bos.transactions.CreditNotesBO;
import com.it.erpapp.framework.bos.transactions.DebitNotesBO;
import com.it.erpapp.framework.bos.transactions.NCDBCDataBO;
import com.it.erpapp.framework.bos.transactions.PaymentsDataBO;
import com.it.erpapp.framework.bos.transactions.PurchaseReturnBO;
import com.it.erpapp.framework.bos.transactions.RCDataBO;
import com.it.erpapp.framework.bos.transactions.ReceiptsDataBO;
import com.it.erpapp.framework.bos.transactions.SaleReturnBO;
import com.it.erpapp.framework.bos.transactions.TVDataBO;
import com.it.erpapp.framework.managers.OTransactionsPersistenceManager;
import com.it.erpapp.framework.model.AgencyCVOBalanceDataDO;
import com.it.erpapp.framework.model.AgencyCashDataDO;
import com.it.erpapp.framework.model.AgencyStockDataDO;
import com.it.erpapp.framework.model.GSTPaymentDetailsDO;
import com.it.erpapp.framework.model.transactions.others.AssetDataDO;
import com.it.erpapp.framework.model.transactions.others.BankTranxsDataDO;
import com.it.erpapp.framework.model.transactions.others.CreditNotesDO;
import com.it.erpapp.framework.model.transactions.others.DebitNotesDO;
import com.it.erpapp.framework.model.transactions.others.PaymentsDataDO;
import com.it.erpapp.framework.model.transactions.others.ReceiptsDataDO;
import com.it.erpapp.framework.model.transactions.sales.NCDBCInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.NCDBCInvoiceDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.PurchaseReturnDO;
import com.it.erpapp.framework.model.transactions.sales.PurchaseReturnDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.RCDataDO;
import com.it.erpapp.framework.model.transactions.sales.SalesReturnDO;
import com.it.erpapp.framework.model.transactions.sales.SalesReturnDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.TVDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class OTransactionsDataFactory {

	private OTransactionsPersistenceManager getTransactionsPersistenceManager(){
		return new OTransactionsPersistenceManager();
	}

	//Receipts Data
	public List<ReceiptsDataDO> getAgencyReceiptsData(long agencyId) throws BusinessException{
		return getTransactionsPersistenceManager().getAgencyReceiptsData(agencyId);
	}

	public void saveAgencyReceiptsData(Map<String, String[]> requestParams, long agencyId) throws BusinessException {
		List<ReceiptsDataDO> doList = new ArrayList<>();
		List<BankTranxsDataDO> bankTranxDOList = new ArrayList<>();
		String[] rdates = requestParams.get("rdate");
		String[] cids = requestParams.get("rfrom");
		String[] ramts = requestParams.get("ramt");
		String[] mops = requestParams.get("mop");
		String[] instrds = requestParams.get("instrd");
		String[] bids = requestParams.get("bid");
		String[] sids = requestParams.get("sid");
		String[] nars = requestParams.get("nar");
		ReceiptsDataBO boObj = new ReceiptsDataBO();
		BankTransxDataBO bankTranxBO = new BankTransxDataBO();
		AgencyCVOBalanceDataBO cvoDataBO = new AgencyCVOBalanceDataBO();
		AgencyCVOBalanceDataDO cvoDOObj = new AgencyCVOBalanceDataDO();

		for(int i=0; i<rdates.length;i++) {
			int mop = Integer.parseInt(mops[i]);
			doList.add(boObj.createDO(rdates[i], Long.parseLong(cids[i]), ramts[i], mop, instrds[i], 
					Long.parseLong(bids[i]), Long.parseLong(sids[i]), nars[i], agencyId));

			cvoDOObj=cvoDataBO.createDO( ramts[i],1,agencyId,3,"0", 6 ,Long.parseLong(cids[i]),rdates[i],"NA");

			if(mop!=1) {
				bankTranxDOList.add(bankTranxBO.createDO(rdates[i], Long.parseLong(bids[i]), ramts[i], "0", 2, 
						Integer.parseInt(mops[i]), instrds[i], 0,nars[i], agencyId));
			}
		}
		
		getTransactionsPersistenceManager().saveAgencyReceiptsData(doList,bankTranxDOList, cvoDOObj);
	}

	public void deleteAgencyReceiptsData(long itemId,long agencyId) throws BusinessException {
		getTransactionsPersistenceManager().deleteAgencyReceiptsData(itemId);
	}

	//Payments Data
	public List<PaymentsDataDO> getAgencyPaymentsData(long agencyId) throws BusinessException{
		return getTransactionsPersistenceManager().getAgencyPaymentsData(agencyId);
	}

	
	public void saveAgencyPaymentsData(Map<String, String[]> requestParams, long agencyId, String mon, String yr)
			throws BusinessException {
		List<PaymentsDataDO> doList = new ArrayList<>();
		List<BankTranxsDataDO> bankTranxDOList = new ArrayList<>();
		// Items
		String[] pdates = requestParams.get("pdate");
		String[] vids = requestParams.get("cvo_id");
		String[] pamts = requestParams.get("pamt");
		String[] cv = requestParams.get("cv");
		String[] taxtype = requestParams.get("ptax");
		String[] mops = requestParams.get("mop");
		String[] instrds = requestParams.get("instrd");
		String[] bids = requestParams.get("bid");
		String[] sids = requestParams.get("sid");
		String[] nars = requestParams.get("nar");
		String[] charges = requestParams.get("charges");
		
		String[] gstamt = requestParams.get("gsta");
		String[] itamt = requestParams.get("ita");
		String[] cvocat = requestParams.get("cvo_cat");
		
		PaymentsDataBO boObj = new PaymentsDataBO();
		BankTransxDataBO bankTranxBO = new BankTransxDataBO();
		GSTPaymentDetailsBO gpboObj = new GSTPaymentDetailsBO();
		GSTPaymentDetailsDO gpDO = new GSTPaymentDetailsDO();
		
		for (int i = 0; i < pdates.length; i++) {
			int mop = Integer.parseInt(mops[i]);
			int cvs = Integer.parseInt(cv[i]);
			int taxtypes = Integer.parseInt(taxtype[i]);

			if (cvs != 3) {
				doList.add(boObj.createDO(pdates[i], Long.parseLong(vids[i]), pamts[i], cvs, 0, mop, instrds[i],
						Long.parseLong(bids[i]), charges[i], Long.parseLong(sids[i]), nars[i], agencyId));
				if (mop != 1) {
					bankTranxDOList.add(bankTranxBO.createDO(pdates[i], Long.parseLong(bids[i]), pamts[i], charges[i], 1,
							Integer.parseInt(mops[i]), instrds[i], 0, nars[i], agencyId));
				}
			}else {
				doList.add(boObj.createDO(pdates[i], 0, pamts[i], cvs, taxtypes, mop, instrds[i],
						Long.parseLong(bids[i]), charges[i], Long.parseLong(sids[i]), nars[i], agencyId));
				gpDO = gpboObj.createDO(Integer.parseInt(mon), Integer.parseInt(yr), pdates[i],1, taxtypes,
						gstamt[i], itamt[i], agencyId);

				int tt = 0;
				if (taxtypes == 1)
					tt = 9;
				else if (taxtypes == 2)
					tt = 10;

				if (mop != 1) {
					bankTranxDOList.add(bankTranxBO.createDO(pdates[i], Long.parseLong(bids[i]), pamts[i], charges[i], tt,
							Integer.parseInt(mops[i]), instrds[i], 0, nars[i], agencyId));
				}
			}
		}

		AgencyCVOBalanceDataBO cvoDataBO = new AgencyCVOBalanceDataBO();
    	long cvoId = (Integer.parseInt(cv[0]) != 3) ? Long.parseLong(vids[0]) : 0;
		
		getTransactionsPersistenceManager().saveAgencyPaymentsData(doList, bankTranxDOList, gpDO, charges[0], agencyId, 
				cvoDataBO.createDO(pamts[0], Integer.parseInt(cvocat[0]), agencyId, 4, "0", 7, cvoId, pdates[0], "NA"));
	}

	public void deleteAgencyPaymentsData(long itemId,long agencyId) throws BusinessException{
		getTransactionsPersistenceManager().deleteAgencyPaymentData(itemId);
	}

	//Bank Transactions Data
	public List<BankTranxsDataDO> getAgencyBankTranxsData(long agencyId) throws BusinessException{
		return getTransactionsPersistenceManager().getAgencyBankTranxsData(agencyId);
	}

	public void saveAgencyBankTranxsData(Map<String, String[]> requestParams, long agencyId) throws BusinessException{
		List<BankTranxsDataDO> doList = new ArrayList<>();
		//Items
		String[] btdates = requestParams.get("btdate");
		String[] bids = requestParams.get("bid");
		String[] btamts = requestParams.get("btamt");
		String[] btcharges = requestParams.get("btcharges");
		String[] tts = requestParams.get("tt");
		String[] mops = requestParams.get("mop");
		String[] instrds = requestParams.get("instrd");
		String[] nars = requestParams.get("nar");
		BankTransxDataBO boObj = new BankTransxDataBO();
		int ttype = 0;
		for(int i=0; i<btdates.length;i++) {
			if(tts[i].equalsIgnoreCase("5")) {
				String[] tbids = requestParams.get("tbid");
				doList.add(boObj.createDO(btdates[i], Long.parseLong(bids[i]), btamts[i], btcharges[i],
						1, Integer.parseInt(mops[i]), instrds[i], 0, nars[i], agencyId));
				doList.add(boObj.createDO(btdates[i], Long.parseLong(tbids[i]), btamts[i], "0.00",
						2, Integer.parseInt(mops[i]), instrds[i], 0, nars[i], agencyId));
				ttype = 1;// For Transfer
			} else {
				doList.add(boObj.createDO(btdates[i], Long.parseLong(bids[i]), btamts[i], btcharges[i],
						Integer.parseInt(tts[i]), Integer.parseInt(mops[i]), instrds[i], 0, nars[i], agencyId));
				ttype = 2;
			}
		}
		getTransactionsPersistenceManager().saveAgencyBankTranxsData(doList,ttype,agencyId);
	}

	public void deleteAgencyBankTranxsData(long itemId,long agencyId) throws BusinessException{
		getTransactionsPersistenceManager().deleteAgencyBankTranxsData(itemId, agencyId);
	}

	//Credit Notes Data
	public List<CreditNotesDO> getAgencyCreditNotesData(long agencyId) throws BusinessException{
		return getTransactionsPersistenceManager().getAgencyCreditNotes(agencyId);
	}

	public void saveAgencyCreditNotesData(Map<String, String[]> requestParams, long agencyId) throws BusinessException{
		
		String[] ndate = requestParams.get("ndate");
		String[] cvoid = requestParams.get("cvo_id");
		String[] cinvnos = requestParams.get("cinvno");
		String[] crtypes = requestParams.get("crtype");
		String[] refDate = requestParams.get("ref_date");

		//Items
		String[] epids = requestParams.get("epid");
		String[] egsts = requestParams.get("egst");
		String[] tamts = requestParams.get("tamt");
		String[] igstas = requestParams.get("igsta");
		String[] cgstas = requestParams.get("cgsta");
		String[] sgstas = requestParams.get("sgsta");
		String[] camts = requestParams.get("camt");
		String[] nbids = requestParams.get("nbid");
		String[] nreasons = requestParams.get("nreason");
		String[] cvocat = requestParams.get("cvo_cat");
		
		if(refDate[0].equals("")) {
			refDate[0]="0";
		}
		CreditNotesBO boObj = new CreditNotesBO();
		CreditNotesDO dataDO = boObj.createDO(ndate[0], Integer.parseInt(crtypes[0]), cinvnos[0], 
				Long.parseLong(cvoid[0]), Long.parseLong(refDate[0]), Long.parseLong(epids[0]), egsts[0], tamts[0], igstas[0], cgstas[0], 
				sgstas[0], camts[0], Long.parseLong(nbids[0]), Integer.parseInt(nreasons[0]), agencyId);
		BankTransxDataBO bankTranxBO = new BankTransxDataBO();
		int pr = 2;
		if(crtypes[0].equalsIgnoreCase("2"))
			pr=1;
		BankTranxsDataDO bankTranxDO = bankTranxBO.createDO(ndate[0], Long.parseLong(nbids[0]), camts[0], "0", pr, 3, "NA", 7, "", agencyId);
		
		AgencyCVOBalanceDataBO cvoDataBO = new AgencyCVOBalanceDataBO();
		int cnflag = (Integer.parseInt(crtypes[0]) == 1) ? 81 : 82 ;

    	getTransactionsPersistenceManager().saveAgencyCreditNotes(dataDO,bankTranxDO,
    			cvoDataBO.createDO(camts[0], Integer.parseInt(cvocat[0]), agencyId,5,"0", cnflag, Long.parseLong(cvoid[0]), ndate[0], "NA"));
	}

	public void deleteAgencyCreditNotesData(long itemId,long agencyId) throws BusinessException{
		getTransactionsPersistenceManager().deleteCreditNotes(itemId,agencyId);
	}

	//Debit Notes Data
	public List<DebitNotesDO> getAgencyDebitNotesData(long agencyId) throws BusinessException{
		return getTransactionsPersistenceManager().getAgencyDebitNotes(agencyId);
	}

	public void saveAgencyDebitNotesData(Map<String, String[]> requestParams, long agencyId) throws BusinessException{
		
		String[] ndate = requestParams.get("ndate");
		String[] cvoid = requestParams.get("cvo_id");
		String[] cinvnos = requestParams.get("cinvno");
		String[] crtypes = requestParams.get("crtype");
		String[] refDate = requestParams.get("ref_date");
		
		//Items
		String[] epids = requestParams.get("epid");
		String[] egsts = requestParams.get("egst");
		String[] tamts = requestParams.get("tamt");
		String[] igstas = requestParams.get("igsta");
		String[] cgstas = requestParams.get("cgsta");
		String[] sgstas = requestParams.get("sgsta");
		String[] camts = requestParams.get("camt");
		String[] nbids = requestParams.get("nbid");
		String[] nreasons = requestParams.get("nreason");
		String[] cvocat = requestParams.get("cvo_cat");
		
		if(refDate[0].equals(""))
		{
			refDate[0]="0";
		}

		DebitNotesBO boObj = new DebitNotesBO();
		DebitNotesDO dataDO = boObj.createDO(ndate[0], Integer.parseInt(crtypes[0]), cinvnos[0], 
				Long.parseLong(cvoid[0]), Long.parseLong(refDate[0]), Long.parseLong(epids[0]), egsts[0], tamts[0], igstas[0], cgstas[0], 
				sgstas[0], camts[0], Long.parseLong(nbids[0]), Integer.parseInt(nreasons[0]), agencyId);
		BankTransxDataBO bankTranxBO = new BankTransxDataBO();
		int pr = 1;
		if(crtypes[0].equalsIgnoreCase("2"))
			pr=2;
		BankTranxsDataDO bankTranxDO = bankTranxBO.createDO(ndate[0], Long.parseLong(nbids[0]), camts[0], "0", pr, 3, "NA", 7, "", agencyId);
		
		AgencyCVOBalanceDataBO cvoDataBO = new AgencyCVOBalanceDataBO();
		int dnflag = (Integer.parseInt(crtypes[0]) == 1) ? 91 : 92 ;

		getTransactionsPersistenceManager().saveAgencyDebitNotes(dataDO,bankTranxDO, 
				cvoDataBO.createDO( camts[0],Integer.parseInt(cvocat[0]),agencyId,6,"0", dnflag, Long.parseLong(cvoid[0]),ndate[0],"NA"));
	}

	public void deleteAgencyDebitNotesData(long itemId,long agencyId) throws BusinessException{
		getTransactionsPersistenceManager().deleteDebitNotes(itemId,agencyId);
	}


	//Purchase Return Data
	public List<PurchaseReturnDO> getAgencyPurchaseReturnData(long agencyId) throws BusinessException{
		return getTransactionsPersistenceManager().getPurchaseReturnsData(agencyId);
	}

	public void saveAgencyPurchaseReturnData(Map<String, String[]> requestParams, long agencyId) throws BusinessException {
		List<PurchaseReturnDetailsDO> doList = new ArrayList<>();
		List<AgencyStockDataDO> asdoList = new ArrayList<>();
		
		String[] invrefno = requestParams.get("pinvno");
		String[] invdate = requestParams.get("invdate");
		String[] invrefdate = requestParams.get("refdate");
		String[] sramt = requestParams.get("pr_t_amt");
		String[] custid = requestParams.get("cvo_id");
		String[] nare = requestParams.get("nar");
		//Items
		String[] pids = requestParams.get("pid");
		String[] gstps = requestParams.get("gstp");
		String[] rqtys = requestParams.get("rqty");
		String[] urs = requestParams.get("ur");
		String[] nws = requestParams.get("nw");
		String[] tamts = requestParams.get("tamt");
		String[] igstas = requestParams.get("igsta");
		String[] cgstas = requestParams.get("cgsta");
		String[] sgstas = requestParams.get("sgsta");
		String[] namts = requestParams.get("namt");
		String[] vnos = requestParams.get("vno");
		String[] cvocat = requestParams.get("cvo_cat");

		PurchaseReturnBO boObj = new PurchaseReturnBO();
		AgencyStockDataBO asboObj = new AgencyStockDataBO();
		
		for(int i=0; i<pids.length;i++) {
			doList.add(boObj.createPurchaseReturnDetailsDO(Integer.parseInt(pids[i]), gstps[i], Integer.parseInt(rqtys[i]), 
					urs[i], nws[i], tamts[i], igstas[i], cgstas[i], sgstas[i], namts[i], vnos[i])); 

        	asdoList.add(asboObj.createDO(0,3,"", invdate[0], 3, Integer.parseInt(pids[i]),0,0,0,0,0,Long.parseLong(custid[0]),"NA", agencyId));
		}
		
		AgencyCVOBalanceDataBO cvoDataBO = new AgencyCVOBalanceDataBO();
		
		getTransactionsPersistenceManager().savePurchaseReturnsData(boObj.createDO(invrefno[0], invdate[0],Long.parseLong(invrefdate[0]), 
				Long.parseLong(custid[0]), sramt[0], nare[0], agencyId) ,doList, asdoList, 
				cvoDataBO.createDO(sramt[0],Integer.parseInt(cvocat[0]),agencyId,7,"0",10,Long.parseLong(custid[0]),invdate[0],"NA"));
	}

	public void deleteAgencyPurchaseReturnData(long itemId,long agencyId) throws BusinessException{
		getTransactionsPersistenceManager().deletePurchaseReturnsData(itemId);
	}

	//Sales Return Data
	public List<SalesReturnDO> getAgencySaleReturnData(long agencyId) throws BusinessException{
		return getTransactionsPersistenceManager().getSalesReturnsData(agencyId);
	}


	
	public void saveAgencySaleReturnData(Map<String, String[]> requestParams, long agencyId) throws BusinessException{
		List<SalesReturnDetailsDO> doList = new ArrayList<>();
		List<AgencyStockDataDO> asdoList = new ArrayList<>();
		
		BankTransxDataBO bankTranxBO = new BankTransxDataBO();
		List<BankTranxsDataDO> bankTranxDOList = new ArrayList<>();
		
		String[] invrefno = requestParams.get("sinvno");
		String[] invdate = requestParams.get("invdate");
		String[] sramt = requestParams.get("sr_t_amt");
		String[] custid = requestParams.get("cvo_id");
		String[] pt = requestParams.get("pt");
		String[] nare = requestParams.get("nar");
		//Items
		String[] pids = requestParams.get("pid");
		String[] gstps = requestParams.get("gstp");
		String[] rqtys = requestParams.get("rqty");
		String[] urs = requestParams.get("ur");
		String[] nws = requestParams.get("nw");
		String[] tamts = requestParams.get("tamt");
		String[] bankIds = requestParams.get("bid");
		String[] igstas = requestParams.get("igsta");
		String[] cgstas = requestParams.get("cgsta");
		String[] sgstas = requestParams.get("sgsta");
		String[] namts = requestParams.get("namt");

		SaleReturnBO boObj = new SaleReturnBO();
		AgencyStockDataBO asboObj = new AgencyStockDataBO();

		AgencyCVOBalanceDataBO cvoDataBO = new AgencyCVOBalanceDataBO();
		AgencyCVOBalanceDataDO cvoDOObj = new AgencyCVOBalanceDataDO();

		for(int i=0; i<pids.length;i++){
			int mop = Integer.parseInt(pt[0]);
			doList.add(boObj.createSalesReturnDetailsDO(Integer.parseInt(pids[i]), gstps[i], Integer.parseInt(rqtys[i]), 
					urs[i], nws[i], tamts[i], Long.parseLong(bankIds[i]), igstas[i], cgstas[i], sgstas[i], namts[i]));

        	asdoList.add(asboObj.createDO(0,7,"", invdate[0], 4,Integer.parseInt(pids[i]),0,0,0,0,0,Long.parseLong(custid[0]),"NA", agencyId));
        	if(mop != 1) {
            	  bankTranxDOList.add(bankTranxBO.createDO(invdate[0], Long.parseLong(bankIds[i]), namts[i], "0", 1, 
            			  mop, "NA", 0,nare[0], agencyId));
        	}
		}
		cvoDOObj = cvoDataBO.createDO(sramt[0],1, agencyId, 8,"0",11, Long.parseLong(custid[0]), invdate[0],"NA");
		getTransactionsPersistenceManager().saveSalesReturnsData(boObj.createDO(invrefno[0], invdate[0], 
				Long.parseLong(custid[0]), sramt[0], Integer.parseInt(pt[0]), nare[0], agencyId) ,doList, asdoList, bankTranxDOList, 
				cvoDOObj);
		
	}

	public void deleteAgencySaleReturnData(long itemId,long agencyId) throws BusinessException{
		getTransactionsPersistenceManager().deleteSalesReturnsData(itemId);
	}

	//Assets Data
	public List<AssetDataDO> getAgencyAssetsData(long agencyId) throws BusinessException{
		return getTransactionsPersistenceManager().getAgencyAssetsData(agencyId);
	}

	public void saveAgencyAssetsData(Map<String, String[]> requestParams, long agencyId) throws BusinessException{
		List<AssetDataDO> doList = new ArrayList<>();
		List<BankTranxsDataDO> bankTranxDOList = new ArrayList<>();
		List<AgencyCashDataDO> agencycashDOList = new ArrayList<>();
		
		String[] atdates = requestParams.get("atdate");
		String[] atts = requestParams.get("att");
		String[] idescs = requestParams.get("idesc");
		String[] amahs = requestParams.get("amah");
		String[] mops = requestParams.get("mop");
		String[] bids = requestParams.get("bid");
		String[] sids = requestParams.get("sid");
		String[] avs = requestParams.get("av");
		AssetDataBO boObj = new AssetDataBO();
		AgencyCashDataBO agencycashBO = new AgencyCashDataBO();
		BankTransxDataBO bankTranxBO = new BankTransxDataBO();
		for(int i=0; i<atdates.length;i++){
			doList.add(boObj.createDO(atdates[i], Integer.parseInt(atts[i]), idescs[i], Integer.parseInt(amahs[i]), 
					Integer.parseInt(mops[i]), Long.parseLong(bids[i]), avs[i], Long.parseLong(sids[i]), agencyId));
			
			int att = Integer.parseInt(atts[i]);
			int tranxType = att == 1 ? 1 : (att == 2 ? 2 : 3);
			if(tranxType==2 && Integer.parseInt(mops[i])==1){
				agencycashDOList.add(agencycashBO.createDO(atdates[i],8,avs[i],agencyId));
			}
			long bankId = Long.parseLong(bids[i]);
			if(tranxType != 3 && bankId>0) {
				bankTranxDOList.add(bankTranxBO.createDO(atdates[i], bankId, avs[i], "0", tranxType, 
						Integer.parseInt(mops[i]), "NA", 0, "", agencyId));
			}
		}
		getTransactionsPersistenceManager().saveAgencyAssetsData(doList,agencycashDOList, bankTranxDOList);
	}

	public void deleteAgencyAssetData(long itemId,long agencyId) throws BusinessException{
		getTransactionsPersistenceManager().deleteAgencyAssetData(itemId);
	}

	//TVs Data
	public List<TVDataDO> getAgencyTVData(long agencyId) throws BusinessException{
		return getTransactionsPersistenceManager().getAgencyTCData(agencyId);
	}

	public void saveAgencyTVData(Map<String, String[]> requestParams, long agencyId) throws BusinessException{
		TVDataBO bo = new TVDataBO();
		AgencyStockDataBO asdataBO = new AgencyStockDataBO();
		List<AgencyStockDataDO> asdoList = new ArrayList<>();
		
		String[] tvDate = requestParams.get("tv_date");
		String[] custno = requestParams.get("custn");
		String[] staffId = requestParams.get("staff_id");
		String[] tvAmount = requestParams.get("invamt");
		String[] sc = requestParams.get("tvcategory");
		String[] bankId = requestParams.get("bankId");
		String[] deptAmt = requestParams.get("depamt");
		//Items
		String[] epid = requestParams.get("epid");
		String[] nocyl = requestParams.get("nocyl");
		String[] noreg = requestParams.get("noreg");
		String[] cyld = requestParams.get("cyld");
		String[] regd = requestParams.get("regd");
		String[] iamt = requestParams.get("iamt");
		String[] acs = requestParams.get("acs");
		String[] ac = requestParams.get("ac");
		String[] sgsta = requestParams.get("cgsta");
		String[] cgsta = requestParams.get("sgsta");
		String[] pt = requestParams.get("pt");
		
		BankTransxDataBO bankTranxBO = new BankTransxDataBO();
		BankTranxsDataDO bankTranxDO = bankTranxBO.createDO(tvDate[0], Long.parseLong(bankId[0]), deptAmt[0], "0", 2, 3, "NA", 6, "", agencyId);

		AgencyStockDataDO asdataDO = asdataBO.createDO(0,10,"", tvDate[0], 7,Integer.parseInt(epid[0]),0,0,0,0,0,0,"NA", agencyId);
		AgencyStockDataDO asdataDO1 = asdataBO.createDO(0,10,"", tvDate[0], 7,10,0,0,0,0,0,0,"NA", agencyId);
		asdoList.add(asdataDO);
		asdoList.add(asdataDO1);
		
		getTransactionsPersistenceManager().saveAgencyTVData(bo.createDO(tvDate[0], custno[0], Integer.parseInt(sc[0]), 
				Long.parseLong(staffId[0]), tvAmount[0], Integer.parseInt(epid[0]), Integer.parseInt(nocyl[0]), Integer.parseInt(noreg[0]), 
				cyld[0], regd[0], iamt[0], cgsta[0], sgsta[0], Integer.parseInt(acs[0]) , ac[0], pt[0], agencyId), bankTranxDO, asdoList);
	}

	public void deleteAgencyTVData(long itemId,long agencyId, long bankId) throws BusinessException{
		getTransactionsPersistenceManager().deleteTVData(itemId, agencyId,bankId);
	}

	//RCs Data
	public List<RCDataDO> getAgencyRCData(long agencyId) throws BusinessException{
		return getTransactionsPersistenceManager().getAgencyRCData(agencyId);
	}

	public void saveAgencyRCData(Map<String, String[]> requestParams, long agencyId) throws BusinessException{
		RCDataBO bo = new RCDataBO();
		AgencyStockDataBO asdataBO = new AgencyStockDataBO();
		
		String[] rcDate = requestParams.get("rc_date");
		String[] custno = requestParams.get("custn");
		String[] staffId = requestParams.get("staff_id");
		String[] rcAmount = requestParams.get("invamt");
		String[] bankId = requestParams.get("bankId");
		String[] deptAmt = requestParams.get("depamt");
		//Items
		String[] epid = requestParams.get("epid");
		String[] nocyl = requestParams.get("nocyl");
		String[] noreg = requestParams.get("noreg");
		String[] cyld = requestParams.get("cyld");
		String[] regd = requestParams.get("regd");
		String[] dgcca = requestParams.get("dgcc");
		String[] acs = requestParams.get("acs");
		String[] ac = requestParams.get("ac");
		String[] cgsta = requestParams.get("cgsta");
		String[] sgsta = requestParams.get("sgsta");
		String[] scgsta = requestParams.get("scgsta");
		String[] ssgsta = requestParams.get("ssgsta");
		String[] pt = requestParams.get("pt");
		
		String[] opbasicPrice = requestParams.get("opbasicprice");
		String[] optaxableas = requestParams.get("optaxablea");
		String[] optotas = requestParams.get("optota");
		String[] opcgstas = requestParams.get("opcgsta");
		String[] opsgstas = requestParams.get("opsgsta");
		String[] opigstas = requestParams.get("opigsta");
		
		BankTransxDataBO bankTranxBO = new BankTransxDataBO();
		BankTranxsDataDO bankTranxDO = bankTranxBO.createDO(rcDate[0], Long.parseLong(bankId[0]), deptAmt[0], "0", 1, 3, "NA", 6, "", agencyId);

		AgencyStockDataDO asdataDO = asdataBO.createDO(0,9,"", rcDate[0], 6,10,0,0,0,0,0,0,"NA", agencyId);
		
		getTransactionsPersistenceManager().saveAgencyRCData(bo.createDO(rcDate[0], custno[0], Long.parseLong(staffId[0]), rcAmount[0], 
				Integer.parseInt(epid[0]), Integer.parseInt(nocyl[0]), Integer.parseInt(noreg[0]), cyld[0], regd[0], dgcca[0], 
				cgsta[0], sgsta[0], scgsta[0], ssgsta[0], Integer.parseInt(acs[0]), ac[0], pt[0], agencyId,
				opbasicPrice[0], optaxableas[0], optotas[0], opcgstas[0], opsgstas[0], opigstas[0]),
				bankTranxDO, asdataDO);		
	}

	public void deleteAgencyRCData(long itemId,long agencyId, long bankId) throws BusinessException{
		getTransactionsPersistenceManager().deleteRCData(itemId, agencyId,bankId);
	}

	//NCs Data
	public List<NCDBCInvoiceDO> getAgencyNCDBCData(long agencyId) throws BusinessException{
		return getTransactionsPersistenceManager().getAgencyNCDBCInvoices(agencyId);
	}

	public void saveAgencyNCDBCData(Map<String, String[]> requestParams, long agencyId) throws BusinessException{
		NCDBCDataBO dataBO = new NCDBCDataBO();
		List<NCDBCInvoiceDetailsDO> doList = new ArrayList<>();
		List<AgencyStockDataDO> asdoList = new ArrayList<>();
		AgencyStockDataBO asboObj = new AgencyStockDataBO();
		
		String[] bomIds = requestParams.get("bomId");
		String[] invDate = requestParams.get("inv_date");
		String[] ctypes = requestParams.get("ctype");
		String[] custno = requestParams.get("custn");
		String[] staffId = requestParams.get("staff_id");
		String[] invAmount = requestParams.get("invamt");
		String[] totalDeposit = requestParams.get("tdamt");
		String[] nocs = requestParams.get("nos");
		String[] cashrs = requestParams.get("cashr");
		String[] aros = requestParams.get("aro");
		String[] bankId = requestParams.get("bank_id");
		String[] starbankId = requestParams.get("bankId");
		String[] totalDiscount = requestParams.get("tdup");
		
		//Items
		String[] pids = requestParams.get("pid");
		String[] qtys = requestParams.get("qty");
		String[] ups = requestParams.get("up");
		String[] gstps = requestParams.get("gstp");
		String[] das = requestParams.get("da");
		String[] dups = requestParams.get("dup");
		String[] bps = requestParams.get("bp");
		String[] sgsta = requestParams.get("cgsta");
		String[] cgsta = requestParams.get("sgsta");
		String[] pas = requestParams.get("pa");
		
		String[] opbasicPrice = requestParams.get("opbasicprice");
		String[] optaxableas = requestParams.get("optaxablea");
		String[] optotas = requestParams.get("optota");
		String[] opcgstas = requestParams.get("opcgsta");
		String[] opsgstas = requestParams.get("opsgsta");
		String[] opigstas = requestParams.get("opigsta");
		
		for(int i=0; i<pids.length;i++){
			doList.add(dataBO.createNCDBCInvoiceDetails(Integer.parseInt(nocs[0]),Long.parseLong(pids[i]), 
					gstps[i], Integer.parseInt(qtys[i]), ups[i], dups[i], bps[i], das[i],sgsta[i], cgsta[i], pas[i],
					opbasicPrice[i], optaxableas[i], optotas[i], opcgstas[i], opsgstas[i], opigstas[i]));

			asdoList.add(asboObj.createDO(0,8,"", invDate[0], 5, Integer.parseInt(pids[i]),0,0,0,0,0,0,"", agencyId));
		}
		
		BankTransxDataBO bankTranxBO = new BankTransxDataBO();
		BankTransxDataBO bankTranxBO1 = new BankTransxDataBO();
		BankTranxsDataDO bankTranxDO = bankTranxBO.createDO(invDate[0], Long.parseLong(bankId[0]), aros[0], "0", 2, 3, "NA", 6, "", agencyId);
		BankTranxsDataDO bankTranxDO1 = bankTranxBO1.createDO(invDate[0], Long.parseLong(starbankId[0]),totalDeposit[0], "0", 1, 3, "NA", 6, "", agencyId);		

		List<BankTranxsDataDO> btdo = new ArrayList<>();
		btdo.add(bankTranxDO);
		btdo.add(bankTranxDO1);
		getTransactionsPersistenceManager().saveAgencyNCDBCInvoices(dataBO.createDO(invDate[0], custno[0], Long.parseLong(staffId[0]), 
				totalDeposit[0], invAmount[0], Integer.parseInt(ctypes[0]), Long.parseLong(bomIds[0]), Integer.parseInt(nocs[0]), 
				cashrs[0], aros[0], agencyId, Long.parseLong(bankId[0]), totalDiscount[0] ), doList, btdo, asdoList);
	}

	public void deleteAgencyNCDBCData(long itemId,long agencyId,long starId) throws BusinessException{
		getTransactionsPersistenceManager().deleteAgencyNCDBCInvoices(itemId, agencyId,starId);
	}
}
