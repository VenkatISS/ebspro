/*function showModule(mname) {
	document.getElementById("homeDiv").style.display="none";
	document.getElementById("mdDiv").style.display="none";
	document.getElementById("transDiv").style.display="none";
	document.getElementById("reportsDiv").style.display="none";
	document.getElementById("profileDiv").style.display="none";
	document.getElementById(mname).style.display="block";
}

function logoutDealer(formobj){
	formobj.submit();
}*/

function alertsAndNotifications(){
	var sysDate = new Date();
	
	sysDate.setHours(0);
	sysDate.setMinutes(0);
	sysDate.setSeconds(0);

	var ems="";
	for(var i=0;i < statutory_data.length;i++) {
		var rDate = statutory_data[i].valid_upto;
		var vuDate = new Date(rDate);
		var cmprDate = vuDate - sysDate;
		var diff = Math.ceil((cmprDate)/(24*60*60*1000));
		var remDays =reminderDays[statutory_data[i].remind_before];

		if(diff == remDays)
			ems = ems +" <font color='yellow' class='alertmsg'>* Your "+statutory_items[(statutory_data[i].item_type)-1].item +" <br>is going to expire in <br> "+diff+" days </br><br></font>";
		else if(diff < remDays && diff > 0)
			ems = ems + "<font color='lightblue' class='alertmsg'>* Your "+statutory_items[(statutory_data[i].item_type)-1].item +"<br> is going to expire in <br> "+diff+" day(s) </br><br></font>";
		else if(diff < remDays && diff != 0)
			ems = ems + "<font color='red' class='alertmsg'>* Your "+statutory_items[(statutory_data[i].item_type)-1].item +"<br> has been expired. Please<br> renewal it </br><br></font>";
		else if(diff < remDays && diff == 0)
			ems = ems + "<font color='orange' class='alertmsg'>* Your "+statutory_items[(statutory_data[i].item_type)-1].item +"<br> is going to expire <br>today </br><br></font>";
		
		//else if(diff > remDays)
			//alert = alert + "<font color='green'>* your "+statutory_items[(statutory_data[i].item_type)-1].item +" is going to expire in  "+diff+" days </br></font>"; 
	}
	document.getElementById("alertsmsg").innerHTML= ems;
	
	if(ems==""){
		if(window.screen.width >= 1400) {
			$(".hmalerts").css("height","200px");
			$(".hmalmrq").css("height","100px");
//			$("#news").css("height","480px");
			$("#news").css("height","600px");
		}else if((window.screen.width < 1400) && (window.screen.width > 1024)) {
			$(".hmalerts").css("height","100px");
			$(".hmalmrq").css("height","80px");
			$("#news").css("height","440px");
		}else if(window.screen.width <= 1024){
//			$('.hmalerts').css("cssText", "margin-top:-70px; margin-left:-30% ; height:100px;padding: 35px 5px 35px 20px !important;");
			$('.hmalerts').css("cssText", "margin-top:-70px; margin-left:-79px ; height:100px;padding: 35px 5px 35px 20px !important;");
			$(".hmalmrq").css("height","90px");
			$("#news").css("height","440px");
		}
	}else {
		if(window.screen.width >= 1400) {
			$(".hmalerts").css("height","400px");
			$(".hmalmrq").css("height","300px");
			$("#news").css("height","280px");
		}else if((window.screen.width < 1400) && (window.screen.width > 1024)) {
			$(".hmalerts").css("height","380px");
			$(".hmalmrq").css("height","220px");
			$("#news").css("height","240px");
		}else if(window.screen.width <= 1024){
//			$('.hmalerts').css("cssText", "margin-top:-70px; margin-left:-30% ; height:325px;padding: 35px 5px 35px 20px !important;");
			$('.hmalerts').css("cssText", "margin-top:-70px; margin-left:-79px ; height:325px;padding: 35px 5px 35px 20px !important;");
			$(".hmalmrq").css("height","210px");
			$("#news").css("height","240px");
		}
	}


/*	
	if(ems==""){
		if(!(!!window.firefox && !!window.firefox.webstore)){
			document.getElementById("alertsmsg").innerHTML= "your statutory <br>reminders will <br>appear here";
		}else{
			if(window.screen.width >= 1400) {
				$(".hmalerts").css("height","200px");
				$(".hmalmrq").css("height","100px");
				$("#news").css("height","480px");
			}else if((window.screen.width < 1400) && (window.screen.width > 1024)) {
				$(".hmalerts").css("height","100px");
				$(".hmalmrq").css("height","80px");
				$("#news").css("height","440px");
			}else if(window.screen.width <= 1024){
				$('.hmalerts').css("cssText", "margin-top:-70px; margin-left:-30% ; height:100px;padding: 35px 5px 35px 20px !important;");
				$(".hmalmrq").css("height","90px");
				$("#news").css("height","440px");			
			}
		}
	}else {
		if(window.screen.width >= 1400) {
			$(".hmalerts").css("height","400px");
			$(".hmalmrq").css("height","300px");
			$("#news").css("height","280px");
		}else if((window.screen.width < 1400) && (window.screen.width > 1024)) {
			$(".hmalerts").css("height","380px");
			$(".hmalmrq").css("height","220px");
			$("#news").css("height","240px");			
		}else if(window.screen.width <= 1024){
			$('.hmalerts').css("cssText", "margin-top:-70px; margin-left:-30% ; height:325px;padding: 35px 5px 35px 20px !important;");			
			$(".hmalmrq").css("height","210px");
			$("#news").css("height","240px");			
		}
	}
	*/
}


