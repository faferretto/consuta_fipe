package br.com.fabioferretto.consultafipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Veiculo (@JsonAlias("Valor") String valor,
                       @JsonAlias("Marca") String marca,
                       @JsonAlias("Modelo") String modelo,
                       @JsonAlias("AnoModelo") String ano,
                       @JsonAlias("Combustivel") String combustivel,
                       @JsonAlias("CodigoFipe") String codigoFipe,
                       @JsonAlias("MesReferencia") String mesReferencia) {
    @Override
    public String toString() {
        return marca + " - " + modelo + " (" + ano.replace("32000","Zero KM") + ") " +
                "-> " + valor + " Codigo Fipe: " + codigoFipe + " Mes de Referencia: " + mesReferencia;
    }
}
