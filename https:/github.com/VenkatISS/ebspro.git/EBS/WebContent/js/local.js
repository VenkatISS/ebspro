//Bank Data
var bndatahtml = "";
var emhdatahtml = "";
var eshdatahtml = "";
var uomdatahtml = "";
var ahsdatahtml = "";
var opahsdatahtml = "";

var banknames = ["SELECT","ABHYUDAYA COOPERATIVE BANK LIMITED","ABU DHABI COMMERCIAL BANK","AHMEDABAD MERCANTILE COOPERATIVE BANK","AKOLA JANATA COMMERCIAL COOPERATIVE BANK",
                 "ALLAHABAD BANK","ALMORA URBAN COOPERATIVE BANK LIMITED","ANDHRA BANK","ANDHRA PRAGATHI GRAMEENA BANK","APNA SAHAKARI BANK LIMITED",
                 "AUSTRALIA AND NEW ZEALAND BANKING GROUP LIMITED","AXIS BANK","B N P PARIBAS","BANDHAN BANK LIMITED","BANK OF AMERICA","BANK OF BAHARAIN AND KUWAIT BSC",
                 "BANK OF BARODA","BANK OF CEYLON","BANK OF INDIA","BANK OF MAHARASHTRA","BANK OF TOKYO MITSUBISHI LIMITED","BARCLAYS BANK",
                 "BASSEIN CATHOLIC COOPERATIVE BANK LIMITED","BHARAT COOPERATIVE BANK MUMBAI LIMITED","BHARATIYA MAHILA BANK LIMITED","CANARA BANK",
                 "CAPITAL LOCAL AREA BANK LIMITED","CATHOLIC SYRIAN BANK LIMITED","CENTRAL BANK OF INDIA","CHINATRUST COMMERCIAL BANK LIMITED","CITI BANK",
                 "CITIZEN CREDIT COOPERATIVE BANK LIMITED","CITY UNION BANK LIMITED","COMMONWEALTH BANK OF AUSTRALIA","CORPORATION BANK",
                 "CREDIT AGRICOLE CORPORATE AND INVESTMENT BANK CALYON BANK","CREDIT SUISEE AG","DCB BANK LIMITED","DENA BANK","DEOGIRI NAGARI SAHAKARI BANK LTD. AURANGABAD",
                 "DEPOSIT INSURANCE AND CREDIT GUARANTEE CORPORATION","DEUSTCHE BANK","DEVELOPMENT BANK OF SINGAPORE","DHANALAKSHMI BANK","DOHA BANK QSC",
                 "DOMBIVLI NAGARI SAHAKARI BANK LIMITED","EXPORT IMPORT BANK OF INDIA","FEDERAL BANK","FIRSTRAND BANK LIMITED","G P PARSIK BANK","HDFC BANK",
                 "HIMACHAL PRADESH STATE COOPERATIVE BANK LTD","HSBC BANK","ICICI BANK LIMITED","IDBI BANK","IDFC BANK LIMITED","INDIAN BANK","INDIAN OVERSEAS BANK",
                 "INDUSIND BANK","INDUSTRIAL AND COMMERCIAL BANK OF CHINA LIMITED","INDUSTRIAL BANK OF KOREA","ING VYSYA BANK","JALGAON JANATA SAHAKARI BANK LIMITED",
                 "JAMMU AND KASHMIR BANK LIMITED","JANAKALYAN SAHAKARI BANK LIMITED","JANASEVA SAHAKARI BANK BORIVLI LIMITED","JANASEVA SAHAKARI BANK LIMITED",
                 "JANATA SAHAKARI BANK LIMITED","JP MORGAN BANK","KALLAPPANNA AWADE ICHALKARANJI JANATA SAHAKARI BANK LTD","KALUPUR COMMERCIAL COOPERATIVE BANK",
                 "KALYAN JANATA SAHAKARI BANK","KAPOL COOPERATIVE BANK LIMITED","KARNATAKA BANK LIMITED","KARNATAKA VIKAS GRAMEENA BANK","KARUR VYSYA BANK",
                 "KEB Hana Bank","KERALA GRAMIN BANK","KOTAK MAHINDRA BANK LIMITED","LAXMI VILAS BANK","MAHANAGAR COOPERATIVE BANK","MAHARASHTRA STATE COOPERATIVE BANK",
                 "MASHREQBANK PSC","MIZUHO CORPORATE BANK LIMITED","NAGAR URBAN CO OPERATIVE BANK","NAGPUR NAGARIK SAHAKARI BANK LIMITED","NATIONAL AUSTRALIA BANK LIMITED",
                 "NATIONAL BANK OF ABU DHABI PJSC","NEW INDIA COOPERATIVE BANK LIMITED","NKGSB COOPERATIVE BANK LIMITED","NUTAN NAGARIK SAHAKARI BANK LIMITED",
                 "ORIENTAL BANK OF COMMERCE","PRAGATHI KRISHNA GRAMIN BANK","PRATHAMA BANK","PRIME COOPERATIVE BANK LIMITED","PT BANK MAYBANK INDONESIA TBK",
                 "PUNJAB AND MAHARSHTRA COOPERATIVE BANK","PUNJAB AND SIND BANK","PUNJAB NATIONAL BANK","RABOBANK INTERNATIONAL","RAJGURUNAGAR SAHAKARI BANK LIMITED",
                 "RAJKOT NAGRIK SAHAKARI BANK LIMITED","RBL Bank Limited","RESERVE BANK OF INDIA","SAHEBRAO DESHMUKH COOPERATIVE BANK LIMITED","SAMARTH SAHAKARI BANK LTD",
                 "SARASWAT COOPERATIVE BANK LIMITED","SBER BANK","SBM BANK MAURITIUS LIMITED","SHIKSHAK SAHAKARI BANK LIMITED","SHINHAN BANK",
                 "SHIVALIK MERCANTILE CO OPERATIVE BANK LTD","SHRI CHHATRAPATI RAJASHRI SHAHU URBAN COOPERATIVE BANK LTD","SOCIETE GENERALE",
                 "SOLAPUR JANATA SAHAKARI BANK LIMITED","SOUTH INDIAN BANK","STANDARD CHARTERED BANK","STATE BANK OF BIKANER AND JAIPUR","STATE BANK OF HYDERABAD",
                 "STATE BANK OF INDIA","STATE BANK OF MYSORE","STATE BANK OF PATIALA","STATE BANK OF TRAVANCORE","SUMITOMO MITSUI BANKING CORPORATION",
                 "SURAT NATIONAL COOPERATIVE BANK LIMITED","SUTEX COOPERATIVE BANK LIMITED","SYNDICATE BANK","TAMILNAD MERCANTILE BANK LIMITED",
                 "THE A.P. MAHESH COOPERATIVE URBAN BANK LIMITED","THE AKOLA DISTRICT CENTRAL COOPERATIVE BANK","THE ANDHRA PRADESH STATE COOPERATIVE BANK LIMITED",
                 "THE BANK OF NOVA SCOTIA","THE COSMOS CO OPERATIVE BANK LIMITED","THE DELHI STATE COOPERATIVE BANK LIMITED",
                 "THE GADCHIROLI DISTRICT CENTRAL COOPERATIVE BANK LIMITED","THE GREATER BOMBAY COOPERATIVE BANK LIMITED","THE GUJARAT STATE COOPERATIVE BANK LIMITED",
                 "THE HASTI COOP BANK LTD","THE JALGAON PEOPELS COOPERATIVE BANK LIMITED","THE KANGRA CENTRAL COOPERATIVE BANK LIMITED","THE KANGRA COOPERATIVE BANK LIMITED",
                 "THE KARAD URBAN COOPERATIVE BANK LIMITED","THE KARANATAKA STATE COOPERATIVE APEX BANK LIMITED","THE KURMANCHAL NAGAR SAHAKARI BANK LIMITED",
                 "THE MEHSANA URBAN COOPERATIVE BANK","THE MUMBAI DISTRICT CENTRAL COOPERATIVE BANK LIMITED","THE MUNICIPAL COOPERATIVE BANK LIMITED","THE NAINITAL BANK LIMITED",
                 "THE NASIK MERCHANTS COOPERATIVE BANK LIMITED","THE PANDHARPUR URBAN CO OP. BANK LTD. PANDHARPUR","THE RAJASTHAN STATE COOPERATIVE BANK LIMITED",
                 "THE ROYAL BANK OF SCOTLAND N V","THE SEVA VIKAS COOPERATIVE BANK LIMITED","THE SHAMRAO VITHAL COOPERATIVE BANK","THE SURAT DISTRICT COOPERATIVE BANK LIMITED",
                 "THE SURATH PEOPLES COOPERATIVE BANK LIMITED","THE TAMIL NADU STATE APEX COOPERATIVE BANK","THE THANE BHARAT SAHAKARI BANK LIMITED",
                 "THE THANE DISTRICT CENTRAL COOPERATIVE BANK LIMITED","THE VARACHHA COOPERATIVE BANK LIMITED","THE VISHWESHWAR SAHAKARI BANK LIMITED",
                 "THE WEST BENGAL STATE COOPERATIVE BANK","THE ZOROASTRIAN COOPERATIVE BANK LIMITED","TJSB SAHAKARI BANK LTD","TUMKUR GRAIN MERCHANTS COOPERATIVE BANK LIMITED",
                 "UCO BANK","UNION BANK OF INDIA","UNITED BANK OF INDIA","UNITED OVERSEAS BANK LIMITED","VASAI VIKAS SAHAKARI BANK LIMITED","VIJAYA BANK",
                 "WESTPAC BANKING CORPORATION","WOORI BANK","YES BANK","ZILA SAHAKRI BANK LIMITED GHAZIABAD","OTHERS"];

