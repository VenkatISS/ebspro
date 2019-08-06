package com.it.erpapp.framework;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.it.erpapp.framework.bos.AgencyCVOBalanceDataBO;
import com.it.erpapp.framework.bos.AgencyStockDataBO;
import com.it.erpapp.framework.bos.transactions.ARBPurchaseInvoiceBO;
import com.it.erpapp.framework.bos.transactions.ARBSalesInvoiceBO;
import com.it.erpapp.framework.bos.transactions.BankTransxDataBO;
import com.it.erpapp.framework.bos.transactions.COMSalesInvoiceBO;
import com.it.erpapp.framework.bos.transactions.DOMSalesInvoiceBO;
import com.it.erpapp.framework.bos.transactions.DeliveryChallanBO;
import com.it.erpapp.framework.bos.transactions.OtherPurchaseInvoiceBO;
import com.it.erpapp.framework.bos.transactions.PurchaseInvoiceBO;
import com.it.erpapp.framework.bos.transactions.QuotationsBO;
import com.it.erpapp.framework.managers.TransactionsPersistenceManager;
import com.it.erpapp.framework.model.AgencyCVOBalanceDataDO;
import com.it.erpapp.framework.model.AgencyStockDataDO;
import com.it.erpapp.framework.model.transactions.ARBPurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.OtherPurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.PurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.others.BankTranxsDataDO;
import com.it.erpapp.framework.model.transactions.others.CreditNotesDO;
import com.it.erpapp.framework.model.transactions.others.DebitNotesDO;
import com.it.erpapp.framework.model.transactions.others.PaymentsDataDO;
import com.it.erpapp.framework.model.transactions.others.ReceiptsDataDO;
import com.it.erpapp.framework.model.transactions.sales.ARBSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.ARBSalesInvoiceDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.COMSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.COMSalesInvoiceDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.DOMSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.DOMSalesInvoiceDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.DeliveryChallanDO;
import com.it.erpapp.framework.model.transactions.sales.DeliveryChallanDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.NCDBCInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.PurchaseReturnDO;
import com.it.erpapp.framework.model.transactions.sales.QuotationDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.QuotationsDO;
import com.it.erpapp.framework.model.transactions.sales.RCDataDO;
import com.it.erpapp.framework.model.transactions.sales.TVDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class TransactionsDataFactory {

	private TransactionsPersistenceManager getTransactionsPersistenceManager() {
		return new TransactionsPersistenceManager();
	}

	// Purchase Invoices Data
	public List<PurchaseInvoiceDO> getAgencyPurchaseInvoices(long agencyId) throws BusinessException {
		return getTransactionsPersistenceManager().getAgencyPurchaseInvoices(agencyId);
	}

	public List<PurchaseInvoiceDO> saveAgencyPurchaseInvoice(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException {
		List<PurchaseInvoiceDO> doList = new ArrayList<>();
		List<BankTranxsDataDO> btList = new ArrayList<>();
		List<AgencyStockDataDO> asdDOList = new ArrayList<>();

		String[] omc_id = requestParams.get("omc_id");
		String[] inv_no = requestParams.get("inv_ref_no");
		String[] invDate = requestParams.get("inv_date");
		String[] stkRecvdDate = requestParams.get("s_r_date");
		String[] bankId = requestParams.get("bankId");

		// Items
		String[] epids = requestParams.get("epid");
		String[] eords = requestParams.get("eord");
		String[] ups = requestParams.get("up");
		String[] qtys = requestParams.get("qty");
		String[] vps = requestParams.get("vp");
		String[] ocs = requestParams.get("oc");
		String[] bps = requestParams.get("bp");
		String[] igstas = requestParams.get("igsta");
		String[] sgstas = requestParams.get("sgsta");
		String[] cgstas = requestParams.get("cgsta");
		String[] amts = requestParams.get("amt");

		PurchaseInvoiceBO boObj = new PurchaseInvoiceBO();
		BankTransxDataBO bankTranxBO = new BankTransxDataBO();
		AgencyStockDataBO asboObj = new AgencyStockDataBO();

		for (int i = 0; i < epids.length; i++) {
			doList.add(boObj.createDO(Long.parseLong(omc_id[0]), inv_no[0], invDate[0], stkRecvdDate[0],
					Integer.parseInt(epids[i]), Integer.parseInt(eords[i]), ups[i], Integer.parseInt(qtys[i]), vps[i],
					ocs[i], bps[i], igstas[i], sgstas[i], cgstas[i], amts[i], agencyId));

			btList.add(bankTranxBO.createDO(invDate[0], Long.parseLong(bankId[0]), amts[i], "0", 1, 3, "NA", 7, "",
					agencyId));

			asdDOList.add(asboObj.createDO(0, 1, inv_no[0], stkRecvdDate[0], 1, Integer.parseInt(epids[i]), 0, 0, 0, 0,
					0, Long.parseLong(omc_id[0]), "NA", agencyId));
		}

		getTransactionsPersistenceManager().saveAgencyPurchaseInvoices(doList, btList, asdDOList);
		return getTransactionsPersistenceManager().getAgencyPurchaseInvoices(agencyId);
	}

	public List<PurchaseInvoiceDO> deleteAgencyPurchaseInvoice(long itemId, long agencyId, long bankId)
			throws BusinessException {
		getTransactionsPersistenceManager().deleteAgencyPurchaseInvoices(itemId, bankId);
		return getTransactionsPersistenceManager().getAgencyPurchaseInvoices(agencyId);
	}

	// ARB Purchase Invoices Data
	public List<ARBPurchaseInvoiceDO> getAgencyARBPurchaseInvoices(long agencyId) throws BusinessException {
		return getTransactionsPersistenceManager().getAgencyARBPurchaseInvoices(agencyId);
	}

	public List<ARBPurchaseInvoiceDO> saveAgencyARBPurchaseInvoice(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException {
		List<ARBPurchaseInvoiceDO> doList = new ArrayList<>();
		List<AgencyStockDataDO> asdoList = new ArrayList<>();

		String[] inv_no = requestParams.get("inv_ref_no");
		String[] invDate = requestParams.get("inv_date");
		String[] stkRecvdDate = requestParams.get("s_r_date");
		String[] vid = requestParams.get("vid");
		String[] rcyn = requestParams.get("rcyn");
		// Items
		String[] epids = requestParams.get("epid");
		String[] ups = requestParams.get("up");
		String[] qtys = requestParams.get("qty");
		String[] vps = requestParams.get("vp");
		String[] ocs = requestParams.get("oc");
		String[] bps = requestParams.get("bp");
		String[] igstas = requestParams.get("igsta");
		String[] sgstas = requestParams.get("sgsta");
		String[] cgstas = requestParams.get("cgsta");
		String[] amts = requestParams.get("amt");

		ARBPurchaseInvoiceBO boObj = new ARBPurchaseInvoiceBO();
		AgencyStockDataBO asboObj = new AgencyStockDataBO();

		AgencyCVOBalanceDataBO cvoDataBO = new AgencyCVOBalanceDataBO();
		List<AgencyCVOBalanceDataDO> cvoDOList = new ArrayList<>();

		for (int i = 0; i < epids.length; i++) {
			doList.add(boObj.createDO(inv_no[0], invDate[0], stkRecvdDate[0], Integer.parseInt(epids[i]),
					Integer.parseInt(vid[0]), Integer.parseInt(rcyn[0]), ups[i], Integer.parseInt(qtys[i]), vps[i],
					ocs[i], bps[i], igstas[i], sgstas[i], cgstas[i], amts[i], agencyId));

			asdoList.add(asboObj.createDO(0, 2, inv_no[0], stkRecvdDate[0], 1, 0, Integer.parseInt(epids[i]), 0, 0, 0,
					0, Long.parseLong(vid[0]), "NA", agencyId));

			cvoDOList.add(cvoDataBO.createDO(amts[i], 0, agencyId, 1, inv_no[0], 1, Integer.parseInt(vid[0]),
					invDate[0], "NA"));
		}

		getTransactionsPersistenceManager().saveAgencyARBPurchaseInvoices(doList, asdoList, cvoDOList);
		return getTransactionsPersistenceManager().getAgencyARBPurchaseInvoices(agencyId);
	}

	public List<ARBPurchaseInvoiceDO> deleteAgencyARBPurchaseInvoice(long itemId, long agencyId)
			throws BusinessException {
		getTransactionsPersistenceManager().deleteARBAgencyPurchaseInvoices(itemId);
		return getTransactionsPersistenceManager().getAgencyARBPurchaseInvoices(agencyId);
	}

	// Other Purchase Invoices Data
	public List<OtherPurchaseInvoiceDO> getAgencyOTHERPurchaseInvoices(long agencyId) throws BusinessException {
		return getTransactionsPersistenceManager().getAgencyOTHERPurchaseInvoices(agencyId);
	}

	public void saveAgencyOTHERPurchaseInvoice(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException {
		List<OtherPurchaseInvoiceDO> doList = new ArrayList<>();
		String[] inv_no = requestParams.get("inv_ref_no");
		String[] invDate = requestParams.get("inv_date");
		String[] vid = requestParams.get("vid");
		String[] rcyn = requestParams.get("rcyn");
		// Items
		String[] pnames = requestParams.get("pname");
		String[] tbls = requestParams.get("tbl");
		String[] hsncs = requestParams.get("hsnc");
		String[] egsts = requestParams.get("egst");
		String[] minhs = requestParams.get("minh");
		String[] chs = requestParams.get("ch");
		String[] ahs = requestParams.get("ah");
		String[] urs = requestParams.get("ur");
		String[] uoms = requestParams.get("uom");
		String[] qtys = requestParams.get("qty");
		String[] tvals = requestParams.get("tval");
		String[] igstas = requestParams.get("igsta");
		String[] sgstas = requestParams.get("sgsta");
		String[] cgstas = requestParams.get("cgsta");
		String[] tas = requestParams.get("ta");
		OtherPurchaseInvoiceBO boObj = new OtherPurchaseInvoiceBO();

		AgencyCVOBalanceDataBO cvoDataBO = new AgencyCVOBalanceDataBO();
		List<AgencyCVOBalanceDataDO> cvoDOList = new ArrayList<>();
		for (int i = 0; i < pnames.length; i++) {

			doList.add(boObj.createDO(inv_no[0], invDate[0], Integer.parseInt(vid[0]), Integer.parseInt(rcyn[0]),
					pnames[i], Integer.parseInt(tbls[i]), hsncs[i], egsts[i], Integer.parseInt(minhs[i]),
					Integer.parseInt(chs[i]), Integer.parseInt(ahs[i]), urs[i], Integer.parseInt(qtys[i]),
					Integer.parseInt(uoms[i]), tvals[i], igstas[i], sgstas[i], cgstas[i], tas[i], agencyId));

			cvoDOList.add(cvoDataBO.createDO(tas[i], 3, agencyId, 1, inv_no[0], 2, Integer.parseInt(vid[0]), invDate[0],
					"NA"));
		}

		getTransactionsPersistenceManager().saveAgencyOTHERPurchaseInvoices(doList, cvoDOList);
	}

	public List<OtherPurchaseInvoiceDO> deleteAgencyOTHERPurchaseInvoice(long itemId, long agencyId)
			throws BusinessException {
		getTransactionsPersistenceManager().deleteOTHERAgencyPurchaseInvoices(itemId);
		return getTransactionsPersistenceManager().getAgencyOTHERPurchaseInvoices(agencyId);
	}

	// Quotations Data
	public List<QuotationsDO> getAgencyQuotations(long agencyId) throws BusinessException {
		return getTransactionsPersistenceManager().getAgencyQuotations(agencyId);
	}

	public QuotationsDO getQuotationsById(long saleInvoiceId) throws BusinessException {
		return getTransactionsPersistenceManager().getAgencyQSInvoiceById(saleInvoiceId);
	}

	public List<QuotationsDO> saveAgencyQuotation(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException {
		QuotationsBO qbo = new QuotationsBO();
		List<QuotationDetailsDO> doList = new ArrayList<>();
		String[] qtnDate = requestParams.get("qtn_date");
		String[] custId = requestParams.get("cust_id");
		String[] custname = requestParams.get("cust_name");
		String[] staffId = requestParams.get("staff_id");
		String[] qtnAmount = requestParams.get("qtn_c_amt");
		String[] fn = requestParams.get("fn");
		
		// Items
		String[] epids = requestParams.get("epid");
		String[] vps = requestParams.get("vp");
		String[] qtys = requestParams.get("qty");
		String[] ups = requestParams.get("up");
		String[] upds = requestParams.get("upd");
		String[] bps = requestParams.get("bp");
		String[] igstas = requestParams.get("igsta");
		String[] sgstas = requestParams.get("sgsta");
		String[] cgstas = requestParams.get("sgsta");
		String[] amts = requestParams.get("amt");
		

		for (int i = 0; i < epids.length; i++) {
			doList.add(qbo.createQuotationDetailsDO(Long.parseLong(epids[i]), vps[i], Integer.parseInt(qtys[i]), ups[i],
					upds[i], bps[i], igstas[i], sgstas[i], cgstas[i], amts[i]));
		}

		getTransactionsPersistenceManager().saveAgencyQuotations(qbo.createDO(qtnDate[0], Long.parseLong(custId[0]),custname[0], 
				Long.parseLong(staffId[0]), qtnAmount[0], fn[0], agencyId), doList);
		return getTransactionsPersistenceManager().getAgencyQuotations(agencyId);
	}

	public List<QuotationsDO> deleteAgencyQuotation(long itemId, long agencyId) throws BusinessException {
		getTransactionsPersistenceManager().deleteQuotations(itemId, agencyId);
		return getTransactionsPersistenceManager().getAgencyQuotations(agencyId);
	}

	// Dom Sales Data
	public List<DOMSalesInvoiceDO> getAgencyDOMSalesInvoices(long agencyId) throws BusinessException {
		return getTransactionsPersistenceManager().getAgencyDSInvoices(agencyId);
	}

	public DOMSalesInvoiceDO getAgencyDOMSalesInvoicesById(long saleInvoiceId) throws BusinessException {
		return getTransactionsPersistenceManager().getAgencyDSInvoiceById(saleInvoiceId);
	}

	public void saveAgencyDOMSalesInvoice(Map<String, String[]> requestParams, long agencyId) throws BusinessException {
		String[] siDate = requestParams.get("si_date");
		String[] custId = requestParams.get("cust_id");
		String[] siamt = requestParams.get("si_amt");
		String[] ciamt = requestParams.get("c_amt");
		String[] pts = requestParams.get("pt");

		// Items
		String[] epids = requestParams.get("epid");
		String[] qtys = requestParams.get("qty");
		String[] ups = requestParams.get("up");
		String[] upds = requestParams.get("upd");
		String[] precs = requestParams.get("prec");
		String[] psvcs = requestParams.get("psvc");
		String[] igsts = requestParams.get("igsta");
		String[] sgsts = requestParams.get("sgsta");
		String[] cgsts = requestParams.get("cgsta");
		String[] sids = requestParams.get("sid");
		String[] acids = requestParams.get("acid");
		String[] siamts = requestParams.get("siamt");
		String[] vps = requestParams.get("vatp");
		String[] onlineBankId = requestParams.get("bid");
		String[] onlineamt = requestParams.get("onlinercvdamt");
		
		String[] opbasicPrice = requestParams.get("opbasicprice");
		String[] optaxableas = requestParams.get("optaxablea");
		String[] optotas = requestParams.get("optota");
		String[] opcgstas = requestParams.get("opcgsta");
		String[] opsgstas = requestParams.get("opsgsta");
		String[] opigstas = requestParams.get("opigsta");

		DOMSalesInvoiceBO dataBO = new DOMSalesInvoiceBO();
		List<DOMSalesInvoiceDetailsDO> doList = new ArrayList<>();

		BankTransxDataBO bankTranxBO = new BankTransxDataBO();
		List<BankTranxsDataDO> bankTranxDO = new ArrayList<>();

		AgencyStockDataBO asdataBO = new AgencyStockDataBO();
		List<AgencyStockDataDO> asdoList = new ArrayList<>();

		BigDecimal discount = BigDecimal.ZERO;
		for (int i = 0; i < epids.length; i++) {
			doList.add(dataBO.createDOMSalesInvoiceDetailsDO(Integer.parseInt(epids[i]), Integer.parseInt(qtys[i]),
					ups[i], upds[i], Integer.parseInt(precs[i]), Integer.parseInt(psvcs[i]), Long.parseLong(sids[i]),
					Long.parseLong(acids[i]), vps[i], igsts[i], sgsts[i], cgsts[i], siamts[i],
					Long.parseLong(onlineBankId[i]), onlineamt[i], opbasicPrice[i], optaxableas[i], optotas[i], opcgstas[i], opsgstas[i], opigstas[i]));
			bankTranxDO.add(bankTranxBO.createDO(siDate[0], Long.parseLong(onlineBankId[i]), onlineamt[i], "0", 2, 3,
					"NA", 7, "", agencyId));

			int quantityAftrPsvP = (Integer.parseInt(qtys[i]) - Integer.parseInt(precs[i]))
					- Integer.parseInt(psvcs[i]);
			discount = discount.add((new BigDecimal(quantityAftrPsvP)).multiply(new BigDecimal(upds[i])));

			asdoList.add(asdataBO.createDO(0, 4, "", siDate[0], 2, Integer.parseInt(epids[i]), 0, 0, 0, 0, 0,
					Long.parseLong(custId[0]), "", agencyId));

		}

		AgencyCVOBalanceDataBO cvoDataBO = new AgencyCVOBalanceDataBO();

		int cvoflag = 0;
		if (Integer.parseInt(pts[0]) == 1)
			cvoflag = 31; // cash
		else if (Integer.parseInt(pts[0]) == 2)
			cvoflag = 32; // credit
		getTransactionsPersistenceManager().saveAgencyDSInvoices(
				dataBO.createDO(siDate[0], Long.parseLong(custId[0]), Integer.parseInt(pts[0]), siamt[0], ciamt[0],
						agencyId, discount.toString()),
				doList, bankTranxDO, asdoList, cvoDataBO.createDO(siamt[0], 1, agencyId, 2, "0", cvoflag,
						Long.parseLong(custId[0]), siDate[0], discount.toString()));
	}

	public void deleteAgencyDOMSalesInvoice(long itemId, long agencyId) throws BusinessException {
		getTransactionsPersistenceManager().deleteAgencyDSInvoices(itemId, agencyId);
	}

	// Com Sales Data
	public List<COMSalesInvoiceDO> getAgencyCOMSalesInvoices(long agencyId) throws BusinessException {
		return getTransactionsPersistenceManager().getAgencyCSInvoices(agencyId);
	}

	public COMSalesInvoiceDO getAgencyCOMSalesInvoicesById(long invId) throws BusinessException {
		return getTransactionsPersistenceManager().getAgencyCSInvoicesById(invId);
	}

	public void saveAgencyCOMSalesInvoice(Map<String, String[]> requestParams, long agencyId) throws BusinessException {
		COMSalesInvoiceBO dataBO = new COMSalesInvoiceBO();
		List<COMSalesInvoiceDetailsDO> doList = new ArrayList<>();

		AgencyStockDataBO asdataBO = new AgencyStockDataBO();
		List<AgencyStockDataDO> asdoList = new ArrayList<>();

		BankTransxDataBO bankTranxBO = new BankTransxDataBO();
		List<BankTranxsDataDO> bankTranxDO = new ArrayList<>();
		
		AgencyCVOBalanceDataBO cvoDataBO = new AgencyCVOBalanceDataBO();
		AgencyCVOBalanceDataDO cvoDOObj = new AgencyCVOBalanceDataDO();

		String[] siDate = requestParams.get("si_date");
		String[] custId = requestParams.get("cust_id");
		String[] siamt = requestParams.get("si_amt");
		String[] ciamt = requestParams.get("c_amt");
		String[] pt = requestParams.get("pt");

		// Items
		String[] epids = requestParams.get("epid");
		String[] qtys = requestParams.get("qty");
		String[] ups = requestParams.get("up");
		String[] upds = requestParams.get("upd");
		String[] precs = requestParams.get("prec");
		String[] psvcs = requestParams.get("psvc");
		String[] sids = requestParams.get("sid");
		String[] acids = requestParams.get("acid");
		String[] bankIds = requestParams.get("bid");
		String[] igsts = requestParams.get("igsta");
		String[] sgsts = requestParams.get("sgsta");
		String[] cgsts = requestParams.get("cgsta");
		String[] siamts = requestParams.get("siamt");
		String[] vps = requestParams.get("vatp");
		
		String[] opbasicPrice = requestParams.get("opbasicprice");
		String[] optaxableas = requestParams.get("optaxablea");
		String[] optotas = requestParams.get("optota");
		String[] opcgstas = requestParams.get("opcgsta");
		String[] opsgstas = requestParams.get("opsgsta");
		String[] opigstas = requestParams.get("opigsta");

		BigDecimal disc = BigDecimal.ZERO;
		int mop = Integer.parseInt(pt[0]);
		for (int i = 0; i < epids.length; i++) {	
			doList.add(dataBO.createCOMSalesInvoiceDetailsDO(Integer.parseInt(epids[i]), Integer.parseInt(qtys[i]),
					ups[i], upds[i], Integer.parseInt(precs[i]), Integer.parseInt(psvcs[i]), Long.parseLong(bankIds[i]) ,
					Long.parseLong(sids[i]), Long.parseLong(acids[i]), vps[i], igsts[i], sgsts[i], cgsts[i], siamts[i],
					opbasicPrice[i], optaxableas[i], optotas[i], opcgstas[i], opsgstas[i], opigstas[i]));
							
			int quantityAftrPsvP = (Integer.parseInt(qtys[i]) - Integer.parseInt(precs[i]));
			disc = disc.add((new BigDecimal(quantityAftrPsvP)).multiply(new BigDecimal(upds[i])));

			asdoList.add(asdataBO.createDO(0, 5, "", siDate[0], 2, Integer.parseInt(epids[i]), 0, 0, 0, 0, 0,
					Long.parseLong(custId[0]), "", agencyId));
			if(mop == 3) {
				bankTranxDO.add(bankTranxBO.createDO(siDate[0], Long.parseLong(bankIds[i]), siamts[i], "0", 2, mop,
						"NA", 0, "", agencyId));
			}
		}

		int cvoflag = 0;
		if (Integer.parseInt(pt[0]) == 1)
			cvoflag = 41; // cash
		else if (Integer.parseInt(pt[0]) == 2)
			cvoflag = 42; // credit
		
		cvoDOObj=cvoDataBO.createDO(siamt[0], 1, agencyId, 2, "0", cvoflag, Long.parseLong(custId[0]), siDate[0], disc.toString());

		getTransactionsPersistenceManager().saveAgencyCSInvoices(
				dataBO.createDO(siDate[0], Long.parseLong(custId[0]), siamt[0], ciamt[0], Integer.parseInt(pt[0]), agencyId, disc.toString()),
				doList, bankTranxDO, asdoList, cvoDOObj);
	}
	
	public void deleteAgencyCOMSalesInvoice(long itemId, long agencyId) throws BusinessException {
		getTransactionsPersistenceManager().deleteAgencyCSInvoices(itemId, agencyId);
	}

	// ARB Sales Data
	public List<ARBSalesInvoiceDO> getAgencyARBSalesInvoices(long agencyId) throws BusinessException {
		return getTransactionsPersistenceManager().getAgencyARBSInvoices(agencyId);
	}

	public ARBSalesInvoiceDO getAgencyARBSalesInvoicesById(long invId) throws BusinessException {
		return getTransactionsPersistenceManager().getAgencyARBInvoicesById(invId);
	}

	public void saveAgencyARBSalesInvoice(Map<String, String[]> requestParams, long agencyId) throws BusinessException {
		ARBSalesInvoiceBO dataBO = new ARBSalesInvoiceBO();
		List<ARBSalesInvoiceDetailsDO> doList = new ArrayList<>();

		AgencyStockDataBO asdataBO = new AgencyStockDataBO();
		List<AgencyStockDataDO> asdoList = new ArrayList<>();

		BankTransxDataBO bankTranxBO = new BankTransxDataBO();
		List<BankTranxsDataDO> bankTranxDO = new ArrayList<>();
		
		AgencyCVOBalanceDataBO cvoDataBO = new AgencyCVOBalanceDataBO();
		AgencyCVOBalanceDataDO cvoDOObj = new AgencyCVOBalanceDataDO();

		String[] siDate = requestParams.get("si_date");
		String[] custId = requestParams.get("cust_id");
		String[] sid = requestParams.get("staff_id");
		String[] siamt = requestParams.get("si_amt");
		String[] pt = requestParams.get("pt");
		// Items
		String[] epids = requestParams.get("epid");
		String[] qtys = requestParams.get("qty");
		String[] ups = requestParams.get("up");
		String[] upds = requestParams.get("upd");
		String[] bankIds = requestParams.get("bid");
		String[] igsts = requestParams.get("igsta");
		String[] sgsts = requestParams.get("sgsta");
		String[] cgsts = requestParams.get("cgsta");
		String[] siamts = requestParams.get("siamt");
		String[] vps = requestParams.get("vatp");

		String[] opbasicPrice = requestParams.get("opbasicprice");
		String[] optaxableas = requestParams.get("optaxablea");
		String[] optotas = requestParams.get("optota");
		String[] opcgstas = requestParams.get("opcgsta");
		String[] opsgstas = requestParams.get("opsgsta");
		String[] opigstas = requestParams.get("opigsta");
		
		BigDecimal disc = BigDecimal.ZERO;
		int mop = Integer.parseInt(pt[0]);
		for (int i = 0; i < epids.length; i++) {
			doList.add(dataBO.createARBSalesInvoiceDetailsDO(Long.parseLong(epids[i]), Integer.parseInt(qtys[i]),
					ups[i], upds[i], vps[i], Long.parseLong(bankIds[i]), igsts[i], sgsts[i], cgsts[i], siamts[i],
					opbasicPrice[i], optaxableas[i], optotas[i], opcgstas[i], opsgstas[i], opigstas[i]));
			disc = disc.add((new BigDecimal(qtys[i])).multiply(new BigDecimal(upds[i])));

			asdoList.add(asdataBO.createDO(0, 6, "", siDate[0], 2, 0, Integer.parseInt(epids[i]), 0, 0, 0, 0,
					Long.parseLong(custId[0]), "", agencyId));

			if(mop == 3) {
				bankTranxDO.add(bankTranxBO.createDO(siDate[0], Long.parseLong(bankIds[i]), siamts[i], "0", 2, mop,"NA", 0, "", agencyId));
			}
		}

		int cvoflag = 0;
		if (Integer.parseInt(pt[0]) == 1)
			cvoflag = 51; // cash
		else if (Integer.parseInt(pt[0]) == 2)
			cvoflag = 52; // credit
		
		cvoDOObj = cvoDataBO.createDO(siamt[0], 1, agencyId, 2, "0", cvoflag, Long.parseLong(custId[0]), siDate[0], disc.toString());
		
		getTransactionsPersistenceManager().saveAgencyARBSInvoices(
				dataBO.createDO(siDate[0], Long.parseLong(custId[0]), Long.parseLong(sid[0]), siamt[0],
						Integer.parseInt(pt[0]), agencyId, disc.toString()),
				doList, asdoList, bankTranxDO, cvoDOObj);
	}

	public void deleteAgencyARBSalesInvoice(long itemId, long agencyId) throws BusinessException {
		getTransactionsPersistenceManager().deleteAgencyARBSInvoices(itemId, agencyId);
	}

	// Delivery Challans Data
	public List<DeliveryChallanDO> getAgencyDeliveryChallan(long agencyId) throws BusinessException {
		return getTransactionsPersistenceManager().getAgencyDeliveryChallans(agencyId);
	}

	public DeliveryChallanDO getDeliveryChallenById(long invId) throws BusinessException {
		return getTransactionsPersistenceManager().getAgencyDeliveryChallenById(invId);
	}

	public void saveAgencyDeliveryChallan(Map<String, String[]> requestParams, long agencyId) throws BusinessException {
		DeliveryChallanBO dcbo = new DeliveryChallanBO();
		List<DeliveryChallanDetailsDO> doList = new ArrayList<>();
		String[] dcDate = requestParams.get("dc_date");
		String[] dcrefdate = requestParams.get("dcrefdate");
		String[] custId = requestParams.get("cust_id");
		String[] staffId = requestParams.get("staff_id");
		String[] fleetId = requestParams.get("fleet_id");
		String[] dcAmount = requestParams.get("dc_amt");
		String[] dinstrs = requestParams.get("dinstr");
		String[] invno = requestParams.get("inv_no");
		String[] dm = requestParams.get("dm");
		// Items
		String[] epids = requestParams.get("epid");
		String[] vps = requestParams.get("vp");
		String[] qtys = requestParams.get("qty");
		String[] ups = requestParams.get("up");
		String[] bps = requestParams.get("bp");
		String[] igstas = requestParams.get("igsta");
		String[] sgstas = requestParams.get("sgsta");
		String[] cgstas = requestParams.get("sgsta");
		String[] amts = requestParams.get("amt");

		for (int i = 0; i < epids.length; i++) {
			doList.add(dcbo.createDeliveryChallanDetailsDO(epids[i], vps[i], Integer.parseInt(qtys[i]), ups[i], bps[i],
					igstas[i], sgstas[i], cgstas[i], amts[i]));
		}

		getTransactionsPersistenceManager().saveAgencyDeliveryChallan(dcbo.createDO(dcDate[0],
				Long.parseLong(dcrefdate[0]), Long.parseLong(custId[0]), Long.parseLong(staffId[0]), dm[0], invno[0],
				Long.parseLong(fleetId[0]), dcAmount[0], dinstrs[0], agencyId), doList);
	}

	public void deleteAgencyDeliveryChallan(long itemId, long agencyId) throws BusinessException {
		getTransactionsPersistenceManager().deleteDeliveryChallan(itemId, agencyId);
	}

	public PurchaseReturnDO getAgencyPurchaseReturnById(long invId) throws BusinessException {
		return getTransactionsPersistenceManager().getAgencyPurchaseReturnById(invId);
	}

	public NCDBCInvoiceDO getAgencyNCDBCById(long invId) throws BusinessException {
		return getTransactionsPersistenceManager().getAgencyNCDBCById(invId);
	}

	public List<RCDataDO> getAgencyRCById(long invId) throws BusinessException {
		return getTransactionsPersistenceManager().getAgencyRCById(invId);
	}

	public List<TVDataDO> getAgencyTVById(long invId) throws BusinessException {
		return getTransactionsPersistenceManager().getAgencyTVById(invId);
	}

	public CreditNotesDO getAgencyCreditNoteById(long invId) throws BusinessException {
		return getTransactionsPersistenceManager().getAgencyCreditNoteById(invId);
	}

	public DebitNotesDO getAgencyDebitNoteById(long invId) throws BusinessException {
		return getTransactionsPersistenceManager().getAgencyDebitNoteById(invId);
	}
	public PaymentsDataDO getAgencyPaymentsDataById(long invId) throws BusinessException {
		return getTransactionsPersistenceManager().getAgencyPaymentsDataById(invId);
	}
	public ReceiptsDataDO getAgencyReceiptsDataById(long invId) throws BusinessException {
		return getTransactionsPersistenceManager().getAgencyReceiptsDataById(invId);
	}
	
}
