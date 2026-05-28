package br.com.estudo.sistemaautomotivo.controller;

import br.com.estudo.sistemaautomotivo.dto.VeiculoPatchRequest;
import br.com.estudo.sistemaautomotivo.dto.VeiculoRequest;
import br.com.estudo.sistemaautomotivo.dto.VeiculoResponse;
import br.com.estudo.sistemaautomotivo.model.StatusVeiculo;
import br.com.estudo.sistemaautomotivo.service.VeiculoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @GetMapping
    public ResponseEntity<List<VeiculoResponse>> listarComFiltros(
            @RequestParam(required = false) Long marcaId,
            @RequestParam(required = false) Long modeloId,
            @RequestParam(required = false) BigDecimal precoMin,
            @RequestParam(required = false) BigDecimal precoMax,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) StatusVeiculo status,
            @RequestParam(required = false) String cor
    ) {
        return ResponseEntity.ok(veiculoService.listarComFiltros(marcaId, modeloId, precoMin, precoMax, ano, status, cor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(veiculoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<VeiculoResponse> criar(@RequestBody @Valid VeiculoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculoService.criar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid VeiculoRequest request) {
        return ResponseEntity.ok(veiculoService.atualizar(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<VeiculoResponse> atualizarParcial(@PathVariable Long id, @RequestBody @Valid VeiculoPatchRequest request) {
        return ResponseEntity.ok(veiculoService.atualizarParcial(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<VeiculoResponse> atualizarStatus(@PathVariable Long id, @RequestParam StatusVeiculo status) {
        return ResponseEntity.ok(veiculoService.atualizarStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        veiculoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
