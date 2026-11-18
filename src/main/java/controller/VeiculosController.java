
package controller;

import java.io.IOException;
import java.util.List;
import java.sql.SQLIntegrityConstraintViolationException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.ModelException;
import model.Veiculo;
import model.Instrutor;
import model.dao.DAOFactory;
import model.dao.VeiculoDAO;
import model.dao.InstrutorDAO;

@WebServlet(urlPatterns = {
    "/veiculos",
    "/veiculo/form",
    "/veiculo/delete",
    "/veiculo/insert",
    "/veiculo/update"
})
public class VeiculosController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getRequestURI();

        switch (action) {
            case "/crud_manager/veiculo/form": {
                listInstrutores(req);
                req.setAttribute("action", "insert");
                ControllerUtil.forward(req, resp, "/form-veiculo.jsp");
                break;
            }

            case "/crud_manager/veiculo/update": {
                listInstrutores(req);
                Veiculo veiculo = loadVeiculo(req);
                req.setAttribute("veiculo", veiculo);
                req.setAttribute("action", "update");
                ControllerUtil.forward(req, resp, "/form-veiculo.jsp");
                break;
            }

            default:
                listVeiculos(req);
                ControllerUtil.transferSessionMessagesToRequest(req);
                ControllerUtil.forward(req, resp, "/veiculos.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getRequestURI();

        if (action == null || action.equals("")) {
            ControllerUtil.forward(req, resp, "/index.jsp");
            return;
        }

        switch (action) {
            case "/crud_manager/veiculo/delete":
                deleteVeiculo(req, resp);
                break;

            case "/crud_manager/veiculo/insert":
                insertVeiculo(req, resp);
                break;

            case "/crud_manager/veiculo/update":
                updateVeiculo(req, resp);
                break;

            default:
                System.out.println("URL inválida " + action);
                break;
        }

        ControllerUtil.redirect(resp, req.getContextPath() + "/veiculos");
    }

    private Veiculo loadVeiculo(HttpServletRequest req) {
        String veiculoIdParameter = req.getParameter("veiculoId");
        int veiculoId = Integer.parseInt(veiculoIdParameter);

        VeiculoDAO dao = DAOFactory.createDAO(VeiculoDAO.class);

        try {
            Veiculo veiculo = dao.findById(veiculoId);

            if (veiculo == null)
                throw new ModelException("Veículo não encontrado para alteração");

            return veiculo;

        } catch (ModelException e) {
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }

        return null;
    }

    private void listVeiculos(HttpServletRequest req) {
        VeiculoDAO dao = DAOFactory.createDAO(VeiculoDAO.class);

        int pagina = 1;
        int quantidade = 5;

        try {
            pagina = Integer.parseInt(req.getParameter("pagina"));
            if (pagina < 1) pagina = 1;
        } catch (Exception e) {
            pagina = 1;
        }

        try {
            List<Veiculo> veiculos = dao.listAll(pagina, quantidade);
            int totalRegistros = dao.count();
            int totalPaginas = (int) Math.ceil((double) totalRegistros / quantidade);
            
            String pesquisa = req.getParameter("pesquisa");


	        if (pesquisa != null && !pesquisa.trim().isEmpty()) {
	        	veiculos = dao.search(pesquisa.trim());
	            totalRegistros = veiculos.size();
	        } else {
	        	veiculos = dao.listAll(pagina, quantidade);
	        }

            req.setAttribute("veiculos", veiculos);
            req.setAttribute("paginaAtual", pagina);
            req.setAttribute("totalPaginas", totalPaginas);

        } catch (ModelException e) {
            e.printStackTrace();
        }
    }

    private void listInstrutores(HttpServletRequest req) {
        InstrutorDAO dao = DAOFactory.createDAO(InstrutorDAO.class);

        List<Instrutor> instrutores = null;

        try {
            instrutores = dao.listAll();
        } catch (ModelException e) {
            e.printStackTrace();
        }

        if (instrutores != null)
            req.setAttribute("instrutores", instrutores);
    }

    private void insertVeiculo(HttpServletRequest req, HttpServletResponse resp) {
        String modelo = req.getParameter("modelo");
        String placa = req.getParameter("placa");
        String ano = req.getParameter("ano");
        String categoria = req.getParameter("categoria");
        String dataAquisicao = req.getParameter("dataAquisicao");
        String situacao = req.getParameter("situacao");
        String instrutorId = req.getParameter("instrutor");

        Veiculo veiculo = new Veiculo();

        veiculo.setModelo(modelo);
        veiculo.setPlaca(placa);
        veiculo.setAno(Integer.parseInt(ano));
        veiculo.setCategoria(categoria);
        veiculo.setSituacao(situacao);

        try {
            veiculo.setDataAquisicao(
                java.sql.Date.valueOf(dataAquisicao)
            );

            InstrutorDAO instrutorDAO = DAOFactory.createDAO(InstrutorDAO.class);
            Instrutor instrutor = instrutorDAO.findById(
                Integer.parseInt(instrutorId)
            );

            if (instrutor == null)
                throw new ModelException("Instrutor não encontrado.");

            veiculo.setInstrutor(instrutor);

            VeiculoDAO dao = DAOFactory.createDAO(VeiculoDAO.class);

            if (dao.save(veiculo)) {
                ControllerUtil.sucessMessage(req,
                    "Veículo '" + veiculo.getModelo() + "' salvo com sucesso.");
            } else {
                ControllerUtil.errorMessage(req,
                    "Veículo '" + veiculo.getModelo() + "' não pode ser salvo.");
            }

        } catch (ModelException | IllegalArgumentException e) {
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }
    }

    private void updateVeiculo(HttpServletRequest req, HttpServletResponse resp) {
        Veiculo veiculo = loadVeiculo(req);

        if (veiculo == null)
            return;

        String modelo = req.getParameter("modelo");
        String placa = req.getParameter("placa");
        String ano = req.getParameter("ano");
        String categoria = req.getParameter("categoria");
        String dataAquisicao = req.getParameter("dataAquisicao");
        String situacao = req.getParameter("situacao");
        String instrutorId = req.getParameter("instrutor");

        veiculo.setModelo(modelo);
        veiculo.setPlaca(placa);
        veiculo.setAno(Integer.parseInt(ano));
        veiculo.setCategoria(categoria);
        veiculo.setSituacao(situacao);

        try {
            veiculo.setDataAquisicao(
                java.sql.Date.valueOf(dataAquisicao)
            );

            InstrutorDAO instrutorDAO = DAOFactory.createDAO(InstrutorDAO.class);
            Instrutor instrutor = instrutorDAO.findById(
                Integer.parseInt(instrutorId)
            );

            if (instrutor == null)
                throw new ModelException("Instrutor não encontrado.");

            veiculo.setInstrutor(instrutor);

            VeiculoDAO dao = DAOFactory.createDAO(VeiculoDAO.class);

            if (dao.update(veiculo)) {
                ControllerUtil.sucessMessage(req,
                    "Veículo '" + veiculo.getModelo() + "' atualizado com sucesso.");
            } else {
                ControllerUtil.errorMessage(req,
                    "Veículo '" + veiculo.getModelo() + "' não pode ser atualizado.");
            }

        } catch (ModelException | IllegalArgumentException e) {
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }
    }

    private void deleteVeiculo(HttpServletRequest req, HttpServletResponse resp) {
        String veiculoIdParameter = req.getParameter("id");
        int veiculoId = Integer.parseInt(veiculoIdParameter);

        VeiculoDAO dao = DAOFactory.createDAO(VeiculoDAO.class);

        try {
            Veiculo veiculo = dao.findById(veiculoId);

            if (veiculo == null)
                throw new ModelException("Veículo não encontrado para deleção.");

            if (dao.delete(veiculo)) {
                ControllerUtil.sucessMessage(req,
                    "Veículo '" + veiculo.getModelo() + "' deletado com sucesso.");
            } else {
                ControllerUtil.errorMessage(req,
                    "Veículo '" + veiculo.getModelo()
                    + "' não pode ser deletado. Há dados relacionados ao veículo.");
            }

        } catch (ModelException e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                ControllerUtil.errorMessage(req, e.getMessage());
            }

            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }
    }
}
