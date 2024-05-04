package com.dev.desafio.warley.service;

import com.dev.desafio.warley.domain.Cliente;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCEPService {

    private static final String VIA_CEP = "https://viacep.com.br/ws/";

    public Cliente consultaEndereco(String cep) {
        String url = VIA_CEP + cep + "/json";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, Cliente.class);
    }
}
