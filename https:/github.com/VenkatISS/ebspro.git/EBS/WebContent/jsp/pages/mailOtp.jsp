<%@ page import="java.util.UUID" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<%
		UUID uuid = UUID.randomUUID();
	    String randomUUIDString = uuid.toString();
    %>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- CSS-->
		<link rel="stylesheet" type="text/css" href="css/main.css?<%=randomUUIDString%>">
		<title>LPG DEALER ERP WEB APPLICATION - PROFILE</title>
		<jsp:useBean id="agencyVO" scope="session"
				class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
		<jsp:useBean id="profile_data" scope="request" class="java.lang.String"></jsp:useBean>
		<script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>
		<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
		<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script>
		<script type="text/javascript">
        	var pinValue=<%= agencyVO.getPinNumber() %>;
        	var otpValue=<%=request.getAttribute("generatedOtp")%>;
        </script>
		<script type="text/javascript">
                window.onload = setWidthHightNav("100%");
        </script>
	</head>
<body>
	<!-- FORGOT PIN POPUP -->
	<!-- Modal -->
	<div class="modal_popup_add fade in" id="myForgotPinDetails"
		style="display: block; padding-left: 14px; height: 100%">
		<div class="modal-dialog modal-lg">
			<!-- Modal content-->
			<div class="modal-content prof_modalContent">
				<div class="modal-header" style="text-align: center;">
					<h4 class="modal-title">SET NEW PIN</h4>
				</div>
				<div class="modal-body">
					<form id="resetPindata_form" name="" method="post" action="AgencyDataControlServlet" onsubmit = "return resetOtpPinDetails(this)">
						<input type="hidden" id="agencyId" name="agencyId"
							value="<%= agencyVO.getAgency_code() %>"> <input
							type="hidden" id="page" name="page"
							value="jsp/pages/erp/profile.jsp"> <input type="hidden"
							id="actionId" name="actionId" value="9003"> <input
							type="hidden" id="cgsta" name="cgsta" value=""> <br>
						<table id="t_data_table">
							<p>
								<font color:red>Please enter the confirmation code <br>
									that has been sent to your Email address
								</font>
							</p>
							<tr class="spaceUnder">
								<td><input class="form-control"
									style="margin-left: 60%; width: 50%" type="text"
									name="enteredEmailOtp" id="emailOtp"
									placeholder="Enter verification code">
								</td>
							</tr>
							<tr class="spaceUnder">
								<td><input class="form-control"
									style="margin-left: 60%; width: 50%" type="password"
									name="agencyPin" id="agencyPin"
									placeholder="Enter new  pin">
								</td>
							</tr>
							<tr class="spaceUnder">
								<td><input class="form-control"
									style="margin-left: 60%; width: 50%" type="password" name="setCpin"
									id="setCpin" placeholder="Re-enter new  pin">
								</td>
							</tr>
							<tr class="spaceUnder">
								<td colspan="6">
									<div class="submit_div"
										style="text-align: center; width: 150px; margin-left: 350px; margin-top: 10px;">
										<input type="submit" name="prof_submit_btn"
											id="prof_submit_btn" value="RESET PIN"
											class="btn btn-success m-r-15">
									</div>
								</td>
							</tr>
						</table>

					</form>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		//new  pin through otp validations
		function resetOtpPinDetails(formobj) {
// 			var formobj = document.getElementById('resetPindata_form');
            var enteredEmailOtp = formobj.enteredEmailOtp.value.trim();
            var agencyPin = formobj.agencyPin.value.trim();
            var setCpin = formobj.setCpin.value.trim();

			var ems="";
            if(!(enteredEmailOtp.length>0))
            	ems= "Please Enter OTP\n";
            else if(!validateOtpNumber(enteredEmailOtp,6,6))
            	ems = "OTP Must Be Valid  6 DIGIT Number \n";
            else if(otpValue!=enteredEmailOtp)
            	ems = "ENTERED OTP NUMBER IS WRONG \n";
            else {
	            if(!(agencyPin.length>0))
    	        	ems= ems+"Please Enter NEW PIN NUMBER\n";
        	    else if(!validatePinNumber(agencyPin,4,4))
            		ems = ems+"NEW PIN NUMBER Must Be A Valid 4 DIGIT Number \n";

				if(!(setCpin.length>0))
            		ems= ems+"Please Enter CONFIRM PIN NUMBER\n";
				else if(agencyPin!=setCpin)
					ems = ems+"CONFIRM PIN NUMBER Must MATCH WITH NEW PIN NUMBER \n";
            }
            
			if (ems.length > 0) {
		    	alert(ems);
                return false;
			}
// 			formobj.submit();
		}

		function validateOtpNumber(fieldV, min, max) { 
			if (!min) { min = 6 } 
			if (!max) { max = 6 } 
			if ( (parseInt(fieldV) != fieldV) || fieldV.length < min || fieldV.length > max) { 
				return false; 
			} 
			return true; 
		}
	</script>
</body>
</html>