package br.com.fabioferretto.consultafipe.model;

public record Dados(String codigo, String nome) {
    @Override
    public String toString() {
        return nome + " - Cód: " + codigo;
    }
}
