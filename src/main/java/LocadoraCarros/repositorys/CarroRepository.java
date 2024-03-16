package LocadoraCarros.repositorys;

import LocadoraCarros.classe.ConexaoBanco;
import LocadoraCarros.model.Carro;
import LocadoraCarros.model.Cliente;
import LocadoraCarros.model.DTO.CarroDTO;
import java.sql.ResultSet;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    public List<CarroDTO> consultarTodos() throws Exception {
        return consultar(Boolean.FALSE);
    }

    public List<CarroDTO> consultarDisponiveis() throws Exception {
        return consultar(Boolean.TRUE);
    }

    public List<CarroDTO> consultar(Boolean pConsultaDisponiveis) throws Exception {
        Statement stm = ConexaoBanco.getConn().createStatement();
        StringBuilder sql = new StringBuilder();

        sql.append("select c.id, c.placa, c.cor, c.valorlocacao, ")
                .append("m.nome AS modelo, f.nome as fabricante")
                .append(" from carro as c")
                .append(" inner join modelo as m on m.id = c.id_modelo")
                .append(" inner join fabricante as f on f.id = c.id_fabricante");

        if (pConsultaDisponiveis) {
            sql.append(" where disponivel = TRUE");
        }

        sql.append(" order by f.nome, m.nome");

        ResultSet rst = stm.executeQuery(sql.toString());

        List<CarroDTO> vCarro = new ArrayList<>();

        while (rst.next()) {
            vCarro.add(new CarroDTO(rst.getLong("id"), rst.getString("placa"),
                    rst.getString("cor"), rst.getDouble("valorlocacao"),
                    rst.getString("modelo"), rst.getString("fabricante")));
        }

        return vCarro;
    }
}
