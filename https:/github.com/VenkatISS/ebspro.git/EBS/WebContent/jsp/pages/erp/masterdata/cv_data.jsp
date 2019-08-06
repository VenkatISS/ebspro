<%@ page import="java.util.UUID"%>
<!DOCTYPE html>
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
<link rel="stylesheet" type="text/css"
	href="css/main.css?<%=randomUUIDString%>">
<title>MyLPGBooks DEALER WEB APPLICATION - CUSTOMER/VENDOR
	MASTER</title>
<jsp:useBean id="agencyVO" scope="session"
	class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
<jsp:useBean id="cvo_data" scope="session" class="java.lang.String"></jsp:useBean>
<jsp:useBean id="dcvo_data" scope="request" class="java.lang.String"></jsp:useBean>

<script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>

<!-- Sidenav -->
<jsp:include page="/jsp/pages/commons/sidenav.jsp" />
<!---END Sidenav--->

<script type="text/javascript">
		window.onload = setWidthHightNav("100%");
	</script>
</head>
<body class="sidebar-mini fixed">
	<div class="wrapper">
		<!-- Header-->
		<jsp:include page="/jsp/pages/commons/header.jsp" />
		<!-- End Header--->

		<div class="content-wrapper">
			<!--       	<div id = "dialog-1" title="Alert(s)"></div>
 		<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div> -->
			<div class="page-title">
				<div>
					<h1>CUSTOMER / VENDOR MASTER</h1>
				</div>
				<ul id="nav" class="nav nav-pills clearfix right" role="tablist">
					<li class="dropdown"><span class="dropdown-toggle  btn-info"
						data-toggle="dropdown" id="ahelp">Help</span>
						<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
							<li><a style="font-size: 14px;" title="cvo master"
								href="https://www.youtube.com/watch?v=PPuZAufr6pg"
								target="_blank">English</a></li>
							<li><a style="font-size: 14px;"
								href="https://youtu.be/bq-wufJmRVc" target="_blank">Hindi</a></li>
						</ul></li>
				</ul>
			</div>
			<div class="row">
				<div class="clearfix"></div>
				<div class="col-md-15" id="myDIV" style="display: none;">
					<div class="main_table">
						<div class="table-responsive">
							<form id="cvo_data_form" name="" method="post"
								action="MasterDataControlServlet">
								<input type="hidden" id="agencyId" name="agencyId"
									value="<%= agencyVO.getAgency_code() %>"> <input
									type="hidden" id="page" name="page"
									value="jsp/pages/erp/masterdata/cv_data.jsp"> <input
									type="hidden" id="actionId" name="actionId" value="3502">
								<input type="hidden" id="dataId" name="dataId" value="">
								<input type="hidden" id="effDateInFTL" name="effDateInFTL"
									value="<%= agencyVO.getEffective_date() %>">

								<table class="table" id="cvo_add_table">
									<thead>
										<tr class="title_head">
											<th width="10%" class="text-center sml_input">PARTY TYPE</th>
											<th width="10%" class="text-center sml_input">PARTY/PLANT
												NAME</th>
											<th width="10%" class="text-center sml_input">GST REG</th>
											<th width="10%" class="text-center sml_input">GSTIN</th>
											<th width="10%" class="text-center sml_input">PAN</th>
											<th width="10%" class="text-center sml_input">ADDRESS</th>
											<th width="10%" class="text-center sml_input">CONTACT NO</th>
											<th width="10%" class="text-center sml_input">EMAIL</th>
											<th width="10%" class="text-center sml_input">OPENING
												BALANCE</th>
											<th width="10%" class="text-center sml_input">ACTIONS</th>

										</tr>
									</thead>
									<tbody id='cvo_add_table_body'>
									</tbody>
								</table>
							</form>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>

			<!-- popup start-->
			<div class="modal_popup_add fade in" id="myModal"
				style="display: none; height: 100%">
				<div class="modal-dialog modal-lg">
					<!-- Modal content-->
					<div class="modal-content" id="modal-content">
						<div class="modal-header" id="modal-header">
							<span class="close" id="close"
								onclick="closeCVOPopupFormDialog()">&times;</span>
							<!--                           <h4 class="modal-title">DOMESTIC CYLINDER SALES </h4> -->

							<!-- -------------------------mstart--------------------- -->
							<b>CUSTOMER / VENDOR MASTER</b>
							<!-- -------------------------mend--------------------- -->
						</div>
						<div class="modal-body">
							<form id="data_form" name="" method="post"
								action="MasterDataControlServlet">
								<input type="hidden" id="agencyId" name="agencyId"
									value="<%= agencyVO.getAgency_code() %>"> <input
									type="hidden" id="page" name="page"
									value="jsp/pages/erp/masterdata/cv_data.jsp"> <input
									type="hidden" id="bankId" name="bankId" value=""> <input
									type="hidden" id="cvoId" name="cvoId" value=""> <input
									type="hidden" id="cvocbal" name="cvocbal" value=""> <input
									type="hidden" id="obal" name="obal" value=""> <input
									type="hidden" id="effDateInFTL" name="effDateInFTL"
									value="<%= agencyVO.getEffective_date() %>"> <input
									type="hidden" id="actionId" name="actionId" value="3558">
								<br />
								<div class="row">
									<div class="clearfix"></div>
									<div class="col-md-12">
										<div class="main_table">
											<div class="table-responsive">
												<table class="table">
													<thead>
														<tr class="title_head">
															<th width="10%" class="text-center sml_input">PARTY
																TYPE</th>
															<th width="10%" class="text-center sml_input">PARTY/PLANT
																NAME</th>
															<th width="10%" class="text-center sml_input">GST
																REG</th>
															<th width="10%" class="text-center sml_input">GSTIN</th>
															<th width="10%" class="text-center sml_input">PAN</th>
															<th width="10%" class="text-center sml_input">ADDRESS</th>
															<th width="10%" class="text-center sml_input">CONTACT
																NO</th>
															<th width="10%" class="text-center sml_input">EMAIL</th>
														</tr>
													</thead>
													<tbody id="data_table_body">
														<tr>
															<td><input type="text" name='partyType'
																class='form-control input_field eadd' id='partyType'
																size='6' readonly='readonly'
																style='background-color: #F3F3F3;'
																placeholder="Party type"></td>
															<td><input type="text" name='partyName'
																class='form-control input_field eadd' id='partyName'
																size='8' maxlength='35' placeholder="Party name">
															</td>
															<td><select
																class="form-control input_field select_dropdown sadd freez"
																name='dgstyn' id='dgstyn' onchange="updateCount()">
																	<option value="0">SELECT</option>
																	<option value="1">YES</option>
																	<option value="2">NO</option>
															</select></td>
															<!-- <td>
                                                                                                                                    <input type="text" name='gstr'  class='form-control input_field eadd' id='gstr' style="background-color:#F3F3F3;" size='6' maxlength='7'  placeholder="GST reg">
                                                                                                                            </td> -->
															<td><input type="text" name='gstin'
																class='form-control input_field qtyc freez eadd'
																id='gstin' maxlength='15' size='8' placeholder="Gstin">
															</td>


															<td><input type="text" name='pan'
																class='form-control input_field freez eadd' id='pan'
																maxlength='10' size='8' placeholder="PAN"></td>

															<td><input type="text" name='addr'
																class='form-control input_field freez eadd' id='addr'
																size='8' maxlength='100' placeholder="Address">
															</td>
															<td><input type="text" name='mobl'
																class='form-control input_field freez eadd' id='mobl'
																size='8' maxlength='10' placeholder="Contact no">
															</td>
															<td><input type=text name='email'
																class='form-control input_field eadd' id='email'
																size='8' maxlength='50' placeholder='Email'></td>

														</tr>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
							</form>
							<div class="row">
								<div class="clearfix"></div>
								<br />
								<div class="col-md-2">
									<!--                                                                                 <button name="save_data" class="btn btn-info color_btn bg_color2" id="save_data"  onclick="saveData(this)" disabled="disabled">SAVE</button>
 -->
									<!--                          <span id="savediv" style="display:block;"><button class="btn btn-info color_btn bg_color2" name="save_data" id="save_data" onclick="updateSavedData(this)">SAVE</button></span>
 -->
									<span id="savepopdiv" style="display: block;"><button
											class="btn btn-info color_btn bg_color2" name="save_data"
											id="save_data" onclick="updateSavedData(this)">SAVE</button></span>


								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- popup end-->
			<!-- Ujwala Balance Update popup div 04052019 -->

			<div class="modal_popup_add fade in" id="ujwalaBalanceUpdatePopup"
				style="display: none; padding-left: 14px; height: 100%">
				<div class="modal-dialog modal-lg">
					<!-- Modal content-->
					<div class="modal-content prof_modalContent"
						style="height: 150px; width: 40%; margin: auto;">
						<div id="mRefilpricepopupcontentDiv">
							<div class="modal-header" style="text-align: center;">
								<span class="close" id="closeBankBalancepopup"
									onclick="closeUjwalaBalanceUpdatePopup()">&times;</span>
								<h4 class="modal-title">ENTER UJWALA BALANCE</h4>
							</div>
							<div class="modal-body">

								<form method="Post" id="update_balance_form"
									action="MasterDataControlServlet"
									onsubmit="return submitSetBalanceDetails(this)">
									<input type="hidden" id="agencyId" name="agencyId"
										value="<%= agencyVO.getAgency_code() %>"> <input
										type="hidden" id="page" name="page"
										value="jsp/pages/erp/masterdata/cv_data.jsp"> <input
										type="hidden" id="actionId" name="actionId" value="3557">
									<input type="hidden" id="bId" name="bId" value=""> <input
										type="hidden" id="bname" name="bname" value="">

									<table id="bankbalance_popup_table">
										<tr class="spaceUnder">
											<td>Enter Account Balance:&nbsp;&nbsp;&nbsp;&nbsp;</td>
											<td><input class="form-control" type="text"
												name="bankbalamt" id="bankbalamt"
												placeholder="Enter Balance Amount"></td>
											<td colspan="6">
												<div class="submit_mRefilpricepopup_div"
													style="text-align: center; width: 100px; margin-left: 40px; margin-top: -3px;">
													<input type="submit" name="balanceamt_submit_btn"
														id="balanceamt_submit_btn" value="SUBMIT"
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
			</div>
			<!--Ujwala Balance Update end popup div 04052019-->
			<button class="btn btn-info color_btn btn" onclick="addRow()"
				href="#">ADD</button>
			<span id="savediv" style="display: none;"><button
					class="btn btn-info color_btn bg_color2" name="save_data"
					id="save_data" onclick="saveData(this)">SAVE</button></span>
			<button name="cancel_data" id="cancel_data"
				class="btn btn-info color_btn bg_color2"
				onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button>
			<div class="row">
				<div class="clearfix"></div>
				<div class="col-md-15">
					<div class="main_table">
						<div class="table-responsive">
							<table class="table  table-striped">
								<thead>
									<tr class="title_head">
										<th width="10%" class="text-center sml_input">PARTY/PLANT
											NAME</th>
										<th width="10%" class="text-center sml_input">PARTY TYPE</th>
										<th width="10%" class="text-center sml_input">GST REG</th>
										<th width="10%" class="text-center sml_input">GSTIN</th>
										<th width="10%" class="text-center sml_input">PAN</th>
										<th width="10%" class="text-center sml_input">ADDRESS</th>
										<th width="10%" class="text-center sml_input">CONTACT NO</th>
										<th width="10%" class="text-center sml_input">EMAIL</th>
										<th width="10%" class="text-center sml_input">OPENING
											BALANCE</th>
										<th width="10%" class="text-center sml_input">CLOSING
											BALANCE</th>
										<th width="10%" class="text-center sml_input">ACTIONS</th>
									</tr>
								</thead>
								<!--                   			<tbody id='cvo_data_table_body' style="text-transform: uppercase;"></tbody>	-->
								<tbody id='cvo_data_table_body'></tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="dialog-1" title="Alert(s)"></div>
	<div id="dialog-confirm">
		<div id="myDialogText" style="color: black;"></div>
	</div>

	<script type="text/javascript">
     	var cvo_data =  <%= cvo_data.length()>0? cvo_data : "[]" %>;
     	var dcvo_data =  <%= dcvo_data.length()>0? dcvo_data : "[]" %>;
     	var agency_oc = <%= agencyVO.getAgency_oc() %>;
     	var omc_name;
		
     	if(agency_oc==1)
			omc_name = "IOCL";
		else if(agency_oc==2)
			omc_name = "HPCL";
		else if(agency_oc==3)
			omc_name = "BPCL";
		
    	var categories = ["VENDOR","CUSTOMER","OMC-"+omc_name,"OTHERS"];
     	var gstyn = ["SELECT","YES","NO"];
	</script>
	<script type="text/javascript"
		src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript"
		src="js/modules/masterdata/cvo_data.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/main.js?<%=randomUUIDString%>"></script>
	<script>
    document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>
    document.getElementById('cvo_data_form').agencyId = <%= agencyVO.getAgency_code() %>;
    
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
       	    
	} );
    </script>
</body>
</html>
