package LocadoraCarros.repositorys;

import LocadoraCarros.model.LocacaoDTO;
import java.sql.ResultSet;
import java.sql.Statement;

public class LocacaoRepository {

    public void locar(Statement statement, LocacaoDTO locacao) throws Exception {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO locacao (id_carro, id_seguradora, id_cliente,")
                .append("datalocacao, datadevolucao, datadevolvida,")
                .append("valor, valordesconto, valortotal) VALUES (")
                .append(locacao.getCarro().getId()).append(", ")
                .append(locacao.getSeguradora().getId()).append(", ")
                .append(locacao.getCliente().getId()).append(", ")
                .append("'").append(locacao.getDataLocacao()).append("',")
                .append("'").append(locacao.getDataPrevDevolucao()).append("',")
                .append("'").append(locacao.getDataDevolvido()).append("',")
                .append(locacao.getValorAcrescimo()).append(",")
                .append(locacao.getValorDesconto()).append(",")
                .append(locacao.getValorTotal())
                .append(")");

        statement.execute(sql.toString());

        ResultSet rst = statement.executeQuery("SELECT CURRVAL('locacao_id_seq') AS id");
        rst.next();

        locacao.setId(rst.getLong("id"));
    }

    public void devolver(Statement statement, LocacaoDTO locacao) throws Exception {

    }
}
