<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <%@include file="base-head.jsp"%>
    </head>
    <body>
        <%@include file="nav-menu.jsp"%>

        <div id="container" class="container-fluid">
            <h3 class="page-header">
                ${not empty pagamento ? "Alterar Pagamento" : "Adicionar Pagamento"}
            </h3>

            <form action="${pageContext.request.contextPath}/pagamento/${action}" method="POST">
                <input type="hidden" value="${pagamento.getId()}" name="pagamentoId">

                <div class="row">
                    <div class="form-group col-md-6">
                        <label for="valor">Valor (R$)</label>
                        <input type="number" step="0.01" min="0.01"
                               class="form-control" id="valor" name="valor"
                               placeholder="Valor do pagamento"
                               required
                               oninvalid="this.setCustomValidity('Por favor, informe o valor do pagamento.')"
                               oninput="setCustomValidity('')"
                               value="${pagamento.getValor()}">
                    </div>

                    <div class="form-group col-md-6">
                        <label for="dataPagamento">Data do pagamento</label>
                        <input type="date" class="form-control" id="dataPagamento" name="dataPagamento"
                               required
                               oninvalid="this.setCustomValidity('Por favor, informe a data do pagamento.')"
                               oninput="setCustomValidity('')"
                               value="${pagamento.getDataPagamento()}">
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-6">
                        <label>Forma de pagamento</label>
                        <div class="radio">
                            <label>
                                <input type="radio" name="formaPagamento" value="Dinheiro"
                                       required ${pagamento.getFormaPagamento() == 'Dinheiro' ? "checked" : ""}>
                                Dinheiro
                            </label>
                        </div>
                        <div class="radio">
                            <label>
                                <input type="radio" name="formaPagamento" value="Pix"
                                       ${pagamento.getFormaPagamento() == 'Pix' ? "checked" : ""}>
                                Pix
                            </label>
                        </div>
                        <div class="radio">
                            <label>
                                <input type="radio" name="formaPagamento" value="Cartão"
                                       ${pagamento.getFormaPagamento() == 'Cartão' ? "checked" : ""}>
                                Cartão
                            </label>
                        </div>
                        <div class="radio">
                            <label>
                                <input type="radio" name="formaPagamento" value="Transferência"
                                       ${pagamento.getFormaPagamento() == 'Transferência' ? "checked" : ""}>
                                Transferência
                            </label>
                        </div>
                    </div>

                    <div class="form-group col-md-6">
                        <label>Status</label>
                        <div class="radio">
                            <label>
                                <input type="radio" name="status" value="Pendente"
                                       required ${pagamento.getStatus() == 'Pendente' ? "checked" : ""}>
                                Pendente
                            </label>
                        </div>
                        <div class="radio">
                            <label>
                                <input type="radio" name="status" value="Pago"
                                       ${pagamento.getStatus() == 'Pago' ? "checked" : ""}>
                                Pago
                            </label>
                        </div>
                        <div class="radio">
                            <label>
                                <input type="radio" name="status" value="Cancelado"
                                       ${pagamento.getStatus() == 'Cancelado' ? "checked" : ""}>
                                Cancelado
                            </label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-6">
                        <label for="numeroParcelas">Número de parcelas</label>
                        <input type="number" class="form-control" id="numeroParcelas" name="numeroParcelas"
                               min="1" placeholder="Quantidade de parcelas"
                               required
                               oninvalid="this.setCustomValidity('Por favor, informe o número de parcelas.')"
                               oninput="setCustomValidity('')"
                               value="${pagamento.getNumeroParcelas()}">
                    </div>

                    <div class="form-group col-md-6">
                        <label for="aluno">Aluno</label>
                        <select id="aluno" class="form-control" name="aluno" required
                                oninvalid="this.setCustomValidity('Por favor, selecione um aluno.')"
                                oninput="setCustomValidity('')">
                            <option value="" disabled ${empty pagamento ? "selected" : ""}>Selecione um aluno</option>
                            <c:forEach var="aluno" items="${alunos}">
                                <option value="${aluno.getId()}"
                                    ${not empty pagamento && pagamento.getAluno().getId() == aluno.getId() ? "selected" : ""}>
                                    ${aluno.getNome()}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-12">
                        <label for="descricao">Descrição</label>
                        <textarea class="form-control" id="descricao" name="descricao"
                                  placeholder="Descrição do pagamento">${pagamento.getDescricao()}</textarea>
                    </div>
                </div>

                <hr />

                <div id="actions" class="row pull-right">
                    <div class="col-md-12">
                        <a href="${pageContext.request.contextPath}/pagamentos" class="btn btn-default">Cancelar</a>
                        <button type="submit" class="btn btn-primary">
                            ${not empty pagamento ? "Alterar Pagamento" : "Criar Pagamento"}
                        </button>
                    </div>
                </div>
            </form>
        </div>

        <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
