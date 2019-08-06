<%@ page import="java.util.UUID" %>
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
    	<link rel="stylesheet" type="text/css" href="css/main.css?<%=randomUUIDString%>">
    	<script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>
	    <script type="text/javascript" src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
		<link href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">
    	<title>MyLPGBooks DEALER WEB APPLICATION - CREDIT NOTES</title>
		<jsp:include page="/jsp/pages/commons/sidenav.jsp"/>
		<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
		<jsp:useBean id="cvo_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="cn_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="cylinder_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_types_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="bank_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="prods_list" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="equipment_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_data" scope="request" class="java.lang.String"></jsp:useBean>

		<jsp:useBean id="pr_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="sr_data" scope="request" class="java.lang.String"></jsp:useBean>
					
		<jsp:useBean id="pi_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arb_pi_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="drs_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="crs_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="arbs_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="staff_data" scope="request" class="java.lang.String"></jsp:useBean>
		<jsp:useBean id="area_codes_data" scope="request" class="java.lang.String"></jsp:useBean>
		
<%-- 		<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script>     --%>
	 	<script type="text/javascript"> 
			window.onload = setWidthHightNav("100%");
	 	</script>
		<script type="text/javascript">
			var page_data = <%= cn_data.length()>0? cn_data : "[]" %>;
			var bank_data =  <%= bank_data.length()>0? bank_data : "[]" %>;
			var cvo_data = <%= cvo_data.length()>0? cvo_data : "[]" %>;
			var cat_cyl_types_data = <%= cylinder_types_list %>;
			var cat_arb_types_data = <%= arb_data %>;
			var prod_types_data = <%= prods_list %>;
	 		var purchases_data = <%= pi_data.length()>0? pi_data : "[]" %>;
			var purchase_return = <%= pr_data.length()>0? pr_data : "[]" %>;
			var equipment_data = <%= equipment_data.length()>0? equipment_data : "[]" %>;
			var arb_data = <%= arb_data.length()>0? arb_data : "[]" %>;			
			var ctdatahtml = "";
			var vendordatahtml = "";
			var months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
			var nreasons = ["","Sales Return","Post Sale Discount","Deficiency in services","Correction in Invoice","Change in POS","Finalization of Provisional assessment","Others"];
			
			var pi_data = <%= pi_data.length()>0? pi_data : "[]" %>;
			var arb_pi_data = <%= arb_pi_data.length()>0? arb_pi_data : "[]" %>;
			var drs_data = <%= drs_data.length()>0? drs_data : "[]" %>;
			var crs_data = <%= crs_data.length()>0? crs_data : "[]" %>;
			var arbs_data = <%= arbs_data.length()>0? arbs_data : "[]" %>;
			var sr_data = <%= sr_data.length()>0? sr_data : "[]" %>;
			var staff_data = <%= staff_data.length()>0? staff_data : "[]" %>;
			var area_codes_data = <%= area_codes_data.length()>0? area_codes_data : "[]" %>;
			var payment_terms_values = ["NONE","CASH","CREDIT"];
			var rcs = ["SELECT","YES","NO"];

	//Construct Customer Type html			
			var agency_oc = <%= agencyVO.getAgency_oc() %>;
	     	var omc_name;
	     	if(agency_oc==1)
				omc_name = "IOCL";
			else if(agency_oc==2)
				omc_name = "HPCL";
			else if(agency_oc==3)
				omc_name = "BPCL";
	     	
	     	//------------new popup 03052019 -------------------------------------------------------	     	
	      
            var dom_salesInv= <%= drs_data.length()>0? drs_data : "[]" %>;
			var com_salesInv= <%= crs_data.length()>0? crs_data : "[]" %>;
			var arb_salesInv= <%= arbs_data.length()>0? arbs_data : "[]" %>;
	 		var arbPurchaseData=<%=arb_pi_data.length()>0? arb_pi_data : "[]" %>;

			var payment_terms_types = ["NONE","CASH","CREDIT","ONLINE TRANSFER"];
	     	var gstyn = ["SELECT","YES","NO"];
			var eus = ["NONE","NOS","KGS"];
