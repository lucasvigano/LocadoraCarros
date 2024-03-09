package LocadoraCarros.services;

import LocadoraCarros.model.Fabricante;
import LocadoraCarros.repositorys.FabricanteRepository;

import java.util.ArrayList;
import java.util.List;

public class FabricanteService {

    public List<Fabricante> consultar() {
        return new FabricanteRepository().consultar();
    }

    public Boolean fabricanteExiste(String pNomeFabricante) throws Exception {
        return new FabricanteRepository().fabricanteExiste(pNomeFabricante);
    }

    public void salvar(Fabricante pFabricante) throws Exception {
        //Verifica se o fabricante já não existe.
        if (fabricanteExiste(pFabricante.getNome())) {
            throw new Exception("Fabricante " + pFabricante.getNome()
                    + " já cadastrado!");
        } else {
            new FabricanteRepository().salvar(pFabricante);
        }
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
