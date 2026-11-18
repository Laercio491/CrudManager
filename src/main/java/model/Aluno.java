package model;

import java.util.Calendar;
import java.util.Date;

public class Aluno {

    private int id;
    private String nome;
    private Date dataNascimento;
    private String cpf;
    private String categoria;
    private Instrutor instrutor;

    public Aluno() {
        this(0);
    }

    public Aluno(int id) {
        this.id = id;
        setNome("");
        setDataNascimento(Calendar.getInstance().getTime());
        setCpf("");
        setCategoria("");
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public Instrutor getInstrutor() {
        return instrutor;
    }

    public void setInstrutor(Instrutor instrutor) {
        this.instrutor = instrutor;
    }
}