<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <%@ include file="base-head.jsp" %>
    <title>Sistema Autoescola</title>
</head>

<body>
    <%@ include file="modal.html" %>
    <%@ include file="nav-menu.jsp" %>

    <div id="container" class="container-fluid">

        <div id="alert"
             style="${not empty message ? 'display: block;' : 'display: none;'}"
             class="alert alert-dismissable ${alertType eq 1 ? 'alert-success' : 'alert-danger'}">

            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            ${message}
        </div>

        <div class="row">
            <div class="col-md-12 text-center">
                <h2>Bem-vindo ao Sistema de Gerenciamento da Autoescola</h2>
                <p class="text-muted">Selecione uma das opções abaixo para acessar o sistema.</p>
            </div>
        </div>

        <hr />

        <div class="row text-center">

            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <span class="glyphicon glyphicon-user" style="font-size: 40px;"></span>
                        <h3>Instrutores</h3>
                        <p>Cadastre, consulte e gerencie os instrutores.</p>
                        <a href="${pageContext.request.contextPath}/instrutores"
                           class="btn btn-danger">
                            Acessar Instrutores
                        </a>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <span class="glyphicon glyphicon-education" style="font-size: 40px;"></span>
                        <h3>Alunos</h3>
                        <p>Cadastre, consulte e gerencie os alunos.</p>
                        <a href="${pageContext.request.contextPath}/alunos"
                           class="btn btn-danger">
                            Acessar Alunos
                        </a>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <span class="glyphicon glyphicon-road" style="font-size: 40px;"></span>
                        <h3>Veículos</h3>
                        <p>Consulte e gerencie os veículos da autoescola.</p>
                        <a href="${pageContext.request.contextPath}/veiculos"
                           class="btn btn-danger">
                            Acessar Veículos
                        </a>
                    </div>
                </div>
            </div>

        </div>

        <div class="row text-center">

            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <span class="glyphicon glyphicon-calendar" style="font-size: 40px;"></span>
                        <h3>Aulas</h3>
                        <p>Agende, consulte e gerencie as aulas dos alunos.</p>
                        <a href="${pageContext.request.contextPath}/aulas"
                           class="btn btn-danger">
                            Acessar Aulas
                        </a>
                    </div>
                </div>
            </div>

            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <span class="glyphicon glyphicon-usd" style="font-size: 40px;"></span>
                        <h3>Pagamentos</h3>
                        <p>Consulte e gerencie os pagamentos dos alunos.</p>
                        <a href="${pageContext.request.contextPath}/pagamentos"
                           class="btn btn-danger">
                            Acessar Pagamentos
                        </a>
                    </div>
                </div>
            </div>

        </div>

    </div>

    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/jquery.min.js"></script>

    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function() {
            setTimeout(function() {
                $("#alert").slideUp(500);
            }, 3000);
        });
    </script>

</body>
</html>
