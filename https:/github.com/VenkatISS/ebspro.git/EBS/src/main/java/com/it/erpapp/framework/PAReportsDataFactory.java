package com.it.erpapp.framework;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.it.erpapp.framework.managers.PAReportsPersistenceManager;
import com.it.erpapp.framework.model.AgencyStockDataDO;
import com.it.erpapp.framework.model.transactions.sales.DEProductsViewDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class PAReportsDataFactory {

	private PAReportsPersistenceManager getPAReportsPersistenceManager(){
		return new PAReportsPersistenceManager();
	}

	//All Products Transactions Data
	public List<DEProductsViewDO> fetchAgencyProductsTransactionsReport(Map<String, String[]> requestParams, long agencyId) throws BusinessException, ParseException{
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getPAReportsPersistenceManager().getAgencyProductsTransactionsByDateRange(agencyId, fdl, tdl);
	}
	
	public Map<Long,AgencyStockDataDO> fetchOpeningStockBalForAllProducts(Map<String, String[]> requestParams, long agencyId) throws BusinessException, ParseException{
		String[] fromdate = requestParams.get("fd");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		return getPAReportsPersistenceManager().getAgencyProductsOpeningBalancesByDateRange(agencyId, fdl);
	}
	
	public Map<Long,AgencyStockDataDO> fetchClosingStockBalOfAllProducts(Map<String, String[]> requestParams, long agencyId) throws BusinessException, ParseException{
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long tdl = (sdf.parse(todate[0])).getTime();
		return getPAReportsPersistenceManager().getAgencyProductsClosingBalancesByDateRange(agencyId, tdl);
	}
	
	public List<AgencyStockDataDO> fetchTransactionsCountOfAllProducts(Map<String, String[]> requestParams, long agencyId) throws BusinessException, ParseException{
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getPAReportsPersistenceManager().getAgencyProductsTotalTransactionsByDateRange(agencyId, fdl, tdl);
	}
	
}
