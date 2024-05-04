package com.dev.desafio.warley.controller;


import com.dev.desafio.warley.domain.Cliente;
import com.dev.desafio.warley.dto.ClienteDto;
import com.dev.desafio.warley.service.ViaCEPService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.dev.desafio.warley.service.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;
@Slf4j
@RestController
@RequestMapping(value = "/clientes", produces = {"application/json"})
@Tag(name = "Cadastro de Clientes")
public class ClienteController {
    private final ClienteService clienteService;
    private final ViaCEPService viaCEPService;

    @Autowired
    public ClienteController(ClienteService clienteService, ViaCEPService viaCEPService) {
        this.clienteService = clienteService;
        this.viaCEPService = viaCEPService;
    }

    @PostMapping
    @Operation(summary = "Efutua cadastro de um novo cliente", method = "POST")
    public ResponseEntity<ClienteDto> cadastrarCliente(@Valid @RequestBody ClienteDto clienteDto) {

        var novoCliente = clienteService.cadastrarCliente(clienteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }

    @GetMapping
    public ResponseEntity<Page<ClienteDto>> listarClientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        log.info("requisicao para listar clientes: pagina={}, tamanho={}", page, size);
        Page<ClienteDto> clientes = clienteService.listarClientes(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> buscarClientePorId(@PathVariable UUID id) {
        try {
            ClienteDto clienteDto = clienteService.buscarClientePorId(id);
            return ResponseEntity.ok(clienteDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> atualizarCliente(@PathVariable UUID id, @Valid @RequestBody ClienteDto clienteDto) {
        try {
            ClienteDto clienteEditado = clienteService.atualizarCliente(id, clienteDto);
            return ResponseEntity.ok(clienteEditado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> removerCliente(@PathVariable UUID id) {
        boolean deletado = clienteService.removerCliente(id);
        if (deletado) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/endereco/{cep}")
    public Cliente obterEndereco(@PathVariable String cep) {
        return viaCEPService.consultaEndereco(cep);
    }

}