//Construct Bank Names html
if(banknames.length>0) {
	for(var z=0; z<banknames.length; z++){
		bndatahtml=bndatahtml+"<OPTION VALUE='"+z+"'>"+banknames[z]+"</OPTION>";
	}
}

var emhnames = ["SELECT","Delivery Expenses (freight outwards)","Inward Transportation charges (Freight Inwards)","Utility Charges","Licenses & Statutory expenditure",
                "Puja expenditure","Stationery and Postage","Travel / conveyance Expenses","Rent","Business promotion","Staff welfare","Godown/Office maintenance",
                "Insurance","Misc Expenditure"];

/*var eshvalues = ["0","-1","11","12","13","14","15","-2","21","22","23","24","25","-3","31","32","33","34","35","-4","41","42","43","44","-5","51","-6","61","62",
                 "-7","71","72","73","-8","81","82","-9","91","-10","101","102","103","104","-11","111","112","113","-12","121","-13","131","-14","141","142","143",
                 "144","145","146","147","151","152","153"];
var eshnames = ["SELECT","Delivery Expenses (freight outwards)","Delivery boy Commission","Delivery Vehicle rent","Delivery Vehicle maintenance",
                "Fuel Charges for Delivery Vehicles","Outward Tolls and Taxes","Inward Transportation charges (Freight Inwards)","Transportation charges paid to truck owner",
                "Unloading charges","Truck maintenance","Inward Tolls and Taxes","Fuel charges for delivery vehicles","Utility Charges","Electricity bill",
                "Telephone Bill","Water bill","Internet bill","News paper bill","Licenses and Statutory expenditure","Renewal charges for licenses",
                "Weighing scale certification charges","Fire extinguisher refilling charges","Annual subscriptions to Associations","Puja expenditure",
                "Expenditure Towards Pujas","Stationery and Postage","Printing cost of stationery","Postage/courier charges","Travel / conveyance Expenses",
                "Travel expenses of dealer/staff for official purpose","Fuel charges for Car/bike (Apart from delivery usage)","Car/bike maintenance costs",
                "Rent","Rent paid to Godown","Rent paid to Office","Business promotion","All expenditure towards Business Promotion","Staff welfare",
                "Tea, coffee and Food expenditure","Uniforms","Staff recreation","All staff welfare expenditure","Godown/Office maintenance",
                "Mastic Flooring","Replacement of wire mesh","Any minor civil works/repairs at godown and Office","Insurance","LPG dealer insurance",
                "Misc Expenditure","All other expenditures","Fixed Assets","Land","Building","Plant and Machinary","Furniture and Fixtures","Motar Vehicles",
                "Computers and Printers","Other Movable Assets","Salaries & Wages","Auditor fees","Interest paid"];*/


