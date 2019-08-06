<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<title>MyLPGBooks DEALER ENTERPRISE SOLUTIONS APP</title>
</head>
<body>
	<br>
	<br>
	<center>
		<!-- https://ux.stackexchange.com/questions/29449/what-are-some-best-practices-for-displaying-an-unsupported-browser-warning-in -->
		<!-- http://www.mccc.edu/~virtcoll/rodney/unsupported.html -->
		<!-- https://www.google.co.in/search?ei=PYonWpGPBoGU8wXIva6QBw&q=unsupported+browser+message+examples&oq=browser+compatibility+messages&gs_l=psy-ab.3.2.0i71k1l4.0.0.0.95167.0.0.0.0.0.0.0.0..0.0....0...1..64.psy-ab..0.0.0....0.m2UcNur-UdA -->

		<h1><img src="<%=request.getContextPath() %>/images/warningImg.png" alt="" title="" width="90"/></h1>
		<h3 style="color:blue"><b> Unsupported Browser</b></h3>
		<font size="3" face = "Times New Roman" color="sky blue">You are seeing this page because you are using an unsupported browser.<br>
		We recommend to use the current versions of Google Chrome OR Mozilla Firefox..</font>
	</center>
</body>
</html>