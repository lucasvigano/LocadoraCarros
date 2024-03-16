package LocadoraCarros.model;

public class Cliente {

    private Long id;
    private String nome;
    private String dataVencimentoCNH;

    public Cliente(Long id, String nome, String dataVencimentoCNH) {
        this.id = id;
        this.nome = nome;
        this.dataVencimentoCNH = dataVencimentoCNH;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataVencimentoCNH() {
        return dataVencimentoCNH;
    }

    public void setDataVencimentoCNH(String dataVencimentoCNH) {
        this.dataVencimentoCNH = dataVencimentoCNH;
    }

}
