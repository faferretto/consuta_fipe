package br.com.fabioferretto.consultafipe.principal;

import br.com.fabioferretto.consultafipe.model.Dados;
import br.com.fabioferretto.consultafipe.model.Modelos;
import br.com.fabioferretto.consultafipe.model.Veiculo;
import br.com.fabioferretto.consultafipe.service.ConsumoAPI;
import br.com.fabioferretto.consultafipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private final String URI = "https://parallelum.com.br/fipe/api/v1/";
    public void exibeMenu() {
        System.out.println("""
                **** OPÇÕES ****
                carros
                motos
                caminhoes
                
                Digite uma das opções para consultar valores:""");

        var tipoVeiculo = leitura.nextLine();

        if (tipoVeiculo.toLowerCase().contains("carr")) {
            tipoVeiculo = "carros/marcas";
        } else if (tipoVeiculo.toLowerCase().contains("mot")) {
            tipoVeiculo = "motos/marcas";
        } else {
            tipoVeiculo = "caminhoes/marcas";
        }

        var json = consumo.obterDados(URI + tipoVeiculo);
        System.out.println(json);
        List<Dados> marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(e -> e.nome().toUpperCase()))
                .forEach(System.out::println);

        System.out.println("Informe o Código da marca para consulta: ");
        var codigoMarca = leitura.nextLine();
        json = consumo.obterDados(URI + tipoVeiculo + "/" + codigoMarca + "/modelos");
        var modeloLista = conversor.obterDados(json, Modelos.class);
        System.out.println("\nModelos dessa marca: ");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(e -> e.nome().toUpperCase()))
                .forEach(System.out::println);
        System.out.println("\nDigite um trecho do nome do veiculo para filtar:");
        var trecho = leitura.nextLine();
        modeloLista.modelos().stream()
                .filter(e -> e.nome().toLowerCase().contains(trecho.toLowerCase()))
                .sorted(Comparator.comparing(e -> e.nome().toUpperCase()))
                .forEach(System.out::println);
        System.out.println("\nInforme o Código do modelo de veiculo para consultar valores: ");
        var codigoVeiculo = leitura.nextLine();
        json = consumo.obterDados(URI + tipoVeiculo + "/" + codigoMarca + "/modelos/" +  codigoVeiculo + "/anos");
        var anos = conversor.obterLista(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoFinal = URI + tipoVeiculo + "/" + codigoMarca + "/modelos/" +  codigoVeiculo + "/anos/" + anos.get(i).codigo();
            json = consumo.obterDados(enderecoFinal);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }
        System.out.println("Todos os veiculos filtrados com avaliações por ano: ");
        veiculos.forEach(System.out::println);
    }
}
