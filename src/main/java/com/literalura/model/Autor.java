package com.literalura.model;

import com.literalura.dto.AutorApiDTO;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private Integer anoNascimento;
    private Integer anoFalecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    // Construtor padrão exigido pelo JPA
    public Autor() {}

    // Construtor que converte DTO para entidade
    public Autor(AutorApiDTO dto) {
        this.nome = dto.nome();
        this.anoNascimento = dto.anoNascimento();
        this.anoFalecimento = dto.anoFalecimento();
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public Integer getAnoNascimento() { return anoNascimento; }
    public Integer getAnoFalecimento() { return anoFalecimento; }
    public List<Livro> getLivros() { return livros; }

    public void setNome(String nome) { this.nome = nome; }
    public void setAnoNascimento(Integer anoNascimento) { this.anoNascimento = anoNascimento; }
    public void setAnoFalecimento(Integer anoFalecimento) { this.anoFalecimento = anoFalecimento; }
    public void setLivros(List<Livro> livros) { this.livros = livros; }

    @Override
    public String toString() {
        String nascimento = anoNascimento != null ? String.valueOf(anoNascimento) : "desconhecido";
        String falecimento = anoFalecimento != null ? String.valueOf(anoFalecimento) : "ainda vivo";
        return String.format("Autor: %s | Nascimento: %s | Falecimento: %s",
                nome, nascimento, falecimento);
    }
}
