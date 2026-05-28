package br.com.estudo.sistemaautomotivo.dto;

import br.com.estudo.sistemaautomotivo.model.StatusVeiculo;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record VeiculoRequest(
        @NotNull(message = "O id do modelo e obrigatorio")
        Long modeloId,

        @NotNull(message = "O ano de fabricacao e obrigatorio")
        @Min(value = 1886, message = "O ano de fabricacao deve ser maior ou igual a 1886")
        @Max(value = 2100, message = "O ano de fabricacao deve ser menor ou igual a 2100")
        Integer anoFabricacao,

        @NotBlank(message = "A cor e obrigatoria")
        @Size(max = 40, message = "A cor deve ter no maximo 40 caracteres")
        String cor,

        @NotNull(message = "O preco e obrigatorio")
        @DecimalMin(value = "0.01", message = "O preco deve ser maior que zero")
        BigDecimal preco,

        @NotNull(message = "A quilometragem e obrigatoria")
        @PositiveOrZero(message = "A quilometragem nao pode ser negativa")
        Integer quilometragem,

        @NotNull(message = "O status e obrigatorio")
        StatusVeiculo status
) {
}
