package br.com.estudo.sistemaautomotivo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ModeloRequest(
        @NotBlank(message = "O nome do modelo e obrigatorio")
        @Size(max = 100, message = "O nome do modelo deve ter no maximo 100 caracteres")
        String nome,

        @NotNull(message = "O id da marca e obrigatorio")
        Long marcaId
) {
}
