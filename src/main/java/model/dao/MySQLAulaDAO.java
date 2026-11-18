package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.Aula;
import model.Aluno;
import model.Instrutor;
import model.Veiculo;

public class MySQLAulaDAO implements AulaDAO {

    @Override
    public boolean save(Aula aula) throws ModelException {

        DBHandler db = new DBHandler();

        String sqlInsert = "INSERT INTO aulas VALUES "
                + " (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?);";

        db.prepareStatement(sqlInsert);
        db.setDate(1, aula.getDataAula());
        db.setTime(2, aula.getHoraInicio());
        db.setInt(3, aula.getDuracaoMinutos());
        db.setString(4, aula.getStatus());
        db.setString(5, aula.getObservacoes());
        db.setInt(6, aula.getAluno().getId());
        db.setInt(7, aula.getInstrutor().getId());
        db.setInt(8, aula.getVeiculo().getId());

        return db.executeUpdate() > 0;
    }

    @Override
    public boolean update(Aula aula) throws ModelException {

        DBHandler db = new DBHandler();

        String sqlUpdate = "UPDATE aulas "
                + " SET data_aula = ?, "
                + " hora_inicio = ?, "
                + " duracao_minutos = ?, "
                + " status = ?, "
                + " observacoes = ?, "
                + " aluno_id = ?, "
                + " instrutor_id = ?, "
                + " veiculo_id = ? "
                + " WHERE id = ?";

        db.prepareStatement(sqlUpdate);

        db.setDate(1, aula.getDataAula());
        db.setTime(2, aula.getHoraInicio());
        db.setInt(3, aula.getDuracaoMinutos());
        db.setString(4, aula.getStatus());
        db.setString(5, aula.getObservacoes());
        db.setInt(6, aula.getAluno().getId());
        db.setInt(7, aula.getInstrutor().getId());
        db.setInt(8, aula.getVeiculo().getId());
        db.setInt(9, aula.getId());

        return db.executeUpdate() > 0;
    }

    @Override
    public boolean delete(Aula aula) throws ModelException {

        DBHandler db = new DBHandler();

        String sqlDelete = "DELETE FROM aulas WHERE id = ?;";

        db.prepareStatement(sqlDelete);
        db.setInt(1, aula.getId());

        return db.executeUpdate() > 0;
    }

    @Override
    public List<Aula> listAll() throws ModelException {

        DBHandler db = new DBHandler();

        List<Aula> aulas = new ArrayList<Aula>();

        String sqlQuery = "SELECT a.id AS aula_id, a.*, "
                + "al.id AS aluno_id, i.id AS instrutor_id, "
                + "v.id AS veiculo_id "
                + "FROM aulas a "
                + "INNER JOIN alunos al ON a.aluno_id = al.id "
                + "INNER JOIN instrutores i ON a.instrutor_id = i.id "
                + "INNER JOIN veiculos v ON a.veiculo_id = v.id "
                + "ORDER BY a.data_aula, a.hora_inicio";

        db.createStatement();
        db.executeQuery(sqlQuery);

        while (db.next()) {
            Aula aula = createAula(db);
            aulas.add(aula);
        }

        return aulas;
    }
    @Override
	public List<Aula> listAll(int pagina, int quantidade) throws ModelException {

		List<Aula> aula = new ArrayList<>();

		String sql = "SELECT aulas.* FROM aulas "
		           + "INNER JOIN alunos ON aulas.aluno_id = alunos.id "
		           + "ORDER BY alunos.nome ASC "
		           + "LIMIT ? OFFSET ?";

		DBHandler db = new DBHandler();
		db.prepareStatement(sql);
		db.setInt(1, quantidade);
		db.setInt(2, (pagina - 1) * quantidade);
		db.executeQuery();

		while (db.next()) {
			aula.add(createAula(db));
		}

		return aula;
	}

	@Override
	public int count() throws ModelException {

		String sql = "SELECT COUNT(*) AS total FROM aulas";

		DBHandler db = new DBHandler();
		db.createStatement();
		db.executeQuery(sql);

		if (db.next()) {
			return db.getInt("total");
		}

		return 0;
	}

	public List<Aula> searchByAluno(String pesquisa) throws ModelException {
	    List<Aula> aulas = new ArrayList<>();

	    String sql = "SELECT * FROM aulas "
	               + "INNER JOIN alunos ON aulas.aluno_id = alunos.id "
	               + "WHERE alunos.nome LIKE ? "
	               + "ORDER BY alunos.nome ASC";

	    DBHandler db = new DBHandler();
	    db.prepareStatement(sql);
	    db.setString(1, "%" + pesquisa + "%");
	    db.executeQuery();

	    while (db.next()) {
	        aulas.add(createAula(db));
	    }

	    return aulas;
	}
	
    @Override
    public Aula findById(int id) throws ModelException {

        DBHandler db = new DBHandler();

        String sql = "SELECT * FROM aulas WHERE id = ?;";

        db.prepareStatement(sql);
        db.setInt(1, id);
        db.executeQuery();

        Aula aula = null;

        while (db.next()) {
            aula = createAula(db);
            break;
        }

        return aula;
    }

    private Aula createAula(DBHandler db) throws ModelException {

        Aula aula = new Aula(db.getInt("id"));

        aula.setDataAula(new java.sql.Date(db.getDate("data_aula").getTime()));
        aula.setHoraInicio(db.getTime("hora_inicio"));
        aula.setDuracaoMinutos(db.getInt("duracao_minutos"));
        aula.setStatus(db.getString("status"));
        aula.setObservacoes(db.getString("observacoes"));

        AlunoDAO alunoDAO = DAOFactory.createDAO(AlunoDAO.class);
        InstrutorDAO instrutorDAO = DAOFactory.createDAO(InstrutorDAO.class);
        VeiculoDAO veiculoDAO = DAOFactory.createDAO(VeiculoDAO.class);

        Aluno aluno = alunoDAO.findById(db.getInt("aluno_id"));
        Instrutor instrutor = instrutorDAO.findById(db.getInt("instrutor_id"));
        Veiculo veiculo = veiculoDAO.findById(db.getInt("veiculo_id"));

        aula.setAluno(aluno);
        aula.setInstrutor(instrutor);
        aula.setVeiculo(veiculo);

        return aula;
    }
}
