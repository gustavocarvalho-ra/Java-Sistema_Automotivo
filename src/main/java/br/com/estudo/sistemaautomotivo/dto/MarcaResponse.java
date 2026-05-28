package br.com.estudo.sistemaautomotivo.dto;

import br.com.estudo.sistemaautomotivo.model.Marca;

public record MarcaResponse(
        Long id,
        String nome
) {
    public static MarcaResponse fromEntity(Marca marca) {
        return new MarcaResponse(marca.getId(), marca.getNome());
    }
}
