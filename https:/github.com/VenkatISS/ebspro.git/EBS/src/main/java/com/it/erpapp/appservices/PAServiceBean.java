package com.it.erpapp.appservices;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.it.erpapp.commons.ApplicationConstants;
import com.it.erpapp.framework.CacheFactory;
import com.it.erpapp.framework.PAReportsDataFactory;
import com.it.erpapp.framework.PPMasterDataFactory;
import com.it.erpapp.framework.model.AgencyStockDataDO;
import com.it.erpapp.framework.model.transactions.sales.DEProductsDetailsViewDO;
import com.it.erpapp.framework.model.transactions.sales.DEProductsViewDO;
import com.it.erpapp.framework.model.vos.AgencyVO;
import com.it.erpapp.framework.model.vos.DEReportVO;
import com.it.erpapp.response.MessageObject;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class PAServiceBean {

	Logger logger = LoggerFactory.getLogger(PAServiceBean.class);
	long agencyId;

	public void showPAPage(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(7061, "SHOW PA REPORT DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {

			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.SHOWPAPAGE.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("rrt", "0");
			request.setAttribute("req_type", "show");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWPAPAGE.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SHOWPAPAGE.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	String year;
	String month;

	public void fetchPAPage(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(7062, "FETCH PA REPORT", ApplicationConstants.ERROR_STATUS_STRING);
		try {

			year = request.getParameter("sy");
			month = request.getParameter("sm");
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHPAPAGE.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.getValue() + ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.MONTH.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, year, month);

			Map<String, String[]> requestParams = request.getParameterMap();
			request.setAttribute("rrt", "1");
			PAReportsDataFactory pardf = new PAReportsDataFactory();
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();

			// Fetch Cylinders Information
			request.setAttribute("cylinder_types_list", new Gson().toJson((new CacheFactory()).getCylinderTypesData()));
			request.setAttribute("equipment_data", new Gson().toJson(ppmdf.getAgencyEquipmentData(agencyId)));

			// Fetch ARB Data
			request.setAttribute("arb_types_list", new Gson().toJson((new CacheFactory()).getARBTypesData()));
			request.setAttribute("arb_data", new Gson().toJson(ppmdf.getAgencyAllARBData(agencyId)));

			Map<Long, AgencyStockDataDO> openingBalsMap = pardf.fetchOpeningStockBalForAllProducts(requestParams,
					agencyId);
			Map<Long, AgencyStockDataDO> closingBalsMap = pardf.fetchClosingStockBalOfAllProducts(requestParams,
					agencyId);
			List<AgencyStockDataDO> tcList = pardf.fetchTransactionsCountOfAllProducts(requestParams, agencyId);
			List<DEProductsViewDO> dataList = pardf.fetchAgencyProductsTransactionsReport(requestParams, agencyId);

			// Process dataList
			// Prepare Empty Product Codes Map
			Collections.sort(dataList, Comparator.comparing(DEProductsViewDO::getCreated_date));
			Map<String, DEReportVO> derMap = new HashMap<String, DEReportVO>();
			for (int i = 0; i < dataList.size(); i++) {
				DEProductsViewDO dataDO = dataList.get(i);
				List<DEProductsDetailsViewDO> dataDetailsDO = dataDO.getDetailsList();
				for (DEProductsDetailsViewDO dearbSalesInvoiceViewDO : dataDetailsDO) {
					String pcKey = String.valueOf(dearbSalesInvoiceViewDO.getProd_code());
					if (!derMap.containsKey(pcKey)) {
						derMap.put(pcKey, new DEReportVO());
					}
				}
			}

			// Fill Total Transactions
			for (Map.Entry<String, DEReportVO> entry : derMap.entrySet()) {
				DEReportVO dervo = new DEReportVO();
				long pccode = Long.parseLong(entry.getKey());

				for (AgencyStockDataDO asdo : tcList) {
					long prod_code = asdo.getProd_code() < 13 ? asdo.getProd_code() : asdo.getProd_id();

					if (pccode == prod_code) {
						if (asdo.getTrans_type() == 1) {
							dervo.setTotalPurchases(dervo.getTotalPurchases() + asdo.getFulls());
						} else if (asdo.getTrans_type() == 2) {
							dervo.setTotalSales(dervo.getTotalSales() + asdo.getFulls());
						} else if (asdo.getTrans_type() == 3) {
							dervo.setTotalPurchaseReturns(dervo.getTotalPurchaseReturns() + asdo.getFulls());
						} else if (asdo.getTrans_type() == 4) {
							dervo.setTotalSalesReturns(dervo.getTotalSalesReturns() + asdo.getFulls());
						} else if (asdo.getTrans_type() == 5) {
							dervo.setTotalNCSales(dervo.getTotalNCSales() + asdo.getFulls());
						}
					}
				}

				for (int i = 0; i < dataList.size(); i++) {
					DEProductsViewDO dataDO = dataList.get(i);
					List<DEProductsDetailsViewDO> dataDetailsDO = dataDO.getDetailsList();
					for (DEProductsDetailsViewDO dearbSalesInvoiceViewDO : dataDetailsDO) {
						if (pccode == dearbSalesInvoiceViewDO.getProd_code()) {
							// Set Opening Balances
							if (openingBalsMap.get(pccode) != null) {
								AgencyStockDataDO asdo = openingBalsMap.get(pccode);
								dervo.setOpeningStockF(asdo.getCs_fulls());
								dervo.setOpeningStockE(asdo.getCs_emptys());
							}

							// Set Closing Balances
							if (closingBalsMap.get(pccode) != null) {
								AgencyStockDataDO asdo = closingBalsMap.get(pccode);
								dervo.setClosingStockF(asdo.getCs_fulls());
								dervo.setClosingStockE(asdo.getCs_emptys());
							}

							if (dearbSalesInvoiceViewDO.getTx_type() == 1
									|| dearbSalesInvoiceViewDO.getTx_type() == 2) {
								BigDecimal ta = new BigDecimal(dearbSalesInvoiceViewDO.getTx_amount());
								BigDecimal ea = new BigDecimal(dervo.getTotalPurchasesAmount());
								dervo.setTotalPurchasesAmount((ea.add(ta)).toString());
							} else if (dearbSalesInvoiceViewDO.getTx_type() == 6) {
								BigDecimal ta = new BigDecimal(dearbSalesInvoiceViewDO.getTx_amount());
								BigDecimal ea = new BigDecimal(dervo.getTotalPurchaseReturnsAmount());
								dervo.setTotalPurchaseReturnsAmount((ea.add(ta)).toString());
							} else if (dearbSalesInvoiceViewDO.getTx_type() == 7) {
								BigDecimal ta = new BigDecimal(dearbSalesInvoiceViewDO.getTx_amount());
								BigDecimal ea = new BigDecimal(dervo.getTotalSalesReturnsAmount());
								dervo.setTotalSalesReturnsAmount((ea.add(ta)).toString());
							} else {
								BigDecimal ta = new BigDecimal(dearbSalesInvoiceViewDO.getTx_amount());
								BigDecimal ea = new BigDecimal(dervo.getTotalSalesAmount());
								dervo.setTotalSalesAmount(ea.add(ta).toString());
							}
							dervo.setTotalNCSales(dervo.getTotalNCSales() + dearbSalesInvoiceViewDO.getNcquantity());

						}
					}
				}
				derMap.put(entry.getKey(), dervo);
			}

			request.setAttribute("pa_data", new Gson().toJson(derMap));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(ApplicationConstants.LogMessageKeys.FETCHPAPAGE.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.getValue() + ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.MONTH.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.STATUS.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, year, month,
					ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);

			logger.info(ApplicationConstants.LogMessageKeys.FETCHPAPAGE.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.getValue() + ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.MONTH.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, year, month, be);

		} catch (ParseException e) {
			e.printStackTrace();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHPAPAGE.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.getValue() + ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.MONTH.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.PARSEEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, year, month, e);

		}
	}

}