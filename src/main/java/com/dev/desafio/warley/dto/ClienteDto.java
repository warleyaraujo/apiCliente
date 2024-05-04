package com.dev.desafio.warley.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record ClienteDto(
        UUID id,
        @NotBlank(message = "nome completo é preenchimento obrigatorio")
        @Length(min = 3, max = 100, message = "nome deve ser preenchido entre 5 a 80 caracteres")
        String nome,

        @NotBlank(message = "cpf é preenchimento obrigatorio")
        String cpf,
        @NotBlank(message = "email é preenchimento obrigatório")
        @Length(min = 1, max = 100, message = "email deve ser preenchido entre 1 a 10 caracteres")
        String email,
        @NotBlank(message = "numero telefone é preenchimento obrigatorio")
        @Length(min = 1, max = 100, message = "numero telefone deve ser preenchido entre 1 a 100  caracteres")
        String fone,
        @NotBlank(message = "endereco é preenchimento obrigatório")
        @Length(min = 1, max = 100, message = "Logradouro deve ser preenchido entre 1 a 100 caracteres")
        String logradouro,
        @NotBlank(message = "número é preenchimento obrigatório")
        @Length(min = 1, max = 100, message = "Número deve ser preenchido entre 1 a 100 caracteres")
        String numero,
        String complemento,
        @NotBlank(message = "bairro preenchimento obrigatório")
        @Length(min = 1, max = 100, message = "bairro deve ser preenchido entre 1 a 100 caracteres")
        String bairro,
        @NotBlank(message = "localidade preenchimento obrigatório")
        @Length(min = 1, max = 100, message = "localidade deve ser preenchido entre 1 a 100 caracteres")
        String localidade,
        @NotBlank(message = "UF preenchimento obrigatório")
        @Length(min = 1, max = 100, message = "UF deve ser preenchido entre 1 a 100 caracteres")
        String uf,
        @NotBlank(message = "CEP é preenchimento obrigatório")
        @Length(min = 1, max = 100, message = "CEP deve ser preenchido entre 1 a 100 caracteres")
        String cep
) {}
