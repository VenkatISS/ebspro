INSERT INTO AGENCYS (
   AGENCY_CODE,
   EMAIL_ID,
   PWD,
   STATUS,
   CREATED_BY,
   CREATED_DATE,
   MODIFIED_BY,
   MODIFIED_DATE,
   VERSION,
   DELETED
) VALUES (
  1234567890 -- AGENCY_CODE - IN bigint(10)
  ,'eagency01@gmail.com' -- EMAIL_ID - IN varchar(50)
  ,'AbMHrLpPVPVar8M7sGu79sqAPpo='		/*  ,'AbMHrLpPVPVar8M7sGu79sqAPpo=' -- PWD - IN varchar(50) */
  ,1 -- STATUS - IN int(1)
  ,1234567890   -- CREATED_BY - IN bigint(10)
  ,SYSDATE()  -- CREATED_DATE - IN datetime
  ,1234567890   -- MODIFIED_BY - IN bigint(10)
  ,SYSDATE()  -- MODIFIED_DATE - IN datetime
  ,1  -- VERSION - IN int(2)
  ,0   -- DELETED - IN int(1)
);
SELECT * FROM AGENCYS;
SELECT * FROM AGENCY_DETAILS;
UPDATE AGENCYS SET STATUS=1 WHERE AGENCY_CODE=1818181818;

SELECT * FROM AGENCY_DETAILS ad where ad.CREATED_DATE > ad.DAYEND_DATE;
UPDATE AGENCY_DETAILS SET LAST_TRXNDATE = EFFECTIVE_DATE WHERE CREATED_BY = 3333333333;
UPDATE AGENCYS SET EMAIL_ID = "krishna@wbry.com" WHERE CREATED_BY = 88888888;
INSERT INTO AGENCY_DETAILS (
	 AGENCY_NAME ,
	 AGENCY_MOBILE ,
	 OFFICE_LANDLINE ,
	 ADDRESS ,
	 AGENCY_ST_OR_UT ,
	 AGENCY_OC ,
	 PPSHIP ,
	 PAN_NO ,
	 TIN_NO ,
	 GSTIN_NO ,
	 REG_NO ,
	 CONTACT_PERSON ,
	 DAYEND_DATE,
	 CREATED_BY ,	
	 CREATED_DATE ,
	 MODIFIED_BY ,
	 MODIFIED_DATE ,
	 VERSION ,
	 DELETED
	) VALUES (
   'Agency01' -- AGENCY_NAME - IN varchar(100)
  ,'987654312' -- AGENCY_MOBILE - IN varchar(10)
  ,'040234567'  -- OFFICE_LANDLINE - IN varchar(10)
  ,'HYD'  -- ADDRESS - IN varchar(250)
  ,1 -- AGENCY_ST_OR_UT - IN int(4)
  ,1 -- AGENCY_OC - IN int(4)
  ,1
  ,'CBXPQ1234D'  -- PAN_NO - IN varchar(15)
  ,'ASDF123D12'  -- TIN_NO - IN varchar(11)
  ,'12CBXPQ1234D3S3' -- GSTIN 
  ,'1029384756'  -- REG_NO - IN varchar(20)
  ,'ABC01'  -- CONTACT_PERSON - IN varchar(50)
  ,1498780800000 --DAYEND_DATE
  ,1234567890   -- CREATED_BY - IN int(10)
  ,SYSDATE()  -- CREATED_DATE - IN datetime
  ,1234567890   -- MODIFIED_BY - IN int(10)
  ,SYSDATE()  -- MODIFIED_DATE - IN datetime
  ,1   -- VERSION - IN int(11)
  ,0   -- DELETED - IN int(1)
);


