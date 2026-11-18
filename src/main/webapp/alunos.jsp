<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-br">

<head>
    <%@include file="base-head.jsp"%>
    <title>Sistema Autoescola - Alunos</title>
</head>

<body>
    <%@include file="modal.html"%>
    <%@include file="nav-menu.jsp"%>

    <div id="container" class="container-fluid">

        <div id="alert" style="${not empty message ? 'display: block;' : 'display: none;'}"
             class="alert alert-dismissable ${alertType eq 1 ? 'alert-success' : 'alert-danger'}">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            ${message}
        </div>

        <div id="top" class="row">
            <div class="col-md-3">
                <h3>Alunos</h3>
            </div>

            <div class="col-md-6">
				<form action="${pageContext.request.contextPath}/alunos" method="GET">
					<div class="input-group h2">
						<input name="pesquisa" class="form-control" id="search"
							type="text" placeholder="Pesquisar aulas"
							value="${param.pesquisa}"> <span class="input-group-btn">
							<button class="btn btn-danger" type="submit">
								<span class="glyphicon glyphicon-search"></span>
							</button>
						</span>
					</div>
				</form>
			</div>

            <div class="col-md-3">
                <a href="${pageContext.request.contextPath}/aluno/form"
                   class="btn btn-danger pull-right h2">
                    <span class="glyphicon glyphicon-plus"></span>&nbsp;Adicionar Aluno
                </a>
            </div>
        </div>

        <hr />

        <div id="list" class="row">
            <div class="table-responsive col-md-12">
                <table class="table table-striped table-hover" cellspacing="0" cellpadding="0">
                    <thead>
                        <tr>
                            <th>Nome</th>
                            <th>Data de nascimento</th>
                            <th>CPF</th>
                            <th>Categoria</th>
                            <th>Instrutor</th>
                            <th>Editar</th>
                            <th>Excluir</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="aluno" items="${alunos}">
                            <tr>
                                <td>${aluno.getNome()}</td>
                                <td>${aluno.getDataNascimento()}</td>
                                <td>${aluno.getCpf()}</td>
                                <td>${aluno.getCategoria()}</td>
                                <td>${aluno.getInstrutor().getNome()}</td>

                                <td class="actions">
                                    <a class="btn btn-info btn-xs"
                                       href="${pageContext.request.contextPath}/aluno/update?alunoId=${aluno.getId()}">
                                        <span class="glyphicon glyphicon-edit"></span>
                                    </a>
                                </td>

                                <td class="actions">
                                    <a class="btn btn-danger btn-xs modal-remove"
                                       aluno-id="${aluno.getId()}"
                                       aluno-nome="${aluno.getNome()}"
                                       data-toggle="modal"
                                       data-target="#delete-modal"
                                       href="#">
                                        <span class="glyphicon glyphicon-trash"></span>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div id="bottom" class="row">
    <div class="col-md-12">
        <ul class="pagination">

            <c:choose>
                <c:when test="${paginaAtual > 1}">
                    <li>
                        <a href="${pageContext.request.contextPath}/alunos?pagina=${paginaAtual - 1}">
                            &lt; Anterior
                        </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="disabled"><a>&lt; Anterior</a></li>
                </c:otherwise>
            </c:choose>

            <c:forEach begin="1" end="${totalPaginas}" var="p">
                <li class="${p == paginaAtual ? 'active' : ''}">
                    <a href="${pageContext.request.contextPath}/alunos?pagina=${p}">
                        ${p}
                    </a>
                </li>
            </c:forEach>

            <c:choose>
                <c:when test="${paginaAtual < totalPaginas}">
                    <li class="next">
                        <a href="${pageContext.request.contextPath}/alunos?pagina=${paginaAtual + 1}" rel="next">
                            Próximo &gt;
                        </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="disabled"><a>Próximo &gt;</a></li>
                </c:otherwise>
            </c:choose>

    </div>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function() {

            setTimeout(function() {
                $("#alert").slideUp(500);
            }, 3000);

            $(".modal-remove").click(function () {
                var alunoNome = $(this).attr('aluno-nome');
                var alunoId = $(this).attr('aluno-id');

                $(".modal-body #hiddenValue").text("o aluno '" + alunoNome + "'");
                $("#id").attr("value", alunoId);
                $("#form").attr("action", "aluno/delete");
            });

        });
    </script>

</body>
</html>