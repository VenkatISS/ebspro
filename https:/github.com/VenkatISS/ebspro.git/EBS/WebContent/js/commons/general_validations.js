var inames = ["","Domestic Cylinders","Commercial Cylinders","NDNE Cylinders","Stoves","Tubes","Lighters","DGCC [PassBook]","Aprons","Trolleys","Others"];
var regex2d  = /^\d+(?:\.\d{0,2})$/;
var regex1d  = /^\d+(?:\.\d{0,1})$/;

// Removes leading whitespaces
function LTrim( value ) {
	var re = /\s*((\S+\s*)*)/;
	return value.replace(re, "$1");
}

// Removes ending whitespaces
function RTrim( value ) {
	var re = /((\s*\S+)*)\s*/;
	return value.replace(re, "$1");	
}

/*//Removes extra whitespaces within the sentence
function BTrim( value ) {	
	var re = / +(?= )/g;
	return value.replace(re,' ');	
//	string = string.replace(/\s+/g, " ");
}
*/

String.prototype.trim = function() {	
	return LTrim(RTrim(this));
	//return this.replace(/^\s+|\s+$/, ''); 
	//return this.replace(/^\s+|\s+$/g, "");	
};

function round(value, decimals) {
	  return Number(Math.round(value+'e'+decimals)+'e-'+decimals);
}

function checkAlphabets(val){
	return (/^[a-zA-Z ]*$/.test(val));
	
}


function checkEmail(emailV) {
	var regemail=/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	if (regemail.test(emailV)){
	return true;
	}
	return false;
}

function validateNumber(fieldV, min, max) { 
	if (!min) { min = 10 } 
	if (!max) { max = 10 } 

	if ( (parseInt(fieldV) != fieldV) ||  
	       fieldV.length < min || fieldV.length > max) { 
	return false; 
	} 

	return true; 
}

function IsName( value ) {
	
	return (/[a-z]+([\sa-z]*)/i.test(value)); 

	//return (/^[a-z]+ ?[a-z]+$/i).test(value);
}

function IsNameSpace( value ) { 
	return (/^[a-z]+ ?[a-z]+$/i).test(value) 
}
/*function IsNameSpaceDot(val) {
    return (/^[a-zA-Z .]*$/i).test(val);
}*/
function IsNameSpaceDot(val) {
    return (/^[a-zA-Z]+[a-zA-Z .]*$/i).test(val);
}

function IsText(value) {
	return (/^[A-Z ]+$/i).test(value) 
}

function checkTextLength(value,min,max){
	
    if(value.length >= min && value.length <= max)
        return true;
    else 
    	return false;
}

function validateAmount(val){
	return ( (/[0-9][.][0-9]{2}$/).test(val) || (/[0-9][.][0-9]{1}$/).test(val) || (validateNumber(val,1,12)));
}

function validateAlphaNumeric(val) {
	return (/^[a-zA-Z0-9 ]*$/i).test(val);
}

function validatePercentage(val){
	return ( (/[0-9][.][0-9]{2}$/).test(val) || (/[0-9][.][0-9]{1}$/).test(val) || (validateNumber(val,1,2)));
}

function IsNumeric(strString)
{
var strValidChars = "0123456789";
var strChar;
var blnResult = true;

if (strString.length == 0) return false;

//  test strString consists of valid characters listed above
for (i = 0; i < strString.length && blnResult == true; i++)
   {
   strChar = strString.charAt(i);
   if (strValidChars.indexOf(strChar) == -1)
      {
      blnResult = false;
      }
   }
return blnResult;
}

function validateDecimalNumberMinMax(fieldV, min, max) { 
	var regx=/^(\d+)?([.]?[0-9]*)?$/;
	if(!regx.test(fieldV)) 
		return false;
	else if((parseFloat(fieldV) <= min) || (parseFloat(fieldV) >= max))
		return false;
	
	return true; 
}
function validateNumberMinMax(fieldV,min,max)
{
	var regx = /^[0-9]*$/;
    if(!regx.test(fieldV)) 
		return false;
	else if((parseFloat(fieldV) <= min) || (parseFloat(fieldV) >= max))
		return false;
	
	return true; 
}

function validateDecimalPrecision(val) {
	return (/^(\d+)?([.]?\d{0,2})?$/).test(val);
}


function validateDot(val) {
	return (/^\.*$/).test(val);
}


function isDecimalNumber(val) {
	return (/^(\d+)?([.]?[0-9]*)?$/).test(val);
}      


function checkNumber(e)
{
    var regex=/^[0-9]+$/;
    if (!regex.test(e))
    {
        return false;
    }
    return true;
}

