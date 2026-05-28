package br.com.estudo.sistemaautomotivo.dto;

import br.com.estudo.sistemaautomotivo.model.Modelo;

public record ModeloResponse(
        Long id,
        String nome,
        Long marcaId,
        String marcaNome
) {
    public static ModeloResponse fromEntity(Modelo modelo) {
        return new ModeloResponse(
                modelo.getId(),
                modelo.getNome(),
                modelo.getMarca().getId(),
                modelo.getMarca().getNome()
        );
    }
}
