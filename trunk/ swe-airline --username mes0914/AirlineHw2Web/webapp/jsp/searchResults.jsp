<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Search Results Page</title>
</head>
<body>
	<c:set var="basePath" value="${pageContext.request.contextPath}" />
	<h1>Available Flights</h1>
	
	<c:forEach var="flight" items="${requestScope.flights}">
	
	</c:forEach>
	
 	<form action="${basePath}/flightSearch">
 		
 	</form>
 	<br />
 	<a href="${basePath}/prepareSearch">Run New Search</a><br>
	<a href="${basePath}/jsp/home.jsp">Main Menu</a>
</body>
<script type="text/javascript">
	//load();

	function clearDate(){
		document.getElementById("flightDate").value = "";
	}
</script>
</html>