INSERT INTO AGENCYS (
   AGENCY_CODE,
   EMAIL_ID,
   PWD,
   STATUS,
   CREATED_BY,
   CREATED_DATE,
   MODIFIED_BY,
   MODIFIED_DATE,
   VERSION,
   DELETED
) VALUES (
  1234567891 -- AGENCY_CODE - IN bigint(10)
  ,'eagency02@gmail.com' -- EMAIL_ID - IN varchar(50)
  ,'ZOoNx9rdSaM38e8UgVvT9CgUHH0='		/*  ,'AbMHrLpPVPVar8M7sGu79sqAPpo=' -- PWD - IN varchar(50) */
  ,1 -- STATUS - IN int(1)
  ,1234567891   -- CREATED_BY - IN bigint(10)
  ,SYSDATE()  -- CREATED_DATE - IN datetime
  ,1234567891   -- MODIFIED_BY - IN bigint(10)
  ,SYSDATE()  -- MODIFIED_DATE - IN datetime
  ,1  -- VERSION - IN int(2)
  ,0   -- DELETED - IN int(1)
);

INSERT INTO AGENCY_SI_SERIAL(SR_NO,CREATED_BY) VALUES(1,1234567890);
INSERT INTO AGENCY_CDR_SERIAL(SR_NO,CREATED_BY) VALUES(1,1234567890);
INSERT INTO AGENCY_PR_SERIAL(SR_NO,CREATED_BY) VALUES(1,1234567890);

select agencydo0_.AGENCY_CODE as AGENCY_C1_2_, agencydo0_.CREATED_BY as CREATED_2_2_, agencydo0_.CREATED_DATE as CREATED_3_2_, agencydo0_.DELETED as DELETED4_2_, agencydo0_.EMAIL_ID as EMAIL_ID5_2_, agencydo0_.MODIFIED_BY as MODIFIED6_2_, agencydo0_.MODIFIED_DATE as MODIFIED7_2_, agencydo0_.PWD as PWD8_2_, agencydo0_.STATUS as STATUS9_2_, agencydo0_.VERSION as VERSION10_2_ from AGENCYS agencydo0_ where agencydo0_.AGENCY_CODE=1234567890 and agencydo0_.PWD='1234567890' and agencydo0_.STATUS=1

SELECT * FROM STATUTORY_DATA;
SELECT * FROM AGENCYS;
UPDATE AGENCYS SET STATUS=1 WHERE AGENCY_CODE=12341234;
UPDATE AGENCYS SET IS_FTL=1 WHERE AGENCY_CODE=1234567890;

SELECT * FROM AGENCY_SERIAL_NOS;
SELECT * FROM NCDBCRCTV_INVOICES;
SELECT * FROM NCDBCRCTV_INVOICES_DETAILS;
SELECT * FROM ARB_SALES_INVOICES_DETAILS;
SELECT * FROM DOM_SALES_INVOICES;
SELECT * FROM DOM_SALES_INVOICES_DETAILS;
SELECT * FROM ARB_PURCHASE_INVS;
SELECT * FROM BANK_DATA;
SELECT * FROM BANK_DATA;
SELECT * FROM CVO_DATA;

DROP TABLE DOM_SALES_INVOICES_DETAILS;
DROP TABLE DOM_SALES_INVOICES;

SELECT id, created_at FROM table WHERE created_at BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 1 day);
SELECT AGENCY_CODE, CREATED_DATE FROM AGENCYS WHERE CREATED_DATE BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 1 day);
SELECT AGENCY_CODE, CREATED_DATE FROM AGENCYS WHERE CREATED_DATE BETWEEN DATE_ADD(CURDATE(), INTERVAL 1 day) AND DATE_ADD(CURDATE(), INTERVAL 2 day);

SELECT SR_NO, CREATED_DATE FROM DOM_SALES_INVOICES WHERE FROM_UNIXTIME(CREATED_DATE/1000) BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 1 day) ORDER BY CREATED_DATE DESC LIMIT 1;
SELECT SR_NO, CREATED_DATE FROM DOM_SALES_INVOICES WHERE CREATED_DATE BETWEEN DATE_ADD(CURDATE(), INTERVAL 1 day) AND DATE_ADD(CURDATE(), INTERVAL 2 day);
SELECT CURDATE();
SELECT NOW(); 
SELECT SR_NO, CREATED_DATE FROM DOM_SALES_INVOICES WHERE CREATED_DATE < NOW();
SELECT (UNIX_TIMESTAMP(CREATED_DATE)*1000) FROM DOM_SALES_INVOICES;
SELECT FROM_UNIXTIME(CREATED_DATE/1000) FROM DOM_SALES_INVOICES;
SI-2017-6 1516863356105
1516777207000
UPDATE DOM_SALES_INVOICES SET CREATED_DATE = 1516777207000 WHERE CREATED_DATE = 1516863356105;

