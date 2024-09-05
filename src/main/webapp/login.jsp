<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta charset="UTF-8">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Login</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
	</head>
	<body style="padding: 15px">
		<div style="display:flex; justify-content:center; align-items: center; height:100vh;">
			<form method="post" action="servlet/DispatchServlet" class="pure-form">
				<fieldset>
					<legend>團購登入</legend>
					<input type="hidden" name="action" value="login">
					😀 帳號: <input type="text" id="username" name="username" placeholder="請輸入帳號" value="user123" required /><p />
					🔑 密碼: <input type="password" id="password" name="password" placeholder="請輸入密碼" value="pass123" required /><p />
					<button type="submit" class="pure-button pure-button-primary">登入</button>
				</fieldset>
			</form>
		</div>
	</body>
</html>