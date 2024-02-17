package LocadoraCarros.repositorys;

import LocadoraCarros.classe.ConexaoBanco;
import LocadoraCarros.model.Modelo;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ModeloRepository {

    public List<Modelo> consultar(Long idFabricante) {
        try {
            Statement statement = ConexaoBanco.getConn().createStatement();
            String sql = "SELECT * from modelo WHERE id_fabricante = " + idFabricante;

            ResultSet result = statement.executeQuery(sql);

            List<Modelo> listaModelo = new ArrayList<>();

            while (result.next()) {
                listaModelo.add(new Modelo(result.getLong("id"),
                        result.getString("nome"),
                        result.getLong("id_fabricante")));
            }

            return listaModelo;

        } catch (Exception ex) {
            System.out.println("Algo deu errado");
            return null;
        }
    }
}
