package LocadoraCarros.services;

import LocadoraCarros.model.Cliente;
import LocadoraCarros.repositorys.ClienteRepository;
import java.util.List;

public class ClienteService {

    public List<Cliente> consultar() throws Exception {
        return new ClienteRepository().consultar();
    }
}