var eshvalues = ["0","-1","11","12","13","14","15","-2","21","22","23","24","25","-3","31","32","33","34","35","-4","41","42","43","44","-5","51","-6","61","62",
    "-7","71","72","73","-8","81","82","-9","91","-10","101","102","103","104","-11","111","112","113","-12","121","-13","131","132","133","134","-14","141","142","143",
    "144","145","146","147"];
var eshnames = ["SELECT","Delivery Expenses (freight outwards)","Delivery boy Commission","Delivery Vehicle rent","Delivery Vehicle maintenance",
   "Fuel Charges for Delivery Vehicles","Outward Tolls and Taxes","Inward Transportation charges (Freight Inwards)","Transportation charges paid to truck owner",
   "Unloading charges","Truck maintenance","Inward Tolls and Taxes","Fuel charges for delivery vehicles","Utility Charges","Electricity bill",
   "Telephone Bill","Water bill","Internet bill","News paper bill","Licenses and Statutory expenditure","Renewal charges for licenses",
   "Weighing scale certification charges","Fire extinguisher refilling charges","Annual subscriptions to Associations","Puja expenditure",
   "Expenditure Towards Pujas","Stationery and Postage","Printing cost of stationery","Postage/courier charges","Travel / conveyance Expenses",
   "Travel expenses of dealer/staff for official purpose","Fuel charges for Car/bike (Apart from delivery usage)","Car/bike maintenance costs",
   "Rent","Rent paid to Godown","Rent paid to Office","Business promotion","All expenditure towards Business Promotion","Staff welfare",
   "Tea, coffee and Food expenditure","Uniforms","Staff recreation","All staff welfare expenditure","Godown/Office maintenance",
   "Mastic Flooring","Replacement of wire mesh","Any minor civil works/repairs at godown and Office","Insurance","LPG dealer insurance",
   "Other Expenditures","Salaries & Wages","Auditor fees","Interest paid","All other misc expenditures","Fixed Assets","Land","Building","Plant and Machinary","Furniture and Fixtures","Motar Vehicles",
   "Computers and Printers","Other Movable Assets"];



