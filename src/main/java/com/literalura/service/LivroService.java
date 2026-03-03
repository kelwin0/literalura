package com.literalura.service;

import com.literalura.dto.LivroApiDTO;
import com.literalura.dto.RespostaApiDTO;
import com.literalura.model.Autor;
import com.literalura.model.Livro;
import com.literalura.repository.AutorRepository;
import com.literalura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private GutendexService gutendexService;

    // Busca um livro na API pelo título e salva no banco de dados
    public Livro buscarESalvarLivro(String titulo) {
        RespostaApiDTO resposta = gutendexService.buscarLivroPorTitulo(titulo);

        if (resposta.livros() == null || resposta.livros().isEmpty()) {
            throw new RuntimeException("Nenhum livro encontrado para: " + titulo);
        }

        // Pega apenas o primeiro resultado conforme especificado no desafio
        LivroApiDTO livroApi = resposta.livros().get(0);

        // Verifica se o livro já está cadastrado no banco
        Optional<Livro> livroCadastrado = livroRepository.findByTitulo(livroApi.titulo());
        if (livroCadastrado.isPresent()) {
            System.out.println("\nEsse livro já está no catálogo!");
            return livroCadastrado.get();
        }

        // Cria o livro a partir do DTO
        Livro novoLivro = new Livro(livroApi);

        // Trata o autor: se já existe no banco, usa o existente; senão cria novo
        if (livroApi.autores() != null && !livroApi.autores().isEmpty()) {
            var autorApi = livroApi.autores().get(0);
            Autor autor = autorRepository.findByNome(autorApi.nome())
                    .orElseGet(() -> {
                        Autor novoAutor = new Autor(autorApi);
                        return autorRepository.save(novoAutor);
                    });
            novoLivro.setAutor(autor);
        }

        return livroRepository.save(novoLivro);
    }

    public List<Livro> listarTodosLivros() {
        return livroRepository.findAll();
    }

    public List<Autor> listarTodosAutores() {
        return autorRepository.findAll();
    }

    public List<Autor> listarAutoresVivosNoAno(Integer ano) {
        return autorRepository.findAutoresVivosNoAno(ano);
    }

    public List<Livro> listarLivrosPorIdioma(String idioma) {
        return livroRepository.findByIdioma(idioma);
    }

    public long contarLivrosPorIdioma(String idioma) {
        return livroRepository.countByIdioma(idioma);
    }
}
