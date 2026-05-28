package br.com.estudo.sistemaautomotivo.service;

import br.com.estudo.sistemaautomotivo.dto.MarcaRequest;
import br.com.estudo.sistemaautomotivo.dto.MarcaResponse;
import br.com.estudo.sistemaautomotivo.exception.BusinessException;
import br.com.estudo.sistemaautomotivo.exception.ResourceNotFoundException;
import br.com.estudo.sistemaautomotivo.model.Marca;
import br.com.estudo.sistemaautomotivo.repository.MarcaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MarcaService {

    private final MarcaRepository marcaRepository;

    public MarcaService(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    @Transactional(readOnly = true)
    public List<MarcaResponse> listar() {
        return marcaRepository.findAll()
                .stream()
                .map(MarcaResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public MarcaResponse buscarPorId(Long id) {
        return MarcaResponse.fromEntity(buscarEntidadePorId(id));
    }

    @Transactional
    public MarcaResponse criar(MarcaRequest request) {
        validarNomeDuplicado(request.nome());
        Marca marca = new Marca(request.nome().trim());
        return MarcaResponse.fromEntity(marcaRepository.save(marca));
    }

    @Transactional
    public MarcaResponse atualizar(Long id, MarcaRequest request) {
        Marca marca = buscarEntidadePorId(id);
        String novoNome = request.nome().trim();

        if (!marca.getNome().equalsIgnoreCase(novoNome) && marcaRepository.existsByNomeIgnoreCase(novoNome)) {
            throw new BusinessException("Ja existe uma marca cadastrada com este nome");
        }

        marca.setNome(novoNome);
        return MarcaResponse.fromEntity(marcaRepository.save(marca));
    }

    @Transactional
    public void remover(Long id) {
        Marca marca = buscarEntidadePorId(id);
        marcaRepository.delete(marca);
    }

    @Transactional(readOnly = true)
    public Marca buscarEntidadePorId(Long id) {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marca nao encontrada com id: " + id));
    }

    private void validarNomeDuplicado(String nome) {
        if (marcaRepository.existsByNomeIgnoreCase(nome.trim())) {
            throw new BusinessException("Ja existe uma marca cadastrada com este nome");
        }
    }
}
