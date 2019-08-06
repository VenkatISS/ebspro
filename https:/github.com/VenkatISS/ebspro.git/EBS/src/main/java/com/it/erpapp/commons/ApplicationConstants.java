package com.it.erpapp.commons;

public class ApplicationConstants {
	
	public static final String MESSAGE_OBJECT_ATTRIBUTE_STRING = "msg_obj";

	public static final String SUCCESS_STATUS_STRING = "SUCCESS";
	public static final String ERROR_STATUS_STRING = "ERROR";
	
	public static final String FAILURE_STATUS_STRING = "FAILED";
	public static final String UNABLE_TO_PROCESS_REQUEST = "UNABLE TO PROCESS REQUEST"; 
	
	public enum JSPFiles {
		HOME_PAGE("jsp/pages/erp/home.jsp"), 
		SESSIONINVALID_MESSAGE_PAGE("jsp/pages/global.jsp");

		private String value;

		JSPFiles(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public enum actionStatusKeys {
		FIRSTLOGIN("FIRST LOGIN :"),
		BUSINESSEXCEPTION("BUSINESS EXCEPTION :"),
		PARSEEXCEPTION("PARS EEXCEPTION :"),
		ERROR("error"),
		EXCEPTION("EXCEPTION"),
		EXCEPTIONOCCUREDIS("Exception Occured is:"),
		ENTERUSERNAMEPASSWORD("ENTER USER NAME / PASSWORD"),
		STATUS("STATUS : ");
			
		private final String actionStatusKey;		
		actionStatusKeys(String actionStatusKeys) {
			this.actionStatusKey = actionStatusKeys;
		}
		@Override
		public String toString() {
			return actionStatusKey;
		}
	}
	public enum LogMessageKeys {
			
		REGISTRATION("REGISTRATION"),
		ACCOUNT_ACTIVATION("ACCOUNT ACTIVATION"),
		REGISTER_DEVICE("Received request for /registerDevice"),
		VALIDATE_DEALER_LOGIN("Received request for /validateLogin"),
		SENDRESETPASSWORDMAIL("Received request for /sendResetPasswordMail"),
		UPDATEPASSWORD("Received request for /updatePassword"),
		CHANGEPASSWORD("Received request for /changePassword"),
		LOGOUTUSER("Received request for /logOutUser"),
		SUCCESSFULLYLOGGEDOUT("SUCCESSFULLY LOGGED OUT."),
		

		SHOWPURCHASEREPORT("Received request for /showPurchaseReport"),
		FETCHPURCHASEREPORT("Received request for /fetchPurchaseReport"),
		SHOWSALESREPORT("Received request for /showSalesReport"),	
		FETCHSALESREPORT("Received request for /fetchSalesReport"),
		SHOWSTOCKREPORT("Received request for /showStockReport"),
		FETCHAGENCYDOMCSALEREPORT("Received request for /fetchAgencyDOMCSaleReport"),
		FETCHAGENCYCOMCSALEREPORT("Received request for /fetchAgencyCOMCSaleReport"),
		FETCHAGENCYARBSALEREPORT("Received request for /fetchAgencyARBSaleReport"),
		FETCHAGENCYRRT9("Received request for /fetchAgencyRrt9"),
		
		FETCHAGENCYDOMCSTOCKREPORT("Received request for /fetchAgencyDOMCStockReport"),
		FETCHAGENCYCOMCSTOCKREPORT("Received request for /fetchAgencyCOMCStockReport"),
		FETCHSTOCKREPORTBYPRODUCT("Received request for /fetchStockReportByProduct"),
			
		SHOWBANKBOOK("Received request for /showBankBook"),
		
		FETCHBANKBOOKREPORT("Received request for /fetchBankBookReport"),
		
		SHOWLEDGER("Received request for /showLedger"),
		FETCHEQUIPMENTLEDGER("Received request for /fetchEquipmentLedger"),
			
		SHOWNCDBCRCTVREPORT("Received request for /showNCDBCRCTVReport"),
		FETCHNCDBCRCTVREPORT("Received request for /fetchNCDBCRCTVReport"),
			
		SHOWRECEIVABLES("Received request for /showReceivables"),
		FETCHRECEIVABLES("Received request for /fetchReceivables"),
			
		
		SHOWPAYABLES("Received request for /showPayables"),
		FETCHPAYABLES("Received request for /fetchPayables"),
			

//		SHOWPAPAGE("Received request for /showPAPage"),
//		FETCHPAPAGE("Received request for /fetchProfitabilityAnalysisPage"),

		GETNCDBCREPORT("GET NC/DBC REPORT"),
		GETRCREPORT("GET RC REPORT"),
		GETTVREPORT("GET TV REPORT"),
			
		//PAServiceBean		
		SHOWPAPAGE("Received request for /PAServiceBean/showPAPage"),
		FETCHPAPAGE("Received request for /PAServiceBean/fetchPAPage"),

		
		//RegistrationServicebBean		
		REGISTERAGENCY("Received request for /RegistrationServiceBean/registerAgency"),
		ACTIVATEDEALERACCOUNT("Received request for /RegistrationServiceBean/activateDealerAccount"),

		//FirstLoginDataServiceBean
		SAVEAGENCYFIRSTLOGINDATA("Received request for /FirstLoginDataServiceBean/saveAgencyFirstLoginData"),
		
			
		//PPMasterDataServiceBean
		FETCHAGENCYEQUIPMENTDATA("Received request for /PPMasterDataServiceBean/fetchAgencyEquipmentData"),
		SAVEAGENCYEQUIPMENTDATA("Received request for /PPMasterDataServiceBean/saveAgencyEquipmentData"),
		DELETEAGENCYEQUIPMENTDATA("Received request for /PPMasterDataServiceBean/deleteAgencyEquipmentData"),
			
		FETCHAGENCYARBDATA("Received request for /PPMasterDataServiceBean/fetchAgencyARBData"),
		SAVEAGENCYARBDATA("Received request for /PPMasterDataServiceBean/saveAgencyARBData"),
		DELETEAGENCYARBDATA("Received request for /PPMasterDataServiceBean/deleteAgencyARBData"),
			
		FETCHAGENCYSERVICESDATA("Received request for /PPMasterDataServiceBean/fetchAgencyServicesData"),
		SAVEAGENCYSERVICESDATA("Received request for /PPMasterDataServiceBean/saveAgencyServicesData"),
		DELETEAGENCYSERVICESDATA("Received request for /PPMasterDataServiceBean/deleteAgencyServicesData"),
			
		FETCHAGENCYAREACODEDATA("Received request for /PPMasterDataServiceBean/fetchAgencyAreaCodeData"),
		SAVEAGENCYAREACODEDATA("Received request for /PPMasterDataServiceBean/saveAgencyAreaCodeData"),
		DELETEAGENCYAREACODEDATA("Received request for /PPMasterDataServiceBean/deleteAgencyAreaCodeData"),
			
		FETCHAGENCYREFILLPRICEDATA("Received request for /PPMasterDataServiceBean/fetchAgencyRefillPriceData"),
		SAVEAGENCYREFILLPRICEDATA("Received request for /PPMasterDataServiceBean/saveAgencyRefillPriceData"),
		DELETEAGENCYREFILLPRICEDATA("Received request for /PPMasterDataServiceBean/deleteAgencyRefillPriceData"),
			
		FETCHAGENCYARBPRICEDATA("Received request for /PPMasterDataServiceBean/fetchAgencyARBPriceData"),
		SAVEAGENCYARBPRICEDATA("Received request for /PPMasterDataServiceBean/saveAgencyARBPriceData"),
		DELETEAGENCYARBPRICEDATA("Received request for /PPMasterDataServiceBean/deleteAgencyARBPriceData"),

		FETCHAGENCYBOMDATA("Received request for /PPMasterDataServiceBean/fetchAgencyBOMData"),
		SAVEAGENCYBOMDATA("Received request for /PPMasterDataServiceBean/saveAgencyBOMData"),
		DELETEAGENCYBOMDATA("Received request for /PPMasterDataServiceBean/deleteAgencyBOMData"),
		
		//MasterDataServiceBean	
			
		FETCHPRODUCTCATOGORYDATA("Received request for /MasterDataServiceBean/fetchProductCatogoryData"),
		
		FETCHSTATUTORYDATA("Received request for /MasterDataServiceBean/fetchStatutoryData"),
		SAVEAGENCYSTATUTORYDATA("Received request for /MasterDataServiceBean/saveAgencyStatutoryData"),
		DELETEAGENCYSTATUTORYDATA("Received request for /MasterDataServiceBean/deleteAgencyStatutoryData"),
			
		FETCHSTAFFDATA("Received request for /MasterDataServiceBean/fetchStaffData"),
		SAVESTAFFDATA("Received request for /MasterDataServiceBean/saveStaffData"),
		DELETESTAFFDATA("Received request for /MasterDataServiceBean/deleteStaffData"),

		FETCHFLEETDATA("Received request for /MasterDataServiceBean/fetchFleetData"),
		SAVEFLEETDATA("Received request for /MasterDataServiceBean/saveFleetData"),
		DELETEFLEETDATA("Received request for /MasterDataServiceBean/deleteFleetData"),
		
		FETCHCVODATA("Received request for /MasterDataServiceBean/fetchCVOData"),
		SAVECVODATA("Received request for /MasterDataServiceBean/saveCVOData"),
		DELETECVODATA("Received request for /MasterDataServiceBean/deleteCVOData"),

		FETCHBANKDATA("Received request for /MasterDataServiceBean/fetchBankData"),
		SAVEBANKDATA("Received request for /MasterDataServiceBean/saveBankData"),
		DELETEBANKDATA("Received request for /MasterDataServiceBean/deleteBankData"),
		UPDATEBANKDATA("Received request for /MasterDataServiceBean/updateBankData"),
		// start opening balance update 06052019
		  FETCHOPENINGBALANCE("Received request for /MasterDataServiceBean/fetchOpeningBalanceData"),
		  UPDATEOPENINGBALANCE("Received request for /MasterDataServiceBean/updateOpeningBalanceData"),

		// END opening balance update 06052019


		FETCHEXPENDITUREDATA("Received request for /MasterDataServiceBean/fetchExpenditureData"),
		SAVEEXPENDITUREDATA("Received request for /MasterDataServiceBean/saveExpenditureData"),
		DELETEEXPENDITUREDATA("Received request for /MasterDataServiceBean/deleteExpenditureData"),
		
		FETCHCREDITLIMITSDATA("Received request for /MasterDataServiceBean/fetchCreditLimitsData"),
		SAVECREDITLIMITSDATA("Received request for /MasterDataServiceBean/saveCreditLimitsData"),
		DELETECREDITLIMITSDATA("Received request for /MasterDataServiceBean/deleteCreditLimitsData"),
		UPDATECREDITLIMITSDATA("Received request for /MasterDataServiceBean/updateCreditLimitsData"),
		//TransactionsDataServiceBean
		
		FETCHPURCHASEINVOICES("Received request for /TransactionsDataServiceBean/fetchPurchaseInvoices"),
		SAVEPURCHASEINVOICES("Received request for /TransactionsDataServiceBean/savePurchaseInvoices"),
		DELETEPURCHASEINVOICES("Received request for /TransactionsDataServiceBean/deletePurchaseInvoices"),

		FETCHARBPURCHASEINVOICES("Received request for /TransactionsDataServiceBean/fetchARBPurchaseInvoices"),
		SAVEARBPURCHASEINVOICES("Received request for /TransactionsDataServiceBean/saveARBPurchaseInvoices"),
		DELETEARBPURCHASEINVOICES("Received request for /TransactionsDataServiceBean/deleteARBPurchaseInvoices"),
			
		FETCHOTHERPURCHASEINVOICES("Received request for /TransactionsDataServiceBean/fetchOTHERPurchaseInvoices"),
		SAVEOTHERPURCHASEINVOICES("Received request for /TransactionsDataServiceBean/saveOTHERPurchaseInvoices"),
		DELETEOTHERPURCHASEINVOICES("Received request for /TransactionsDataServiceBean/deleteOTHERPurchaseInvoices"),

		FETCHDOMESTICCYLINDERSALESINVOICES("Received request for /TransactionsDataServiceBean/fetchDomesticCylinderSalesInvoices"),
		SAVEDOMESTICCYLINDERSALESINVOICES("Received request for /TransactionsDataServiceBean/saveDomesticCylinderSalesInvoices"),
		DELETEDOMESTICCYLINDERSALESINVOICES("Received request for /TransactionsDataServiceBean/deleteDomesticCylinderSalesInvoices"),

		FETCHCOMMERCIALSALESINVOICES("Received request for /TransactionsDataServiceBean/fetchCommercialSalesInvoices"),
		SAVECOMMERCIALSALESINVOICES("Received request for /TransactionsDataServiceBean/saveCommercialSalesInvoices"),
		DELETECOMMERCIALSALESINVOICES("Received request for /TransactionsDataServiceBean/deleteCommercialSalesInvoices"),

		FETCHARBSALESINVOICES("Received request for /TransactionsDataServiceBean/fetchARBSalesInvoices"),
		SAVEARBSALESINVOICES("Received request for /TransactionsDataServiceBean/saveARBSalesInvoices"),
		DELETEARBSALESINVOICES("Received request for /TransactionsDataServiceBean/deleteARBSalesInvoices"),

		FETCHQUOTATIONS("Received request for /TransactionsDataServiceBean/fetchQuotations"),
		SAVEQUOTATION("Received request for /TransactionsDataServiceBean/saveQuotation"),
		DELETEQUOTATION("Received request for /TransactionsDataServiceBean/deleteQuotation"),

		FETCHDELIVERYCHALLAN("Received request for /TransactionsDataServiceBean/fetchDeliveryChallan"),
		SAVEDELIVERYCHALLAN("Received request for /TransactionsDataServiceBean/saveDeliveryChallan"),
		DELETEDELIVERYCHALLAN("Received request for /TransactionsDataServiceBean/deleteDeliveryChallan"),

		//OTransactionsDataServiceBean
			
			
		FETCHRECEIPTSDATA("Received request for /OTransactionsDataServiceBean/fetchReceiptsData"),
		SAVERECEIPTSDATA("Received request for /OTransactionsDataServiceBean/saveReceiptsData"),
		DELETERECEIPTSDATA("Received request for /OTransactionsDataServiceBean/deleteReceiptsData"),
		
		FETCHPAYMENTSDATA("Received request for /OTransactionsDataServiceBean/fetchPaymentsData"),
		SAVEPAYMENTSDATA("Received request for /OTransactionsDataServiceBean/savePaymentsData"),
		DELETEPAYMENTSDATA("Received request for /OTransactionsDataServiceBean/deletePaymentsData"),

		FETCHBANKTRANXSDATA("Received request for /OTransactionsDataServiceBean/fetchBankTranxsData"),
		SAVEBANKTRANXSDATA("Received request for /OTransactionsDataServiceBean/saveBankTranxsData"),
		DELETEBANKTRANXSDATA("Received request for /OTransactionsDataServiceBean/deleteBankTranxsData"),

		FETCHCREDITNOTESDATA("Received request for /OTransactionsDataServiceBean/fetchCreditNotesData"),
		SAVECREDITNOTESDATA("Received request for /OTransactionsDataServiceBean/saveCreditNotesData"),
		DELETECREDITNOTESDATA("Received request for /OTransactionsDataServiceBean/deleteCreditNotesData"),

		FETCHDEBITNOTESDATA("Received request for /OTransactionsDataServiceBean/fetchDebitNotesData"),
		SAVEDEBITNOTESDATA("Received request for /OTransactionsDataServiceBean/saveDebitNotesData"),
		DELETEDEBITNOTESDATA("Received request for /OTransactionsDataServiceBean/deleteDebitNotesData"),

		FETCHPURCHASERETURNDATA("Received request for /OTransactionsDataServiceBean/fetchPurchaseReturnData"),
		SAVEPURCHASERETURNDATA("Received request for /OTransactionsDataServiceBean/savePurchaseReturnData"),
		DELETEPURCHASERETURNDATA("Received request for /OTransactionsDataServiceBean/deletePurchaseReturnData"),

		FETCHSALESRETURNDATA("Received request for /OTransactionsDataServiceBean/fetchSalesReturnData"),
		SAVESALESRETURNDATA("Received request for /OTransactionsDataServiceBean/saveSalesReturnData"),
		DELETESALESRETURNDATA("Received request for /OTransactionsDataServiceBean/deleteSalesReturnData"),

		FETCHASSETSDATA("Received request for /OTransactionsDataServiceBean/fetchAssetsData"),
		SAVEASSETSDATA("Received request for /OTransactionsDataServiceBean/saveAssetsData"),
		DELETEASSETDATA("Received request for /OTransactionsDataServiceBean/deleteAssetData"),

		FETCHNCDBCDATA("Received request for /OTransactionsDataServiceBean/fetchNCDBCData"),
		SAVENCDBCDATA("Received request for /OTransactionsDataServiceBean/saveNCDBCData"),
		DELETENCDBCDATA("Received request for /OTransactionsDataServiceBean/deleteNCDBCData"),

		FETCHTVDATA("Received request for /OTransactionsDataServiceBean/fetchTVData"),
		SAVETVDATA("Received request for /OTransactionsDataServiceBean/saveTVData"),
		DELETETVDATA("Received request for /OTransactionsDataServiceBean/deleteTVData"),

		FETCHRCDATA("Received request for /OTransactionsDataServiceBean/fetchRCData"),
		SAVERCDATA("Received request for /OTransactionsDataServiceBean/saveRCData"),
		DELETERCDATA("Received request for /OTransactionsDataServiceBean/deleteRCData"),

		FETCHNCDATA("Received request for /OTransactionsDataServiceBean/fetchNCData"),
		SAVENCDATA("Received request for /OTransactionsDataServiceBean/saveNCData"),
		DELETENCDATA("Received request for /OTransactionsDataServiceBean/deleteNCData"),

		//DEServiceBean
			
			
		SHOWDAYENDPAGE("Received request for /DEServiceBean/showDayEndPage"),
		FETCHDAYENDPAGE("Received request for /DEServiceBean/fetchDayEndPage"),
		SUBMITDAYENDREPORT("Received request for /DEServiceBean/submitDayEndReport"),

		//GSTReport1ServiceBean
			
		FETCHGSTREPORT1DATA("Received request for /GSTReport1ServiceBean/fetchGSTReport1Data"),
		FETCHGSTR1HSNDATA("Received request for /GSTReport1ServiceBean/fetchGSTR1HSNData"),
		FETCHGSTR1B2CSDATA("Received request for /GSTReport1ServiceBean/fetchGSTR1B2CSData"),

		//GSTReportServiceBean
			
		SHOWGSTREPORT("Received request for /GSTReportServiceBean/showGSTReport"),
		FETCHGSTREPORT("Received request for /GSTReportServiceBean/fetchGSTReport"),
		SETOFFGSTPAYMENTSDATA("Received request for /GSTReportServiceBean/setOffAgencyPaymentsData"),
		PAYOFFGSTPAYMENTSDATA("Received request for /GSTReportServiceBean/payOffAgencyPaymentDetailsData"),
		
		//MyProfileServiceBean
		FETCHPROFILEPAGEDETAILS("Received request for /MyProfileServiceBean/fetchProfilepageDetails"),
		UPDATEPROFILEDETAILS("Received request for /MyProfileServiceBean/updateProfileDetails"),
		FORGOTPINNUMBER("Received request for /MyProfileServiceBean/forgotPinNumber"),
		SAVEPINNUMBER("Received request for /MyProfileServiceBean/savePinNumber"),
		CHANGEPINNUMBER("Received request for /MyProfileServiceBean/changePinNumber"),
		UPLOADPROFILEPICTOPATH("Received request for /MyProfileServiceBean/uploadprofilepicToPath"),
		
		//ShowPagesServiceBean		
		SHOWDOMSALESINVOICEPOPUP("Received request for /ShowPagesServiceBean/showDOMSalesInvoicePopup"),
		SHOWCOMSALESINVOICEPOPUP("Received request for /ShowPagesServiceBean/showCOMSalesInvoicePopup"),
		SHOWARBSALESINVOICEPOPUP("Received request for /ShowPagesServiceBean/showARBSalesInvoicePopup"),
		
		SHOWQUOTATIONSPOPUP("Received request for /ShowPagesServiceBean/showQuotationsPopup"),
		SHOWDELIVERYCHALLENPOPUP("Received request for /ShowPagesServiceBean/showDeliveryChallenPopup"),
		SHOWPURCHASERETURNPOPUP("Received request for /ShowPagesServiceBean/showPurchaseReturnPopup"),

		SHOWNCDBCPOPUP("Received request for /ShowPagesServiceBean/showNCDBCPopup"),
		SHOWRCPOPUP("Received request for /ShowPagesServiceBean/showRCPopup"),
		SHOWTVPOPUP("Received request for /ShowPagesServiceBean/showTVPopup"),

		SHOWCREDITNOTEPOPUP("Received request for /ShowPagesServiceBean/showCreditNotePopup"),
		SHOWDEBITNOTEPOPUP("Received request for /ShowPagesServiceBean/showDebitNotePopup"),

		//HomePageServiceBean
			
		FETCHALERTSSTATUTORYDATA("Received request for /HomePageServiceBean/fetchAlertsStatutoryData"),
		FETCHCLIENTLISTDATA("Received request for /HomePageServiceBean/fetchClientListData"),
		
		//FinancialReportsServiceBean
				
		FETCHPARTNERSDATA("Received request for /FinancialReportsServiceBean/fetchPartnersData"),
		SAVEPARTNERSDATA("Received request for /FinancialReportsServiceBean/savePartnersData"),
		DELETEPARTNERSDATA("Received request for /FinancialReportsServiceBean/deletePartnersData"),
		
		FETCHPARTNERSTRANXSDATA("Received request for /FinancialReportsServiceBean/fetchPartnersTranxsData"),
		SAVEPARTNERSTRANXSDATA("Received request for /FinancialReportsServiceBean/savePartnersTranxsData"),
		DELETEPARTNERSTRANXSDATA("Received request for /FinancialReportsServiceBean/deletePartnersTranxsData"),
		
		SHOWCAPITALACCOUNTDATA("Received request for /FinancialReportsServiceBean/showCapitalAccountData"),
		FETCHCAPITALACCOUNTDATA("Received request for /FinancialReportsServiceBean/fetchCapitalAccountData"),
		SHOWDEPRECIATIONREPORT("Received request for /FinancialReportsServiceBean/showDepreciationReport"),
		
		FETCHDEPRECIATIONREPORT("Received request for /FinancialReportsServiceBean/fetchDepreciationReport"),
		PROCESSDEPRECIATIONREPORT("Received request for /FinancialReportsServiceBean/processDepreciationReport"),
		SUBMITDEPRECIATIONREPORT("Received request for /FinancialReportsServiceBean/submitDepreciationReport"),
		
		SHOWCAPITALACCOUNTREPORT("Received request for /FinancialReportsServiceBean/showCapitalAccountReport"),
		FETCHCAPITALACCOUNTREPORT("Received request for /FinancialReportsServiceBean/fetchCapitalAccountReport"),
		PROCESSCAPITALACCOUNTREPORT("Received request for /FinancialReportsServiceBean/processCapitalAccountReport"),
		
		SUBMITCAPITALACCOUNTREPORT("Received request for /FinancialReportsServiceBean/submitCapitalAccountReport"),
		SHOWPANDLACCOUNT("Received request for /FinancialReportsServiceBean/showPandLAccount"),
		FETCHPANDLACCOUNT("Received request for /FinancialReportsServiceBean/fetchPandLAccount"),
		
		PROCESSPANDLACCOUNT("Received request for /FinancialReportsServiceBean/processPandLAccount"),
		SAVEPANDLACCOUNT("Received request for /FinancialReportsServiceBean/savePandLAccount"),
		SUBMITPANDLACCOUNT("Received request for /FinancialReportsServiceBean/submitPandLAccount"),
		
		SHOWBALANCESHEET("Received request for /FinancialReportsServiceBean/showBalanceSheet"),
		FETCHBALANCESHEET("Received request for /FinancialReportsServiceBean/fetchBalanceSheet"),
		PROCESSBALANCESHEET("Received request for /FinancialReportsServiceBean/processBalanceSheet"),
		
		SAVEBALANCESHEET("Received request for /FinancialReportsServiceBean/saveBalanceSheet"),
		SUBMITBALANCESHEET("Received request for /FinancialReportsServiceBean/submitBalanceSheet"),
		
		//new popup data 19042019
		
		SAVEANDFETCHPOPUPDATA("Received request for /TransactionsDataServiceBean/saveAndFetchPopUpData"),

		test("Test");
		String value;
			
		LogMessageKeys(String value){
			this.value=value;
		}
		public String getValue(){
			return value;
		}
			
	}
	
	public enum LogKeys {
		LOG_PARAM(" - {}");
		String value;
		LogKeys(String value) {
			this.value = value;
		}
		public String getValue(){
			return value;
		}
	}
		
	public enum paramKeys {
		PARAM(" --->  "),	
		AGENCYID("AGENCY_ID : "),
		AGENCY_ID("agencyId"),
		AGENCYCODE("agencycode"),
		PASSWORD("PASSWORD : "),
		EMAIL("EMAIL :"),
		ACTIVATIONCODE("ACTIVATION CODE : "),
		NEWPASSWORD("NEW PASSWORD : "),
		DNAME("DISTRIBUTERSHIP NAME :"),
		DMOBILE("DEALER MOBILE :"),
		EMAILID("EMAIL ID :"),
		GSTIN("GSTIN :"),
		STATEORUT("STATE OR UT :"),
		CPASSWORD("CONFIRM PASSWORD :"),
		FIRMTYPE("FIRM TYPE :"),
		
		ITEMID("ITEM ID :"),
		FYEAR("FINANCIAL YEAR :"),
		TAROBALANCE("TAR OPENING BALANCE :"),
		STAROBALANCE("STAR OPENING BALANCE :"),
		CASHBALANCE("CASH BALANCE :"),
		EFFECTIVEDATE("EFFECTIVE DATE :"),
		STAFFDATAID("STAFFDATA ID :"),
		FLEETDATAID("FLEETDATA ID :"),
		CVOID("CVOID :"),
		BANKDATAID("BANKDATA ID :"),
		EXPDATAID("EXPDATA ID :"),
		CREDITLIMITDATAID("CREDIT LIMIT DATAID :"),

		SEPERATOR(",  "),
		STATUS("STATUS : "),
		FILE("FILE"),
		FROMDATE("FROM DATE : "),
		TODATE("TO DATE : "),
		PRODUCTID("PRODUCT ID : "),
		VENDORID("VENDOR ID : "),
		PARTYID("PARTY ID : "),
		REQUEST("REQUEST :"),
		BANKID("BANK ID : "),
		NCDBCRCTVPRODUCTID("NC/DBC/RC/TV PRODUCT ID : "),
		CONNECTIONTYPE("CONNECTION TYPE : "),
		ARDATE("AGENCY RECEIVABLES REPORT DATE : "),
		CUSTOMERID("CUSTOMER ID :"),
		SETDAYSTYPE("SET DAYS TYPE "),
		VENDORORCUSTOMERID("VENDOR/CUSTOMER ID :"),
		YEAR("YEAR : "),
		MONTH("MONTH : "),
		SUBMITDATE("SUBMIT DATE : "),
		CONTACTPERSONNAME("CONTACT PERSON NAME :"),
		CONTACTMOBILE("CONTACT MOBILE NO :"),
		CONTACTLANDLINENO("CONTACT LANDLINE NO :"),
		CONTACTEMAILID("CONTACT EMAIL ID :"),
		CONTACTOFFCADDR("CONTACT OFFICE ADDRESS :"),
		INVOICEID("INVOICE ID :"),
		DISCOUNTON19KGNDNECYL("DISCOUNT ON 19KG NDNE CYL :"),
		DISCOUNTON19KGCUTTINGGASCYL("DISCOUNT ON 19KG CUTTING GAS CYL :"),
		DISCOUNTON35KGNDNECYL("DISCOUNT ON 35KG NDNE CYL :"),
		DISCOUNTON47_5KGNDNECYL("DISCOUNT ON 47_5KG NDNE CYL :"),
		DISCOUNTON450KGSUMOCYL("DISCOUNT ON 450KG SUMO CYL :"),
		ERROR("ERROR"),
		AGENCYVO("agencyVO"),
		PARTNERS_DATA("partners_data"),
		CAPAR_DATA("capar_data"),
		DEPR_DATA("depr_data"),
		DEPR_DETAILS_DATA("depr_details_data"),
		PAL_DATA("pal_data"),
		PAL_DETAILS("pal_details"),
		BS_DATA("bs_data"),
		BS_DETAILS("bs_details"),
		GST_PAYMENT_DETAILS("gst_payment_details"),
		GST_PAYMENTS_DATA("gst_payments_data"),
		STATUTORY_DATA("statutory_data"),
		ITEM_ID("itemId"),
		STAFF_DATA("staff_data"),
		FLEET_DATA("fleet_data"),
		DCVO_DATA("dcvo_data");
		
		String value;
		paramKeys(String value) {
			this.value = value;
		}
		public String getValue(){
			return value;
		}
	}
}

