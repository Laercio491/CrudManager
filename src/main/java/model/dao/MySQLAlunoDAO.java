package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.Aluno;
import model.Instrutor;

public class MySQLAlunoDAO implements AlunoDAO {

    @Override
    public boolean save(Aluno aluno) throws ModelException {

        DBHandler db = new DBHandler();

        String sqlInsert = "INSERT INTO alunos VALUES "
                + " (DEFAULT, ?, ?, ?, ?, ?);";

        db.prepareStatement(sqlInsert);
        db.setString(1, aluno.getNome());
        db.setDate(2, aluno.getDataNascimento());
        db.setString(3, aluno.getCpf());
        db.setString(4, aluno.getCategoria());
        db.setInt(5, aluno.getInstrutor().getId());

        return db.executeUpdate() > 0;
    }

    @Override
    public boolean update(Aluno aluno) throws ModelException {

        DBHandler db = new DBHandler();

        String sqlUpdate = "UPDATE alunos "
                + " SET nome = ?, "
                + " data_nascimento = ?, "
                + " cpf = ?, "
                + " categoria = ?, "
                + " instrutor_id = ? "
                + " WHERE id = ?";

        db.prepareStatement(sqlUpdate);

        db.setString(1, aluno.getNome());
        db.setDate(2, aluno.getDataNascimento());
        db.setString(3, aluno.getCpf());
        db.setString(4, aluno.getCategoria());
        db.setInt(5, aluno.getInstrutor().getId());
        db.setInt(6, aluno.getId());

        return db.executeUpdate() > 0;
    }

    @Override
    public boolean delete(Aluno aluno) throws ModelException {

        DBHandler db = new DBHandler();

        String sqlDelete = "DELETE FROM alunos "
                + " WHERE id = ?;";

        db.prepareStatement(sqlDelete);
        db.setInt(1, aluno.getId());

        return db.executeUpdate() > 0;
    }

    @Override
    public List<Aluno> listAll() throws ModelException {

        DBHandler db = new DBHandler();

        List<Aluno> alunos = new ArrayList<Aluno>();

        String sqlQuery = "SELECT a.id AS aluno_id, a.*, i.id AS instrutor_id "
                + "FROM alunos a "
                + "INNER JOIN instrutores i "
                + "ON a.instrutor_id = i.id "
                + "ORDER BY a.nome";

        db.createStatement();
        db.executeQuery(sqlQuery);

        while (db.next()) {
            Aluno aluno = createAluno(db);
            alunos.add(aluno);
        }

        return alunos;
    }
    
    @Override
	public List<Aluno> listAll(int pagina, int quantidade) throws ModelException {

		List<Aluno> aluno = new ArrayList<>();

		String sql = "SELECT * FROM alunos ORDER BY nome ASC LIMIT ? OFFSET ?";

		DBHandler db = new DBHandler();
		db.prepareStatement(sql);
		db.setInt(1, quantidade);
		db.setInt(2, (pagina - 1) * quantidade);
		db.executeQuery();

		while (db.next()) {
			aluno.add(createAluno(db));
		}

		return aluno;
	}

	@Override
	public int count() throws ModelException {

		String sql = "SELECT COUNT(*) AS total FROM alunos";

		DBHandler db = new DBHandler();
		db.createStatement();
		db.executeQuery(sql);

		if (db.next()) {
			return db.getInt("total");
		}

		return 0;
	}
	
	@Override
	public List<Aluno> search(String pesquisa) throws ModelException {
	    List<Aluno> alunos = new ArrayList<>();

	    String sql = "SELECT alunos.* FROM alunos "
	               + "WHERE alunos.nome LIKE ? "
	               + "ORDER BY alunos.nome ASC";

	    DBHandler db = new DBHandler();
	    db.prepareStatement(sql);
	    db.setString(1, "%" + pesquisa + "%");
	    db.executeQuery();

	    while (db.next()) {
	        alunos.add(createAluno(db));
	    }

	    return alunos;
	}

    @Override
    public Aluno findById(int id) throws ModelException {

        DBHandler db = new DBHandler();

        String sql = "SELECT * FROM alunos WHERE id = ?;";

        db.prepareStatement(sql);
        db.setInt(1, id);
        db.executeQuery();

        Aluno aluno = null;

        while (db.next()) {
            aluno = createAluno(db);
            break;
        }

        return aluno;
    }

    private Aluno createAluno(DBHandler db) throws ModelException {

        Aluno aluno = new Aluno(db.getInt("id"));
        aluno.setNome(db.getString("nome"));
        aluno.setDataNascimento(db.getDate("data_nascimento"));
        aluno.setCpf(db.getString("cpf"));
        aluno.setCategoria(db.getString("categoria"));

        InstrutorDAO instrutorDAO = DAOFactory.createDAO(InstrutorDAO.class);

        Instrutor instrutor = instrutorDAO.findById(db.getInt("instrutor_id"));
        aluno.setInstrutor(instrutor);

        return aluno;
    }
}