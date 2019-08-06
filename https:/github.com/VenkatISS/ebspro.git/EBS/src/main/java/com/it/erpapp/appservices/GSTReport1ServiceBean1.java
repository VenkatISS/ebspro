package com.it.erpapp.appservices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.it.erpapp.commons.ApplicationConstants;
import com.it.erpapp.framework.CacheFactory;
import com.it.erpapp.framework.GSTReports1DataFactory;
import com.it.erpapp.framework.GSTReportsDataFactory;
import com.it.erpapp.framework.MasterDataFactory;
import com.it.erpapp.framework.PPMasterDataFactory;
import com.it.erpapp.framework.model.pps.ARBDataDO;
import com.it.erpapp.framework.model.pps.EquipmentDataDO;
import com.it.erpapp.framework.model.pps.ServicesDataDO;
import com.it.erpapp.framework.model.transactions.GSTR1CDNDataDO;
import com.it.erpapp.framework.model.transactions.GSTR1DataDO;
import com.it.erpapp.framework.model.transactions.GSTR1DocsDataDO;
import com.it.erpapp.framework.model.transactions.GSTR1HSNDataDO;
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
import com.it.erpapp.framework.model.transactions.sales.TVDataDO;
import com.it.erpapp.framework.model.vos.AgencyVO;
import com.it.erpapp.framework.model.vos.GSTR1ProductDataVO;
import com.it.erpapp.response.MessageObject;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class GSTReport1ServiceBean1 {

	Logger logger = LoggerFactory.getLogger(GSTReport1ServiceBean1.class);
	long agencyId;
	String[] sMonth;
	String[] sYear;

	public void fetchGSTReport1Data(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(7033, "FETCH GSTR1 DATA", ApplicationConstants.ERROR_STATUS_STRING);
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			Map<String, String[]> requestParams = request.getParameterMap();

			sMonth = requestParams.get("sm");
			sYear = requestParams.get("sy");

			logger.info(ApplicationConstants.LogMessageKeys.FETCHGSTREPORT1DATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.MONTH.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.paramKeys.YEAR.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, sMonth[0], sYear[0]);

			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			List<EquipmentDataDO> edos = ppmdf.getAgencyAllEquipmentData(agencyId);
			List<ARBDataDO> arbdos = ppmdf.getAgencyAllARBData(agencyId);
			List<ServicesDataDO> serdos = ppmdf.getAgencyServicesData(agencyId);
			MasterDataFactory mdf = new MasterDataFactory();

			request.setAttribute("service_types_list", new Gson().toJson(new CacheFactory().getServiceTypesData()));
			request.setAttribute("cvo_data", new Gson().toJson(mdf.getAgencyAllCVOData(agencyId)));
			request.setAttribute("cylinder_types_list", new Gson().toJson(new CacheFactory().getCylinderTypesData()));
			request.setAttribute("arb_data", new Gson().toJson(ppmdf.getAgencyAllARBData(agencyId)));
			request.setAttribute("staff_data", (new Gson().toJson(mdf.getAgencyAllStaffData(agencyId))));
			String[] fromdate = requestParams.get("fd");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long fdl = (sdf.parse(fromdate[0])).getTime();
			request.setAttribute("sd", String.valueOf(fdl));
			GSTReports1DataFactory r1df = new GSTReports1DataFactory();
			GSTReportsDataFactory rdf = new GSTReportsDataFactory();

			List<DOMSalesInvoiceDO> dsis = r1df.fetchAgencyGSTR1B2BDOMCSalesData(requestParams, agencyId);
			List<COMSalesInvoiceDO> csis = r1df.fetchAgencyGSTR1B2BCOMCSalesData(requestParams, agencyId);
			List<ARBSalesInvoiceDO> arbsis = r1df.fetchAgencyGSTR1B2BARBSalesData(requestParams, agencyId);
			List<PurchaseReturnDO> prsis = r1df.fetchAgencyGSTR1B2BPurReturnsData(requestParams, agencyId);

			List<DOMSalesInvoiceDO> dcsis2 = r1df.fetchAgencyGSTR1B2CLDOMCSalesData(requestParams, agencyId);
			List<COMSalesInvoiceDO> ccsis2 = r1df.fetchAgencyGSTR1B2CLCOMCSalesData(requestParams, agencyId);
			List<ARBSalesInvoiceDO> arbsis2 = r1df.fetchAgencyGSTR1B2CLARBSalesData(requestParams, agencyId);
			List<PurchaseReturnDO> prsis2 = r1df.fetchAgencyGSTR1B2CLPurReturnsData(requestParams, agencyId);

			List<DOMSalesInvoiceDO> dcsis3 = r1df.fetchAgencyGSTR1B2CSDOMCSalesData(requestParams, agencyId);
			List<COMSalesInvoiceDO> ccsis3 = r1df.fetchAgencyGSTR1B2CSCOMCSalesData(requestParams, agencyId);
			List<ARBSalesInvoiceDO> arbsis3 = r1df.fetchAgencyGSTR1B2CSARBSalesData(requestParams, agencyId);
			List<PurchaseReturnDO> prsis3 = r1df.fetchAgencyGSTR1B2CSPurReturnsData(requestParams, agencyId);

			List<NCDBCInvoiceDO> ncdbcs = rdf.fetchAgencyGSTNCDBCSaleReport(requestParams, agencyId);
			List<RCDataDO> rcs = rdf.fetchAgencyGSTRCSaleReport(requestParams, agencyId);
			List<TVDataDO> tvs = rdf.fetchAgencyGSTTVSaleReport(requestParams, agencyId);

			List<GSTR1DataDO> b2bList = fetchGSTR1B2BData(dsis, csis, arbsis, edos, arbdos, prsis);
			List<GSTR1DataDO> b2clList = fetchGSTR1B2CLData(dcsis2, ccsis2, arbsis2, edos, arbdos, prsis2);
			List<GSTR1DataDO> b2csList = fetchGSTR1B2CSData(dcsis3, ccsis3, arbsis3, edos, arbdos, prsis3, serdos,
					ncdbcs, rcs, tvs);
			List<GSTR1DataDO> b2csListt = fetchGSTR1B2CSData(b2csList);
			List<GSTR1HSNDataDO> hsnList = fetchGSTR1HSNData(request, response);
			GSTR1DocsDataDO sdocsList = r1df.fetchAgencyGSTR1B2BSalesDocsData(requestParams, agencyId);
			GSTR1DocsDataDO scdocsList = r1df.fetchAgencyGSTR1B2CSalesDocsData(requestParams, agencyId);
			GSTR1DocsDataDO prdocsList = r1df.fetchAgencyGSTR1PRDocsData(requestParams, agencyId);
			GSTR1DocsDataDO cndocsList = r1df.fetchAgencyGSTR1CNDocsData(requestParams, agencyId);
			GSTR1DocsDataDO dndocsList = r1df.fetchAgencyGSTR1DNDocsData(requestParams, agencyId);
			List<GSTR1DocsDataDO> gstDataDODocsList = fetchGSTR1DocsData(sdocsList, scdocsList, prdocsList, cndocsList,
					dndocsList);

			List<GSTR1CDNDataDO> cnrList = r1df.fetchAgencyGSTR1CNRData(requestParams, agencyId);
			List<GSTR1CDNDataDO> dnrList = r1df.fetchAgencyGSTR1DNRData(requestParams, agencyId);
			List<GSTR1CDNDataDO> cnurList = r1df.fetchAgencyGSTR1CNURData(requestParams, agencyId);
			List<GSTR1CDNDataDO> dnurList = r1df.fetchAgencyGSTR1DNURData(requestParams, agencyId);
			List<GSTR1CDNDataDO> cdnrList = fetchGSTR1CDNData(cnrList, dnrList);
			List<GSTR1CDNDataDO> cdnurList = fetchGSTR1CDNData(cnurList, dnurList);

			Collections.sort(b2bList,
					Comparator.comparingLong(GSTR1DataDO::getInv_date).thenComparing(GSTR1DataDO::getCreated_date));
			Collections.sort(b2clList, Comparator.comparingLong(GSTR1DataDO::getInv_date));
			Collections.sort(b2csList, Comparator.comparingLong(GSTR1DataDO::getInv_date));

			request.setAttribute("gstr1b2b_data", new Gson().toJson(b2bList));
			request.setAttribute("gstr1b2cl_data", new Gson().toJson(b2clList));
			request.setAttribute("gstr1b2cs_data", new Gson().toJson(b2csListt));
			request.setAttribute("gstr1hsn_data", new Gson().toJson(hsnList));
			request.setAttribute("gstr1docs_data", new Gson().toJson(gstDataDODocsList));
			request.setAttribute("gstr1cdnr_data", new Gson().toJson(cdnrList));
			request.setAttribute("gstr1cdnur_data", new Gson().toJson(cdnurList));
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(ApplicationConstants.LogMessageKeys.FETCHGSTREPORT1DATA.getValue()
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

			logger.info(ApplicationConstants.LogMessageKeys.FETCHGSTREPORT1DATA.getValue()
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

			logger.info(ApplicationConstants.LogMessageKeys.FETCHGSTREPORT1DATA.getValue()
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

	public List<GSTR1DataDO> fetchGSTR1B2BData(List<DOMSalesInvoiceDO> dcsis, List<COMSalesInvoiceDO> ccsis,
			List<ARBSalesInvoiceDO> arbsis, List<EquipmentDataDO> edos, List<ARBDataDO> arbdos,
			List<PurchaseReturnDO> prsis) {

		List<GSTR1DataDO> gstDataDOList = new ArrayList<>();

		dcsis.forEach(dcsi -> {
			for (EquipmentDataDO edo : edos) {
				List<DOMSalesInvoiceDetailsDO> detailsList = dcsi.getDetailsList();
				for (DOMSalesInvoiceDetailsDO dataDO : detailsList) {
					if (edo.getProd_code() == dataDO.getProd_code()) {
						GSTR1DataDO gstDataDO = new GSTR1DataDO();
						gstDataDO.setInv_date(dcsi.getSi_date());
						gstDataDO.setInv_amount(dataDO.getSale_amount());
						gstDataDO.setCustomer_id(dcsi.getCustomer_id());
						gstDataDO.setGstp(edo.getGstp());
						gstDataDO.setInv_no(dcsi.getSr_no());
						gstDataDO.setQuantity(dataDO.getQuantity() - dataDO.getPsv_cyls());
						gstDataDO.setUnit_rate(dataDO.getUnit_rate());
						float tax = (dataDO.getQuantity() - dataDO.getPsv_cyls())
								* (Float.parseFloat(dataDO.getUnit_rate())
										- Float.parseFloat(dataDO.getDisc_unit_rate()));
						String taxable = Float.toString(tax);
						gstDataDO.setTaxable_value(taxable);
						gstDataDO.setInv_type(1);
						gstDataDO.setCreated_date(dcsi.getCreated_date());
						gstDataDOList.add(gstDataDO);
					}
				}
			}
		});

		ccsis.forEach(ccsi -> {
			for (EquipmentDataDO edo : edos) {
				List<COMSalesInvoiceDetailsDO> detailsList = ccsi.getDetailsList();
				for (COMSalesInvoiceDetailsDO dataDO : detailsList) {
					if (edo.getProd_code() == dataDO.getProd_code()) {
						GSTR1DataDO gstDataDO = new GSTR1DataDO();
						gstDataDO.setGstp(edo.getGstp());
						gstDataDO.setCustomer_id(ccsi.getCustomer_id());
						gstDataDO.setInv_no(ccsi.getSr_no());
						gstDataDO.setInv_date(ccsi.getSi_date());
						gstDataDO.setInv_amount(dataDO.getSale_amount());
						gstDataDO.setQuantity(dataDO.getQuantity() - dataDO.getPre_cyls());
						gstDataDO.setUnit_rate(dataDO.getUnit_rate());
						float tax = (dataDO.getQuantity() - dataDO.getPre_cyls())
								* (Float.parseFloat(dataDO.getUnit_rate())
										- Float.parseFloat(dataDO.getDisc_unit_rate()));
						String taxable = Float.toString(tax);
						gstDataDO.setTaxable_value(taxable);
						gstDataDO.setInv_type(1);
						gstDataDO.setCreated_date(ccsi.getCreated_date());
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
						GSTR1DataDO gstDataDO = new GSTR1DataDO();
						gstDataDO.setGstp(arbdo.getGstp());
						gstDataDO.setCustomer_id(arbsi.getCustomer_id());
						gstDataDO.setInv_no(arbsi.getSr_no());
						gstDataDO.setInv_date(arbsi.getSi_date());
						gstDataDO.setInv_amount(dataDO.getSale_amount());
						gstDataDO.setQuantity(dataDO.getQuantity());
						gstDataDO.setUnit_rate(dataDO.getUnit_rate());
						float tax = dataDO.getQuantity() * (Float.parseFloat(dataDO.getUnit_rate())
								- Float.parseFloat(dataDO.getDisc_unit_rate()));
						String taxable = Float.toString(tax);
						gstDataDO.setTaxable_value(taxable);
						gstDataDO.setInv_type(1);
						gstDataDO.setCreated_date(arbsi.getCreated_date());
						gstDataDOList.add(gstDataDO);
					}
				}
			}
		});

		prsis.forEach(prsi -> {
			for (EquipmentDataDO edo : edos) {
				List<PurchaseReturnDetailsDO> detailsList = prsi.getDetailsList();
				for (PurchaseReturnDetailsDO dataDO : detailsList) {
					if (edo.getProd_code() == dataDO.getProd_code()) {
						GSTR1DataDO gstDataDO = new GSTR1DataDO();
						gstDataDO.setInv_date(prsi.getInv_date());
						gstDataDO.setInv_amount(dataDO.getAmount());
						gstDataDO.setCustomer_id(prsi.getCvo_id());
						gstDataDO.setGstp(edo.getGstp());
						gstDataDO.setInv_no(prsi.getSid());
						gstDataDO.setQuantity(dataDO.getRtn_qty());
						gstDataDO.setUnit_rate(dataDO.getUnit_rate());
						float tax = dataDO.getRtn_qty() * (Float.parseFloat(dataDO.getUnit_rate()));
						String taxable = Float.toString(tax);
						gstDataDO.setTaxable_value(taxable);
						gstDataDO.setInv_type(2);
						gstDataDO.setCreated_date(prsi.getCreated_date());
						gstDataDOList.add(gstDataDO);
					}
				}
			}
			for (ARBDataDO arbdo : arbdos) {
				List<PurchaseReturnDetailsDO> detailsList = prsi.getDetailsList();
				for (PurchaseReturnDetailsDO dataDO : detailsList) {
					if (arbdo.getId() == dataDO.getProd_code()) {
						GSTR1DataDO gstDataDO = new GSTR1DataDO();
						gstDataDO.setGstp(arbdo.getGstp());
						gstDataDO.setCustomer_id(prsi.getCvo_id());
						gstDataDO.setInv_no(prsi.getSid());
						gstDataDO.setInv_date(prsi.getInv_date());
						gstDataDO.setInv_amount(dataDO.getAmount());
						gstDataDO.setQuantity(dataDO.getRtn_qty());
						gstDataDO.setUnit_rate(dataDO.getUnit_rate());
						float tax = (dataDO.getRtn_qty()) * (Float.parseFloat(dataDO.getUnit_rate()));
						String taxable = Float.toString(tax);
						gstDataDO.setTaxable_value(taxable);
						gstDataDO.setInv_type(2);
						gstDataDO.setCreated_date(prsi.getCreated_date());
						gstDataDOList.add(gstDataDO);
					}
				}
			}
		});

		return gstDataDOList;
	}

	public List<GSTR1DataDO> fetchGSTR1B2CLData(List<DOMSalesInvoiceDO> dcsis, List<COMSalesInvoiceDO> ccsis,
			List<ARBSalesInvoiceDO> arbsis, List<EquipmentDataDO> edos, List<ARBDataDO> arbdos,
			List<PurchaseReturnDO> prsis) {

		List<GSTR1DataDO> gstDataDOList = new ArrayList<>();

		dcsis.forEach(dcsi -> {
			for (EquipmentDataDO edo : edos) {
				List<DOMSalesInvoiceDetailsDO> detailsList = dcsi.getDetailsList();
				for (DOMSalesInvoiceDetailsDO dataDO : detailsList) {
					if (edo.getProd_code() == dataDO.getProd_code()) {
						GSTR1DataDO gstDataDO = new GSTR1DataDO();
						gstDataDO.setInv_date(dcsi.getSi_date());
						gstDataDO.setInv_amount(dataDO.getSale_amount());
						gstDataDO.setCustomer_id(dcsi.getCustomer_id());
						gstDataDO.setGstp(edo.getGstp());
						gstDataDO.setInv_no(dcsi.getSr_no());
						gstDataDO.setQuantity(dataDO.getQuantity() - dataDO.getPsv_cyls());
						gstDataDO.setUnit_rate(dataDO.getUnit_rate());
						float tax = (dataDO.getQuantity() - dataDO.getPsv_cyls())
								* (Float.parseFloat(dataDO.getUnit_rate())
										- Float.parseFloat(dataDO.getDisc_unit_rate()));
						String taxable = Float.toString(tax);
						gstDataDO.setTaxable_value(taxable);
						gstDataDO.setInv_type(1);
						gstDataDOList.add(gstDataDO);
					}
				}
			}
		});

		ccsis.forEach(ccsi -> {
			for (EquipmentDataDO edo : edos) {
				List<COMSalesInvoiceDetailsDO> detailsList = ccsi.getDetailsList();
				for (COMSalesInvoiceDetailsDO dataDO : detailsList) {
					if (edo.getProd_code() == dataDO.getProd_code()) {
						GSTR1DataDO gstDataDO = new GSTR1DataDO();
						gstDataDO.setGstp(edo.getGstp());
						gstDataDO.setCustomer_id(ccsi.getCustomer_id());
						gstDataDO.setInv_no(ccsi.getSr_no());
						gstDataDO.setInv_date(ccsi.getSi_date());
						gstDataDO.setInv_amount(dataDO.getSale_amount());
						gstDataDO.setQuantity(dataDO.getQuantity() - dataDO.getPre_cyls());
						gstDataDO.setUnit_rate(dataDO.getUnit_rate());
						float tax = (dataDO.getQuantity() - dataDO.getPre_cyls())
								* (Float.parseFloat(dataDO.getUnit_rate())
										- Float.parseFloat(dataDO.getDisc_unit_rate()));
						String taxable = Float.toString(tax);
						gstDataDO.setTaxable_value(taxable);
						gstDataDO.setInv_type(1);
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
						GSTR1DataDO gstDataDO = new GSTR1DataDO();
						gstDataDO.setGstp(arbdo.getGstp());
						gstDataDO.setCustomer_id(arbsi.getCustomer_id());
						gstDataDO.setInv_no(arbsi.getSr_no());
						gstDataDO.setInv_date(arbsi.getSi_date());
						gstDataDO.setInv_amount(dataDO.getSale_amount());
						gstDataDO.setQuantity(dataDO.getQuantity());
						gstDataDO.setUnit_rate(dataDO.getUnit_rate());
						float tax = dataDO.getQuantity() * (Float.parseFloat(dataDO.getUnit_rate())
								- Float.parseFloat(dataDO.getDisc_unit_rate()));
						String taxable = Float.toString(tax);
						gstDataDO.setTaxable_value(taxable);
						gstDataDO.setInv_type(1);
						gstDataDOList.add(gstDataDO);
					}
				}
			}
		});

		prsis.forEach(prsi -> {
			for (ARBDataDO arbdo : arbdos) {
				List<PurchaseReturnDetailsDO> detailsList = prsi.getDetailsList();
				for (PurchaseReturnDetailsDO dataDO : detailsList) {
					if (arbdo.getId() == dataDO.getProd_code()) {
						GSTR1DataDO gstDataDO = new GSTR1DataDO();
						gstDataDO.setGstp(arbdo.getGstp());
						gstDataDO.setCustomer_id(prsi.getCvo_id());
						gstDataDO.setInv_no(prsi.getSid());
						gstDataDO.setInv_date(prsi.getInv_date());
						gstDataDO.setInv_amount(dataDO.getAmount());
						gstDataDO.setQuantity(dataDO.getRtn_qty());
						gstDataDO.setUnit_rate(dataDO.getUnit_rate());
						float tax = dataDO.getRtn_qty() * (Float.parseFloat(dataDO.getUnit_rate()));
						String taxable = Float.toString(tax);
						gstDataDO.setTaxable_value(taxable);
						gstDataDOList.add(gstDataDO);
					}
				}
			}
		});

		return gstDataDOList;
	}

	public List<GSTR1DataDO> fetchGSTR1B2CSData(List<DOMSalesInvoiceDO> dcsis, List<COMSalesInvoiceDO> ccsis,
			List<ARBSalesInvoiceDO> arbsis, List<EquipmentDataDO> edos, List<ARBDataDO> arbdos,
			List<PurchaseReturnDO> prsis, List<ServicesDataDO> serdos, List<NCDBCInvoiceDO> ncdbcs, List<RCDataDO> rcs,
			List<TVDataDO> tvs) {

		List<GSTR1DataDO> gstDataDOList = new ArrayList<>();

		dcsis.forEach(dcsi -> {
			for (EquipmentDataDO edo : edos) {
				List<DOMSalesInvoiceDetailsDO> detailsList = dcsi.getDetailsList();
				for (DOMSalesInvoiceDetailsDO dataDO : detailsList) {
					if (edo.getProd_code() == dataDO.getProd_code()) {
						GSTR1DataDO gstDataDO = new GSTR1DataDO();
						gstDataDO.setInv_date(dcsi.getSi_date());
						gstDataDO.setInv_amount(dataDO.getSale_amount());
						gstDataDO.setCustomer_id(dcsi.getCustomer_id());
						gstDataDO.setGstp(edo.getGstp());
						gstDataDO.setInv_no(dcsi.getSr_no());
						gstDataDO.setQuantity(dataDO.getQuantity() - dataDO.getPsv_cyls());
						gstDataDO.setUnit_rate(dataDO.getUnit_rate());
						float tax = (dataDO.getQuantity() - dataDO.getPsv_cyls())
								* (Float.parseFloat(dataDO.getUnit_rate())
										- Float.parseFloat(dataDO.getDisc_unit_rate()));
						String taxable = Float.toString(tax);
						gstDataDO.setTaxable_value(taxable);
						gstDataDOList.add(gstDataDO);
					}
				}
			}
		});

		ccsis.forEach(ccsi -> {
			for (EquipmentDataDO edo : edos) {
				List<COMSalesInvoiceDetailsDO> detailsList = ccsi.getDetailsList();
				for (COMSalesInvoiceDetailsDO dataDO : detailsList) {
					if (edo.getProd_code() == dataDO.getProd_code()) {
						GSTR1DataDO gstDataDO = new GSTR1DataDO();
						gstDataDO.setGstp(edo.getGstp());
						gstDataDO.setCustomer_id(ccsi.getCustomer_id());
						gstDataDO.setInv_no(ccsi.getSr_no());
						gstDataDO.setInv_date(ccsi.getSi_date());
						gstDataDO.setInv_amount(dataDO.getSale_amount());
						gstDataDO.setQuantity(dataDO.getQuantity() - dataDO.getPre_cyls());
						gstDataDO.setUnit_rate(dataDO.getUnit_rate());
						float tax = (dataDO.getQuantity() - dataDO.getPre_cyls())
								* (Float.parseFloat(dataDO.getUnit_rate())
										- Float.parseFloat(dataDO.getDisc_unit_rate()));
						String taxable = Float.toString(tax);
						gstDataDO.setTaxable_value(taxable);
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
						GSTR1DataDO gstDataDO = new GSTR1DataDO();
						gstDataDO.setGstp(arbdo.getGstp());
						gstDataDO.setCustomer_id(arbsi.getCustomer_id());
						gstDataDO.setInv_no(arbsi.getSr_no());
						gstDataDO.setInv_date(arbsi.getSi_date());
						gstDataDO.setInv_amount(dataDO.getSale_amount());
						gstDataDO.setQuantity(dataDO.getQuantity());
						gstDataDO.setUnit_rate(dataDO.getUnit_rate());
						float tax = dataDO.getQuantity() * (Float.parseFloat(dataDO.getUnit_rate())
								- Float.parseFloat(dataDO.getDisc_unit_rate()));
						String taxable = Float.toString(tax);
						gstDataDO.setTaxable_value(taxable);
						gstDataDOList.add(gstDataDO);
					}
				}
			}
		});

		prsis.forEach(prsi -> {
			for (ARBDataDO arbdo : arbdos) {
				List<PurchaseReturnDetailsDO> detailsList = prsi.getDetailsList();
				for (PurchaseReturnDetailsDO dataDO : detailsList) {
					if (arbdo.getId() == dataDO.getProd_code()) {
						GSTR1DataDO gstDataDO = new GSTR1DataDO();
						gstDataDO.setGstp(arbdo.getGstp());
						gstDataDO.setCustomer_id(prsi.getCvo_id());
						gstDataDO.setInv_no(prsi.getSid());
						gstDataDO.setInv_date(prsi.getInv_date());
						gstDataDO.setInv_amount(dataDO.getAmount());
						gstDataDO.setQuantity(dataDO.getRtn_qty());
						gstDataDO.setUnit_rate(dataDO.getUnit_rate());
						float tax = dataDO.getRtn_qty() * (Float.parseFloat(dataDO.getUnit_rate()));
						String taxable = Float.toString(tax);
						gstDataDO.setTaxable_value(taxable);
						gstDataDOList.add(gstDataDO);
					}
				}
			}
		});

		ncdbcs.forEach(ncdbc -> {
			for (EquipmentDataDO edo : edos) {
				List<NCDBCInvoiceDetailsDO> ncdbcList = ncdbc.getDetailsList();
				for (NCDBCInvoiceDetailsDO detailsDO : ncdbcList) {
					if ((edo.getProd_code() == detailsDO.getProd_code()) && (edo.getProd_code() < 10)) {
						GSTR1DataDO gstDataDO = new GSTR1DataDO();
						gstDataDO.setGstp(edo.getGstp());
						gstDataDO.setInv_no(ncdbc.getSr_no());
						gstDataDO.setInv_date(ncdbc.getInv_date());
						gstDataDO.setQuantity(detailsDO.getQuantity());
						gstDataDO.setUnit_rate(detailsDO.getUnit_rate());
						float tax = Float.parseFloat(detailsDO.getBasic_price());
						String taxable = Float.toString(tax);
						gstDataDO.setTaxable_value(taxable);
						gstDataDOList.add(gstDataDO);
					}
				}
			}
			for (ARBDataDO arbdo : arbdos) {
				List<NCDBCInvoiceDetailsDO> ncdbcList = ncdbc.getDetailsList();
				for (NCDBCInvoiceDetailsDO detailsDO : ncdbcList) {
					if (arbdo.getId() == detailsDO.getProd_code()) {
						GSTR1DataDO gstDataDO = new GSTR1DataDO();
						gstDataDO.setGstp(arbdo.getGstp());
						gstDataDO.setInv_no(ncdbc.getSr_no());
						gstDataDO.setInv_date(ncdbc.getInv_date());
						gstDataDO.setQuantity(detailsDO.getQuantity());
						gstDataDO.setUnit_rate(detailsDO.getUnit_rate());
						float tax = Float.parseFloat(detailsDO.getBasic_price());
						String taxable = Float.toString(tax);
						gstDataDO.setTaxable_value(taxable);
						gstDataDOList.add(gstDataDO);
					}
				}
			}
			for (ServicesDataDO serdo : serdos) {
				List<NCDBCInvoiceDetailsDO> ncdbcList = ncdbc.getDetailsList();
				for (NCDBCInvoiceDetailsDO detailsDO : ncdbcList) {
					if (serdo.getProd_code() == detailsDO.getProd_code()) {
						GSTR1DataDO gstDataDO = new GSTR1DataDO();
						gstDataDO.setGstp("18");
						gstDataDO.setInv_no(ncdbc.getSr_no());
						gstDataDO.setInv_date(ncdbc.getInv_date());
						gstDataDO.setQuantity(detailsDO.getQuantity());
						gstDataDO.setUnit_rate(detailsDO.getUnit_rate());
						float tax = Float.parseFloat(detailsDO.getBasic_price());
						String taxable = Float.toString(tax);
						gstDataDO.setTaxable_value(taxable);
						gstDataDOList.add(gstDataDO);
					}
				}
			}
		});

		rcs.forEach(rc -> {
			for (EquipmentDataDO edo : edos) {
				GSTR1DataDO gstDataDO = new GSTR1DataDO();
				if (edo.getProd_code() == rc.getProd_code()) {
					gstDataDO.setGstp(edo.getGstp());
					gstDataDO.setInv_no(String.valueOf(rc.getId()));
					gstDataDO.setInv_date(rc.getRc_date());
					gstDataDO.setInv_amount(rc.getRc_amount());
					gstDataDO.setQuantity(rc.getNo_of_cyl());
					float tax = Float.parseFloat(rc.getRc_amount()) - Float.parseFloat(rc.getCyl_deposit())
							- Float.parseFloat(rc.getReg_deposit()) - Float.parseFloat(rc.getIgst_amount())
							- Float.parseFloat(rc.getCgst_amount()) - Float.parseFloat(rc.getSgst_amount())
							- Float.parseFloat(rc.getAdmin_charges()) - Float.parseFloat(rc.getDgcc_amount());
					String taxable = Float.toString(tax);
					gstDataDO.setTaxable_value(taxable);
					gstDataDOList.add(gstDataDO);
				}
			}

			for (ServicesDataDO serdo : serdos) {
				if (serdo.getProd_code() == 23) {
					GSTR1DataDO gstDataDO = new GSTR1DataDO();
					gstDataDO.setGstp("18");
					gstDataDO.setInv_no(String.valueOf(rc.getId()));
					gstDataDO.setInv_date(rc.getRc_date());
					gstDataDO.setQuantity(rc.getNo_of_cyl());
					gstDataDO.setInv_amount(rc.getRc_amount());
					float tax = Float.parseFloat(rc.getAdmin_charges()) + Float.parseFloat(rc.getDgcc_amount());
					String taxable = Float.toString(tax);
					gstDataDO.setTaxable_value(taxable);
					gstDataDOList.add(gstDataDO);
				}
			}
		});

		tvs.forEach(tv -> {
			for (ServicesDataDO serdo : serdos) {
				GSTR1DataDO gstDataDO = new GSTR1DataDO();
				if (serdo.getProd_code() == 20 || serdo.getProd_code() == 28 || serdo.getProd_code() == 29) {
					if (tv.getTv_charges_type() == serdo.getProd_code()) {
						gstDataDO.setGstp("18");
						gstDataDO.setInv_no(String.valueOf(tv.getId()));
						gstDataDO.setInv_date(tv.getTv_date());
						gstDataDO.setInv_amount(tv.getTv_amount());
						gstDataDO.setQuantity(tv.getNo_of_cyl());
						String taxable = tv.getAdmin_charges();
						gstDataDO.setTaxable_value(taxable);
						gstDataDOList.add(gstDataDO);
					}
				}
			}
		});
		return gstDataDOList;
	}

	public List<GSTR1HSNDataDO> fetchGSTR1HSNData(HttpServletRequest request, HttpServletResponse response) {
		MessageObject msgObj = new MessageObject(7033, "FETCH GSTR1 HSN REPORTS DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		List<GSTR1HSNDataDO> gstDataDOList = new ArrayList<>();
		try {
			agencyId = ((AgencyVO) request.getSession()
					.getAttribute(ApplicationConstants.paramKeys.AGENCYVO.getValue())).getAgency_code();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHGSTR1HSNDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId);

			PPMasterDataFactory ppmdf = new PPMasterDataFactory();
			List<EquipmentDataDO> edos = ppmdf.getAgencyAllEquipmentData(agencyId);
			List<ARBDataDO> arbdos = ppmdf.getAgencyAllARBData(agencyId);
			List<ServicesDataDO> serdos = ppmdf.getAgencyServicesData(agencyId);

			Map<String, String[]> requestParams = request.getParameterMap();

			GSTReports1DataFactory srdf = new GSTReports1DataFactory();
			List<GSTR1ProductDataVO> consolidatedList = new ArrayList<>();

			for (EquipmentDataDO edo : edos) {
				long pc = edo.getProd_code();
				if (pc <= 3) {
					consolidatedList = srdf.fetchAgencyGSTR1HSNDOMCSalesData(requestParams, agencyId, pc);
				}
				if ((pc > 3 && pc <= 9)) {
//					consolidatedList = srdf.fetchAgencyGSTR1HSNCOMCSalesData(requestParams, agencyId, pc);
					consolidatedList = srdf.fetchAgencyGSTR1HSNCOMCSalesData(requestParams, agencyId, pc, 0);// remove 0 if offerprice not needed
				}
				if ((pc > 9 && pc <= 12)) {
					consolidatedList.clear();
				}
				Collections.sort(consolidatedList, Comparator.comparing(GSTR1ProductDataVO::getCreatedDate));
				GSTR1HSNDataDO srdvo = new GSTR1HSNDataDO();

				if (!consolidatedList.isEmpty()) {
					int tq = 0;
					String ttax = "0";
					String tt = "0";
					String igst = "0";
					String sgst = "0";
					String cgst = "0";

					for (GSTR1ProductDataVO psvo : consolidatedList) {
						if (edo.getProd_code() == psvo.getProd_code()) {
							srdvo.setProd_code(psvo.getProd_code());
							tq = tq + psvo.getTotal_quantity();

							ttax = Float.toString(Float.parseFloat(ttax) + Float.parseFloat(psvo.getTaxable_value()));
							tt = Float.toString(Float.parseFloat(tt) + Float.parseFloat(psvo.getTotal_value()));
							igst = Float.toString(Float.parseFloat(igst) + Float.parseFloat(psvo.getIgst_amount()));
							sgst = Float.toString(Float.parseFloat(sgst) + Float.parseFloat(psvo.getSgst_amount()));
							cgst = Float.toString(Float.parseFloat(cgst) + Float.parseFloat(psvo.getCgst_amount()));

							srdvo.setProd_type(psvo.getProd_type());
							srdvo.setHsncode(edo.getCsteh_no());
							srdvo.setUqc(edo.getUnits());
							srdvo.setTotal_quantity(tq);
							srdvo.setTaxable_value(ttax);
							srdvo.setTotal_value(tt);
							srdvo.setIgst_amount(igst);
							srdvo.setSgst_amount(sgst);
							srdvo.setCgst_amount(cgst);
						}
					}
					gstDataDOList.add(srdvo);
				}
			}

			for (ARBDataDO arbdo : arbdos) {
				int pc = (int) arbdo.getId();
//				consolidatedList = srdf.fetchAgencyGSTR1HSNARBSalesData(requestParams, agencyId, pc);
				consolidatedList = srdf.fetchAgencyGSTR1HSNARBSalesData(requestParams, agencyId, pc, 0); // remove 0 if offerprice not needed
				Collections.sort(consolidatedList, Comparator.comparing(GSTR1ProductDataVO::getCreatedDate));
				GSTR1HSNDataDO srdvo = new GSTR1HSNDataDO();
				srdvo.setProd_code(pc);
				if (!consolidatedList.isEmpty()) {
					int tq = 0;
					String ttax = "0";
					String tt = "0";
					String igst = "0";
					String sgst = "0";
					String cgst = "0";
					List<Integer> pcarry = new ArrayList<>();
					for (GSTR1ProductDataVO psvo : consolidatedList) {
						if (arbdo.getId() == psvo.getProd_code()) {
							pcarry.add((int) psvo.getProd_code());
							tq = tq + psvo.getTotal_quantity();

							ttax = Float.toString(Float.parseFloat(ttax) + Float.parseFloat(psvo.getTaxable_value()));
							tt = Float.toString(Float.parseFloat(tt) + Float.parseFloat(psvo.getTotal_value()));
							igst = Float.toString(Float.parseFloat(igst) + Float.parseFloat(psvo.getIgst_amount()));
							sgst = Float.toString(Float.parseFloat(sgst) + Float.parseFloat(psvo.getSgst_amount()));
							cgst = Float.toString(Float.parseFloat(cgst) + Float.parseFloat(psvo.getCgst_amount()));

							srdvo.setProd_type(psvo.getProd_type());
							srdvo.setTotal_quantity(tq);
							srdvo.setTaxable_value(ttax);
							srdvo.setTotal_value(tt);
							srdvo.setIgst_amount(igst);
							srdvo.setSgst_amount(sgst);
							srdvo.setCgst_amount(cgst);
						}
					}
					gstDataDOList.add(srdvo);
				}
			}
			for (ServicesDataDO serdo : serdos) {
				int pc = (int) serdo.getProd_code();
				consolidatedList = srdf.fetchAgencyGSTR1HSNSevicesSalesData(requestParams, agencyId, pc);
				Collections.sort(consolidatedList, Comparator.comparing(GSTR1ProductDataVO::getCreatedDate));
				GSTR1HSNDataDO srdvo = new GSTR1HSNDataDO();
				srdvo.setProd_code(pc);
				if (!consolidatedList.isEmpty()) {
					int tq = 0;
					String ttax = "0";
					String tt = "0";
					String igst = "0";
					String sgst = "0";
					String cgst = "0";
					List<Integer> pcarry = new ArrayList<>();
					for (GSTR1ProductDataVO psvo : consolidatedList) {
						if (serdo.getProd_code() == psvo.getProd_code()) {
							pcarry.add((int) psvo.getProd_code());
							tq = tq + psvo.getTotal_quantity();

							ttax = Float.toString(Float.parseFloat(ttax) + Float.parseFloat(psvo.getTaxable_value()));
							tt = Float.toString(Float.parseFloat(tt) + Float.parseFloat(psvo.getTotal_value()));
							igst = Float.toString(Float.parseFloat(igst) + Float.parseFloat(psvo.getIgst_amount()));
							sgst = Float.toString(Float.parseFloat(sgst) + Float.parseFloat(psvo.getSgst_amount()));
							cgst = Float.toString(Float.parseFloat(cgst) + Float.parseFloat(psvo.getCgst_amount()));

							srdvo.setProd_type(4);
							srdvo.setUqc(1);
							srdvo.setHsncode(serdo.getSac_code());
							srdvo.setTotal_quantity(tq);
							srdvo.setTaxable_value(ttax);
							srdvo.setTotal_value(tt);
							srdvo.setIgst_amount(igst);
							srdvo.setSgst_amount(sgst);
							srdvo.setCgst_amount(cgst);
						}
					}
					gstDataDOList.add(srdvo);
				}
			}
			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);

			logger.info(
					ApplicationConstants.LogMessageKeys.FETCHGSTR1HSNDATA.getValue()
							+ ApplicationConstants.paramKeys.PARAM.getValue()
							+ ApplicationConstants.paramKeys.AGENCYID.getValue()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
							+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
							+ ApplicationConstants.actionStatusKeys.STATUS.toString()
							+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(),
					agencyId, ApplicationConstants.SUCCESS_STATUS_STRING);

		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());

			logger.info(ApplicationConstants.LogMessageKeys.FETCHGSTR1HSNDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);

		} catch (ParseException e) {
			e.printStackTrace();

			logger.info(ApplicationConstants.LogMessageKeys.FETCHGSTR1HSNDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.EXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, e);

		}
		request.setAttribute(ApplicationConstants.MESSAGE_OBJECT_ATTRIBUTE_STRING, msgObj);
		return gstDataDOList;
	}

	public List<GSTR1DataDO> fetchGSTR1B2CSData(List<GSTR1DataDO> dt) {
		MessageObject msgObj = new MessageObject(7033, "FETCH GSTR1 HSN REPORTS DATA",
				ApplicationConstants.ERROR_STATUS_STRING);
		List<GSTR1DataDO> consolidatedList = new ArrayList<>();
		try {
			if (!dt.isEmpty()) {
				String ttax = "0";
				String ttax2 = "0";
				String ttax3 = "0";
				String ttax4 = "0";
				GSTR1DataDO gd1 = new GSTR1DataDO();
				GSTR1DataDO gd2 = new GSTR1DataDO();
				GSTR1DataDO gd3 = new GSTR1DataDO();
				GSTR1DataDO gd4 = new GSTR1DataDO();

				for (GSTR1DataDO gd : dt) {
					if (Integer.parseInt(gd.getGstp()) == 5) {
						ttax = Float.toString(Float.parseFloat(ttax) + Float.parseFloat(gd.getTaxable_value()));
						gd1.setTaxable_value(ttax);
						gd1.setGstp("5");

					} else if (Integer.parseInt(gd.getGstp()) == 12) {
						ttax2 = Float.toString(Float.parseFloat(ttax2) + Float.parseFloat(gd.getTaxable_value()));
						gd2.setTaxable_value(ttax2);
						gd2.setGstp("12");

					} else if (Integer.parseInt(gd.getGstp()) == 18) {
						ttax3 = Float.toString(Float.parseFloat(ttax3) + Float.parseFloat(gd.getTaxable_value()));
						gd3.setTaxable_value(ttax3);
						gd3.setGstp("18");

					} else if (Integer.parseInt(gd.getGstp()) == 28) {
						ttax4 = Float.toString(Float.parseFloat(ttax4) + Float.parseFloat(gd.getTaxable_value()));
						gd4.setTaxable_value(ttax4);
						gd4.setGstp("28");
					}
				}
				if (gd1.getGstp() != null)
					consolidatedList.add(gd1);
				if (gd2.getGstp() != null)
					consolidatedList.add(gd2);
				if (gd3.getGstp() != null)
					consolidatedList.add(gd3);
				if (gd4.getGstp() != null)
					consolidatedList.add(gd4);
			}

			msgObj.setMessageStatus(ApplicationConstants.SUCCESS_STATUS_STRING);
		} catch (BusinessException be) {
			msgObj.setMessageText(be.getExceptionMessage());
			logger.info(ApplicationConstants.LogMessageKeys.FETCHGSTR1B2CSDATA.getValue()
					+ ApplicationConstants.paramKeys.PARAM.getValue()
					+ ApplicationConstants.paramKeys.AGENCYID.getValue()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue()
					+ ApplicationConstants.paramKeys.SEPERATOR.getValue()
					+ ApplicationConstants.actionStatusKeys.BUSINESSEXCEPTION.toString()
					+ ApplicationConstants.LogKeys.LOG_PARAM.getValue(), agencyId, be);
			throw be;
		}
		return consolidatedList;
	}

	public List<GSTR1CDNDataDO> fetchGSTR1CDNData(List<GSTR1CDNDataDO> gstDataDOList7,
			List<GSTR1CDNDataDO> gstDataDOList8) {

		List<GSTR1CDNDataDO> gstDataDOList = new ArrayList<>();

		gstDataDOList7.forEach(cnd -> {
			gstDataDOList.add(cnd);
		});

		gstDataDOList8.forEach(dnd -> {
			gstDataDOList.add(dnd);
		});
		return gstDataDOList;
	}

	public List<GSTR1DocsDataDO> fetchGSTR1DocsData(GSTR1DocsDataDO gstDataDOList5, GSTR1DocsDataDO gstDataDOList6,
			GSTR1DocsDataDO gstDataDOList13, GSTR1DocsDataDO gstDataDOList15, GSTR1DocsDataDO gstDataDOList7) {

		List<GSTR1DocsDataDO> gstDataDOList = new ArrayList<>();
		gstDataDOList.add(gstDataDOList5);
		gstDataDOList.add(gstDataDOList6);
		gstDataDOList.add(gstDataDOList13);
		gstDataDOList.add(gstDataDOList15);
		gstDataDOList.add(gstDataDOList7);
		return gstDataDOList;
	}

}
