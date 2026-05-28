package br.com.estudo.sistemaautomotivo.dto;

import br.com.estudo.sistemaautomotivo.model.StatusVeiculo;
import br.com.estudo.sistemaautomotivo.model.Veiculo;
import java.math.BigDecimal;

public record VeiculoResponse(
        Long id,
        Long marcaId,
        String marcaNome,
        Long modeloId,
        String modeloNome,
        Integer anoFabricacao,
        String cor,
        BigDecimal preco,
        Integer quilometragem,
        StatusVeiculo status
) {
    public static VeiculoResponse fromEntity(Veiculo veiculo) {
        return new VeiculoResponse(
                veiculo.getId(),
                veiculo.getModelo().getMarca().getId(),
                veiculo.getModelo().getMarca().getNome(),
                veiculo.getModelo().getId(),
                veiculo.getModelo().getNome(),
                veiculo.getAnoFabricacao(),
                veiculo.getCor(),
                veiculo.getPreco(),
                veiculo.getQuilometragem(),
                veiculo.getStatus()
        );
    }
}