function validateNegativesForDecimals(val) {
	return (/^\-+[1-9]\d{0,2}(\.\d*)?$/).test(val); 
}

function validateNagatives(val) {
	/^-[0-9]\d*(\.\d+)?$/
}

function validateNegatives(val) {
	return (/^-+[0-9]*$/).test(val);
}      

function checkPAN(input) {
	var pancardPattern = /^([A-Z]{5})(\d{4})([A-Z]{1})$/;
	if(pancardPattern.test(input))
		return true;
	return false;
}

function validateCorrectGSTINFromPAN(tin, pan) {
	var tpan = tin.substr(2, 10);
	if (tpan.localeCompare(pan) == 0)
		return true;
	return false;
}

function checkGSTIN(input) {
	//var pancardPattern = /^(\d{2})([A-Z]{5})(\d{4})([A-Z]{1})(([0-9]|[A-Z]){1})(Z{1})(([0-9]|[A-Z]){1})$/;
	var pancardPattern = /^([0-3]{1}[1-7]{1}|08|09|10|18|19|20|29|30|97)([A-Z]{5})(\d{4})([A-Z]{1})(([0-9]|[A-Z]){1})(Z{1})(([0-9]|[A-Z]){1})$/;
	if(pancardPattern.test(input))
		return true;
	return false;
}

function checkIFSC(input) {
	var ifscPattern = /^([A-Z]{4})(0{1})(\d{6}|[A-Z]{6}|[0-9A-Z]{6})$/;
	if(ifscPattern.test(input))
		return true;
	return false;
}

function checkInteger(input) {
	var pattern = /^-?(\d+)?([.]?[0-9]*)?$/;
	if(pattern.test(input))
		return true;
	return false;
}
function validateBothAlphaNumeric(val) {
	return (/^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$/).test(val);
}

//pin number
function validatePinNumber(fieldV, min, max) {
        if (!min) { min = 4 }
        if (!max) { max = 4 }

        if ( (parseInt(fieldV) != fieldV) ||
               fieldV.length < min || fieldV.length > max) {
        return false;
        }

        return true;
}

function checkDateFormate(ed)
{
	//dd/mm/yyyy
	var regex = /^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$/g;
    if (!regex.test(ed))
    {
        return false;
    }
    return true;
}

function checkdate(input){
//	var validformat=/^\d{2}\-\d{2}\-\d{4}$/; //Basic check for format validity 	//working

	var validformat = /^(19|20)\d\d[\/\-\.](0[1-9]|1[012])[\/\-\.](0[1-9]|[12][0-9]|3[01])$/;  // working yyyymmdd

	var returnval="false";
	
	if (!validformat.test(input)) {	
		returnval="Invalid Date Format.Must be in the format of YYYY-MM-DD or YYYY/MM/DD. Please correct and submit again.";
		return returnval;
		//return false;
	}else { //Detailed check for valid date ranges
		
		var monthfield=input.split(/[/.-]+/)[1];
		var dayfield=input.split(/[/.-]+/)[2];
		var yearfield=input.split(/[/.-]+/)[0];
		
		var dayobj = new Date(yearfield, monthfield-1, dayfield);
		
		if ((dayobj.getMonth()+1!=monthfield)||(dayobj.getDate()!=dayfield)||(dayobj.getFullYear()!=yearfield)) {
			returnval="Invalid Day, Month, or Year range detected. Please correct and submit again.";
			return returnval;
			//return false;
		} else
			returnval = "true";
		}
		return returnval;
	
}

function dateConvert(ed){
	//converting date format from dd/mm/yyyy to yyyy/mm/dd(no need for Chrome default it Converts.)
	var chkFrom = ed;
	var d=new Date(chkFrom.split(/[/.-]+/).reverse().join("-"));
	var dd=d.getDate();
	if(dd<10){
		dd="0"+dd;
	}
	var mm=d.getMonth()+1;
	if(mm<10){
		mm="0"+mm;
	}
	var yy=d.getFullYear();
	var newdate=yy+"-"+mm+"-"+dd;
	return newdate;	
}

