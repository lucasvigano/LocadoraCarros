package LocadoraCarros.repositorys;

import LocadoraCarros.classe.ConexaoBanco;
import LocadoraCarros.model.Modelo;
import LocadoraCarros.model.Seguradora;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SeguradoraRepository {

    public List<Seguradora> consultar() throws Exception {
        Statement statement = ConexaoBanco.getConn().createStatement();
        String sql = "SELECT * from seguradora";

        ResultSet result = statement.executeQuery(sql);

        List<Seguradora> listaModelo = new ArrayList<>();

        while (result.next()) {
            listaModelo.add(new Seguradora(result.getLong("id"),
                    result.getString("nome"),
                    result.getDouble("valor")));
        }

        return listaModelo;
    }
}
