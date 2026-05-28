package br.com.estudo.sistemaautomotivo.repository;

import br.com.estudo.sistemaautomotivo.model.StatusVeiculo;
import br.com.estudo.sistemaautomotivo.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long>, JpaSpecificationExecutor<Veiculo> {
    List<Veiculo> findByStatus(StatusVeiculo status);
}
