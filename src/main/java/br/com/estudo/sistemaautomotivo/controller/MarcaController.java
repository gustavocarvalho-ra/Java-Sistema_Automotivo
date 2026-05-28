package br.com.estudo.sistemaautomotivo.controller;

import br.com.estudo.sistemaautomotivo.dto.MarcaRequest;
import br.com.estudo.sistemaautomotivo.dto.MarcaResponse;
import br.com.estudo.sistemaautomotivo.service.MarcaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @GetMapping
    public ResponseEntity<List<MarcaResponse>> listar() {
        return ResponseEntity.ok(marcaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(marcaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<MarcaResponse> criar(@RequestBody @Valid MarcaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(marcaService.criar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarcaResponse> atualizar(@PathVariable Long id, @RequestBody @Valid MarcaRequest request) {
        return ResponseEntity.ok(marcaService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        marcaService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
