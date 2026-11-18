<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="pt-br">

<head>
<%@ include file="base-head.jsp"%>
<title>Login - Sistema Autoescola</title>

<style>
body {
	margin: 0;
	background-color: #f5f5f5;
}

.navbar {
	background-color: #000;
	border: none;
	border-radius: 0;
	margin-bottom: 0;
}

.navbar-header {
    display: flex;
    align-items: center;
    gap: 10px;
}

.navbar-brand {
	color: #fff !important;
	font-weight: bold;
}

.login-container {
	min-height: calc(100vh - 120px);
	display: flex;
	justify-content: center;
	align-items: center;
}

.login-card {
	width: 100%;
	max-width: 400px;
	background-color: #fff;
	border-radius: 8px;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.login-card .alert {
	width: 18.3%;
	box-sizing: border-box;
	margin-bottom: 0;
}

.login-card .panel-heading {
	text-align: center;
	background-color: #222;
	color: #fff;
	border-radius: 8px 8px 0 0;
}

.login-card .panel-body {
	padding: 25px;
}

footer {
	padding: 10px 0;
}
</style>
</head>

<body>

	<nav class="navbar navbar-default">
		<div class="container">
			<div class="navbar-header">
				<img height="30px" width="30px" alt="Autoescola" src="${pageContext.request.contextPath}/images/logo.png"> 
					<span class="navbar-brand">Sistema Autoescola</span>
			</div>
		</div>
	</nav>

	<main class="container login-container">

		<div class="panel panel-default login-card">

			<div class="panel-heading">
				<h3>Login do Instrutor</h3>
			</div>

			<div class="panel-body">

				<form action="${pageContext.request.contextPath}/login"
					method="POST">

					<div class="form-group">
						<label for="cnh">CNH</label> <input type="text"
							class="form-control" id="cnh" name="cnh"
							placeholder="Digite sua CNH" required />
					</div>

					<div class="form-group">
						<label for="password">Senha</label> <input type="password"
							class="form-control" id="password" name="password"
							placeholder="Digite sua senha" required />
					</div>

					<div class="form-group">
						<button type="submit" class="btn btn-danger btn-block">
							Entrar</button>
					</div>

					<c:if test="${param.erro == 'true'}">
						<div class="alert alert-danger">CNH ou senha inválidas.</div>
					</c:if>

				</form>

			</div>
		</div>

	</main>

	<footer class="text-center">
		<div class="container">
			<hr />
			<jsp:useBean id="date" class="java.util.Date" />
			<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
			<p>Sistema de Gerenciamento de Autoescola - © ${currentYear}</p>
		</div>
	</footer>

	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

</body>
</html>