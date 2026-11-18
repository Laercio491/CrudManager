package model.dao;

import java.util.List;

import model.ModelException;
import model.Aluno;
import model.Instrutor;

public interface AlunoDAO {

    boolean save(Aluno aluno) throws ModelException;

    boolean update(Aluno aluno) throws ModelException;

    boolean delete(Aluno aluno) throws ModelException;

    List<Aluno> listAll() throws ModelException;

    Aluno findById(int id) throws ModelException;
    
    List<Aluno> listAll(int pagina, int quantidade) throws ModelException;

    int count() throws ModelException;
    
    List<Aluno> search(String pesquisa) throws ModelException;
}