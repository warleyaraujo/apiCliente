package com.dev.desafio.warley.service;

import com.dev.desafio.warley.domain.Cliente;
import com.dev.desafio.warley.dto.ClienteDto;
import com.dev.desafio.warley.exceptions.ClienteNotFoundException;
import com.dev.desafio.warley.repository.ClienteRepository;
import com.dev.desafio.warley.service.serviceImpl.ClienteServiceImpl;
import com.dev.desafio.warley.utils.ClienteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;
public class ClienteServiceTest {

    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        clienteService = new ClienteServiceImpl(clienteRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class CadastrarCliente {

        @Test
        void deveCadastrarCliente() {
            var cliente = ClienteHelper.gerarCliente();

            when(clienteRepository.save(any(Cliente.class)))
                    .thenAnswer(i -> i.getArgument(0));

            var clienteRegistrado = clienteService.cadastrarCliente(clienteService.clienteToDto(cliente));
            assertThat(clienteRegistrado).isInstanceOf(ClienteDto.class).isNotNull();
            assertThat(clienteRegistrado.nome()).isEqualTo(cliente.getNome());
            assertThat(clienteRegistrado.email()).isEqualTo(cliente.getEmail());
            assertThat(clienteRegistrado.cpf()).isEqualTo(cliente.getCpf());
            assertThat(clienteRegistrado.fone()).isEqualTo(cliente.getFone());
            assertThat(clienteRegistrado.cep()).isEqualTo(cliente.getCep());
            assertThat(clienteRegistrado.logradouro()).isEqualTo(cliente.getLogradouro());
            assertThat(clienteRegistrado.numero()).isEqualTo(cliente.getNumero());
            assertThat(clienteRegistrado.complemento()).isEqualTo(cliente.getComplemento());
            assertThat(clienteRegistrado.bairro()).isEqualTo(cliente.getBairro());
            assertThat(clienteRegistrado.localidade()).isEqualTo(cliente.getLocalidade());
            assertThat(clienteRegistrado.uf()).isEqualTo(cliente.getUf());
            verify(clienteRepository, times(1)).save(any(Cliente.class));
        }
    }

    @Nested
    class RemoverCliente {

        @Test
        void deveRemoverCliente() {

            var id = UUID.fromString("a12fadde-8e28-44eb-abc3-946f3c456ae8");
            var cliente = ClienteHelper.gerarCliente();
            cliente.setId(id);

            when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

            var resultado = clienteService.removerCliente(id);

            assertThat(resultado).isTrue();

            when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

            verify(clienteRepository, times(1)).deleteById(id);
        }
    }

    @Nested
    class ListarClientes {

        @Test
        void deveListarClientes() {
            var cliente1 = ClienteHelper.gerarCliente();
            var cliente2 = ClienteHelper.gerarCliente();
            Page<Cliente> lista = new PageImpl<>(Arrays.asList(cliente1, cliente2));
            when(clienteRepository.findAll(any(Pageable.class))).thenReturn(lista);

            var listaRecebida = clienteService.listarClientes(Pageable.unpaged());
            assertThat(listaRecebida).hasSizeGreaterThan(1);
            verify(clienteRepository, times(1)).findAll(any(Pageable.class));
        }
    }

    @Nested
    class BuscarClientePorId {
        @Test
        void deveBuscarClientePorId() {
            var id = UUID.randomUUID();
            var cliente = ClienteHelper.gerarCliente();
            cliente.setId(id);
            var clienteDto = clienteService.clienteToDto(cliente);

            when(clienteRepository.findById(any(UUID.class)))
                    .thenReturn(Optional.of(cliente));

            var clienteObtido = clienteService
                    .buscarClientePorId(cliente.getId());

            assertThat(clienteObtido).isEqualTo(clienteDto);
            verify(clienteRepository, times(1)).findById(any(UUID.class));
        }
    }

    @Test
    void deveGerarExcecao_QuandoBuscarPorId_NaoExiste() {
        var id = UUID.randomUUID();

        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clienteService.buscarClientePorId(id))
                .isInstanceOf(ClienteNotFoundException.class)
                .hasMessage("Cliente não encontrado com o ID: " + id);
        verify(clienteRepository, times(1)).findById(id);
    }

    @Nested
    class EditarCliente {

        @Test
        void devePermitirAlterarCliente(){

            var id = UUID.randomUUID();
            var cliente = ClienteHelper.gerarCliente();
            cliente.setId(id);

            var clienteNovo = cliente;
            clienteNovo.setNome("Teste");
            clienteNovo.setFone("5499199999");
            clienteNovo.setEmail("teste2@teste.com");

            when(clienteRepository.findById(any(UUID.class)))
                    .thenReturn(Optional.of(cliente));

            when(clienteRepository.save(any(Cliente.class)))
                    .thenAnswer(i -> i.getArgument(0));


            var clienteObtidoDto = clienteService.atualizarCliente(id, clienteService.clienteToDto(clienteNovo));
            var clienteObtido = new Cliente(clienteObtidoDto);

            assertThat(clienteObtido).isInstanceOf(Cliente.class).isNotNull();

            assertThat(clienteObtido.getNome()).isEqualTo(clienteNovo.getNome());
            assertThat(clienteObtido.getFone()).isEqualTo(clienteNovo.getFone());
            assertThat(clienteObtido.getEmail()).isEqualTo(clienteNovo.getEmail());
        }
    }
}
