package br.com.estudo.sistemaautomotivo.service;

import br.com.estudo.sistemaautomotivo.dto.ModeloRequest;
import br.com.estudo.sistemaautomotivo.dto.ModeloResponse;
import br.com.estudo.sistemaautomotivo.exception.BusinessException;
import br.com.estudo.sistemaautomotivo.exception.ResourceNotFoundException;
import br.com.estudo.sistemaautomotivo.model.Marca;
import br.com.estudo.sistemaautomotivo.model.Modelo;
import br.com.estudo.sistemaautomotivo.repository.ModeloRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ModeloService {

    private final ModeloRepository modeloRepository;
    private final MarcaService marcaService;

    public ModeloService(ModeloRepository modeloRepository, MarcaService marcaService) {
        this.modeloRepository = modeloRepository;
        this.marcaService = marcaService;
    }

    @Transactional(readOnly = true)
    public List<ModeloResponse> listar(Long marcaId) {
        List<Modelo> modelos = marcaId == null
                ? modeloRepository.findAll()
                : modeloRepository.findByMarcaId(marcaId);

        return modelos.stream()
                .map(ModeloResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ModeloResponse buscarPorId(Long id) {
        return ModeloResponse.fromEntity(buscarEntidadePorId(id));
    }

    @Transactional
    public ModeloResponse criar(ModeloRequest request) {
        Marca marca = marcaService.buscarEntidadePorId(request.marcaId());
        validarDuplicidade(request.nome(), request.marcaId());

        Modelo modelo = new Modelo(request.nome().trim(), marca);
        return ModeloResponse.fromEntity(modeloRepository.save(modelo));
    }

    @Transactional
    public ModeloResponse atualizar(Long id, ModeloRequest request) {
        Modelo modelo = buscarEntidadePorId(id);
        Marca marca = marcaService.buscarEntidadePorId(request.marcaId());
        String novoNome = request.nome().trim();

        boolean mudouNome = !modelo.getNome().equalsIgnoreCase(novoNome);
        boolean mudouMarca = !modelo.getMarca().getId().equals(request.marcaId());

        if ((mudouNome || mudouMarca) && modeloRepository.existsByNomeIgnoreCaseAndMarcaId(novoNome, request.marcaId())) {
            throw new BusinessException("Ja existe um modelo com este nome para a marca informada");
        }

        modelo.setNome(novoNome);
        modelo.setMarca(marca);
        return ModeloResponse.fromEntity(modeloRepository.save(modelo));
    }

    @Transactional
    public void remover(Long id) {
        Modelo modelo = buscarEntidadePorId(id);
        modeloRepository.delete(modelo);
    }

    @Transactional(readOnly = true)
    public Modelo buscarEntidadePorId(Long id) {
        return modeloRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Modelo nao encontrado com id: " + id));
    }

    private void validarDuplicidade(String nome, Long marcaId) {
        if (modeloRepository.existsByNomeIgnoreCaseAndMarcaId(nome.trim(), marcaId)) {
            throw new BusinessException("Ja existe um modelo com este nome para a marca informada");
        }
    }
}
