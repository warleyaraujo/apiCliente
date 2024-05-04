package com.dev.desafio.warley.utils;

import com.dev.desafio.warley.domain.Cliente;

import java.util.UUID;

public abstract class ClienteHelper {
    public static Cliente gerarCliente() {
        var entity = Cliente.builder()
                .id(UUID.randomUUID())
                .nome("Cliente Teste")
                .email("email@email.com")
                .fone("61999999999")
                .cpf("00011122233")
                .cep("72000000")
                .logradouro("quadra")
                .bairro("bairro comum")
                .numero("1")
                .complemento("casa 2")
                .localidade("brasilia")
                .build();
        return entity;
    }

}
