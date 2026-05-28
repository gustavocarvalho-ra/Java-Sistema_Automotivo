package br.com.estudo.sistemaautomotivo.controller;

import br.com.estudo.sistemaautomotivo.dto.ModeloRequest;
import br.com.estudo.sistemaautomotivo.dto.ModeloResponse;
import br.com.estudo.sistemaautomotivo.service.ModeloService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modelos")
public class ModeloController {

    private final ModeloService modeloService;

    public ModeloController(ModeloService modeloService) {
        this.modeloService = modeloService;
    }

    @GetMapping
    public ResponseEntity<List<ModeloResponse>> listar(@RequestParam(required = false) Long marcaId) {
        return ResponseEntity.ok(modeloService.listar(marcaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModeloResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(modeloService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ModeloResponse> criar(@RequestBody @Valid ModeloRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(modeloService.criar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModeloResponse> atualizar(@PathVariable Long id, @RequestBody @Valid ModeloRequest request) {
        return ResponseEntity.ok(modeloService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        modeloService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
