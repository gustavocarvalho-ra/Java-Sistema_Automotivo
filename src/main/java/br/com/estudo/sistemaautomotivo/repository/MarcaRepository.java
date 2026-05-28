package br.com.estudo.sistemaautomotivo.repository;

import br.com.estudo.sistemaautomotivo.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
    boolean existsByNomeIgnoreCase(String nome);
    Optional<Marca> findByNomeIgnoreCase(String nome);
}
