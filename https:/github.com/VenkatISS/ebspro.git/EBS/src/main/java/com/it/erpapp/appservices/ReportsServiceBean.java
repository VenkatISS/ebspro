package com.it.erpapp.appservices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.it.erpapp.commons.ApplicationConstants;
import com.it.erpapp.framework.CacheFactory;
import com.it.erpapp.framework.MasterDataFactory;
import com.it.erpapp.framework.PPMasterDataFactory;
import com.it.erpapp.framework.ReportsDataFactory;
import com.it.erpapp.framework.StockReportDataFactory;
import com.it.erpapp.framework.model.AgencyCVOBalanceDataDO;
import com.it.erpapp.framework.model.AgencyStockDataDO;
import com.it.erpapp.framework.model.GSTPaymentDetailsDO;
import com.it.erpapp.framework.model.transactions.ARBPurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.EquipmentDEDO;
import com.it.erpapp.framework.model.transactions.GSTPaymentsLedgerDO;
import com.it.erpapp.framework.model.transactions.PartyLedgerDO;
import com.it.erpapp.framework.model.transactions.PayablesDO;
import com.it.erpapp.framework.model.transactions.PurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.RPPageDO;
import com.it.erpapp.framework.model.transactions.ReceivablesDO;
import com.it.erpapp.framework.model.transactions.others.BankTranxsDataDO;
import com.it.erpapp.framework.model.transactions.sales.COMSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.COMSalesInvoiceDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.DOMSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.DOMSalesInvoiceDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.NCDBCInvoiceDO;
import com.it.erpapp.framework.model.vos.AgencyVO;
import com.it.erpapp.framework.model.vos.NCDBCRCTVReportVO;
import com.it.erpapp.framework.model.vos.ProductStockVO;
import com.it.erpapp.framework.model.vos.StockReportDataVO;
import com.it.erpapp.framework.model.vos.StockReportVO;
import com.it.erpapp.response.MessageObject;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class ReportsServiceBean {

	Logger logger = LoggerFactory.getLogger(ReportsServiceBean.class);
	long agencyId;

	public void showPurchaseReport(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(7001, "SHOW PURCHASE REPORTS DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.SHOWPURCHASEREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			request.setAttribute("arb_types_list", (new Gson().toJson((new CacheFactory()).getARBTypesData())));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(agencyId))));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(ppmdf.getAgencyAllEquipmentData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("arb_data", (new Gson().toJson(ppmdf.getAgencyAllARBData(agencyId))));
			request.setAttribute("cpreport_data", (new Gson().toJson(new ArrayList<PurchaseInvoiceDO>())));
			request.setAttribute("arbpreport_data", (new Gson().toJson(new ArrayList<ARBPurchaseInvoiceDO>())));
			request.setAttribute("rrt", "0");
			request.setAttribute("req_type", "show");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWPURCHASEREPORT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			logger.info(ApplicationConstants.LogMessageKeys.SHOWPURCHASEREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void fetchPurchaseReport(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(7001, "FETCH PURCHASE REPORTS DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			request.setAttribute("arb_types_list", (new Gson().toJson((new CacheFactory()).getARBTypesData())));
			request.setAttribute("cpreport_data", (new Gson().toJson(new ArrayList<PurchaseInvoiceDO>())));
			request.setAttribute("arbpreport_data", (new Gson().toJson(new ArrayList<ARBPurchaseInvoiceDO>())));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(agencyId))));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();

			request.setAttribute("equipment_data", (new Gson().toJson(ppmdf.getAgencyAllEquipmentData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("arb_data", (new Gson().toJson(ppmdf.getAgencyAllARBData(agencyId))));

			Map<String, String[]> requestParams = request.getParameterMap();

			String[] fromdate = requestParams.get("fd");
			String[] todate = requestParams.get("td");
			String[] pcodep = requestParams.get("pid");
			String[] vcodep = requestParams.get("pv");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long fdl = (sdf.parse(fromdate[0])).getTime();
			long tdl = (sdf.parse(todate[0])).getTime();

			Date date1 = new Date(fdl);
			SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yy");
			String fdate = df1.format(date1);

			Date date2 = new Date(tdl);
			SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
			String tdate = df2.format(date2);

			request.setAttribute("fdate", String.valueOf(fdate));
			request.setAttribute("tdate", String.valueOf(tdate));
			request.setAttribute("pcodep", String.valueOf(pcodep[0]));
			request.setAttribute("vcodep", String.valueOf(vcodep[0]));

			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHPURCHASEREPORT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.PRODUCTID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.VENDORID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.FROMDATE.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.TODATE.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, String.valueOf(pcodep[0]), String.valueOf(vcodep[0]), fdate, tdate);

			ReportsDataFactory rdf = new ReportsDataFactory();
			String[] pcode = requestParams.get("pid");
			int pc = Integer.parseInt(pcode[0]);
			if (pc == 0 || pc <= 12) {
				request.setAttribute("cpreport_data",
						(new Gson().toJson(rdf.fetchAgencyPurReport(requestParams, agencyId))));
				request.setAttribute("rrt", "1");
			}
			if (pc == 0 || pc > 12) {
				request.setAttribute("arbpreport_data",
						(new Gson().toJson(rdf.fetchAgencyARBPurReport(requestParams, agencyId))));
				request.setAttribute("rrt", "2");
			}
			if (pc == 0) {
				request.setAttribute("rrt", "3");
			}
			request.setAttribute("req_type", "fetch");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHPURCHASEREPORT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);
		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.FETCHPURCHASEREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		} catch (ParseException e) {
			e.printStackTrace();
			logger.info(ApplicationConstants.LogMessageKeys.FETCHPURCHASEREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, e);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void showSalesReport(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(7011, "SHOW SALES REPORTS DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.SHOWSALESREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			request.setAttribute("arb_types_list", (new Gson().toJson((new CacheFactory()).getARBTypesData())));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("staff_data", (new Gson().toJson(mdf.getAgencyStaffData(agencyId))));
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(agencyId))));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(ppmdf.getAgencyAllEquipmentData(agencyId))));
			request.setAttribute("arb_data", (new Gson().toJson(ppmdf.getAgencyAllARBData(agencyId))));
			request.setAttribute("dcsreport_data", (new Gson().toJson(new ArrayList<PurchaseInvoiceDO>())));
			request.setAttribute("ncdcsreport_data", (new Gson().toJson(new ArrayList<NCDBCInvoiceDO>())));
			request.setAttribute("ccsreport_data", (new Gson().toJson(new ArrayList<PurchaseInvoiceDO>())));
			request.setAttribute("ncccsreport_data", (new Gson().toJson(new ArrayList<NCDBCInvoiceDO>())));
			request.setAttribute("arbsreport_data", (new Gson().toJson(new ArrayList<ARBPurchaseInvoiceDO>())));
			request.setAttribute("ncarbsreport_data", (new Gson().toJson(new ArrayList<ARBPurchaseInvoiceDO>())));
			request.setAttribute("rrt", "0");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWSALESREPORT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SHOWSALESREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void fetchSalesReport(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(7012, "FETCH SALES REPORTS DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			request.setAttribute("arb_types_list", (new Gson().toJson((new CacheFactory()).getARBTypesData())));
			request.setAttribute("dcsreport_data", (new Gson().toJson(new ArrayList<PurchaseInvoiceDO>())));
			request.setAttribute("ncdcsreport_data", (new Gson().toJson(new ArrayList<NCDBCInvoiceDO>())));
			request.setAttribute("ccsreport_data", (new Gson().toJson(new ArrayList<PurchaseInvoiceDO>())));
			request.setAttribute("ncccsreport_data", (new Gson().toJson(new ArrayList<NCDBCInvoiceDO>())));
			request.setAttribute("arbsreport_data", (new Gson().toJson(new ArrayList<ARBPurchaseInvoiceDO>())));
			request.setAttribute("ncarbsreport_data", (new Gson().toJson(new ArrayList<ARBPurchaseInvoiceDO>())));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(agencyId))));
			request.setAttribute("staff_data", (new Gson().toJson(mdf.getAgencyAllStaffData(agencyId))));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(ppmdf.getAgencyAllEquipmentData(agencyId))));
			request.setAttribute("arb_data", (new Gson().toJson(ppmdf.getAgencyAllARBData(agencyId))));
			Map<String, String[]> requestParams = request.getParameterMap();

			String[] fromdate = requestParams.get("fd");
			String[] todate = requestParams.get("td");
			String[] pcodes = requestParams.get("pid");
			String[] scodes = requestParams.get("pv");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long fdl = (sdf.parse(fromdate[0])).getTime();
			long tdl = (sdf.parse(todate[0])).getTime();

			Date date1 = new Date(fdl);
			SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yy");
			String fdate = df1.format(date1);

			Date date2 = new Date(tdl);
			SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
			String tdate = df2.format(date2);

			request.setAttribute("fdate", String.valueOf(fdate));
			request.setAttribute("tdate", String.valueOf(tdate));
			request.setAttribute("pcodes", String.valueOf(pcodes[0]));
			request.setAttribute("scodes", String.valueOf(scodes[0]));

			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHSALESREPORT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.PRODUCTID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.VENDORID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.FROMDATE.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.TODATE.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, String.valueOf(pcodes[0]), String.valueOf(scodes[0]), fdate, tdate);

			ReportsDataFactory rdf = new ReportsDataFactory();
			String[] pcode = requestParams.get("pid");
			int pc = Integer.parseInt(pcode[0]);

			if (pc == 0 || pc <= 3) {
				request.setAttribute("dcsreport_data",
						(new Gson().toJson(rdf.fetchAgencyDOMSaleReport(requestParams, agencyId))));
				request.setAttribute("rrt", "1");

				logger.info(
						ApplicationConstants.LogMessageKeys.FETCHAGENCYDOMCSALEREPORT.getValue()
								+ ApplicationConstants.paramKeys.PARAM.getValue()
								+ ApplicationConstants.paramKeys.AGENCYID.getValue()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
								+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
								+ ApplicationConstants.actionStatusKeys.STATUS.toString()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
						agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

			}

			if (pc == 0 || (pc > 3 && pc <= 9)) {
				request.setAttribute("ccsreport_data",
						(new Gson().toJson(rdf.fetchAgencyCOMSaleReport(requestParams, agencyId))));
				request.setAttribute("rrt", "2");
				logger.info(
						ApplicationConstants.LogMessageKeys.FETCHAGENCYCOMCSALEREPORT.getValue()
								+ ApplicationConstants.paramKeys.PARAM.getValue()
								+ ApplicationConstants.paramKeys.AGENCYID.getValue()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
								+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
								+ ApplicationConstants.actionStatusKeys.STATUS.toString()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
						agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

			}

			if (pc == 0 || pc > 12) {
				request.setAttribute("arbsreport_data",
						(new Gson().toJson(rdf.fetchAgencyARBRSaleReport(requestParams, agencyId))));
				request.setAttribute("ncarbsreport_data",
						(new Gson().toJson(rdf.fetchAgencyNCDBCARBSaleReport(requestParams, agencyId))));
				request.setAttribute("rrt", "3");
				logger.info(
						ApplicationConstants.LogMessageKeys.FETCHAGENCYARBSALEREPORT.getValue()
								+ ApplicationConstants.paramKeys.PARAM.getValue()
								+ ApplicationConstants.paramKeys.AGENCYID.getValue()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
								+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
								+ ApplicationConstants.actionStatusKeys.STATUS.toString()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
						agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

			}
			if (pc == 0) {
				request.setAttribute("rrt", "9");

				logger.info(
						ApplicationConstants.LogMessageKeys.FETCHAGENCYRRT9.getValue()
								+ ApplicationConstants.paramKeys.PARAM.getValue()
								+ ApplicationConstants.paramKeys.AGENCYID.getValue()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
								+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
								+ ApplicationConstants.actionStatusKeys.STATUS.toString()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
						agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

			}
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHSALESREPORT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.FETCHSALESREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		} catch (ParseException e) {
			e.printStackTrace();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHSALESREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, e);
		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void showStockReport(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(7001, "SHOW STOCK REPORTS DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.SHOWSTOCKREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			request.setAttribute("arb_types_list", (new Gson().toJson((new CacheFactory()).getARBTypesData())));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("arb_data", (new Gson().toJson(ppmdf.getAgencyAllARBData(agencyId))));
			request.setAttribute("sreport_data", (new Gson().toJson(new StockReportVO())));
			request.setAttribute("rrt", "0");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWSTOCKREPORT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SHOWSTOCKREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void fetchStockReportByProduct(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(7022, "FETCH STOCK REPORTS DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			request.setAttribute("arb_types_list", (new Gson().toJson((new CacheFactory()).getARBTypesData())));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("arb_data", (new Gson().toJson(ppmdf.getAgencyAllARBData(agencyId))));
			request.setAttribute("sreport_data", (new Gson().toJson(new StockReportVO())));
			request.setAttribute("rrt", "1");
			Map<String, String[]> requestParams = request.getParameterMap();

			String[] fromdate = requestParams.get("fd");
			String[] todate = requestParams.get("td");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long fdl = (sdf.parse(fromdate[0])).getTime();
			long tdl = (sdf.parse(todate[0])).getTime();

			Date date1 = new Date(fdl);
			SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yy");
			String fdate = df1.format(date1);

			Date date2 = new Date(tdl);
			SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
			String tdate = df2.format(date2);

			request.setAttribute("fdate", String.valueOf(fdate));
			request.setAttribute("tdate", String.valueOf(tdate));

			StockReportDataFactory srdf = new StockReportDataFactory();
			String[] pcode = requestParams.get("pid");
			int pc = Integer.parseInt(pcode[0]);

			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHSTOCKREPORTBYPRODUCT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.PRODUCTID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.FROMDATE.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.TODATE.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, String.valueOf(pcode[0]), fdate, tdate);

			int ops = srdf.fetchAgencyOpeningStockByProductId(requestParams, agencyId);
			StockReportDataVO srdvo = new StockReportDataVO();
			srdvo.setProductCode(pc);
			srdvo.setOpeningStockF(ops);

			for (ProductStockVO psvo : srdf.fetchAgencyStockReportByProductId(requestParams, agencyId)) {
				if (psvo.getTtype() == 1) {
					srdvo.setTotalPurchases(srdvo.getTotalPurchases() + psvo.getQuantity());
				} else if (psvo.getTtype() == 2 || psvo.getTtype() == 5) {
					srdvo.setTotalSales(srdvo.getTotalSales() + psvo.getQuantity());
				} else if (psvo.getTtype() == 3) {
					srdvo.setTotalPurchaseReturns(srdvo.getTotalPurchaseReturns() + psvo.getQuantity());
				} else if (psvo.getTtype() == 4) {
					srdvo.setTotalSalesReturns(srdvo.getTotalSalesReturns() + psvo.getQuantity());
				}
				srdvo.setClosingStockF(psvo.getCurrentStock());
				srdvo.setClosingStockE(psvo.getEmptyStock());
			}
			request.setAttribute("sreport_data", (new Gson().toJson(srdvo)));

			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHSTOCKREPORTBYPRODUCT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			logger.info(ApplicationConstants.LogMessageKeys.FETCHSTOCKREPORTBYPRODUCT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		} catch (ParseException e) {
			e.printStackTrace();
			logger.info(ApplicationConstants.LogMessageKeys.FETCHSTOCKREPORTBYPRODUCT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, e);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void showBankBook(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(7001, "SHOW BANK REPORTS DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.SHOWBANKBOOK.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("bank_data", (new Gson().toJson(mdf.getAgencyAllBankData(agencyId))));
			request.setAttribute("bbr_data", (new Gson().toJson(new ArrayList<BankTranxsDataDO>())));
			request.setAttribute("norecf", 1);
			request.setAttribute("openingBalance", "0");
			request.setAttribute("sfd", "0");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWBANKBOOK.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);
		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SHOWBANKBOOK.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void fetchBankBookReport(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(7001, "FETCH BANK REPORTS DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("bank_data", (new Gson().toJson(mdf.getAgencyAllBankData(agencyId))));
			Map<String, String[]> requestParams = request.getParameterMap();
			String[] fromdate = requestParams.get("fd");
            String[] todate = requestParams.get("td");
            String[] bcodeb = requestParams.get("bid");
			long bid = Long.parseLong(bcodeb[0]);

			String openingBalAmount =  mdf.getOpeningBalanceByBank(requestParams, agencyId,bid);
          System.out.println(openingBalAmount);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long fdl = (sdf.parse(fromdate[0])).getTime();
			long tdl = (sdf.parse(todate[0])).getTime();

			Date date1 = new Date(fdl);
			SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yy");
			String fdate = df1.format(date1);

			Date date2 = new Date(tdl);
			SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
			String tdate = df2.format(date2);

			request.setAttribute("fdate", String.valueOf(fdate));
			request.setAttribute("tdate", String.valueOf(tdate));
			request.setAttribute("bcodeb", String.valueOf(bcodeb[0]));
			request.setAttribute("openingBalance", openingBalAmount); 
			request.setAttribute("sfd", (sdf.parse(fromdate[0])).getTime()+"");

			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHBANKBOOKREPORT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.BANKID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.FROMDATE.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.TODATE.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, String.valueOf(bcodeb[0]), fdate, tdate);

			ReportsDataFactory rdf = new ReportsDataFactory();
			List<BankTranxsDataDO> consolidatedList = new ArrayList<BankTranxsDataDO>();
			List<BankTranxsDataDO> btDOList = rdf.fetchAgencyBankTransactionsReport(requestParams, agencyId);

			btDOList.forEach(paydo -> {
				consolidatedList.add(paydo);
				if (paydo.getDeleted() == 2) {
					BankTranxsDataDO dataDO = setBankTransactionsData(paydo);
					consolidatedList.add(dataDO);
				}
			});

			request.setAttribute("bbr_data", (new Gson().toJson(consolidatedList)));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHBANKBOOKREPORT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			logger.info(ApplicationConstants.LogMessageKeys.FETCHBANKBOOKREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		} catch (ParseException e) {
			e.printStackTrace();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHBANKBOOKREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, e);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
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

	public void showLedger(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(7001, "SHOW PURCHASE REPORTS DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.SHOWLEDGER.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			request.setAttribute("arb_types_list", (new Gson().toJson((new CacheFactory()).getARBTypesData())));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(agencyId))));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("arb_data", (new Gson().toJson(ppmdf.getAgencyAllARBData(agencyId))));
			request.setAttribute("pwreport_data", (new Gson().toJson(new ArrayList<PurchaseInvoiceDO>())));
			request.setAttribute("preport_data", (new Gson().toJson(new ArrayList<ARBPurchaseInvoiceDO>())));
			request.setAttribute("openingBalance", "0");
			request.setAttribute("rrt", "0");
			request.setAttribute("req_type", "show");
			request.setAttribute("sfd", "0");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWLEDGER.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SHOWLEDGER.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void fetchEquipmentLedger(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(7001, "SHOW PURCHASE REPORTS DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			request.setAttribute("arb_types_list", (new Gson().toJson((new CacheFactory()).getARBTypesData())));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(agencyId))));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("arb_data", (new Gson().toJson(ppmdf.getAgencyAllARBData(agencyId))));
			request.setAttribute("pwreport_data", (new Gson().toJson(new ArrayList<PurchaseInvoiceDO>())));
			request.setAttribute("preport_data", (new Gson().toJson(new ArrayList<ARBPurchaseInvoiceDO>())));

			ReportsDataFactory rdf = new ReportsDataFactory();
			Map<String, String[]> requestParams = request.getParameterMap();
			String[] stype = requestParams.get("stype");

			String[] fromdate = requestParams.get("fd");
			String[] todate = requestParams.get("td");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			String[] pcodeval = requestParams.get("pid");
			String[] ptycodeval = requestParams.get("pv");
			long fdl = (sdf1.parse(fromdate[0])).getTime();
			long tdl = (sdf1.parse(todate[0])).getTime();

			Date date1 = new Date(fdl);
			SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yy");
			String fdate = df1.format(date1);

			Date date2 = new Date(tdl);
			SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
			String tdate = df2.format(date2);

			request.setAttribute("fdate", String.valueOf(fdate));
			request.setAttribute("tdate", String.valueOf(tdate));
			request.setAttribute("pcodetype", String.valueOf(pcodeval[0]));
			request.setAttribute("ptycodetype", String.valueOf(ptycodeval[0]));

			int stypev = Integer.parseInt(stype[0]);

			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHEQUIPMENTLEDGER.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.PRODUCTID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.PARTYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.FROMDATE.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.TODATE.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, String.valueOf(pcodeval[0]), String.valueOf(ptycodeval[0]), fdate, tdate);

			if (stypev == 1) {
				String openingBal = rdf.fetchAgencyStockLedgerReportOpsByProductId(requestParams, agencyId);
				List<AgencyStockDataDO> asDataDOList = rdf.fetchAgencyStockLedgerReportByProductId(requestParams,
						agencyId);

				request.setAttribute("pwreport_data", (new Gson().toJson(new ArrayList<PurchaseInvoiceDO>())));
				request.setAttribute("preport_data", (new Gson().toJson(asDataDOList)));
				request.setAttribute("openingBalance", openingBal);
				request.setAttribute("taxreport_data", (new Gson().toJson(new ArrayList<PurchaseInvoiceDO>())));
				request.setAttribute("rrt", "1");
			}

			else if (stypev == 2) {
				String[] cvoids = requestParams.get("pv");

				long cvoid = Long.parseLong(cvoids[0]);

				List<PartyLedgerDO> consolidatedList = new ArrayList<PartyLedgerDO>();

				// Ledger Opening balance based on PartyType(CVO)
				String openingBalAmount = rdf.fetchOpeningBalanceLedgerByPartyType(requestParams, agencyId, cvoid);

				// Ledger based on PartyType(CVO)
				List<AgencyCVOBalanceDataDO> domsidos = rdf.fetchTransactionReportCVOId(requestParams, agencyId, cvoid);
				domsidos.forEach(domsido -> {
					AtomicInteger ai = new AtomicInteger(0);
					if (ai.intValue() == 0) {
						ai.getAndIncrement();
						PartyLedgerDO dataDO = new PartyLedgerDO();
						dataDO.setTranxDate(domsido.getInv_date());
						dataDO.setDisplayId(domsido.getInv_ref_no());
						dataDO.setTranxType(domsido.getTrans_type());
						dataDO.setAmount(domsido.getAmount());
						dataDO.setBalAmount(domsido.getCbal_amount());
						dataDO.setDiscount(domsido.getDiscount());
						dataDO.setPaymentTerms(domsido.getCvoflag());
						dataDO.setAmtType(domsido.getCvo_cat());
						dataDO.setCreatedDate(domsido.getCreated_date());
						consolidatedList.add(dataDO);
					}
				});

				Collections.sort(consolidatedList, Comparator.comparing(PartyLedgerDO::getTranxDate));
				request.setAttribute("pwreport_data", (new Gson().toJson(consolidatedList)));
				request.setAttribute("preport_data", (new Gson().toJson(new ArrayList<PurchaseInvoiceDO>())));
				request.setAttribute("openingBalance", openingBalAmount);
				request.setAttribute("taxreport_data", (new Gson().toJson(new ArrayList<PurchaseInvoiceDO>())));
				request.setAttribute("rrt", "2");
			} else if (stypev == 3) {
				HttpSession session = request.getSession(true);
				CacheFactory cf = new CacheFactory();
				session.setAttribute("cash_trans_enum_data", (new Gson().toJson(cf.getCashTransEnumData())));
				String[] cvoids = requestParams.get("pv");
				request.setAttribute("cbr_data",
						(new Gson().toJson(rdf.fetchAgencyCashTransactionsReport(requestParams, agencyId, cvoids))));
				request.setAttribute("rrt", "3");
				request.setAttribute("openingBalance", "0");
			} else if (stypev == 4) {
				List<GSTPaymentsLedgerDO> consolidatedList = new ArrayList<>();
				List<GSTPaymentDetailsDO> gstpdetailsDO = rdf.fetchAgencyGSTPaymentDetailsReport(requestParams,
						agencyId);

				gstpdetailsDO.forEach(gstpdo -> {
					if (gstpdo.getTax_type() == 1) {
						GSTPaymentsLedgerDO eldo = new GSTPaymentsLedgerDO();
						if (gstpdo.getDeleted() == 2) {
							GSTPaymentsLedgerDO eldo2 = new GSTPaymentsLedgerDO();
							eldo2.setTranxId(gstpdo.getId());
							eldo2.setpStatus(gstpdo.getPstatus());
							eldo2.setTaxType(gstpdo.getTax_type());
							eldo2.setGstAmount(gstpdo.getGst_amount());
							eldo2.setBalGSTAmount(gstpdo.getDt_gst_amount());
							eldo2.setCreatedDate(gstpdo.getModified_date());
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String time = sdf.format(gstpdo.getModified_date());
							try {
								eldo2.setTranxDate(sdf.parse(time).getTime());
							} catch (ParseException e) {
								e.printStackTrace();
							}

							if (gstpdo.getPstatus() == 1) {
								// REVERSAL OF PAYMENT TO GST ACCOUNT
								eldo2.setTranxType(2);
							} else {
								// for NONE - because there will be no deleted option for status 2(as it is
								// payoff in gstr3)
								eldo2.setTranxType(0);
							}
							consolidatedList.add(eldo2);
						}
						eldo.setTranxId(gstpdo.getId());
						eldo.setTranxDate(gstpdo.getPdate());
						eldo.setpStatus(gstpdo.getPstatus());
						eldo.setTaxType(gstpdo.getTax_type());
						eldo.setGstAmount(gstpdo.getGst_amount());
						eldo.setBalGSTAmount(gstpdo.getDs_total_gst_amount());
						eldo.setCreatedDate(gstpdo.getCreated_date());

						if (gstpdo.getPstatus() == 1) {
							// PAYMENT TO GST ACCOUNT
							eldo.setTranxType(1);
						} else if (gstpdo.getPstatus() == 2) {
							// GST AMOUNT PAID
							eldo.setTranxType(3);
						}

						consolidatedList.add(eldo);
					}
				});

				Collections.sort(consolidatedList, Comparator.comparing(GSTPaymentsLedgerDO::getTranxDate));
				request.setAttribute("pwreport_data", (new Gson().toJson(new ArrayList<PurchaseInvoiceDO>())));
				request.setAttribute("preport_data", (new Gson().toJson(new ArrayList<PurchaseInvoiceDO>())));
				request.setAttribute("taxreport_data", (new Gson().toJson(consolidatedList)));
				request.setAttribute("openingBalance", "0");
				request.setAttribute("rrt", "4");
			}

			request.setAttribute("req_type", "fetch");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			request.setAttribute("sfd", (sdf.parse(fromdate[0])).getTime() + "");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHEQUIPMENTLEDGER.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			logger.info(ApplicationConstants.LogMessageKeys.FETCHEQUIPMENTLEDGER.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		} catch (ParseException e) {
			e.printStackTrace();
			logger.info(ApplicationConstants.LogMessageKeys.FETCHEQUIPMENTLEDGER.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.PARSEEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, e);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void showDayEndReport(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(7301, "SHOW DAY END REPORT DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			request.setAttribute("dom_data", (new Gson().toJson(new ArrayList<EquipmentDEDO>())));
			request.setAttribute("bank_data", (new Gson().toJson(new ArrayList<BankTranxsDataDO>())));
			request.setAttribute("bbr_data", (new Gson().toJson(new ArrayList<BankTranxsDataDO>())));
			request.setAttribute("rder", "0");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SHOWDAYENDPAGE.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.STATUS.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);
		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void fetchDayEndReport(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(7302, "FETCH DAY END REPORT DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			long agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			Map<String, String[]> requestParams = request.getParameterMap();
			String[] pcode = requestParams.get("der");
			ReportsDataFactory rdf = new ReportsDataFactory();
			int pc = Integer.parseInt(pcode[0]);
			switch (pc) {
			case 1:
				request.setAttribute("rder", "1");
				List<PurchaseInvoiceDO> allDOMPurchasesList = rdf.fetchAgencyDAYENDDOMPurchaseReport(requestParams,
						agencyId);
				List<DOMSalesInvoiceDO> allDOMSalesList = rdf.fetchAgencyDAYENDDOMCSaleReport(requestParams, agencyId);
				List<EquipmentDEDO> allDOMList = new ArrayList<EquipmentDEDO>();
				EquipmentDEDO displayDO1 = new EquipmentDEDO();
				displayDO1.setName("5 KG SUB CYLINDER");
				EquipmentDEDO displayDO2 = new EquipmentDEDO();
				displayDO2.setName("14.2 KG SUB CYLINDER");
				EquipmentDEDO displayDO3 = new EquipmentDEDO();
				displayDO3.setName("14.2 KG NS CYLINDER");
				for (int c = 0; c < allDOMPurchasesList.size(); c++) {
					PurchaseInvoiceDO dataDO = allDOMPurchasesList.get(c);
					if (dataDO.getProd_code() == 1) {
						if (displayDO1.getOpeningStock() == 0)
							displayDO1.setOpeningStock(dataDO.getCs_fulls() - dataDO.getQuantity());
						displayDO1.setPurchasedQuantity(displayDO1.getPurchasedQuantity() + dataDO.getQuantity());
					} else if (dataDO.getProd_code() == 2) {
						if (displayDO2.getOpeningStock() == 0)
							displayDO2.setOpeningStock(dataDO.getCs_fulls() - dataDO.getQuantity());
						displayDO2.setPurchasedQuantity(displayDO2.getPurchasedQuantity() + dataDO.getQuantity());
					} else if (dataDO.getProd_code() == 3) {
						if (displayDO3.getOpeningStock() == 0)
							displayDO2.setOpeningStock(dataDO.getCs_fulls() - dataDO.getQuantity());
						displayDO3.setPurchasedQuantity(displayDO3.getPurchasedQuantity() + dataDO.getQuantity());
					}
				}
				for (int c = 0; c < allDOMSalesList.size(); c++) {
					DOMSalesInvoiceDO sidataDO = allDOMSalesList.get(c);
					List<DOMSalesInvoiceDetailsDO> dataDOList = sidataDO.getDetailsList();
					for (int d = 0; d < dataDOList.size(); d++) {
						DOMSalesInvoiceDetailsDO dataDO = dataDOList.get(d);
						if (dataDO.getProd_code() == 1) {
							displayDO1.setSaleQuantity(displayDO1.getSaleQuantity() + dataDO.getQuantity());
							displayDO1.setClosingStock(dataDO.getCs_fulls());
						} else if (dataDO.getProd_code() == 2) {
							displayDO2.setSaleQuantity(displayDO2.getSaleQuantity() + dataDO.getQuantity());
							displayDO2.setClosingStock(dataDO.getCs_fulls());
						} else if (dataDO.getProd_code() == 3) {
							displayDO3.setSaleQuantity(displayDO3.getSaleQuantity() + dataDO.getQuantity());
							displayDO3.setClosingStock(dataDO.getCs_fulls());
						}
					}
				}
				allDOMList.add(displayDO1);
				allDOMList.add(displayDO2);
				allDOMList.add(displayDO3);
				request.setAttribute("dom_data", (new Gson().toJson(allDOMList)));
				break;
			case 2:
				request.setAttribute("rder", "1");
				List<PurchaseInvoiceDO> allCOMPurchasesList = rdf.fetchAgencyDAYENDCOMPurchaseReport(requestParams,
						agencyId);
				List<COMSalesInvoiceDO> allCOMSalesList = rdf.fetchAgencyDAYENDCOMCSaleReport(requestParams, agencyId);
				List<EquipmentDEDO> allCOMList = new ArrayList<EquipmentDEDO>();
				EquipmentDEDO displayDO4 = new EquipmentDEDO();
				displayDO4.setName("5 KG FTL CYLINDER");
				EquipmentDEDO displayDO5 = new EquipmentDEDO();
				displayDO5.setName("19 KG NDNE CYLINDER");
				EquipmentDEDO displayDO6 = new EquipmentDEDO();
				displayDO6.setName("19 KG RAZOR CYLINDER");
				EquipmentDEDO displayDO7 = new EquipmentDEDO();
				displayDO7.setName("35 KG NDNE CYLINDER");
				EquipmentDEDO displayDO8 = new EquipmentDEDO();
				displayDO8.setName("47.5 KG NDNE CYLINDER");
				EquipmentDEDO displayDO9 = new EquipmentDEDO();
				displayDO9.setName("450 KG SUMO CYLINDERS");
				for (int c = 0; c < allCOMPurchasesList.size(); c++) {
					PurchaseInvoiceDO dataDO = allCOMPurchasesList.get(c);
					if (dataDO.getProd_code() == 4) {
						if (displayDO4.getOpeningStock() == 0)
							displayDO4.setOpeningStock(dataDO.getCs_fulls() - dataDO.getQuantity());
						displayDO4.setPurchasedQuantity(displayDO4.getPurchasedQuantity() + dataDO.getQuantity());
					} else if (dataDO.getProd_code() == 5) {
						if (displayDO5.getOpeningStock() == 0)
							displayDO5.setOpeningStock(dataDO.getCs_fulls() - dataDO.getQuantity());
						displayDO5.setPurchasedQuantity(displayDO5.getPurchasedQuantity() + dataDO.getQuantity());
					} else if (dataDO.getProd_code() == 6) {
						if (displayDO6.getOpeningStock() == 0)
							displayDO6.setOpeningStock(dataDO.getCs_fulls() - dataDO.getQuantity());
						displayDO6.setPurchasedQuantity(displayDO6.getPurchasedQuantity() + dataDO.getQuantity());
					} else if (dataDO.getProd_code() == 7) {
						if (displayDO7.getOpeningStock() == 0)
							displayDO7.setOpeningStock(dataDO.getCs_fulls() - dataDO.getQuantity());
						displayDO7.setPurchasedQuantity(displayDO7.getPurchasedQuantity() + dataDO.getQuantity());
					} else if (dataDO.getProd_code() == 8) {
						if (displayDO8.getOpeningStock() == 0)
							displayDO8.setOpeningStock(dataDO.getCs_fulls() - dataDO.getQuantity());
						displayDO8.setPurchasedQuantity(displayDO8.getPurchasedQuantity() + dataDO.getQuantity());
					} else if (dataDO.getProd_code() == 9) {
						if (displayDO9.getOpeningStock() == 0)
							displayDO9.setOpeningStock(dataDO.getCs_fulls() - dataDO.getQuantity());
						displayDO9.setPurchasedQuantity(displayDO9.getPurchasedQuantity() + dataDO.getQuantity());
					}
				}
				for (int c = 0; c < allCOMSalesList.size(); c++) {
					COMSalesInvoiceDO sidataDO = allCOMSalesList.get(c);
					List<COMSalesInvoiceDetailsDO> dataDOList = sidataDO.getDetailsList();
					for (int d = 0; d < dataDOList.size(); d++) {
						COMSalesInvoiceDetailsDO dataDO = dataDOList.get(d);
						if (dataDO.getProd_code() == 4) {
							displayDO4.setSaleQuantity(displayDO4.getSaleQuantity() + dataDO.getQuantity());
							displayDO4.setClosingStock(dataDO.getCs_fulls());
						} else if (dataDO.getProd_code() == 5) {
							displayDO5.setSaleQuantity(displayDO5.getSaleQuantity() + dataDO.getQuantity());
							displayDO5.setClosingStock(dataDO.getCs_fulls());
						} else if (dataDO.getProd_code() == 6) {
							displayDO6.setSaleQuantity(displayDO6.getSaleQuantity() + dataDO.getQuantity());
							displayDO6.setClosingStock(dataDO.getCs_fulls());
						} else if (dataDO.getProd_code() == 7) {
							displayDO7.setSaleQuantity(displayDO7.getSaleQuantity() + dataDO.getQuantity());
							displayDO7.setClosingStock(dataDO.getCs_fulls());
						} else if (dataDO.getProd_code() == 8) {
							displayDO8.setSaleQuantity(displayDO8.getSaleQuantity() + dataDO.getQuantity());
							displayDO8.setClosingStock(dataDO.getCs_fulls());
						} else if (dataDO.getProd_code() == 9) {
							displayDO9.setSaleQuantity(displayDO9.getSaleQuantity() + dataDO.getQuantity());
							displayDO9.setClosingStock(dataDO.getCs_fulls());
						}
					}
				}
				allCOMList.add(displayDO4);
				allCOMList.add(displayDO5);
				allCOMList.add(displayDO6);
				allCOMList.add(displayDO7);
				allCOMList.add(displayDO8);
				allCOMList.add(displayDO9);

				request.setAttribute("dom_data", (new Gson().toJson(allCOMList)));
				break;

			case 3:
				request.setAttribute("rder", "3");
				break;
			case 4:
				MasterDataFactory mdf = new MasterDataFactory();
				request.setAttribute("bank_data", (new Gson().toJson(mdf.getAgencyBankData(agencyId))));
				request.setAttribute("rder", "4");
				List<BankTranxsDataDO> bankTranxDataDOList = rdf.fetchAgencyDAYENDBankTransactionsReport(requestParams,
						agencyId);
				Collections.sort(bankTranxDataDOList, Comparator.comparing(BankTranxsDataDO::getBt_date));
				request.setAttribute("bbr_data", (new Gson().toJson(bankTranxDataDOList)));
				break;
			default:
				break;
			}
			request.setAttribute("req_type", "fetch");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void showDCMS(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(7311, "SHOW DCMS REPORT DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			request.setAttribute("rrt", "0");
			request.setAttribute("req_type", "show");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void fetchDCMS(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(7312, "FETCH DCMS REPORT DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			request.setAttribute("rrt", "0");
			request.setAttribute("req_type", "show");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
		}
	}

	public void showNCDBCRCTVReport(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(7001, "SHOW PURCHASE REPORTS DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {

			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			logger.info(ApplicationConstants.LogMessageKeys.SHOWNCDBCRCTVREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(ppmdf.getAgencyAllEquipmentData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			request.setAttribute("report_data", "");
			request.setAttribute("rrt", "0");
			request.setAttribute("req_type", "show");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWNCDBCRCTVREPORT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SHOWNCDBCRCTVREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void fetchNCDBCRCTVReport(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(7001, "FETCH PURCHASE REPORTS DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			request.setAttribute("cylinder_types_list",
					(new Gson().toJson((new CacheFactory()).getCylinderTypesData())));
			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			request.setAttribute("equipment_data", (new Gson().toJson(ppmdf.getAgencyAllEquipmentData(
					((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue()))
							.getAgency_code()))));
			Map<String, String[]> requestParams = request.getParameterMap();
			String[] fromdate = requestParams.get("fd");
			String[] todate = requestParams.get("td");
			String[] pcodenc = requestParams.get("pid");
			String[] concodenc = requestParams.get("ct");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long fdl = (sdf.parse(fromdate[0])).getTime();
			long tdl = (sdf.parse(todate[0])).getTime();

			Date date1 = new Date(fdl);
			SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yy");
			String fdate = df1.format(date1);

			Date date2 = new Date(tdl);
			SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
			String tdate = df2.format(date2);

			request.setAttribute("fdate", String.valueOf(fdate));
			request.setAttribute("tdate", String.valueOf(tdate));
			request.setAttribute("pcodenc", String.valueOf(pcodenc[0]));
			request.setAttribute("concodenc", String.valueOf(concodenc[0]));

			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHNCDBCRCTVREPORT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.FROMDATE.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.TODATE.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.NCDBCRCTVPRODUCTID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.CONNECTIONTYPE.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, String.valueOf(pcodenc[0]), String.valueOf(concodenc[0]), fdate, tdate);

			ReportsDataFactory rdf = new ReportsDataFactory();
			String[] cts = requestParams.get("ct");
			int ct = Integer.parseInt(cts[0]);
			List<NCDBCRCTVReportVO> allVOS = new ArrayList<>();

			if (ct == 1 || ct == 2 || ct == 3 || ct == 4 || ct == 0) {// For NC/DBC

				logger.info(
						ApplicationConstants.LogMessageKeys.GETNCDBCREPORT.getValue()
								+ ApplicationConstants.paramKeys.PARAM.getValue()
								+ ApplicationConstants.paramKeys.AGENCYID.getValue()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
								+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
								+ ApplicationConstants.paramKeys.NCDBCRCTVPRODUCTID.getValue()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
								+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
								+ ApplicationConstants.paramKeys.CONNECTIONTYPE.getValue()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
						agencyId, String.valueOf(pcodenc[0]), ct);

				allVOS.addAll(rdf.fetchAgencyNCDBCForNCDBCRCTVReport(requestParams, agencyId));

				logger.info(
						ApplicationConstants.LogMessageKeys.GETNCDBCREPORT.getValue()
								+ ApplicationConstants.paramKeys.PARAM.getValue()
								+ ApplicationConstants.paramKeys.AGENCYID.getValue()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
								+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
								+ ApplicationConstants.actionStatusKeys.STATUS.toString()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
						agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);
			}

			if (ct == 5 || ct == 0) {// For RC

				logger.info(
						ApplicationConstants.LogMessageKeys.GETRCREPORT.getValue()
								+ ApplicationConstants.paramKeys.PARAM.getValue()
								+ ApplicationConstants.paramKeys.AGENCYID.getValue()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
								+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
								+ ApplicationConstants.paramKeys.NCDBCRCTVPRODUCTID.getValue()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
								+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
								+ ApplicationConstants.paramKeys.CONNECTIONTYPE.getValue()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
						agencyId, String.valueOf(pcodenc[0]), ct);

				allVOS.addAll(rdf.fetchAgencyRCForNCDBCRCTVReport(requestParams, agencyId));

				logger.info(
						ApplicationConstants.LogMessageKeys.GETRCREPORT.getValue()
								+ ApplicationConstants.paramKeys.PARAM.getValue()
								+ ApplicationConstants.paramKeys.AGENCYID.getValue()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
								+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
								+ ApplicationConstants.actionStatusKeys.STATUS.toString()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
						agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);
			}

			if (ct == 6 || ct == 0) {// For TV

				logger.info(
						ApplicationConstants.LogMessageKeys.GETTVREPORT.getValue()
								+ ApplicationConstants.paramKeys.PARAM.getValue()
								+ ApplicationConstants.paramKeys.AGENCYID.getValue()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
								+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
								+ ApplicationConstants.paramKeys.NCDBCRCTVPRODUCTID.getValue()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
								+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
								+ ApplicationConstants.paramKeys.CONNECTIONTYPE.getValue()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
						agencyId, String.valueOf(pcodenc[0]), ct);

				allVOS.addAll(rdf.fetchAgencyTVForNCDBCRCTVReport(requestParams, agencyId));

				logger.info(
						ApplicationConstants.LogMessageKeys.GETTVREPORT.getValue()
								+ ApplicationConstants.paramKeys.PARAM.getValue()
								+ ApplicationConstants.paramKeys.AGENCYID.getValue()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
								+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
								+ ApplicationConstants.actionStatusKeys.STATUS.toString()
								+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
						agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);
			}

			Collections.sort(allVOS, Comparator.comparing(NCDBCRCTVReportVO::getTranxDate));
			request.setAttribute("report_data", (new Gson().toJson(allVOS)));
			request.setAttribute("rrt", "1");
			request.setAttribute("req_type", "fetch");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHNCDBCRCTVREPORT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.FETCHNCDBCRCTVREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);
		} catch (ParseException e) {
			e.printStackTrace();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHNCDBCRCTVREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.PARSEEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, e);
		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void showReceivables(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(7501, "SHOW RECEIVABLES", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			logger.info(ApplicationConstants.LogMessageKeys.SHOWRECEIVABLES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(agencyId))));
			request.setAttribute("rrt", "0");
			request.setAttribute("scustid", "0");
			request.setAttribute("req_type", "show");
			request.setAttribute("page_data", (new Gson().toJson(new ArrayList<PurchaseInvoiceDO>())));
			request.setAttribute("sad", "0");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWRECEIVABLES.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SHOWRECEIVABLES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	public void fetchReceivables(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(7501, "SHOW RECEIVABLES", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(agencyId))));
			// fetch data
			Map<String, String[]> requestParams = request.getParameterMap();
			ReportsDataFactory rdf = new ReportsDataFactory();
			List<ReceivablesDO> rcList = rdf.fetchReceivablesByCustomerId(requestParams, agencyId);
			String[] asondate = requestParams.get("ad");
			long customerId = Long.parseLong(requestParams.get("cs")[0]);

			Date d = new Date();
			long adl = Date.parse(asondate[0]);

			// Process 30,60, >60 days
			long w30 = adl - (30 * 24 * 60 * 60 * 1000L);
			long w60 = adl - (60 * 24 * 60 * 60 * 1000L);

			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHRECEIVABLES.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.ARDATE.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.CUSTOMERID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, String.valueOf(asondate[0]), customerId);

			List<RPPageDO> pageDOList = new ArrayList<>();
			for (ReceivablesDO rcDO : rcList) {
				RPPageDO pageDO = new RPPageDO();
				if ((rcDO.getInvDate() >= w30) && (rcDO.getInvDate() <= adl)) {
					pageDO.setDaysType(1);
				} else if ((rcDO.getInvDate() < w30) && (rcDO.getInvDate() >= w60)) {
					pageDO.setDaysType(2);
				} else {
					pageDO.setDaysType(3);
				}
				pageDO.setInvAmount(rcDO.getInvAmount());
				pageDOList.add(pageDO);
			}
			request.setAttribute("rrt", "1");
			request.setAttribute("scustid", customerId + "");
			request.setAttribute("req_type", "fetch");
			request.setAttribute("page_data", (new Gson().toJson(pageDOList)));
			request.setAttribute("sad", (d.parse(asondate[0])) + "");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHRECEIVABLES.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.FETCHRECEIVABLES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		} catch (ParseException e) {
			e.printStackTrace();
			logger.info(ApplicationConstants.LogMessageKeys.FETCHRECEIVABLES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.PARSEEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, e);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void showPayables(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(7511, "SHOW PAYABLES", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			logger.info(ApplicationConstants.LogMessageKeys.SHOWPAYABLES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(agencyId))));
			request.setAttribute("rrt", "0");
			request.setAttribute("svenid", "0");
			request.setAttribute("req_type", "show");
			request.setAttribute("sad", "0");
			request.setAttribute("page_data", (new Gson().toJson(new ArrayList<PurchaseInvoiceDO>())));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWPAYABLES.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			logger.info(ApplicationConstants.LogMessageKeys.SHOWPAYABLES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);
		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	@SuppressWarnings("deprecation")
	public void fetchPayables(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(7511, "SHOW PAYABLES", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("cvo_data", (new Gson().toJson(mdf.getAgencyAllCVOData(agencyId))));
			// fetch data
			Map<String, String[]> requestParams = request.getParameterMap();
			ReportsDataFactory rdf = new ReportsDataFactory();
			List<PayablesDO> pbList = rdf.fetchPayablesByVendorId(requestParams, agencyId);
			String[] asondate = requestParams.get("ad");
			long vendorId = Long.parseLong(requestParams.get("vs")[0]);

			long adl = Date.parse(asondate[0]);

			// Process 30,60, >60 days
			long w30 = adl - (30 * 24 * 60 * 60 * 1000L);
			long w60 = adl - (60 * 24 * 60 * 60 * 1000L);

			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHPAYABLES.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.ARDATE.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.paramKeys.VENDORORCUSTOMERID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, String.valueOf(asondate[0]), vendorId);

			List<RPPageDO> pageDOList = new ArrayList<>();
			for (PayablesDO pbDO : pbList) {
				RPPageDO pageDO = new RPPageDO();
				if ((pbDO.getInvDate() >= w30) && (pbDO.getInvDate() <= adl)) {
					pageDO.setDaysType(1);
				} else if ((pbDO.getInvDate() < w30) && (pbDO.getInvDate() >= w60)) {
					pageDO.setDaysType(2);
				} else {
					pageDO.setDaysType(3);
				}
				pageDO.setInvAmount(pbDO.getInvAmount());
				pageDOList.add(pageDO);
			}
			request.setAttribute("rrt", "1");
			request.setAttribute("svenid", vendorId + "");
			request.setAttribute("req_type", "fetch");
			request.setAttribute("sad", (adl) + "");
			request.setAttribute("page_data", (new Gson().toJson(pageDOList)));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHPAYABLES.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			logger.info(ApplicationConstants.LogMessageKeys.FETCHPAYABLES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		} catch (ParseException e) {
			e.printStackTrace();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHPAYABLES.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.PARSEEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, e);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

}
