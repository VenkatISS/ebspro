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
    <title>LPG DEALER WEB APPLICATION - CLIENT LIST</title>
	<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
	<jsp:useBean id="clients_data" scope="request" class="java.lang.String"></jsp:useBean>	
	<script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript"> 
		window.onload = setWidthHightNav("100%");
	</script>
	<script type="text/javascript">
		var page_data = <%= clients_data.length()>0? clients_data : "[]" %>;
	</script>
  </head>
  <body class="sidebar-mini fixed">
    	<div class="wrapper">
    	<!-- Header-->
		<jsp:include page="/jsp/pages/commons/header.jsp"/>      
	  	<!--header End--->
		<!-- Sidenav -->
		<jsp:include page="/jsp/pages/commons/sidenav.jsp"/>    
	  	<!---END Sidenav--->
      		<div class="content-wrapper">
        		<div class="page-title">
          			<div>
            			<h1>CLIENTS </h1>
          			</div>
        		</div>
				<div class="row">
          			<div class="clearfix"></div>
          			<div class="col-md-12">
						<div class="table-responsive">									
               	 			<table class="table table-striped">
                  				<thead>
                     				<tr class="title_head">
					                	<th width="10%" class="text-center sml_input">AGENCY NAME</th>
                					  	<th width="10%" class="text-center sml_input">OIL COMPANY</th>
                  	    				<th width="10%" class="text-center sml_input">STATE</th>
					                 </tr> 
                  				</thead>
                  				<tbody id="page_data_table_body"></tbody>
							</table>
						</div>	                  				
          			</div>
        		</div>
      		</div>
    	</div>
        <script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
		<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
        <script src="js/commons/main.js?<%=randomUUIDString%>"></script>
    	<script>
			document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>;
			
			var tbody = document.getElementById('page_data_table_body');
			for(var f=0; f<page_data.length; f++) {
				var emailId = page_data[f].email_id;
				var wbflag = emailId.includes("@wearberryworld.com");
				var gflag = emailId.includes("@gmailwb.com");
				if((wbflag != true) && (gflag != true)) {
					var tblRow = tbody.insertRow(-1);
					tblRow.align="left";

					tblRow.innerHTML = "<tr>"+
    					"<td>"+ (page_data[f].agency_name).toUpperCase() +"</td>"+
     					"<td>"+ findOMCname(page_data[f].agency_oc) +"</td>"+
     					"<td>"+ findState(page_data[f].agency_st_or_ut)+"</td>"+
   	 					"</tr>";
				}
			};
			
			
			function findOMCname(agency_oc) {
				var omc_name;
		     	if(agency_oc==1)
					omc_name = "IOCL";
				else if(agency_oc==2)
					omc_name = "HPCL";
				else if(agency_oc==3)
					omc_name = "BPCL";
		     	
		     	return omc_name;
			}
			
			function findState(stcode) {
				var regstate;
				switch(stcode) {
					case 1: regstate="JAMMU & KASHMIR";
						  	break;	
					case 2: regstate="HIMACHAL PRADESH";
				  			break;
					case 3: regstate="PUNJAB";
				  			break;
					case 4: regstate="CHANDIGARH";
				  			break;
					case 5: regstate="UTTARAKHAND";
				  			break;
					case 6: regstate="HARYANA";
				  			break;
					case 7: regstate="DELHI";
				  			break;
					case 8: regstate="RAJASTHAN";
				  			break;
					case 9: regstate="UTTAR PRADESH";
				  			break;
					case 10: regstate="BIHAR";
				  			 break;
					case 11: regstate="SIKKIM";
				  			break;
					case 12: regstate="ARUNACHAL PRADESH";
				  			break;
					case 13: regstate="NAGALAND";
				  			break;
					case 14: regstate="MANIPUR";
				  			break;
					case 15: regstate="MIZORAM";
				  			break;
					case 16: regstate="TRIPURA";
				  			break;
					case 17: regstate="MEGHALAYA";
				  			break;
					case 18: regstate="ASSAM";
				  			break;
					case 19: regstate="WEST BENGAL";
				  			break;
					case 20: regstate="JHARKHAND";
				  			break;
					case 21: regstate="ODISHA";
				  			break;
					case 22: regstate="CHHATTISGARH";
				  			break;
					case 23: regstate="MADHYA PRADESH";
				  			break;
					case 24: regstate="GUJARAT";
				  			break;
					case 25: regstate="DAMAN & DIU";
				  			break;
					case 26: regstate="DADRA & NAGAR HAVELI";
				  			break;
					case 27: regstate="MAHARASHTRA";
				  			break;
					case 29: regstate="KARNATAKA";
				  			break;
					case 30: regstate="GOA";
				  			break;
					case 31: regstate="LAKSHDWEEP";
				  			break;
					case 32: regstate="KERALA";
				  			break;
					case 33: regstate="TAMIL NADU";
				  			break;
					case 34: regstate="PONDICHERRY";
				  			break;
					case 35: regstate="ANDAMAN & NICOBAR ISLANDS";
				  			break;
					case 36: regstate="TELANGANA";
				  			break;
					case 37: regstate="ANDHRA PRADESH";
				  			break;
					case 97: regstate="OTHER TERRITORY";
							break;
				}
				return regstate;
			}
		</script>
	</body>
</html>