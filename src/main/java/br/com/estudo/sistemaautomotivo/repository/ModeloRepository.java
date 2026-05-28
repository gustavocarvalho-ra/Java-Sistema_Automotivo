package br.com.estudo.sistemaautomotivo.repository;

import br.com.estudo.sistemaautomotivo.model.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    boolean existsByNomeIgnoreCaseAndMarcaId(String nome, Long marcaId);
    List<Modelo> findByMarcaId(Long marcaId);
}
