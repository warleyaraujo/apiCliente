package com.dev.desafio.warley.domain;


import com.dev.desafio.warley.dto.ClienteDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "tb_clientes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private UUID id;

    @Column
    private String nome;

    @Column
    private String cpf;

    @Column
    private String email;

    @Column
    private String fone;

    @Column
    private String logradouro;
    @Column
    private String numero;
    @Column
    private String complemento;
    @Column
    private String bairro;
    @Column
    private String localidade;
    @Column
    private String uf;
    @Column
    private String cep;

    public Cliente(ClienteDto clienteDto) {
        this.nome = clienteDto.nome();
        this.cpf =  clienteDto.cpf();
        this.email = clienteDto.email();
        this.fone = clienteDto.fone();
        this.logradouro = clienteDto.logradouro();
        this.numero = clienteDto.numero();
        this.complemento = clienteDto.complemento();
        this.bairro = clienteDto.bairro();
        this.localidade = clienteDto.localidade();
        this.uf = clienteDto.uf();
        this.cep = clienteDto.cep();

    }
}
