<jsp:directive.page contentType="text/html; charset=UTF-8" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <%@include file="base-head.jsp"%>
    </head>
    <body>
        <%@include file="nav-menu.jsp"%>

        <div id="container" class="container-fluid">
            <h3 class="page-header">${not empty aluno ? "Alterar Aluno" : "Adicionar Aluno"}</h3>

            <form action="${pageContext.request.contextPath}/aluno/${action}" method="POST">
                <input type="hidden" value="${aluno.getId()}" name="alunoId">

                <div class="row">
                    <div class="form-group col-md-6">
                        <label for="nome">Nome</label>
                        <input type="text" class="form-control" id="nome" name="nome"
                               autofocus="autofocus" placeholder="Nome do aluno"
                               required oninvalid="this.setCustomValidity('Por favor, informe o nome do aluno.')"
                               oninput="setCustomValidity('')"
                               value="${aluno.getNome()}">
                    </div>

                    <div class="form-group col-md-6">
                        <label for="dataNascimento">Data de nascimento</label>
                        <input type="date" class="form-control" id="dataNascimento" name="dataNascimento"
                               required oninvalid="this.setCustomValidity('Por favor, informe a data de nascimento.')"
                               oninput="setCustomValidity('')"
                               value="${aluno.getDataNascimento()}">
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-6">
                        <label for="cpf">CPF</label>
                        <input type="text" class="form-control" id="cpf" name="cpf"
                               placeholder="CPF do aluno"
                               required oninvalid="this.setCustomValidity('Por favor, informe o CPF.')"
                               oninput="setCustomValidity('')"
                               value="${aluno.getCpf()}">
                    </div>

                    <div class="form-group col-md-6">
                        <label for="categoria">Categoria</label>
                        <select id="categoria" class="form-control" name="categoria" required
                                oninvalid="this.setCustomValidity('Por favor, selecione a categoria.')"
                                oninput="setCustomValidity('')">
                            <option value="" disabled ${empty aluno ? "selected" : ""}>Selecione a categoria</option>
                            <option value="ACC" ${aluno.getCategoria() == 'ACC' ? "selected" : ""}>ACC</option>
                            <option value="A" ${aluno.getCategoria() == 'A' ? "selected" : ""}>A</option>
                            <option value="B" ${aluno.getCategoria() == 'B' ? "selected" : ""}>B</option>
                            <option value="AB" ${aluno.getCategoria() == 'AB' ? "selected" : ""}>AB</option>
                            <option value="C" ${aluno.getCategoria() == 'C' ? "selected" : ""}>C</option>
                            <option value="D" ${aluno.getCategoria() == 'D' ? "selected" : ""}>D</option>
                            <option value="E" ${aluno.getCategoria() == 'E' ? "selected" : ""}>E</option>
                        </select>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-6">
                        <label for="instrutor">Instrutor</label>
                        <select id="instrutor" class="form-control" name="instrutor" required
                                oninvalid="this.setCustomValidity('Por favor, selecione um instrutor.')"
                                oninput="setCustomValidity('')">
                            <option value="" disabled ${empty aluno ? "selected" : ""}>Selecione um instrutor</option>
                            <c:forEach var="instrutor" items="${instrutores}">
                                <option value="${instrutor.getId()}"
                                    ${not empty aluno && aluno.getInstrutor().getId() == instrutor.getId() ? "selected" : ""}>
                                    ${instrutor.getNome()}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <hr />

                <div id="actions" class="row pull-right">
                    <div class="col-md-12">
                        <a href="${pageContext.request.contextPath}/alunos" class="btn btn-default">Cancelar</a>
                        <button type="submit" class="btn btn-primary">
                            ${not empty aluno ? "Alterar Aluno" : "Criar Aluno"}
                        </button>
                    </div>
                </div>
            </form>
        </div>

        <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>