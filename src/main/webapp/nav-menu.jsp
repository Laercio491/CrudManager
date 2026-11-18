<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<nav id="menu" class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">

        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed"
                    data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

            <a class="navbar-brand" href="${pageContext.request.contextPath}">
                <span>
                    <img height="30px" width="30px" alt="Autoescola"
                         src="${pageContext.request.contextPath}/images/logo.png">
                    <strong>&nbsp;Autoescola</strong>
                </span>
            </a>
        </div>

        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">

                <li>
                    <a href="${pageContext.request.contextPath}">
                        <span class="glyphicon glyphicon-home"></span>
                        <strong>&nbsp;Início</strong>
                    </a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/instrutores">
                        <span class="glyphicon glyphicon-user"></span>
                        <strong>&nbsp;Instrutores</strong>
                    </a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/alunos">
                        <span class="glyphicon glyphicon-education"></span>
                        <strong>&nbsp;Alunos</strong>
                    </a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/veiculos">
                        <span class="glyphicon glyphicon-road"></span>
                        <strong>&nbsp;Veículos</strong>
                    </a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/aulas">
                        <span class="glyphicon glyphicon-calendar"></span>
                        <strong>&nbsp;Aulas</strong>
                    </a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/pagamentos">
                        <span class="glyphicon glyphicon-usd"></span>
                        <strong>&nbsp;Pagamentos</strong>
                    </a>
                </li>
                
                <li>
                    <a href="${pageContext.request.contextPath}/logout">
                        <span class="glyphicon glyphicon-log-out"></span>
                         &nbsp;Sair
                    </a>
                </li>

            </ul>
        </div>
    </div>
</nav>

<br><br><br>
