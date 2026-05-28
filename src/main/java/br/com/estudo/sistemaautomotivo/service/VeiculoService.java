package br.com.estudo.sistemaautomotivo.service;

import br.com.estudo.sistemaautomotivo.dto.VeiculoPatchRequest;
import br.com.estudo.sistemaautomotivo.dto.VeiculoRequest;
import br.com.estudo.sistemaautomotivo.dto.VeiculoResponse;
import br.com.estudo.sistemaautomotivo.exception.BusinessException;
import br.com.estudo.sistemaautomotivo.exception.ResourceNotFoundException;
import br.com.estudo.sistemaautomotivo.model.Modelo;
import br.com.estudo.sistemaautomotivo.model.StatusVeiculo;
import br.com.estudo.sistemaautomotivo.model.Veiculo;
import br.com.estudo.sistemaautomotivo.repository.VeiculoRepository;
import br.com.estudo.sistemaautomotivo.specification.VeiculoSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final ModeloService modeloService;

    public VeiculoService(VeiculoRepository veiculoRepository, ModeloService modeloService) {
        this.veiculoRepository = veiculoRepository;
        this.modeloService = modeloService;
    }

    @SuppressWarnings("removal")
    @Transactional(readOnly = true)
    public List<VeiculoResponse> listarComFiltros(
            Long marcaId,
            Long modeloId,
            BigDecimal precoMin,
            BigDecimal precoMax,
            Integer ano,
            StatusVeiculo status,
            String cor
    ) {
        if (precoMin != null && precoMax != null && precoMin.compareTo(precoMax) > 0) {
            throw new BusinessException("O preco minimo nao pode ser maior que o preco maximo");
        }

        Specification<Veiculo> spec = Specification
                .where(VeiculoSpecifications.marcaId(marcaId))
                .and(VeiculoSpecifications.modeloId(modeloId))
                .and(VeiculoSpecifications.precoMin(precoMin))
                .and(VeiculoSpecifications.precoMax(precoMax))
                .and(VeiculoSpecifications.ano(ano))
                .and(VeiculoSpecifications.status(status))
                .and(VeiculoSpecifications.cor(cor));

        return veiculoRepository.findAll(spec)
                .stream()
                .map(VeiculoResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public VeiculoResponse buscarPorId(Long id) {
        return VeiculoResponse.fromEntity(buscarEntidadePorId(id));
    }

    @Transactional
    public VeiculoResponse criar(VeiculoRequest request) {
        Modelo modelo = modeloService.buscarEntidadePorId(request.modeloId());

        Veiculo veiculo = new Veiculo(
                modelo,
                request.anoFabricacao(),
                request.cor().trim(),
                request.preco(),
                request.quilometragem(),
                request.status()
        );

        return VeiculoResponse.fromEntity(veiculoRepository.save(veiculo));
    }

    @Transactional
    public VeiculoResponse atualizar(Long id, VeiculoRequest request) {
        Veiculo veiculo = buscarEntidadePorId(id);
        Modelo modelo = modeloService.buscarEntidadePorId(request.modeloId());

        veiculo.setModelo(modelo);
        veiculo.setAnoFabricacao(request.anoFabricacao());
        veiculo.setCor(request.cor().trim());
        veiculo.setPreco(request.preco());
        veiculo.setQuilometragem(request.quilometragem());
        veiculo.setStatus(request.status());

        return VeiculoResponse.fromEntity(veiculoRepository.save(veiculo));
    }

    @Transactional
    public VeiculoResponse atualizarParcial(Long id, VeiculoPatchRequest request) {
        Veiculo veiculo = buscarEntidadePorId(id);

        if (request.preco() != null) {
            veiculo.setPreco(request.preco());
        }
        if (request.quilometragem() != null) {
            veiculo.setQuilometragem(request.quilometragem());
        }
        if (request.status() != null) {
            veiculo.setStatus(request.status());
        }

        return VeiculoResponse.fromEntity(veiculoRepository.save(veiculo));
    }

    @Transactional
    public VeiculoResponse atualizarStatus(Long id, StatusVeiculo status) {
        Veiculo veiculo = buscarEntidadePorId(id);
        veiculo.setStatus(status);
        return VeiculoResponse.fromEntity(veiculoRepository.save(veiculo));
    }

    @Transactional
    public void remover(Long id) {
        Veiculo veiculo = buscarEntidadePorId(id);
        veiculoRepository.delete(veiculo);
    }

    @Transactional(readOnly = true)
    public Veiculo buscarEntidadePorId(Long id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veiculo nao encontrado com id: " + id));
    }
}
