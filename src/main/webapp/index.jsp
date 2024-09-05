<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome</title>
</head>
<body>
	<h1>Index</h1>
	<ul>
		<li>
			<a href="${pageContext.request.contextPath}/controller/bmi?h=170&w=60">BMIServlet</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/controller/bmr?h=170&w=60&age=30">BMRServlet</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/controller/score?score=100&score=40&score=72">ScoreServlet</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/controller/guestbook">GuestBookServlet(InMemory 版)</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/controller/guestbook/guestbook">GuestBookServlet(MySQL 版)</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/groupbuy/login.jsp">團購(MsSQL 版)</a>
		</li>
	</ul>
</body>
</html>