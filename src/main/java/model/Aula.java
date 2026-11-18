package model;

import java.sql.Date;
import java.sql.Time;

public class Aula {

    private int id;
    private Date dataAula;
    private Time horaInicio;
    private int duracaoMinutos;
    private String status;
    private String observacoes;
    private Aluno aluno;
    private Instrutor instrutor;
    private Veiculo veiculo;

    public Aula() {
        this(0);
    }
    
    public Aula(int id) {
        this.id = id;
    }

    public Aula(Date dataAula, Time horaInicio, int duracaoMinutos,
                String status, String observacoes, Aluno aluno,
                Instrutor instrutor, Veiculo veiculo) {
        this.dataAula = dataAula;
        this.horaInicio = horaInicio;
        this.duracaoMinutos = duracaoMinutos;
        this.status = status;
        this.observacoes = observacoes;
        this.aluno = aluno;
        this.instrutor = instrutor;
        this.veiculo = veiculo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataAula() {
        return dataAula;
    }

    public void setDataAula(Date dataAula) {
        this.dataAula = dataAula;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(int duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Instrutor getInstrutor() {
        return instrutor;
    }

    public void setInstrutor(Instrutor instrutor) {
        this.instrutor = instrutor;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
}
