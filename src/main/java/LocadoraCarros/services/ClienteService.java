package LocadoraCarros.services;

import LocadoraCarros.classe.Uteis;
import LocadoraCarros.model.Cliente;
import LocadoraCarros.repositorys.ClienteRepository;

import java.util.List;

public class ClienteService {

    public List<Cliente> consultar() throws Exception {
        return new ClienteRepository().consultar();
    }

    public Boolean verificaValidadeCNH(Cliente pCliente) throws Exception {
        if (pCliente.getDataVencimentoCNH().isEmpty()) {
            throw new Exception("Data de Vencimento não Informada!");
        }

        if (Uteis.diferencaDatas(pCliente.getDataVencimentoCNH(), "2024-03-22", "d") < 0) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }
}
