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
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/main.css?<%=randomUUIDString%>">
    <title>LPG DEALER ERP WEB APPLICATION - NC/DBC PACKAGING </title>
    <jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
	<jsp:useBean id="cylinder_types_list" scope="request" class="java.lang.String"></jsp:useBean>
	<jsp:useBean id="arb_types_list" scope="request" class="java.lang.String"></jsp:useBean>
	<jsp:useBean id="arb_data" scope="request" class="java.lang.String"></jsp:useBean>
	<jsp:useBean id="service_types_list" scope="request" class="java.lang.String"></jsp:useBean>
	<jsp:useBean id="prod_types_list" scope="request" class="java.lang.String"></jsp:useBean>
	<jsp:useBean id="bom_data" scope="request" class="java.lang.String"></jsp:useBean>
	<script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>
<%-- 	<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script> --%>

	<!-- Sidenav -->
		<jsp:include page="/jsp/pages/commons/sidenav.jsp"/>    
	<!---END Sidenav--->

	<script type="text/javascript"> 
		window.onload = setWidthHightNav("100%");
	</script>
	<script type="text/javascript">
		var e_types = <%= cylinder_types_list %>;
		var s_types = <%= service_types_list %>;
		var arb_types = <%= arb_types_list %>;
		var arb_data = <%= arb_data %>;
		var prod_types = <%= prod_types_list %>;
		var page_data =  <%= bom_data.length()>0? bom_data : "[]" %>;
		function tableAlternateRow() {
		    if(document.getElementsByTagName){
		        var table = document.getElementById("m_data_table");
		        var rows = table.getElementsByTagName("tr");
		               var rl=rows.length;
		               var rowno=rl-rl+1;
		        for(j=0;j<page_data.length;j++){
		                var length=page_data[j].bomItemsSet.length;
		                if(length>1){
		                         if(j % 2 == 0){
		                                for(k=0;k<length;k++){
		                        rows[rowno].className = "even";
		                        rowno++;
		                                }
		                 }else{
		                    for(h=0;h<length;h++){
		                        rows[rowno].className = "odd";
		                        rowno++;
		                            }
		                 }
		                }else{
		                        if(j % 2 == 0){
		                        rows[rowno].className = "even";
		                    rowno++;
		                }else{
		                    rows[rowno].className = "odd";
		                    rowno++;
		                }
		                }
		        }
		    }
		}
		if (window.addEventListener) {
		    window.addEventListener("load", tableAlternateRow, false);
		} else if (window.attachEvent) {
		    window.attachEvent("onload", tableAlternateRow);
		} else {
		    window.onload = tableAlternateRow;
		}
	</script>
	<style>
table tr.even {
    background: #fff;
}
table tr.odd {
    background: #eeeeee;
}
</style>
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
<!--   	<div id = "dialog-1" title="Alert(s)"></div>
 	<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div> -->
        		<div class="page-title">
          			<div>
<!--             			<h1>BOM/NC PACKAGING </h1> -->
            			<h1>NC/DBC PACKAGING </h1>
          			</div>
					<ul id="nav" class="nav nav-pills clearfix right" role="tablist">
          				<li class="dropdown"><span class="dropdown-toggle  btn-info" data-toggle="dropdown" id="ahelp">Help</span>
							<ul id="products-menu" class="dropdown-menu clearfix" role="menu">
								<li><a style="font-size: 14px;" title="NC DBC Packaging" href="https://www.youtube.com/watch?v=qEKqUcZygbw" target="_blank">English</a></li>
								<li><a style="font-size: 14px;" href="https://youtu.be/itSXk3OJvok" target="_blank">Hindi</a></li>
							</ul>
						</li>
					</ul>
				</div>
				<button name="b_sb" id="b_sb" class="btn btn-info color_btn bg_color4" onclick="showBOMFormDialog()">ADD </button>
				<button name="cancel_data" id="cancel_data"	class="btn btn-info color_btn bg_color2" onclick="doAction('MasterDataControlServlet','jsp/pages/erp/home.jsp','2001')">BACK</button>
				<!-- Modal -->
				<div class="modal_popup_add fade in" id="myModal" style="display: none;padding-left: 14px; height: 100%">					
					<div class="modal-dialog modal-lg">
			  			<!-- Modal content-->
			  			<div class="modal-content" id="modal-content">
							<div class="modal-header" id="modal-header">
					  			<span class="close" id="close" onclick="closeBOMFormDialog()">&times;</span>
