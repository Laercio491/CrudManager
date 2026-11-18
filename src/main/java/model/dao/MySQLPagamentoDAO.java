
package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.Pagamento;
import model.Aluno;

public class MySQLPagamentoDAO implements PagamentoDAO {

    @Override
    public boolean save(Pagamento pagamento) throws ModelException {

        DBHandler db = new DBHandler();

        String sqlInsert = "INSERT INTO pagamentos VALUES "
                + " (DEFAULT, ?, ?, ?, ?, ?, ?, ?);";

        db.prepareStatement(sqlInsert);
        db.setBigDecimal(1, pagamento.getValor());
        db.setDate(2, pagamento.getDataPagamento());
        db.setString(3, pagamento.getFormaPagamento());
        db.setString(4, pagamento.getStatus());
        db.setString(5, pagamento.getDescricao());
        db.setInt(6, pagamento.getNumeroParcelas());
        db.setInt(7, pagamento.getAluno().getId());

        return db.executeUpdate() > 0;
    }

    @Override
    public boolean update(Pagamento pagamento) throws ModelException {

        DBHandler db = new DBHandler();

        String sqlUpdate = "UPDATE pagamentos "
                + " SET valor = ?, "
                + " data_pagamento = ?, "
                + " forma_pagamento = ?, "
                + " status = ?, "
                + " descricao = ?, "
                + " numero_parcelas = ?, "
                + " aluno_id = ? "
                + " WHERE id = ?";

        db.prepareStatement(sqlUpdate);

        db.setBigDecimal(1, pagamento.getValor());
        db.setDate(2, pagamento.getDataPagamento());
        db.setString(3, pagamento.getFormaPagamento());
        db.setString(4, pagamento.getStatus());
        db.setString(5, pagamento.getDescricao());
        db.setInt(6, pagamento.getNumeroParcelas());
        db.setInt(7, pagamento.getAluno().getId());
        db.setInt(8, pagamento.getId());

        return db.executeUpdate() > 0;
    }

    @Override
    public boolean delete(Pagamento pagamento) throws ModelException {

        DBHandler db = new DBHandler();

        String sqlDelete = "DELETE FROM pagamentos WHERE id = ?;";

        db.prepareStatement(sqlDelete);
        db.setInt(1, pagamento.getId());

        return db.executeUpdate() > 0;
    }

    @Override
    public List<Pagamento> listAll() throws ModelException {

        DBHandler db = new DBHandler();

        List<Pagamento> pagamentos = new ArrayList<Pagamento>();

        String sqlQuery = "SELECT p.id AS pagamento_id, p.*, "
                + "a.id AS aluno_id "
                + "FROM pagamentos p "
                + "INNER JOIN alunos a ON p.aluno_id = a.id "
                + "ORDER BY p.data_pagamento";

        db.createStatement();
        db.executeQuery(sqlQuery);

        while (db.next()) {
            Pagamento pagamento = createPagamento(db);
            pagamentos.add(pagamento);
        }

        return pagamentos;
    }
    @Override
	public List<Pagamento> listAll(int pagina, int quantidade) throws ModelException {

		List<Pagamento> pagamento = new ArrayList<>();

		String sql = "SELECT * FROM pagamentos INNER JOIN alunos ON pagamentos.aluno_id = alunos.id ORDER BY alunos.nome ASC LIMIT ? OFFSET ?";

		DBHandler db = new DBHandler();
		db.prepareStatement(sql);
		db.setInt(1, quantidade);
		db.setInt(2, (pagina - 1) * quantidade);
		db.executeQuery();

		while (db.next()) {
			pagamento.add(createPagamento(db));
		}

		return pagamento;
	}

	@Override
	public int count() throws ModelException {

		String sql = "SELECT COUNT(*) AS total FROM pagamentos";

		DBHandler db = new DBHandler();
		db.createStatement();
		db.executeQuery(sql);

		if (db.next()) {
			return db.getInt("total");
		}

		return 0;
	}
	
	@Override
	public List<Pagamento> search(String pesquisa) throws ModelException {
	    List<Pagamento> pagamentos = new ArrayList<>();

	    String sql = "SELECT * FROM pagamentos "
	               + "INNER JOIN alunos ON pagamentos.aluno_id = alunos.id "
	               + "WHERE alunos.nome LIKE ? "
	               + "ORDER BY alunos.nome ASC";

	    DBHandler db = new DBHandler();
	    db.prepareStatement(sql);
	    db.setString(1, "%" + pesquisa + "%");
	    db.executeQuery();

	    while (db.next()) {
	        pagamentos.add(createPagamento(db));
	    }

	    return pagamentos;
	}

    @Override
    public Pagamento findById(int id) throws ModelException {

        DBHandler db = new DBHandler();

        String sql = "SELECT * FROM pagamentos WHERE id = ?;";

        db.prepareStatement(sql);
        db.setInt(1, id);
        db.executeQuery();

        Pagamento pagamento = null;

        while (db.next()) {
            pagamento = createPagamento(db);
            break;
        }

        return pagamento;
    }

    private Pagamento createPagamento(DBHandler db) throws ModelException {

        Pagamento pagamento = new Pagamento(db.getInt("id"));

        pagamento.setValor(db.getBigDecimal("valor"));
        pagamento.setDataPagamento(new java.sql.Date(db.getDate("data_pagamento").getTime()));
        pagamento.setFormaPagamento(db.getString("forma_pagamento"));
        pagamento.setStatus(db.getString("status"));
        pagamento.setDescricao(db.getString("descricao"));
        pagamento.setNumeroParcelas(db.getInt("numero_parcelas"));

        AlunoDAO alunoDAO = DAOFactory.createDAO(AlunoDAO.class);

        Aluno aluno = alunoDAO.findById(db.getInt("aluno_id"));
        pagamento.setAluno(aluno);

        return pagamento;
    }
}
