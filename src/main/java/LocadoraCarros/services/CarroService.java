package LocadoraCarros.services;

import LocadoraCarros.model.Carro;
import LocadoraCarros.model.Fabricante;
import LocadoraCarros.repositorys.FabricanteRepository;
import java.util.List;

public class CarroService {

    public void cadastrar() {
        Carro carroCadastro = new Carro();

        List<Fabricante> listaFabricante = new FabricanteService().consultar();

        System.out.println("Lista Fabricantes");
        System.out.println("________________________________");

        for (Fabricante fabricante : listaFabricante) {
            System.out.println(fabricante.toString());
        }

        System.out.println("________________________________");

        System.out.println("Selecione o fabricante do carro: ");
        String fabricante = new LeituraService().ler();

    }

}
