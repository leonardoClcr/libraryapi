package io.github.leonardoClcr.libraryapi.repository;

import io.github.leonardoClcr.libraryapi.model.Autor;
import io.github.leonardoClcr.libraryapi.model.GeneroLivro;
import io.github.leonardoClcr.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // Query Method
    // select * from livro where id_autor = id;
    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);

    List<Livro> findByIsbn(String isbn);

    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    @Query(" select l from Livro as l order by l.titulo, l.preco ")
    List<Livro> listarTodosOrdenadoPorTituloAndPreco();

    @Query(" select a from Livro l join l.autor a ")
    List<Autor> listarAutoresDosLivros();

    @Query("""
        select l.genero
        from Livro l
        join l.autor a
        where a.nacionalidade = 'Brasileiro'
        order by l.genero
    """)
    List<String> listarGenerosAutoresBrasileiros();

    @Query("select l from Livro l where l.genero = :genero order by :paramOrdenacao")
    List<Livro> findByGenero(
            @Param("genero") GeneroLivro generoLivro,
            @Param("paramOrdenacao") String nomePropriedade);

    @Modifying
    @Transactional
    @Query("delete from Livro where genero = ?1 ")
    void deleteByGenero(GeneroLivro generoLivro);
}