//Construct Expenditure Major Head Names html
if(emhnames.length>0) {
	for(var z=0; z<emhnames.length; z++){
		emhdatahtml=emhdatahtml+"<OPTION VALUE='"+z+"'>"+emhnames[z]+"</OPTION>";
	}
}

if(eshnames.length>0) {
	for(var z=0; z<eshnames.length; z++){
		if(eshvalues[z]<0) {
			eshdatahtml=eshdatahtml+"<OPTION VALUE='"+eshvalues[z]+"' disabled>---"+eshnames[z]+"---</OPTION>";
		} else {
			eshdatahtml=eshdatahtml+"<OPTION VALUE='"+eshvalues[z]+"'>"+eshnames[z]+"</OPTION>";
		}
	}
}

var uoms = ["SELECT","BAG-BAGS","BOX-BOX","BTL-BOTTLES","CAN-CANS","CBM-CUBIC METERS","CTN-CARTONS","DOZ-DOZENS","GMS-GRAMMES","KGS-KILOGRAMS","KLR-KILOLITRE",
	"KME-KILOMETRE","MTR-METERS","MTS-METRIC TON","NOS-NUMBERS","PAC-PACKS","PCS-PIECES","QTL-QUINTAL","SET-SETS","SQF-SQUARE FEET","SQM-SQUARE METERS","SQY-SQUARE YARDS",
	"TON-TONNES","UNT-UNITS","YDS-YARDS","OTH-OTHERS"];

if(uoms.length>0){
	for(var z=0; z<uoms.length; z++){
		uomdatahtml=uomdatahtml+"<OPTION VALUE='"+z+"'>"+uoms[z]+"</OPTION>";
	}
}

var ahs = ["SELECT","Bank Account","Capital Account","Cash In Hand","Current Liabilities","Deposits(Assets)","Direct Expenses","Direct Income","Duties and Taxes",
	"Fixed Assets","Indirect Expenses","Indirect Income","Loans and Advances","Misc. Expenses","Provisions","Reserves and Surplus","Secured Loan","UnSecured Loan"];

if(ahs.length>0){
	for(var z=0; z<ahs.length; z++){
		ahsdatahtml=ahsdatahtml+"<OPTION VALUE='"+z+"'>"+ahs[z]+"</OPTION>";
	}
}

var opahs = ["SELECT","Current Liabilities","Deposits(Assets)","Direct Expenses","Direct Income","Fixed Assets",
	"Indirect Expenses","Indirect Income","Loans and Advances","Provisions","Reserves and Surplus","UnSecured Loan"];

if(opahs.length>0){
	for(var z=0; z<opahs.length; z++){
		opahsdatahtml=opahsdatahtml+"<OPTION VALUE='"+z+"'>"+opahs[z]+"</OPTION>";
	}
}