select count(distinct dsid.DSI_ID) from DOM_SALES_INVOICES_DETAILS dsid,DOM_SALES_INVOICES dsi where dsi.CREATED_BY=1234567890;
select ID from DOM_SALES_INVOICES where CREATED_BY=1234567890;
select count(distinct dsid.DSI_ID) from DOM_SALES_INVOICES_DETAILS dsid,DOM_SALES_INVOICES dsi where dsi.CREATED_BY=1234567890 and dsi.ID=dsid.DSI_ID;
select distinct dsid.DSI_ID from DOM_SALES_INVOICES_DETAILS dsid,DOM_SALES_INVOICES dsi where dsi.CREATED_BY=1234567890 and dsi.ID=dsid.DSI_ID;

SELECT * FROM AGENCYS;
SELECT * FROM ACTIVE_AGENCIES_VIEW;

SELECT * FROM AGENCY_SERIAL_NOS;
SELECT * FROM BANK_DATA;
TRUNCATE TABLE BANK_DATA;
SELECT * FROM ARB_DATA;

UPDATE AGENCYS SET STATUS = 1 WHERE agency_code=1234567890;
UPDATE AGENCYS SET STATUS = 1 WHERE STATUS=0;

ALTER TABLE PURCHASE_RETURN_DATA ADD COLUMN INV_REF_DATE BIGINT NOT NULL;
ALTER TABLE PURCHASE_RETURN_DATA DROP COLUMN INV_REF_DATE;
ALTER TABLE PURCHASE_RETURN_DATA ADD COLUMN INV_REF_DATE BIGINT NOT NULL AFTER INV_DATE;


SELECT * FROM DOM_SALES_INVOICES where CREATED_BY = 5555555555 AND DELETED = 0 AND CUSTOMER_ID = 1000024 AND SI_DATE BETWEEN 1498847400000 AND 1518777632000 AND PAYMENT_STATUS < 3;
SELECT * FROM COM_SALES_INVOICES;

SELECT * FROM DOM_SALES_INVOICES_DETAILS where DSI_ID = 1000056 and PROD_CODE = 2;

SELECT * FROM DOM_SALES_INVOICES WHERE CREATED_BY = 87654321 AND ID > 1000175;


CREATE TABLE SAMPLE (
	ID INT(10) NOT NULL,
	NAME VARCHAR(50) NOT NULL,
	CONSTRAINT SAMPLE PRIMARY KEY (ID)
);

insert into SAMPLE(ID,NAME,MAIL_ID,SAL) values(1,'devi2','devi@gmail.com',1000);
ALTER TABLE SAMPLE
ADD COLUMN SAL INT(6) NOT NULL AFTER NAME;
ALTER TABLE SAMPLE
ADD COLUMN MAIL_ID VARCHAR(12) NOT NULL AFTER NAME;
ALTER TABLE SAMPLE
ADD COLUMN MOBILE VARCHAR(12)  NULL AFTER MAIL_ID;
ALTER TABLE SAMPLE
ADD COLUMN SAL2 INT(6) NULL AFTER MOBILE;

SELECT * FROM AGENCY_DETAILS;
UPDATE AGENCY_DETAILS SET EFFECTIVE_DATE=1520489811036;

SELECT * from ARB_PURCHASE_INVS where created_by = 1111111111 and deleted = 0 and vendor_id = 1000011 and inv_date between 1498847400000 and 1522175400000 and payment_status != 3;
SELECT * from DOM_SALES_INVOICES where created_by = 9999999999 and deleted = 0 and customer_id = 1000068 and si_date between 1498847400000 and 1522089000000 and payment_status < 3;

SELECT * from DOM_SALES_INVOICES;


where created_by = 1111111111 and deleted = 0 and customer_id = 1000099 and si_date between 1498847400000 and 1522521000000 and payment_status < 3;

SELECT * FROM AGENCYS;
UPDATE AGENCYS set EMAIL_ID = "priya@wb.com" where AGENCY_CODE = 12345123;

SELECT * FROM ACCOUNT_ACTIVATION;

