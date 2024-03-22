package LocadoraCarros.services;

import LocadoraCarros.classe.Uteis;
import LocadoraCarros.model.LocacaoDTO;
import java.util.Date;

public class LocacaoService {

    public void locar(LocacaoDTO locar) throws Exception {
        if (!new ClienteService().verificaValidadeCNH(locar.getCliente())) {
            throw new Exception("CNH Vencida!");
        }

    }

    public void devolver() throws Exception {

    }

    public Double calcularValorTotal(Integer pQtdDias, Double valorCarro, Double valorSeguro, Double percentualDesconto, Double valorAcrescimo) {
        Double valorTotal = (valorCarro + valorSeguro + valorAcrescimo) * pQtdDias;
        Double valorDesconto = 0D;

        if (percentualDesconto != 0) {
            valorDesconto = (valorTotal * (percentualDesconto / 100));
            valorTotal = valorTotal - valorDesconto;
        }

        return valorTotal;
    }

    public String calcularDataDevolcao(int pQuantidadeDias) throws Exception {
        return Uteis.somaDatas(Uteis.getDataAtual(), "d", pQuantidadeDias);
    }

}
