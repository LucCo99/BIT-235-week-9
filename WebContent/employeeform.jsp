<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Employees</title>
</head>
<body>
	<center>
		<h4>
			<a href="${pageContext.request.contextPath}/EmployeeServlet?action=list">List All Employees</a>
		</h4>
	</center>
	<div align="center">
		<c:if test="${employee != null}">
			<form action="${pageContext.request.contextPath}/EmployeeServlet" method="post">
				<input type="hidden" name="action" value="update">
		</c:if>
		<c:if test="${employee == null}">
			<form action="${pageContext.request.contextPath}/EmployeeServlet?action=insert" method="post">
		</c:if>
				<table border="1" cellpadding="5">
					<caption>
						<h2>
							<c:if test="${employee != null}"> Edit Employee </c:if>
							<c:if test="${employee == null}"> Add New Employee </c:if>
						</h2>
					</caption>
					<c:if test="${employee != null}">
						<input type="hidden" name="id" value="<c:out value='${employee.getEid()}' />" />
					</c:if>
					<tr>
						<th>Employee Name:</th>
						<td><input type="text" name="name" size="45" required value="<c:out value='${employee.getEname()}' />" /></td>
					</tr>
					<tr>
						<th>Age:</th>
						<td><input type="number" name="age" min="10" required value="<c:out value='${employee.getEage()}' />" /></td>
					</tr>
					<tr>
						<td colspan="2" align="center"><input type="submit" value="Save" /></td>
					</tr>
				</table>
			</form>
	</div>
</body>
</html>