package LocadoraCarros.services;

import LocadoraCarros.model.Modelo;
import LocadoraCarros.repositorys.ModeloRepository;

import java.util.ArrayList;
import java.util.List;

public class ModeloService {

    public List<Modelo> consultar(Long idFabricante) {
        return new ModeloRepository().consultar(idFabricante);
    }

    public Long selecionar(Long idFabricante) {
        List<Modelo> listaModelos = consultar(idFabricante);

        System.out.println("Lista de Modelos");
        System.out.println("___________________________________");

        List<Long> idsModelos = new ArrayList<>();

        for (Modelo modelo : listaModelos) {
            System.out.println(modelo.toString());
            idsModelos.add(modelo.getId());
        }

        System.out.println("___________________________________");
        System.out.println("Selecione o modelo desejado: ");

        Long idModelo = 0L;

        try {
            idModelo = Long.parseLong(new LeituraService().ler());
        } catch (Exception ex) {
            System.out.println("Opcao Invalida");
            return -1L;
        }

        if (!idsModelos.contains(idModelo)) {
            System.out.println("Opcao Invalida");
            return -1L;
        }

        return idModelo;
    }

}
