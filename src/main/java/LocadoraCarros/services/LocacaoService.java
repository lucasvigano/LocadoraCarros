package LocadoraCarros.services;

import LocadoraCarros.model.Cliente;
import LocadoraCarros.model.LocacaoDTO;

public class LocacaoService {

    public void locar(LocacaoDTO locar) throws Exception {
        if (!new ClienteService().verificaValidadeCNH(locar.getCliente())) {
            throw new Exception("CNH Vencida!");
        }

        //
    }

    public void devolver() throws Exception {

    }

    public Double calcularValorTotal(Integer pQtdDias,Double valorCarro, Double valorSeguro, Double percentualDesconto, Double valorAcrescimo) {
        Double valorTotal = (valorCarro + valorSeguro + valorAcrescimo) * pQtdDias;
        Double valorDesconto = 0D;

        if (percentualDesconto != 0) {
            //Descobrir o valor de desconto com base no percentualDEsconto.
        }

//        return valorTotal - valorDesconto;
        return 145540.37D;
    }

}