SELECT CASH_BALANCE FROM AGENCY_CASH_DATA WHERE CREATED_BY = 109109109 and T_DATE <= 1523318400000 order by ID desc limit 1;

SELECT * FROM AGENCYS;
UPDATE AGENCYS set STATUS = 1 where AGENCY_CODE = 36363636;
SELECT * FROM AGENCY_DETAILS where created_by = 1234512345;
SELECT * FROM GST_PAYMENT_DETAILS WHERE CREATED_BY = 1111111111 ORDER BY CREATED_DATE DESC LIMIT 1;
SELECT * FROM GST_PAYMENT_DETAILS WHERE CREATED_BY = 1234567890;

SELECT * FROM BANK_DATA;
INSERT INTO BANK_DATA(BANK_CODE,BANK_NAME,BANK_ACC_NO,BANK_BRANCH,BANK_IFSC_CODE,ACC_OB,ACC_CB,BANK_ADDR,CREATED_BY,CREATED_DATE,MODIFIED_BY,
	MODIFIED_DATE,VERSION,DELETED) VALUES ("TAR ACCOUNT","50",15931593,"SANDOZ","HDFC0006","0.00","0.00","SANDOZ",15931593,NOW(),0,0,1,0);
	
INSERT INTO BANK_DATA(BANK_CODE,BANK_NAME,BANK_ACC_NO,BANK_BRANCH,BANK_IFSC_CODE,ACC_OB,ACC_CB,BANK_ADDR,CREATED_BY,CREATED_DATE,MODIFIED_BY,
	MODIFIED_DATE,VERSION,DELETED) VALUES ("STAR ACCOUNT","50",15931593,"SANDOZ","HDFC0006","0.00","0.00","SANDOZ",15931593,NOW(),0,0,1,0);	
		

SELECT * FROM GST_PAYMENT_DETAILS WHERE CREATED_BY = 87654321 and (deleted = 0 OR deleted = 2) and pdate between 1525113000000 and 1526409000000;

SELECT COUNT(*) FROM GST_PAYMENT_DETAILS WHERE CREATED_BY = 9999999999 AND (DELETED = 0 OR DELETED = 2) AND PDATE < 1525132800000;
SELECT COUNT(*) FROM GST_PAYMENT_DETAILS WHERE CREATED_BY = 9999999999 AND (DELETED = 0 OR DELETED = 2) AND PDATE < 1526342400000;

update GST_PAYMENTS_DATA set status = 1 where created_by = 9999999999 and status = 2;
delete from GST_PAYMENT_DETAILS where created_by = 9999999999;

INSERT INTO SERVICES_DATA (ID,PROD_CODE,PROD_CHARGES,GST_AMT,SAC_CODE,EFFECTIVE_DATE,CREATED_BY,CREATED_DATE,MODIFIED_BY,MODIFIED_DATE,VERSION,DELETED) 
	VALUES (1000010,27,50.00,9.00,'998399',1498890600000,0,0,0,0,0,0);
	
UPDATE SERVICES_DATA SET EFFECTIVE_DATE = 1522540800000 WHERE CREATED_BY=CREATED_BY;

SELECT * FROM AGENCY_CASH_DATA WHERE CREATED_BY = 9999999999 and INV_NO = "SI-18-0061";


mysql> SELECT dd.ID, dd.DSI_ID, d.SR_NO, dd.PROD_CODE, dd.QUANTITY, dd.PRE_CYLS, dd.PSV_CYLS, dd.CS_FULLS, dd.CS_EMPTYS, dd.DS_FULLS, dd.DS_EMPTYS, dd.DSS_FULLS, dd.DSS_EMPTYS FROM DOM_SALES_INVOICES d,DOM_SALES_INVOICES_DETAILS dd  WHERE d.ID=dd.DSI_ID AND dd.DSI_ID > 1000287 AND d.CREATED_BY=1313131313 ;

