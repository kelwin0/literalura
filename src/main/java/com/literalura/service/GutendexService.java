package com.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.literalura.dto.RespostaApiDTO;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class GutendexService {

    private static final String URL_BASE = "https://gutendex.com/books/";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public GutendexService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    // Consome a API e retorna o JSON como String
    public String buscarDadosDaApi(String url) {
        HttpRequest requisicao = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> resposta = httpClient.send(requisicao,
                    HttpResponse.BodyHandlers.ofString());
            return resposta.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao consumir a API: " + e.getMessage());
        }
    }

    // Busca livros pelo título e retorna o DTO mapeado
    public RespostaApiDTO buscarLivroPorTitulo(String titulo) {
        String tituloCodificado = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
        String url = URL_BASE + "?search=" + tituloCodificado;
        String json = buscarDadosDaApi(url);
        return converterJson(json);
    }

    // Converte a resposta JSON para o DTO usando Jackson
    private RespostaApiDTO converterJson(String json) {
        try {
            return objectMapper.readValue(json, RespostaApiDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao converter o JSON da API: " + e.getMessage());
        }
    }
}
