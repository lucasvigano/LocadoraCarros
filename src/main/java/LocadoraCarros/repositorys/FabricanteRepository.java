package LocadoraCarros.repositorys;

import LocadoraCarros.classe.ConexaoBanco;
import LocadoraCarros.model.Fabricante;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FabricanteRepository {

    public List<Fabricante> consultar() {
        try {
            Statement statement = ConexaoBanco.getConn().createStatement();

            String sql = "SELECT * from fabricante";

            ResultSet result = statement.executeQuery(sql);

            List<Fabricante> listaFabricante = new ArrayList<>();

            while (result.next()) {
//                Fabricante fabricante = new Fabricante();
//                fabricante.setId(result.getLong("id"));
//                fabricante.setNome(result.getString("nome"));
//                listaFabricante.add(fabricante);

                listaFabricante.add(new Fabricante(
                        result.getLong("id"),
                        result.getString("nome")));
            }

            return listaFabricante;

        } catch (Exception ex) {
            System.out.println("Algo deu errado!");
            return null;
        }
    }

}
