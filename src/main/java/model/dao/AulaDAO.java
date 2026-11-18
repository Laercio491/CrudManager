package model.dao;

import java.util.List;

import model.ModelException;
import model.Aluno;
import model.Aula;

public interface AulaDAO {

    boolean save(Aula aula) throws ModelException;

    boolean update(Aula aula) throws ModelException;

    boolean delete(Aula aula) throws ModelException;

    List<Aula> listAll() throws ModelException;

    Aula findById(int id) throws ModelException;
    
    List<Aula> listAll(int pagina, int quantidade) throws ModelException;

    int count() throws ModelException;
    
    List<Aula> searchByAluno(String pesquisa) throws ModelException;
}