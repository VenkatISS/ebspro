<!-- Eqp HSN Code: 27111900 -->
<!DOCTYPE html>
<%@ page import="com.it.erpapp.response.MessageObject,java.util.UUID" %>
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
    <title>MyLPGBooks DEALER EBS WEB APPLICATION - PROFILE</title>
    <jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
    <jsp:useBean id="profile_data" scope="request" class="java.lang.String"></jsp:useBean>
    
    <script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>
    <script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
    <!-- Sidenav -->
    	<jsp:include page="/jsp/pages/commons/sidenav.jsp"/>
	<!---END Sidenav--->
	
    <script type="text/javascript">
        var pinValue=<%= agencyVO.getPinNumber() %>;
        var xlUplpoadStatus=<%= agencyVO.getXl_upload_status() %>;
    </script>
    <script type="text/javascript">
		window.onload = setWidthHightNav("100%");
    </script>

	<style>
	label{
		float:left;
		width:200px;
	}
	</style>
  
  </head>
  <body class="sidebar-mini fixed">
	<div class="wrapper">
		<!-- Header-->
    	<jsp:include page="/jsp/pages/commons/header.jsp"/>
		<!--header End--->

		<div class="content-wrapper">
<!-- 			<a href="https://youtu.be/bgQnKRrz2X8" target="_blank" style="text-decoration: underline; float:right;margin-right: 5px;">help?</a> -->
			<ul id="nav" class="nav nav-pills clearfix right" role="tablist" style="float:right;">
          		<li class="dropdown"><span class="dropdown-toggle  btn-warn" data-toggle="dropdown" style="text-decoration: underline; color:#2cbfca;/* margin-left: 1120px; */" id="ahelp">help?</span>
					<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
						<li><a href="files/MyLPGBooks Promo.mp4" target="_blank">English</a></li>
<!-- 						<li><a style="font-size: 14px;" href="https://youtu.be/bgQnKRrz2X8" target="_blank">Hindi</a></li> -->
					</ul>
				</li>
			</ul>
			
			<div>
			
        	<!-- Modal -->
            <div class="modal_popup_add fade in" id="myModal" style="display: none;padding-left: 14px; height: 100%">
            	<div class="modal-dialog modal-lg">
                	<!-- Modal content-->
                    <div class="modal-content prof_modalContent">
                    	<div class="modal-header" style="text-align:center;">
                        	<span class="close" id="close" onclick="closeChangeProfileDialog()">&times;</span>
                            <h4 class="modal-title">CHANGE PROFILE DETAILS</h4>
                        </div>
                        <div class="modal-body">
                        	<form id="data_form" name="" method="post" action="AgencyDataControlServlet" onsubmit="return submitUpdatedProfileDetails(this)">
                            	<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>" >
<%--                            <input type="hidden" id="pin" name="pin" value="<%= agencyVO.getPinNumber() %>">   --%>
                                <input type="hidden" id="page" name="page" value="jsp/pages/erp/profile.jsp">
								<input type="hidden" id="actionId" name="actionId" value="9002">
                                <input type="hidden" id="cgsta" name="cgsta" value="">
                                <br>

<!-- 
                                <table id="t_data_table">
                                	<tr class="spaceUnder">
                                    	<td>PERSON NAME:</td>
                                        <td> <input class="form-control" type="text" style="" name="cpname" id="cpname"  maxlength="35" placeholder="PERSON NAME" autofocus></td>
                                        <td>MOBILE:&nbsp</td>
                                        <td><input class="form-control" type="text" name="cdmobile" id="cdmobile" maxlength="10" placeholder="MOBILE NUMBER"></td>
                                        <td>&nbspADDRESS:&nbsp</td>
                                        <td><input class="form-control" type="text" name="cOffcAddr" id="cOffcAddr"   maxlength="50" placeholder="OFFICE ADDRESS"></td>
                                    </tr>
                                    <tr class="spaceUnder">
                                    	<td>EMAIL ID:&nbsp</td>
                                        <td><input class="form-control" type="text" name="cemailId" id="cemailId" placeholder="EMAIL ID"></td>
                                        <td>LANDLINE NUMBER:</td>
                                        <td><input class="form-control" type="text" name="stdc" id="stdc"  style="width:80px;"  placeholder="STD CODE "></td>
                                        <td><input class="form-control" type="text" name="lnno" id="lnno"  style="display: inline-block;float:left;margin-left:-60px;" placeholder="LANDLINE NUMBER ">
                                        <input type ="hidden" name="clnno" id="clnno"></td>
									</tr>
									<tr class="spaceUnder">
                                    	<td colspan="6">
                                        	
                                        	//<div class="submit_div" style="text-align:center;padding-top:20px;margin-bottom:-25px;">
                                            //	 	<button name="prof_submit_btn" id="prof_submit_btn" class="prof_submit_btn" onclick="submitUpdatedProfileDetails()">SUBMIT</button>
											//	</div> KEEP THESE IN COMMENTS AFTER REMOVING BLOCK COMMENTS
											
											<div class="submit_div" style="text-align:center;width:100px;margin-left: 350px;margin-top: 10px;">
                                            	<input type="submit" name="prof_submit_btn"  id="prof_submit_btn" value="SUBMIT" class="btn btn-success m-r-15">
											</div>
											
										</td>
									</tr>
								</table>