function isValidDate(cd) { 
	// JULY MONTH VALIDATION it will not accpet before july'2017.
    var todate, dt1, dt2, mon1, mon2, yr1, yr2, date1, date2;
  // cd is fromdate
    var resp = checkdate(cd);
    var rep="false";
    
        if (resp != "true") {
             return resp;
        }
        else {
            todate = "2017-07-01";
            dt1 = parseInt(cd.substring(8, 10), 10);
            mon1 = parseInt(cd.substring(5, 7), 10);
            yr1 = parseInt(cd.substring(0, 4), 10);
            dt2 = parseInt(todate.substring(8, 10), 10);
            mon2 = parseInt(todate.substring(5, 7), 10);
            yr2 = parseInt(todate.substring(0, 4), 10);
            date1 = new Date(yr1, mon1-1, dt1);
            date2 = new Date(yr2, mon2-1, dt2);

            if (date2 > date1) {
                rep="DATE is valid only after JUNE 30,2017";
            	return rep;
            }        
        }
    return rep;
}

/*function ValidateFutureDate(cd) {
	//it will not accept future date and accepts present, past dates.
    var fromdate, todate, dt1, dt2, mon1, mon2, yr1, yr2, date1, date2;
    // cd = fromdate
    var resp = checkdate(cd);
    var rep="false";
    
        if (resp != "true") {
             return resp;
        }
        else {
            todate = new Date();
            dt1 = parseInt(cd.substring(8, 10), 10);
            mon1 = parseInt(cd.substring(5, 7), 10);
            yr1 = parseInt(cd.substring(0, 4), 10);
            dt2 = todate.getDate();
            mon2 = todate.getMonth()+1;
            yr2 = todate.getFullYear();
            date1 = new Date(yr1, mon1, dt1); //given date
            date2 = new Date(yr2, mon2, dt2);//todays date

            if (date2 < date1) {
            	rep=" Date cant be future date."
                return rep;
            }
        }
    
    return rep;
}
*/
function ValidateFutureDate(cd) {
	//it will not accept future date and accepts present, past dates.
    var fromdate, todate, dtOne, dt2, monOne, mon2, yrOne, yr2, date1, date2;
    // cd = fromdate
    var resp = checkdate(cd);
    var rep="false";
    
        if (resp != "true") {
             return resp;
        }
        else {
            todate = new Date();
            dtOne = parseInt(cd.substring(8, 10), 10);
            monOne = parseInt(cd.substring(5, 7), 10);
            yrOne = parseInt(cd.substring(0, 4), 10);
            dt2 = todate.getDate();
            mon2 = todate.getMonth();
            yr2 = todate.getFullYear();
            date1 = new Date(yrOne, monOne-1, dtOne); //given date
            date2 = new Date(yr2, mon2, dt2);//todays date

            if (date2 < date1) {
            	rep=" Date cant be future date."
                return rep;
            }
        }
    return rep;
}
function ValidatePresentAndPastDate(cd) {
	//it will not accept past, present dates and accepts future date.
    var todate, dt1, dt2, mon1, mon2, yr1, yr2, date1, date2;
    // cd = fromdate
    var resp = checkdate(cd);
    var rep="false";
    
        if (resp != "true") {
             return resp;
        }
        else {
            todate = new Date();
            dt1 = parseInt(cd.substring(8, 10), 10);
            mon1 = parseInt(cd.substring(5, 7), 10);
            yr1 = parseInt(cd.substring(0, 4), 10);
            dt2 = todate.getDate();
            mon2 = todate.getMonth();
            yr2 = todate.getFullYear();
            date1 = new Date(yr1, mon1-1, dt1);
            date2 = new Date(yr2, mon2, dt2);

            if (date2 >= date1) {
                rep = "Please Enter Future Date.";
            	return rep;
            }
        }
    
    return rep;
}


function comparisionofTwoDates(fd,td) {
	//comparision of two date fields
    var dt1, dt2, mon1, mon2, yr1, yr2, date1, date2;

    var resp = checkdate(td);
    var rep="false";
    
        if (resp != "true") {
             return resp;
        }
        else {
            dt1 = parseInt(fd.substring(8, 10), 10);
            mon1 = parseInt(fd.substring(5, 7), 10);
            yr1 = parseInt(fd.substring(0, 4), 10);
            dt2 = parseInt(td.substring(8, 10), 10);
            mon2 = parseInt(td.substring(5, 7), 10);
            yr2 = parseInt(td.substring(0, 4), 10);
            date1 = new Date(yr1, mon1-1, dt1);// From Date
            date2 = new Date(yr2, mon2-1, dt2);// TO DATE

            if (date1 > date2) {
                rep = " \"FROM DATE\" must be earlier than \"TO DATE \" ";
            	return rep;
            }
        }
    
    return rep;
}

