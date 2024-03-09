package LocadoraCarros.repositorys;

import LocadoraCarros.classe.ConexaoBanco;
import LocadoraCarros.model.Fabricante;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FabricanteRepository {

    public Boolean fabricanteExiste(String pNomeFabricante) throws Exception {
        Statement statemant = ConexaoBanco.getConn().createStatement();

        StringBuilder sql = new StringBuilder();
        sql.append("select * from fabricante");
        sql.append(" WHERE nome = '" + pNomeFabricante.toUpperCase() + "'");

        ResultSet rst = statemant.executeQuery(sql.toString());

        if (rst.next()) {
            return true;
        } else {
            return false;
        }
    }

    public void salvar(Fabricante pFabricante) throws Exception {
        Statement statement = ConexaoBanco.getConn().createStatement();

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO fabricante (nome) VALUES (");
        sql.append("'" + pFabricante.getNome().toUpperCase() + "');");

        statement.execute(sql.toString());

        ResultSet rst = statement.executeQuery("SELECT CURRVAL('fabricante_id_seq') AS id");
        rst.next();

        pFabricante.setId(rst.getLong("id"));
    }

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
