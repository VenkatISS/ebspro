package com.it.erpapp.processor;

import java.util.HashMap;
import java.util.Map;

public class ServiceMethodsMapProcessor {

	private volatile static ServiceMethodsMapProcessor _instance;

	private ServiceMethodsMapProcessor() {
	}

	private Map<String, String> serviceBeanNamesMap = new HashMap<String, String>();
	private Map<String, String> serviceBeanMethodsNamesMap = new HashMap<String, String>();

	public String getServiceBeanName(String actionId) {
		return serviceBeanNamesMap.get(actionId);
	}

	public String getServiceBeanMethodName(String actionId) {
		return serviceBeanMethodsNamesMap.get(actionId);
	}

	public static ServiceMethodsMapProcessor getInstance() {

		if (_instance == null) {
			synchronized (ServiceMethodsMapProcessor.class) {
				if (_instance == null) {
					// create instance
					_instance = new ServiceMethodsMapProcessor();
					// populate Maps
					_instance.populateMaps();
				}
			}
		}
		return _instance;
	}

	private void populateMaps() {

		// Dealer Module methods
		serviceBeanNamesMap.put("1001", "com.it.erpapp.appservices.AuthenticationServiceBean");
		serviceBeanMethodsNamesMap.put("1001", "validateLogin");
		serviceBeanNamesMap.put("1002", "com.it.erpapp.appservices.RegistrationServiceBean");
		serviceBeanMethodsNamesMap.put("1002", "registerAgency");
		serviceBeanNamesMap.put("1000", "com.it.erpapp.appservices.AuthenticationServiceBean");
		serviceBeanMethodsNamesMap.put("1000", "logoutUser");
		serviceBeanNamesMap.put("1003", "com.it.erpapp.appservices.FirstLoginDataServiceBean");
		serviceBeanMethodsNamesMap.put("1003", "saveAgencyFirstLoginData");
		// Dealer Registration Mail Activation
		serviceBeanNamesMap.put("1004", "com.it.erpapp.appservices.RegistrationServiceBean");
		serviceBeanMethodsNamesMap.put("1004", "activateDealerAccount");

		// forgot password
		serviceBeanNamesMap.put("1005", "com.it.erpapp.appservices.AuthenticationServiceBean");
		serviceBeanMethodsNamesMap.put("1005", "sendResetPasswordMail");
		serviceBeanNamesMap.put("1006", "com.it.erpapp.appservices.AuthenticationServiceBean");
		serviceBeanMethodsNamesMap.put("1006", "updatePassword");
		// Change Password
		serviceBeanNamesMap.put("1007", "com.it.erpapp.appservices.AuthenticationServiceBean");
		serviceBeanMethodsNamesMap.put("1007", "changePassword");

		// Home - 2000
		serviceBeanNamesMap.put("2000", "com.it.erpapp.appservices.ShowPagesServiceBean");
		serviceBeanMethodsNamesMap.put("2000", "showHome");
		serviceBeanNamesMap.put("2001", "com.it.erpapp.appservices.HomePageServiceBean");
		serviceBeanMethodsNamesMap.put("2001", "fetchAlertsStatutoryData");
		serviceBeanNamesMap.put("2002", "com.it.erpapp.appservices.HomePageServiceBean");
		serviceBeanMethodsNamesMap.put("2002", "fetchClientListData");

		// Master Data - 3000
		serviceBeanNamesMap.put("3000", "com.it.erpapp.appservices.ShowPagesServiceBean");
		serviceBeanMethodsNamesMap.put("3000", "showMasterData");

		// Master Data - Top Level
		serviceBeanNamesMap.put("3001", "com.it.erpapp.appservices.MasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3001", "fetchStatutoryData");
		serviceBeanNamesMap.put("3002", "com.it.erpapp.appservices.MasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3002", "saveAgencyStatutoryData");
		serviceBeanNamesMap.put("3003", "com.it.erpapp.appservices.MasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3003", "deleteAgencyStatutoryData");
		serviceBeanNamesMap.put("3006", "com.it.erpapp.appservices.MasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3006", "fetchProductCatogoryData");
		serviceBeanNamesMap.put("3501", "com.it.erpapp.appservices.MasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3501", "fetchCVOData");
		serviceBeanNamesMap.put("3502", "com.it.erpapp.appservices.MasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3502", "saveCVOData");
		serviceBeanNamesMap.put("3503", "com.it.erpapp.appservices.MasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3503", "deleteCVOData");
		serviceBeanNamesMap.put("3511", "com.it.erpapp.appservices.MasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3511", "fetchBankData");
		serviceBeanNamesMap.put("3512", "com.it.erpapp.appservices.MasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3512", "saveBankData");
		serviceBeanNamesMap.put("3513", "com.it.erpapp.appservices.MasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3513", "deleteBankData");
		serviceBeanNamesMap.put("3521", "com.it.erpapp.appservices.MasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3521", "fetchExpenditureData");
		serviceBeanNamesMap.put("3522", "com.it.erpapp.appservices.MasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3522", "saveExpenditureData");
		serviceBeanNamesMap.put("3523", "com.it.erpapp.appservices.MasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3523", "deleteExpenditureData");
		serviceBeanNamesMap.put("3531", "com.it.erpapp.appservices.MasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3531", "fetchFleetData");
		serviceBeanNamesMap.put("3532", "com.it.erpapp.appservices.MasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3532", "saveFleetData");
		serviceBeanNamesMap.put("3533", "com.it.erpapp.appservices.MasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3533", "deleteFleetData");
		serviceBeanNamesMap.put("3541", "com.it.erpapp.appservices.MasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3541", "fetchStaffData");
		serviceBeanNamesMap.put("3542", "com.it.erpapp.appservices.MasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3542", "saveStaffData");
		serviceBeanNamesMap.put("3543", "com.it.erpapp.appservices.MasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3543", "deleteStaffData");
		serviceBeanNamesMap.put("3551", "com.it.erpapp.appservices.MasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3551", "fetchCreditLimitsData");
		serviceBeanNamesMap.put("3552", "com.it.erpapp.appservices.MasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3552", "saveCreditLimitsData");
		serviceBeanNamesMap.put("3553", "com.it.erpapp.appservices.MasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3553", "deleteCreditLimitsData");
		serviceBeanNamesMap.put("3554", "com.it.erpapp.appservices.MasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3554", "updateCreditLimitsData");

		// start opening balance update 06052019
		serviceBeanNamesMap.put("3556", "com.it.erpapp.appservices.MasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3556", "fetchOpeningBalanceData");
		
		serviceBeanNamesMap.put("3557", "com.it.erpapp.appservices.MasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3557", "updateOpeningBalanceData");
				
		//update cvo details 23-July2k19
		serviceBeanNamesMap.put("3558", "com.it.erpapp.appservices.MasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3558", "updateCVOData");
		
		//update bank data details 29-july2k19
        serviceBeanNamesMap.put("3559", "com.it.erpapp.appservices.MasterDataServiceBean");
        serviceBeanMethodsNamesMap.put("3559", "updateBankData");
				
		// end opening balance update 06052019
				
		// Master Data - Second Level
		serviceBeanNamesMap.put("3101", "com.it.erpapp.appservices.PPMasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3101", "fetchAgencyEquipmentData");
		serviceBeanNamesMap.put("3102", "com.it.erpapp.appservices.PPMasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3102", "saveAgencyEquipmentData");
		serviceBeanNamesMap.put("3103", "com.it.erpapp.appservices.PPMasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3103", "deleteAgencyEquipmentData");
		serviceBeanNamesMap.put("3111", "com.it.erpapp.appservices.PPMasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3111", "fetchAgencyARBData");
		serviceBeanNamesMap.put("3112", "com.it.erpapp.appservices.PPMasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3112", "saveAgencyARBData");
		serviceBeanNamesMap.put("3113", "com.it.erpapp.appservices.PPMasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3113", "deleteAgencyARBData");
		serviceBeanNamesMap.put("3121", "com.it.erpapp.appservices.PPMasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3121", "fetchAgencyServicesData");
		serviceBeanNamesMap.put("3122", "com.it.erpapp.appservices.PPMasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3122", "saveAgencyServicesData");
		serviceBeanNamesMap.put("3123", "com.it.erpapp.appservices.PPMasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3123", "deleteAgencyServicesData");
		
	//  ----------------------- new requirement 25042019 ---------------------------------
			serviceBeanNamesMap.put("3124", "com.it.erpapp.appservices.PPMasterDataServiceBean");
			serviceBeanMethodsNamesMap.put("3124", "updateServiceGSTApplicablePercentage");
			
			// ----------------------- new requirement 25042019 ---------------------------------
			
		serviceBeanNamesMap.put("3131", "com.it.erpapp.appservices.PPMasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3131", "fetchAgencyAreaCodeData");
		serviceBeanNamesMap.put("3132", "com.it.erpapp.appservices.PPMasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3132", "saveAgencyAreaCodeData");
		serviceBeanNamesMap.put("3133", "com.it.erpapp.appservices.PPMasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3133", "deleteAgencyAreaCodeData");
		serviceBeanNamesMap.put("3141", "com.it.erpapp.appservices.PPMasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3141", "fetchAgencyBOMData");
		serviceBeanNamesMap.put("3142", "com.it.erpapp.appservices.PPMasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3142", "saveAgencyBOMData");
		serviceBeanNamesMap.put("3143", "com.it.erpapp.appservices.PPMasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3143", "deleteAgencyBOMData");
		serviceBeanNamesMap.put("3201", "com.it.erpapp.appservices.PPMasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3201", "fetchAgencyRefillPriceData");
		serviceBeanNamesMap.put("3202", "com.it.erpapp.appservices.PPMasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3202", "saveAgencyRefillPriceData");
		serviceBeanNamesMap.put("3203", "com.it.erpapp.appservices.PPMasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3203", "deleteAgencyRefillPriceData");
		serviceBeanNamesMap.put("3211", "com.it.erpapp.appservices.PPMasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3211", "fetchAgencyARBPriceData");
		serviceBeanNamesMap.put("3212", "com.it.erpapp.appservices.PPMasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3212", "saveAgencyARBPriceData");
		serviceBeanNamesMap.put("3213", "com.it.erpapp.appservices.PPMasterDataServiceBean");
		serviceBeanMethodsNamesMap.put("3213", "deleteAgencyARBPriceData");

		// Transaction Data - 5000
		serviceBeanNamesMap.put("5000", "com.it.erpapp.appservices.ShowPagesServiceBean");
		serviceBeanMethodsNamesMap.put("5000", "showTransactions");
		serviceBeanNamesMap.put("5101", "com.it.erpapp.appservices.TransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5101", "fetchPurchaseInvoices");
		serviceBeanNamesMap.put("5102", "com.it.erpapp.appservices.TransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5102", "savePurchaseInvoices");
		serviceBeanNamesMap.put("5103", "com.it.erpapp.appservices.TransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5103", "deletePurchaseInvoices");
		serviceBeanNamesMap.put("5111", "com.it.erpapp.appservices.TransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5111", "fetchARBPurchaseInvoices");
		serviceBeanNamesMap.put("5112", "com.it.erpapp.appservices.TransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5112", "saveARBPurchaseInvoices");
		serviceBeanNamesMap.put("5113", "com.it.erpapp.appservices.TransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5113", "deleteARBPurchaseInvoices");
		serviceBeanNamesMap.put("5116", "com.it.erpapp.appservices.TransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5116", "fetchOTHERPurchaseInvoices");
		serviceBeanNamesMap.put("5117", "com.it.erpapp.appservices.TransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5117", "saveOTHERPurchaseInvoices");
		serviceBeanNamesMap.put("5118", "com.it.erpapp.appservices.TransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5118", "deleteOTHERPurchaseInvoices");
		serviceBeanNamesMap.put("5121", "com.it.erpapp.appservices.TransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5121", "fetchDomesticCylinderSalesInvoices");
		serviceBeanNamesMap.put("5122", "com.it.erpapp.appservices.TransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5122", "saveDomesticCylinderSalesInvoices");
		serviceBeanNamesMap.put("5123", "com.it.erpapp.appservices.TransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5123", "deleteDomesticCylinderSalesInvoices");
		serviceBeanNamesMap.put("5126", "com.it.erpapp.appservices.TransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5126", "fetchCommercialSalesInvoices");
		serviceBeanNamesMap.put("5127", "com.it.erpapp.appservices.TransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5127", "saveCommercialSalesInvoices");
		serviceBeanNamesMap.put("5128", "com.it.erpapp.appservices.TransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5128", "deleteCommercialSalesInvoices");
		serviceBeanNamesMap.put("5131", "com.it.erpapp.appservices.TransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5131", "fetchARBSalesInvoices");
		serviceBeanNamesMap.put("5132", "com.it.erpapp.appservices.TransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5132", "saveARBSalesInvoices");
		serviceBeanNamesMap.put("5133", "com.it.erpapp.appservices.TransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5133", "deleteARBSalesInvoices");
		serviceBeanNamesMap.put("5141", "com.it.erpapp.appservices.TransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5141", "fetchQuotations");
		serviceBeanNamesMap.put("5142", "com.it.erpapp.appservices.TransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5142", "saveQuotation");
		serviceBeanNamesMap.put("5143", "com.it.erpapp.appservices.TransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5143", "deleteQuotation");
		serviceBeanNamesMap.put("5151", "com.it.erpapp.appservices.TransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5151", "fetchDeliveryChallan");
		serviceBeanNamesMap.put("5152", "com.it.erpapp.appservices.TransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5152", "saveDeliveryChallan");
		serviceBeanNamesMap.put("5153", "com.it.erpapp.appservices.TransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5153", "deleteDeliveryChallan");
		// Transaction Data Others - 5501
		serviceBeanNamesMap.put("5501", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5501", "fetchReceiptsData");
		serviceBeanNamesMap.put("5502", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5502", "saveReceiptsData");
		serviceBeanNamesMap.put("5503", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5503", "deleteReceiptsData");
		serviceBeanNamesMap.put("5511", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5511", "fetchPaymentsData");
		serviceBeanNamesMap.put("5512", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5512", "savePaymentsData");
		serviceBeanNamesMap.put("5513", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5513", "deletePaymentsData");
		serviceBeanNamesMap.put("5521", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5521", "fetchBankTranxsData");
		serviceBeanNamesMap.put("5522", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5522", "saveBankTranxsData");
		serviceBeanNamesMap.put("5523", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5523", "deleteBankTranxsData");
		serviceBeanNamesMap.put("5531", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5531", "fetchCreditNotesData");
		serviceBeanNamesMap.put("5532", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5532", "saveCreditNotesData");
		serviceBeanNamesMap.put("5533", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5533", "deleteCreditNotesData");
		serviceBeanNamesMap.put("5541", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5541", "fetchDebitNotesData");
		serviceBeanNamesMap.put("5542", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5542", "saveDebitNotesData");
		serviceBeanNamesMap.put("5543", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5543", "deleteDebitNotesData");
		serviceBeanNamesMap.put("5551", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5551", "fetchSalesReturnData");
		serviceBeanNamesMap.put("5552", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5552", "saveSalesReturnData");
		serviceBeanNamesMap.put("5553", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5553", "deleteSalesReturnData");
		serviceBeanNamesMap.put("5561", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5561", "fetchAssetsData");
		serviceBeanNamesMap.put("5562", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5562", "saveAssetsData");
		serviceBeanNamesMap.put("5563", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5563", "deleteAssetData");
		serviceBeanNamesMap.put("5566", "com.it.erpapp.appservices.FinancialReportsServiceBean");
		serviceBeanMethodsNamesMap.put("5566", "showDepreciationReport");
		serviceBeanNamesMap.put("5567", "com.it.erpapp.appservices.FinancialReportsServiceBean");
		serviceBeanMethodsNamesMap.put("5567", "fetchDepreciationReport");
		serviceBeanNamesMap.put("5568", "com.it.erpapp.appservices.FinancialReportsServiceBean");
		serviceBeanMethodsNamesMap.put("5568", "processDepreciationReport");
		serviceBeanNamesMap.put("5569", "com.it.erpapp.appservices.FinancialReportsServiceBean");
		serviceBeanMethodsNamesMap.put("5569", "submitDepreciationReport");
		serviceBeanNamesMap.put("5571", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5571", "fetchPurchaseReturnData");
		serviceBeanNamesMap.put("5572", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5572", "savePurchaseReturnData");
		serviceBeanNamesMap.put("5573", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5573", "deletePurchaseReturnData");

		serviceBeanNamesMap.put("5701", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5701", "fetchTVData");
		serviceBeanNamesMap.put("5702", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5702", "saveTVData");
		serviceBeanNamesMap.put("5703", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5703", "deleteTVData");

		serviceBeanNamesMap.put("5711", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5711", "fetchRCData");
		serviceBeanNamesMap.put("5712", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5712", "saveRCData");
		serviceBeanNamesMap.put("5713", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5713", "deleteRCData");

		serviceBeanNamesMap.put("5721", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5721", "fetchNCData");
		serviceBeanNamesMap.put("5722", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5722", "saveNCData");
		serviceBeanNamesMap.put("5723", "com.it.erpapp.appservices.OTransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("5723", "deleteNCData");

		// Reports - 7000
		serviceBeanNamesMap.put("7001", "com.it.erpapp.appservices.ReportsServiceBean");
		serviceBeanMethodsNamesMap.put("7001", "showPurchaseReport");
		serviceBeanNamesMap.put("7002", "com.it.erpapp.appservices.ReportsServiceBean");
		serviceBeanMethodsNamesMap.put("7002", "fetchPurchaseReport");
		serviceBeanNamesMap.put("7011", "com.it.erpapp.appservices.ReportsServiceBean");
		serviceBeanMethodsNamesMap.put("7011", "showSalesReport");
		serviceBeanNamesMap.put("7012", "com.it.erpapp.appservices.ReportsServiceBean");
		serviceBeanMethodsNamesMap.put("7012", "fetchSalesReport");
		serviceBeanNamesMap.put("7021", "com.it.erpapp.appservices.ReportsServiceBean");
		serviceBeanMethodsNamesMap.put("7021", "showStockReport");
		serviceBeanNamesMap.put("7022", "com.it.erpapp.appservices.ReportsServiceBean");
		serviceBeanMethodsNamesMap.put("7022", "fetchStockReportByProduct");
		serviceBeanNamesMap.put("7031", "com.it.erpapp.appservices.GSTReportServiceBean");
		serviceBeanMethodsNamesMap.put("7031", "showGSTReport");
		serviceBeanNamesMap.put("7032", "com.it.erpapp.appservices.GSTReportServiceBean");
		serviceBeanMethodsNamesMap.put("7032", "fetchGSTReport");
		serviceBeanNamesMap.put("7033", "com.it.erpapp.appservices.GSTReport1ServiceBean");
		serviceBeanMethodsNamesMap.put("7033", "fetchGSTReport1Data");
		serviceBeanNamesMap.put("7034", "com.it.erpapp.appservices.GSTReportServiceBean");
		serviceBeanMethodsNamesMap.put("7034", "saveAgencyPaymentsData");
		serviceBeanNamesMap.put("7035", "com.it.erpapp.appservices.GSTReportServiceBean");
		serviceBeanMethodsNamesMap.put("7035", "setOffAgencyPaymentsData");
		serviceBeanNamesMap.put("7036", "com.it.erpapp.appservices.GSTReportServiceBean");
		serviceBeanMethodsNamesMap.put("7036", "payOffAgencyPaymentDetailsData");

		serviceBeanNamesMap.put("7041", "com.it.erpapp.appservices.ReportsServiceBean");
		serviceBeanMethodsNamesMap.put("7041", "showBankBook");
		serviceBeanNamesMap.put("7042", "com.it.erpapp.appservices.ReportsServiceBean");
		serviceBeanMethodsNamesMap.put("7042", "fetchBankBookReport");
		serviceBeanNamesMap.put("7051", "com.it.erpapp.appservices.ReportsServiceBean");
		serviceBeanMethodsNamesMap.put("7051", "showNCDBCRCTVReport");
		serviceBeanNamesMap.put("7052", "com.it.erpapp.appservices.ReportsServiceBean");
		serviceBeanMethodsNamesMap.put("7052", "fetchNCDBCRCTVReport");
		serviceBeanNamesMap.put("7061", "com.it.erpapp.appservices.PAServiceBean");
		serviceBeanMethodsNamesMap.put("7061", "showPAPage");
		serviceBeanNamesMap.put("7062", "com.it.erpapp.appservices.PAServiceBean");
		serviceBeanMethodsNamesMap.put("7062", "fetchPAPage");

		serviceBeanNamesMap.put("7101", "com.it.erpapp.appservices.OReportsServiceBean");
		serviceBeanMethodsNamesMap.put("7101", "showReceivables");
		serviceBeanNamesMap.put("7111", "com.it.erpapp.appservices.OReportsServiceBean");
		serviceBeanMethodsNamesMap.put("7111", "showPayables");
		serviceBeanNamesMap.put("7121", "com.it.erpapp.appservices.OReportsServiceBean");
		serviceBeanMethodsNamesMap.put("7121", "showExpenditures");
		serviceBeanNamesMap.put("7131", "com.it.erpapp.appservices.OReportsServiceBean");
		serviceBeanMethodsNamesMap.put("7131", "showNCDBCRCTV");

		serviceBeanNamesMap.put("7201", "com.it.erpapp.appservices.ReportsServiceBean");
		serviceBeanMethodsNamesMap.put("7201", "showLedger");
		serviceBeanNamesMap.put("7202", "com.it.erpapp.appservices.ReportsServiceBean");
		serviceBeanMethodsNamesMap.put("7202", "fetchEquipmentLedger");

		serviceBeanNamesMap.put("7301", "com.it.erpapp.appservices.ReportsServiceBean");
		serviceBeanMethodsNamesMap.put("7301", "showDayEndReport");
		serviceBeanNamesMap.put("7302", "com.it.erpapp.appservices.ReportsServiceBean");
		serviceBeanMethodsNamesMap.put("7302", "fetchDayEndReport");
		serviceBeanNamesMap.put("7311", "com.it.erpapp.appservices.ReportsServiceBean");
		serviceBeanMethodsNamesMap.put("7311", "showDCMS");
		serviceBeanNamesMap.put("7312", "com.it.erpapp.appservices.ReportsServiceBean");
		serviceBeanMethodsNamesMap.put("7312", "fetchDCMS");

		serviceBeanNamesMap.put("7351", "com.it.erpapp.appservices.ReportsServiceBean");
		serviceBeanMethodsNamesMap.put("7351", "showReceivables");
		serviceBeanNamesMap.put("7352", "com.it.erpapp.appservices.ReportsServiceBean");
		serviceBeanMethodsNamesMap.put("7352", "fetchReceivables");
		serviceBeanNamesMap.put("7361", "com.it.erpapp.appservices.ReportsServiceBean");
		serviceBeanMethodsNamesMap.put("7361", "showPayables");
		serviceBeanNamesMap.put("7362", "com.it.erpapp.appservices.ReportsServiceBean");
		serviceBeanMethodsNamesMap.put("7362", "fetchPayables");

		// DAY END - 8000
		serviceBeanNamesMap.put("8001", "com.it.erpapp.appservices.DEServiceBean");
		serviceBeanMethodsNamesMap.put("8001", "showDayEndPage");
		serviceBeanNamesMap.put("8002", "com.it.erpapp.appservices.DEServiceBean");
		serviceBeanMethodsNamesMap.put("8002", "fetchDayEndPage");
		serviceBeanNamesMap.put("8003", "com.it.erpapp.appservices.DEServiceBean");
		serviceBeanMethodsNamesMap.put("8003", "submitDayEndReport");

		// FIN REPORTS - 8500
		serviceBeanNamesMap.put("8501", "com.it.erpapp.appservices.FinancialReportsServiceBean");
		serviceBeanMethodsNamesMap.put("8501", "fetchPartnersData");
		serviceBeanNamesMap.put("8502", "com.it.erpapp.appservices.FinancialReportsServiceBean");
		serviceBeanMethodsNamesMap.put("8502", "savePartnersData");
		serviceBeanNamesMap.put("8503", "com.it.erpapp.appservices.FinancialReportsServiceBean");
		serviceBeanMethodsNamesMap.put("8503", "deletePartnersData");
		serviceBeanNamesMap.put("8511", "com.it.erpapp.appservices.FinancialReportsServiceBean");
		serviceBeanMethodsNamesMap.put("8511", "fetchPartnersTranxsData");
		serviceBeanNamesMap.put("8512", "com.it.erpapp.appservices.FinancialReportsServiceBean");
		serviceBeanMethodsNamesMap.put("8512", "savePartnersTranxsData");
		serviceBeanNamesMap.put("8513", "com.it.erpapp.appservices.FinancialReportsServiceBean");
		serviceBeanMethodsNamesMap.put("8513", "deletePartnersTranxsData");
		serviceBeanNamesMap.put("8521", "com.it.erpapp.appservices.FinancialReportsServiceBean");
		serviceBeanMethodsNamesMap.put("8521", "showCapitalAccountData");
		serviceBeanNamesMap.put("8522", "com.it.erpapp.appservices.FinancialReportsServiceBean");
		serviceBeanMethodsNamesMap.put("8522", "fetchCapitalAccountData");
		serviceBeanNamesMap.put("8531", "com.it.erpapp.appservices.FinancialReportsServiceBean");
		serviceBeanMethodsNamesMap.put("8531", "showCapitalAccountReport");
		serviceBeanNamesMap.put("8532", "com.it.erpapp.appservices.FinancialReportsServiceBean");
		serviceBeanMethodsNamesMap.put("8532", "fetchCapitalAccountReport");
		serviceBeanNamesMap.put("8533", "com.it.erpapp.appservices.FinancialReportsServiceBean");
		serviceBeanMethodsNamesMap.put("8533", "processCapitalAccountReport");
		serviceBeanNamesMap.put("8534", "com.it.erpapp.appservices.FinancialReportsServiceBean");
		serviceBeanMethodsNamesMap.put("8534", "submitCapitalAccountReport");
		serviceBeanNamesMap.put("8541", "com.it.erpapp.appservices.FinancialReportsServiceBean");
		serviceBeanMethodsNamesMap.put("8541", "showPandLAccount");
		serviceBeanNamesMap.put("8542", "com.it.erpapp.appservices.FinancialReportsServiceBean");
		serviceBeanMethodsNamesMap.put("8542", "fetchPandLAccount");
		serviceBeanNamesMap.put("8543", "com.it.erpapp.appservices.FinancialReportsServiceBean");
		serviceBeanMethodsNamesMap.put("8543", "processPandLAccount");
		serviceBeanNamesMap.put("8544", "com.it.erpapp.appservices.FinancialReportsServiceBean");
		serviceBeanMethodsNamesMap.put("8544", "savePandLAccount");
		serviceBeanNamesMap.put("8545", "com.it.erpapp.appservices.FinancialReportsServiceBean");
		serviceBeanMethodsNamesMap.put("8545", "submitPandLAccount");
		serviceBeanNamesMap.put("8551", "com.it.erpapp.appservices.FinancialReportsServiceBean");
		serviceBeanMethodsNamesMap.put("8551", "showBalanceSheet");
		serviceBeanNamesMap.put("8552", "com.it.erpapp.appservices.FinancialReportsServiceBean");
		serviceBeanMethodsNamesMap.put("8552", "fetchBalanceSheet");
		serviceBeanNamesMap.put("8553", "com.it.erpapp.appservices.FinancialReportsServiceBean");
		serviceBeanMethodsNamesMap.put("8553", "processBalanceSheet");
		serviceBeanNamesMap.put("8554", "com.it.erpapp.appservices.FinancialReportsServiceBean");
		serviceBeanMethodsNamesMap.put("8554", "saveBalanceSheet");
		serviceBeanNamesMap.put("8555", "com.it.erpapp.appservices.FinancialReportsServiceBean");
		serviceBeanMethodsNamesMap.put("8555", "submitBalanceSheet");

		// Profile - 9000
		serviceBeanNamesMap.put("9000", "com.it.erpapp.appservices.ShowPagesServiceBean");
		serviceBeanMethodsNamesMap.put("9000", "showProfile");

		// Profile - 9000
		serviceBeanNamesMap.put("9001", "com.it.erpapp.appservices.MyProfileServiceBean");
		serviceBeanMethodsNamesMap.put("9001", "fetchProfilepageDetails");
		serviceBeanNamesMap.put("9002", "com.it.erpapp.appservices.MyProfileServiceBean");
		serviceBeanMethodsNamesMap.put("9002", "updateProfileDetails");
		
		serviceBeanNamesMap.put("9010", "com.it.erpapp.appservices.MyProfileServiceBean");
		serviceBeanMethodsNamesMap.put("9010", "sendingConfirmationMailAndUpdatingEmailId");
		serviceBeanNamesMap.put("9011", "com.it.erpapp.appservices.MyProfileServiceBean");
		serviceBeanMethodsNamesMap.put("9011", "UpdateEmailId");
		
		// set pin number
		serviceBeanNamesMap.put("9003", "com.it.erpapp.appservices.MyProfileServiceBean");
		serviceBeanMethodsNamesMap.put("9003", "savePinNumber");
		// change pin number
		serviceBeanNamesMap.put("9004", "com.it.erpapp.appservices.MyProfileServiceBean");
		serviceBeanMethodsNamesMap.put("9004", "changePinNumber");

		// forgot pin number
		serviceBeanNamesMap.put("9005", "com.it.erpapp.appservices.MyProfileServiceBean");
		serviceBeanMethodsNamesMap.put("9005", "forgotPinNumber");

		// upload profile
		serviceBeanNamesMap.put("9006", "com.it.erpapp.appservices.MyProfileServiceBean");
		serviceBeanMethodsNamesMap.put("9006", "uploadprofilepicToPath");

		// remove profile
		serviceBeanNamesMap.put("9007", "com.it.erpapp.appservices.MyProfileServiceBean");
		serviceBeanMethodsNamesMap.put("9007", "removeProfilePhoto");
		
		//upload master data through Excel
        serviceBeanNamesMap.put("9008", "com.it.erpapp.appservices.MyProfileServiceBean");
        serviceBeanMethodsNamesMap.put("9008", "uploadMasterDataXLtoDB");
		

		// Show Popup
		serviceBeanNamesMap.put("999", "com.it.erpapp.appservices.ShowPagesServiceBean");
		serviceBeanMethodsNamesMap.put("999", "showDOMSalesInvoicePopup");
		serviceBeanNamesMap.put("998", "com.it.erpapp.appservices.ShowPagesServiceBean");
		serviceBeanMethodsNamesMap.put("998", "showCOMSalesInvoicePopup");
		serviceBeanNamesMap.put("997", "com.it.erpapp.appservices.ShowPagesServiceBean");
		serviceBeanMethodsNamesMap.put("997", "showARBSalesInvoicePopup");
		serviceBeanNamesMap.put("996", "com.it.erpapp.appservices.ShowPagesServiceBean");
		serviceBeanMethodsNamesMap.put("996", "showQuotationsPopup");
		serviceBeanNamesMap.put("995", "com.it.erpapp.appservices.ShowPagesServiceBean");
		serviceBeanMethodsNamesMap.put("995", "showDeliveryChallenPopup");
		serviceBeanNamesMap.put("994", "com.it.erpapp.appservices.ShowPagesServiceBean");
		serviceBeanMethodsNamesMap.put("994", "showPurchaseReturnPopup");
		serviceBeanNamesMap.put("993", "com.it.erpapp.appservices.ShowPagesServiceBean");
		serviceBeanMethodsNamesMap.put("993", "showNCDBCPopup");
		serviceBeanNamesMap.put("992", "com.it.erpapp.appservices.ShowPagesServiceBean");
		serviceBeanMethodsNamesMap.put("992", "showRCPopup");
		serviceBeanNamesMap.put("991", "com.it.erpapp.appservices.ShowPagesServiceBean");
		serviceBeanMethodsNamesMap.put("991", "showSalesReturnPopup");
		serviceBeanNamesMap.put("990", "com.it.erpapp.appservices.ShowPagesServiceBean");
		serviceBeanMethodsNamesMap.put("990", "showCreditNotePopup");
		serviceBeanNamesMap.put("989", "com.it.erpapp.appservices.ShowPagesServiceBean");
		serviceBeanMethodsNamesMap.put("989", "showDebitNotePopup");
		serviceBeanNamesMap.put("988", "com.it.erpapp.appservices.ShowPagesServiceBean");
		serviceBeanMethodsNamesMap.put("988", "showTVPopup");
		
		serviceBeanNamesMap.put("987", "com.it.erpapp.appservices.ShowPagesServiceBean");
		serviceBeanMethodsNamesMap.put("987", "showPaymentsInvoicePopup");
		serviceBeanNamesMap.put("986", "com.it.erpapp.appservices.ShowPagesServiceBean");
		serviceBeanMethodsNamesMap.put("986", "showReceiptsInvoicePopup");

		// Financial Reports - 7500
		serviceBeanNamesMap.put("7501", "com.it.erpapp.appservices.FRServiceBean");
		serviceBeanMethodsNamesMap.put("7501", "showCapitalAccountPage");
		serviceBeanNamesMap.put("7502", "com.it.erpapp.appservices.FRServiceBean");
		serviceBeanMethodsNamesMap.put("7502", "fetchCapitalAccountPage");
		serviceBeanNamesMap.put("7601", "com.it.erpapp.appservices.FRServiceBean");
		serviceBeanMethodsNamesMap.put("7601", "showDepreciationAccountPage");
		serviceBeanNamesMap.put("7602", "com.it.erpapp.appservices.FRServiceBean");
		serviceBeanMethodsNamesMap.put("7602", "fetchDepreciationAccountPage");

/*--------------new multi popup 29032019 start-------------------*/

		
		serviceBeanNamesMap.put("9999", "com.it.erpapp.appservices.TransactionsDataServiceBean");
		serviceBeanMethodsNamesMap.put("9999", "saveAndFetchPopUpData");
		

	}

}
