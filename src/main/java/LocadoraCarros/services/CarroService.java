package LocadoraCarros.services;

import LocadoraCarros.model.Carro;
import LocadoraCarros.model.DTO.CarroDTO;
import LocadoraCarros.repositorys.CarroRepository;
import java.sql.Statement;
import java.util.List;

public class CarroService {

    public void salvar(Carro pCarro) throws Exception {
        new CarroRepository().salvar(pCarro);
    }

    public List<CarroDTO> consultarTodos() throws Exception {
        return new CarroRepository().consultarTodos();
    }

    public List<CarroDTO> consultarDisponiveis() throws Exception {
        return new CarroRepository().consultarDisponiveis();
    }

    void setIndisponivel(Statement statement, CarroDTO carro) throws Exception {
        if (!new CarroRepository().verificaDisponibilidade(statement, carro.getId())) {
            throw new Exception("Carro Indisponível");
        }

        new CarroRepository().setIndisponivel(statement, carro.getId());
    }
}
