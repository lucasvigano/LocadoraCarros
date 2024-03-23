package LocadoraCarros.services;

import LocadoraCarros.classe.ConexaoBanco;
import LocadoraCarros.classe.Uteis;
import LocadoraCarros.model.LocacaoDTO;
import LocadoraCarros.repositorys.LocacaoRepository;
import java.sql.Statement;

public class LocacaoService {

    public void locar(LocacaoDTO locar) throws Exception {
        if (!new ClienteService().verificaValidadeCNH(locar.getCliente())) {
            throw new Exception("CNH Vencida!");
        }

        //validar se o cliente vai utilizar no rodizio. 
        Statement statement = ConexaoBanco.getConn().createStatement();

        locar.setDataDevolvido(locar.getDataDevolvido().isEmpty()
                ? locar.getDataPrevDevolucao() : locar.getDataDevolvido());

        try {
            statement.execute("begin;");

            new LocacaoRepository().locar(statement, locar);
            new CarroService().setIndisponivel(statement, locar.getCarro());

            statement.execute("commit;");

        } catch (Exception ex) {
            statement.execute("rollback;");
            throw ex;
        }
    }

    public void devolver() throws Exception {

    }

    public Double calcularValorTotal(Integer pQtdDias, Double valorCarro, Double valorSeguro, Double percentualDesconto, Double valorAcrescimo) {
        Double valorTotal = (valorCarro + valorSeguro + valorAcrescimo) * pQtdDias;
//        Double valorTotal = (valorCarro + valorSeguro) * pQtdDias;
        Double valorDesconto = 0D;

        if (percentualDesconto != 0) {
            valorDesconto = (valorTotal * (percentualDesconto / 100));
            valorTotal = valorTotal - valorDesconto;
        }

//        valorTotal += valorAcrescimo;
        return valorTotal;
    }

    public String calcularDataDevolcao(int pQuantidadeDias) throws Exception {
        return Uteis.somaDatas(Uteis.getDataAtual(), "d", pQuantidadeDias);
    }

}