// Validate FTL AND SUBMIT
function submitData() {
	var formobj = document.getElementById("data_form");
	var errmsg = "";
	var fy = formobj.fy.selectedIndex;
	var mon = formobj.mon.selectedIndex;
	var tarob = formobj.tarob.value.trim();
	var starob = formobj.starob.value.trim();
	var cashob = formobj.cashb.value.trim();
	var ujwob = formobj.ujwob.value.trim();
	var ed = formobj.ed.value.trim();
	var sdate = new Date();
	var sysyear = sdate.getFullYear();
	var sysmon = sdate.getMonth(); 
	var btbInvoiceCount = formobj.btbInvCounter.value.trim();
    var btcInvoiceCount = formobj.btcInvCounter.value.trim();
	
	var year = [0,2018,2019,2020,2021];
	
	if(!btbInvoiceCount.length>0)
        formobj.btbInvCounter.value='0001';
	else if(!checkInteger(btbInvoiceCount))
		 errmsg = errmsg + "B2B INVOICE COUNT Must Contain Only Numerics. <br>";
	else if(!(parseInt(btbInvoiceCount) > 0))
		errmsg = errmsg + "B2B INVOICE COUNTER cannot be initiated with 0. <br>";

	
	if(!btcInvoiceCount.length>0)
        formobj.btcInvCounter.value='0001';
	else if(!checkInteger(btcInvoiceCount))
		 errmsg = errmsg + "B2C INVOICE COUNT Must Contain Only Numerics. <br>";
	else if(!(parseInt(btcInvoiceCount) > 0))
		errmsg = errmsg + "B2C INVOICE COUNTER cannot be initiated with 0. <br>";
	
	
	if (!(fy > 0))
		errmsg = errmsg + "Please Select FINANCIAL YEAR<br>";
	else if(!(year[fy] <= sysyear))
		errmsg = errmsg + "FINANCIAL YEAR can't Be Future Year<br>";
	
	
	if (!(mon > 0))
		errmsg = errmsg + "Please Select MONTH<br>";
	else if(!(mon == sysmon+1))
		errmsg = errmsg + "Please select current month<br>";
	
	
	if(!tarob.length>0)
		errmsg = errmsg+"Please Enter TAR ACCOUNT OPENING BALANCE<br>";
	else if (validateDot(tarob))
		errmsg = errmsg + "TAR ACCOUNT OPENING BALANCE Must Contain Atleast One Number. <br>";
	else if (!checkInteger(tarob))
		errmsg = errmsg + "TAR ACCOUNT OPENING BALANCE Must Contain Only Numerics. <br>";
	else if (!(parseFloat(tarob) < 100000000))
		errmsg = errmsg + "TAR ACCOUNT OPENING BALANCE Must Be Less Than 10 Crores.<br>";
	else
		formobj.tarob.value = round(parseFloat(tarob), 2);
	
	
	if(!starob.length>0)
		errmsg = errmsg+"Please Enter STAR ACCOUNT Opening Balance<br>";
	else if (validateDot(starob))
		errmsg = errmsg + "STAR ACCOUNT OPENING BALANCE Must Contain Atleast One Number. <br>";
	else if (!checkInteger(starob))
		errmsg = errmsg + "STAR ACCOUNT OPENING BALANCE Must Contain Only Numerics. <br>";
	else if (!(parseFloat(starob) < 100000000))
		errmsg = errmsg + "STAR ACCOUNT OPENING BALANCE Must Be Less Than 10 Crores.<br>";
	else
		formobj.starob.value = round(parseFloat(starob), 2);
	
	
	if(!cashob.length>0)
		errmsg = errmsg+"Please Enter CASH Opening Balance<br>";
	else if (validateDot(cashob))
		errmsg = errmsg + "CASH OPENING BALANCE Must Contain Atleast One Number. <br>";
	else if (!checkInteger(cashob))
		errmsg = errmsg + "CASH OPENING BALANCE Must Contain Only Numerics. <br>";
	else if (!(parseFloat(cashob) < 100000000))
		errmsg = errmsg + "CASH OPENING BALANCE Must Be Less Than 10 Crores.<br>";
	else
		formobj.cashb.value = round(parseFloat(cashob), 2);
	

	if(!ujwob.length>0)
		errmsg = errmsg+"Please Enter UJWALA Opening Balance<br>";
	else if (validateDot(ujwob))
		errmsg = errmsg + "UJWALA OPENING BALANCE Must Contain Atleast One Number. <br>";
	else if (!checkInteger(ujwob))
		errmsg = errmsg + "UJWALA OPENING BALANCE Must Contain Only Numerics. <br>";
	else if (!(parseFloat(ujwob) < 100000000))
		errmsg = errmsg + "UJWALA OPENING BALANCE Must Be Less Than 10 Crores.<br>";
	else
		formobj.ujwob.value = round(parseFloat(ujwob), 2);
	
	
	var efdate = new Date(ed);
	var efd = new Date(efdate.getFullYear(),efdate.getMonth(),efdate.getDate(),0,0,0);
//	var efd1 = new Date((new Date(ed)).getFullYear(),(new Date(ed)).getMonth(),(new Date(ed)).getDate(),0,0,0);
	var aprd = new Date(2018,03,01,0,0,0);
	if (checkDateFormate(ed)) {
		var ved = dateConvert(ed);
		formobj.ed.value = ved;
		ed = ved;
	}
	
	
	var vd = isValidDate(ed);
	var vfd = ValidateFutureDate(ed);
	if (!(ed.length > 0))
		errmsg = errmsg + "Please Enter EFFECTIVE DATE<br>";
	else if (vd != "false")
		errmsg = errmsg + "EFFECTIVE"+vd+"<br>";
	else if (vfd != "false")
		errmsg = errmsg + "EFFECTIVE"+vfd+"<br>";
	else if(efd<aprd){
        errmsg = errmsg + "Please enter EFFECTIVE DATE from April,2018 Onwards<br>";
	}	
	
	if (errmsg.length > 0 ){
		document.getElementById("dialog-1").innerHTML = errmsg;
		alertdialogue();
		//alert(errmsg);
		return;
	}

	formobj.submit();
}
