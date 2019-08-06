<%@ page import="java.util.UUID, com.it.erpapp.utils.RandomAlphaNumericStringGenerator,
	com.it.erpapp.response.MessageObject"%> 
<!DOCTYPE html>
<html style="overflow :hidden;">
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
		<!-- For Password Masking-START -->
		<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
		<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-show-password/1.0.3/bootstrap-show-password.min.js"></script>
		<!--Password Masking-END -->
		<title>LPG DEALER WEB APPLICATION - DEALER LOGIN</title>
		<jsp:useBean id="showDIV" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="showDIV1" scope="request" class="java.lang.String"></jsp:useBean>
		
		<script src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
		<script src="js/app.js?<%=randomUUIDString%>"></script>
		<script type="text/javascript" src="js/global.js?<%=randomUUIDString%>"></script>
		<script src="js/commons/detectmobilebrowser.js?<%=randomUUIDString%>"></script>
	  	<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script>
	  	<script type="text/javascript">
    	</script>
 		<script type="text/javascript">
			window.onload = checkBrowser();
			window.onbeforeunload = winClose();
		</script>
	  	<script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>
		<noscript>Sorry! Your browser does not support JavaScript.!</noscript> 
  	</head>
   	<body class="bg_white apppage_body">
		<section class="login-content">
		
<!-- 		<div class="containr" style="width:100%;height:auto;">
// This image is kept in css for bg_white class 
 				<img class="img-responsive" src="images/image3.jpg" style="width:100%;opacity:0.8;">
// 				<img class="img-responsive" src="images/background.png" style="width:100%;opacity:0.4;">
      		</div> -->
      		
      		<div class="contbox" id="contbox" >
	      		<div class="logo" style="margin-top:-10%;">
