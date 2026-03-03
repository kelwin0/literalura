package com.literalura.model;

import com.literalura.dto.LivroApiDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String titulo;

    // Armazenamos apenas o primeiro idioma da lista conforme especificado no desafio
    private String idioma;

    private Integer numeroDownloads;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    // Construtor padrão exigido pelo JPA
    public Livro() {}

    // Construtor que converte DTO para entidade
    public Livro(LivroApiDTO dto) {
        this.titulo = dto.titulo();
        // Pega o primeiro idioma da lista, ou "desconhecido" se estiver vazia
        this.idioma = dto.idiomas() != null && !dto.idiomas().isEmpty()
                ? dto.idiomas().get(0)
                : "desconhecido";
        this.numeroDownloads = dto.numeroDownloads();
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getIdioma() { return idioma; }
    public Integer getNumeroDownloads() { return numeroDownloads; }
    public Autor getAutor() { return autor; }

    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public void setNumeroDownloads(Integer numeroDownloads) { this.numeroDownloads = numeroDownloads; }
    public void setAutor(Autor autor) { this.autor = autor; }

    @Override
    public String toString() {
        String nomeAutor = autor != null ? autor.getNome() : "autor desconhecido";
        return String.format(
                "------------------------------\n" +
                "Título: %s\nAutor: %s\nIdioma: %s\nDownloads: %d\n" +
                "------------------------------",
                titulo, nomeAutor, idioma,
                numeroDownloads != null ? numeroDownloads : 0
        );
    }
}
