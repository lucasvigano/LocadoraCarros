package LocadoraCarros.services;

import LocadoraCarros.model.Carro;
import LocadoraCarros.model.DTO.CarroDTO;
import LocadoraCarros.repositorys.CarroRepository;
import java.util.List;

public class CarroService {

    public void salvar(Carro pCarro) throws Exception {
        new CarroRepository().salvar(pCarro);
    }

    public List<CarroDTO> consultarDisponiveis() throws Exception {
        return new CarroRepository().consultarDisponiveis();
    }
}