-->
								<table id="t_data_table">
									<tr class="spaceUnder">
                                    	<td>PERSON NAME: </td>
                                        <td> <input class="form-control" type="text" style="margin-left:1px;" name="cpname" id="cpname"  maxlength="35" placeholder="PERSON NAME" autofocus></td>
                                        <td><span style="float:right;">MOBILE:</span></td>
                                        <td><input class="form-control" type="text" style="margin-left:5px;" name="cdmobile" id="cdmobile" maxlength="10" placeholder="MOBILE NUMBER"></td>
                                    </tr>
                                    <tr class="spaceUnder">
                                    	<td>ADDRESS:&nbsp</td>
                                        <td><input class="form-control" type="text" style="margin-left:1px;" name="cOffcAddr" id="cOffcAddr" maxlength="50" placeholder="OFFICE ADDRESS"></td>
                                        <td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspLANDLINE NUMBER:</td>
                                        <td><input class="form-control" type="text" name="stdc" id="stdc" style="width:85px;margin-left:5px;" placeholder="STD CODE "></td>
                                        <td><input class="form-control" type="text" name="lnno" id="lnno" style="display:inline-block;float:left;margin-left: -104px;width: 105px;" placeholder="LANDLINE NUMBER ">
                                        <input type ="hidden" name="clnno" id="clnno"></td>
									</tr>
									<tr class="spaceUnder">
                                    	<td colspan="6">
											<div class="submit_div" style="text-align:center;width:100px;margin-left: 350px;margin-top: 10px;">
                                            	<input type="submit" name="prof_submit_btn"  id="prof_submit_btn" value="SUBMIT" class="btn btn-success m-r-15">
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
				</div>
			</div>
			
			
			<!-- --------------------------------------------UPDATE REGISTERED EMAIL ID START------------------------------------------- -->
			
			 <!-- popup div -->
     	 	<div class="modal_popup_add fade in" id="myModalEmIdUpPin" style="display:none;" padding-left: 14px; height: 100%">
      			<div class="modal-dialog modal-lg">
        			<!-- Modal content-->
            		<div class="modal-content prof_modalContent" style="height:200px;width:60%; margin:auto;">
						<div id="contentDiv" style="display:none;">
							<div class="modal-header" style="text-align:center;">
                    			<h4 class="modal-title">ENTER SECURITY PIN DETAILS</h4>
                   			</div>
                    		<div class="modal-body">
								<input type="hidden" id="pinNO" name="pinNO" value="<%= agencyVO.getPinNumber() %>">
                       			<table id="t_data_table">
									<tr class="spaceUnder">
										<td>Pin Number:&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td><input class="form-control" type="password" name="enteredPin" id="enteredPin" placeholder="Enter pin here" autofocus></td>
										<td colspan="6">
											<div class="submit_div" style="text-align: center; width: 100px; margin-left: 40px; margin-top: -3px;">
												<input type="button" name="prof_submit_btn" id="prof_submit_btn" value="SUBMIT" class="btn btn-success m-r-15" onclick="submitPinNumber()">
											</div>
										</td>
									</tr>
								</table>
								<div class="submit_div" style="text-align: center; width: 100px; margin-left: 2px; margin-top: 10px;">
                  					<button name="prof_submit_btn" id="prof_submit_btn" class="btn btn-success m-r-15" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/profile.jsp','9001')">BACK</button>
                  				</div>
                			</div>
                		</div>
                		
                		<div id="displayDiv" style="display:none;">
        					<div class="modal-header" style="text-align:center;">
    	            			<h4 class="modal-title">SECURITY PIN DETAILS</h4>
							</div>
							<div class="modal-body"><br>
								YOU  HAVEN'T  SET  ANY  PROFILE  PIN.
								<a href="#prof" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/profile.jsp','9001')">CLICK HERE</a>
								TO  SET  PIN  OR  SET  PIN  IN  YOUR  PROFILE  DETAILS  AND  THEN  PROCEED  <br><br>
								<div class="submit_div" style="text-align: center; width: 100px; margin-left: 200px;">
									<button name="prof_submit_btn" id="prof_submit_btn" class="btn btn-success m-r-15" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/profile.jsp','9001')">BACK</button>
								</div>
        					</div>
        				</div>
					</div>
       			</div>
 			</div>
			
			<!-- ENTER NEW AND OLD EMAIL IDs POPUP -->
			<!-- Modal -->
            <div class="modal_popup_add fade in" id="updateRegEmailIdModal" style="display: none;padding-left: 14px; height: 100%">
            	<div class="modal-dialog modal-lg">
                	<!-- Modal content-->
                    <div class="modal-content prof_modalContent">
                    	<div class="modal-header" style="text-align:center;">
                        	<span class="close" id="close" onclick="closeupdateEmailIdDialog()">&times;</span>
                            <h4 class="modal-title">UPDATE REGISTERED EMAIL ID</h4>
                        </div>
                        <div class="modal-body">
                        	<form id="data_form" name="" method="post" action="AgencyDataControlServlet" onsubmit="return checkProvidedEmailId(this)">
                            	<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>" >
                                <input type="hidden" id="page" name="page" value="jsp/pages/erp/profile.jsp">
 								<input type="hidden" id="actionId" name="actionId" value="9010">
								<span style="display:inline-block;text-align:center;">Confirmation Emails will be sent to your both existing and new Email Id's. Please contact us if you don't receive confirmation mail.</span>
								<br>
								<br>
								<table id="u_data_table" style="text-align:center;">
									<tr class="spaceUnder">
										<td style="width:150px;"></td><td></td><td></td><td></td>
                                    	<td>Old EmailId : &nbsp&nbsp</td>
                                        <td> <input class="form-control" type="text" style="margin-left:1px;text-transform:lowercase;width: 370px;" name="oldEmailId" id="oldEmailId" placeholder="OLD EMAIL ID" value="<%= agencyVO.getEmail_id()%>" autofocus></td>
									</tr>
									<tr class="spaceUnder">
										<td style="width:150px;"></td><td></td><td></td><td></td>
                                    	<td>New EmailId : &nbsp&nbsp</td>
                                        <td> <input class="form-control" type="text" style="margin-left:1px;text-transform:lowercase;width: 370px;" name="newEmailId" id="newEmailId" placeholder="NEW EMAIL ID" ></td>
									</tr>
									<tr class="spaceUnder">
                                    	<td colspan="6">
	                                    	<div class="usubmit_div" style="text-align:center;width:100px;margin-left: 350px;margin-top: 10px;">
                                            	<input type="submit" name="upEm_submit_btn"  id="upEm_submit_btn" value="SUBMIT" class="btn btn-success m-r-15">
											</div>
                                    	<!-- 
                                    		<div class="usubmit_div" style="text-align:center;width:100px;margin-left: 350px;margin-top: 10px;">
												<button name="upEm_submit_btn" id="upEm_submit_btn" class="btn btn-success m-r-15" onclick="checkProvidedEmailId()">SEND OTP</button>
											</div> -->
										</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
				</div>
			</div>
			<!-- --------------------------------------------UPDATE REGISTERED EMAIL ID END------------------------------------------- -->


            <!-- SET PIN POPUP -->
            <!-- Modal -->
            <div class="modal_popup_add fade in" id="myModalsPin" style="display: none;padding-left: 14px; height: 100%">
				<div class="modal-dialog modal-lg">
                	<!-- Modal content-->
					<div class="modal-content prof_modalContent" style="height:200px;width:50%;margin:auto;">
                    	<div class="modal-header" style="text-align:center;">
                        	<span class="close" id="closeSfm" onclick="closeSetPinDialog()">&times;</span>
                            <h4 class="modal-title">SET PIN </h4>
						</div>
                        <div class="modal-body">
                        	<form id="setpindata_form" name="" method="post" action="AgencyDataControlServlet" onsubmit="return submitSetPinDetails(this)">
                            	<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
                                <input type="hidden" id="page" name="page" value="jsp/pages/erp/profile.jsp">
                                <input type="hidden" id="actionId" name="actionId" value="9003">
                                <input type="hidden" id="cgsta" name="cgsta" value="">
                                <br>
                                <table id="t_data_table">
									<tr class="spaceUnder">
                                    	<td>PIN :&nbsp</td>
                                        <td><input class="form-control" type="password" name="agencyPin" id="agencyPin" placeholder="SET PIN" style="width:140px;"></td>
									</tr>
									<tr class="spaceUnder">
                                    	<td colspan="6">
                                        	<div class="submit_div" style="text-align:center;width:100px;margin-left:250px;margin-top: -50px;">
                                            	<input type="submit" name="prof_submit_btn"  id="prof_submit_btn" value="SUBMIT" class="btn btn-success m-r-15" >
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
				</div>
			</div>

			<!-- UPDATE PIN POPUP -->
			<!-- Modal -->
			<div class="modal_popup_add fade in" id="myModaluPin" style="display: none;padding-left: 14px; height: 100%">
				<div class="modal-dialog modal-lg">
					<!-- Modal content-->
					<div class="modal-content prof_modalContent" style="height=200px;width:60%;margin:auto;">
						<div class="modal-header" style="text-align:center;">
							<span class="close" id="closeUfm" onclick="closeUpdatePinDialog()">&times;</span>
							<h4 class="modal-title">UPDATE PIN </h4>
						</div>
						<div class="modal-body">
 							<form id="updatepindata_form" name="updatepindata_form" method="post" action="AgencyDataControlServlet" style="margin-left:70px" onsubmit="return submitUpdatedPinDetails(this)">
