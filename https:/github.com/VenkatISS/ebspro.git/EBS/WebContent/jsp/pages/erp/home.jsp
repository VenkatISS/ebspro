<%@ page import="java.util.Map,java.util.Calendar,java.util.UUID" %>
<!DOCTYPE html>
<html>
	<%
		UUID uuid = UUID.randomUUID();
	    String randomUUIDString = uuid.toString();
    %>
  <head>
	<meta charset="utf-8">
<!--     <meta http-equiv="X-UA-Compatible" content="IE=edge"> -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="pragma" content="nocache">
   	<!-- CSS-->
    <link rel="stylesheet" type="text/css" href="css/main.css?<%=randomUUIDString%>">
    <title>MyLPGBooks DEALER EBS WEB APPLICATION - HOME PAGE</title>
 	<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
	<jsp:useBean id="statutory_data" scope="request" class="java.lang.String"></jsp:useBean>
	<jsp:useBean id="statutory_items_data" scope="session" class="java.lang.String"></jsp:useBean>
	
	<script type="text/javascript" src="js/local.js?<%=randomUUIDString%>"></script>
    <script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>
    <script type="text/javascript" src="js/home.js?<%=randomUUIDString%>"></script>
    <script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>

	<!-- SideNav-->
	<jsp:include page="/jsp/pages/commons/sidenav.jsp"/>   
	<!--SideNav End--->
	
 	<script type="text/javascript">
		window.onload = setwidthHeight("100%");
 	</script>
	<script type="text/javascript">
		var statutory_items = <%= statutory_items_data.length()>0? statutory_items_data : "[]" %>;
		var statutory_data =  <%= statutory_data.length()>0? statutory_data : "[]" %>;
		var reminderDays = [0,7,15,30,60];
		var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];		
	</script>
	<%
    	int cyear = Calendar.getInstance().get(Calendar.YEAR);
        String cfy = (Integer.toString(cyear)).substring(2);
    %>
	<style>
		html{
			overflow : hidden;
		}
    </style>
   
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<style>
		.bg_details {
			margin-top: 1%;
		}
	</style>

	<script>
	/* $(document).ready(function() {
   		$('[data-toggle="popover"]').popover({
			placement: 'bottom',
			container: 'body',
			html: true,
			content: function () {
				var clone = $($(this).data('popover-content')).clone(true).removeClass('hide');
				return clone;
			}
		}).click(function(e) {
			e.preventDefault();

		});
	}); */

	$(function(){
		$("#nav .dropdown").hover(
			function() {
				$('#products-menu.dropdown-menu', this).stop( true, true ).fadeIn("fast");
				$(this).toggleClass('open');
			},
			function() {
				$('#products-menu.dropdown-menu', this).stop( true, true ).fadeOut("fast");
				$(this).toggleClass('open');
			});
		});

	</script>
  </head>
