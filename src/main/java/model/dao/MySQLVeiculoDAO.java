package model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Veiculo;
import model.Aluno;
import model.Instrutor;
import model.ModelException;

public class MySQLVeiculoDAO implements VeiculoDAO {

	@Override
	public boolean save(Veiculo veiculo) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlInsert = "INSERT INTO veiculos VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?);";

		db.prepareStatement(sqlInsert);

		db.setString(1, veiculo.getModelo());
		db.setString(2, veiculo.getPlaca());
		db.setInt(3, veiculo.getAno());
		db.setString(4, veiculo.getCategoria());
		db.setDate(5, veiculo.getDataAquisicao() == null ? new Date() : veiculo.getDataAquisicao());
		db.setString(6, veiculo.getSituacao());
		db.setInt(7, veiculo.getInstrutor().getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Veiculo veiculo) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlUpdate = "UPDATE veiculos " + " SET modelo = ?, " + " placa = ?, " + " ano = ?, " + " categoria = ?, "
				+ " data_aquisicao = ?, " + " situacao = ?, " + " instrutor_id = ? " + " WHERE id = ?;";

		db.prepareStatement(sqlUpdate);

		db.setString(1, veiculo.getModelo());
		db.setString(2, veiculo.getPlaca());
		db.setInt(3, veiculo.getAno());
		db.setString(4, veiculo.getCategoria());
		db.setDate(5, veiculo.getDataAquisicao() == null ? new Date() : veiculo.getDataAquisicao());
		db.setString(6, veiculo.getSituacao());
		db.setInt(7, veiculo.getInstrutor().getId());
		db.setInt(8, veiculo.getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Veiculo veiculo) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlDelete = "DELETE FROM veiculos WHERE id = ?;";

		db.prepareStatement(sqlDelete);
		db.setInt(1, veiculo.getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public List<Veiculo> listAll() throws ModelException {
		DBHandler db = new DBHandler();

		List<Veiculo> veiculos = new ArrayList<Veiculo>();

		String sqlQuery = "SELECT v.id AS veiculo_id, v.*, i.* " + "FROM veiculos v " + "INNER JOIN instrutores i "
				+ "ON v.instrutor_id = i.id;";

		db.createStatement();
		db.executeQuery(sqlQuery);

		while (db.next()) {
			Instrutor instrutor = new Instrutor(db.getInt("instrutor_id"));
			instrutor.setNome(db.getString("nome"));
			instrutor.setSexo(db.getString("sexo"));
			instrutor.setEmail(db.getString("email"));
			instrutor.setTelefone(db.getString("telefone"));
			instrutor.setCnh(db.getString("cnh"));

			Veiculo veiculo = new Veiculo(db.getInt("veiculo_id"));
			veiculo.setModelo(db.getString("modelo"));
			veiculo.setPlaca(db.getString("placa"));
			veiculo.setAno(db.getInt("ano"));
			veiculo.setCategoria(db.getString("categoria"));
			veiculo.setDataAquisicao(db.getDate("data_aquisicao"));
			veiculo.setSituacao(db.getString("situacao"));
			veiculo.setInstrutor(instrutor);

			veiculos.add(veiculo);
		}

		return veiculos;
	}

	@Override
	public List<Veiculo> listAll(int pagina, int quantidade) throws ModelException {

		List<Veiculo> veiculo = new ArrayList<>();

		String sql = "SELECT * FROM veiculos ORDER BY categoria ASC LIMIT ? OFFSET ?";

		DBHandler db = new DBHandler();
		db.prepareStatement(sql);
		db.setInt(1, quantidade);
		db.setInt(2, (pagina - 1) * quantidade);
		db.executeQuery();

		while (db.next()) {
			veiculo.add(createVeiculo(db));
		}

		return veiculo;
	}

	@Override
	public int count() throws ModelException {

		String sql = "SELECT COUNT(*) AS total FROM veiculos";

		DBHandler db = new DBHandler();
		db.createStatement();
		db.executeQuery(sql);

		if (db.next()) {
			return db.getInt("total");
		}

		return 0;
	}
	
	@Override
	public List<Veiculo> search(String pesquisa) throws ModelException {
	    List<Veiculo> veiculos = new ArrayList<>();

	    String sql = "SELECT * FROM veiculos "
	               + "WHERE modelo LIKE ? OR placa LIKE ? "
	               + "ORDER BY modelo ASC";

	    DBHandler db = new DBHandler();
	    db.prepareStatement(sql);
	    db.setString(1, "%" + pesquisa + "%");
	    db.setString(2, "%" + pesquisa + "%");
	    db.executeQuery();

	    while (db.next()) {
	        veiculos.add(createVeiculo(db));
	    }

	    return veiculos;
	}

	@Override
	public Veiculo findById(int id) throws ModelException {
		DBHandler db = new DBHandler();

		String sql = "SELECT * FROM veiculos WHERE id = ?;";

		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();

		Veiculo veiculo = null;

		while (db.next()) {
			veiculo = new Veiculo(id);
			veiculo.setModelo(db.getString("modelo"));
			veiculo.setPlaca(db.getString("placa"));
			veiculo.setAno(db.getInt("ano"));
			veiculo.setCategoria(db.getString("categoria"));
			veiculo.setDataAquisicao(db.getDate("data_aquisicao"));
			veiculo.setSituacao(db.getString("situacao"));

			InstrutorDAO instrutorDAO = DAOFactory.createDAO(InstrutorDAO.class);
			Instrutor instrutor = instrutorDAO.findById(db.getInt("instrutor_id"));

			veiculo.setInstrutor(instrutor);

			break;
		}

		return veiculo;
	}

	private Veiculo createVeiculo(DBHandler db) throws ModelException {

		Veiculo veiculo = new Veiculo(db.getInt("id"));

		veiculo.setModelo(db.getString("modelo"));
		veiculo.setPlaca(db.getString("placa"));
		veiculo.setAno(db.getInt("ano"));
		veiculo.setCategoria(db.getString("categoria"));
		veiculo.setDataAquisicao(db.getDate("data_aquisicao"));
		veiculo.setSituacao(db.getString("situacao"));

		InstrutorDAO instrutorDAO = DAOFactory.createDAO(InstrutorDAO.class);
		Instrutor instrutor = instrutorDAO.findById(db.getInt("instrutor_id"));

		veiculo.setInstrutor(instrutor);

		return veiculo;
	}
}