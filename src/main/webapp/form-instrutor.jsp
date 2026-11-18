<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-br">

<head>
    <%@ include file="base-head.jsp" %>
</head>

<body>

    <%@ include file="nav-menu.jsp" %>

    <div id="container" class="container-fluid">

        <h3 class="page-header">
            ${not empty instrutor ? "Alterar Instrutor" : "Adicionar Instrutor"}
        </h3>

        <form action="${pageContext.request.contextPath}/instrutor/${action}" method="POST">

            <input type="hidden" value="${instrutor.getId()}" name="instrutorId">

            <div class="row">

                <div class="form-group col-md-4">
                    <label for="nome">Nome</label>
                    <input type="text" class="form-control"
                           id="nome" name="nome"
                           autofocus="autofocus"
                           placeholder="Nome do instrutor"
                           required value="${instrutor.getNome()}">
                </div>

                <div class="form-group col-md-4">

                    <label>Sexo</label>

                    <div>

                        <input type="radio" id="feminino" name="sexo"
                               value="F"
                               ${instrutor.getSexo() == 'F' ? "checked" : ""}
                               required>

                        <label for="feminino">Feminino</label>

                        <input type="radio" id="masculino" name="sexo"
                               value="M"
                               ${instrutor.getSexo() == 'M' ? "checked" : ""}>

                        <label for="masculino">Masculino</label>

                    </div>

                </div>

                <div class="form-group col-md-4">
                    <label for="email">E-mail</label>
                    <input type="email" class="form-control"
                           id="email" name="email"
                           placeholder="E-mail do instrutor"
                           required value="${instrutor.getEmail()}">
                </div>

            </div>

            <div class="row">

                <div class="form-group col-md-4">
                    <label for="telefone">Telefone</label>
                    <input type="text" class="form-control"
                           id="telefone" name="telefone"
                           placeholder="Telefone do instrutor"
                           required value="${instrutor.getTelefone()}">
                </div>

                <div class="form-group col-md-4">
                    <label for="cnh">CNH</label>
                    <input type="text" class="form-control"
                           id="cnh" name="cnh"
                           placeholder="Número da CNH"
                           required value="${instrutor.getCnh()}">
                </div>

                <div class="form-group col-md-4">
                    <label for="password">
                        ${not empty instrutor ? "Nova senha" : "Senha"}
                    </label>

                    <input type="password" class="form-control"
                           id="password" name="password"
                           placeholder="${not empty instrutor ? 'Deixe vazio para manter a senha atual' : 'Digite sua senha'}"
                           ${empty instrutor ? "required" : ""}>
                </div>

            </div>

            <hr />

            <div id="actions" class="row pull-right">

                <div class="col-md-12">

                    <a href="${pageContext.request.contextPath}/instrutores"
                       class="btn btn-default">
                        Cancelar
                    </a>

                    <button type="submit" class="btn btn-primary">
                        ${not empty instrutor ? "Alterar Instrutor" : "Cadastrar Instrutor"}
                    </button>

                </div>

            </div>

        </form>

    </div>

    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

</body>
</html>
