package com.dev.desafio.warley.service.serviceImpl;

import com.dev.desafio.warley.exceptions.ClienteNotFoundException;
import com.dev.desafio.warley.domain.Cliente;
import com.dev.desafio.warley.dto.ClienteDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.dev.desafio.warley.repository.ClienteRepository;
import com.dev.desafio.warley.service.ClienteService;

import java.util.UUID;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository) {this.clienteRepository = clienteRepository;}

    @Override
    public ClienteDto cadastrarCliente(ClienteDto clienteDto) {
        Cliente novoCliente = new Cliente(clienteDto);
        clienteRepository.save(novoCliente);
        return clienteToDto(novoCliente);
    }

    @Override
    public Page<ClienteDto> listarClientes(Pageable pageable) {
        Page<Cliente> clientes = clienteRepository.findAll(pageable);
        return clientes.map(this::clienteToDto);
    }

    @Override
    public ClienteDto buscarClientePorId(UUID id) {
        Cliente cliente;
        cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado com o ID: " + id));
        return clienteToDto(cliente);
    }

    @Override
    public ClienteDto atualizarCliente(UUID id, ClienteDto clienteDto) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente nao encontrado com o ID: " + id));
        Cliente clienteAtual = new Cliente(clienteDto);
        clienteAtual.setId(clienteExistente.getId());
        return clienteToDto(clienteAtual);
    }

    @Override
    public boolean removerCliente(UUID id) {
        clienteRepository.deleteById(id);
        return true;
    }

    @Override
    public ClienteDto clienteToDto(Cliente cliente) {
        return new ClienteDto(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getEmail(),
                cliente.getFone(),
                cliente.getLogradouro(),
                cliente.getNumero(),
                cliente.getComplemento(),
                cliente.getUf(),
                cliente.getCep(),
                cliente.getBairro(),
                cliente.getLocalidade()
        );
    }
}
