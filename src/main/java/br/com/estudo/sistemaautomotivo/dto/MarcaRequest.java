package br.com.estudo.sistemaautomotivo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MarcaRequest(
        @NotBlank(message = "O nome da marca e obrigatorio")
        @Size(max = 100, message = "O nome da marca deve ter no maximo 100 caracteres")
        String nome
) {
}
