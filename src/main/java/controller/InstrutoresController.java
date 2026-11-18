
package controller;

import java.io.IOException;
import java.util.List;
import java.sql.SQLIntegrityConstraintViolationException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.ModelException;
import model.Instrutor;
import model.dao.DAOFactory;
import model.dao.InstrutorDAO;
import model.utils.PasswordEncryptor;

@WebServlet(urlPatterns = {
    "/instrutores",
    "/instrutor/form",
    "/instrutor/delete",
    "/instrutor/insert",
    "/instrutor/update"
})
public class InstrutoresController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getRequestURI();

        switch (action) {

            case "/crud_manager/instrutor/form": {
                req.setAttribute("action", "insert");
                ControllerUtil.forward(req, resp, "/form-instrutor.jsp");
                break;
            }

            case "/crud_manager/instrutor/update": {
                Instrutor instrutor = loadInstrutor(req);
                req.setAttribute("instrutor", instrutor);
                req.setAttribute("action", "update");
                ControllerUtil.forward(req, resp, "/form-instrutor.jsp");
                break;
            }

            default:
                listInstrutores(req);
                ControllerUtil.transferSessionMessagesToRequest(req);
                ControllerUtil.forward(req, resp, "/instrutores.jsp");
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

            case "/crud_manager/instrutor/delete":
                deleteInstrutor(req, resp);
                break;

            case "/crud_manager/instrutor/insert":
                insertInstrutor(req, resp);
                break;

            case "/crud_manager/instrutor/update":
                updateInstrutor(req, resp);
                break;

            default:
                System.out.println("URL inválida " + action);
                break;
        }

        ControllerUtil.redirect(resp, req.getContextPath() + "/instrutores");
    }

    private Instrutor loadInstrutor(HttpServletRequest req) {

        String instrutorIdParameter = req.getParameter("instrutorId");
        int instrutorId = Integer.parseInt(instrutorIdParameter);

        InstrutorDAO dao = DAOFactory.createDAO(InstrutorDAO.class);

        try {

            Instrutor instrutor = dao.findById(instrutorId);

            if (instrutor == null)
                throw new ModelException("Instrutor não encontrado para alteração");

            return instrutor;

        } catch (ModelException e) {
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }

        return null;
    }


private void listInstrutores(HttpServletRequest req) {

    InstrutorDAO dao = DAOFactory.createDAO(InstrutorDAO.class);

    int pagina = 1;
    int quantidade = 5;

    if (req.getParameter("pagina") != null) {
        try {
            pagina = Integer.parseInt(req.getParameter("pagina"));

            if (pagina < 1) {
                pagina = 1;
            }

        } catch (NumberFormatException e) {
            pagina = 1;
        }
    }

    try {

        List<Instrutor> instrutores = dao.listAll(pagina, quantidade);

        int totalRegistros = dao.count();

        int totalPaginas = (int) Math.ceil(
            (double) totalRegistros / quantidade
        );
        String pesquisa = req.getParameter("pesquisa");


        if (pesquisa != null && !pesquisa.trim().isEmpty()) {
        	instrutores = dao.search(pesquisa.trim());
            totalRegistros = instrutores.size();
        } else {
        	instrutores = dao.listAll(pagina, quantidade);
            totalRegistros = dao.count();
        }

        req.setAttribute("instrutores", instrutores);
        req.setAttribute("paginaAtual", pagina);
        req.setAttribute("totalPaginas", totalPaginas);

    } catch (ModelException e) {
        e.printStackTrace();
    }
}


    private void insertInstrutor(HttpServletRequest req, HttpServletResponse resp) {

        String nome = req.getParameter("nome");
        String sexo = req.getParameter("sexo");
        String email = req.getParameter("email");
        String telefone = req.getParameter("telefone");
        String cnh = req.getParameter("cnh");
        String password = req.getParameter("password");

        Instrutor instrutor = new Instrutor();

        instrutor.setNome(nome);
        instrutor.setSexo(sexo);
        instrutor.setEmail(email);
        instrutor.setTelefone(telefone);
        instrutor.setCnh(cnh);
        instrutor.setPassword(PasswordEncryptor.hashPassword(password));

        InstrutorDAO dao = DAOFactory.createDAO(InstrutorDAO.class);

        try {

            if (dao.save(instrutor)) {
                ControllerUtil.sucessMessage(req,
                    "Instrutor '" + instrutor.getNome() + "' salvo com sucesso.");
            } else {
                ControllerUtil.errorMessage(req,
                    "Instrutor '" + instrutor.getNome() + "' não pode ser salvo.");
            }

        } catch (ModelException e) {
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }
    }

    private void updateInstrutor(HttpServletRequest req, HttpServletResponse resp) {

        String nome = req.getParameter("nome");
        String sexo = req.getParameter("sexo");
        String email = req.getParameter("email");
        String telefone = req.getParameter("telefone");
        String cnh = req.getParameter("cnh");
        String password = req.getParameter("password");

        Instrutor instrutor = loadInstrutor(req);

        if (instrutor == null)
            return;

        instrutor.setNome(nome);
        instrutor.setSexo(sexo);
        instrutor.setEmail(email);
        instrutor.setTelefone(telefone);
        instrutor.setCnh(cnh);

        if (password != null && !password.isBlank()) {
            instrutor.setPassword(PasswordEncryptor.hashPassword(password));
        }

        InstrutorDAO dao = DAOFactory.createDAO(InstrutorDAO.class);

        try {

            if (dao.update(instrutor)) {
                ControllerUtil.sucessMessage(req,
                    "Instrutor '" + instrutor.getNome() + "' atualizado com sucesso.");
            } else {
                ControllerUtil.errorMessage(req,
                    "Instrutor '" + instrutor.getNome() + "' não pode ser atualizado.");
            }

        } catch (ModelException e) {
            e.printStackTrace();
            ControllerUtil.errorMessage(req, e.getMessage());
        }
    }

    private void deleteInstrutor(HttpServletRequest req, HttpServletResponse resp) {

        String instrutorIdParameter = req.getParameter("id");
        int instrutorId = Integer.parseInt(instrutorIdParameter);

        InstrutorDAO dao = DAOFactory.createDAO(InstrutorDAO.class);

        try {

            Instrutor instrutor = dao.findById(instrutorId);

            if (instrutor == null)
                throw new ModelException("Instrutor não encontrado para deleção.");

            if (dao.delete(instrutor)) {
                ControllerUtil.sucessMessage(req,
                    "Instrutor '" + instrutor.getNome() + "' deletado com sucesso.");
            } else {
                ControllerUtil.errorMessage(req,
                    "Instrutor '" + instrutor.getNome()
                    + "' não pode ser deletado. Há alunos ou veículos relacionados ao instrutor.");
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
