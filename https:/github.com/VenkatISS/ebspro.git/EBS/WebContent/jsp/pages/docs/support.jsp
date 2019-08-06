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
    <title>LPG DEALER WEB APPLICATION - SUPPORT</title>
	<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
	<jsp:useBean id="service_types_list" scope="request" class="java.lang.String"></jsp:useBean>
	<jsp:useBean id="services_data" scope="request" class="java.lang.String"></jsp:useBean>
	<script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>
	<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script>
	<script type="text/javascript"> 
		window.onload = setWidthHightNav("100%");
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
            			<h1>SUPPORT </h1>
          			</div>
        		</div>
				<div class="row">
          			<div class="clearfix"></div>
          			<div class="col-md-12">
          			UNDER DEVELOPMENT
          			</div>
        		</div>
      		</div>
    	</div>
        <script src="js/commons/bootstrap.min.js?<%=randomUUIDString%>"></script>
		<script src="js/commons/plugins/pace.min.js?<%=randomUUIDString%>"></script>
        <script src="js/commons/main.js?<%=randomUUIDString%>"></script>
    	<script>
			document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>
		</script>
 	</body>
</html>