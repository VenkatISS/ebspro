package com.it.erpapp.appservices;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.it.erpapp.commons.ApplicationConstants;
import com.it.erpapp.framework.GSTReportsDataFactory;
import com.it.erpapp.framework.MasterDataFactory;
import com.it.erpapp.framework.PPMasterDataFactory;
import com.it.erpapp.framework.model.GSTPaymentsDataDO;
import com.it.erpapp.framework.model.StaffDataDO;
import com.it.erpapp.framework.model.pps.ARBDataDO;
import com.it.erpapp.framework.model.pps.EquipmentDataDO;
import com.it.erpapp.framework.model.pps.ServicesGSTData;
import com.it.erpapp.framework.model.transactions.ARBPurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.GSTDataDO;
import com.it.erpapp.framework.model.transactions.OtherPurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.PurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.others.CreditNotesDO;
import com.it.erpapp.framework.model.transactions.others.DebitNotesDO;
import com.it.erpapp.framework.model.transactions.sales.ARBSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.ARBSalesInvoiceDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.COMSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.COMSalesInvoiceDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.DOMSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.DOMSalesInvoiceDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.NCDBCInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.NCDBCInvoiceDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.PurchaseReturnDO;
import com.it.erpapp.framework.model.transactions.sales.PurchaseReturnDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.RCDataDO;
import com.it.erpapp.framework.model.transactions.sales.SalesReturnDO;
import com.it.erpapp.framework.model.transactions.sales.SalesReturnDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.TVDataDO;
import com.it.erpapp.framework.model.vos.AgencyVO;
import com.it.erpapp.response.MessageObject;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class GSTReportServiceBean {

	Logger logger = LoggerFactory.getLogger(GSTReportServiceBean.class);
	long agencyId;
	String[] sMonth;
	String[] sYear;
	
	int ofp_acceptable = -1;

	public void showGSTReport(HttpServletRequest request,HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(7301, "SHOW GST REPORT DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.SHOWGSTREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			request.setAttribute("gst_p_data", new Gson().toJson(new ArrayList<String>()));
			request.setAttribute("gst_s_data", new Gson().toJson(new ArrayList<String>()));
			request.setAttribute("staff_data", new Gson().toJson(new ArrayList<StaffDataDO>()));
			request.setAttribute("gstdata_size", "-1");
			request.setAttribute("sd", "0");
			request.setAttribute("rder", "0");
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.SHOWGSTREPORT.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SHOWGSTREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void fetchGSTReport(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(7302, "FETCH GST REPORTS DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();
			Map<String, String[]> requestParams = request.getParameterMap();

			ofp_acceptable = ((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getOffer_price_acceptable();
			
			sMonth = requestParams.get("sm");
			sYear = requestParams.get("sy");
			String month = request.getParameter("sm");
			String year = request.getParameter("sy");

			request.setAttribute("month", month);
			request.setAttribute("year", year);

			logger.info(ApplicationConstants.LogMessageKeys.FETCHGSTREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.MONTH.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, sMonth[0], sYear[0]);

			String[] fromdate = requestParams.get("fd");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long fdl = sdf.parse(fromdate[0]).getTime();
			request.setAttribute("sd", String.valueOf(fdl));
			MasterDataFactory mdf = new MasterDataFactory();
			request.setAttribute("staff_data", new Gson().toJson(mdf.getAgencyStaffData(agencyId)));


			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			List<EquipmentDataDO> edos = ppmdf.getAgencyAllEquipmentData(agencyId);
			List<ARBDataDO> arbdos = ppmdf.getAgencyAllARBData(agencyId);
	//		List<ServicesDataDO> serdos = ppmdf.getAgencyServicesData(agencyId);
			List<ServicesGSTData> serdos = ppmdf.getAgencyServicesGTData(agencyId);

			GSTReportsDataFactory rdf = new GSTReportsDataFactory();
			List<PurchaseInvoiceDO> dcpis = rdf.fetchAgencyGSTDOMPurchaseReport(requestParams, agencyId);
			List<PurchaseInvoiceDO> ccpis = rdf.fetchAgencyGSTCOMPurchaseReport(requestParams, agencyId);
			List<ARBPurchaseInvoiceDO> arbpis = rdf.fetchAgencyGSTARBPurchaseReport(requestParams, agencyId);
			List<OtherPurchaseInvoiceDO> opis = rdf.fetchAgencyGSTOtherPurchaseReport(requestParams, agencyId);

			List<DOMSalesInvoiceDO> dcsis = rdf.fetchAgencyGSTDOMCSaleReport(requestParams, agencyId);
			List<COMSalesInvoiceDO> ccsis = rdf.fetchAgencyGSTCOMCSaleReport(requestParams, agencyId);
			List<ARBSalesInvoiceDO> arbsis = rdf.fetchAgencyGSTARBSaleReport(requestParams, agencyId);

			List<NCDBCInvoiceDO> ncdbcs = rdf.fetchAgencyGSTNCDBCSaleReport(requestParams, agencyId);
			List<RCDataDO> rcs = rdf.fetchAgencyGSTRCSaleReport(requestParams, agencyId);
			List<TVDataDO> tvs = rdf.fetchAgencyGSTTVSaleReport(requestParams, agencyId);

			List<PurchaseReturnDO> prs = rdf.fetchAgencyGSTPurchaseReturnsReport(requestParams, agencyId);
			List<SalesReturnDO> srs = rdf.fetchAgencyGSTSalesReturnsReport(requestParams, agencyId);

			List<CreditNotesDO> cns = rdf.fetchAgencyGSTCreditNotesReport(requestParams, agencyId);
			List<DebitNotesDO> dns = rdf.fetchAgencyGSTDebitNotesReport(requestParams, agencyId);
			List<GSTPaymentsDataDO> gps = rdf.fetchAgencyGSTPaymentsData(sMonth[0], sYear[0], agencyId);

			List<GSTDataDO> gstDataDOList = new ArrayList<>();
			// Process Purchase Invoices
			dcpis.forEach(dcpi -> {
				for (EquipmentDataDO edo : edos) {
					if (edo.getProd_code() == dcpi.getProd_code()) {
						GSTDataDO gstDataDO = new GSTDataDO();
						gstDataDO.setGstType("domp");
						gstDataDO.setRate(edo.getGstp());
						gstDataDO.setTtype(1);
						gstDataDO.setIgst(new BigDecimal(dcpi.getIgst_amount()));
						gstDataDO.setCgst(new BigDecimal(dcpi.getCgst_amount()));
						gstDataDO.setSgst(new BigDecimal(dcpi.getSgst_amount()));
						gstDataDO.setTaxable_value(new BigDecimal(dcpi.getBasic_amount()));
						gstDataDOList.add(gstDataDO);
						break;
					}
				}
			});

			ccpis.forEach(ccpi -> {
				for (EquipmentDataDO edo : edos) {
					if (edo.getProd_code() == ccpi.getProd_code()) {
						GSTDataDO gstDataDO = new GSTDataDO();
						gstDataDO.setGstType("comp");
						gstDataDO.setRate(edo.getGstp());
						gstDataDO.setTtype(1);
						gstDataDO.setIgst(new BigDecimal(ccpi.getIgst_amount()));
						gstDataDO.setCgst(new BigDecimal(ccpi.getCgst_amount()));
						gstDataDO.setSgst(new BigDecimal(ccpi.getSgst_amount()));
						gstDataDO.setTaxable_value(new BigDecimal(ccpi.getBasic_amount()));
						gstDataDOList.add(gstDataDO);
						break;
					}
				}
			});

			for (ARBPurchaseInvoiceDO pdo : arbpis) {
				for (ARBDataDO arbdo : arbdos) {
					if (arbdo.getId() == pdo.getArb_code()) {
						int revc = pdo.getReverse_charge();
						if (revc == 2) {
							GSTDataDO gstDataDO = new GSTDataDO();
							gstDataDO.setRate(arbdo.getGstp());
							gstDataDO.setGstType("arbp");
							gstDataDO.setTtype(1);
							gstDataDO.setIgst(new BigDecimal(pdo.getIgst_amount()));
							gstDataDO.setCgst(new BigDecimal(pdo.getCgst_amount()));
							gstDataDO.setSgst(new BigDecimal(pdo.getSgst_amount()));
							gstDataDO.setTaxable_value(new BigDecimal(pdo.getBasic_amount()));
							gstDataDOList.add(gstDataDO);
						} else if (revc == 1) {
							GSTDataDO sgstDataDO = new GSTDataDO();
							sgstDataDO.setRate(arbdo.getGstp());
							sgstDataDO.setGstType("arbrp");
							sgstDataDO.setTtype(3);
							sgstDataDO.setIgst(new BigDecimal(pdo.getIgst_amount()));
							sgstDataDO.setCgst(new BigDecimal(pdo.getCgst_amount()));
							sgstDataDO.setSgst(new BigDecimal(pdo.getSgst_amount()));
							sgstDataDO.setTaxable_value(new BigDecimal(pdo.getBasic_amount()));
							gstDataDOList.add(sgstDataDO);
						}
						break;
					}
				}
			}

			opis.forEach(opi -> {
				int tbl = opi.getTaxable();
				if (tbl == 1) {
					int revc = opi.getReverse_charge();
					if (revc == 2) {
						GSTDataDO gstDataDO = new GSTDataDO();
						gstDataDO.setRate(opi.getGstp());
						gstDataDO.setGstType("op");
						gstDataDO.setTtype(1);
						gstDataDO.setIgst(new BigDecimal(opi.getIgst_amount()));
						gstDataDO.setCgst(new BigDecimal(opi.getCgst_amount()));
						gstDataDO.setSgst(new BigDecimal(opi.getSgst_amount()));
						gstDataDO.setTaxable_value(new BigDecimal(opi.getBasic_amount()));
						gstDataDOList.add(gstDataDO);
					}
					if (revc == 1) {
						GSTDataDO sgstDataDO = new GSTDataDO();
						sgstDataDO.setRate(opi.getGstp());
						sgstDataDO.setGstType("orp");
						sgstDataDO.setTtype(3);
						sgstDataDO.setIgst(new BigDecimal(opi.getIgst_amount()));
						sgstDataDO.setCgst(new BigDecimal(opi.getCgst_amount()));
						sgstDataDO.setSgst(new BigDecimal(opi.getSgst_amount()));
						sgstDataDO.setTaxable_value(new BigDecimal(opi.getBasic_amount()));
						gstDataDOList.add(sgstDataDO);
					}
				}
			});

						
			dcsis.forEach(dcsi -> {
				for (EquipmentDataDO edo : edos) {
					List<DOMSalesInvoiceDetailsDO> detailsList = dcsi.getDetailsList();
					for (DOMSalesInvoiceDetailsDO dataDO : detailsList) {
						if (edo.getProd_code() == dataDO.getProd_code()) {
							GSTDataDO gstDataDO = new GSTDataDO();
							gstDataDO.setGstType("doms");
							gstDataDO.setRate(edo.getGstp());
							gstDataDO.setTtype(2);
							gstDataDO.setIgst(new BigDecimal(dataDO.getIgst_amount()));
							gstDataDO.setCgst(new BigDecimal(dataDO.getCgst_amount()));
							gstDataDO.setSgst(new BigDecimal(dataDO.getSgst_amount()));
							float tax = (dataDO.getQuantity() - dataDO.getPsv_cyls())
											* (Float.parseFloat(dataDO.getUnit_rate())
											- Float.parseFloat(dataDO.getDisc_unit_rate()));
							gstDataDO.setTaxable_value(BigDecimal.valueOf(tax));
							gstDataDOList.add(gstDataDO);
						}
					}
				}
			});
			
			
//			dcsis.forEach(dcsi -> {
//				for (EquipmentDataDO edo : edos) {
//					List<DOMSalesInvoiceDetailsDO> detailsList = dcsi.getDetailsList();
//					for (DOMSalesInvoiceDetailsDO dataDO : detailsList) {
//						if (edo.getProd_code() == dataDO.getProd_code()) {
//							GSTDataDO gstDataDO = new GSTDataDO();
//							gstDataDO.setGstType("doms");
//							gstDataDO.setRate(edo.getGstp());
//							gstDataDO.setTtype(2);
//							if(dcsi.getSr_no().contains("SI")) {
//								gstDataDO.setIgst(new BigDecimal(dataDO.getIgst_amount()));
//								gstDataDO.setCgst(new BigDecimal(dataDO.getCgst_amount()));
//								gstDataDO.setSgst(new BigDecimal(dataDO.getSgst_amount()));
//								float tax = (dataDO.getQuantity() - dataDO.getPsv_cyls())
//												* (Float.parseFloat(dataDO.getUnit_rate())
//												- Float.parseFloat(dataDO.getDisc_unit_rate()));
//								gstDataDO.setTaxable_value(BigDecimal.valueOf(tax));
//							}else if(dcsi.getSr_no().contains("CS")) {
//								gstDataDO.setIgst(new BigDecimal(dataDO.getOp_igst_amt()));
//								gstDataDO.setCgst(new BigDecimal(dataDO.getOp_cgst_amt()));
//								gstDataDO.setSgst(new BigDecimal(dataDO.getOp_sgst_amt()));
//								gstDataDO.setTaxable_value(new BigDecimal(dataDO.getOp_taxable_amt()));
//							}
//							gstDataDOList.add(gstDataDO);
//						}
//					}
//				}
//			});

//			ccsis.forEach(ccsi -> {
//				for (EquipmentDataDO edo : edos) {
//					List<COMSalesInvoiceDetailsDO> detailsList = ccsi.getDetailsList();
//					for (COMSalesInvoiceDetailsDO dataDO : detailsList) {
//						if (edo.getProd_code() == dataDO.getProd_code()) {
//							GSTDataDO gstDataDO = new GSTDataDO();
//							gstDataDO.setGstType("coms");
//							gstDataDO.setRate(edo.getGstp());
//							gstDataDO.setTtype(2);
//							gstDataDO.setIgst(new BigDecimal(dataDO.getIgst_amount()));
//							gstDataDO.setCgst(new BigDecimal(dataDO.getCgst_amount()));
//							gstDataDO.setSgst(new BigDecimal(dataDO.getSgst_amount()));
//							float tax = (dataDO.getQuantity() - dataDO.getPre_cyls())
//									* (Float.parseFloat(dataDO.getUnit_rate())
//											- Float.parseFloat(dataDO.getDisc_unit_rate()));
//							gstDataDO.setTaxable_value(BigDecimal.valueOf(tax));
//							gstDataDOList.add(gstDataDO);
//						}
//					}
//				}
//			});
			
			if(ofp_acceptable == -1){
				ofp_acceptable = ((AgencyVO) request.getSession().getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getOffer_price_acceptable();
			}
			
			if(ofp_acceptable == 0){
				
				ccsis.forEach(ccsi -> {
					for (EquipmentDataDO edo : edos) {
						List<COMSalesInvoiceDetailsDO> detailsList = ccsi.getDetailsList();
						for (COMSalesInvoiceDetailsDO dataDO : detailsList) {
							if (edo.getProd_code() == dataDO.getProd_code()) {
								GSTDataDO gstDataDO = new GSTDataDO();
								gstDataDO.setGstType("coms");
								gstDataDO.setRate(edo.getGstp());
								gstDataDO.setTtype(2);
								gstDataDO.setIgst(new BigDecimal(dataDO.getIgst_amount()));
								gstDataDO.setCgst(new BigDecimal(dataDO.getCgst_amount()));
								gstDataDO.setSgst(new BigDecimal(dataDO.getSgst_amount()));
								float tax = (dataDO.getQuantity() - dataDO.getPre_cyls())
											* (Float.parseFloat(dataDO.getUnit_rate())
											- Float.parseFloat(dataDO.getDisc_unit_rate()));
								gstDataDO.setTaxable_value(BigDecimal.valueOf(tax));
								gstDataDOList.add(gstDataDO);
							}
						}
					}
				});
			
				arbsis.forEach(arbsi -> {
					for (ARBDataDO arbdo : arbdos) {
						List<ARBSalesInvoiceDetailsDO> detailsList = arbsi.getDetailsList();
						for (ARBSalesInvoiceDetailsDO dataDO : detailsList) {
							if (arbdo.getId() == dataDO.getProd_code()) {
								GSTDataDO gstDataDO = new GSTDataDO();
								gstDataDO.setGstType("arbs");
								gstDataDO.setRate(arbdo.getGstp());
								gstDataDO.setTtype(2);
								gstDataDO.setIgst(new BigDecimal(dataDO.getIgst_amount()));
								gstDataDO.setCgst(new BigDecimal(dataDO.getCgst_amount()));
								gstDataDO.setSgst(new BigDecimal(dataDO.getSgst_amount()));
								float tax = dataDO.getQuantity() * (Float.parseFloat(dataDO.getUnit_rate())
												- Float.parseFloat(dataDO.getDisc_unit_rate()));
								gstDataDO.setTaxable_value(BigDecimal.valueOf(tax));
								gstDataDOList.add(gstDataDO);
							}
						}
					}
				});
				
				// process nc dbc
				ncdbcs.forEach(ncdbc -> {
					List<NCDBCInvoiceDetailsDO> ncdbcList = ncdbc.getDetailsList();
					for (NCDBCInvoiceDetailsDO detailsDO : ncdbcList) {
						GSTDataDO gstDataDO = new GSTDataDO();
						gstDataDO.setGstType("nc");
						gstDataDO.setRate(detailsDO.getGstp());
						gstDataDO.setTtype(2);
						gstDataDO.setIgst(new BigDecimal("0.00"));
						if (!(("NA").equals(detailsDO.getCgst_amount()))) {
							
							if(detailsDO.getProd_code()>=18 && detailsDO.getProd_code()<=31) {
								for (ServicesGSTData serdo : serdos) {
									if(detailsDO.getProd_code()<=28 && (serdo.getService_product_id()==detailsDO.getProd_code())){
										gstDataDO.setCgst(new BigDecimal(Float.parseFloat((serdo.getGst_percent_applicable()))*Float.parseFloat((detailsDO.getCgst_amount()))));
										gstDataDO.setSgst(new BigDecimal(Float.parseFloat((serdo.getGst_percent_applicable()))*Float.parseFloat((detailsDO.getSgst_amount()))));
										break;
									}else if(detailsDO.getProd_code()>28 && (serdo.getService_product_id()==detailsDO.getProd_code())){
										gstDataDO.setCgst(BigDecimal.valueOf(0.00));
										gstDataDO.setSgst(BigDecimal.valueOf(0.00));
										break;
									}
								}
							}else {
								gstDataDO.setCgst(new BigDecimal(detailsDO.getCgst_amount()));
								gstDataDO.setSgst(new BigDecimal(detailsDO.getSgst_amount()));
							}
						} else {
							gstDataDO.setCgst(BigDecimal.valueOf(0.00));
							gstDataDO.setSgst(BigDecimal.valueOf(0.00));
						}
						float tax = Float.parseFloat(detailsDO.getBasic_price());
						gstDataDO.setTaxable_value(BigDecimal.valueOf(tax));
						gstDataDOList.add(gstDataDO);
					}
				});
				
			}else if(ofp_acceptable == 1) {
				
				ccsis.forEach(ccsi -> {
					for (EquipmentDataDO edo : edos) {
						List<COMSalesInvoiceDetailsDO> detailsList = ccsi.getDetailsList();
						for (COMSalesInvoiceDetailsDO dataDO : detailsList) {
							if (edo.getProd_code() == dataDO.getProd_code()) {
								GSTDataDO gstDataDO = new GSTDataDO();
								gstDataDO.setGstType("coms");
								gstDataDO.setRate(edo.getGstp());
								gstDataDO.setTtype(2);
								if(ccsi.getSr_no().contains("SI")) {
									gstDataDO.setIgst(new BigDecimal(dataDO.getIgst_amount()));
									gstDataDO.setCgst(new BigDecimal(dataDO.getCgst_amount()));
									gstDataDO.setSgst(new BigDecimal(dataDO.getSgst_amount()));
									float tax = (dataDO.getQuantity() - dataDO.getPre_cyls())
													* (Float.parseFloat(dataDO.getUnit_rate())
													- Float.parseFloat(dataDO.getDisc_unit_rate()));
									gstDataDO.setTaxable_value(BigDecimal.valueOf(tax));
								}else if(ccsi.getSr_no().contains("CS")) {
									gstDataDO.setIgst(new BigDecimal(dataDO.getOp_igst_amt()));
									gstDataDO.setCgst(new BigDecimal(dataDO.getOp_cgst_amt()));
									gstDataDO.setSgst(new BigDecimal(dataDO.getOp_sgst_amt()));
									gstDataDO.setTaxable_value(new BigDecimal(dataDO.getOp_taxable_amt()));
								}
								gstDataDOList.add(gstDataDO);
							}
						}
					}
				});
				
				
				arbsis.forEach(arbsi -> {
					for (ARBDataDO arbdo : arbdos) {
						List<ARBSalesInvoiceDetailsDO> detailsList = arbsi.getDetailsList();
						for (ARBSalesInvoiceDetailsDO dataDO : detailsList) {
							if (arbdo.getId() == dataDO.getProd_code()) {
								GSTDataDO gstDataDO = new GSTDataDO();
								gstDataDO.setGstType("arbs");
								gstDataDO.setRate(arbdo.getGstp());
								gstDataDO.setTtype(2);
								if(arbsi.getSr_no().contains("SI")) {
									gstDataDO.setIgst(new BigDecimal(dataDO.getIgst_amount()));
									gstDataDO.setCgst(new BigDecimal(dataDO.getCgst_amount()));
									gstDataDO.setSgst(new BigDecimal(dataDO.getSgst_amount()));
									float tax = dataDO.getQuantity() * (Float.parseFloat(dataDO.getUnit_rate())
													- Float.parseFloat(dataDO.getDisc_unit_rate()));
									gstDataDO.setTaxable_value(BigDecimal.valueOf(tax));
								}else if(arbsi.getSr_no().contains("CS")) {
									gstDataDO.setIgst(new BigDecimal(dataDO.getOp_igst_amt()));
									gstDataDO.setCgst(new BigDecimal(dataDO.getOp_cgst_amt()));
									gstDataDO.setSgst(new BigDecimal(dataDO.getOp_sgst_amt()));
									gstDataDO.setTaxable_value(new BigDecimal(dataDO.getOp_taxable_amt()));
								}
								gstDataDOList.add(gstDataDO);
							}
						}
					}
				});
				
				// process nc dbc
				ncdbcs.forEach(ncdbc -> {
					List<NCDBCInvoiceDetailsDO> ncdbcList = ncdbc.getDetailsList();
					for (NCDBCInvoiceDetailsDO detailsDO : ncdbcList) {
						GSTDataDO gstDataDO = new GSTDataDO();
						gstDataDO.setGstType("nc");
						gstDataDO.setRate(detailsDO.getGstp());
						gstDataDO.setTtype(2);
						gstDataDO.setIgst(new BigDecimal("0.00"));
						
						float tax = Float.parseFloat(detailsDO.getBasic_price());
						if (!(("NA").equals(detailsDO.getCgst_amount()))) {

							// start code for services gst applicable % 30/04/2019 end
							if(detailsDO.getProd_code()>=18 && detailsDO.getProd_code()<=31) {
								for (ServicesGSTData serdo : serdos) {
									if(detailsDO.getProd_code()<=28 && (serdo.getService_product_id()==detailsDO.getProd_code())){
										gstDataDO.setCgst(new BigDecimal(Float.parseFloat((serdo.getGst_percent_applicable()))*Float.parseFloat((detailsDO.getCgst_amount()))));
										gstDataDO.setSgst(new BigDecimal(Float.parseFloat((serdo.getGst_percent_applicable()))*Float.parseFloat((detailsDO.getSgst_amount()))));
										break;
									}else if(detailsDO.getProd_code()>28 && (serdo.getService_product_id()==detailsDO.getProd_code())){
										gstDataDO.setCgst(BigDecimal.valueOf(0.00));
										gstDataDO.setSgst(BigDecimal.valueOf(0.00));
										break;
									}
								}
							}else if(detailsDO.getProd_code() <= 3){
								// Remove this else block when you need offer price for Dom also.
								gstDataDO.setCgst(new BigDecimal(detailsDO.getCgst_amount()));
								gstDataDO.setSgst(new BigDecimal(detailsDO.getSgst_amount()));
							}else {
								// Products other than services and Dom.
								gstDataDO.setCgst(new BigDecimal(detailsDO.getOp_cgst_amt()));
								gstDataDO.setSgst(new BigDecimal(detailsDO.getOp_sgst_amt()));
								tax = Float.parseFloat(detailsDO.getOp_taxable_amt());
							}
							// end code for services gst applicable % 30/04/2019 end
						} else {
							gstDataDO.setCgst(BigDecimal.valueOf(0.00));
							gstDataDO.setSgst(BigDecimal.valueOf(0.00));
						}
//						float tax = Float.parseFloat(detailsDO.getBasic_price());
//						float tax = Float.parseFloat(detailsDO.getOp_taxable_amt());
						gstDataDO.setTaxable_value(BigDecimal.valueOf(tax));
						gstDataDOList.add(gstDataDO);
					}
				});
				
			}
			

			// process rcs
			rcs.forEach(rc -> {
				GSTDataDO gstDataDO = new GSTDataDO();
				gstDataDO.setRate("18");
				gstDataDO.setGstType("rcs");
				gstDataDO.setTtype(2);
				gstDataDO.setIgst(new BigDecimal("0.00"));
				gstDataDO.setCgst(new BigDecimal(rc.getScgst_amount()));
				gstDataDO.setSgst(new BigDecimal(rc.getSsgst_amount()));
				float tax = Float.parseFloat(rc.getAdmin_charges()) + Float.parseFloat(rc.getDgcc_amount());
				gstDataDO.setTaxable_value(BigDecimal.valueOf(tax));
				
				// starts code for services gst applicable % 30/04/2019
				if(rc.getRc_charges_type()>=18 && rc.getRc_charges_type()<=31) {
					for (ServicesGSTData serdo : serdos) {
						if(rc.getRc_charges_type()<=28 && (serdo.getService_product_id()==rc.getRc_charges_type())){
							gstDataDO.setCgst(new BigDecimal(Float.parseFloat((serdo.getGst_percent_applicable()))*Float.parseFloat((rc.getScgst_amount()))));
							gstDataDO.setSgst(new BigDecimal(Float.parseFloat((serdo.getGst_percent_applicable()))*Float.parseFloat((rc.getSsgst_amount()))));
							break;
						}else if(rc.getRc_charges_type()>28 && (serdo.getService_product_id()==rc.getRc_charges_type())){
							gstDataDO.setCgst(new BigDecimal(0.00));
							gstDataDO.setSgst(new BigDecimal(0.00));
							break;
						}
					}
				}
				// end code for services gst applicable % 30/04/2019

				gstDataDOList.add(gstDataDO);

				MathContext mc = new MathContext(2);
				BigDecimal cgstAmt = new BigDecimal(rc.getCgst_amount());
				BigDecimal sgstAmt = new BigDecimal(rc.getSgst_amount());
//				BigDecimal cgstAmt = new BigDecimal(rc.getOp_cgst_amt()); // For Offer Price. Uncomment These two when you need ofp for Dom
//				BigDecimal sgstAmt = new BigDecimal(rc.getOp_sgst_amt());
				BigDecimal scgstAmt = new BigDecimal(rc.getScgst_amount());
				BigDecimal ssgstAmt = new BigDecimal(rc.getSsgst_amount());
				GSTDataDO gstCDataDO = new GSTDataDO();
				gstCDataDO.setRate("5");
				gstCDataDO.setGstType("rc");
				gstCDataDO.setTtype(2);
				gstCDataDO.setIgst(new BigDecimal("0.00"));
				gstCDataDO.setCgst(cgstAmt.subtract(scgstAmt, mc));
				gstCDataDO.setSgst(sgstAmt.subtract(ssgstAmt, mc));
				float tax2 = Float.parseFloat(rc.getRc_amount()) - Float.parseFloat(rc.getCyl_deposit())
						- Float.parseFloat(rc.getReg_deposit()) - Float.parseFloat(rc.getIgst_amount())
						- Float.parseFloat(rc.getCgst_amount()) - Float.parseFloat(rc.getSgst_amount())
						- Float.parseFloat(rc.getAdmin_charges()) - Float.parseFloat(rc.getDgcc_amount());
				gstCDataDO.setTaxable_value(BigDecimal.valueOf(tax2));
//				gstCDataDO.setTaxable_value(new BigDecimal(rc.getOp_taxable_amt()));
				gstDataDOList.add(gstCDataDO);
			});

			// process tvs
			tvs.forEach(tv -> {
				GSTDataDO gstDataDO = new GSTDataDO();
				gstDataDO.setRate("18");
				gstDataDO.setGstType("tvs");
				gstDataDO.setTtype(2);
				gstDataDO.setIgst(new BigDecimal("0.00"));

				// start code for services gst applicable % 30/04/2019 end
				if(tv.getTv_charges_type()>=18 && tv.getTv_charges_type()<=31) {
					for (ServicesGSTData serdo : serdos) {
						if(tv.getTv_charges_type()<=28 && (serdo.getService_product_id()==tv.getTv_charges_type())){
							gstDataDO.setCgst(new BigDecimal(Float.parseFloat((serdo.getGst_percent_applicable()))*Float.parseFloat((tv.getCgst_amount()))));
							gstDataDO.setSgst(new BigDecimal(Float.parseFloat((serdo.getGst_percent_applicable()))*Float.parseFloat((tv.getSgst_amount()))));
							break;
						}else if(tv.getTv_charges_type()>28 && (serdo.getService_product_id()==tv.getTv_charges_type())){
							gstDataDO.setCgst(new BigDecimal(0.00));
							gstDataDO.setSgst(new BigDecimal(0.00));
							break;
						}
					}
				}else{
					gstDataDO.setCgst(new BigDecimal(tv.getCgst_amount()));
					gstDataDO.setSgst(new BigDecimal(tv.getSgst_amount()));
				}
				// end code for services gst applicable % 30/04/2019 end

				String tax = tv.getAdmin_charges();
				gstDataDO.setTaxable_value(new BigDecimal(tax));
				gstDataDOList.add(gstDataDO);
			});

			// process cns
			cns.forEach(cn -> {
				GSTDataDO gstDataDO = new GSTDataDO();
				gstDataDO.setGstType("cns");
				gstDataDO.setRate(cn.getGstp());
				if (cn.getCredit_note_type() == 1)
					gstDataDO.setTtype(1);
				else if (cn.getCredit_note_type() == 2)
					gstDataDO.setTtype(2);
				gstDataDO.setIgst(new BigDecimal(cn.getIgst_amount()));
				gstDataDO.setCgst(new BigDecimal(cn.getCgst_amount()));
				gstDataDO.setSgst(new BigDecimal(cn.getSgst_amount()));
				gstDataDO.setTaxable_value(new BigDecimal(cn.getTaxable_amount()));
				gstDataDOList.add(gstDataDO);
			});

			// process dns
			dns.forEach(dn -> {
				GSTDataDO gstDataDO = new GSTDataDO();
				gstDataDO.setGstType("dns");
				gstDataDO.setRate(dn.getGstp());
				if (dn.getDebit_note_type() == 1)
					gstDataDO.setTtype(1);
				else if (dn.getDebit_note_type() == 2)
					gstDataDO.setTtype(2);
				gstDataDO.setIgst(new BigDecimal(dn.getIgst_amount()));
				gstDataDO.setCgst(new BigDecimal(dn.getCgst_amount()));
				gstDataDO.setSgst(new BigDecimal(dn.getSgst_amount()));
				gstDataDO.setTaxable_value(new BigDecimal(dn.getTaxable_amount()));
				gstDataDOList.add(gstDataDO);
			});

			prs.forEach(prsi -> {
				for (EquipmentDataDO edo : edos) {
					List<PurchaseReturnDetailsDO> detailsList = prsi.getDetailsList();
					for (PurchaseReturnDetailsDO dataDO : detailsList) {
						if (edo.getProd_code() == dataDO.getProd_code()) {
							GSTDataDO gstDataDO = new GSTDataDO();
							gstDataDO.setGstType("prcyls");
							gstDataDO.setRate(edo.getGstp());
							gstDataDO.setTtype(2);
							gstDataDO.setIgst(new BigDecimal(dataDO.getIgst_amount()));
							gstDataDO.setCgst(new BigDecimal(dataDO.getCgst_amount()));
							gstDataDO.setSgst(new BigDecimal(dataDO.getSgst_amount()));
							float tax = dataDO.getRtn_qty() * (Float.parseFloat(dataDO.getUnit_rate()));
							gstDataDO.setTaxable_value(BigDecimal.valueOf(tax));
							gstDataDOList.add(gstDataDO);
						}
					}
				}

				for (ARBDataDO arbdo : arbdos) {
					List<PurchaseReturnDetailsDO> detailsList = prsi.getDetailsList();
					for (PurchaseReturnDetailsDO dataDO : detailsList) {
						if (arbdo.getId() == dataDO.getProd_code()) {
							GSTDataDO gstDataDO = new GSTDataDO();
							gstDataDO.setGstType("prarbs");
							gstDataDO.setRate(arbdo.getGstp());
							gstDataDO.setTtype(2);
							gstDataDO.setIgst(new BigDecimal(dataDO.getIgst_amount()));
							gstDataDO.setCgst(new BigDecimal(dataDO.getCgst_amount()));
							gstDataDO.setSgst(new BigDecimal(dataDO.getSgst_amount()));
							float tax = dataDO.getRtn_qty() * (Float.parseFloat(dataDO.getUnit_rate()));
							gstDataDO.setTaxable_value(BigDecimal.valueOf(tax));
							gstDataDOList.add(gstDataDO);
						}
					}
				}
			});
			srs.forEach(srsi -> {
				for (EquipmentDataDO edo : edos) {
					List<SalesReturnDetailsDO> detailsList = srsi.getDetailsList();
					for (SalesReturnDetailsDO dataDO : detailsList) {
						if (edo.getProd_code() == dataDO.getProd_code()) {
							GSTDataDO gstDataDO = new GSTDataDO();
							gstDataDO.setGstType("srcyls");
							gstDataDO.setRate(edo.getGstp());
							gstDataDO.setTtype(1);
							gstDataDO.setIgst(new BigDecimal(dataDO.getIgst_amount()));
							gstDataDO.setCgst(new BigDecimal(dataDO.getCgst_amount()));
							gstDataDO.setSgst(new BigDecimal(dataDO.getSgst_amount()));

							
							float tax = dataDO.getRtn_qty() * (Float.parseFloat(dataDO.getUnit_rate()));
							gstDataDO.setTaxable_value(BigDecimal.valueOf(tax));
							gstDataDOList.add(gstDataDO);
						}
					}
				}
				for (ARBDataDO arbdo : arbdos) {
					List<SalesReturnDetailsDO> detailsList = srsi.getDetailsList();
					for (SalesReturnDetailsDO dataDO : detailsList) {
						if (arbdo.getId() == dataDO.getProd_code()) {
							GSTDataDO gstDataDO = new GSTDataDO();
							gstDataDO.setGstType("srarbs");
							gstDataDO.setRate(arbdo.getGstp());
							gstDataDO.setTtype(1);
							gstDataDO.setIgst(new BigDecimal(dataDO.getIgst_amount()));
							gstDataDO.setCgst(new BigDecimal(dataDO.getCgst_amount()));
							gstDataDO.setSgst(new BigDecimal(dataDO.getSgst_amount()));
							float tax = dataDO.getRtn_qty() * (Float.parseFloat(dataDO.getUnit_rate()));
							gstDataDO.setTaxable_value(BigDecimal.valueOf(tax));
							gstDataDOList.add(gstDataDO);
						}
					}
				}
			});
			Integer gstdata_size = gstDataDOList.size();
			request.setAttribute("gst_data", new Gson().toJson(gstDataDOList));
			request.setAttribute("gstdata_size", gstdata_size.toString());
			request.setAttribute(ApplicationConstants.paramKeys.GST_PAYMENTS_DATA.getValue(), new Gson().toJson(gps));
			request.setAttribute("rder", "1");
			request.setAttribute(ApplicationConstants.paramKeys.GST_PAYMENT_DETAILS.getValue(),(new Gson().toJson(rdf.getGSTPaymentDetailsData(agencyId))));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.FETCHGSTREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.MONTH.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.toString() + ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.STATUS.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, sMonth[0], sYear[0],
					ApplicationConstants.SUCCESS_STATUS_STRING);
		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			logger.info(ApplicationConstants.LogMessageKeys.FETCHGSTREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.MONTH.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.toString() + ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, sMonth[0], sYear[0], be);

		} catch (ParseException e) {
			e.printStackTrace();
			logger.info(ApplicationConstants.LogMessageKeys.FETCHGSTREPORT.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.MONTH.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.toString() + ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.PARSEEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, sMonth[0], sYear[0], e);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void saveAgencyPaymentsData(HttpServletRequest request, HttpServletResponse response) {

		MessageObject msgObj = new MessageObject(3002, "SAVE STATUTORY DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.getValue()));
			Map<String, String[]> requestParams = request.getParameterMap();
			sMonth = requestParams.get("sm");
			sYear = requestParams.get("sy");
			logger.info(ApplicationConstants.LogMessageKeys.SAVEAGENCYSTATUTORYDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			GSTReportsDataFactory rdf = new GSTReportsDataFactory();
			fetchGSTReport(request, response);
			request.setAttribute(ApplicationConstants.paramKeys.GST_PAYMENTS_DATA.getValue(),
					new Gson().toJson(rdf.saveAgencyPaymentsData(requestParams, agencyId)));
			request.setAttribute("rder", "2");
			request.setAttribute(ApplicationConstants.paramKeys.GST_PAYMENT_DETAILS.getValue(),
					new Gson().toJson(rdf.getGSTPaymentDetailsData(agencyId)));
			
			
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.SAVEAGENCYSTATUTORYDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.SAVEAGENCYSTATUTORYDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		} catch (ParseException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	public void setOffAgencyPaymentsData(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(3002, "SET-OFF GST PAYMENT DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.toString()));
			Map<String, String[]> requestParams = request.getParameterMap();
			sMonth = requestParams.get("sm");
			sYear = requestParams.get("sy");
			logger.info(ApplicationConstants.LogMessageKeys.SETOFFGSTPAYMENTSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			GSTReportsDataFactory rdf = new GSTReportsDataFactory();
			request.setAttribute(ApplicationConstants.paramKeys.GST_PAYMENTS_DATA.getValue(),
					new Gson().toJson(rdf.setOffAgencyPaymentsData(requestParams, agencyId)));
			request.setAttribute("rder", "2");
			request.setAttribute(ApplicationConstants.paramKeys.GST_PAYMENT_DETAILS.getValue(),
					new Gson().toJson(rdf.getGSTPaymentDetailsData(agencyId)));
			
			
			
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.SETOFFGSTPAYMENTSDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		}catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			be.printStackTrace();
			logger.info(ApplicationConstants.LogMessageKeys.SETOFFGSTPAYMENTSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
	}

	
	public void payOffAgencyPaymentDetailsData(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(3002, "PAY-OFF GST PAYMENT DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = Long.parseLong(request.getParameter(ApplicationConstants.paramKeys.AGENCY_ID.toString()));
			Map<String, String[]> requestParams = request.getParameterMap();
			sMonth = requestParams.get("sm");
			sYear = requestParams.get("sy");
			logger.info(ApplicationConstants.LogMessageKeys.PAYOFFGSTPAYMENTSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			GSTReportsDataFactory rdf = new GSTReportsDataFactory();
			request.setAttribute(ApplicationConstants.paramKeys.GST_PAYMENTS_DATA.getValue(),
					(new Gson().toJson(rdf.payOffGSTPaymentsData(requestParams, agencyId))));
			request.setAttribute("rder", "3");
			request.setAttribute(ApplicationConstants.paramKeys.GST_PAYMENT_DETAILS.getValue(),
					new Gson().toJson(rdf.getGSTPaymentDetailsData(agencyId)));
			
			
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
			logger.info(
					ApplicationConstants.LogMessageKeys.PAYOFFGSTPAYMENTSDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			be.printStackTrace();
			logger.info(ApplicationConstants.LogMessageKeys.PAYOFFGSTPAYMENTSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);		
	}
}