function ValidatePastDate(cd) {
	//it will not accept past date and accepts present and future dates.
    var fromdate, todate, dt1, dt2, mon1, mon2, yr1, yr2, date1, date2;
    var chkFrom = cd;
    
        if (checkdate(chkFrom) != true) {
                   return false;
        }
        else {
            fromdate = cd;
            todate = new Date();
            dt1 = parseInt(fromdate.substring(8, 10), 10);
            mon1 = parseInt(fromdate.substring(5, 7), 10);
            yr1 = parseInt(fromdate.substring(0, 4), 10);
            dt2 = todate.getDate();
            mon2 = todate.getMonth();
            yr2 = todate.getFullYear();
            date1 = new Date(yr1, mon1-1, dt1);
            date2 = new Date(yr2, mon2, dt2);

            if (date2 > date1) {
                return false;
            }
        }
    
    return true;
}


function checkOnFocus(obj)
{
   obj.value = '';
}
function checkOnBlur(obj) 
{
    if(obj.value=='')
        obj.value = obj.defaultValue;
}

/*function checkDayEndDateForAdd(dedate) {
    var dayenddate=new Date(dedate);
	var today = new Date();    
	if(!((dayenddate.getFullYear()==2017) && ((dayenddate.getMonth()+1) <= 12))){
		if ((dayenddate.getMonth()==today.getMonth()) && ((dayenddate.getDate()+1)==today.getDate()) && (dayenddate.getFullYear()==today.getFullYear()))	
			return true;
		else
			return false;
	}else return true;
}*/


/*function checkDayEndDateForAdd(dedate) {
    var dayenddate=new Date(dedate);
	var today = new Date();    
	if(!((dayenddate.getFullYear()==2017) && ((dayenddate.getMonth()+1) <= 12))){
		var date = new Date();
		var checklpyr=checkleapyear(dayenddate.getFullYear());

		if((dayenddate.getMonth()+1)==2){
			if((checklpyr==true) && (dayenddate.getDate()==29)){
	    		document.getElementById("fd").value = dayenddate.getFullYear()+"-0"+3+"-0"+1;
			}else if((checklpyr==true) && (dayenddate.getDate()==28)){
	        	document.getElementById("fd").value = dayenddate.getFullYear()+"-0"+2+"-"+29;
			}else {
				date.setDate(dayenddate.getDate()+1);
				date.setMonth(dayenddate.getMonth());
			    date.setFullYear(dayenddate.getFullYear());
			}
		 }else {        	
			date.setFullYear(dayenddate.getFullYear());
			date.setMonth(dayenddate.getMonth());
	        date.setDate(dayenddate.getDate()+1);
		 }
		
		if ((date.getMonth()==today.getMonth()) && ((date.getDate())==today.getDate()) && (date.getFullYear()==today.getFullYear()))	
			return true;
		else
			return false;
	}else return true;
}
*/

/*function checkDayEndDateForAdd(dedate,agencyCreatedDate) {
    var dayenddate=new Date(dedate);
    var agcrdate = new Date(agencyCreatedDate);
	var today = new Date();
	if(!((dayenddate.getFullYear()==2017) && ((dayenddate.getMonth()+1) <= 12))){
		var cefd = new Date();
		cefd.setDate(agcrdate.getDate()-1);
		cefd.setMonth(agcrdate.getMonth());
		cefd.setFullYear(agcrdate.getFullYear());

		if(( (cefd.getFullYear()==dayenddate.getFullYear()) && (cefd.getMonth()==dayenddate.getMonth()) && (cefd.getDate()<=dayenddate.getDate()) )) {
			var date = new Date();
			var checklpyr=checkleapyear(dayenddate.getFullYear());

			if((dayenddate.getMonth()+1)==2){
				if((checklpyr==true) && (dayenddate.getDate()==29)){
					document.getElementById("fd").value = dayenddate.getFullYear()+"-0"+3+"-0"+1;
				}else if((checklpyr==true) && (dayenddate.getDate()==28)){
					document.getElementById("fd").value = dayenddate.getFullYear()+"-0"+2+"-"+29;
				}else {
					date.setDate(dayenddate.getDate()+1);
					date.setMonth(dayenddate.getMonth());
					date.setFullYear(dayenddate.getFullYear());
				}
			}else {
				date.setFullYear(dayenddate.getFullYear());
				date.setMonth(dayenddate.getMonth());
				date.setDate(dayenddate.getDate()+1);
			}
		
			if ((date.getMonth()==today.getMonth()) && ((date.getDate())==today.getDate()) && (date.getFullYear()==today.getFullYear()))	
				return true;
			else
				return false;
		}else return true;
	}else return true;
}*/

