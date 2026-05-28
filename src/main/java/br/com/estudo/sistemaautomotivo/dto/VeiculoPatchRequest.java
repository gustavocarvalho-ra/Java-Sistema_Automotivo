package br.com.estudo.sistemaautomotivo.dto;

import br.com.estudo.sistemaautomotivo.model.StatusVeiculo;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record VeiculoPatchRequest(
        @DecimalMin(value = "0.01", message = "O preco deve ser maior que zero")
        BigDecimal preco,

        @PositiveOrZero(message = "A quilometragem nao pode ser negativa")
        Integer quilometragem,

        StatusVeiculo status
) {
}
