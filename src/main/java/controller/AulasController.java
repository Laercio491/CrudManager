package controller;

import java.io.IOException;
import java.util.List;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Time;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.ModelException;
import model.Aula;
import model.Aluno;
import model.Instrutor;
import model.Veiculo;
import model.dao.DAOFactory;
import model.dao.AulaDAO;
import model.dao.AlunoDAO;
import model.dao.InstrutorDAO;
import model.dao.VeiculoDAO;

@WebServlet(urlPatterns = { "/aulas", "/aula/form", "/aula/delete", "/aula/insert", "/aula/update" })
public class AulasController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getServletPath();

		switch (action) {
		case "/aula/form":
			listRelacionamentos(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-aula.jsp");
			break;

		case "/aula/update":
			listRelacionamentos(req);
			Aula aula = loadAula(req);

			req.setAttribute("aula", aula);
			req.setAttribute("action", "update");

			ControllerUtil.forward(req, resp, "/form-aula.jsp");
			break;

		default:
			listAulas(req);
			ControllerUtil.transferSessionMessagesToRequest(req);
			ControllerUtil.forward(req, resp, "/aulas.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getServletPath();

		switch (action) {
		case "/aula/delete":
			deleteAula(req, resp);
			break;

		case "/aula/insert":
			insertAula(req, resp);
			break;

		case "/aula/update":
			updateAula(req, resp);
			break;

		default:
			System.out.println("URL inválida " + action);
			break;
		}

		ControllerUtil.redirect(resp, req.getContextPath() + "/aulas");
	}

	private Aula loadAula(HttpServletRequest req) {

		int aulaId = Integer.parseInt(req.getParameter("aulaId"));

		AulaDAO dao = DAOFactory.createDAO(AulaDAO.class);

		try {
			Aula aula = dao.findById(aulaId);

			if (aula == null)
				throw new ModelException("Aula não encontrada para alteração.");

			return aula;

		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}

		return null;
	}

	private void listAulas(HttpServletRequest req) {
    AulaDAO dao = DAOFactory.createDAO(AulaDAO.class);

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
        String pesquisa = req.getParameter("pesquisa");

        List<Aula> aulas;
        int totalRegistros;

        if (pesquisa != null && !pesquisa.trim().isEmpty()) {
            aulas = dao.searchByAluno(pesquisa.trim());
            totalRegistros = aulas.size();
        } else {
            aulas = dao.listAll(pagina, quantidade);
            totalRegistros = dao.count();
        }

        int totalPaginas = (int) Math.ceil((double) totalRegistros / quantidade);

        req.setAttribute("aulas", aulas);
        req.setAttribute("paginaAtual", pagina);
        req.setAttribute("totalPaginas", totalPaginas);
        req.setAttribute("pesquisa", pesquisa);

    } catch (ModelException e) {
        e.printStackTrace();
    }
}
	private void listRelacionamentos(HttpServletRequest req) {

		AlunoDAO alunoDAO = DAOFactory.createDAO(AlunoDAO.class);
		InstrutorDAO instrutorDAO = DAOFactory.createDAO(InstrutorDAO.class);
		VeiculoDAO veiculoDAO = DAOFactory.createDAO(VeiculoDAO.class);

		try {
			List<Aluno> alunos = alunoDAO.listAll();
			List<Instrutor> instrutores = instrutorDAO.listAll();
			List<Veiculo> veiculos = veiculoDAO.listAll();

			req.setAttribute("alunos", alunos);
			req.setAttribute("instrutores", instrutores);
			req.setAttribute("veiculos", veiculos);

		} catch (ModelException e) {
			e.printStackTrace();
		}
	}

	private void insertAula(HttpServletRequest req, HttpServletResponse resp) {

		String dataAula = req.getParameter("dataAula");
		String horaInicio = req.getParameter("horaInicio");
		String duracao = req.getParameter("duracaoMinutos");
		String status = req.getParameter("status");
		String observacoes = req.getParameter("observacoes");

		String alunoId = req.getParameter("aluno");
		String instrutorId = req.getParameter("instrutor");
		String veiculoId = req.getParameter("veiculo");

		try {
			AlunoDAO alunoDAO = DAOFactory.createDAO(AlunoDAO.class);
			InstrutorDAO instrutorDAO = DAOFactory.createDAO(InstrutorDAO.class);
			VeiculoDAO veiculoDAO = DAOFactory.createDAO(VeiculoDAO.class);

			Aluno aluno = alunoDAO.findById(Integer.parseInt(alunoId));
			Instrutor instrutor = instrutorDAO.findById(Integer.parseInt(instrutorId));
			Veiculo veiculo = veiculoDAO.findById(Integer.parseInt(veiculoId));

			if (aluno == null || instrutor == null || veiculo == null)
				throw new ModelException("Aluno, instrutor ou veículo não encontrado.");

			Aula aula = new Aula();
			aula.setDataAula(java.sql.Date.valueOf(dataAula));
			String horario = req.getParameter("horaInicio");

			Time horaIni = Time.valueOf(horario.length() == 5 ? horario + ":00" : horario);
			aula.setHoraInicio(horaIni);
			aula.setDuracaoMinutos(Integer.parseInt(duracao));
			aula.setStatus(status);
			aula.setObservacoes(observacoes);
			aula.setAluno(aluno);
			aula.setInstrutor(instrutor);
			aula.setVeiculo(veiculo);

			AulaDAO dao = DAOFactory.createDAO(AulaDAO.class);

			if (dao.save(aula)) {
				ControllerUtil.sucessMessage(req, "Aula cadastrada com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Não foi possível cadastrar a aula.");
			}

		} catch (ModelException | IllegalArgumentException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void updateAula(HttpServletRequest req, HttpServletResponse resp) {

		Aula aula = loadAula(req);

		if (aula == null)
			return;

		try {
			AlunoDAO alunoDAO = DAOFactory.createDAO(AlunoDAO.class);
			InstrutorDAO instrutorDAO = DAOFactory.createDAO(InstrutorDAO.class);
			VeiculoDAO veiculoDAO = DAOFactory.createDAO(VeiculoDAO.class);

			Aluno aluno = alunoDAO.findById(Integer.parseInt(req.getParameter("aluno")));
			Instrutor instrutor = instrutorDAO.findById(Integer.parseInt(req.getParameter("instrutor")));
			Veiculo veiculo = veiculoDAO.findById(Integer.parseInt(req.getParameter("veiculo")));

			if (aluno == null || instrutor == null || veiculo == null)
				throw new ModelException("Aluno, instrutor ou veículo não encontrado.");

			aula.setDataAula(java.sql.Date.valueOf(req.getParameter("dataAula")));
			String horario = req.getParameter("horaInicio");

			Time horaIni = Time.valueOf(
			    horario.length() == 5 ? horario + ":00" : horario
			);

			aula.setHoraInicio(horaIni);
			aula.setDuracaoMinutos(Integer.parseInt(req.getParameter("duracaoMinutos")));
			aula.setStatus(req.getParameter("status"));
			aula.setObservacoes(req.getParameter("observacoes"));
			aula.setAluno(aluno);
			aula.setInstrutor(instrutor);
			aula.setVeiculo(veiculo);

			AulaDAO dao = DAOFactory.createDAO(AulaDAO.class);

			if (dao.update(aula)) {
				ControllerUtil.sucessMessage(req, "Aula atualizada com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Não foi possível atualizar a aula.");
			}

		} catch (ModelException | IllegalArgumentException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void deleteAula(HttpServletRequest req, HttpServletResponse resp) {

		int aulaId = Integer.parseInt(req.getParameter("id"));

		AulaDAO dao = DAOFactory.createDAO(AulaDAO.class);

		try {
			Aula aula = dao.findById(aulaId);

			if (aula == null)
				throw new ModelException("Aula não encontrada para deleção.");

			if (dao.delete(aula)) {
				ControllerUtil.sucessMessage(req, "Aula deletada com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Não foi possível deletar a aula.");
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
