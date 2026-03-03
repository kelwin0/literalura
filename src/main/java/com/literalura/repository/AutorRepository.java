package com.literalura.repository;

import com.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    // Busca autor pelo nome exato
    Optional<Autor> findByNome(String nome);

    // Lista autores vivos em um determinado ano:
    // o autor estava vivo se nasceu antes ou no ano dado E morreu depois do ano dado (ou ainda está vivo)
    @Query("SELECT a FROM Autor a WHERE a.anoNascimento <= :ano AND (a.anoFalecimento IS NULL OR a.anoFalecimento > :ano)")
    List<Autor> findAutoresVivosNoAno(@Param("ano") Integer ano);
}
