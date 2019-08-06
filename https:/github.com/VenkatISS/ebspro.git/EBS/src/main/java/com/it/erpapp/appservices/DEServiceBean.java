package com.it.erpapp.appservices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.it.erpapp.commons.ApplicationConstants;
import com.it.erpapp.framework.AgencyFactory;
import com.it.erpapp.framework.CacheFactory;
import com.it.erpapp.framework.DEReportsDataFactory;
import com.it.erpapp.framework.MasterDataFactory;
import com.it.erpapp.framework.PPMasterDataFactory;
import com.it.erpapp.framework.model.pps.ARBDataDO;
import com.it.erpapp.framework.model.pps.EquipmentDataDO;
import com.it.erpapp.framework.model.transactions.others.BankTranxsDataDO;
import com.it.erpapp.framework.model.transactions.sales.DEProductsDetailsViewDO;
import com.it.erpapp.framework.model.transactions.sales.DEProductsViewDO;
import com.it.erpapp.framework.model.vos.AgencyVO;
import com.it.erpapp.framework.model.vos.DEReportVO;
import com.it.erpapp.response.MessageObject;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class DEServiceBean {

	Logger logger = LoggerFactory.getLogger(DEServiceBean.class);
	long agencyId;
	String[] fromdate;

	public void showDayEndPage(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(8001, "SHOW DAY END REPORT PAGE",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {

			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.SHOWDAYENDPAGE.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("cylinder_types_list", new Gson().toJson(new CacheFactory().getCylinderTypesData()));
			request.setAttribute("arb_types_list", new Gson().toJson(new CacheFactory().getARBTypesData()));
			request.setAttribute("equipment_data", new Gson().toJson(new ArrayList<EquipmentDataDO>()));
			request.setAttribute("arb_data", new Gson().toJson(new ArrayList<ARBDataDO>()));
			request.setAttribute("prod_de_data", new Gson().toJson(new ArrayList<DEProductsViewDO>()));
			request.setAttribute("bank_data", new Gson().toJson(new ArrayList<BankTranxsDataDO>()));
			request.setAttribute("bbr_data", new Gson().toJson(new ArrayList<BankTranxsDataDO>()));
			request.setAttribute("sded", "0");
			request.setAttribute("rder", "0");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWDAYENDPAGE.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SHOWDAYENDPAGE.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void fetchDayEndPage(HttpServletRequest request,HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(8002, "FETCH DAY END REPORT PAGE",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			Map<String, String[]> requestParams = request.getParameterMap();
			fromdate = requestParams.get("fd");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long fdl = sdf.parse(fromdate[0]).getTime();
			request.setAttribute("sded", String.valueOf(fdl));
			request.setAttribute("rder", "1");

			logger.info(ApplicationConstants.LogMessageKeys.FETCHDAYENDPAGE.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.FROMDATE.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, fromdate[0]);

			DEReportsDataFactory drdf = new DEReportsDataFactory();
			MasterDataFactory mdf = new MasterDataFactory();
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();

			// Fetch Cylinders Information
			request.setAttribute("cylinder_types_list", new Gson().toJson(new CacheFactory().getCylinderTypesData()));
			request.setAttribute("equipment_data", new Gson().toJson(ppmdf.getAgencyEquipmentData(agencyId)));

			// Fetch ARB Data
			request.setAttribute("arb_types_list", new Gson().toJson(new CacheFactory().getARBTypesData()));
			request.setAttribute("arb_data", new Gson().toJson(ppmdf.getAgencyARBData(agencyId)));

			List<DEProductsViewDO> dataList = drdf.fetchAgencyProductsTransactionsReport(fdl, agencyId);

			// Process dataList
			// Prepare Empty Product Codes Map
			Collections.sort(dataList, Comparator.comparing(DEProductsViewDO::getCreated_date));
			Map<String, DEReportVO> derMap = new HashMap<>();
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

			// Fill Product Codes Map
			for (Map.Entry<String, DEReportVO> entry : derMap.entrySet()) {
				int count = 0;
				DEReportVO dervo = new DEReportVO();
				long pccode = Long.parseLong(entry.getKey());
				for (int i = 0; i < dataList.size(); i++) {
					DEProductsViewDO dataDO = dataList.get(i);
					List<DEProductsDetailsViewDO> dataDetailsDO = dataDO.getDetailsList();
					for (DEProductsDetailsViewDO dearbSalesInvoiceViewDO : dataDetailsDO) {
						if (pccode == dearbSalesInvoiceViewDO.getProd_code()) {
							if (count == 0) {
								if (dearbSalesInvoiceViewDO.getTx_type() == 1
										|| dearbSalesInvoiceViewDO.getTx_type() == 2
										|| dearbSalesInvoiceViewDO.getTx_type() == 9) {
									dervo.setOpeningStockF(dearbSalesInvoiceViewDO.getC_stock()
											- dearbSalesInvoiceViewDO.getQuantity());
								} else if (dearbSalesInvoiceViewDO.getTx_type() == 8) {
									dervo.setOpeningStockF(dearbSalesInvoiceViewDO.getC_stock());
								} else {
									dervo.setOpeningStockF(dearbSalesInvoiceViewDO.getC_stock()
											+ dearbSalesInvoiceViewDO.getQuantity());
								}
							}
							++count;
							if (dearbSalesInvoiceViewDO.getTx_type() == 1
									|| dearbSalesInvoiceViewDO.getTx_type() == 2) {
								dervo.setTotalPurchases(
										dervo.getTotalPurchases() + dearbSalesInvoiceViewDO.getQuantity());
							} else if (dearbSalesInvoiceViewDO.getTx_type() == 7) {
								dervo.setTotalPurchaseReturns(
										dervo.getTotalPurchaseReturns() + dearbSalesInvoiceViewDO.getQuantity());
							} else if (dearbSalesInvoiceViewDO.getTx_type() == 8) {
								// DON'T DO ANYTHING
							} else if (dearbSalesInvoiceViewDO.getTx_type() == 9) {
								dervo.setTotalSalesReturns(
										dervo.getTotalSalesReturns() + dearbSalesInvoiceViewDO.getQuantity());
							} else {
								dervo.setTotalSales(dervo.getTotalSales() + dearbSalesInvoiceViewDO.getQuantity());
							}
							dervo.setTotalNCSales(dervo.getTotalNCSales() + dearbSalesInvoiceViewDO.getNcquantity());
							if (dearbSalesInvoiceViewDO.getTx_type() != 8) {
								dervo.setClosingStockF(dearbSalesInvoiceViewDO.getC_stock());
								dervo.setClosingStockE(dearbSalesInvoiceViewDO.getE_stock());
							}
						}
					}
				}
				derMap.put(entry.getKey(), dervo);
			}
			request.setAttribute("prod_de_data", new Gson().toJson(derMap));

			// Fetch Bank Data
			request.setAttribute("bank_data", new Gson().toJson(mdf.getAgencyBankData(agencyId)));
			List<BankTranxsDataDO> consolidatedList = new ArrayList<>();
			List<BankTranxsDataDO> btDOList = drdf.fetchAgencyBankTransactionsReport(requestParams, agencyId);

			btDOList.forEach(paydo -> {
				consolidatedList.add(paydo);
				if (paydo.getDeleted() == 2) {
					BankTranxsDataDO dataDO = setBankTransactionsData(paydo);
					consolidatedList.add(dataDO);
				}
			});

			request.setAttribute("bbr_data", new Gson().toJson(consolidatedList));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHDAYENDPAGE.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.FROMDATE.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, fromdate[0], ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);

			logger.info(ApplicationConstants.LogMessageKeys.FETCHDAYENDPAGE.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.FROMDATE.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, fromdate[0], be);

		} catch (ParseException e) {
			e.printStackTrace();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHDAYENDPAGE.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.FROMDATE.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.PARSEEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, fromdate[0], e);

		}
	}

	public BankTranxsDataDO setBankTransactionsData(BankTranxsDataDO paydo) {
		BankTranxsDataDO dataDO = new BankTranxsDataDO();
		dataDO.setBt_date(paydo.getModified_date());
		dataDO.setBank_id(paydo.getBank_id());
		dataDO.setTrans_amount(paydo.getTrans_amount());
		dataDO.setTrans_charges(paydo.getTrans_charges());
		if (paydo.getTrans_type() == 1) {
			dataDO.setTrans_type(7);
		} else if (paydo.getTrans_type() == 2) {
			dataDO.setTrans_type(6);
		} else if (paydo.getTrans_type() == 3) {
			dataDO.setTrans_type(13);
		} else if (paydo.getTrans_type() == 4) {
			dataDO.setTrans_type(14);
		} else if (paydo.getTrans_type() == 5) {
			dataDO.setTrans_type(8);
		} else if (paydo.getTrans_type() == 9) {
			dataDO.setTrans_type(11);
		} else if (paydo.getTrans_type() == 10) {
			dataDO.setTrans_type(12);
		}

		dataDO.setTrans_mode(paydo.getTrans_mode());
		dataDO.setInstr_details(paydo.getInstr_details());
		dataDO.setAcc_head(0);
		dataDO.setNarration("");
		dataDO.setCreated_by(paydo.getCreated_by());
		dataDO.setCreated_date(paydo.getModified_date());
		dataDO.setVersion(1);
		dataDO.setDeleted(0);
		dataDO.setBank_acb(paydo.getDbank_acb());

		return dataDO;
	}

	public void submitDayEndReport(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(8003, "SUBMIT DAY END REPORT",
				ApplicationConstants.ERROR_STATUS_STRING);
		Map<String, String[]> requestParams = request.getParameterMap();
		String[] sdate = requestParams.get("deds");
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			long dsl = Long.parseLong(sdate[0]);

			logger.info(ApplicationConstants.LogMessageKeys.SUBMITDAYENDREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.SUBMITDATE.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, sdate[0]);

			DEReportsDataFactory drdf = new DEReportsDataFactory();
			drdf.submitDayendReport(dsl, agencyId);
			// Update Session Object
			HttpSession session = request.getSession(true);
			AgencyFactory af = new AgencyFactory();
			session.setAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue(), af.fetchAgencyVO(agencyId));
			showDayEndPage(request, response);
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SUBMITDAYENDREPORT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.SUBMITDATE.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, sdate[0], ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SUBMITDAYENDREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.SUBMITDATE.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, sdate[0], be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}
}
