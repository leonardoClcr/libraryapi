package io.github.leonardoClcr.libraryapi.repository;

import io.github.leonardoClcr.libraryapi.model.Autor;
import io.github.leonardoClcr.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {
    List<Livro> findByAutor(Autor autor);
}