+---------+---------+------------+-----------+----------+----------+----------+----------+-----------+----------+-----------+-----------+------------+
| ID      | DSI_ID  | SR_NO      | PROD_CODE | QUANTITY | PRE_CYLS | PSV_CYLS | CS_FULLS | CS_EMPTYS | DS_FULLS | DS_EMPTYS | DSS_FULLS | DSS_EMPTYS |
+---------+---------+------------+-----------+----------+----------+----------+----------+-----------+----------+-----------+-----------+------------+
| 1000334 | 1000290 | SI-18-0032 |         1 |        2 |        0 |        1 |      476 |       517 |        0 |         0 |       476 |        517 |
| 1000335 | 1000290 | SI-18-0032 |         2 |        1 |        0 |        0 |      481 |       515 |        0 |         0 |       481 |        515 |
| 1000336 | 1000291 | SI-18-0033 |         3 |        1 |        0 |        1 |      486 |       508 |        0 |         0 |       486 |        508 |
| 1000337 | 1000292 | SI-18-0034 |         3 |        2 |        1 |        0 |      484 |       510 |        0 |         0 |       484 |        510 |
| 1000339 | 1000294 | SI-18-0035 |         2 |        2 |        0 |        0 |      479 |       517 |      475 |       519 |       479 |        517 |
| 1000340 | 1000294 | SI-18-0035 |         3 |        2 |        0 |        1 |      482 |       511 |      481 |       512 |       482 |        511 |
| 1000362 | 1000307 | SI-18-0036 |         2 |        2 |        0 |        1 |      477 |       518 |        0 |         0 |       477 |        518 |
| 1000363 | 1000307 | SI-18-0036 |         3 |        1 |        0 |        0 |      481 |       512 |        0 |         0 |       485 |        510 |
| 1000364 | 1000308 | SI-18-0037 |         2 |        3 |        0 |        1 |      474 |       520 |      473 |       521 |       474 |        520 |
| 1000365 | 1000309 | SI-18-0038 |         2 |        2 |        0 |        0 |      472 |       522 |        0 |         0 |       475 |        520 |
| 1000366 | 1000310 | SI-18-0039 |         3 |        2 |        0 |        1 |      479 |       513 |        0 |         0 |       483 |        511 |
| 1000367 | 1000310 | SI-18-0039 |         1 |        2 |        0 |        0 |      474 |       519 |        0 |         0 |       474 |        519 |
| 1000368 | 1000311 | SI-18-0040 |         2 |        2 |        0 |        1 |      470 |       523 |        0 |         0 |       473 |        521 |
+---------+---------+------------+-----------+----------+----------+----------+----------+-----------+----------+-----------+-----------+------------+


mysql> SELECT dd.ID,dd.DSI_ID,d.SR_NO,dd.PROD_CODE,dd.QUANTITY,dd.PRE_CYLS,dd.PSV_CYLS,dd.CS_FULLS,dd.CS_EMPTYS,dd.DS_FULLS,dd.DS_EMPTYS,dd.DSS_FULLS,dd.DSS_EMPTYS,d.DELETED FROM DOM_SALES_INVOICES d,DOM_SALES_INVOICES_DETAILS dd  WHERE d.ID=dd.DSI_ID AND dd.DSI_ID > 1000287 AND d.CREATED_BY=1313131313 AND dd.PROD_CODE=2 ;
+---------+---------+------------+-----------+----------+----------+----------+----------+-----------+----------+-----------+-----------+------------+---------+
| ID      | DSI_ID  | SR_NO      | PROD_CODE | QUANTITY | PRE_CYLS | PSV_CYLS | CS_FULLS | CS_EMPTYS | DS_FULLS | DS_EMPTYS | DSS_FULLS | DSS_EMPTYS | DELETED |
+---------+---------+------------+-----------+----------+----------+----------+----------+-----------+----------+-----------+-----------+------------+---------+
| 1000335 | 1000290 | SI-18-0032 |         2 |        1 |        0 |        0 |      481 |       515 |        0 |         0 |       481 |        515 |       0 |
| 1000339 | 1000294 | SI-18-0035 |         2 |        2 |        0 |        0 |      479 |       517 |      475 |       519 |       479 |        517 |       3 |
| 1000362 | 1000307 | SI-18-0036 |         2 |        2 |        0 |        1 |      477 |       518 |        0 |         0 |       477 |        518 |       0 |
| 1000364 | 1000308 | SI-18-0037 |         2 |        3 |        0 |        1 |      474 |       520 |      473 |       521 |       474 |        520 |       3 |
| 1000365 | 1000309 | SI-18-0038 |         2 |        2 |        0 |        0 |      472 |       522 |        0 |         0 |       475 |        520 |       0 |
| 1000368 | 1000311 | SI-18-0040 |         2 |        2 |        0 |        1 |      470 |       523 |        0 |         0 |       473 |        521 |       0 |
+---------+---------+------------+-----------+----------+----------+----------+----------+-----------+----------+-----------+-----------+------------+---------+
6 rows in set (0.00 sec)

