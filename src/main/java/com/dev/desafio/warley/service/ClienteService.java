package com.dev.desafio.warley.service;

import com.dev.desafio.warley.domain.Cliente;
import com.dev.desafio.warley.dto.ClienteDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ClienteService {
    ClienteDto cadastrarCliente(ClienteDto clienteDto);

    Page<ClienteDto> listarClientes(Pageable pageable);

    ClienteDto buscarClientePorId(UUID id);

    ClienteDto atualizarCliente(UUID id, ClienteDto clienteDto);

    boolean removerCliente(UUID id);

    public ClienteDto clienteToDto(Cliente cliente);

}
