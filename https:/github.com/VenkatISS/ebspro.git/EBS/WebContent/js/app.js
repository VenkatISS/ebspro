//Show Registration Form
function showRegistrationForm(){
	document.getElementById("loginDiv").style.display="none";
	document.dealer_reg_form.reset();
	document.getElementById("registrationDiv").style.display="block";
	
}

//Show Login Form
function showLoginForm() {
	document.getElementById("registrationDiv").style.display="none";
	document.getElementById("loginDiv").style.display="block";
	document.dealer_reg_form.reset();
	//document.getElementById("successDiv").style.display="none";
}

//Show Forgot Password Form
function showForgotPasswordDIV() {
	document.getElementById('forgotPwd').style.display="block";
}

//Validate FORGOT PASSWORD Form and submit
function validateEmailForForgotPwd(formobj){
	var errMsg = "";
	var femailId = formobj.femailId.value.trim();

	if(!femailId.length>0)
		errMsg = errMsg+"EMAIL ID Can't be empty.\n";
	else {
		if(!checkEmail(femailId))
			errMsg = errMsg+"Please Enter Proper Email.\n";
	}
	if ( errMsg.length > 0 ){
		alert(errMsg);
		return false;
	}
	formobj.femailId.value = (formobj.femailId.value.trim()).toLowerCase();

//	formobj.submit();
}

//Validate Login Form and submit
function validateLoginDetails(formobj){
	var error_text = "";
	var agencyid = formobj.ai.value.trim();
	var pwd = formobj.pwd.value.trim();
	var robotcheckbox=formobj.robot;

	if(!agencyid.length>0)
		error_text = "AGENCY ID Can't Be Empty\n";
	else if(!checkNumber(agencyid))
		error_text = "Invalid AGENCY ID\n";
	else if(agencyid != formobj.ai.value)
		error_text = "No spaces are allowed in AGENCY ID\n";
	
	if(!pwd.length>0)
		error_text = error_text+"PASSWORD Can't Be Empty\n";

	if(robotcheckbox.checked == false)
        error_text = error_text+"You can't leave Captcha Code empty\n";
	
	if ( error_text.length > 0 ) {
		alert(error_text);
		return false;
	}
	
	formobj.ai.value = formobj.ai.value.trim();
	formobj.pwd.value = formobj.pwd.value.trim();
//	formobj.submit();
}

//Validate Registration Form and submit
function submitRegistrationForm(formobj) {
	var errMsg = "";
	var agencycode = formobj.agencycode.value.trim();
	var emailadd = formobj.emailId.value.trim();
	var pwd = formobj.pwd.value.trim();
	var cpwd = formobj.cpwd.value.trim();
	var dname = formobj.dname.value.trim();
	var dmobile = formobj.dmobile.value.trim();
	var gstno = formobj.gstin.value.trim();
	var occ = formobj.oc.selectedIndex;
	var sutv = formobj.storut.selectedIndex;
	var ftype = formobj.ft.selectedIndex;
	var terms =formobj.checkbox1;
	var uecaptchastr = formobj.uecaptchastr.value.trim();
	var captchaVal= formobj.captchaValue.value.trim();
	var sutvalue = formobj.storut.value;
	var gstcode  = gstno.substring(0,2);
	
	if(!agencycode.length>0)
		errMsg = errMsg+"AGENCY CODE Can't Be Empty\n";
	else {
		if(!validateNumber(agencycode,6,10))
			errMsg = errMsg+"AGENCY CODE Must Be Valid number\n";
	}

	if(!dname.length>0)
		errMsg = errMsg + "DISTRIBUTORSHIP NAME Can't Be Empty\n";
	else if(checkNumber(dname))
        errMsg = errMsg + "Please Enter Valid  DISTRIBUTORSHIP NAME\n"
    else if(!IsNameSpaceDot(dname))
		errMsg = errMsg + "Please Enter Valid  DISTRIBUTORSHIP NAME\n";

	if(!dmobile.length>0)
		errMsg = errMsg+"MOBILE NUMBER Can't Be Empty\n";
	else if(!validateNumber(dmobile,10,10))
			errMsg = errMsg+"MOBILE NUMBER Must Be Valid Number\n";
	
	if(!emailadd.length>0)
		errMsg = errMsg+"EMAIL ID Can't be empty.\n";
	else if(!checkEmail(emailadd))
			errMsg = errMsg+"Please Enter Proper Email.\n";
	
/*	if(!gstno.length>0)
		errMsg = errMsg+"GSTIN Can't Be Empty\n";
	else if (gstno.length != 15)
		errMsg = errMsg + "GSTIN Must Contains 15 Characters \n";
	else if (!(checkGSTIN(gstno)))
		errMsg = errMsg + "Enter Valid GSTIN \n";

	if(!sutv>0)
		errMsg = errMsg+"Please Select STATE OR UNION TERRITORY\n";
	if (!(sutvalue == gstcode))
		errMsg = errMsg+"Please Select Valid STATE OR UNION TERRITORY For The Given GSTIN \n";*/
	
	var stcode = gstno.substr(0,2);
	if((!gstno.length>0) || (!sutv>0)){
		if(!gstno.length>0)  
			errMsg = errMsg+"GSTIN Can't Be Empty\n";
		if(!sutv>0)
			errMsg = errMsg+"Please Select STATE OR UNION TERRITORY\n";			
	}else if(gstno.length>0) {	
		if (gstno.length != 15)
			errMsg = errMsg + "GSTIN Must Contains 15 Characters \n";
		else if(stcode == "00")
			errMsg = errMsg + "Enter valid StateCode in GSTIN \n";
		else if (!(checkGSTIN(gstno)))
			errMsg = errMsg + "Enter Valid GSTIN \n";
		else if ((sutv>0) && !(sutvalue == gstcode))
			errMsg = errMsg+"Please Select Valid STATE OR UNION TERRITORY For The Given GSTIN \n";
	}
	
	if(pwd.length<0)
		errMsg = errMsg+"Please Enter  Password.\n";
	else if((pwd.length<8)||(pwd.length>12)){
		errMsg = errMsg+"Please Enter Proper Password. 8-12 Characters.\n";
	}
	else if(!cpwd.length>0){
		errMsg = errMsg+"Please Enter Confirm Password Same As Password.\n";
	}
	else if(!(pwd==cpwd)){
		errMsg = errMsg+"Please Enter Same Value For PASSWORD and CONFIRM PASSWORD\n";
	}
	if(terms.checked == false)
		errMsg = errMsg+"Please Check The TERMS OF SERVICES  \n";
	
	if(!ftype>0)
		errMsg = errMsg+"Please Select Firm Type\n";

	if(!occ>0)
		errMsg = errMsg+"Please Select OMC \n";
	
	if(!uecaptchastr.length>0)
		errMsg = errMsg+"Please enter CAPTCHA \n";
	else if(!(uecaptchastr.localeCompare(captchaVal)== 0))
		errMsg = errMsg+"Please enter same characters as Shown In CAPTCHA\n";
		
	if ( errMsg.length > 0 ){
		alert(errMsg);
		return false;
	}

//	formobj.submit();
}
