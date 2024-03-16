package LocadoraCarros.model.DTO;

public class CarroDTO {

    private Long id;
    private String placa;
    private String cor;
    private Double valorLocacao;
    private String modelo;
    private String fabricante;

    public CarroDTO(Long id, String placa, String cor, Double valorLocacao, String modelo, String fabricante) {
        this.id = id;
        this.placa = placa;
        this.cor = cor;
        this.valorLocacao = valorLocacao;
        this.modelo = modelo;
        this.fabricante = fabricante;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Double getValorLocacao() {
        return valorLocacao;
    }

    public void setValorLocacao(Double valorLocacao) {
        this.valorLocacao = valorLocacao;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
}
