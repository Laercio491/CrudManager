package model.dao;

import java.util.List;

import model.Aula;
import model.ModelException;
import model.Veiculo;

public interface VeiculoDAO {

    boolean save(Veiculo veiculo) throws ModelException;

    boolean update(Veiculo veiculo) throws ModelException;

    boolean delete(Veiculo veiculo) throws ModelException;

    List<Veiculo> listAll() throws ModelException;

    Veiculo findById(int id) throws ModelException;
    
    List<Veiculo> listAll(int pagina, int quantidade) throws ModelException;

    int count() throws ModelException;
    
    List<Veiculo> search(String pesquisa) throws ModelException;
}