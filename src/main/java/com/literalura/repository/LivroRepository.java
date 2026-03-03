package com.literalura.repository;

import com.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    // Busca um livro pelo título exato
    Optional<Livro> findByTitulo(String titulo);

    // Busca livros que contenham parte do título (case-insensitive)
    List<Livro> findByTituloContainingIgnoreCase(String titulo);

    // Busca livros por idioma
    List<Livro> findByIdioma(String idioma);

    // Conta livros por idioma
    long countByIdioma(String idioma);
}