<!--  							<form id="updatepindata_form" name="" method="post" action="AgencyDataControlServlet" style="margin-left:70px">  -->
								<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
								<input type="hidden" id="page" name="page" value="jsp/pages/erp/profile.jsp">
								<input type="hidden" id="actionId" name="actionId" value="9004">
								<input type="hidden" id="pin" name="pin" value="<%= agencyVO.getPinNumber() %>">
								<input type="hidden" id="cgsta" name="cgsta" value="">

								<br>
								<table id="t_data_table">

									<tr class="spaceUnder">
										<td>OLD PIN :&nbsp</td>
										<td><input class="form-control" type="password" name="agencyoPin" id="agencyoPin" placeholder="OLD PIN"></td>
									</tr>
									<tr class="spaceUnder">
										<td>NEW PIN :&nbsp</td>
										<td><input class="form-control" type="password" name="agencynPin" id="agencynPin" placeholder="NEW PIN"></td>
									</tr>
									<tr class="spaceUnder">
										<td>CONFIRM PIN :&nbsp</td>
										<td><input class="form-control" type="password" name="agencycPin" id="agencycPin" placeholder="CONFIRM PIN"></td>
									</tr>
									<tr class="spaceUnder">
										<td colspan="6">
											<div class="submit_div" style="text-align:center;width:100px;margin-left:140px;margin-top: 10px;">
 												<input type="submit" name="prof_submit_btn"  id="prof_submit_btn" value="SUBMIT" class="btnn btn btn-success m-r-15">