<!--      <body class="sidebar-mini fixed body_bg  pace-done sidebar-collapse homebody home_body">      -->
	<body class="sidebar-mini fixed body_bg pace-done homebody hbody homepage_body">
	
    	<div class="wrapper" style="background-image: url(images/bg13.jpg);">
      		<!-- Header-->
			<jsp:include page="/jsp/pages/commons/header.jsp"/>
	  		<!--header End--->

       		<div class="content-wrapper content_wrapper">
 			<% Map<String,Object> details = (Map<String,Object>) session.getAttribute("details");
    		if(agencyVO.getIs_ftl()==0) { %>
	        	<div class="row">
		 		<!-- Modal -->
					<div class="modal_popup_add fade in" id="myModal" style="display: none;padding-left: 14px; height: 100%">
						<div class="modal-content hmcontent">
							<div style="text-align: center;">
								<br>
								<div id="firstloginDiv" align="center">
									<font color="#0b1e59" face="tahoma" size="3" style="font-weight: bold;"> PLEASE ENTER THE FOLLOWING DETAILS</font>
								</div>
							</div>
							
							<ul id="nav" class="nav nav-pills clearfix right" role="tablist" style="float:right;">
          						<li class="dropdown"><span class="dropdown-toggle  btn-warn" data-toggle="dropdown" style="text-decoration: underline; color:#2cbfca;/* margin-left: 1120px; */" id="ahelp">help?</span>
									<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
										<li><a href="files/MyLPGBooks Promo.mp4" target="_blank">English</a></li>
<!-- 									<li><a style="font-size: 14px;" href="https://youtu.be/bgQnKRrz2X8" target="_blank">Hindi</a></li> -->
									</ul>
								</li>
							</ul>
							
							<div class="col-md-4 col-sm-4 col-xs-4 login-box" id="loginDiv" style="margin-top: 1%; text-align: center;">
								<form id="data_form" name="" method="post" action="AgencyControlServlet">
									<input type="hidden" id="agencyId" name="agencyId" value="<%=agencyVO.getAgency_code()%>"> 
									<input type="hidden" id="bankId" name="bankId" value=""> 
									<input type="hidden" id="actionId" name="actionId" value="1003">
									<input type="hidden" name="actionId" value="1001"> 
									<input type="hidden" id="page" name="page" value="erp/profile"> 
									<input type="hidden" name="page" value="erp/home"> <BR>
						
								<%-- 	<table width="800px" >
										<tr ><td style='width: 150px;'>	<b>FINANCIAL YEAR</b> </td>
											<td style='width: 180px;' >	
												<select class="form-control select_dropdown" name='fy' id='fy' style='width: 190px;align:left;'>
													<option value='0'>SELECT YEAR</option>
													<option value='18'>2018-19</option>
													<option value='19'>2019-20</option>
													<option value='20'>2020-21</option>
												</select></td>
											<td style='width: 40px;'></td>
						    				<td style='width: 180px;'><b>MONTH</b> </td>
											<td><select class="form-control select_dropdown" name='mon' id='mon' style='width: 190px;'>
													<option value='0'>SELECT MONTH</option>
													<option value='01'>JAN</option>
													<option value='02'>FEB</option>
													<option value='03'>MAR</option>
													<option value='04'>APR</option>
													<option value='05'>MAY</option>
													<option value='06'>JUN</option>
													<option value='07'>JUL</option>
													<option value='08'>AUG</option>
													<option value='09'>SEP</option>
													<option value='10'>OCT</option>
													<option value='11'>NOV</option>
													<option value='12'>DEC</option>
												</select>
											</td>
										</tr>
										<tr style="height:40px"><td></td></tr>
										<tr><td style='width: 150px;' >	<b>LOAD ACC<br> OPENING<br> BALANCE </b></td>
											<td style='width: 180px;'><input class="form-control" type="text" id="tarob" name="tarob" value="" placeholder="LOAD ACCT OPENING BALANCE"></td>
											<td style='width: 40px;'></td>
											<td style='width: 180px;'>	<b>SV/TV ACC OPENING BALANCE</b> </td>
											<td style='width: 180px;'><input class="form-control" type="text" id="starob" name="starob" value="" placeholder="SV/TV ACCT OPENING BALANCE"></td>
										</tr>
										<tr style="height:40px"><td></td></tr>
										<tr><td style='width: 150px;'><b>CASH BALANCE</b> </td>
											<td style='width: 180px;'><input class="form-control" type="text" id="cashb" name="cashb" value="" placeholder="CASH OPENING BALANCE"></td>
											<td style='width: 40px;'></td>
											<td style='width: 180px;'>	<b>EFFECTIVE<br> DATE</b> </td>
											<td style='width: 180px;'>
												<input type=date name='ed' id='ed' class='form-control input_field eadd' placeholder='DD-MM-YYYY'>
											</td>
										</tr>
										<tr><td></td></tr>
							
										<tr style="height:40px"><td></td></tr>
										<tr><td style='width: 150px;'><b>B2B INVOICE<br> COUNTER</b> </td>
                            				<td style='width: 100px;'><input class="form-control" type="text" id="btb" name="btb" value="SI-<%=cfy %>-" style="/* margin-left:20px; */width:73px;height:40px;border-right: 0px;" readonly="readonly"></td>
                                			<td style='width: 180px;'><input class="form-control" type="text" maxlength="4" id="btbInvCounter" name="btbInvCounter" value="" placeholder="001" style="border-left:  0px;margin-left:-120px;width:80px;"></td>
                                			<td style='width: 180px;'><b>B2C INVOICE<br> COUNTER</b> </td>
                                			<td style='width: 100px;'><input class="form-control" type="text" id="btc" name="btc" value="CS-<%=cfy %>-" readonly="readonly" style="border-right: 0px;width:73px"></td>
                                			<td style='width: 180px;'><input class="form-control" type="text" maxlength="4" id="btcInvCounter" name="btcInvCounter" value="" placeholder="001" style=" border-left: 0px;margin-left:-120px;width:80px;"></td>
                                			<td style='width: 40px;'></td>
                            			</tr>
                            			<tr><td></td></tr>

										<tr style="height:40px"></tr>
										<tr style="height:40px">
											<td style='min-width: 150px;'>	<b>UJWALA<br> OPENING<br> BALANCE</b> </td>
											<td style='width: 180px;'><input class="form-control" type="text" id="ujwob" name="ujwob" value="" placeholder="UJWALA OPENING BALANCE"></td>
										</tr>
										<tr><td></td></tr>
									</table>
 --%>
 
                                        <table width="800px" >
										<tr ><td style='width: 150px;'>	<b>FINANCIAL YEAR</b> </td>
											<td style='width: 180px;' >	
												<select class="form-control select_dropdown" name='fy' id='fy' style='width: 160px;align:left;'>
													<option value='0'>SELECT YEAR</option>
													<option value='18'>2018-19</option>
													<option value='19'>2019-20</option>
													<option value='20'>2020-21</option>
												</select></td>
                                  <!--		<td style='width: 40px;'></td> -->
						    				<td style='width: 40px;margin-left:-60px;'><b>MONTH</b> </td>
											<td><select class="form-control select_dropdown" name='mon' id='mon' style='width: 160px;margin-left:-30px;'>
													<option value='0'>SELECT MONTH</option>
													<option value='01'>JAN</option>
													<option value='02'>FEB</option>
													<option value='03'>MAR</option>
													<option value='04'>APR</option>
													<option value='05'>MAY</option>
													<option value='06'>JUN</option>
													<option value='07'>JUL</option>
													<option value='08'>AUG</option>
													<option value='09'>SEP</option>
													<option value='10'>OCT</option>
													<option value='11'>NOV</option>
													<option value='12'>DEC</option>
												</select>
											</td>
                                            <td style='width: 40px;'></td>
										</tr>
										
									<!--<tr style="height:40px"><td></td></tr>
										<tr> <td style='width: 150px;' >	<b>LOAD ACC<br> OPENING<br> BALANCE </b></td> -->
											<td style='width: 180px;'><input class="form-control" type="hidden" id="tarob" name="tarob" value="0.00" placeholder="LOAD ACCT OPENING BALANCE" readonly></td>
											<td style='width: 40px;'></td>
											<!-- <td style='width: 180px;'>	<b>SV/TV ACC OPENING BALANCE</b> </td> -->
											<td style='width: 180px;'><input class="form-control" type="hidden" id="starob" name="starob" value="0.00" placeholder="SV/TV ACCT OPENING BALANCE" readonly></td>
										<!-- </tr> -->
										
								<!-- 	<tr style="height:40px"><td></td></tr>	<tr><td style='width: 150px;'><b>CASH BALANCE</b> </td> -->
											<td style='width: 180px;'><input class="form-control" type="hidden" id="cashb" name="cashb" value="0.00" placeholder="CASH OPENING BALANCE" readonly></td>
											<td style='width: 40px;'></td>
											<!-- <td style='width: 180px;'>	<b>EFFECTIVE<br> DATE</b> </td>
											<td style='width: 180px;'>
												<input type=date name='ed' id='ed' class='form-control input_field eadd' placeholder='DD-MM-YYYY'>
											</td> -->
										<!-- </tr> 
										<tr><td></td></tr>-->
										
							
										<tr style="height:40px"><td></td></tr>
										<tr><td style='width: 150px;'><b>B2B INVOICE<br> COUNTER</b> </td>
                            				<td style='width: 50px;'><input class="form-control" type="text" id="btb" name="btb" value="SI-<%=cfy %>-" style="/* margin-left:20px; */width:70px;height:40px;border-right: 0px;" readonly="readonly">
                                            <input class="form-control" type="text" maxlength="4" id="btbInvCounter" name="btbInvCounter" value="" placeholder="001" style="border-left:  0px;margin-left:67px;margin-top:-40px;width:90px;"></td>
                                			<td style='width: 180px;margin-left:-100px;'><b>B2C INVOICE<br> COUNTER</b> </td>
                                			<td style='width: 100px;'><input class="form-control" type="text" id="btc" name="btc" value="CS-<%=cfy %>-" readonly="readonly" style="width:70px;margin-left:-30px;"></td>
                                			<td style='width: 180px;'><input class="form-control" type="text" maxlength="4" id="btcInvCounter" name="btcInvCounter" value="" placeholder="001" style="border-left:0;margin-left:-120px;width:90px;"></td>
                                			<td style='width: 40px;'></td>
                            			</tr>
                            			<tr><td></td></tr>

										
									<!-- <tr style="height:40px"></tr>	<tr style="height:40px">
											<td style='min-width: 150px;'>	<b>UJWALA<br> OPENING<br> BALANCE</b> </td> -->
											<td style='width: 180px;'><input class="form-control" type="hidden" id="ujwob" name="ujwob" value="0.00" placeholder="UJWALA OPENING BALANCE" readonly></td>
									<!-- 	</tr>  <tr><td></td></tr>-->
										
																			<tr style="height:40px"></tr>
										<td></td>
										<td style='width: 280px;'>	<b>EFFECTIVE DATE</b> </td>
											<td style='width: 180px;'>
												<input type=date name='ed' id='ed' class='form-control input_field eadd' placeholder='DD-MM-YYYY' style='margin-left:-40px;'>
											</td>
											<td></td>
											</tr>
									</table>

 								</form>
								<br>
								<input type="button" value="SUBMIT DATA" class="btn btn-info" style="float: right;padding-left: 20px;margin-right:-200px" onclick="submitData()">
							</div>
						</div>
					</div>
					<%
						} else {
					%>
		 			<div id="home_div" style="">
						<div class="row" style="background-image: url(images/bg13.jpg);">
							<ul id="nav" class="nav nav-pills clearfix right" role="tablist">
								<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
							<!-- <li><span class="hai" style="margin-left:170px;padding:10px;background-color:red;">SiriGB</span> </li> -->
   
								<li class="dropdown"><span class="dropdown-toggle  btn-primary" data-toggle="dropdown">Downloads</span>
        							<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
										<center>
<!--           									<i>Here you can download all the files that you need.</i>  -->
          									<p>Here you can download all the files that you need.</p><br>
										</center>
										
										<h6>MASTER DATA EXCEL SHEET: </h6>
										<li><a style="font-size: 14px;" href="files/Ilpg MasterData.xls" download="EBS_MasterData.xls">MyLPGBooks EBS master data sheet.xls</a></li>
										
										<h6>USER MANUAL: </h6>
										<li><a style="font-size: 14px;" href="files/MyLPGBooks User Manual.pdf" download>User Manual: version 1.0</a></li>
										<h6>PRIVACY POLICY: </h6>
										<li>  <a style="font-size: 14px;" href="files/Privacy Policy MyLPGBooks.pdf" download>Privacy Policy: version 1.0</a></li>
										<h6>TERMS OF SERVICES: </h6>
										<li><a style="font-size: 14px;" href="files/Terms of Service MyLPGBooks.pdf" download>Terms Of Services: version 1.0</a></li>
										<br><br>
									</ul>
								</li>
							
								<li class="dropdown"><span class="dropdown-toggle  btn-success" data-toggle="dropdown">Contact Us</span>
									<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
										<center><br>
											<p style="font-size: 13px;">Email us with any questions or inquires or call us. We would be happy to answer your questions and set up a meeting with you. <br>Our app can help set you apart from the flock!</p>
										</center>
          								<br>
          								<p style="font-size: 15px;">Contact Details:</p>
          								<p style="font-size: 13px;"><b >Email Id </b> <span style="padding:0px 35px 35px 33px;">: &nbsp&nbsp support@mylpgbooks.com </span></p>
										<p style="font-size: 13px;"><b>Landline No </b> <span style="padding:5px;">: &nbsp&nbsp 040 23546767 </span></p>
										<p style="font-size: 13px;"><b>Address </b> <span style="padding:0px 30px 30px 28px;">: &nbsp&nbsp Road No.41, Jubilee Hills, H.No.8-2-293/82/A,Plot No:1091, Hyderabad, Telangana 500033 </span> </p>
										<br>
									</ul>
								</li>
    
								<li class="dropdown"><span class="dropdown-toggle btn-info" data-toggle="dropdown" style="padding:10px;">Help</span>
									<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
										<li><a href="files/MyLPGBooks Promo.mp4" target="_blank">English</a></li>
<!-- 										<li><a href="https://youtu.be/bgQnKRrz2X8" target="_blank">Hindi</a></li> -->									
									</ul>
								</li>

								<li class="dropdown"><span class="dropdown-toggle  btn-warning" data-toggle="dropdown">Client List</span>
									<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
										<li><a class="dropdown-toggle" data-toggle="dropdown" href="#clntlst" onclick="doAction('MasterDataControlServlet','jsp/pages/docs/clientlist.jsp','2002')">Client List</a></li>
									</ul>
								</li>
							</ul>
				
							<div class="row row-in white-box hmwbox" style="/* margin-left: 16%; */margin-right: 0px;padding: 0px;height: 700px;">
								<div class="col-lg-9 col-sm-8 col-xs-12 row-in-br imgndtext">
									<div class="dsply cih lftdsply lft" style="">
										CASH IN HAND : &nbsp&nbsp <span id="cihand" style="font-weight:bold;"></span>
									</div>
									<div class="dsply crs lftdsply lft" style="">
										PAYABLES : &nbsp&nbsp <span id="creditors" style="font-weight:bold;"></span>
									</div>
									<div class="dsply dtrs lftdsply lft" style="">
										RECEIVABLES : &nbsp&nbsp <span id="debitors" style="font-weight:bold;"></span>
									</div>

									<div class="dsply ldact rtdsply rght" style="">
										LOAD ACCOUNT : &nbsp&nbsp <span id="ldacct" style="font-weight:bold;"></span>
									</div>
									<div class="dsply svact rtdsply rght" style="">
										SV ACCOUNT : &nbsp&nbsp <span id="svacct" style="font-weight:bold;"></span>
									</div>
									<div class="dsply denddate rtdsply rght" style="">
										LAST DAYEND DATE:<span id="denddate"></span>
									</div>
								</div>

								<div class = "alertsAndnews">
									<div class="alerts_div" style="padding-bottom:10px;">
 										<p style="color:white;padding-bottom:3px;">ALERTS & NOTIFICATIONS</p>
 										 										
										<marquee id="alertsmarq" behavior="scroll" scrollamount="3"
													direction="up" onMouseOver="document.all.alertsmarq.stop()"
													onMouseOut="document.all.alertsmarq.start()" style="margin-right: 5%;">
											<div id="alertsmsg" class="marquee hmalmrq" style="height:300px;"></div>
										</marquee>										
									</div>
									
									<div style="background-color:white;height:1px;margin-left:-24px;margin-right:-10px;"><br></div>

									<div class="news_div" style="color:white;">
 										<p style="padding-top: 25px;">NEWS</p>
										<marquee id="newsmarq" behavior="scroll" scrollamount="3"
													direction="up" onMouseOver="document.all.newsmarq.stop()"
													onMouseOut="document.all.newsmarq.start()" style="margin-right: 15%;">

											<div id="news" style="min-height:275px;max-height:275px;">
												PRICE TREND FOR JULY 2019: <br>
												<b>==></b> DOMESTIC-14.2 KG NS CYLINDER has <br>
												  decrease of Rs.100.00 to Rs. 110.00<sup> <font size=2> * </font> </sup>
												  <br>per cylinder.
												<br><br>
												<b>==></b> COMMERCIAL-19 KG CYLINDER has<br>
												   decrease of Rs.170.00 to Rs. 190.00 <br>per cylinder.
												<br><br>
												
												* If OMCs initiate recovers recovery of 
												  <br>election period loss The domestic may 
												  <br>go down around 65.00 to 75.00 only.
												
											</div>
										</marquee>
									</div>
									<br style="clear:both;"/>
								</div>
							</div>


							<div class="tos_modal fade in" id="myAboutus" style="display: none; margin-left: -25%; height:100%;width: 156%;">
								<div class="modal-dialog modal-lg">
			  						<!-- Modal content-->
			  						<div class="tos_modal_div">
										<div class="modal-header">
					  						<span class="close" id="close" onclick="closeFormDialog()">&times;</span>
					  						<h4>NEW VERSION REALESE NOTES</h4>
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
MyLPGBooks, MyLPGBooks, MyLPGBooks logo, the names of individual Services and their logos are trademarks of MyLPGBooks. You agree not to display or use, in any manner, the MyLPGBooks trademarks, without MyLPGBooks&#39;s prior permission.
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
							</div>
						</div>
					</div>	
				<% } %>
				</div>
			</div>
		</div>

		<!-- Footer Start-->
		<jsp:include page="/jsp/pages/commons/footer.jsp"/>      
	  	<!--Footer End--->	
			
	    <!-- Javascripts-->
		<script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
		<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
    	<script src="js/commons/main.js?<%=randomUUIDString%>"></script>
    	<script type="text/javascript">
			document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>;
		
			var dedate = new Date(<%= agencyVO.getDayend_date() %>);
			document.getElementById("denddate").innerHTML = "<b>"+dedate.getDate()+"-"+months[dedate.getMonth()]+"-"+dedate.getFullYear()+"</b>";
			
			document.getElementById("cihand").innerHTML = <%=details.get("cashBal") %>;
			document.getElementById("ldacct").innerHTML = <%=details.get("tarcb") %>;
			document.getElementById("svacct").innerHTML = <%=details.get("starcb") %>;
			document.getElementById("creditors").innerHTML = round((<%=details.get("ctrs_Pbls") %>),2);
    	    document.getElementById("debitors").innerHTML = round((<%=details.get("dtrs_Rcbls") %>),2);		
		</script>
	</body>
	<div id = "dialog-1" title="Alert(s)"></div>
	<script type="text/javascript">
		function aboutUs() {
			document.getElementById("myAboutus").style.display="block";
		}
		function closeFormDialog() {
			document.getElementById("myAboutus").style.display="none";
		}
    </script>
	<script type="text/javascript">
		<% if(agencyVO.getIs_ftl()==0) { %>
				document.getElementById('myModal').style.display = "block";
		<% } %>
		alertsAndNotifications();

		$(document).ready(function() {
			/* tooltip for select */
			$('select').each(function(){
				var tooltip = $(this).tooltip({
    				selector: 'data-toggle="tooltip"',
        			trigger: 'manual'
    			});
    			var selection = $(this).find('option:selected').text();
    			tooltip.attr('title', selection)
	
	    	$('select').change(function() {
    			var selection = $(this).find('option:selected').text();
    		    tooltip.attr('title', selection)
	    	});
		});
	});
	</script>
	<script>
		var ctoggle = 0;
		function checkToggle(){
			if(ctoggle == 0){
				$(".homebody").addClass('home_body');
			}else {
				$(".homebody").removeClass('home_body');
			}
			++ctoggle;
		}
	</script>
</html>
