package LocadoraCarros.model;

import LocadoraCarros.model.DTO.CarroDTO;

public class LocacaoDTO {

    private Long id;
    private Cliente cliente;
    private CarroDTO carro;
    private Seguradora seguradora;
    private String dataLocacao;
    private String dataPrevDevolucao;
    private Integer qtdDias;
    private Double valorDesconto;
    private Double valorAcrescimo;
    private double valorTotal;
    private String dataDevolvido;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public CarroDTO getCarro() {
        return carro;
    }

    public void setCarro(CarroDTO carro) {
        this.carro = carro;
    }

    public Seguradora getSeguradora() {
        return seguradora;
    }

    public void setSeguradora(Seguradora seguradora) {
        this.seguradora = seguradora;
    }

    public String getDataLocacao() {
        return dataLocacao;
    }

    public void setDataLocacao(String dataLocacao) {
        this.dataLocacao = dataLocacao;
    }

    public String getDataPrevDevolucao() {
        return dataPrevDevolucao;
    }

    public void setDataPrevDevolucao(String dataPrevDevolucao) {
        this.dataPrevDevolucao = dataPrevDevolucao;
    }

    public Integer getQtdDias() {
        return qtdDias;
    }

    public void setQtdDias(Integer qtdDias) {
        this.qtdDias = qtdDias;
    }

    public Double getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(Double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public Double getValorAcrescimo() {
        return valorAcrescimo;
    }

    public void setValorAcrescimo(Double valorAcrescimo) {
        this.valorAcrescimo = valorAcrescimo;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getDataDevolvido() {
        return dataDevolvido;
    }

    public void setDataDevolvido(String dataDevolvido) {
        this.dataDevolvido = dataDevolvido;
    }

}
