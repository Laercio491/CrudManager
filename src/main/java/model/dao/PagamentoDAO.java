package model.dao;

import java.util.List;

import model.Aula;
import model.ModelException;
import model.Pagamento;

public interface PagamentoDAO {

    boolean save(Pagamento pagamento) throws ModelException;

    boolean update(Pagamento pagamento) throws ModelException;

    boolean delete(Pagamento pagamento) throws ModelException;

    List<Pagamento> listAll() throws ModelException;

    Pagamento findById(int id) throws ModelException;
    
    List<Pagamento> listAll(int pagina, int quantidade) throws ModelException;

    int count() throws ModelException;
    
    List<Pagamento> search(String pesquisa) throws ModelException;
}