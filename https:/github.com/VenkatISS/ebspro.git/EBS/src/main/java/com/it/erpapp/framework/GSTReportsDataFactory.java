package com.it.erpapp.framework;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.it.erpapp.framework.managers.GSTReportsPersistenceManager;
import com.it.erpapp.framework.model.GSTPaymentDetailsDO;
import com.it.erpapp.framework.model.GSTPaymentsDataDO;
import com.it.erpapp.framework.model.transactions.ARBPurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.OtherPurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.PurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.others.CreditNotesDO;
import com.it.erpapp.framework.model.transactions.others.DebitNotesDO;
import com.it.erpapp.framework.model.transactions.sales.ARBSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.COMSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.DOMSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.NCDBCInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.PurchaseReturnDO;
import com.it.erpapp.framework.model.transactions.sales.RCDataDO;
import com.it.erpapp.framework.model.transactions.sales.SalesReturnDO;
import com.it.erpapp.framework.model.transactions.sales.TVDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class GSTReportsDataFactory {

	private GSTReportsPersistenceManager getGSTReportsPersistenceManager() {
		return new GSTReportsPersistenceManager();
	}

	// DOM Cylinders Data
	public List<PurchaseInvoiceDO> fetchAgencyGSTDOMPurchaseReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReportsPersistenceManager().getAgencyGSTDOMPurchaseInvoicesByDateRangeAndProductType(agencyId, fdl,
				tdl);
	}

	public List<DOMSalesInvoiceDO> fetchAgencyGSTDOMCSaleReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReportsPersistenceManager().getAgencyGSTDSInvoicesByDateRangeAndProductType(agencyId, fdl, tdl);
	}

	public List<PurchaseInvoiceDO> fetchAgencyGSTCOMPurchaseReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReportsPersistenceManager().getAgencyGSTCOMPurchaseInvoicesByDateRangeAndProductType(agencyId, fdl,
				tdl);
	}

	public List<COMSalesInvoiceDO> fetchAgencyGSTCOMCSaleReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReportsPersistenceManager().getAgencyGSTCSInvoicesByDateRangeAndProductType(agencyId, fdl, tdl);
	}

	// ARB Data
	public List<ARBPurchaseInvoiceDO> fetchAgencyGSTARBPurchaseReport(Map<String, String[]> requestParams,
			long agencyId) throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReportsPersistenceManager().getAgencyGSTARBPurchaseInvoicesByDateRange(agencyId, fdl, tdl);
	}

	// ARB Data
	public List<OtherPurchaseInvoiceDO> fetchAgencyGSTOtherPurchaseReport(Map<String, String[]> requestParams,
			long agencyId) throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReportsPersistenceManager().getAgencyGSTOtherPurchaseInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<ARBSalesInvoiceDO> fetchAgencyGSTARBSaleReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReportsPersistenceManager().getAgencyGSTARBSInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<NCDBCInvoiceDO> fetchAgencyGSTNCDBCSaleReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReportsPersistenceManager().getAgencyGSTNCDBCInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<RCDataDO> fetchAgencyGSTRCSaleReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReportsPersistenceManager().getAgencyGSTRCInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<TVDataDO> fetchAgencyGSTTVSaleReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReportsPersistenceManager().getAgencyGSTTVInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<PurchaseReturnDO> fetchAgencyGSTPurchaseReturnsReport(Map<String, String[]> requestParams,
			long agencyId) throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReportsPersistenceManager().getAgencyGSTPurchaseReturnsInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<SalesReturnDO> fetchAgencyGSTSalesReturnsReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReportsPersistenceManager().getAgencyGSTSalesReturnsInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<CreditNotesDO> fetchAgencyGSTCreditNotesReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReportsPersistenceManager().getAgencyCreditNotesByDateRange(agencyId, fdl, tdl);
	}

	public List<DebitNotesDO> fetchAgencyGSTDebitNotesReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReportsPersistenceManager().getAgencyDebitNotesByDateRange(agencyId, fdl, tdl);
	}

	public List<GSTPaymentsDataDO> fetchAgencyGSTPaymentsData(String m, String y, long agencyId)
			throws BusinessException, ParseException {
		return getGSTReportsPersistenceManager().getAgencyGSTPaymentsData(agencyId, m, y);
	}

	public List<GSTPaymentsDataDO> saveAgencyPaymentsData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] sMonth = requestParams.get("sm");
		String[] sYear = requestParams.get("sy");
		String[] sigst = requestParams.get("sigst");
		String[] scgst = requestParams.get("scgst");
		String[] ssgst = requestParams.get("ssgst");
		String[] pigst = requestParams.get("pigst");
		String[] pcgst = requestParams.get("pcgst");
		String[] psgst = requestParams.get("psgst");
		GSTReportsPersistenceManager gstrpm = new GSTReportsPersistenceManager();
		gstrpm.saveAgencyPaymentsData(agencyId, sMonth[0], sYear[0], sigst[0], scgst[0], ssgst[0], pigst[0], pcgst[0],
				psgst[0]);
		return getGSTReportsPersistenceManager().getAgencyGSTPaymentsData(agencyId, sMonth[0], sYear[0]);
	}

	public List<GSTPaymentsDataDO> setOffAgencyPaymentsData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] sMonth = requestParams.get("sm");
		String[] sYear = requestParams.get("sy");
		String[] sigst = requestParams.get("sigst");
		String[] scgst = requestParams.get("scgst");
		String[] ssgst = requestParams.get("ssgst");
		String[] pigst = requestParams.get("pigst");
		String[] pcgst = requestParams.get("pcgst");
		String[] psgst = requestParams.get("psgst");
		String[] ccgst = requestParams.get("ccgst");
		String[] csgst = requestParams.get("csgst");
		GSTReportsPersistenceManager gstrpm = new GSTReportsPersistenceManager();

		gstrpm.setOffAgencyPaymentsData(agencyId, sMonth[0], sYear[0], sigst[0], scgst[0], ssgst[0], pigst[0], pcgst[0],
				psgst[0], ccgst[0], csgst[0]);
		return getGSTReportsPersistenceManager().getAgencyGSTPaymentsData(agencyId, sMonth[0], sYear[0]);
	}

	public List<GSTPaymentsDataDO> payOffGSTPaymentsData(Map<String, String[]> requestParams, long agencyId) {
		long curdl = 0;
		String[] sMonth = requestParams.get("sm");
		String[] sYear = requestParams.get("sy");
		String[] tgstAmount = requestParams.get("gstAmt");

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		String curdate1 = (cal.getTime()).toString();

		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

		try {
			curdl = sdf.parse(curdate1).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		GSTReportsPersistenceManager gstrpm = new GSTReportsPersistenceManager();
		gstrpm.payoffAgencyPaymentsData(agencyId, sMonth[0], sYear[0], tgstAmount[0], curdl);
		return getGSTReportsPersistenceManager().getAgencyGSTPaymentsData(agencyId, sMonth[0], sYear[0]);
	}

	public List<GSTPaymentDetailsDO> getGSTPaymentDetailsData(long agencyId) {
		return getGSTReportsPersistenceManager().getAgencyPaymentDetailsData(agencyId);
	}
}