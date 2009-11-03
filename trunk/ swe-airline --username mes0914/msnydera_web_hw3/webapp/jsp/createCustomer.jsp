<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Create Customer</title>
	
	<style type="text/css">
		table.searchResults {
			border-width: 1px 1px 1px 1px;
			border-spacing: 2px;
			border-style: none none none none;
			border-color: gray gray gray gray;
			border-collapse: separate;
			background-color: white;
		}
		table.searchResults th {
			border-width: thin thin thin thin;
			padding: 2px 2px 2px 2px;
			border-style: inset inset inset inset;
			border-color: gray gray gray gray;
			background-color: white;
			-moz-border-radius: 0px 0px 0px 0px;
		}
		table.searchResults td {
			border-width: thin thin thin thin;
			padding: 2px 2px 2px 2px;
			border-style: inset inset inset inset;
			border-color: gray gray gray gray;
			background-color: white;
			-moz-border-radius: 0px 0px 0px 0px;
		}
	</style>
</head>
<body>
	<c:set var="basePath" value="${pageContext.request.contextPath}" />
	<h1>Create Customer</h1>
	<c:if test="${not empty requestScope.error}">
 		<span style="color: red;">
 			<ul>
 				<li><c:out value="${requestScope.error}" escapeXml="false" /></li>
 			</ul>
 		</span>
 	
 	</c:if>
 	
 	<c:if test="${not empty requestScope.createdCustomer}">
 		<span style="color: green;">
 			<ul>
 				<li>Successfully created ${createdCustomer.name} as a new customer.</li>
 			</ul>
 		</span>
 	</c:if>
 	
 	<b>Please enter the information to create a customer:</b>
 	
 	<form action="${basePath}/createCustomer">
	 	Customer Name: <input type="text" name="customerName"> <br /> 
	 	Customer Address: <input type="text" name="address"> <br />
	 	Customer Phone Number: <input type="text" name="phone"> <br />
	 	<span style="font-style: italic;">Note: There is no validation for a valid phone number.  There is also no duplicate customer checking.</span>
	 	<br />
	 	<input type="submit" value="Create Customer" />
 	</form>
 	 
 	<br />
	<a href="${basePath}/jsp/home.jsp">Main Menu</a>
	
	<h2>Existing Customers</h2>
	<display:table name="requestScope.customers" cellspacing="10px" class="searchResults">
 		<display:column property="id" title="Customer ID" />
 		<display:column property="name" title="Name" />
 		<display:column property="address" title="Address" />
 		<display:column property="phone" title="Phone" />
 	</display:table>
</body>
</html>