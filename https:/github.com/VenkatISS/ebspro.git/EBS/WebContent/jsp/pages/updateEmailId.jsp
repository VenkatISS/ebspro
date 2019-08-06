<%@ page import="com.it.erpapp.response.MessageObject,java.util.UUID" %>
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
		<title>MyLPGBooks DEALER EBS WEB APPLICATION - U</title>
		<jsp:useBean id="agencyVO" scope="session"
				class="com.it.erpapp.framework.model.vos.AgencyVO"></jsp:useBean>
		<jsp:useBean id="profile_data" scope="request" class="java.lang.String"></jsp:useBean>
		<script src="js/commons/jquery-2.1.4.min.js?<%=randomUUIDString%>"></script>
		<script type="text/javascript" src="js/commons/general_validations.js?<%=randomUUIDString%>"></script>
		<script src="js/commons/checkBrowser.js?<%=randomUUIDString%>"></script>
		<script type="text/javascript">
        	var emailId=<%= agencyVO.getEmail_id() %>;
        	var otpValue=<%=request.getAttribute("generatedOtp")%>;
        </script>
		<script type="text/javascript">
                window.onload = setWidthHightNav("100%");
        </script>
	</head>
<body>
	<br>
	<br>
	<%
		MessageObject msgObj = (MessageObject) request.getAttribute("msg_obj");
		if((null != msgObj)) {
			if((msgObj.getMessageId() == 9011) && (msgObj.getMessageStatus()=="SUCCESS")) {
	%>
				<div style="text-align:center;">
					<p style="font-size: 20px;">Congratulations! Your Email has been verified successfully.</p><br>
					<center><hr style="width:80%;"></center>
					<p style="font-size: 15px;">Please <a href="https://www.mylpgbooks.com/ebs/login">Click Here</a> to login to MyLPGBooks Dealer Account.</p>
				</div>

			<% }else if ((msgObj.getMessageId() == 9011) && (msgObj.getMessageStatus()=="ERROR")) { %>
					<div style="text-align:center;">
						<p style="font-size: 20px;">Your Email ID has already been updated.</p><br>
						<center><hr style="width:80%;"></center>
						<p style="font-size: 15px;">Please <a href="https://www.mylpgbooks.com/ebs/login">Click Here</a> to login to MyLPGBooks Dealer Account.</p>
					</div>
		<% }} %>

</body>
</html>