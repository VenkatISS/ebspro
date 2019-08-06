package com.it.erpapp.framework;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.it.erpapp.framework.managers.StockReportPersistenceManager;
import com.it.erpapp.framework.model.vos.ProductStockVO;

public class StockReportDataFactory {

	private StockReportPersistenceManager getStockReportPersistenceManager(){
		return new StockReportPersistenceManager();
	}

	public List<ProductStockVO> fetchAgencyStockReportByProductId(Map<String, String[]> requestParams, long agencyId) throws ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");
		String[] pcode = requestParams.get("pid");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getStockReportPersistenceManager().getAgencyProductStockByDateRangeAndProductType(
				agencyId, fdl, tdl, Integer.parseInt(pcode[0]));
	}
	
	public int fetchAgencyOpeningStockByProductId(Map<String, String[]> requestParams, long agencyId) throws ParseException {
		String[] fromdate = requestParams.get("fd");
		String[] todate = requestParams.get("td");
		String[] pcode = requestParams.get("pid");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long fdl = (sdf.parse(fromdate[0])).getTime();
		long tdl = (sdf.parse(todate[0])).getTime();
		return getStockReportPersistenceManager().getAgencyOpeningStockByDateRangeAndProductType(
				agencyId, fdl, tdl, Integer.parseInt(pcode[0]));
	}

}