<!-- 					  			<h4 class="modal-title">BOM/NC PACKAGING</h4> -->
					  			<h4 class="modal-title">NC/DBC PACKAGING</h4>
							</div>
							<div class="modal-body">
								<div class="row">
									<br/>
									<div class="col-sm-3 bomspan">
										<span id="espan"></span> &nbsp; 
									</div>
									<div class="col-sm-3 bomspan">
										<span id="rspan"></span> &nbsp; 
									</div>
									<div class="col-sm-3 bomspan">
										<span id="sspan"></span> &nbsp; 
									</div>
									<div class="col-sm-3 bomspan">
										<span id="arbspan"></span> &nbsp; 
									</div><br/><br/><br/>
									<div class="col-sm-3">
										<button name="add_e" id="add_e"  class="btn btn-info color_btn" onclick="addProduct('e')">ADD EQUIPMENT</button>
									</div>
									<div class="col-sm-3">
										<button name="add_r" id="add_r"  class="btn btn-info color_btn" onclick="addProduct('r')">ADD REGULATOR</button>
									</div>
									<div class="col-sm-3">
										<button name="add_s" id="add_s"  class="btn btn-info color_btn" onclick="addProduct('s')">ADD SERVICE</button>
									</div>
									<div class="col-sm-3">
										<button name="add_a" id="add_a"  class="btn btn-info color_btn" onclick="addProduct('a')">ADD ARB ITEM</button>
									</div>
									<div class="clearfix"></div>
								</div>
								<br/>
								<div>
									<form id="data_form" name="" method="post" action="TransactionsDataControlServlet">
										<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
										<input type="hidden" id="page" name="page" value="jsp/pages/erp/masterdata/products/bom_data.jsp">
										<input type="hidden" id="actionId" name="actionId" value="3142">
									
										<div class="col-sm-3">
											<!-- <label  style="margin-top:10px;">NC/DBC PACKAGING NAME </label> -->
											<label style="margin-top:10px;margin-left:-10px;">PACKAGING NAME </label>
										</div>
										<div style="display:inline-block; width:390px;%;">
											<label style="margin-left:-100px;">CONNECTION TYPE</label>
											&nbsp&nbsp&nbsp
											<input type="radio" id="ctypen" name="ctype" value="1" style="margin: 7px;">GEN NC 
											&nbsp&nbsp&nbsp
											<input type="radio" id="ctyped" name="ctype" value="2" style="margin: 7px;">DBC
											&nbsp&nbsp&nbsp
											<input type="radio" id="ctypebpl" name="ctype" value="4" style="margin: 7px;">BPL 
											&nbsp&nbsp&nbsp
											
											<label>UJJWALA: </label>
											<input type="radio" id="ctypeul" name="ctype" value="3" style="margin: 5px;">LOAN 
											<br><span style="margin-left:330px;"><input type="radio" id="ctypeuc" name="ctype" value="5" style="margin: 5px;">CASH</span>
											
										</div>
										
										<div class="col-sm-3">
											<!--<input type="text" class="form-control input_field bom" id="bom_name" name="bom_name" style="margin-left:-130px;" placeholder="NC/DBC PACKAGING NAME"  maxlength="25"> -->
					    					<input type="text" class="form-control input_field bom" id="bom_name" name="bom_name" style="" placeholder="NC/DBC PACKAGING NAME"  maxlength="25">
										</div>
										<div class="clearfix"></div>
										<br/>
										<div class="row">
         									<div class="clearfix"></div>
         									<div class="col-md-12">
            									<div class="main_table">
              										<div class="table-responsive">
                										<table class="table" id="s_data_table">
                  											<thead>
                    											<tr class="title_head">
                      												<th width="10%" class="text-center sml_input">PRODUCT</th>
					  												<th width="10%" class="text-center sml_input">QUANTITY	</th>
                      												<th width="10%" class="text-center sml_input">DEPOSIT REQUIRED</th>
					  												<th width="10%" class="text-center sml_input">Actions</th>
                   												</tr>
                  											</thead>
                  											<tbody id="s_data_table_tbody"></tbody>
                										</table>
	              									</div>
    	        								</div>
											</div>
        								</div>
        							</form>
        						</div>
        						<div class="row">
									<div class="clearfix"></div><br/>
										<div class="col-md-2">
											<button name="save_data" class="btn btn-info color_btn bg_color2" id="save_data" onclick="saveData(this)">SAVE</button>
										</div>
								</div>
		   					</div>
			  			</div>
					</div>
				</div>
				<div class="row">
          			<div class="clearfix"></div>
          				<div class="col-md-12">
<!-- 							<h1 class="table_title">BOM DATA PAGE   </h1> -->
							<h1 class="table_title">NC/DBC PACKAGING DATA PAGE   </h1>
            				<div class="main_table">
              					<div class="table-responsive">
              						<form id="m_data_form" name="" method="post" action="MasterDataControlServlet">
										<input type="hidden" id="agencyId" name="agencyId" value="<%= agencyVO.getAgency_code() %>">
										<input type="hidden" id="page" name="page" value="jsp/pages/erp/masterdata/products/bom_data.jsp">
										<input type="hidden" id="actionId" name="actionId" value="">
										<input type="hidden" id="dataId" name="dataId" value="">
                					<table class="table" id="m_data_table">
                  						<thead>
                    						<tr class="title_head">
												<th width="10%" class="text-center sml_input">NC/DBC PACKAGING NAME</th>
                     		 					<th width="10%" class="text-center sml_input">PRODUCT DETAILS</th>
					  							<th width="10%" class="text-center sml_input">QUANTITY</th>
                      							<th width="10%" class="text-center sml_input">DEPOSIT REQUIRED</th>
						 						<th width="10%" class="text-center sml_input">Actions</th>
                    						</tr>
                  						</thead>
                  						<tbody id="m_data_table_body">
                  	 					</tbody>
                					</table>
                					</form>
              					</div>
            				</div>
          				</div>
        			</div>
      			</div>
    		</div>

		  	<div id = "dialog-1" title="Alert(s)"></div>
		 	<div id="dialog-confirm"><div id="myDialogText" style="color:black;"></div></div>
    	</body>
  	<script type="text/javascript" src="js/local.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript" src="js/modules/masterdata/products/bom_data.js?<%=randomUUIDString%>"></script>
    <script src="js/commons/main.js?<%=randomUUIDString%>"></script>

 	<script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
    <script type="text/javascript">
		document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>;
		
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
</html>