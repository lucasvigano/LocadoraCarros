package LocadoraCarros.services;

import LocadoraCarros.model.Fabricante;
import LocadoraCarros.repositorys.FabricanteRepository;
import java.util.List;

public class FabricanteService {

    public List<Fabricante> consultar() {
        return new FabricanteRepository().consultar();
    }
}