<!--  												<input type="button" name="prof_submit_btn"  id="prof_submit_btn" value="SUBMIT" class="btnn btn btn-success m-r-15" onclick = "submitUpdatedPinDetails(this.form)"> -->
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
				</div>
			</div>

			<!-- change password -->
			<!-- Modal -->
			<div class="modal_popup_add fade in" id="changepwdmodal" style="display: none; padding-left: 14px; height: 100%">
				<div class="modal-dialog modal-lg">
					<!-- Modal content-->
					<div class="modal-content prof_modalContent">
						<div class="modal-header" style="text-align: center;">
							<span class="close" id="closeCfm" onclick="closeChangePasswordDialog()">&times;</span>
							<h4 class="modal-title">CHANGE PASSWORD DETAILS</h4>
						</div>
						<div class="modal-body">
							<form id="changepwddata_form" name="changepwddata_form" method="post" action="AgencyDataControlServlet" style="margin-top:10px;" onsubmit="return submitChangeProfileDetails(this)" >
								<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>"> 
								<input type="hidden" id="pin" name="pin" value="<%= agencyVO.getPinNumber()%>"> 
								<input type="hidden" id="page" name="page" value="jsp/pages/erp/profile.jsp">
								<input type="hidden" id="actionId" name="actionId" value="1007">
								
								<table id="t_data_table">
									<%
									MessageObject msgObj2 = (MessageObject) request.getAttribute("msg_obj");
									if((null != msgObj2)) {
										if((msgObj2.getMessageId() == 1007) && (msgObj2.getMessageStatus()=="SUCCESS")) {
									%>
	
									<tr>
										<td colspan="1"></td>
										<td colspan="4" align="center"><font color="green" style="margin-left:20px;"><%=msgObj2.getMessageText() %></font></td>
									</tr>
									<tr><td colspan="4"><br></td></tr>
									
									<% }else if ((msgObj2.getMessageId() == 1007) && (msgObj2.getMessageStatus()=="ERROR")) { %>
									
									<tr>
										<td colspan="1"></td>
										<td colspan="4" align="center"><font color="red" style="margin-left:20px;"><%=msgObj2.getMessageText() %></font></td>
									</tr>
									<tr><td colspan="4"><br></td></tr>
									<% } %>

									<tr class="spaceUnder">
										<td>AGENCY ID:</td>
										<td><input class="form-control" type="text" style=""
													name="aid" id="aid"  readonly="readonly" maxlength="35" placeholder="AGENCY ID" autofocus></td>
										<td>AGENCY NAME:&nbsp</td>
										<td><input class="form-control" type="text" readonly="readonly" name="aname"
													id="aname" maxlength="10" placeholder="AGENCY NAME"></td>
										<td>EMAIL:</td>
										<td><input class="form-control" type="text" readonly="readonly" name="aemail"
													id="aemail" maxlength="30" placeholder="EMAIL"></td>
									</tr>
									<tr class="spaceUnder">
										<td>OLD PASSWORD:&nbsp</td>
										<td><input class="form-control" type="password"
													name="copwd" id="copwd" placeholder="OLD PASSWORD"></td>
										<td>NEW PASSWORD:</td>
										<td><input class="form-control" type="password"
												name="cnpwd" id="cnpwd" placeholder="NEW PASSWORD"></td>
										<td>CONFIRM PASSWORD:</td>
										<td><input class="form-control" type="password" name="ccpwd" id="ccpwd" placeholder="CONFIRM PASSWORD"></td>
									</tr>
									<tr class="spaceUnder">
										<td colspan="6">
											<div class="submit_div" style="text-align: center; width: 100px; margin-left: 350px; margin-top: 10px;">
												<input type="submit" name="prof_submit_btn" id="prof_submit_btn" value="SUBMIT"
															class="btn btn-success m-r-15">
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
						<%} %>
					</div>
				</div>
			</div>
			
			<div class="modal_popup_add fade in" id="myUploadXLDiv" style="display: none;padding-left: 14px; height: 100%">
                <div class="modal-dialog modal-lg">

					<!-- Modal content-->
					<div class="modal-content prof_modalContent" style="height:225px;width:50%;margin:auto;">
						<div class="modal-header" style="text-align:center;">
							<span class="close" id="closeSfm" onclick="closeXLDialog()">&times;</span>
							<h4 class="modal-title">UPLOAD MASTER DATA </h4>
						</div>
						<div class="modal-body">
							<form id="uploadxl_form" name="uploadxl_form" method="post" enctype='multipart/form-data' action="AgencyDataControlServlet" onsubmit="return submitExcelDetails(this)">
								<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
                                <input type="hidden" id="page" name="page" value="jsp/pages/erp/profile.jsp">
                                <input type="hidden" id="actionId" name="actionId" value="9008">
                                <input type="hidden" id="cgsta" name="cgsta" value="">
                                <br>
                                <table id="t_data_table">
                                    <p><font style="color:red">Please select your excel to upload master data</font></p>

									<tr class="spaceUnder">
										<td style="padding-right:30px;"></td>
										<td><font size=2 face="tahoma"><b>UPLOAD EXCEL</b></font></td>
										<td><input type="text" runat="server" id="xltxtUploadFile" disabled="disabled"/></td>
										<td><input type="button" runat="server" id="btnUpload" value="Browse" class="handicon" onclick="xlhookFileClick()"/></td>
										<td> <input type="file" id="xldatafile" name="xldatafile" style="visibility: hidden;" onchange="xlfnOnChange(this)"></td>
									</tr>
									<tr class="spaceUnder">
										<td colspan="6">
											<div class="submit_div" style="text-align:center;width:100px;margin-left:250px;margin-top: 6px;">
												<input type="submit" name="prof_submit_btn"  id="prof_submit_btn" value="UPLOAD" class="btn btn-success m-r-15" >
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
				</div>
			</div>
			<div id = "dialog-1" title="Alert(s)"></div>
			<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div>
            <!--    <center><h4 class="login-head"><img src="images/registration_icon.png" alt="" title=""/> PROFILE</h4></center> -->
			<%
				MessageObject xlmsgObj=(MessageObject)request.getAttribute("msg_obj");
				if(xlmsgObj!=null && xlmsgObj.getMessageStatus().equals("SUCCESS") && xlmsgObj.getMessageId()==9008){
			%>
					<font color="green" id="xl_errmsg">EXCEL UPLOAD SUCCESSFUL</font>
			<%	}else if(xlmsgObj!=null && xlmsgObj.getMessageStatus().equals("ERROR") && xlmsgObj.getMessageId()==9008) { %>
                
					<script type="text/javascript">
						document.getElementById("dialog-1").innerHTML ="<%=xlmsgObj.getMessageText()%>";
						alertdialogue();
					</script>
					<%--	<font color="red" id="xl_errmsg"><%=xlmsgObj.getMessageText()%></font> --%>	
			<%	} %>
			
			<!-- upload PROFILE PICTURE div -->
			<div class="modal_popup_add fade in" id="myUploadDiv" style="display: none;padding-left: 14px; height: 100%">
				<div class="modal-dialog modal-lg">
				<!-- Modal content-->
					<div class="modal-content prof_modalContent" style="height:200px;width:50%;margin:auto;">
						<div class="modal-header" style="text-align:center;">
							<span class="close" id="closeSfm" onclick="closeUploadDialog()">&times;</span>
							<h4 class="modal-title">UPLOAD PICTURE </h4>
						</div>
                        <div class="modal-body">
							<form id="uploadpic_form" name="uploadpic_form" method="post" enctype='multipart/form-data' action="AgencyDataControlServlet" onsubmit="return submitProfilepicDetails(this)">
								<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
                                <input type="hidden" id="page" name="page" value="jsp/pages/erp/profile.jsp">
                                <input type="hidden" id="actionId" name="actionId" value="9006">
                                <input type="hidden" id="cgsta" name="cgsta" value="">
                                <br>
                                <table id="t_data_table">
									<tr class="spaceUnder">
										<td><font size=2 face="tahoma"><b>UPLOAD PICTURE</b></font></td>
										<td><input type="text" runat="server" id="txtUploadFile" disabled="disabled"/></td>
										<td><input type="button" runat="server"
                                                    id="btnUpload" value="Browse" class="handicon" onclick="hookFileClick()"/></td>
										<td> <input type="file" id="datafile" name="datafile" style="visibility: hidden;" onchange="fnOnChange(this)"></td>
									</tr>
									<tr class="spaceUnder">
										<td colspan="6">
											<div class="submit_div" style="text-align:center;width:100px;margin-left:250px;margin-top: 6px;">
												<input type="submit" name="prof_submit_btn"  id="prof_submit_btn" value="UPLOAD" class="btn btn-success m-r-15" >
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
				</div>
			</div>

			<!-- FORGOT PIN POPUP -->
			<!-- Modal -->
			<div class="modal_popup_add fade in" id="myForgotPinDetails" style="display: none;padding-left: 14px; height: 100%">
				<div class="modal-dialog modal-lg">
					<!-- Modal content-->
					<div class="modal-content prof_modalContent" style="height:250px;width:60%;margin: auto;">
						<div class="modal-header" style="text-align:center;">
							<span class="close" id="closeFfm" onclick="closeForgotPinDialog()">&times;</span>
							<h4 class="modal-title">FORGOT PIN </h4>
						</div>
						<div class="modal-body">
							<form id="pindata_form" name="" method="post" action="AgencyDataControlServlet" onsubmit="return submitOtpPinDetails(this)">
								<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
								<input type="hidden" id="agencyEmail" name="agencyEmail" value="<%= agencyVO.getEmail_id() %>">
								<input type="hidden" id="page" name="page" value="jsp/pages/mailOtp.jsp">
								<input type="hidden" id="actionId" name="actionId" value="9005">
								<input type="hidden" id="cgsta" name="cgsta" value="">
								
								<br>
								<table id="t_data_table">
									<p><font style="color:red">Please enter your email address & we will send a confirmation code</font></p>
									<tr class="spaceUnder">
										<td><input class="form-control" style="margin-left:20%;width:70%" name="emailIdforOtp" id="emailIdforOtp" placeholder="EMAIL"></td>
									</tr>
									<tr>
										<td colspan="6">
											<div class="submit_div" style="text-align:center;width:100px;margin-left: 350px;margin-top: 10px;">
												<input type="submit" name="prof_submit_btn"  id="prof_submit_btn butn" style="margin-left:-150px;" value="SUBMIT" class="btn btn-success m-r-15">
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
				</div>
			</div>

			<div>
				<center><h4 class="login-head" style="margin-left:60px;"><img src="images/registration_icon.png" alt="" title=""/> PROFILE</h4></center>
				
				<div style="text-align:center;" class="msgcls">
					<%
						MessageObject msgObj = (MessageObject) request.getAttribute("msg_obj");
						if((null != msgObj)) {
							if((msgObj.getMessageId() == 9010) && (msgObj.getMessageStatus()=="SUCCESS")) {
					%>
								<font color="green" size="3"><%=msgObj.getMessageText() %></font>

						<% }else if ((msgObj.getMessageId() == 9010) && (msgObj.getMessageStatus()=="ERROR")) { %>
								<font color="red" size="3"><%=msgObj.getMessageText() %></font>
					<% }} %>
				</div>
				<br><br>
				
				<div class="row">
					<div class="profile-label">AGENCY INFORMATION
					<!-- <div style="float:right;margin-top:-5px;">
						<input type="button" name="prof_btn" id="prof_btn" value="EDIT PROFILE" class="btn btn-success m-r-15" onclick="editProfileDetails(this.form)">
					</div> -->
						
						<input type="button" name="upprf_btn" id="upprf_btn" class="prof_btn uprofile_btn1" value="UPLOAD PROFILE PIC" onclick="uploadDetails()" title="UPLOAD YOUR IMAGE HERE" />
						<form id="profile_form" name="" method="post" action="AgencyDataControlServlet" onsubmit="return removeProfile(this)">
                            <input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>" >
							<input type="hidden" id="page" name="page" value="jsp/pages/erp/profile.jsp">
							<input type="hidden" id="actionId" name="actionId" value="9007">
							<input type="submit" name="rmprf_btn" id="rmprf_btn"  class="prof_btn rmprof_btn1" value="REMOVE PROFILE PIC"/>
						</form>
						<hr/><br>
						<div class="prof_divs">
							<div class="form-group">
								<label for="agencyIdSpan" class="profile-label">DISTRIBUTORSHIP ID</label>
								<span id="agencyIdSpan" class="profile-span" >: AGENCY ID</span>
							</div>
							<div class="form-group">
								<label for="dstrspNameSpan" class="profile-label">DISTRIBUTORSHIP NAME</label>
								<span id="dstrspNameSpan" class="profile-span">: DISTRIBUTORSHIP NAME</span>
							</div>
							<div class="form-group">
								<label for="gstinSpan" class="profile-label">GSTIN</label>
								<span id="gstinSpan" class="profile-span">: GSTIN</span>
							</div>
							<div class="form-group">
								<label for="ocSpan" class="profile-label">OIL COMPANY</label>
								<span id="ocSpan" class="profile-span">: OIL COMPANY</span>
							</div>
							<div class="form-group">
								<label for="stSpan" class="profile-label">STATE</label>
								<span id="stSpan" class="profile-span">: STATE</span>
							</div>
						</div>
					</div>
					<br>
					<span class="profile-label" id="xlprf_btn" style="display:none;margin-right:14px;">
						<input type="button" name="xlprf_btn" id="xlprf_btn" class="prof_btn" value="UPLOAD MASTER DATA EXCEL FILE"
                        			onclick="uploadMasterData()" title="UPLOAD YOUR DATA HERE" />
					</span>

					<div class="profile-label">CONTACT DETAILS
						<input type="button" name="cpwd_btn" id="cpwd_btn" class="prof_btn chnpwd_btn1" value="CHANGE PASSWORD"
									onclick="changePasswordDetails()" title="CHANGE YOUR ACCOUNT PASSWORD HERE" />
						<input type="button" name="prof_btn" id="prof_btn"  style="margin-right: 5px;" class="prof_btn" value="EDIT PROFILE" onclick="editProfileDetails()" />
						<hr/>
						<br>
						<div class="prof_divs">
							<div class="form-group">
								<label for="pnameSpan" class="profile-label">NAME </label>
								<span id="pnameSpan" class="profile-span">: CONTACT PERSON NAME</span>
							</div>
							<div class="form-group">
								<label for="dmobileSpan" class="profile-label">MOBILE NUMBER </label>
								<span id="dmobileSpan" class="profile-span">: DEALER MOBILE</span>
							</div>
							<div class="form-group">
								<label for="stdSpan" class="profile-label">LANDLINE NUMBER </label>
								<span id="stdSpan" class="profile-span">: LAND</span>
								<span id="lnSpan" class="profile-span">NUMBER</span>
							</div>
							<div class="form-group">
								<label for="emailIdSpan" class="profile-label">EMAIL ID </label>
								<span id="emailIdSpan" class="profile-span" >: EMAIL ID</span>
								<a onclick="showPinPopup()" style="text-decoration:underline;">update</a>
							</div>
							<div class="form-group">
								<label for="addrSpan" class="profile-label">REGISTERED OFFICE ADDRESS</label>
								<span id="addrSpan" class="profile-span" >: YOUR OFFICE ADDRESS</span>
							</div>
						</div>
					</div>
				</div>

 				<br>

				<div class="profile-label" id="setPin" style="display:none">SET SECURITY PIN FOR  REFIL ,ARB AND CREDIT LIMIT
					<input type="button" name="prof_btn" id="prof_btn" class="prof_btn" value="SET PIN" onclick="setPinNumber()" />
					<hr/>
					<div class="clearfix"></div>
				</div>
				<br>
				<div class="profile-label" id="updatePin" style="display:none">UPDATE SECURITY PIN FOR  REFIL ,ARB AND CREDIT LIMIT
					<input type="button" name="prof_btn" id="prof_btn" class="prof_btn" value="UPDATE PIN" onclick="updatePinNumber()" />
					<hr/><br>
					<div class="clearfix"></div>
				</div>

				<div class="profile-label" id="forgotPin" style="display:none">SET NEW PIN FOR  REFIL ,ARB AND CREDIT LIMIT
					<input type="button" name="prof_btn" id="prof_btn" class="prof_btn" value="FORGOT PIN" onclick="forgotPinNumber()" />
					<hr/>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<div id = "dialog-1" title="Alert(s)"></div>
	<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div>
	
	<!-- Javascripts-->
	<script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/main.js?<%=randomUUIDString%>"></script>
	