function checkDayEndDateForAdd(dedate,agencyCreatedDate,effectiveDate) {
    var dayenddate=new Date(dedate);
    var agcrdate = new Date(agencyCreatedDate);
    var checkDate = effectiveDate-86400000;
    var cddate = new Date(checkDate);
	var today = new Date();
	if(!((dayenddate.getFullYear()==2017) && ((dayenddate.getMonth()+1) <= 12))){
		var cefd = new Date();
		cefd.setDate(agcrdate.getDate());
		cefd.setMonth(agcrdate.getMonth());
		cefd.setFullYear(agcrdate.getFullYear());

		var ddate = new Date(dayenddate.getFullYear(),dayenddate.getMonth(),dayenddate.getDate(),0,0,0);
		var acdate = new Date(cefd.getFullYear(),cefd.getMonth(),cefd.getDate(),0,0,0);
		
		if((acdate <= ddate) && (cddate < dayenddate)) {
			var date = new Date();
			var checklpyr=checkleapyear(dayenddate.getFullYear());

			if((dayenddate.getMonth()+1)==2) {
				if((checklpyr==true) && (dayenddate.getDate()==29)){
					document.getElementById("fd").value = dayenddate.getFullYear()+"-0"+3+"-0"+1;
				}else if((checklpyr==true) && (dayenddate.getDate()==28)){
					document.getElementById("fd").value = dayenddate.getFullYear()+"-0"+2+"-"+29;
				}else {
					date.setDate(dayenddate.getDate()+1);
					date.setMonth(dayenddate.getMonth());
					date.setFullYear(dayenddate.getFullYear());
				}
			}else {
				date.setFullYear(dayenddate.getFullYear());
				date.setMonth(dayenddate.getMonth());
				date.setDate(dayenddate.getDate()+1);
			}
			
			if ((date.getMonth()==today.getMonth()) && ((date.getDate())==today.getDate()) && (date.getFullYear()==today.getFullYear()))
				return true;
			else if((today.getMonth() == dayenddate.getMonth())&& (today.getDate() == dayenddate.getDate()) && (today.getFullYear() == dayenddate.getFullYear()))
				return "As You have submitted DayEnd For the Day You Cannot add Transactions";
			else
				return "Please Submit Previous DayEnd inorder to add todays data";
		}else return true;
	}else return true;
}

function checkleapyear(year){
	return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
}

function validateEffectiveDateForCVO(ipdate,effdate) {
    var effectiveDate = new Date(effdate);
    var dt1 = parseInt(ipdate.substring(8, 10), 10);
    var mon1 = parseInt(ipdate.substring(5, 7), 10);
    var yr1 = parseInt(ipdate.substring(0, 4), 10);
    var inputdate = new Date(yr1, mon1-1, dt1);
    if(inputdate < effectiveDate)
    	return true;
    else
    	return false;
}

function validateDayEndAdd(ipdate,dedate) {
    var dayenddate=new Date(dedate);
    var dt1 = parseInt(ipdate.substring(8, 10), 10);
    var mon1 = parseInt(ipdate.substring(5, 7), 10);
    var yr1 = parseInt(ipdate.substring(0, 4), 10);
    var inputdate = new Date(yr1, mon1-1, dt1);
    if(inputdate<=dayenddate)
    	return true;
    else
    	return false;
}

function validateDayEndForDelete(ipdate,dedate) {
	 var inputdate=new Date(ipdate);
	 var dayenddate=new Date(dedate);
	 if(inputdate>dayenddate)
		 return true;
	 else 
		 return false;
}

function checkforLastTrxnDate(dedate,a_created_date,inv_date,trdate) {
	var dayenddate=new Date(dedate);
    var agcrdate = new Date(a_created_date);
    var ddate = new Date(dayenddate.getFullYear(),dayenddate.getMonth(),dayenddate.getDate(),0,0,0);
    var acdate = new Date(agcrdate.getFullYear(),agcrdate.getMonth(),agcrdate.getDate(),0,0,0);

    if(!(acdate <= ddate)) {
		var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
    	var invd = new Date(inv_date);
    	var invdate = new Date(invd.getFullYear(),invd.getMonth(),invd.getDate(),0,0,0);
    	var txndate = new Date(new Date(trdate).getFullYear(),new Date(trdate).getMonth(),new Date(trdate).getDate(),0,0,0);
    	var td;
    	if(txndate.getDate()<10)
    		td = "0"+txndate.getDate();
    	else
    		td = txndate.getDate();

    	var tdate = td +"-"+ months[txndate.getMonth()] +"-"+ txndate.getFullYear();
    	if(invdate < txndate) {
    		return "Back dates are not allowed. Your last transaction date is "+tdate+ "\nPlease check it and add again." ;
    	}
    }
    return "false";
}