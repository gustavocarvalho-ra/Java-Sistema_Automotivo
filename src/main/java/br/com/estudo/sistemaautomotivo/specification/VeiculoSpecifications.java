package br.com.estudo.sistemaautomotivo.specification;

import br.com.estudo.sistemaautomotivo.model.StatusVeiculo;
import br.com.estudo.sistemaautomotivo.model.Veiculo;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class VeiculoSpecifications {

    private VeiculoSpecifications() {
    }

    public static Specification<Veiculo> marcaId(Long marcaId) {
        return (root, query, cb) -> marcaId == null
                ? cb.conjunction()
                : cb.equal(root.get("modelo").get("marca").get("id"), marcaId);
    }

    public static Specification<Veiculo> modeloId(Long modeloId) {
        return (root, query, cb) -> modeloId == null
                ? cb.conjunction()
                : cb.equal(root.get("modelo").get("id"), modeloId);
    }

    public static Specification<Veiculo> ano(Integer ano) {
        return (root, query, cb) -> ano == null
                ? cb.conjunction()
                : cb.equal(root.get("anoFabricacao"), ano);
    }

    public static Specification<Veiculo> status(StatusVeiculo status) {
        return (root, query, cb) -> status == null
                ? cb.conjunction()
                : cb.equal(root.get("status"), status);
    }

    public static Specification<Veiculo> cor(String cor) {
        return (root, query, cb) -> cor == null || cor.isBlank()
                ? cb.conjunction()
                : cb.like(cb.lower(root.get("cor")), "%" + cor.toLowerCase() + "%");
    }

    public static Specification<Veiculo> precoMin(BigDecimal precoMin) {
        return (root, query, cb) -> precoMin == null
                ? cb.conjunction()
                : cb.greaterThanOrEqualTo(root.get("preco"), precoMin);
    }

    public static Specification<Veiculo> precoMax(BigDecimal precoMax) {
        return (root, query, cb) -> precoMax == null
                ? cb.conjunction()
                : cb.lessThanOrEqualTo(root.get("preco"), precoMax);
    }
}
