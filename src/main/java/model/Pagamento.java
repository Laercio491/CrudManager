package model;

import java.math.BigDecimal;
import java.sql.Date;

public class Pagamento {

    private int id;
    private BigDecimal valor;
    private Date dataPagamento;
    private String formaPagamento;
    private String status;
    private String descricao;
    private int numeroParcelas;
    private Aluno aluno;
    
    public Pagamento() {
        this(0);
    }
    
    public Pagamento(int id) {
        this.id = id;
    }
    
    public Pagamento(BigDecimal valor, Date dataPagamento,
                     String formaPagamento, String status, String descricao,
                     int numeroParcelas, Aluno aluno) {
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.formaPagamento = formaPagamento;
        this.status = status;
        this.descricao = descricao;
        this.numeroParcelas = numeroParcelas;
        this.aluno = aluno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getNumeroParcelas() {
        return numeroParcelas;
    }

    public void setNumeroParcelas(int numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
}
