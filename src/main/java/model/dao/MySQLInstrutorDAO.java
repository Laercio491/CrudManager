
package model.dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.Instrutor;

public class MySQLInstrutorDAO implements InstrutorDAO {

	@Override
	public boolean save(Instrutor instrutor) throws ModelException {

		DBHandler db = new DBHandler();

		String sqlInsert = "INSERT INTO instrutores VALUES " + " (DEFAULT, ?, ?, ?, ?, ?, ?);";

		db.prepareStatement(sqlInsert);
		db.setString(1, instrutor.getNome());
		db.setString(2, instrutor.getSexo());
		db.setString(3, instrutor.getEmail());
		db.setString(4, instrutor.getTelefone());
		db.setString(5, instrutor.getCnh());
		db.setString(6, instrutor.getPassword());

		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Instrutor instrutor) throws ModelException {

		DBHandler db = new DBHandler();

		String sqlUpdate = "UPDATE instrutores " + "SET nome = ?, " + "sexo = ?, " + "email = ?, " + "telefone = ?, "
				+ "cnh = ?, " + "password = ? " + "WHERE id = ?";

		db.prepareStatement(sqlUpdate);

		db.setString(1, instrutor.getNome());
		db.setString(2, instrutor.getSexo());
		db.setString(3, instrutor.getEmail());
		db.setString(4, instrutor.getTelefone());
		db.setString(5, instrutor.getCnh());
		db.setString(6, instrutor.getPassword());
		db.setInt(7, instrutor.getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Instrutor instrutor) throws ModelException {

		DBHandler db = new DBHandler();

		String sqlDelete = "DELETE FROM instrutores " + "WHERE id = ?;";

		db.prepareStatement(sqlDelete);
		db.setInt(1, instrutor.getId());

		try {

			return db.executeUpdate() > 0;

		} catch (ModelException e) {
			if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
				return false;
			}

			throw e;
		}
	}

	@Override
	public List<Instrutor> listAll() throws ModelException {

		DBHandler db = new DBHandler();

		List<Instrutor> instrutores = new ArrayList<Instrutor>();

		String sqlQuery = "SELECT * FROM instrutores";

		db.createStatement();
		db.executeQuery(sqlQuery);

		while (db.next()) {
			Instrutor instrutor = createInstrutor(db);
			instrutores.add(instrutor);
		}

		return instrutores;
	}

	@Override
	public List<Instrutor> listAll(int pagina, int quantidade) throws ModelException {

		List<Instrutor> instrutores = new ArrayList<>();

		String sql = "SELECT * FROM instrutores ORDER BY nome ASC LIMIT ? OFFSET ?";

		DBHandler db = new DBHandler();
		db.prepareStatement(sql);
		db.setInt(1, quantidade);
		db.setInt(2, (pagina - 1) * quantidade);
		db.executeQuery();

		while (db.next()) {
			instrutores.add(createInstrutor(db));
		}

		return instrutores;
	}

	@Override
	public int count() throws ModelException {

		String sql = "SELECT COUNT(*) AS total FROM instrutores";

		DBHandler db = new DBHandler();
		db.createStatement();
		db.executeQuery(sql);

		if (db.next()) {
			return db.getInt("total");
		}

		return 0;
	}
	
	@Override
	public List<Instrutor> search(String pesquisa) throws ModelException {
	    List<Instrutor> instrutores = new ArrayList<>();

	    String sql = "SELECT * FROM instrutores "
	               + "WHERE nome LIKE ? "
	               + "ORDER BY nome ASC";

	    DBHandler db = new DBHandler();
	    db.prepareStatement(sql);
	    db.setString(1, "%" + pesquisa + "%");
	    db.executeQuery();

	    while (db.next()) {
	        instrutores.add(createInstrutor(db));
	    }

	    return instrutores;
	}

	@Override
	public Instrutor findById(int id) throws ModelException {

		DBHandler db = new DBHandler();

		String sql = "SELECT * FROM instrutores WHERE id = ?";

		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();

		Instrutor instrutor = null;

		while (db.next()) {
			instrutor = createInstrutor(db);
			break;
		}

		return instrutor;
	}

	@Override
	public Instrutor findByCNH(String cnh) throws ModelException {

		Instrutor instrutor = null;

		DBHandler db = new DBHandler();

		String sql = "SELECT * FROM instrutores WHERE cnh = ?";

		db.prepareStatement(sql);

		db.setString(1, cnh);

		db.executeQuery();

		if (db.next()) {
			instrutor = createInstrutor(db);
		}

		return instrutor;
	}

	private Instrutor createInstrutor(DBHandler db) throws ModelException {

		Instrutor instrutor = new Instrutor(db.getInt("id"));

		instrutor.setNome(db.getString("nome"));
		instrutor.setSexo(db.getString("sexo"));
		instrutor.setEmail(db.getString("email"));
		instrutor.setTelefone(db.getString("telefone"));
		instrutor.setCnh(db.getString("cnh"));
		instrutor.setPassword(db.getString("password"));

		return instrutor;
	}
}
