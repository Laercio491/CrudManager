
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
import model.Aluno;
import model.Instrutor;
import model.dao.DAOFactory;
import model.dao.AlunoDAO;
import model.dao.InstrutorDAO;

@WebServlet(urlPatterns = { "/alunos", "/aluno/form", "/aluno/delete", "/aluno/insert", "/aluno/update" })
public class AlunosController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getRequestURI();

		switch (action) {
		case "/crud_manager/aluno/form": {
			listInstrutores(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-aluno.jsp");
			break;
		}

		case "/crud_manager/aluno/update": {
			listInstrutores(req);
			Aluno aluno = loadAluno(req);
			req.setAttribute("aluno", aluno);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-aluno.jsp");
			break;
		}

		default:
			listAlunos(req);
			ControllerUtil.transferSessionMessagesToRequest(req);
			ControllerUtil.forward(req, resp, "/alunos.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getRequestURI();

		if (action == null || action.equals("")) {
			ControllerUtil.forward(req, resp, "/index.jsp");
			return;
		}

		switch (action) {
		case "/crud_manager/aluno/delete":
			deleteAluno(req, resp);
			break;

		case "/crud_manager/aluno/insert":
			insertAluno(req, resp);
			break;

		case "/crud_manager/aluno/update":
			updateAluno(req, resp);
			break;

		default:
			System.out.println("URL inválida " + action);
			break;
		}

		ControllerUtil.redirect(resp, req.getContextPath() + "/alunos");
	}

	private Aluno loadAluno(HttpServletRequest req) {
		String alunoIdParameter = req.getParameter("alunoId");
		int alunoId = Integer.parseInt(alunoIdParameter);

		AlunoDAO dao = DAOFactory.createDAO(AlunoDAO.class);

		try {
			Aluno aluno = dao.findById(alunoId);

			if (aluno == null)
				throw new ModelException("Aluno não encontrado para alteração");

			return aluno;

		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}

		return null;
	}

	private void listAlunos(HttpServletRequest req) {
		AlunoDAO dao = DAOFactory.createDAO(AlunoDAO.class);

		int pagina = 1;
		int quantidade = 5;

		try {
			pagina = Integer.parseInt(req.getParameter("pagina"));
			if (pagina < 1)
				pagina = 1;
		} catch (Exception e) {
			pagina = 1;
		}

		try {
			
			List<Aluno> alunos = dao.listAll(pagina, quantidade);
			int totalRegistros = dao.count();
			int totalPaginas = (int) Math.ceil((double) totalRegistros / quantidade);
			String pesquisa = req.getParameter("pesquisa");

			if (pesquisa != null && !pesquisa.trim().isEmpty()) {
			    alunos = dao.search(pesquisa.trim());
			    totalRegistros = alunos.size();
			} else {
			    alunos = dao.listAll(pagina, quantidade);
			    totalRegistros = dao.count();
			}

			req.setAttribute("alunos", alunos);
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

	private void insertAluno(HttpServletRequest req, HttpServletResponse resp) {
		String nome = req.getParameter("nome");
		String dataNascimento = req.getParameter("dataNascimento");
		String cpf = req.getParameter("cpf");
		String categoria = req.getParameter("categoria");
		String instrutorId = req.getParameter("instrutor");

		Aluno aluno = new Aluno();
		aluno.setNome(nome);
		aluno.setCpf(cpf);
		aluno.setCategoria(categoria);

		try {
			aluno.setDataNascimento(java.sql.Date.valueOf(dataNascimento));

			InstrutorDAO instrutorDAO = DAOFactory.createDAO(InstrutorDAO.class);
			Instrutor instrutor = instrutorDAO.findById(Integer.parseInt(instrutorId));

			aluno.setInstrutor(instrutor);

			AlunoDAO dao = DAOFactory.createDAO(AlunoDAO.class);

			if (dao.save(aluno)) {
				ControllerUtil.sucessMessage(req, "Aluno '" + aluno.getNome() + "' salvo com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Aluno '" + aluno.getNome() + "' não pode ser salvo.");
			}

		} catch (ModelException | IllegalArgumentException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void updateAluno(HttpServletRequest req, HttpServletResponse resp) {
		Aluno aluno = loadAluno(req);

		if (aluno == null)
			return;

		String nome = req.getParameter("nome");
		String dataNascimento = req.getParameter("dataNascimento");
		String cpf = req.getParameter("cpf");
		String categoria = req.getParameter("categoria");
		String instrutorId = req.getParameter("instrutor");

		aluno.setNome(nome);
		aluno.setCpf(cpf);
		aluno.setCategoria(categoria);

		try {
			aluno.setDataNascimento(java.sql.Date.valueOf(dataNascimento));

			InstrutorDAO instrutorDAO = DAOFactory.createDAO(InstrutorDAO.class);
			Instrutor instrutor = instrutorDAO.findById(Integer.parseInt(instrutorId));

			aluno.setInstrutor(instrutor);

			AlunoDAO dao = DAOFactory.createDAO(AlunoDAO.class);

			if (dao.update(aluno)) {
				ControllerUtil.sucessMessage(req, "Aluno '" + aluno.getNome() + "' atualizado com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Aluno '" + aluno.getNome() + "' não pode ser atualizado.");
			}

		} catch (ModelException | IllegalArgumentException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void deleteAluno(HttpServletRequest req, HttpServletResponse resp) {
		String alunoIdParameter = req.getParameter("id");
		int alunoId = Integer.parseInt(alunoIdParameter);

		AlunoDAO dao = DAOFactory.createDAO(AlunoDAO.class);

		try {
			Aluno aluno = dao.findById(alunoId);

			if (aluno == null)
				throw new ModelException("Aluno não encontrado para deleção.");

			if (dao.delete(aluno)) {
				ControllerUtil.sucessMessage(req, "Aluno '" + aluno.getNome() + "' deletado com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Aluno '" + aluno.getNome() + "' não pode ser deletado.");
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
