<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>MyLPGBooks DEALER ENTERPRISE SOLUTIONS APP</title>

<script>
history.pushState(null, null, 'https://www.mylpgbooks.com/ebs/jsp/pages/global.jsp');
window.addEventListener('popstate', function () {
    history.pushState(null, null, 'https://www.mylpgbooks.com/ebs/jsp/pages/global.jsp');
});

function closeWindows() {
	setTimeout("window.close();",15000);  
}
</script>

 
</head>
 <body onLoad='closeWindows()'>
<br>
<br>
<center>
<h2><font color="green"><code> YOUR SESSION  HAS  BEEN  EXPIRED </code></font></h2>
<h4> <code>PLEASE CLOSE THIS WINDOW. </code></h4>
<h5><code>THIS WILL ENSURE THAT ANY INFORMATION THAT IS CACHED (STORED) ON YOUR BROWSER IS ERASED AND WILL NOT ALLOW OTHERS TO VIEW IT LATER.</code></h5>
</center>
</body>
</html>