<!-- 	// UPLOAD XL
       function uploadMasterData() {
         document.getElementById("myUploadXLDiv").style.display = "block";
 }
 function closeXLDialog() {
         document.getElementById('myUploadXLDiv').style.display = "none";
 }
//upload xl data
 function xlhookFileClick(){
        // Initiate the File Upload Click Event
 document.getElementById('xldatafile').click();
}

 function xlfnOnChange(obj){
     document.getElementById("xltxtUploadFile").value = obj.value;
}

function submitExcelDetails(formobj){
                var errMsg = "";
                var uploadfile=formobj.xldatafile.value.trim();
                if(!(uploadfile.length>0))
                        errMsg = errMsg+"Upload file can't be empty.<br>";

                if ( errMsg.length > 0 ){
                        document.getElementById("dialog-1").innerHTML = errMsg;
                        alertdialogue();
                        return false;
                }

                 }
 -->	

	<script type="text/javascript">
	
		if(!pinValue)
			document.getElementById("setPin").style.display = "block";
		else {
			document.getElementById("updatePin").style.display = "block";
			document.getElementById("forgotPin").style.display = "block";
		}
	</script>

	<script type="text/javascript">
		document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>
		var agency_oc = <%= agencyVO.getAgency_oc() %>;
		var state = <%= agencyVO.getAgency_st_or_ut() %>;

		var oc;
		if(agency_oc==1)
			oc="IOCL";
		else if(agency_oc==2)
			oc="HPCL";
		else if(agency_oc==3)
			oc="BPCL";

		switch(state) {
			case 1: state="JAMMU & KASHMIR";
					break;
			case 2: state="HIMACHAL PRADESH";
					break;
			case 3: state="PUNJAB";
					break;
			case 4: state="CHANDIGARH";
					break;
			case 5: state="UTTARAKHAND";
					break;
			case 6: state="HARYANA";
					break;
			case 7: state="DELHI";
					break;
			case 8: state="RAJASTHAN";
					break;
			case 9: state="UTTAR PRADESH";
					break;
			case 10: state="BIHAR";
					break;
			case 11: state="SIKKIM";
					break;
			case 12: state="ARUNACHAL PRADESH";
					break;
			case 13: state="NAGALAND";
					break;
			case 14: state="MANIPUR";
					break;
			case 15: state="MIZORAM";
					break;
			case 16: state="TRIPURA";
					break;
			case 17: state="MEGHALAYA";
					break;
			case 18: state="ASSAM";
					break;
			case 19: state="WEST BENGAL";
					break;
			case 20: state="JHARKHAND";
					break;
			case 21: state="ODISHA";
					break;
			case 22: state="CHHATTISGARH";
					break;
			case 23: state="MADHYA PRADESH";
					break;
			case 24: state="GUJARAT";
					break;
			case 25: state="DAMAN & DIU";
					break;
			case 26: state="DADRA & NAGAR HAVELI";
					break;
			case 27: state="MAHARASHTRA";
					break;displayDiv
			case 29: state="KARNATAKA";
					break;
			case 30: state="GOA";
					break;
			case 31: state="LAKSHDWEEP";
					break;
			case 32: state="KERALA";
					break;
			case 33: state="TAMIL NADU";
					break;
			case 34: state="PONDICHERRY";
					break;
			case 35: state="ANDAMAN & NICOBAR ISLANDS";
					break;
			case 36: state="TELANGANA";
					break;
			case 37: state="ANDHRA PRADESH";
					break;
			case 97: state="OTHER TERRITORY";
					break;
		}

		document.getElementById("agencyIdSpan").innerHTML =  ":   <%= agencyVO.getAgency_code() %>";
		document.getElementById("dstrspNameSpan").innerHTML = ":  <%= agencyVO.getAgency_name()%>";
		document.getElementById("gstinSpan").innerHTML =  ":  <%= agencyVO.getGstin_no() %>";
		document.getElementById("ocSpan").innerHTML =  ":  "+ oc;
		document.getElementById("stSpan").innerHTML =  ":  "+ state;
		var myaddr = "<%= agencyVO.getAddress() %>";
		var cperson = "<%= agencyVO.getContact_person() %>";
		var olndline = "<%= agencyVO.getOffice_landline() %>";

		var landnumber =  "<%= agencyVO.getOffice_landline() %>";
		var lnumber = landnumber.split(" ");

		if((myaddr== "null") || (cperson == "null") || (olndline== "null") || (myaddr== "undefined") || (cperson == "undefined") || (olndline== "undefined")) {
			if((myaddr == "null") || (myaddr == "undefined")) {
				document.getElementById("addrSpan").innerHTML =  ":  NOT DEFINED";
			}
			if((cperson == "null") || (cperson == "undefined")) {
				document.getElementById("pnameSpan").innerHTML =  ":  NOT DEFINED";
			}
			if(((lnumber[0] == "null") || (lnumber[0] == undefined)) && ((lnumber[1] == "null") || (lnumber[1] == undefined))) {
				document.getElementById("stdSpan").innerHTML =  ":  NOT";
				document.getElementById("lnSpan").innerHTML = "DEFINED";
			}

			if((myaddr != "null") && (myaddr != "undefined")) {
				document.getElementById("addrSpan").innerHTML =  ":   <%= agencyVO.getAddress() %>";
			}
			if((cperson != "null") && (cperson != "undefined")) {
				document.getElementById("pnameSpan").innerHTML =  ":  <%= agencyVO.getContact_person() %>";
			}
			if(((lnumber[0] != "null") && (lnumber[0] != "undefined")) && ((lnumber[1] != "null") && (lnumber[1] != "undefined"))) {
				document.getElementById("stdSpan").innerHTML = ": "+lnumber[0];;
				document.getElementById("lnSpan").innerHTML = lnumber[1];
			}
			document.getElementById("dmobileSpan").innerHTML =  ":  <%= agencyVO.getAgency_mobile() %>";
			document.getElementById("emailIdSpan").innerHTML =  ":   <%= agencyVO.getEmail_id() %>";
		}else {
			document.getElementById("addrSpan").innerHTML =  ":  <%= agencyVO.getAddress() %>";
			document.getElementById("pnameSpan").innerHTML =  ":  <%= agencyVO.getContact_person() %>";
			document.getElementById("dmobileSpan").innerHTML =  ":  <%= agencyVO.getAgency_mobile() %>";
			var landnumber =  "<%= agencyVO.getOffice_landline() %>";
			var lnumber = landnumber.split(" ");
			document.getElementById("stdSpan").innerHTML =": "+lnumber[0];
			document.getElementById("lnSpan").innerHTML = lnumber[1];
<%-- 			document.getElementById("dlnSpan").innerHTML =  ":  <%= agencyVO.getOffice_landline() %>"; --%>
			document.getElementById("emailIdSpan").innerHTML =  ":  <%= agencyVO.getEmail_id() %>";
		}

		function editProfileDetails() {
			var oaddress = "<%= agencyVO.getAddress() %>";
			var olnumber = "<%= agencyVO.getOffice_landline() %>";
			var elnumber = olnumber.split(" ");
			var ocpname = "<%= agencyVO.getContact_person() %>";

			if((oaddress == "undefined") || (oaddress == "null"))
				oaddress = "NOT DEFINED";
			if((elnumber == "undefined") || (elnumber == "null"))
				elnumber = "NOT DEFINED";
			if((ocpname == "undefined") || (ocpname == "null"))
				ocpname = "NOT DEFINED";

			document.getElementById("cpname").value = ocpname;
			document.getElementById("cdmobile").value = "<%= agencyVO.getAgency_mobile() %>";
			
			if(elnumber.length == 11){
				document.getElementById("stdc").value = elnumber;
				document.getElementById("lnno").value = elnumber;
			}else{
				document.getElementById("stdc").value = elnumber[0];
				document.getElementById("lnno").value = elnumber[1];
			}
			<%-- document.getElementById("cemailId").value = "<%= agencyVO.getEmail_id() %>"; --%>
			document.getElementById("cOffcAddr").value = oaddress;
			
			//Registered Office Address
			document.getElementById("myModal").style.display = "block";
		}
		function closeChangeProfileDialog() {
			document.getElementById('myModal').style.display = "none";
		}
		
		
		//----------------------------------START UPDATE REGISTERED EMAILID-----------------------------------------------------
		
		function showPinPopup() {
			document.getElementById('enteredPin').value="";
			var profPin = <%= agencyVO.getPinNumber() %>;
			document.getElementById('myModalEmIdUpPin').style.display = "block";
			
			if(profPin) {
				document.getElementById('displayDiv').style.display = "none";
				document.getElementById('contentDiv').style.display = "block";
			}else {
				document.getElementById('contentDiv').style.display = "none";
				document.getElementById('displayDiv').style.display = "block";
			}
		}

		function submitPinNumber() {
			var pinNO = document.getElementById("pinNO").value.trim();
			var enteredPin = document.getElementById("enteredPin").value.trim();
			var ems="";
			if(!(enteredPin.length>0))
				ems= ems+"Please Enter PIN NUMBER<br>";
			else if(!validatePinNumber(enteredPin,4,4))
				ems = ems+"PIN NUMBER Must Be Valid  4 DIGIT Number <br>";
			else if(enteredPin !== pinNO)
				ems= ems+"Please Enter Valid PIN NUMBER<br>";
			else if(enteredPin != document.getElementById("enteredPin").value)
				ems= ems+ "No spaces are allowed in pin Number\n";
			else if(enteredPin == pinNO) {
				
				document.getElementById('displayDiv').style.display = "none";
				document.getElementById('contentDiv').style.display = "none";
				document.getElementById('myModalEmIdUpPin').style.display="none";
				
				document.getElementById('oldEmailId').value = "<%= agencyVO.getEmail_id() %>";
				document.getElementById('newEmailId').value = "";
				document.getElementById('updateRegEmailIdModal').style.display="block";
				
			}
			if (ems.length > 0) {
				document.getElementById("dialog-1").innerHTML = ems;
				alertdialogue();    
				return;
			}
		}
		
		function updateEmailIdBox() {
			document.getElementById("checkEmailId").innerHTML =  ":  <%= agencyVO.getEmail_id() %>";
			document.getElementById('updateRegEmailIdModal').style.display = "block";
		}
		function closeupdateEmailIdDialog() {
			document.getElementById('updateRegEmailIdModal').style.display = "none";
		}
		
		function checkProvidedEmailId() {
			var oeid = document.getElementById("oldEmailId").value;
			var neid = document.getElementById("newEmailId").value;
			var remailId = "<%= agencyVO.getEmail_id() %>";
			
			var errMsg="";
			
			if((oeid.length <= 0) && (neid.length <= 0)) {
				errMsg = errMsg + "Please Enter EMAIL IDs<br>";
			}else if(oeid.length <= 0) {
				errMsg = errMsg + "Please Enter OLD EMAIL ID<br>";
			}else if(neid.length <= 0) {
				errMsg = errMsg + "Please Enter NEW EMAIL ID<br>";	
			}else if(!checkEmail(oeid)) {
				errMsg = errMsg + "The OLD EMAIL ID you have entered is not in a proper format.Please check it and enter again.<br>";
			}else if(!checkEmail(neid)) {
				errMsg = errMsg + "The NEW EMAIL ID you have entered is not in a proper format.Please check it and enter again.<br>";
			}else if(((oeid).toLowerCase()) !== (remailId.toLowerCase())) {
				errMsg = errMsg + "The OLD EMAIL ID you have entered does not match with the existing EMAIL ID. <br>";
			}else if( ((oeid).toLowerCase()) === ((neid).toLowerCase()) ){
				document.getElementById('updateRegEmailIdModal').style.display = "none";
				return false;
			}
			
			if (errMsg.length > 0) {
				document.getElementById("dialog-1").innerHTML = errMsg;
				alertdialogue();
				return false;
			}
			
		}
		//----------------------------------END UPDATE REGISTERED EMAILID-----------------------------------------------------

		//SET PIN
		function setPinNumber() {
			document.getElementById("myModalsPin").style.display = "block";
//			var formobj = document.getElementById('data_form');
/*			var pin = formobj.pin.value.trim();
			if(pin=="" || pin=='0')
			else if(pin!="" &&  pin!='0'))
				alert("your pin is already set"); */
		}
		function closeSetPinDialog() {
			document.getElementById('myModalsPin').style.display = "none";
		}

		// UPDATE PIN
		function updatePinNumber() {
			document.getElementById("myModaluPin").style.display = "block";
		}
		function closeUpdatePinDialog() {
			document.getElementById('myModaluPin').style.display = "none";
		}

		// forgot PIN
		function forgotPinNumber() {
			document.getElementById("myForgotPinDetails").style.display = "block";
		}
		function closeForgotPinDialog() {
			document.getElementById('myForgotPinDetails').style.display = "none";
		}
		//upload profile
        function hookFileClick(){
		// Initiate the File Upload Click Event
  	  		document.getElementById('datafile').click();
		}
        function fnOnChange(obj){
                document.getElementById("txtUploadFile").value = obj.value;
        }

        function uploadDetails() {
                document.getElementById("myUploadDiv").style.display = "block";
        }
        function closeUploadDialog() {
                document.getElementById('myUploadDiv').style.display = "none";
        }

      //Remove profile
		function removeProfile(formobj){
			var returnValue=confirm("ARE YOU SURE TO REMOVE PROFILE PIC");
			if(returnValue==true)
				return true;
			else
				return false;
		}
      
      
		// UPLOAD XL
			if(xlUplpoadStatus==0)
               document.getElementById("xlprf_btn").style.display = "block";

	      function uploadMasterData() {
	         document.getElementById("myUploadXLDiv").style.display = "block";
			 	}
		 function closeXLDialog() {
	         document.getElementById('myUploadXLDiv').style.display = "none";
			 	}
		//upload xl data
		 function xlhookFileClick(){
	        // Initiate the File Upload Click Event
	 		document.getElementById('xldatafile').click();
		}

		function xlfnOnChange(obj){
			document.getElementById("xltxtUploadFile").value = obj.value;
		}

		function submitExcelDetails(formobj){
			var errMsg = "";
			var uploadfile = formobj.xldatafile.value.trim();
			if(!(uploadfile.length>0))
				errMsg = errMsg+"Upload file can't be empty.<br>";

				if ( errMsg.length > 0 ){
					document.getElementById("dialog-1").innerHTML = errMsg;
					alertdialogue();
					return false;
				}
		}

		
		//CHANGE PASSWORD
		function changePasswordDetails() {
			document.getElementById("aid").value = "<%= agencyVO.getAgency_code() %>";
			document.getElementById("aname").value = "<%= agencyVO.getAgency_name() %>";
			document.getElementById("aemail").value = "<%= agencyVO.getEmail_id() %>";

			document.getElementById("changepwdmodal").style.display = "block";
		}
		function closeChangePasswordDialog() {
			document.getElementById('changepwdmodal').style.display = "none";
		}
		
		<%
		if((msgObj2.getMessageId()==1007) && (msgObj2.getMessageStatus().equalsIgnoreCase("ERROR"))){
		%>
		
			document.getElementById("aid").value = "<%= agencyVO.getAgency_code() %>";
			document.getElementById("aname").value = "<%= agencyVO.getAgency_name() %>";
			document.getElementById("aemail").value = "<%= agencyVO.getEmail_id() %>";
			document.getElementById("changepwdmodal").style.display = "block";
		<% }
		else if((msgObj2.getMessageId()==1007) && (msgObj2.getMessageStatus().equalsIgnoreCase("SUCCESS"))){
		%>
			document.getElementById("aid").value = "<%= agencyVO.getAgency_code() %>";
			document.getElementById("aname").value = "<%= agencyVO.getAgency_name() %>";
			document.getElementById("aemail").value = "<%= agencyVO.getEmail_id() %>";
			document.getElementById("changepwdmodal").style.display = "block";
		<%}%>

		//change password
		function submitChangeProfileDetails(formobj) {
//			var formobj = document.getElementById('changepwddata_form');
			var changeOldPwd = formobj.copwd.value.trim();
			var changeNewPwd = formobj.cnpwd.value.trim();
			var changeCpwd = formobj.ccpwd.value.trim();

//			PasswordUtil.encryptPasscode(changeNewPwd);
//			alert("encrypted data is:"+changeNewPwd);

//			var oldpin = formobj.pin.value.trim();
			var errMsg="";
			if(changeOldPwd.length<0)
				errMsg = errMsg+"Please Enter OLD Password.<br>";
			else if((changeOldPwd.length<8)||(changeOldPwd.length>12)){
				errMsg = errMsg+"Please Enter Proper Password. 8-12 Characters.<br>";
			}else if(!changeNewPwd.length>0){
				errMsg = errMsg+"Please Enter New Password.<br>";
			}else if((changeNewPwd.length<8)||(changeNewPwd.length>12)){
				errMsg = errMsg+"Please Enter Proper Password. 8-12 Characters.<br>";
			}else if(!changeCpwd.length>0){
				errMsg = errMsg+"Please Enter Confirm Password.<br>";
			}else if(!(changeNewPwd==changeCpwd)){
				errMsg = errMsg+"Please Enter Same Value For PASSWORD and CONFIRM PASSWORD<br>";
			}

			if (errMsg.length > 0) {
				document.getElementById("dialog-1").innerHTML = errMsg;
				alertdialogue();
				return false;
			}
		}

		function submitUpdatedProfileDetails(formobj) {
//			var formobj = document.getElementById('data_form');
			var cpname = formobj.cpname.value.trim();
			var clnstd = formobj.stdc.value.trim();
			var clnno = formobj.lnno.value.trim();
			var cemobile = formobj.cdmobile.value.trim();
//			var ceemail = formobj.cemailId.value.trim();
			var cOffcAddr = formobj.cOffcAddr.value.trim();

			var ems="";

			if(cpname.localeCompare("NOT DEFINED")==0)
				ems = ems+"Please Enter PERSON NAME <br>";
			else if(!(IsNameSpaceDot(cpname)))
				ems = ems+" PERSON NAME Should Contains Only Alphabets <br>";

			if(!(cemobile.length>0))
				ems= ems+"Please Enter Your MOBILE NUMBER<br>";
			else if(!validateNumber(cemobile,10,10))
				ems = ems+"MOBILE NUMBER Must Be Valid Number <br>";

			if(clnstd.localeCompare("NOT DEFINED")==0 && clnno.localeCompare("NOT DEFINED")==0)
				ems= ems+"Please Enter Your LANDLINE NUMBER <br>";
			else if(!IsNumeric(clnstd))
				ems = ems+"STD CODE Must Be Valid Number <br>";
			else if(!IsNumeric(clnno))
				ems = ems+"LANDLINE  NUMBER Must Be Valid Number <br>";
			else if(!(((clnstd.length) + (clnno.length)) == 11))
				ems = ems+"Enter Valid LANDLINE NUMBER <br>";

/* 			if(!(ceemail.length>0))
				ems= ems+"Please Enter Your EMAIL ID <br>";
			else if(!checkEmail(ceemail))
				ems = ems+"Please Enter Proper EMAIL <br>"; */

			if(cOffcAddr.localeCompare("NOT DEFINED")==0)
				ems = ems+"Please Enter Your AGENCY OFFICE ADDRESS <br>";
			else if(cOffcAddr.length < 25)
				ems = ems+" AGENCY OFFICE ADDRESS Must Contains Atleast 25 Characthers <br>";

			if (ems.length > 0) {
				document.getElementById("dialog-1").innerHTML = ems;
				alertdialogue();
				return false;
			}
			document.getElementById("clnno").value = (clnstd +" "+ clnno);
		}

		//set pin validations
		function submitSetPinDetails(formobj) {
//			var formobj = document.getElementById('setpindata_form');
			var agencyPin = formobj.agencyPin.value.trim();
//			var pin=formobj.pin.value.trim();

			var ems="";
			if(!(agencyPin.length>0))
				ems= ems+"Please Enter PIN number<br>";
			else if(!validatePinNumber(agencyPin,4,4))
				ems = ems+"PIN number Must Be A Valid 4 DIGIT Number <br>";
			if (ems.length > 0) {
				document.getElementById("dialog-1").innerHTML = ems;
				alertdialogue();
				return false;
			}
		}
		//submitProfilepicDetails
        function submitProfilepicDetails(formobj) {
        	var datapath = formobj.datafile.value.trim();
            var Extension = datapath.substring(datapath.lastIndexOf('.') + 1).toLowerCase();
            if(datapath.length>0)
	            var FileSize = (formobj.datafile.files[0].size);
				var ems="";
				var fuData = document.getElementById("datafile");
				var FileUploadPath = fuData.value;
				if(!(datapath.length>0))
					ems= ems+"Please Browse your image before Upload<br>";
				else if (Extension == "pdf" || Extension == "gif" || Extension == "bmp" || Extension == "zip") {
					ems= ems+"Photo only allows file types of PNG, JPG, JPEG";
				}else if (FileSize >10000){
					ems = ems+"Max Upload size is 10KB only";
				}
				if (ems.length > 0) {
					document.getElementById("dialog-1").innerHTML = ems;
					alertdialogue();
					return false;
				}
   	     }

		//UPDATE pin validations
		function submitUpdatedPinDetails(formobj) {
						
			var agencyoPin = formobj.agencyoPin.value.trim();
			var agencynPin = formobj.agencynPin.value.trim();
			var agencycPin = formobj.agencycPin.value.trim();
			var oldpin = formobj.pin.value.trim();

			var ems="";
			if(!(agencyoPin.length>0))
				ems= ems+"Please Enter OLD PIN number<br>";
			else if(!validatePinNumber(agencyoPin,4,4))
				ems = ems+"PIN number Must Be A Valid 4 DIGIT Number <br>";
			else if(oldpin!=agencyoPin)
				ems = ems+"THE OLD PIN YOU HAVE ENTERED DOES NOT EXIST<br>";

			if(!(agencynPin.length>0))
				ems= ems+"Please Enter NEW PIN number<br>";
			else if(!validatePinNumber(agencynPin,4,4))
				ems = ems+"NEW PIN number Must Be A Valid 4 DIGIT Number <br>";

			if(!(agencycPin.length>0))
				ems= ems+"Please Enter CONFIRM PIN number<br>";
			else if(agencynPin!=agencycPin)
				ems = ems+"CONFIRM PIN number Must MATCH WITH NEW PIN number <br>";
			if (ems.length > 0) {
				document.getElementById("dialog-1").innerHTML = ems;

				alertdialogue();
//				event.preventDefault();
				return false;
			}
		}

