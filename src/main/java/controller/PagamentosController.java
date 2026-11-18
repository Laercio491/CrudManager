package controller;

import java.io.IOException;
import java.util.List;
import java.sql.SQLIntegrityConstraintViolationException;
import java.math.BigDecimal;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.ModelException;
import model.Pagamento;
import model.Aluno;
import model.dao.DAOFactory;
import model.dao.PagamentoDAO;
import model.dao.AlunoDAO;

@WebServlet(urlPatterns = { "/pagamentos", "/pagamento/form", "/pagamento/delete", "/pagamento/insert",
		"/pagamento/update" })
public class PagamentosController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getServletPath();

		switch (action) {
		case "/pagamento/form":
			listAlunos(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-pagamento.jsp");
			break;

		case "/pagamento/update":
			listAlunos(req);
			Pagamento pagamento = loadPagamento(req);

			req.setAttribute("pagamento", pagamento);
			req.setAttribute("action", "update");

			ControllerUtil.forward(req, resp, "/form-pagamento.jsp");
			break;

		default:
			listPagamentos(req);
			ControllerUtil.transferSessionMessagesToRequest(req);
			ControllerUtil.forward(req, resp, "/pagamentos.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getServletPath();

		switch (action) {
		case "/pagamento/delete":
			deletePagamento(req, resp);
			break;

		case "/pagamento/insert":
			insertPagamento(req, resp);
			break;

		case "/pagamento/update":
			updatePagamento(req, resp);
			break;

		default:
			System.out.println("URL inválida " + action);
			break;
		}

		ControllerUtil.redirect(resp, req.getContextPath() + "/pagamentos");
	}

	private Pagamento loadPagamento(HttpServletRequest req) {

		int pagamentoId = Integer.parseInt(req.getParameter("pagamentoId"));

		PagamentoDAO dao = DAOFactory.createDAO(PagamentoDAO.class);

		try {
			Pagamento pagamento = dao.findById(pagamentoId);

			if (pagamento == null)
				throw new ModelException("Pagamento não encontrado para alteração.");

			return pagamento;

		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}

		return null;
	}

	private void listPagamentos(HttpServletRequest req) {
		PagamentoDAO dao = DAOFactory.createDAO(PagamentoDAO.class);

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
			List<Pagamento> pagamentos = dao.listAll(pagina, quantidade);
			int totalRegistros = dao.count();
			int totalPaginas = (int) Math.ceil((double) totalRegistros / quantidade);
			
			String pesquisa = req.getParameter("pesquisa");


	        if (pesquisa != null && !pesquisa.trim().isEmpty()) {
	        	pagamentos = dao.search(pesquisa.trim());
	            totalRegistros = pagamentos.size();
	        } else {
	        	pagamentos = dao.listAll(pagina, quantidade);
	        }
	           

			req.setAttribute("pagamentos", pagamentos);
			req.setAttribute("paginaAtual", pagina);
			req.setAttribute("totalPaginas", totalPaginas);

		} catch (ModelException e) {
			e.printStackTrace();
		}
	}

	private void listAlunos(HttpServletRequest req) {

		AlunoDAO dao = DAOFactory.createDAO(AlunoDAO.class);

		try {
			List<Aluno> alunos = dao.listAll();

			if (alunos != null)
				req.setAttribute("alunos", alunos);

		} catch (ModelException e) {
			e.printStackTrace();
		}
	}

	private void insertPagamento(HttpServletRequest req, HttpServletResponse resp) {

		try {
			BigDecimal valor = new BigDecimal(req.getParameter("valor"));
			java.sql.Date dataPagamento = java.sql.Date.valueOf(req.getParameter("dataPagamento"));

			String formaPagamento = req.getParameter("formaPagamento");
			String status = req.getParameter("status");
			String descricao = req.getParameter("descricao");

			int numeroParcelas = Integer.parseInt(req.getParameter("numeroParcelas"));
			int alunoId = Integer.parseInt(req.getParameter("aluno"));

			AlunoDAO alunoDAO = DAOFactory.createDAO(AlunoDAO.class);
			Aluno aluno = alunoDAO.findById(alunoId);

			if (aluno == null)
				throw new ModelException("Aluno não encontrado.");

			Pagamento pagamento = new Pagamento();
			pagamento.setValor(valor);
			pagamento.setDataPagamento(dataPagamento);
			pagamento.setFormaPagamento(formaPagamento);
			pagamento.setStatus(status);
			pagamento.setDescricao(descricao);
			pagamento.setNumeroParcelas(numeroParcelas);
			pagamento.setAluno(aluno);

			PagamentoDAO dao = DAOFactory.createDAO(PagamentoDAO.class);

			if (dao.save(pagamento)) {
				ControllerUtil.sucessMessage(req, "Pagamento cadastrado com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Não foi possível cadastrar o pagamento.");
			}

		} catch (ModelException | IllegalArgumentException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void updatePagamento(HttpServletRequest req, HttpServletResponse resp) {

		Pagamento pagamento = loadPagamento(req);

		if (pagamento == null)
			return;

		try {
			BigDecimal valor = new BigDecimal(req.getParameter("valor"));
			java.sql.Date dataPagamento = java.sql.Date.valueOf(req.getParameter("dataPagamento"));

			int alunoId = Integer.parseInt(req.getParameter("aluno"));

			AlunoDAO alunoDAO = DAOFactory.createDAO(AlunoDAO.class);
			Aluno aluno = alunoDAO.findById(alunoId);

			if (aluno == null)
				throw new ModelException("Aluno não encontrado.");

			pagamento.setValor(valor);
			pagamento.setDataPagamento(dataPagamento);
			pagamento.setFormaPagamento(req.getParameter("formaPagamento"));
			pagamento.setStatus(req.getParameter("status"));
			pagamento.setDescricao(req.getParameter("descricao"));
			pagamento.setNumeroParcelas(Integer.parseInt(req.getParameter("numeroParcelas")));
			pagamento.setAluno(aluno);

			PagamentoDAO dao = DAOFactory.createDAO(PagamentoDAO.class);

			if (dao.update(pagamento)) {
				ControllerUtil.sucessMessage(req, "Pagamento atualizado com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Não foi possível atualizar o pagamento.");
			}

		} catch (ModelException | IllegalArgumentException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void deletePagamento(HttpServletRequest req, HttpServletResponse resp) {

		int pagamentoId = Integer.parseInt(req.getParameter("id"));

		PagamentoDAO dao = DAOFactory.createDAO(PagamentoDAO.class);

		try {
			Pagamento pagamento = dao.findById(pagamentoId);

			if (pagamento == null)
				throw new ModelException("Pagamento não encontrado para deleção.");

			if (dao.delete(pagamento)) {
				ControllerUtil.sucessMessage(req, "Pagamento deletado com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Não foi possível deletar o pagamento.");
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
