package com.it.erpapp.framework;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.it.erpapp.framework.managers.ReportsPersistenceManager;
import com.it.erpapp.framework.model.AgencyCVOBalanceDataDO;
import com.it.erpapp.framework.model.AgencyCashDataDO;
import com.it.erpapp.framework.model.AgencyStockDataDO;
import com.it.erpapp.framework.model.GSTPaymentDetailsDO;
import com.it.erpapp.framework.model.transactions.ARBPurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.PayablesDO;
import com.it.erpapp.framework.model.transactions.PurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.ReceivablesDO;
import com.it.erpapp.framework.model.transactions.others.BankTranxsDataDO;
import com.it.erpapp.framework.model.transactions.others.CreditNotesDO;
import com.it.erpapp.framework.model.transactions.others.DebitNotesDO;
import com.it.erpapp.framework.model.transactions.others.ReceiptsDataDO;
import com.it.erpapp.framework.model.transactions.sales.ARBSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.COMSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.DOMSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.NCDBCInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.PurchaseReturnDO;
import com.it.erpapp.framework.model.transactions.sales.SalesReturnDO;
import com.it.erpapp.framework.model.vos.NCDBCRCTVReportVO;
import com.it.erpapp.framework.model.vos.StockReportVO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class ReportsDataFactory {

	private ReportsPersistenceManager getReportsPersistenceManager() {
		return new ReportsPersistenceManager();
	}

	// Cylinder Purchase Reports Data
	public List<PurchaseInvoiceDO> fetchAgencyPurReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");
		String[] pcode = requestParams.get("pid");
		String[] ven = requestParams.get("pv");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		int pc = Integer.parseInt(pcode[0]);
		if (pc > 0) {
			return getReportsPersistenceManager().getAgencyPurInvoicesByDateRangeAndProductType(agencyId, fdl, tdl,
					Integer.parseInt(pcode[0]));
		} else {
			return getReportsPersistenceManager().getAgencyPurchaseInvoicesByDateRangeAndVendor(agencyId, fdl, tdl,
					Long.parseLong(ven[0]));
		}
	}

	// Stock Ledger Reports Data
	public List<AgencyStockDataDO> fetchAgencyStockLedgerReportByProductId(Map<String, String[]> requestParams,
			long agencyId) throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");
		String[] pcode = requestParams.get("pid");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		int pc = Integer.parseInt(pcode[0]);

		return getReportsPersistenceManager().getAgencyStockLedgerDataByDateRangeAndProductType(agencyId, fdl, tdl, pc);
	}

	// Stock Ledger Report Opening Balance Data
	public String fetchAgencyStockLedgerReportOpsByProductId(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] pcode = requestParams.get("pid");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		int pc = Integer.parseInt(pcode[0]);

		return getReportsPersistenceManager().getAgencyStockLedgerOpsByProductType(agencyId, fdl, pc);
	}

	// Cylinder Purchase Reports Data
	public List<PurchaseInvoiceDO> fetchAgencyPurchaseReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");
		String[] pcode = requestParams.get("pid");
		String[] ven = requestParams.get("pv");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		int pc = Integer.parseInt(pcode[0]);
		if (pc > 0) {
			return getReportsPersistenceManager().getAgencyPurchaseInvoicesByDateRangeAndProductType(agencyId, fdl, tdl,
					Integer.parseInt(pcode[0]));
		} else {
			return getReportsPersistenceManager().getAgencyPurchaseInvoicesByDateRangeAndVendor(agencyId, fdl, tdl,
					Long.parseLong(ven[0]));
		}
	}

	// ARB Purchase Reports Data
	public List<ARBPurchaseInvoiceDO> fetchAgencyARBPurReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");
		String[] pcode = requestParams.get("pid");
		String[] ven = requestParams.get("pv");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		long pc = Long.parseLong(pcode[0]);
		if (pc > 0) {
			return getReportsPersistenceManager().getAgencyARBPurInvoicesByDateRangeAndProductType(agencyId, fdl, tdl,
					Long.parseLong(pcode[0]));
		} else {
			return getReportsPersistenceManager().getAgencyARBPurchaseInvoicesByDateRangeAndVendor(agencyId, fdl, tdl,
					Long.parseLong(ven[0]));
		}
	}

	// ARB Purchase Reports Data
	public List<ARBPurchaseInvoiceDO> fetchAgencyARBPurchaseReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");
		String[] pcode = requestParams.get("pid");
		String[] ven = requestParams.get("pv");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		long pc = Long.parseLong(pcode[0]);
		if (pc > 0) {
			return getReportsPersistenceManager().getAgencyARBPurchaseInvoicesByDateRangeAndProductType(agencyId, fdl,
					tdl, Long.parseLong(pcode[0]));
		} else {
			return getReportsPersistenceManager().getAgencyARBPurchaseInvoicesByDateRangeAndVendor(agencyId, fdl, tdl,
					Long.parseLong(ven[0]));
		}
	}

	// DOM Cylinder Sale Reports Data
	public List<DOMSalesInvoiceDO> fetchAgencyDOMCSaleReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");
		String[] pcode = requestParams.get("pid");
		String[] sids = requestParams.get("pv");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		int pc = Integer.parseInt(pcode[0]);
		if (pc > 0) {
			return getReportsPersistenceManager().getAgencyDSInvoicesByDateRangeAndProductType(agencyId, fdl, tdl,
					Integer.parseInt(pcode[0]));
		} else {
			return getReportsPersistenceManager().getAgencyDSInvoicesByDateRangeAndStaff(agencyId, fdl, tdl,
					Long.parseLong(sids[0]));
		}
	}

	// DOM Cylinder Sale Reports Data
	public List<DOMSalesInvoiceDO> fetchAgencyDOMSaleReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");
		String[] pcode = requestParams.get("pid");
		String[] sids = requestParams.get("pv");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		int pc = Integer.parseInt(pcode[0]);
		if (pc > 0) {
			return getReportsPersistenceManager().getAgencyDSRInvoicesByDateRangeAndProductType(agencyId, fdl, tdl,
					Integer.parseInt(pcode[0]));
		} else {
			return getReportsPersistenceManager().getAgencyDSInvoicesByDateRangeAndStaff(agencyId, fdl, tdl,
					Long.parseLong(sids[0]));
		}
	}

	// DOM Cylinder Sale Reports Data from NC DBC
	public List<NCDBCInvoiceDO> fetchAgencyNCDOMCSaleReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");
		String[] pcode = requestParams.get("pid");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyNCSInvoicesByDateRangeAndProductType(agencyId, fdl, tdl,
				Integer.parseInt(pcode[0]));
	}

	// COM Cylinder Sale Reports Data
	public List<COMSalesInvoiceDO> fetchAgencyCOMSaleReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");
		String[] pcode = requestParams.get("pid");
		String[] sids = requestParams.get("pv");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		int pc = Integer.parseInt(pcode[0]);
		if (pc > 0) {
			return getReportsPersistenceManager().getAgencyCSRInvoicesByDateRangeAndProductType(agencyId, fdl, tdl,
					Integer.parseInt(pcode[0]));
		} else {
			return getReportsPersistenceManager().getAgencyCSInvoicesByDateRangeAndStaff(agencyId, fdl, tdl,
					Long.parseLong(sids[0]));
		}
	}

	// ARB Sale Reports Data
	public List<ARBSalesInvoiceDO> fetchAgencyARBRSaleReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");
		String[] pcode = requestParams.get("pid");
		String[] sids = requestParams.get("pv");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		int pc = Integer.parseInt(pcode[0]);
		if (pc > 0) {
			return getReportsPersistenceManager().getAgencyARBSRInvoicesByDateRangeAndProductType(agencyId, fdl, tdl,
					Long.parseLong(pcode[0]));
		} else {
			return getReportsPersistenceManager().getAgencyARBSInvoicesByDateRangeAndStaff(agencyId, fdl, tdl,
					Long.parseLong(sids[0]));
		}
	}

	// COM Cylinder Sale Reports Data
	public List<COMSalesInvoiceDO> fetchAgencyCOMCSaleReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");
		String[] pcode = requestParams.get("pid");
		String[] sids = requestParams.get("pv");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		int pc = Integer.parseInt(pcode[0]);
		if (pc > 0) {
			return getReportsPersistenceManager().getAgencyCSInvoicesByDateRangeAndProductType(agencyId, fdl, tdl,
					Integer.parseInt(pcode[0]));
		} else {
			return getReportsPersistenceManager().getAgencyCSInvoicesByDateRangeAndStaff(agencyId, fdl, tdl,
					Long.parseLong(sids[0]));
		}
	}

	// COM Cylinder Sale Reports Data NC Data
	public List<NCDBCInvoiceDO> fetchAgencyNCCOMCSaleReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");
		String[] pcode = requestParams.get("pid");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyNCSInvoicesByDateRangeAndProductType(agencyId, fdl, tdl,
				Integer.parseInt(pcode[0]));
	}

	// Regulator Reports Data NC Data
	public List<NCDBCInvoiceDO> fetchAgencyRegulatorSaleReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");
		String[] pcode = requestParams.get("pid");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyRegulatorSalesByDateRangeAndProductType(agencyId, fdl, tdl,
				Integer.parseInt(pcode[0]));
	}

	// NCDBCRCTV Report Methods
	public List<NCDBCRCTVReportVO> fetchAgencyNCDBCForNCDBCRCTVReport(Map<String, String[]> requestParams,
			long agencyId) throws ParseException {

		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");
		String[] pcode = requestParams.get("pid");
		String[] cts = requestParams.get("ct");
		String[] deps = requestParams.get("dep");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyNCDBCByTypeProductDeposit(agencyId, fdl, tdl,
				Integer.parseInt(pcode[0]), Integer.parseInt(cts[0]), Integer.parseInt(deps[0]));
	}

	public List<NCDBCRCTVReportVO> fetchAgencyRCForNCDBCRCTVReport(Map<String, String[]> requestParams, long agencyId)
			throws ParseException {

		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");
		String[] pcode = requestParams.get("pid");
		String[] cts = requestParams.get("ct");
		String[] deps = requestParams.get("dep");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyRCByTypeProductDeposit(agencyId, fdl, tdl,
				Integer.parseInt(pcode[0]), Integer.parseInt(cts[0]), Integer.parseInt(deps[0]));
	}

	public List<NCDBCRCTVReportVO> fetchAgencyTVForNCDBCRCTVReport(Map<String, String[]> requestParams, long agencyId)
			throws ParseException {

		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");
		String[] pcode = requestParams.get("pid");
		String[] cts = requestParams.get("ct");
		String[] deps = requestParams.get("dep");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyTVByTypeProductDeposit(agencyId, fdl, tdl,
				Integer.parseInt(pcode[0]), Integer.parseInt(cts[0]), Integer.parseInt(deps[0]));
	}

	// ARB Sale Reports Data
	public List<ARBSalesInvoiceDO> fetchAgencyARBSaleReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");
		String[] pcode = requestParams.get("pid");
		String[] sids = requestParams.get("pv");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		int pc = Integer.parseInt(pcode[0]);
		if (pc > 0) {
			return getReportsPersistenceManager().getAgencyARBSInvoicesByDateRangeAndProductType(agencyId, fdl, tdl,
					Long.parseLong(pcode[0]));
		} else {
			return getReportsPersistenceManager().getAgencyARBSInvoicesByDateRangeAndStaff(agencyId, fdl, tdl,
					Long.parseLong(sids[0]));
		}
	}

	// ARB Sale Reports Data NC ARB
	public List<NCDBCInvoiceDO> fetchAgencyNCARBSaleReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");
		String[] pcode = requestParams.get("pid");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyNCSInvoicesByDateRangeAndProductType(agencyId, fdl, tdl,
				Integer.parseInt(pcode[0]));
	}
	// ARB Sale Reports Data NC ARB

	public List<NCDBCInvoiceDO> fetchAgencyNCDBCARBSaleReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");
		String[] pcode = requestParams.get("pid");
		String[] sids = requestParams.get("pv");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		int pc = Integer.parseInt(pcode[0]);
		if (pc > 0) {
			return getReportsPersistenceManager().getAgencyNCDBCSInvoicesByDateRangeAndProductType(agencyId, fdl, tdl,
					Integer.parseInt(pcode[0]));
		} else {
			return getReportsPersistenceManager().getAgencyNCDBCSInvoicesByDateRangeAndStaff(agencyId, fdl, tdl,
					Long.parseLong(sids[0]));
		}
	}

	// Bank Transactions Reports Data
	public List<BankTranxsDataDO> fetchAgencyBankTransactionsReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");
		String[] bankId = requestParams.get("bid");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyBankTransactionsByDateRangeAndBankId(agencyId, fdl, tdl,
				Long.parseLong(bankId[0]));
	}

	public StockReportVO fetchAgencyDOMCStockReport(Map<String, String[]> requestParams, long agencyId)
			throws ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");
		String[] pcode = requestParams.get("pid");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyDOMCStockByDateRangeAndProductType(agencyId, fdl, tdl,
				Integer.parseInt(pcode[0]));
	}

	public StockReportVO fetchAgencyCOMCStockReport(Map<String, String[]> requestParams, long agencyId)
			throws ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");
		String[] pcode = requestParams.get("pid");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyCOMCStockByDateRangeAndProductType(agencyId, fdl, tdl,
				Integer.parseInt(pcode[0]));
	}

	public StockReportVO fetchAgencyARBStockReport(Map<String, String[]> requestParams, long agencyId)
			throws ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");
		String[] pcode = requestParams.get("pid");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyARBStockByDateRangeAndProductType(agencyId, fdl, tdl,
				Long.parseLong(pcode[0]));
	}

	// DAY END
	// DOM Cylinders Data
	public List<PurchaseInvoiceDO> fetchAgencyDAYENDDOMPurchaseReport(Map<String, String[]> requestParams,
			long agencyId) throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyDAYENDDOMPurchaseInvoicesByDateRangeAndProductType(agencyId, fdl,
				tdl);
	}

	public List<DOMSalesInvoiceDO> fetchAgencyDAYENDDOMCSaleReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyDAYENDDSInvoicesByDateRangeAndProductType(agencyId, fdl, tdl);
	}

	public List<PurchaseInvoiceDO> fetchAgencyDAYENDCOMPurchaseReport(Map<String, String[]> requestParams,
			long agencyId) throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyDAYENDCOMPurchaseInvoicesByDateRangeAndProductType(agencyId, fdl,
				tdl);
	}

	public List<COMSalesInvoiceDO> fetchAgencyDAYENDCOMCSaleReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyDAYENDCSInvoicesByDateRangeAndProductType(agencyId, fdl, tdl);
	}

	// ARB Data
	public List<ARBPurchaseInvoiceDO> fetchAgencyDAYENDARBPurchaseReport(Map<String, String[]> requestParams,
			long agencyId) throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyDAYENDARBPurchaseInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<ARBSalesInvoiceDO> fetchAgencyDAYENDARBSaleReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyDAYENDARBSInvoicesByDateRange(agencyId, fdl, tdl);
	}

	// Bank Transactions Reports Data
	public List<BankTranxsDataDO> fetchAgencyDAYENDBankTransactionsReport(Map<String, String[]> requestParams,
			long agencyId) throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyDAYENDBankTransactionsByDateRangeAndBankId(agencyId, fdl, tdl);
	}

	// Party type Ledger Report Opening Balance Data

	public String fetchOpeningBalanceLedgerByPartyType(Map<String, String[]> requestParams, long agencyId, long cvoId)
			throws BusinessException, ParseException {

		String[] fromdate = requestParams.get("fd");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();

		return getReportsPersistenceManager().getOpeningBalanceLedgerByPartyType(agencyId, fdl, cvoId);
	}

	// Ledger
	public List<AgencyCVOBalanceDataDO> fetchTransactionReportCVOId(Map<String, String[]> requestParams, long agencyId,
			long cvoId) throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyTransactionReportByDateRangeAndCVOId(agencyId, fdl, tdl, cvoId);
	}

	// GST PAYMENTS Report Data
	public List<GSTPaymentDetailsDO> fetchAgencyGSTPaymentDetailsReport(Map<String, String[]> requestParams,
			long agencyId) throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyGSTPaymentDetailsReportByDateRange(agencyId, fdl, tdl);

	}

	public List<COMSalesInvoiceDO> fetchCOMSalesInvoiceByCustomerId(Map<String, String[]> requestParams, long agencyId,
			long cvoId) throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyCOMSInvoicesByDateRangeAndCustomerId(agencyId, fdl, tdl, cvoId);
	}

	public List<ARBSalesInvoiceDO> fetchARBSalesInvoiceByCustomerId(Map<String, String[]> requestParams, long agencyId,
			long cvoId) throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyARBSInvoicesByDateRangeAndCustomerId(agencyId, fdl, tdl, cvoId);
	}

	public List<ReceiptsDataDO> fetchReceiptsByCustomerId(Map<String, String[]> requestParams, long agencyId,
			long cvoId) throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyReceiptsByDateRangeAndCustomerId(agencyId, fdl, tdl, cvoId);
	}

	public List<SalesReturnDO> fetchSalesReturnsByCustomerId(Map<String, String[]> requestParams, long agencyId,
			long cvoId) throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencySalesReturnsByDateRangeAndCustomerId(agencyId, fdl, tdl, cvoId);
	}

	public List<CreditNotesDO> fetchCreditNotesByCustomerId(Map<String, String[]> requestParams, long agencyId,
			long cvoId) throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyCreditNotesByDateRangeAndCustomerId(agencyId, fdl, tdl, cvoId);
	}

	public List<DebitNotesDO> fetchDebitNotesByCustomerId(Map<String, String[]> requestParams, long agencyId,
			long cvoId) throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyDebitNotesByDateRangeAndCustomerId(agencyId, fdl, tdl, cvoId);
	}

	public List<AgencyCashDataDO> fetchAgencyCashTransactionsReport(Map<String, String[]> requestParams, long agencyId,
			String[] cvoids) throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyCashTransactionsByDateRangeAndCashId(agencyId, fdl, tdl,
				Long.parseLong(cvoids[0]));
	}

	public List<PurchaseReturnDO> fetchPurchaseReturnsByCustomerId(Map<String, String[]> requestParams, long agencyId,
			long cvoId) throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyPurchaseReturnsByDateRangeAndCustomerId(agencyId, fdl, tdl,
				cvoId);
	}

	public List<SalesReturnDO> fetchSalesReturnsByProductType(Map<String, String[]> requestParams, long agencyId,
			int pc) throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencySalesReturnsByDateRangeAndProductType(agencyId, fdl, tdl, pc);
	}

	public List<PurchaseReturnDO> fetchPurchaseReturnsByProductType(Map<String, String[]> requestParams, long agencyId,
			int pc) throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyPurchaseReturnsByDateRangeAndProductType(agencyId, fdl, tdl, pc);
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	public List<ReceivablesDO> fetchReceivablesByCustomerId(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		System.out.println(requestParams.get("ad"));
		String[] asondate = requestParams.get("ad");
		System.out.println(asondate);
		System.out.println(asondate[0]);

		long customerId = Long.parseLong(requestParams.get("cs")[0]);
		Date d = new Date();
		long adl = d.parse(asondate[0]);
		return getReportsPersistenceManager().getAgencyReceivablesByCustomerId(agencyId, adl, customerId);
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	public List<PayablesDO> fetchPayablesByVendorId(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] asondate = requestParams.get("ad");
		long vendorId = Long.parseLong(requestParams.get("vs")[0]);
		Date d = new Date();
		long adl = d.parse(asondate[0]);
		System.out.println(adl);
		return getReportsPersistenceManager().getAgencyPayablesByVendorId(agencyId, adl, vendorId);
	}

	// Profitability Analysis
	// Cylinder Purchase Reports Data
	public List<PurchaseInvoiceDO> fetchMonthlyAgencyPurchaseReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyPurchaseInvoicesByDateRangeAndProductType(agencyId, fdl, tdl, 0);
	}

	// ARB Purchase Reports Data
	public List<ARBPurchaseInvoiceDO> fetchMonthlyAgencyARBPurchaseReport(Map<String, String[]> requestParams,
			long agencyId) throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getReportsPersistenceManager().getAgencyARBPurchaseInvoicesByDateRangeAndProductType(agencyId, fdl, tdl,
				0);
	}

}
