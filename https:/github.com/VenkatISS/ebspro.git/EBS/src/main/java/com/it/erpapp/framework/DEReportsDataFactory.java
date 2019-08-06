package com.it.erpapp.framework;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.it.erpapp.framework.managers.DEReportsPersistenceManager;
import com.it.erpapp.framework.model.transactions.others.BankTranxsDataDO;
import com.it.erpapp.framework.model.transactions.sales.DEProductsViewDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class DEReportsDataFactory {

	private DEReportsPersistenceManager getDEReportsPersistenceManager() {
		return new DEReportsPersistenceManager();
	}

	// All Products Transactions Data
	public List<DEProductsViewDO> fetchAgencyProductsTransactionsReport(long fdl, long agencyId)
			throws BusinessException, ParseException {
		return getDEReportsPersistenceManager().getAgencyProductsTransactionsByDateRange(agencyId, fdl, fdl);
	}

	// Bank Transactions Reports Data
	public List<BankTranxsDataDO> fetchAgencyBankTransactionsReport(Map<String, String[]> requestParams, long agencyId)
			throws BusinessException, ParseException {
		String[] fromdate = requestParams.get("fd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		return getDEReportsPersistenceManager().getAgencyBankTransactionsByDateRange(agencyId, fdl, fdl);
	}

	// Submit Day End Report Data
	public void submitDayendReport(long dsl, long agencyId) {
		getDEReportsPersistenceManager().submitDayEndConfirmation(dsl, agencyId);
	}
}
