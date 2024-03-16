package LocadoraCarros.repositorys;

import LocadoraCarros.classe.ConexaoBanco;
import LocadoraCarros.model.Cliente;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {

    public List<Cliente> consultar() throws Exception {
        Statement stm = ConexaoBanco.getConn().createStatement();

        ResultSet rst = stm.executeQuery("SELECT * from cliente");

        List<Cliente> vCliente = new ArrayList<>();

        while (rst.next()) {
            vCliente.add(new Cliente(rst.getLong("id"), rst.getString("nome"),
                    rst.getString("datavencimentocnh")));
        }

        return vCliente;
    }
}
