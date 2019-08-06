package com.it.erpapp.framework;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.it.erpapp.framework.managers.GSTReports1PersistenceManager;
import com.it.erpapp.framework.model.transactions.GSTR1CDNDataDO;
import com.it.erpapp.framework.model.transactions.GSTR1DocsDataDO;
import com.it.erpapp.framework.model.transactions.sales.ARBSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.COMSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.DOMSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.NCDBCInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.PurchaseReturnDO;
import com.it.erpapp.framework.model.transactions.sales.RCDataDO;
import com.it.erpapp.framework.model.transactions.sales.TVDataDO;
import com.it.erpapp.framework.model.vos.GSTR1ProductDataVO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class GSTReports1DataFactory {

	private GSTReports1PersistenceManager getGSTReports1PersistenceManager() {
		return new GSTReports1PersistenceManager();
	}

	public List<DOMSalesInvoiceDO> fetchAgencyGSTR1B2BDOMCSalesData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1B2BDSInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<COMSalesInvoiceDO> fetchAgencyGSTR1B2BCOMCSalesData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1B2BCSInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<ARBSalesInvoiceDO> fetchAgencyGSTR1B2BARBSalesData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1B2BARBSInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<PurchaseReturnDO> fetchAgencyGSTR1B2BPurReturnsData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1B2BPurReturnSInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<PurchaseReturnDO> fetchAgencyGSTR1B2CLPurReturnsData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1B2CLPurReturnSInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<PurchaseReturnDO> fetchAgencyGSTR1B2CSPurReturnsData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1B2CSPurReturnSInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<DOMSalesInvoiceDO> fetchAgencyGSTR1B2CLDOMCSalesData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1B2CLDSInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<COMSalesInvoiceDO> fetchAgencyGSTR1B2CLCOMCSalesData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1B2CLCSInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<ARBSalesInvoiceDO> fetchAgencyGSTR1B2CLARBSalesData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1B2CLARBSInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<DOMSalesInvoiceDO> fetchAgencyGSTR1B2CSDOMCSalesData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1B2CSDSInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<COMSalesInvoiceDO> fetchAgencyGSTR1B2CSCOMCSalesData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1B2CSCSInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<ARBSalesInvoiceDO> fetchAgencyGSTR1B2CSARBSalesData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1B2CSARBSInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<NCDBCInvoiceDO> fetchAgencyGSTR1B2BNCDBCSalesData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1B2BNCDBCInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<RCDataDO> fetchAgencyGSTR1B2BRCSalesData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1B2BRCInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<TVDataDO> fetchAgencyGSTR1B2BTVSalesData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1B2BTVInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<NCDBCInvoiceDO> fetchAgencyGSTR1B2CLNCDBCSalesData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1B2CLNCDBCInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<RCDataDO> fetchAgencyGSTR1B2CLRCSalesData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1B2CLRCInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<TVDataDO> fetchAgencyGSTR1B2CLTVSalesData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1B2CLTVInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<NCDBCInvoiceDO> fetchAgencyGSTR1B2CSNCDBCSalesData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1B2CSNCDBCInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<RCDataDO> fetchAgencyGSTR1B2CSRCSalesData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1B2CSRCInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<TVDataDO> fetchAgencyGSTR1B2CSTVSalesData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1B2CSTVInvoicesByDateRange(agencyId, fdl, tdl);
	}

	public List<GSTR1ProductDataVO> fetchAgencyGSTR1HSNDOMCSalesData(Map<String, String[]> requestParams, long agencyId,
			long pc) throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
//		return getGSTReports1PersistenceManager().getAgencyGSTR1DSInvoicesByDateRangeAndByProductType(agencyId, fdl,
//				tdl, pc, ofp_acceptable);
		return getGSTReports1PersistenceManager().getAgencyGSTR1DSInvoicesByDateRangeAndByProductType(agencyId, fdl,
				tdl, pc);
	}

	public List<GSTR1ProductDataVO> fetchAgencyGSTR1HSNCOMCSalesData(Map<String, String[]> requestParams, long agencyId,
			long pc, int ofp_acceptable) throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1CSInvoicesByDateRangeAndByProductType(agencyId, fdl,
				tdl, pc, ofp_acceptable);
	}

	public List<GSTR1ProductDataVO> fetchAgencyGSTR1HSNRegulatorCSalesData(Map<String, String[]> requestParams,
			long agencyId, int pc) throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1RegInvoicesByDateRangeAndByProductType(agencyId, fdl,
				tdl, pc);
	}

	public List<GSTR1ProductDataVO> fetchAgencyGSTR1HSNARBSalesData(Map<String, String[]> requestParams, long agencyId,
			int pc, int ofp_acceptable) throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1ASInvoicesByDateRangeAndByProductType(agencyId, fdl,
				tdl, pc, ofp_acceptable);
	}

	public List<GSTR1ProductDataVO> fetchAgencyGSTR1HSNSevicesSalesData(Map<String, String[]> requestParams,
			long agencyId, int pc) throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1ServicesInvoicesByDateRangeAndByProductType(agencyId,
				fdl, tdl, pc);
	}

	public GSTR1DocsDataDO fetchAgencyGSTR1B2BSalesDocsData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1B2BSalesDocsData(agencyId, fdl, tdl);
	}

	public GSTR1DocsDataDO fetchAgencyGSTR1B2CSalesDocsData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1B2CSalesDocsData(agencyId, fdl, tdl);
	}

	public GSTR1DocsDataDO fetchAgencyGSTR1PRDocsData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1PRDocsData(agencyId, fdl, tdl);
	}

	public GSTR1DocsDataDO fetchAgencyGSTR1CNDocsData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1CNDocsData(agencyId, fdl, tdl);
	}

	public GSTR1DocsDataDO fetchAgencyGSTR1DNDocsData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1DNDocsData(agencyId, fdl, tdl);
	}

	public List<GSTR1CDNDataDO> fetchAgencyGSTR1CNRData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1CNRData(agencyId, fdl, tdl);
	}

	public List<GSTR1CDNDataDO> fetchAgencyGSTR1DNRData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1DNRData(agencyId, fdl, tdl);
	}

	public List<GSTR1CDNDataDO> fetchAgencyGSTR1CNURData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1CNURData(agencyId, fdl, tdl);
	}

	public List<GSTR1CDNDataDO> fetchAgencyGSTR1DNURData(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getGSTReports1PersistenceManager().getAgencyGSTR1DNURData(agencyId, fdl, tdl);
	}
}
