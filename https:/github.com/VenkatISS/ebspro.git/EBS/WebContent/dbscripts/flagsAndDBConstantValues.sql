1)FOR BANK_TRANS_DATA:
	* BTFLAG = 1 : BankTransactions, 
	* BTFLAG = 2 : NCDBC - delete,  
	* BTFLAG = 3 : SaveAgencyPurchases,
	* BTFLAG = 4 : SaveAgencyDSInvoices,delete
	* BTFLAG = 5 : SaveAgencyNCDBCInvoices,
	* BTFLAG = 6 : SaveAgencyRCData,
	* BTFLAG = 7 : SaveAgencyTVData,
	* BTFLAG = 8 : SaveAgencyReceiptsData,
	* BTFLAG = 9 : SaveAgencyPaymentsData, - subtract
	* BTFLAG = 10 : SaveAgencyCreditNotes, - 
	* BTFLAG = 11 : SaveAgencyDebitNotes,
	* BTFLAG = 12 : SaveAgencyAssetsData,
	* BTFLAG = 13 : saveAgencyPartnersTranxsData.
	* BTFLAG = 14 : saveAgencyCSInvoices
	* BTFLAG = 15 : saveAgencyARBSInvoices
	* BTFLAG = 16 : saveSalesReturnsData


2)FOR AGENCY_STOCK_DATA:
  
  * DELETED = 1  ==>  DELETED THAT PRODUCT IN EQUIPMENT MASTER.

  * TRANSACTION TYPES FOR AGENCY_STOCK_DATA:
		1 PURCHASE 			--- +
		2 SALE				--- -
		3 PURCHASE RETURN	--- -
		4 SALES RETURN		--- +
		5 NCDBC				--- Prod_code() > 9 --> -
		6 RC				--- No_of_reg() > 0 --> -
		7 TV				--- No_of_reg()>0 --> + , Prod_code() != 10 --> E+

  * STOCK_FLAG :
		1	CYLD PURCHASES
		2	ARB PURCHASES
		3	PURCHASE RETURN
		4	DOM SALES
		5	COM SALES
		6	ARB SALES
		7	SALES RETURN
		8	NCDBC
		9	RC
		10	TV	
		
	
		
3)FOR CVO_BALANCE_DATA:
  
  * CVO_CAT:
		0 : VENDOR
		1 : CUSTOMER
		2 : OMC
		3 : OTHERS

  * TRANSACTION TYPE(ttype)
		0 : First Time login
		1 : Purchases (VENDOR   = 0)
		2 : Sales (CUSTOMER = 1)
		3 : RECEIPTS (CUSTOMER = 1)
		4 : PAYMENTS (VENDOR   = 0 & CUSTOMER = 1)
		5 : CREDITNOTE (CUSTOMER = 1)
		6 : DEBITNOTE (CUSTOMER = 1)
		7 : PURCASERETURN (VENDOR   = 0)
		8 : SALESRETURN (CUSTOMER = 1)

  * CVOFLAG:
		cvoflag = 0  (First Time login and customer/vendor data adding)
		cvoflag = 1 (ARBPurchases invoice)
		cvoflag = 2 (OTHERPurchases invoice)

  		(DOMSales invoice)
		cvoflag = 31 (Payment_terms=1)
		cvoflag = 32 (Payment_terms=2)

		(COMSales invoice)
		cvoflag = 41 (Payment_terms=1)(for CASH)
		cvoflag = 42 (Payment_terms=2)(for CREDIT)

		(ARBSales invoice)
		cvoflag = 51 (Payment_terms=1)(for CASH)
		cvoflag = 52 (Payment_terms=2)(for CREDIT)

		(RECEIPTS invoice)
		cvoflag = 61  (Payment_terms=1)(for CASH)
		cvoflag = 62  (Payment_terms=2)(for CHECK OR ONLINE TRANSFER)

		(PAYMENTS invoice)
		cvoflag = 71  (Payment_terms=1)(for CASH)
		cvoflag = 72  (Payment_terms=2)(for CHECK OR ONLINE TRANSFER)

		(CREDITNOTE)
		cvoflag = 81 (Receive CreditNote) 
		cvoflag = 82 (Issue CreditNote) 
		
		(DEBITNOTE)
		cvoflag = 91 (Receive DebitNote) 
		cvoflag = 92 (Issue DebitNote)

		cvoflag = 10  (PURCASERETURN)
		cvoflag = 11  (SALESRETURN)
		
		
