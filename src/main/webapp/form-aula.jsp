<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <!DOCTYPE html>
        <html lang="pt-br">

        <head>
            <%@include file="base-head.jsp" %>
        </head>

        <body>
            <%@include file="nav-menu.jsp" %>

                <div id="container" class="container-fluid">
                    <h3 class="page-header">
                        ${not empty aula ? "Alterar Aula" : "Adicionar Aula"}
                    </h3>

                    <form action="${pageContext.request.contextPath}/aula/${action}" method="POST">
                        <input type="hidden" value="${aula.getId()}" name="aulaId">

                        <div class="row">
                            <div class="form-group col-md-6">
                                <label for="dataAula">Data da aula</label>
                                <input type="date" class="form-control" id="dataAula" name="dataAula" required
                                    oninvalid="this.setCustomValidity('Por favor, informe a data da aula.')"
                                    oninput="setCustomValidity('')" value="${aula.getDataAula()}">
                            </div>

                            <div class="form-group col-md-6">
                                <label for="horaInicio">Horário de início</label>
                                <input type="time" class="form-control" id="horaInicio" name="horaInicio" required
                                    oninvalid="this.setCustomValidity('Por favor, informe o horário de início.')"
                                    oninput="setCustomValidity('')" value="${aula.getHoraInicio()}">
                            </div>
                        </div>

                        <div class="row">
                            <div class="form-group col-md-6">
                                <label for="duracaoMinutos">Duração (minutos)</label>
                                <input type="number" class="form-control" id="duracaoMinutos" name="duracaoMinutos"
                                    min="1" placeholder="Duração da aula" required
                                    oninvalid="this.setCustomValidity('Por favor, informe a duração da aula.')"
                                    oninput="setCustomValidity('')" value="${aula.getDuracaoMinutos()}">
                            </div>

                            <div class="form-group col-md-6">
                                <label>Status</label>

                                <div class="form-check">
                                    <input class="form-check-input" type="radio" id="statusAgendada" name="status"
                                        value="Agendada" required ${aula.getStatus()=='Agendada' ? "checked" : "" }>
                                    <label class="form-check-label" for="statusAgendada">Agendada</label>
                                </div>

                                <div class="form-check">
                                    <input class="form-check-input" type="radio" id="statusRealizada" name="status"
                                        value="Realizada" ${aula.getStatus()=='Realizada' ? "checked" : "" }>
                                    <label class="form-check-label" for="statusRealizada">Realizada</label>
                                </div>

                                <div class="form-check">
                                    <input class="form-check-input" type="radio" id="statusCancelada" name="status"
                                        value="Cancelada" ${aula.getStatus()=='Cancelada' ? "checked" : "" }>
                                    <label class="form-check-label" for="statusCancelada">Cancelada</label>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="form-group col-md-12">
                                <label for="observacoes">Observações</label>
                                <textarea class="form-control" id="observacoes" name="observacoes"
                                    placeholder="Observações sobre a aula">${aula.getObservacoes()}</textarea>
                            </div>
                        </div>

                        <div class="row">
                            <div class="form-group col-md-6">
                                <label for="aluno">Aluno</label>
                                <select id="aluno" class="form-control" name="aluno" required
                                    oninvalid="this.setCustomValidity('Por favor, selecione um aluno.')"
                                    oninput="setCustomValidity('')">
                                    <option value="" disabled ${empty aula ? "selected" : "" }>Selecione um aluno
                                    </option>
                                    <c:forEach var="aluno" items="${alunos}">
                                        <option value="${aluno.getId()}" ${not empty aula &&
                                            aula.getAluno().getId()==aluno.getId() ? "selected" : "" }>
                                            ${aluno.getNome()}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group col-md-6">
                                <label for="instrutor">Instrutor</label>
                                <select id="instrutor" class="form-control" name="instrutor" required
                                    oninvalid="this.setCustomValidity('Por favor, selecione um instrutor.')"
                                    oninput="setCustomValidity('')">
                                    <option value="" disabled ${empty aula ? "selected" : "" }>Selecione um instrutor
                                    </option>
                                    <c:forEach var="instrutor" items="${instrutores}">
                                        <option value="${instrutor.getId()}" ${not empty aula &&
                                            aula.getInstrutor().getId()==instrutor.getId() ? "selected" : "" }>
                                            ${instrutor.getNome()}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="row">
                            <div class="form-group col-md-6">
                                <label for="veiculo">Veículo</label>
                                <select id="veiculo" class="form-control" name="veiculo" required
                                    oninvalid="this.setCustomValidity('Por favor, selecione um veículo.')"
                                    oninput="setCustomValidity('')">
                                    <option value="" disabled ${empty aula ? "selected" : "" }>Selecione um veículo
                                    </option>
                                    <c:forEach var="veiculo" items="${veiculos}">
                                        <option value="${veiculo.getId()}" ${not empty aula &&
                                            aula.getVeiculo().getId()==veiculo.getId() ? "selected" : "" }>
                                            ${veiculo.getModelo()} - ${veiculo.getPlaca()}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <hr />

                        <div id="actions" class="row pull-right">
                            <div class="col-md-12">
                                <a href="${pageContext.request.contextPath}/aulas" class="btn btn-default">Cancelar</a>
                                <button type="submit" class="btn btn-primary">
                                    ${not empty aula ? "Alterar Aula" : "Criar Aula"}
                                </button>
                            </div>
                        </div>
                    </form>
                </div>

                <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
                <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        </body>

        </html>