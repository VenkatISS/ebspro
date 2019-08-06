<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>MyLPGBooks DEALER EBS WEB APPLICATION - MASTER DATA PAGE</title>
<jsp:useBean id="agencyVO" scope="session" class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
</head>
<body style="width: 100%;margin: -1px;padding: -1px;">
	<table style="width: 100%;border-collapse: 0;border-spacing: 0;border: 1;">
		<tr valign="top">
			<td style="width: 100%">
			<header style="background-color: #145272;height: 100px;vertical-align: middle;text-align: center;">
				<br>
				<h2><font color="white">MyLPGBooks DEALER EBS PORTAL</font></h2>
			</header>
			</td>
		</tr>
		<tr valign="middle" height="20px" bgcolor="#DBEEFA">
			<td style="width: 100%; text-align: right;" nowrap="nowrap">
				<font face="tahoma" size=2><b>WELCOME : <span id="nameSpan"></span></b></font>
				<form id="logout_form" name="logout_form" action="<%=request.getContextPath() %>/login" method="post">
					<input type="hidden" id="actionId" value="1000">
					<input type="hidden" id="page" value="jsp/pages/app.jsp"> 
					<input type="button" value="LOGOUT" onclick="logoutDealer(this.form)">
				</form>
			</td>
		</tr>
		<tr valign="top">
			<td>
				<table style="width: 100%; border: 0; border-spacing: 0; border-collapse: 0;padding: -1px;margin: -1px;">
					<tr valign="top">
						<td width="20%">
<!-- Header-->
		<jsp:include page="/jsp/pages/commons/header.jsp"/>      
	  	<!--header End--->
	<!-- Sidenav -->
		<jsp:include page="/jsp/pages/commons/sidenav.jsp"/>    
	  <!---END Sidenav--->
						</td>
						<td width="80%">
							MASTER DATA PAGE
						</td>
				</table>
			</td>
		</tr>
	</table>
</body>
<script type="text/javascript">
document.getElementById("nameSpan").innerHTML = <%= agencyVO.getAgency_code() %>
</script>