/* 		//UPDATE pin validations
		function submitUpdatedPinDetails(formobj) {
						
			var agencyoPin = formobj.agencyoPin.value.trim();
			var agencynPin = formobj.agencynPin.value.trim();
			var agencycPin = formobj.agencycPin.value.trim();
			var oldpin = formobj.pin.value.trim();

			var ems="";
			if(!(agencyoPin.length>0))
				ems= ems+"Please Enter OLD PIN number<br>";
			else if(!validatePinNumber(agencyoPin,4,4))
				ems = ems+"PIN number Must Be A Valid 4 DIGIT Number <br>";
			else if(oldpin!=agencyoPin)
				ems = ems+"THE OLD PIN YOU HAVE ENTERED DOES NOT EXIST<br>";

			if(!(agencynPin.length>0))
				ems= ems+"Please Enter NEW PIN number<br>";
			else if(!validatePinNumber(agencynPin,4,4))
				ems = ems+"NEW PIN number Must Be A Valid 4 DIGIT Number <br>";

			if(!(agencycPin.length>0))
				ems= ems+"Please Enter CONFIRM PIN number<br>";
			else if(agencynPin!=agencycPin)
				ems = ems+"CONFIRM PIN number Must MATCH WITH NEW PIN number <br>";
			if (ems.length > 0) {
				document.getElementById("dialog-1").innerHTML = ems;

				alertdialogue();
				return false;
			}
			formobj.submit();
		}
 */
		function submitOtpPinDetails(formobj){
			var errMsg = "";
			var emailIdforOtp = formobj.emailIdforOtp.value.trim();
			var agencyEmail = formobj.agencyEmail.value.trim();

			if(!emailIdforOtp.length>0)
				errMsg = errMsg+"EMAIL ID Can't be empty.<br>";
			else {
				if(!checkEmail(emailIdforOtp))
					errMsg = errMsg+"Please Enter Proper Email.<br>";
				else if(agencyEmail != emailIdforOtp)
					errMsg = errMsg+"Please Enter Registered Email.<br>";
			}
			
			if ( errMsg.length > 0 ){
				document.getElementById("dialog-1").innerHTML = errMsg;
				alertdialogue();
				return false;
			}
		}

		$(document).ready(function() {
		
			/* tooltip for select */
			$('select').each(function(){
				var tooltip = $(this).tooltip({
					selector: 'data-toggle="tooltip"',
					trigger: 'manual'
				});
				var selection = $(this).find('option:selected').text();
				msgclstooltip.attr('title', selection)

				$('select').change(function() {
					var selection = $(this).find('option:selected').text();
					tooltip.attr('title', selection)
				});
			});
		});
	</script>

	<script type="text/javascript">

		$("#closeUfm").click(function(){
			$("#updatepindata_form")[0].reset();
		});

		$("#closeCfm").click(function(){
			$("#changepwddata_form")[0].reset();
		});

		$("#closeFfm").click(function(){
			$("#pindata_form")[0].reset();
		});
		$("#closeSfm").click(function(){
			$("#setpindata_form")[0].reset();
		});


		$("#butn").click(function(){
			$("#pindata_form")[0].reset();
		});
	</script>
	
	<script type="text/javascript">
		$('.msgcls').click(function(e) { //button click class name is msgcls
			e.stopPropagation();
		});
		$(function(){
			$(document).click(function(){  
				$('.msgcls').hide();
			});

		});
	</script>
	
	</body>
</html>