<!--     	    		<h1><img src="images/logo.png" alt="" title="" width="100"/><br/> -->
    	    		<h1><img src="images/mylpgbooks.png" alt="" title="" width="110"/><br/>
						LPG DEALER ENTERPRISE BUSINESS SOLUTIONS PORTAL
					</h1>
      			</div>
 				<div style="text-align:center;" class="msgcls">
 				<%
					MessageObject msgObj = (MessageObject) request.getAttribute("msg_obj");
					if ((null != msgObj)) {
						if ((msgObj.getMessageId() == 1001) || (msgObj.getMessageId() == 1000) || (msgObj.getMessageId() == 1002)) {
				%>
					<font color="red" id="reg_errmsg"><%=msgObj.getMessageText()%></font>
				<%
						}
					}
				%>
				<%
					if ((showDIV != null) && showDIV.equalsIgnoreCase("successDiv")) {
				%>
					<div id="successDiv" align="center">
						<font color="red" face="tahoma" size="2"> AGENCY REGISTRATION SUCCUSSFUL. <BR>PLEASE CHECK YOUR REGISTERED EMAIL FOR ACTIVATION LINK</font>
					</div>
				<%
					}
					else if ((showDIV1 != null) && showDIV1.equalsIgnoreCase("successDiv1")) {
				%>
					<div id="successDiv" align="center">
						<font color="red" face="tahoma" size="2">AN EMAIL HAS BEEN SENT TO THE GIVEN EMAIL ID <BR> WITH A LINK TO RESET YOUR PASSWORD</font>
					</div>
				<%
					} 
                    else {
				%>
			</div>
			
			<%  if(msgObj != null) {
					if(msgObj.getMessageText() == null) { %>
						<div class="col-md-4 col-sm-4 col-xs-4 login-box" id="loginDiv" style="margin-top:1%;">
			<% 		}else if((msgObj.getMessageText() != null) && (msgObj.getMessageText().contains("AGENCY ID / EMAIL ID ALREADY EXISTS"))) { %>
						<div class="col-md-4 col-sm-4 col-xs-4 login-box" id="loginDiv" style="margin-top:2%;margin-left:48px;">
			<% 		}else if((msgObj.getMessageText() != null) && (!(msgObj.getMessageText().contains("AGENCY ID / EMAIL ID ALREADY EXISTS")))) { %>
						<div class="col-md-4 col-sm-4 col-xs-4 login-box" id="loginDiv" style="margin-top:1%;">
			<%		}}else {%>
						<div class="col-md-4 col-sm-4 col-xs-4 login-box" id="loginDiv" style="margin-top:1%;">
			<% 	}%>
<%-- 		  		<form name="dealer_login_form" id="dealer_login_form" class="login-form" action="<%=request.getContextPath() %>/login" method="post"> --%>
		  		<form name="dealer_login_form" id="dealer_login_form" class="login-form" action="<%=request.getContextPath() %>/login" method="post" onsubmit="return validateLoginDetails(this)">
	      			<input type="hidden" name="actionId" value="1001">
					<input type="hidden" name="page" value="erp/home">
	        		<h3 class="login-head"><img src="images/login_icon.png" alt="" title=""/> DEALER LOGIN</h3>
        			<div class="form-group">
            			<input class="form-control" type="text" id="ai" name="ai" title="AGENCY ID" placeholder="AGENCY ID" autofocus>
          			</div>
          			<div class="form-group">
            			<input class="form-control" type="password" id="pwd" name="pwd" title="PASSOWRD" placeholder="PASSWORD" data-toggle="password">
          			</div>
            		<p class="forgot_pass"><a data-toggle="flip" class="forgotpwd cleardata"><b>Forgot Password ?</b></a></p>
					<table border="1px" bordercolor="#d3d3d3" style="background-color:#f9f9f9;width:300px;height:60px;margin-top: -40px;">
                    	<tr>
                        	<td  style="border:none;width:120px;"><input id="robot" type="checkbox"> I'm not a robot</td>
							<td style="border:none;width:120px;"><img src="images/recaptcha.png" width="80px" height="50px"/></td>
						</tr>
					</table>
          			<div class="form-group btn-container" style="margin-top:20px;">
<!--             			<input type="button" value="L O G I N" class="btn btn-success m-r-15" onclick="validateLoginDetails(this.form)"> -->
            			<input type="submit" value="L O G I N" class="btn btn-success m-r-15">
						<input type="button" value="NEW REGISTRATION" class="btn btn-info cleardata" onclick="showRegistrationForm()">
          			</div>
        		</form>


        		<form class="forget-form" name="forget-form" id="forget-form" action="<%=request.getContextPath() %>/login" method="post" onsubmit="return validateEmailForForgotPwd(this)">
	        		<input type="hidden" name="actionId" value="1005">
					<input type="hidden" name="page" value="app">
          			<h3 class="login-head"><i class="fa fa-lg fa-fw fa-lock"></i>Forgot Password ?</h3>
          			<p><center>Enter Your Registered Email Address And We Will Send <br>You The Link To Reset Your Password</center></p>
          			<div class="form-group">
            			<label class="control-label">EMAIL</label>
            			<input class="form-control" type="text" name="femailId" id="femailId" placeholder="Email">
          			</div>
          			<div class="form-group btn-container">
          				<input type="submit" value="RESET PASSWORD" class="btn btn-info">
		          	</div>
          			<div class="form-group mt-20">
            			<p class="semibold-text mb-0"><a data-toggle="flip" class="clearform"><i class="fa fa-angle-left fa-fw"></i> Back to Login</a></p>
          			</div>
        		</form>
      		</div>


      		<div class="col-md-4 col-sm-4 col-xs-4 login-box register-box" id="registrationDiv" style="display: none;margin-top: 1%;">
      			<form name="dealer_reg_form" id="dealer_reg_form" class="login-form" action="<%=request.getContextPath()%>/RegistrationControlServlet" method="post" onsubmit="return submitRegistrationForm(this)">
					<input type="hidden" name="actionId" value="1002">
					<input type="hidden" name="page" value="app">
<!-- 					<input type="hidden" name="oc" value="2"> -->
          			<h3 class="login-head"><img src="images/registration_icon.png" alt="" title=""/> DEALER REGISTRATION</h3>
		  			<div class="row">
          				<div class="form-group col-sm-6">
            				<input class="form-control" type="text" name="agencycode" id="agencycode" title="AGENCY ID" placeholder="AGENCY ID" autofocus>
          				</div>
          				<div class="form-group col-sm-6">
            				<input class="form-control" type="text" name="dname" id="dname" maxlength="35" title="DISTRIBUTORSHIP NAME" placeholder="DISTRIBUTORSHIP NAME">
          				</div>
          				<div class="form-group col-sm-6">
            				<input class="form-control" type="text" name="dmobile" id="dmobile" title="DEALER MOBILE" placeholder="DEALER MOBILE">
          				</div>
          				<div class="form-group col-sm-6">
            				<input class="form-control" type="text" name="emailId" id="emailId" title="EMAIL ID" placeholder="EMAIL ID">
          				</div>
          				<div class="form-group col-sm-6">
            				<input class="form-control" type="text" name="gstin" id="gstin" title="GSTIN" placeholder="GSTIN">
          				</div>
          				<div class="form-group col-sm-6">
            				<select class="form-control select_dropdown" id="storut" name="storut">
								<option value="0">STATE OR UT</option>
								<option value="01">01 - JAMMU & KASHMIR</option>
								<option value="02">02 - HIMACHAL PRADESH</option>
								<option value="03">03 - PUNJAB</option>
								<option value="04">04 - CHANDIGARH</option>
								<option value="05">05 - UTTARAKHAND</option>
								<option value="06">06 - HARYANA</option>
								<option value="07">07 - DELHI</option>
								<option value="08">08 - RAJASTHAN</option>
								<option value="09">09 - UTTAR PRADESH</option>
								<option value="10">10 - BIHAR</option>
								<option value="11">11 - SIKKIM</option>
								<option value="12">12 - ARUNACHAL PRADESH</option>
								<option value="13">13 - NAGALAND</option>
								<option value="14">14 - MANIPUR</option>
								<option value="15">15 - MIZORAM</option>
								<option value="16">16 - TRIPURA</option>
								<option value="17">17 - MEGHALAYA</option>
								<option value="18">18 - ASSAM</option>
								<option value="19">19 - WEST BENGAL</option>
								<option value="20">20 - JHARKHAND</option>
								<option value="21">21 - ODISHA</option>
								<option value="22">22 - CHHATTISGARH</option>
								<option value="23">23 - MADHYA PRADESH</option>
								<option value="24">24 - GUJARAT</option>
								<option value="25">25 - DAMAN & DIU</option>
								<option value="26">26 - DADRA & NAGAR HAVELI</option>
								<option value="27">27 - MAHARASHTRA</option>
								<option value="29">29 - KARNATAKA</option>
								<option value="30">30 - GOA</option>
								<option value="31">31 - LAKSHDWEEP</option>
								<option value="32">32 - KERALA</option>
								<option value="33">33 - TAMIL NADU</option>
								<option value="34">34 - PONDICHERRY</option>
								<option value="35">35 - ANDAMAN & NICOBAR ISLANDS</option>
								<option value="36">36 - TELANGANA</option>
								<option value="37">37 - ANDHRA PRADESH</option>
								<option value="97">97 - OTHER TERRITORY</option>
							</select>
          				</div>
		  				<div class="clearfix"></div>
          					<div class="form-group col-sm-6">
            					<input class="form-control" type="password" name="pwd" id="pwd" title="PASSWORD" placeholder="PASSWORD">
								<p class="small m-t-5">[8-12 Characters]</p>
          					</div>
          					<div class="form-group col-sm-6">
            					<input class="form-control" type="password" name="cpwd" id="cpwd" title="CONFIRM PASSWORD" placeholder="CONFIRM PASSWORD">
								<p class="small m-t-5">[Same as Password]</p>
          					</div>
          					<div class="clearfix"></div>
          					<div class="form-group col-sm-6">
            					<select class="form-control select_dropdown" id="ft" name="ft">
            					 	<option value="0">FIRM TYPE</option>
            						<option value="1">PROPRIETORSHIP</option>
								 	<option value="2">PARTNERSHIP</option>
            					</select>
          					</div>
          					<div class="form-group col-sm-6">
            					<select class="form-control select_dropdown" id="oc" name="oc">
            					 	<option value="0">SELECT OMC</option>
            						<option value="1">IOCL</option>
								 	<option value="2">HPCL</option>
            						<option value="3">BPCL</option>
            					</select>
          					</div>
							<div class="clearfix"></div>
		  					<div class="form-group col-sm-6" style="margin-top:-2%;"><br>
								<label>TYPE THE CHARACTERS</label>
								<input type="text" class="form-control" name="uecaptchastr" title="CAPTCHA" value="">
									<%
										String cs = RandomAlphaNumericStringGenerator.getAlphaString(); 
												request.getSession().setAttribute("captchastr",cs);
									%>	
								<input type="hidden" id="captchaValue" value="<%=cs%>">
		  					</div>
		  					<div class="form-group col-sm-6 captchacls">
								<label class="hidden-xs">&nbsp;</label>
								<img alt="No Captcha" class="captcha" src="jsp/pages/scaptcha.jsp">
		  					</div>
		 					<div class="clearfix"></div>
 		  					<div>
                				<input id="checkbox1" type="checkbox" style="margin-left: 3.5%;">
		  						<a href="javascript:termsOfServices()" id="tos" style="margin-left: 1%;">Terms Of Services</a>
	          				</div>
<!--           					<center><a class="btn btn-success m-t-15 reg_btn" href="javascript:submitRegistrationForm(dealer_reg_form)">REGISTER</a></center>-->
          					<input type="submit" value="REGISTER" class="btn btn-success m-t-15 reg_btn">
		  					<p class="semibold-text reg_a"><a href="javascript:showLoginForm()" class="reg_a clearregdata" style="width: 150px;"><i class="fa fa-angle-left fa-fw"></i> Back to Login</a></p>
		  				</div>
		  				<div class="clearfix"></div>
        			</form>
      			</div>
      			
      			<div style="display:block;margin-top:350px;">
				<%!static int click=10078; %>
  				<%
    				if (click == 0) {
  				%>
    				<br><h3 align="center">
    				<% click ++; %>
   					<label style="font-size:10px;"><b>No.of views:</b></label><span style="float:right;font-size:20px;"><%=click %>.</span></h3>
   					<%
     					click++;
   					}else {
    			%>
          		<span style="color:blue;float:right;font-size:1.2vw;margin-top:10px; font-family: Times New Roman, Times, serif;"><label>No.Of Visitors:</label><b><%=click %></b></span>

     		 	<%
          			click++;
      			}
   				%>
			</div>
<!--
			<div style="text-align:center;">
				<script type="text/javascript" src="http://services.webestools.com/cpt_pages_views/66659-11-5.js"></script>
			</div>
 -->

			</div>


			<div class="tos_modal fade in" id="myModal" style="display: none; margin-left: -25%; height:100%;width: 156%;">
				<div class="modal-dialog modal-lg">
					<!-- Modal content-->
						<div class="tos_modal_div">
							<div class="modal-header">
								<span class="close" id="close" onclick="closeFormDialog()">&times;</span>
								<h4>TERMS OF SERVICE</h4>
							</div>
							<div class="modal-body" style="padding:20px;">
Updated on: 01st July 2017.<br>
Effective Date: 01st July 2017.
<br><br>



THIS IS AN AGREEMENT BETWEEN YOU OR THE ENTITY THAT YOU REPRESENT (hereinafter <q>You</q> or <q>Your</q>) AND MyLPGBooks (hereinafter <q>MyLPGBooks</q>) GOVERNING YOUR USE OF MyLPGBooks SOFTWARE PRODUCTS.M<br>
<h4>Parts of this Agreement</h4>
This Agreement consists of the following terms and conditions (hereinafter the <q>General Terms</q>) and terms and conditions, if any, specific to use of individual Services (hereinafter the <q>Service Specific Terms</q>). The General Terms and Service Specific Terms are collectively referred to as the <q>Terms</q>. In the event of a conflict between the General Terms and Service Specific Terms, the Service Specific Terms shall prevail.<br>
<h4>Acceptance of the Terms</h4>
You must be of legal age to enter into a binding agreement in order to accept the Terms. If you do not agree to the General Terms, do not use any of our Services. If you agree to the General Terms and do not agree to any Service Specific Terms, do not use the corresponding Service. You can accept the Terms by checking a checkbox or clicking on a button indicating your acceptance of the terms or by actually using the Services.
<br><h4>Description of Service</h4>
We provide an array of services for online collaboration and management including custom accounting package, mobile applications and e-commerce  ("Service" or "Services"). You may use the Services for your personal and business use or for internal business purpose in the organization that you represent. You may connect to the Services using any Internet browser supported by the Services. You are responsible for obtaining access to the Internet and the equipment necessary to use the Services. You can create and edit content with your user account and if you choose to do so, you can publish and share such content.
<br><h4>Subscription to Beta Service</h4>
We may offer certain Services as closed or open beta services ("Beta Service" or <q>Beta Services</q>) for the purpose of testing and evaluation. You agree that we have the sole authority and discretion to determine the period of time for testing and evaluation of Beta Services. We will be the sole judge of the success of such testing and the decision, if any, to offer the Beta Services as commercial services. You will be under no obligation to acquire a subscription to use any paid Service as a result of your subscription to any Beta Service. We reserve the right to fully or partially discontinue, at any time and from time to time, temporarily or permanently, any of the Beta Services with or without notice to you. You agree that MyLPGBooks will not be liable to you or to any third party for any harm related to, arising out of, or caused by the modification, suspension or discontinuance of any of the Beta Services for any reason.
<br><h4>Modification of Terms of Service </h4>
We may modify the Terms upon notice to you at any time through a service announcement or by sending email to your primary email address. If we make significant changes to the Terms that affect your rights, you will be provided with at least 30 days advance notice of the changes by email to your primary email address. You may terminate your use of the Services by providing MyLPGBooks notice by email within 30 days of being notified of the availability of the modified Terms if the Terms are modified in a manner that substantially affects your rights in connection with use of the Services. In the event of such termination, you will be entitled to prorated refund of the unused portion of any prepaid fees. Your continued use of the Service after the effective date of any change to the Terms will be deemed to be your agreement to the modified Terms.
<br><h4>User Sign up Obligations </h4>
You need to sign up for a user account by providing all required information in order to access or use the Services. If you represent an organization and wish to use the Services for corporate internal use, we recommend that you, and all other users from your organization, sign up for user accounts by providing your corporate contact information. In particular, we recommend that you use your corporate email address. You agree to: a) provide true, accurate, current and complete information about yourself as prompted by the sign up process; and b) maintain and promptly update the information provided during sign up to keep it true, accurate, current, and complete. If you provide any information that is untrue, inaccurate, outdated, or incomplete, or if MyLPGBooks has reasonable grounds to suspect that such information is untrue, inaccurate, outdated, or incomplete, MyLPGBooks may terminate your user account and refuse current or future use of any or all of the Services.
<br><h4>Organization Accounts and Administrators</h4>
When you sign up for an account for your organization you may specify one or more administrators. The administrators will have the right to configure the Services based on your requirements and manage end users in your organization account. If your organization account is created and configured on your behalf by a third party, it is likely that such third party has assumed administrator role for your organization. Make sure that you enter into a suitable agreement with such third party specifying such party&#39;s roles and restrictions as an administrator of your organization account.
You are responsible for i) ensuring confidentiality of your organization account password, ii) appointing competent individuals as administrators for managing your organization account, and iii) ensuring that all activities that occur in connection with your organization account comply with this Agreement. You understand that MyLPGBooks is not responsible for account administration and internal management of the Services for you.
You are responsible for taking necessary steps for ensuring that your organization does not lose control of the administrator accounts. You may specify a process to be followed for recovering control in the event of such loss of control of the administrator accounts by sending an email to <a href="mailto:info@mylpgbooks.com">info@mylpgbooks.com</a> , provided that the process is acceptable to MyLPGBooks. In the absence of any specified administrator account recovery process, MyLPGBooks may provide control of an administrator account to an individual providing proof satisfactory to MyLPGBooks demonstrating authorization to act on behalf of the organization. You agree not to hold MyLPGBooks liable for the consequences of any action taken by MyLPGBooks in good faith in this regard.
<br><h4>Personal Information and Privacy</h4>
Personal information you provide to MyLPGBooks through the Service is governed by MyLPGBooks Privacy Policy. Your election to use the Service indicates your acceptance of the terms of the MyLPGBooks Privacy Policy. You are responsible for maintaining confidentiality of your username, password and other sensitive information. You are responsible for all activities that occur in your user account and you agree to inform us immediately of any unauthorized use of your user account by email to <a href="mailto:support@mylpgbooks.com">support@mylpgbooks.com</a> or by calling us on any of the numbers listed on Contact Us at <a href="https://www.mylpgbooks.com/ebs/login">https://www.mylpgbooks.com/ebs/login</a> . We are not responsible for any loss or damage to you or to any third party incurred as a result of any unauthorized access and/or use of your user account, or otherwise.
<br><h4>Communications from MyLPGBooks</h4>
The Service may include certain communications from MyLPGBooks, such as service announcements, administrative messages and newsletters. You understand that these communications shall be considered part of using the Services. As part of our policy to provide you total privacy, we also provide you the option of opting out from receiving newsletters from us. However, you will not be able to opt-out from receiving service announcements and administrative messages.
<br><h4>Complaints</h4>
If we receive a complaint from any person against you with respect to your activities as part of use of the Services, we will forward the complaint to the primary email address of your user account. You must respond to the complainant directly within 10 days of receiving the complaint forwarded by us and copy MyLPGBooks in the communication. If you do not respond to the complainant within 10 days from the date of our email to you, we may disclose your name and contact information to the complainant for enabling the complainant to take legal action against you. You understand that your failure to respond to the forwarded complaint within the 10 days&#39; time limit will be construed as your consent to disclosure of your name and contact information by MyLPGBooks to the complainant.
<br><h4>Fees and Payments</h4>
The Services are available under subscription plans of various durations. Payments for subscription plans of duration of less than a year can be made only by IMPS/NEFT. Your subscription will be automatically renewed at the end of each subscription period unless you downgrade your paid subscription plan to a free plan or inform us that you do not wish to renew the subscription. At the time of automatic renewal, the subscription fee will be charged to the Credit Card last used by you. We provide you the option of changing the details if you would like the payment for the renewal to be made through a different Credit Card. If you do not wish to renew the subscription, you must inform us at least seven days prior to the renewal date. If you have not downgraded to a free plan and if you have not informed us that you do not wish to renew the subscription, you will be presumed to have authorized MyLPGBooks to charge the subscription fee to the Credit Card last used by you. Please click here to know about our Refund Policy.
From time to time, we may change the price of any Service or charge for use of Services that are currently available free of charge. Any increase in charges will not apply until the expiry of your then current billing cycle. You will not be charged for using any Service unless you have opted for a paid subscription plan.
<br><h4>Restrictions on Use</h4>
In addition to all other terms and conditions of this Agreement, you shall not: (i) transfer the Services or otherwise make it available to any third party; (ii) provide any service based on the Services without prior written permission; (iii) use the third party links to sites without agreeing to their website terms & conditions; (iv) post links to third party sites or use their logo, company name, etc. without their prior written permission; (v) publish any personal or confidential information belonging to any person or entity without obtaining consent from such person or entity; (vi) use the Services in any manner that could damage, disable, overburden, impair or harm any server, network, computer system, resource of MyLPGBooks; (vii) violate any applicable local, state, national or international law; and (viii) create a false identity to mislead any person as to the identity or origin of any communication.
<br><h4>Spamming and Illegal Activities</h4>
You agree to be solely responsible for the contents of your transmissions through the Services. You agree not to use the Services for illegal purposes or for the transmission of material that is unlawful, defamatory, harassing, libelous, invasive of another's privacy, abusive, threatening, harmful, vulgar, pornographic, obscene, or is otherwise objectionable, offends religious sentiments, promotes racism, contains viruses or malicious code, or that which infringes or may infringe intellectual property or other rights of another. You agree not to use the Services for the transmission of "junk mail", "spam", "chain letters", <q>phishing</q> or unsolicited mass distribution of email. We reserve the right to terminate your access to the Services if there are reasonable grounds to believe that you have used the Services for any illegal or unauthorized activity.
<br><h4>Inactive User Accounts Policy</h4>
We reserve the right to terminate unpaid user accounts that are inactive for a continuous period of 120 days. In the event of such termination, all data associated with such user account will be deleted. We will provide you prior notice of such termination and option to back-up your data. The data deletion policy may be implemented with respect to any or all of the Services. Each Service will be considered an independent and separate service for the purpose of calculating the period of inactivity. In other words, activity in one of the Services is not sufficient to keep your user account in another Service active. In case of accounts with more than one user, if at least one of the users is active, the account will not be considered inactive.
<br><h4>Data Ownership</h4>
We respect your right to ownership of content created or stored by you. You own the content created or stored by you. Unless specifically permitted by you, your use of the Services does not grant MyLPGBooks the license to use, reproduce, adapt, modify, publish or distribute the content created by you or stored in your user account for MyLPGBooks&#39;s commercial, marketing or any similar purpose. But you grant MyLPGBooks permission to access, copy, distribute, store, transmit, reformat, publicly display and publicly perform the content of your user account solely as required for the purpose of providing the Services to you.
<br><h4>User Generated Content</h4>
You may transmit or publish content created by you using any of the Services or otherwise. However, you shall be solely responsible for such content and the consequences of its transmission or publication. Any content made public will be publicly accessible through the internet and may be crawled and indexed by search engines. You are responsible for ensuring that you do not accidentally make any private content publicly available. Any content that you may receive from other users of the Services, is provided to you AS IS for your information and personal use only and you agree not to use, copy, reproduce, distribute, transmit, broadcast, display, sell, license or otherwise exploit such content for any purpose, without the express written consent of the person who owns the rights to such content. In the course of using any of the Services, if you come across any content with copyright notice(s) or any copy protection feature(s), you agree not to remove such copyright notice(s) or disable such copy protection feature(s) as the case may be. By making any copyrighted/copyrightable content available on any of the Services you affirm that you have the consent, authorization or permission, as the case may be from every person who may claim any rights in such content to make such content available in such manner. Further, by making any content available in the manner aforementioned, you expressly agree that MyLPGBooks will have the right to block access to or remove such content made available by you if MyLPGBooks receives complaints concerning any illegality or infringement of third party rights in such content. By using any of the Services and transmitting or publishing any content using such Service, you expressly consent to determination of questions of illegality or infringement of third party rights in such content by the agent designated by MyLPGBooks for this purpose.
For procedure relating to complaints of illegality or infringement of third party rights in content transmitted or published using the Services, click here.
If you wish to protest any blocking or removal of content by MyLPGBooks, you may do so in the manner provided here.
<br><h4>Sample files and Applications</h4>
MyLPGBooks may provide sample files and applications for the purpose of demonstrating the possibility of using the Services effectively for specific purposes. The information contained in any such sample files and applications consists of random data. MyLPGBooks makes no warranty, either express or implied, as to the accuracy, usefulness, completeness or reliability of the information or the sample files and applications.
<br><h4>Trademark</h4>
MyLPGBooks Apps, MyLPGBooks, MyLPGBooks logo, the names of individual Services and their logos are trademarks of MyLPGBooks . You agree not to display or use, in any manner, the MyLPGBooks trademarks, without MyLPGBooks&#39;s prior permission.
<br><h4>Disclaimer of Warranties</h4>
YOU EXPRESSLY UNDERSTAND AND AGREE THAT THE USE OF THE SERVICES IS AT YOUR SOLE RISK. THE SERVICES ARE PROVIDED ON AN AS-IS-AND-AS-AVAILABLE BASIS. MyLPGBooks EXPRESSLY DISCLAIMS ALL WARRANTIES OF ANY KIND, WHETHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. MyLPGBooks MAKES NO WARRANTY THAT THE SERVICES WILL BE UNINTERRUPTED, TIMELY, SECURE, OR ERROR FREE. USE OF ANY MATERIAL DOWNLOADED OR OBTAINED THROUGH THE USE OF THE SERVICES SHALL BE AT YOUR OWN DISCRETION AND RISK AND YOU WILL BE SOLELY RESPONSIBLE FOR ANY DAMAGE TO YOUR COMPUTER SYSTEM, MOBILE TELEPHONE, WIRELESS DEVICE OR DATA THAT RESULTS FROM THE USE OF THE SERVICES OR THE DOWNLOAD OF ANY SUCH MATERIAL. NO ADVICE OR INFORMATION, WHETHER WRITTEN OR ORAL, OBTAINED BY YOU FROM MyLPGBooks, ITS EMPLOYEES OR REPRESENTATIVES SHALL CREATE ANY WARRANTY NOT EXPRESSLY STATED IN THE TERMS.
<br><h4>Limitation of Liability</h4>
YOU AGREE THAT MyLPGBooks SHALL, IN NO EVENT, BE LIABLE FOR ANY CONSEQUENTIAL, INCIDENTAL, INDIRECT, SPECIAL, PUNITIVE, OR OTHER LOSS OR DAMAGE WHATSOEVER OR FOR LOSS OF BUSINESS PROFITS, BUSINESS INTERRUPTION, COMPUTER FAILURE, LOSS OF BUSINESS INFORMATION, OR OTHER LOSS ARISING OUT OF OR CAUSED BY YOUR USE OF OR INABILITY TO USE THE SERVICE, EVEN IF MyLPGBooks HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. IN NO EVENT SHALL MyLPGBooks&#39;S ENTIRE LIABILITY TO YOU IN RESPECT OF ANY SERVICE, WHETHER DIRECT OR INDIRECT, EXCEED THE FEES PAID BY YOU TOWARDS SUCH SERVICE.
<br><h4>Indemnification</h4>
You agree to indemnify and hold harmless MyLPGBooks, its officers, directors, employees, suppliers, and affiliates, from and against any losses, damages, fines and expenses (including attorney's fees and costs) arising out of or relating to any claims that you have used the Services in violation of another party's rights, in violation of any law, in violations of any provisions of the Terms, or any other claim related to your use of the Services, except where such use is authorized by MyLPGBooks.
<br><h4>Arbitration</h4>
Any controversy or claim arising out of or relating to the Terms shall be settled by binding arbitration in accordance with the commercial arbitration rules of the American Arbitration Association. Any such controversy or claim shall be arbitrated on an individual basis, and shall not be consolidated in any arbitration with any claim or controversy of any other party. The decision of the arbitrator shall be final and un-appealable. The arbitration shall be conducted in California and judgment on the arbitration award may be entered into any court having jurisdiction thereof. Notwithstanding anything to the contrary, MyLPGBooks may at any time seek injunctions or other forms of equitable relief from any court of competent jurisdiction.
<br><h4>Suspension and Termination</h4>
We may suspend your user account or temporarily disable access to whole or part of any Service in the event of any suspected illegal activity, extended periods of inactivity or requests by law enforcement or other government agencies. Objections to suspension or disabling of user accounts should be made to <a href="mailto:support@mylpgbooks.com">support@mylpgbooks.com</a> within thirty days of being notified about the suspension. We may terminate a suspended or disabled user account after thirty days. We will also terminate your user account on your request.
<br>In addition, we reserve the right to terminate your user account and deny the Services upon reasonable belief that you have violated the Terms and to terminate your access to any Beta Service in case of unexpected technical issues or discontinuation of the Beta Service. You have the right to terminate your user account if MyLPGBooks breaches its obligations under these Terms and in such event, you will be entitled to prorated refund of any prepaid fees. Termination of user account will include denial of access to all Services, deletion of information in your user account such as your email address and password and deletion of all data in your user account.
<br><h4>END OF TERMS OF SERVICE</h4>
If you have any questions or concerns regarding this Agreement, please contact us at <a href="mailto:support@mylpgbooks.com">support@mylpgbooks.com</a> .
<br>
<!-- <div style="float:right;">
<input id="checkbox1" type="checkbox">
<label for="checkbox1" >I Agree</label>
</div> -->
</div>
        					</div>
        				</div>
        					
<!--         			<div style="display:none;overflow:scroll;width:500px;height:500px;position:absolute;border:2px solid white;background-color:white; " id="tos">
 -->
					</div>
				
				
				
        		<%} %>