mysql> SELECT dd.ID,dd.DSI_ID,d.SR_NO,dd.PROD_CODE,dd.QUANTITY,dd.PRE_CYLS,dd.PSV_CYLS,dd.CS_FULLS,dd.CS_EMPTYS,dd.DS_FULLS,dd.DS_EMPTYS,dd.DSS_FULLS,dd.DSS_EMPTYS,d.DELETED FROM DOM_SALES_INVOICES d,DOM_SALES_INVOICES_DETAILS dd  WHERE d.ID=dd.DSI_ID AND dd.DSI_ID > 1000287 AND d.CREATED_BY=1313131313;
+---------+---------+------------+-----------+----------+----------+----------+----------+-----------+----------+-----------+-----------+------------+---------+
| ID      | DSI_ID  | SR_NO      | PROD_CODE | QUANTITY | PRE_CYLS | PSV_CYLS | CS_FULLS | CS_EMPTYS | DS_FULLS | DS_EMPTYS | DSS_FULLS | DSS_EMPTYS | DELETED |
+---------+---------+------------+-----------+----------+----------+----------+----------+-----------+----------+-----------+-----------+------------+---------+
| 1000334 | 1000290 | SI-18-0032 |         1 |        2 |        0 |        1 |      476 |       517 |        0 |         0 |       476 |        517 |       0 |
| 1000335 | 1000290 | SI-18-0032 |         2 |        1 |        0 |        0 |      481 |       515 |        0 |         0 |       481 |        515 |       0 |
| 1000336 | 1000291 | SI-18-0033 |         3 |        1 |        0 |        1 |      486 |       508 |        0 |         0 |       486 |        508 |       0 |
| 1000337 | 1000292 | SI-18-0034 |         3 |        2 |        1 |        0 |      484 |       510 |        0 |         0 |       484 |        510 |       0 |
| 1000339 | 1000294 | SI-18-0035 |         2 |        2 |        0 |        0 |      479 |       517 |      475 |       519 |       479 |        517 |       3 |
| 1000340 | 1000294 | SI-18-0035 |         3 |        2 |        0 |        1 |      482 |       511 |      481 |       512 |       482 |        511 |       3 |
| 1000362 | 1000307 | SI-18-0036 |         2 |        2 |        0 |        1 |      477 |       518 |        0 |         0 |       477 |        518 |       0 |
| 1000363 | 1000307 | SI-18-0036 |         3 |        1 |        0 |        0 |      481 |       512 |        0 |         0 |       485 |        510 |       0 |
| 1000364 | 1000308 | SI-18-0037 |         2 |        3 |        0 |        1 |      474 |       520 |      473 |       521 |       474 |        520 |       3 |
| 1000365 | 1000309 | SI-18-0038 |         2 |        2 |        0 |        0 |      472 |       522 |        0 |         0 |       475 |        520 |       0 |
| 1000366 | 1000310 | SI-18-0039 |         3 |        2 |        0 |        1 |      479 |       513 |        0 |         0 |       483 |        511 |       0 |
| 1000367 | 1000310 | SI-18-0039 |         1 |        2 |        0 |        0 |      474 |       519 |        0 |         0 |       474 |        519 |       0 |
| 1000368 | 1000311 | SI-18-0040 |         2 |        2 |        0 |        1 |      470 |       523 |        0 |         0 |       473 |        521 |       0 |
+---------+---------+------------+-----------+----------+----------+----------+----------+-----------+----------+-----------+-----------+------------+---------+
13 rows in set (0.00 sec)



TRUNCATE TABLE SERVICES_DATA;
TRUNCATE TABLE PRODUCT_CATEGORY_DATA;