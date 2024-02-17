package LocadoraCarros.services;

import LocadoraCarros.model.Fabricante;
import LocadoraCarros.repositorys.FabricanteRepository;

import java.util.ArrayList;
import java.util.List;

public class FabricanteService {

    public List<Fabricante> consultar() {
        return new FabricanteRepository().consultar();
    }

    public Long selecionar() {
        List<Fabricante> listaFabricante = consultar();

        System.out.println("Lista Fabricantes");
        System.out.println("________________________________");

        List<Long> idsFabricantes = new ArrayList<>();

        for (Fabricante fabricante : listaFabricante) {
            System.out.println(fabricante.toString());
            idsFabricantes.add(fabricante.getId());
        }

        System.out.println("________________________________");

        System.out.println("Selecione o fabricante do carro: ");

        Long idFabricante = 0L;

        try {
            idFabricante = Long.parseLong(new LeituraService().ler());

        } catch (Exception ex) {
            System.out.println("OPCAO INVALIDA");
            return -1L;
        }

        if (!idsFabricantes.contains(idFabricante)) {
            System.out.println("OPCAO INVALIDA");
            return -1L;
        }

        return idFabricante;
    }
}