<!--     		</div> -->
</section>

  	</body>
    <script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
    <script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
    <script src="js/commons/main.js?<%=randomUUIDString%>"></script>
    <script type="text/javascript">
		$('.msgcls').click(function(e) { //button click class name is msgcls
			e.stopPropagation();
		});
		if(!($("#reg_errmsg").is(":visible"))){
			$(".login-box").css("margin-left","0px");
		}
		$(function(){
			$(document).click(function(){  
				$('.msgcls').hide();
				
				if(!($("#reg_errmsg").is(":visible"))){
					$(".login-box").css("margin-left","0px");
				}

			});
			$('.forgotpwd').click(function(){  
				$('.msgcls').hide();
			});
			$('.cleardata').click(function(){
				$('#dealer_login_form')[0].reset();
			});
 			$('.clearregdata').click(function(){
				$('#dealer_reg_form')[0].reset();
			});
			$('.clearform').click(function(){  
				$('#forget-form')[0].reset();
			});

		});
	</script>
    <script type="text/javascript">
    	function termsOfServices() {
//    		document.getElementById("contbox").style.display="none";
    		document.getElementById("myModal").style.display="block";
    	}
    	function closeFormDialog() {
    		document.getElementById("myModal").style.display="none";
//    		document.getElementById("contbox").style.display="block";
    	}
    	
    </script>
	<script type="text/javascript">
		$("#pwd").password('toggle');
	</script>
	
	<script>
	//	Example For Optional Comment:
	//	<script>
	//	<!-- 
	//	document.write("Helloo"); //-->
	//	</script>
	</script>
	
</html>