// ---------------------------------------------------------------------------------------
			
			custdatahtml = "<OPTION VALUE='-1'>SELECT</OPTION>";
			if(cvo_data.length>0) {
				for(var z=0; z<cvo_data.length; z++){
					if(cvo_data[z].deleted == 0){
						if(cvo_data[z].cvo_cat != 1 && cvo_data[z].cvo_cat!=3 ) {
							if(cvo_data[z].cvo_cat == 2)
								custdatahtml=custdatahtml+"<OPTION VALUE='"+cvo_data[z].id+"'>"+omc_name+"-"+cvo_data[z].cvo_name+"</OPTION>";
							else
								custdatahtml=custdatahtml+"<OPTION VALUE='"+cvo_data[z].id+"'>"+cvo_data[z].cvo_name+"</OPTION>";
						}
					}
				}
			}

						
			//Construct Category Type html
			ctdatahtml = "<OPTION VALUE='0'>SELECT</OPTION>";
			ctdatahtml = ctdatahtml + "<OPTION VALUE='-1' disabled>---CYLINDER LIST---</OPTION>";
			if(cat_cyl_types_data.length>0) {
				for(var z=0; z<cat_cyl_types_data.length; z++){
					for(var y=0; y<equipment_data.length; y++){
						if(equipment_data[y].prod_code == cat_cyl_types_data[z].id) {
							ctdatahtml=ctdatahtml+"<OPTION VALUE='"+cat_cyl_types_data[z].id+"'>"
								+cat_cyl_types_data[z].cat_name+"-"+cat_cyl_types_data[z].cat_desc+"</OPTION>";
							break;
						}
					}
				}
			}

			ctdatahtml = ctdatahtml + "<OPTION VALUE='-2' disabled>---ARB LIST---</OPTION>";
			if(cat_arb_types_data.length>0) {
				for(var z=0; z<cat_arb_types_data.length; z++){
					if(cat_arb_types_data[z].deleted == 0){	
						ctdatahtml=ctdatahtml+"<OPTION VALUE='"+cat_arb_types_data[z].id+"'>"
						+getARBType(cat_arb_types_data[z].prod_code)+" - "+cat_arb_types_data[z].prod_brand+" - "+cat_arb_types_data[z].prod_name
						+"</OPTION>";
					}
				}
			}
			ctdatahtml = ctdatahtml + "</select>";
			
		</script>   
		<style>
			.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th {
    			padding: 12px;
    			line-height: 1.42857143;
    			vertical-align: top;
    			border-top: 0px solid #ddd;
		</style>
  	</head>
  	<body class="sidebar-mini fixed">
    	<div class="wrapper">
	<!-- Header-->
		<jsp:include page="/jsp/pages/commons/header.jsp"/>      
	  	<!--header End--->
	      		<div class="content-wrapper">
        		<div class="page-title">
          			<div>
            			<h1>CREDIT NOTE(S)</h1>
          			</div>
					<ul id="nav" class="nav nav-pills clearfix right" role="tablist">
          				<li class="dropdown"><span class="dropdown-toggle  btn-info" data-toggle="dropdown" id="ahelp">Help</span>
							<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
								<li><a style="font-size: 14px;" href="https://www.youtube.com/watch?v=0xlnTXuuZJc" target="_blank">English</a></li>
								<!-- <li><a style="font-size: 14px;" href="https://www.youtube.com/watch?v=0xlnTXuuZJc" target="_blank">Hindi</a></li> -->
							</ul>
						</li>
					</ul>
        		</div>

				<button name="b_sb" id="b_sb" class="btn btn-info color_btn bg_color4" onclick="showCNFormDialog()">ADD</button>
				<button name="cancel_data" id="cancel_data" class="btn btn-info color_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button>
				<!-- Modal -->
				<div class="modal_popup_add fade in" id="myModal" style="display: none; height: 100%" >
					<div class="modal-dialog modal-lg">
			  			<!-- Modal content-->
			  			<div class="modal-content" id="modal-content">
							<div class="modal-header" id="modal-header">
					  			<span class="close" id="close" onclick="closeCNFormDialog()">&times;</span>
					  			<!-- <h4 class="modal-title">CREDIT NOTE(S)</h4> -->
                   <!-- 		<b>CREDIT NOTE(S)</b> -->
                   <!-- -------------------------mstart--------------------- -->
   <b>CREDIT NOTE(S)</b><button name="cp_pop1" id="cp_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showCPFormDialog()">CP</button>
							<button name="arbp_pop1" id="arbp_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showARBPFormDialog()">ARBP</button>
                            <button name="ds_pop1" id="ds_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showDSFormDialog()">DS</button>
							<button name="cs_pop1" id="cs_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showCSFormDialog()">CS</button>
							<button name="arb_pop1" id="arb_pop1" class=" btn-info color_popup_btn bg_color4 btnr" onclick="showARBFormDialog()">ARB</button>

<!-- -------------------------mend--------------------- -->
                   
							</div>
							<div class="modal-body">
								<form id="data_form" name="" method="post" action="TransactionsDataControlServlet">
									<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
									<input type="hidden" id="page" name="page" value="jsp/pages/erp/cash&bank/credit_notes.jsp">
									<input type="hidden" id="actionId" name="actionId" value="5532">
									<input type="hidden" id="cvo_cat" name="cvo_cat" value=""/>
									<input type="hidden" id="ref_date" name="ref_date" value=""/>
																		
									<div class="row">
										<div class="col-adj-res-sub" style="padding-left:15px;">
											<label>CREDIT NOTE DATE</label>
											<input type="date" class="form-control input_field ndate" id="ndate" name="ndate" placeholder="DD-MM-YY">
										</div>
										<div class="col-adj-res-sub">
											<label>CREDIT NOTE TYPE</label>
											<select id="crtype" name="crtype" onchange="setVC()" class="form-control input_field select_dropdown sadd">
							    				<option value="0">SELECT</option>
							    				<option value="1">RECEIVE CREDIT NOTE</option>
							    				<option value="2">ISSUE CREDIT NOTE</option>
							    			</select>										
										</div>
										<div class="col-adj-res-sub">
											<label>REFERENCE NUMBER</label>
											<input type="text" class="form-control input_field freez"  id="cinvno" name="cinvno" placeholder="REFERENCE NUMBER">
										</div>
										<div class="col-adj-res-sub" id="cvname" style="display:none;" >
											<label>VENDOR/CUSTOMER</label>
											<span id="vSpan"></span>
										</div>
										<div class="clearfix"></div>
									</div>
									<br/>
									<div class="row">
										<div class="col-md-12">
											<div class="animated-radio-button">
													<label>LOCAL/INTER-STATE</label>&nbsp;&nbsp;
													<label>
						  								<input type="radio"  id="stype" name="stype"  value="lp"><span  id="stypes" class="label-text">LOCAL</span>
													</label>&nbsp;&nbsp;
													<label>
						  								<input type="radio" id="stype" name="stype"  value="isp"><span  id="stypes" class="label-text">INTER-STATE</span>
													</label>
											</div>
										</div>
									</div>
									<br/>
									<div class="row">
          								<div class="clearfix"></div>
          								<div class="col-md-12">
            								<div class="main_table">
              									<div class="table-responsive">									
               	 									<table class="table" id="b_data_table">
                  										<thead>
                     										<tr class="title_head">
																<th width="10%" class="text-center sml_input">PRODUCT NAME</th>
																<th width="10%" class="text-center sml_input">GST %</th>
																<th width="10%" class="text-center sml_input">TAXABLE<br>AMOUNT</th>
																<th width="10%" class="text-center sml_input">IGST<br>AMOUNT</th>
																<th width="10%" class="text-center sml_input">CGST<br>AMOUNT</th>
																<th width="10%" class="text-center sml_input">SGST<br>AMOUNT</th>
																<th width="10%" class="text-center sml_input">TOTAL<br>AMOUNT</th>
																<th width="10%" class="text-center sml_input">CREDIT/DEBIT TO<br>(BANK ACCOUNT)</th>
																<th width="10%" class="text-center sml_input">REASON</th>	  
                    										</tr> 
                  										</thead>
                  										<tbody id="data_table_body">
                  											<tr>
                  												<td valign="top" height="4" align="center">
                  													<select name="epid" class="form-control input_field freez sadd"  id="epid" style="width:100px;">
                  														<%String str3 = "<script>document.writeln(ctdatahtml)</script>";
				   															out.println("value: " + str3);%>
				   													</select>
				   												</td>
				   												<td valign='top' height='4' align='center'>
				   													<select name='egst' id='egst'  class='form-control input_field freez sadd' style='width:100px;'>
																		<option value="-1">SELECT</option>		
																		<OPTION VALUE="0">0</OPTION>
																		<OPTION VALUE="5">5</OPTION>
																		<OPTION VALUE="12">12</OPTION>
																		<OPTION VALUE="18">18</OPTION>
																		<OPTION VALUE="28">28</OPTION>
																	</select>
																</td>
				   												<td valign='top' height='4' align='center'><input type=text name='tamt' id='tamt' class='form-control input_field eadd freez qtyc' placeholder='TAXABLE AMOUNT'></td>
                   												<td valign='top' height='4' align='center'><input type=text name='igsta' id='igsta' class='form-control input_field eadd' readonly='readonly' style='background-color:#FAFAC2;' placeholder='IGST AMOUNT'></td>
                   												<td valign='top' height='4' align='center'><input type=text name='cgsta' id='cgsta' class='form-control input_field eadd' readonly='readonly' style='background-color:#FAFAC2;' placeholder='CGST AMOUNT'></td>
                   												<td valign='top' height='4' align='center'><input type=text name='sgsta' id='sgsta' class='form-control input_field eadd' readonly='readonly' style='background-color:#FAFAC2;' placeholder='SGST AMOUNT'></td>
                   												<td valign='top' height='4' align='center'><input type=text name='camt' id='camt' class='form-control input_field eadd' readonly='readonly' style='background-color:#FAFAC2;' placeholder='TOTAL AMOUNT'></td>
                  												<td valign='top' height='4' align='center'><span id="bankSpan"></span></td>
																<td valign='top' height='4' align='center'>
				   													<select name='nreason' id='nreason' class='form-control input_field freez sadd' style='width:100px;'>
																		<OPTION VALUE='0'>SELECT</OPTION>
																		<OPTION VALUE='1'>01-Sales Return</OPTION>
																		<OPTION VALUE='2'>02-Post Sale Discount</OPTION>
																		<OPTION VALUE='3'>03-Deficiency in services</OPTION>
																		<OPTION VALUE='4'>04-Correction in Invoice</OPTION>
																		<OPTION VALUE='5'>05-Change in POS</OPTION>
																		<OPTION VALUE='6'>06-Finalization of Provisional assessment</OPTION>
																		<OPTION VALUE='7'>07-Others</OPTION>
																	</select>
																</td>
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
			 						<br/> 
									<div class="col-md-2">
										<button   name="calc_data" id="calc_data" class="btn btn-info color_btn"  onclick="calculateAmounts()">CALCULATE</button>
									</div>
									<div class="col-md-2">
										<button name="save_data" id="save_data" class="btn btn-info color_btn bg_color2"  onclick="saveData(this)" disabled="disabled">SAVE</button>
									</div>
								</div>
							</div>
			  			</div>
					</div>
				</div>
				<div class="row">
          			<div class="clearfix"></div>
          			<div class="col-md-12">
						<form id="m_data_form" name="" method="post" action="MasterDataControlServlet">
							<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
							<input type="hidden" id="page" name="page" value="jsp/pages/erp/cash&bank/credit_notes.jsp">
							<input type="hidden" id="actionId" name="actionId" value="">
							<input type="hidden" id="itemId" name="itemId" value=""> 
           					<table class="table  table-striped" id="m_data_table">
           						<thead>
               						<tr class="title_head">
										<th width="10%" class="text-center sml_input">CREDIT<br>NOTE<br>NUMBER</th>
               							<th width="10%" class="text-center sml_input">CREDIT<br>NOTE<br>DATE</th>
			  							<th width="10%" class="text-center sml_input">REFERENCE<br>NUMBER</th>
			  							<th width="10%" class="text-center sml_input">VENDOR <br>/ OMC /<br>CUSTOMER<br>NAME</th>
			  							<th width="10%" class="text-center sml_input">PRODUCT</th>
			  							<th width="10%" class="text-center sml_input">GST %</th>
			  							<th width="10%" class="text-center sml_input">TAXABLE<br>AMOUNT</th>
			  							<th width="10%" class="text-center sml_input">IGST<br>AMOUNT</th>
			  							<th width="10%" class="text-center sml_input">CGST<br>AMOUNT</th>
			  							<th width="10%" class="text-center sml_input">SGST<br>AMOUNT</th>
			  							<th width="10%" class="text-center sml_input">TOTAL<br>AMOUNT</th>
			  							<th width="10%" class="text-center sml_input">CREDIT/DEBIT TO <br>BANK ACCOUNT</th>
			  							<th width="10%" class="text-center sml_input">REASON</th>
				 						<th width="10%" class="text-center sml_input">Actions</th>
               						</tr>
           						</thead>
             					<tbody id="m_data_table_body"></tbody>
                			</table>
                		</form>
         	 		</div>
         	 		<div class="clearfix"></div>
        		</div>
        		
        		<!-- new popup 2202018 -->
			
			
				<!-- Modal -->
				<div class="modal_popup_newadd fade in" id="cppopupModal" style="display: none;height: 100%">
					<div class="modal-dialog modal-lg">
			  			<!-- Modal content-->
						<div class="modal-content" id="modal-content" style="top: -50px;font-size:10px;">
                            	<div class="modal-header" id="modal-header" style="padding:5px;">
					  			<span class="close" id="closecppopup" onclick="closeCPFormDialog()">&times;</span>
					  			<h4 class="modal-title">CYLINDERS PURCHASE INVOICE ENTRY </h4>
							</div>
				
	<div class="row">
        	<div class="clearfix"></div>
          	<div class="col-md-15">
	            <div class="main_table"  style="margin-left: 37px;margin-right:37px;">
              		<div class="table-mpopup_responsive">
                		<table class="table table-striped" id="cp_data_table">
                  					<thead>
                    					<tr class="title_head_mpopup">
										<th width="10%" class="text-center sml_input">OMC</th>
										<th width="10%" class="text-center sml_input">INV NO</th>
               							<th width="10%" class="text-center sml_input">INVOICE DATE</th>
			  							<th width="10%" class="text-center sml_input">STOCK RECVD DATE</th>
			  							<th width="10%" class="text-center sml_input">PRODUCT</th>
			  							<th width="10%" class="text-center sml_input">ONE WAY/TWO WAY LOAD</th>
			  							<th width="10%" class="text-center sml_input">UNIT PRICE</th>
			  							<th width="10%" class="text-center sml_input">QUANTITY</th>
			  							<th width="10%" class="text-center sml_input">GST%</th>
			  							<th width="10%" class="text-center sml_input">TAXABLE AMOUNT</th>
			  							<th width="10%" class="text-center sml_input">IGST</th>
			  							<th width="10%" class="text-center sml_input">SGST</th>
			  							<th width="10%" class="text-center sml_input">CGST</th>
			  							<th width="10%" class="text-center sml_input">OTHER CHARGES	</th>
					  					<th width="10%" class="text-center sml_input">TOTAL AMOUNT	</th>
				 		
                   						</tr>
                  					</thead>
                  					<tbody id="cp_data_table_body"></tbody>
                				</table>
              		</div>
            	</div>
          	</div>
        </div>
      </div>
    </div>
</div>
					
	<!-- ARB Purchase -->		

	 <div class="modal_popup_newadd fade in" id="arbppopupModal" style="display: none;height: 100%">
					<div class="modal-dialog modal-lg">
			  			<!-- Modal content-->
<!-- 			  			<div class="modal-mulpopup_content" id="modal-content" style="top: -50px;">
 -->						<div class="modal-content" id="modal-content" style="top: -50px;font-size:10px;">
                            	<div class="modal-header" id="modal-header" style="padding:5px;">
					  			<span class="close" id="closearbppopup" onclick="closeARBPFormDialog()">&times;</span>
					  			<h4 class="modal-title">BLPG/ARB/NFR PURCHASE INVOICES</h4>
							</div>
			
           		<div class="row">
          			<div class="clearfix"></div>
          			<div class="col-md-15">
            			<div class="main_table" style="margin-left: 37px;margin-right:37px;">
              				<div class="table-mpopup_responsive">
                				<table class="table  table-striped" id="arbp_popup_data_table">
                  					<thead>
                    				<tr class="title_head_mpopup">
									<th width="10%" class="text-center sml_input"> INV NO</th>
                      				<th width="10%" class="text-center sml_input">INVOICE DATE</th>
					  				<th width="10%" class="text-center sml_input">STOCK RECVD DATE</th>
					  				<th width="10%" class="text-center sml_input">PRODUCT</th>
					  				<th width="10%" class="text-center sml_input">VENDOR</th>
					  				<th width="10%" class="text-center sml_input">REVERSE CHARGE</th>
					  				<th width="10%" class="text-center sml_input">UNIT PRICE</th>
					  				<th width="10%" class="text-center sml_input">QUANTITY</th>
					  				<th width="10%" class="text-center sml_input">GST%</th>
					  				<th width="10%" class="text-center sml_input">TAXABLE AMOUNT</th>
					  				<th width="10%" class="text-center sml_input">IGST</th>
					  				<th width="10%" class="text-center sml_input">SGST</th>
					  				<th width="10%" class="text-center sml_input">CGST</th>
					  				<th width="10%" class="text-center sml_input">OTHER CHARGES	</th>
							  		<th width="10%" class="text-center sml_input">TOTAL AMOUNT	</th>
									</tr>
                    					
                  					</thead>
                  					<tbody id="arbp_data_table_body">                    
                  					</tbody>
                				</table>
              				</div>
           				 </div>
          			</div>
        		</div>
			</div>
		</div>
	</div>		


        

			<!-- <button name="cvo_pop1" id="cvo_pop1" class="btn btn-info color_btn bg_color4" onclick="showCVOFormDialog()">CVO</button>
				<button name="cancel_data" id="cancel_data"	class="btn btn-info color_btn bg_color3" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button>
			 -->	<!-- Modal -->
				<div class="modal_popup_newadd fade in" id="dspopupModal" style="display: none;height: 100%">
					<div class="modal-dialog modal-lg">
			  			<!-- Modal content-->
						<div class="modal-content" id="modal-content" style="top: -50px;font-size:10px;">
                            	<div class="modal-header" id="modal-header" style="padding:5px;">
					  			<span class="close" id="closedspopup" onclick="closeDSFormDialog()">&times;</span>
					  			<h4 class="modal-title">DOMESTIC CYLINDER SALES </h4>
							</div>
				
	<div class="row">
        	<div class="clearfix"></div>
          	<div class="col-md-15">
	            <div class="main_table"  style="margin-left: 37px;margin-right:37px;">
              		<div class="table-mpopup_responsive">
                		<table class="table table-striped" id="ds_data_table">
                  					<thead>
                    					<tr class="title_head_mpopup">
											<th width="10%" class="text-center sml_input"> INV NO</th>
                      						<th width="10%" class="text-center sml_input">INVOICE DATE</th>
                      						<th width="10%" class="text-center sml_input">CUSTOMER</th>
					  						<th width="10%" class="text-center sml_input">PAYMENT TERMS</th>                      						
                      						<th width="10%" class="text-center sml_input">TRANSACTION<br>AMOUNT</th>
					  						<th width="10%" class="text-center sml_input">INV AMOUNT</th>
					  						<th width="10%" class="text-center sml_input">PRODUCT</th>
					  						<th width="10%" class="text-center sml_input">UNIT PRICE</th>
					  						<th width="10%" class="text-center sml_input">DISCOUNT AMOUNT</th>
                      						<th width="10%" class="text-center sml_input">QUANTITY</th>
					  						<th width="10%" class="text-center sml_input">IGST AMOUNT</th>                      						
					  						<th width="10%" class="text-center sml_input">CGST AMOUNT</th>
					   						<th width="10%" class="text-center sml_input">SGST AMOUNT</th>
					   						<th width="10%" class="text-center sml_input">TOTAL AMOUNT</th>
					    					<th width="10%" class="text-center sml_input">DIGI / PRE PAID QUANTITY</th>
					  						<th width="10%" class="text-center sml_input">PSV QUANTITY</th>
					  						<th width="10%" class="text-center sml_input">DELIVERED BY</th>
											<th width="10%" class="text-center sml_input">AREA CODE</th>
											<th width="10%" class="text-center sml_input">BANK ACCOUNT</th>
											<th width="10%" class="text-center sml_input">ONLINE RCVD AMOUNT</th>
											
                   						</tr>
                  					</thead>
                  					<tbody id="ds_data_table_body"></tbody>
                				</table>
              		</div>
            	</div>
          	</div>
        </div>
      </div>
    </div>
</div>
					
	<!-- COM SALES -->		

	 <div class="modal_popup_newadd fade in" id="cspopupModal" style="display: none;height: 100%">
					<div class="modal-dialog modal-lg">
			  			<!-- Modal content-->
<!-- 			  			<div class="modal-mulpopup_content" id="modal-content" style="top: -50px;">
 -->						<div class="modal-content" id="modal-content" style="top: -50px;;font-size:10px;">
                            	<div class="modal-header" id="modal-header" style="padding:5px;">
					  			<span class="close" id="closecspopup" onclick="closeCSFormDialog()">&times;</span>
					  			<h4 class="modal-title">COMMERCIAL CYLINDER SALES</h4>
							</div>
			
           		<div class="row">
          			<div class="clearfix"></div>
          			<div class="col-md-15">
            			<div class="main_table" style="margin-left: 37px;margin-right:37px;">
              				<div class="table-mpopup_responsive">
                				<table class="table  table-striped" id="cs_popup_data_table">
                  					<thead>
                    					<tr class="title_head_mpopup">
										<th width="10%" class="text-center sml_input"> INV NO</th>
                      					<th width="10%" class="text-center sml_input">INVOICE DATE</th>
                      					<th width="10%" class="text-center sml_input">CUSTOMER</th>
					  					<th width="10%" class="text-center sml_input">PAYMENT TERMS</th>                      
					  					<th width="10%" class="text-center sml_input">INV AMOUNT</th>
					  					<th width="10%" class="text-center sml_input">PRODUCT</th>
					  					<th width="10%" class="text-center sml_input">UNIT PRICE</th>
					  					<th width="10%" class="text-center sml_input">DISCOUNT AMOUNT</th>
                      					<th width="10%" class="text-center sml_input">QUANTITY</th>
                      		          	<th width="10%" class="text-center sml_input">IGST AMOUNT</th>
                      		          	<th width="10%" class="text-center sml_input">CGST AMOUNT</th>
					   					<th width="10%" class="text-center sml_input">SGST AMOUNT</th>
					   					<th width="10%" class="text-center sml_input">TOTAL AMOUNT</th>
					    				<th width="10%" class="text-center sml_input">PSV CYLINDERS</th>
					  					<th width="10%" class="text-center sml_input">EMPTIES RECEIVED</th>
										<th width="10%" class="text-center sml_input">BANK ACCOUNT</th>
					  					<th width="10%" class="text-center sml_input">DELIVERED BY</th>
										<th width="10%" class="text-center sml_input">AREA CODE</th>
                    				</tr>
                    					
                  					</thead>
                  					<tbody id="cs_data_table_body">                    
                  					</tbody>
                				</table>
              				</div>
           				 </div>
          			</div>
        		</div>
			</div>
		</div>
	</div>		


<!-- ARB SALES -->		

	 <div class="modal_popup_newadd fade in" id="arbpopupModal" style="display: none;height: 100%">
					<div class="modal-dialog modal-lg">
			  			<!-- Modal content-->
<!-- 			  			<div class="modal-mulpopup_content" id="modal-content" style="top: -50px;">
 -->						<div class="modal-content" id="modal-content" style="top: -50px;;font-size:10px;">
                            	<div class="modal-header" id="modal-header" style="padding:5px;">
					  			<span class="close" id="closecspopup" onclick="closeARBFormDialog()">&times;</span>
					  			<h4 class="modal-title">BLPG/ARB/NFR SALES</h4>
							</div>
			
           		<div class="row">
          			<div class="clearfix"></div>
          			<div class="col-md-15">
            			<div class="main_table" style="margin-left: 37px;margin-right:37px;">
              				<div class="table-mpopup_responsive">
                				<table class="table  table-striped" id="arb_popup_data_table">
                  					<thead>
                    					<tr class="title_head_mpopup">
									<th width="10%" class="text-center sml_input">INV NO</th>
                      				<th width="10%" class="text-center sml_input">INVOICE DATE</th>
                      				<th width="10%" class="text-center sml_input">CUSTOMER</th>
					   				<th width="10%" class="text-center sml_input">STAFF NAME</th>
					   				<th width="10%" class="text-center sml_input">PAYMENT TERMS</th>
					  				<th width="10%" class="text-center sml_input">INVOICE AMOUNT</th>
					  				<th width="10%" class="text-center sml_input">PRODUCT</th>
					  				<th width="10%" class="text-center sml_input">UNIT PRICE</th>
					  				<th width="10%" class="text-center sml_input">DISCOUNT AMOUNT</th>
                      				<th width="10%" class="text-center sml_input">QUANTITY</th>
                      				<th width="10%" class="text-center sml_input">BANK ACCOUNT</th>
					  				<th width="10%" class="text-center sml_input">IGST AMOUNT</th>
					  				<th width="10%" class="text-center sml_input">CGST AMOUNT</th>
					   				<th width="10%" class="text-center sml_input">SGST AMOUNT</th>
					 				<th width="10%" class="text-center sml_input">TOTAL AMOUNT</th>
                   				</tr>                    					
                  					</thead>
                  					<tbody id="arb_data_table_body">                    
                  					</tbody>
                				</table>
              				</div>
           				 </div>
          			</div>
        		</div>
			</div>
		</div>
	</div>		
        
        <!---------------------------------- new popup end ---------------------------------------------------->
      		</div>
    	</div>
    </body>
    <div id = "dialog-1" title="Alert(s)"></div>
	<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div>
    <script>
 	var agency_oc = <%= agencyVO.getAgency_oc() %>;
 	var omc_name;			
 	if(agency_oc==1)
		omc_name = "IOCL";
	else if(agency_oc==2)
		omc_name = "HPCL";
	else if(agency_oc==3)
		omc_name = "BPCL";    
    </script>
	<script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/modal.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>		
	<script type="text/javascript" src="js/modules/cash&bank/credit_notes.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/modules/multipopup/mrefill_purchases.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/modules/multipopup/mdom_refill_sales.js?<%=randomUUIDString%>"></script>
    <script src="js/commons/main.js?<%=randomUUIDString%>"></script>
    <script type="text/javascript">
     	document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>
     	var dedate = <%=agencyVO.getDayend_date()%>;
     	var effdate = <%=agencyVO.getEffective_date()%>;
		var a_created_date = <%=agencyVO.getCreated_date()%>;
    	var dealergstin =  "<%= agencyVO.getGstin_no() %>";
    	
/*     	document.getElementById("vSpan").innerHTML = "<select id='vid' name='vid' class='form-control input_field'>"+vendordatahtml+"</select>"; */
		$(document).ready(function() {
	   	    $('#m_data_table').DataTable( { 
//  	 	    	"bPaginate" : $('#m_data_table tbody tr').length>5,
//	   			    "iDisplayLength": 5,
				"lengthMenu": [[5,10, 25, 50, -1], [5,10, 25, 50, "All"]],			
				"bFilter": false,
   	    		"ordering": false,
   	    		"scrollY":'50vh',
				"scrollCollapse": true,
   	        	"scrollX": true,
            	language: {
                	paginate: {
            	    	next: '&#x003E;&#x003E;',
                    	previous: '&#x003C;&#x003C;'
                	}
              	}
   	    	} );
	   	    
	    	 $('#cp_data_table').DataTable( {
	 	 			"lengthMenu": [[5,10, 25, 50, -1], [5,10, 25, 50, "All"]],
	 		    	"bFilter": false,
	 		    	"ordering": false,
	 		    	"scrollY":'50vh',
	 			    "scrollCollapse": true,
	 		        "scrollX": true,
	 		        language: {
	 	             paginate: {
	 	             	next: '&#x003E;&#x003E;',
	 	                 previous: '&#x003C;&#x003C;'
	 	             }
	 	           }
	 		    } );
	 		 
	 		$('#arbp_popup_data_table').DataTable( {
	 			    "lengthMenu": [[5,10, 25, 50, -1], [5,10, 25, 50, "All"]],
	 		    	"bFilter": false,
	 		    	"ordering": false,
	 	 	    "scrollY":'50vh',
	 		        "scrollCollapse": true,
	 		        "scrollX": true,
	 	         language: {
	 	             paginate: {
	 	             	next: '&#x003E;&#x003E;',
	 	                 previous: '&#x003C;&#x003C;'
	 	             }
	 	           }
	 		    } );
	 		    

	 	   	 $('#ds_data_table').DataTable( {
	  			"lengthMenu": [[5,10, 25, 50, -1], [5,10, 25, 50, "All"]],
	 	    	"bFilter": false,
	 	    	"ordering": false,
	 	    	"scrollY":'50vh',
	 		    "scrollCollapse": true,
	 	        "scrollX": true,
	 	        language: {
	              paginate: {
	              	next: '&#x003E;&#x003E;',
	                  previous: '&#x003C;&#x003C;'
	              }
	            }
	 	    } );
	 	 
	 	$('#cs_popup_data_table').DataTable( {
	 		    "lengthMenu": [[5,10, 25, 50, -1], [5,10, 25, 50, "All"]],
	 	    	"bFilter": false,
	 	    	"ordering": false,
	  	    "scrollY":'50vh',
	 	        "scrollCollapse": true,
	 	        "scrollX": true,
	          language: {
	              paginate: {
	              	next: '&#x003E;&#x003E;',
	                  previous: '&#x003C;&#x003C;'
	              }
	            }
	 	    } );
	 	    
	 	$('#arb_popup_data_table').DataTable( {
	 	    "lengthMenu": [[5,10, 25, 50, -1], [5,10, 25, 50, "All"]],
	     	"bFilter": false,
	     	"ordering": false,
	 	    "scrollY":'50vh',
	         "scrollCollapse": true,
	         "scrollX": true,
	      language: {
	          paginate: {
	          	next: '&#x003E;&#x003E;',
	              previous: '&#x003C;&#x003C;'
	          }
	        }
	     } );
	   	    
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
</html>