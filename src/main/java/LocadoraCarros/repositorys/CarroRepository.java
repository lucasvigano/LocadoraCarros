package LocadoraCarros.repositorys;

import LocadoraCarros.classe.ConexaoBanco;
import LocadoraCarros.model.Carro;
import java.sql.ResultSet;

import java.sql.Statement;

public class CarroRepository {

    public void salvar(Carro pCarro) throws Exception {
        try {
            Statement statement = ConexaoBanco.getConn().createStatement();

            StringBuilder insertCarro2 = new StringBuilder();
            insertCarro2.append("INSERT INTO carro (id_fabricante, id_modelo, placa,");
            insertCarro2.append(" cor, disponivel, ano, valorlocacao) VALUES (");
            insertCarro2.append(pCarro.getIdFabricante()).append(",");
            insertCarro2.append(pCarro.getIdModelo()).append(", ");
            insertCarro2.append("'").append(pCarro.getPlaca()).append("'").append(",");
            insertCarro2.append("'").append(pCarro.getCor()).append("'").append(",");
            insertCarro2.append(pCarro.getDisponivel()).append(", ");
            insertCarro2.append(pCarro.getAno()).append(",");
            insertCarro2.append(pCarro.getValorLocacao());
            insertCarro2.append(");");

            statement.execute(insertCarro2.toString());

            ResultSet rst = statement.executeQuery("SELECT CURRVAL('carro_id_seq') AS id");
            rst.next();

            pCarro.setId(rst.getLong("id"));

        } catch (Exception ex) {
            throw ex;
        }
    }
}
