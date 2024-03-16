package LocadoraCarros.services;

import LocadoraCarros.model.Seguradora;
import LocadoraCarros.repositorys.SeguradoraRepository;
import java.util.List;

public class SeguradoraService {

    public List<Seguradora> consultar() throws Exception {
        //incluir regra do nenhum?
        return new SeguradoraRepository().consultar();
    }

}
