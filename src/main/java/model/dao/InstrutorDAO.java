package model.dao;

import java.util.List;

import model.ModelException;
import model.Instrutor;

public interface InstrutorDAO {

    boolean save(Instrutor instrutor) throws ModelException;

    boolean update(Instrutor instrutor) throws ModelException;

    boolean delete(Instrutor instrutor) throws ModelException;

    List<Instrutor> listAll() throws ModelException;

    Instrutor findById(int id) throws ModelException;
    
    Instrutor findByCNH(String cnh) throws ModelException;
    
    List<Instrutor> listAll(int pagina, int quantidade) throws ModelException;

    int count() throws ModelException;
    
    List<Instrutor> search(String pesquisa) throws ModelException;
}