
package model;

public class Instrutor {

    private int id;
    private String nome;
    private String sexo;
    private String email;
    private String telefone;
    private String cnh;
    private String password;

    public Instrutor() {
        this(0);
    }

    public Instrutor(int id) {
        this.id = id;
        setNome("");
        setSexo("");
        setEmail("");
        setTelefone("");
        setCnh("");
        setPassword("");
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }
}
