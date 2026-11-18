<jsp:directive.page contentType="text/html; charset=UTF-8" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <!DOCTYPE html>
    <html>

    <head>
        <%@include file="base-head.jsp" %>
    </head>

    <body>
        <%@include file="nav-menu.jsp" %>

            <div id="container" class="container-fluid">
                <h3 class="page-header">${not empty veiculo ? 'Atualizar' : 'Adicionar'} Veículo</h3>

                <form action="${pageContext.request.contextPath}/veiculo/${action}" method="POST">
                    <input type="hidden" value="${veiculo.getId()}" name="veiculoId">

                    <div class="row">
                        <div class="form-group col-md-6">
                            <label for="modelo">Modelo</label>
                            <input type="text" class="form-control" id="modelo" name="modelo" autofocus="autofocus"
                                placeholder="Modelo do veículo" required
                                oninvalid="this.setCustomValidity('Por favor, informe o modelo do veículo.')"
                                oninput="setCustomValidity('')" value="${veiculo.getModelo()}" />
                        </div>

                        <div class="form-group col-md-6">
                            <label for="placa">Placa</label>
                            <input type="text" class="form-control" id="placa" name="placa"
                                placeholder="Placa do veículo" required
                                oninvalid="this.setCustomValidity('Por favor, informe a placa.')"
                                oninput="setCustomValidity('')" value="${veiculo.getPlaca()}" />
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-md-4">
                            <label for="ano">Ano</label>
                            <input type="number" class="form-control" id="ano" name="ano" placeholder="Ano do veículo"
                                min="1900" max="2100" required
                                oninvalid="this.setCustomValidity('Por favor, informe o ano do veículo.')"
                                oninput="setCustomValidity('')" value="${veiculo.getAno()}" />
                        </div>

                        <div class="form-group col-md-4">
                            <label for="categoria">Categoria</label>
                            <select id="categoria" class="form-control" name="categoria" required
                                oninvalid="this.setCustomValidity('Por favor, selecione a categoria.')"
                                oninput="setCustomValidity('')">

                                <option value="" disabled ${empty veiculo ? 'selected' : '' }>
                                    Selecione uma categoria
                                </option>
                                <option value="ACC" ${not empty veiculo && veiculo.getCategoria()=='ACC' ? 'selected'
                                    : '' }>ACC</option>
                                <option value="A" ${not empty veiculo && veiculo.getCategoria()=='A' ? 'selected' : ''
                                    }>A</option>
                                <option value="B" ${not empty veiculo && veiculo.getCategoria()=='B' ? 'selected' : ''
                                    }>B</option>
                                <option value="C" ${not empty veiculo && veiculo.getCategoria()=='C' ? 'selected' : ''
                                    }>C</option>
                                <option value="D" ${not empty veiculo && veiculo.getCategoria()=='D' ? 'selected' : ''
                                    }>D</option>
                                <option value="E" ${not empty veiculo && veiculo.getCategoria()=='E' ? 'selected' : ''
                                    }>E</option>
                            </select>
                        </div>

                        <div class="form-group col-md-4">
                            <label for="dataAquisicao">Data de aquisição</label>
                            <input type="date" class="form-control" id="dataAquisicao" name="dataAquisicao" required
                                oninvalid="this.setCustomValidity('Por favor, informe a data de aquisição.')"
                                oninput="setCustomValidity('')" value="${veiculo.getDataAquisicao()}" />
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-md-6">
                            <label>Situação</label>

                                <div class="form-check">
                                    <input type="radio" class="form-check-input" id="disponivel" name="situacao"
                                        value="Disponível" required ${not empty veiculo &&
                                        veiculo.getSituacao()=='Disponível' ? 'checked' : '' }>

                                    <label class="form-check-label" for="disponivel">
                                        Disponível
                                    </label>
                                </div>

                                <div class="form-check">
                                    <input type="radio" class="form-check-input" id="manutencao" name="situacao"
                                        value="Em manutenção" required ${not empty veiculo &&
                                        veiculo.getSituacao()=='Em manutenção' ? 'checked' : '' }>

                                    <label class="form-check-label" for="manutencao">
                                        Em manutenção
                                    </label>
                                </div>
                        </div>
                    </div>

                    <div class="form-group col-md-6">
                        <label for="instrutor">Instrutor</label>
                        <select id="instrutor" class="form-control selectpicker" name="instrutor" required
                            oninvalid="this.setCustomValidity('Por favor, selecione o instrutor.')"
                            oninput="setCustomValidity('')">

                            <option value="" disabled ${empty veiculo ? 'selected' : '' }>
                                Selecione um instrutor
                            </option>

                            <c:forEach var="instrutor" items="${instrutores}">
                                <option value="${instrutor.getId()}" ${not empty veiculo &&
                                    veiculo.getInstrutor().getId()==instrutor.getId() ? 'selected' : '' }>
                                    ${instrutor.getNome()}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
            </div>

            <hr />

            <div id="actions" class="row pull-right">
                <div class="col-md-12">
                    <a href="${pageContext.request.contextPath}/veiculos" class="btn btn-default">Cancelar</a>

                    <button type="submit" class="btn btn-primary">
                        ${not empty veiculo ? 'Atualizar' : 'Cadastrar'} Veículo
                    </button>
                </div>
            </div>
            </form>
            </div>

    </body>

    </html>